SET client_min_messages TO WARNING;

---------------------------------------------------------
--
-- DROP TABLES IF EXISTS
--
---------------------------------------------------------

DROP TABLE IF EXISTS hist.transit_logs;
DROP TABLE IF EXISTS hist.transit_attachments;
DROP TABLE IF EXISTS hist.transit_images;
DROP TABLE IF EXISTS hist.transit_vehicle_values;
DROP TABLE IF EXISTS hist.transit_driver_values;
DROP TABLE IF EXISTS hist.transit_values;
DROP TABLE IF EXISTS hist.transits;

---------------------------------------------------------
--
-- Table: hist.transits
-- Descripción: Registro de tránsitos
-- Scope: hist
--
---------------------------------------------------------

CREATE TABLE hist.transits (
    -- Transit info
    transit_id int8 NOT NULL,
    transit_code varchar(200) NOT NULL,
    transit_type_id int8 NOT NULL,
    dossier_code varchar(200) NULL,
    remittance_code varchar(200) NULL,
    installation_code varchar(200) NULL,
    detection_point_code varchar(200) NULL,
    date_transit timestamptz NOT NULL,
    transit_state_id int8 NOT NULL,
    transit_state_option_id int8 NULL,
    location_id int8 NULL,
    lane_number int4 NULL,
    element_type_id int8 NOT NULL,
    element_id int8 NOT NULL,
    certificate_valid_date timestamptz NULL,
    certificate_path varchar(500) NULL,
    infraction_type_id int8 NULL,
    infraction_code varchar(10) NULL,
    speed float8 NULL,
    distance float8 NULL,
    assigned_user_id int8 NULL,
    towards varchar(1000) NULL,
    address varchar(1000) NULL,
    locality varchar(100) NULL,
    road varchar(100) NULL,
    pk varchar(50) NULL,
    direction varchar(20) NULL,

    -- Vehicle info
    vehicle_type_id int8 NULL,
    vehicle_id int8 NULL,
    vehicle_brand_id int8 NULL,
    vehicle_model_id int8 NULL,
    vehicle_class_id int8 NULL,
    vehicle_country_id int8 NULL,
    vehicle_type_name varchar(100) NULL,
    vehicle_brand_name varchar(100) NULL,
    vehicle_model_name varchar(100) NULL,
    vehicle_plate_number varchar(20) NULL,
    vehicle_direction bool NULL,
    vehicle_date_registration timestamptz NULL,
    vehicle_color varchar(100) NULL,

    -- Driver info
    driver_type_id int8 NULL,
    driver_id int8 NULL,
    driver_license_type_id int8 NULL,
    driver_license_value varchar(200) NULL,
    driver_license_issue_date timestamptz NULL,
    driver_license_expire_date timestamptz NULL,
    driver_name varchar(100) NULL,
    driver_surname varchar(300) NULL,
    driver_gender varchar(1) NULL,
    driver_date_of_birth timestamptz NULL,
    driver_country_id int8 NULL,
    driver_state_id int8 NULL,
    driver_region_id int8 NULL,
    driver_locality_id int8 NULL,
    driver_address varchar(1000) NULL,

    -- Others
    enabled bool NULL,
    visible bool NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,

    CONSTRAINT pk_transits PRIMARY KEY (transit_id, created_at)
);

-- Transit indexes
CREATE INDEX idx_hist_transits_transit_types
    ON hist.transits USING btree (transit_type_id);

CREATE INDEX idx_hist_transits_transit_states
    ON hist.transits USING btree (transit_state_id);

CREATE INDEX idx_hist_transits_transit_state_options
    ON hist.transits USING btree (transit_state_id, transit_state_option_id);

CREATE INDEX idx_hist_transits_transit_locations
    ON hist.transits USING btree (location_id);

CREATE INDEX idx_hist_transits_transit_elements
    ON hist.transits USING btree (element_type_id, element_id);

