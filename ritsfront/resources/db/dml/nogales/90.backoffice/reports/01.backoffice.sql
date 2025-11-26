DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'hist') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(2, 'reports', NULL, NULL, 'jdbc:postgresql://10.133.51.20:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'reports',
	-- default_metadata_backoffice
	'{}',
	-- default_metadata_table
	'{
		"formHeight": 850,
    	"formWidth": 1100,
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
  	-- smartgen.sg_metadata_tables
  	--
	INSERT INTO reports.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, grid_id, metadata) VALUES
	(1, 'alarmas_hist', 'Histórico alarmas', 'Histórico alarmas', 'Histórico alarmas', NULL, false, false, 
			-- sql_view 
			'SELECT 
			    id,
			    inl."translation",
			    source_element as elemento,
			    MAX(activation)    AS fecha_activacion,
			    MAX(deactivation) AS fecha_desactivacion,
			    MAX(recognized) AS fecha_reconocimiento
			FROM hist.alarms a
			left join conf.alarm_configs ac  
			on a.alias = ac.alias
			left join master.i18n_labels inl 
			on ac.label_alias = inl."label" 
			where inl.locale_code = ''es_CL''
			GROUP BY id, source_element, inl."translation" 
			ORDER BY fecha_activacion desc'
			, null, null),
	(2, 'estados_hist', 'Histórico estados', 'Histórico estados', 'Histórico estados', NULL, false, false, 
			-- sql_view 
			'SELECT 
				created_at as fecha,
				element as alias,
			    CASE 
			        WHEN alias = ''UP''   THEN ''Comunicando''
			        WHEN alias = ''DOWN'' THEN ''Fuera de comunicaciones''
			        WHEN alias = ''ALARMS'' THEN ''Alarmado''
			        WHEN alias = ''UNKNOWN'' THEN ''Desconocido''
			        ELSE alias
			    END AS estado
			FROM 
				hist.element_states es 
			ORDER BY fecha desc'
			, null, null),
	(3, 'pmv_commands', 'Comandos enviados a PMVs', 'Comandos enviados a PMVs', 'Comandos enviados a PMVs', NULL, false, false, 
			-- sql_view 
			'SELECT 
				*
			FROM 
				reports.vw_pmv_completa 
			ORDER BY time_stamp desc'
			, 0171, null);
			
	INSERT INTO reports.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata) VALUES
	(0301, 3, 'id', 'LBL_BACKOFFICE_SG_METADATA_COLUMNS_ID', NULL, false, '{"formVisible": false, "tableVisible": false}'),
	(0302, 3, 'time_stamp', 'LBL_GRID_FIELD_DATE', NULL, false, '{"editable": false}'),
	(0303, 3, 'user_name', 'LBL_GRID_FIELD_USER', NULL, false, '{"editable": false}'),
	(0304, 3, 'translation', 'LBL_GRID_FIELD_COMMAND', NULL, false, '{"editable": false}'),
	(0305, 3, 'comment', 'LBL_GRID_FIELD_ORIGEN', NULL, false, '{"editable": false}'),
	(0306, 3, 'element', 'LBL_GRID_FIELD_EQUIPO', NULL, false, '{"editable": false}'),
	(0307, 3, 'zona_grafico_1', 'grafico principal', NULL, false, '{"editable": false}'),
	(0308, 3, 'mensaje', 'LBL_GRID_FIELD_MENSAJE', NULL, false, '{"editable": false}'),
	(0309, 3, 'alternancia_zona_grafico_1', 'grafico alternancia', NULL, false, '{"editable": false}'),
	(0310, 3, 'mensaje_alternancia', 'LBL_GRID_FIELD_ALTER_MENSAJE', NULL, false, '{"editable": false}');
	
  END IF;
END $$;
