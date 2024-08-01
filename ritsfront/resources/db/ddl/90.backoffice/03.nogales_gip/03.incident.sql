DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_nogales_gip') THEN

		create table nogales_gip.sources (
			id serial not null,
			alias varchar null,
			constraint pk_sources primary key (id)
		) tablespace tbl_nogales_gip;
		
		create table nogales_gip.elements (
			id serial not null,
			alias varchar null,
			constraint pk_elements primary key (id)
		) tablespace tbl_nogales_gip;
		
		create table nogales_gip.incidents (
			id serial not null,
			incident_report_id  varchar null,
			type_detection varchar null,
			source_id int null,
			element_id int null,
			incident_type varchar null,
			incident_sub_type varchar null,
			level varchar null,
			km_start varchar null,
			km_end varchar null,
			user_n varchar null,
			phone  varchar null,
			
			comment  varchar null,
			created_at timestamptz default current_timestamp,
			constraint pk_incidents primary key (id)
		) tablespace tbl_nogales_gip;
		
		create index idx_type_detection on
		nogales_gip.incidents
			using btree (type_detection);
			
		create index idx_source_id on
		nogales_gip.incidents
			using btree (source_id);
		
		create index idx_element_id on
		nogales_gip.incidents
			using btree (element_id);
			
		create index idx_incident_type on
		nogales_gip.incidents
			using btree (incident_type);	
		
		create index idx_incident_sub_type on
		nogales_gip.incidents
			using btree (incident_sub_type);
			
		alter table nogales_gip.incidents add constraint fk_type_source_id foreign key (source_id) references nogales_gip.sources(id) ON DELETE CASCADE;
		alter table nogales_gip.incidents add constraint fk_type_element_id foreign key (element_id) references nogales_gip.elements(id) ON DELETE CASCADE;
  END IF;
END $$;



