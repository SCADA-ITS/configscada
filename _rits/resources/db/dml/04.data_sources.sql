INSERT INTO ui.data_source_types (id, "name", description, enabled, visible) VALUES
(1, 'graphql', 'graphql data source', true, true),
(2, 'stomp_ws', 'Stomp Over WebSocket data source', true, true);


INSERT INTO ui.data_source_params(id, data_source_type_id, "name", description, enabled, visible) VALUES
(1, 1, 'url', 'url graphql', true, true),
(2, 1, 'method', 'graphql method', true, true),
(3, 1, 'params', 'graphql params', true, true),
(4, 1, 'content', 'graphql content', true, true),
(5, 2, 'url', 'url graphql', true, true),
(6, 2, 'topic', 'graphql method', true, true),
(7, 2, 'params', 'graphql params', true, true);

INSERT INTO ui.data_sources(id, data_source_type_id, "name", description, entity_vo, enabled, visible) VALUES
(1, 1, 'getElementsByType', 'getElementsByType', 'ElementVO', true, true),
(2, 1, 'getElementTypes', 'getElementTypes', 'ElementTypeVO', true, true),
(3, 2, 'topicElementByType', 'topicElementByType', 'ElementVO', true, true),
(4, 1, 'getElementTypeById', 'getElementType', 'ElementTypeVO', true, true),
(5, 1, 'getElements', 'getElements', 'ElementVO', true, true),
(6, 1, 'getElement', 'getElement', 'ElementVO', true, true);


INSERT INTO ui.data_source_values(id, data_source_id, data_source_param_id, value) VALUES
(1, 1, 1, 'http://localhost:8082/graphql'),
(2, 1, 2, 'getElementsByType'),
(3, 1, 3, 'elementTypeId'),
(4, 1, 4, '{id alias description state stateLabel elementValues{id value state elementTypeParam{id alias paramType dataType}}}'),
(5, 2, 1, 'http://localhost:8082/graphql'),
(6, 2, 2, 'getElementTypes'),
(7, 2, 3, ''),
(8, 2, 4, '{id alias description labelAlias labelDescription}'),
(9, 3, 1, 'ws://192.168.244.12:61614'),
(10, 3, 2, '/topic/element'),
(11, 3, 3, null),
(12, 4, 1, 'http://localhost:8082/graphql'),
(13, 4, 2, 'getElementType'),
(14, 4, 3, 'elementTypeId'),
(15, 4, 4, '{id alias description labelAlias labelDescription}'),
(16, 5, 1, 'http://localhost:8082/graphql'),
(17, 5, 2, 'getElements'),
(18, 5, 3, ''),
(19, 5, 4, '{id alias description state stateLabel elementValues{id value state elementTypeParam{id alias paramType dataType}}}'),
(20, 6, 1, 'http://localhost:8082/graphql'),
(21, 6, 2, 'getElement'),
(22, 6, 3, 'elementId'),
(23, 6, 4, '{id alias description state stateLabel elementValues{id value state elementTypeParam{id alias paramType dataType}}}');
