DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
    CREATE TABLE backoffice.insurance_company_reports (
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
      police_station varchar NULL,
      officer_in_charge varchar NULL,
      police_report varchar NULL,
      court varchar NULL,
      vehicle_1 varchar NULL,
      driver_vehicle_1 varchar NULL,
      passenger_vehicle_1 varchar NULL,
      vehicle_2 varchar NULL,
      vehicle_3 varchar NULL,
      vehicle_participants varchar NULL,
      event_description varchar NULL,
      injured varchar NULL,
      deaths varchar NULL,
      status varchar NULL,
      road_condition varchar NULL,
      weather_state varchar NULL,
      visibility varchar NULL,
      CONSTRAINT pk_insurance_company_reports PRIMARY KEY (id)
    ) TABLESPACE tbl_backoffice;

    CREATE INDEX idx_insurance_company_reports_incident_id
      ON backoffice.insurance_company_reports USING btree (incident_id);

    ALTER TABLE backoffice.insurance_company_reports
      ADD CONSTRAINT fk_insurance_company_reports_incident_id
      FOREIGN KEY (incident_id)
      REFERENCES backoffice.incidents(id)
      ON DELETE CASCADE;
  END IF;
END $$;

CREATE OR REPLACE FUNCTION backoffice.refresh_insurance_company_report_values(p_incident_id integer)
RETURNS void
LANGUAGE plpgsql
AS $$
BEGIN
  UPDATE backoffice.insurance_company_reports r
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
      police_station = i.stamped,
      police_report = i.report_number,
      court = i.court,
      vehicle_1 = backoffice.insurance_vehicle_text(i.id, 1),
      vehicle_2 = backoffice.insurance_vehicle_text(i.id, 2),
      vehicle_3 = backoffice.insurance_vehicle_text(i.id, 3),
      driver_vehicle_1 = backoffice.insurance_participant_text(i.id, 'Conductor', false),
      passenger_vehicle_1 = backoffice.insurance_participant_text(i.id, 'Pasajero', true),
      vehicle_participants = backoffice.insurance_vehicle_participants_text(i.id),
      event_description = i.comments,
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
      ), '0'),
      road_condition = rc.alias,
      weather_state = ws.alias,
      visibility = l.alias
  FROM backoffice.incidents i
  LEFT JOIN backoffice.event_type et ON et.id = i.event_type_id
  LEFT JOIN backoffice.comunne c ON c.id = i.comunne_id
  LEFT JOIN backoffice.stretch s ON s.id = i.stretch_id
  LEFT JOIN backoffice.direction d ON d.id = i.direction_id
  LEFT JOIN backoffice.road_conditions rc ON rc.id = i.road_condition_id
  LEFT JOIN backoffice.weather_states ws ON ws.id = i.weather_state_id
  LEFT JOIN backoffice.luminosity l ON l.id = i.luminosity_id
  WHERE r.incident_id = p_incident_id
    AND i.id = r.incident_id;
END;
$$;

CREATE OR REPLACE FUNCTION backoffice.insurance_vehicle_text(p_incident_id integer, p_position integer)
RETURNS varchar
LANGUAGE sql
STABLE
AS $$
  SELECT concat_ws(', ', nullif(vb.alias, ''), nullif(v.patent, ''))::varchar
  FROM backoffice.vehicles v
  LEFT JOIN backoffice.vehicle_brands vb ON vb.id = v.vehicle_brand_id
  WHERE v.incident_id = p_incident_id
  ORDER BY v.id
  OFFSET GREATEST(p_position - 1, 0)
  LIMIT 1;
$$;

CREATE OR REPLACE FUNCTION backoffice.insurance_participant_text(
  p_incident_id integer,
  p_type varchar,
  p_all boolean
)
RETURNS varchar
LANGUAGE sql
STABLE
AS $$
  WITH first_vehicle AS (
    SELECT patent
    FROM backoffice.vehicles
    WHERE incident_id = p_incident_id
    ORDER BY id
    LIMIT 1
  ), participant_rows AS (
    SELECT p.id,
           concat_ws(', ',
             nullif(p.participant_name, ''),
             CASE WHEN nullif(p.participant_rut, '') IS NOT NULL THEN 'RUT: ' || p.participant_rut END,
             CASE WHEN nullif(p.participant_address, '') IS NOT NULL THEN 'Dirección: ' || p.participant_address END,
             CASE WHEN nullif(pi.alias, '') IS NOT NULL THEN 'Lesiones: ' || pi.alias END
           ) AS participant_text,
           CASE
             WHEN lower(trim(COALESCE(p.participant_vehicle, ''))) =
                  lower(trim(COALESCE((SELECT patent FROM first_vehicle), '')))
             THEN 0 ELSE 1
           END AS vehicle_order
    FROM backoffice.participants p
    JOIN backoffice.participant_types pt ON pt.id = p.participant_type_id
    LEFT JOIN backoffice.participant_injuries pi ON pi.id = p.participant_injury_id
    WHERE p.incident_id = p_incident_id
      AND lower(pt.alias) = lower(p_type)
  ), selected_rows AS (
    SELECT participant_text
    FROM participant_rows
    ORDER BY vehicle_order, id
    LIMIT CASE WHEN p_all THEN 2147483647 ELSE 1 END
  )
  SELECT string_agg(participant_text, '; ')::varchar
  FROM selected_rows;
