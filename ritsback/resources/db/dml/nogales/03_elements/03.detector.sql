INSERT INTO conf.elements (element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES
(3, 1, 1, 0010003, NULL, NULL, 'NO-S1S1-DET-1OP', 'NO-S1S1-DET-1OP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 2, 0020003, NULL, NULL, 'NO-S1S1-DET-2OP', 'NO-S1S1-DET-2OP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 3, 0030003, NULL, NULL, 'NO-S1S1-DET-1PO', 'NO-S1S1-DET-1PO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 4, 0040003, NULL, NULL, 'NO-S1S1-DET-2PO', 'NO-S1S1-DET-2PO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 5, 0050003, NULL, NULL, 'NO-S1S2-DET-1OP', 'NO-S1S2-DET-1OP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 6, 0060003, NULL, NULL, 'NO-S1S2-DET-2OP', 'NO-S1S2-DET-2OP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 7, 0070003, NULL, NULL, 'NO-S1S2-DET-1PO', 'NO-S1S2-DET-1PO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 8, 0080003, NULL, NULL, 'NO-S1S2-DET-2PO', 'NO-S1S2-DET-2PO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 9, 0090003, NULL, NULL, 'NO-S1S3-DET-1OP', 'NO-S1S3-DET-1OP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 10, 0100003, NULL, NULL, 'NO-S1S3-DET-2OP', 'NO-S1S3-DET-2OP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 11, 0110003, NULL, NULL, 'NO-S1S3-DET-1PO', 'NO-S1S3-DET-1PO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 12, 0120003, NULL, NULL, 'NO-S1S3-DET-2PO', 'NO-S1S3-DET-2PO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 13, 0130003, NULL, NULL, 'NO-S2-DET-1OP', 'NO-S2-DET-1OP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 14, 0140003, NULL, NULL, 'NO-S2-DET-2OP', 'NO-S2-DET-2OP', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 15, 0150003, NULL, NULL, 'NO-S2-DET-1PO', 'NO-S2-DET-1PO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1, 16, 0160003, NULL, NULL, 'NO-S2-DET-2PO', 'NO-S2-DET-2PO', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_values (element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES
(3, 1, 1, 1, NULL, 'OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 1, NULL, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 1, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 1, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 1, NULL, 'NO-S1S1-DET-1OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 2, NULL, 'OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 2, NULL, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 2, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 2, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 2, NULL, 'NO-S1S1-DET-2OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 3, NULL, 'PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 3, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 3, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 3, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 3, NULL, 'NO-S1S1-DET-1PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 4, NULL, 'PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 4, NULL, '4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 4, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 4, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 4, NULL, 'NO-S1S1-DET-2PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 5, NULL, 'OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 5, NULL, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 5, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 5, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 5, NULL, 'NO-S1S2-DET-1OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 6, NULL, 'OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 6, NULL, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 6, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 6, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 6, NULL, 'NO-S1S2-DET-2OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 7, NULL, 'PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 7, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 7, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 7, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 7, NULL, 'NO-S1S2-DET-1PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 8, NULL, 'PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 8, NULL, '4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 8, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 8, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 8, NULL, 'NO-S1S2-DET-2PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 9, NULL, 'OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 9, NULL, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 9, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 9, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 9, NULL, 'NO-S1S3-DET-1OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 10, NULL, 'OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 10, NULL, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 10, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 10, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 10, NULL, 'NO-S1S3-DET-2OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 11, NULL, 'PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 11, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 11, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 11, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 11, NULL, 'NO-S1S3-DET-1PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 12, NULL, 'PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 12, NULL, '4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 12, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 12, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 12, NULL, 'NO-S1S3-DET-2PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 13, NULL, 'OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 13, NULL, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 13, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 13, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 13, NULL, 'NO-S2-DET-1OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 14, NULL, 'OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 14, NULL, '2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 14, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 14, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 14, NULL, 'NO-S2-DET-2OP', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 15, NULL, 'PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 15, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 15, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 15, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 15, NULL, 'NO-S2-DET-1PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

(3, 1, 1, 16, NULL, 'PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 16, NULL, '4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 16, NULL, '0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 16, NULL, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 1003, 1, 16, NULL, 'NO-S2-DET-2PO', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(3, 1, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 2, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 3, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 4, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 5, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 6, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 7, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 8, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 9, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 10, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 11, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 12, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 13, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 14, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 15, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(3, 16, 1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 5;
update master.element_type_params set element_type_param_unit_id = 11 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 17;
update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 100;
update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 101;
update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 102;
update master.element_type_params set element_type_param_unit_id = 37 where element_type_id = 3 and param_type_id = 2 and element_type_param_id = 103;

delete from master.element_subtype_active_params where element_type_id = 3 and element_subtype_id = 1;

INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(1, 3, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 3, 2, 40, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 41, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 42, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 43, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 3, 2, 100, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 101, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 102, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 2, 103, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(1, 3, 2, 120, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


update master.i18n_labels set translation = 'Catg. Vel 1 (<= 60 km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_1' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 2 (61-100 km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_2' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 3 (101-120 km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_3' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 4 (>= 120 km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_4' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 1 (<= 60 km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_1_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 2 (61-100 km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_2_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 3 (101-120 km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_3_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Catg. Vel 4 (>= 120 km/h)' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_4_DESC' and locale_code = 'es_CL';

update master.i18n_labels set translation = 'Clase 1 = Autos y camionetas' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_1' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 2 = Camionetas doble rueda trasera y/o autos y camionetas con remolque' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_2' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 3 = Camiones de dos ejes, maquinaria agrícola o de construcción ' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_3' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 4 = Motos, cuatrimotos y motonetas' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_4' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 1 = Autos y camionetas' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_1_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 2 = Camionetas doble rueda trasera y/o autos y camionetas con remolque' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_2_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 3 = Camiones de dos ejes, maquinaria agrícola o de construcción ' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_3_DESC' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Clase 4 = Motos, cuatrimotos y motonetas' where label = 'LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_4_DESC' and locale_code = 'es_CL';

update master.i18n_labels set translation = 'Detector PAT' where label = 'LBL_ELEMENT_SUBTYPE_DETECTOR_KINEO' and locale_code = 'es_CL';
update master.i18n_labels set translation = 'Detector PAT' where label = 'LBL_ELEMENT_SUBTYPE_DETECTOR_KINEO_DESC' and locale_code = 'es_CL';


