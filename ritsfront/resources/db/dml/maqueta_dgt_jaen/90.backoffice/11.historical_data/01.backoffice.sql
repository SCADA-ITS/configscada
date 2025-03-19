DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'historical_data') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(11, 'historical_data_backoffice', NULL, NULL, 'jdbc:postgresql://192.168.88.51:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'historical_data',
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

	-- 
  	-- historical_data.sg_metadata_tables
  	--
	INSERT INTO historical_data.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, metadata) VALUES
	(1, 'pmv', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV_DESCRIPTION', NULL, true, true, 
	--sql_view
	'
		SELECT audit_log_id, time_stamp, user_name, translation, element, 
		CASE
			WHEN hist.audit_commands.command_values = ''null'' THEN ''-''
			ELSE hist.audit_commands.command_values
		END AS command_values 
			FROM hist.audit_commands 
				INNER JOIN master.i18n_labels on master.i18n_labels.label = hist.audit_commands.command_type
				INNER JOIN conf.elements on conf.elements.alias = hist.audit_commands.element
			WHERE master.i18n_labels.locale_code = ''es_ES''
				and conf.elements.element_type_id = 71 
				and hist.audit_commands.log_subtype_id = 1 
				and time_stamp >= date_trunc(''month'', now()) 
				and time_stamp < date_trunc(''month'', now()) + INTERVAL ''1 month''
			ORDER BY time_stamp desc
	', NULL),

	(2, 'sem', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM_DESCRIPTION', NULL, true, true, 
	--sql_view
	'
		SELECT audit_log_id, time_stamp, user_name, translation, element
			FROM hist.audit_commands 
				INNER JOIN master.i18n_labels on master.i18n_labels.label = hist.audit_commands.command_type
				INNER JOIN conf.elements on conf.elements.alias = hist.audit_commands.element
			WHERE master.i18n_labels.locale_code = ''es_ES''
				and conf.elements.element_type_id = 13 
				and hist.audit_commands.log_subtype_id = 1 
				and time_stamp >= date_trunc(''month'', now()) 
				and time_stamp < date_trunc(''month'', now()) + INTERVAL ''1 month''
			ORDER BY time_stamp desc
	', NULL),


	(3, 'pressurization', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES_DESCRIPTION', NULL, true, true, 
	--sql_view
	'
		SELECT audit_log_id, time_stamp, user_name, translation, element
			FROM hist.audit_commands 
				INNER JOIN master.i18n_labels on master.i18n_labels.label = hist.audit_commands.command_type
				INNER JOIN conf.elements on conf.elements.alias = hist.audit_commands.element
			WHERE master.i18n_labels.locale_code = ''es_ES''
				and conf.elements.element_type_id = 32 
				and hist.audit_commands.log_subtype_id = 1 
				and time_stamp >= date_trunc(''month'', now()) 
				and time_stamp < date_trunc(''month'', now()) + INTERVAL ''1 month''
			ORDER BY time_stamp desc
	', NULL),

	(4, 'lightning_circuit', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT_DESCRIPTION', NULL, true, true, 
	--sql_view
	'
		SELECT audit_log_id, time_stamp, user_name, translation, element
			FROM hist.audit_commands 
				INNER JOIN master.i18n_labels on master.i18n_labels.label = hist.audit_commands.command_type
				INNER JOIN conf.elements on conf.elements.alias = hist.audit_commands.element
			WHERE master.i18n_labels.locale_code = ''es_ES''
				and conf.elements.element_type_id = 84 
				and hist.audit_commands.log_subtype_id = 1 
				and time_stamp >= date_trunc(''month'', now()) 
				and time_stamp < date_trunc(''month'', now()) + INTERVAL ''1 month''
			ORDER BY time_stamp desc
	', NULL),

	(5, 'barrier', 'LBL_BACKOFFICE_SG_METADATA_TABLES_BARRIER', 'LBL_BACKOFFICE_SG_METADATA_TABLES_BARRIER', 'LBL_BACKOFFICE_SG_METADATA_TABLES_BARRIER_DESCRIPTION', NULL, true, true, 
	--sql_view
	'
		SELECT audit_log_id, time_stamp, user_name, translation, element
			FROM hist.audit_commands 
				INNER JOIN master.i18n_labels on master.i18n_labels.label = hist.audit_commands.command_type
				INNER JOIN conf.elements on conf.elements.alias = hist.audit_commands.element
			WHERE master.i18n_labels.locale_code = ''es_ES''
				and conf.elements.element_type_id = 8 
				and hist.audit_commands.log_subtype_id = 1 
				and time_stamp >= date_trunc(''month'', now()) 
				and time_stamp < date_trunc(''month'', now()) + INTERVAL ''1 month''
			ORDER BY time_stamp desc
	', NULL),

	(6, 'cam', 'LBL_BACKOFFICE_SG_METADATA_TABLES_CAM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_CAM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_CAM_DESCRIPTION', NULL, true, true, 
	--sql_view
	'
		SELECT audit_log_id, time_stamp, user_name, translation, element
			FROM hist.audit_commands 
				INNER JOIN master.i18n_labels on master.i18n_labels.label = hist.audit_commands.command_type
				INNER JOIN conf.elements on conf.elements.alias = hist.audit_commands.element
			WHERE master.i18n_labels.locale_code = ''es_ES''
				and conf.elements.element_type_id = 2 
				and hist.audit_commands.log_subtype_id = 1 
				and time_stamp >= date_trunc(''month'', now()) 
				and time_stamp < date_trunc(''month'', now()) + INTERVAL ''1 month''
			ORDER BY time_stamp desc
	', NULL);

	-- 
  	-- historical_data.sg_metadata_columns
  	--
	INSERT INTO historical_data.sg_metadata_columns (sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	--pmv
	(1, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(1, 'date', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(1, 'user', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(1, 'command', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
    (1, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),
	(1, 'value', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATA', NULL, false, '{"editable": false}'),

	--sem
	(2, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(2, 'date', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(2, 'user', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(2, 'command', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
    (2, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

	--pressurization
	(3, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(3, 'date', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(3, 'user', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(3, 'command', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
    (3, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

	--lightning_circuit
	(4, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(4, 'date', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(4, 'user', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(4, 'command', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
    (4, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

	--barrier
	(5, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(5, 'date', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(5, 'user', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(5, 'command', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
    (5, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

	--cam
	(6, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(6, 'date', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(6, 'user', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(6, 'command', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
    (6, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}');

  END IF;
  
END $$;
