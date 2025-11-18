DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
		
		INSERT INTO backoffice.road_types (alias) VALUES('Concreto');
		INSERT INTO backoffice.road_types (alias) VALUES('Asfalto');
		INSERT INTO backoffice.road_types (alias) VALUES('Ripio');
		INSERT INTO backoffice.road_types (alias) VALUES('Tierra adoquín');
		INSERT INTO backoffice.road_types (alias) VALUES('Mixto');
		
		INSERT INTO backoffice.road_states (alias) VALUES('Bueno');
		INSERT INTO backoffice.road_states (alias) VALUES('Regular');
		INSERT INTO backoffice.road_states (alias) VALUES('Malo');
		
		INSERT INTO backoffice.road_conditions (alias) VALUES('Seca');
		INSERT INTO backoffice.road_conditions (alias) VALUES('Húmeda');
		INSERT INTO backoffice.road_conditions (alias) VALUES('Mojada');
		INSERT INTO backoffice.road_conditions (alias) VALUES('Con barro');
		INSERT INTO backoffice.road_conditions (alias) VALUES('Con nieve');
		INSERT INTO backoffice.road_conditions (alias) VALUES('Con aceite');
		INSERT INTO backoffice.road_conditions (alias) VALUES('Escarcha');
		INSERT INTO backoffice.road_conditions (alias) VALUES('Gravilla');
		INSERT INTO backoffice.road_conditions (alias) VALUES('Otros');
		
		INSERT INTO backoffice.weather_states (alias) VALUES('Despejado');
		INSERT INTO backoffice.weather_states (alias) VALUES('Nublado');
		INSERT INTO backoffice.weather_states (alias) VALUES('Lluvia');
		INSERT INTO backoffice.weather_states (alias) VALUES('Llovizna');
		INSERT INTO backoffice.weather_states (alias) VALUES('Neblina');
		INSERT INTO backoffice.weather_states (alias) VALUES('Nieve');
		
		INSERT INTO backoffice.luminosity (alias) VALUES('Diurna');
		INSERT INTO backoffice.luminosity (alias) VALUES('Nocturna');
		INSERT INTO backoffice.luminosity (alias) VALUES('Amanecer');
		INSERT INTO backoffice.luminosity (alias) VALUES('Atardecer');
		
		INSERT INTO backoffice.ligth (alias) VALUES('Apagada');
		INSERT INTO backoffice.ligth (alias) VALUES('Encendida suficiente');
		INSERT INTO backoffice.ligth (alias) VALUES('Encendida insuficiente');
		INSERT INTO backoffice.ligth (alias) VALUES('No existe luminaria');
		
		INSERT INTO backoffice.causes (alias) VALUES('Falla humana');
		INSERT INTO backoffice.causes (alias) VALUES('Falla mecánica');
		INSERT INTO backoffice.causes (alias) VALUES('Reventón de neumático');
		INSERT INTO backoffice.causes (alias) VALUES('Pavimento resbaladizo');
		INSERT INTO backoffice.causes (alias) VALUES('Acto vandálico');
		INSERT INTO backoffice.causes (alias) VALUES('Falta de visibilidad');
		INSERT INTO backoffice.causes (alias) VALUES('Peatón en la vía');
		INSERT INTO backoffice.causes (alias) VALUES('Animal en la vía');
		INSERT INTO backoffice.causes (alias) VALUES('Obstáculo en la vía');
		INSERT INTO backoffice.causes (alias) VALUES('Carga mal estibada');
		INSERT INTO backoffice.causes (alias) VALUES('Condición climática');
		INSERT INTO backoffice.causes (alias) VALUES('Otro');
  END IF;
END $$;