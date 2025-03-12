SET client_min_messages TO WARNING;

DO $$ 
DECLARE r RECORD;
BEGIN
    FOR r IN (SELECT * FROM rt.temp_fk_constraints_rt) LOOP
    
        -- Verificar si la constraint ya existe
        IF NOT EXISTS (
            SELECT 1 
            FROM pg_constraint c
            WHERE confrelid IS NOT null
			  AND conname = r.constraint_name			
              AND contype = 'f'
			  AND connamespace::regnamespace::text = 'rt'
        ) THEN
            -- Si no existe, agregar la constraint
            EXECUTE format('ALTER TABLE %I ADD CONSTRAINT %I %s', 
                r.tabla, r.constraint_name, r.definition);
        END IF;
        
    END LOOP;
    
    DROP TABLE rt.temp_fk_constraints_rt;
    
END $$;