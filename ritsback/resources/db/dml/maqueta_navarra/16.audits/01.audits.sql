UPDATE master.log_types SET level_output_db = 2;
UPDATE master.log_types SET level_output_stomp = 2;
UPDATE master.log_types SET level_treatment = 2;

UPDATE master.log_subtypes SET level_output_db = 2 WHERE log_subtype_id IN (4,6,34,35,36,38,39,40,42,43,44);
UPDATE master.log_subtypes SET level_output_stomp = 2 WHERE log_subtype_id IN (4,6,34,35,36,38,39,40,42,43,44);
UPDATE master.log_subtypes SET level_treatment = 2 WHERE log_subtype_id IN (4,6,34,35,36,38,39,40,42,43,44);