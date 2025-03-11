DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_incidents') THEN
  
		create table incidents.incidents (
			id serial not null,
			incident_report_id  varchar null,
			type_detection varchar null,
			incident_type varchar null,
			incident_sub_type varchar null,
			level varchar null,
			km_start varchar null,
			km_end varchar null,
			user_n varchar null,
			phone  varchar null,
			
			comment  varchar null,
			created_at timestamptz default current_timestamp,
			constraint pk_incidents primary key (id)
		) tablespace tbl_incidents;
		
		create index idx_type_detection on
		incidents.incidents
			using btree (type_detection);			
			
		create index idx_incident_type on
		incidents.incidents
			using btree (incident_type);	
		
		create index idx_incident_sub_type on
		incidents.incidents
			using btree (incident_sub_type);
  END IF;
END $$;



