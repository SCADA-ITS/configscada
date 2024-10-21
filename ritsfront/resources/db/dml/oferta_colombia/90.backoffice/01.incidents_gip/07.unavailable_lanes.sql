DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'incidents_gip') THEN
		
		INSERT INTO incidents_gip.lanes_actions (alias) VALUES('Corte');
		INSERT INTO incidents_gip.lanes_actions (alias) VALUES('Apertura');
		
		INSERT INTO incidents_gip.lanes_states (alias) VALUES('Cerrada');
		INSERT INTO incidents_gip.lanes_states (alias) VALUES('Abierta');	

  END IF;
END $$;