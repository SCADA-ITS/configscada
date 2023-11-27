DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'nogales_gip') THEN
		
		INSERT INTO nogales_gip.lanes_actions (alias) VALUES('Corte');
		INSERT INTO nogales_gip.lanes_actions (alias) VALUES('Apertura');
		
		INSERT INTO nogales_gip.lanes_states (alias) VALUES('Cerrada');
		INSERT INTO nogales_gip.lanes_states (alias) VALUES('Abierta');	

  END IF;
END $$;