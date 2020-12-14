INSERT INTO ui.adapter_types(id, "name", description, enabled, visible) VALUES
(1, 'VO TO GRID DATA', 'VO TO GRID DATA', true, true);

INSERT INTO ui.adapters(id, adapter_type_id, "name", description, file, enabled, visible) VALUES
(1, 1, 'GENERIC TO GRID DATA', 'GENERIC TO GRID DATA', 'groovy/adapter/grid/AdapterGridGeneric.groovy', true, true),
(2, 1, 'ELEMENTS TO GRID DATA', 'ELEMENTS TO GRID DATA', 'groovy/adapter/grid/AdapterGridElement.groovy', true, true),
(3, 1, 'USERS TO GRID DATA', 'USERS TO GRID DATA', 'groovy/adapter/grid/AdapterGridUser.groovy', true, true),
(4, 1, 'ALARMCONFIG TO GRID DATA', 'ALARMCONFIG TO GRID DATA', 'groovy/adapter/grid/AdapterGridAlarmConfig.groovy', true, true);

INSERT INTO ui.adapter_params(id, adapter_id, pos, param, enabled, visible) VALUES
(1, 2, 1, 'elementTypeId', true, true);

INSERT INTO ui.adapter_data_sources(adapter_id, data_source_id) values
(2, 1),
(2, 3),
(3, 9),
(1, 44),
(4, 23);
