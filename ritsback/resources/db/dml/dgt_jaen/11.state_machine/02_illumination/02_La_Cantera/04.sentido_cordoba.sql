INSERT INTO static.sm_state_machines
(state_machine_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Túnel (2 cifras), coincidiendo con el id del fichero
-- 2.- Orden de la maquina de estados (2 cifras)
(0401, 'LIGHTING_SERVICE_LA_CANTERA_SENTIDO_CORDOBA_AUT', 'States machine to calulate service lighting regime in La Cantera Aut', NULL, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO static.sm_states
(state_machine_id, state_id, alias, description, label_alias, label_description, init_state, t_min, enabled, visible, created_at, updated_at) VALUES
-- Estados para la única máquina estados que tiene este túnel
(0401, 1, 'INIT', 'Init', 'LBL_SM_STATE_INIT', NULL, true, 0, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0401, 2, 'SOLEADO', 'SOLEADO', 'LBL_SM_STATE_SOLEADO', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0401, 3, 'NUBLADO', 'NUBLADO', 'LBL_SM_STATE_NUBLADO', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0401, 4, 'CREPUSCULAR', 'CREPUSCULAR', 'LBL_SM_STATE_CREPUSCULAR', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0401, 5, 'NOCTURNO', 'NOCTURNO', 'LBL_SM_STATE_NOCTURNO', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0401, 6, 'EXTERIOR/NOCTURNO', 'EXTERIOR/NOCTURNO', 'LBL_SM_STATE_EXTERIOR_NOCTURNO', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO static.sm_transitions
(state_machine_id, state_id, transition_id, nexus_and, next_state_id, enabled, visible, created_at, updated_at) VALUES
-- La Cantera - Ponemos todas las posibles transiciones entre estados de sentido Córdoba
-- Desde el estado inicial podremos transitar al resto de estados. De cada estado podremos transitar a los otros
-- posibles estados, pero nunca podremos volver al inicial.
-- Desde Estado Inicial al resto de estados
(0401, 1, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> SOLEADO
(0401, 1, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> CREPUSCULAR
(0401, 1, 3, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> NUBLADO
(0401, 1, 4, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> NOCTURNO
(0401, 1, 5, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> EXTERIOR/NOCTURNO
-- Desde Soleado al resto de estados
(0401, 2, 1, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SOLEADO --> CREPUSCULAR
(0401, 2, 2, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SOLEADO --> NUBLADO
(0401, 2, 3, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SOLEADO --> NOCTURNO
(0401, 2, 4, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SOLEADO --> EXTERIOR/NOCTURNO
-- Desde Nublado al resto de estados
(0401, 3, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NUBLADO --> SOLEADO
(0401, 3, 2, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NUBLADO --> CREPUSCULAR
(0401, 3, 3, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NUBLADO --> NOCTURNO
(0401, 3, 4, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NUBLADO --> EXTERIOR/NOCTURNO
-- Desde Crepuscular al resto de estados
(0401, 4, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- CREPUSCULAR --> SOLEADO
(0401, 4, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- CREPUSCULAR --> NUBLADO
(0401, 4, 3, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- CREPUSCULAR --> NOCTURNO
(0401, 4, 4, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- CREPUSCULAR --> EXTERIOR/NOCTURNO
-- Desde Nocturno al resto de estados
(0401, 5, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NOCTURNO --> SOLEADO
(0401, 5, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NOCTURNO --> CREPUSCULAR
(0401, 5, 3, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NOCTURNO --> NUBLADO
(0401, 5, 4, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NOCTURNO --> EXTERIOR/NOCTURNO
-- Desde Exterior/Nocturno al resto de estados
(0401, 6, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- EXTERIOR/NOCTURNO --> SOLEADO
(0401, 6, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- EXTERIOR/NOCTURNO --> CREPUSCULAR
(0401, 6, 3, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- EXTERIOR/NOCTURNO --> NUBLADO
(0401, 6, 4, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- EXTERIOR/NOCTURNO --> NOCTURNO


INSERT INTO static.sm_thresholds
(threshold_id, alias, description, label_alias, label_description, data_type_id, value, enabled, visible, created_at, updated_at) VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Túnel (2 cifras), coincidiendo con el id del fichero
-- 2.- Orden del umbral (2 cifras)
(0401, 'Threshold_LUM_2500', 'Threshold_LUM_2500', 'LBL_THRESHOLD_LUM_2500', 'LBL_THRESHOLD_LUM_2500', 3, '2500', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),							-- Soleado > 1000 lux
(0402, 'Threshold_LUM_1000', 'Threshold_LUM_1000', 'LBL_THRESHOLD_LUM_1000', 'LBL_THRESHOLD_LUM_1000', 3, '1000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	-- Crepusuclar < 1000 lux y > 800 lux
(0403, 'Threshold_LUM_300', 'Threshold_LUM_300', 'LBL_THRESHOLD_LUM_300', 'LBL_THRESHOLD_LUM_300', 3, '300', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	-- Nublado < 800 lux y > 500 lux
(0404, 'Threshold_LUM_200', 'Threshold_LUM_200', 'LBL_THRESHOLD_LUM_200', 'LBL_THRESHOLD_LUM_200', 3, '200', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);	-- Nocturno < 500 lux

INSERT INTO static.sm_conditions
(condition_id, threshold_operation_id, threshold_val1_id, threshold_val2_id, element_type_id, element_type_param_id, param_type_id, timer_id, enabled, visible, created_at, updated_at)VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Túnel (2 cifras), coincidiendo con el id del fichero
-- 2.- Orden de la condición (2 cifras)
-- Operations: 1 = Entre; 4 = Mayor que; 6 = Menor que
(0401, 4, 0401, NULL, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- > 2500 lux
(0402, 1, 0402, 0401, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- < 2500 lux y > 1000 lux
(0403, 1, 0403, 0402, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- < 1000 lux y > 300 lux
(0404, 1, 0404, 0403, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- < 300 lux y > 200 lux
(0405, 6, 0404, NULL, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- < 200 lux

INSERT INTO static.sm_transition_conditions
(state_machine_id, state_id, transition_id, condition_id, enabled, visible, created_at, updated_at)VALUES
--CONDICIONES PARA TRANSITAR DESDE EL ESTADO INICIAL AL RESTO DE ESTADOS


-- De inicial a SOLEADO se debe cumplir que: 
--	1.	> 2500 lux
(0401, 1, 1, 0401, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De inicial a NUBLADO se debe cumplir que: 
--	2.	< 2500 lux y > 1000 lux
(0401, 1, 2, 0402, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De inicial a CREPUSCULAR se debe cumplir que: 
--	3.	< 1000 lux y > 300 lux
(0401, 1, 3, 0403, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De inicial a NOCTURNO se debe cumplir que: 
--	4.	< 300 lux y > 200 lux
(0401, 1, 4, 0404, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De inicial a EXTERIOR/NOCTURNO se debe cumplir que: 
--	5.	< 200 lux
(0401, 1, 5, 0405, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),



-- De SOLEADO a NUBLADO se debe cumplir que: 
--	2.	< 2500 lux y > 1000 lux
(0401, 2, 1, 0402, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De SOLEADO a CREPUSCULAR se debe cumplir que: 
--	3.	< 1000 lux y > 300 lux
(0401, 2, 2, 0403, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De SOLEADO a NOCTURNO se debe cumplir que: 
--	4.	< 300 lux y > 200 lux
(0401, 2, 3, 0404, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De SOLEADO a EXTERIOR/NOCTURNO se debe cumplir que: 
--	5.	< 200 lux
(0401, 2, 4, 0405, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),



-- De NUBLADO a SOLEADO se debe cumplir que: 
--	1.	> 2500 lux	
(0401, 3, 1, 0401, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NUBLADO a CREPUSCULAR se debe cumplir que: 
--	3.	< 1000 lux y > 300 lux
(0401, 3, 2, 0403, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NUBLADO a NOCTURNO se debe cumplir que: 
--	4.	< 300 lux y > 200 lux
(0401, 3, 3, 0404, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NUBLADO a EXTERIOR/NOCTURNO se debe cumplir que: 
--	5.	< 200 lux
(0401, 3, 4, 0405, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),



-- De CREPUSCULAR a SOLEADO se debe cumplir que: 
--	1.	> 2500 lux	
(0401, 4, 1, 0401, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De CREPUSCULAR a NUBLADO se debe cumplir que: 
--	2.	< 2500 lux y > 1000 lux
(0401, 4, 2, 0402, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De CREPUSCULAR a NOCTURNO se debe cumplir que: 
--	4.	< 300 lux y > 200 lux
(0401, 4, 3, 0404, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De CREPUSCULAR a EXTERIOR/NOCTURNO se debe cumplir que: 
--	5.	< 200 lux
(0401, 4, 4, 0405, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- De NOCTURNO a SOLEADO se debe cumplir que: 
--	1.	> 2500 lux		
(0401, 5, 1, 0401, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NOCTURNO a NUBLADO se debe cumplir que: 
--	2.	< 2500 lux y > 1000 lux
(0401, 5, 2, 0402, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NOCTURNO a CREPUSCULAR se debe cumplir que: 
--	3.	< 1000 lux y > 300 lux
(0401, 5, 3, 0403, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De CREPUSCULAR a EXTERIOR/NOCTURNO se debe cumplir que: 
--	5.	< 200 lux
(0401, 5, 4, 0405, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- De EXTERIOR/NOCTURNO a SOLEADO se debe cumplir que: 
--	1.	> 2500 lux
(0401, 6, 1, 0401, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De EXTERIOR/NOCTURNO a NUBLADO se debe cumplir que: 
--	2.	< 2500 lux y > 1000 lux
(0401, 6, 2, 0402, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De EXTERIOR/NOCTURNO a CREPUSCULAR se debe cumplir que: 
--	3.	< 1000 lux y > 300 lux
(0401, 6, 3, 0403, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De EXTERIOR/NOCTURNO a NOCTURNO se debe cumplir que: 
--	4.	< 300 lux y > 200 lux
(0401, 6, 4, 0404, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
