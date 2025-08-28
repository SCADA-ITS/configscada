DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
		
		INSERT INTO backoffice.owner_types (alias) VALUES('Particular');
		INSERT INTO backoffice.owner_types (alias) VALUES('Empresa');
  END IF;
END $$;