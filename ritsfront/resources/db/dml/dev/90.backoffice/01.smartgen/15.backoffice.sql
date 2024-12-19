-- smartgen
DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'smartgen') THEN
  
  	-- 
  	-- ui.backoffices
  	-- 
	
  	INSERT INTO ui.backoffices (id, "name", description, "label", jdbc, "user", "password", "schema", default_metadata_backoffice, default_metadata_table, default_metadata_column, enabled) 
	VALUES(1, 'smartgen', NULL, NULL, 'jdbc:postgresql://192.168.243.30:5430/rits', 'rits', 'uMuD7Uy0gpCRL4Jo1w6SOQ==', 'smartgen',  
	-- default_metadata_backoffice
	'{
		
	}',
	-- default_metadata_table
	'{
		"formHeight": 700,
    	"formWidth": 900,
    	"gridHeight": 700,
    	"gridWidth": 900,    	
		"showIdForm": true,
    	"showIdTable": false,
    	"showIdSearch": false,
    	"gridFilters": true
	}',
	-- default_metadata_column
	'{
	    "required": false,
	    "editable": true,
	    "refAddButton": true
	}',
	true);
	
	-- 
  	-- smartgen.sg_metadata_tables
  	--
	INSERT INTO smartgen.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, grid_id, metadata) VALUES
	(1, 'customers', 'LBL_CUSTOMERS', 'LBL_CUSTOMER', 'LBL_CUSTOMERS_DESCRIPTION', NULL, true, true, NULL, NULL,
	-- metadata products
	'{
		  "importable": true	
	}'),
	(2, 'products', 'LBL_PRODUCTS', 'LBL_PRODUCT', 'LBL_PRODUCTS_DESCRIPTION', NULL, true, true, NULL, NULL, 
	-- metadata products
	'{
		  "formHeight": 1000,
		  "autorefresh": 5,
	      "autorefreshForm": true,
		  "tabs": [
		    {
		      "label": "LBL_PRODUCT_SECTION_OTHERS",
		      "position": 2,
		      "fields": [
		        {
		          "name": "subcategory_id",
		          "position": 1
		        },
		        {
		          "name": "price",
		          "position": 2
		        }
		      ]
		    }
		  ],
		  "initialTabLabel": "LBL_PRODUCT_SECTION_MAIN",
		  "groupImages": [
		    {
		      "name": "main",
		      "label": "LBL_IMAGE_GROUP_PRODUCTS_1",
		      "description": "LBL_IMAGE_GROUP_PRODUCTS_1_DESC",
		      "position": 1,
		      "height": 300
		    }
		  ],
		  "groupAttachments": [
		    {
		      "name": "main",
		      "label": "LBL_ATTACHMENT_GROUP_PRODUCTS_1",
		      "description": "LBL_ATTACHMENT_GROUP_PRODUCTS_1_DESC",
		      "position": 3,
		      "height": 200
		    }
		  ],
		  "referTables": [],
		  "srcTemplate": "productFormTemplate.html"
	}'),
	(3, 'orders', 'LBL_ORDERS', 'LBL_ORDER', 'LBL_ORDERS_DESCRIPTION', NULL, true, true, NULL, NULL, 
	-- metadata orders
	'{
		"virtualCols": [
			{
				"tableName": "customers",
				"columnName": "fullname",
				"position": 1,
				"tableVisible": true,
				"formVisible": true
			},
			{
				"tableName": "customers",
				"columnName": "surname",
				"position": 2,
				"tableVisible": true,
				"formVisible": true
			}
		]
	}'
	),
	(4, 'order_details', 'LBL_ORDER_DETAILS', 'LBL_ORDER_DETAIL', 'LBL_ORDER_DETAILS', NULL, true, true, NULL, 50, NULL),
	(5, 'categories', 'LBL_CATEGORIES', 'LBL_CATEGORY', 'LBL_CATEGORIES_DESCRIPTION', NULL, true, true, NULL, NULL, '{"customJS": "smartgenCategoryForm.js"}'),
	(6, 'subcategories', 'LBL_SUBCATEGORIES', 'LBL_SUBCATEGORY', 'LBL_SUBCATEGORIES_DESCRIPTION', NULL, true, true, NULL, NULL, NULL);
	
	-- 
  	-- smartgen.sg_metadata_tables (views)
  	--
	INSERT INTO smartgen.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, sql_view, grid_id, metadata) VALUES
	(7, 'vw_subcategories', 'LBL_SUBCATEGORIES', 'LBL_SUBCATEGORY', 'LBL_SUBCATEGORY_DESCRIPTION', NULL, true, true,
	-- sql_view
	'
	 SELECT ROW_NUMBER () OVER (ORDER BY A.category ASC) id, A.*
       FROM ( 
              SELECT c.name as "category", sc.name as "subcategory"
                FROM smartgen.categories c, 
                     smartgen.subcategories sc
                WHERE c.category_id = sc.category_id 
            ) as A
    ' 
    ,  51, '{"gridId": 51}'),
    -- vw_drivers
    (8, 'vw_drivers', 'LBL_DRIVERS', 'LBL_DRIVER', 'LBL_DRIVER_DESCRIPTION', NULL, true, true,
		'
		 SELECT *
	       FROM (VALUES
	           (1, ''Juan'', ''Pérez''),
	           (2, ''María'', ''López''),
	           (3, ''Carlos'', ''García''),
	           (4, ''Ana'', ''Martínez''),
	           (5, ''Luis'', ''Hernández'')
	       ) AS drivers(id, name, surname)
	    ', null, null),
	-- vw_addresses
    (9, 'vw_addreses', 'LBL_ADDRESSES', 'LBL_ADDRESS', 'LBL_ADDRESS_DESCRIPTION', NULL, true, true,
		'
		 SELECT *
	       FROM (VALUES
    	       (1, ''Calle Falsa 123'', 1),
    	       (2, ''Avenida Siempreviva 742'', 1),
    	       (3, ''Calle Luna 45'', 2),
    	       (4, ''Boulevard del Sol 678'', 3),
	           (5, ''Calle Estrella 9'', 3),
    	       (6, ''Avenida Central 456'', 4)
	       ) AS addresses(id, address, driver_id)
	    ', null, null),
	-- vw_brands
	(10, 'vw_brands', 'LBL_BRANDS', 'LBL_BRAND', 'LBL_BRAND_DESCRIPTION', NULL, true, true,
		'
		 SELECT *
	       FROM (VALUES
    	       (1, ''Toyota''),
    	       (2, ''Seat''),
    	       (3, ''Porsche''),
    	       (4, ''Citroën'')
	       ) AS brands(id, name)
	    ', null, null),
	-- vw_cars
    (11, 'vw_cars', 'LBL_CARS', 'LBL_CAR', 'LBL_CAR_DESCRIPTION', NULL, true, true,
		'
		 SELECT *
	       FROM (VALUES
    	       (1, ''Avensis'', 1, 1),
    	       (2, ''Altea'', 1, 2),
    	       (3, ''Leon'', 2, 2),
    	       (4, ''Taycan'', 3, 3),
	           (5, ''C5'', 3, 4),
    	       (6, ''Berlingo'', 3, 4)
	       ) AS cars(id, model, driver_id, brand_id)
	    ', null, null);
	
	-- 
  	-- smartgen.sg_metadata_columns
  	--
	INSERT INTO smartgen.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, needs_translation, metadata, ref_view_column_id) VALUES
	(1, 2, 'product_name', 'LBL_PRODUCT', NULL, true, '{ "badgeInHeader": "mdi mdi-account" }', null),
	(2, 1, 'fullname', 'LBL_FULLNAME', NULL, false, '{ "multiline": 4 }', null),
	(3, 1, 'name', 'LBL_NAME', NULL, false, '{ "refName": true }', null),
	(4, 2, 'subcategory_id', NULL, NULL, false, 
	-- metadata
	'{ 
		"associatedFieldName": "category_id",
		"badgeInHeader": "mdi mdi-cog"
	}', null),
	(5, 4, 'product_id', NULL, NULL, false, '{ "refGroupFieldName": "category_id" }', null),
	(6, 2, 'category_id', NULL, NULL, false, '{ "badgeInHeader": "mdi mdi-cog" }', null),
	-- drivers
	(7, 8, 'id', NULL, NULL, false, null, null),
	(8, 8, 'name', NULL, NULL, false, null, null),
	(9, 8, 'surname', NULL, NULL, false, null, null),
	-- addresses
	(10, 9, 'id', NULL, NULL, false, null, null),
	(11, 9, 'address', NULL, NULL, false, null, null),
	(12, 9, 'driver_id', NULL, NULL, false, null, 7),
	-- brands
	(13, 10, 'id', NULL, NULL, false, null, null),
	(14, 10, 'name', NULL, NULL, false, null, null),
	-- cars
	(15, 11, 'id', NULL, NULL, false, null, null),
	(16, 11, 'model', NULL, NULL, false, null, null),
	(17, 11, 'driver_id', NULL, NULL, false, null, 7),
	(18, 11, 'brand_id', NULL, NULL, false, null, 13);
	
	-- 
  	-- smartgen.sg_metadata_table_triggers
  	--
	INSERT INTO smartgen.sg_metadata_table_triggers (id, sg_metadata_table_id, name, groovy) VALUES
	(1, 3, 'tg_orders', 'config/groovy/backoffice/smartgen/trigger/OrderTrigger.groovy'),
	(2, 4, 'tg_order_details', 'config/groovy/backoffice/smartgen/trigger/OrderDetailTrigger.groovy'),
	(3, 1, 'tg_customers', 'config/groovy/backoffice/smartgen/trigger/CustomerTrigger.groovy');
	
	-- 
  	-- smartgen.sg_metadata_column_fillers
  	--
	INSERT INTO smartgen.sg_metadata_column_fillers (id, sg_metadata_column_id, "name", groovy) VALUES
	(1, 2, 'fl_customers', 'config/groovy/backoffice/smartgen/filler/CustomerFiller.groovy');
	
	-- 
  	-- smartgen.sg_metadata_table_commands
  	--
	INSERT INTO smartgen.sg_metadata_table_commands (id, sg_metadata_table_id, "name", "label", label_description, mdi_icon, require_confirmation, available_in_form, available_in_table, groovy, custom_js) VALUES
	(1, 1, 'cmd1', 'LBL_ACTION_1', 'LBL_ACTION_1', 'mdi mdi-ab-testing', true, true, true, 'config/groovy/backoffice/smartgen/command/CustomerCommand.groovy', null),
	(2, 1, 'cmd2', 'LBL_ACTION_2', 'LBL_ACTION_2', 'mdi mdi-ab-testing', true, true, false, 'config/groovy/backoffice/smartgen/command/CustomerCommandParam.groovy', 'customerCommandParams.js');

	INSERT INTO smartgen.sg_metadata_tasks (id, "name", cron_expression, groovy, params) VALUES
	(1, 'CleanAttachments', '0 0/1 * 1/1 * ? *', 'config/groovy/backoffice/common/task/CleanAttachmentTask.groovy', NULL);
	-- 
  	-- smartgen.sg_i18n_labels
  	--
	INSERT INTO smartgen.sg_i18n_labels (locale_code, "label", "translation", created_at, updated_at) VALUES
	('es_ES', 'LBL_CUSTOMERS', 'clientes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_CUSTOMER', 'cliente', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_PRODUCTS', 'productos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_PRODUCT', 'producto', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_ORDERS', 'pedidos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_ORDER', 'pedido', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_ORDER_DETAILS', 'detalles de pedido', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_ORDER_DETAIL', 'detalle de pedido', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_CATEGORIES', 'categorías', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_CATEGORY', 'categoría', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_CATEGORIES_DETAIL', 'detalles de categoría', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_SUBCATEGORIES', 'subcategorías', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_SUBCATEGORY', 'subcategoría', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_SUBCATEGORIES_DETAIL', 'detalles de subcategoría', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_FULLNAME', 'Nombre completo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_NAME', 'Nombre', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_SURNAME', 'Apellidos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_IMAGE_GROUP_PRODUCTS_1', 'Grupo imágenes 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_IMAGE_GROUP_PRODUCTS_1_DESC', 'Descripción del grupo imágenes 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_ATTACHMENT_GROUP_PRODUCTS_1', 'Grupo de adjuntos 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 'LBL_ATTACHMENT_GROUP_PRODUCTS_1_DESC', 'Descripción del grupos de adjuntos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
/*	
	INSERT INTO smartgen.infractions(infraction_id, fecha, tipo, estado, matricula, operador, radar, carretera, pk, sentido, carril, limite, velocidad_vehiculo, expediente) VALUES
	(nextval('smartgen.infractions_infraction_id_seq'::regclass), CURRENT_TIMESTAMP, 'Radar fijo', 'RECIBIDAS', '0000-BCD', 'operator 1', 'RAD-01', 'AP-7', '130.300', 'C', '0', 120, 130, 'EXP-0001'),
	(nextval('smartgen.infractions_infraction_id_seq'::regclass), CURRENT_TIMESTAMP, 'Radar fijo', 'RECIBIDAS', '0001-BCD', 'operator 1', 'RAD-01', 'AP-7', '130.300', 'C', '0', 120, 135, 'EXP-0002'),
	(nextval('smartgen.infractions_infraction_id_seq'::regclass), CURRENT_TIMESTAMP, 'Radar fijo', 'RECIBIDAS', '0002-BCD', 'operator 1', 'RAD-01', 'AP-7', '130.300', 'C', '0', 120, 137, 'EXP-0002'),
	(nextval('smartgen.infractions_infraction_id_seq'::regclass), CURRENT_TIMESTAMP, 'Radar fijo', 'RECIBIDAS', '0003-BCD', 'operator 1', 'RAD-01', 'AP-7', '130.300', 'C', '0', 120, 138, 'EXP-0002'),
	(nextval('smartgen.infractions_infraction_id_seq'::regclass), CURRENT_TIMESTAMP, 'Radar fijo', 'RECIBIDAS', '0004-BCD', 'operator 1', 'RAD-01', 'AP-7', '130.300', 'C', '0', 120, 139, 'EXP-0002'),
	(nextval('smartgen.infractions_infraction_id_seq'::regclass), CURRENT_TIMESTAMP, 'Radar fijo', 'RECIBIDAS', '0005-BCD', 'operator 1', 'RAD-01', 'AP-7', '130.300', 'C', '0', 120, 129, 'EXP-0002'),
	(nextval('smartgen.infractions_infraction_id_seq'::regclass), CURRENT_TIMESTAMP, 'Radar fijo', 'RECIBIDAS', '0006-BCD', 'operator 1', 'RAD-01', 'AP-7', '130.300', 'C', '0', 120, 130, 'EXP-0002'),
	(nextval('smartgen.infractions_infraction_id_seq'::regclass), CURRENT_TIMESTAMP, 'Radar fijo', 'RECIBIDAS', '0007-BCD', 'operator 1', 'RAD-01', 'AP-7', '130.300', 'C', '0', 120, 134, 'EXP-0002'),
	(nextval('smartgen.infractions_infraction_id_seq'::regclass), CURRENT_TIMESTAMP, 'Radar fijo', 'RECIBIDAS', '0008-BCD', 'operator 1', 'RAD-01', 'AP-7', '130.300', 'C', '0', 120, 135, 'EXP-0002');
*/
/*	
	-- Insertar tipos de entidades de Waze
	INSERT INTO smartgen.ext_entities (tipo_entidad, tipo_info, descripcion, localizacion) 
	VALUES 
	('Waze', 'Incidente', 'Accidente de tráfico', 'Carretera A1, Km 15'),
	('Waze', 'Incidente', 'Obras en la vía', 'Carretera B2, Km 30'),
	('Waze', 'Incidente', 'Vehículo detenido', 'Autopista C3, Km 45'),
	('Waze', 'Clima', 'Lluvia intensa', 'Carretera D4, Km 60');
	
	-- Insertar tipos de entidades de Google
	INSERT INTO smartgen.ext_entities (tipo_entidad, tipo_info, descripcion, localizacion) 
	VALUES 
	('Google', 'Incidente', 'Tráfico lento', 'Ciudad Z, Avenida Central'),
	('Google', 'Incidente', 'Carretera cerrada por evento', 'Ciudad X, Calle Mayor'),
	('Google', 'Clima', 'Niebla densa', 'Ciudad Y, Puente Norte'),
	('Google', 'Incidente', 'Desvío por obras', 'Autopista M5, Km 22');
	
	-- Insertar tipos de entidades de TomTom
	INSERT INTO smartgen.ext_entities (tipo_entidad, tipo_info, descripcion, localizacion) 
	VALUES 
	('TomTom', 'Incidente', 'Manifestación en la vía', 'Ciudad A, Plaza Principal'),
	('TomTom', 'Clima', 'Tormenta eléctrica', 'Región B, Carretera L7'),
	('TomTom', 'Incidente', 'Carretera con hielo', 'Autopista N6, Km 32'),
	('TomTom', 'Incidente', 'Obras de mantenimiento', 'Ruta Nacional O1, Km 18');
	
	-- Insertar tipos de entidades de DGT
	INSERT INTO smartgen.ext_entities (tipo_entidad, tipo_info, descripcion, localizacion) 
	VALUES 
	('DGT', 'Incidente', 'Camión averiado', 'Ruta Nacional N1, Km 10'),
	('DGT', 'Incidente', 'Derrumbe', 'Carretera de montaña, Km 12'),
	('DGT', 'Clima', 'Nieve intensa', 'Autopista P2, Km 25'),
	('DGT', 'Incidente', 'Colisión múltiple', 'Autopista Q3, Km 40');
*/

  END IF;
END $$;

-- 
-- Re-calculate sequences
--
-- DO $$
-- DECLARE
--     tabla_record RECORD;
--     nombre_secuencia TEXT;
--     nuevo_valor INT;
-- BEGIN
-- 	
-- 	IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'smartgen') THEN
-- 	
--	    Recorrer todas las tablas del esquema
-- 	    FOR tabla_record IN (
-- 	        SELECT table_name
-- 	        FROM information_schema.tables
-- 	        WHERE table_schema = 'smartgen' -- Reemplaza 'nombre_esquema' con el nombre de tu esquema
-- 	    ) LOOP
-- 	        nombre_secuencia := tabla_record.table_name || '_id_seq'; -- Suponiendo que las secuencias siguen la convención de nombre
-- 	        EXECUTE 'SELECT COALESCE(MAX(id), 0) + 1 FROM smartgen.' || tabla_record.table_name INTO nuevo_valor;
-- 	        EXECUTE 'SELECT setval(''smartgen.' || nombre_secuencia || ''', ' || nuevo_valor || ', false)';
-- 	    END LOOP;
-- 	    
-- 	END IF;
-- END $$;