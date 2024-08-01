DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'nogales_gip') THEN
		
		INSERT INTO nogales_gip.lanes_actions (alias) VALUES('Corte');
		INSERT INTO nogales_gip.lanes_actions (alias) VALUES('Apertura');
		
		INSERT INTO nogales_gip.lanes_states (alias) VALUES('Cerrada');
		INSERT INTO nogales_gip.lanes_states (alias) VALUES('Abierta');	
		
		INSERT INTO nogales_gip.lanes (alias, state_id) VALUES('Carril izquierdo', 1);	
		INSERT INTO nogales_gip.lanes (alias, state_id) VALUES('Carril derecho', 1);	
		INSERT INTO nogales_gip.lanes (alias, state_id) VALUES('Ambos carriles', 1);	
		INSERT INTO nogales_gip.lanes (alias, state_id) VALUES('Carril izquierdo', 2);	
		INSERT INTO nogales_gip.lanes (alias, state_id) VALUES('Carril derecho', 2);	
		INSERT INTO nogales_gip.lanes (alias, state_id) VALUES('Ambos carriles', 2);
  END IF;
END $$;