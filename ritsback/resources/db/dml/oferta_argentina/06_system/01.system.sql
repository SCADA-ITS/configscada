DELETE FROM conf.system_param_values;

-- 
-- Table: conf.system_param_values
--

INSERT INTO conf.system_param_values
(system_param_id, value, enabled, visible, created_at, updated_at) VALUES
(1, '30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '90', true, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, '60', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, '40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, '600', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, '0 * * ? * * *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Número máximo de alarmas permitidas sin reconocer
(14, '50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Período de cálculo para los tiempos de recorrido
(15, '5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Número máximo de sesiones simultáneas
(16, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Número máximo de log permitidas sin reconocer
(17, '100', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);