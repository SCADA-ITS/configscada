DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN

    CREATE TABLE backoffice.user_letter_recipient_titles (
        id serial NOT NULL,
        alias varchar NULL,
        CONSTRAINT pk_user_letter_recipient_titles PRIMARY KEY (id)
    ) TABLESPACE tbl_backoffice;

    CREATE TABLE backoffice.user_letter_reports (
        id serial NOT NULL,
        incident_id int NULL,
        report_date timestamptz,
        gg_number varchar NULL,
        recipient_title_id int NULL,
        recipient_name varchar NULL,
        recipient_address varchar NULL,
        recipient_region varchar NULL,
        reference varchar NULL,
        incident_description varchar NULL,
        insurance_company varchar NULL,
        insurance_policy_number varchar NULL,
        insurance_branch varchar NULL,
        signatory_name varchar NULL,
        signatory_role varchar NULL,

        CONSTRAINT pk_user_letter_reports PRIMARY KEY (id)
    ) TABLESPACE tbl_backoffice;

    CREATE INDEX idx_user_letter_reports_incident_id
        ON backoffice.user_letter_reports USING btree (incident_id);

    CREATE INDEX idx_user_letter_reports_recipient_title_id
        ON backoffice.user_letter_reports USING btree (recipient_title_id);

    ALTER TABLE backoffice.user_letter_reports
        ADD CONSTRAINT fk_user_letter_report_incident_id
        FOREIGN KEY (incident_id)
        REFERENCES backoffice.incidents(id) ON DELETE CASCADE;

    ALTER TABLE backoffice.user_letter_reports
        ADD CONSTRAINT fk_user_letter_report_recipient_title_id
        FOREIGN KEY (recipient_title_id)
        REFERENCES backoffice.user_letter_recipient_titles(id);
  END IF;
END $$;
