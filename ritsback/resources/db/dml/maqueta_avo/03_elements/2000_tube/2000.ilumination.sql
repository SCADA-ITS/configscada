-- Tubos para la iluminacion
INSERT INTO conf.elements
(element_type_id,  element_subtype_id,  element_id,  location_id,  keep_values_on_disconnected,  inherit_state_id,  alias,  description,  on_change_alarm,  on_change_state,  on_change_measure,  on_schedule,  enabled,  visible,  alert,  alarm_count,  created_at,  updated_at) VALUES 
--Entradas
(2000, NULL, 1001, NULL, NULL, NULL, 'S1TS - Tramo 1', 'S1TS - Tramo 1 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1027, NULL, NULL, NULL, 'IVIP - Tramo 27', 'IVIP - Tramo 27 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1037, NULL, NULL, NULL, 'ICCP - Tramo 37', 'ICCP - Tramo 37 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1038, NULL, NULL, NULL, 'ICCP - Tramo 38', 'ICCP - Tramo 38 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1058, NULL, NULL, NULL, 'INKP - Tramo 58', 'INKP - Tramo 58 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1059, NULL, NULL, NULL, 'INKP - Tramo 59', 'INKP - Tramo 59 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1066, NULL, NULL, NULL, 'IPRO - Tramo 66', 'IPRO - Tramo 66 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1071, NULL, NULL, NULL, 'ICCO - Tramo 71', 'ICCO - Tramo 71 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1072, NULL, NULL, NULL, 'ICCO - Tramo 72', 'ICCO - Tramo 72 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1073, NULL, NULL, NULL, 'IBTO - Tramo 73', 'IBTO - Tramo 73 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1074, NULL, NULL, NULL, 'IBTO - Tramo 74', 'IBTO - Tramo 74 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Interior
(2000, NULL, 1002, NULL, NULL, NULL, 'S1TS - Tramo 2', 'S1TS - Tramo 2 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1003, NULL, NULL, NULL, 'S1TS - Tramo 3', 'S1TS - Tramo 3 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1007, NULL, NULL, NULL, 'S1TS - Tramo 7', 'S1TS - Tramo 7 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1008, NULL, NULL, NULL, 'S2TS - Tramo 8', 'S2TS - Tramo 8 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1009, NULL, NULL, NULL, 'S2TS - Tramo 9', 'S2TS - Tramo 9 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1010, NULL, NULL, NULL, 'S2TS - Tramo 10', 'S2TS - Tramo 10 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1011, NULL, NULL, NULL, 'S2TS - Tramo 11', 'S2TS - Tramo 11 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1012, NULL, NULL, NULL, 'S2TS - Tramo 12', 'S2TS - Tramo 12 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1013, NULL, NULL, NULL, 'S2TS - Tramo 13', 'S2TS - Tramo 13 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1014, NULL, NULL, NULL, 'S2TS - Tramo 14', 'S2TS - Tramo 14 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1015, NULL, NULL, NULL, 'S2TS - Tramo 15', 'S2TS - Tramo 15 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1016, NULL, NULL, NULL, 'S2TS - Tramo 16', 'S2TS - Tramo 16 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1017, NULL, NULL, NULL, 'S2TS - Tramo 17', 'S2TS - Tramo 17 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1018, NULL, NULL, NULL, 'S2TS - Tramo 18', 'S2TS - Tramo 18 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1019, NULL, NULL, NULL, 'S2TS - Tramo 19', 'S2TS - Tramo 19 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1020, NULL, NULL, NULL, 'S2TS - Tramo 20', 'S2TS - Tramo 20 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1021, NULL, NULL, NULL, 'S2TS - Tramo 21', 'S2TS - Tramo 21 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1022, NULL, NULL, NULL, 'S2TS - Tramo 22', 'S2TS - Tramo 22 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1024, NULL, NULL, NULL, 'SCNO - Tramo 24', 'SCNO - Tramo 24 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1028, NULL, NULL, NULL, 'INKP - Tramo 28', 'INKP - Tramo 28 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1029, NULL, NULL, NULL, 'INKP - Tramo 29', 'INKP - Tramo 29 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1030, NULL, NULL, NULL, 'ICNP - Tramo 30', 'ICNP - Tramo 30 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1031, NULL, NULL, NULL, 'ICNP - Tramo 31', 'ICNP - Tramo 31 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1032, NULL, NULL, NULL, 'ICNP - Tramo 32', 'ICNP - Tramo 32 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1033, NULL, NULL, NULL, 'ICNP - Tramo 33', 'ICNP - Tramo 33 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1034, NULL, NULL, NULL, 'SMPN - Tramo 34', 'SMPN - Tramo 34 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1035, NULL, NULL, NULL, 'SICO - Tramo 35', 'SICO - Tramo 35 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1039, NULL, NULL, NULL, 'SBTP - Tramo 39', 'SBTP - Tramo 39 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1043, NULL, NULL, NULL, 'S1TN - Tramo 43', 'S1TN - Tramo 43 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1044, NULL, NULL, NULL, 'S2TN - Tramo 44', 'S2TN - Tramo 44 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1045, NULL, NULL, NULL, 'S2TN - Tramo 45', 'S2TN - Tramo 45 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1046, NULL, NULL, NULL, 'S2TN - Tramo 46', 'S2TN - Tramo 46 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1047, NULL, NULL, NULL, 'S2TN - Tramo 47', 'S2TN - Tramo 47 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1048, NULL, NULL, NULL, 'S2TN - Tramo 48', 'S2TN - Tramo 48 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1049, NULL, NULL, NULL, 'S2TN - Tramo 49', 'S2TN - Tramo 49 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1050, NULL, NULL, NULL, 'S2TN - Tramo 50', 'S2TN - Tramo 50 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1051, NULL, NULL, NULL, 'S2TN - Tramo 51', 'S2TN - Tramo 51 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1052, NULL, NULL, NULL, 'S2TN - Tramo 52', 'S2TN - Tramo 52 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1053, NULL, NULL, NULL, 'S2TN - Tramo 53', 'S2TN - Tramo 53 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1054, NULL, NULL, NULL, 'S2TN - Tramo 54', 'S2TN - Tramo 54 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1055, NULL, NULL, NULL, 'S2TN - Tramo 55', 'S2TN - Tramo 55 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1056, NULL, NULL, NULL, 'S2TN - Tramo 56', 'S2TN - Tramo 56 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1060, NULL, NULL, NULL, 'INKO - Tramo 60', 'INKO - Tramo 60 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1061, NULL, NULL, NULL, 'SNKO - Tramo 61', 'SNKO - Tramo 61 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1062, NULL, NULL, NULL, 'SNKO - Tramo 62', 'SNKO - Tramo 62 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1063, NULL, NULL, NULL, 'SNKO - Tramo 63', 'SNKO - Tramo 63 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1064, NULL, NULL, NULL, 'SNKO - Tramo 64', 'SNKO - Tramo 64 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1065, NULL, NULL, NULL, 'SNKP - Tramo 65', 'SNKP - Tramo 65 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1068, NULL, NULL, NULL, 'SMPS - Tramo 68', 'SMPS - Tramo 68 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1069, NULL, NULL, NULL, 'SMPN - Tramo 69', 'SMPN - Tramo 69 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 

--Salidas
(2000, NULL, 1004, NULL, NULL, NULL, 'S1TS - Tramo 4', 'S1TS - Tramo 4 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1005, NULL, NULL, NULL, 'S1TS - Tramo 5', 'S1TS - Tramo 5 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1006, NULL, NULL, NULL, 'S1TS - Tramo 6', 'S1TS - Tramo 6 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1023, NULL, NULL, NULL, 'SCNO - Tramo 23', 'SCNO - Tramo 23 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1025, NULL, NULL, NULL, 'SAEB - Tramo 25', 'SAEB - Tramo 25 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1026, NULL, NULL, NULL, 'SIGO - Tramo 26', 'SIGO - Tramo 26 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1036, NULL, NULL, NULL, 'SICO - Tramo 36', 'SICO - Tramo 36 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1040, NULL, NULL, NULL, 'SBTP - Tramo 40', 'SBTP - Tramo 40 Norte-Sur', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1041, NULL, NULL, NULL, 'S1TN - Tramo 41', 'S1TN - Tramo 41 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1042, NULL, NULL, NULL, 'S1TN - Tramo 42', 'S1TN - Tramo 42 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1057, NULL, NULL, NULL, 'SVIP - Tramo 57', 'SVIP - Tramo 57 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1067, NULL, NULL, NULL, 'SAPR - Tramo 67', 'SAPR - Tramo 67 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, NULL, 1070, NULL, NULL, NULL, 'SMPN - Tramo 70', 'SMPN - Tramo 70 Sur-Norte', NULL, NULL, NULL, NULL, true, true, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
(2000, 1003, 1,1001, 'S1TS_Tramo_1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1002, 'S1TS_Tramo_2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1003, 'S1TS_Tramo_3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1004, 'S1TS_Tramo_4', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1005, 'S1TS_Tramo_5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1006, 'S1TS_Tramo_6', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1007, 'S1TS_Tramo_7', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1008, 'S2TS_Tramo_8', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1009, 'S2TS_Tramo_9', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1010, 'S2TS_Tramo_10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1011, 'S2TS_Tramo_11', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1012, 'S2TS_Tramo_12', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1013, 'S2TS_Tramo_13', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1014, 'S2TS_Tramo_14', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1015, 'S2TS_Tramo_15', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1016, 'S2TS_Tramo_16', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1017, 'S2TS_Tramo_17', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1018, 'S2TS_Tramo_18', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1019, 'S2TS_Tramo_19', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1020, 'S2TS_Tramo_20', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1021, 'S2TS_Tramo_21', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1022, 'S2TS_Tramo_22', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1023, 'SCNO_Tramo_23', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1024, 'SCNO_Tramo_24', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1025, 'SAEB_Tramo_25', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1026, 'SIGO_Tramo_26', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1027, 'IVIP_Tramo_27', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1028, 'INKP_Tramo_28', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1029, 'INKP_Tramo_29', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1030, 'ICNP_Tramo_30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1031, 'ICNP_Tramo_31', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1032, 'ICNP_Tramo_32', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1033, 'ICNP_Tramo_33', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1034, 'SMPN_Tramo_34', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1035, 'SICO_Tramo_35', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1036, 'SICO_Tramo_36', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1037, 'ICCP_Tramo_37', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1038, 'ICCP_Tramo_38', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1039, 'SBTP_Tramo_39', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1040, 'SBTP_Tramo_40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1041, 'S1TN_Tramo_41', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1042, 'S1TN_Tramo_42', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1043, 'S1TN_Tramo_43', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1044, 'S2TN_Tramo_44', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1045, 'S2TN_Tramo_45', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1046, 'S2TN_Tramo_46', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1047, 'S2TN_Tramo_47', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1048, 'S2TN_Tramo_48', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1049, 'S2TN_Tramo_49', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1050, 'S2TN_Tramo_50', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1051, 'S2TN_Tramo_51', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1052, 'S2TN_Tramo_52', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1053, 'S2TN_Tramo_53', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1054, 'S2TN_Tramo_54', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1055, 'S2TN_Tramo_55', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1056, 'S2TN_Tramo_56', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1057, 'SVIP_Tramo_57', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1058, 'INKP_Tramo_58', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1059, 'INKP_Tramo_59', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1060, 'INKO_Tramo_60', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1061, 'SNKO_Tramo_61', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1062, 'SNKO_Tramo_62', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1063, 'SNKO_Tramo_63', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1064, 'SNKO_Tramo_64', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1065, 'SNKP_Tramo_65', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1066, 'IPRO_Tramo_66', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1067, 'SAPR_Tramo_67', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1068, 'SMPS_Tramo_68', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1069, 'SMPN_Tramo_69', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1070, 'SMPN_Tramo_70', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1071, 'ICCO_Tramo_71', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1072, 'ICCO_Tramo_72', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1073, 'IBTO_Tramo_73', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003, 1,1074, 'IBTO_Tramo_74', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.element_values
(element_type_id, element_type_param_id, param_type_id, element_id, value, enabled, visible, created_at, updated_at) VALUES
--Entradas
(2000, 2, 1, 1001, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1027, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1037, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1038, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1058, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1059, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1066, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1071, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1072, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1073, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1074, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Interior
(2000, 2, 1, 1002, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1003, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1007, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1008, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1009, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1010, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1011, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1012, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1013, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1014, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1015, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1016, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1017, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1018, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1019, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1020, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1021, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1022, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1024, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1028, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1029, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1030, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1031, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1032, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1033, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1034, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1035, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1039, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1043, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1044, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1045, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1046, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1047, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1048, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1049, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1050, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1051, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1052, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1053, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1054, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1055, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1056, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1060, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1061, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1062, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1063, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1064, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1065, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1068, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1069, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Diurno", "labelAlias": "LBL_DIURNO"},
	{"id": 4, "alias": "REGIMEN_1", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--Salidas
(2000, 2, 1, 1004, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1005, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1006, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1023, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1025, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1026, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1036, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1040, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1041, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1042, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1057, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1067, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 2, 1, 1070, '{	
	"regimes": [ {"id": 0, "alias": "REGIMEN_0", "description": "Soleado", "labelAlias": "LBL_SOLEADO"},
	{"id": 1, "alias": "REGIMEN_1", "description": "Nublado", "labelAlias": "LBL_NUBLADO"},
	{"id": 2, "alias": "REGIMEN_2", "description": "Crepuscular", "labelAlias": "LBL_CREPUSCULAR"},
	{"id": 3, "alias": "REGIMEN_3", "description": "Nocturno", "labelAlias": "LBL_NOCTURNO"},
	{"id": 4, "alias": "REGIMEN_4", "description": "Nocturno reducido", "labelAlias": "LBL_NOCTURNO_REDUCIDO"}]
}', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.element_io_controller_modules (element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES
(2000, 1001,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2000, 1002,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1003,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1004,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1005,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1006,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1007,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1008,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1009,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1010,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1011,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1012,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1013,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1014,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1015,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1016,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1017,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1018,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1019,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1020,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1021,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1022,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1023,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1024,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1025,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1026,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1027,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1028,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1029,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1030,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1031,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1032,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1033,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1034,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1035,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1036,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1037,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1038,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1039,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1040,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1041,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1042,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1043,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1044,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1045,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1046,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1047,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1048,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1049,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1050,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1051,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1052,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1053,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1054,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1055,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1056,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1057,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1058,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1059,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1060,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1061,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1062,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1063,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1064,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1065,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1066,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1067,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1068,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1069,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1070,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1071,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1072,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1073,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2000, 1074,1, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Update groovy
UPDATE master.command_element_types SET on_change='SignallingCommand' WHERE element_type_id=2000 AND command_element_type_id=4;
UPDATE master.command_element_types SET on_change='SignallingCommand' WHERE element_type_id=2000 AND command_element_type_id=5;
UPDATE master.command_element_types SET on_change='SignallingCommand' WHERE element_type_id=2000 AND command_element_type_id=6;
UPDATE master.command_element_types SET on_change='SignallingCommand' WHERE element_type_id=2000 AND command_element_type_id=7;





