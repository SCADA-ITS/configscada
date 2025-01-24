INSERT INTO ui.pas_libraries(id, name, description, label, enabled, visible) VALUES
(1, 'Librería de señalización ', null, 'LBL_PAS_LIBRARY', true, true);

INSERT INTO ui.pas_messages(id, alias, description, message, audio, enabled, visible) VALUES
(1, 'Mensaje de test', 'Mensaje de test', '1', './resources/pasystem/messages/TESTCASTELLANO.mp3', true, true),
(2, 'Mensaje incendio túnel', 'Mensaje incendio túnel', '2', './resources/pasystem/messages/Mensaje_Incendio_Tunel.mp3', true, true),
(3, 'Peatón en túnel', '“Atención, Se encuentra Usted en zona de riesgo.” “Desplacese utilizando las aceras y llame al servicio de emergencia a través del poste SOS más cercano.”', '3', './resources/pasystem/messages/M01.mp3', true, true),
(4, 'Presencia prohibida en túnel', '“Atención, Se encuentra Usted en una zona prohibida y de riesgo.”Desplacese utilizando las aceras y llame al servicio de emergencia a través del poste SOS más cercano.”', '4', './resources/pasystem/messages/M02.mp3', true, true),
(5, 'Vehículo detenido en la calzada', '“Atención, se encuentra Usted en zona de riesgo.”"Si es posible reanude la marcha y diríjase inmediatamante a la salida del túnel."“En caso contrario, detenga su vehículo a la derecha y llame al servicio de emergencia a través del poste SOS más cercano.”', '5', './resources/pasystem/messages/M03.mp3', true, true),
(6, 'Retenciones en interior del túnel', '“Atención: Circule con precaución retenciones en el interior del túnel.”', '6', './resources/pasystem/messages/M04.mp3', true, true),
(7, 'Obstáculo en la calzada', '“Atención, reduzca la velocidad y circule con precaución debido a la presencia de obstáculos en la calzada"', '7', './resources/pasystem/messages/M05.mp3', true, true),
(8, 'Derrame de líquidos', '“Atención, reduzca la velocidad y circule con precaución debido a la presencia de líquidos que reducen la adherencia al firme."', '8', './resources/pasystem/messages/M06.mp3', true, true),
(9, 'Corte de carril hasta la sección afectada', '“Atención: reduzca la velocidad y circule con precaución debido a corte de carril en el túnel."', '9', './resources/pasystem/messages/M07.mp3', true, true),
(10, 'Instrucciones por sabotajes, manifestaciones o desórdenes públicos', '“Atención: Se encuentra Usted en zona de riesgo.”"Si usted se encuentra en el interior del túnel, si es posible, reanude la marcha y diríjase inmediatamante a la salida del túnel."“En caso contrario, apague el motor de su vehículo. y espere instrucciones.”"Si usted se encuentra en el exterior del túnel detenga su vehículo y pare el motor"."En el momento en que se reestablezca la situación se lo notificaremos"', '10', './resources/pasystem/messages/M08.mp3', true, true),
(11, 'Presencia de animales sueltos', '“Atención, reduzca la velocidad y circule con precaución debido a la presencia de animales sueltos en el interior del túnel."', '11', './resources/pasystem/messages/M09.mp3', true, true),
(12, 'Vehículo incendiado', '“Atención: Se encuentra Usted en zona de riesgo por incendio en el túnel”"Si usted se encuentra en el interior del túnel, si es posible, reanude la marcha y diríjase inmediatamante a la salida. "“En caso contrario, apague el motor de su vehículo y espere instrucciones.”"Si usted se encuentra en el exterior del túnel detenga su vehículo y pare el motor".', '12', './resources/pasystem/messages/M10.mp3', true, true),
(13, 'Aviso al conductor para que se detenga junto a poste SOS', '“Atención: Detengase y paré el motor de su vehículo junto al poste SOS más cercano para recibir instrucciones.”', '13', './resources/pasystem/messages/M11.mp3', true, true),
(14, 'Filtraciones de agua', '“Atención, reduzca la velocidad y circule con precaución debido a la presencia de líquidos que reducen la adherencia al firme."', '14', './resources/pasystem/messages/M12.mp3', true, true),
(15, 'Exceso de gálibo', '“Atención: Su vehículo está circulando con exceso de gálibo. Por favor, si es posible, diríjase a la salida más cercana.”“En caso contrario, deténgase, pare el motor de su vehículo y espere instrucciones.”', '15', './resources/pasystem/messages/M13.mp3', true, true),
(16, 'Vehículo circulando en sentido contrario', '“Atención: Extreme precaución por vehículo circulando en sentido contrario. Detengase a la derecha inmediatamente, pare el motor de su vehículo y espere instrucciones.”', '16', './resources/pasystem/messages/M14.mp3', true, true),
(17, 'Vehículo circulando marcha atrás', '“Atención: Extreme precaución por vehículo circulando marcha atrás. Detengase a la derecha inmediatamente, pare el motor de su vehículo y espere instrucciones.”', '17', './resources/pasystem/messages/M15.mp3', true, true),
(18, 'Túnel bloqueado', '“Atención: El túnel se encuentra bloqueado. En estos momentos está prohibida la circulación por el túnel”"Si usted se encuentra en el interior del túnel, si es posible reanude la marcha y diríjase inmediatamante a la salida del túnel. "“En caso contrario, apague el motor de su vehículo.”"Si usted se encuentra en el exterior del túnel detenga su vehículo y pare el motor"."En el momento en que se reestablezca la situación se lo notificaremos"', '18', './resources/pasystem/messages/M16.mp3', true, true),
(19, 'Interrumpir mensajes de emergencia', '"Atención: "', '19', './resources/pasystem/messages/M17.mp3', true, true),
(20, 'Utilización de Postes SOS', '"Atención: Vaya al poste SOS más cercano y espere intrucciones"', '20', './resources/pasystem/messages/M18.mp3', true, true),
(21, 'Zona Restringida', '"Atención: Se encuentra usted en una zona restringida. Su presencia no está autorizada en la zona en que se encuentra.""Atención: El personal de mantenimiento se pondrá en contacto para notificarle las siguientes instrucciones. Por favor, Atienda en todo momento las instrucciones del personal de mantenimiento."', '21', './resources/pasystem/messages/M19.mp3', true, true),
(22, 'Evacuación del túnel', '"Atención: Se encuentra Usted en zona de riesgo. Pare el motor, abandone su vehículo a la derecha y diríjase a la salida del túnel.”“Atención: prosiga su marcha con precaución y salga del tunel.”', '22', './resources/pasystem/messages/M20.mp3', true, true),
(23, 'Accidente en el túnel', '"Atención ha ocurrido un accidente, mantengase alejado de las bocas y espere instrucciones"', '23', './resources/pasystem/messages/Radio_01.mp3', true, true),
(24, 'Accidente en el túnel 2', '"Atención ha ocurrido un accidente en el túnel, se encuentra usted en zona de riesgo""Abandone su vehículo a la derecha y diríjase a la salida del túnel""Atención prosiga su marcha con precaución y salga del túnel"', '24', './resources/pasystem/messages/Radio_02.mp3', true, true),
(25, 'Accidente en el túnel 3', '"Atención ha ocurrido un accidente, mantengase alejado de las bocas y espere instrucciones""Atención prosiga su marcha con precaución y salga del túnel"', '25', './resources/pasystem/messages/Radio_03.mp3', true, true),
(26, 'Accidente en el túnel 4', '"Atención ha ocurrido un accidente, mantengase alejado de las bocas y espere instrucciones"', '26', './resources/pasystem/messages/Radio_04.mp3', true, true);

