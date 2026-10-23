-- Creacion del Reporte de Informe Preliminar de cada incidencia.
-- Ejecutar despues de las tablas de incidencias, participantes, vehiculos y danos.
-- Los campos automaticos se rellenan al guardar el reporte y al cambiar sus fuentes.
BEGIN;

CREATE TABLE backoffice.preliminary_incident_reports (
    id serial NOT NULL,
    incident_id int NULL,
    month_incident_number varchar NULL,
    report_datetime timestamptz,
    km varchar NULL,
    commune varchar NULL,
    sector varchar NULL,
    carriageway varchar NULL,
    traffic_direction varchar NULL,
    involved_lanes varchar NULL,
    traffic_condition varchar NULL,
    incident_type varchar NULL,
    deaths int NULL,
    injured int NULL,
    involved_vehicles varchar NULL,
    infrastructure_damage varchar NULL,
    event_description varchar NULL,
    road_assistance varchar NULL,
    information_responsible varchar NULL,
    mobile_phone varchar NULL,
    -- Derivados de report_datetime para imprimir fecha y hora por separado.
    report_date varchar NULL,
    report_time varchar NULL,
    CONSTRAINT pk_preliminary_incident_reports PRIMARY KEY (id),
    CONSTRAINT fk_preliminary_incident_reports_incident_id
        FOREIGN KEY (incident_id) REFERENCES backoffice.incidents(id)
        ON DELETE CASCADE
) TABLESPACE tbl_backoffice;

CREATE INDEX idx_preliminary_incident_reports_incident_id
    ON backoffice.preliminary_incident_reports USING btree (incident_id);

-- Fuente unica de los datos automaticos. Cada conjunto de hijos se calcula
-- por separado para que los JOIN no multipliquen los recuentos.
-- Sin incidencia valida no devuelve una fila: el BEFORE dejara estos datos NULL.
CREATE FUNCTION backoffice.preliminary_incident_report_values(p_incident_id int)
RETURNS TABLE (
    report_datetime timestamptz,
    km varchar,
    traffic_direction varchar,
    deaths int,
    injured int,
    involved_vehicles varchar,
    infrastructure_damage varchar,
    event_description varchar,
    report_date varchar,
    report_time varchar
)
LANGUAGE sql
STABLE
AS $function$
    SELECT
        i.date,
        i.km,
        d.alias,
        persons.deaths,
        persons.injured,
        (
            SELECT count(*)::varchar
            FROM backoffice.vehicles v
            WHERE v.incident_id = i.id
        ),
        (
            SELECT string_agg(dr.description, E'\n' ORDER BY dr.id)::varchar
            FROM backoffice.damages_responsabilities dr
            WHERE dr.incident_id = i.id
              AND NULLIF(btrim(dr.description), '') IS NOT NULL
        ),
        i.comments,
        to_char(i.date AT TIME ZONE 'America/Santiago', 'DD/MM/YYYY')::varchar,
        to_char(i.date AT TIME ZONE 'America/Santiago', 'HH24:MI:SS')::varchar
    FROM backoffice.incidents i
    LEFT JOIN backoffice.direction d ON d.id = i.direction_id
    CROSS JOIN LATERAL (
        SELECT
            count(*) FILTER (
                WHERE lower(btrim(pi.alias)) = 'muerto'
            )::int AS deaths,
            count(*) FILTER (
                WHERE lower(btrim(pi.alias)) IN ('leve', 'menos grave', 'grave')
            )::int AS injured
        FROM backoffice.participants p
        LEFT JOIN backoffice.participant_injuries pi
            ON pi.id = p.participant_injury_id
        WHERE p.incident_id = i.id
    ) persons
    WHERE i.id = p_incident_id;
$function$;

CREATE FUNCTION backoffice.fill_preliminary_incident_report()
RETURNS trigger
LANGUAGE plpgsql
AS $function$
BEGIN
    SELECT
        source.report_datetime,
        source.km,
        source.traffic_direction,
        source.deaths,
        source.injured,
        source.involved_vehicles,
        source.infrastructure_damage,
        source.event_description,
        source.report_date,
        source.report_time,
        et.alias
    INTO
        NEW.report_datetime,
        NEW.km,
        NEW.traffic_direction,
        NEW.deaths,
        NEW.injured,
        NEW.involved_vehicles,
        NEW.infrastructure_damage,
        NEW.event_description,
        NEW.report_date,
        NEW.report_time,
        NEW.incident_type
    FROM backoffice.preliminary_incident_report_values(NEW.incident_id) source
    LEFT JOIN backoffice.incidents i ON i.id = NEW.incident_id
    LEFT JOIN backoffice.event_type et ON et.id = i.event_type_id;

    -- Nunca se asignan los campos manuales de NEW.
    RETURN NEW;
END;
$function$;

CREATE TRIGGER trg_fill_preliminary_incident_report
BEFORE INSERT OR UPDATE ON backoffice.preliminary_incident_reports
FOR EACH ROW EXECUTE PROCEDURE backoffice.fill_preliminary_incident_report();

