INSERT INTO ui.pas_libraries(id, name, description, label, enabled, visible) VALUES
(1, 'Librería de señalización ', null, 'LBL_LIB_PAS_DGT', true, true);

INSERT INTO ui.pas_messages(id, alias, description, message, audio, enabled, visible) VALUES
(1, 'Mensaje de señalización UNO', 'Mensaje de señalización UNO descripcion', 'sonido', 'sound/alarm.mp3', true, true),
(2, 'Mensaje de señalización DOS', 'Mensaje de señalización DOS descripcion', 'megafonia', null, true, true);

INSERT INTO ui.pas_library_nodes(id, pas_library_id, name, description, label, pas_message_id, enabled, visible) VALUES
(1, 1, 'Generico', null, 'LBL_GENERIC', null, true, true),
(2, 1, 'Meteo', null, 'LBL_METEO', null, true, true),
(3, 1, 'Seguridad', null, 'LBL_SECURITY', null, true, true),

(4, 1, 'Generico grupo 1', null, 'LBL_GENERIC_1', null, true, true),
(5, 1, 'Generico grupo 2', null, 'LBL_GENERIC_2', null, true, true),

(6, 1, 'Mensaje 1 Generico 1', null, 'LBL_MSG_1_GENERIC_1', 2, true, true),
(7, 1, 'Mensaje 2 Generico 1', null, 'LBL_MSG_2_GENERIC_1', 1, true, true);

INSERT INTO ui.pas_library_hierarchies(pas_library_id, node_parent_id, node_child_id, enabled, visible) VALUES
(1, 1, 4, true, true),
(1, 1, 5, true, true),
(1, 4, 6, true, true),
(1, 5, 7, true, true);

INSERT INTO ui.pas_library_elements(element_id, pas_library_id, enabled, visible) VALUES
('Element:5:1', 1, true, true);