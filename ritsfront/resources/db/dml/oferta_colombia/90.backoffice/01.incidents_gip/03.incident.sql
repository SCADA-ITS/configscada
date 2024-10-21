DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents_gip') THEN
		
		INSERT INTO incidents_gip.sources (alias) VALUES('Poste SOS');
		INSERT INTO incidents_gip.sources (alias) VALUES('Cámara CCTV');
		INSERT INTO incidents_gip.sources (alias) VALUES('Meteo');

		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 40');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 41');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 42');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 43');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 44');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 45');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 46');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 47');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 48');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 49');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 50');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 51');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 52');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 57');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 58');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 59');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 60');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 61');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 62');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 63');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 64');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 65');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 66');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 67');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 68');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 69');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 70');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 71');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 72');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 73');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 74');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 75');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 76');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 77');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 78');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 79');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 80');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 81');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 82');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 83');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 84');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 85');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 86');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 87');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 88');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 89');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 90');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 91');
		INSERT INTO incidents_gip.elements (alias) VALUES('SOS 92');
                                                           
                                                           
                                                           
  END IF;                                                  
END $$;                                                    
                                                           
                                                           
                                                           
                                                           
                                                           
                                                           
                                                           
                                                           
                                                           