-- 
-- Type:   model
-- Entity: master.system_permissions
-- File:   system_permissions.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
	--SystemPermission:1
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_LOGIN', 'Iniciar Sesión', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_LOGIN_DESC', 'Permite iniciar sesión en la página de OpenITS con las credenciales correspondientes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:2
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_USER', 'Editar usuarios', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_USER_DESC', 'Permite editar los usuarios', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:3
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROL', 'Editar rol', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROL_DESC', 'Permite editar los roles de la aplicación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:4
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ADMIN', 'Administración de planes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ADMIN_DESC', 'Permite la creación de planes desde cero y la edición de los ya existentes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:5
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ACTION', 'Ejecución de planes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ACTION_DESC', 'Permite la ejecución de planes ya creados', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:6
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_COMMAND_ACTION', 'Acción', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_COMMAND_ACTION_DESC', 'Permite comandar equipos que puedan aceptar comandos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:7
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_ALARM_RECOGNIZE', 'Reconocer alarmas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_ALARM_RECOGNIZE_DESC', 'Permite reconocer las alarmas en el monitor de alarmas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:8
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENT', 'Gestionar incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENT_DESC', 'Permite gestionar el ciclo de vida una incidencia ya creada en el gestor de incidencias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:9
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_CREATE_INCIDENT', 'Crear incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_CREATE_INCIDENT_DESC', 'Permite crear una incidencia de manera manual en el gestor de incidencias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:10
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_CONFIRM_INCIDENT', 'Confirmar incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_CONFIRM_INCIDENT_DESC', 'Permite configurar una incidencia genera automáticamente para su posterior gestión', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:11
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_CANCEL_INCIDENT', 'Cancelar incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_CANCEL_INCIDENT_DESC', 'Permite cancelar una incidencia, cerrándola en el proceso', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:12
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_SEE_INCIDENT', 'Ver incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_SEE_INCIDENT_DESC', 'Permite ver la lista de incidencias sin editarlas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:13
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_MANUAL_EDIT_VMS', 'Editar señalización paneles', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_MANUAL_EDIT_VMS_DESC', 'Permite cambiar la señalización de un PMV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:14
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_VMS_LIBRARY', 'Editar libreria de señalización', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_VMS_LIBRARY_DESC', 'Permite editar los mensajes en la librería de los PMVs', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:15
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_AUTOASSINGN_INCIDENT', 'Autoasignar incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_AUTOASSIGN_INCIDENT_DESC', 'Permite asignar al usuario actual la gestión de la incidencia automáticamente', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:16
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_ASSIGN_INCIDENT_ANOTHER_USER', 'Asignar incidencia a otro usuario', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_ASSIGN_INCIDENT_ANOTHER_USER_DESC', 'Permite asignar una incidencia a otro usuario', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:17
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_HELP', 'Ayuda', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_HELP_DESC', 'Permite mostrar el menú de ayuda. Este menú contiene la ventana de acerca de, esta ventana muestra información de los procesos actuales que componen el software OpenitS y las versiones actuales de cada uno', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:18
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SMTHRESHOLDS', 'Editar umbral de máquinas de estado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SMTHRESHOLDS_DESC', 'Algoritmos: Permite cambiar el umbral al que saltará la alarma en dispositivos como luxómetros o detectores de NO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:19
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_SHOW_ALARM_CONFIG', 'Mostrar configuración de alarmas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_SHOW_ALARM_CONFIG_DESC', 'Permite ver las alarmas configuradas en el sistema por tipos de equipos, su nivel y umbrales en caso de tenerlos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:20
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENTS', 'Configuración de incidencias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENTS_DESC', 'Permite editar la configuración de un tipo de incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:21
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_PLAN_ORDER_AUTOMATISM', 'Editar orden de tipo automatismo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_PLAN_ORDER_AUTOMATISM_DESC', 'Habilita la edición de órdenes con automatismos específicos en los planes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	
	--SystemPermission:22
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ALARM_CONFIG', 'Añadir, eliminar y editar configuración de alarma', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ALARM_CONFIG_DESC', 'Permite crear, editar y borrar alarmas personalizadas fuera de las establecidas por defecto', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:23
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_SHOW_SYSTEM_PROCESSES', 'Mostrar administrador de tareas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_SHOW_SYSTEM_PROCESSES_DESC', 'Permite ver los procesos del sistema y el rendimiento de los mismos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:24
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_RESTART_SYSTEM_PROCESSES', 'Reiniciar procesos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_RESTART_SYSTEM_PROCESSES_DESC', 'Permite reiniciar procesos del sistema', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:25
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_EQUIPMENTS', 'Editar equipos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_EQUIPMENTS_DESC', 'Permite crear, editar, clonar y eliminar equipos del sistema', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:27
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS', 'Editar mapas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS_DESC', 'Permite editar mapas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:28
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SYNOPTICS', 'Editar sinópticos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SYNOPTICS_DESC', 'Permite editar sinópticos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:29
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS_ADVANCED', 'Editar mapas (avanzado)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS_ADVANCED_DESC', 'Permite editar mapas (avanzado)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:30
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_VIEW_EXTERNAL_REPORTS', 'Ver informes en Jasper', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_VIEW_EXTERNAL_REPORTS_DESC', 'Ver informes en Jasper', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:31
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_TRANSITS', 'Gestor de tránsitos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_TRANSITS_DESC', 'estor de tránsitos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:32
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_LOCATIONS', 'Editor de localizaciones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_LOCATIONS_DESC', 'Editor de localizaciones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:33
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROUTES', 'Editor de rutas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROUTES_DESC', 'Editor de rutas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:34
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_SHOW_EQUIPMENT', 'Permite visualizar el menú que muestr el grid con los tipos de equipos y su información', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_SHOW_EQUIPMENT_DESC', 'Permite visualizar el menú que muestr el grid con los tipos de equipos y su información', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:35
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_SHOW_INCIDENT_BACKOFFICE', 'Permite visualizar la bitácora de incidencias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_SHOW_INCIDENT_BACKOFFICE_DESC', 'Permite visualizar la bitácora de incidencias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:36
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_SHOW_EXTERNAL_INCIDENTS', 'Permite visualizar la tabla con la información de las incidencias externas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_ES', 1, 'LBL_SYSTEM_PERMISSION_SHOW_EXTERNAL_INCIDENTS_DESC', 'Permite visualizar la tabla con la información de las incidencias externas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	
--en_GB
	--SystemPermission:1
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_LOGIN', 'Login', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_LOGIN_DESC', 'Allows you to log in to the OpenITS page with the corresponding credentials', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:2
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_USER', 'Edit users', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_USER_DESC', 'Allows edit users', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:3
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROL', 'Edit rol', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROL_DESC', 'Allows edit roles', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:4
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ADMIN', 'Plans management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ADMIN_DESC', 'Allows create plans and edit of existing ones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:5
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ACTION', 'Plans execution', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ACTION_DESC', 'Allows the execution of already created plans', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:6
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_COMMAND_ACTION', 'Equipment command', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_COMMAND_ACTION_DESC', 'Allows commanding equipment that can accept commands', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:7
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_ALARM_RECOGNIZE', 'Recognize alarms', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_ALARM_RECOGNIZE_DESC', 'Allows recognizing alarms on the alarm monitor', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:8
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENT', 'Incident management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENT_DESC', 'Allows managing the life cycle of an already created incident in the incident manager', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:9
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_CREATE_INCIDENT', 'Create incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_CREATE_INCIDENT_DESC', 'Allows creating an incident manually in the incident manager', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:10
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_CONFIRM_INCIDENT', 'Confirm incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_CONFIRM_INCIDENT_DESC', 'Permite confirar una incidencia genera automáticamente para su posterior gestión', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:11
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_CANCEL_INCIDENT', 'Cancel incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_CANCEL_INCIDENT_DESC', 'Allows configuring an incident to be generated automatically for later management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:12
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_SEE_INCIDENT', 'Show incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_SEE_INCIDENT_DESC', 'Allows viewing the list of incidents without editing them', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:13
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_MANUAL_EDIT_VMS', 'Edit panel signaling', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_MANUAL_EDIT_VMS_DESC', 'Allows changing the signaling of a VMS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:14
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_VMS_LIBRARY', 'Edit signaling library', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_VMS_LIBRARY_DESC', 'Allows editing messages in the library of VMS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:15
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_AUTOASSINGN_INCIDENT', 'Autoassign incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_AUTOASSIGN_INCIDENT_DESC', 'Allows assigning the current user the management of the incident automatically', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:16
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_ASSIGN_INCIDENT_ANOTHER_USER', 'Assign incident to another user', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_ASSIGN_INCIDENT_ANOTHER_USER_DESC', 'Allows assigning an incident to another user', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:17
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_HELP', 'Help', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_HELP_DESC', 'Allows displaying the help menu. This menu contains the About window, which displays information on the current processes that make up the OpenitS software and their current versions', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:18
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SMTHRESHOLDS', 'Edit threshold of state machines', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SMTHRESHOLDS_DESC', 'Algorithms: Allows changing the threshold at which the alarm will trigger in devices such as luxmeters or NO detectors', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:19
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_SHOW_ALARM_CONFIG', 'Show alarm config', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_SHOW_ALARM_CONFIG_DESC', 'Allows viewing the alarms configured in the system by equipment types, their level, and thresholds if they have them', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:20
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENTS', 'Incident configuration', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENTS_DESC', 'Allows editing the configuration of an incident type', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:21
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_PLAN_ORDER_AUTOMATISM', 'Editar orden de tipo automatismo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_PLAN_ORDER_AUTOMATISM_DESC', 'Edit order of automation type', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	
	--SystemPermission:22
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ALARM_CONFIG', 'Add, delete, and edit alarm configuration', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ALARM_CONFIG_DESC', 'Allows creating, editing, and deleting custom alarms outside those established by default', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:23
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_SHOW_SYSTEM_PROCESSES', 'Show task manager', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_SHOW_SYSTEM_PROCESSES_DESC', 'Allows you to view system processes and their performance', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:24
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_RESTART_SYSTEM_PROCESSES', 'Restart processes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_RESTART_SYSTEM_PROCESSES_DESC', 'Allows you to restart system processes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:25
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_EQUIPMENTS', 'Edit equipments', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_EQUIPMENTS_DESC', 'User can create, edit, clone and delete equipments', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:27
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS', 'Edit maps', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS_DESC', 'Allows edit maps', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:28
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SYNOPTICS', 'Edit synoptics', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SYNOPTICS_DESC', 'Allows edit synoptics', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:29
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS_ADVANCED', 'Edit maps (expert)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS_ADVANCED_DESC', 'Allows edit maps (expert)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:30
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_VIEW_EXTERNAL_REPORTS', 'Show Jasper reports', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_VIEW_EXTERNAL_REPORTS_DESC', 'Show Jasper reports', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:31
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_TRANSITS', 'Transit management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_TRANSITS_DESC', 'Transit management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:32
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_LOCATIONS', 'Edit locations', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_LOCATIONS_DESC', 'transit management', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:33
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROUTES', 'Edit routes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROUTES_DESC', 'Edit routes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:34
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_SHOW_EQUIPMENT', 'It allows you to view the menu that displays the grid with the types of equipment and their information', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_SHOW_EQUIPMENT_DESC', 'It allows you to view the menu that displays the grid with the types of equipment and their information', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:35
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_SHOW_INCIDENT_BACKOFFICE', 'It allows you to view the incident log', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_SHOW_INCIDENT_BACKOFFICE_DESC', 'It allows you to view the incident log', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:36
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_SHOW_EXTERNAL_INCIDENTS', 'It allows you to view the table with information on external incidents', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('en_GB', 1, 'LBL_SYSTEM_PERMISSION_SHOW_EXTERNAL_INCIDENTS_DESC', 'It allows you to view the table with information on external incidents', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--sk_SK
	--SystemPermission:1
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_LOGIN', 'Prihlásenie', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_LOGIN_DESC', 'Prihlásenie', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:2
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_USER', 'Upraviť užívateľov', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_USER_DESC', 'Upraviť užívateľov', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:3
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROL', 'Upraviť oprávnenia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROL_DESC', 'Upraviť oprávnenia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:4
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ADMIN', 'Administrátor plánovania', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ADMIN_DESC', 'Administrátor plánovania', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:5
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ACTION', 'Scenáre', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ACTION_DESC', 'Scenáre', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:6
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_COMMAND_ACTION', 'Príkaz', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_COMMAND_ACTION_DESC', 'Príkaz', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:7
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_ALARM_RECOGNIZE', 'Detegovať alarmy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_ALARM_RECOGNIZE_DESC', 'Detegovať alarmy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:8
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENT', 'Upraviť incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENT_DESC', 'Upraviť incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:9
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_CREATE_INCIDENT', 'VYtvoriť incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_CREATE_INCIDENT_DESC', 'VYtvoriť incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:10
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_CONFIRM_INCIDENT', 'Potvrdiť incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_CONFIRM_INCIDENT_DESC', 'Potvrdiť incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:11
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_CANCEL_INCIDENT', 'Zrušiť incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_CANCEL_INCIDENT_DESC', 'Zrušiť incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:12
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_SEE_INCIDENT', 'Otvoriť incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_SEE_INCIDENT_DESC', 'Otvoriť incident', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:13
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_MANUAL_EDIT_VMS', 'Upraviť označenie panela', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_MANUAL_EDIT_VMS_DESC', 'Upraviť označenie panela', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:14
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_VMS_LIBRARY', 'Upraviť knižnicu nápisov', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_VMS_LIBRARY_DESC', 'Upraviť knižnicu nápisov', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:15
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_AUTOASSINGN_INCIDENT', 'Problém s automatickým priradením', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_AUTOASSIGN_INCIDENT_DESC', 'Problém s automatickým priradením', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:16
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_ASSIGN_INCIDENT_ANOTHER_USER', 'Priraďte problém inému používateľovi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_ASSIGN_INCIDENT_ANOTHER_USER_DESC', 'Priraďte problém inému používateľovi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:17
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_HELP', 'Pomoc', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_HELP_DESC', 'Pomoc', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:18
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SMTHRESHOLDS', 'Upraviť prah stavových automatov', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SMTHRESHOLDS_DESC', 'Upraviť prah stavových automatov', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:19
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_SHOW_ALARM_CONFIG', 'Zobraziť konfiguráciu alarmu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_SHOW_ALARM_CONFIG_DESC', 'Zobraziť konfiguráciu alarmu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:20
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENTS', 'konfigurácia incidentu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENTS_DESC', 'konfigurácia incidentu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:21
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_PLAN_ORDER_AUTOMATISM', 'Upraviť poradie typu automatizmu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_PLAN_ORDER_AUTOMATISM_DESC', 'Upraviť poradie typu automatizmu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	
	--SystemPermission:22
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ALARM_CONFIG', 'Pridať, odstrániť a upraviť konfiguráciu alarmu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ALARM_CONFIG_DESC', 'Pridať, odstrániť a upraviť konfiguráciu alarmu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:23
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_SHOW_SYSTEM_PROCESSES', 'zobraziť správcu úloh', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_SHOW_SYSTEM_PROCESSES_DESC', 'Umožňuje zobraziť systémové procesy a ich výkon', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:24
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_RESTART_SYSTEM_PROCESSES', 'reštartovať procesy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_RESTART_SYSTEM_PROCESSES_DESC', 'Umožňuje reštartovať systémové procesy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:25
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_EQUIPMENTS', 'Upraviť tímy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_EQUIPMENTS_DESC', 'Umožňuje vytvárať, upravovať, klonovať a mazať počítače zo systému', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:27
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS', 'Úprava máp', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS_DESC', 'Umožňuje upravovať mapy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:28
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SYNOPTICS', 'Upraviť synoptiku', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SYNOPTICS_DESC', 'Umožňuje upravovať synoptiku', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:29
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS_ADVANCED', 'Úprava máp (expert)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('sk_SK', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS_ADVANCED_DESC', 'Umožňuje upravovať mapy (expert)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	
--es_CL
	--SystemPermission:1
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_LOGIN', 'Iniciar Sesión', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_LOGIN_DESC', 'Permite iniciar sesión en la página de OpenITS con las credenciales correspondientes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:2
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_USER', 'Editar usuarios', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_USER_DESC', 'Permite editar los usuarios', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:3
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROL', 'Editar rol', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROL_DESC', 'Permite editar los roles de la aplicación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:4
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ADMIN', 'Administración de planes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ADMIN_DESC', 'Permite la creación de planes desde cero y la edición de los ya existentes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:5
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ACTION', 'Ejecución de planes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_PLAN_ACTION_DESC', 'Permite la ejecución de planes ya creados', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:6
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_COMMAND_ACTION', 'Acción', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_COMMAND_ACTION_DESC', 'Permite comandar equipos que puedan aceptar comandos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:7
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_ALARM_RECOGNIZE', 'Reconocer alarmas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_ALARM_RECOGNIZE_DESC', 'Permite reconocer las alarmas en el monitor de alarmas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:8
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENT', 'Gestionar incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENT_DESC', 'Permite gestionar el ciclo de vida una incidencia ya creada en el gestor de incidencias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:9
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_CREATE_INCIDENT', 'Crear incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_CREATE_INCIDENT_DESC', 'Permite crear una incidencia de manera manual en el gestor de incidencias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:10
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_CONFIRM_INCIDENT', 'Confirmar incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_CONFIRM_INCIDENT_DESC', 'Permite configurar una incidencia genera automáticamente para su posterior gestión', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:11
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_CANCEL_INCIDENT', 'Cancelar incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_CANCEL_INCIDENT_DESC', 'Permite cancelar una incidencia, cerrándola en el proceso', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:12
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_SEE_INCIDENT', 'Ver incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_SEE_INCIDENT_DESC', 'Permite ver la lista de incidencias sin editarlas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:13
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_MANUAL_EDIT_VMS', 'Editar señalización paneles', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_MANUAL_EDIT_VMS_DESC', 'Permite cambiar la señalización de un PMV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:14
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_VMS_LIBRARY', 'Editar libreria de señalización', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_VMS_LIBRARY_DESC', 'Permite editar los mensajes en la librería de los PMVs', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:15
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_AUTOASSINGN_INCIDENT', 'Autoasignar incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_AUTOASSIGN_INCIDENT_DESC', 'Permite asignar al usuario actual la gestión de la incidencia automáticamente', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:16
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_ASSIGN_INCIDENT_ANOTHER_USER', 'Asignar incidencia a otro usuario', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_ASSIGN_INCIDENT_ANOTHER_USER_DESC', 'Permite asignar una incidencia a otro usuario', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:17
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_HELP', 'Ayuda', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_HELP_DESC', 'Permite mostrar el menú de ayuda. Este menú contiene la ventana de acerca de, esta ventana muestra información de los procesos actuales que componen el software OpenitS y las versiones actuales de cada uno', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:18
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SMTHRESHOLDS', 'Editar umbral de máquinas de estado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SMTHRESHOLDS_DESC', 'Algoritmos: Permite cambiar el umbral al que saltará la alarma en dispositivos como luxómetros o detectores de NO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:19
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_SHOW_ALARM_CONFIG', 'Mostrar configuración de alarmas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_SHOW_ALARM_CONFIG_DESC', 'Permite ver las alarmas configuradas en el sistema por tipos de equipos, su nivel y umbrales en caso de tenerlos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:20
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENTS', 'Configuración de incidencias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_INCIDENTS_DESC', 'Permite editar la configuración de un tipo de incidencia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:21
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_PLAN_ORDER_AUTOMATISM', 'Editar orden de tipo automatismo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_PLAN_ORDER_AUTOMATISM_DESC', 'Habilita la edición de órdenes con automatismos específicos en los planes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	
	--SystemPermission:22
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ALARM_CONFIG', 'Añadir, eliminar y editar configuración de alarma', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ALARM_CONFIG_DESC', 'Permite crear, editar y borrar alarmas personalizadas fuera de las establecidas por defecto', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:23
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_SHOW_SYSTEM_PROCESSES', 'Mostrar administrador de tareas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_SHOW_SYSTEM_PROCESSES_DESC', 'Permite ver los procesos del sistema y el rendimiento de los mismos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:24
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_RESTART_SYSTEM_PROCESSES', 'Reiniciar procesos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_RESTART_SYSTEM_PROCESSES_DESC', 'Permite reiniciar procesos del sistema', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:25
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_EQUIPMENTS', 'Editar equipos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_EQUIPMENTS_DESC', 'Permite crear, editar, clonar y eliminar equipos del sistema', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:27
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS', 'Editar mapas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS_DESC', 'Permite editar mapas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:28
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SYNOPTICS', 'Editar sinópticos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_SYNOPTICS_DESC', 'Permite editar sinópticos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:29
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS_ADVANCED', 'Editar mapas (avanzado)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_MAPS_ADVANCED_DESC', 'Permite editar mapas (avanzado)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:30
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_VIEW_EXTERNAL_REPORTS', 'Ver informes en Jasper', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_VIEW_EXTERNAL_REPORTS_DESC', 'Ver informes en Jasper', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:31
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_TRANSITS', 'Gestor de tránsitos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_TRANSITS_DESC', 'estor de tránsitos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:32
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_LOCATIONS', 'Editor de localizaciones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_LOCATIONS_DESC', 'Editor de localizaciones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:33
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROUTES', 'Editor de rutas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_EDIT_ROUTES_DESC', 'Editor de rutas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:34
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_SHOW_EQUIPMENT', 'Permite visualizar el menú que muestr el grid con los tipos de equipos y su información', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_SHOW_EQUIPMENT_DESC', 'Permite visualizar el menú que muestr el grid con los tipos de equipos y su información', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:35
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_SHOW_INCIDENT_BACKOFFICE', 'Permite visualizar la bitácora de incidencias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_SHOW_INCIDENT_BACKOFFICE_DESC', 'Permite visualizar la bitácora de incidencias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	--SystemPermission:36
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_SHOW_EXTERNAL_INCIDENTS', 'Permite visualizar la tabla con la información de las incidencias externas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
	('es_CL', 1, 'LBL_SYSTEM_PERMISSION_SHOW_EXTERNAL_INCIDENTS_DESC', 'Permite visualizar la tabla con la información de las incidencias externas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
