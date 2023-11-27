INSERT INTO conf."plans" (plan_id,plan_mode_type_id,alias,description,enabled,visible,created_at,updated_at) VALUES
	 (1,1,'Test Audio','Test Audio',true,true,'2022-12-14 08:59:17.17073+01','2022-12-14 08:59:17.17073+01'),
	 (2,1,'Test Audio S1S1','Test Audio Bloque 1',true,true,'2022-12-14 10:25:18.814729+01','2022-12-14 10:27:48.331925+01'),
	 (3,1,'Test Audio S1S2','Test Audio S1S2',true,true,'2022-12-14 10:28:05.148378+01','2022-12-14 10:28:05.148378+01'),
	 (4,1,'Test Audio S1S3','Test Audio S1S3',true,true,'2022-12-14 10:29:05.109228+01','2022-12-14 10:29:05.109228+01'),
	 (5,1,'Test Audio S2','Test Audio S2',true,true,'2022-12-14 10:30:09.80668+01','2022-12-14 10:30:09.80668+01');

INSERT INTO conf.plan_nodes (node_id,alias,description,plan_id,enabled,visible) VALUES
	 (1,'Planes Generales','Planes Generales',NULL,true,true),
	 (2,'Test Audio','Test Audio',NULL,true,true),
	 (3,'Test Audio S1S1','Test Audio S1S1',2,true,true),
	 (4,'Test Audio S1S2','Test Audio S1S2',3,true,true),
	 (5,'Test Audio S1S3','Test Audio S1S3',4,true,true),
	 (6,'Test Audio S2','Test Audio S2',5,true,true);


INSERT INTO conf.plan_node_hierarchies (node_parent_id,node_child_id,enabled,visible) VALUES
	 (1,2,true,true),
	 (2,3,true,true),
	 (2,4,true,true),
	 (2,5,true,true),
	 (2,6,true,true);
