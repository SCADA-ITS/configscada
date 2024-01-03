DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'elements_gip') THEN
		
		INSERT INTO elements_gip.road_impacts (alias) VALUES('Corte de 1 carril');
		INSERT INTO elements_gip.road_impacts (alias) VALUES('Corte de 2 carriles');
		INSERT INTO elements_gip.road_impacts (alias) VALUES('Corte de Carril Lento');
		INSERT INTO elements_gip.road_impacts (alias) VALUES('Corte de Carril Rápido');
		INSERT INTO elements_gip.road_impacts (alias) VALUES('Corte de Ramal de Entrada');
		INSERT INTO elements_gip.road_impacts (alias) VALUES('Corte de Ramal de Salida');
		INSERT INTO elements_gip.road_impacts (alias) VALUES('Berma Derecha');
		INSERT INTO elements_gip.road_impacts (alias) VALUES('Berma Izquierda');
		INSERT INTO elements_gip.road_impacts (alias) VALUES('Toda la Autopista');
		
  END IF;
END $$;