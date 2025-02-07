SET client_min_messages TO WARNING;

-- conf.transit_type_states
INSERT INTO conf.transit_type_states (transit_type_state_id,transit_type_state_code,alias,description,label_alias,label_description,user_transit_enable,enabled,visible,created_at,updated_at) VALUES
	 (1,'IN','Initial','Initial','LBL_TRANSIT_TYPE_STATE_INITIAL','LBL_TRANSIT_TYPE_STATE_INITIAL_DESC',false,true,true,'2025-01-31 11:20:35.936+01','2025-01-31 11:20:38.612+01'),
	 (2,'PT','Pending','Pending','LBL_TRANSIT_TYPE_STATE_PENDING','LBL_TRANSIT_TYPE_STATE_PENDING_DESC',true,true,true,'2025-01-31 11:20:35.936+01','2025-01-31 11:20:38.612+01'),
	 (3,'EN','End','End','LBL_TRANSIT_TYPE_STATE_END','LBL_TRANSIT_TYPE_STATE_END_DESC',true,true,true,'2025-01-31 11:20:35.936+01','2025-01-31 11:20:38.612+01'),
	 (4,'CA','Cancel','Cancel','LBL_TRANSIT_TYPE_STATE_CANCEL','LBL_TRANSIT_TYPE_STATE_CANCEL_DESC',true,true,true,'2025-01-31 11:20:35.936+01','2025-01-31 11:20:38.612+01');

-- conf.transit_types
INSERT INTO conf.transit_types (transit_type_id,init_transit_type_state_id,end_transit_type_state_id,discard_transit_type_state_id,alias,description,label_alias,label_description,enabled,visible,created_at,updated_at) VALUES
	 (1,1,3,4,'Generic Transit','Generic Transit','LBL_TRANSIT_GENERIC','LBL_TRANSIT_GENERIC_DESC',true,true,'2025-01-31 11:24:43.315+01','2025-01-31 11:24:49.967+01');


-- conf.transit_type_state_transitions
INSERT INTO conf.transit_type_state_transitions (transit_type_id,parent_transit_type_state_id,child_transit_type_state_id,enabled,visible,created_at,updated_at) VALUES
	 (1,1,2,true,true,'2025-01-31 11:30:29.574+01','2025-01-31 10:30:29.574+01'),
	 (1,1,4,true,true,'2025-01-31 11:30:29.574+01','2025-01-31 10:30:29.574+01'),
	 (1,2,3,true,true,'2025-01-31 11:30:29.574+01','2025-01-31 10:30:29.574+01'),
	 (1,2,4,true,true,'2025-01-31 11:30:29.574+01','2025-01-31 10:30:29.574+01');



