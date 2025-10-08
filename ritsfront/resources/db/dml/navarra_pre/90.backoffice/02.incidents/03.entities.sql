DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents') THEN

		INSERT INTO incidents.entities (alias) VALUES('Policía');
		INSERT INTO incidents.entities (alias) VALUES('Bomberos');
		INSERT INTO incidents.entities (alias) VALUES('112');
		INSERT INTO incidents.entities (alias) VALUES('Ayuntamiento');

  END IF;
END $$;