CREATE INDEX idx_hist_transits_transit_infraction_types
    ON hist.transits USING btree (infraction_type_id);

CREATE INDEX idx_hist_transits_transit_users
    ON hist.transits USING btree (assigned_user_id);

CREATE INDEX idx_hist_transits_element_transit_types
    ON hist.transits USING btree (element_type_id, element_id, transit_type_id);

CREATE INDEX idx_hist_transits_search_idx_1
    ON hist.transits USING btree (date_transit, transit_type_id, element_type_id, element_id);

-- Vehicle indexes
CREATE INDEX idx_hist_transits_vehicle_types
    ON hist.transits USING btree (vehicle_type_id);

CREATE INDEX idx_hist_transits_vehicles
    ON hist.transits USING btree (vehicle_type_id, vehicle_id);

CREATE INDEX idx_hist_transits_vehicle_brands
    ON hist.transits USING btree (vehicle_brand_id);

CREATE INDEX idx_hist_transits_vehicle_models
    ON hist.transits USING btree (vehicle_brand_id, vehicle_model_id);

CREATE INDEX idx_hist_transits_vehicle_classes
    ON hist.transits USING btree (vehicle_class_id);

CREATE INDEX idx_hist_transits_vehicle_countries
    ON hist.transits USING btree (vehicle_country_id);

-- Driver indexes
CREATE INDEX idx_hist_transits_driver_types
    ON hist.transits USING btree (driver_type_id);

CREATE INDEX idx_hist_transits_drivers
    ON hist.transits USING btree (driver_type_id, driver_id);

CREATE INDEX idx_hist_transits_driver_license_types
    ON hist.transits USING btree (driver_license_type_id);

CREATE INDEX idx_hist_transits_driver_countries
    ON hist.transits USING btree (driver_country_id);

CREATE INDEX idx_hist_transits_driver_states
    ON hist.transits USING btree (driver_country_id, driver_state_id);

CREATE INDEX idx_hist_transits_driver_regions
    ON hist.transits USING btree (driver_country_id, driver_state_id, driver_region_id);

CREATE INDEX idx_hist_transits_driver_localities
    ON hist.transits USING btree (driver_country_id, driver_state_id, driver_region_id, driver_locality_id);

ALTER TABLE hist.transits SET TABLESPACE tbl_hist;

---------------------------------------------------------
--
-- Table: hist.transit_values
-- Descripción: Parámetros asociados al tránsito
-- Scope: hist
--
---------------------------------------------------------

CREATE TABLE hist.transit_values (
    transit_id int8 NOT NULL,
    transit_type_id int8 NOT NULL,
    transit_type_param_id int8 NOT NULL,
    value varchar NULL,
    visible bool NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,

    CONSTRAINT pk_transit_values
        PRIMARY KEY (transit_id, transit_type_id, transit_type_param_id, created_at)
);

CREATE INDEX idx_hist_transit_values_transit_types
    ON hist.transit_values USING btree (transit_id);

CREATE INDEX idx_hist_transit_values_transit_type_params
    ON hist.transit_values USING btree (transit_type_id, transit_type_param_id);

ALTER TABLE hist.transit_values SET TABLESPACE tbl_hist;

---------------------------------------------------------
--
-- Table: hist.transit_driver_values
-- Descripción: Parámetros del conductor asociado al tránsito
-- Scope: hist
--
---------------------------------------------------------

CREATE TABLE hist.transit_driver_values (
    transit_id int8 NOT NULL,
    driver_type_id int8 NOT NULL,
    driver_param_id int8 NOT NULL,
    value varchar NULL,
    visible bool NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,

    CONSTRAINT pk_transit_driver_values
        PRIMARY KEY (transit_id, driver_type_id, driver_param_id, created_at)
);

CREATE INDEX idx_hist_transit_driver_values_transit_drivers
    ON hist.transit_driver_values USING btree (transit_id);

