-- Las maquinas de estados de iluminación para el túnel de La Quinta sentido Málaga tienen que cumplir las siguientes condiciones:
-- 1.- Si la fotocélula del túnel > 300 cd/m2 pasara a estado soleado, en caso contrario comprobaremos el luminancímetro boca Málaga con las siguientes condiciones:
-- 		1.1.- Si el luminancimetro boca Málaga < 420 cd/m2 pondremos régimen en estado soleado, con el DALI al 10%.
-- 		1.2.- Si el luminancimetro boca Málaga está entre 420 y 840 cd/m2 pondremos régimen en estado soleado, con el DALI al 20%.
-- 		1.3.- Si el luminancimetro boca Málaga está entre 840 y 1260 cd/m2 pondremos régimen en estado soleado, con el DALI al 30%.
-- 		1.4.- Si el luminancimetro boca Málaga está entre 1260 y 1890 cd/m2 pondremos régimen en estado soleado, con el DALI al 40%.
-- 		1.5.- Si el luminancimetro boca Málaga está entre 1890 y 2521 cd/m2 pondremos régimen en estado soleado, con el DALI al 50%.
-- 		1.6.- Si el luminancimetro boca Málaga está entre 2521 y 3878 cd/m2 pondremos régimen en estado soleado, con el DALI al 65%.
-- 		1.7.- Si el luminancimetro boca Málaga está entre 3878 y 4562 cd/m2 pondremos régimen en estado soleado, con el DALI al 85%.
-- 		1.8.- Si el luminancimetro boca Málaga > 4562 cd/m2 pondremos régimen en estado soleado, con el DALI al 100%.


