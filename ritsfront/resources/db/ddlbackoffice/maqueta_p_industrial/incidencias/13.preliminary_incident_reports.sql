-- Creacion del Reporte de Informe Preliminar de cada incidencia.
-- Ejecutar despues de las tablas de incidencias, participantes y vehiculos.
-- Solo conserva los datos del formato vigente y los campos internos de impresion.
BEGIN;

CREATE TABLE backoffice.preliminary_incident_reports (
    id serial NOT NULL,
    incident_id int NULL,
    event_category varchar NULL,
    report_date varchar NULL,
    report_time varchar NULL,
    km varchar NULL,
    sector varchar NULL,
    traffic_direction varchar NULL,
    involved_lanes varchar NULL,
    traffic_condition varchar NULL,
    deaths int NULL,
    injured int NULL,
    congestion varchar NULL,
    event_description varchar NULL,
    report_status varchar NULL,
    consequences_html varchar NULL,
    report_title varchar NULL,
    CONSTRAINT pk_preliminary_incident_reports PRIMARY KEY (id),
    CONSTRAINT fk_preliminary_incident_reports_incident_id
        FOREIGN KEY (incident_id) REFERENCES backoffice.incidents(id)
        ON DELETE CASCADE
) TABLESPACE tbl_backoffice;

CREATE INDEX idx_preliminary_incident_reports_incident_id
    ON backoffice.preliminary_incident_reports USING btree (incident_id);

-- Fuente unica de los diez datos automaticos del informe.
-- Vehiculos y participantes se agregan por separado para evitar recuentos duplicados.
-- Sin incidencia valida no devuelve una fila: el BEFORE deja estos datos NULL.
CREATE FUNCTION backoffice.preliminary_incident_report_values(p_incident_id int)
RETURNS TABLE (
    event_category varchar,
    report_date varchar,
    report_time varchar,
    km varchar,
    sector varchar,
    traffic_direction varchar,
    involved_lanes varchar,
    deaths int,
    injured int,
    event_description varchar
)
LANGUAGE sql
STABLE
AS $function$
    SELECT
        event.alias,
        to_char(i.date AT TIME ZONE 'America/Santiago', 'DD/MM/YYYY')::varchar,
        to_char(i.date AT TIME ZONE 'America/Santiago', 'HH24:MI:SS')::varchar,
        i.km,
        vehicle_values.sector,
        direction.alias,
        vehicle_values.involved_lanes,
        persons.deaths,
        persons.injured,
        i.comments
    FROM backoffice.incidents i
    LEFT JOIN backoffice.event event ON event.id = i.event_id
    LEFT JOIN backoffice.direction direction ON direction.id = i.direction_id
    CROSS JOIN LATERAL (
        SELECT
            (
                SELECT string_agg(sector_values.alias, ', ' ORDER BY sector_values.id)::varchar
                FROM (
                    SELECT sector.alias, min(sector.id) AS id
                    FROM backoffice.vehicles vehicle
                    JOIN backoffice.sector sector ON sector.id = vehicle.sector_id
                    WHERE vehicle.incident_id = i.id
                      AND NULLIF(btrim(sector.alias), '') IS NOT NULL
                    GROUP BY sector.alias
                ) sector_values
            ) AS sector,
            (
                SELECT string_agg(lane_values.alias, ', ' ORDER BY lane_values.id)::varchar
                FROM (
                    SELECT lane.alias, min(lane.id) AS id
                    FROM backoffice.vehicles vehicle
                    JOIN backoffice.vehicle_lanes lane ON lane.id = vehicle.vehicle_lane_id
                    WHERE vehicle.incident_id = i.id
                      AND NULLIF(btrim(lane.alias), '') IS NOT NULL
                    GROUP BY lane.alias
                ) lane_values
            ) AS involved_lanes
    ) vehicle_values
    CROSS JOIN LATERAL (
        SELECT
            count(*) FILTER (
                WHERE lower(btrim(injury.alias)) = 'muerto'
            )::int AS deaths,
            count(*) FILTER (
                WHERE lower(btrim(injury.alias)) IN ('leve', 'menos grave', 'grave')
            )::int AS injured
        FROM backoffice.participants participant
        LEFT JOIN backoffice.participant_injuries injury
            ON injury.id = participant.participant_injury_id
        WHERE participant.incident_id = i.id
    ) persons
    WHERE i.id = p_incident_id;
$function$;

