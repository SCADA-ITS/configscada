-- 
-- Type:  model
-- Entity: master.element_subtypes
-- File:  element_subtypes.sql
-- Langs: es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

UPDATE master.i18n_labels
SET "translation"='Semáforo rojo rojo'
WHERE locale_code='es_ES' AND module_id=1 AND 
    "label" in ('LBL_ELEMENT_SUBTYPE_SEM_ROJO_ROJO_ERU', 'LBL_ELEMENT_SUBTYPE_SEM_ROJO_ROJO_ERU_DESC');

UPDATE master.i18n_labels
SET "translation"='Red red traffic light '
WHERE locale_code='en_GB' AND module_id=1 AND 
    "label" in ('LBL_ELEMENT_SUBTYPE_SEM_ROJO_ROJO_ERU', 'LBL_ELEMENT_SUBTYPE_SEM_ROJO_ROJO_ERU_DESC');

UPDATE master.i18n_labels
SET "translation"=' semafor červ.-červ.'
WHERE locale_code='sk_SK' AND module_id=1 AND 
    "label" in ('LBL_ELEMENT_SUBTYPE_SEM_ROJO_ROJO_ERU', 'LBL_ELEMENT_SUBTYPE_SEM_ROJO_ROJO_ERU_DESC');

UPDATE master.i18n_labels
SET "translation"='Semáforo rojo rojo'
WHERE locale_code='es_CL' AND module_id=1 AND 
    "label" in ('LBL_ELEMENT_SUBTYPE_SEM_ROJO_ROJO_ERU', 'LBL_ELEMENT_SUBTYPE_SEM_ROJO_ROJO_ERU_DESC');