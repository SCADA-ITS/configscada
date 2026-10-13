-- 
-- Table: conf.spaths
--
INSERT INTO conf.spaths
(spath_id, alias, description, enabled, visible, created_at, updated_at)VALUES
(10, 'Meteorología tronco', 'Troncal en túneles', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'Tunel', 'Túnel afectado', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'Meteorología pirámide', 'Bajada de la pirámide', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'Meteorología ramales', 'Ramales', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'Ramal', 'Ramal afectado', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(100, 'Aguas arriba', 'Aguas arriba', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101, 'Aguas arriba hasta 1km', 'Aguas arriba desde el incidente hasta 1km antes', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(102, 'Aguas arriba hasta 1.5km', 'Aguas arriba desde el incidente hasta 1.5km antes', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(103, 'Aguas arriba desde 1km', 'Aguas arriba 1km antes del incidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(104, 'Aguas arriba desde 1.5km', 'Aguas arriba 1.5km antes del incidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(105, 'Aguas abajo hasta 500m', 'Aguas abajo desde el incidente hasta 500m despues', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(106, 'Exterior', 'Exterior', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(107, 'Aguas arriba salida anterior', 'Aguas arriba hasta la salida anterior', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(108, 'Aguas arriba desde salida anterior', 'Aguas arriba desde la salida anterior hasta la boca del túnel', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(109, 'Exterior ramales de entrada', 'Exterior ramales de entrada', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(110, 'Aguas abajo', 'Aguas abajo desde el incidente', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(111, 'Megafonia N1', 'Megafonia N1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(112, 'Megafonia N2', 'Megafonia N2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(113, 'Tronco nivel no afectado', 'Tronco nivel no afectado', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(114, 'Exterior tubo', 'Exterior tub', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


--(100, 'Todos los troncales', 'Todos los troncales', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(101, 'Todos los ramales', 'Todos los ramales', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(102, 'Troncal afectado', 'Troncal afectado', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(103, 'Troncal no afectado', 'Troncal no afectado', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(104, 'Bajada de la pirámide', 'Troncal no afectado', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(105, 'Exterior troncal', 'Exterior troncal', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(106, 'Exterior ramal', 'Exterior ramal', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(107, 'Troncal afectado aguas arriba', 'Troncal afectado aguas arriba', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(108, 'Troncal afectado aguas arriba hasta 1km antes', 'Troncal afectado aguas arriba hasta 1km antes', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(109, 'Troncal afectado aguas arriba hasta 1.5km antes', 'Troncal afectado aguas arriba hasta 1.5km antes', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(110, 'Troncal afectado aguas arriba a partir de 1km', 'Troncal afectado aguas arriba a partir de 1km', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(111, 'Troncal afectado aguas arriba a partir de 1.5km', 'Troncal afectado aguas arriba a partir de 1.5km', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(112, 'Troncal afectado aguas abajo hasta 500m despues', 'Troncal afectado aguas abajo hasta 500m despues', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(113, 'Ramal de entrada anterior a la incidencia', 'Ramal de entrada anterior a la incidencia', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(114, 'Ramal aguas arriba', 'Ramal aguas arriba', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--(200, 'Megafonia N1', 'Megafonia N1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--(201, 'Megafonia N2', 'Megafonia N2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),


