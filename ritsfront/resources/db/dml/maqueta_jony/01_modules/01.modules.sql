UPDATE ui.modules SET autoload=true WHERE id=1018;

INSERT INTO ui.modules(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES
-- Synoptics
(600, 4, 'Metro', 'Metro', 1, null, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(601, 4, 'CH Plano CT', 'CH Plano CT', 1, null, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(602, 4, 'CH Comunicaciones', 'CH Comunicaciones', 1, null, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(603, 4, 'CH Control tráfico', 'CH Control tráfico', 1, null, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(604, 4, 'CH Energía', 'CH Energía', 1, null, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(605, 4, 'CH General', 'CH General', 1, null, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(606, 4, 'CH Iluminacion', 'CH Iluminacion', 1, null, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(607, 4, 'CH PCI', 'CH PCI', 1, null, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),
(608, 4, 'CH Ventilación', 'CH Ventilación', 1, null, 'modules/synopticModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

(910, 1, 'EquipmentForm', 'Equipment form', 2, null, 'modules/equipmentFormModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL),

-- Maps
(970, 3, 'Map', 'Map', 1, null, 'modules/mapModule.js', 'getView', 'loadData', NULL, NULL, true, true, NULL);


INSERT INTO ui.module_actions(id, module_id, name, description, system_permission_id) VALUES
(91001, 910, 'view equipment', 'view equipment', NULL);
