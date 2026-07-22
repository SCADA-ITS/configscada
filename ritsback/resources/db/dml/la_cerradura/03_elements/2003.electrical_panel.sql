-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2003, NULL, 1, 1001, NULL,  NULL, 'Despeñaperros Cuadro eléctrico Sentido Norte', 'Despeñaperros Cuadro eléctrico para el tubo Sentido Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, NULL, 2, 1002, NULL,  NULL, 'Despeñaperros Cuadro eléctrico Sentido SUR', 'Despeñaperros Cuadro eléctrico para el tubo Sentido SUR', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, NULL, 3, 3001, NULL,  NULL, 'El Corzo Cuadro eléctrico Sentido Norte', 'El Corzo Cuadro eléctrico para el tubo Sentido Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 2, 1, 1, '2',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 3, 1, 1, '[
	{
					"id": 1,
					"startRampSeconds": 60,
					"startRampNFans": [0,1]
	},
	{
					"id": 2,
					"startRampSeconds": 60,
					"startRampNFans": [2,3]
	},
	{
					"id": 3,
					"startRampSeconds": 60,
					"startRampNFans": [4,5]
	},
	{
					"id": 4,
					"startRampSeconds": 60,
					"startRampNFans": [6,7]
	},
	{
					"id": 5,
					"startRampSeconds": 60,
					"startRampNFans": [8,9]
	}

]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 1, '14', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


(2003, 2, 1, 2, '2',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 3, 1, 2, '[
	{
					"id": 1,
					"startRampSeconds": 60,
					"startRampNFans": [0,1]
	},
	{
					"id": 2,
					"startRampSeconds": 60,
					"startRampNFans": [2,3]
	},
	{
					"id": 3,
					"startRampSeconds": 60,
					"startRampNFans": [4,5]
	},
	{
					"id": 4,
					"startRampSeconds": 60,
					"startRampNFans": [6,7]
	},
	{
					"id": 5,
					"startRampSeconds": 60,
					"startRampNFans": [8,9]
	},
	{
					"id": 6,
					"startRampSeconds": 60,
					"startRampNFans": [10,11]
	},
	{
					"id": 7,
					"startRampSeconds": 60,
					"startRampNFans": [12,13]
	},
	{
					"id": 8,
					"startRampSeconds": 60,
					"startRampNFans": [14,15]
	},
	{
					"id": 9,
					"startRampSeconds": 60,
					"startRampNFans": [16,17]
	},
	{
					"id": 10,
					"startRampSeconds": 60,
					"startRampNFans": [18,19]
	},
	{
					"id": 11,
					"startRampSeconds": 60,
					"startRampNFans": [20,21]
	},
	{
					"id": 12,
					"startRampSeconds": 60,
					"startRampNFans": [22,23]
	},
	{
					"id": 13,
					"startRampSeconds": 60,
					"startRampNFans": [24,25]
	}
]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 2, '14', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2003, 2, 1, 3, '2',true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 3, 1, 3, '[
	{
					"id": 1,
					"startRampSeconds": 60,
					"startRampNFans": [0,1]
	},
	{
					"id": 2,
					"startRampSeconds": 60,
					"startRampNFans": [2,3]
	},
	{
					"id": 3,
					"startRampSeconds": 60,
					"startRampNFans": [4,5]
	},
	{
					"id": 4,
					"startRampSeconds": 60,
					"startRampNFans": [6,7]
	}
]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 3, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


