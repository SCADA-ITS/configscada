SET client_min_messages TO WARNING;

DO $$
BEGIN
    IF EXISTS (
        SELECT schema_name 
        FROM information_schema.schemata 
        WHERE schema_name = 'conf'
    ) THEN
        DROP SCHEMA conf CASCADE;
    END IF;
END $$;

DO $$
BEGIN
    IF EXISTS (
        SELECT schema_name 
        FROM information_schema.schemata 
        WHERE schema_name = 'static'
    ) THEN
        DROP SCHEMA static CASCADE;
    END IF;
END $$;


DO $$
BEGIN
    IF EXISTS (
        SELECT schema_name 
        FROM information_schema.schemata 
        WHERE schema_name = 'master'
    ) THEN
        DROP SCHEMA master CASCADE;
    END IF;
END $$;

CREATE SCHEMA conf;
CREATE SCHEMA static;
CREATE SCHEMA master;