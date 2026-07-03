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
		
		create table backoffice.road_conditions (
			id serial not null,
			alias varchar null,
			constraint pk_road_conditions primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.road_types (
			id serial not null,
			alias varchar null,
			constraint pk_road_types primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.road_states (
			id serial not null,
			alias varchar null,
			constraint pk_road_states primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.weather_states (
			id serial not null,
			alias varchar null,
			constraint pk_weather_states  primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.luminosity (
			id serial not null,
			alias varchar null,
			constraint pk_luminosity  primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.ligth (
			id serial not null,
			alias varchar null,
			constraint pk_ligth  primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.causes (
			id serial not null,
			alias varchar null,
			constraint pk_causes  primary key (id)
		) tablespace tbl_backoffice;
  
		create table backoffice.incidents (
			id serial not null,
			incident_report_id  varchar null,
			km varchar null,
			date timestamptz,
			detection_time timestamptz,
			event_id int null,
			side_id int null,
			stretch_id int null,
			comments varchar null,
			
			event_type_id int null,
			collision_id int null,
			object_crash_id int null,
			vehicle_crash_id int null,
			relative_location_id int null,
			road_condition_id int null,
			road_type_id int null,
			road_state_id int null,
			weather_state_id int null,
			luminosity_id int null,
			ligth_id int null,
			cause_id int null,
			detection_id int null,
			direction_id int null,
			notice_id int null,

			report_by varchar null,
			stamped varchar null,
			court varchar null,
			citattion_date timestamptz null,
			report_number varchar null,
			station varchar null,
			file_number varchar null,
			patrol_name varchar null,
			operator_name varchar null,
			supervisor_name varchar null,
			operator varchar null,


			
			constraint pk_incidents primary key (id)
		) tablespace tbl_backoffice;

		create table backoffice.detection (
			id serial not null,
			alias varchar null,
			constraint pk_detection primary key (id)
		) tablespace tbl_backoffice;
		
		create table backoffice.direction (
			id serial not null,
			alias varchar null,
			constraint pk_direction  primary key (id)
		) tablespace tbl_backoffice;

		create table backoffice.notice (
			id serial not null,
			alias varchar null,
			constraint pk_notice primary key (id)
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
		
		create index idx_road_condition_id on
		backoffice.incidents
			using btree (road_condition_id);
		
		create index idx_road_type_id on
		backoffice.incidents
			using btree (road_type_id);
		
		create index idx_road_state_id on
		backoffice.incidents
			using btree (road_state_id);
		
		create index idx_weather_state_id on
		backoffice.incidents
			using btree (weather_state_id);
		
		create index idx_luminosity_id on
		backoffice.incidents
			using btree (luminosity_id);
		
		create index idx_ligth_id on
		backoffice.incidents
			using btree (ligth_id);
		
		create index idx_cause_id on
		backoffice.incidents
			using btree (cause_id);

		create index idx_detection_id on
		backoffice.incidents
			using btree (detection_id);

		create index idx_direction_id on
		backoffice.incidents
			using btree (direction_id);


		create index idx_notice_id on
		backoffice.incidents
			using btree (notice_id);

		alter table backoffice.incidents add constraint fk_type_event_id foreign key (event_id) references backoffice.event(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_type_side_id foreign key (side_id) references backoffice.side(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_type_stretch_id foreign key (stretch_id) references backoffice.stretch(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_type_event_type_id foreign key (event_type_id) references backoffice.event_type(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_collision_id foreign key (collision_id) references backoffice.collision(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_object_crash_id foreign key (object_crash_id) references backoffice.object_crash(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_vehicle_crash_id foreign key (vehicle_crash_id) references backoffice.vehicle_crash(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_relative_location_id foreign key (relative_location_id) references backoffice.relative_location(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_road_condition_id foreign key (road_condition_id) references backoffice.road_conditions(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_road_type_id foreign key (road_type_id) references backoffice.road_types(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_road_state_id foreign key (road_state_id) references backoffice.road_states(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_weather_state_id foreign key (weather_state_id) references backoffice.weather_states(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_luminosity_id foreign key (luminosity_id) references backoffice.luminosity(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_ligth_id foreign key (ligth_id) references backoffice.ligth(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_cause_id foreign key (cause_id) references backoffice.causes(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_type_detection_id foreign key (detection_id) references backoffice.detection(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_type_direction_id foreign key (direction_id) references backoffice.direction(id) ON DELETE RESTRICT;
		alter table backoffice.incidents add constraint fk_notice_id foreign key (notice_id) references backoffice.notice(id) ON DELETE RESTRICT;

	END IF;
END $$;



