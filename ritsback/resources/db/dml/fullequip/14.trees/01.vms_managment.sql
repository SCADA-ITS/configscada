INSERT INTO conf.element_trees(element_tree_id, user_id, role_id, "label", enabled, visible) VALUES
(01, NULL, NULL, 'AP7 - SENTIDO CADIZ', true, true),
(02, NULL, NULL, 'AP7 - SENTIDO MALAGA', true, true);

INSERT INTO conf.element_tree_nodes(element_tree_node_id, element_tree_id, "label", elEment_type_id, element_id, "position", management_area_id, enabled, visible) VALUES
-- AP7 - SENTIDO CADIZ
-- Ausol I
(0101, 01, 'CALAHONDA', NULL, NULL, 1, 1, true, true),
	(010101, 01, '',71, 10, 2, 1, true, true),
(0105, 01, 'LA QUINTA', NULL, NULL, 1, 1, true, true),
	(010501, 01, '',71, 7, 2, 1, true, true),
(0107, 01, 'NAGÜELES', NULL, NULL, 1, 1, true, true),
	(010701, 01, '',71, 82, 2, 1, true, true),
(0108, 01, 'RIO VERDE', NULL, NULL, 1, 1, true, true),
	(010801, 01, '',71, 2, 2, 1, true, true),
(0106, 01, 'MONTEMAYOR', NULL, NULL, 1, 1, true, true),
	(010601, 01, '',71, 4, 2, 1, true, true),
(0110, 01, 'SANTA MARIA I', NULL, NULL, 1, 1, true, true),
	(011001, 01, '',71, 5, 2, 1, true, true),

--Ausol II 	
(0102, 01, 'CASARES', NULL, NULL, 1, 2, true, true),
	(010201, 01, '',71, 11, 2, 2, true, true),
	(010202, 01, '',71, 12, 3, 2, true, true),
	(010203, 01, '',71, 13, 4, 2, true, true),
	(010204, 01, '',71, 17, 5, 2, true, true),
	(010205, 01, '',71, 18, 6, 2, true, true),
	(010206, 01, '',71, 19, 7, 2, true, true),
	(010207, 01, '',71, 20, 8, 2, true, true),
	(010208, 01, '',71, 21, 9, 2, true, true),
	(010209, 01, '',71, 22, 10, 2, true, true),
	(010210, 01, '',71, 23, 11, 2, true, true),
	(010211, 01, '',71, 24, 12, 2, true, true),
	(010212, 01, '',71, 25, 13, 2, true, true),
	(010213, 01, '',71, 26, 14, 2, true, true),
	(010214, 01, '',71, 27, 15, 2, true, true),

(0103, 01, 'COROMINAS', NULL, NULL, 1, 2, true, true),
	(010301, 01, '',71, 39, 2, 2, true, true),
	(010302, 01, '',71, 42, 3, 2, true, true),
	(010303, 01, '',71, 43, 4, 2, true, true),
	(010304, 01, '',71, 50, 5, 2, true, true),
	(010305, 01, '',71, 51, 6, 2, true, true),
	(010306, 01, '',71, 52, 7, 2, true, true),
	(010307, 01, '',71, 53, 8, 2, true, true),
	(010308, 01, '',71, 54, 9, 2, true, true),
	(010309, 01, '',71, 55, 10, 2, true, true),
	(010310, 01, '',71, 60, 11, 2, true, true),
	(010311, 01, '',71, 61, 12, 2, true, true),
	(010312, 01, '',71, 62, 13, 2, true, true),
	(010313, 01, '',71, 63, 14, 2, true, true),
	
(0104, 01, 'ESTEPONA', NULL, NULL, 1, 2, true, true),
	(010401, 01, '',71, 81, 2, 2, true, true),
	(010402, 01, '',71, 90, 3, 2, true, true),	
		
(0109, 01, 'SANTA MARIA 2', NULL, NULL, 1, 2, true, true),
	(010901, 01, '',71, 64, 2, 2, true, true),
	(010902, 01, '',71, 65, 3, 2, true, true),
	(010903, 01, '',71, 66, 4, 2, true, true),
	(010904, 01, '',71, 70, 5, 2, true, true),
	(010905, 01, '',71, 71, 6, 2, true, true),
	(010906, 01, '',71, 72, 7, 2, true, true),
	(010907, 01, '',71, 73, 8, 2, true, true),
	(010908, 01, '',71, 78, 9, 2, true, true),

	
