DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'dgt_gip') THEN
  
		INSERT INTO dgt_gip.sg_metadata_table_images (sg_metadata_table_id,id_value,group_name,file_name,description,"position") VALUES
			 (1,2,'photos','moto.jpeg',NULL,1),
			 (1,5,'photos','moto.jpeg',NULL,1),
			 (1,6,'photos','camion.jpeg',NULL,1),
			 (1,14,'photos','furgonetas.jpeg',NULL,1),
			 (1,29,'photos','furgonetas.jpeg',NULL,1),
			 (1,28,'photos','moto.jpeg',NULL,1),
			 (1,1,'photos','coche_sancion.jpg',NULL,1),
			 (1,4,'photos','coche_sancion.jpg',NULL,1),
			 (1,10,'photos','radar-dgt.jpg',NULL,1),
			 (1,11,'photos','coche_sancion.jpg',NULL,1),
			 (1,12,'photos','coche_sancion.jpg',NULL,1),
			 (1,13,'photos','coche_sancion.jpg',NULL,1),
			 (1,15,'photos','radar-dgt.jpg',NULL,1),
			 (1,16,'photos','coche_sancion.jpg',NULL,1),
			 (1,17,'photos','radar-dgt.jpg',NULL,1),
			 (1,18,'photos','radar-dgt.jpg',NULL,1),
			 (1,19,'photos','coche_sancion.jpg',NULL,1),
			 (1,20,'photos','coche_sancion.jpg',NULL,1),
			 (1,21,'photos','radar-dgt.jpg',NULL,1),
			 (1,22,'photos','coche_sancion.jpg',NULL,1),
			 (1,23,'photos','coche_sancion.jpg',NULL,1),
			 (1,25,'photos','coche_sancion.jpg',NULL,1),
			 (1,8,'photos','Trasera-BMW-Serie-3.jpg',NULL,1),
			 (1,8,'photos','Matricula-BMW-Serie-3.jpg',NULL,2),
			 (1,27,'photos','hiundai_i30.jpeg',NULL,1),
			 (1,27,'photos','Matricula_hiundai_i30.jpg',NULL,2),
			 (1,24,'photos','fiat-500-rojo-trasera-accion.jpg',NULL,1),
			 (1,24,'photos','MATRICULA_fiat-500-rojo-trasera-accion.jpg',NULL,2),
			 (1,7,'photos','GOLF.jpg',NULL,1),
			 (1,7,'photos','matricula_GOLF.jpg',NULL,2),
			 (1,9,'photos','Iveco_Stralis_XP_Edicion_Limitada_Pegaso.jpg',NULL,1),
			 (1,9,'photos','maticula_Iveco_Stralis_XP_Edicion_Limitada_Pegaso.jpg',NULL,2),
			 (1,26,'photos','honda_cbr600.jpeg',NULL,1),
			 (1,26,'photos','matricula_honda_cbr600.jpg',NULL,2),
			 (1,3,'photos','renault_trafic.jpg',NULL,1),
			 (1,3,'photos','matricula_renault_trafic.jpg',NULL,2);

  END IF;
END $$;
