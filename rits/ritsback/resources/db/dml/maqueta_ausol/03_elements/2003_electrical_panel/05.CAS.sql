-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2003, NULL, 8, 1042, NULL,  NULL, 'Cuadro eléctrico CAS-BM', 'Cuadro eléctrico situado en la boca Málaga del tunel Casares', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, NULL, 9, 1042, NULL,  NULL, 'Cuadro eléctrico CAS-BC', 'Cuadro eléctrico situado en la boca Cádiz del tunel Casares', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 3, 1, 8, '[
	{
					"id": 1,
					"startRampSeconds": 30, 
					"startRampNFans": [0, 1, 2]
	},
	{
					"id": 2,
					"startRampSeconds": 30, 
					"startRampNFans": [3]
	},
	{
					"id": 3,
					"startRampSeconds": 30, 
					"startRampNFans": [4]
	},
	{
					"id": 4,
					"startRampSeconds": 30, 
					"startRampNFans": [5]
	},
	{
					"id": 5,
					"startRampSeconds": 30, 
					"startRampNFans": [6]
	}
]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 8, '7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 3, 1, 9, '[
	{
					"id": 1,
					"startRampSeconds": 30, 
					"startRampNFans": [0, 1, 2, 3]
	},
	{
					"id": 2,
					"startRampSeconds": 30, 
					"startRampNFans": [4, 5]
	},
	{
					"id": 3,
					"startRampSeconds": 30, 
					"startRampNFans": [6]
	},
	{
					"id": 4,
					"startRampSeconds": 30, 
					"startRampNFans": [7]
	}

]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 9, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
