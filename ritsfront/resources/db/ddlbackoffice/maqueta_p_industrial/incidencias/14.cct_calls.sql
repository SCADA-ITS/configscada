DO $$
BEGIN
	IF EXISTS (
		SELECT 1
		FROM pg_tablespace
		WHERE spcname = 'tbl_backoffice'
	) THEN

		CREATE TABLE backoffice.cct_call_reasons (
			id serial NOT NULL,
			alias varchar NULL,
			CONSTRAINT pk_cct_call_reasons PRIMARY KEY (id)
		) TABLESPACE tbl_backoffice;

		CREATE TABLE backoffice.cct_call_shifts (
			id serial NOT NULL,
			alias varchar NULL,
			CONSTRAINT pk_cct_call_shifts PRIMARY KEY (id)
		) TABLESPACE tbl_backoffice;

		CREATE TABLE backoffice.cct_calls (
			id serial NOT NULL,
			call_number int NULL,
			call_datetime timestamptz NULL,
			response_time varchar NULL,
			reason_id int NULL,
			call_description varchar NULL,
			operator varchar NULL,
			shift_id int NULL,
			CONSTRAINT pk_cct_calls PRIMARY KEY (id)
		) TABLESPACE tbl_backoffice;

		CREATE INDEX idx_cct_calls_reason_id
			ON backoffice.cct_calls USING btree (reason_id);

		CREATE INDEX idx_cct_calls_shift_id
			ON backoffice.cct_calls USING btree (shift_id);

		ALTER TABLE backoffice.cct_calls ADD CONSTRAINT fk_cct_calls_reason_id FOREIGN KEY (reason_id) REFERENCES backoffice.cct_call_reasons(id) ON DELETE RESTRICT;
		ALTER TABLE backoffice.cct_calls ADD CONSTRAINT fk_cct_calls_shift_id FOREIGN KEY (shift_id) REFERENCES backoffice.cct_call_shifts(id) ON DELETE RESTRICT;

	END IF;
END $$;