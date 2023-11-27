DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'gip') THEN
		
		
		INSERT INTO gip.participant_types (alias) VALUES('No ocupa vehiculo');
		
		INSERT INTO gip.genders (alias) VALUES('Femenino');
		INSERT INTO gip.genders (alias) VALUES('Masculino');
		
		
		INSERT INTO gip.stages (alias) VALUES('Adolescente');
		INSERT INTO gip.stages (alias) VALUES('Adulto');
		
		
		INSERT INTO gip.user_types (alias) VALUES('Acompañante');
		INSERT INTO gip.user_types (alias) VALUES('Conductor');
		
		
		INSERT INTO gip.injury_types (alias) VALUES('Contusion');
		INSERT INTO gip.injury_types (alias) VALUES('Corte');
		
		INSERT INTO gip.injury_levels (alias) VALUES('Fallecidos');
		INSERT INTO gip.injury_levels (alias) VALUES('Graves');
		
		
		INSERT INTO gip.participant_assistance_types (alias) VALUES('Auxilio en accidente');
		INSERT INTO gip.participant_assistance_types (alias) VALUES('Primeros auxilios');
		
		INSERT INTO gip.participant_transfer_places (alias) VALUES('Clinica');
		INSERT INTO gip.participant_transfer_places (alias) VALUES('Comisaria');

		

  END IF;
END $$;