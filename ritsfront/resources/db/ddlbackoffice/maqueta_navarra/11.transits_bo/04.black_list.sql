DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_transits_bo') THEN
  
		create table transits_bo.black_list (
			id serial not null,
			matricula  varchar null,
			administration_id int not null,
			fecha_inicio timestamptz null,
			fecha_fin timestamptz null,
			constraint pk_black_list primary key (id),
			constraint fk_black_list_administration foreign key (administration_id)
				references transits_bo.administrations(id)
		) tablespace tbl_transits_bo;	

		create index idx_black_list_administration_id on
		transits_bo.black_list using btree (administration_id);

		-- Cargar la extensión `tablefunc` si no está ya instalada
	    CREATE EXTENSION IF NOT EXISTS tablefunc;

	END IF;
END $$;



