-- 
-- Type:   modules
-- Entity: Task manager service
-- File:   task_manager_service.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

-- es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	('es_ES', 1, 'LBL_TASK_MANAGER', 'Administrador de procesos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_TASK_MANAGER_DESCRIPTION', 'Administrador de rendimiento y procesos del sistema.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_RESTART_PROCESSES_CONFIRM_DESCRIPTION', '¿Desea reiniciar el/los proceso/s seleccionado/s?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_PERFORMANCE', 'Rendimiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_PROCESSES', 'Procesos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_UPDATE_TIME', 'Tiempo de actualización', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_PID', 'PID', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_RESTARTS', 'Reinicios', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_RESTART_PROCESS', 'Reiniciar proceso', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_COMMAND_LINE', 'Línea de comandos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_UP', 'Conectado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_DOWN', 'Desconectado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- en_GB
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES	
	('en_GB', 1, 'LBL_TASK_MANAGER', 'System process manager', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_TASK_MANAGER_DESCRIPTION', 'System process and performance manager.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_RESTART_PROCESSES_CONFIRM_DESCRIPTION', 'Do you want to restart the selected process?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_PERFORMANCE', 'Performance', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_PROCESSES', 'Processes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_UPDATE_TIME', 'Update time', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_PID', 'PID', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_RESTARTS', 'Restarts', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_RESTART_PROCESS', 'Restart process', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_COMMAND_LINE', 'Command line', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_UP', 'Up', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_DOWN', 'Down', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);		
 	
-- sk_SK
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES	
	('sk_SK', 1, 'LBL_TASK_MANAGER', 'Manažér procesov a výkonu systému.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_TASK_MANAGER_DESCRIPTION', 'Manažér procesov a výkonu systému.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_RESTART_PROCESSES_CONFIRM_DESCRIPTION', 'Chcete reštartovať vybraný proces?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_PERFORMANCE', 'Výkon', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_PROCESSES', 'procesy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_UPDATE_TIME', 'čas aktualizácie', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_PID', 'PID', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_RESTARTS', 'Reštart', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	
	('sk_SK', 1, 'LBL_RESTART_PROCESS', 'reštartovať proces', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_COMMAND_LINE', 'Príkazový riadok', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_UP', 'Hore', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_DOWN', 'Dole', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);		
	
-- es_CL
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
	('es_CL', 1, 'LBL_TASK_MANAGER', 'Administrador de procesos.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_TASK_MANAGER_DESCRIPTION', 'Administrador de rendimiento y procesos del sistema.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_RESTART_PROCESSES_CONFIRM_DESCRIPTION', '¿Desea reiniciar el/los proceso/s seleccionado/s?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_PERFORMANCE', 'Rendimiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_PROCESSES', 'Procesos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_UPDATE_TIME', 'Tiempo de actualización', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_PID', 'PID', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_RESTARTS', 'Reinicios', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_RESTART_PROCESS', 'Reiniciar proceso', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_COMMAND_LINE', 'Línea de comandos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_UP', 'Conectado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_DOWN', 'Desconectado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


