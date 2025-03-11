DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents') THEN
		
		INSERT INTO incidents.genders (alias) VALUES('Femenino');
		INSERT INTO incidents.genders (alias) VALUES('Masculino');
		
		
		INSERT INTO incidents.stages (alias) VALUES('Adolescente');
		INSERT INTO incidents.stages (alias) VALUES('Adulto');
		
		
		INSERT INTO incidents.user_types (alias) VALUES('Acompañante');
		INSERT INTO incidents.user_types (alias) VALUES('Conductor');
		
		
		INSERT INTO incidents.injury_types (alias) VALUES('Contusion');
		INSERT INTO incidents.injury_types (alias) VALUES('Corte');
		
		INSERT INTO incidents.injury_levels (alias) VALUES('Fallecidos');
		INSERT INTO incidents.injury_levels (alias) VALUES('Graves');
		INSERT INTO incidents.injury_levels (alias) VALUES('Leves');
		
		
		INSERT INTO incidents.participant_assistance_types (alias) VALUES('Auxilio en accidente');
		INSERT INTO incidents.participant_assistance_types (alias) VALUES('Primeros auxilios');
		
		INSERT INTO incidents.participant_transfer_places (alias) VALUES('Clinica');
		INSERT INTO incidents.participant_transfer_places (alias) VALUES('Comisaria');


  END IF;
END $$;
