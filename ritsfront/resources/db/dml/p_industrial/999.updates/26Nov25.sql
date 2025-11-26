--Actualizamos los permisos para los módulos que usamos
UPDATE ui.modules set default_system_permission_id = 'SystemPermission:2' where id = 1000;    --Usuarios
UPDATE ui.modules set default_system_permission_id = 'SystemPermission:3' where id = 1001;    --Roles
UPDATE ui.modules set default_system_permission_id = 'SystemPermission:17' where id = 1047;   --Ayuda
UPDATE ui.modules set default_system_permission_id = 'SystemPermission:30' where id = 403;   --Reports
UPDATE ui.modules set default_system_permission_id = 'SystemPermission:34' where id = 500;   --Ver equipamiento
UPDATE ui.modules set default_system_permission_id = 'SystemPermission:35' where id in (1921, 1922);   --Ver backoffice de incidencias