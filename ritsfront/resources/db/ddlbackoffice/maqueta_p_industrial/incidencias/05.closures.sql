DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
  
  	create table backoffice.closures (			
		id serial not null,
		incident_id int null,
		side_road varchar null,
		lanes varchar null,
		entity varchar null,
		reasons varchar null,
		start_hour timestamptz null,
		end_hour timestamptz null,
		total_time varchar null,
		
  		constraint pk_closures primary key (id)
	) tablespace tbl_backoffice;
				
	create index idx_closure_incident_id on
	backoffice.closures
		using btree (incident_id);	
		
	alter table backoffice.closures add constraint fk_closure_incident_id foreign key (incident_id) references backoffice.incidents(id) ON DELETE CASCADE;
  END IF;
END $$;