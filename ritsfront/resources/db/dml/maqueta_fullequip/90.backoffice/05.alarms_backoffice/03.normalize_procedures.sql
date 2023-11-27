DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'alarms_backoffice') THEN

    INSERT INTO alarms_backoffice.normalize_procedures (name) VALUES
    ('Avisar mantenimiento - PRIORIDAD ALTA'),
    ('Avisar mantenimiento - PRIORIDAD MEDIA'),
    ('Avisar mantenimiento - PRIORIDAD BAJA'),
    ('Climatología');

  END IF;
END $$;