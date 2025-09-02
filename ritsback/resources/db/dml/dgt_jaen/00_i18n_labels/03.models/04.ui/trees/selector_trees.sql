-- 
-- Type:   model
-- Entity: ui.selector_trees
-- File:   selector_trees.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--

INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
    ('es_ES', 1, 'LBL_SELECTOR_TREES_SINOPTICOS', 'Sinópticos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('es_ES', 1, 'LBL_SELECTOR_TREE_HISTORICAL_DATA_BACKOFFICE', 'Datos históricos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--en_GB
    ('en_GB', 1, 'LBL_SELECTOR_TREES_SINOPTICOS', 'Synoptics', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('en_GB', 1, 'LBL_SELECTOR_TREE_HISTORICAL_DATA_BACKOFFICE', 'Historical data', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--es_CL
    ('es_CL', 1, 'LBL_SELECTOR_TREES_SINOPTICOS', 'Sinópticos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('es_CL', 1, 'LBL_SELECTOR_TREE_HISTORICAL_DATA_BACKOFFICE', 'Datos históricos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

