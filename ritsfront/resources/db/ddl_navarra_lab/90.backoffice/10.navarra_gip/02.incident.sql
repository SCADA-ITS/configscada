DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_incidents') THEN
  
  		create table incidents.municipalities (
			id serial not null,
			alias varchar null,
			constraint pk_municipalities primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create table incidents.requesting_entities (
			id serial not null,
			alias varchar null,
			constraint pk_requesting_entities primary key (id)
		) tablespace tbs_controltrafico_incidents;
  
		create table incidents.incidents (
			id serial not null,
			incident_report_id  varchar null,
			incident_type varchar null,
			incident_sub_type varchar null,
			level varchar null,
			location varchar null,
			municipalitie_id int null,
			requesting_entity_id int null,
			comment  varchar null,
			created_at timestamptz default current_timestamp,
			constraint pk_incidents primary key (id)
		) tablespace tbs_controltrafico_incidents;	
			
		create index idx_municipalitie_id on
		incidents.incidents
			using btree (municipalitie_id);	
		
		create index idx_requesting_entity_id on
		incidents.incidents
			using btree (requesting_entity_id);
			
		alter table incidents.incidents add constraint fk_municipalitie_id foreign key (municipalitie_id) references incidents.municipalities(id) ON DELETE CASCADE;
		alter table incidents.incidents add constraint fk_requesting_entity_id foreign key (requesting_entity_id) references incidents.requesting_entities(id) ON DELETE CASCADE;
  END IF;
END $$;



