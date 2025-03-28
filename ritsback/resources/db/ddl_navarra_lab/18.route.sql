-- 
-- Table: conf.routes
-- Scope: conf
-- 
	
		CREATE TABLE conf.routes (
		route_id int8 NOT NULL,
		alias varchar NOT NULL,
		description varchar NULL,
		coordinates varchar NULL,
		auto_route boolean NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_route PRIMARY KEY (route_id)
	);
	
	ALTER TABLE conf.routes SET TABLESPACE tbs_controltrafico_conf;