DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'alarms_backoffice') THEN

    INSERT INTO alarms_backoffice.responsibilities (name) VALUES
    ('Mantenimiento 1'),
    ('Mantenimiento 2'),
    ('Mantenimiento 3'),
    ('Mantenimiento 4'),
    ('Bomberos'),
    ('Policía');

  END IF;
END $$;