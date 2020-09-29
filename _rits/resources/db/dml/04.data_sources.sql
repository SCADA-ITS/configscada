INSERT INTO ui.data_source_types (id, "name", description, enabled, visible) VALUES
(1, 'graphql', 'graphql data source', true, true);


INSERT INTO ui.data_source_params(id, data_source_type_id, "name", description, enabled, visible) VALUES
(1, 1, 'url', 'url graphql', true, true),
(2, 1, 'method', 'graphql method', true, true),
(3, 1, 'params', 'graphql params', true, true),
(4, 1, 'content', 'graphql content', true, true);

INSERT INTO ui.data_sources(id, data_source_type_id, "name", description, entity_vo, enabled, visible) VALUES
(1, 1, 'getElementsByType', 'getElementsByType', 'ElementVO', true, true),
(2, 1, 'getElementTypes', 'getElementTypes', 'ElementTypeVO', true, true);


INSERT INTO ui.data_source_values(id, data_source_id, data_source_param_id, value) VALUES
(1, 1, 1, 'http://localhost:8082/graphql'),
(2, 1, 2, 'getElementsByType'),
(3, 1, 3, 'elementTypeId'),
(4, 1, 4, '{id alias description state stateLabel elementValues{id value state elementTypeParam{id alias paramType dataType}}}'),
(5, 2, 1, 'http://localhost:8082/graphql'),
(6, 2, 2, 'getElementTypes'),
(7, 2, 3, ''),
(8, 2, 4, '{id alias description labelAlias labelDescription}');
