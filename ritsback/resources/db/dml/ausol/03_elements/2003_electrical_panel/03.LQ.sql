-- 
-- Table: conf.elements
--
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2003, NULL, 4, 1022, NULL,  NULL, 'Cuadro eléctrico LQ-TSM', 'Cuadro eléctrico para la boca sentido Málaga del tunel La Quinta', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, NULL, 5, 1023, NULL,  NULL, 'Cuadro eléctrico LQ-TSC', 'Cuadro eléctrico para la boca sentido Cádiz del tunel La Quinta', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: conf.element_values
--
INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 3, 1, 4, '[
	{
					"id": 1,
					"startRampSeconds": 30, 
					"startRampNFans": [0, 1, 2, 3, 4, 5]
	},
	{
					"id": 2,
					"startRampSeconds": 30, 
					"startRampNFans": [6, 7, 8]
	},
	{
					"id": 3,
					"startRampSeconds": 30, 
					"startRampNFans": [9]
	}

]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 4, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2003, 3, 1, 5, '[
	{
					"id": 1,
					"startRampSeconds": 30, 
					"startRampNFans": [0, 1, 2, 3, 4, 5, 6, 7, 8]
	},
	{
					"id": 2,
					"startRampSeconds": 30, 
					"startRampNFans": [9, 10, 11, 12]
	},
	{
					"id": 3,
					"startRampSeconds": 30, 
					"startRampNFans": [13]
	},
	{
					"id": 4,
					"startRampSeconds": 30, 
					"startRampNFans": [14]
	},
	{
					"id": 5,
					"startRampSeconds": 30, 
					"startRampNFans": [15]
	}

]'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2003, 4, 1, 5, '16', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
