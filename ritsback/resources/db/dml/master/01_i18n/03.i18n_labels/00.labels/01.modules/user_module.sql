-- 
-- Type:   modules
-- Entity: User module
-- File:   user_module.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
-- es_ES
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_ES', 1, 'LBL_QUESTION_SAVE_USER_DESCRIPTION', '¿Esta seguro de guardar los cambios realizados?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_QUESTION_SAVE_ROLE_DESCRIPTION', '¿Esta seguro de guardar los cambios realizados?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_SELECT_LOCATE', 'Seleccionar localizacioń', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LOCALE_CODE', 'Codigo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_LOCALE_NAME', 'Localización', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_PERMISSION_IS_ALREADY_IN_USE_DESCRIPTION', 'El permiso ya habia sido asignado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_USER_WITH_ALL_PERMISSIONS_ASIGNED', 'Usuario con todos los permisos asignados', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- en_GB
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('en_GB', 1, 'LBL_QUESTION_SAVE_USER_DESCRIPTION', '¿Are you sure to save the changes?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_QUESTION_SAVE_ROLE_DESCRIPTION', '¿Are you sure to save the changes?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_SELECT_LOCATE', 'Select locale', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LOCALE_CODE', 'Code', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_LOCALE_NAME', 'Locale', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_PERMISSION_IS_ALREADY_IN_USE_DESCRIPTION', 'Permission had already been assigned', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_USER_WITH_ALL_PERMISSIONS_ASIGNED', 'User with all permissions asigned', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- es_CL
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
('es_CL', 1, 'LBL_QUESTION_SAVE_USER_DESCRIPTION', '¿Esta seguro de guardar los cambios realizados?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_QUESTION_SAVE_ROLE_DESCRIPTION', '¿Esta seguro de guardar los cambios realizados?', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_SELECT_LOCATE', 'Seleccionar localizacioń', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_LOCALE_CODE', 'Codigo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_LOCALE_NAME', 'Localización', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_PERMISSION_IS_ALREADY_IN_USE_DESCRIPTION', 'El permiso ya habia sido asignado', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_USER_WITH_ALL_PERMISSIONS_ASIGNED', 'Usuario con todos los permisos asignados', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
