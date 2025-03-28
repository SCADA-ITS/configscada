DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbs_controltrafico_incidents') THEN
		
		create table incidents.genders (
			id serial not null,
			alias varchar null,
			constraint pk_vehicle_genders primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create table incidents.stages  (
			id serial not null,
			alias varchar null,
			constraint pk_stages primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create table incidents.user_types  (
			id serial not null,
			alias varchar null,
			constraint pk_user_types primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create table incidents.injury_types(
			id serial not null,
			alias varchar null,
			constraint pk_injury_types primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create table incidents.injury_levels(
			id serial not null,
			alias varchar null,
			constraint pk_injury_levels primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create table incidents.participant_assistance_types (
			id serial not null,
			alias varchar null,
			constraint pk_participant_assistance_types primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create table incidents.participant_transfer_places (
			id serial not null,
			alias varchar null,
			constraint pk_participant_transfer_places primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create table incidents.participants (
			id serial not null,
			incident_id int null,
			name varchar null,
			rut varchar null,
			address varchar null,
			phone varchar null,
			age int null,
			gender_id int null,
			stage_id int null,
			user_type_id int null,
			injury_type_id int null,
			injury_level_id int null,
			participant_assistance_type_id int null,
			participant_transfer_place_id int null,
			constraint pk_participants primary key (id)
		) tablespace tbs_controltrafico_incidents;
		
		create index idx_participants_incident_id on
		incidents.participants
			using btree (incident_id);	
		
		create index idx_gender_id on
		incidents.participants
			using btree (gender_id);
		
		create index idx_stage_id on
		incidents.participants
			using btree (stage_id);	
		
		create index idx_user_type_id on
		incidents.participants
			using btree (user_type_id);	
		
		create index idx_injury_type_id on
		incidents.participants
			using btree (injury_type_id);
		
		create index idx_injury_level_id on
		incidents.participants
			using btree (injury_level_id);
		
		create index idx_participant_assistance_type_id on
		incidents.participants
			using btree (participant_assistance_type_id);	
			
		create index idx_participant_transfer_place_id on
		incidents.participants
			using btree (participant_transfer_place_id);	
		
		
		alter table incidents.participants add constraint fk_participants_incident_id foreign key (incident_id) references incidents.incidents(id) ON DELETE CASCADE;
		alter table incidents.participants add constraint fk_gender_id foreign key (gender_id) references incidents.genders(id) ON DELETE CASCADE;
		alter table incidents.participants add constraint fk_stage_id foreign key (stage_id) references incidents.stages(id) ON DELETE CASCADE;
		alter table incidents.participants add constraint fk_user_type_id foreign key (user_type_id ) references incidents.user_types(id) ON DELETE CASCADE;
		
		alter table incidents.participants add constraint fk_injury_type_id foreign key (injury_type_id) references incidents.injury_types(id) ON DELETE CASCADE;
		alter table incidents.participants add constraint fk_injury_level_id foreign key (injury_level_id) references incidents.injury_levels(id) ON DELETE CASCADE;
		alter table incidents.participants add constraint fk_participant_assistance_type_id  foreign key (participant_assistance_type_id) references incidents.participant_assistance_types(id) ON DELETE CASCADE;
		alter table incidents.participants add constraint fk_participant_transfer_place_id  foreign key (participant_transfer_place_id) references incidents.participant_transfer_places(id) ON DELETE CASCADE;
		
		

  END IF;
END $$;












