INSERT INTO static.sm_state_machines
(state_machine_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Túnel (2 cifras), coincidiendo con el id del fichero
-- 2.- Orden de la maquina de estados (2 cifras)
(0501, 'LIGHTING_SERVICE_EL_CORZO_SENTIDO_MADRID_AUT', 'States machine to calulate service lighting regime in El Corzo Aut', 'LBL_ILUM_CZ_NORTE', 'LBL_ILUM_CZ_NORTE_DESC', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO static.sm_states
(state_machine_id, state_id, alias, description, label_alias, label_description, init_state, t_min, enabled, visible, created_at, updated_at) VALUES
-- Estados para la única máquina estados que tiene este túnel
(0501, 1, 'INIT', 'Init', 'LBL_SM_STATE_INIT', NULL, true, 0, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0501, 2, 'SOLEADO', 'SOLEADO', 'LBL_SM_STATE_SOLEADO', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0501, 3, 'NUBLADO', 'NUBLADO', 'LBL_SM_STATE_NUBLADO', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0501, 4, 'CREPUSCULAR', 'CREPUSCULAR', 'LBL_SM_STATE_CREPUSCULAR', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0501, 5, 'NOCTURNO', 'NOCTURNO', 'LBL_SM_STATE_NOCTURNO', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0501, 6, 'EXTERIOR/NOCTURNO', 'EXTERIOR/NOCTURNO', 'LBL_SM_STATE_EXTERIOR_NOCTURNO', NULL, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO static.sm_transitions
(state_machine_id, state_id, transition_id, nexus_and, next_state_id, enabled, visible, created_at, updated_at) VALUES
-- El Corzo - Ponemos todas las posibles transiciones entre estados de sentido Madrid
-- Desde el estado inicial podremos transitar al resto de estados. De cada estado podremos transitar a los otros
-- posibles estados, pero nunca podremos volver al inicial.
-- Desde Estado Inicial al resto de estados
(0501, 1, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> SOLEADO
(0501, 1, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> CREPUSCULAR
(0501, 1, 3, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> NUBLADO
(0501, 1, 4, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> NOCTURNO
(0501, 1, 5, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> EXTERIOR/NOCTURNO
-- Desde Soleado al resto de estados
(0501, 2, 1, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SOLEADO --> CREPUSCULAR
(0501, 2, 2, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SOLEADO --> NUBLADO
(0501, 2, 3, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SOLEADO --> NOCTURNO
(0501, 2, 4, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- SOLEADO --> EXTERIOR/NOCTURNO
-- Desde Nublado al resto de estados
(0501, 3, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NUBLADO --> SOLEADO
(0501, 3, 2, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NUBLADO --> CREPUSCULAR
(0501, 3, 3, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NUBLADO --> NOCTURNO
(0501, 3, 4, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NUBLADO --> EXTERIOR/NOCTURNO
-- Desde Crepuscular al resto de estados
(0501, 4, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- CREPUSCULAR --> SOLEADO
(0501, 4, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- CREPUSCULAR --> NUBLADO
(0501, 4, 3, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- CREPUSCULAR --> NOCTURNO
(0501, 4, 4, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- CREPUSCULAR --> EXTERIOR/NOCTURNO
-- Desde Nocturno al resto de estados
(0501, 5, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NOCTURNO --> SOLEADO
(0501, 5, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NOCTURNO --> CREPUSCULAR
(0501, 5, 3, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NOCTURNO --> NUBLADO
(0501, 5, 4, false, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NOCTURNO --> EXTERIOR/NOCTURNO
-- Desde Exterior/Nocturno al resto de estados
(0501, 6, 1, false, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- EXTERIOR/NOCTURNO --> SOLEADO
(0501, 6, 2, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- EXTERIOR/NOCTURNO --> CREPUSCULAR
(0501, 6, 3, false, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- EXTERIOR/NOCTURNO --> NUBLADO
(0501, 6, 4, false, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- EXTERIOR/NOCTURNO --> NOCTURNO


INSERT INTO static.sm_thresholds
(threshold_id, alias, description, label_alias, label_description, data_type_id, value, enabled, visible, created_at, updated_at) VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Túnel (2 cifras), coincidiendo con el id del fichero
-- 2.- Orden del umbral (2 cifras)

--Iluminación exterior: 0-200 cd/m2
--Iluminación nocturno: 0-300 cd/m2
--Iluminación crepuscular: 300-1000 cd/m2
--Iluminación nublado: 1000-2500 cd/m2
--Iluminación refuerzo: 1000-2500 cd/m2
--Iluminación soleado: >2500 cd/m2
(0501, 'Threshold_LUM_2500', 'Threshold_LUM_2500', 'LBL_THRESHOLD_LUM_2500', 'LBL_THRESHOLD_LUM_2500', 3, '2500', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0502, 'Threshold_LUM_1000', 'Threshold_LUM_1000', 'LBL_THRESHOLD_LUM_1000', 'LBL_THRESHOLD_LUM_1000', 3, '1000', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0503, 'Threshold_LUM_300', 'Threshold_LUM_300', 'LBL_THRESHOLD_LUM_300', 'LBL_THRESHOLD_LUM_300', 3, '300', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0504, 'Threshold_LUM_200', 'Threshold_LUM_200', 'LBL_THRESHOLD_LUM_200', 'LBL_THRESHOLD_LUM_200', 3, '200', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO static.sm_conditions
(condition_id, threshold_operation_id, threshold_val1_id, threshold_val2_id, element_type_id, element_type_param_id, param_type_id, timer_id, enabled, visible, created_at, updated_at)VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Túnel (2 cifras), coincidiendo con el id del fichero
-- 2.- Orden de la condición (2 cifras)
-- Operations: 1 = Entre; 4 = Mayor que; 6 = Menor que
(0501, 4, 0501, NULL, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- > 2500 lux
(0502, 1, 0502, 0501, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- < 2500 lux y > 1000 lux
(0503, 1, 0503, 0502, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- < 1000 lux y > 300 lux
(0504, 1, 0504, 0503, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- < 300 lux y > 200 lux
(0505, 6, 0504, NULL, 14, 2, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- < 200 lux

INSERT INTO static.sm_transition_conditions
(state_machine_id, state_id, transition_id, condition_id, enabled, visible, created_at, updated_at)VALUES
--CONDICIONES PARA TRANSITAR DESDE EL ESTADO INICIAL AL RESTO DE ESTADOS


-- De inicial a SOLEADO se debe cumplir que: 
--	1.	> 2500 lux
(0501, 1, 1, 0501, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De inicial a NUBLADO se debe cumplir que: 
--	2.	< 2500 lux y > 1000 lux
(0501, 1, 2, 0502, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De inicial a CREPUSCULAR se debe cumplir que: 
--	3.	< 1000 lux y > 300 lux
(0501, 1, 3, 0503, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De inicial a NOCTURNO se debe cumplir que: 
--	4.	< 300 lux y > 200 lux
(0501, 1, 4, 0504, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De inicial a EXTERIOR/NOCTURNO se debe cumplir que: 
--	5.	< 200 lux
(0501, 1, 5, 0505, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),



-- De SOLEADO a NUBLADO se debe cumplir que: 
--	2.	< 2500 lux y > 1000 lux
(0501, 2, 1, 0502, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De SOLEADO a CREPUSCULAR se debe cumplir que: 
--	3.	< 1000 lux y > 300 lux
(0501, 2, 2, 0503, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De SOLEADO a NOCTURNO se debe cumplir que: 
--	4.	< 300 lux y > 200 lux
(0501, 2, 3, 0504, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De SOLEADO a EXTERIOR/NOCTURNO se debe cumplir que: 
--	5.	< 200 lux
(0501, 2, 4, 0505, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),



-- De NUBLADO a SOLEADO se debe cumplir que: 
--	1.	> 2500 lux	
(0501, 3, 1, 0501, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NUBLADO a CREPUSCULAR se debe cumplir que: 
--	3.	< 1000 lux y > 300 lux
(0501, 3, 2, 0503, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NUBLADO a NOCTURNO se debe cumplir que: 
--	4.	< 300 lux y > 200 lux
(0501, 3, 3, 0504, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NUBLADO a EXTERIOR/NOCTURNO se debe cumplir que: 
--	5.	< 200 lux
(0501, 3, 4, 0505, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),



-- De CREPUSCULAR a SOLEADO se debe cumplir que: 
--	1.	> 2500 lux	
(0501, 4, 1, 0501, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De CREPUSCULAR a NUBLADO se debe cumplir que: 
--	2.	< 2500 lux y > 1000 lux
(0501, 4, 2, 0502, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De CREPUSCULAR a NOCTURNO se debe cumplir que: 
--	4.	< 300 lux y > 200 lux
(0501, 4, 3, 0504, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De CREPUSCULAR a EXTERIOR/NOCTURNO se debe cumplir que: 
--	5.	< 200 lux
(0501, 4, 4, 0505, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- De NOCTURNO a SOLEADO se debe cumplir que: 
--	1.	> 2500 lux		
(0501, 5, 1, 0501, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NOCTURNO a NUBLADO se debe cumplir que: 
--	2.	< 2500 lux y > 1000 lux
(0501, 5, 2, 0502, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De NOCTURNO a CREPUSCULAR se debe cumplir que: 
--	3.	< 1000 lux y > 300 lux
(0501, 5, 3, 0503, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De CREPUSCULAR a EXTERIOR/NOCTURNO se debe cumplir que: 
--	5.	< 200 lux
(0501, 5, 4, 0505, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


-- De EXTERIOR/NOCTURNO a SOLEADO se debe cumplir que: 
--	1.	> 2500 lux
(0501, 6, 1, 0501, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De EXTERIOR/NOCTURNO a NUBLADO se debe cumplir que: 
--	2.	< 2500 lux y > 1000 lux
(0501, 6, 2, 0502, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De EXTERIOR/NOCTURNO a CREPUSCULAR se debe cumplir que: 
--	3.	< 1000 lux y > 300 lux
(0501, 6, 3, 0503, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De EXTERIOR/NOCTURNO a NOCTURNO se debe cumplir que: 
--	4.	< 300 lux y > 200 lux
(0501, 6, 4, 0504, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
