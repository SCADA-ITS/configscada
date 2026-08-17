DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'transits_bo') THEN

		INSERT INTO transits_bo.notification_types (name) VALUES
			('Telegram'),
			('Correo');

  END IF;
END $$;

