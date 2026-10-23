DO $do$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM pg_tablespace
        WHERE spcname = 'tbl_backoffice'
    ) THEN

        CREATE TABLE IF NOT EXISTS backoffice.insurance_reports (
            id serial NOT NULL,
            incident_id int NOT NULL,
            report_datetime timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
            report_text varchar NULL,
            additional_text varchar NULL DEFAULT
                'En cumplimiento con las BALI, se adjunta carta al usuario propietario y conductor del vehículo, esto para fines pertinentes relacionado con la denuncia preventiva a la póliza RC.',

            CONSTRAINT pk_insurance_reports PRIMARY KEY (id),
            CONSTRAINT fk_insurance_reports_incident_id
                FOREIGN KEY (incident_id)
                REFERENCES backoffice.incidents(id)
                ON DELETE CASCADE
        ) TABLESPACE tbl_backoffice;

        CREATE INDEX IF NOT EXISTS idx_insurance_reports_incident_id
            ON backoffice.insurance_reports
            USING btree (incident_id);

        EXECUTE $ddl$

            -- Al guardar un reporte nuevo, inicializa su fecha y redaccion.
            -- Editar despues el reporte no modifica la incidencia original.
            CREATE OR REPLACE FUNCTION backoffice.insurance_report_defaults()
            RETURNS trigger
            LANGUAGE plpgsql
            AS $function$
            BEGIN
                NEW.report_datetime :=
                    COALESCE(NEW.report_datetime, CURRENT_TIMESTAMP);

                NEW.additional_text := COALESCE(
                    NEW.additional_text,
                    'En cumplimiento con las BALI, se adjunta carta al usuario propietario y conductor del vehículo, esto para fines pertinentes relacionado con la denuncia preventiva a la póliza RC.'
                );

                IF NEW.report_text IS NULL
                    OR BTRIM(NEW.report_text) = '' THEN
                    SELECT incident.comments
                    INTO NEW.report_text
                    FROM backoffice.incidents incident
                    WHERE incident.id = NEW.incident_id;
                END IF;

                RETURN NEW;
            END;
            $function$;

            DROP TRIGGER IF EXISTS tr_insurance_report_defaults
                ON backoffice.insurance_reports;

            CREATE TRIGGER tr_insurance_report_defaults
                BEFORE INSERT
                ON backoffice.insurance_reports
                FOR EACH ROW
                EXECUTE PROCEDURE backoffice.insurance_report_defaults();

            -- Una fila por reporte, con la informacion de su incidencia.
            CREATE OR REPLACE VIEW backoffice.insurance_report_data AS
            SELECT
                report.id,
                report.incident_id,
                report.report_datetime,
                TO_CHAR(
                    report.report_datetime AT TIME ZONE 'America/Santiago',
                    'DD/MM/YYYY'
                ) AS report_date,
                report.report_text,
                report.additional_text,
                incident.incident_report_id AS incident_number,
                incident.operator AS incident_operator,
                incident.km,
                TO_CHAR(
                    incident.date AT TIME ZONE 'America/Santiago',
                    'DD/MM/YYYY HH24:MI'
                ) AS incident_datetime,
                event.alias AS event,
                side.alias AS road_side,
                stretch.alias AS stretch,
                direction.alias AS traffic_direction,
                event_type.alias AS event_type,
                TO_CHAR(
                    incident.detection_time AT TIME ZONE 'America/Santiago',
                    'DD/MM/YYYY HH24:MI'
                ) AS detection_datetime,
                collision.alias AS collision,
                object_crash.alias AS object_crash,
                vehicle_crash.alias AS vehicle_crash,
                relative_location.alias AS relative_location,
                road_condition.alias AS road_condition,
                road_type.alias AS road_type,
                road_state.alias AS road_state,
                weather_state.alias AS weather_state,
                luminosity.alias AS luminosity,
                artificial_light.alias AS artificial_light,
                detection.alias AS detection_method,
                notice.alias AS police_notice,
                cause.alias AS probable_cause,
                incident.comments AS incident_description
            FROM backoffice.insurance_reports report
            INNER JOIN backoffice.incidents incident
                ON incident.id = report.incident_id
            LEFT JOIN backoffice.event event
                ON event.id = incident.event_id
            LEFT JOIN backoffice.side side
                ON side.id = incident.side_id
            LEFT JOIN backoffice.stretch stretch
                ON stretch.id = incident.stretch_id
            LEFT JOIN backoffice.direction direction
                ON direction.id = incident.direction_id
            LEFT JOIN backoffice.event_type event_type
                ON event_type.id = incident.event_type_id
            LEFT JOIN backoffice.collision collision
                ON collision.id = incident.collision_id
            LEFT JOIN backoffice.object_crash object_crash
                ON object_crash.id = incident.object_crash_id
            LEFT JOIN backoffice.vehicle_crash vehicle_crash
                ON vehicle_crash.id = incident.vehicle_crash_id
            LEFT JOIN backoffice.relative_location relative_location
                ON relative_location.id = incident.relative_location_id
            LEFT JOIN backoffice.road_conditions road_condition
                ON road_condition.id = incident.road_condition_id
            LEFT JOIN backoffice.road_types road_type
                ON road_type.id = incident.road_type_id
            LEFT JOIN backoffice.road_states road_state
                ON road_state.id = incident.road_state_id
            LEFT JOIN backoffice.weather_states weather_state
                ON weather_state.id = incident.weather_state_id
            LEFT JOIN backoffice.luminosity luminosity
                ON luminosity.id = incident.luminosity_id
            LEFT JOIN backoffice.ligth artificial_light
                ON artificial_light.id = incident.ligth_id
            LEFT JOIN backoffice.detection detection
                ON detection.id = incident.detection_id
            LEFT JOIN backoffice.notice notice
                ON notice.id = incident.notice_id
            LEFT JOIN backoffice.causes cause
                ON cause.id = incident.cause_id;

            -- Una fila por vehiculo y reporte; sin cruzar participantes.
            CREATE OR REPLACE VIEW backoffice.insurance_report_vehicles AS
            SELECT
                (
                    report.id::bigint * 2147483648 + vehicle.id::bigint
                ) AS id,
                report.id AS insurance_report_id,
                vehicle.id AS vehicle_id,
                vehicle.incident_id,
                incident.incident_report_id AS incident_number,
                vehicle_type.alias AS vehicle_type,
                vehicle_service.alias AS vehicle_service,
                consequence.alias AS consequence,
                manoeuvre.alias AS manoeuvre,
                vehicle.patent,
                vehicle.lane AS roadway,
                brand.alias AS brand,
                direction.alias AS direction,
                lane.alias AS lane,
                hit_and_run.alias AS hit_and_run
            FROM backoffice.insurance_reports report
            INNER JOIN backoffice.vehicles vehicle
                ON vehicle.incident_id = report.incident_id
            INNER JOIN backoffice.incidents incident
                ON incident.id = report.incident_id
            LEFT JOIN backoffice.vehicle_types vehicle_type
                ON vehicle_type.id = vehicle.vehicle_type_id
            LEFT JOIN backoffice.vehicle_services vehicle_service
                ON vehicle_service.id = vehicle.vehicle_service_id
            LEFT JOIN backoffice.vehicle_consecuences consequence
                ON consequence.id = vehicle.vehicle_consecuence_id
            LEFT JOIN backoffice.vehicle_manoeuvres manoeuvre
                ON manoeuvre.id = vehicle.vehicle_manoeuvre_id
            LEFT JOIN backoffice.vehicle_brands brand
                ON brand.id = vehicle.vehicle_brand_id
            LEFT JOIN backoffice.vehicle_directions direction
                ON direction.id = vehicle.vehicle_direction_id
            LEFT JOIN backoffice.vehicle_lanes lane
                ON lane.id = vehicle.vehicle_lane_id
            LEFT JOIN backoffice.vehicle_hit_and_run hit_and_run
                ON hit_and_run.id = vehicle.vehicle_hit_and_run_id;

            -- Una fila por participante y reporte; sin cruzar vehiculos.
            CREATE OR REPLACE VIEW backoffice.insurance_report_participants AS
            SELECT
                (
                    report.id::bigint * 2147483648 + participant.id::bigint
                ) AS id,
                report.id AS insurance_report_id,
                participant.id AS participant_id,
                participant.incident_id,
                incident.incident_report_id AS incident_number,
                participant.participant_name,
                participant.participant_rut,
                participant.participant_age,
                participant.participant_address,
                participant.participant_vehicle AS patent,
                participant_type.alias AS participant_type,
                injury.alias AS injury,
                nationality.alias AS nationality,
                hit_and_run.alias AS hit_and_run,
                attention.alias AS attention
            FROM backoffice.insurance_reports report
            INNER JOIN backoffice.participants participant
                ON participant.incident_id = report.incident_id
            INNER JOIN backoffice.incidents incident
                ON incident.id = report.incident_id
            LEFT JOIN backoffice.participant_types participant_type
                ON participant_type.id = participant.participant_type_id
            LEFT JOIN backoffice.participant_injuries injury
                ON injury.id = participant.participant_injury_id
            LEFT JOIN backoffice.participant_nacionalities nationality
                ON nationality.id = participant.participant_nacionality_id
            LEFT JOIN backoffice.participant_hit_and_runs hit_and_run
                ON hit_and_run.id = participant.participant_hit_and_run_id
            LEFT JOIN backoffice.participant_attentions attention
                ON attention.id = participant.participant_attention_id;

        $ddl$;

    END IF;
END;
$do$;
