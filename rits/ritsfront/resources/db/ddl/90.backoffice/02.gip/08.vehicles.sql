DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_gip') THEN
		
		
		create table gip.vehicle_types (
			id serial not null,
			alias varchar null,
			constraint pk_vehicle_types primary key (id)
		) tablespace tbl_gip;
		
		create table gip.vehicle_brands (
			id serial not null,
			alias varchar null,
			constraint pk_vehicle_brands primary key (id)
		) tablespace tbl_gip;
		
		create table gip.vehicle_models (
			id serial not null,
			alias varchar null,
			constraint pk_vehicle_models primary key (id)
		) tablespace tbl_gip;
		
		create table gip.vehicle_damages (
			id serial not null,
			alias varchar null,
			constraint pk_vehicle_damages primary key (id)
		) tablespace tbl_gip;
		
		create table gip.attendeds (
			id serial not null,
			alias varchar null,
			constraint pk_attended primary key (id)
		) tablespace tbl_gip;
		
		create table gip.assistance_types (
			id serial not null,
			alias varchar null,
			constraint pk_assistance_types primary key (id)
		) tablespace tbl_gip;
		
		create table gip.transfer_places (
			id serial not null,
			alias varchar null,
			constraint pk_transfer_places primary key (id)
		) tablespace tbl_gip;
		
		create table gip.vehicles (
			id serial not null,
			incident_id int null,
			comment varchar null,
			vehicle_type_id int null,
			vehicle_brand_id int null,
			vehicle_model_id int null,
			patent varchar null,
			color varchar null,
			vehicle_damage_id int null,
			attended_id int null,
			assistance_type_id int null,
			transfer_place_id int null,
			constraint pk_vehicles primary key (id)
		) tablespace tbl_gip;
		
		create index idx_vehicles_incident_id on
		gip.vehicles
			using btree (incident_id);	
		
		create index idx_vehicle_type_id on
		gip.vehicles
			using btree (vehicle_type_id);	
		
		create index idx_vehicle_brand_id on 
		gip.vehicles
			using btree (vehicle_brand_id);
		
		create index idx_vehicle_model_id on 
		gip.vehicles
			using btree (vehicle_model_id);
		
		create index idx_vehicle_damage_id on 
		gip.vehicles
			using btree (vehicle_damage_id);
		
		create index idx_attended_id on 
		gip.vehicles
			using btree (attended_id);
		
		create index idx_assistance_type_id on 
		gip.vehicles
			using btree (assistance_type_id);
		
		create index idx_transfer_place_id on 
		gip.vehicles
			using btree (transfer_place_id);
		
		alter table gip.vehicles add constraint fk_vehicle_incident_id foreign key (incident_id) references gip.incidents(id) ON DELETE CASCADE;
		alter table gip.vehicles add constraint fk_vehicle_type_id foreign key (vehicle_type_id) references gip.vehicle_types(id) ON DELETE CASCADE;
		alter table gip.vehicles add constraint fk_vehicle_brand_id foreign key (vehicle_brand_id) references gip.vehicle_brands(id) ON DELETE CASCADE;
		alter table gip.vehicles add constraint fk_vehicle_model_id foreign key (vehicle_model_id) references gip.vehicle_models(id) ON DELETE CASCADE;
		alter table gip.vehicles add constraint fk_vehicle_damage_id foreign key (vehicle_damage_id) references gip.vehicle_damages(id) ON DELETE CASCADE;
		alter table gip.vehicles add constraint fk_attended_id foreign key (attended_id) references gip.attendeds(id) ON DELETE CASCADE;
		alter table gip.vehicles add constraint fk_assistance_type_id foreign key (assistance_type_id) references gip.assistance_types(id) ON DELETE CASCADE;
		alter table gip.vehicles add constraint fk_transfer_place_id foreign key (transfer_place_id) references gip.transfer_places(id) ON DELETE CASCADE;
		
  END IF;
END $$;

