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

    CREATE TABLE elements_gip.elements(
        id serial not null,
        element_type_id int4 not null,
        alias varchar null,
        location_id int4 null,
        constraint pk_elements primary key(id)
    ) tablespace tbl_elements_gip;

    ALTER TABLE elements_gip.elements add constraint fk_element_type_id foreign key (element_type_id) references elements_gip.element_types (id) ON DELETE CASCADE;
    ALTER TABLE elements_gip.elements add constraint fk_location_id foreign key (location_id) references elements_gip.locations (id) ON DELETE CASCADE;

     CREATE TABLE elements_gip.actions(
        id serial not null,
        alias varchar null,
        constraint pk_action primary key (id)
    ) tablespace tbl_elements_gip;  

     CREATE TABLE elements_gip.causes(
        id serial not null,
        alias varchar null,
        constraint pk_causes primary key (id)
    ) tablespace tbl_elements_gip; 

     CREATE TABLE elements_gip.road_impacts(
        id serial not null,
        alias varchar null,
        constraint pk_road_impacts primary key (id)
    ) tablespace tbl_elements_gip;       


    CREATE TABLE elements_gip.partes_trabajos(
        id serial not null,
        id_element varchar null,
        elements_id int4 null,
        action_id int4 null,
        cause_id int4 null,
        users_names varchar null,
        comments varchar null,
        road_imapcts_id int4 null,
		arrival_date timestamptz null,
		release_date timestamptz null,
        constraint pk_partes_trabajos primary key (id)

    ) tablespace tbl_elements_gip;


    ALTER TABLE elements_gip.partes_trabajos add constraint fk_element_id foreign key (elements_id) references elements_gip.elements (id) ON DELETE CASCADE;
    ALTER TABLE elements_gip.partes_trabajos add constraint fk_action_id foreign key (action_id) references elements_gip.actions (id) ON DELETE CASCADE;
    ALTER TABLE elements_gip.partes_trabajos add constraint fk_cause_id foreign key (cause_id) references elements_gip.causes (id) ON DELETE CASCADE;
    ALTER TABLE elements_gip.partes_trabajos add constraint fk_road_impact foreign key (road_imapcts_id) references elements_gip.road_impacts (id) ON DELETE CASCADE;
  END IF;
END $$;