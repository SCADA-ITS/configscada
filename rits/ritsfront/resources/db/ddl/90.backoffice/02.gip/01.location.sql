DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_gip') THEN

		create table gip.zones (
			id serial not null,
			alias varchar null,
			constraint pk_zones primary key (id)
		) tablespace tbl_gip;
		
		create table gip.sections (
			id serial not null,
			alias varchar null,
			constraint pk_sections primary key (id)
		) tablespace tbl_gip;
		
		create table gip.cantones (
			id serial not null,
			alias varchar null,
			constraint pk_cantones primary key (id)
		) tablespace tbl_gip;
		
		create table gip.sectors (
			id serial not null,
			alias varchar null,
			constraint pk_sectors primary key (id)
		) tablespace tbl_gip;
		
		create table gip.loc_leveles (
			id serial not null,
			alias varchar null,
			constraint pk_loc_levels primary key (id)
		) tablespace tbl_gip;
		
		create table gip.type_stretchs (
			id serial not null,
			alias varchar null,
			constraint pk_type_stretch primary key (id)
		) tablespace tbl_gip;
		
		create table gip.roads (
			id serial not null,
			alias varchar null,
			constraint pk_roads primary key (id)
		) tablespace tbl_gip;
		
		create table gip.axis (
			id serial not null,
			alias varchar null,
			constraint pk_axis primary key (id)
		) tablespace tbl_gip;
		
		create table gip.ways (
			id serial not null,
			alias varchar null,
			constraint pk_ways primary key (id)
		) tablespace tbl_gip;
		
		
		create table gip.locations (
			id serial not null,
			alias varchar null,
			description varchar null,
			zone_id int null,
			section_id int null,
			canton_id int null,
			sector_id int null,
			loc_level_id int null,
			type_stretch_id int null,
			road_id int null,
			axi_id int null,
			way_id int null,
			dm float null,
			dm_ini float null,
			dm_end float null,
			km  float null,
			km_ini float,
			km_end float,
			constraint pk_locations primary key (id)
		) tablespace tbl_gip;
		
		create index idx_zone_id on
		gip.locations
			using btree (zone_id);
		
		create index idx_section_id on
		gip.locations
			using btree (section_id);
		
		create index idx_canton_id on
		gip.locations
			using btree (canton_id);
		
		create index idx_sector_id on
		gip.locations
			using btree (sector_id);
		
		create index idx_loc_level_id on
		gip.locations
			using btree (loc_level_id);
		
		create index idx_strech_id on
		gip.locations
			using btree (type_stretch_id);
		
		create index idx_road_id on
		gip.locations
			using btree (road_id);
		
		create index idx_axi_id on
		gip.locations
			using btree (axi_id);
		
		create index idx_way_id on
		gip.locations
			using btree (way_id);
		
		
		alter table gip.locations add constraint fk_zone_id foreign key (zone_id) references gip.zones(id) ON DELETE CASCADE;
		alter table gip.locations add constraint fk_section_id foreign key (section_id) references gip.sections(id) ON DELETE CASCADE;
		alter table gip.locations add constraint fk_canton_id foreign key (canton_id) references gip.cantones(id) ON DELETE CASCADE;
		alter table gip.locations add constraint fk_sector_id foreign key (sector_id) references gip.sectors(id) ON DELETE CASCADE;
		alter table gip.locations add constraint fk_loc_level_id foreign key (loc_level_id) references gip.loc_leveles(id) ON DELETE CASCADE;
		alter table gip.locations add constraint fk_type_stretch_id foreign key (type_stretch_id) references gip.type_stretchs(id) ON DELETE CASCADE;
		alter table gip.locations add constraint fk_road_id foreign key (road_id) references gip.roads(id) ON DELETE CASCADE;
		alter table gip.locations add constraint fk_axi_id foreign key (axi_id) references gip.axis(id) ON DELETE CASCADE;
		alter table gip.locations add constraint fk_way_id foreign key (way_id) references gip.ways(id) ON DELETE CASCADE;

  END IF;
END $$;