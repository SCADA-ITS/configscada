DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_backoffice') THEN
  
  	create table backoffice.participant_injuries (
		id serial not null,
		alias varchar null,
		constraint pk_participant_injuries primary key (id)
	) tablespace tbl_backoffice;
	
  	create table backoffice.participant_nacionalities (
		id serial not null,
		alias varchar null,
		constraint pk_participant_nacionalities primary key (id)
	) tablespace tbl_backoffice;
	
  	create table backoffice.participant_types (
		id serial not null,
		alias varchar null,
		constraint pk_participant_types primary key (id)
	) tablespace tbl_backoffice;
  
	create table backoffice.participants (			
		id serial not null,
		incident_id int null,
		participant_name varchar null,
		participant_rut varchar null,
		participant_age int null,
		participant_address varchar null,
		participant_vehicle varchar null,
		participant_type_id int null,
		participant_injury_id int null,
		participant_nacionality_id int null,
		
  		constraint pk_participants primary key (id)
	) tablespace tbl_backoffice;
				
	create index idx_participant_incident_id on
	backoffice.participants
		using btree (incident_id);	
				
	create index idx_participant_injury_id on
	backoffice.participants
		using btree (participant_injury_id);	
				
	create index idx_participant_nacionality_id on
	backoffice.participants
		using btree (participant_nacionality_id);
				
	create index idx_participant_type_id on
	backoffice.participants
		using btree (participant_type_id);
		
	alter table backoffice.participants add constraint fk_participant_incident_id foreign key (incident_id) references backoffice.incidents(id) ON DELETE CASCADE;
	alter table backoffice.participants add constraint fk_participant_injury_id foreign key (participant_injury_id) references backoffice.participant_injuries(id) ON DELETE CASCADE;
	alter table backoffice.participants add constraint fk_participant_nacionality_id foreign key (participant_nacionality_id) references backoffice.participant_nacionalities(id) ON DELETE CASCADE;
	alter table backoffice.participants add constraint fk_participant_type_id foreign key (participant_type_id) references backoffice.participant_types(id) ON DELETE CASCADE;
  END IF;
END $$;