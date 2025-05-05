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
WITH comando_normalizado AS (
	SELECT
		ac.audit_log_id,
		ac.time_stamp,
		ac.user_name,
		ac.command_values,
		CASE
			WHEN jsonb_typeof(ac.command_values::jsonb) = 'string' THEN
				(ac.command_values::jsonb)::text::jsonb
			WHEN jsonb_typeof(ac.command_values::jsonb) = 'array'
				AND jsonb_typeof((ac.command_values::jsonb)->0->'value') = 'string'
			THEN ((ac.command_values::jsonb)->0->>'value')::jsonb
			WHEN jsonb_typeof(ac.command_values::jsonb) = 'array' THEN
				(ac.command_values::jsonb)
			ELSE NULL
		END AS comando_json
	FROM hist.audit_commands ac
)

SELECT
	ac.audit_log_id,
	ac.time_stamp,
	COALESCE(
		CASE
			WHEN ac.comment ~* '^Plan:\d+$' THEN (
				SELECT ap.user_name
				FROM hist.audit_plans ap
				INNER JOIN conf.plans p ON p.alias = ap.plan
				WHERE p.plan_id = CAST(regexp_replace(ac.comment, '^Plan:', '', 'i') AS INTEGER)
				AND ap.log_subtype_id = 3
				LIMIT 1
			)
			ELSE ac.user_name
		END,
		'Usuario') AS user_name,
	il.translation,
	ac.element, 
	COALESCE(
		CASE
			WHEN ac.comment ~* '^Plan:\d+$' THEN 'plan: ' || pl.alias
			WHEN ac.comment ~* '^ImsIncidentReport:\d+$' THEN 'incidencia: ' || it.description
			ELSE ac.comment
		END,
		'Usuario') AS comment,

	-- Zona gráfica 1
	COALESCE((
		SELECT
			TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'value', '0'), ' '))
		FROM jsonb_array_elements(cn.comando_json) zone
		CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
		WHERE zone->>'zone' = '1'
	), ' ') AS zona_grafico_1,

	-- Mensaje
	COALESCE((
		SELECT string_agg(t->>'value', ' / ' ORDER BY (t->>'id')::int)
		FROM jsonb_array_elements(cn.comando_json) zone
		CROSS JOIN LATERAL jsonb_array_elements(zone->'texts') t
		WHERE zone->>'zone' = '2'
	), ' ') AS mensaje,

	-- Alternancia zona gráfica 1
	COALESCE((
		SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'alternance', '0'), ' '))
		FROM jsonb_array_elements(cn.comando_json) zone
		CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
		WHERE zone->>'zone' = '1'
	), ' ') AS alternancia_zona_grafico_1,

	-- Zona gráfica 2
	COALESCE((
		SELECT
			TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'value', '0'), ' '))
		FROM jsonb_array_elements(cn.comando_json) zone
		CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
		WHERE zone->>'zone' = '3'
	), ' ') AS zona_grafico_2,

	-- Alternancia del mensaje
	COALESCE((
		SELECT string_agg(NULLIF(t->>'alternance', ''), E' / ' ORDER BY (t->>'id')::int)
		FROM jsonb_array_elements(cn.comando_json) zone
		CROSS JOIN LATERAL jsonb_array_elements(zone->'texts') t
		WHERE zone->>'zone' = '2' AND NULLIF(t->>'alternance', '') IS NOT NULL
	), ' ') AS mensaje_alternancia,

	-- Alternancia zona gráfica 2
	COALESCE((
		SELECT TRIM(BOTH ' ' FROM string_agg(NULLIF(g->>'alternance', '0'), ' '))
		FROM jsonb_array_elements(cn.comando_json) zone
		CROSS JOIN LATERAL jsonb_array_elements(zone->'graphics') g
		WHERE zone->>'zone' = '3'
	), ' ') AS alternancia_zona_grafico_2

FROM hist.audit_commands ac
LEFT JOIN comando_normalizado cn ON ac.audit_log_id = cn.audit_log_id
LEFT JOIN LATERAL (
	SELECT alias
	FROM conf.plans
	WHERE ac.comment ~* '^Plan:\d+$'
		AND plan_id = CAST(regexp_replace(ac.comment, '^Plan:', '', 'i') AS INTEGER)
	LIMIT 1
) AS pl ON TRUE
LEFT JOIN LATERAL (
	SELECT description
	FROM hist.ims_incident_reports hir
	JOIN conf.ims_incident_types cit ON cit.incident_type_id = hir.incident_type_id
	WHERE ac.comment ~* '^ImsIncidentReport:\d+$'
		AND hir.incident_report_id = CAST(regexp_replace(ac.comment, '^ImsIncidentReport:', '') AS INTEGER)
	LIMIT 1
) AS it ON TRUE
INNER JOIN master.i18n_labels il ON il.label = ac.command_type
INNER JOIN conf.elements el ON el.alias = ac.element
WHERE il.locale_code = 'es_ES'
	AND el.element_type_id = 71
	AND ac.log_subtype_id = 1
	AND ac.time_stamp >= now() - INTERVAL '1 month'
	AND ac.time_stamp <= now()
