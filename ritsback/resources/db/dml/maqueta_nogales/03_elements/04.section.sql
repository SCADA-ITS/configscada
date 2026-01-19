INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(4, 1, 1, 0010004, NULL, NULL, 'NO-S1S1-SEC-OP', 'NO-S1S1-SEC-OP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 1, 2, 0020004, NULL, NULL, 'NO-S1S1-SEC-PO', 'NO-S1S1-SEC-PO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 1, 3, 0030004, NULL, NULL, 'NO-S1S2-SEC-OP', 'NO-S1S2-SEC-OP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 1, 4, 0040004, NULL, NULL, 'NO-S1S2-SEC-PO', 'NO-S1S2-SEC-PO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 1, 5, 0050004, NULL, NULL, 'NO-S1S3-SEC-OP', 'NO-S1S3-SEC-OP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 1, 6, 0060004, NULL, NULL, 'NO-S1S3-SEC-PO', 'NO-S1S3-SEC-PO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 1, 7, 0070004, NULL, NULL, 'NO-S2-SEC-OP', 'NO-S2-SEC-OP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 1, 8, 0080004, NULL, NULL, 'NO-S2-SEC-PO', 'NO-S2-SEC-PO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(4, 1, 1, 1, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 1, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 3, 1, 1, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 4, 1, 1, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 5, 1, 1, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 6, 1, 1, NULL, '[8, 32, 72]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 7, 1, 1, NULL, '[16, 28, 35]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 80, 1, 1, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 81, 1, 1, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 82, 1, 1, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 83, 1, 1, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 100, 1, 1, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 101, 1, 1, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 102, 1, 1, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 103, 1, 1, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 104, 1, 1, NULL, '[5]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 105, 1, 1, NULL, '[6]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 106, 1, 1, NULL, '[7]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 2, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 2, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 3, 1, 2, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 4, 1, 2, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 5, 1, 2, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 6, 1, 2, NULL, '[8, 32, 72]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 7, 1, 2, NULL, '[16, 28, 35]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 80, 1, 2, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 81, 1, 2, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 82, 1, 2, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 83, 1, 2, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 100, 1, 2, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 101, 1, 2, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 102, 1, 2, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 103, 1, 2, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 104, 1, 2, NULL, '[5]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 105, 1, 2, NULL, '[6]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 106, 1, 2, NULL, '[7]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 3, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 3, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 3, 1, 3, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 4, 1, 3, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 5, 1, 3, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 6, 1, 3, NULL, '[8, 32, 72]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 7, 1, 3, NULL, '[16, 28, 35]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 80, 1, 3, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 81, 1, 3, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 82, 1, 3, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 83, 1, 3, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 100, 1, 3, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 101, 1, 3, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 102, 1, 3, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 103, 1, 3, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 104, 1, 3, NULL, '[5]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 105, 1, 3, NULL, '[6]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 106, 1, 3, NULL, '[7]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 4, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 4, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 3, 1, 4, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 4, 1, 4, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 5, 1, 4, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 6, 1, 4, NULL, '[8, 32, 72]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 7, 1, 4, NULL, '[16, 28, 35]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 80, 1, 4, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 81, 1, 4, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 82, 1, 4, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 83, 1, 4, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 100, 1, 4, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 101, 1, 4, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 102, 1, 4, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 103, 1, 4, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 104, 1, 4, NULL, '[5]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 105, 1, 4, NULL, '[6]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 106, 1, 4, NULL, '[7]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 5, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 5, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 3, 1, 5, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 4, 1, 5, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 5, 1, 5, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 6, 1, 5, NULL, '[8, 32, 72]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 7, 1, 5, NULL, '[16, 28, 35]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 80, 1, 5, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 81, 1, 5, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 82, 1, 5, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 83, 1, 5, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 100, 1, 5, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 101, 1, 5, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 102, 1, 5, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 103, 1, 5, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 104, 1, 5, NULL, '[5]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 105, 1, 5, NULL, '[6]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 106, 1, 5, NULL, '[7]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 6, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 6, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 3, 1, 6, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 4, 1, 6, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 5, 1, 6, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 6, 1, 6, NULL, '[8, 32, 72]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 7, 1, 6, NULL, '[16, 28, 35]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 80, 1, 6, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 81, 1, 6, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 82, 1, 6, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 83, 1, 6, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 100, 1, 6, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 101, 1, 6, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 102, 1, 6, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 103, 1, 6, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 104, 1, 6, NULL, '[5]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 105, 1, 6, NULL, '[6]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 106, 1, 6, NULL, '[7]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 7, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 7, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 3, 1, 7, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 4, 1, 7, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 5, 1, 7, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 6, 1, 7, NULL, '[8, 32, 72]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 7, 1, 7, NULL, '[16, 28, 35]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 80, 1, 7, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 81, 1, 7, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 82, 1, 7, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 83, 1, 7, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 100, 1, 7, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 101, 1, 7, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 102, 1, 7, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 103, 1, 7, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 104, 1, 7, NULL, '[5]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 105, 1, 7, NULL, '[6]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 106, 1, 7, NULL, '[7]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(4, 1, 1, 8, NULL, 'true', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 8, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 3, 1, 8, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 4, 1, 8, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 5, 1, 8, NULL, 'false', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 6, 1, 8, NULL, '[8, 32, 72]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 7, 1, 8, NULL, '[16, 28, 35]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 80, 1, 8, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 81, 1, 8, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 82, 1, 8, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 83, 1, 8, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 100, 1, 8, NULL, '[1]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 101, 1, 8, NULL, '[2]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 102, 1, 8, NULL, '[3]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 103, 1, 8, NULL, '[4]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 104, 1, 8, NULL, '[5]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 105, 1, 8, NULL, '[6]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 106, 1, 8, NULL, '[7]', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(4, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(4, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

delete from master.element_subtype_active_params where element_type_id = 4 and element_subtype_id = 1;

update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 5;
update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 140;
update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 141;
update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 142;
update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 143;

update master.i18n_labels set translation = 'Sección PAT' where label = 'LBL_ELEMENT_SUBTYPE_SECTION_KINEO' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Sección PAT' where label = 'LBL_ELEMENT_SUBTYPE_SECTION_KINEO_DESC' and locale_code = 'es_CL';

update master.i18n_labels set translation = 'Catg. Vel 1 (<= 60 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_SPEED_1' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 2 (61-100 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_SPEED_2' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 3 (101-120 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_SPEED_3' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 4 (>= 120 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_SPEED_4' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 1 (<= 60 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_SPEED_1_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 2 (61-100 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_SPEED_2_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 3 (101-120 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_SPEED_3_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 4 (>= 120 (km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_SPEED_4_DESC' and locale_code = 'es_CL';

update master.i18n_labels set translation = 'Clase 1 = Autos y camionetas' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_1' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 2 = Camionetas doble rueda trasera y/o autos y camionetas con remolque' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_2' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 3 = Camiones de dos ejes, maquinaria agrícola o de construcción ' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_3' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 4 = Motos, cuatrimotos y motonetas' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_4' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 5 = Buses de dos ejes' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_5' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 6 = Buses de más de dos ejes' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_6' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 7 = Motos y motonetas' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_7' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 1 = Autos y camionetas' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_1_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 2 = Camionetas doble rueda trasera y/o autos y camionetas con remolque' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_2_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 3 = Camiones de dos ejes, maquinaria agrícola o de construcción ' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_3_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 4 = Motos, cuatrimotos y motonetas' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_4_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 5 = Buses de dos ejes' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_5_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 6 = Buses de más de dos ejes' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_6_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 7 = Motos y motonetas' where label = 'LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_7_DESC' and locale_code = 'es_CL';


INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(1, 4, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 4, 2, 80, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 81, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 82, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 83, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 4, 2, 160, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 4, 2, 140, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 141, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 142, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 143, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 144, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 145, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 146, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
