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
	INSERT INTO smartgen.sg_metadata_tables (id, name, label, label_singular, label_description, mdi_icon, support_images, support_attachments, metadata) VALUES
	(1, 'customers', 'LBL_CUSTOMERS', 'LBL_CUSTOMER', 'LBL_CUSTOMERS_DESCRIPTION', NULL, true, true, NULL),
	(2, 'products', 'LBL_PRODUCTS', 'LBL_PRODUCT', 'LBL_PRODUCTS_DESCRIPTION', NULL, true, true, 
	-- metadata
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
	(3, 'orders', 'LBL_ORDERS', 'LBL_ORDER', 'LBL_ORDERS_DESCRIPTION', NULL, true, true, NULL),
	(4, 'order_details', 'LBL_ORDER_DETAILS', 'LBL_ORDER_DETAIL', 'LBL_ORDER_DETAILS', NULL, true, true, '{"gridId": 50}'),
	(5, 'categories', 'LBL_CATEGORIES', 'LBL_CATEGORY', 'LBL_CATEGORIES_DESCRIPTION', NULL, true, true, '{"customJS": "smartgenCategoryForm.js"}'),
	(6, 'subcategories', 'LBL_SUBCATEGORIES', 'LBL_SUBCATEGORY', 'LBL_SUBCATEGORIES_DESCRIPTION', NULL, true, true, NULL);
	
	-- 
  	-- smartgen.sg_metadata_columns
  	--
	INSERT INTO smartgen.sg_metadata_columns (id, sg_metadata_table_id, column_name, "label", label_description, mdi_icon, needs_translation, metadata) VALUES
	(1, 2, 'product_name', 'LBL_PRODUCT', NULL, NULL, true, '{ "badgeInHeader": "mdi mdi-account" }'),
	(2, 1, 'fullname', 'LBL_FULLNAME', NULL, NULL, false, '{ "multiline": 4 }'),
	(3, 1, 'name', 'LBL_NAME', NULL, NULL, false, '{ "refName": true }'),
	(4, 2, 'subcategory_id', NULL, NULL, NULL, false, 
	-- metadata
	'{ 
		"associatedFieldName": "category_id",
		"badgeInHeader": "mdi mdi-cog"
	}'),
	(5, 4, 'product_id', NULL, NULL, NULL, false, '{ "refGroupFieldName": "category_id" }'),
	(6, 2, 'category_id', NULL, NULL, NULL, false, '{ "badgeInHeader": "mdi mdi-cog" }');
	
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
	INSERT INTO smartgen.sg_metadata_table_commands (id, sg_metadata_table_id, "name", "label", label_description, mdi_icon, require_confirmation, available_in_form, available_in_table, groovy) VALUES
	(1, 1, 'cmd1', 'LBL_ACTION_1', 'LBL_ACTION_1', 'mdi mdi-ab-testing', true, true, true, 'config/groovy/backoffice/smartgen/command/CustomerCommand.groovy');

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