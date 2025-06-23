DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents') THEN
		
		INSERT INTO incidents.municipalities (alias) VALUES('Pamplona');
		INSERT INTO incidents.municipalities (alias) VALUES('Tudela');
		INSERT INTO incidents.municipalities (alias) VALUES('Barañáin');
		INSERT INTO incidents.municipalities (alias) VALUES('Burlada');
		INSERT INTO incidents.municipalities (alias) VALUES('Estella-Lizarra');
		INSERT INTO incidents.municipalities (alias) VALUES('Sangüesa');
		INSERT INTO incidents.municipalities (alias) VALUES('Alsasua');
		INSERT INTO incidents.municipalities (alias) VALUES('Villava');
		INSERT INTO incidents.municipalities (alias) VALUES('Corella');
		INSERT INTO incidents.municipalities (alias) VALUES('Cintruénigo');
		INSERT INTO incidents.municipalities (alias) VALUES('Peralta');
		INSERT INTO incidents.municipalities (alias) VALUES('Azagra');
		INSERT INTO incidents.municipalities (alias) VALUES('Falces');
		INSERT INTO incidents.municipalities (alias) VALUES('San Adrián');
		INSERT INTO incidents.municipalities (alias) VALUES('Marcilla');
		INSERT INTO incidents.municipalities (alias) VALUES('Olite');
		INSERT INTO incidents.municipalities (alias) VALUES('Aoiz');
		INSERT INTO incidents.municipalities (alias) VALUES('Lekunberri');
		INSERT INTO incidents.municipalities (alias) VALUES('Elizondo');
		INSERT INTO incidents.municipalities (alias) VALUES('Lodosa');

		INSERT INTO incidents.entities (alias) VALUES('Policía');
		INSERT INTO incidents.entities (alias) VALUES('Bomberos');
		INSERT INTO incidents.entities (alias) VALUES('112');
		INSERT INTO incidents.entities (alias) VALUES('Ayuntamiento');

  END IF;
END $$;
