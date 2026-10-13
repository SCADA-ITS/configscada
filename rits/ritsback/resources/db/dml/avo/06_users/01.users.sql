UPDATE master.system_permissions SET enabled=false WHERE system_permission_id IN (18,20,21);

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
	 (2,19,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,1,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,2,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,4,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,5,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,6,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,7,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,8,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03');
INSERT INTO conf.role_permissions (role_id,system_permission_id,enabled,visible,created_at,updated_at) VALUES
	 (3,9,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,10,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,11,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,12,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,13,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,15,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,16,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (3,19,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03'),
	 (4,1,true,true,'2022-09-13 05:15:26.253761-03','2022-09-13 05:15:26.253761-03');

-- 
-- Table: conf.users
--
INSERT INTO conf.users (user_id,role_id,name_,password_,password_expired_at,session_expire_time_s,locked_until,preset,allow_multiple_sessions,full_name,email,locale_code,enabled,visible,created_at,updated_at) VALUES
	 (2,1,'avo','AVO_2022',NULL,NULL,NULL,true,true,'System admin','avo@avo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 05:15:26.256969-03'),
	 (5,2,'fguerrero','fguerrero',NULL,NULL,NULL,true,true,'Francisco Guerrero Basure','fguerrero@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 05:15:26.256969-03'),
	 (15,3,'jvillalon','jvillalon',NULL,NULL,NULL,true,true,'Jose Villalón Ugarte','jvillalon@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 05:15:26.256969-03'),
	 (18,1,'mfavre','mfavre',NULL,NULL,NULL,true,true,'Manuel Favre','mfavre@globe.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 05:15:26.256969-03'),
	 (19,1,'omarcos','omarcos',NULL,NULL,NULL,true,true,'Oscar Marcos','oscar.marcos@revenga.com','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 05:15:26.256969-03'),
	 (25,3,'rorellana','rorellana',NULL,NULL,NULL,true,true,'rorellana','rorellana@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 05:15:26.256969-03'),
	 (7,2,'dhernandez','dhernandez',NULL,NULL,NULL,false,true,'Diego Hernández Ramírez','dhernandez@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 09:37:51.284419-03');
INSERT INTO conf.users (user_id,role_id,name_,password_,password_expired_at,session_expire_time_s,locked_until,preset,allow_multiple_sessions,full_name,email,locale_code,enabled,visible,created_at,updated_at) VALUES
	 (3,2,'rgalvez','rgalvez',NULL,NULL,NULL,false,true,'Ricardo Gálvez Calderón','rgalvez@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 09:38:14.917455-03'),
	 (10,2,'jorellana','jorellana',NULL,NULL,NULL,false,true,'Javiera Orellana Rivera','jorellana@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 09:38:29.578948-03'),
	 (13,2,'cescalona','cescalona',NULL,NULL,NULL,false,true,'Claudia Escalona Cáceres','cescalona@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 09:38:46.188325-03'),
	 (12,2,'rmunoz','rmunoz',NULL,NULL,NULL,false,true,'Ricardo Muñoz Mella','rmunoz@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 09:39:00.74402-03'),
	 (6,2,'clillo','clillo',NULL,NULL,NULL,false,true,'Carlos Lillo Catalán','clillo@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 09:39:11.506063-03'),
	 (23,3,'fgajardo','24242525',NULL,NULL,NULL,false,true,'Felipe Gajardo','fgajardo@globe.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 09:40:17.129063-03'),
	 (14,2,'ihuenuman','ihuenuman',NULL,NULL,NULL,false,true,'Ivana Huenuman Cortes','ihuenuman@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 09:40:27.712804-03'),
	 (9,2,'jvalenzuela','jvalenzuela2127',NULL,NULL,NULL,false,true,'Jaime Valenzuela Retamal','jvalenzuela@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 09:40:47.29022-03'),
	 (8,2,'mleiton','mleiton',NULL,NULL,NULL,false,true,'Mario Leiton Carrillo','mleiton@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 09:41:04.629473-03'),
	 (4,2,'vgonzalez','vgonzalez',NULL,NULL,NULL,false,true,'Vladimir González Villarroel','vgonzalez@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 09:41:20.113193-03');
INSERT INTO conf.users (user_id,role_id,name_,password_,password_expired_at,session_expire_time_s,locked_until,preset,allow_multiple_sessions,full_name,email,locale_code,enabled,visible,created_at,updated_at) VALUES
	 (22,3,'ssembler','ssembler',NULL,NULL,NULL,false,true,'Sebastian Sembler','ssembler@globe.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-13 15:06:28.601218-03'),
	 (1,1,'admin','Revenga.19',NULL,NULL,NULL,false,true,'System admin','admin@rits.com','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-16 16:49:10.409389-03'),
	 (17,1,'jgutierrez','jgv-7732',NULL,NULL,NULL,false,true,'José Gutiérrez Vivanco','jgutierrez@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-20 10:45:48.116548-03'),
	 (21,3,'jcastaneda','jcastaneda',NULL,NULL,NULL,false,true,'Juan Castañeda','','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-20 10:45:53.785817-03'),
	 (16,1,'mgallardo','1234',NULL,NULL,NULL,false,true,'Mario Gallardo Villagra','mgallardo@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-20 10:46:32.245002-03'),
	 (2001,2,'ihinojosa','ihinojosa',NULL,NULL,NULL,false,true,'ihinojosa','ihinojosa@scavo.cl','es_CL',true,true,'2022-09-26 18:40:24.153134-03','2022-09-26 18:44:26.333998-03'),
	 (2002,2,'jortiz','jortiz',NULL,NULL,NULL,false,true,'jortiz','jortiz@scavo.cl','es_CL',true,true,'2022-09-26 18:41:02.229157-03','2022-09-26 18:44:39.138015-03'),
	 (2003,3,'rsapiain','1234',NULL,NULL,NULL,false,true,'Ronald Sapiain','rsapiain@globe.cl','es_CL',true,true,'2022-09-29 18:32:53.417192-03','2022-09-29 18:32:53.417192-03'),
	 (24,1,'jmoreno','orjnob781',NULL,NULL,NULL,false,true,'Jorge Moreno','jmoreno@globe.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-09-29 18:33:39.770491-03'),
	 (2004,3,'jsanmartin','1234',NULL,NULL,NULL,false,true,'Jose San Martin','jsanmartin@globe.cl','es_CL',true,true,'2022-09-29 18:35:09.609487-03','2022-09-29 18:35:09.609487-03');
INSERT INTO conf.users (user_id,role_id,name_,password_,password_expired_at,session_expire_time_s,locked_until,preset,allow_multiple_sessions,full_name,email,locale_code,enabled,visible,created_at,updated_at) VALUES
	 (2005,1001,'test','1234',NULL,NULL,NULL,false,true,'','','es_ES',true,true,'2022-10-05 17:29:19.411172-03','2022-10-05 17:30:59.180869-03'),
	 (2006,4,'monitor','monitor',NULL,NULL,NULL,false,true,'','','es_CL',true,true,'2022-10-05 17:29:19.411172-03','2022-10-05 17:30:59.180869-03'),
	 (11,2,'jcespedes','jcespedes',NULL,NULL,'2022-11-01 23:17:10.794-03',false,true,'Julio Céspedes Miranda','jcespedes@scavo.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-11-01 23:17:11.253429-03'),
	 (2007,2,'artha.tec','@Artha',NULL,NULL,NULL,false,true,'Rocke Almuna','almuna@arthatecnologia.cl','es_CL',true,true,'2022-11-23 12:41:00.07718-03','2022-11-23 12:41:00.07718-03'),
	 (20,3,'cduben','1234',NULL,NULL,NULL,false,true,'Carlos Duben','caduben@globe.cl','es_CL',true,true,'2022-09-13 05:15:26.256969-03','2022-12-21 14:36:10.89567-03'),
	 (2008,3,'acabral','1234',NULL,NULL,NULL,false,true,'Aristoteles Cabral','acabral@globe.cl','es_CL',true,true,'2022-12-21 14:37:48.607475-03','2022-12-21 14:37:48.607475-03'),
	 (2009,3,'Snicolao','1234',NULL,NULL,NULL,false,true,'Salvador Nicolao','snicolao@globe.cl','es_CL',true,true,'2023-01-12 09:12:35.317435-03','2023-01-12 09:12:35.317435-03');
