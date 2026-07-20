DO $$
BEGIN
	IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN

		INSERT INTO backoffice.cct_call_reasons (alias) VALUES('Comercial');
		INSERT INTO backoffice.cct_call_reasons (alias) VALUES('Asistencia en ruta');
		INSERT INTO backoffice.cct_call_reasons (alias) VALUES('Emergencia');
		INSERT INTO backoffice.cct_call_reasons (alias) VALUES('Prueba');
		INSERT INTO backoffice.cct_call_reasons (alias) VALUES('Otro');
		INSERT INTO backoffice.cct_call_reasons (alias) VALUES('Simulacro');

		INSERT INTO backoffice.cct_call_shifts (alias) VALUES('Día');
		INSERT INTO backoffice.cct_call_shifts (alias) VALUES('Noche');

	END IF;
END $$;