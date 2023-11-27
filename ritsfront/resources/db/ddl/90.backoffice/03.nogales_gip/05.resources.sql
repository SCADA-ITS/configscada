DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_nogales_gip') THEN

		create table nogales_gip.actions (
			id serial not null,
			alias varchar null,
			constraint pk_actions primary key (id)
		) tablespace tbl_nogales_gip;
		
		create table nogales_gip.typologies (
			id serial not null,
			alias varchar null,
			constraint pk_typologies primary key (id)
		) tablespace tbl_nogales_gip;
		
		create table nogales_gip.resources (
			id serial not null,
			alias varchar null,
			total_unit int null,
			current_unit int null,
			constraint pk_resorces primary key (id)
		) tablespace tbl_nogales_gip;
		
		create table nogales_gip.assigned_resources (
			id serial not null,
			incident_id int null,
			comment varchar null,
			action_id int null,
			typology_id int null,
			resource_id int null,
			notice_date timestamptz default current_timestamp,
			arrival_date timestamptz null,
			release_date timestamptz null,
			constraint pk_assigned_resorces primary key (id)
		) tablespace tbl_nogales_gip;
		
		
		create index idx_incident_id on
		nogales_gip.assigned_resources
			using btree (incident_id);
		
		create index idx_action_id on
		nogales_gip.assigned_resources
			using btree (action_id);
		
		create index idx_typologie_id on
		nogales_gip.assigned_resources
			using btree (typology_id);
		
		create index idx_resource_id on
		nogales_gip.assigned_resources
			using btree (resource_id);
		
		alter table nogales_gip.assigned_resources add constraint fk_incident_id foreign key (incident_id) references nogales_gip.incidents(id) ON DELETE CASCADE;
		alter table nogales_gip.assigned_resources add constraint fk_action_id foreign key (action_id) references nogales_gip.actions(id) ON DELETE CASCADE;
		alter table nogales_gip.assigned_resources add constraint fk_typology_id foreign key (typology_id) references nogales_gip.typologies(id) ON DELETE CASCADE;
		alter table nogales_gip.assigned_resources add constraint fk_resource_id foreign key (resource_id) references nogales_gip.resources(id) ON DELETE CASCADE;
		
  END IF;
END $$;

