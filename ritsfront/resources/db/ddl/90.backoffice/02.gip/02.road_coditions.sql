DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_tablespace WHERE spcname = 'tbl_gip') THEN

create table gip.conditions (
	id serial not null,
	alias varchar null,
	constraint pk_conditions primary key (id)
) tablespace tbl_gip;

create table gip.luminosities (
	id serial not null,
	alias varchar null,
	constraint pk_luminosities primary key (id)
) tablespace tbl_gip;

create table gip.atmospheric_states (
	id serial not null,
	alias varchar null,
	constraint pk_atmospheric_states primary key (id)
) tablespace tbl_gip;

create table gip.causes (
	id serial not null,
	alias varchar null,
	constraint pk_causes primary key (id)
) tablespace tbl_gip;

create table gip.maneuvers (
	id serial not null,
	alias varchar null,
	constraint pk_maneuvers primary key (id)
) tablespace tbl_gip;

create table gip.road_impacts (
	id serial not null,
	alias varchar null,
	constraint pk_road_impacts primary key (id)
) tablespace tbl_gip;

create table gip.infrastructure_damages (
	id serial not null,
	alias varchar null,
	constraint pk_infrastructure_damages primary key (id)
) tablespace tbl_gip;

create table gip.road_conditions (
	id serial not null,
	alias varchar null,
	condition_id int null,
	luminosity_id int null,
	atmospheric_state_id int null,
	cause_id int null,
	maneuver_id int null,
	road_impact_id int null,
	infrastructure_damage_id int null,
	constraint pk_road_conditions primary key (id)
) tablespace tbl_gip;

create index idx_condition_id on
gip.road_conditions
	using btree (condition_id);

create index idx_luminosity_id on
gip.road_conditions
	using btree (luminosity_id);

create index idx_atmospheric_state_id on
gip.road_conditions
	using btree (atmospheric_state_id);

create index idx_cause_id on
gip.road_conditions
	using btree (cause_id);

create index idx_maneuver_id on
gip.road_conditions
	using btree (maneuver_id);

create index idx_road_impact_id on
gip.road_conditions
	using btree (road_impact_id);

create index idx_infrastructure_damage_id on
gip.road_conditions
	using btree (infrastructure_damage_id);

alter table gip.road_conditions add constraint fk_condition_id foreign key (condition_id) references gip.conditions(id) ON DELETE CASCADE;
alter table gip.road_conditions add constraint fk_luminosity_id foreign key (luminosity_id) references gip.luminosities(id) ON DELETE CASCADE;
alter table gip.road_conditions add constraint fk_atmospheric_state_id foreign key (atmospheric_state_id) references gip.atmospheric_states(id) ON DELETE CASCADE;
alter table gip.road_conditions add constraint fk_cause_id foreign key (cause_id) references gip.causes(id) ON DELETE CASCADE;
alter table gip.road_conditions add constraint fk_maneuver_id foreign key (maneuver_id) references gip.maneuvers(id) ON DELETE CASCADE;
alter table gip.road_conditions add constraint fk_road_impact_id foreign key (road_impact_id) references gip.road_impacts(id) ON DELETE CASCADE;
alter table gip.road_conditions add constraint fk_infrastructure_damage_id foreign key (infrastructure_damage_id) references gip.infrastructure_damages(id) ON DELETE CASCADE;


  END IF;
END $$;