ORDER BY ac.time_stamp DESC;


CREATE OR REPLACE VIEW historical_data.lightning_circuit AS
WITH circuit_data AS (
    SELECT
        ac.audit_log_id,
        ac.time_stamp,
        ac.user_name,
        il.translation,
        -- Aquí verificamos si el command_values contiene un "Element:84:<id>"
        CASE
            WHEN el84.alias IS NOT NULL THEN el84.alias
            ELSE el.alias
        END AS element,
        COALESCE(
            CASE
                WHEN ac.comment ~* '^Plan:\d+$' THEN 'plan: ' || pl.alias
                WHEN ac.comment ~* '^ImsIncidentReport:\d+$' THEN 'incidencia: ' || it.description
                ELSE ac.comment
            END,
            'Usuario'
        ) AS comment,
        reg.regimen::varchar AS regimen
    FROM hist.audit_commands ac
    LEFT JOIN LATERAL (
        -- Comprobamos si "command_values" contiene "Element:84:<id>" y extraemos el alias
        SELECT e.alias
        FROM conf.elements e
        WHERE EXISTS (
            SELECT 1
            FROM regexp_matches(ac.command_values, 'Element:84:(\d+)', 'g') AS m(id)
            WHERE
                e.element_type_id = 84
                AND e.element_id = CAST(m[1] AS INTEGER)
            LIMIT 1
        )
        LIMIT 1
    ) AS el84 ON TRUE
    LEFT JOIN LATERAL (
        SELECT alias
        FROM conf.plans
        WHERE ac.comment ~* '^Plan:\d+$'
        AND plan_id = CAST(regexp_replace(ac.comment, '^Plan:', '', 'i') AS INTEGER)
        LIMIT 1
    ) AS pl ON TRUE
    LEFT JOIN LATERAL (
        SELECT cit.description
        FROM hist.ims_incident_reports hir
        JOIN conf.ims_incident_types cit ON cit.incident_type_id = hir.incident_type_id
        WHERE ac.comment ~* '^ImsIncidentReport:\d+$'
        AND hir.incident_report_id = CAST(regexp_replace(ac.comment, '^ImsIncidentReport:', '', 'i') AS INTEGER)
        LIMIT 1
    ) AS it ON TRUE
    LEFT JOIN LATERAL (
        SELECT
            -- Reemplazo de valores en command_values
            CASE
                -- Si contiene "Element:84:<id>", lo reemplazamos por ''
                WHEN ac.command_values ~* 'Element:84:\d+' THEN ''
                -- Si es una lista, la analizamos
                WHEN ac.command_values ~* '^\[.*\]$' THEN
                    -- Extraemos el primer elemento de la lista
                    CASE
                        WHEN ac.command_values LIKE '%2%' THEN 'Soleado'
                        WHEN ac.command_values LIKE '%3%' THEN 'Nublado'
                        WHEN ac.command_values LIKE '%4%' THEN 'Crepuscular'
                        WHEN ac.command_values LIKE '%5%' THEN 'Nocturno'
                        WHEN ac.command_values LIKE '%6%' THEN 'Exterior/Nocturno'
                        ELSE ''
                    END
                -- Si es solo un valor, lo reemplazamos según corresponda
                WHEN ac.command_values LIKE '%2%' THEN 'Soleado'
                WHEN ac.command_values LIKE '%3%' THEN 'Nublado'
                WHEN ac.command_values LIKE '%4%' THEN 'Crepuscular'
                WHEN ac.command_values LIKE '%5%' THEN 'Nocturno'
                WHEN ac.command_values LIKE '%6%' THEN 'Exterior/Nocturno'
                ELSE ''
            END AS regimen
    ) AS reg ON TRUE
    INNER JOIN master.i18n_labels il ON il.label = ac.command_type
    INNER JOIN conf.elements el ON el.alias = ac.element
    WHERE il.locale_code = 'es_ES'
      AND (
        el.element_type_id = 84
        OR (el.element_type_id = 2000 AND ac.command_type ~* '(ILLUMINATION|CIRCUIT)')
      )
      AND ac.log_subtype_id = 1 
	  AND ac.time_stamp >= now() - INTERVAL '1 month'
	  AND ac.time_stamp <= now()
)
SELECT * FROM circuit_data;


	-- 
  	-- historical_data.sg_metadata_tables
  	--
	INSERT INTO historical_data.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, grid_id, metadata) VALUES
	(1, 'pmv', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PMV_DESCRIPTION', NULL, true, true,
		'SELECT * FROM historical_data.vw_pmv_completa', 200, NULL),

	(2, 'sem', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM', 'LBL_BACKOFFICE_SG_METADATA_TABLES_SEM_DESCRIPTION', NULL, true, true, 
	--sql_view
	'
		SELECT ac.audit_log_id, ac.time_stamp, ac.user_name, il.translation, ac.element, 
			COALESCE(
				CASE
					WHEN ac.comment ~* ''^Plan:\d+$'' THEN ''plan: '' || pl.alias
					WHEN ac.comment ~* ''^ImsIncidentReport:\d+$'' THEN ''incidencia: '' || it.description
					ELSE ac.comment
				END,
				''Usuario''
			) AS comment

		FROM hist.audit_commands ac

		LEFT JOIN LATERAL (
			SELECT alias
			FROM conf.plans
			WHERE ac.comment ~* ''^Plan:\d+$''
			AND plan_id = CAST(regexp_replace(ac.comment, ''^Plan:'', '''', ''i'') AS INTEGER)
			LIMIT 1
		) AS pl ON TRUE

		LEFT JOIN LATERAL (
			SELECT cit.description
			FROM hist.ims_incident_reports hir
			JOIN conf.ims_incident_types cit ON cit.incident_type_id = hir.incident_type_id
			WHERE ac.comment ~* ''^ImsIncidentReport:\d+$''
			AND hir.incident_report_id = CAST(regexp_replace(ac.comment, ''^ImsIncidentReport:'', '''', ''i'') AS INTEGER)
			LIMIT 1
		) AS it ON TRUE

		INNER JOIN master.i18n_labels il ON il.label = ac.command_type
		INNER JOIN conf.elements el ON el.alias = ac.element

		WHERE il.locale_code = ''es_ES''
		AND el.element_type_id = 13 
		AND ac.log_subtype_id = 1 
		AND ac.time_stamp >= date_trunc(''month'', now()) 
		AND ac.time_stamp < date_trunc(''month'', now()) + INTERVAL ''1 month''

		ORDER BY ac.time_stamp DESC
	', NULL, NULL),


	(3, 'pressurization', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES', 'LBL_BACKOFFICE_SG_METADATA_TABLES_PRES_DESCRIPTION', NULL, true, true, 
	--sql_view
	'
		SELECT ac.audit_log_id, ac.time_stamp, ac.user_name, il.translation, ac.element, 
			COALESCE(
				CASE
					WHEN ac.comment ~* ''^Plan:\d+$'' THEN ''plan: '' || pl.alias
					WHEN ac.comment ~* ''^ImsIncidentReport:\d+$'' THEN ''incidencia: '' || it.description
					ELSE ac.comment
				END,
				''Usuario''
			) AS comment

		FROM hist.audit_commands ac

		LEFT JOIN LATERAL (
			SELECT alias
			FROM conf.plans
			WHERE ac.comment ~* ''^Plan:\d+$''
			AND plan_id = CAST(regexp_replace(ac.comment, ''^Plan:'', '''', ''i'') AS INTEGER)
			LIMIT 1
		) AS pl ON TRUE

		LEFT JOIN LATERAL (
			SELECT cit.description
			FROM hist.ims_incident_reports hir
			JOIN conf.ims_incident_types cit ON cit.incident_type_id = hir.incident_type_id
			WHERE ac.comment ~* ''^ImsIncidentReport:\d+$''
			AND hir.incident_report_id = CAST(regexp_replace(ac.comment, ''^ImsIncidentReport:'', '''', ''i'') AS INTEGER)
			LIMIT 1
		) AS it ON TRUE

		INNER JOIN master.i18n_labels il ON il.label = ac.command_type
		INNER JOIN conf.elements el ON el.alias = ac.element

		WHERE il.locale_code = ''es_ES''
		AND el.element_type_id = 32
		AND ac.log_subtype_id = 1 
		AND ac.time_stamp >= date_trunc(''month'', now()) 
		AND ac.time_stamp < date_trunc(''month'', now()) + INTERVAL ''1 month''

		ORDER BY ac.time_stamp DESC
	', NULL, NULL),

	(4, 'lightning_circuit', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT', 'LBL_BACKOFFICE_SG_METADATA_TABLES_LIGHTNING_CIRCUIT_DESCRIPTION', NULL, true, true, 
	--sql_view
	'SELECT * FROM historical_data.lightning_circuit', NULL, NULL),

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
	--(1, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	--(1, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	--(1, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	--(1, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
	--(1, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),
	--(1, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	--(1, 'mensaje_texto', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_TEXT', NULL, false, '{"editable": false}'),
	--(1, 'mensaje_grafico', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_GRAPHIC', NULL, false, '{"editable": false}'),

	--sem
	(2, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(2, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(2, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(2, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
	(2, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(2, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

	--pressurization
	(3, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(3, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(3, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(3, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
 	(3, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(3, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

	--lightning_circuit
	(4, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(4, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(4, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(4, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
 	(4, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(4, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),
	(4, 'regimen', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}'),

	--barrier
	(5, 'audit_log_id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(5, 'time_stamp', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_DATE', NULL, false, '{"editable": false}'),
	(5, 'user_name', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_USER', NULL, false, '{"editable": false}'),
	(5, 'translation', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMAND', NULL, false, '{"editable": false}'),
	(5, 'comment', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_COMMENT', NULL, false, '{"editable": false}'),
	(5, 'element', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ELEMENT', NULL, false, '{"editable": false}');



  END IF;
  
END $$;
