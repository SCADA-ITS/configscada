DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
  
    create table backoffice.closure_types (
		id serial not null,
		alias varchar null,
		constraint pk_closure_types primary key (id)
	) tablespace tbl_backoffice;

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

    create table backoffice.clousure_entities (
		id serial not null,
		alias varchar null,
		constraint pk_clousure_entities primary key (id)
	) tablespace tbl_backoffice;

  	create table backoffice.closures (			
		id serial not null,
		incident_id int null,
		side_roads_id int null,
		lanes_id int null,
		clousure_entities_id int null,
		closure_types_id int null,
		start_hour timestamptz null,
		end_hour timestamptz null,
		total_time varchar null,
		start_km float8 null,
		end_km float8 null,
		comments varchar null,

  		constraint pk_closures primary key (id)
	) tablespace tbl_backoffice;
				
	create index idx_closure_incident_id on
	backoffice.closures
		using btree (incident_id);	

	create index idx_side_roads on
	backoffice.closures
		using btree (side_roads_id);

	create index idx_lanes on
	backoffice.closures
		using btree (lanes_id);

	create index idx_clousure_entities on
	backoffice.closures
		using btree (clousure_entities_id);

	create index idx_closure_types on
	backoffice.closures
		using btree (closure_types_id);
		
	alter table backoffice.closures add constraint fk_closure_incident_id foreign key (incident_id) references backoffice.incidents(id) ON DELETE CASCADE;
	alter table backoffice.closures add constraint fk_side_roads foreign key (side_roads_id) references backoffice.side_roads(id) ON DELETE CASCADE;
	alter table backoffice.closures add constraint fk_lanes foreign key (lanes_id) references backoffice.lanes(id) ON DELETE CASCADE;
	alter table backoffice.closures add constraint fk_clousure_entities foreign key (clousure_entities_id) references backoffice.clousure_entities(id) ON DELETE CASCADE;
	alter table backoffice.closures add constraint fk_closure_types foreign key (closure_types_id) references backoffice.closure_types(id) ON DELETE CASCADE;

  END IF;
END $$;
