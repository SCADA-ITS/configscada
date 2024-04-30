DO $$
BEGIN
   IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_dgt_gip') THEN

        CREATE TABLE dgt_gip.marcas(
          id serial not null,
          alias varchar null,
          constraint pk_marca primary key (id)
        ) tablespace tbl_dgt_gip;

        CREATE TABLE dgt_gip.modelo(
          id serial not null,
          marca_id int null,
          alias varchar null,
          constraint pk_modelo primary key (id)
        ) tablespace tbl_dgt_gip;

        ALTER TABLE dgt_gip.modelo add constraint fk_modelo foreign key (marca_id) references dgt_gip.marcas (id) ON DELETE CASCADE;


        CREATE TABLE dgt_gip.transitos(
            id serial not null,
            conductor varchar null,
            marca_vehiculo_id int null,
            modelo_vehiculo_id int null,
            categoria_vehiculo varchar null,
            matricula varchar null,
            nacionalidad_vehiculo varchar null,
            itv varchar null,
            seguro varchar null,
            valoracion boolean null,
            fecha varchar null,
            constraint pk_transitos primary key (id)
            
        ) tablespace tbl_dgt_gip;

        ALTER TABLE dgt_gip.transitos add constraint fk_marca_id foreign key (marca_vehiculo_id) references dgt_gip.marcas (id) ON DELETE CASCADE;
        ALTER TABLE dgt_gip.transitos add constraint fk_modelo_id foreign key (modelo_vehiculo_id) references dgt_gip.modelo (id) ON DELETE CASCADE;


  END IF;
END $$;