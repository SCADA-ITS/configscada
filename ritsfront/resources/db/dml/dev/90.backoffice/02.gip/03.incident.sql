DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'gip') THEN
		
		INSERT INTO gip.type_detections (alias) VALUES('Manual');
		INSERT INTO gip.type_detections (alias) VALUES('Automática');
		
		INSERT INTO gip.sources (alias) VALUES('Anemometro');
		INSERT INTO gip.sources (alias) VALUES('Bomberos');
		
		INSERT INTO gip.states (alias) VALUES('En ejecucion');
		INSERT INTO gip.states (alias) VALUES('Pendiente');
		INSERT INTO gip.states (alias) VALUES('Cerrada');
		INSERT INTO gip.states (alias) VALUES('Cancelada');
		
		
		INSERT INTO gip.types (alias) VALUES('A - Acccidente');
		INSERT INTO gip.types (alias) VALUES('B - Incidente');
		
		
		INSERT INTO gip.sub_types (alias) VALUES('A1 - Atropello');
		INSERT INTO gip.sub_types (alias) VALUES('B1 - Caida de carga si daños en infraestructura');
		
		INSERT INTO gip.levels (alias) VALUES('IE');
		INSERT INTO gip.levels (alias) VALUES('MA');
		INSERT INTO gip.levels (alias) VALUES('Nivel 0');
		INSERT INTO gip.levels (alias) VALUES('Nivel 1');
		INSERT INTO gip.levels (alias) VALUES('Nivel 2');
		INSERT INTO gip.levels (alias) VALUES('Preemergencia');
		
		
		
		
		
		

  END IF;
END $$;