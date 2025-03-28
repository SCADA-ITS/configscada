---------------------------------------------------------
--
-- TABLES FOR MASTER SCHEMA
--
---------------------------------------------------------

-- 
-- Table: master.pm_queues
-- Descripción: Colas de prioridades 
-- Scope: master
--

CREATE TABLE master.pm_queues (
	queue_id int8 NOT NULL,
	alias varchar NULL,
	description varchar NULL,
	label_alias varchar NULL,
	label_description varchar NULL,
	enabled bool NULL,
	visible bool NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT pk_pm_queues PRIMARY KEY (queue_id)
);

ALTER TABLE master.pm_queues SET TABLESPACE tbs_controltrafico_master;

-- 
-- Table: master.pm_queues
-- Descripción: Priroridades de cada cola
-- Scope: master
--		

CREATE TABLE master.pm_queue_priorities (
	queue_id int8 NOT NULL,
	priority_id int8 NOT NULL,
	alias varchar NULL,
	description varchar NULL,
	label_alias varchar NULL,
	label_description varchar NULL,
	priority int4 NOT NULL,
	default_priority bool NULL,
	plan_priority bool NULL,
	incident_priority bool NULL,
	travel_time_priority bool NULL,
	check_conflict bool NULL,
	enabled bool NULL,
	visible bool NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT pk_pm_queue_priorities PRIMARY KEY (queue_id, priority_id)
);

ALTER TABLE master.pm_queue_priorities ADD CONSTRAINT fk_pm_queue_priorities_pm_queues FOREIGN KEY (queue_id) REFERENCES master.pm_queues(queue_id);
ALTER TABLE master.pm_queue_priorities SET TABLESPACE tbs_controltrafico_master;

-- 
-- Table: master.pm_queues
-- Descripción: Colas asociadas a cada tipo de equipos
-- Scope: master
--

CREATE TABLE master.pm_element_type_queues (
	element_type_id int8 NOT NULL,
	queue_id int8 NOT NULL,
	enabled bool NULL,
	visible bool NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	UNIQUE (element_type_id),
	CONSTRAINT pk_pm_element_type_queues PRIMARY KEY (element_type_id, queue_id)
);

ALTER TABLE master.pm_element_type_queues ADD CONSTRAINT fk_pm_element_type_queues_element_type FOREIGN KEY (element_type_id) REFERENCES master.element_types(element_type_id);
ALTER TABLE master.pm_element_type_queues ADD CONSTRAINT fk_pm_element_type_queues_pm_queues FOREIGN KEY (queue_id) REFERENCES master.pm_queues(queue_id);
ALTER TABLE master.pm_element_type_queues SET TABLESPACE tbs_controltrafico_master;


-- 
-- Table: conf.pm_disabled_queue_priorities
-- Descripción: Prioridades deshabilitadas para cada equipo
-- Scope: conf
--


CREATE TABLE conf.pm_disabled_queue_priorities (
	queue_id int8 NOT NULL,
	priority_id int8 NOT NULL,
	element_type_id int8 NOT NULL,
	element_id int8 NOT NULL,
	enabled bool NULL,
	visible bool NULL,
	created_at timestamptz NOT NULL,
	updated_at timestamptz NOT NULL,
	CONSTRAINT pk_pm_disabled_queue_priorities PRIMARY KEY (queue_id, priority_id, element_type_id, element_id)
);

ALTER TABLE conf.pm_disabled_queue_priorities ADD CONSTRAINT fk_pm_disabled_queue_priorities_element FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
ALTER TABLE conf.pm_disabled_queue_priorities ADD CONSTRAINT fk_pm_disabled_queue_priorities_pm_queue_priorities FOREIGN KEY (queue_id, priority_id) REFERENCES master.pm_queue_priorities(queue_id, priority_id);
ALTER TABLE conf.pm_disabled_queue_priorities SET TABLESPACE tbs_controltrafico_conf;

