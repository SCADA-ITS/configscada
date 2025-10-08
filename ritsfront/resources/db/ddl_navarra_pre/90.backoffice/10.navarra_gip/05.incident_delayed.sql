DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_incidents') THEN

		create table incidents.delayed_incidents (
			id serial not null,
			delayed_incident_report_id  varchar null,
			created_at timestamptz default current_timestamp,
			constraint pk_delayed_incidents primary key (id)
		) tablespace tbs_controltrafico_incidents;	
			
	END IF;
END $$;



