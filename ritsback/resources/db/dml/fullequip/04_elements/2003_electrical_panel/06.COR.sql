-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2003, NULL, 10, 1054, NULL,  NULL, 'Cuadro eléctrico COR-TSM-BM', 'Cuadro eléctrico situado en la boca Málaga del tubo sentido Málaga del tunel Corominas', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, NULL, 11, 1054, NULL,  NULL, 'Cuadro eléctrico COR-TSM-BC', 'Cuadro eléctrico situado en la boca Cádiz del tubo sentido Málaga del tunel Corominas', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, NULL, 12, 1055, NULL,  NULL, 'Cuadro eléctrico COR-TSC-BM', 'Cuadro eléctrico situado en la boca Málaga del tubo sentido Cádiz del tunel Corominas', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, NULL, 13, 1055, NULL,  NULL, 'Cuadro eléctrico COR-TSC-BC', 'Cuadro eléctrico situado en la boca Cádiz del tubo sentido Cádiz del tunel Corominas', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 3, 1, 10, '[
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
	}
]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 10, '4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 3, 1, 11, '[
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
	}

]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 11, '6', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 3, 1, 12, '[
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
	}
]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 12, '5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 3, 1, 13, '[
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
	}
]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 13, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
