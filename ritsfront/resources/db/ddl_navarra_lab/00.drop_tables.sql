SET client_min_messages TO WARNING;
DO $$
BEGIN
    IF EXISTS (
        SELECT schema_name 
        FROM information_schema.schemata 
        WHERE schema_name = 'ui'
    ) THEN
        DROP SCHEMA ui CASCADE;
    END IF;
END $$;

CREATE SCHEMA ui;