-- Solicita un UPDATE de los reportes existentes. El BEFORE anterior obtiene
-- todos los datos automaticos actuales, sin modificar los campos manuales.
-- No hay INSERT y, durante el borrado en cascada de una incidencia,
-- no se refresca ningun reporte de la incidencia que ya no existe.
CREATE FUNCTION backoffice.refresh_preliminary_incident_reports(p_incident_id int)
RETURNS void
LANGUAGE plpgsql
AS $function$
BEGIN
    IF p_incident_id IS NULL OR NOT EXISTS (
        SELECT 1 FROM backoffice.incidents i WHERE i.id = p_incident_id
    ) THEN
        RETURN;
    END IF;

    UPDATE backoffice.preliminary_incident_reports report
       SET report_datetime = report.report_datetime
     WHERE report.incident_id = p_incident_id;
END;
$function$;

CREATE FUNCTION backoffice.refresh_preliminary_from_incident()
RETURNS trigger
LANGUAGE plpgsql
AS $function$
BEGIN
    PERFORM backoffice.refresh_preliminary_incident_reports(NEW.id);
    RETURN NULL;
END;
$function$;

CREATE TRIGGER trg_refresh_preliminary_from_incident
AFTER UPDATE ON backoffice.incidents
FOR EACH ROW EXECUTE PROCEDURE backoffice.refresh_preliminary_from_incident();

-- La misma funcion cubre altas, modificaciones, borrados y reasignaciones
-- de hijos entre dos incidencias.
CREATE FUNCTION backoffice.refresh_preliminary_from_child()
RETURNS trigger
LANGUAGE plpgsql
AS $function$
BEGIN
    IF TG_OP = 'DELETE' THEN
        PERFORM backoffice.refresh_preliminary_incident_reports(OLD.incident_id);
    ELSIF TG_OP = 'INSERT' THEN
        PERFORM backoffice.refresh_preliminary_incident_reports(NEW.incident_id);
    ELSE
        IF OLD.incident_id IS DISTINCT FROM NEW.incident_id THEN
            PERFORM backoffice.refresh_preliminary_incident_reports(OLD.incident_id);
        END IF;
        PERFORM backoffice.refresh_preliminary_incident_reports(NEW.incident_id);
    END IF;
    RETURN NULL;
END;
$function$;

CREATE TRIGGER trg_refresh_preliminary_from_participants
AFTER INSERT OR UPDATE OR DELETE ON backoffice.participants
FOR EACH ROW EXECUTE PROCEDURE backoffice.refresh_preliminary_from_child();

CREATE TRIGGER trg_refresh_preliminary_from_vehicles
AFTER INSERT OR UPDATE OR DELETE ON backoffice.vehicles
FOR EACH ROW EXECUTE PROCEDURE backoffice.refresh_preliminary_from_child();

CREATE TRIGGER trg_refresh_preliminary_from_damages
AFTER INSERT OR UPDATE OR DELETE ON backoffice.damages_responsabilities
FOR EACH ROW EXECUTE PROCEDURE backoffice.refresh_preliminary_from_child();

-- Si se corrige el nombre de un maestro, se actualizan tambien las copias.
CREATE FUNCTION backoffice.refresh_preliminary_from_master()
RETURNS trigger
LANGUAGE plpgsql
AS $function$
DECLARE
    affected_incident_id int;
BEGIN
    IF TG_TABLE_NAME = 'direction' THEN
        FOR affected_incident_id IN
            SELECT i.id FROM backoffice.incidents i WHERE i.direction_id = NEW.id
        LOOP
            PERFORM backoffice.refresh_preliminary_incident_reports(affected_incident_id);
        END LOOP;
    ELSIF TG_TABLE_NAME = 'event_type' THEN
        FOR affected_incident_id IN
            SELECT i.id FROM backoffice.incidents i WHERE i.event_type_id = NEW.id
        LOOP
            PERFORM backoffice.refresh_preliminary_incident_reports(affected_incident_id);
        END LOOP;
    ELSIF TG_TABLE_NAME = 'participant_injuries' THEN
        FOR affected_incident_id IN
            SELECT DISTINCT p.incident_id
            FROM backoffice.participants p
            WHERE p.participant_injury_id = NEW.id
              AND p.incident_id IS NOT NULL
        LOOP
            PERFORM backoffice.refresh_preliminary_incident_reports(affected_incident_id);
        END LOOP;
    END IF;
    RETURN NULL;
END;
$function$;

CREATE TRIGGER trg_refresh_preliminary_from_direction
AFTER UPDATE OF alias ON backoffice.direction
FOR EACH ROW
WHEN (OLD.alias IS DISTINCT FROM NEW.alias)
EXECUTE PROCEDURE backoffice.refresh_preliminary_from_master();

CREATE TRIGGER trg_refresh_preliminary_from_injuries
AFTER UPDATE OF alias ON backoffice.participant_injuries
FOR EACH ROW
WHEN (OLD.alias IS DISTINCT FROM NEW.alias)
EXECUTE PROCEDURE backoffice.refresh_preliminary_from_master();

CREATE TRIGGER trg_refresh_preliminary_from_event_type
AFTER UPDATE OF alias ON backoffice.event_type
FOR EACH ROW
WHEN (OLD.alias IS DISTINCT FROM NEW.alias)
EXECUTE PROCEDURE backoffice.refresh_preliminary_from_master();

COMMIT;