(0111, 01, 'DGT', NULL, NULL, 1, null, true, true),
	(011101, 01, '',71, 89, 2, null, true, true),
			
-- AP7 - SENTIDO MALAGA
-- Ausol I
(0201, 02, 'CALAHONDA', NULL, NULL, 1, 1, true, true),
	(020101, 02, '',71, 9, 2, 1, true, true),
(0205, 02, 'LA QUINTA', NULL, NULL, 1, 1, true, true),
	(020501, 02, '',71, 8, 2, 1, true, true),
(0206, 02, 'MONTEMAYOR', NULL, NULL, 1, 1, true, true),
	(020601, 02, '',71, 3, 2, 1, true, true),	
(0207, 02, 'NAGÜELES', NULL, NULL, 1, 1, true, true),
	(020701, 02, '',71, 83, 2, 1, true, true),	
(0208, 02, 'RIO VERDE', NULL, NULL, 1, 1, true, true),
	(020801, 02, '',71, 1, 2, 1, true, true),
(0210, 02, 'SANTA MARIA I', NULL, NULL, 1, 1, true, true),
	(021001, 02, '',71, 6, 2, 1, true, true),
	
--Ausol II 			
(0202, 02, 'CASARES', NULL, NULL, 1, 2, true, true),
	(020201, 02, '',71, 14, 2, 2, true, true),
	(020202, 02, '',71, 15, 3, 2, true, true),
	(020203, 02, '',71, 16, 4, 2, true, true),
	(020204, 02, '',71, 28, 5, 2, true, true),
	(020205, 02, '',71, 29, 6, 2, true, true),
	(020206, 02, '',71, 30, 7, 2, true, true),
	(020207, 02, '',71, 31, 8, 2, true, true),
	(020208, 02, '',71, 32, 9, 2, true, true),
	(020209, 02, '',71, 33, 10, 2, true, true),
	(020210, 02, '',71, 34, 11, 2, true, true),
	(020211, 02, '',71, 35, 12, 2, true, true),
	(020212, 02, '',71, 36, 13, 2, true, true),
	(020213, 02, '',71, 37, 14, 2, true, true),
	(020214, 02, '',71, 38, 15, 2, true, true),

(0203, 02, 'COROMINAS', NULL, NULL, 1, 2, true, true),
	(020301, 02, '',71, 40, 2, 2, true, true),
	(020302, 02, '',71, 41, 3, 2, true, true),
	(020303, 02, '',71, 44, 4, 2, true, true),
	(020304, 02, '',71, 45, 5, 2, true, true),
	(020305, 02, '',71, 46, 6, 2, true, true),
	(020306, 02, '',71, 47, 7, 2, true, true),
	(020307, 02, '',71, 48, 8, 2, true, true),
	(020308, 02, '',71, 49, 9, 2, true, true),
	(020309, 02, '',71, 56, 10, 2, true, true),
	(020310, 02, '',71, 57, 11, 2, true, true),
	(020311, 02, '',71, 58, 12, 2, true, true),
	(020312, 02, '',71, 59, 13, 2, true, true),

(0204, 02, 'ESTEPONA', NULL, NULL, 1, 2, true, true),
	(020401, 02, '',71, 84, 2, 2, true, true),
	(020402, 02, '',71, 85, 3, 2, true, true),

	
(0209, 02, 'SANTA MARIA 2', NULL, NULL, 1, 2, true, true),
	(020901, 02, '',71, 67, 2, 2, true, true),
	(020902, 02, '',71, 68, 3, 2, true, true),
	(020903, 02, '',71, 69, 4, 2, true, true),
	(020904, 02, '',71, 74, 5, 2, true, true),
	(020905, 02, '',71, 75, 6, 2, true, true),
	(020906, 02, '',71, 76, 7, 2, true, true),
	(020907, 02, '',71, 77, 8, 2, true, true),
	(020908, 02, '',71, 79, 9, 2, true, true),
	(020909, 02, '',71, 80, 10, 2, true, true),
	

(0211, 02, 'DGT', NULL, NULL, 1, null, true, true),
	(021101, 02, '',71, 86, 2, null, true, true),
	(021102, 02, '',71, 87, 3, null, true, true),
	(021103, 02, '',71, 88, 4, null, true, true);

	
INSERT INTO conf.element_tree_node_hierarchies (element_tree_node_hierarchy_id, element_tree_id, node_parent_id, node_child_id, enabled, visible) VALUES
--AP7 - SENTIDO CADIZ
(010101, 01, 0101, 010101, true, true),

