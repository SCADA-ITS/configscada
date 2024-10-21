DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents_gip') THEN
  
		
		INSERT INTO incidents_gip.agents (alias) VALUES('Bombero');
		INSERT INTO incidents_gip.agents (alias) VALUES('Centro de Control de Tráfico');
		INSERT INTO incidents_gip.agents (alias) VALUES('Inspector Fiscal');
		INSERT INTO incidents_gip.agents (alias) VALUES('Policía investigaciones');
		INSERT INTO incidents_gip.agents (alias) VALUES('Fiscal');
		

  END IF;
END $$;