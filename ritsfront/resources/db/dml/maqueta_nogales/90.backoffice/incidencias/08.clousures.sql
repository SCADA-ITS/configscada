DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN

		INSERT INTO backoffice.side_roads (alias) VALUES('Derecho');
		INSERT INTO backoffice.side_roads (alias) VALUES('Izquierdo');

		INSERT INTO backoffice.lanes (alias) VALUES('1');
		INSERT INTO backoffice.lanes (alias) VALUES('2');
		INSERT INTO backoffice.lanes (alias) VALUES('3');
		INSERT INTO backoffice.lanes (alias) VALUES('4');

		INSERT INTO backoffice.clousure_entities (alias) VALUES('Patrullera');
		INSERT INTO backoffice.clousure_entities (alias) VALUES('Grúa vehiculos livianos');
		INSERT INTO backoffice.clousure_entities (alias) VALUES('Grúa vehiculos pesados');
		INSERT INTO backoffice.clousure_entities (alias) VALUES('Ambulancia concesionaria');
		INSERT INTO backoffice.clousure_entities (alias) VALUES('Carabineros');
		INSERT INTO backoffice.clousure_entities (alias) VALUES('Bomberos');
		INSERT INTO backoffice.clousure_entities (alias) VALUES('Ambulancia');
		INSERT INTO backoffice.clousure_entities (alias) VALUES('Conaf');
		INSERT INTO backoffice.clousure_entities (alias) VALUES('Seguridad municipal');
		INSERT INTO backoffice.clousure_entities (alias) VALUES('SML');
		INSERT INTO backoffice.clousure_entities (alias) VALUES('Fuerzas armadas');
		INSERT INTO backoffice.clousure_entities (alias) VALUES('Otro');

		INSERT INTO backoffice.closure_types (alias) VALUES('Berma');
		INSERT INTO backoffice.closure_types (alias) VALUES('Una pista');
		INSERT INTO backoffice.closure_types (alias) VALUES('Dos pistas');
		INSERT INTO backoffice.closure_types (alias) VALUES('Tres pistas');
		INSERT INTO backoffice.closure_types (alias) VALUES('Cuatro pistas');
  END IF;
END $$;
