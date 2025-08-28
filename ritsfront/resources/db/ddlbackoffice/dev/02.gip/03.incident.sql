DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_gip') THEN


		create table gip.type_detections (
			id serial not null,
			alias varchar null,
			constraint pk_type_detections primary key (id)
		) tablespace tbl_gip;
		
		create table gip.sources (
			id serial not null,
			alias varchar null,
			constraint pk_sources primary key (id)
		) tablespace tbl_gip;
		
		create table gip.states (
			id serial not null,
			alias varchar null,
			constraint pk_states primary key (id)
		) tablespace tbl_gip;
		
		create table gip.types (
			id serial not null,
			alias varchar null,
			constraint pk_types primary key (id)
		) tablespace tbl_gip;
		
		create table gip.sub_types (
			id serial not null,
			alias varchar null,
			constraint pk_sub_types primary key (id)
		) tablespace tbl_gip;
		
		create table gip.levels (
			id serial not null,
			alias varchar null,
			constraint pk_levels primary key (id)
		) tablespace tbl_gip;
		
		create table gip.incidents (
			id serial not null,
			incident_report_id  varchar null,
			type_detection_id int null,
			location_id int null,
			source_id int null,
			state_id int null,
			type_id int null,
			sub_type_id int null,
			level_id int null,
			created_at timestamptz default current_timestamp,
			user_n varchar null,
			phone  varchar null,
			road_condition_id int null,
			constraint pk_incidents primary key (id)
		) tablespace tbl_gip;
		
		create index idx_type_detection_id on
		gip.incidents
			using btree (type_detection_id);
		
		create index idx_location_id on
		gip.incidents
			using btree (location_id);
		
		create index idx_source_id on
		gip.incidents
			using btree (source_id);
		
		create index idx_state_id on
		gip.incidents
			using btree (state_id);	
		
		create index idx_type_id on
		gip.incidents
			using btree (type_id);	
		
		create index idx_sub_type_id on
		gip.incidents
			using btree (sub_type_id);
		
		create index idx_level_id on
		gip.incidents
			using btree (level_id);
		
		create index idx_road_condition_id on
		gip.incidents
			using btree (road_condition_id);
		
		alter table gip.incidents add constraint fk_type_detection_id foreign key (type_detection_id) references gip.type_detections(id) ON DELETE CASCADE;
		alter table gip.incidents add constraint fk_location_id foreign key (location_id) references gip.locations(id) ON DELETE CASCADE;
		alter table gip.incidents add constraint fk_type_source_id foreign key (source_id) references gip.sources(id) ON DELETE CASCADE;
		alter table gip.incidents add constraint fk_type_state_id foreign key (state_id) references gip.states(id) ON DELETE CASCADE;
		alter table gip.incidents add constraint fk_type_id foreign key (type_id) references gip.types(id) ON DELETE CASCADE;
		alter table gip.incidents add constraint fk_sub_type_id foreign key (sub_type_id) references gip.sub_types(id) ON DELETE CASCADE;
		alter table gip.incidents add constraint fk_level_id foreign key (level_id) references gip.levels(id) ON DELETE CASCADE;
		alter table gip.incidents add constraint fk_road_condition_id foreign key (road_condition_id) references gip.road_conditions(id) ON DELETE CASCADE;
		
  END IF;
END $$;



