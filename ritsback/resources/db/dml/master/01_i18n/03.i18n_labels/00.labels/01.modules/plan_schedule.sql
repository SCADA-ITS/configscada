-- 
-- Type:   modules
-- Entity: Plan schedule
-- File:   plan_schedule.sql
-- Langs:  es_ES, en_GB, sk_SK, es_CL
--

-- 
-- Table: master.i18n_labels
--
INSERT INTO master.i18n_labels (locale_code,module_id,"label","translation",created_at,updated_at) VALUES
--es_ES
('es_ES', 1, 'LBL_PLAN_CRON', 'Programación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_PLAN_ID', 'ID', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_ES', 1, 'LBL_PLAN_SCHEDULER', 'Planificaciones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    
--en_GB	 
('en_GB', 1, 'LBL_PLAN_CRON', 'Scheduling', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_PLAN_ID', 'ID', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('en_GB', 1, 'LBL_PLAN_SCHEDULER', 'Plan scheduler', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    
--sk_SK	 
('sk_SK', 1, 'LBL_PLAN_CRON', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_PLAN_ID', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sk_SK', 1, 'LBL_PLAN_SCHEDULER', 'Plánovač', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--es_CL	 
('es_CL', 1, 'LBL_PLAN_CRON', 'Programación', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_PLAN_ID', 'ID', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('es_CL', 1, 'LBL_PLAN_SCHEDULER', 'Planificaciones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
