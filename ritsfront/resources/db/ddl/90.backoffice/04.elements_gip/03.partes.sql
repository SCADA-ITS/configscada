DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_elements_gip') THEN

    CREATE TABLE elements_gip.element_types(
        id int4 null,
        alias varchar null,
        constraint pk_element_types primary key (id)
    ) tablespace tbl_elements_gip;

    CREATE TABLE elements_gip.locations(
        id int4 null,
        alias varchar null,
        constraint pk_locations primary key (id)
    ) tablespace tbl_elements_gip;

    CREATE TABLE elements_gip.elementos(
        id serial not null,
        element_type_id int4 not null,
        alias varchar null,
        location_id int4 null,
        constraint pk_elementos primary key(id)
    ) tablespace tbl_elements_gip;

    ALTER TABLE elements_gip.elementos add constraint fk_element_type_id foreign key (element_type_id) references elements_gip.element_types (id) ON DELETE CASCADE;
    ALTER TABLE elements_gip.elementos add constraint fk_location_id foreign key (location_id) references elements_gip.locations (id) ON DELETE CASCADE;

     CREATE TABLE elements_gip.acciones(
        id serial not null,
        alias varchar null,
        constraint pk_acciones primary key (id)
    ) tablespace tbl_elements_gip;  

     CREATE TABLE elements_gip.causas(
        id serial not null,
        alias varchar null,
        constraint pk_causas primary key (id)
    ) tablespace tbl_elements_gip; 

     CREATE TABLE elements_gip.afecciones(
        id serial not null,
        alias varchar null,
        constraint pk_afecciones primary key (id)
    ) tablespace tbl_elements_gip;       


    CREATE TABLE elements_gip.partes_trabajos(
        id serial not null,
        id_element varchar null,
        elementos_id int4 null,
        acciones_id int4 null,
        causas_id int4 null,
        usuarios varchar null,
        comentarios varchar null,
        afecciones_id int4 null,
		inicio timestamptz null,
		fin timestamptz null,
        constraint pk_partes_trabajos primary key (id)

    ) tablespace tbl_elements_gip;


    ALTER TABLE elements_gip.partes_trabajos add constraint fk_element_id foreign key (elementos_id) references elements_gip.elementos (id) ON DELETE CASCADE;
    ALTER TABLE elements_gip.partes_trabajos add constraint fk_acciones_id foreign key (acciones_id) references elements_gip.acciones (id) ON DELETE CASCADE;
    ALTER TABLE elements_gip.partes_trabajos add constraint fk_causas_id foreign key (causas_id) references elements_gip.causas (id) ON DELETE CASCADE;
    ALTER TABLE elements_gip.partes_trabajos add constraint fk_road_impact foreign key (afecciones_id) references elements_gip.afecciones (id) ON DELETE CASCADE;
  END IF;
END $$;