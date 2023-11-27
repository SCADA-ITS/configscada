DO
$do$
BEGIN
IF EXISTS (SELECT * from information_schema.tables WHERE table_schema = 'rt' and table_name = 'alarms') THEN
	IF NOT EXISTS (SELECT * from information_schema.columns WHERE table_schema = 'rt' and table_name = 'alarms' and column_name = 'disagree') THEN
	
		RAISE INFO 'Creating column disagree in table rt.alarms';
	    ALTER TABLE rt.alarms ADD COLUMN disagree bool NULL;
	
	END IF;
END IF;
END;
$do$