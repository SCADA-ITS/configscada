DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
  
  	create table backoffice.support_service_types (
		id serial not null,
		alias varchar null,
		constraint pk_support_service_types primary key (id)
	) tablespace tbl_backoffice;
	
  	create table backoffice.support_service_subtypes (
		id serial not null,
		support_service_type_id int null,
		alias varchar null,
		constraint pk_support_service_subtypes primary key (id)
	) tablespace tbl_backoffice;
	
	alter table backoffice.support_service_subtypes add constraint fk_support_service_type_id foreign key (support_service_type_id) references backoffice.support_service_types(id) ON DELETE CASCADE;
  
	create table backoffice.support_services (			
		id serial not null,
		incident_id int null,
		type_id int null,
		subtype_id int null,
		arrival_time timestamptz null,
		comment varchar null,
		quantity int null,
		departure_time timestamptz null,
		
  		constraint pk_support_services primary key (id)
	) tablespace tbl_backoffice;
				
	create index idx_support_services_incident_id on
	backoffice.support_services
		using btree (incident_id);	
				
	create index idx_type_id on
	backoffice.support_services
		using btree (type_id);	
				
	create index idx_subtype_id on
	backoffice.support_services
		using btree (subtype_id);
		
	alter table backoffice.support_services add constraint fk_support_service_incident_id foreign key (incident_id) references backoffice.incidents(id) ON DELETE CASCADE;
	alter table backoffice.support_services add constraint fk_support_service_type_id foreign key (type_id) references backoffice.support_service_types(id) ON DELETE CASCADE;
	alter table backoffice.support_services add constraint fk_support_service_subtype_id foreign key (subtype_id) references backoffice.support_service_subtypes(id) ON DELETE CASCADE;
  END IF;
END $$;