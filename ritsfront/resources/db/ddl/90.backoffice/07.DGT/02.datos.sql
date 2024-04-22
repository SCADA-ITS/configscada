DO $$
BEGIN
   IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_dgt_gip') THEN

        CREATE TABLE dgt_gip.transitos(
            id serial not null,
            conductor varchar null,
            marca_vehiculo varchar null,
            modelo_vehiculo varchar null,
            categoria_vehiculo varchar null,
            matricula varchar null,
            nacionalidad_vehiculo varchar null,
            itv varchar null,
            seguro varchar null,
            valoracion boolean null,
            fecha varchar null,
            constraint pk_transitos primary key (id)
            
        ) tablespace tbl_dgt_gip;


  END IF;
END $$;