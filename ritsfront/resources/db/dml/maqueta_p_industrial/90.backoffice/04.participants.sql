DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
		
		INSERT INTO backoffice.participant_types (alias) VALUES('Pasajero');
		INSERT INTO backoffice.participant_types (alias) VALUES('Conductor');
		INSERT INTO backoffice.participant_types (alias) VALUES('Peatón');
		INSERT INTO backoffice.participant_types (alias) VALUES('Ciclista');
		INSERT INTO backoffice.participant_types (alias) VALUES('Morador');
		INSERT INTO backoffice.participant_types (alias) VALUES('Contratista');
		INSERT INTO backoffice.participant_types (alias) VALUES('Personal Conc');
		INSERT INTO backoffice.participant_types (alias) VALUES('Otro');
		
		INSERT INTO backoffice.participant_injuries (alias) VALUES('Ileso');
		INSERT INTO backoffice.participant_injuries (alias) VALUES('Leve');
		INSERT INTO backoffice.participant_injuries (alias) VALUES('Menos grave');
		INSERT INTO backoffice.participant_injuries (alias) VALUES('Grave');
		INSERT INTO backoffice.participant_injuries (alias) VALUES('Muerto');
		
		INSERT INTO backoffice.participant_nacionalities (alias) VALUES('Chilena');
		INSERT INTO backoffice.participant_nacionalities (alias) VALUES('Extranjera');
  END IF;
END $$;