$$;

CREATE OR REPLACE FUNCTION backoffice.insurance_html_escape(p_value text)
RETURNS text
LANGUAGE sql
IMMUTABLE
AS $$
  SELECT replace(replace(replace(replace(COALESCE($1, ''),
         '&', '&amp;'), '<', '&lt;'), '>', '&gt;'), '"', '&quot;');
$$;

CREATE OR REPLACE FUNCTION backoffice.insurance_vehicle_participants_text(p_incident_id integer)
RETURNS varchar
LANGUAGE sql
STABLE
AS $$
  WITH ordered_vehicles AS (
    SELECT v.id,
           row_number() OVER (ORDER BY v.id) AS item_number,
           v.patent,
           vb.alias AS brand
    FROM backoffice.vehicles v
    LEFT JOIN backoffice.vehicle_brands vb ON vb.id = v.vehicle_brand_id
    WHERE v.incident_id = p_incident_id
  ), ordered_participants AS (
    SELECT p.id,
           row_number() OVER (ORDER BY p.id) AS item_number,
           ov.item_number AS vehicle_number,
           p.participant_name,
           p.participant_rut,
           p.participant_vehicle,
           pt.alias AS participant_type,
           pi.alias AS injury,
           p.participant_address
    FROM backoffice.participants p
    LEFT JOIN ordered_vehicles ov
      ON lower(trim(COALESCE(p.participant_vehicle, ''))) = lower(trim(COALESCE(ov.patent, '')))
    LEFT JOIN backoffice.participant_types pt ON pt.id = p.participant_type_id
    LEFT JOIN backoffice.participant_injuries pi ON pi.id = p.participant_injury_id
    WHERE p.incident_id = p_incident_id
  ), report_cards AS (
    SELECT ov.item_number::bigint AS card_order,
           format(
             '<div class="report-card"><div class="report-card-title">Vehículo participante %s</div>' ||
             '<div class="report-card-grid">' ||
             '<div class="report-card-field"><div class="report-card-label">Marca</div><div class="report-card-value">%s</div></div>' ||
             '<div class="report-card-field"><div class="report-card-label">Patente</div><div class="report-card-value">%s</div></div>' ||
             '</div></div>',
             ov.item_number,
             backoffice.insurance_html_escape(ov.brand),
             backoffice.insurance_html_escape(ov.patent)
           ) AS card_html
    FROM ordered_vehicles ov

    UNION ALL

    SELECT (100000 + op.item_number)::bigint AS card_order,
           CASE WHEN op.item_number = 1
                THEN '<div class="section-title dynamic-section-title">Personas involucradas</div>'
                ELSE ''
           END || format(
             '<div class="report-card"><div class="report-card-title">%s</div>' ||
             '<div class="report-card-grid">' ||
             '<div class="report-card-field"><div class="report-card-label">Nombre</div><div class="report-card-value">%s</div></div>' ||
             '<div class="report-card-field"><div class="report-card-label">RUT</div><div class="report-card-value">%s</div></div>' ||
             '<div class="report-card-field"><div class="report-card-label">Dirección</div><div class="report-card-value">%s</div></div>' ||
             '<div class="report-card-field"><div class="report-card-label">Lesiones</div><div class="report-card-value">%s</div></div>' ||
             '</div></div>',
             backoffice.insurance_html_escape(
               concat(COALESCE(NULLIF(op.participant_type, ''), 'Persona involucrada'),
                      CASE WHEN op.vehicle_number IS NOT NULL THEN ' - vehículo ' || op.vehicle_number ELSE '' END)
             ),
             backoffice.insurance_html_escape(op.participant_name),
             backoffice.insurance_html_escape(op.participant_rut),
             backoffice.insurance_html_escape(op.participant_address),
             backoffice.insurance_html_escape(op.injury)
           ) AS card_html
    FROM ordered_participants op
  )
  SELECT string_agg(card_html, '' ORDER BY card_order)::varchar
  FROM report_cards;
$$;

