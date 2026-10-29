DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN

	create table backoffice.user_letter_recipient_titles (
		id serial not null,
		alias varchar null,
		constraint pk_user_letter_recipient_titles primary key (id)
	) tablespace tbl_backoffice;

	create table backoffice.user_letter_reports (
		id serial not null,
		incident_id int null,
		report_date timestamptz,
		gg_number varchar null,
		recipient_title_id int null,
		recipient_name varchar null,
		recipient_address varchar null,
		recipient_region varchar null,
		reference varchar null,
		incident_description varchar null,
		event_date timestamptz null,
		event_km varchar null,
		participants_text varchar null,
		vehicle_patents varchar null,

		constraint pk_user_letter_reports primary key (id)
	) tablespace tbl_backoffice;

	create index idx_user_letter_reports_incident_id on
	backoffice.user_letter_reports
		using btree (incident_id);

	create index idx_user_letter_reports_recipient_title_id on
	backoffice.user_letter_reports
		using btree (recipient_title_id);

	alter table backoffice.user_letter_reports add constraint fk_user_letter_report_incident_id foreign key (incident_id) references backoffice.incidents(id) ON DELETE CASCADE;
	alter table backoffice.user_letter_reports add constraint fk_user_letter_report_recipient_title_id foreign key (recipient_title_id) references backoffice.user_letter_recipient_titles(id);
  END IF;
END $$;

CREATE OR REPLACE FUNCTION backoffice.refresh_user_letter_report_values(p_incident_id integer)
RETURNS void
LANGUAGE plpgsql
AS $$
BEGIN
	UPDATE backoffice.user_letter_reports ulr
	SET event_date = i.date,
		event_km = i.km,
		participants_text = COALESCE((
			SELECT string_agg(concat_ws(' - ', NULLIF(p.participant_name, ''), NULLIF(p.participant_address, '')), CHR(10) ORDER BY p.id)
			FROM backoffice.participants p
			WHERE p.incident_id = ulr.incident_id
		), ''),
		vehicle_patents = COALESCE((
			SELECT string_agg(NULLIF(v.patent, ''), CHR(10) ORDER BY v.id)
			FROM backoffice.vehicles v
			WHERE v.incident_id = ulr.incident_id
		), '')
	FROM backoffice.incidents i
	WHERE ulr.incident_id = p_incident_id
		AND i.id = ulr.incident_id;
END;
$$;

CREATE OR REPLACE FUNCTION backoffice.trg_user_letter_report_values()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
	SELECT i.date, i.km INTO NEW.event_date, NEW.event_km
	FROM backoffice.incidents i WHERE i.id = NEW.incident_id;

	SELECT COALESCE(string_agg(concat_ws(' - ', NULLIF(p.participant_name, ''), NULLIF(p.participant_address, '')), CHR(10) ORDER BY p.id), '')
	INTO NEW.participants_text
	FROM backoffice.participants p WHERE p.incident_id = NEW.incident_id;

	SELECT COALESCE(string_agg(NULLIF(v.patent, ''), CHR(10) ORDER BY v.id), '')
	INTO NEW.vehicle_patents
	FROM backoffice.vehicles v WHERE v.incident_id = NEW.incident_id;

	RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION backoffice.trg_refresh_user_letter_from_related()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
	old_incident_id integer;
	new_incident_id integer;
BEGIN
	IF TG_TABLE_NAME = 'incidents' THEN
		old_incident_id := OLD.id;
		new_incident_id := NEW.id;
	ELSE
		IF TG_OP <> 'INSERT' THEN old_incident_id := OLD.incident_id; END IF;
		IF TG_OP <> 'DELETE' THEN new_incident_id := NEW.incident_id; END IF;
	END IF;

	IF old_incident_id IS NOT NULL THEN
		PERFORM backoffice.refresh_user_letter_report_values(old_incident_id);
	END IF;
	IF new_incident_id IS NOT NULL AND new_incident_id IS DISTINCT FROM old_incident_id THEN
		PERFORM backoffice.refresh_user_letter_report_values(new_incident_id);
	END IF;

	IF TG_OP = 'DELETE' THEN
		RETURN OLD;
	END IF;
	RETURN NEW;
END;
$$;

CREATE TRIGGER trg_user_letter_report_values
BEFORE INSERT OR UPDATE ON backoffice.user_letter_reports
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_user_letter_report_values();

CREATE TRIGGER trg_user_letter_from_incident
AFTER UPDATE OF date, km ON backoffice.incidents
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_refresh_user_letter_from_related();

CREATE TRIGGER trg_user_letter_from_participant
AFTER INSERT OR UPDATE OR DELETE ON backoffice.participants
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_refresh_user_letter_from_related();

CREATE TRIGGER trg_user_letter_from_vehicle
AFTER INSERT OR UPDATE OR DELETE ON backoffice.vehicles
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_refresh_user_letter_from_related();
