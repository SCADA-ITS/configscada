---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------
	
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
-- Table: ui.modules
-- Description: Module param by profile table
-- Scope: ui
--
	CREATE TABLE ui.module_profile_params (
		module_type_id int8 NOT NULL,
		module_profile_param_id int8 NOT NULL,
		name varchar NOT NULL,
		description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		CONSTRAINT pk_module_profile_params PRIMARY KEY (module_type_id, module_profile_param_id)
	);
	
	CREATE INDEX idx_module_profile_params_module_types ON ui.module_profile_params USING btree (module_type_id);

	ALTER TABLE ui.module_profile_params ADD CONSTRAINT fk_module_profile_params_module_types FOREIGN KEY (module_type_id) REFERENCES ui.module_types(module_type_id);
		
	ALTER TABLE ui.module_profile_params SET TABLESPACE tbl_ui;
	
-- 
-- Table: ui.modules
-- Description: Module param by profile table
-- Scope: ui
--
	CREATE TABLE ui.module_profile_values (
		module_type_id int8 NOT NULL,
		module_id int8 NOT NULL,
		module_profile_param_id int8 NOT NULL,
		profile_id int8 NOT NULL,
		user_id int8 NOT NULL,
		value varchar NULL,
		CONSTRAINT pk_module_profile_values PRIMARY KEY (module_type_id, module_id, module_profile_param_id, profile_id)
	);
	
	CREATE INDEX idx_module_profile_values_modules ON ui.module_profile_values USING btree (module_type_id, module_id);
	CREATE INDEX idx_module_profile_values_module_profile_params ON ui.module_profile_values USING btree (module_type_id, module_profile_param_id);

	ALTER TABLE ui.module_profile_values ADD CONSTRAINT fk_module_profile_values_modules FOREIGN KEY (module_type_id, module_id) REFERENCES ui.modules(module_type_id, module_id);
	ALTER TABLE ui.module_profile_values ADD CONSTRAINT fk_module_profile_values_module_profile_params FOREIGN KEY (module_type_id, module_profile_param_id) REFERENCES ui.module_profile_params(module_type_id, module_profile_param_id);
	ALTER TABLE ui.module_profile_values ADD CONSTRAINT fk_module_profile_values_profiles FOREIGN KEY (profile_id, user_id) REFERENCES ui.profiles(profile_id, user_id);
		
	ALTER TABLE ui.module_profile_values SET TABLESPACE tbl_ui;
	
