INSERT INTO ui.vms_libraries(id, name, description, label, enabled, visible) VALUES
(1, 'Librería mensajes panel dgt', null, 'LBL_LIB_VMS_DGT', true, true),
(2, 'VMS_MAT_64_64', null, 'LBL_LIB_VMS_VMS_MAT_64_64', true, true),
(3, 'VMS_A_B', null, 'LBL_LIB_VMS_VMS_A_B', true, true),
(4, 'VMS_MAT_192_64', null, 'LBL_LIB_VMS_VMS_MAT_192_64', true, true),
(5, 'VMS_B_D', null, 'LBL_LIB_VMS_VMS_B_D', true, true),
(6, 'VMS_TEMP', null, 'LBL_LIB_VMS_VMS_TEMP', true, true),
(7, 'VMS_WEIGHT', null, 'LBL_LIB_VMS_VMS_WEIGHT', true, true),
(8, 'VMS_WEIGHT_LIMIT', null, 'LBL_LIB_VMS_VMS_WEIGHT_LIMIT', true, true),
(9, 'VMS_GR_TXT', null, 'LBL_LIB_VMS_VMS_GR_TXT', true, true),
(10, 'VMS_TXT', null, 'LBL_LIB_VMS_VMS_TXT', true, true),
(11, 'VMS_CLV_AF', null, 'LBL_LIB_VMS_VMS_CLV_AF', true, true),
(12, 'VMS_CLV_AF', null, 'LBL_LIB_VMS_VMS_CLV_AF', true, true),
(13, 'OVERWEIGHT', null, 'LBL_LIB_VMS_OVERWEIGHT', true, true),
(14, 'RED BEACON', null, 'LBL_LIB_VMS_BEACON', true, true),
(15, 'BLUE ARROW', null, 'LBL_LIB_VMS_ARROW', true, true),
(16, 'VMS_TXT_1LINE', null, 'LBL_LIB_VMS_VMS_TXT_1LINE', true, true),
(17, 'VMS Text 4 Lines', null, 'LBL_LIB_VMS_VMS_TEXT_4_LINES', true, true),
(18, 'VMS_MAT_128_64', null, 'LBL_LIB_VMS_VMS_MAT_128_64', true, true),
(22, 'Librería mensajes panel dgt', null, 'LBL_LIB_VMS_DGT', true, true);

INSERT INTO ui.vms_library_nodes(id, vms_library_id, name, description, label, signaling, enabled, visible) VALUES
(1, 22, 'Generico', null, 'LBL_GENERIC', null, true, true),
(2, 22, 'Meteo', null, 'LBL_METEO', null, true, true),
(3, 22, 'Seguridad', null, 'LBL_SECURITY', null, true, true),

(4, 22, 'Generico grupo 1', null, 'LBL_GENERIC_1', null, true, true),
(5, 22, 'Generico grupo 2', null, 'LBL_GENERIC_2', null, true, true),

(6, 22, 'Mensaje 1 Generico 1', null, 'LBL_MSG_1_GENERIC_1', '[{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":70,"alternance":71}]},{"zone":2,"alternance_ms":5000,"align": "left","texts":[{"id":1,"value":"MEN1 LIN1","alternance":"MEN1 LIN1 AL"},{"id":2,"value":"MEN1 LIN2","alternance":"MEN1 LIN2 AL"},{"id":3,"value":"MEN1 LIN3","alternance":"MEN1 LIN3 AL"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphics":[{"id":1,"value":60}]}]', true, true),
(7, 22, 'Mensaje 2 Generico 1', null, 'LBL_MSG_2_GENERIC_1', '[{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":50,"alternance":51}]},{"zone":2,"alternance_ms":5000,"align": "left","texts":[{"id":1,"value":"MEN2 LIN1","alternance":"MEN2 LIN1 AL"},{"id":2,"value":"MEN2 LIN2","alternance":"MEN2 LIN2 AL"},{"id":3,"value":"MEN2 LIN3","alternance":"MEN2 LIN3 AL"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphics":[{"id":1,"value":40}]}]', true, true),

(8, 22, 'Mensaje 3 Meteo', null, 'LBL_MSG_3_METEO', '[{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":81,"alternance":744}]},{"zone":2,"alternance_ms":5000,"align": "left","texts":[{"id":1,"value":"ACCIDENTE","alternance":"REDUZCA"},{"id":2,"value":"A 3 KM EN","alternance":"LA VELOCIDAD"},{"id":3,"value":"CARRIL DER.","alternance":"USE CAR. IZD"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphics":[{"id":1,"value":85}]}]', true, true),
(9, 22, 'Mensaje 4 Seguridad', null, 'LBL_MSG_4_SECURITY', '[{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":22,"alternance":29}]},{"zone":2,"alternance_ms":5000,"align": "left","texts":[{"id":1,"value":"PRECAUCION","alternance":"ATENCION","color":"#ff0000"},{"id":2,"value":"LA SENDA","alternance":"AMIGO","color":"#00ff00"},{"id":3,"value":"ES PELIGROSA","alternance":"CONDUCTOR","color":"#0000ff"}]},{"zone":3,"flashing_on_ms":1000,"flashing_off_ms":1000,"graphics":[{"id":1,"value":28}]}]', true, true),
(10, 22, 'Mensaje 5 Seguridad', null, 'LBL_MSG_5_SECURITY', '[{"zone":1,"graphics":[{"id":1,"value":438}]},{"zone":2,"alternance_ms":5000,"align": "left","texts":[{"id":1,"value":"CAMPAÑA"},{"id":2,"value":"CONTROL USO"},{"id":3,"value":"CINTURON"}]}]', true, true);

INSERT INTO ui.vms_library_hierarchies(vms_library_id, node_parent_id, node_child_id, enabled, visible) VALUES
(22, 1, 4, true, true),
(22, 1, 5, true, true),
(22, 4, 6, true, true),
(22, 5, 7, true, true),
(22, 2, 8, true, true),
(22, 3, 9, true, true),
(22, 3, 10, true, true);

INSERT INTO ui.vms_library_element_subtypes(element_subtype_id, vms_library_id, enabled, visible) VALUES
('ElementSubtype:1:71', 1, true, true),
('ElementSubtype:2:71', 2, true, true),
('ElementSubtype:3:71', 3, true, true),
('ElementSubtype:4:71', 4, true, true),
('ElementSubtype:5:71', 5, true, true),
('ElementSubtype:6:71', 6, true, true),
('ElementSubtype:7:71', 7, true, true),
('ElementSubtype:8:71', 8, true, true),
('ElementSubtype:9:71', 9, true, true),
('ElementSubtype:10:71', 10, true, true),
('ElementSubtype:11:71', 11, true, true),
('ElementSubtype:12:71', 12, true, true),
('ElementSubtype:13:71', 13, true, true),
('ElementSubtype:14:71', 14, true, true),
('ElementSubtype:15:71', 15, true, true),
('ElementSubtype:16:71', 16, true, true),
('ElementSubtype:17:71', 17, true, true),
('ElementSubtype:18:71', 18, true, true),
('ElementSubtype:22:71', 22, true, true);