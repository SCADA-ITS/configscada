DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN

    CREATE TABLE backoffice.preliminary_incident_reports (
      id serial NOT NULL,
      incident_id int NULL,
      event_type varchar NULL,
      event_date varchar NULL,
      route varchar NULL,
      km varchar NULL,
      commune varchar NULL,
      event_time varchar NULL,
      sector varchar NULL,
      direction varchar NULL,
      lane varchar NULL,
      police_notice varchar NULL,
      police_presence varchar NULL,
      traffic_management varchar NULL,
      event_description varchar NULL,
      injured varchar NULL,
      deaths varchar NULL,
      status varchar NULL,

      CONSTRAINT pk_preliminary_incident_reports PRIMARY KEY (id)
    ) TABLESPACE tbl_backoffice;

    CREATE INDEX idx_preliminary_incident_reports_incident_id
      ON backoffice.preliminary_incident_reports USING btree (incident_id);

    ALTER TABLE backoffice.preliminary_incident_reports
      ADD CONSTRAINT fk_preliminary_incident_reports_incident_id
      FOREIGN KEY (incident_id)
      REFERENCES backoffice.incidents(id)
      ON DELETE CASCADE;

  END IF;
END $$;

CREATE OR REPLACE FUNCTION backoffice.refresh_preliminary_incident_report_values(p_incident_id integer)
RETURNS void
LANGUAGE plpgsql
AS $$
BEGIN
  UPDATE backoffice.preliminary_incident_reports pir
  SET event_type = et.alias,
      event_date = to_char(i.date, 'DD-MM-YYYY'),
      km = i.km,
      commune = c.alias,
      event_time = to_char(i.date, 'HH24:MI'),
      sector = s.alias,
      direction = d.alias,
      lane = COALESCE((
        SELECT string_agg(DISTINCT vl.alias, ', ' ORDER BY vl.alias)
        FROM backoffice.vehicles v
        JOIN backoffice.vehicle_lanes vl ON vl.id = v.vehicle_lanes_id
        WHERE v.incident_id = i.id
      ), ''),
      police_notice = n.alias,
      police_presence = CASE WHEN EXISTS (
        SELECT 1
        FROM backoffice.support_services ss
        JOIN backoffice.support_service_subtypes sss ON sss.id = ss.subtype_id
        WHERE ss.incident_id = i.id
          AND lower(sss.alias) = 'carabineros'
      ) THEN 'Si' ELSE 'No' END,
      injured = COALESCE((
        SELECT count(*)::varchar
        FROM backoffice.participants p
        JOIN backoffice.participant_injuries pi ON pi.id = p.participant_injury_id
        WHERE p.incident_id = i.id
          AND lower(pi.alias) IN ('leve', 'menos grave', 'grave')
      ), '0'),
      deaths = COALESCE((
        SELECT count(*)::varchar
        FROM backoffice.participants p
        JOIN backoffice.participant_injuries pi ON pi.id = p.participant_injury_id
        WHERE p.incident_id = i.id
          AND lower(pi.alias) = 'muerto'
      ), '0')
  FROM backoffice.incidents i
  LEFT JOIN backoffice.event_type et ON et.id = i.event_type_id
  LEFT JOIN backoffice.comunne c ON c.id = i.comunne_id
  LEFT JOIN backoffice.stretch s ON s.id = i.stretch_id
  LEFT JOIN backoffice.direction d ON d.id = i.direction_id
  LEFT JOIN backoffice.notice n ON n.id = i.notice_id
  WHERE pir.incident_id = p_incident_id
    AND i.id = pir.incident_id;
END;
$$;

CREATE OR REPLACE FUNCTION backoffice.trg_preliminary_incident_report_values()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
  IF TG_OP = 'UPDATE' THEN
    NEW.incident_id := OLD.incident_id;
  END IF;

  SELECT et.alias,
         to_char(i.date, 'DD-MM-YYYY'),
         i.km,
         c.alias,
         to_char(i.date, 'HH24:MI'),
         s.alias,
         d.alias,
         n.alias
  INTO NEW.event_type,
       NEW.event_date,
       NEW.km,
       NEW.commune,
       NEW.event_time,
       NEW.sector,
       NEW.direction,
       NEW.police_notice
  FROM backoffice.incidents i
  LEFT JOIN backoffice.event_type et ON et.id = i.event_type_id
  LEFT JOIN backoffice.comunne c ON c.id = i.comunne_id
  LEFT JOIN backoffice.stretch s ON s.id = i.stretch_id
  LEFT JOIN backoffice.direction d ON d.id = i.direction_id
  LEFT JOIN backoffice.notice n ON n.id = i.notice_id
  WHERE i.id = NEW.incident_id;

  SELECT COALESCE(string_agg(DISTINCT vl.alias, ', ' ORDER BY vl.alias), '')
  INTO NEW.lane
  FROM backoffice.vehicles v
  JOIN backoffice.vehicle_lanes vl ON vl.id = v.vehicle_lanes_id
  WHERE v.incident_id = NEW.incident_id;

  SELECT CASE WHEN EXISTS (
    SELECT 1
    FROM backoffice.support_services ss
    JOIN backoffice.support_service_subtypes sss ON sss.id = ss.subtype_id
    WHERE ss.incident_id = NEW.incident_id
      AND lower(sss.alias) = 'carabineros'
  ) THEN 'Si' ELSE 'No' END
  INTO NEW.police_presence;

  SELECT count(*) FILTER (WHERE lower(pi.alias) IN ('leve', 'menos grave', 'grave'))::varchar,
         count(*) FILTER (WHERE lower(pi.alias) = 'muerto')::varchar
  INTO NEW.injured, NEW.deaths
  FROM backoffice.participants p
  JOIN backoffice.participant_injuries pi ON pi.id = p.participant_injury_id
  WHERE p.incident_id = NEW.incident_id;

  RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION backoffice.trg_refresh_preliminary_report_from_related()
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
    PERFORM backoffice.refresh_preliminary_incident_report_values(old_incident_id);
  END IF;
  IF new_incident_id IS NOT NULL AND new_incident_id IS DISTINCT FROM old_incident_id THEN
    PERFORM backoffice.refresh_preliminary_incident_report_values(new_incident_id);
  END IF;

  IF TG_OP = 'DELETE' THEN RETURN OLD; END IF;
  RETURN NEW;
END;
$$;

CREATE TRIGGER trg_preliminary_incident_report_values
BEFORE INSERT OR UPDATE ON backoffice.preliminary_incident_reports
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_preliminary_incident_report_values();

CREATE TRIGGER trg_preliminary_report_from_incident
AFTER UPDATE OF date, km, event_type_id, comunne_id, stretch_id, direction_id, notice_id
ON backoffice.incidents
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_refresh_preliminary_report_from_related();

CREATE TRIGGER trg_preliminary_report_from_vehicle
AFTER INSERT OR UPDATE OR DELETE ON backoffice.vehicles
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_refresh_preliminary_report_from_related();

CREATE TRIGGER trg_preliminary_report_from_participant
AFTER INSERT OR UPDATE OR DELETE ON backoffice.participants
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_refresh_preliminary_report_from_related();

CREATE TRIGGER trg_preliminary_report_from_support_service
AFTER INSERT OR UPDATE OR DELETE ON backoffice.support_services
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_refresh_preliminary_report_from_related();
