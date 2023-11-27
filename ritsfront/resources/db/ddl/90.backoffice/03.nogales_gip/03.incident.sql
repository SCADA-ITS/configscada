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
			location varchar null,
			user_n varchar null,
			phone  varchar null,

			condition_id int null,
			luminosity_id int null,
			atmospheric_state_id int null,
			cause_id int null,
			maneuver_id int null,
			road_impact_id int null,
			infrastructure_damage_id int null,
			
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
		
		create index idx_condition_id on
		nogales_gip.incidents
			using btree (condition_id);
			
		create index idx_luminosity_id on
		nogales_gip.incidents
			using btree (luminosity_id);
			
		create index idx_atmospheric_state_id on
		nogales_gip.incidents
			using btree (atmospheric_state_id);
			
		create index idx_cause_id on
		nogales_gip.incidents
			using btree (cause_id);
			
		create index idx_maneuver_id on
		nogales_gip.incidents
			using btree (maneuver_id);
			
		create index idx_road_impact_id on
		nogales_gip.incidents
			using btree (road_impact_id);
			
		create index idx_infrastructure_damage_id on
		nogales_gip.incidents		
			using btree (infrastructure_damage_id);
		
		alter table nogales_gip.incidents add constraint fk_type_source_id foreign key (source_id) references nogales_gip.sources(id) ON DELETE CASCADE;
		alter table nogales_gip.incidents add constraint fk_type_element_id foreign key (element_id) references nogales_gip.elements(id) ON DELETE CASCADE;

		alter table nogales_gip.incidents add constraint fk_condition_id foreign key (condition_id) references nogales_gip.conditions(id) ON DELETE CASCADE;
		alter table nogales_gip.incidents add constraint fk_luminosity_id foreign key (luminosity_id) references nogales_gip.luminosities(id) ON DELETE CASCADE;
		alter table nogales_gip.incidents add constraint fk_atmospheric_state_id foreign key (atmospheric_state_id) references nogales_gip.atmospheric_states(id) ON DELETE CASCADE;
		alter table nogales_gip.incidents add constraint fk_cause_id foreign key (cause_id) references nogales_gip.causes(id) ON DELETE CASCADE;
		alter table nogales_gip.incidents add constraint fk_maneuver_id foreign key (maneuver_id) references nogales_gip.maneuvers(id) ON DELETE CASCADE;
		alter table nogales_gip.incidents add constraint fk_road_impact_id foreign key (road_impact_id) references nogales_gip.road_impacts(id) ON DELETE CASCADE;
		alter table nogales_gip.incidents add constraint fk_infrastructure_damage_id foreign key (infrastructure_damage_id) references nogales_gip.infrastructure_damages(id) ON DELETE CASCADE;

  END IF;
END $$;