CREATE INDEX idx_hist_transit_driver_values_driver_params
    ON hist.transit_driver_values USING btree (driver_type_id, driver_param_id);

ALTER TABLE hist.transit_driver_values SET TABLESPACE tbl_hist;

---------------------------------------------------------
--
-- Table: hist.transit_vehicle_values
-- Descripción: Parámetros del vehículo asociado al tránsito
-- Scope: hist
--
---------------------------------------------------------

CREATE TABLE hist.transit_vehicle_values (
    transit_id int8 NOT NULL,
    vehicle_type_id int8 NOT NULL,
    vehicle_param_id int8 NOT NULL,
    value varchar NULL,
    visible bool NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,

    CONSTRAINT pk_transit_vehicle_values
        PRIMARY KEY (transit_id, vehicle_type_id, vehicle_param_id, created_at)
);

CREATE INDEX idx_hist_transit_vehicle_values_transit_vehicles
    ON hist.transit_vehicle_values USING btree (transit_id);

CREATE INDEX idx_hist_transit_driver_values_vehicle_params
    ON hist.transit_vehicle_values USING btree (vehicle_type_id, vehicle_param_id);

ALTER TABLE hist.transit_vehicle_values SET TABLESPACE tbl_hist;

---------------------------------------------------------
--
-- Table: hist.transit_images
-- Descripción: Imágenes asociadas al tránsito
-- Scope: hist
--
---------------------------------------------------------

CREATE TABLE hist.transit_images (
    transit_image_id int8 NOT NULL,
    transit_id int8 NOT NULL,
    path varchar(400) NOT NULL,
    read_only bool NULL,
    to_send bool NULL,
    visible bool NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,

    CONSTRAINT pk_transit_images
        PRIMARY KEY (transit_image_id, created_at)
);

CREATE INDEX idx_hist_transit_images_transits
    ON hist.transit_images USING btree (transit_id);

ALTER TABLE hist.transit_images SET TABLESPACE tbl_hist;

---------------------------------------------------------
--
-- Table: hist.transit_attachments
-- Descripción: Adjuntos asociados al tránsito
-- Scope: hist
--
---------------------------------------------------------

CREATE TABLE hist.transit_attachments (
    transit_attachment_id int8 NOT NULL,
    transit_id int8 NOT NULL,
    path varchar(400) NOT NULL,
    visible bool NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,

    CONSTRAINT pk_transit_attachments
        PRIMARY KEY (transit_attachment_id, created_at)
);

CREATE INDEX idx_hist_transit_attachments_transits
    ON hist.transit_attachments USING btree (transit_id);

ALTER TABLE hist.transit_attachments SET TABLESPACE tbl_hist;

---------------------------------------------------------
--
-- Table: hist.transit_logs
-- Descripción: Trazas asociadas al tránsito
-- Scope: hist
--
---------------------------------------------------------

CREATE TABLE hist.transit_logs (
    transit_log_id int8 NOT NULL,
    transit_id int8 NOT NULL,
    assigned_user_id int8 NOT NULL,
    transit_state_id int8 NOT NULL,
    transit_state_option_id int8 NULL,
    timestamp_log timestamptz NOT NULL,
    log_level varchar(100) NOT NULL,
    message varchar(1000) NULL,
    visible bool NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,

    CONSTRAINT pk_transit_logs
        PRIMARY KEY (transit_log_id, created_at)
);

CREATE INDEX idx_hist_transit_logs_timestamp_log
    ON hist.transit_logs USING btree (timestamp_log);

CREATE INDEX idx_hist_transit_logs_users
    ON hist.transit_logs USING btree (assigned_user_id);

CREATE INDEX idx_hist_transit_logs_transit_states
    ON hist.transit_logs USING btree (transit_state_id);

CREATE INDEX idx_hist_transit_logs_transit_state_options
    ON hist.transit_logs USING btree (transit_state_id, transit_state_option_id);

ALTER TABLE hist.transit_logs SET TABLESPACE tbl_hist;