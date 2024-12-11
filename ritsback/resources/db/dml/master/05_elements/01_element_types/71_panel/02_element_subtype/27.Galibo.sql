-- 
-- Table: master.element_subtypes
--
INSERT INTO master.element_subtypes(element_subtype_id, element_type_id, alias, description,label_alias, label_description, enabled, visible, created_at, updated_at)
VALUES
(27, 71, 'VMS_GALIBO', 'VMS_GALIBO', 'LBL_ELEMENT_SUBTYPE_PANEL_VMS_GALIBO', 'LBL_ELEMENT_SUBTYPE_PANEL_VMS_GALIBO_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: master.element_subtype_active_params 
--
INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at)VALUES
(27, 71, 2, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 71, 2, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


