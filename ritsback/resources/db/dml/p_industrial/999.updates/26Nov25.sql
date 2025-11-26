--Actualizamos el delay de los detectores a 6 minutos:
update conf.element_values
set value = '6'
where element_type_id = 3
and element_type_param_id = 4
and param_type_id = 2;

--Actualizamos las unidades de los parámetros de configuración que hacen referencia a número de vehículos
update master.element_type_params 
set element_type_param_unit_id = 37 
where element_type_id = 3 
and param_type_id = 2 
and element_type_param_id between 20 and 35;

update master.element_type_params 
set element_type_param_unit_id = 37 
where element_type_id = 3 
and param_type_id = 2 
and element_type_param_id between 40 and 55;

update master.element_type_params 
set element_type_param_unit_id = 37 
where element_type_id = 3 
and param_type_id = 2 
and element_type_param_id between 100 and 112;

update master.element_type_params 
set element_type_param_unit_id = 37 
where element_type_id = 4 
and param_type_id in (1,2) 
and element_type_param_id between 60 and 75;

update master.element_type_params 
set element_type_param_unit_id = 37 
where element_type_id = 4 
and param_type_id in (1,2)
and element_type_param_id between 80 and 95;

update master.element_type_params 
set element_type_param_unit_id = 37 
where element_type_id = 4 
and param_type_id = 1 
and element_type_param_id between 100 and 112;

update master.element_type_params 
set element_type_param_unit_id = 37 
where element_type_id = 4 
and param_type_id = 2 
and element_type_param_id between 140 and 152;

--Actualización de permisos
update master.system_permissions set enabled = false;

update master.system_permissions set enabled = true where system_permission_id = 1; --login
update master.system_permissions set enabled = true where system_permission_id = 2; --edit_user
update master.system_permissions set enabled = true where system_permission_id = 3; --edit_rol
update master.system_permissions set enabled = true where system_permission_id = 4; --edit_plan
update master.system_permissions set enabled = true where system_permission_id = 5; --execute_plan
update master.system_permissions set enabled = true where system_permission_id = 6; --command_action
update master.system_permissions set enabled = true where system_permission_id = 7; --alarm_recognize
update master.system_permissions set enabled = false where system_permission_id = 8; --edit_incident_report
update master.system_permissions set enabled = false where system_permission_id = 9; --create_incident_report
update master.system_permissions set enabled = false where system_permission_id = 10; --confirm_incident_report
update master.system_permissions set enabled = false where system_permission_id = 11; --cancel_incident_report
update master.system_permissions set enabled = false where system_permission_id = 12; --list_incident_report
update master.system_permissions set enabled = true where system_permission_id = 13; --manual edit vms
update master.system_permissions set enabled = true where system_permission_id = 14; --edit vms library
update master.system_permissions set enabled = false where system_permission_id = 15; --autoassign incident
update master.system_permissions set enabled = false where system_permission_id = 16; --assign incident another user
update master.system_permissions set enabled = true where system_permission_id = 17; --help menu
update master.system_permissions set enabled = false where system_permission_id = 18; --edit smthresholds
update master.system_permissions set enabled = true where system_permission_id = 19; --show alarms config
update master.system_permissions set enabled = false where system_permission_id = 20; --edit incidents
update master.system_permissions set enabled = false where system_permission_id = 21; --edit plan order automatism
update master.system_permissions set enabled = true where system_permission_id = 22; --edit alarms config
update master.system_permissions set enabled = true where system_permission_id = 23; --show system processes
update master.system_permissions set enabled = true where system_permission_id = 24; --restart system processes
update master.system_permissions set enabled = false where system_permission_id = 25; --edit equipments
update master.system_permissions set enabled = false where system_permission_id = 26; --edit cctv scenarios
update master.system_permissions set enabled = false where system_permission_id = 27; --edit maps
update master.system_permissions set enabled = false where system_permission_id = 28; --edit synoptics
update master.system_permissions set enabled = false where system_permission_id = 29; --edit maps advanced
update master.system_permissions set enabled = true where system_permission_id = 30; --view external reports
update master.system_permissions set enabled = false where system_permission_id = 31; --transits
update master.system_permissions set enabled = false where system_permission_id = 32; --edit locations
update master.system_permissions set enabled = false where system_permission_id = 33; --edit routes
update master.system_permissions set enabled = true where system_permission_id = 34; --show equipment
update master.system_permissions set enabled = true where system_permission_id = 35; --show incident backoffice
update master.system_permissions set enabled = false where system_permission_id = 36; --show external incidents

delete from conf.role_permissions;
INSERT INTO conf.role_permissions
(role_id, system_permission_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 23, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 24, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 34, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 35, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 14, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 30, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 34, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 35, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--Quitamos alarmas de sensores para detectores de ETD, ya que PAT no las da como tal
update conf.alarm_configs
set enabled = false
where alarm_config_id in (40004, 50004, 60004);

--Cambiamos la configuración de los umbrales de los niveles de servicio del traffic_manager
update conf.element_values
set value = '[6,25,57]'
where element_type_id = 4
and element_type_param_id = 6
and param_type_id = 1;

--Labels
update master.i18n_labels set translation = 'Daños responsables' where label = 'LBL_DAMAGES_RESPONSABILITIES' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Propietario daño' where label = 'LBL_DAMAGES_RESPONSABILITIES_OWNER_TYPE' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Tipo vía' where label = 'LBL_INCIDENT_ROAD_TYPE' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Subtipo servicios auxiliares' where label = 'LBL_SUPPORT_SERVICES_SUBTYPES' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Tipo servicios auxiliares' where label = 'LBL_SUPPORT_SERVICES_TYPES' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Selección idioma' where label = 'LBL_SELECT_LOCALE' and locale_code = 'es_CL';


