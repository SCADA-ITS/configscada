DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'gip') THEN
		
		INSERT INTO gip.conditions (alias) VALUES('Vía seca');
		INSERT INTO gip.conditions (alias) VALUES('Vía mojada');
		INSERT INTO gip.conditions (alias) VALUES('Vía con barro o lodo');
		INSERT INTO gip.conditions (alias) VALUES('Vía con nieve');
		INSERT INTO gip.conditions (alias) VALUES('Vía con aceite');
		INSERT INTO gip.conditions (alias) VALUES('Vía con hielo');
		INSERT INTO gip.conditions (alias) VALUES('Vía con gravilla');
		INSERT INTO gip.conditions (alias) VALUES('Otros');


		INSERT INTO gip.luminosities (alias) VALUES('Diurna');
		INSERT INTO gip.luminosities (alias) VALUES('Nocturno');
		INSERT INTO gip.luminosities (alias) VALUES('Amanecer');
		INSERT INTO gip.luminosities (alias) VALUES('Atardecer');
		
		
		INSERT INTO gip.atmospheric_states (alias) VALUES('Soleado');
		INSERT INTO gip.atmospheric_states (alias) VALUES('Nublado');
		INSERT INTO gip.atmospheric_states (alias) VALUES('Lluvia');
		INSERT INTO gip.atmospheric_states (alias) VALUES('Llovizna');
		INSERT INTO gip.atmospheric_states (alias) VALUES('Niebla');
		INSERT INTO gip.atmospheric_states (alias) VALUES('Nieve');
		INSERT INTO gip.atmospheric_states (alias) VALUES('Lluvia granizo');
		INSERT INTO gip.atmospheric_states (alias) VALUES('Bruma');
		INSERT INTO gip.atmospheric_states (alias) VALUES('Despejado');

		
		INSERT INTO gip.causes (alias) VALUES('Falla humano');
		INSERT INTO gip.causes (alias) VALUES('Falla mecánico');
		
		
		INSERT INTO gip.maneuvers (alias) VALUES('Adelanta');
		INSERT INTO gip.maneuvers (alias) VALUES('Cambio de pista');
		INSERT INTO gip.maneuvers (alias) VALUES('Cruzando la vía');
		
		
		INSERT INTO gip.road_impacts (alias) VALUES('Corte de 1 carril');
		INSERT INTO gip.road_impacts (alias) VALUES('Corte de 2 carriles');
		INSERT INTO gip.road_impacts (alias) VALUES('Corte de Carril Lento');
		INSERT INTO gip.road_impacts (alias) VALUES('Corte de Carril Rápido');
		INSERT INTO gip.road_impacts (alias) VALUES('Corte de Ramal de Entrada');
		INSERT INTO gip.road_impacts (alias) VALUES('Corte de Ramal de Salida');
		INSERT INTO gip.road_impacts (alias) VALUES('Berma Derecha');
		INSERT INTO gip.road_impacts (alias) VALUES('Berma Izquierda');
		INSERT INTO gip.road_impacts (alias) VALUES('Toda la Autopista');
		
		INSERT INTO gip.infrastructure_damages (alias) VALUES('Con daños');
		INSERT INTO gip.infrastructure_damages (alias) VALUES('Sin daños');	

  END IF;
END $$;