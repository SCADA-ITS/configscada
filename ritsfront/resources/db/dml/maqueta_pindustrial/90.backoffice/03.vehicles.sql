DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
		
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Bus/Taxibus');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Minibús');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Trolebús');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Automóvil');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Camioneta');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Jeep');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Camión simple');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Camión con remolque');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Tracto-camión');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Tracto-camión con semiremolque');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Furgón');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Ambulancia');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Carro bomba');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Motocicleta');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Bibicleta');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Tracción animal');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Maquinaria agrícola');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Maquinaria movimiento tierra');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('FF.CC.');
		INSERT INTO backoffice.vehicle_types (alias) VALUES('Sobredimensionados');
		
		INSERT INTO backoffice.vehicle_services (alias) VALUES('Carabineros');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('Fiscal');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('Particular');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('Transporte escolar');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('Taxi básico');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('Taxi colectivo');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('Bomberos');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('Ambulancia');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('L.Colectiva urbana');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('L. Interprovincial');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('L. Internacional');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('Carga normal');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('Carga peligrosa');
		INSERT INTO backoffice.vehicle_services (alias) VALUES('Otros');
		
		INSERT INTO backoffice.vehicle_consecuences (alias) VALUES('Con daños');
		INSERT INTO backoffice.vehicle_consecuences (alias) VALUES('Sin daños');
		
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Viaja derecho por vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Vira derecho hacía vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Vira izquierda hacia vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Adelanta en vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Detenido/deteníendose en vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Retrocede en vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Vira en "U" en vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Entra a vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Sale a vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Estac. en calzada de vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Estac. en berma de vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Cambia de pista en vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Reinicia marcha en vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Cruzando la vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Frena en vía');
		INSERT INTO backoffice.vehicle_manoeuvres (alias) VALUES('Otras');
  END IF;
END $$;