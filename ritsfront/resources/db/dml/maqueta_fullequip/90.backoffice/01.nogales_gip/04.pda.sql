DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'nogales_gip') THEN

		INSERT INTO nogales_gip.pdas (alias) VALUES('Patrullero');
		INSERT INTO nogales_gip.pdas (alias) VALUES('Antonio');
		

  END IF;
END $$;