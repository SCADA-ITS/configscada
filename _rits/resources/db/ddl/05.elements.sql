---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.widgets
-- Description: Widgets library available
-- Scope: ui
--
	CREATE TABLE ui.widgets (
		widget_id int8 NOT NULL,
		default_data_type_id int8 NULL,
		name varchar NOT NULL,
		description varchar NULL,
		CONSTRAINT pk_widgets PRIMARY KEY (widget_id)
	);
	
	CREATE INDEX idx_widgets_data_types ON ui.widgets USING btree (default_data_type_id);

	ALTER TABLE ui.widgets ADD CONSTRAINT fk_widgets_data_types FOREIGN KEY (default_data_type_id) REFERENCES master.data_types(data_type_id);
		
	ALTER TABLE ui.widgets SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.widget_params
-- Description: Available params for widget
-- Scope: ui
--
	CREATE TABLE ui.widget_params (
		widget_id int8 NULL,
		widget_param_id int8 NULL,
		name varchar NOT NULL,
		description varchar NULL,
		CONSTRAINT pk_widget_params PRIMARY KEY (widget_id, widget_param_id)
	);

	CREATE INDEX idx_widget_params_widgets ON ui.widget_params USING btree (widget_id);
	
	ALTER TABLE ui.widget_params ADD CONSTRAINT fk_widget_params_widgets FOREIGN KEY (widget_id) REFERENCES ui.widgets(widget_id);
	
	ALTER TABLE ui.widget_params SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.element_param_widget_values
-- Description: Values to configure associated widget with element
-- Scope: ui
--
	CREATE TABLE ui.element_param_widget_values (
		element_type_id int8 NOT NULL,
		param_type_id int8 NOT NULL,
		element_type_param_id int8 NOT NULL,
		widget_id int8 NULL,
		widget_param_id int8 NULL,
		value varchar NULL,
		CONSTRAINT pk_element_param_widgets PRIMARY KEY (element_type_id, param_type_id, element_type_param_id)
	);
	
	CREATE INDEX idx_element_param_widgets_widget_params ON ui.element_param_widget_values USING btree (widget_id, widget_param_id);

	ALTER TABLE ui.element_param_widget_values ADD CONSTRAINT fk_element_param_widget_values_element_type_params FOREIGN KEY (element_type_id, param_type_id, element_type_param_id) REFERENCES master.element_type_params(element_type_id, param_type_id, element_type_param_id);
	ALTER TABLE ui.element_param_widget_values ADD CONSTRAINT fk_element_param_widget_values_widget_params FOREIGN KEY (widget_id, widget_param_id) REFERENCES ui.widget_params(widget_id, widget_param_id);
		
	ALTER TABLE ui.element_param_widget_values SET TABLESPACE tbl_ui;
