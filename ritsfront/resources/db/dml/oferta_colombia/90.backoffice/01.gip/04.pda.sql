DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'gip') THEN

		INSERT INTO gip.pdas (alias) VALUES('Patrullero');
		INSERT INTO gip.pdas (alias) VALUES('Antonio');
		

  END IF;
END $$;