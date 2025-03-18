DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents_gip') THEN
		
		INSERT INTO incidents_gip.sources (alias) VALUES('Poste SOS');
		INSERT INTO incidents_gip.sources (alias) VALUES('Cámara CCTV');
		INSERT INTO incidents_gip.sources (alias) VALUES('Mail');
		INSERT INTO incidents_gip.sources (alias) VALUES('Twitter');

		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S1-CCTV-01');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S1-CCTV-02');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S1-CCTV-03');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S1-CCTV-04');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S1-CCTV-05');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S1-CCTV-06');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-CCTV-01');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-CCTV-02');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-CCTV-03');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-CCTV-04');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-CCTV-05');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-CCTV-06');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-CCTV-07');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S3-CCTV-01');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S3-CCTV-02');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S3-CCTV-03');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S2-CCTV-01');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S2-CCTV-02');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S2-CCTV-03');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S2-CCTV-04');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S1-SOS-01');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S1-SOS-02');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S1-SOS-03');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S1-SOS-04');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S1-SOS-05');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S1-SOS-06');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-SOS-01');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-SOS-02');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-SOS-03');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-SOS-06');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-SOS-07');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-SOS-08');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-SOS-04');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-SOS-05');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-SOS-09');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S2-SOS-10');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S3-SOS-01');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S3-SOS-02');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S3-SOS-03');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S3-SOS-04');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S3-SOS-05');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S1S3-SOS-06');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S2-SOS-01');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S2-SOS-02');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S2-SOS-03');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S2-SOS-04');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S2-SOS-05');
		INSERT INTO incidents_gip.elements (alias) VALUES('NO-S2-SOS-06'); 



  END IF;
END $$;