UPDATE conf.element_values
SET value='[{"id": 1,"alias": "Preset 1","description": "Panorámica hacia el Sur","default": true},{"id": 2,"alias": "Preset 2","description": "Panorámica hacia el Norte","default": false}]'
WHERE element_type_id=2 AND element_type_param_id=6 AND param_type_id=1 AND element_id between 1 and 20;

UPDATE conf.element_values
SET value='10.50.2.10'
WHERE element_type_id=2 AND element_type_param_id=9 AND param_type_id=1 AND element_id=12;

UPDATE conf.element_values
SET value='10.50.2.11'
WHERE element_type_id=2 AND element_type_param_id=9 AND param_type_id=1 AND element_id=13;

UPDATE conf.element_values
SET value='10.50.2.12'
WHERE element_type_id=2 AND element_type_param_id=9 AND param_type_id=1 AND element_id=14;

UPDATE conf.element_values
SET value='10.50.2.13'
WHERE element_type_id=2 AND element_type_param_id=9 AND param_type_id=1 AND element_id=15;

UPDATE conf.element_values
SET value='10.50.2.14'
WHERE element_type_id=2 AND element_type_param_id=9 AND param_type_id=1 AND element_id=16;

