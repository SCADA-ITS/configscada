DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'historical_data') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(11, 'historical_data_backoffice', NULL, NULL, 'jdbc:postgresql://192.168.88.202:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'historical_data',
	-- default_metadata_backoffice
	'{}',
	-- default_metadata_table
	'{
		"formHeight": 700,
    	"formWidth": 900,
    	"tableHeight": 700,
    	"tableWidth": 900,
    	"showIdForm": false,
    	"showIdTable": false,
    	"showIdSearch": false,
    	"gridFilters": true
	}',
	-- default_metadata_column
	'{
	    "required": false,
	    "editable": true,
	    "refAddButton": false
	}',
	true);

  	-- historical_data.sg_metadata_tables
  	--
	INSERT INTO historical_data.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, grid_id, metadata) VALUES
	(1, 'pmv', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV_DESCRIPTION', NULL, true, true,
		'SELECT * FROM historical_data.vw_pmv_completa', 200, NULL),

	(2, 'sem', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM_DESCRIPTION', NULL, true, true, 
	'SELECT * FROM historical_data.view_barriers_commands', NULL, NULL),


	(3, 'pressurization', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.presurization', NULL, '{"formHeight": 800,
    	"formWidth": 1000}'),

	(4, 'lightning_circuit', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.lightning_circuit', NULL, NULL),

	(5, 'barrier', 'LBL_BACKOFFICE_SG_METADATA_TABLES_BARRIER', 'LBL_BACKOFFICE_SG_METADATA_TABLES_BARRIER', 'LBL_BACKOFFICE_SG_METADATA_TABLES_BARRIER_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.barrier', NULL, NULL),

	(6, 'ventilation', 'LBL_BACKOFFICE_SG_METADATA_TABLES_VENTILATION', 'LBL_BACKOFFICE_SG_METADATA_TABLES_VENTILATION', 'LBL_BACKOFFICE_SG_METADATA_TABLES_VENTILATION_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.ventilation', NULL, NULL),

	(7, 'co', 'LBL_BACKOFFICE_SG_METADATA_TABLES_CO', 'LBL_BACKOFFICE_SG_METADATA_TABLES_CO', 'LBL_BACKOFFICE_SG_METADATA_TABLES_CO_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.co', NULL, NULL),

	(8, 'opac', 'LBL_BACKOFFICE_SG_METADATA_TABLES_OPAC', 'LBL_BACKOFFICE_SG_METADATA_TABLES_OPAC', 'LBL_BACKOFFICE_SG_METADATA_TABLES_OPAC_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.opac', NULL, NULL),

    (9, 'lum', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LUM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LUM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LUM_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.lum', NULL, NULL),

    (10, 'ws', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.ws', NULL, NULL),

    (11, 'ane', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.ane', NULL, NULL),

    (12, 'vane', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.vane', NULL, NULL),

    (13, 'barreras', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.barreras', NULL, NULL),

    (14, 'pres', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.pres', NULL, NULL),


    (15, 'paneles', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.pmv', 201, NULL),

    (16, 'semaforos', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS', 'LBL_BACKOFFICE_SG_METADATA_TABLES_WS_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.semaforos', NULL, NULL);  



	-- 
  	-- historical_data.sg_metadata_columns
  	--
	INSERT INTO historical_data.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	--pmv
	(0101, 1, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
	(0102, 1, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"editable": false, formVisible": false, "tableVisible": false}'),
    (0103, 1, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(0104, 1, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(0105, 1, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
	(0106, 1, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),
	(0107, 1, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ORIGEN', NULL, false, '{"editable": false}'),
	(0108, 1, 'zona_grafico_1', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_1', NULL, false, '{"editable": false, "formVisible": false}'),
	(0109, 1, 'mensaje', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_TEXT', NULL, false, '{"editable": false}'),
	(0110, 1, 'zona_grafico_2', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_2', NULL, false, '{"editable": false, "formVisible": false}'),
	(0111, 1, 'alternancia_zona_grafico_1', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_ALT_1', NULL, false, '{"editable": false, "formVisible": false}'),
	(0112, 1, 'mensaje_alternancia', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_TEXT_ALT', NULL, false, '{"editable": false}'),
	(0113, 1, 'alternancia_zona_grafico_2', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_ALT_2', NULL, false, '{"editable": false, "formVisible": false}'),
	(0114, 1, 'zona_grafico_1_label', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_1', NULL, false, '{"editable": false}'),
	(0115, 1, 'zona_grafico_2_label', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_2', NULL, false, '{"editable": false}'),
	(0116, 1, 'alternancia_zona_grafico_1_label', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_ALT_1', NULL, false, '{"editable": false}'),
	(0117, 1, 'alternancia_zona_grafico_2_label', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_ALT_2', NULL, false, '{"editable": false}'),

	--sem
	(0201,2, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
	(0202,2, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"tableVisible": false}'),
	(0203,2, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": true}'),
	(0204,2, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": true}'),
	(0205,2, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": true}'),
	(0206,2, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": true}'),
	(0207,2, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": true, "refName":true}'),

	--pressurization
	(0301,3, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"editable": false, "formVisible": false, "tableVisible": false}'),
	(0302,3, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"tableVisible": false}'),
	(0303,3, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(0304,3, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(0305,3, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
 	(0306,3, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(0307,3, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),

	--lightning_circuit
	(0401, 4, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
	(0402, 4, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"position": 2, "tableVisible": false}'),
	(0403, 4, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"position": 3, "editable": false}'),
	(0404, 4, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"position": 4, "editable": false}'),
	(0405, 4, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"position": 5, "editable": false}'),
	(0407, 4, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"position": 7, "editable": false, "refName":true}'),
 	(0406, 4, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"position": 6, "editable": false}'),
	(0408, 4, 'regimen', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_REGIMEN', NULL, true, '{"position": 8, "editable": false}'),

	--barrier
	(0501, 5, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
	(0502, 5, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"tableVisible": false}'),
	(0503, 5, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(0504, 5, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(0505, 5, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
	(0506, 5, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(0507, 5, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),

		--Ventilation
	(0601, 6, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
	(0602, 6, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"tableVisible": false}'),
	(0603, 6, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(0604, 6, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(0605, 6, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
 	(0606, 6, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(0607, 6, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),
	(0608, 6, 'regimen', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_REGIMEN', NULL, true, '{"editable": false}'),
--ANALOGICOS
    --CO
	(0701, 7, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
	(0702, 7, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(0703, 7, 'concentration', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_CONCENTRATION', NULL, false, '{"editable": false}'),
	(0704, 7, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),
    --opac
	(0801, 8, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
	(0802, 8, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(0803, 8, 'concentration', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_CONCENTRATION', NULL, false, '{"editable": false}'),
	(0804, 8, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),
    --LUM
	(0901, 9, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
	(0902, 9, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(0903, 9, 'luminosity_real', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_LUMINOSITY_REAL', NULL, false, '{"editable": false}'),
	(0904, 9, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),
    --WS
	(1001, 10, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
	(1002, 10, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(1003, 10, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),
	(1004, 10, 'air_pressure', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_AIR_PRESSURE', NULL, false, '{"editable": false}'),
	(1005, 10, 'air_temperature', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_AIR_TEMP', NULL, false, '{"editable": false}'),
	(1006, 10, 'dew_point_temperature', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DEW_POINT_TEMP', NULL, false, '{"editable": false}'),
	(1007, 10, 'period', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_PERIOD', NULL, false, '{"editable": false}'),
	(1008, 10, 'relative_humidity', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_RELATIVE_HUMIDITY', NULL, false, '{"editable": false}'),
	(1009, 10, 'visibility', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_VISIBILITY', NULL, false, '{"editable": false}'),
	(1010, 10, 'wind_direction', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_WIND_DIRECTION', NULL, false, '{"editable": false}'),
	(1011, 10, 'wind_speed', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_WIND_SPEED', NULL, false, '{"editable": false}'),
	(1012, 10, 'wind_type', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_WIND_TYPE', NULL, false, '{"editable": false}'),
	(1013, 10, 'date', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE_WS', NULL, false, '{"editable": false}'),
	(1014, 10, 'precipitation_type', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_PRECIPITATION_TYPE', NULL, false, '{"editable": false}'),
	(1015, 10, 'precipitation_quantity', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_PRECIPITATION_QUANTITY', NULL, false, '{"editable": false}'),
	(1016, 10, 'precipitation_intensity', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_PRECIPITATION_INTENSITY', NULL, false, '{"editable": false}'),

	(1101, 11, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
    (1102, 11, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(1103, 11, 'wind_speed', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_WIND_SPEED', NULL, false, '{"editable": false}'),
	(1104, 11, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),

	(1201, 12, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
    (1202, 12, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(1203, 12, 'wind_speed', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_WIND_SPEED', NULL, false, '{"editable": false}'),
    (1204, 12, 'wind_direction', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_WIND_DIRECTION', NULL, false, '{"editable": false}'),
	(1205, 12, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),

	(1301, 13, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
    (1302, 13, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(1303, 13, 'estado_barrera', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ESTADO_BARRERA', NULL, false, '{"editable": false}'),
	(1304, 13, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),

	(1401, 14, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
    (1402, 14, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(1403, 14, 'fan_speed', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_FAN_SPEED', NULL, false, '{"editable": false}'),
    (1404, 14, 'grid_gate_state', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRID_GATE_STATE', NULL, false, '{"editable": false}'),
	(1405, 14, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),

	(1501, 15, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
	(1502, 15, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}'),
    (1503, 15, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(1504, 15, 'zona_grafico_1', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_1', NULL, false, '{"editable": false, "formVisible": false}'),
	(1505, 15, 'mensaje', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_TEXT', NULL, false, '{"editable": false}'),
	(1506, 15, 'zona_grafico_2', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_2', NULL, false, '{"editable": false, "formVisible": false}'),
	(1507, 15, 'alternancia_zona_grafico_1', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_ALT_1', NULL, false, '{"editable": false, "formVisible": false}'),
	(1508, 15, 'mensaje_alternancia', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_TEXT_ALT', NULL, false, '{"editable": false}'),
	(1509, 15, 'alternancia_zona_grafico_2', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_ALT_2', NULL, false, '{"editable": false, "formVisible": false}'),
	(1510, 15, 'zona_grafico_1_label', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_1', NULL, false, '{"editable": false}'),
	(1511, 15, 'zona_grafico_2_label', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_2', NULL, false, '{"editable": false}'),
	(1512, 15, 'alternancia_zona_grafico_1_label', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_ALT_1', NULL, false, '{"editable": false}'),
	(1513, 15, 'alternancia_zona_grafico_2_label', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC_ALT_2', NULL, false, '{"editable": false}'),

	(1601, 16, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, true, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
    (1602, 16, 'timestamp_at', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(1603, 16, 'estado_semaforo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ESTADO_SEM', NULL, false, '{"editable": false}'),
	(1604, 16, 'equipo', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false, "refName":true}');

  END IF;
  
END $$;
