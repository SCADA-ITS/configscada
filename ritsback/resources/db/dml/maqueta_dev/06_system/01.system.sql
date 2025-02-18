DELETE FROM conf.system_param_values;

-- 
-- Table: conf.system_param_values
--

INSERT INTO conf.system_param_values
(system_param_id, value, enabled, visible, created_at, updated_at) VALUES
(1, '30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '90', true, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, '60', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, '40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, '60', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, '0 * * ? * * *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 
'{
	"mail.smtp.auth": "true",
	"mail.smtp.starttls.enable": "true",
	"mail.smtp.host": "smtp.gmail.com",
	"mail.smtp.port": "587",
	"mail.smtp.ssl.trust": "*",
	"mail.smtp.ssl.protocols": "TLSv1.2",
	"mail.smtp.user": "dev.openits@gmail.com",
	"mail.smtp.password": "Revenga.19"
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, '5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 'http://192.168.88.161:8702/api', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, '/home/agomez/radar', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
