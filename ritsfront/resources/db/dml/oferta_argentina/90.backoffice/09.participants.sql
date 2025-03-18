DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents_gip') THEN
		
		INSERT INTO incidents_gip.genders (alias) VALUES('Femenino');
		INSERT INTO incidents_gip.genders (alias) VALUES('Masculino');
		
		
		INSERT INTO incidents_gip.stages (alias) VALUES('Adolescente');
		INSERT INTO incidents_gip.stages (alias) VALUES('Adulto');
		
		
		INSERT INTO incidents_gip.user_types (alias) VALUES('Acompañante');
		INSERT INTO incidents_gip.user_types (alias) VALUES('Conductor');
		
		
		INSERT INTO incidents_gip.injury_types (alias) VALUES('Contusion');
		INSERT INTO incidents_gip.injury_types (alias) VALUES('Corte');
		
		INSERT INTO incidents_gip.injury_levels (alias) VALUES('Fallecidos');
		INSERT INTO incidents_gip.injury_levels (alias) VALUES('Graves');
		
		
		INSERT INTO incidents_gip.participant_assistance_types (alias) VALUES('Auxilio en accidente');
		INSERT INTO incidents_gip.participant_assistance_types (alias) VALUES('Primeros auxilios');
		
		INSERT INTO incidents_gip.participant_transfer_places (alias) VALUES('Clinica');
		INSERT INTO incidents_gip.participant_transfer_places (alias) VALUES('Comisaria');


  END IF;
END $$;