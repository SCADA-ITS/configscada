DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents_gip') THEN

		INSERT INTO incidents_gip.pdas (alias) VALUES('Patrullero');
		INSERT INTO incidents_gip.pdas (alias) VALUES('Antonio');
		

  END IF;
END $$;