DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'elements_gip') THEN

        INSERT INTO elements_gip.acciones(alias) VALUES
	      ('Mantenimiento'),
        ('Reparación'),
        ('Sustitución');

        INSERT INTO elements_gip.causas(alias) VALUES
        ('Error Humano'),
        ('Fallo mecánico'),
        ('Fallo electrónico'),
        ('Mantenimiento');

  END IF;
END $$;
