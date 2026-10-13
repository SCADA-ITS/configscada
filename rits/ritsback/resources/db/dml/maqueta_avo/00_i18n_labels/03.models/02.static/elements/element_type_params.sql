-- 
-- Type:   model
-- Entity: master.element_type_params
-- File:   element_subtypes.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

--es_CL
    --ElementType:3
    UPDATE master.i18n_labels SET "translation"='Autos y camionetas', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_1';
    UPDATE master.i18n_labels SET "translation"='Buses y camiones', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_2';
    UPDATE master.i18n_labels SET "translation"='Camiones con remolque', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_3';
    UPDATE master.i18n_labels SET "translation"='Motos', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_PA_4';

    UPDATE master.i18n_labels SET "translation"='<50 km/h', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_1';
    UPDATE master.i18n_labels SET "translation"='50-100 km/h', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_2';
    UPDATE master.i18n_labels SET "translation"='>100 km/h', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_DETECTOR_VEH_SPEED_3';

    --ElementType:4
    UPDATE master.i18n_labels SET "translation"='Autos y camionetas', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_1';
    UPDATE master.i18n_labels SET "translation"='Buses y camiones', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_2';
    UPDATE master.i18n_labels SET "translation"='Camiones con remolque', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_3';
    UPDATE master.i18n_labels SET "translation"='Motos', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_PA_4';

    UPDATE master.i18n_labels SET "translation"='<50 km/h', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_SPEED_1';
    UPDATE master.i18n_labels SET "translation"='50-100 km/h', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_SPEED_2';
    UPDATE master.i18n_labels SET "translation"='>100 km/h', created_at=CURRENT_TIMESTAMP, updated_at=CURRENT_TIMESTAMP WHERE locale_code='es_CL' AND module_id=1 AND "label"='LBL_ELEMENT_TYPE_PARAM_SECTION_VEH_SPEED_3';

