DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_incidents') THEN
		
		
		create table incidents.vehicle_types (
			id serial not null,
			alias varchar null,
			constraint pk_vehicle_types primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create table incidents.vehicle_brands (
			id serial not null,
			alias varchar null,
			constraint pk_vehicle_brands primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create table incidents.vehicle_models (
			id serial not null,
			vehicle_brand_id int null,
			alias varchar null,
			constraint pk_vehicle_models primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		alter table incidents.vehicle_models add constraint fk_vehicle_brand_id foreign key (vehicle_brand_id) references incidents.vehicle_brands(id) ON DELETE CASCADE;
		
		create index idx_vehicle_brand_models_id on 
		incidents.vehicle_models
			using btree (vehicle_brand_id);
			
		create table incidents.vehicle_damages (
			id serial not null,
			alias varchar null,
			constraint pk_vehicle_damages primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create table incidents.vehicles (
			id serial not null,
			incident_id int null,
			vehicle_type_id int null,
			vehicle_brand_id int null,
			vehicle_model_id int null,
			patent varchar null,
			color varchar null,
			vehicle_damage_id int null,
			comment varchar null,
			constraint pk_vehicles primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create index idx_vehicles_incident_id on
		incidents.vehicles
			using btree (incident_id);	
		
		create index idx_vehicle_type_id on
		incidents.vehicles
			using btree (vehicle_type_id);	
		
		create index idx_vehicle_brand_id on 
		incidents.vehicles
			using btree (vehicle_brand_id);
		
		create index idx_vehicle_model_id on 
		incidents.vehicles
			using btree (vehicle_model_id);
		
		create index idx_vehicle_damage_id on 
		incidents.vehicles
			using btree (vehicle_damage_id);
		
		alter table incidents.vehicles add constraint fk_vehicle_incident_id foreign key (incident_id) references incidents.incidents(id) ON DELETE CASCADE;
		alter table incidents.vehicles add constraint fk_vehicle_type_id foreign key (vehicle_type_id) references incidents.vehicle_types(id) ON DELETE CASCADE;
		alter table incidents.vehicles add constraint fk_vehicle_brand_id foreign key (vehicle_brand_id) references incidents.vehicle_brands(id) ON DELETE CASCADE;
		alter table incidents.vehicles add constraint fk_vehicle_model_id foreign key (vehicle_model_id) references incidents.vehicle_models(id) ON DELETE CASCADE;
		alter table incidents.vehicles add constraint fk_vehicle_damage_id foreign key (vehicle_damage_id) references incidents.vehicle_damages(id) ON DELETE CASCADE;
		
  END IF;
END $$;

