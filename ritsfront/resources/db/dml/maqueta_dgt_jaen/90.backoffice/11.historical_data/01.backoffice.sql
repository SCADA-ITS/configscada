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

	CREATE OR REPLACE VIEW historical_data.vw_pmv_completa AS
	SELECT 
		audit_log_id,
		time_stamp,
		user_name,
		translation,
		element,
		COALESCE(
			CASE 
				WHEN comment ~* '^Plan:\d+$' THEN 'plan: ' || plan.alias
				WHEN comment ~* '^ImsIncidentReport:\d+$' THEN 'incidencia: ' || incident_type.description
				ELSE comment
			END,
		'Usuario') AS comment,

		-- texto plano + alternancia si existe
		CASE
			WHEN hist.audit_commands.command_values = 'null' THEN '-'
			WHEN jsonb_typeof(hist.audit_commands.command_values::jsonb) = 'array' THEN (
				SELECT 
					TRIM(BOTH ' ' FROM string_agg(t->>'value', ' ')) || 
					CASE 
						WHEN COUNT(NULLIF(t->>'alternance', '')) > 0 THEN 
							' / ' || TRIM(BOTH ' ' FROM string_agg(DISTINCT NULLIF(t->>'alternance', ''), ' ')) 
						ELSE '' 
					END
				FROM jsonb_array_elements(hist.audit_commands.command_values::jsonb) zone
				CROSS JOIN LATERAL jsonb_array_elements(zone->'texts') t
			)
			ELSE NULL
		END AS mensaje_texto,

		-- Zona gráfica 1
		CASE
			WHEN hist.audit_commands.command_values = 'null' THEN '-'
			WHEN jsonb_typeof(hist.audit_commands.command_values::jsonb) = 'array' THEN (
				SELECT 
					TRIM(BOTH ' ' FROM string_agg(g->>'value', ' '))
				FROM jsonb_array_elements(hist.audit_commands.command_values::jsonb) zone
				CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
				WHERE zone->>'zone' = '1'  -- Filtramos para la zona 1
				LIMIT 1
			)
			ELSE '-'
		END AS zona_grafico_1,

		-- Alternancia zona gráfica 1
		CASE
			WHEN hist.audit_commands.command_values = 'null' THEN '-'
			WHEN jsonb_typeof(hist.audit_commands.command_values::jsonb) = 'array' THEN (
				SELECT 
					TRIM(BOTH ' ' FROM string_agg(g->>'alternance', ' '))
				FROM jsonb_array_elements(hist.audit_commands.command_values::jsonb) zone
				CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
				WHERE zone->>'zone' = '1'  -- Filtramos para la zona 1
				LIMIT 1
			)
			ELSE '-'
		END AS alternancia_zona_grafico_1,

		-- Zona gráfica 2 (Zona 3 en tu caso)
		CASE
			WHEN hist.audit_commands.command_values = 'null' THEN '-'
			WHEN jsonb_typeof(hist.audit_commands.command_values::jsonb) = 'array' THEN (
				SELECT 
					TRIM(BOTH ' ' FROM string_agg(g->>'value', ' '))
				FROM jsonb_array_elements(hist.audit_commands.command_values::jsonb) zone
				CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
				WHERE zone->>'zone' = '3'  -- Filtramos para la zona 3 (zona gráfica 2)
				LIMIT 1
			)
			ELSE '-'
		END AS zona_grafico_2,

		-- Alternancia zona gráfica 2 (Zona 3)
		CASE
			WHEN hist.audit_commands.command_values = 'null' THEN '-'
			WHEN jsonb_typeof(hist.audit_commands.command_values::jsonb) = 'array' THEN (
				SELECT 
					TRIM(BOTH ' ' FROM string_agg(g->>'alternance', ' '))
				FROM jsonb_array_elements(hist.audit_commands.command_values::jsonb) zone
				CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
				WHERE zone->>'zone' = '3'  -- Filtramos para la zona 3 (zona gráfica 2)
				LIMIT 1
			)
			ELSE '-'
		END AS alternancia_zona_grafico_2

	FROM hist.audit_commands
	LEFT JOIN LATERAL (
		SELECT alias 
		FROM conf.plans 
		WHERE 
			hist.audit_commands.comment ~* '^Plan:\d+$' 
			AND plan_id = CAST(regexp_replace(hist.audit_commands.comment, '^Plan:', '', 'i') AS INTEGER)
		LIMIT 1
	) AS plan ON TRUE
	LEFT JOIN LATERAL (
		SELECT cit.description
		FROM hist.ims_incident_reports h
		JOIN conf.ims_incident_types cit ON cit.incident_type_id = h.incident_type_id
		WHERE 
			hist.audit_commands.comment ~* '^ImsIncidentReport:\d+$'
			AND h.incident_report_id = CAST(regexp_replace(hist.audit_commands.comment, '^ImsIncidentReport:', '', 'i') AS INTEGER)
		LIMIT 1
	) AS incident_type ON TRUE
	INNER JOIN master.i18n_labels 
		ON master.i18n_labels.label = hist.audit_commands.command_type
	INNER JOIN conf.elements 
		ON conf.elements.alias = hist.audit_commands.element
	WHERE master.i18n_labels.locale_code = 'es_ES'
		AND conf.elements.element_type_id = 71 
		AND hist.audit_commands.log_subtype_id = 1
		AND time_stamp >= date_trunc('month', now()) 
		AND time_stamp < date_trunc('month', now()) + INTERVAL '1 month'
	ORDER BY time_stamp DESC;



	-- 
  	-- historical_data.sg_metadata_tables
  	--
	INSERT INTO historical_data.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, grid_id, metadata) VALUES
	(1, 'pmv', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV_DESCRIPTION', NULL, true, true,
		'SELECT * FROM historical_data.vw_pmv_completa', NULL, NULL),

	(2, 'sem', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM_DESCRIPTION', NULL, true, true, 
	--sql_view
	'
		SELECT audit_log_id, time_stamp, user_name, translation, element, comment
			FROM hist.audit_commands 
				INNER JOIN master.i18n_labels on master.i18n_labels.label = hist.audit_commands.command_type
				INNER JOIN conf.elements on conf.elements.alias = hist.audit_commands.element
			WHERE master.i18n_labels.locale_code = ''es_ES''
				and conf.elements.element_type_id = 13 
				and hist.audit_commands.log_subtype_id = 1 
				and time_stamp >= date_trunc(''month'', now()) 
				and time_stamp < date_trunc(''month'', now()) + INTERVAL ''1 month''
			ORDER BY time_stamp desc
	', NULL, NULL),


	(3, 'pressurization', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES_DESCRIPTION', NULL, true, true, 
	--sql_view
	'
		SELECT audit_log_id, time_stamp, user_name, translation, element, comment
			FROM hist.audit_commands 
				INNER JOIN master.i18n_labels on master.i18n_labels.label = hist.audit_commands.command_type
				INNER JOIN conf.elements on conf.elements.alias = hist.audit_commands.element
			WHERE master.i18n_labels.locale_code = ''es_ES''
				and conf.elements.element_type_id = 32 
				and hist.audit_commands.log_subtype_id = 1 
				and time_stamp >= date_trunc(''month'', now()) 
				and time_stamp < date_trunc(''month'', now()) + INTERVAL ''1 month''
			ORDER BY time_stamp desc
	', NULL, NULL),

	(4, 'lightning_circuit', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT_DESCRIPTION', NULL, true, true, 
	--sql_view
	'
		SELECT audit_log_id, time_stamp, user_name, translation, element, comment
			FROM hist.audit_commands 
				INNER JOIN master.i18n_labels on master.i18n_labels.label = hist.audit_commands.command_type
				INNER JOIN conf.elements on conf.elements.alias = hist.audit_commands.element
			WHERE master.i18n_labels.locale_code = ''es_ES''
				and conf.elements.element_type_id = 84 
				and hist.audit_commands.log_subtype_id = 1 
				and time_stamp >= date_trunc(''month'', now()) 
				and time_stamp < date_trunc(''month'', now()) + INTERVAL ''1 month''
			ORDER BY time_stamp desc
	', NULL, NULL),

	(5, 'barrier', 'LBL_BACKOFFICE_SG_METADATA_TABLES_BARRIER', 'LBL_BACKOFFICE_SG_METADATA_TABLES_BARRIER', 'LBL_BACKOFFICE_SG_METADATA_TABLES_BARRIER_DESCRIPTION', NULL, true, true, 
	--sql_view
	'
		SELECT audit_log_id, time_stamp, user_name, translation, element, comment
			FROM hist.audit_commands 
				INNER JOIN master.i18n_labels on master.i18n_labels.label = hist.audit_commands.command_type
				INNER JOIN conf.elements on conf.elements.alias = hist.audit_commands.element
			WHERE master.i18n_labels.locale_code = ''es_ES''
				and conf.elements.element_type_id = 8 
				and hist.audit_commands.log_subtype_id = 1 
				and time_stamp >= date_trunc(''month'', now()) 
				and time_stamp < date_trunc(''month'', now()) + INTERVAL ''1 month''
			ORDER BY time_stamp desc
	', NULL, NULL);


	-- 
  	-- historical_data.sg_metadata_columns
  	--
	INSERT INTO historical_data.sg_metadata_columns (sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	--pmv
	(1, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(1, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(1, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(1, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
	(1, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),
	(1, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(1, 'mensaje_texto', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_TEXT', NULL, false, '{"editable": false}'),
	(1, 'mensaje_grafico', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC', NULL, false, '{"editable": false}'),

	--sem
	(2, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(2, 'date', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(2, 'user', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(2, 'command', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
	(2, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(2, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

	--pressurization
	(3, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(3, 'date', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(3, 'user', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(3, 'command', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
 	(3, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(3, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

	--lightning_circuit
	(4, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(4, 'date', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(4, 'user', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(4, 'command', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
 	(4, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(4, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

	--barrier
	(5, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(5, 'date', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(5, 'user', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(5, 'command', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
	(5, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(5, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}');



  END IF;
  
END $$;
