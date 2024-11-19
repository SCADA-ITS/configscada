UPDATE master.log_types SET level_output_db = 2;

UPDATE master.log_subtypes SET level_output_db = 2 WHERE log_subtype_id IN (1,2,36,38,39,40);