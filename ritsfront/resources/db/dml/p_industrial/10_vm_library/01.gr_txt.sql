INSERT INTO ui.vms_libraries(id, name, description, label, enabled, visible) VALUES
(01, 'Librería mensajes', null, 'LBL_LIB_VMS', true, true);

INSERT INTO ui.vms_library_element_subtypes(element_subtype_id, vms_library_id, enabled, visible) VALUES
('ElementSubtype:19:71', 01, true, true);

INSERT INTO ui.vms_library_nodes (id,vms_library_id,"name",description,"label",signaling,enabled,visible) VALUES
(1,1,'INFORMATIVOS',NULL,'INFORMATIVOS',NULL,true,true),
(3,1,'CLIMATICOS',NULL,'CLIMATICOS',NULL,true,true),
(4,1,'TRAFICO',NULL,'TRAFICO',NULL,true,true),
(6,1,'BIENVENIDA',NULL,'BIENVENIDA',NULL,true,true),
(7,1,'Bienvenida',NULL,'Bienvenida','[{"zone":1,"graphics":[{"id":1,"value":"99"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"BIENVENIDO","color":"#FFFFFF"},{"id":2,"value":"A PUENTE","color":"#FFFFFF"},{"id":3,"value":"INDUSTRIAL","color":"#FFFFFF"}]}]',true,true),
(8,1,'Niebla',NULL,'Niebla','[{"zone":1,"graphics":[{"id":1,"value":"138"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"PRECUACION","color":"#FFFFFF"},{"id":2,"value":"BANCOS DE","color":"#FFFFFF"},{"id":3,"value":"NIEBLA","color":"#FFFFFF"}]}]',true,true),
(9,1,'Lluvia',NULL,'Lluvia','[{"zone":1,"graphics":[{"id":1,"value":"139"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"PRECAUCION","color":"#FFFFFF"},{"id":2,"value":"LLUVIA","color":"#FFFFFF"},{"id":3,"value":"MODERADA","color":"#FFFFFF"}]}]',true,true),
(10,1,'Accidente',NULL,'Accidente','[{"zone":1,"graphics":[{"id":1,"value":"141"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"PRECUACION","color":"#FFFFFF"},{"id":2,"value":"ACCIDENTE","color":"#FFFFFF"},{"id":3,"value":"EN RUTA","color":"#FFFFFF"}]}]',true,true),
(11,1,'TLF EMERGENCIA',NULL,'TLF EMERGENCIA',NULL,true,true),
(12,1,'Telefono',NULL,'Telefono','[{"zone":1,"graphics":[{"id":1,"value":"44"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"TELEFONO DE","color":"#FFFFFF"},{"id":2,"value":"EMERGENCIAS","color":"#FFFFFF"},{"id":3,"value":"600 006 2100","color":"#FFFFFF"}]}]',true,true),
(13,1,'Luces',NULL,'Luces','[{"zone":1,"graphics":[{"id":1,"value":"53"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"POR FAVOR","color":"#FFFFFF"},{"id":2,"value":"ENCIENDA","color":"#FFFFFF"},{"id":3,"value":"SUS LUCES","color":"#FFFFFF"}]}]',true,true),
(14,1,'Portico sin cobro',NULL,'Portico sin cobro','[{"zone":1,"graphics":[{"id":1,"value":"55"}]},{"zone":2,"align":"center","texts":[{"id":1,"value":"PORTICO","color":"#FFFFFF"},{"id":2,"value":"SIN","color":"#FFFFFF"},{"id":3,"value":"COBRO","color":"#FFFFFF"}]}]',true,true),
(15,1,'Cinturon',NULL,'Cinturon','[{"zone":2,"align":"center","texts":[{"id":1,"value":"USE","color":"#FFFFFF"},{"id":2,"value":"CINTURON","color":"#FFFFFF"},{"id":3,"value":"DE SEGURIDAD","color":"#FFFFFF"}]},{"zone":1,"graphics":[{"id":1,"value":"54"}]}]',true,true),
(16,1,'Transito lento',NULL,'Transito lento','[{"zone":2,"alternance_ms":5000,"align":"center","texts":[{"id":1,"value":"ATENCION","alternance":"MANTENGA","color":"#FFFFFF"},{"id":2,"value":"TRANSITO","alternance":"DISTANCIA","color":"#FFFFFF"},{"id":3,"value":"LENTO","alternance":"DE SEGURIDAD","color":"#FFFFFF"}]},{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":"141","alternance":"45"}]}]',true,true),
(17,1,'Carril izq cortado',NULL,'Carril izq cortado','[{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":"118","alternance":"68"}]},{"zone":2,"alternance_ms":5000,"align":"center","texts":[{"id":1,"value":"CARRIL","alternance":"CAMBIESE","color":"#FFFFFF"},{"id":2,"value":"IZQUIERDO","alternance":"AL CARRIL","color":"#FFFFFF"},{"id":3,"value":"CORTADO","alternance":"DERECHO","color":"#FFFFFF"}]}]',true,true),
(18,1,'Carril drch cortado',NULL,'Carril drch cortado','[{"zone":2,"alternance_ms":5000,"align":"center","texts":[{"id":1,"value":"CARRIL","alternance":"CAMBIESE","color":"#FFFFFF"},{"id":2,"value":"DERECHO","alternance":"AL CARRIL","color":"#FFFFFF"},{"id":3,"value":"CORTADO","alternance":"IZQUIERDO","color":"#FFFFFF"}]},{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":"120","alternance":"70"}]}]',true,true),
(20,1,'OBRAS',NULL,'OBRAS',NULL,true,true),
(21,1,'Obras en la ruta',NULL,'Obras en la ruta','[{"zone":1,"alternance_ms":5000,"graphics":[{"id":1,"value":"76","alternance":"102"}]},{"zone":2,"alternance_ms":5000,"align":"center","texts":[{"id":1,"value":"PRECAUCION","alternance":"DISMINUYA","color":"#FFFFFF"},{"id":2,"value":"OBRAS EN","alternance":"LA","color":"#FFFFFF"},{"id":3,"value":"LA RUTA","alternance":"VELOCIDAD","color":"#FFFFFF"}]}]',true,true);

INSERT INTO ui.vms_library_hierarchies (vms_library_id,node_parent_id,node_child_id,enabled,visible) VALUES
(1,6,7,true,true),
(1,3,8,true,true),
(1,3,9,true,true),
(1,4,10,true,true),
(1,11,12,true,true),
(1,1,13,true,true),
(1,1,14,true,true),
(1,1,15,true,true),
(1,4,16,true,true),
(1,4,17,true,true),
(1,4,18,true,true),
(1,20,21,true,true);
