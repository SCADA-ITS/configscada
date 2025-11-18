DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
		
		INSERT INTO backoffice.support_service_types (alias) VALUES('Externos');
		INSERT INTO backoffice.support_service_types (alias) VALUES('Internos');
		
		--Externos
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(1, 'Ambulancia');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(1, 'Carabineros');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(1, 'Bomberos');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(1, 'Rescate');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(1, 'Servicio médico legal');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(1, 'SIAT');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(1, 'CONAF');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(1, 'ONEMI');
		--Internos                                        
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(2, 'Grúa liviana');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(2, 'Grúa Pesada');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(2, 'Patrulla');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(2, 'Cuadrilla emergencia');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(2, 'Sup de operaciones');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(2, 'Jefe operaciones');
		INSERT INTO backoffice.support_service_subtypes (support_service_type_id, alias) VALUES(2, 'Jefe de turno');
  END IF;
END $$;