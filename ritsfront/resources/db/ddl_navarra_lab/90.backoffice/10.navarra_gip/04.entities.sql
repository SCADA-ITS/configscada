DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_incidents') THEN


		create table incidents.entities (
			id serial not null,
			alias varchar null,
			constraint pk_entities primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create table incidents.assigned_entities (
			id serial not null,
			incident_id int null,
			entity_id int null,
			constraint pk_assigned_entities primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		
		create index idx_entities_incident_id on
		incidents.assigned_entities
			using btree (incident_id);
		
		create index idx_entities_id on
		incidents.assigned_entities
			using btree (entity_id);	
		
		alter table incidents.assigned_entities add constraint fk_entities_incident_id foreign key (incident_id) references incidents.incidents(id) ON DELETE CASCADE;
		alter table incidents.assigned_entities add constraint fk_entity_id foreign key (entity_id) references incidents.entities(id) ON DELETE CASCADE;

  END IF;
END $$;

