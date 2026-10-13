-- 
-- Table: conf.elements.tube
--

-- Tubos para la vetilacion
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
(2000, NULL, 1, 0012000, NULL,  NULL, 'Cantón 1', 'Cantón 1 (720 – 1748 sentido N-S)', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 2, 0022000, NULL,  NULL, 'Cantón 2', 'Cantón 2 (1748 – 2690 sentido N-S)', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 3, 0032000, NULL,  NULL, 'Cantón 3', 'Cantón 3 (2690 – 3368 sentido N-S)', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 4, 0042000, NULL,  NULL, 'Cantón 4', 'Cantón 4 (4020 – 5160 sentido N-S)', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 5, 0052000, NULL,  NULL, 'Cantón 5', 'Cantón 5 (5160 – 6280 sentido N-S)', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 6, 0062000, NULL,  NULL, 'Cantón 6', 'Cantón 6 (6280 – 7220 sentido N-S)', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 7, 0072000, NULL,  NULL, 'Cantón 7', 'Cantón 7 (7220 – 8220 sentido N-S)', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 8, 0082000, NULL,  NULL, 'Cantón 8', 'Cantón 8 (8220 – 7220 sentido S-N)', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 9, 0092000, NULL,  NULL, 'Cantón 9', 'Cantón 9 (7220 – 6280 sentido S-N)', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 10, 0102000, NULL,  NULL, 'Cantón 10', 'Cantón 10 (6280 – 5160 sentido S-N)', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 11, 0112000, NULL,  NULL, 'Cantón 11', 'Cantón 11 (8220 – 7220 sentido S-N)', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, NULL, 12, 0122000, NULL,  NULL, 'Cantón 12', 'Cantón 12 (4020 – 2690 sentido S-N)', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2000, 1003, 1, 1, 'canton_1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1003, 1, 2, 'canton_2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1003, 1, 3, 'canton_3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1003, 1, 4, 'canton_4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1003, 1, 5, 'canton_5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1003, 1, 6, 'canton_6', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1003, 1, 7, 'canton_7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1003, 1, 8, 'canton_8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1003, 1, 9, 'canton_9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1003, 1, 10, 'canton_10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1003, 1, 11, 'canton_11', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1003, 1, 12, 'canton_12', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2000, 1, 1, 1, '{	
	"ventilationRegimes": [{
			"id": 0,
			"alias": "REGIMEN_0",
			"description": "00 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_00_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_1.pdf"
		},
		{
			"id": 1,
			"alias": "REGIMEN_1",
			"description": "20 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_20_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_1.pdf"
		},
		{
			"id": 2,
			"alias": "REGIMEN_2",
			"description": "40 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_40_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_1.pdf"
		},
		{
			"id": 3,
			"alias": "REGIMEN_3",
			"description": "60 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_60_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_1.pdf"
		},
		{
			"id": 4,
			"alias": "REGIMEN_4",
			"description": "80 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_80_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_1.pdf"
		},
		{
			"id": 5,
			"alias": "REGIMEN_5",
			"description": "100 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_100_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_1.pdf"
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 1, 2, '{	
	"ventilationRegimes": [{
			"id": 0,
			"alias": "REGIMEN_0",
			"description": "00 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_00_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_2.pdf"
		},
		{
			"id": 1,
			"alias": "REGIMEN_1",
			"description": "20 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_20_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_2.pdf"
		},
		{
			"id": 2,
			"alias": "REGIMEN_2",
			"description": "40 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_40_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_2.pdf"
		},
		{
			"id": 3,
			"alias": "REGIMEN_3",
			"description": "60 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_60_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_2.pdf"
		},
		{
			"id": 4,
			"alias": "REGIMEN_4",
			"description": "80 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_80_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_2.pdf"
		},
		{
			"id": 5,
			"alias": "REGIMEN_5",
			"description": "100 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_100_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_2.pdf"
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 1, 3, '{	
	"ventilationRegimes": [{
			"id": 0,
			"alias": "REGIMEN_0",
			"description": "00 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_00_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_3.pdf"
		},
		{
			"id": 1,
			"alias": "REGIMEN_1",
			"description": "20 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_20_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_3.pdf"
		},
		{
			"id": 2,
			"alias": "REGIMEN_2",
			"description": "40 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_40_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_3.pdf"
		},
		{
			"id": 3,
			"alias": "REGIMEN_3",
			"description": "60 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_60_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_3.pdf"
		},
		{
			"id": 4,
			"alias": "REGIMEN_4",
			"description": "80 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_80_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_3.pdf"
		},
		{
			"id": 5,
			"alias": "REGIMEN_5",
			"description": "100 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_100_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_3.pdf"
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 1, 4, '{	
	"ventilationRegimes": [{
			"id": 0,
			"alias": "REGIMEN_0",
			"description": "00 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_00_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_4.pdf"
		},
		{
			"id": 1,
			"alias": "REGIMEN_1",
			"description": "20 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_20_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_4.pdf"
		},
		{
			"id": 2,
			"alias": "REGIMEN_2",
			"description": "40 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_40_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_4.pdf"
		},
		{
			"id": 3,
			"alias": "REGIMEN_3",
			"description": "60 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_60_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_4.pdf"
		},
		{
			"id": 4,
			"alias": "REGIMEN_4",
			"description": "80 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_80_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_4.pdf"
		},
		{
			"id": 5,
			"alias": "REGIMEN_5",
			"description": "100 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_100_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_4.pdf"
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 1, 5, '{	
	"ventilationRegimes": [{
			"id": 0,
			"alias": "REGIMEN_0",
			"description": "00 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_00_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_5.pdf"
		},
		{
			"id": 1,
			"alias": "REGIMEN_1",
			"description": "20 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_20_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_5.pdf"
		},
		{
			"id": 2,
			"alias": "REGIMEN_2",
			"description": "40 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_40_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_5.pdf"
		},
		{
			"id": 3,
			"alias": "REGIMEN_3",
			"description": "60 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_60_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_5.pdf"
		},
		{
			"id": 4,
			"alias": "REGIMEN_4",
			"description": "80 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_80_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_5.pdf"
		},
		{
			"id": 5,
			"alias": "REGIMEN_5",
			"description": "100 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_100_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_5.pdf"
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 1, 6, '{	
	"ventilationRegimes": [{
			"id": 0,
			"alias": "REGIMEN_0",
			"description": "00 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_00_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_6.pdf"
		},
		{
			"id": 1,
			"alias": "REGIMEN_1",
			"description": "20 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_20_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_6.pdf"
		},
		{
			"id": 2,
			"alias": "REGIMEN_2",
			"description": "40 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_40_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_6.pdf"
		},
		{
			"id": 3,
			"alias": "REGIMEN_3",
			"description": "60 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_60_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_6.pdf"
		},
		{
			"id": 4,
			"alias": "REGIMEN_4",
			"description": "80 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_80_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_6.pdf"
		},
		{
			"id": 5,
			"alias": "REGIMEN_5",
			"description": "100 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_100_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_6.pdf"
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 1, 7, '{	
	"ventilationRegimes": [{
			"id": 0,
			"alias": "REGIMEN_0",
			"description": "00 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_00_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_7.pdf"
		},
		{
			"id": 1,
			"alias": "REGIMEN_1",
			"description": "20 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_20_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_7.pdf"
		},
		{
			"id": 2,
			"alias": "REGIMEN_2",
			"description": "40 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_40_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_7.pdf"
		},
		{
			"id": 3,
			"alias": "REGIMEN_3",
			"description": "60 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_60_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_7.pdf"
		},
		{
			"id": 4,
			"alias": "REGIMEN_4",
			"description": "80 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_80_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_7.pdf"
		},
		{
			"id": 5,
			"alias": "REGIMEN_5",
			"description": "100 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_100_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_7.pdf"
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 1, 8, '{	
	"ventilationRegimes": [{
			"id": 0,
			"alias": "REGIMEN_0",
			"description": "00 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_00_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_8.pdf"
		},
		{
			"id": 1,
			"alias": "REGIMEN_1",
			"description": "20 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_20_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_8.pdf"
		},
		{
			"id": 2,
			"alias": "REGIMEN_2",
			"description": "40 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_40_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_8.pdf"
		},
		{
			"id": 3,
			"alias": "REGIMEN_3",
			"description": "60 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_60_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_8.pdf"
		},
		{
			"id": 4,
			"alias": "REGIMEN_4",
			"description": "80 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_80_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_8.pdf"
		},
		{
			"id": 5,
			"alias": "REGIMEN_5",
			"description": "100 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_100_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_8.pdf"
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 1, 9, '{	
	"ventilationRegimes": [{
			"id": 0,
			"alias": "REGIMEN_0",
			"description": "00 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_00_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_9.pdf"
		},
		{
			"id": 1,
			"alias": "REGIMEN_1",
			"description": "20 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_20_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_9.pdf"
		},
		{
			"id": 2,
			"alias": "REGIMEN_2",
			"description": "40 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_40_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_9.pdf"
		},
		{
			"id": 3,
			"alias": "REGIMEN_3",
			"description": "60 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_60_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_9.pdf"
		},
		{
			"id": 4,
			"alias": "REGIMEN_4",
			"description": "80 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_80_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_9.pdf"
		},
		{
			"id": 5,
			"alias": "REGIMEN_5",
			"description": "100 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_100_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_9.pdf"
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 1, 10, '{	
	"ventilationRegimes": [{
			"id": 0,
			"alias": "REGIMEN_0",
			"description": "00 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_00_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_10.pdf"
		},
		{
			"id": 1,
			"alias": "REGIMEN_1",
			"description": "20 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_20_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_10.pdf"
		},
		{
			"id": 2,
			"alias": "REGIMEN_2",
			"description": "40 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_40_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_10.pdf"
		},
		{
			"id": 3,
			"alias": "REGIMEN_3",
			"description": "60 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_60_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_10.pdf"
		},
		{
			"id": 4,
			"alias": "REGIMEN_4",
			"description": "80 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_80_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_10.pdf"
		},
		{
			"id": 5,
			"alias": "REGIMEN_5",
			"description": "100 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_100_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_10.pdf"
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 1, 11, '{	
	"ventilationRegimes": [{
			"id": 0,
			"alias": "REGIMEN_0",
			"description": "00 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_00_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_11.pdf"
		},
	 	{
			"id": 1,
			"alias": "REGIMEN_1",
			"description": "20 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_20_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_11.pdf"
		},
		{
			"id": 2,
			"alias": "REGIMEN_2",
			"description": "40 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_40_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_11.pdf"
		},
		{
			"id": 3,
			"alias": "REGIMEN_3",
			"description": "60 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_60_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_11.pdf"
		},
		{
			"id": 4,
			"alias": "REGIMEN_4",
			"description": "80 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_80_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_11.pdf"
		},
		{
			"id": 5,
			"alias": "REGIMEN_5",
			"description": "100 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_100_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_11.pdf"
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1, 1, 12, '{	
	"ventilationRegimes": [{
			"id": 0,
			"alias": "REGIMEN_0",
			"description": "00 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_00_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_12.pdf"
		},
		{
			"id": 1,
			"alias": "REGIMEN_1",
			"description": "20 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_20_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_12.pdf"
		},
		{
			"id": 2,
			"alias": "REGIMEN_2",
			"description": "40 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_40_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_12.pdf"
		},
		{
			"id": 3,
			"alias": "REGIMEN_3",
			"description": "60 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_60_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_12.pdf"
		},
		{
			"id": 4,
			"alias": "REGIMEN_4",
			"description": "80 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_80_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_12.pdf"
		},
		{
			"id": 5,
			"alias": "REGIMEN_5",
			"description": "100 por ciento de ventiladores activos",
			"labelAlias": "LBL_REGIME_100_PERCENT",
			"documentUrl": "resources/ims/ventilation_regimes/Canton_12.pdf"
		}
	],
	"smConfigs": [
		{
			"id": 0,
			"alias": "MANUAL_FAN",
			"description": "Config to calculate service regime",
			"mode": 0
		},
		{
			"id": 1,
			"alias": "AUTOMATIC_VENTILATION_SERVICE",
			"description": "Config to calculate service regime",
			"mode": 1
		},
		{
			"id": 2,
			"alias": "FIRE_MODE_VENTILATION_SERVICE",
			"description": "Config to calculate service regime",
			"mode": 2
		},
		{
			"id": 3,
			"alias": "MANUAL_VENTILATION_SERVICE",
			"description": "Config to calculate maintenance regime",
			"mode": 3
		}
	]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(2000, 1,1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2,1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 3,1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 4,1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 5,1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 6,1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 7,1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 8,1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 9,1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 10,1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 11,1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 12,1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Update groovy
UPDATE master.command_element_types SET on_change='SignallingCommand' WHERE element_type_id=2000 AND command_element_type_id=4;
UPDATE master.command_element_types SET on_change='SignallingCommand' WHERE element_type_id=2000 AND command_element_type_id=5;
UPDATE master.command_element_types SET on_change='SignallingCommand' WHERE element_type_id=2000 AND command_element_type_id=6;
UPDATE master.command_element_types SET on_change='SignallingCommand' WHERE element_type_id=2000 AND command_element_type_id=7;





