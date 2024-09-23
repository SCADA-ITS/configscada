---------------------------------------------------------
--
-- TABLES FOR UI
--
---------------------------------------------------------

-- 
-- Table: ui.geo_its_roads
-- Description: Roads
-- Scope: ui
CREATE TABLE ui.geo_its_roads (
  id int8 primary key,
  nombre varchar(500) not null,
  sentido varchar(50) not null
)
TABLESPACE tbl_ui;


-- 
-- Table: ui.geo_its_pks
-- Description: Road pks
-- Scope: ui
CREATE TABLE ui.geo_its_pks (
  id int8 primary key,
  geo_its_road_id int8 not null,
  numero int4 not null,
  latitud float8 not null,
  longitud float8 not null
)
TABLESPACE tbl_ui;

-- ui.geo_its_pks foreign keys

CREATE INDEX idx_fk_geo_its_pks_geo_its_road ON ui.geo_its_pks USING btree (geo_its_road_id);

ALTER TABLE ui.geo_its_pks ADD CONSTRAINT fk_geo_its_pks_geo_its_road FOREIGN KEY (geo_its_road_id) REFERENCES ui.geo_its_roads(id);