---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.equip_viewers
-- Description: Definition of equip_viewers
-- Scope: ui
--
	CREATE TABLE ui.equip_viewers (
		id varchar NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		show_info_tab boolean NOT NULL,
		show_childs boolean NOT NULL,
		show_actions boolean NOT NULL,
		initial_tab int NULL,
		measure_width int NULL,
		measure_max_rows int NULL,
		measure_only boolean NULL,
		graphic_icon_id int NULL,
		no_show_measures boolean NULL,
		info_tab_mode int NULL,
		no_show_properties boolean NULL,
		show_chart boolean NULL,
		show_table boolean NULL,
		show_historical_chart boolean NULL,
		chart_interval_min int NULL,
		chart_interval_time_min int NULL,
		CONSTRAINT pk_equip_viewers PRIMARY KEY (id)
	);
	
	CREATE INDEX idx_equip_viewers_vo_entities ON ui.equip_viewers USING btree (id);
	CREATE INDEX idx_equip_viewers_graphic_icons ON ui.equip_viewers USING btree (graphic_icon_id);
	
	ALTER TABLE ui.equip_viewers ADD CONSTRAINT fk_equip_viewers_vo_entities FOREIGN KEY (id) REFERENCES ui.vo_entities(id);
	ALTER TABLE ui.equip_viewers ADD CONSTRAINT fk_equip_viewers_graphic_icons FOREIGN KEY (graphic_icon_id) REFERENCES ui.graphic_icons(id);
	
	ALTER TABLE ui.equip_viewers SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.equip_viewer_configs
-- Description: Definition of equip_viewer_configs
-- Scope: ui
--
	CREATE TABLE ui.equip_viewer_configs (
		id serial primary key,
		equip_viewer_id varchar NOT NULL,
		vo_entities_id varchar NOT NULL,
		position int NOT NULL,
		visible boolean NOT NULL
	);
	
	CREATE INDEX idx_equip_viewer_configs_equip_viewers ON ui.equip_viewer_configs USING btree (equip_viewer_id);
	CREATE INDEX idx_equip_viewer_configs_vo_entities ON ui.equip_viewer_configs USING btree (vo_entities_id);
	
	ALTER TABLE ui.equip_viewer_configs ADD CONSTRAINT fk_equip_viewer_configs_equip_viewers FOREIGN KEY (equip_viewer_id) REFERENCES ui.equip_viewers(id);
	ALTER TABLE ui.equip_viewer_configs ADD CONSTRAINT fk_equip_viewer_configs_vo_entities FOREIGN KEY (vo_entities_id) REFERENCES ui.vo_entities(id);
	
	ALTER TABLE ui.equip_viewer_configs SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.equip_viewer_tabs
-- Description: Definition equip_viewer_tabs
-- Scope: ui
--
	CREATE TABLE ui.equip_viewer_tabs (
		id serial primary key,
		equip_viewer_id varchar NOT NULL,
		module_action_id int NOT NULL,
		alias varchar NOT NULL,
		description varchar NULL,
		label varchar NULL,
		icon varchar NULL,
		position int NOT NULL,
		visible boolean NOT NULL
	);
	
	CREATE INDEX idx_equip_viewer_tabs_equip_viewers ON ui.equip_viewer_tabs USING btree (equip_viewer_id);
	CREATE INDEX idx_equip_viewer_tabs_module_actions ON ui.equip_viewer_tabs USING btree (module_action_id);
	
	ALTER TABLE ui.equip_viewer_tabs ADD CONSTRAINT fk_equip_viewer_tabs_equip_viewers FOREIGN KEY (equip_viewer_id) REFERENCES ui.equip_viewers(id);
	ALTER TABLE ui.equip_viewer_tabs ADD CONSTRAINT fk_equip_viewer_tabs_module_actions FOREIGN KEY (module_action_id) REFERENCES ui.module_actions(id);
	
	ALTER TABLE ui.equip_viewer_tabs SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.equip_viewer_grp_measures
-- Description: Definition equip_viewer_grp_measures
-- Scope: ui
--
	CREATE TABLE ui.equip_viewer_grp_measures (
		id serial primary key,
		equip_viewer_id varchar NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		label varchar NOT NULL,
		icon varchar NULL,
		position int NOT NULL,
		visible boolean NOT NULL
	);
	
	CREATE INDEX idx_equip_viewer_grp_measures_equip_viewers ON ui.equip_viewer_grp_measures USING btree (equip_viewer_id);
	
	ALTER TABLE ui.equip_viewer_grp_measures ADD CONSTRAINT fk_equip_viewer_grp_measures_equip_viewers FOREIGN KEY (equip_viewer_id) REFERENCES ui.equip_viewers(id);
	
	ALTER TABLE ui.equip_viewer_grp_measures SET TABLESPACE tbl_ui;

-- 
-- Table: ui.equip_viewer_measures
-- Description: Definition equip_viewer_measures
-- Scope: ui
--
	CREATE TABLE ui.equip_viewer_measures (
		id serial primary key,
		equip_viewer_grp_measure_id int NOT NULL,
		vo_entities_id varchar NOT NULL,
		position int NOT NULL,
		visible boolean NOT NULL
	);
	
	CREATE INDEX idx_equip_viewer_measures_equip_viewer_grp_measures ON ui.equip_viewer_measures USING btree (equip_viewer_grp_measure_id);
	CREATE INDEX idx_equip_viewer_measures_vo_entities ON ui.equip_viewer_measures USING btree (vo_entities_id);
	
	ALTER TABLE ui.equip_viewer_measures ADD CONSTRAINT fk_equip_viewer_measures_equip_viewer_grp_measures FOREIGN KEY (equip_viewer_grp_measure_id) REFERENCES ui.equip_viewer_grp_measures(id);
	ALTER TABLE ui.equip_viewer_measures ADD CONSTRAINT fk_equip_viewer_measures_vo_entities FOREIGN KEY (vo_entities_id) REFERENCES ui.vo_entities(id);
	
	ALTER TABLE ui.equip_viewer_measures SET TABLESPACE tbl_ui;