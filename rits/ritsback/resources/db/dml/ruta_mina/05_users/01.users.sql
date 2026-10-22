-- 
-- Table: conf.roles
--

INSERT INTO conf.roles
(role_id, alias, description, preset, enabled, visible, created_at, updated_at) VALUES
(1, 'Administrators', 'System administrators', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Operators', 'System operators', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Visualizador', 'System visualizador', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: conf.role_permissions
--
INSERT INTO conf.role_permissions (role_id,system_permission_id,enabled,visible,created_at,updated_at) VALUES
	 (1,1,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,2,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,3,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,4,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,5,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,6,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,7,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,8,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,9,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,10,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,11,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,12,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,13,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,14,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,15,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,16,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,17,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,18,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,19,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,21,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,22,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,24,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (1,23,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),


	 (2,1,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,4,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,5,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,6,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,7,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,8,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,9,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,10,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,11,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,12,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,13,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,14,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,15,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,16,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,17,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (2,19,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),

	 (3,1,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	 (3,17,true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

-- 
-- Table: conf.users
--
INSERT INTO conf.users (user_id,role_id,name_,password_,password_expired_at,session_expire_time_s,locked_until,preset,allow_multiple_sessions,full_name,email,locale_code,enabled,visible,created_at,updated_at) VALUES
	(1,1,'admin','PTp8841/XyOkxeVUCyNf+w==',NULL,NULL,NULL,false,true,'System admin','admin@rits.com','es_CL',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(2,3,'user','WwenRLCQXHyG+ErnioNWtw==',NULL,NULL,NULL,false,true,'System user','user@rits.com','es_CL',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(3,2,'backup','dU4VZb2v3lEsBUYECmBZ/A==',NULL,NULL,NULL,false,true,'System operator','operator@rits.com','es_CL',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(4,2,'EliasJi','dU4VZb2v3lEsBUYECmBZ/A==',NULL,NULL,NULL,false,true,'Elias Esteban Jimenez  ','operator@rits.com','es_CL',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(5,2,'JoseGu','dU4VZb2v3lEsBUYECmBZ/A==',NULL,NULL,NULL,false,true,'Jose Luis Gutlerrez ','operator@rits.com','es_CL',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(6,2,'PatriciaAl','dU4VZb2v3lEsBUYECmBZ/A==',NULL,NULL,NULL,false,true,'Patricia Alcaly Inarejo ','operator@rits.com','es_CL',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(7,2,'SebastianJa','dU4VZb2v3lEsBUYECmBZ/A==',NULL,NULL,NULL,false,true,'Sebastian Andres Jara ','operator@rits.com','es_CL',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(8,1,'AlbertFlo ','dU4VZb2v3lEsBUYECmBZ',NULL,NULL,NULL,false,true,'Albert Flores ','admin@rits.com','es_CL',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(9,3,'DanielaRi','dU4VZb2v3lEsBUYECmBZ',NULL,NULL,NULL,false,true,'Daniela Rivero ','user@rits.com','es_CL',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	(10,3,'AldoPi','dU4VZb2v3lEsBUYECmBZ',NULL,NULL,NULL,false,true,'Aldo Pinto','user@rits.com','es_CL',true,true,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),



