INSERT INTO ui.pas_libraries(id, name, description, label, enabled, visible) VALUES
(1, 'Librería de señalización ', null, 'LBL_PAS_LIB_AVO', true, true);

INSERT INTO ui.pas_messages(id, alias, description, message, audio, enabled, visible) VALUES
(1, 'CONGESTION', 'Congestión al interior del túnel', '1', './resources/pasystem/messages/CONGESTION.mp3', true, true),
(2, 'REDUZCA', 'Reduzca velocidad', '2', './resources/pasystem/messages/REDUZCA.mp3', true, true),
(3, 'DETENGA VEHÍCULO', 'Detenga el motor de su vehículo', '3', './resources/pasystem/messages/DETENGA_VEHICULO.mp3', true, true),
(4, 'ACCIDENTE', 'Atención. Accidente en el interior del túnel', '4', './resources/pasystem/messages/ACCIDENTE.mp3', true, true),
(5, 'VEH.DETENIDO', 'Precaución. Vehículo detenido en el interior del túnel', '5', './resources/pasystem/messages/VEH_DETENIDO.mp3', true, true),
(6, 'PI CERRADA', 'Pista izquierda cerrada', '6', './resources/pasystem/messages/PI_CERRADA.mp3', true, true),
(7, 'PC CERRADA', 'Pista central cerrada', '7', './resources/pasystem/messages/PC_CERRADA.mp3', true, true),
(8, 'PD CERRADA', 'Pista derecha cerrada', '8', './resources/pasystem/messages/PD_CERRADA.mp3', true, true),
(9, 'SALIDA', 'Utilice salida más próxima', '9', './resources/pasystem/messages/SALIDA.mp3', true, true),
(10, 'VEH.EMERGENCIA', 'Ceda el paso a los equipos de emergencia', '10', './resources/pasystem/messages/VEH_EMERGENCIA.mp3', true, true),
(11, 'INCENDIO', 'Atención. Incendio en el interior del túnel', '11', './resources/pasystem/messages/INCENDIO.mp3', true, true),
(12, 'SALIDA EMERGENCIA', 'Utilice salida de emergencia más cercana', '12', './resources/pasystem/messages/SALIDA_EMERGENCIA.mp3', true, true),
(13, 'BOCAS', 'Diríjase a las bocas del túnel con precaución', '13', './resources/pasystem/messages/BOCAS.mp3', true, true),
(14, 'CICLISTA', 'Precaución. Ciclista al interior del túnel', '14', './resources/pasystem/messages/CICLISTA.mp3', true, true),
(15, 'PEATÓN', 'Precaución. Peatón al interior del túnel', '15', './resources/pasystem/messages/PEATON.mp3', true, true),
(16, 'POSTE SOS', 'Utilice Poste SOS', '16', './resources/pasystem/messages/POSTE_SOS.mp3', true, true),
(17, 'TRABAJOS', 'Precaución. Trabajos al interior del túnel', '17', './resources/pasystem/messages/TRABAJOS.mp3', true, true),
(18, 'OBJETO', 'Precaución. Objeto en la vía', '18', './resources/pasystem/messages/OBJETO.mp3', true, true),
(19, 'SENTIDO CONTRARIO', 'Precaución. Vehículo en sentido contrario. Detenga su marcha', '19', './resources/pasystem/messages/SENTIDO_CONTRARIO.mp3', true, true);

-- MAQUETA
--(2, 'Mensaje de alerta', 'Mensaje de alerta', '1001', './resources/pasystem/messages/ALERTACASTELLANO.mp3', true, true); 

INSERT INTO ui.pas_library_nodes(id, pas_library_id, name, description, label, pas_message_id, enabled, visible) VALUES
(1, 1, 'Mensajes Pas', null, 'LBL_PAS_NODE_LIBRARY_PLAYER_HEADER', null, true, true),
	(2, 1, 'Mensaje de Congestion', null, 'LBL_PAS_NODE_CONGESTION', 1, true, true),
	(3, 1, 'Mensaje de Reducir', null, 'LBL_PAS_NODE_REDUCIR', 2, true, true),
	(4, 1, 'Mensaje de Detenerse', null, 'LBL_PAS_NODE_DETENERSE', 3, true, true),
	(5, 1, 'Mensaje de Accidente', null, 'LBL_PAS_NODE_ACCIDENTE', 4, true, true),
	(6, 1, 'Mensaje de Precaucion', null, 'LBL_PAS_NODE_PRECAUCION', 5, true, true),
	(7, 1, 'Mensaje de PI Cortado', null, 'LBL_PAS_NODE_CI_CORTADO', 6, true, true),
	(8, 1, 'Mensaje de PC Cortado', null, 'LBL_PAS_NODE_CC_CORTADO', 7, true, true),
	(9, 1, 'Mensaje de PD Cortado', null, 'LBL_PAS_NODE_CD_CORTADO', 8, true, true),
	(10, 1, 'Mensaje de Salida', null, 'LBL_PAS_NODE_SALIDA', 9, true, true),
	(11, 1, 'Mensaje de Emergencia', null, 'LBL_PAS_NODE_EMERGENCIA', 10, true, true),
	(12, 1, 'Mensaje de Incendio', null, 'LBL_PAS_NODE_INCENDIO', 11, true, true),
	(13, 1, 'Mensaje de Salida de Emergencia', null, 'LBL_PAS_NODE_SALIDA_EMERGENCIA', 12, true, true),
	(14, 1, 'Mensaje de Bocas', null, 'LBL_PAS_NODE_BOCAS', 13, true, true),
	(15, 1, 'Mensaje de Ciclista', null, 'LBL_PAS_NODE_CICLISTA', 14, true, true),
	(16, 1, 'Mensaje de Peaton', null, 'LBL_PAS_NODE_PEATON', 15, true, true),
	(17, 1, 'Mensaje de Poste SOS', null, 'LBL_PAS_NODE_POSTE_SOS', 16, true, true),
	(18, 1, 'Mensaje de Trabajos', null, 'LBL_PAS_NODE_TRABAJOS', 17, true, true),
	(19, 1, 'Mensaje de Objeto', null, 'LBL_PAS_NODE_OBJETO', 18, true, true),
	(20, 1, 'Mensaje de Sentido Contrario', null, 'LBL_PAS_NODE_SENT_CONTRARIO', 19, true, true);

INSERT INTO ui.pas_library_hierarchies(pas_library_id, node_parent_id, node_child_id, enabled, visible) VALUES
(1, 1, 2, true, true),
(1, 1, 3, true, true),
(1, 1, 4, true, true),
(1, 1, 5, true, true),
(1, 1, 6, true, true),
(1, 1, 7, true, true),
(1, 1, 8, true, true),
(1, 1, 9, true, true),
(1, 1, 10, true, true),
(1, 1, 11, true, true),
(1, 1, 12, true, true),
(1, 1, 13, true, true),
(1, 1, 14, true, true),
(1, 1, 15, true, true),
(1, 1, 16, true, true),
(1, 1, 17, true, true),
(1, 1, 18, true, true),
(1, 1, 19, true, true),
(1, 1, 20, true, true);

INSERT INTO ui.pas_library_elements(element_id, pas_library_id, enabled, visible) VALUES
('Element:5:1', 1, true, true),
('Element:5:2', 1, true, true),
('Element:5:3', 1, true, true),
('Element:5:4', 1, true, true),
('Element:5:5', 1, true, true),
('Element:5:6', 1, true, true),
('Element:5:7', 1, true, true),
('Element:5:8', 1, true, true),
('Element:5:9', 1, true, true);
