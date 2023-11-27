DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'gip') THEN
		
		INSERT INTO gip.conditions (alias) VALUES('Vía seca');
		INSERT INTO gip.conditions (alias) VALUES('Vía mojada');
		INSERT INTO gip.conditions (alias) VALUES('Vía con barro o lodo');
		
		INSERT INTO gip.luminosities (alias) VALUES('Diurna');
		INSERT INTO gip.luminosities (alias) VALUES('Nocturno');
		INSERT INTO gip.luminosities (alias) VALUES('Amanecer');
		INSERT INTO gip.luminosities (alias) VALUES('Atardecer');
		
		
		INSERT INTO gip.atmospheric_states (alias) VALUES('Soleado');
		INSERT INTO gip.atmospheric_states (alias) VALUES('Nublado');
		
		
		INSERT INTO gip.causes (alias) VALUES('Falla humano');
		INSERT INTO gip.causes (alias) VALUES('Falla mecanico');
		
		
		INSERT INTO gip.maneuvers (alias) VALUES('Adelanta');
		INSERT INTO gip.maneuvers (alias) VALUES('Cambio de pista');
		
		
		INSERT INTO gip.road_impacts (alias) VALUES('Corte de 1 carril');
		INSERT INTO gip.road_impacts (alias) VALUES('Corte de 2 carriles');
		INSERT INTO gip.road_impacts (alias) VALUES('Corte de 3 carriles');
		
		
		INSERT INTO gip.infrastructure_damages (alias) VALUES('Con daños');
		INSERT INTO gip.infrastructure_damages (alias) VALUES('Sin daños');
		
		
		

  END IF;
END $$;