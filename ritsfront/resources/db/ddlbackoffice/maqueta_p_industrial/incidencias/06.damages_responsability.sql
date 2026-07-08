DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
  
  	create table backoffice.owner_types (
		id serial not null,
		alias varchar null,
		constraint pk_owner_types primary key (id)
	) tablespace tbl_backoffice;
  
  	create table backoffice.damages_responsabilities (			
		id serial not null,
		incident_id int null,
		address varchar null,
		phone_number varchar null,
		vehicle_owner_id int null,
		owner_name varchar null,
		business_activity varchar null,
		rut varchar null,
		description varchar null,
		quantity int null,
		entity varchar null,
		
  		constraint pk_damages_responsabilities primary key (id)
	) tablespace tbl_backoffice;
				
	create index idx_damage_responsability_incident_id on
	backoffice.damages_responsabilities
		using btree (incident_id);	
				
	create index idx_vehicle_owner_id on
	backoffice.damages_responsabilities
		using btree (vehicle_owner_id);	
		
	alter table backoffice.damages_responsabilities add constraint fk_damage_responsability_incident_id foreign key (incident_id) references backoffice.incidents(id) ON DELETE CASCADE;
	alter table backoffice.damages_responsabilities add constraint fk_vehicle_owner_id_id foreign key (vehicle_owner_id) references backoffice.owner_types(id) ON DELETE CASCADE;
  END IF;
END $$;