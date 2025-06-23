DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_incidents') THEN

		create table incidents.delayed_incidents (
			id serial not null,
			delayed_incident_report_id  varchar null,
			created_at timestamptz default current_timestamp,
			constraint pk_delayed_incidents primary key (id)
		) tablespace tbl_incidents;	
			
	END IF;
END $$;



