DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
  
	create table backoffice.monthly_incident_reports (
		id serial not null,
		incident_id int null,
		month_incident_number varchar null,
		report_datetime timestamptz,
		km varchar null,
		commune varchar null,
		sector varchar null,
		carriageway varchar null,
		traffic_direction varchar null,
		involved_lanes varchar null,
		traffic_condition varchar null,
		incident_type varchar null,
		deaths int null,
		injured int null,
		involved_vehicles varchar null,
		infrastructure_damage varchar null,
		event_description varchar null,
		road_assistance varchar null,
		information_responsible varchar null,
		mobile_phone varchar null,

  		constraint pk_monthly_incident_reports primary key (id)
	) tablespace tbl_backoffice;

	create index idx_monthly_incident_reports_incident_id on
	backoffice.monthly_incident_reports
		using btree (incident_id);

	alter table backoffice.monthly_incident_reports add constraint fk_monthly_incident_reports_incident_id foreign key (incident_id) references backoffice.incidents(id) ON DELETE CASCADE;

  END IF;
END $$;