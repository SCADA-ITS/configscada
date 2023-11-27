DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'gip') THEN
		
		INSERT INTO gip.lanes_actions (alias) VALUES('Corte');
		INSERT INTO gip.lanes_actions (alias) VALUES('Apertura');
		
		INSERT INTO gip.lanes_states (alias) VALUES('Cerrada');
		INSERT INTO gip.lanes_states (alias) VALUES('Abierta');
		
		INSERT INTO gip.lanes(alias) VALUES('Ramal CRL.L2');
		INSERT INTO gip.lanes(alias) VALUES('Ramal CRL.R4');
		

  END IF;
END $$;