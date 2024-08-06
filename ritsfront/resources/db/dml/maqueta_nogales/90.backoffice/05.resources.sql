DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'nogales_gip') THEN
		
		INSERT INTO nogales_gip.actions (alias) VALUES('Asignación');
		INSERT INTO nogales_gip.actions (alias) VALUES('Liberación');
		
		INSERT INTO nogales_gip.typologies (alias) VALUES('Internos');
		INSERT INTO nogales_gip.typologies (alias) VALUES('Externos');
		
		INSERT INTO nogales_gip.resources (alias, total_unit, current_unit) VALUES('Ambulancia 1', 0, 0);
		INSERT INTO nogales_gip.resources (alias, total_unit, current_unit) VALUES('Grua 1', 0, 0);
		INSERT INTO nogales_gip.resources (alias, total_unit, current_unit) VALUES('Moto 1', 0, 0);
		INSERT INTO nogales_gip.resources (alias, total_unit, current_unit) VALUES('Moto 2', 0, 0);
		INSERT INTO nogales_gip.resources (alias, total_unit, current_unit) VALUES('Bomberos', 0, 0);
		INSERT INTO nogales_gip.resources (alias, total_unit, current_unit) VALUES('Unidad de rescate 1', 0, 0);
		INSERT INTO nogales_gip.resources (alias, total_unit, current_unit) VALUES('Unidad de rescate 2', 0, 0);

  END IF;
END $$;