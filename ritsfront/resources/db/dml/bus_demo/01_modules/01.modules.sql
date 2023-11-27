--UPDATE ui.modules SET autoload=true WHERE id=1018;

INSERT INTO ui.modules(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES

-- DATOS MAESTROS
(1920, 8, 'Selector Tree Module. Datos maestros', 'Selector Tree Module. Datos maestros', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	-- Seccion Geográfica
	(192001, 9, 'BUS DEMO - Zonas MUGI', 'BUS DEMO - Zonas MUGI', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	(192002, 9, 'BUS DEMO - Zonas MUGI/Saltos', 'BUS DEMO - Zonas MUGI/Saltos', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	(192003, 9, 'BUS DEMO - Municipios', 'BUS DEMO - Municipios', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	-- Seccion Administrativa
	(192004, 9, 'BUS DEMO - Consorcios', 'BUS DEMO - Consorcios', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	-- Seccion Topológica
	(192005, 9, 'BUS DEMO - Superoperadores', 'BUS DEMO - Superoperadores', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	(192006, 9, 'BUS DEMO - Líneas', 'BUS DEMO - Líneas', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	(192007, 9, 'BUS DEMO - Paradas', 'BUS DEMO - Paradas', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	(192008, 9, 'BUS DEMO - Transbordo exclusiones', 'BUS DEMO - Transbordo exclusiones', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	-- Seccion Monética
	(192009, 9, 'BUS DEMO - Títulos', 'BUS DEMO - Títulos', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	(192010, 9, 'BUS DEMO - Tarifas', 'BUS DEMO - Tarifas', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	(192011, 9, 'BUS DEMO - Origenes/Destino vta. anticipada', 'BUS DEMO - Origenes/Destino vta. anticipada', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	-- Seccion Recursos
	(192012, 9, 'BUS DEMO - Vehículos', 'BUS DEMO - Vehículos', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	(192013, 9, 'BUS DEMO - Empleados', 'BUS DEMO - Empleados', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	(192014, 9, 'BUS DEMO - Dispositivos', 'BUS DEMO - Dispositivos', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	-- Seccion Planificación
	(192015, 9, 'BUS DEMO - Turnos planificados', 'BUS DEMO - Turnos planificados', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	(192016, 9, 'BUS DEMO - Expediciones planificadas', 'BUS DEMO - Expediciones planificadas', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
	-- Seccion Parámetros
	(192017, 9, 'BUS DEMO - Parámetros', 'BUS DEMO - Parámetros', 2, null, 'modules/backOffice/backOfficeModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
-- PROCESOS
(1921, 8, 'Selector Tree Module. Procesos', 'Selector Tree Module. Procesos', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
-- VALIDACIONES Y VENTAS
(1922, 8, 'Selector Tree Module. Validaciones y ventas', 'Selector Tree Module. Validaciones y ventas', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
-- TURNOS Y EXPEDICIONES
(1923, 8, 'Selector Tree Module. Turnos y expediciones', 'Selector Tree Module. Turnos y expediciones', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
-- LIQUIDACION PERSONAL DE CONDUCCIÓN
(1924, 8, 'Selector Tree Module. Liquidación personal de conducción', 'Selector Tree Module. Liquidación personal de conducción', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, null),
-- CONSULTAS
(1925, 8, 'Selector Tree Module. Consultas', 'Selector Tree Module. Consultas', 1, null, 'modules/selectorModule.js', 'getView', 'loadData', NULL, NULL, true, true, null);



UPDATE ui.modules SET enabled = false WHERE id IN(11, 12, 14, 15, 18);
