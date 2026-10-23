DO $do$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM pg_tablespace
        WHERE spcname = 'tbl_backoffice'
    ) THEN

        IF to_regclass('backoffice.support_services') IS NULL THEN
            RAISE EXCEPTION
                'Debe cargarse primero 08.support_services.sql';
        END IF;

        CREATE TABLE IF NOT EXISTS backoffice.cct_call_reasons (
            id serial NOT NULL,
            alias varchar NULL,

            CONSTRAINT pk_cct_call_reasons PRIMARY KEY (id)
        ) TABLESPACE tbl_backoffice;

        CREATE TABLE IF NOT EXISTS backoffice.cct_call_shifts (
            id serial NOT NULL,
            alias varchar NULL,

            CONSTRAINT pk_cct_call_shifts PRIMARY KEY (id)
        ) TABLESPACE tbl_backoffice;

        CREATE TABLE IF NOT EXISTS backoffice.cct_calls (
            id serial NOT NULL,
            incident_id int NULL,
            call_number int NULL,
            call_datetime timestamptz,
            response_time varchar NULL,
            reason_id int NULL,
            call_description varchar NULL,
            operator varchar NULL,
            shift_id int NULL,

            CONSTRAINT pk_cct_calls PRIMARY KEY (id)
        ) TABLESPACE tbl_backoffice;

        CREATE INDEX IF NOT EXISTS idx_cct_calls_incident_id
            ON backoffice.cct_calls
            USING btree (incident_id);

        CREATE INDEX IF NOT EXISTS idx_cct_calls_reason_id
            ON backoffice.cct_calls
            USING btree (reason_id);

        CREATE INDEX IF NOT EXISTS idx_cct_calls_shift_id
            ON backoffice.cct_calls
            USING btree (shift_id);

        IF NOT EXISTS (
            SELECT 1
            FROM pg_constraint
            WHERE conrelid = 'backoffice.cct_calls'::regclass
                AND conname = 'fk_cct_calls_incident_id'
        ) THEN
            ALTER TABLE backoffice.cct_calls
                ADD CONSTRAINT fk_cct_calls_incident_id
                FOREIGN KEY (incident_id)
                REFERENCES backoffice.incidents(id)
                ON DELETE CASCADE;
        END IF;

        IF NOT EXISTS (
            SELECT 1
            FROM pg_constraint
            WHERE conrelid = 'backoffice.cct_calls'::regclass
                AND conname = 'fk_cct_calls_reason_id'
        ) THEN
            ALTER TABLE backoffice.cct_calls
                ADD CONSTRAINT fk_cct_calls_reason_id
                FOREIGN KEY (reason_id)
                REFERENCES backoffice.cct_call_reasons(id);
        END IF;

        IF NOT EXISTS (
            SELECT 1
            FROM pg_constraint
            WHERE conrelid = 'backoffice.cct_calls'::regclass
                AND conname = 'fk_cct_calls_shift_id'
        ) THEN
            ALTER TABLE backoffice.cct_calls
                ADD CONSTRAINT fk_cct_calls_shift_id
                FOREIGN KEY (shift_id)
                REFERENCES backoffice.cct_call_shifts(id);
        END IF;

        -- Copia el campo ya calculado en Servicios de apoyo.
        -- No calcula una duracion nueva.
        -- Con varios servicios, conserva sus valores separados por "; ".

        EXECUTE $ddl$

            CREATE OR REPLACE FUNCTION
                backoffice.cct_response_time_from_support(
                    p_incident_id integer
                )
            RETURNS varchar
            LANGUAGE sql
            VOLATILE
            AS $function$
                SELECT STRING_AGG(
                    service.response_time,
                    '; ' ORDER BY service.id
                )::varchar
                FROM backoffice.support_services service
                WHERE service.incident_id = p_incident_id
                    AND service.response_time IS NOT NULL
                    AND BTRIM(service.response_time) <> '';
            $function$;

            -- Evita que dos guardados simultaneos
            -- dejen el campo de CCT desactualizado.

            CREATE OR REPLACE FUNCTION
                backoffice.cct_response_time_write_lock()
            RETURNS trigger
            LANGUAGE plpgsql
            AS $function$
            BEGIN
                PERFORM pg_advisory_xact_lock(140065, 1);
                RETURN NULL;
            END;
            $function$;

            -- Al guardar una llamada, toma el valor
            -- de Servicios de apoyo de su incidencia.

            CREATE OR REPLACE FUNCTION
                backoffice.cct_copy_support_response_time()
            RETURNS trigger
            LANGUAGE plpgsql
            AS $function$
            BEGIN
                NEW.response_time :=
                    backoffice.cct_response_time_from_support(
                        NEW.incident_id
                    );

                RETURN NEW;
            END;
            $function$;

            -- Actualiza las llamadas de una incidencia.

            CREATE OR REPLACE FUNCTION
                backoffice.cct_refresh_response_time(
                    p_incident_id integer
                )
            RETURNS void
            LANGUAGE plpgsql
            AS $function$
            DECLARE
                v_response_time varchar;
            BEGIN
                IF p_incident_id IS NULL THEN
                    RETURN;
                END IF;

                v_response_time :=
                    backoffice.cct_response_time_from_support(
                        p_incident_id
                    );

                UPDATE backoffice.cct_calls
                SET response_time = v_response_time
                WHERE incident_id = p_incident_id
                    AND response_time
                        IS DISTINCT FROM v_response_time;
            END;
            $function$;

            -- Mantiene CCT actualizado cuando se añade,
            -- modifica o elimina un servicio de apoyo.

            CREATE OR REPLACE FUNCTION
                backoffice.cct_refresh_after_support_change()
            RETURNS trigger
            LANGUAGE plpgsql
            AS $function$
            BEGIN
                IF TG_OP = 'INSERT' THEN

                    PERFORM backoffice.cct_refresh_response_time(
                        NEW.incident_id
                    );

                ELSIF TG_OP = 'DELETE' THEN

                    PERFORM backoffice.cct_refresh_response_time(
                        OLD.incident_id
                    );

                ELSE

                    IF OLD.incident_id
                            IS NOT DISTINCT FROM NEW.incident_id
                        AND OLD.response_time
                            IS NOT DISTINCT FROM NEW.response_time
                        AND OLD.id
                            IS NOT DISTINCT FROM NEW.id THEN

                        RETURN NULL;
                    END IF;

                    IF OLD.incident_id
                            IS DISTINCT FROM NEW.incident_id THEN

                        PERFORM backoffice.cct_refresh_response_time(
                            OLD.incident_id
                        );
                    END IF;

                    PERFORM backoffice.cct_refresh_response_time(
                        NEW.incident_id
                    );

                END IF;

                RETURN NULL;
            END;
            $function$;

            DROP TRIGGER IF EXISTS tr_cct_response_time_lock_calls
                ON backoffice.cct_calls;

            CREATE TRIGGER tr_cct_response_time_lock_calls
                BEFORE INSERT OR UPDATE OR DELETE
                ON backoffice.cct_calls
                FOR EACH STATEMENT
                EXECUTE PROCEDURE
                    backoffice.cct_response_time_write_lock();

            DROP TRIGGER IF EXISTS tr_cct_response_time_lock_support
                ON backoffice.support_services;

            CREATE TRIGGER tr_cct_response_time_lock_support
                BEFORE INSERT OR UPDATE OR DELETE
                ON backoffice.support_services
                FOR EACH STATEMENT
                EXECUTE PROCEDURE
                    backoffice.cct_response_time_write_lock();

            DROP TRIGGER IF EXISTS tr_cct_copy_support_response_time
                ON backoffice.cct_calls;

            CREATE TRIGGER tr_cct_copy_support_response_time
                BEFORE INSERT OR UPDATE
                ON backoffice.cct_calls
                FOR EACH ROW
                EXECUTE PROCEDURE
                    backoffice.cct_copy_support_response_time();

            DROP TRIGGER IF EXISTS tr_cct_refresh_after_support_change
                ON backoffice.support_services;

            CREATE TRIGGER tr_cct_refresh_after_support_change
                AFTER INSERT OR UPDATE OR DELETE
                ON backoffice.support_services
                FOR EACH ROW
                EXECUTE PROCEDURE
                    backoffice.cct_refresh_after_support_change();

            -- Actualiza tambien las llamadas ya guardadas.

            UPDATE backoffice.cct_calls cct_call
            SET response_time =
                backoffice.cct_response_time_from_support(
                    cct_call.incident_id
                )
            WHERE cct_call.response_time IS DISTINCT FROM
                backoffice.cct_response_time_from_support(
                    cct_call.incident_id
                );

        $ddl$;

    END IF;
END;
$do$;