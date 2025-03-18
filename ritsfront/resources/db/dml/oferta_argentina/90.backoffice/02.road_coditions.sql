DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents_gip') THEN
		
		INSERT INTO incidents_gip.conditions (alias) VALUES('Vía seca');
		INSERT INTO incidents_gip.conditions (alias) VALUES('Vía mojada');
		INSERT INTO incidents_gip.conditions (alias) VALUES('Vía con barro o lodo');
		INSERT INTO incidents_gip.conditions (alias) VALUES('Vía con nieve');
		INSERT INTO incidents_gip.conditions (alias) VALUES('Vía con aceite');
		INSERT INTO incidents_gip.conditions (alias) VALUES('Vía con hielo');
		INSERT INTO incidents_gip.conditions (alias) VALUES('Vía con gravilla');
		INSERT INTO incidents_gip.conditions (alias) VALUES('Otros');


		INSERT INTO incidents_gip.luminosities (alias) VALUES('Diurna');
		INSERT INTO incidents_gip.luminosities (alias) VALUES('Nocturno');
		INSERT INTO incidents_gip.luminosities (alias) VALUES('Amanecer');
		INSERT INTO incidents_gip.luminosities (alias) VALUES('Atardecer');
		
		
		INSERT INTO incidents_gip.atmospheric_states (alias) VALUES('Soleado');
		INSERT INTO incidents_gip.atmospheric_states (alias) VALUES('Nublado');
		INSERT INTO incidents_gip.atmospheric_states (alias) VALUES('Lluvia');
		INSERT INTO incidents_gip.atmospheric_states (alias) VALUES('Llovizna');
		INSERT INTO incidents_gip.atmospheric_states (alias) VALUES('Niebla');
		INSERT INTO incidents_gip.atmospheric_states (alias) VALUES('Nieve');
		INSERT INTO incidents_gip.atmospheric_states (alias) VALUES('Lluvia granizo');
		INSERT INTO incidents_gip.atmospheric_states (alias) VALUES('Bruma');
		INSERT INTO incidents_gip.atmospheric_states (alias) VALUES('Despejado');

		
		INSERT INTO incidents_gip.causes (alias) VALUES('Falla humano');
		INSERT INTO incidents_gip.causes (alias) VALUES('Falla mecánico');
		
		
		INSERT INTO incidents_gip.maneuvers (alias) VALUES('Adelanta');
		INSERT INTO incidents_gip.maneuvers (alias) VALUES('Cambio de pista');
		INSERT INTO incidents_gip.maneuvers (alias) VALUES('Cruzando la vía');
		
		
		INSERT INTO incidents_gip.road_impacts (alias) VALUES('Corte de 1 carril');
		INSERT INTO incidents_gip.road_impacts (alias) VALUES('Corte de 2 carriles');
		INSERT INTO incidents_gip.road_impacts (alias) VALUES('Corte de Calzada Oriente-Poniente');
		INSERT INTO incidents_gip.road_impacts (alias) VALUES('Corte de Calzada Poniente Oriente');
		INSERT INTO incidents_gip.road_impacts (alias) VALUES('Corte de Carril Lento');
		INSERT INTO incidents_gip.road_impacts (alias) VALUES('Corte de Carril Rápido');
		INSERT INTO incidents_gip.road_impacts (alias) VALUES('Corte de Ramal de Entrada');
		INSERT INTO incidents_gip.road_impacts (alias) VALUES('Corte de Ramal de Salida');
		INSERT INTO incidents_gip.road_impacts (alias) VALUES('Berma Derecha');
		INSERT INTO incidents_gip.road_impacts (alias) VALUES('Berma Izquierda');
		INSERT INTO incidents_gip.road_impacts (alias) VALUES('Toda la Autopista');
		
		INSERT INTO incidents_gip.infrastructure_damages (alias) VALUES('Con daños');
		INSERT INTO incidents_gip.infrastructure_damages (alias) VALUES('Sin daños');	

  END IF;
END $$;