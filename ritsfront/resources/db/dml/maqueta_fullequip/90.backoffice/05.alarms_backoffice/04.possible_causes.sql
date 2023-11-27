DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'alarms_backoffice') THEN

    INSERT INTO alarms_backoffice.possible_causes (name) VALUES
    ('Fallo eléctrico');

  END IF;
END $$;