(010201, 01, 0102, 010201, true, true),
(010202, 01, 0102, 010202, true, true),
(010203, 01, 0102, 010203, true, true),
(010204, 01, 0102, 010204, true, true),
(010205, 01, 0102, 010205, true, true),
(010206, 01, 0102, 010206, true, true),
(010207, 01, 0102, 010207, true, true),
(010208, 01, 0102, 010208, true, true),
(010209, 01, 0102, 010209, true, true),
(010210, 01, 0102, 010210, true, true),
(010211, 01, 0102, 010211, true, true),
(010212, 01, 0102, 010212, true, true),
(010213, 01, 0102, 010213, true, true),
(010214, 01, 0102, 010214, true, true),

(010301, 01, 0103, 010301, true, true),
(010302, 01, 0103, 010302, true, true),
(010303, 01, 0103, 010303, true, true),
(010304, 01, 0103, 010304, true, true),
(010305, 01, 0103, 010305, true, true),
(010306, 01, 0103, 010306, true, true),
(010307, 01, 0103, 010307, true, true),
(010308, 01, 0103, 010308, true, true),
(010309, 01, 0103, 010309, true, true),
(010310, 01, 0103, 010310, true, true),
(010311, 01, 0103, 010311, true, true),
(010312, 01, 0103, 010312, true, true),
(010313, 01, 0103, 010313, true, true),

(010401, 01, 0104, 010401, true, true),
(010402, 01, 0104, 010402, true, true),

(010501, 01, 0105, 010501, true, true),

(010601, 01, 0106, 010601, true, true),

(010701, 01, 0107, 010701, true, true),

(010801, 01, 0108, 010801, true, true),

(010901, 01, 0109, 010901, true, true),
(010902, 01, 0109, 010902, true, true),
(010903, 01, 0109, 010903, true, true),
(010904, 01, 0109, 010904, true, true),
(010905, 01, 0109, 010905, true, true),
(010906, 01, 0109, 010906, true, true),
(010907, 01, 0109, 010907, true, true),
(010908, 01, 0109, 010908, true, true),

(011001, 01, 0110, 011001, true, true),

(011101, 01, 0111, 011101, true, true),

--AP7 - SENTIDO MALAGA
(020101, 02, 0201, 020101, true, true),

(020201, 02, 0202, 020201, true, true),
(020202, 02, 0202, 020202, true, true),
(020203, 02, 0202, 020203, true, true),
(020204, 02, 0202, 020204, true, true),
(020205, 02, 0202, 020205, true, true),
(020206, 02, 0202, 020206, true, true),
(020207, 02, 0202, 020207, true, true),
(020208, 02, 0202, 020208, true, true),
(020209, 02, 0202, 020209, true, true),
(020210, 02, 0202, 020210, true, true),
(020211, 02, 0202, 020211, true, true),
(020212, 02, 0202, 020212, true, true),
(020213, 02, 0202, 020213, true, true),
(020214, 02, 0202, 020214, true, true),

(020301, 02, 0203, 020301, true, true),
(020302, 02, 0203, 020302, true, true),
(020303, 02, 0203, 020303, true, true),
(020304, 02, 0203, 020304, true, true),
(020305, 02, 0203, 020305, true, true),
(020306, 02, 0203, 020306, true, true),
(020307, 02, 0203, 020307, true, true),
(020308, 02, 0203, 020308, true, true),
(020309, 02, 0203, 020309, true, true),
(020310, 02, 0203, 020310, true, true),
(020311, 02, 0203, 020311, true, true),
(020312, 02, 0203, 020312, true, true),

(020401, 02, 0204, 020401, true, true),
(020402, 02, 0204, 020402, true, true),

(020501, 02, 0205, 020501, true, true),

(020601, 02, 0206, 020601, true, true),

(020701, 02, 0207, 020701, true, true),

(020801, 02, 0208, 020801, true, true),

(020901, 02, 0209, 020901, true, true),
(020902, 02, 0209, 020902, true, true),
(020903, 02, 0209, 020903, true, true),
(020904, 02, 0209, 020904, true, true),
(020905, 02, 0209, 020905, true, true),
(020906, 02, 0209, 020906, true, true),
(020907, 02, 0209, 020907, true, true),
(020908, 02, 0209, 020908, true, true),
(020909, 02, 0209, 020909, true, true),
	
(021001, 02, 0210, 021001, true, true),

(021101, 02, 0211, 021101, true, true),
(021102, 02, 0211, 021102, true, true),
(021103, 02, 0211, 021103, true, true);
