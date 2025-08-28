DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_gip') THEN

		
		create table gip.agents (
			id serial not null,
			alias varchar null,
			constraint pk_agents primary key (id)
		) tablespace tbl_gip;
		
		create table gip.interrupt_agents (
			id serial not null,
			incident_id int null,
			comment varchar null,
			agents_id int null,
			start_interrupt timestamptz default current_timestamp,
			end_interrupt timestamptz null,
			constraint pk_interrupt_agents primary key (id)
		) tablespace tbl_gip;
		
		create index idx_interrupt_agents_incident_id on
		gip.interrupt_agents
			using btree (incident_id);	
		
		create index idx_agents_id on
		gip.interrupt_agents
			using btree (agents_id);
		
		
		alter table gip.interrupt_agents add constraint fk_interrupt_agents_incident_id foreign key (incident_id) references gip.incidents(id) ON DELETE CASCADE;
		alter table gip.interrupt_agents add constraint fk_agents_id foreign key (agents_id) references gip.agents(id) ON DELETE CASCADE;

  END IF;
END $$;

