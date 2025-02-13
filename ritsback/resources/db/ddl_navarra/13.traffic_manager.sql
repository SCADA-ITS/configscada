--DROP TABLE IF EXISTS static.los_softened_arrays;
--DROP TABLE IF EXISTS static.los_arrays;
--DROP TABLE IF EXISTS static.level_of_services;


---------------------------------------------------------
--
-- TABLES FOR STATIC SCHEMA
--
---------------------------------------------------------

-- 
-- Table: static.level_of_services
-- Descripción: Niveles de servicio 
-- Scope: static
--
	CREATE TABLE static.level_of_services (
		level_of_service_id int8 NOT NULL,
		los int4 NOT NULL UNIQUE,
  		alias varchar NOT NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		colour varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_level_of_services PRIMARY KEY (level_of_service_id)
	);

	ALTER TABLE static.level_of_services SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.los_arrays
-- Descripción: Matriz del Nivel de Servicio 
-- Scope: static
--
	CREATE TABLE static.los_arrays (
		los_threshold_speed int4 NOT NULL,
		los_threshold_occupancy int4 NOT NULL,
		level_of_service_id int8 NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_los_matrices PRIMARY KEY (los_threshold_speed, los_threshold_occupancy)
	);
	
	ALTER TABLE static.los_arrays ADD CONSTRAINT fk_los_arrays_level_of_service_id FOREIGN KEY (level_of_service_id) REFERENCES static.level_of_services(level_of_service_id);
	
	ALTER TABLE static.los_arrays SET TABLESPACE tbs_controltrafico_static;

-- 
-- Table: static.los_softened_arrays
-- Descripción: Matriz de suavizado
-- Scope: static
--
	CREATE TABLE static.los_softened_arrays (
		los_softened_array_id int8 NOT NULL,
		init_los_id int8  NULL,
		end_los_id int8  NULL,
		count_softened int4 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_los_softened_arrays PRIMARY KEY (los_softened_array_id),
		CONSTRAINT un_los_softened_arrays UNIQUE (init_los_id, end_los_id)
	);
	
	ALTER TABLE static.los_softened_arrays ADD CONSTRAINT fk_los_softened_arrays_init_los_id FOREIGN KEY (init_los_id) REFERENCES static.level_of_services(level_of_service_id);
	ALTER TABLE static.los_softened_arrays ADD CONSTRAINT fk_los_softened_arrays_end_los_id FOREIGN KEY (end_los_id) REFERENCES static.level_of_services(level_of_service_id);

	ALTER TABLE static.los_softened_arrays SET TABLESPACE tbs_controltrafico_static;