CREATE FUNCTION backoffice.fill_preliminary_incident_report()
RETURNS trigger
LANGUAGE plpgsql
AS $function$
BEGIN
    SELECT
        source.event_category,
        source.report_date,
        source.report_time,
        source.km,
        source.sector,
        source.traffic_direction,
        source.involved_lanes,
        source.deaths,
        source.injured,
        source.event_description
    INTO
        NEW.event_category,
        NEW.report_date,
        NEW.report_time,
        NEW.km,
        NEW.sector,
        NEW.traffic_direction,
        NEW.involved_lanes,
        NEW.deaths,
        NEW.injured,
        NEW.event_description
    FROM backoffice.preliminary_incident_report_values(NEW.incident_id) source;

    -- El generador existente inserta este contenido mediante innerHTML.
    -- Se genera en una sola linea para evitar que el front lo envuelva en un parrafo.
    NEW.report_title := CASE lower(btrim(NEW.event_category))
        WHEN 'accidente' THEN 'INFORME EN CASO DE ACCIDENTE'
        WHEN 'incidente' THEN 'INFORME EN CASO DE INCIDENTE.'
        ELSE 'INFORME PRELIMINAR'
    END;

    IF lower(btrim(NEW.event_category)) = 'accidente' THEN
        NEW.consequences_html := concat(
            '<div class="report-line consequence-line"><div class="report-content">',
            '<span class="label">Fallecidos:</span> ',
            '<span class="data value">', COALESCE(NEW.deaths::text, ''), '</span></div></div>',
            '<div class="report-line consequence-line"><div class="report-content">',
            '<span class="label">Lesionados:</span> ',
            '<span class="data value">', COALESCE(NEW.injured::text, ''), '</span></div></div>'
        );
    ELSE
        -- Una cadena no vacia evita que el generador sustituya el valor por un guion.
        NEW.consequences_html := '<span hidden></span>';
    END IF;

    -- Nunca se asignan los tres campos manuales de NEW:
    -- traffic_condition, congestion y report_status.
    RETURN NEW;
END;
$function$;

CREATE TRIGGER trg_fill_preliminary_incident_report
BEFORE INSERT OR UPDATE ON backoffice.preliminary_incident_reports
FOR EACH ROW EXECUTE PROCEDURE backoffice.fill_preliminary_incident_report();

-- Solicita el recálculo de los informes existentes sin cambiar su incidencia
-- ni sus campos manuales. No crea nuevos informes.
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
       SET incident_id = report.incident_id
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

-- Altas, modificaciones, borrados y reasignaciones de hijos entre incidencias.
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

-- Si se corrige el nombre de un maestro utilizado, actualiza tambien las copias.
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
    ELSIF TG_TABLE_NAME = 'event' THEN
        FOR affected_incident_id IN
            SELECT i.id FROM backoffice.incidents i WHERE i.event_id = NEW.id
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
    ELSIF TG_TABLE_NAME = 'sector' THEN
        FOR affected_incident_id IN
            SELECT DISTINCT v.incident_id
            FROM backoffice.vehicles v
            WHERE v.sector_id = NEW.id
              AND v.incident_id IS NOT NULL
        LOOP
            PERFORM backoffice.refresh_preliminary_incident_reports(affected_incident_id);
        END LOOP;
    ELSIF TG_TABLE_NAME = 'vehicle_lanes' THEN
        FOR affected_incident_id IN
            SELECT DISTINCT v.incident_id
            FROM backoffice.vehicles v
            WHERE v.vehicle_lane_id = NEW.id
              AND v.incident_id IS NOT NULL
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

CREATE TRIGGER trg_refresh_preliminary_from_event
AFTER UPDATE OF alias ON backoffice.event
FOR EACH ROW
WHEN (OLD.alias IS DISTINCT FROM NEW.alias)
EXECUTE PROCEDURE backoffice.refresh_preliminary_from_master();

CREATE TRIGGER trg_refresh_preliminary_from_sector
AFTER UPDATE OF alias ON backoffice.sector
FOR EACH ROW
WHEN (OLD.alias IS DISTINCT FROM NEW.alias)
EXECUTE PROCEDURE backoffice.refresh_preliminary_from_master();

CREATE TRIGGER trg_refresh_preliminary_from_vehicle_lanes
AFTER UPDATE OF alias ON backoffice.vehicle_lanes
FOR EACH ROW
WHEN (OLD.alias IS DISTINCT FROM NEW.alias)
EXECUTE PROCEDURE backoffice.refresh_preliminary_from_master();

COMMIT;