CREATE OR REPLACE FUNCTION backoffice.trg_insurance_company_report_values()
RETURNS trigger
LANGUAGE plpgsql
AS $$
DECLARE
  v_event_type varchar;
  v_event_date varchar;
  v_km varchar;
  v_commune varchar;
  v_event_time varchar;
  v_sector varchar;
  v_direction varchar;
  v_police_station varchar;
  v_police_report varchar;
  v_court varchar;
  v_event_description varchar;
  v_road_condition varchar;
  v_weather_state varchar;
  v_visibility varchar;
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
         i.stamped,
         i.report_number,
         i.court,
         i.comments,
         rc.alias,
         ws.alias,
         l.alias
  INTO v_event_type, v_event_date, v_km, v_commune, v_event_time,
       v_sector, v_direction, v_police_station, v_police_report, v_court,
       v_event_description, v_road_condition, v_weather_state, v_visibility
  FROM backoffice.incidents i
  LEFT JOIN backoffice.event_type et ON et.id = i.event_type_id
  LEFT JOIN backoffice.comunne c ON c.id = i.comunne_id
  LEFT JOIN backoffice.stretch s ON s.id = i.stretch_id
  LEFT JOIN backoffice.direction d ON d.id = i.direction_id
  LEFT JOIN backoffice.road_conditions rc ON rc.id = i.road_condition_id
  LEFT JOIN backoffice.weather_states ws ON ws.id = i.weather_state_id
  LEFT JOIN backoffice.luminosity l ON l.id = i.luminosity_id
  WHERE i.id = NEW.incident_id;

  NEW.event_type := v_event_type;
  NEW.event_date := v_event_date;
  NEW.km := v_km;
  NEW.commune := v_commune;
  NEW.event_time := v_event_time;
  NEW.sector := v_sector;
  NEW.direction := v_direction;
  NEW.police_station := v_police_station;
  NEW.police_report := v_police_report;
  NEW.court := v_court;
  NEW.event_description := v_event_description;
  NEW.road_condition := v_road_condition;
  NEW.weather_state := v_weather_state;
  NEW.visibility := v_visibility;

  SELECT COALESCE(string_agg(DISTINCT vl.alias, ', ' ORDER BY vl.alias), '')
  INTO NEW.lane
  FROM backoffice.vehicles v
  JOIN backoffice.vehicle_lanes vl ON vl.id = v.vehicle_lanes_id
  WHERE v.incident_id = NEW.incident_id;

  NEW.vehicle_1 := backoffice.insurance_vehicle_text(NEW.incident_id, 1);
  NEW.vehicle_2 := backoffice.insurance_vehicle_text(NEW.incident_id, 2);
  NEW.vehicle_3 := backoffice.insurance_vehicle_text(NEW.incident_id, 3);
  NEW.driver_vehicle_1 := backoffice.insurance_participant_text(NEW.incident_id, 'Conductor', false);
  NEW.passenger_vehicle_1 := backoffice.insurance_participant_text(NEW.incident_id, 'Pasajero', true);
  NEW.vehicle_participants := backoffice.insurance_vehicle_participants_text(NEW.incident_id);

  SELECT count(*) FILTER (WHERE lower(pi.alias) IN ('leve', 'menos grave', 'grave'))::varchar,
         count(*) FILTER (WHERE lower(pi.alias) = 'muerto')::varchar
  INTO NEW.injured, NEW.deaths
  FROM backoffice.participants p
  JOIN backoffice.participant_injuries pi ON pi.id = p.participant_injury_id
  WHERE p.incident_id = NEW.incident_id;

  RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION backoffice.trg_refresh_insurance_report_from_related()
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
    PERFORM backoffice.refresh_insurance_company_report_values(old_incident_id);
  END IF;
  IF new_incident_id IS NOT NULL AND new_incident_id IS DISTINCT FROM old_incident_id THEN
    PERFORM backoffice.refresh_insurance_company_report_values(new_incident_id);
  END IF;

  IF TG_OP = 'DELETE' THEN RETURN OLD; END IF;
  RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION backoffice.trg_insurance_collision_event_type()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
  SELECT CASE
           WHEN c.alias IS NOT NULL THEN 'Colisión ' || lower(c.alias)
           ELSE et.alias
         END
  INTO NEW.event_type
  FROM backoffice.incidents i
  LEFT JOIN backoffice.event_type et ON et.id = i.event_type_id
  LEFT JOIN backoffice.collision c ON c.id = i.collision_id
  WHERE i.id = NEW.incident_id;

  RETURN NEW;
END;
$$;

CREATE TRIGGER trg_insurance_company_report_values
BEFORE INSERT OR UPDATE ON backoffice.insurance_company_reports
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_insurance_company_report_values();

CREATE TRIGGER zz_trg_insurance_collision_event_type
BEFORE INSERT OR UPDATE ON backoffice.insurance_company_reports
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_insurance_collision_event_type();

CREATE TRIGGER trg_insurance_report_from_incident
AFTER UPDATE OF date, km, event_type_id, comunne_id, stretch_id, direction_id,
                stamped, report_number, court, comments, road_condition_id,
                weather_state_id, luminosity_id
ON backoffice.incidents
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_refresh_insurance_report_from_related();

CREATE TRIGGER trg_insurance_report_from_vehicle
AFTER INSERT OR UPDATE OR DELETE ON backoffice.vehicles
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_refresh_insurance_report_from_related();

CREATE TRIGGER trg_insurance_report_from_participant
AFTER INSERT OR UPDATE OR DELETE ON backoffice.participants
FOR EACH ROW EXECUTE FUNCTION backoffice.trg_refresh_insurance_report_from_related();
