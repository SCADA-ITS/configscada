DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'hist') THEN
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(3, 'etd_reports', NULL, NULL, 'jdbc:sqlserver://;serverName=10.133.51.23;databaseName=Nogales', 'sa', 'TkMFCQOQzi92f6CXerDDUg==', 'reports',
	-- default_metadata_backoffice
	'{}',
	-- default_metadata_table
	'{
		"formHeight": 850,
    	"formWidth": 1100,
    	"tableHeight": 700,
    	"tableWidth": 900,
    	"showIdForm": false,
    	"showIdTable": true,
    	"showIdSearch": false,
    	"gridFilters": true
	}',
	-- default_metadata_column
	'{
	    "required": false,
	    "editable": false,
	    "refAddButton": false
	}',
	true);
	
	-- 
  	-- smartgen.sg_metadata_tables
  	--
  	-- A ejecutar en el SQLServer de la ETD
	/*SET IDENTITY_INSERT reports.sg_metadata_tables ON;

	INSERT INTO reports.sg_metadata_tables (
	    id, name, label, label_singular, label_description, mdi_icon,
	    support_images, support_attachments, sql_view, grid_id, metadata
	)
	VALUES
	(3,'ETD_hist_data', 'Histórico datos ETD', 'Histórico datos ETD', 'Histórico datos ETD', NULL, 0, 0, 
	    -- sql_view
	    'SELECT 
	        id,
	        NroCarril, 
	        Fecha,
	        Hora,
	        Volumen,
	        Occupancy, 
	        Speed,
	        Categoria1,
	        Categoria2,
	        Categoria3,
	        Categoria4
	     FROM reports.minutos r
	     WHERE Fecha >= DATEADD(DAY, -1, CAST(GETDATE() AS DATE))
	     ORDER BY Fecha DESC, Hora DESC',
	     NULL, NULL
	),
	(4,'ETD_hist_data_hours', 'Histórico datos ETD Hora', 'Histórico datos ETD Hora', 'Histórico datos ETD Hora', NULL, 0, 0, 
	    'SELECT 
	        id,
	        NroCarril, 
	        Fecha,
	        Hora,
	        Volumen,
	        Speed,
	        Categoria1,
	        Categoria2,
	        Categoria3,
	        Categoria4
	     FROM reports.horas r
	     WHERE Fecha >= DATEADD(DAY, -1, CAST(GETDATE() AS DATE))
	     ORDER BY Fecha DESC, Hora DESC',
	     NULL, NULL
	),
	(5,'ETD_hist_data_days', 'Histórico datos ETD Día', 'Histórico datos ETD Día', 'Histórico datos ETD Día', NULL, 0, 0, 
	    'SELECT 
	        id,
	        NroCarril, 
	        Fecha,
	        Volumen,
	        Speed,
	        Categoria1,
	        Categoria2,
	        Categoria3,
	        Categoria4
	     FROM reports.dias r
	     WHERE Fecha >= DATEADD(DAY, -1, CAST(GETDATE() AS DATE))
	     ORDER BY Fecha DESC',
	     NULL, NULL
	),
	(6,'ETD_hist_data_months', 'Histórico datos ETD Mes', 'Histórico datos ETD Mes', 'Histórico datos ETD Mes', NULL, 0, 0, 
	    'SELECT 
	        id,
	        NroCarril, 
	        Anio,
	        Mes,
	        Volumen,
	        Speed,
	        Categoria1,
	        Categoria2,
	        Categoria3,
	        Categoria4
	     FROM reports.meses r
	     ORDER BY Anio DESC, Mes DESC',
	     NULL, NULL
	);
	
	SET IDENTITY_INSERT reports.sg_metadata_tables OFF;*/
	
	/*SET IDENTITY_INSERT reports.sg_metadata_columns ON;

INSERT INTO reports.sg_metadata_columns (id, sg_metadata_table_id, column_name, label, label_description, needs_translation, metadata, ref_view_column_id) VALUES
(0301, 3, 'id', 'Identificador', NULL, 1, '{"position": 1, "tableVisible": true, "editable": false, "refName": true}', null),
(0302, 3, 'Fecha', 'Fecha', NULL, 1, '{"position": 2, "editable": false, "tableVisible": true}', null),
(0303, 3, 'Hora', 'Hora', NULL, 1, '{"position": 3, "editable": false, "tableVisible": true}', null),
(0304, 3, 'NroCarril', 'Carril', NULL, 1, '{"position": 4, "editable": false, "tableVisible": true}', null),
(0305, 3, 'Volumen', 'Volumen', NULL, 1, '{"position": 5, "editable": false, "tableVisible": true}', null),
(0306, 3, 'Occupancy', 'Ocupación', NULL, 1, '{"position": 6, "editable": false, "tableVisible": true}', null),
(0307, 3, 'Speed', 'Velocidad', NULL, 1, '{"position": 7, "editable": false, "tableVisible": true}', null),
(0308, 3, 'Categoria1', 'Autos y camionetas', NULL, 1, '{"position": 8, "editable": false, "tableVisible": true}', null),
(0309, 3, 'Categoria2', 'Camionetas doble rueda trasera y/o autos y camionetas con remolque', NULL, 1, '{"position": 9, "editable": false, "tableVisible": true}', null),
(0310, 3, 'Categoria3', 'Camiones de dos ejes, maquinaria agrícola o de construcción', NULL, 1, '{"position": 10, "editable": false, "tableVisible": true}', null),
(0311, 3, 'Categoria4', 'Motos, cuatrimotos y motonetas', NULL, 1, '{"position": 11, "editable": false, "tableVisible": true}', null),

(0401, 4, 'id', 'Identificador', NULL, 1, '{"position": 1, "tableVisible": true, "editable": false, "refName": true}', null),
(0402, 4, 'Fecha', 'Fecha', NULL, 1, '{"position": 2, "editable": false, "tableVisible": true}', null),
(0403, 4, 'Hora', 'Hora', NULL, 1, '{"position": 3, "editable": false, "tableVisible": true}', null),
(0404, 4, 'NroCarril', 'Carril', NULL, 1, '{"position": 4, "editable": false, "tableVisible": true}', null),
(0405, 4, 'Volumen', 'Volumen', NULL, 1, '{"position": 5, "editable": false, "tableVisible": true}', null),
(0406, 4, 'Speed', 'Velocidad', NULL, 1, '{"position": 6, "editable": false, "tableVisible": true}', null),
(0407, 4, 'Categoria1', 'Autos y camionetas', NULL, 1, '{"position": 7, "editable": false, "tableVisible": true}', null),
(0408, 4, 'Categoria2', 'Camionetas doble rueda trasera y/o autos y camionetas con remolque', NULL, 1, '{"position": 8, "editable": false, "tableVisible": true}', null),
(0409, 4, 'Categoria3', 'Camiones de dos ejes, maquinaria agrícola o de construcción', NULL, 1, '{"position": 9, "editable": false, "tableVisible": true}', null),
(0410, 4, 'Categoria4', 'Motos, cuatrimotos y motonetas', NULL, 1, '{"position": 10, "editable": false, "tableVisible": true}', null),

(0501, 5, 'id', 'Identificador', NULL, 1, '{"position": 1, "tableVisible": true, "editable": false, "refName": true}', null),
(0502, 5, 'Fecha', 'Fecha', NULL, 1, '{"position": 2, "editable": false, "tableVisible": true}', null),
(0503, 5, 'Hora', 'Hora', NULL, 1, '{"position": 3, "editable": false, "tableVisible": true}', null),
(0504, 5, 'NroCarril', 'Carril', NULL, 1, '{"position": 4, "editable": false, "tableVisible": true}', null),
(0505, 5, 'Volumen', 'Volumen', NULL, 1, '{"position": 5, "editable": false, "tableVisible": true}', null),
(0506, 5, 'Speed', 'Velocidad', NULL, 1, '{"position": 6, "editable": false, "tableVisible": true}', null),
(0507, 5, 'Categoria1', 'Autos y camionetas', NULL, 1, '{"position": 7, "editable": false, "tableVisible": true}', null),
(0508, 5, 'Categoria2', 'Camionetas doble rueda trasera y/o autos y camionetas con remolque', NULL, 1, '{"position": 8, "editable": false, "tableVisible": true}', null),
(0509, 5, 'Categoria3', 'Camiones de dos ejes, maquinaria agrícola o de construcción', NULL, 1, '{"position": 9, "editable": false, "tableVisible": true}', null),
(0510, 5, 'Categoria4', 'Motos, cuatrimotos y motonetas', NULL, 1, '{"position": 10, "editable": false, "tableVisible": true}', null),

(0601, 6, 'id', 'Identificador', NULL, 1, '{"position": 1, "tableVisible": true, "editable": false, "refName": true}', null),
(0602, 6, 'Fecha', 'Fecha', NULL, 1, '{"position": 2, "editable": false, "tableVisible": true}', null),
(0603, 6, 'Hora', 'Hora', NULL, 1, '{"position": 3, "editable": false, "tableVisible": true}', null),
(0604, 6, 'NroCarril', 'Carril', NULL, 1, '{"position": 4, "editable": false, "tableVisible": true}', null),
(0605, 6, 'Volumen', 'Volumen', NULL, 1, '{"position": 5, "editable": false, "tableVisible": true}', null),
(0606, 6, 'Speed', 'Velocidad', NULL, 1, '{"position": 6, "editable": false, "tableVisible": true}', null),
(0607, 6, 'Categoria1', 'Autos y camionetas', NULL, 1, '{"position": 7, "editable": false, "tableVisible": true}', null),
(0608, 6, 'Categoria2', 'Camionetas doble rueda trasera y/o autos y camionetas con remolque', NULL, 1, '{"position": 8, "editable": false, "tableVisible": true}', null),
(0609, 6, 'Categoria3', 'Camiones de dos ejes, maquinaria agrícola o de construcción', NULL, 1, '{"position": 9, "editable": false, "tableVisible": true}', null),
(0610, 6, 'Categoria4', 'Motos, cuatrimotos y motonetas', NULL, 1, '{"position": 10, "editable": false, "tableVisible": true}', null);
	
SET IDENTITY_INSERT reports.sg_metadata_columns OFF;*/
	
	
  END IF;
END $$;
