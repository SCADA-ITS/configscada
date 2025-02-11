-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2003, NULL, 1, 1002, NULL,  NULL, 'Cuadro eléctrico Sentido MA', 'Cuadro eléctrico para el tubo Sentido Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, NULL, 2, 1001, NULL,  NULL, 'Cuadro eléctrico Sentido CA', 'Cuadro eléctrico para el tubo Sentido SUR', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 2, 1, 1, '1',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 3, 1, 1, '[
	{
					"id": 1,
					"startRampSeconds": 30, 
					"startRampNFans": [0]
	},
	{
					"id": 2,
					"startRampSeconds": 30, 
					"startRampNFans": [1]
	},
	{
					"id": 3,
					"startRampSeconds": 30, 
					"startRampNFans": [2]
	},
	{
					"id": 4,
					"startRampSeconds": 30, 
					"startRampNFans": [3]
	},
	{
					"id": 5,
					"startRampSeconds": 30, 
					"startRampNFans": [4]
	},
	{
					"id": 6,
					"startRampSeconds": 30, 
					"startRampNFans": [5]
	},
	{
					"id": 7,
					"startRampSeconds": 30, 
					"startRampNFans": [6]
	},
	{
					"id": 8,
					"startRampSeconds": 30, 
					"startRampNFans": [7]
	},
	{
					"id": 9,
					"startRampSeconds": 30, 
					"startRampNFans": [8]
	},
	{
					"id": 10,
					"startRampSeconds": 30, 
					"startRampNFans": [9]
	}

]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 1, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(2003, 2, 1, 2, '1',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 3, 1, 2, '[
	{
					"id": 1,
					"startRampSeconds": 30, 
					"startRampNFans": [0]
	},
	{
					"id": 2,
					"startRampSeconds": 30, 
					"startRampNFans": [1]
	},
	{
					"id": 3,
					"startRampSeconds": 30, 
					"startRampNFans": [2]
	},
	{
					"id": 4,
					"startRampSeconds": 30, 
					"startRampNFans": [3]
	},
	{
					"id": 5,
					"startRampSeconds": 30, 
					"startRampNFans": [4]
	},
	{
					"id": 6,
					"startRampSeconds": 30, 
					"startRampNFans": [5]
	},
	{
					"id": 7,
					"startRampSeconds": 30, 
					"startRampNFans": [6]
	},
	{
					"id": 8,
					"startRampSeconds": 30, 
					"startRampNFans": [7]
	},
	{
					"id": 9,
					"startRampSeconds": 30, 
					"startRampNFans": [8]
	},
	{
					"id": 10,
					"startRampSeconds": 30, 
					"startRampNFans": [9]
	},
	{
					"id": 11,
					"startRampSeconds": 30, 
					"startRampNFans": [10]
	},
	{
					"id": 12,
					"startRampSeconds": 30, 
					"startRampNFans": [11]
	},
	{
					"id": 13,
					"startRampSeconds": 30, 
					"startRampNFans": [12]
	},
	{
					"id": 14,
					"startRampSeconds": 30, 
					"startRampNFans": [13]
	},
	{
					"id": 15,
					"startRampSeconds": 30, 
					"startRampNFans": [14]
	},
	{
					"id": 16,
					"startRampSeconds": 30, 
					"startRampNFans": [15]
	}
]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 2, '16', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

