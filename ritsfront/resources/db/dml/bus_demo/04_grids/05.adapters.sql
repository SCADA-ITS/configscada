-- Update datasource_id for user's grid
UPDATE ui.adapter_data_sources SET data_source_id = 234 WHERE adapter_id = 3 and adapter_data_source_type_id = 1;
-- Update datasource_id for role's grid
UPDATE ui.adapter_data_sources SET data_source_id = 235 WHERE adapter_id = 1 and adapter_data_source_type_id = 1;
