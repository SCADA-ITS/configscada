DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
		
		INSERT INTO backoffice.incidents
		(id, incident_report_id, km, "date", event_id, side_id, stretch_id, "comments", event_type_id, collision_id, object_crash_id, vehicle_crash_id, relative_location_id, road_condition_id, road_type_id, road_state_id, weather_state_id, luminosity_id, ligth_id, cause_id, report_by, stamped, court, citattion_date, report_number, station, file_number, patrol_name, operator_name, supervisor_name)
		VALUES(1, '1', '25.4', '2025-08-21 19:04:00.000', 2, 2, 1, 'Camioneta la cual circulaba de poniente a oriente, al llegar al kilómetro 25,480 es colisionada de manera frontal por automóvil, el cual circulaba en sentido contrario y que habría efectuado maniobra de adelantamiento provocando accidente. Conductor de la camioneta resulta con lesiones menos graves, en tanto conductora y acompañante del automóvil resultan con lesiones graves.', 2, 1, 3, 5, 1, 1, 2, 1, 1, 1, 2, 1, 'No existe denuncia', 'Maitencito', NULL, NULL, 'Nº 66', NULL, 'No hubo', 'Felipe Álvarez Jeria - José Miranda Delgado', 'Leandro Navia López', 'Leandro Navia López');

		INSERT INTO backoffice.vehicles (incident_id,vehicle_type_id,vehicle_service_id,vehicle_consecuence_id,vehicle_manoeuvre_id,patent,vehicle_brand_id) VALUES
	    (1,4,3,1,1,'KSXP*18',39),
	    (1,5,3,1,4,'SRHC*93',38);
	    
	    INSERT INTO backoffice.participants (incident_id,participant_name,participant_rut,participant_age,participant_address,participant_vehicle,participant_type_id,participant_injury_id,participant_nacionality_id) VALUES
		(1,NULL,NULL,NULL,NULL,'SRHC*93',2,3,1),
		(1,NULL,NULL,NULL,NULL,'KSXP*18',2,4,1),
		(1,NULL,NULL,NULL,NULL,'KSXP*18',1,4,1);
		
		INSERT INTO backoffice.closures (incident_id,side_road,lanes,entity,reasons,start_hour,end_hour,total_time) VALUES
	 	(1,'Izquierdo / Derecho','1 y 2','Carabineros Maitencillo','Para trabajos de equipos de Emergencias, Ambulancia, Bomberos y Carabineros.','2025-08-26 17:10:41+02','2025-08-26 19:15:41+02','2:05');

	 	INSERT INTO backoffice.support_services (incident_id,type_id,subtype_id,arrival_time,"comment",quantity,departure_time) VALUES
		(1,1,1,'2025-08-26 16:55:41+02','Evaluación y traslado  de accidentados ',3,'2025-08-26 18:00:41+02'),
		(1,1,2,'2025-08-26 16:55:41+02','Procedimiento policial',NULL,'2025-08-26 18:50:41+02'),
		(1,1,3,'2025-08-26 16:55:41+02','Rescate y apoyo a accidentados',NULL,'2025-08-26 18:15:41+02'),
		(1,2,9,'2025-08-26 18:05:41+02','Apoyo señalización y traslado de vehículos',1,'2025-08-26 19:00:41+02'),
		(1,2,11,'2025-08-26 17:10:41+02','Señalización y recolección de información',NULL,'2025-08-26 19:30:41+02'),
		(1,2,12,'2025-08-26 17:15:41+02','Apoyo señalización',NULL,'2025-08-26 19:30:41+02');
		
		INSERT INTO backoffice.damages_evaluations (incident_id,description,quantity,entity) VALUES
	 	(1,'Barrera de contención',5,'metros');
		
	 	INSERT INTO backoffice.damages_responsabilities (incident_id,address,phone_number,vehicle_owner_id,owner_name) VALUES
	 	(1,'Calle Tortuga, 1','+56 666-666-666',1,NULL);
	 	
	 	INSERT INTO backoffice.sg_metadata_table_images (sg_metadata_table_id,id_value,group_name,file_name,description,"position") VALUES
		 (1,1,'photos','foto1.jpg',NULL,1),
		 (1,1,'photos','foto2.jpg',NULL,2),
		 (1,1,'sketch','esquema.jpg',NULL,1);
	END IF;
END $$;