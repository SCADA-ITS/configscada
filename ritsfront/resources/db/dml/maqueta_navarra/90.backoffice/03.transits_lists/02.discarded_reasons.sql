DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'transits_bo') THEN

		INSERT INTO transits_bo.discarded_reasons (alias) VALUES('No se puede identificar la matrícula');
		INSERT INTO transits_bo.discarded_reasons (alias) VALUES('No coincide la marca, modelo, color o tipo de vehículo');
		INSERT INTO transits_bo.discarded_reasons (alias) VALUES('Imagen no válida (oscura, borrosa, sin vehículo…)');
		INSERT INTO transits_bo.discarded_reasons (alias) VALUES('Vehículo prioritario (policial, bomberos, ambulancias, …)');
		INSERT INTO transits_bo.discarded_reasons (alias) VALUES('Vehículo en lista blanca');

  END IF;
END $$;