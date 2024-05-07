-- 
-- Table: conf.roles
--

INSERT INTO conf.roles
(role_id, alias, description, preset, enabled, visible, created_at, updated_at) VALUES
(1, 'Administrators', 'System administrators', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Operators', 'System operators', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Supervisor', 'System supervisor', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Monitor', 'System monitor', true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001,'test','',false,true,true,'2022-10-05 17:30:33.163393-03','2022-10-05 17:30:33.163393-03');


-- 
-- Table: conf.role_permissions
--
INSERT INTO conf.role_permissions (role_id,system_permission_id,enabled,visible,created_at,updated_at) VALUES
	 (1,1,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,2,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,3,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,4,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,5,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,6,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,7,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,8,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,9,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,10,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03');
INSERT INTO conf.role_permissions (role_id,system_permission_id,enabled,visible,created_at,updated_at) VALUES
	 (1,11,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,12,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,13,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,14,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,15,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,16,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (1,19,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,1,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,2,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,4,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03');
INSERT INTO conf.role_permissions (role_id,system_permission_id,enabled,visible,created_at,updated_at) VALUES
	 (2,5,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,6,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,7,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,8,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,9,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,10,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,11,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,12,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,13,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,14,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03');
INSERT INTO conf.role_permissions (role_id,system_permission_id,enabled,visible,created_at,updated_at) VALUES
	 (2,15,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,16,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (2,19,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03');

-- 
-- Table: conf.users
--
INSERT INTO conf.users (user_id,role_id,name_,password_,password_expired_at,session_expire_time_s,locked_until,preset,allow_multiple_sessions,full_name,email,locale_code,enabled,visible,created_at,updated_at) VALUES
	(1,1,'admin','Revenga.19',NULL,NULL,NULL,false,true,'System admin','admin@rits.com','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-16 16:49:10.409389-03'),
	(2,1,'user','user',NULL,NULL,NULL,false,true,'System user','user@rits.com','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-16 16:49:10.409389-03'),
	(3,2,'operator','operator',NULL,NULL,NULL,false,true,'System operator','operator@rits.com','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-16 16:49:10.409389-03');

