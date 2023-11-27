DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'gip') THEN
		
		INSERT INTO gip.actions (alias) VALUES('Asignación');
		INSERT INTO gip.actions (alias) VALUES('Liberación');
		
		INSERT INTO gip.typologies (alias) VALUES('Internos');
		INSERT INTO gip.typologies (alias) VALUES('Externos');
		
		INSERT INTO gip.resources (alias, total_unit, current_unit) VALUES('Ambulacia 1', 0, 0);
		INSERT INTO gip.resources (alias, total_unit, current_unit) VALUES('Grua 1', 0, 0);
		

  END IF;
END $$;