DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents') THEN
		
		INSERT INTO incidents.actions (alias) VALUES('Asignación');
		INSERT INTO incidents.actions (alias) VALUES('Liberación');
		
		INSERT INTO incidents.typologies (alias) VALUES('Internos');
		INSERT INTO incidents.typologies (alias) VALUES('Externos');
		
		INSERT INTO incidents.resources (alias, total_unit, current_unit) VALUES('Ambulacia 1', 0, 0);
		INSERT INTO incidents.resources (alias, total_unit, current_unit) VALUES('Grua 1', 0, 0);
		

  END IF;
END $$;