DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_transits_bo') THEN
  
		create table transits_bo.white_list (
			id serial not null,
			matricula  varchar null,
			observaciones varchar null,
			constraint pk_white_list primary key (id)
		) tablespace tbl_transits_bo;	

		-- Cargar la extensión `tablefunc` si no está ya instalada
	    CREATE EXTENSION IF NOT EXISTS tablefunc;

	END IF;
END $$;



