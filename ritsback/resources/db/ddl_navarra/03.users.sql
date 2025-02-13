--DROP TABLE IF EXISTS conf.user_permissions;
--DROP TABLE IF EXISTS conf.users;
--DROP TABLE IF EXISTS conf.role_permissions;
--DROP TABLE IF EXISTS conf.roles;
--DROP TABLE IF EXISTS conf.system_param_values;
--DROP TABLE IF EXISTS conf.roles_users;
--DROP TABLE IF EXISTS master.system_permissions;
--DROP TABLE IF EXISTS master.system_params;
---------------------------------------------------------
--
-- TABLES FOR MASTER SCHEMA
--
---------------------------------------------------------

-- 
-- Table: master.system_params
-- Descripción: Parámetros globales del sistema
-- Scope: master
--
	CREATE TABLE master.system_params (
		system_param_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_system_params PRIMARY KEY (system_param_id)
	);

	ALTER TABLE master.system_params SET TABLESPACE tbs_controltrafico_master;
	
-- 
-- Table: master.system_permissions
-- Descripción: Permisos globales del sistema
-- Scope: master
--
	CREATE TABLE master.system_permissions (
		system_permission_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		label_alias varchar NULL,
		label_description varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_system_permissions PRIMARY KEY (system_permission_id)
	);
	
	ALTER TABLE master.system_permissions SET TABLESPACE tbs_controltrafico_master;
	
---------------------------------------------------------
--
-- TABLES FOR CONF SCHEMA
--
---------------------------------------------------------

-- 
-- Table: conf.system_param_values
-- Descripción: Valores de parámetros del sistema
-- Scope: conf
--
	CREATE TABLE conf.system_param_values (
		system_param_id int8 NOT NULL,
		value varchar NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_system_param_values PRIMARY KEY (system_param_id)
	);

	ALTER TABLE conf.system_param_values ADD CONSTRAINT fk_system_param_values_system_param FOREIGN KEY (system_param_id) REFERENCES master.system_params(system_param_id);
	
	ALTER TABLE conf.system_param_values SET TABLESPACE tbs_controltrafico_conf;

-- 
-- Table: conf.roles
-- Descripción: Roles o grupos de usuarios
-- Scope: conf
--
	CREATE TABLE conf.roles (
		role_id int8 NOT NULL,
  		alias varchar NULL,
		description varchar NULL,
		preset bool NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_roles PRIMARY KEY (role_id)
	);

	ALTER TABLE conf.roles SET TABLESPACE tbs_controltrafico_conf;
	
-- 
-- Table: conf.role_permissions
-- Descripción: Permisos del sistema concedidos por rol
-- Scope: conf
--
	CREATE TABLE conf.role_permissions (
		role_id int8 NOT NULL,
		system_permission_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_role_permissions PRIMARY KEY (role_id, system_permission_id)
	);

	CREATE INDEX idx_role_permissions_system_permission_id ON conf.role_permissions USING btree (system_permission_id);

	ALTER TABLE conf.role_permissions ADD CONSTRAINT fk_role_permissions_role FOREIGN KEY (role_id) REFERENCES conf.roles(role_id);
	ALTER TABLE conf.role_permissions ADD CONSTRAINT fk_role_permissions_system_permission FOREIGN KEY (system_permission_id) REFERENCES master.system_permissions(system_permission_id);
	
	ALTER TABLE conf.role_permissions SET TABLESPACE tbs_controltrafico_conf;
	
-- 
-- Table: conf.users
-- Descripción: Usuarios del sistema
-- Scope: conf
--
	CREATE TABLE conf.users (
		user_id int8 NOT NULL,
		role_id int8 NOT NULL,
		name_ varchar NOT NULL,
		password_ varchar NOT NULL,
		password_expired_at timestamptz NULL,
		session_expire_time_s int4 NULL,
		locked_until timestamptz NULL,
		preset bool NOT NULL,
		allow_multiple_sessions bool NOT NULL,
		full_name varchar NULL,
		email varchar NULL,
		locale_code varchar NOT NULL,
		ldap bool NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_users PRIMARY KEY (user_id)
	);

	CREATE INDEX idx_users_role_id ON conf.users USING btree (role_id);

	ALTER TABLE conf.users ADD CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES conf.roles(role_id);
	ALTER TABLE conf.users ADD CONSTRAINT fk_users_i18n_locale FOREIGN KEY (locale_code) REFERENCES master.i18n_locales(locale_code);
	
	ALTER TABLE conf.users SET TABLESPACE tbs_controltrafico_conf;

-- 
-- Table: conf.user_permissions
-- Descripción: Permisos del sistema concedidos por usuario
-- Scope: conf
--
	CREATE TABLE conf.user_permissions (
		user_id int8 NOT NULL,
		system_permission_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_user_permissions PRIMARY KEY (user_id, system_permission_id)
	);

	CREATE INDEX idx_user_permissions_system_permission_id ON conf.user_permissions USING btree (system_permission_id);

	ALTER TABLE conf.user_permissions ADD CONSTRAINT fk_user_permissions_user FOREIGN KEY (user_id) REFERENCES conf.users(user_id);
	ALTER TABLE conf.user_permissions ADD CONSTRAINT fk_user_permissions_system_permission FOREIGN KEY (system_permission_id) REFERENCES master.system_permissions(system_permission_id);
	
	ALTER TABLE conf.user_permissions SET TABLESPACE tbs_controltrafico_conf;
	
	
-- 
-- Table: conf.roles_users
-- Descripción: Roles asociados a usuarios
-- Scope: conf
--
	CREATE TABLE conf.roles_users (
		user_id int8 NOT NULL,
		role_id int8 NOT NULL,
		enabled bool NULL,
		visible bool NULL,
		created_at timestamptz NOT NULL,
		updated_at timestamptz NOT NULL,
		CONSTRAINT pk_roles_users PRIMARY KEY (user_id, role_id)
	);
	
	CREATE INDEX idx_roles_users_users ON conf.roles_users USING btree (user_id);
	CREATE INDEX idx_roles_users_roles ON conf.roles_users USING btree (role_id);
	
	ALTER TABLE conf.roles_users ADD CONSTRAINT fk_roles_users_users FOREIGN KEY (user_id) REFERENCES conf.users(user_id);
	ALTER TABLE conf.roles_users ADD CONSTRAINT fk_roles_users_roles FOREIGN KEY (role_id) REFERENCES conf.roles(role_id);
	
	ALTER TABLE conf.roles_users SET TABLESPACE tbs_controltrafico_conf;
	
