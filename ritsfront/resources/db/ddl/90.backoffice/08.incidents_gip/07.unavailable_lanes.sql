DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_incidents_gip') THEN

		
		create table incidents_gip.lanes_actions (
			id serial not null,
			alias varchar null,
			constraint pk_lanes_actions primary key (id)
		) tablespace tbl_incidents_gip;
		
		create table incidents_gip.lanes_states (
			id serial not null,
			alias varchar null,
			constraint pk_lane_states primary key (id)
		) tablespace tbl_incidents_gip;
		
		create table incidents_gip.lanes (
			id serial not null,
			alias varchar null,
			state_id int null,
			constraint pk_lanes primary key (id)
		) tablespace tbl_incidents_gip;
		
		create index idx_lane_id on
		incidents_gip.lanes
			using btree (state_id);
		
		alter table incidents_gip.lanes add constraint fk_state_id foreign key (state_id) references incidents_gip.lanes_states(id) ON DELETE CASCADE;
		
		create table incidents_gip.unavailable_lanes (
			id serial not null,
			incident_id int null,
			--count varchar null,
			--clousing_count varchar null,
			lane_action_id int null,
			lane_id int null,
			start_action timestamptz default current_timestamp,
			comment varchar null,
			constraint pk_unavailable_lanes primary key (id)
		) tablespace tbl_incidents_gip;
		
		create index idx_unavailable_lanes_incident_id on
		incidents_gip.unavailable_lanes
			using btree (incident_id);	
		
		create index idx_lanes_action_id on
		incidents_gip.unavailable_lanes
			using btree (lane_action_id);
		
		create index idx_lanes_id on
		incidents_gip.unavailable_lanes
			using btree (lane_id);	
		
		alter table incidents_gip.unavailable_lanes add constraint fk_unavailable_lanes_incident_id foreign key (incident_id) references incidents_gip.incidents(id) ON DELETE CASCADE;
		alter table incidents_gip.unavailable_lanes add constraint fk_lanes_action_id foreign key (lane_action_id) references incidents_gip.lanes_actions(id) ON DELETE CASCADE;
		alter table incidents_gip.unavailable_lanes add constraint fk_lanes foreign key (lane_id) references incidents_gip.lanes(id) ON DELETE CASCADE;
		
  END IF;
END $$;

