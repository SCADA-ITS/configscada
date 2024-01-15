INSERT INTO ui.vo_entities(id, vo_entity_type_id, enabled, visible) VALUES
--Localizaciones incidencias mapa para túnel de Rio Verde
('Location:1000', 'LocationUI', true, true),
('Location:1001', 'LocationUI', true, true),
('Location:1002', 'LocationUI', true, true),

--Localizaciones incidencias mapa para túnel de MonteMayor
('Location:1007', 'LocationUI', true, true),
('Location:1008', 'LocationUI', true, true),
('Location:1009', 'LocationUI', true, true),

--Localizaciones incidencias mapa para túnel de Sta. Maria I
('Location:1018', 'LocationUI', true, true),
('Location:1019', 'LocationUI', true, true),
('Location:1020', 'LocationUI', true, true),

--Localizaciones incidencias mapa para túnel de La Quinta
('Location:1021', 'LocationUI', true, true),
('Location:1022', 'LocationUI', true, true),
('Location:1023', 'LocationUI', true, true),

--Localizaciones incidencias mapa para túnel de Calahonda
('Location:1031', 'LocationUI', true, true),
('Location:1032', 'LocationUI', true, true),
('Location:1033', 'LocationUI', true, true),

--Localizaciones incidencias mapa para túnel de Casares
('Location:1042', 'LocationUI', true, true),
('Location:1043', 'LocationUI', true, true),
('Location:1044', 'LocationUI', true, true),

--Localizaciones incidencias mapa para túnel de Corominas
('Location:1053', 'LocationUI', true, true),
('Location:1054', 'LocationUI', true, true),
('Location:1055', 'LocationUI', true, true),

--Localizaciones incidencias mapa para túnel de Sta. Maria II
('Location:1064', 'LocationUI', true, true),
('Location:1065', 'LocationUI', true, true),
('Location:1066', 'LocationUI', true, true),

--Localizaciones incidencias mapa para túnel de Estepona
('Location:1075', 'LocationUI', true, true),
('Location:1076', 'LocationUI', true, true),
('Location:1077', 'LocationUI', true, true),

--Localizaciones incidencias mapa para túnel de Nagueles
('Location:1078', 'LocationUI', true, true),
('Location:1079', 'LocationUI', true, true),
('Location:1080', 'LocationUI', true, true);

INSERT INTO ui.vo_entity_hierarchies(parent_id, child_id, "position") VALUES
('Location:4', 'Location:2', 1),
('Location:4', 'Location:3', 2);

