DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
  
  	create table backoffice.vehicle_types (
		id serial not null,
		alias varchar null,
		constraint pk_vehicle_types primary key (id)
	) tablespace tbl_backoffice;
	
  	create table backoffice.vehicle_services (
		id serial not null,
		alias varchar null,
		constraint pk_vehicle_services primary key (id)
	) tablespace tbl_backoffice;
	
  	create table backoffice.vehicle_consecuences (
		id serial not null,
		alias varchar null,
		constraint pk_vehicle_consecuences primary key (id)
	) tablespace tbl_backoffice;
	
	create table backoffice.vehicle_manoeuvres (
		id serial not null,
		alias varchar null,
		constraint pk_vehicle_manoeuvres primary key (id)
	) tablespace tbl_backoffice;

	create table backoffice.vehicle_directions (
		id serial not null,
		alias varchar null,
		constraint pk_vehicle_directions primary key (id)
	) tablespace tbl_backoffice;
	
	create table backoffice.vehicle_hit_and_run (
		id serial not null,
		alias varchar null,
		constraint pk_vehicle_hit_and_run primary key (id)
	) tablespace tbl_backoffice;

	create table backoffice.vehicle_brands (
		id serial not null,
		alias varchar null,
		constraint pk_vehicle_brands primary key (id)
	) tablespace tbl_backoffice;

	create table backoffice.vehicle_lanes (
		id serial not null,
		alias varchar null,
		constraint pk_vehicle_lanes primary key (id)
	) tablespace tbl_backoffice;

	create table backoffice.vehicle_traffic_lanes (
		id serial not null,
		alias varchar null,
		constraint pk_vehicle_traffic_lanes primary key (id)
	) tablespace tbl_backoffice;
  
	create table backoffice.vehicles (			
		id serial not null,
		incident_id int null,
		vehicle_type_id int null,
		vehicle_service_id int null,
		vehicle_consecuence_id int null,
		vehicle_manoeuvre_id int null,
		vehicle_lanes_id int null,
		vehicle_traffic_lanes_id int null,
		patent varchar null,
		vehicle_directions_id int null,
		vehicle_hit_and_run_id int null,
		vehicle_brand_id int null,
  		constraint pk_vehicles primary key (id)
	) tablespace tbl_backoffice;
				
	create index idx_vehicles_incident_id on
	backoffice.vehicles
		using btree (incident_id);	
		
	create index idx_vehicle_type_id on
	backoffice.vehicles
		using btree (vehicle_type_id);	
		
	create index idx_vehicle_service_id on
	backoffice.vehicles
		using btree (vehicle_service_id);	
		
	create index idx_vehicle_consecuence_id on
	backoffice.vehicles
		using btree (vehicle_consecuence_id);	
		
	create index idx_vehicle_manoeuvre_id on
	backoffice.vehicles
		using btree (vehicle_manoeuvre_id);	

	create index idx_vehicle_hit_and_run on
	backoffice.vehicles
		using btree (vehicle_hit_and_run_id);

	create index idx_vehicle_directions_id on
	backoffice.vehicles
		using btree (vehicle_directions_id);

	create index vehicle_lanes_id on
	backoffice.vehicles
		using btree (vehicle_lanes_id);

	create index idx_vehicle_traffic_lanes_id on
	backoffice.vehicles
		using btree (vehicle_traffic_lanes_id);

	create index idx_vehicle_brand_id on
	backoffice.vehicles
		using btree (vehicle_brand_id);	
		
	alter table backoffice.vehicles add constraint fk_vehicle_incident_id foreign key (incident_id) references backoffice.incidents(id) ON DELETE CASCADE;
	alter table backoffice.vehicles add constraint fk_vehicle_type_id foreign key (vehicle_type_id) references backoffice.vehicle_types(id) ON DELETE CASCADE;
	alter table backoffice.vehicles add constraint fk_vehicle_service_id foreign key (vehicle_service_id) references backoffice.vehicle_services(id) ON DELETE CASCADE;
	alter table backoffice.vehicles add constraint fk_vehicle_consecuence_id foreign key (vehicle_consecuence_id) references backoffice.vehicle_consecuences(id) ON DELETE CASCADE;
	alter table backoffice.vehicles add constraint fk_vehicle_manoeuvre_id foreign key (vehicle_manoeuvre_id) references backoffice.vehicle_manoeuvres(id) ON DELETE CASCADE;
	alter table backoffice.vehicles add constraint fk_vehicle_directions_id foreign key (vehicle_directions_id) references backoffice.vehicle_directions(id) ON DELETE CASCADE;
	alter table backoffice.vehicles add constraint fk_vehicle_brand_id foreign key (vehicle_brand_id) references backoffice.vehicle_brands(id) ON DELETE CASCADE;
	alter table backoffice.vehicles add constraint fk_vehicle_lanes foreign key (vehicle_lanes_id) references backoffice.vehicle_lanes(id) ON DELETE CASCADE;
	alter table backoffice.vehicles add constraint fk_vehicle_traffic_lanes foreign key (vehicle_traffic_lanes_id) references backoffice.vehicle_traffic_lanes(id) ON DELETE CASCADE;
	alter table backoffice.vehicles add constraint fk_vehicle_hit_and_run foreign key (vehicle_hit_and_run_id) references backoffice.vehicle_hit_and_run(id) ON DELETE CASCADE;
  END IF;
END $$;
