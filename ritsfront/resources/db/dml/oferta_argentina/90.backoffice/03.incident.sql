DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents_gip') THEN
		
		INSERT INTO incidents_gip.sources (alias) VALUES('Poste SOS');
		INSERT INTO incidents_gip.sources (alias) VALUES('Cámara CCTV');
		INSERT INTO incidents_gip.sources (alias) VALUES('Mail');
		INSERT INTO incidents_gip.sources (alias) VALUES('Twitter');

		INSERT INTO incidents_gip.elements (alias) VALUES('CAM-GP-1');
		INSERT INTO incidents_gip.elements (alias) VALUES('CAM-GP-2');
		INSERT INTO incidents_gip.elements (alias) VALUES('CAM-GP-3');
		INSERT INTO incidents_gip.elements (alias) VALUES('CAM-GP-4');
		INSERT INTO incidents_gip.elements (alias) VALUES('CAM-GP-5');
		INSERT INTO incidents_gip.elements (alias) VALUES('CAM-GP-6');
		INSERT INTO incidents_gip.elements (alias) VALUES('CAM-GP-7');
		INSERT INTO incidents_gip.elements (alias) VALUES('CAM-GP-8');
		INSERT INTO incidents_gip.elements (alias) VALUES('CAM-GP-9');



  END IF;
END $$;