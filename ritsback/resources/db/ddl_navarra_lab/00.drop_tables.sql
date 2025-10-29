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
DECLARE
    r RECORD;
BEGIN
    -- Borrar todas las tablas del esquema conf
    FOR r IN
        SELECT tablename 
        FROM pg_tables 
        WHERE schemaname = 'conf'
    LOOP
        EXECUTE format('DROP TABLE IF EXISTS conf.%I CASCADE', r.tablename);
    END LOOP;

    -- Borrar todas las tablas del esquema static
    FOR r IN
        SELECT tablename 
        FROM pg_tables 
        WHERE schemaname = 'static'
    LOOP
        EXECUTE format('DROP TABLE IF EXISTS static.%I CASCADE', r.tablename);
    END LOOP;

    -- Borrar todas las tablas del esquema master
    FOR r IN
        SELECT tablename 
        FROM pg_tables 
        WHERE schemaname = 'master'
    LOOP
        EXECUTE format('DROP TABLE IF EXISTS master.%I CASCADE', r.tablename);
    END LOOP;
END $$;