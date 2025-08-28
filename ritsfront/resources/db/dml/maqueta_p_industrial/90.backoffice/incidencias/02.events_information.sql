DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
		
		INSERT INTO backoffice.event (alias) VALUES('Accidente');
		INSERT INTO backoffice.event (alias) VALUES('Incidente');
		
		INSERT INTO backoffice.side (alias) VALUES('Derecha');
		INSERT INTO backoffice.side (alias) VALUES('Izquierda');
		
		INSERT INTO backoffice.stretch (alias) VALUES('Troncal');
		INSERT INTO backoffice.stretch (alias) VALUES('Ramal');
		
		INSERT INTO backoffice.event_type (alias) VALUES('Atropello');
		INSERT INTO backoffice.event_type (alias) VALUES('Caída');
		INSERT INTO backoffice.event_type (alias) VALUES('Volcadura');
		INSERT INTO backoffice.event_type (alias) VALUES('Incencio vehicular');
		INSERT INTO backoffice.event_type (alias) VALUES('Incendio pastizal');
		INSERT INTO backoffice.event_type (alias) VALUES('Derrame');
		INSERT INTO backoffice.event_type (alias) VALUES('Asalto o robo');
		INSERT INTO backoffice.event_type (alias) VALUES('Apedreamiento');
		INSERT INTO backoffice.event_type (alias) VALUES('Sale de la vía');
		INSERT INTO backoffice.event_type (alias) VALUES('Otro');
		
		INSERT INTO backoffice.collision(alias) VALUES('Frontal');
		INSERT INTO backoffice.collision(alias) VALUES('Lateral');
		INSERT INTO backoffice.collision(alias) VALUES('Por alcance');
		INSERT INTO backoffice.collision(alias) VALUES('Perpendicular');
		INSERT INTO backoffice.collision(alias) VALUES('Impacto con animal');
		
		INSERT INTO backoffice.object_crash(alias) VALUES('Frontal');
		INSERT INTO backoffice.object_crash(alias) VALUES('Lateral');
		INSERT INTO backoffice.object_crash(alias) VALUES('Posterior');
		
		INSERT INTO backoffice.vehicle_crash(alias) VALUES('Frente/Frente');
		INSERT INTO backoffice.vehicle_crash(alias) VALUES('Frente/Lado');
		INSERT INTO backoffice.vehicle_crash(alias) VALUES('Frente/Posterior');
		INSERT INTO backoffice.vehicle_crash(alias) VALUES('Lado/Frente');
		INSERT INTO backoffice.vehicle_crash(alias) VALUES('Lado/Lado');
		INSERT INTO backoffice.vehicle_crash(alias) VALUES('Lado/Posterior');
		INSERT INTO backoffice.vehicle_crash(alias) VALUES('Posterior/Frente');
		INSERT INTO backoffice.vehicle_crash(alias) VALUES('Posterior/Lado');
		INSERT INTO backoffice.vehicle_crash(alias) VALUES('Posterior/Posterior');
		
		INSERT INTO backoffice.relative_location(alias) VALUES('Cruce con semáforo funcionando');
		INSERT INTO backoffice.relative_location(alias) VALUES('Cruce con semáforo apagado');
		INSERT INTO backoffice.relative_location(alias) VALUES('Cruce regulado por carabineros');
		INSERT INTO backoffice.relative_location(alias) VALUES('Cruce con señal "Pare"');
		INSERT INTO backoffice.relative_location(alias) VALUES('Cruce con señal "Ceda el Paso"');
		INSERT INTO backoffice.relative_location(alias) VALUES('Cruce sin señalización');
		INSERT INTO backoffice.relative_location(alias) VALUES('Tramo de vía recta');
		INSERT INTO backoffice.relative_location(alias) VALUES('Tramo de vía curva horizontal');
		INSERT INTO backoffice.relative_location(alias) VALUES('Tramo de vía curva vertical');
		INSERT INTO backoffice.relative_location(alias) VALUES('Acera o berma');
		INSERT INTO backoffice.relative_location(alias) VALUES('Puente');
		INSERT INTO backoffice.relative_location(alias) VALUES('Calle de servicio');
		INSERT INTO backoffice.relative_location(alias) VALUES('Ramal');
		INSERT INTO backoffice.relative_location(alias) VALUES('Lazo ramal');
		INSERT INTO backoffice.relative_location(alias) VALUES('Enlace a nivel');
		INSERT INTO backoffice.relative_location(alias) VALUES('Enlace a desnivel');
		INSERT INTO backoffice.relative_location(alias) VALUES('Acceso no habilitado');
		INSERT INTO backoffice.relative_location(alias) VALUES('Rotonda');
		INSERT INTO backoffice.relative_location(alias) VALUES('Plaza de peaje');
		INSERT INTO backoffice.relative_location(alias) VALUES('Otros no considerados');
		INSERT INTO backoffice.relative_location(alias) VALUES('Área atención emergencia');
		INSERT INTO backoffice.relative_location(alias) VALUES('Plaza de pesaje');
  END IF;
END $$;