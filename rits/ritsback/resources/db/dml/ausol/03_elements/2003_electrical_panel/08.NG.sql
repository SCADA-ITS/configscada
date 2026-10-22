-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2003, NULL, 16, 1078, NULL,  NULL, 'Cuadro eléctrico NG', 'Cuadro eléctrico para el tunel de Nagüelles', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 3, 1, 16, '[
	{
					"id": 1,
					"startRampSeconds": 30, 
					"startRampNFans": [0, 1, 2, 3]
	},
	{
					"id": 2,
					"startRampSeconds": 30, 
					"startRampNFans": [4, 5, 6]
	},
	{
					"id": 3,
					"startRampSeconds": 30, 
					"startRampNFans": [7]
	},
	{
					"id": 4,
					"startRampSeconds": 30, 
					"startRampNFans": [8]
	},
	{
					"id": 5,
					"startRampSeconds": 30, 
					"startRampNFans": [9]
	},
	{
					"id": 6,
					"startRampSeconds": 30, 
					"startRampNFans": [10]
	}
	
]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 16, '11', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
