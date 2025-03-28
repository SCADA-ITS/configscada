---------------------------------------------------------
--
-- TABLES FOR CONF SCHEMA
--
---------------------------------------------------------

-- 
-- Table: conf.cctv_scenarios
-- Description: Definition of cctv scenarios
-- Scope: conf

CREATE TABLE conf.cctv_scenarios (
	scenario_id int8 NOT NULL,
	alias varchar NOT NULL,
	description varchar NULL,
	n_rows int4 NOT NULL,
	n_columns int4 NOT NULL,
	enabled bool NULL,
	visible bool NULL,
	CONSTRAINT pk_cctv_scenarios PRIMARY KEY (scenario_id)
);

ALTER TABLE conf.cctv_scenarios SET TABLESPACE tbs_controltrafico_conf;


-- 
-- Table: conf.cctv_scenario_elements
-- Description: 
-- Scope: conf		
CREATE TABLE conf.cctv_scenario_elements (
		scenario_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		n_order int4 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_cctv_scenario_elements PRIMARY KEY (scenario_id, element_type_id, element_id)
	);
	
ALTER TABLE conf.cctv_scenario_elements SET TABLESPACE tbs_controltrafico_conf;

ALTER TABLE conf.cctv_scenario_elements ADD CONSTRAINT fk_cctv_senario_elements_cctv_scenarios FOREIGN KEY (scenario_id) REFERENCES conf.cctv_scenarios(scenario_id);
ALTER TABLE conf.cctv_scenario_elements ADD CONSTRAINT fk_cctv_senario_elements_element FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);


-- 
-- Table: conf.cctv_scenario_nodes
-- Description: 
-- Scope: conf		
CREATE TABLE conf.cctv_scenario_nodes (
		node_id int8 NOT NULL,
		alias varchar NOT NULL,
		description varchar NULL,
		scenario_id int8  NULL,
		management_area_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_cctv_scenario_nodes PRIMARY KEY (node_id)
	);
	
ALTER TABLE conf.cctv_scenario_nodes SET TABLESPACE tbs_controltrafico_conf;

ALTER TABLE conf.cctv_scenario_nodes ADD CONSTRAINT fk_cctv_senario_nodes_cctv_scenarios FOREIGN KEY (scenario_id) REFERENCES conf.cctv_scenarios(scenario_id);
ALTER TABLE conf.cctv_scenario_nodes ADD CONSTRAINT fk_cctv_senario_nodes_management_area FOREIGN KEY (management_area_id) REFERENCES static.management_areas(management_area_id);


-- 
-- Table: conf.cctv_scenario_node_hierarchies
-- Description: 
-- Scope: conf		
CREATE TABLE conf.cctv_scenario_node_hierarchies (
		node_parent_id int8 NOT NULL,
		node_child_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_cctv_scenario_node_hierarchies PRIMARY KEY (node_parent_id, node_child_id)
	);
	
ALTER TABLE conf.cctv_scenario_node_hierarchies SET TABLESPACE tbs_controltrafico_conf;

ALTER TABLE conf.cctv_scenario_node_hierarchies ADD CONSTRAINT fk_cctv_scenario_node_hierarchies_parent FOREIGN KEY (node_parent_id) REFERENCES conf.cctv_scenario_nodes(node_id);
ALTER TABLE conf.cctv_scenario_node_hierarchies ADD CONSTRAINT fk_cctv_scenario_node_hierarchies_child FOREIGN KEY (node_child_id) REFERENCES conf.cctv_scenario_nodes(node_id);



