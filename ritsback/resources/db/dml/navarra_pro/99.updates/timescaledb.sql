SELECT create_hypertable('hist.ext_entities', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.ext_entity_values', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.alarms', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.element_states', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);

SELECT create_hypertable('hist.transits', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.transit_images', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.transit_logs', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.transit_values', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.transit_vehicle_values', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.transit_driver_values', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.transit_attachments', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);

SELECT create_hypertable('hist.ims_incident_reports', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.ims_incident_report_task_values', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.ims_incident_report_task_states', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.ims_incident_report_logs', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);
SELECT create_hypertable('hist.ims_incident_report_log_values', 'created_at', chunk_time_interval => INTERVAL '1 month', migrate_data => true);


SELECT add_retention_policy('hist.ext_entities', INTERVAL '13 months');
SELECT add_retention_policy('hist.ext_entity_values', INTERVAL '13 months');
SELECT add_retention_policy('hist.alarms', INTERVAL '13 months');
SELECT add_retention_policy('hist.element_states', INTERVAL '13 months');

SELECT add_retention_policy('hist.transits', INTERVAL '13 months');
SELECT add_retention_policy('hist.transit_images', INTERVAL '13 months');
SELECT add_retention_policy('hist.transit_logs', INTERVAL '13 months');
SELECT add_retention_policy('hist.transit_values', INTERVAL '13 months');
SELECT add_retention_policy('hist.transit_vehicle_values', INTERVAL '13 months');
SELECT add_retention_policy('hist.transit_driver_values', INTERVAL '13 months');
SELECT add_retention_policy('hist.transit_attachments', INTERVAL '13 months');

SELECT add_retention_policy('hist.ims_incident_reports', INTERVAL '13 months');
SELECT add_retention_policy('hist.ims_incident_report_task_values', INTERVAL '13 months');
SELECT add_retention_policy('hist.ims_incident_report_task_states', INTERVAL '13 months');
SELECT add_retention_policy('hist.ims_incident_report_logs', INTERVAL '13 months');
SELECT add_retention_policy('hist.ims_incident_report_log_values', INTERVAL '13 months');

SELECT alter_job(
    id::integer,
    schedule_interval => INTERVAL '1 month'
)
FROM _timescaledb_config.bgw_job
WHERE id BETWEEN 1000 AND 1015;