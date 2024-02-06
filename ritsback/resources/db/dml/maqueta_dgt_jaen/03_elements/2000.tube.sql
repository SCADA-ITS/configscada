INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(2000, NULL, 1, 0012000, NULL, NULL, 'Despeñaperros tubo sentido Madrid', 'Despeñaperros tubo sentido Madrid', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 2, 0022000, NULL, NULL, 'Despeñaperros tubo sentido Córdoba', 'Despeñaperros tubo sentido Córdoba', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2000, 1, 1, 1,
'{	
	"ventilationRegimes": [
        {
            "id": 1,
            "alias": "REGIMEN_PARADO",
            "description": "Régimen 0 túnel Despeñaperros para el sentido Madrid",
            "labelAlias": "LBL_REGIME_0",
            "fans": 0
        },
        {
            "id": 2,
            "alias": "REGIMEN_BAJO",
            "description": "Régimen bajo túnel Despeñaperros para el sentido Madrid",
            "labelAlias": "LBL_REGIME_1",
            "fans": 2
        },
        {
            "id": 3,
            "alias": "REGIMEN_MEDIO",
            "description": "Régimen medio túnel Despeñaperros para el sentido Madrid",
            "labelAlias": "LBL_REGIME_2",
            "fans": 3
        },
        {
            "id": 4,
            "alias": "REGIMEN_ALTO",
            "description": "Régimen alto túnel Despeñaperros para el sentido Madrid",
            "labelAlias": "LBL_REGIME_3",
            "fans": 4
        }
    ]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2000, 1, 1, 2,
'{	
	"ventilationRegimes": [
        {
            "id": 1,
            "alias": "REGIMEN_PARADO",
            "description": "Régimen 0 túnel Despeñaperros para el sentido Córdoba",
            "labelAlias": "LBL_REGIME_0",
            "fans": 0
        },
        {
            "id": 2,
            "alias": "REGIMEN_BAJO",
            "description": "Régimen bajo túnel Despeñaperros para el sentido Córdoba",
            "labelAlias": "LBL_REGIME_1",
            "fans": 2
        },
        {
            "id": 3,
            "alias": "REGIMEN_MEDIO",
            "description": "Régimen medio túnel Despeñaperros para el sentido Córdoba",
            "labelAlias": "LBL_REGIME_2",
            "fans": 3
        },
        {
            "id": 4,
            "alias": "REGIMEN_ALTO",
            "description": "Régimen alto túnel Despeñaperros para el sentido Córdoba",
            "labelAlias": "LBL_REGIME_3",
            "fans": 4
        }
    ]
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);