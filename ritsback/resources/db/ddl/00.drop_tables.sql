SET client_min_messages TO WARNING;

CREATE TABLE rt.temp_fk_constraints_rt AS
SELECT conrelid::regclass AS tabla,
       conname AS constraint_name,
       pg_get_constraintdef(oid) AS definition
FROM pg_constraint
WHERE confrelid IS NOT null
AND contype = 'f'
AND connamespace::regnamespace::text = 'rt';

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