-- 
-- Table: conf.stretchs
--
INSERT INTO conf.stretchs
(stretch_id, stretch_type_id, alias, description, pk_ini, pk_end, lanes, road_id, enabled, visible, created_at, updated_at) VALUES
(1000, 2, 'TEST', 'TEST', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001, 2, 'TEST_2', 'TEST_2', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1002, 2, 'TEST_3', 'TEST_3', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1, 'D4_1 : Jarovce-Rusovce', 'D4_1 : Jarovce-Rusovce', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'D4_2 : Rusovce-Ketelec', 'D4_2 : Rusovce-Ketelec', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 'D4_3 : Ketelec-Rovinca', 'D4_3 : Ketelec-Rovinca', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 'D4_4 : Rovinka-Pod.Biskupice', 'D4_4 : Rovinka-Pod.Biskupice', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1, 'D4_5 : Pod.Biskupice-Ivanka západ', 'D4_5 : Pod.Biskupice-Ivanka západ', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, 'D4_6 : Ivanka západ-Ivanka sever', 'D4_6 : Ivanka západ-Ivanka sever', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1, 'D4_7 : Ivanka sever-Cierna voda', 'D4_7 : Ivanka sever-Cierna voda', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1, 'D4_8 : Cierna voda-Raca', 'D4_8 : Cierna voda-Raca', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 1, 'R7_1 : Prievoz-Ketelec', 'R7_1 : Prievoz-Ketelec', 0, 0, 0, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 1, 'R7_2 : Ketelec-Dunajská Lužná', 'R7_2 : Ketelec-Dunajská Lužná', 0, 0, 0, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 1, 'R7_3 : Dunajská Lužná-Šamorín', 'R7_3 : Dunajská Lužná-Šamorín', 0, 0, 0, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 1, 'R7_4 : Šamorín-Holice', 'R7_4 : Šamorín-Holice', 0, 0, 0, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 1, 'R7_5 : Dunajská Lužná-Holice', 'R7_5 : Dunajská Lužná-Holice', 0, 0, 0, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.stretch_stretchs
--
INSERT INTO conf.stretch_stretchs 
(stretch_stretch_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1, 'SUPER STRETCH TEST 1', 'SUPER STRETCH TEST 1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'SUPER STRETCH TEST 2', 'SUPER STRETCH TEST 2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--
-- Table: conf.conf.stretch_stretchs_stretchs
--
INSERT INTO conf.stretch_stretchs_stretchs 
(stretch_stretch_id, stretch_id, enabled, visible, created_at, updated_at) VALUES
(1, 1000, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1001, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1002, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1000, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
		