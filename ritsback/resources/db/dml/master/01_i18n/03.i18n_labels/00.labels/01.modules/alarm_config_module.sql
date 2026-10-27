-- 
-- Type:   modules
-- Entity: Alarm config module
-- File:   alarm_config_module.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES 
('es_ES', 1, 'LBL_ADD_ALARM_CONFIG', 'Añadir configuración de alarma', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ADD_ALARM_CONFIG_DESCRIPTION', 'Formulario para añadir nueva configuración de alarma y umbrales', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ADD_NEW_ALARM_CONFIG', 'Añadir nueva configuración de alarma', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIGS_TITLE', 'Configuración de alarmas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIG_DBLIST_DESCRIPTION', 'Seleccione, de entre los disponibles, los estados que generen esta alarma.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIG_INFO', 'Información', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIG_SAVE', '¿Desea guardar la configuración de alarma?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIG_THRESHOLD', 'Umbral', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ALARM_CONFIG_THRESHOLDS', 'Umbrales', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_EDIT_ALARM_CONFIG', 'Editar configuración de alarma', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_EDIT_ALARM_CONFIG_DESCRIPTION', 'Formulario para editar configuración de alarma y umbrales', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_EDIT_THRESHOLD', 'Editar umbrales', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ELEMENT_TYPE_REQUIRED', 'Requerido seleccionar tipo de equipo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_ELEMENT_TYPE_STATE_WITH_ALARM', 'Cambio de estado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_NEW_THRESHOLD', 'Nuevo umbral', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_QUESTION_DELETE_ALARM_CONFIG_DESCRIPTION', '¿Desea eliminar la configuración de alarma?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_TREATMENT_REQUIRED', 'Requerido tratamiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_MIN_TIME_ALARMED', 'Permanencia mínima de alarma (secs)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_THRESHOLD_TIME', 'Tiempo mínimo de activación (secs)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_STATE', 'Estados', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--en_GB
('en_GB', 1, 'LBL_ADD_ALARM_CONFIG', 'Add alarm configuration', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ADD_ALARM_CONFIG_DESCRIPTION', 'Form to add new alarm configuration', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ADD_NEW_ALARM_CONFIG', 'Add new alarm config', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ALARM_CONFIGS_TITLE', 'Alarm configuration', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ALARM_CONFIG_DBLIST_DESCRIPTION', 'Select, from those available, the states that generate this alarm.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ALARM_CONFIG_INFO', 'Information', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ALARM_CONFIG_SAVE', 'Do you want to save the alarm settings?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ALARM_CONFIG_THRESHOLD', 'Threshold', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ALARM_CONFIG_THRESHOLDS', 'Thresholds', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_EDIT_ALARM_CONFIG', 'Edit alarm configuration', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_EDIT_ALARM_CONFIG_DESCRIPTION', 'Edit alarm configuration information and thresholds', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_EDIT_ALARM_CONFIG_PERM', 'Add, delete and edit alarm config', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_EDIT_ALARM_CONFIG_PERM_DESC', 'Add, delete and edit alarms config', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_EDIT_THRESHOLD', 'Edit thresholds', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ELEMENT_TYPE_REQUIRED', 'Required to select element type', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_ELEMENT_TYPE_STATE_WITH_ALARM', 'Status change', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_NEW_THRESHOLD', 'New threshold', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_QUESTION_DELETE_ALARM_CONFIG_DESCRIPTION', 'Do you want to delete the alarm settings?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_TREATMENT_REQUIRED', 'Treatment required', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_MIN_TIME_ALARMED', 'Minimum alarm duration (secs)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_THRESHOLD_TIME', 'Minimum activation time (secs)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_STATE', 'States', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--sk_SK
('sk_SK', 1, 'LBL_ADD_ALARM_CONFIG', 'Pridať konfiguráciu alarmu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_ADD_ALARM_CONFIG_DESCRIPTION', 'Formulár na pridanie novej konfigurácie alarmu a prahových hodnôt', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_ADD_NEW_ALARM_CONFIG', 'Pridať novú konfiguráciu alarmu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_ALARM_CONFIGS_TITLE', 'Konfigurácia alarmov', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_ALARM_CONFIG_DBLIST_DESCRIPTION', 'Vyberte z dostupných stavov tie, ktoré generujú tento alarm.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_ALARM_CONFIG_INFO', 'Informácie', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_ALARM_CONFIG_SAVE', 'Chcete uložiť konfiguráciu alarmu?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_ALARM_CONFIG_THRESHOLD', 'Prahové hodnoty', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_ALARM_CONFIG_THRESHOLDS', 'Prahové hodnoty', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_EDIT_ALARM_CONFIG', 'Upraviť konfiguráciu alarmu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_EDIT_ALARM_CONFIG_DESCRIPTION', 'Formulár na úpravu konfigurácie alarmu a prahových hodnôt', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_EDIT_ALARM_CONFIG_PERM', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_EDIT_ALARM_CONFIG_PERM_DESC', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_EDIT_THRESHOLD', 'Upraviť prahové hodnoty', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_ELEMENT_TYPE_REQUIRED', 'Je potrebné vybrať typ zariadenia', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_ELEMENT_TYPE_STATE_WITH_ALARM', 'Zmena stavu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_NEW_THRESHOLD', 'Nová prahová hodnota', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_QUESTION_DELETE_ALARM_CONFIG_DESCRIPTION', 'Chcete odstrániť konfiguráciu alarmu?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_TREATMENT_REQUIRED', 'Vyžadované spracovanie', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_MIN_TIME_ALARMED', 'Minimálne trvanie alarmu (sek)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_THRESHOLD_TIME', 'Minimálny čas aktivácie (sek)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_STATE', 'Stav', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--es_CL
('es_CL', 1, 'LBL_ADD_ALARM_CONFIG', 'Añadir configuración de alarma', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ADD_ALARM_CONFIG_DESCRIPTION', 'Formulario para añadir nueva configuración de alarma y umbrales', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ADD_NEW_ALARM_CONFIG', 'Añadir nueva configuración de alarma', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ALARM_CONFIGS_TITLE', 'Configuración de alarmas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ALARM_CONFIG_DBLIST_DESCRIPTION', 'Seleccione, de entre los disponibles, los estados que generen esta alarma.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ALARM_CONFIG_INFO', 'Información', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ALARM_CONFIG_SAVE', '¿Desea guardar la configuración de alarma?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ALARM_CONFIG_THRESHOLD', 'Umbral', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ALARM_CONFIG_THRESHOLDS', 'Umbrales', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_EDIT_ALARM_CONFIG', 'Editar configuración de alarma', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_EDIT_ALARM_CONFIG_DESCRIPTION', 'Formulario para editar configuración de alarma y umbrales', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_EDIT_THRESHOLD', 'Editar umbrales', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ELEMENT_TYPE_REQUIRED', 'Requerido seleccionar tipo de equipo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_ELEMENT_TYPE_STATE_WITH_ALARM', 'Cambio de estado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_NEW_THRESHOLD', 'Nuevo umbral', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_QUESTION_DELETE_ALARM_CONFIG_DESCRIPTION', '¿Desea eliminar la configuración de alarma?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_TREATMENT_REQUIRED', 'Requerido tratamiento', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_MIN_TIME_ALARMED', 'Permanencia mínima de alarma (secs)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_THRESHOLD_TIME', 'Tiempo mínimo de activación (secs)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_STATE', 'Estados', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

