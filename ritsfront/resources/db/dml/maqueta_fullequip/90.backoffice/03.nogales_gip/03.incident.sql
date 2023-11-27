DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'nogales_gip') THEN
		
		INSERT INTO nogales_gip.sources (alias) VALUES('Poste SOS');
		INSERT INTO nogales_gip.sources (alias) VALUES('Cámara CCTV');
		INSERT INTO nogales_gip.sources (alias) VALUES('Meteo');
		INSERT INTO nogales_gip.sources (alias) VALUES('Section');

		INSERT INTO nogales_gip.elements (alias) VALUES('RV-MET-1MA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-MET-1MA');
		INSERT INTO nogales_gip.elements (alias) VALUES('CH-MET-1MA');
		INSERT INTO nogales_gip.elements (alias) VALUES('LQ-MET-1MA');
		INSERT INTO nogales_gip.elements (alias) VALUES('RV-ETD-1-SEC-1CA-IN');
		INSERT INTO nogales_gip.elements (alias) VALUES('RV-ETD-1-SEC-2MA-OUT');
		INSERT INTO nogales_gip.elements (alias) VALUES('RV-ETD-2-SEC-1MA-IN');
		INSERT INTO nogales_gip.elements (alias) VALUES('RV-ETD-2-SEC-2CA-OUT');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-ETD-1-SEC-1CA-IN');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-ETD-1-SEC-2MA-OUT');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-ETD-2-SEC-1MA-IN');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-ETD-2-SEC-2CA-OUT');
		INSERT INTO nogales_gip.elements (alias) VALUES('CH-SOS-1CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('CH-SOS-2CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('CH-SOS-3CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('NG-SOS-1CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('NG-SOS-2CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('NG-SOS-3CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-1CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-2CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-3CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-4CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-5CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-6CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-7CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-8CA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-1GA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-2GA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-1MA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-2MA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-3MA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-4MA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-5MA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-6MA');
		INSERT INTO nogales_gip.elements (alias) VALUES('MM-TV-7MA');



  END IF;
END $$;