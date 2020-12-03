INSERT INTO ui.adapter_types(id, "name", description, enabled, visible) VALUES
(1, 'VO TO GRID DATA', 'VO TO GRID DATA', true, true);

INSERT INTO ui.adapters(id, adapter_type_id, "name", description, file, enabled, visible) VALUES
(1, 1, 'ELEMENTS TO GRID DATA', 'ELEMENTS TO GRID DATA', 'groovy/adapter/grid/AdapterGridElement.groovy', true, true),
(2, 1, 'PLANS TO GRID DATA', 'PLANS TO GRID DATA', 'groovy/adapter/grid/AdapterGridGeneric.groovy', true, true);

INSERT INTO ui.adapter_params(id, adapter_id, pos, param, enabled, visible) VALUES
(1, 1, 1, 'elementTypeId', true, true);

INSERT INTO ui.adapter_data_sources(adapter_id, data_source_id) values
(1, 1),
(1, 3),
(2, 18);
