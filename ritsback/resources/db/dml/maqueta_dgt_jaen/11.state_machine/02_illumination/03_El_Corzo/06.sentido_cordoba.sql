INSERT INTO static.sm_state_machines
(state_machine_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Túnel (2 cifras), coincidiendo con el id del fichero
-- 2.- Orden de la maquina de estados (2 cifras)
(0601, 'LIGHTING_SERVICE_EL_CORZO_SENTIDO_CORDOBA_AUT', 'States machine to calulate service lighting regime in El Corzo Aut', NULL, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO static.sm_states
(state_machine_id, state_id, alias, description, label_alias, label_description, init_state, t_min, enabled, visible, created_at, updated_at) VALUES
-- Estados para la única máquina estados que tiene este túnel
(0601, 1, 'INIT', 'Init', 'LBL_SM_STATE_INIT', NULL, true, 0, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0601, 2, 'SOLEADO', 'SOLEADO', 'LBL_SM_STATE_SOLEADO', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0601, 3, 'CREPUSCULAR', 'CREPUSCULAR', 'LBL_SM_STATE_CREPUSCULAR', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0601, 4, 'NUBLADO', 'NUBLADO', 'LBL_SM_STATE_NUBLADO', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0601, 5, 'NOCTURNO', 'NOCTURNO', 'LBL_SM_STATE_NOCTURNO', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO static.sm_transitions
(state_machine_id, state_id, transition_id, nexus_and, next_state_id, enabled, visible, created_at, updated_at) VALUES
-- El Corzo - Ponemos todas las posibles transiciones entre estados de sentido Córdoba
-- Desde el estado inicial podremos transitar al resto de estados. De cada estado podremos transitar a los otros
-- posibles estados, pero nunca podremos volver al inicial.
-- Desde Estado Inicial al resto de estados
(0601, 1, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> SOLEADO
(0601, 1, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> CREPUSCULAR
(0601, 1, 3, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> NUBLADO
(0601, 1, 4, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> NOCTURNO
-- Desde Soleado al resto de estados
(0601, 2, 1, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SOLEADO --> CREPUSCULAR
(0601, 2, 2, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SOLEADO --> NUBLADO
(0601, 2, 3, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SOLEADO --> NOCTURNO
-- Desde Crepuscular al resto de estados
(0601, 3, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- CREPUSCULAR --> SOLEADO
(0601, 3, 2, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- CREPUSCULAR --> NUBLADO
(0601, 3, 3, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- CREPUSCULAR --> NOCTURNO
-- Desde Nublado al resto de estados
(0601, 4, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NUBLADO --> SOLEADO
(0601, 4, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NUBLADO --> CREPUSCULAR
(0601, 4, 3, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NUBLADO --> NOCTURNO
-- Desde Nocturno al resto de estados
(0601, 5, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NOCTURNO --> SOLEADO
(0601, 5, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NOCTURNO --> CREPUSCULAR
(0601, 5, 3, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- NOCTURNO --> NUBLADO


INSERT INTO static.sm_thresholds
(threshold_id, alias, description, label_alias, label_description, data_type_id, value, enabled, visible, created_at, updated_at) VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Túnel (2 cifras), coincidiendo con el id del fichero
-- 2.- Orden del umbral (2 cifras)
(0601, 'Threshold_LUM_MAYOR_1000', 'Threshold_LUM_MAYOR_1000', 'LBL_THRESHOLD_LUM_MAYOR_1000', 'LBL_THRESHOLD_LUM_MAYOR_1000', 3, '1000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),							-- Soleado > 1000 lux
(0602, 'Threshold_LUM_ENTRE_1000_Y_800', 'Threshold_LUM_ENTRE_1000_Y_800', 'LBL_THRESHOLD_LUM_ENTRE_1000_Y_800', 'LBL_THRESHOLD_LUM_ENTRE_1000_Y_800', 3, '800', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	-- Crepusuclar < 1000 lux y > 800 lux
(0603, 'Threshold_LUM_ENTRE_800_Y_500', 'Threshold_LUM_ENTRE_800_Y_500', 'LBL_THRESHOLD_LUM_ENTRE_800_Y_500', 'LBL_THRESHOLD_LUM_ENTRE_800_Y_500', 3, '500', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	-- Nublado < 800 lux y > 500 lux
(0604, 'Threshold_LUM_MENOR_500', 'Threshold_LUM_MENOR_500', 'LBL_THRESHOLD_LUM_MENOR_500', 'LBL_THRESHOLD_LUM_MENOR_500', 3, '500', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);	-- Nocturno < 500 lux

INSERT INTO static.sm_conditions
(condition_id, threshold_operation_id, threshold_val1_id, threshold_val2_id, element_type_id, element_type_param_id, param_type_id, timer_id, enabled, visible, created_at, updated_at)VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Túnel (2 cifras), coincidiendo con el id del fichero
-- 2.- Orden de la condición (2 cifras)
-- Operations: 1 = Entre; 4 = Mayor que; 6 = Menor que
(0601, 4, 0601, NULL, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- > 1000 lux
(0602, 1, 0602, 0601, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- < 1000 lux y > 800 lux
(0603, 1, 0603, 0602, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- < 800 lux y > 500 lux
(0604, 6, 0604, NULL, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- < 500 lux

INSERT INTO static.sm_transition_conditions
(state_machine_id, state_id, transition_id, condition_id, enabled, visible, created_at, updated_at)VALUES
--CONDICIONES PARA TRANSITAR DESDE EL ESTADO INICIAL AL RESTO DE ESTADOS

-- De inicial a SOLEADO se debe cumplir que: 
--	1.	> 1000 lux	
(0601, 1, 1, 0601, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De inicial a CREPUSCULAR se debe cumplir que: 
--	2.	< 1000 lux y > 800 lux	
(0601, 1, 2, 0602, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De inicial a NUBLADO se debe cumplir que: 
--	3.	< 800 lux y > 500 lux
(0601, 1, 3, 0603, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De inicial a NOCTURNO se debe cumplir que: 
--	4.	< 500 lux
(0601, 1, 4, 0604, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),



-- De SOLEADO a CREPUSCULAR se debe cumplir que: 
--	2.	< 1000 lux y > 800 lux	
(0601, 2, 1, 0602, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De SOLEADO a NUBLADO se debe cumplir que: 
--	3.	< 800 lux y > 500 lux
(0601, 2, 2, 0603, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De SOLEADO a NOCTURNO se debe cumplir que: 
--	4.	< 500 lux
(0601, 2, 3, 0604, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),



-- De CREPUSCULAR a SOLEADO se debe cumplir que: 
--	1.	> 1000 lux	
(0601, 3, 1, 0601, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De CREPUSCULAR a NUBLADO se debe cumplir que: 
--	3.	< 800 lux y > 500 lux
(0601, 3, 2, 0603, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De CREPUSCULAR a NOCTURNO se debe cumplir que: 
--	4.	< 500 lux
(0601, 3, 3, 0604, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),



-- De NUBLADO a SOLEADO se debe cumplir que: 
--	1.	> 1000 lux	
(0601, 4, 1, 0601, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NUBLADO a CREPUSCULAR se debe cumplir que: 
--	2.	< 1000 lux y > 800 lux	
(0601, 4, 2, 0602, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NUBLADO a NOCTURNO se debe cumplir que: 
--	4.	< 500 lux
(0601, 4, 3, 0604, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- De NOCTURNO a SOLEADO se debe cumplir que: 
--	1.	> 1000 lux	
(0601, 5, 1, 0601, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NOCTURNO a CREPUSCULAR se debe cumplir que: 
--	2.	< 1000 lux y > 800 lux	
(0601, 5, 2, 0602, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NOCTURNO a NOCTURNO se debe cumplir que: 
--	3.	< 800 lux y > 500 lux
(0601, 5, 3, 0603, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);