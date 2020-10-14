INSERT INTO ui.adapter_types(id, "name", description, enabled, visible) VALUES
(1, 'VO TO GRID DATA', 'VO TO GRID DATA', true, true);

INSERT INTO ui.adapters(id, adapter_type_id, "name", description, enabled, visible) VALUES
(2, 1, 'ELEMENTS TO GRID DATA BY ELEMENT_TYPE_ID', 'ELEMENTS TO GRID DATA BY ELEMENT_TYPE_ID', true, true);

INSERT INTO ui.adapter_params(id, adapter_id, pos, param, enabled, visible) VALUES
(1, 2, 1, 'elementTypeId', true, true);

INSERT INTO ui.adapter_data_sources(adapter_id, data_source_id) values
(2, 1),
(2, 3);