INSERT INTO static.sm_thresholds
(threshold_id, alias, description, label_alias, label_description, data_type_id, value, enabled, visible, created_at, updated_at) VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Túnel (2 cifras), coincidiendo con el id del fichero
-- 2.- Orden del umbral (2 cifras)
(0801, 'Threshold_LQ_BM-FOT', 'Threshold LQ_BM-FOT (lux)', 'LBL_THRESHOLD_PHO', 'LBL_THRESHOLD_PHO', 3, '300', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),							-- Umbral fotocelula
(0802, 'Threshold_LQ_BM_DALI_100', 'Threshold Threshold_LQ_BM_DALI_85_100 (cd/m²)', 'LBL_THRESHOLD_LUM', 'LBL_THRESHOLD_LUM', 3, '4562', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	-- Umbral cambio entre 85% y 100%
(0803, 'Threshold_LQ_BM_DALI_85', 'Threshold Threshold_LQ_BM_DALI_65_85 (cd/m²)', 'LBL_THRESHOLD_LUM', 'LBL_THRESHOLD_LUM', 3, '3878', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	-- Umbral cambio entre 65% y 85%
(0804, 'Threshold_LQ_BM_DALI_65', 'Threshold Threshold_LQ_BM_DALI_50_65 (cd/m²)', 'LBL_THRESHOLD_LUM', 'LBL_THRESHOLD_LUM', 3, '2521', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	-- Umbral cambio entre 50% y 65%
(0805, 'Threshold_LQ_BM_DALI_50', 'Threshold Threshold_LQ_BM_DALI_40_50 (cd/m²)', 'LBL_THRESHOLD_LUM', 'LBL_THRESHOLD_LUM', 3, '1890', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	-- Umbral cambio entre 40% y 50%
(0806, 'Threshold_LQ_BM_DALI_40', 'Threshold Threshold_LQ_BM_DALI_30_40 (cd/m²)', 'LBL_THRESHOLD_LUM', 'LBL_THRESHOLD_LUM', 3, '1260', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	-- Umbral cambio entre 30% y 40%
(0807, 'Threshold_LQ_BM_DALI_30', 'Threshold Threshold_LQ_BM_DALI_20_30 (cd/m²)', 'LBL_THRESHOLD_LUM', 'LBL_THRESHOLD_LUM', 3, '840', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	-- Umbral cambio entre 20% y 30%
(0808, 'Threshold_LQ_BM_DALI_20', 'Threshold Threshold_LQ_BM_DALI_10_20 (cd/m²)', 'LBL_THRESHOLD_LUM', 'LBL_THRESHOLD_LUM', 3, '420', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),	-- Umbral cambio entre 10% y 20%
(0899, 'Threshold_out_of_range', 'Threshold_out_of_range', 'LBL_THRESHOLD_OUT_OF_RANGE', 'LBL_THRESHOLD_OUT_OF_RANGE', 3, '65534.0', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 	-- Umbral fuera de rango

-- Operations: 1 = Entre; 4 = Mayor que; 6 = Menor que
INSERT INTO static.sm_conditions
(condition_id, threshold_operation_id, threshold_val1_id, threshold_val2_id, element_type_id, element_type_param_id, param_type_id, timer_id, enabled, visible, created_at, updated_at)VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Túnel (2 cifras), coincidiendo con el id del fichero
-- 2.- Orden de la condición (2 cifras)
--Condiciones para la fotocelula
(0801, 6, 0801, NULL, 31, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- luminosity_fot < Threshold_LQ_BM-FOT
(0802, 1, 0801, 0899, 31, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range
--Condiciones para el luminancimetro
(0803, 1, 0802, 0899, 14, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), --  Threshold_LQ_BM_DALI_85_100 <= luminosity_lum <= LBL_THRESHOLD_OUT_OF_RANGE
(0804, 1, 0803, 0802, 14, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Threshold_LQ_BM_DALI_65_85 <= luminosity_lum <= Threshold_LQ_BM_DALI_85_100
(0805, 1, 0804, 0803, 14, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Threshold_LQ_BM_DALI_50_65 <= luminosity_lum <= Threshold_LQ_BM_DALI_65_85
(0806, 1, 0805, 0804, 14, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Threshold_LQ_BM_DALI_40_50 <= luminosity_lum <= Threshold_LQ_BM_DALI_50_65
(0807, 1, 0806, 0805, 14, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Threshold_LQ_BM_DALI_30_40 <= luminosity_lum <= Threshold_LQ_BM_DALI_40_50
(0808, 1, 0807, 0806, 14, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Threshold_LQ_BM_DALI_20_30 <= luminosity_lum <= Threshold_LQ_BM_DALI_30_40
(0809, 1, 0808, 0807, 14, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- Threshold_LQ_BM_DALI_10_20 <= luminosity_lum <= Threshold_LQ_BM_DALI_20_30
(0810, 6, 0808, NULL, 14, 1, 2, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- luminosity_lum <= Threshold_LQ_BM_DALI_10_20(0306)

INSERT INTO static.sm_state_machines
(state_machine_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at)VALUES
-- Para el id seguiremos el siguiente criterio:
-- 1.- Túnel (2 cifras), coincidiendo con el id del fichero
-- 2.- Orden de la maquina de estados (2 cifras)
(0801, 'LIGHTING_SERVICE_LQ_BM_AUT', 'States machine to calulate service lighting regime in La Quinta Aut', NULL, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO static.sm_states
(state_machine_id, state_id, alias, description, label_alias, label_description, init_state, t_min, enabled, visible, created_at, updated_at) VALUES
-- Estados para la única máquina estados que tiene este túnel
(0801, 1, 'INIT', 'Init', 'LBL_SM_STATE_INIT', NULL, true, 0, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 2, 'DAY_100', 'Day 100', 'LBL_SM_STATE_DAY_100', NULL, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 3, 'DAY_85', 'Day 85', 'LBL_SM_STATE_DAY_85', NULL, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 4, 'DAY_65', 'Day 65', 'LBL_SM_STATE_DAY_65', NULL, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 5, 'DAY_50', 'Day 50', 'LBL_SM_STATE_DAY_50', NULL, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 6, 'DAY_40', 'Day 40', 'LBL_SM_STATE_DAY_40', NULL, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 7, 'DAY_30', 'Day 30', 'LBL_SM_STATE_DAY_30', NULL, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 8, 'DAY_20', 'Day 20', 'LBL_SM_STATE_DAY_20', NULL, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 9, 'DAY_10', 'Day 10', 'LBL_SM_STATE_DAY_10', NULL, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 99, 'NIGHT', 'Night', 'LBL_SM_STATE_NIGHT', NULL, false, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO static.sm_transitions
(state_machine_id, state_id, transition_id, nexus_and, next_state_id, enabled, visible, created_at, updated_at) VALUES
-- La Quinta - Ponemos todas las posibles transiciones entre estados de la boca Málaga
-- Desde el estado inicial podremos transitar al resto de estados. De cada estado podremos transitar a los otros
-- posibles estados, pero nunca podremos volver al inicial.Además en aquellos estados que para transitar de estado 
-- se tienen que cumplir dos condiciones a la vez (la del luminancímetro y el de la fotocélula), ese necesario
-- poner el campo "nexus_and" = true

-- Desde Estado Inicial al resto de estados
(0801, 1, 1, true, 99, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> NIGHT
(0801, 1, 2, true, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> DAY_100
(0801, 1, 3, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> DAY_85
(0801, 1, 4, true, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> DAY_65
(0801, 1, 5, true, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> DAY_50
(0801, 1, 6, true, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> DAY_40
(0801, 1, 7, true, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> DAY_30
(0801, 1, 8, true, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> DAY_20
(0801, 1, 9, true, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- INIT --> DAY_10
-- Desde Soleado 100% al resto de estados
(0801, 2, 1, true, 99, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_100 --> NIGHT
(0801, 2, 2, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_100 --> DAY_85
(0801, 2, 3, true, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_100 --> DAY_65
(0801, 2, 4, true, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_100 --> DAY_50
(0801, 2, 5, true, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_100 --> DAY_40
(0801, 2, 6, true, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_100 --> DAY_30
(0801, 2, 7, true, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_100 --> DAY_20
(0801, 2, 8, true, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_100 --> DAY_10
-- Desde Soleado 85% al resto de estados
(0801, 3, 1, true, 99, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_85 --> NIGHT
(0801, 3, 2, true, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_85 --> DAY_100
(0801, 3, 3, true, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_85 --> DAY_65
(0801, 3, 4, true, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_85 --> DAY_50
(0801, 3, 5, true, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_85 --> DAY_40
(0801, 3, 6, true, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_85 --> DAY_30
(0801, 3, 7, true, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_85 --> DAY_20
(0801, 3, 8, true, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_85 --> DAY_10
-- Desde Soleado 65% al resto de estados
(0801, 4, 1, true, 99, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_65 --> NIGHT
(0801, 4, 2, true, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_65 --> DAY_100
(0801, 4, 3, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_65 --> DAY_85
(0801, 4, 4, true, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_65 --> DAY_50
(0801, 4, 5, true, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_65 --> DAY_40
(0801, 4, 6, true, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_65 --> DAY_30
(0801, 4, 7, true, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_65 --> DAY_20
(0801, 4, 8, true, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_65 --> DAY_10
-- Desde Soleado 50% al resto de estados
(0801, 5, 1, true, 99, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_50 --> NIGHT
(0801, 5, 2, true, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_50 --> DAY_100
(0801, 5, 3, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_50 --> DAY_85
(0801, 5, 4, true, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_50 --> DAY_65
(0801, 5, 5, true, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_50 --> DAY_40
(0801, 5, 6, true, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_50 --> DAY_30
(0801, 5, 7, true, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_50 --> DAY_20
(0801, 5, 8, true, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_50 --> DAY_10
-- Desde Soleado 40% al resto de estados
(0801, 6, 1, true, 99, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_40 --> NIGHT
(0801, 6, 2, true, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_40 --> DAY_100
(0801, 6, 3, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_40 --> DAY_85
(0801, 6, 4, true, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_40 --> DAY_65
(0801, 6, 5, true, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_40 --> DAY_50
(0801, 6, 6, true, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_40 --> DAY_30
(0801, 6, 7, true, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_40 --> DAY_20
(0801, 6, 8, true, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_40 --> DAY_10
-- Desde Soleado 30% al resto de estados
(0801, 7, 1, true, 99, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_30 --> NIGHT
(0801, 7, 2, true, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_30 --> DAY_100
(0801, 7, 3, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_30 --> DAY_85
(0801, 7, 4, true, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_30 --> DAY_65
(0801, 7, 5, true, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_30 --> DAY_50
(0801, 7, 6, true, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_30 --> DAY_40
(0801, 7, 7, true, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_30 --> DAY_20
(0801, 7, 8, true, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_30 --> DAY_10
-- Desde Soleado 20% al resto de estados
(0801, 8, 1, true, 99, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_20 --> NIGHT
(0801, 8, 2, true, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_20 --> DAY_100
(0801, 8, 3, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_20 --> DAY_85
(0801, 8, 4, true, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_20 --> DAY_65
(0801, 8, 5, true, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_20 --> DAY_50
(0801, 8, 6, true, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_20 --> DAY_40
(0801, 8, 7, true, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_20 --> DAY_30
(0801, 8, 8, true, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_20 --> DAY_10
-- Desde Soleado 10% al resto de estados
(0801, 9, 1, true, 99, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_10 --> NIGHT
(0801, 9, 2, true, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_10 --> DAY_100
(0801, 9, 3, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_10 --> DAY_85
(0801, 9, 4, true, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_10 --> DAY_65
(0801, 9, 5, true, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_10 --> DAY_50
(0801, 9, 6, true, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_10 --> DAY_40
(0801, 9, 7, true, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_10 --> DAY_30
(0801, 9, 8, true, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- DAY_10 --> DAY_20
-- Desde Nocturno al resto de estados
(0801, 99, 1, true, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NIGHT --> DAY_100
(0801, 99, 2, true, 3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NIGHT --> DAY_85
(0801, 99, 3, true, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NIGHT --> DAY_65
(0801, 99, 4, true, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NIGHT --> DAY_50
(0801, 99, 5, true, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NIGHT --> DAY_40
(0801, 99, 6, true, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NIGHT --> DAY_30
(0801, 99, 7, true, 8, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- NIGHT --> DAY_20
(0801, 99, 8, true, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- NIGHT --> DAY_10

INSERT INTO static.sm_transition_conditions
(state_machine_id, state_id, transition_id, condition_id, enabled, visible, created_at, updated_at)VALUES
--CONDICIONES PARA TRANSITAR DESDE EL ESTADO INICIAL AL RESTO DE ESTADOS
-- De inicial a nocturno se debe cumplir que: 
--	1.	luminosity_fot < Threshold_LQ_BM-FOT	
(0801, 1, 1, 0801, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De inicial a soleado 100% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_85_100 <= luminosity_lum <= LBL_THRESHOLD_OUT_OF_RANGE	
(0801, 1, 2, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 1, 2, 0803, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De inicial a soleado 85% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_65_85 <= luminosity_lum <= Threshold_LQ_BM_DALI_85_100	
(0801, 1, 3, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 1, 3, 0804, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
-- De inicial a soleado 65% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_50_65 <= luminosity_lum <= Threshold_LQ_BM_DALI_65_85	
(0801, 1, 4, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
(0801, 1, 4, 0805, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De inicial a soleado 50% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_40_50 <= luminosity_lum <= Threshold_LQ_BM_DALI_50_65	  
(0801, 1, 5, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 1, 5, 0806, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),        
-- De inicial a soleado 40% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_30_40 <= luminosity_lum <= Threshold_LQ_BM_DALI_40_50	
(0801, 1, 6, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 1, 6, 0807, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),        
-- De inicial a soleado 30% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_20_30 <= luminosity_lum <= Threshold_LQ_BM_DALI_30_40	
(0801, 1, 7, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 1, 7, 0808, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),         
-- De inicial a soleado 20% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_10_20 <=luminosity_lum <= Threshold_LQ_BM_DALI_20_30	
(0801, 1, 8, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 1, 8, 0809, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De inicial a soleado 10% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	luminosity_lum <= Threshold_LQ_BM_DALI_10_20	
(0801, 1, 9, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 1, 9, 0810, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  

--CONDICIONES PARA TRANSITAR DESDE EL ESTADO SOLEADO AL 100% AL RESTO DE ESTADOS
-- De soleado 100% a nocturno se debe cumplir que: 
--	1.	luminosity_fot < Threshold_LQ_BM-FOT	
(0801, 2, 1, 0801, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 100% a soleado 85% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_65_85 <= luminosity_lum <= Threshold_LQ_BM_DALI_85_100	
(0801, 2, 2, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 2, 2, 0804, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 100% a soleado 65% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_50_65 <= luminosity_lum <= Threshold_LQ_BM_DALI_65_85	
(0801, 2, 3, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 2, 3, 0805, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 100% a soleado 50% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_40_50 <= luminosity_lum <= Threshold_LQ_BM_DALI_50_65	
(0801, 2, 4, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 2, 4, 0806, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 100% a soleado 40% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_30_40 <= luminosity_lum <= Threshold_LQ_BM_DALI_40_50	
(0801, 2, 5, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 2, 5, 0807, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De soleado 100% a soleado 30% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_20_30 <= luminosity_lum <= Threshold_LQ_BM_DALI_30_40	
(0801, 2, 6, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 2, 6, 0808, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),        
-- De soleado 100% a soleado 20% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_10_20 <=luminosity_lum <= Threshold_LQ_BM_DALI_20_30	
(0801, 2, 7, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 2, 7, 0809, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De soleado 100% a soleado 10% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	luminosity_lum <= Threshold_LQ_BM_DALI_10_20	
(0801, 2, 8, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 2, 8, 0810, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--CONDICIONES PARA TRANSITAR DESDE EL ESTADO SOLEADO AL 85% AL RESTO DE ESTADOS
-- De soleado 85% a nocturno se debe cumplir que: 
--	1.	luminosity_fot < Threshold_LQ_BM-FOT	
(0801, 3, 1, 0801, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 85% a soleado 100% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--  2.  Threshold_LQ_BM_DALI_85_100 <= luminosity_lum <= LBL_THRESHOLD_OUT_OF_RANGE	
(0801, 3, 2, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 3, 2, 0803, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 85% a soleado 65% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_50_65 <= luminosity_lum <= Threshold_LQ_BM_DALI_65_85
(0801, 3, 3, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 3, 3, 0805, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 85% a soleado 50% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_30_50 <= luminosity_lum <= Threshold_LQ_BM_DALI_50_65
(0801, 3, 4, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 3, 4, 0806, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 85% a soleado 40% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_30_40 <= luminosity_lum <= Threshold_LQ_BM_DALI_40_50	
(0801, 3, 5, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 3, 5, 0807, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De soleado 85% a soleado 30% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_20_30 <= luminosity_lum <= Threshold_LQ_BM_DALI_30_40
(0801, 3, 6, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 3, 6, 0808, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 85% a soleado 20% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_10_20 <=luminosity_lum <= Threshold_LQ_BM_DALI_20_30	
(0801, 3, 7, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 3, 7, 0809, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De soleado 85% a soleado 10% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	luminosity_lum <= Threshold_LQ_BM_DALI_10_20	
(0801, 3, 8, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 3, 8, 0810, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--CONDICIONES PARA TRANSITAR DESDE EL ESTADO SOLEADO AL 65% AL RESTO DE ESTADOS
-- De soleado 65% a nocturno se debe cumplir que: 
--	1.	luminosity_fot < Threshold_LQ_BM-FOT	
(0801, 4, 1, 0801, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 65% a soleado 100% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--  2.  Threshold_LQ_BM_DALI_85_100 <= luminosity_lum <= LBL_THRESHOLD_OUT_OF_RANGE	
(0801, 4, 2, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 4, 2, 0803, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 65% a soleado 85% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_65_85 <= luminosity_lum <= Threshold_LQ_BM_DALI_85_100
(0801, 4, 3, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 4, 3, 0804, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 65% a soleado 50% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_40_50 <= luminosity_lum <= Threshold_LQ_BM_DALI_50_65
(0801, 4, 4, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 4, 4, 0806, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 65% a soleado 40% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_30_40 <= luminosity_lum <= Threshold_LQ_BM_DALI_40_50	
(0801, 4, 5, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 4, 5, 0807, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
-- De soleado 65% a soleado 30% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_20_30 <= luminosity_lum <= Threshold_LQ_BM_DALI_30_40
(0801, 4, 6, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 4, 6, 0808, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 65% a soleado 20% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_10_20 <=luminosity_lum <= Threshold_LQ_BM_DALI_20_30	
(0801, 4, 7, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 4, 7, 0809, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De soleado 65% a soleado 10% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	luminosity_lum <= Threshold_LQ_BM_DALI_10_20	
(0801, 4, 8, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 4, 8, 0810, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--CONDICIONES PARA TRANSITAR DESDE EL ESTADO SOLEADO AL 50% AL RESTO DE ESTADOS
-- De soleado 50% a nocturno se debe cumplir que: 
--	1.	luminosity_fot < Threshold_LQ_BM-FOT	
(0801, 5, 1, 0801, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 50% a soleado 100% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--  2.  Threshold_LQ_BM_DALI_85_100 <= luminosity_lum <= LBL_THRESHOLD_OUT_OF_RANGE	
(0801, 5, 2, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 5, 2, 0803, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 50% a soleado 85% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_65_85 <= luminosity_lum <= Threshold_LQ_BM_DALI_85_100
(0801, 5, 3, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 5, 3, 0804, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 50% a soleado 65% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_50_65 <= luminosity_lum <= Threshold_LQ_BM_DALI_65_85
(0801, 5, 4, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 5, 4, 0805, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 50% a soleado 40% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_30_40 <= luminosity_lum <= Threshold_LQ_BM_DALI_40_50	
(0801, 5, 5, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 5, 5, 0807, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
-- De soleado 50% a soleado 30% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_20_30 <= luminosity_lum <= Threshold_LQ_BM_DALI_30_40
(0801, 5, 6, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 5, 6, 0808, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 50% a soleado 20% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_10_20 <=luminosity_lum <= Threshold_LQ_BM_DALI_20_30	
(0801, 5, 7, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 5, 7, 0809, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De soleado 50% a soleado 10% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	luminosity_lum <= Threshold_LQ_BM_DALI_10_20	
(0801, 5, 8, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 5, 8, 0810, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--CONDICIONES PARA TRANSITAR DESDE EL ESTADO SOLEADO AL 40% AL RESTO DE ESTADOS
-- De soleado 40% a nocturno se debe cumplir que: 
--	1.	luminosity_fot < Threshold_LQ_BM-FOT	
(0801, 6, 1, 0801, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 40% a soleado 100% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--  2.  Threshold_LQ_BM_DALI_85_100 <= luminosity_lum <= LBL_THRESHOLD_OUT_OF_RANGE	
(0801, 6, 2, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 6, 2, 0803, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 40% a soleado 85% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_65_85 <= luminosity_lum <= Threshold_LQ_BM_DALI_85_100
(0801, 6, 3, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 6, 3, 0804, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 40% a soleado 65% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_50_65 <= luminosity_lum <= Threshold_LQ_BM_DALI_65_85
(0801, 6, 4, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 6, 4, 0805, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 40% a soleado 50% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_40_50 <= luminosity_lum <= Threshold_LQ_BM_DALI_50_65	
(0801, 6, 5, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 6, 5, 0806, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
-- De soleado 40% a soleado 30% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_20_30 <= luminosity_lum <= Threshold_LQ_BM_DALI_30_40
(0801, 6, 6, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 6, 6, 0808, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 40% a soleado 20% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_10_20 <=luminosity_lum <= Threshold_LQ_BM_DALI_20_30	
(0801, 6, 7, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 6, 7, 0809, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De soleado 40% a soleado 10% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	luminosity_lum <= Threshold_LQ_BM_DALI_10_20	
(0801, 6, 8, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 6, 8, 0810, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--CONDICIONES PARA TRANSITAR DESDE EL ESTADO SOLEADO AL 30% AL RESTO DE ESTADOS
-- De soleado 30% a nocturno se debe cumplir que: 
--	1.	luminosity_fot < Threshold_LQ_BM-FOT	
(0801, 7, 1, 0801, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 30% a soleado 100% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--  2.  Threshold_LQ_BM_DALI_85_100 <= luminosity_lum <= LBL_THRESHOLD_OUT_OF_RANGE	
(0801, 7, 2, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 7, 2, 0803, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 30% a soleado 85% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_65_85 <= luminosity_lum <= Threshold_LQ_BM_DALI_85_100
(0801, 7, 3, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 7, 3, 0804, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 30% a soleado 65% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_50_65 <= luminosity_lum <= Threshold_LQ_BM_DALI_65_85
(0801, 7, 4, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 7, 4, 0805, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 30% a soleado 50% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_40_50 <= luminosity_lum <= Threshold_LQ_BM_DALI_50_65
(0801, 7, 5, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 7, 5, 0806, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 30% a soleado 40% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_30_40 <= luminosity_lum <= Threshold_LQ_BM_DALI_40_50
(0801, 7, 6, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 7, 6, 0807, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 30% a soleado 20% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_10_20 <=luminosity_lum <= Threshold_LQ_BM_DALI_20_30	
(0801, 7, 7, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 7, 7, 0809, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De soleado 30% a soleado 10% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	luminosity_lum <= Threshold_LQ_BM_DALI_10_20	
(0801, 7, 8, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 7, 8, 0810, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--CONDICIONES PARA TRANSITAR DESDE EL ESTADO SOLEADO AL 20% AL RESTO DE ESTADOS
-- De soleado 20% a nocturno se debe cumplir que: 
--	1.	luminosity_fot < Threshold_LQ_BM-FOT	
(0801, 8, 1, 0801, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 20% a soleado 100% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--  2.  Threshold_LQ_BM_DALI_85_100 <= luminosity_lum <= LBL_THRESHOLD_OUT_OF_RANGE	
(0801, 8, 2, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 8, 2, 0803, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 20% a soleado 85% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_65_85 <= luminosity_lum <= Threshold_LQ_BM_DALI_85_100
(0801, 8, 3, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 8, 3, 0804, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 20% a soleado 65% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_50_65 <= luminosity_lum <= Threshold_LQ_BM_DALI_65_85
(0801, 8, 4, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 8, 4, 0805, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 20% a soleado 50% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_40_50 <= luminosity_lum <= Threshold_LQ_BM_DALI_50_65
(0801, 8, 5, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 8, 5, 0806, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 20% a soleado 40% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_30_40 <= luminosity_lum <= Threshold_LQ_BM_DALI_40_50
(0801, 8, 6, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 8, 6, 0807, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 20% a soleado 30% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_20_30 <=luminosity_lum <= CURRENT_TIMESTAMP	
(0801, 8, 7, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 8, 7, 0808, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De soleado 20% a soleado 10% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	luminosity_lum <= Threshold_LQ_BM_DALI_10_20	
(0801, 8, 8, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 8, 8, 0810, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--CONDICIONES PARA TRANSITAR DESDE EL ESTADO SOLEADO AL 10% AL RESTO DE ESTADOS
-- De soleado 10% a nocturno se debe cumplir que: 
--	1.	luminosity_fot < Threshold_LQ_BM-FOT	
(0801, 9, 1, 0801, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 10% a soleado 100% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--  2.  Threshold_LQ_BM_DALI_85_100 <= luminosity_lum <= LBL_THRESHOLD_OUT_OF_RANGE	
(0801, 9, 2, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 9, 2, 0803, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 10% a soleado 85% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_65_85 <= luminosity_lum <= Threshold_LQ_BM_DALI_85_100
(0801, 9, 3, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 9, 3, 0804, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 10% a soleado 65% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_50_65 <= luminosity_lum <= Threshold_LQ_BM_DALI_65_85
(0801, 9, 4, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 9, 4, 0805, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 10% a soleado 50% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_40_50 <= luminosity_lum <= Threshold_LQ_BM_DALI_50_65
(0801, 9, 5, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 9, 5, 0806, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De soleado 10% a soleado 40% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_30_40 <= luminosity_lum <= Threshold_LQ_BM_DALI_40_50
(0801, 9, 6, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 9, 6, 0807, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De soleado 10% a soleado 30% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_20_30 <=luminosity_lum <= CURRENT_TIMESTAMP	
(0801, 9, 7, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 9, 7, 0808, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De soleado 10% a soleado 20% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_10_20 <=luminosity_lum <= Threshold_LQ_BM_DALI_20_30	
(0801, 9, 8, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 9, 8, 0809, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--CONDICIONES PARA TRANSITAR DESDE EL ESTADO NOCTURNO AL RESTO DE ESTADOS
-- De Nocturno a soleado 100% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--  2.  Threshold_LQ_BM_DALI_85_100 <= luminosity_lum <= LBL_THRESHOLD_OUT_OF_RANGE	
(0801, 99, 1, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 99, 1, 0803, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De Nocturno a soleado 85% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_65_85 <= luminosity_lum <= Threshold_LQ_BM_DALI_85_100
(0801, 99, 2, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 99, 2, 0804, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
-- De Nocturno a soleado 65% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_50_65 <= luminosity_lum <= Threshold_LQ_BM_DALI_65_85 
(0801, 99, 3, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 99, 3, 0805, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),  
-- De Nocturno a soleado 50% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_30_50 <= luminosity_lum <= Threshold_LQ_BM_DALI_50_65
(0801, 99, 4, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 99, 4, 0806, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De Nocturno a soleado 40% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_30_40 <= luminosity_lum <= Threshold_LQ_BM_DALI_40_50
(0801, 99, 5, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(0801, 99, 5, 0807, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De Nocturno a soleado 30% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_20_30 <= luminosity_lum <= Threshold_LQ_BM_DALI_30_50
(0801, 99, 6, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 99, 6, 0808, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- De Nocturno a soleado 20% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	Threshold_LQ_BM_DALI_10_20 <=luminosity_lum <= Threshold_LQ_BM_DALI_20_30	
(0801, 99, 7, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 99, 7, 0809, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    
-- De Nocturno a soleado 10% se debe cumplir que: 
--	1.	Threshold_LQ_BM-FOT <= luminosity_fot <= Threshold_out_of_range	
--	2.	luminosity_lum <= Threshold_LQ_BM_DALI_10_20	
(0801, 99, 8, 0802, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(0801, 99, 8, 0810, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
