SET client_min_messages TO WARNING;

DO $$ 
DECLARE r RECORD;
BEGIN
	
	IF EXISTS (
        SELECT table_name
 	        FROM information_schema.tables
 	        where table_schema = 'rt'
 	          and table_name =  'temp_fk_constraints_rt'
    ) THEN
    
	    FOR r IN (SELECT * FROM rt.temp_fk_constraints_rt) LOOP
	    	BEGIN

		    	IF NOT EXISTS (
		            SELECT 1 
		            FROM pg_constraint c
		            WHERE confrelid IS NOT null
					  AND conname = r.constraint_name			
		              AND contype = 'f'
					  AND connamespace::regnamespace::text = 'rt'
		        ) THEN
	
		        	EXECUTE format('ALTER TABLE %s ADD CONSTRAINT %s %s', 
		                r.tabla, r.constraint_name, r.definition);
		        END IF;

		    EXCEPTION 
	            WHEN OTHERS THEN
	                RAISE WARNING 'Error to apply constraint % in table %: %', 
	                    r.constraint_name, r.tabla, SQLERRM;
	        END;
	    END LOOP;
        
	    DROP TABLE rt.temp_fk_constraints_rt;
        
    END IF;
    
END $$;