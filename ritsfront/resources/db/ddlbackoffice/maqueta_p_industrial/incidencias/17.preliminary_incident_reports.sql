DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM pg_tablespace
        WHERE spcname = 'tbl_backoffice'
    ) THEN

        CREATE TABLE backoffice.preliminary_incident_reports (
            id serial NOT NULL,
            contract_name varchar NULL,
            incident_type varchar NULL,
            report_date timestamptz NULL,
            report_date_print varchar NULL,
            report_time_print varchar NULL,
            km varchar NULL,
            sector varchar NULL,
            traffic_direction varchar NULL,
            involved_lanes varchar NULL,
            traffic_condition varchar NULL,
            congestion varchar NULL,
            event_description varchar NULL,
            report_status varchar NULL,
            CONSTRAINT pk_preliminary_incident_reports PRIMARY KEY (id)
        ) TABLESPACE tbl_backoffice;

        CREATE OR REPLACE FUNCTION backoffice.set_preliminary_incident_datetime_print()
        RETURNS trigger
        LANGUAGE plpgsql
        AS $function$
        BEGIN
            NEW.report_date_print := to_char(
                NEW.report_date,
                'DD/MM/YYYY'
            );
            NEW.report_time_print := to_char(
                NEW.report_date,
                'HH24:MI'
            );
            RETURN NEW;
        END;
        $function$;

        CREATE TRIGGER trg_preliminary_incident_datetime_print
        BEFORE INSERT OR UPDATE OF report_date
        ON backoffice.preliminary_incident_reports
        FOR EACH ROW
        EXECUTE PROCEDURE backoffice.set_preliminary_incident_datetime_print();

    END IF;
END $$;