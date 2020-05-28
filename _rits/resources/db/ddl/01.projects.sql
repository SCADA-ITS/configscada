---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.project_types
-- Description: Type of projects
-- Scope: ui
--
	CREATE TABLE ui.project_types (
		project_type_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_project_types PRIMARY KEY (project_type_id)
	);
	
	ALTER TABLE ui.project_types SET TABLESPACE tbl_ui;

-- 
-- Table: ui.projects
-- Description: Projects table
-- Scope: ui
--
	CREATE TABLE ui.projects (
		project_type_id int8 NOT NULL,
		project_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_projects PRIMARY KEY (project_type_id, project_id)
	);
	
	CREATE INDEX idx_projects_project_type_id ON ui.projects USING btree (project_type_id);

	ALTER TABLE ui.projects ADD CONSTRAINT fk_projects_project_types FOREIGN KEY (project_type_id) REFERENCES ui.project_types(project_type_id);
	
	ALTER TABLE ui.projects SET TABLESPACE tbl_ui;

-- 
-- Table: ui.project_params
-- Description: Project params table
-- Scope: ui
--
	CREATE TABLE ui.project_params (
		project_type_id int8 NOT NULL,
		project_param_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_project_params PRIMARY KEY (project_type_id, project_param_id)
	);
	
	CREATE INDEX idx_project_params_project_type_id ON ui.project_params USING btree (project_type_id);

	ALTER TABLE ui.project_params ADD CONSTRAINT fk_project_params_project_types FOREIGN KEY (project_type_id) REFERENCES ui.project_types(project_type_id);
	
	ALTER TABLE ui.project_params SET TABLESPACE tbl_ui;

-- 
-- Table: ui.project_values
-- Description: Project values table
-- Scope: ui
--
	CREATE TABLE ui.project_values (
		project_type_id int8 NOT NULL,
		project_id int8 NOT NULL,
		project_param_id int8 NOT NULL,
		value varchar NOT null,
		CONSTRAINT pk_project_values PRIMARY KEY (project_type_id, project_param_id, project_id)
	);
	
	CREATE INDEX idx_project_values_projects ON ui.project_values USING btree (project_type_id, project_id);
	CREATE INDEX idx_project_values_project_params ON ui.project_values USING btree (project_type_id, project_param_id);

	ALTER TABLE ui.project_values ADD CONSTRAINT fk_project_values_projects FOREIGN KEY (project_type_id, project_id) REFERENCES ui.projects(project_type_id, project_id);
	ALTER TABLE ui.project_values ADD CONSTRAINT fk_project_values_project_params FOREIGN KEY (project_type_id, project_param_id) REFERENCES ui.project_params(project_type_id, project_param_id);
	
	ALTER TABLE ui.project_values SET TABLESPACE tbl_ui;
	

-- 
-- Table: ui.profiles
-- Description: Profiles table
-- Scope: ui
--
	CREATE TABLE ui.profiles (
		profile_id int8 NOT NULL,
		user_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_profiles PRIMARY KEY (profile_id, user_id)
	);
	
	CREATE INDEX idx_profiles_users ON ui.profiles USING btree (user_id);

	ALTER TABLE ui.profiles ADD CONSTRAINT fk_profiles_users FOREIGN KEY (user_id) REFERENCES conf.users(user_id);
		
	ALTER TABLE ui.profiles SET TABLESPACE tbl_ui;

-- 
-- Table: ui.project_profile_params
-- Description: Project param by profile table
-- Scope: ui
--
	CREATE TABLE ui.project_profile_params (
		project_type_id int8 NOT NULL,
		project_profile_param_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_project_profile_params PRIMARY KEY (project_type_id, project_profile_param_id)
	);
	
	CREATE INDEX idx_project_profile_params_project_types ON ui.project_profile_params USING btree (project_type_id);

	ALTER TABLE ui.project_profile_params ADD CONSTRAINT fk_project_profile_params_project_types FOREIGN KEY (project_type_id) REFERENCES ui.project_types(project_type_id);
		
	ALTER TABLE ui.project_profile_params SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.project_profile_values
-- Description: Project param values by profile table
-- Scope: ui
--
	CREATE TABLE ui.project_profile_values (
		project_type_id int8 NOT NULL,
		project_id int8 NOT NULL,
		project_profile_param_id int8 NOT NULL,
		profile_id int8 NOT NULL,
		user_id int8 NOT NULL,
		value varchar NULL,
		CONSTRAINT pk_project_profile_values PRIMARY KEY (project_type_id, project_id, project_profile_param_id, profile_id)
	);
	
	CREATE INDEX idx_project_profile_values_projects ON ui.project_profile_values USING btree (project_type_id, project_id);
	CREATE INDEX idx_project_profile_values_project_profile_params ON ui.project_profile_values USING btree (project_type_id, project_profile_param_id);

	ALTER TABLE ui.project_profile_values ADD CONSTRAINT fk_project_profile_values_projects FOREIGN KEY (project_type_id, project_id) REFERENCES ui.projects(project_type_id, project_id);
	ALTER TABLE ui.project_profile_values ADD CONSTRAINT fk_project_profile_values_project_profile_params FOREIGN KEY (project_type_id, project_profile_param_id) REFERENCES ui.project_profile_params(project_type_id, project_profile_param_id);
	ALTER TABLE ui.project_profile_values ADD CONSTRAINT fk_project_profile_values_profiles FOREIGN KEY (profile_id, user_id) REFERENCES ui.profiles(profile_id, user_id);
		
	ALTER TABLE ui.project_profile_values SET TABLESPACE tbl_ui;
	
