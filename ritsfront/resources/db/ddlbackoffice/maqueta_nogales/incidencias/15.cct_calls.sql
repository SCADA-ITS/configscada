CREATE TABLE backoffice.cct_user_types (
	id serial NOT NULL,
	alias varchar(100) NOT NULL,
	CONSTRAINT pk_cct_user_types PRIMARY KEY (id),
	CONSTRAINT uk_cct_user_types_alias UNIQUE (alias)
) TABLESPACE tbl_backoffice;

CREATE TABLE backoffice.cct_calls (
	id serial NOT NULL,
	call_number integer NULL,
	call_date timestamptz NULL,
	response_time timestamptz NULL,
	kilometer_location varchar(100) NULL,
	user_type_id integer NULL,
	origin_phone varchar(50) NULL,
	detail varchar(4000) NULL,
	observation varchar(4000) NULL,
	assigned_resources varchar(4000) NULL,
	CONSTRAINT pk_cct_calls PRIMARY KEY (id),
	CONSTRAINT uk_cct_calls_call_number UNIQUE (call_number),
	CONSTRAINT fk_cct_calls_user_type FOREIGN KEY (user_type_id)
		REFERENCES backoffice.cct_user_types(id)
) TABLESPACE tbl_backoffice;

CREATE INDEX idx_cct_calls_user_type_id
	ON backoffice.cct_calls USING btree (user_type_id)
	TABLESPACE tbl_backoffice;

CREATE OR REPLACE FUNCTION backoffice.assign_cct_call_number()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
	IF NEW.call_number IS NULL
		AND (NEW.call_date IS NOT NULL
			OR NEW.response_time IS NOT NULL
			OR NEW.kilometer_location IS NOT NULL
			OR NEW.user_type_id IS NOT NULL
			OR NEW.origin_phone IS NOT NULL
			OR NEW.detail IS NOT NULL
			OR NEW.observation IS NOT NULL
			OR NEW.assigned_resources IS NOT NULL) THEN
		PERFORM pg_advisory_xact_lock(hashtext('backoffice.cct_calls.call_number'));
		SELECT COALESCE(MAX(call_number), 0) + 1
		INTO NEW.call_number
		FROM backoffice.cct_calls;
	END IF;

	RETURN NEW;
END;
$$;

CREATE TRIGGER tg_assign_cct_call_number
	BEFORE INSERT OR UPDATE ON backoffice.cct_calls
	FOR EACH ROW
	EXECUTE PROCEDURE backoffice.assign_cct_call_number();
