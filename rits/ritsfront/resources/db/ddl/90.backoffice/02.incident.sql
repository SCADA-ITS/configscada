DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
  
		create table backoffice.event (
			id serial not null,
			alias varchar null,
			constraint pk_event primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.side (
			id serial not null,
			alias varchar null,
			constraint pk_side primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.stretch (
			id serial not null,
			alias varchar null,
			constraint pk_stretch primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.event_type (
			id serial not null,
			alias varchar null,
			constraint pk_event_type primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.collision (
			id serial not null,
			alias varchar null,
			constraint pk_collision primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.object_crash (
			id serial not null,
			alias varchar null,
			constraint pk_object_crash primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.vehicle_crash (
			id serial not null,
			alias varchar null,
			constraint pk_vehicle_crash primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.relative_location (
			id serial not null,
			alias varchar null,
			constraint pk_relative_location primary key (id)
		) tablespace tbl_backoffice;
  
		create table backoffice.incidents (
			id serial not null,
			incident_report_id  varchar null,
			km varchar null,
			date timestamptz,
			event_id int null,
			side_id int null,
			stretch_id int null,
			event_type_id int null,
			collision_id int null,
			object_crash_id int null,
			vehicle_crash_id int null,
			relative_location_id int null,
			
			created_at timestamptz default current_timestamp,
			constraint pk_incidents primary key (id)
		) tablespace tbl_backoffice;
		
		create index idx_event_id on
		backoffice.incidents
			using btree (event_id);
		
		create index idx_side_id on
		backoffice.incidents
			using btree (side_id);
		
		create index idx_stretch_id on
		backoffice.incidents
			using btree (stretch_id);
		
		create index idx_event_type_id on
		backoffice.incidents
			using btree (event_type_id);
		
		create index idx_collision_id on
		backoffice.incidents
			using btree (collision_id);
		
		create index idx_object_crash_id on
		backoffice.incidents
			using btree (object_crash_id);
		
		create index idx_vehicle_crash_id on
		backoffice.incidents
			using btree (vehicle_crash_id);
		
		create index idx_relative_location_id on
		backoffice.incidents
			using btree (relative_location_id);
			
		alter table backoffice.incidents add constraint fk_type_event_id foreign key (event_id) references backoffice.event(id) ON DELETE CASCADE;
		alter table backoffice.incidents add constraint fk_type_side_id foreign key (side_id) references backoffice.side(id) ON DELETE CASCADE;
		alter table backoffice.incidents add constraint fk_type_stretch_id foreign key (stretch_id) references backoffice.stretch(id) ON DELETE CASCADE;
		alter table backoffice.incidents add constraint fk_type_event_type_id foreign key (event_type_id) references backoffice.event_type(id) ON DELETE CASCADE;
		alter table backoffice.incidents add constraint fk_collision_id foreign key (collision_id) references backoffice.collision(id) ON DELETE CASCADE;
		alter table backoffice.incidents add constraint fk_object_crash_id foreign key (object_crash_id) references backoffice.object_crash(id) ON DELETE CASCADE;
		alter table backoffice.incidents add constraint fk_vehicle_crash_id foreign key (vehicle_crash_id) references backoffice.vehicle_crash(id) ON DELETE CASCADE;
		alter table backoffice.incidents add constraint fk_relative_location_id foreign key (relative_location_id) references backoffice.relative_location(id) ON DELETE CASCADE;
	END IF;
END $$;



