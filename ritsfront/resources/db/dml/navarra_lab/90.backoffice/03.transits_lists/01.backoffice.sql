DO $$
BEGIN
  -- Verifica si el TABLESPACE existe
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_reporting_bo') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(3, 'transits_bo', NULL, NULL, 'jdbc:postgresql://10.253.5.84:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'transits_bo',
	-- default_metadata_backoffice
	'{}',
	-- default_metadata_table
	'{
		"formHeight": 700,
    	"formWidth": 900,
    	"tableHeight": 700,
    	"tableWidth": 900,
    	"gridFilters": true,
		"editable": true,
		"importable": true
	}',
	-- default_metadata_column
	'{
	    "required": false,
	    "editable": true,
	    "refAddButton": false
	}',
	true);

	INSERT INTO transits_bo.sg_metadata_tables
		(name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, metadata, sql_view, grid_id)
	VALUES
		('black_list', 'Lista negra', 'Entrada de lista negra', 'Vehículos incluidos en la lista negra', NULL, false, false,
			'{"showIdForm": false, "showIdTable": false}', NULL, NULL),
		('administrations', 'Administraciones', 'Administración', 'Administraciones asociadas a la lista negra', NULL, false, false,
			'{"showIdForm": false, "showIdTable": false}', NULL, NULL),
		('notification_types', 'Tipos de aviso', 'Tipo de aviso', 'Tipos de aviso disponibles', NULL, false, false,
			'{"showIdForm": false, "showIdTable": false, "editable": false}', NULL, NULL),
		('administration_recipients', 'Destinatarios', 'Destinatario', 'Correos o personas que recibirán los avisos', NULL, false, false,
			'{"showIdForm": false, "showIdTable": false}', NULL, NULL);

	INSERT INTO transits_bo.sg_metadata_columns
		(sg_metadata_table_id, column_name, label, label_description, needs_translation, metadata)
	VALUES
		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'black_list'),
			'id', 'Id', NULL, false, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'black_list'),
			'matricula', 'Matrícula', NULL, false, '{"position": 2, "required": true}'),
		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'black_list'),
			'administration_id', 'Administración', NULL, false, '{"position": 3, "required": true}'),
		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'black_list'),
			'fecha_inicio', 'Fecha de inicio', NULL, false, '{"position": 4}'),
		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'black_list'),
			'fecha_fin', 'Fecha de fin', NULL, false, '{"position": 5}'),

		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'administrations'),
			'id', 'Id', NULL, false, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'administrations'),
			'name', 'Nombre', NULL, false, '{"position": 2, "required": true, "refName": true}'),
		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'administrations'),
			'notification_type_id', 'Tipo de aviso', NULL, false, '{"position": 3, "required": true}'),

		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'notification_types'),
			'id', 'Id', NULL, false, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'notification_types'),
			'name', 'Tipo de aviso', NULL, false, '{"position": 2, "editable": false, "refName": true}'),

		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'administration_recipients'),
			'id', 'Id', NULL, false, '{"position": 1, "editable": false, "formVisible": false, "tableVisible": false}'),
		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'administration_recipients'),
			'administration_id', 'Administración', NULL, false, '{"position": 2, "required": true}'),
		((SELECT id FROM transits_bo.sg_metadata_tables WHERE name = 'administration_recipients'),
			'recipient', 'Correo, persona o grupo', NULL, false, '{"position": 3, "required": true, "refName": true}');

  END IF;
END $$;
