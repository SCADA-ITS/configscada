DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
		
		INSERT INTO backoffice.side_roads (alias) VALUES('Derecha');
		INSERT INTO backoffice.side_roads (alias) VALUES('Izquierda');
		
		INSERT INTO backoffice.lanes (alias) VALUES('Pista 1');
		INSERT INTO backoffice.lanes (alias) VALUES('Pista 2');
		INSERT INTO backoffice.lanes (alias) VALUES('Pista 3');
		INSERT INTO backoffice.lanes (alias) VALUES('Pista 4');
		INSERT INTO backoffice.lanes (alias) VALUES('Pista 5');
		INSERT INTO backoffice.lanes (alias) VALUES('Pista 6');
		INSERT INTO backoffice.lanes (alias) VALUES('Pista 7');
		INSERT INTO backoffice.lanes (alias) VALUES('Pista 8');
		INSERT INTO backoffice.lanes (alias) VALUES('Berma');
		INSERT INTO backoffice.lanes (alias) VALUES('Pista de Incorporación');
		
		INSERT INTO backoffice.entities (alias) VALUES('Patrullera');
		INSERT INTO backoffice.entities (alias) VALUES('Grúa de Remolque');
		INSERT INTO backoffice.entities (alias) VALUES('Camión Grúa');
		INSERT INTO backoffice.entities (alias) VALUES('Ambulancia');
		INSERT INTO backoffice.entities (alias) VALUES('Atenuador de Impacto');
		INSERT INTO backoffice.entities (alias) VALUES('Supervisor Contratista');
		INSERT INTO backoffice.entities (alias) VALUES('Carabineros');
		INSERT INTO backoffice.entities (alias) VALUES('Personal de Concesionaria');
		INSERT INTO backoffice.entities (alias) VALUES('Supervisor CCT');
		
		INSERT INTO backoffice.closure_types (alias) VALUES('Angostamiento');
		INSERT INTO backoffice.closure_types (alias) VALUES('Corte Parcial');
		INSERT INTO backoffice.closure_types (alias) VALUES('Corte Total');
		INSERT INTO backoffice.closure_types (alias) VALUES('Desvío');
  END IF;
END $$;