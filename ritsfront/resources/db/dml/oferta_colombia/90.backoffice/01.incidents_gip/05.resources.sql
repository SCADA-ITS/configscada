DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents_gip') THEN
		
		INSERT INTO incidents_gip.actions (alias) VALUES('Asignación');
		INSERT INTO incidents_gip.actions (alias) VALUES('Liberación');
		
		INSERT INTO incidents_gip.typologies (alias) VALUES('Internos');
		INSERT INTO incidents_gip.typologies (alias) VALUES('Externos');
		
		INSERT INTO incidents_gip.resources (alias, total_unit, current_unit) VALUES('Ambulacia 1', 0, 0);
		INSERT INTO incidents_gip.resources (alias, total_unit, current_unit) VALUES('Grua 1', 0, 0);
		

  END IF;
END $$;