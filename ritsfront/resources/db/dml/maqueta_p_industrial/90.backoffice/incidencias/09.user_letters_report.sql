DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
		
		INSERT INTO backoffice.user_letter_recipient_titles (alias) VALUES('Señor');
		INSERT INTO backoffice.user_letter_recipient_titles (alias) VALUES('Señora');

  END IF;
END $$;