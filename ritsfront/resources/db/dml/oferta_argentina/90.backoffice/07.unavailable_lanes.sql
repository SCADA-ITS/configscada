DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents_gip') THEN
		
		INSERT INTO incidents_gip.lanes_actions (alias) VALUES('Corte');
		INSERT INTO incidents_gip.lanes_actions (alias) VALUES('Apertura');
		
		INSERT INTO incidents_gip.lanes_states (alias) VALUES('Cerrada');
		INSERT INTO incidents_gip.lanes_states (alias) VALUES('Abierta');	
		
		INSERT INTO incidents_gip.lanes (state_id, alias) VALUES(1, 'Carril izquierdo');	
		INSERT INTO incidents_gip.lanes (state_id, alias) VALUES(1, 'Carril derecho');	
		INSERT INTO incidents_gip.lanes (state_id, alias) VALUES(1, 'Ambos carriles');	
		INSERT INTO incidents_gip.lanes (state_id, alias) VALUES(2, 'Carril izquierdo');	
		INSERT INTO incidents_gip.lanes (state_id, alias) VALUES(2, 'Carril derecho');	
		INSERT INTO incidents_gip.lanes (state_id, alias) VALUES(2, 'Ambos carriles');
  END IF;
END $$;