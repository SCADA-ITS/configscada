DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
		
		INSERT INTO backoffice.support_service_types (alias) VALUES('Externos');
		INSERT INTO backoffice.support_service_types (alias) VALUES('Internos');
		
		--Externos
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('Ambulancia');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('Carabineros');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('Bomberos');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('Rescate');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('Servicio médico legal');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('SIAT');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('CONAF');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('ONEMI');
		--Internos
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('Grúa liviana');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('Grúa Pesada');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('Patrulla');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('Cuadrilla emergencia');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('Sup de operaciones');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('Jefe operaciones');
		INSERT INTO backoffice.support_service_subtypes (alias) VALUES('Jefe de turno');
  END IF;
END $$;