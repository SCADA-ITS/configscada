DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'nogales_gip') THEN
  
		
		INSERT INTO nogales_gip.agents (alias) VALUES('Bombero');
		INSERT INTO nogales_gip.agents (alias) VALUES('Carabineros');
		INSERT INTO nogales_gip.agents (alias) VALUES('Centro de Control de Tráfico');
		INSERT INTO nogales_gip.agents (alias) VALUES('Inspector Fiscal');
		INSERT INTO nogales_gip.agents (alias) VALUES('Policía investigaciones');
		INSERT INTO nogales_gip.agents (alias) VALUES('Fiscal');
		

  END IF;
END $$;