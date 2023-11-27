DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'nogales_gip') THEN
		
		INSERT INTO nogales_gip.conditions (alias) VALUES('Vía seca');
		INSERT INTO nogales_gip.conditions (alias) VALUES('Vía mojada');
		INSERT INTO nogales_gip.conditions (alias) VALUES('Vía con barro o lodo');
		INSERT INTO nogales_gip.conditions (alias) VALUES('Vía con nieve');
		INSERT INTO nogales_gip.conditions (alias) VALUES('Vía con aceite');
		INSERT INTO nogales_gip.conditions (alias) VALUES('Vía con hielo');
		INSERT INTO nogales_gip.conditions (alias) VALUES('Vía con gravilla');
		INSERT INTO nogales_gip.conditions (alias) VALUES('Otros');


		INSERT INTO nogales_gip.luminosities (alias) VALUES('Diurna');
		INSERT INTO nogales_gip.luminosities (alias) VALUES('Nocturno');
		INSERT INTO nogales_gip.luminosities (alias) VALUES('Amanecer');
		INSERT INTO nogales_gip.luminosities (alias) VALUES('Atardecer');
		
		
		INSERT INTO nogales_gip.atmospheric_states (alias) VALUES('Soleado');
		INSERT INTO nogales_gip.atmospheric_states (alias) VALUES('Nublado');
		INSERT INTO nogales_gip.atmospheric_states (alias) VALUES('Lluvia');
		INSERT INTO nogales_gip.atmospheric_states (alias) VALUES('Llovizna');
		INSERT INTO nogales_gip.atmospheric_states (alias) VALUES('Niebla');
		INSERT INTO nogales_gip.atmospheric_states (alias) VALUES('Nieve');
		INSERT INTO nogales_gip.atmospheric_states (alias) VALUES('Lluvia granizo');
		INSERT INTO nogales_gip.atmospheric_states (alias) VALUES('Bruma');
		INSERT INTO nogales_gip.atmospheric_states (alias) VALUES('Despejado');

		
		INSERT INTO nogales_gip.causes (alias) VALUES('Falla humano');
		INSERT INTO nogales_gip.causes (alias) VALUES('Falla mecánico');
		
		
		INSERT INTO nogales_gip.maneuvers (alias) VALUES('Adelanta');
		INSERT INTO nogales_gip.maneuvers (alias) VALUES('Cambio de pista');
		INSERT INTO nogales_gip.maneuvers (alias) VALUES('Cruzando la vía');
		
		
		INSERT INTO nogales_gip.road_impacts (alias) VALUES('Corte de 1 carril');
		INSERT INTO nogales_gip.road_impacts (alias) VALUES('Corte de 2 carriles');
		INSERT INTO nogales_gip.road_impacts (alias) VALUES('Corte de Calzada Oriente-Poniente');
		INSERT INTO nogales_gip.road_impacts (alias) VALUES('Corte de Calzada Poniente Oriente');
		INSERT INTO nogales_gip.road_impacts (alias) VALUES('Corte de Carril Lento');
		INSERT INTO nogales_gip.road_impacts (alias) VALUES('Corte de Carril Rápido');
		INSERT INTO nogales_gip.road_impacts (alias) VALUES('Corte de Ramal de Entrada');
		INSERT INTO nogales_gip.road_impacts (alias) VALUES('Corte de Ramal de Salida');
		INSERT INTO nogales_gip.road_impacts (alias) VALUES('Berma Derecha');
		INSERT INTO nogales_gip.road_impacts (alias) VALUES('Berma Izquierda');
		INSERT INTO nogales_gip.road_impacts (alias) VALUES('Toda la Autopista');
		
		INSERT INTO nogales_gip.infrastructure_damages (alias) VALUES('Con daños');
		INSERT INTO nogales_gip.infrastructure_damages (alias) VALUES('Sin daños');	

  END IF;
END $$;