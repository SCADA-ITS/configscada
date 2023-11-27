DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'gip') THEN
  
		
		INSERT INTO gip.agents (alias) VALUES('Bombero');
		INSERT INTO gip.agents (alias) VALUES('Fiscal');
		

  END IF;
END $$;