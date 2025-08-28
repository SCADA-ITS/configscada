DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_incidents') THEN


		create table incidents.municipalities (
			id serial not null,
			alias varchar null,
			constraint pk_municipalities primary key (id)
		) tablespace tbl_incidents;
		
		create table incidents.assigned_municipalities (
			id serial not null,
			incident_id int null,
			municipality_id int null,
			constraint pk_assigned_municipalities primary key (id)
		) tablespace tbl_incidents;
		
		
		create index idx_municipalities_incident_id on
		incidents.assigned_municipalities
			using btree (incident_id);
		
		create index idx_municipalities_id on
		incidents.assigned_municipalities
			using btree (municipality_id);	
		
		alter table incidents.assigned_municipalities add constraint fk_municipalities_incident_id foreign key (incident_id) references incidents.incidents(id) ON DELETE CASCADE;
		alter table incidents.assigned_municipalities add constraint fk_municipality_id foreign key (municipality_id) references incidents.municipalities(id) ON DELETE CASCADE;

  END IF;
END $$;

