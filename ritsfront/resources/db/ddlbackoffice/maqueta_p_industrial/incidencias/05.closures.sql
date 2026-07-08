DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
  
  	create table backoffice.side_roads (
		id serial not null,
		alias varchar null,
		constraint pk_side_roads primary key (id)
	) tablespace tbl_backoffice;
	
  	create table backoffice.lanes (
		id serial not null,
		alias varchar null,
		constraint pk_lanes primary key (id)
	) tablespace tbl_backoffice;
	
  	create table backoffice.entities (
		id serial not null,
		alias varchar null,
		constraint pk_entities primary key (id)
	) tablespace tbl_backoffice;
	
  	create table backoffice.closure_types (
		id serial not null,
		alias varchar null,
		constraint pk_closure_types primary key (id)
	) tablespace tbl_backoffice;
  
  	create table backoffice.closures (			
		id serial not null,
		incident_id int null,
		side_road_id int null,
		lanes_id int null,
		entity_id int null,
		reasons varchar null,
		closure_type_id int null,
		wording varchar null,
		start_hour timestamptz null,
		end_hour timestamptz null,
		total_time varchar null,
		
  		constraint pk_closures primary key (id)
	) tablespace tbl_backoffice;
				
	create index idx_closure_incident_id on
	backoffice.closures
		using btree (incident_id);	
				
	create index idx_closure_side_road_id on
	backoffice.closures
		using btree (side_road_id);	
				
	create index idx_closure_lanes_id on
	backoffice.closures
		using btree (lanes_id);
				
	create index idx_closure_entity_id on
	backoffice.closures
		using btree (entity_id);
				
	create index idx_closure_type_id on
	backoffice.closures
		using btree (closure_type_id);
		
	alter table backoffice.closures add constraint fk_closure_incident_id foreign key (incident_id) references backoffice.incidents(id) ON DELETE CASCADE;
	alter table backoffice.closures add constraint fk_closure_side_road_id foreign key (side_road_id) references backoffice.side_roads(id) ON DELETE CASCADE;
	alter table backoffice.closures add constraint fk_closure_lanes_id foreign key (lanes_id) references backoffice.lanes(id) ON DELETE CASCADE;
	alter table backoffice.closures add constraint fk_closure_entity_id foreign key (entity_id) references backoffice.entities(id) ON DELETE CASCADE;
	alter table backoffice.closures add constraint fk_closure_type_id foreign key (closure_type_id) references backoffice.closure_types(id) ON DELETE CASCADE;
  END IF;
END $$;