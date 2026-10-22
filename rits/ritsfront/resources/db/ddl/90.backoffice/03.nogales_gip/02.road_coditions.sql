DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_nogales_gip') THEN

create table nogales_gip.conditions (
	id serial not null,
	alias varchar null,
	constraint pk_conditions primary key (id)
) tablespace tbl_nogales_gip;

create table nogales_gip.luminosities (
	id serial not null,
	alias varchar null,
	constraint pk_luminosities primary key (id)
) tablespace tbl_nogales_gip;

create table nogales_gip.atmospheric_states (
	id serial not null,
	alias varchar null,
	constraint pk_atmospheric_states primary key (id)
) tablespace tbl_nogales_gip;

create table nogales_gip.causes (
	id serial not null,
	alias varchar null,
	constraint pk_causes primary key (id)
) tablespace tbl_nogales_gip;

create table nogales_gip.maneuvers (
	id serial not null,
	alias varchar null,
	constraint pk_maneuvers primary key (id)
) tablespace tbl_nogales_gip;

create table nogales_gip.road_impacts (
	id serial not null,
	alias varchar null,
	constraint pk_road_impacts primary key (id)
) tablespace tbl_nogales_gip;

create table nogales_gip.infrastructure_damages (
	id serial not null,
	alias varchar null,
	constraint pk_infrastructure_damages primary key (id)
) tablespace tbl_nogales_gip;

  END IF;
END $$;



