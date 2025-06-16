DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_incidents') THEN

		create table incidents.incidents (
			id serial not null,
			incident_report_id  varchar null,
			incident_type varchar null,
			incident_sub_type varchar null,
			level varchar null,
			location varchar null,
			comment  varchar null,
			created_at timestamptz default current_timestamp,
			constraint pk_incidents primary key (id)
		) tablespace tbl_incidents;	
			
	END IF;
END $$;



