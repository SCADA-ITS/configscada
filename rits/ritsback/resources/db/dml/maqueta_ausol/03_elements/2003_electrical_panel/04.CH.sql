-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2003, NULL, 6, 1032, NULL,  NULL, 'Cuadro eléctrico CH-TSM', 'Cuadro eléctrico para la boca sentido Málaga del tunel Calahoda', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, NULL, 7, 1033, NULL,  NULL, 'Cuadro eléctrico CH-TSC', 'Cuadro eléctrico para la boca sentido Cádiz del tunel Calahoda', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 3, 1, 6, '[
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
(2003, 4, 1, 6, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 3, 1, 7, '[
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
(2003, 4, 1, 7, '8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
