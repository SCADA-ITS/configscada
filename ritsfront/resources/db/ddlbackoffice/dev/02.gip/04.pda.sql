DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_gip') THEN


		create table gip.pdas (
			id serial not null,
			alias varchar null,
			constraint pk_pdas primary key (id)
		) tablespace tbl_gip;
		
		create table gip.assigned_pdas (
			id serial not null,
			incident_id int null,
			pda_id int null,
			constraint pk_assigned_pdas primary key (id)
		) tablespace tbl_gip;
		
		
		create index idx_pdas_incident_id on
		gip.assigned_pdas
			using btree (incident_id);
		
		create index idx_pdas_id on
		gip.assigned_pdas
			using btree (pda_id);	
		
		alter table gip.assigned_pdas add constraint fk_pdas_incident_id foreign key (incident_id) references gip.incidents(id) ON DELETE CASCADE;
		alter table gip.assigned_pdas add constraint fk_pda_id foreign key (pda_id) references gip.pdas(id) ON DELETE CASCADE;


  END IF;
END $$;

