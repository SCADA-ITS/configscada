DO $do$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF (EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_reports')) THEN

    -- Cargar la extensión `tablefunc` si no está ya instalada
    CREATE EXTENSION IF NOT EXISTS tablefunc;

    -- Crear o reemplazar la vista
    EXECUTE 'CREATE OR REPLACE VIEW reports.vw_pmv_completa AS
			WITH comando_normalizado AS (
			  SELECT
			    ac.audit_log_id,
			    ac.time_stamp,
			    ac.user_name,
			    ac.comment,
			    ac.element,
			    ac.command_type,
			    CASE
			      WHEN jsonb_typeof(ac.command_values::jsonb) = ''string'' THEN (ac.command_values::jsonb)::text::jsonb
			      WHEN jsonb_typeof(ac.command_values::jsonb) = ''array''
			        AND jsonb_typeof((ac.command_values::jsonb)->0->''value'') = ''string''
			      THEN ((ac.command_values::jsonb)->0->>''value'')::jsonb
			      WHEN jsonb_typeof(ac.command_values::jsonb) = ''array'' THEN ac.command_values::jsonb
			    END AS comando_json
			  FROM hist.audit_commands ac
			  WHERE ac.log_subtype_id = 1
			    AND ac.time_stamp BETWEEN now() - INTERVAL ''1 month'' AND now()
    			AND element like ''%PMV%''
			),
			zonas AS (
			  SELECT
			    cn.audit_log_id,
			    jsonb_array_elements(cn.comando_json) AS zone
			  FROM comando_normalizado cn
			)
			
			SELECT
			  cn.audit_log_id as id,
			  cn.time_stamp,
			
			  -- Usuario
			  COALESCE(
			    CASE
			      WHEN cn.comment ~* ''^Plan:\\d+$'' THEN (
			        SELECT ap.user_name
			        FROM hist.audit_plans ap
			        JOIN conf.plans p ON p.alias = ap.plan
			        WHERE p.plan_id = CAST(regexp_replace(cn.comment, ''^Plan:'', '''', ''i'') AS INT)
			          AND ap.log_subtype_id = 3
			        LIMIT 1
			      )
			      ELSE cn.user_name
			    END,
			    ''Usuario''
			  ) AS user_name,
			
			  il.translation,
			  cn.element,
			
			  -- Comentario (plan o incidencia)
			  COALESCE(
			    CASE
			      WHEN cn.comment ~* ''^Plan:\\d+$'' THEN ''plan: '' || pl.alias
			      WHEN cn.comment ~* ''^ImsIncidentReport:\\d+$'' THEN ''incidencia: '' || it.description
			      ELSE cn.comment
			    END,
			    ''Usuario''
			  ) AS comment,
			
			  -- Zona gráfica 1
			  COALESCE((
			    SELECT string_agg(NULLIF(g->>''value'',''0''), '' '')
			    FROM zonas z, jsonb_array_elements(z.zone->''graphics'') g
			    WHERE z.audit_log_id = cn.audit_log_id AND z.zone->>''zone'' = ''1''
			  ), '' '') AS zona_grafico_1,
			
			  -- Mensaje
			  COALESCE((
			    SELECT string_agg(t->>''value'', '' / '' ORDER BY (t->>''id'')::int)
			    FROM zonas z, jsonb_array_elements(z.zone->''texts'') t
			    WHERE z.audit_log_id = cn.audit_log_id AND z.zone->>''zone'' = ''2''
			  ), '' '') AS mensaje,
			
			  -- Alternancias
			  COALESCE((
			    SELECT string_agg(NULLIF(g->>''alternance'',''0''), '' '')
			    FROM zonas z, jsonb_array_elements(z.zone->''graphics'') g
			    WHERE z.audit_log_id = cn.audit_log_id AND z.zone->>''zone'' = ''1''
			  ), '' '') AS alternancia_zona_grafico_1,
			
			  COALESCE((
			    SELECT string_agg(NULLIF(t->>''alternance'',''''), '' / '' ORDER BY (t->>''id'')::int)
			    FROM zonas z, jsonb_array_elements(z.zone->''texts'') t
			    WHERE z.audit_log_id = cn.audit_log_id AND z.zone->>''zone'' = ''2''
			      AND NULLIF(t->>''alternance'','''') IS NOT NULL
			  ), '' '') AS mensaje_alternancia
			
			FROM comando_normalizado cn
			LEFT JOIN LATERAL (
			  SELECT alias FROM conf.plans
			  WHERE cn.comment ~* ''^Plan:\\d+$''
			    AND plan_id = CAST(regexp_replace(cn.comment, ''^Plan:'', '''', ''i'') AS INT)
			  LIMIT 1
			) pl ON TRUE
			LEFT JOIN LATERAL (
			  SELECT cit.description
			  FROM hist.ims_incident_reports hir
			  JOIN conf.ims_incident_types cit USING (incident_type_id)
			  WHERE hir.incident_report_id = CAST(regexp_replace(cn.comment, ''^ImsIncidentReport:'', '''', ''i'') AS INT)
			  LIMIT 1
			) it ON TRUE
			JOIN master.i18n_labels il ON il.label = cn.command_type AND il.locale_code = ''es_ES''
			JOIN conf.elements el ON el.alias = cn.element AND el.element_type_id = 71
			ORDER BY cn.time_stamp DESC';
  END IF;
END $do$;
