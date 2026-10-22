INSERT INTO ui.selector_trees 
(id, default_module_id, label,enabled,visible) VALUES 
(1, 901, 'LBL_SINOPTICOS',true,true);

INSERT INTO ui.selector_tree_nodes 
(id, selector_tree_id, label, image, module_id,enabled,visible) VALUES 
(1, 1, 'LBL_TREE_SINOPTICO', 'trees/map.svg', 901,true,true);
