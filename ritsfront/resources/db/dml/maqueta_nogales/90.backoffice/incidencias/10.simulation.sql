DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
		--Incidente 1
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(1, '1', '31.6', '2025-02-10 03:01:29', 2, 2, 1, 'Vehículo menor pierde el control y choca contra barrera de contención.', 2, 1, 3, 5, 1, 1, 2, 1, 1, 1, 2, 2, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 61', NULL, 'No hubo', 'Patrulla 15', 'Operador 1', 'Supervisor 1');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (1,4,3,1,1,'XBTB52',46),
	    (1,5,3,1,4,'LKDW64',46);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(1,NULL,NULL,NULL,NULL,'LKDW64',2,2,1),
		(1,NULL,NULL,NULL,NULL,'XBTB52',2,3,1),
		(1,NULL,NULL,NULL,NULL,'XBTB52',1,2,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(1, 1, 1, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(1, 1, 2, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(1, 1, 3, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(1, 2, 9, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(1, 2, 11, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(1, 2, 12, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 2
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(2, '2', '36.2', '2025-11-06 01:11:06', 2, 2, 1, 'Volcamiento de camión de carga por exceso de velocidad en curva.', 1, 1, 3, 5, 2, 1, 2, 1, 1, 1, 2, 9, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 90', NULL, 'No hubo', 'Patrulla 3', 'Operador 3', 'Supervisor 2');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (2,4,3,1,1,'LYKH59',39),
	    (2,5,3,1,4,'BCXJ52',31);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(2,NULL,NULL,NULL,NULL,'BCXJ52',2,2,1),
		(2,NULL,NULL,NULL,NULL,'LYKH59',2,2,1),
		(2,NULL,NULL,NULL,NULL,'LYKH59',1,3,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(2, 1, 4, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(2, 1, 5, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(2, 1, 6, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(2, 2, 11, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(2, 2, 13, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(2, 2, 15, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 3
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(3, '3', '103.9', '2025-05-24 05:42:59', 2, 2, 1, 'Colisión por alcance entre automóvil y camioneta, con daños materiales y un lesionado leve.', 3, 1, 3, 5, 3, 1, 2, 1, 1, 1, 2, 7, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 73', NULL, 'No hubo', 'Patrulla 14', 'Operador 3', 'Supervisor 4');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (3,4,3,1,1,'BZLM36',32),
	    (3,5,3,1,4,'SBSL27',39);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(3,NULL,NULL,NULL,NULL,'SBSL27',2,4,1),
		(3,NULL,NULL,NULL,NULL,'BZLM36',2,2,1),
		(3,NULL,NULL,NULL,NULL,'BZLM36',1,2,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(3, 1, 7, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(3, 1, 8, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(3, 1, 1, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(3, 2, 11, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(3, 2, 14, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(3, 2, 13, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 4
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(4, '4', '26.4', '2024-07-12 23:26:14', 2, 2, 1, 'Volcamiento de camión de carga por exceso de velocidad en curva.', 4, 1, 3, 5, 4, 1, 2, 1, 1, 1, 2, 6, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 54', NULL, 'No hubo', 'Patrulla 2', 'Operador 3', 'Supervisor 5');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (4,4,3,1,1,'YBVN97',50),
	    (4,5,3,1,4,'JCTL77',43);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(4,NULL,NULL,NULL,NULL,'JCTL77',2,3,1),
		(4,NULL,NULL,NULL,NULL,'YBVN97',2,2,1),
		(4,NULL,NULL,NULL,NULL,'YBVN97',1,4,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(4, 1, 1, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(4, 1, 2, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(4, 1, 3, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(4, 2, 9, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(4, 2, 11, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(4, 2, 14, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 5
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(5, '5', '82.2', '2024-02-05 10:21:03', 2, 2, 1, 'Bus interurbano colisiona con automóvil particular en cruce no regulado.', 5, 1, 3, 5, 5, 1, 2, 1, 1, 1, 2, 4, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 79', NULL, 'No hubo', 'Patrulla 15', 'Operador 2', 'Supervisor 5');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (5,4,3,1,1,'MXNC73',41),
	    (5,5,3,1,4,'GTZM15',50);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(5,NULL,NULL,NULL,NULL,'GTZM15',2,2,1),
		(5,NULL,NULL,NULL,NULL,'MXNC73',2,2,1),
		(5,NULL,NULL,NULL,NULL,'MXNC73',1,4,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(5, 1, 4, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(5, 1, 5, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(5, 1, 6, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(5, 2, 12, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(5, 2, 13, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(5, 2, 15, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 6
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(6, '6', '12.7', '2025-04-03 10:43:16', 2, 2, 1, 'Colisión frontal entre automóvil y furgón en zona rural.', 6, 1, 3, 5, 6, 1, 2, 1, 1, 1, 2, 3, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 66', NULL, 'No hubo', 'Patrulla 3', 'Operador 2', 'Supervisor 4');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (6,4,3,1,1,'NMTD28',31),
	    (6,5,3,1,4,'JZZX63',44);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(6,NULL,NULL,NULL,NULL,'JZZX63',2,3,1),
		(6,NULL,NULL,NULL,NULL,'NMTD28',2,2,1),
		(6,NULL,NULL,NULL,NULL,'NMTD28',1,2,1);

		--Incidente 7
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(7, '7', '63.3', '2025-09-15 03:14:56', 2, 2, 1, 'Vehículo menor pierde el control y choca contra barrera de contención.', 7, 1, 3, 5, 7, 1, 2, 1, 1, 1, 2, 1, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 76', NULL, 'No hubo', 'Patrulla 2', 'Operador 7', 'Supervisor 3');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (7,4,3,1,1,'ZXSV39',45),
	    (7,5,3,1,4,'CDFD28',39);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(7,NULL,NULL,NULL,NULL,'CDFD28',2,3,1),
		(7,NULL,NULL,NULL,NULL,'ZXSV39',2,4,1),
		(7,NULL,NULL,NULL,NULL,'ZXSV39',1,3,1);

		--Incidente 8
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(8, '8', '34.5', '2024-03-07 22:26:45', 2, 2, 1, 'Volcamiento de camión de carga por exceso de velocidad en curva.', 8, 1, 3, 5, 8, 1, 2, 1, 1, 1, 2, 12, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 95', NULL, 'No hubo', 'Patrulla 10', 'Operador 8', 'Supervisor 2');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (8,4,3,1,1,'BJDK94',44),
	    (8,5,3,1,4,'VGJL21',44);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(8,NULL,NULL,NULL,NULL,'VGJL21',2,4,1),
		(8,NULL,NULL,NULL,NULL,'BJDK94',2,3,1),
		(8,NULL,NULL,NULL,NULL,'BJDK94',1,3,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(8, 1, 4, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(8, 1, 5, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(8, 1, 6, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(8, 2, 9, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(8, 2, 13, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(8, 2, 15, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 9
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(9, '9', '71.0', '2025-03-01 09:16:41', 2, 2, 1, 'Colisión por alcance entre automóvil y camioneta, con daños materiales y un lesionado leve.', 9, 1, 3, 5, 9, 1, 2, 1, 1, 1, 2, 11, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 50', NULL, 'No hubo', 'Patrulla 13', 'Operador 1', 'Supervisor 5');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (9,4,3,1,1,'SMCW44',34),
	    (9,5,3,1,4,'WSXL94',42);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(9,NULL,NULL,NULL,NULL,'WSXL94',2,4,1),
		(9,NULL,NULL,NULL,NULL,'SMCW44',2,3,1),
		(9,NULL,NULL,NULL,NULL,'SMCW44',1,3,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(9, 1, 4, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(9, 1, 5, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(9, 1, 6, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(9, 2, 11, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(9, 2, 14, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(9, 2, 15, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 10
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(10, '10', '15.8', '2025-11-11 18:44:02', 2, 2, 1, 'Camión con acoplado derrama carga en la vía, provocando accidente múltiple.', 10, 1, 3, 5, 10, 1, 2, 1, 1, 1, 2, 10, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 90', NULL, 'No hubo', 'Patrulla 15', 'Operador 4', 'Supervisor 4');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (10,4,3,1,1,'RLZY16',33),
	    (10,5,3,1,4,'SWZC51',41);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(10,NULL,NULL,NULL,NULL,'SWZC51',2,4,1),
		(10,NULL,NULL,NULL,NULL,'RLZY16',2,3,1),
		(10,NULL,NULL,NULL,NULL,'RLZY16',1,2,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(10, 1, 1, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(10, 1, 2, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(10, 1, 3, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(10, 2, 9, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(10, 2, 11, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(10, 2, 12, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 11
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(11, '11', '106.9', '2024-02-05 08:06:52', 2, 2, 1, 'Atropello de peatón en paso no habilitado, con lesiones graves.', 1, 1, 3, 5, 11, 1, 2, 1, 1, 1, 2, 1, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 90', NULL, 'No hubo', 'Patrulla 15', 'Operador 5', 'Supervisor 2');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (11,4,3,1,1,'FNCC82',36),
	    (11,5,3,1,4,'SNKP30',46);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(11,NULL,NULL,NULL,NULL,'SNKP30',2,4,1),
		(11,NULL,NULL,NULL,NULL,'FNCC82',2,4,1),
		(11,NULL,NULL,NULL,NULL,'FNCC82',1,4,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(11, 1, 4, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(11, 1, 5, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(11, 1, 6, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(11, 2, 12, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(11, 2, 13, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(11, 2, 15, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 12
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(12, '12', '30.0', '2024-11-24 06:07:45', 2, 2, 1, 'Vehículo menor pierde el control y choca contra barrera de contención.', 2, 1, 3, 5, 12, 1, 2, 1, 1, 1, 2, 9, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 58', NULL, 'No hubo', 'Patrulla 10', 'Operador 4', 'Supervisor 3');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (12,4,3,1,1,'WLMR55',35),
	    (12,5,3,1,4,'MBFT92',35);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(12,NULL,NULL,NULL,NULL,'MBFT92',2,3,1),
		(12,NULL,NULL,NULL,NULL,'WLMR55',2,2,1),
		(12,NULL,NULL,NULL,NULL,'WLMR55',1,3,1);

		--Incidente 13
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(13, '13', '65.8', '2025-12-12 23:26:44', 2, 2, 1, 'Bus interurbano colisiona con automóvil particular en cruce no regulado.', 3, 1, 3, 5, 13, 1, 2, 1, 1, 1, 2, 8, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 59', NULL, 'No hubo', 'Patrulla 1', 'Operador 6', 'Supervisor 3');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (13,4,3,1,1,'FXRJ56',33),
	    (13,5,3,1,4,'JYKK66',43);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(13,NULL,NULL,NULL,NULL,'JYKK66',2,4,1),
		(13,NULL,NULL,NULL,NULL,'FXRJ56',2,2,1),
		(13,NULL,NULL,NULL,NULL,'FXRJ56',1,2,1);

		--Incidente 14
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(14, '14', '95.6', '2025-04-09 08:23:43', 2, 2, 1, 'Colisión frontal entre automóvil y furgón en zona rural.', 4, 1, 3, 5, 4, 1, 2, 1, 1, 1, 2, 7, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 61', NULL, 'No hubo', 'Patrulla 3', 'Operador 10', 'Supervisor 2');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (14,4,3,1,1,'KWPG82',41),
	    (14,5,3,1,4,'LHFV62',41);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(14,NULL,NULL,NULL,NULL,'LHFV62',2,4,1),
		(14,NULL,NULL,NULL,NULL,'KWPG82',2,3,1),
		(14,NULL,NULL,NULL,NULL,'KWPG82',1,2,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(14, 1, 4, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(14, 1, 5, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(14, 1, 6, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(14, 2, 12, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(14, 2, 13, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(14, 2, 15, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 15
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(15, '15', '10.1', '2024-03-21 13:44:09', 2, 2, 1, 'Choque lateral entre bus y automóvil al intentar cambio de pista.', 5, 1, 3, 5, 15, 1, 2, 1, 1, 1, 2, 6, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 63', NULL, 'No hubo', 'Patrulla 3', 'Operador 9', 'Supervisor 3');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (15,4,3,1,1,'LZLP29',41),
	    (15,5,3,1,4,'HJMF34',33);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(15,NULL,NULL,NULL,NULL,'HJMF34',2,2,1),
		(15,NULL,NULL,NULL,NULL,'LZLP29',2,4,1),
		(15,NULL,NULL,NULL,NULL,'LZLP29',1,4,1);

		--Incidente 16
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(16, '16', '10.1', '2025-03-11 04:41:00', 2, 2, 1, 'Camioneta impacta a motocicleta al no respetar señal de ceda el paso.', 6, 1, 3, 5, 16, 1, 2, 1, 1, 1, 2, 5, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 62', NULL, 'No hubo', 'Patrulla 10', 'Operador 7', 'Supervisor 3');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (16,4,3,1,1,'LTCB63',36),
	    (16,5,3,1,4,'ZTPB50',47);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(16,NULL,NULL,NULL,NULL,'ZTPB50',2,2,1),
		(16,NULL,NULL,NULL,NULL,'LTCB63',2,2,1),
		(16,NULL,NULL,NULL,NULL,'LTCB63',1,4,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(16, 1, 1, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(16, 1, 2, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(16, 1, 3, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(16, 2, 9, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(16, 2, 11, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(16, 2, 12, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 17
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(17, '17', '88.4', '2025-02-08 05:42:27', 2, 2, 1, 'Colisión múltiple de tres vehículos debido a frenado brusco en carretera.', 7, 1, 3, 5, 17, 1, 2, 1, 1, 1, 2, 4, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 43', NULL, 'No hubo', 'Patrulla 5', 'Operador 3', 'Supervisor 5');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (17,4,3,1,1,'LNSC96',35),
	    (17,5,3,1,4,'SDTB32',36);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(17,NULL,NULL,NULL,NULL,'SDTB32',2,3,1),
		(17,NULL,NULL,NULL,NULL,'LNSC96',2,2,1),
		(17,NULL,NULL,NULL,NULL,'LNSC96',1,4,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(17, 1, 4, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(17, 1, 5, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(17, 1, 6, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(17, 2, 12, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(17, 2, 13, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(17, 2, 15, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 18
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(18, '18', '91.7', '2024-07-25 19:05:45', 2, 2, 1, 'Atropello de peatón en paso no habilitado, con lesiones graves.', 8, 1, 3, 5, 18, 1, 2, 1, 1, 1, 2, 3, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 61', NULL, 'No hubo', 'Patrulla 4', 'Operador 3', 'Supervisor 4');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (18,4,3,1,1,'ZSDK71',50),
	    (18,5,3,1,4,'SFMJ22',45);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(18,NULL,NULL,NULL,NULL,'SFMJ22',2,4,1),
		(18,NULL,NULL,NULL,NULL,'ZSDK71',2,2,1),
		(18,NULL,NULL,NULL,NULL,'ZSDK71',1,2,1);

		--Incidente 19
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(19, '19', '15.6', '2024-09-02 13:26:04', 2, 2, 1, 'Vehículo menor pierde el control y choca contra barrera de contención.', 9, 1, 3, 5, 19, 1, 2, 1, 1, 1, 2, 1, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 75', NULL, 'No hubo', 'Patrulla 15', 'Operador 10', 'Supervisor 4');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (19,4,3,1,1,'ZDCG50',45),
	    (19,5,3,1,4,'XLSR78',41);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(19,NULL,NULL,NULL,NULL,'XLSR78',2,3,1),
		(19,NULL,NULL,NULL,NULL,'ZDCG50',2,2,1),
		(19,NULL,NULL,NULL,NULL,'ZDCG50',1,3,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(19, 1, 1, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(19, 1, 2, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(19, 1, 3, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(19, 2, 9, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(19, 2, 11, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(19, 2, 12, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		--Incidente 20
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(20, '20', '53.4', '2024-05-07 00:47:27', 2, 2, 1, 'Vehículo menor pierde el control y choca contra barrera de contención.', 10, 1, 3, 5, 20, 1, 2, 1, 1, 1, 2, 2, 'No existe denuncia', 'Comisaría Central', NULL, NULL, 'Nº 28', NULL, 'No hubo', 'Patrulla 2', 'Operador 2', 'Supervisor 3');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (20,4,3,1,1,'WXZK89',36),
	    (20,5,3,1,4,'DGGS94',41);

	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(20,NULL,NULL,NULL,NULL,'DGGS94',2,2,1),
		(20,NULL,NULL,NULL,NULL,'WXZK89',2,4,1),
		(20,NULL,NULL,NULL,NULL,'WXZK89',1,3,1);
		
		INSERT INTO backoffice.support_services(incident_id, type_id, subtype_id, arrival_time, comment, quantity, departure_time) VALUES
		(20, 1, 4, '2025-02-10 03:01:29', 'Evaluación y traslado de accidentados', 3, '2025-02-10 03:05:29'),
		(20, 1, 5, '2025-02-10 03:01:29', 'Procedimiento policial', 0, '2025-02-10 03:15:29'),
		(20, 1, 6, '2025-02-10 03:01:29', 'Rescate y apoyo a accidentados', 0, '2025-02-10 03:22:29'),
		(20, 2, 12, '2025-02-10 03:10:00', 'Apoyo señalización y traslado de vehículos', 1, '2025-02-10 03:14:30'),
		(20, 2, 13, '2025-02-10 03:15:00', 'Señalización y recolección de información', 0, '2025-02-10 03:22:00'),
		(20, 2, 15, '2025-02-10 03:17:00', 'Apoyo señalización', 0, '2025-02-10 03:40:13');

		PERFORM setval('backoffice.incidents_id_seq', 20, true);
  END IF;
END $$;