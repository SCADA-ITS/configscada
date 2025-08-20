DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents') THEN

		INSERT INTO incidents.affection_details (alias) VALUES('Afección en arcén');
		INSERT INTO incidents.affection_details (alias) VALUES('Afección en la calzada');
		INSERT INTO incidents.affection_details (alias) VALUES('Carril derecho cerrado');
		INSERT INTO incidents.affection_details (alias) VALUES('Carril izquierdo cerrado');
		INSERT INTO incidents.affection_details (alias) VALUES('Estrechamiento de calzada');
		INSERT INTO incidents.affection_details (alias) VALUES('Paso alternativo');
		INSERT INTO incidents.affection_details (alias) VALUES('Desvío del tráfico');
		INSERT INTO incidents.affection_details (alias) VALUES('Carretera cortada');

  END IF;
END $$;