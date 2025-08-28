DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
  
  	create table backoffice.damages_evaluations (			
		id serial not null,
		incident_id int null,
		description varchar null,
		quantity int null,
		entity varchar null,
		
  		constraint pk_damages_evaluations primary key (id)
	) tablespace tbl_backoffice;
				
	create index idx_damage_evaluation_incident_id on
	backoffice.damages_evaluations
		using btree (incident_id);	
		
	alter table backoffice.damages_evaluations add constraint fk_damage_evaluation_incident_id foreign key (incident_id) references backoffice.incidents(id) ON DELETE CASCADE;
  END IF;
END $$;