DELETE FROM conf.system_param_values;

-- 
-- Table: conf.system_param_values
--

INSERT INTO conf.system_param_values
(system_param_id, value, enabled, visible, created_at, updated_at) VALUES
-- Timeout (min) para que expire la sesión de usuario
(1, '30', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Número de reintentos de login máximos para un usuario
(2, '3', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Número de días para caducidad de una contraseña
(3, '90', true, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Tiempo (min) de bloque de una cuenta de usuario
(4, '60', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Periodo de integración del traffic-manager (min)
(5, '1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Segundo de inicio de cálculo de datos para el traffic-manager
(6, '40', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Tiempo de espera (s) para autoconfirmar una incidencia
(7, '300', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Expressión cron para el tiempo de borrado de incidencias y que pasen a históricos
(8, '0 * * ? * * *', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Configuración del servicio de correo
(9, 
'{
	"mail.smtp.auth": "true",
	"mail.smtp.starttls.enable": "true",
	"mail.smtp.host": "smtp.serviciodecorreo.es ",
	"mail.smtp.port": "587",
	"mail.smtp.ssl.trust": "*",
	"mail.smtp.ssl.protocols": "TLSv1.2",
	"mail.smtp.user": "cgt@traficonavarra.es",
	"mail.smtp.password": "TraficoNavarra2@"
}'
, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Número máximo de alarmas sin reconocer permitidas
(14, '100', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Período de cálculo para los tiempos de recorrido
(15, '5', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Número máximo de sesiones simultáneas
(16, '10', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Número máximo de log permitidas sin reconocer
(17, '100', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- URL de conexión al smart road info
(18, 'https://descontroltrafico.admon-cfnavarra.es/api', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Path para servidor de ficheros
(19, '/home/admin/app/frontrits/resources/trs', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

