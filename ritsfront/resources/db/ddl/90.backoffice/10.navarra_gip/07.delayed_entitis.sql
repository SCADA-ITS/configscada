DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_incidents') THEN

		create table incidents.delayed_assigned_entities (
			id serial not null,
			delayed_incident_id int null,
			entity_id int null,
			constraint pk_delayed_assigned_entities primary key (id)
		) tablespace tbl_incidents;
		
		
		create index idx_entities_delayed_incident_id on
		incidents.delayed_assigned_entities
			using btree (delayed_incident_id);
		
		create index idx_delayed_entities_id on
		incidents.delayed_assigned_entities
			using btree (entity_id);	
		
		alter table incidents.delayed_assigned_entities add constraint fk_entities_incident_id foreign key (delayed_incident_id) references incidents.delayed_incidents(id) ON DELETE CASCADE;
		alter table incidents.delayed_assigned_entities add constraint fk_entity_id foreign key (entity_id) references incidents.entities(id) ON DELETE CASCADE;

  END IF;
END $$;

