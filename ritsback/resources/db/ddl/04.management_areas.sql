
-- 
-- Table: conf.management_area_users
-- Descripción: 
-- Scope: conf
--
	CREATE TABLE conf.management_area_users (
		management_area_id int8 NOT NULL,
		user_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_management_area_users PRIMARY KEY (management_area_id, user_id)
	);

	ALTER TABLE conf.management_area_users SET TABLESPACE tbl_conf;

	CREATE INDEX idx_management_area_users ON conf.management_area_users USING btree (user_id);

	ALTER TABLE conf.management_area_users ADD CONSTRAINT fk_management_area_users_area FOREIGN KEY (management_area_id) REFERENCES static.management_areas(management_area_id);
	ALTER TABLE conf.management_area_users ADD CONSTRAINT fk_management_area_users_user FOREIGN KEY (user_id) REFERENCES conf.users(user_id);
	
	
-- 
-- Table: conf.management_area_elements
-- Descripción: 
-- Scope: conf
--
	CREATE TABLE conf.management_area_elements (
		management_area_id int8 NOT NULL,
		element_type_id int8 NOT NULL,
		element_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_management_area_elements PRIMARY KEY (management_area_id, element_type_id, element_id)
	);

	ALTER TABLE conf.management_area_elements SET TABLESPACE tbl_conf;

	CREATE INDEX idx_management_area_elements ON conf.management_area_elements USING btree (element_type_id, element_id);

	ALTER TABLE conf.management_area_elements ADD CONSTRAINT fk_management_area_elements_area FOREIGN KEY (management_area_id) REFERENCES static.management_areas(management_area_id);
	ALTER TABLE conf.management_area_elements ADD CONSTRAINT fk_management_area_elements_element FOREIGN KEY (element_type_id, element_id) REFERENCES conf.elements(element_type_id, element_id);
	
	
	