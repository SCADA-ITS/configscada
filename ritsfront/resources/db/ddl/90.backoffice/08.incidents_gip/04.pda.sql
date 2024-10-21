DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_incidents_gip') THEN


		create table incidents_gip.pdas (
			id serial not null,
			alias varchar null,
			constraint pk_pdas primary key (id)
		) tablespace tbl_incidents_gip;
		
		create table incidents_gip.assigned_pdas (
			id serial not null,
			incident_id int null,
			pda_id int null,
			constraint pk_assigned_pdas primary key (id)
		) tablespace tbl_incidents_gip;
		
		
		create index idx_pdas_incident_id on
		incidents_gip.assigned_pdas
			using btree (incident_id);
		
		create index idx_pdas_id on
		incidents_gip.assigned_pdas
			using btree (pda_id);	
		
		alter table incidents_gip.assigned_pdas add constraint fk_pdas_incident_id foreign key (incident_id) references incidents_gip.incidents(id) ON DELETE CASCADE;
		alter table incidents_gip.assigned_pdas add constraint fk_pda_id foreign key (pda_id) references incidents_gip.pdas(id) ON DELETE CASCADE;


  END IF;
END $$;