-- MAQUETA
--(2, 'Mensaje de alerta', 'Mensaje de alerta', '1001', './resources/pasystem/messages/ALERTACASTELLANO.mp3', true, true); 

INSERT INTO ui.pas_library_nodes(id, pas_library_id, name, description, label, pas_message_id, enabled, visible) VALUES
(1, 1, 'Mensajes Pas', null, 'LBL_PAS_NODE_LIBRERIA', null, true, true),
(2, 1, 'Mensajes de test', null, 'LBL_PAS_NODE_TEST_MESSAGE', 1, true, true),
(3, 1, 'Mensaje incendio túnel', null, 'LBL_PAS_NODE_FIRE_MESSAGE', 2, true, true),
(4, 1, 'Peatón en túnel', null, 'LBL_PAS_NODE_MESSEAGE_01', 3, true, true),
(5, 1, 'Presencia prohibida en túnel', null, 'LBL_PAS_NODE_MESSEAGE_02', 4, true, true),
(6, 1, 'Vehículo detenido en la calzada', null, 'LBL_PAS_NODE_MESSEAGE_03', 5, true, true),
(7, 1, 'Retenciones en interior del túnel', null, 'LBL_PAS_NODE_MESSEAGE_04', 6, true, true),
(8, 1, 'Obstáculo en la calzada', null, 'LBL_PAS_NODE_MESSEAGE_05', 7, true, true),
(9, 1, 'Derrame de líquidos', null, 'LBL_PAS_NODE_MESSEAGE_06', 8, true, true),
(10, 1, 'Corte de carril hasta la sección afectada', null, 'LBL_PAS_NODE_MESSEAGE_07', 9, true, true),
(11, 1, 'Instrucciones por sabotajes, manifestaciones o desórdenes públicos', null, 'LBL_PAS_NODE_MESSEAGE_08', 10, true, true),
(12, 1, 'Presencia de animales sueltos', null, 'LBL_PAS_NODE_MESSEAGE_09', 11, true, true),
(13, 1, 'Vehículo incendiado', null, 'LBL_PAS_NODE_MESSEAGE_10', 12, true, true),
(14, 1, 'Aviso al conductor para que se detenga junto a poste SOS', null, 'LBL_PAS_NODE_MESSEAGE_11', 13, true, true),
(15, 1, 'Filtraciones de agua', null, 'LBL_PAS_NODE_MESSEAGE_12', 14, true, true),
(16, 1, 'Exceso de gálibo', null, 'LBL_PAS_NODE_MESSEAGE_13', 15, true, true),
(17, 1, 'Vehículo circulando en sentido contrario', null, 'LBL_PAS_NODE_MESSEAGE_14', 16, true, true),
(18, 1, 'Vehículo circulando marcha atrás', null, 'LBL_PAS_NODE_MESSEAGE_15', 17, true, true),
(19, 1, 'Túnel bloqueado', null, 'LBL_PAS_NODE_MESSEAGE_16', 18, true, true),
(20, 1, 'Interrumpir mensajes de emergencia', null, 'LBL_PAS_NODE_MESSEAGE_17', 19, true, true),
(21, 1, 'Utilización de Postes SOS', null, 'LBL_PAS_NODE_MESSEAGE_18', 20, true, true),
(22, 1, 'Zona Restringida', null, 'LBL_PAS_NODE_MESSEAGE_19', 21, true, true),
(23, 1, 'Evacuación del túnel', null, 'LBL_PAS_NODE_MESSEAGE_20', 22, true, true),
(24, 1, 'Accidente en el túnel ', null, 'LBL_PAS_NODE_RADIO_MESSAGE_01', 23, true, true),
(25, 1, 'Accidente en el túnel 2', null, 'LBL_PAS_NODE_RADIO_MESSAGE_02', 24, true, true),
(26, 1, 'Accidente en el túnel 3', null, 'LBL_PAS_NODE_RADIO_MESSAGE_03', 25, true, true),
(27, 1, 'Accidente en el túnel 4', null, 'LBL_PAS_NODE_RADIO_MESSAGE_04', 26, true, true);

-- MAQUETA
--(3, 1, 'Mensaje de alerta', null, 'Mensaje de alerta', 2, true, true);

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
(1, 1, 20, true, true),
(1, 1, 21, true, true),
(1, 1, 22, true, true),
(1, 1, 23, true, true),
(1, 1, 24, true, true),
(1, 1, 25, true, true),
(1, 1, 26, true, true),
(1, 1, 27, true, true);




-- MAQUETA
--(1, 1, 3, true, true);

INSERT INTO ui.pas_library_elements(element_id, pas_library_id, enabled, visible) VALUES
('Element:5:1', 1, true, true);

