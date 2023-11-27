DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'gip') THEN

		INSERT INTO gip.zones (alias) VALUES('Zona 0');
		INSERT INTO gip.zones (alias) VALUES('Zona 1');
		INSERT INTO gip.zones (alias) VALUES('Zona 2');
		INSERT INTO gip.zones (alias) VALUES('Zona 3');
		
		INSERT INTO gip.sections (alias) VALUES('Secc.01-10-14');
		INSERT INTO gip.sections (alias) VALUES('Secc.01-11-12');
		INSERT INTO gip.sections (alias) VALUES('Secc.01-11-13');
		
		INSERT INTO gip.cantones (alias) VALUES('Secc.01-10-14');
		INSERT INTO gip.cantones (alias) VALUES('Secc.01-11-12');
		INSERT INTO gip.cantones (alias) VALUES('Secc.01-11-13');
		
		INSERT INTO gip.sectors (alias) VALUES('S0');
		INSERT INTO gip.sectors (alias) VALUES('S1');
		INSERT INTO gip.sectors (alias) VALUES('S2');
		INSERT INTO gip.sectors (alias) VALUES('S3');
		
		INSERT INTO gip.loc_leveles (alias) VALUES('NO');
		INSERT INTO gip.loc_leveles (alias) VALUES('N 1');
		INSERT INTO gip.loc_leveles (alias) VALUES('N-0');
		INSERT INTO gip.loc_leveles (alias) VALUES('N-1');
		INSERT INTO gip.loc_leveles (alias) VALUES('N-2');
		INSERT INTO gip.loc_leveles (alias) VALUES('N-3');
		
		INSERT INTO gip.type_stretchs (alias) VALUES('EXTERIOR');
		INSERT INTO gip.type_stretchs (alias) VALUES('ENTRADA');
		INSERT INTO gip.type_stretchs (alias) VALUES('INTERIOR');
		INSERT INTO gip.type_stretchs (alias) VALUES('SALIDA');
		
		INSERT INTO gip.roads (alias) VALUES('TRANCAL-SUR');
		INSERT INTO gip.roads (alias) VALUES('TRANCAL-NORTE');
		
		INSERT INTO gip.axis (alias) VALUES('E0');
		INSERT INTO gip.axis (alias) VALUES('E1');
		INSERT INTO gip.axis (alias) VALUES('E10');
		
		INSERT INTO gip.ways (alias) VALUES('ASCENDNTE');
		INSERT INTO gip.ways (alias) VALUES('DESCENDENTE');


  END IF;
END $$;