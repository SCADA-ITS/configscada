DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'nogales_gip') THEN
		
		INSERT INTO nogales_gip.genders (alias) VALUES('Femenino');
		INSERT INTO nogales_gip.genders (alias) VALUES('Masculino');
		
		
		INSERT INTO nogales_gip.stages (alias) VALUES('Adolescente');
		INSERT INTO nogales_gip.stages (alias) VALUES('Adulto');
		
		
		INSERT INTO nogales_gip.user_types (alias) VALUES('Acompañante');
		INSERT INTO nogales_gip.user_types (alias) VALUES('Conductor');
		
		
		INSERT INTO nogales_gip.injury_types (alias) VALUES('Contusion');
		INSERT INTO nogales_gip.injury_types (alias) VALUES('Corte');
		
		INSERT INTO nogales_gip.injury_levels (alias) VALUES('Fallecidos');
		INSERT INTO nogales_gip.injury_levels (alias) VALUES('Graves');
		
		
		INSERT INTO nogales_gip.participant_assistance_types (alias) VALUES('Auxilio en accidente');
		INSERT INTO nogales_gip.participant_assistance_types (alias) VALUES('Primeros auxilios');
		
		INSERT INTO nogales_gip.participant_transfer_places (alias) VALUES('Clinica');
		INSERT INTO nogales_gip.participant_transfer_places (alias) VALUES('Comisaria');


  END IF;
END $$;
