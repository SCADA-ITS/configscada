DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_incidents') THEN

		
		create table incidents.delayed_assigned_municipalities (
			id serial not null,
			delayed_incident_id int null,
			municipality_id int null,
			constraint pk_delayed_assigned_municipalities primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		
		create index idx_delayed_municipalities_incident_id on
		incidents.delayed_assigned_municipalities
			using btree (delayed_incident_id);
		
		create index idx_delayed_municipalities_id on
		incidents.delayed_assigned_municipalities
			using btree (municipality_id);	
		
		alter table incidents.delayed_assigned_municipalities add constraint fk_delayed_municipalities_incident_id foreign key (delayed_incident_id) references incidents.delayed_incidents(id) ON DELETE CASCADE;
		alter table incidents.delayed_assigned_municipalities add constraint fk_delayed_municipality_id foreign key (municipality_id) references incidents.municipalities(id) ON DELETE CASCADE;

  END IF;
END $$;

