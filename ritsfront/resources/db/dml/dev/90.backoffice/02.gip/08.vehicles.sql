DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'gip') THEN
		  
		
		INSERT INTO gip.vehicle_types (alias) VALUES('Coche');
		INSERT INTO gip.vehicle_types (alias) VALUES('Bus');
		INSERT INTO gip.vehicle_types (alias) VALUES('Moto');
		
		
		INSERT INTO gip.vehicle_brands (alias) VALUES('Audi');
		INSERT INTO gip.vehicle_brands (alias) VALUES('BMV');
		
		
		INSERT INTO gip.vehicle_models (alias) VALUES('X1');
		INSERT INTO gip.vehicle_models (alias) VALUES('X2');
		INSERT INTO gip.vehicle_models (alias) VALUES('X3');
		INSERT INTO gip.vehicle_models (alias) VALUES('X2');
		
		INSERT INTO gip.vehicle_models (alias) VALUES('A1');
		INSERT INTO gip.vehicle_models (alias) VALUES('A2');
		INSERT INTO gip.vehicle_models (alias) VALUES('A3');
		INSERT INTO gip.vehicle_models (alias) VALUES('A2');
		
		
		INSERT INTO gip.vehicle_damages (alias) VALUES('Con daños');
		INSERT INTO gip.vehicle_damages (alias) VALUES('Sin daños');
		
		INSERT INTO gip.attendeds (alias) VALUES('Atendido');
		INSERT INTO gip.attendeds (alias) VALUES('No atendido');
		INSERT INTO gip.attendeds (alias) VALUES('Rechazado');
		
		
		INSERT INTO gip.assistance_types (alias) VALUES('Detecta falla y solicita servico');
		INSERT INTO gip.assistance_types (alias) VALUES('Instalación señalización preventiva');
		
		
		INSERT INTO gip.transfer_places (alias) VALUES('CAE Norte');
		INSERT INTO gip.transfer_places (alias) VALUES('CAE Sur');
		

  END IF;
END $$;