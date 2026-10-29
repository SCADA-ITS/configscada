DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'backoffice') THEN
		
		INSERT INTO backoffice.event (alias) VALUES('Accidente');
		INSERT INTO backoffice.event (alias) VALUES('Incidente');

		INSERT INTO backoffice.comunne (alias) VALUES('Nogales');
		INSERT INTO backoffice.comunne (alias) VALUES('Puchuncaví');
		
		INSERT INTO backoffice.side (alias) VALUES('Derecha');
		INSERT INTO backoffice.side (alias) VALUES('Izquierda');
		INSERT INTO backoffice.side (alias) VALUES('Berma');
		INSERT INTO backoffice.side (alias) VALUES('Faja fiscal');
		
		INSERT INTO backoffice.stretch (alias) VALUES('Fresia');
		INSERT INTO backoffice.stretch (alias) VALUES('Las Romazas');
		INSERT INTO backoffice.stretch (alias) VALUES('Pasarela 1');
		INSERT INTO backoffice.stretch (alias) VALUES('Puente Pucalán');
		INSERT INTO backoffice.stretch (alias) VALUES('Huellacanal');
		INSERT INTO backoffice.stretch (alias) VALUES('Agrícola El Carmen');
		INSERT INTO backoffice.stretch (alias) VALUES('Enlace El Carmen');
		INSERT INTO backoffice.stretch (alias) VALUES('Pasarela 2');
		INSERT INTO backoffice.stretch (alias) VALUES('Punto de cobro 1');
		INSERT INTO backoffice.stretch (alias) VALUES('Pasarela 3');
		INSERT INTO backoffice.stretch (alias) VALUES('Enlace Cumbre');
		INSERT INTO backoffice.stretch (alias) VALUES('Lado poniente cuesta');
		INSERT INTO backoffice.stretch (alias) VALUES('Pasarela 4');
		INSERT INTO backoffice.stretch (alias) VALUES('Enlace Pucalán');
		INSERT INTO backoffice.stretch (alias) VALUES('Área de Emergencias');
		INSERT INTO backoffice.stretch (alias) VALUES('Estacionamiento camiones');
		INSERT INTO backoffice.stretch (alias) VALUES('La Estancilla');
		INSERT INTO backoffice.stretch (alias) VALUES('Pasarela 5');
		INSERT INTO backoffice.stretch (alias) VALUES('Enlace F-190');
		INSERT INTO backoffice.stretch (alias) VALUES('Puente Puchuncaví');
		INSERT INTO backoffice.stretch (alias) VALUES('Enlace Campiche');
		INSERT INTO backoffice.stretch (alias) VALUES('Quirilluca');
		INSERT INTO backoffice.stretch (alias) VALUES('Punto de cobro 2');
		INSERT INTO backoffice.stretch (alias) VALUES('Las Cañas');
		INSERT INTO backoffice.stretch (alias) VALUES('Ruperto Vinagre');
		INSERT INTO backoffice.stretch (alias) VALUES('Las Lomas');

		INSERT INTO backoffice.direction (alias) VALUES('Oriente/Poniente');
		INSERT INTO backoffice.direction (alias) VALUES('Poniente/Oriente');
		INSERT INTO backoffice.direction (alias) VALUES('Norte/Sur');
		INSERT INTO backoffice.direction (alias) VALUES('Sur/Norte');

		INSERT INTO backoffice.detection (alias) VALUES('Citofonía S.O.S');
		INSERT INTO backoffice.detection (alias) VALUES('Call center');
		INSERT INTO backoffice.detection (alias) VALUES('Teléfono directo');
		INSERT INTO backoffice.detection (alias) VALUES('Vigilancia');
		INSERT INTO backoffice.detection (alias) VALUES('Aviso en vías de cobro');
		INSERT INTO backoffice.detection (alias) VALUES('Reporte personal Conces');
		INSERT INTO backoffice.detection (alias) VALUES('Fuera de concesión');
		INSERT INTO backoffice.detection (alias) VALUES('Encargado emerg.comunal');
		INSERT INTO backoffice.detection (alias) VALUES('Carabineros');
		INSERT INTO backoffice.detection (alias) VALUES('Otro');

		INSERT INTO backoffice.notice (alias) VALUES('Si');
		INSERT INTO backoffice.notice (alias) VALUES('No');
		INSERT INTO backoffice.notice (alias) VALUES('Sin respuesta a llamado');
		INSERT INTO backoffice.notice (alias) VALUES('No aplica');

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
		
		INSERT INTO backoffice.relative_location(alias) VALUES('Troncal');
		INSERT INTO backoffice.relative_location(alias) VALUES('Calle de servicio');
		INSERT INTO backoffice.relative_location(alias) VALUES('Ramal entrada');
		INSERT INTO backoffice.relative_location(alias) VALUES('Ramal salida');
		INSERT INTO backoffice.relative_location(alias) VALUES('Paso inferior');
		INSERT INTO backoffice.relative_location(alias) VALUES('Calle local');
		INSERT INTO backoffice.relative_location(alias) VALUES('Paso superior');
		INSERT INTO backoffice.relative_location(alias) VALUES('Vías de cobro');
		INSERT INTO backoffice.relative_location(alias) VALUES('F-190');
		INSERT INTO backoffice.relative_location(alias) VALUES('Las Romazas');
		INSERT INTO backoffice.relative_location(alias) VALUES('Otra');
		INSERT INTO backoffice.relative_location(alias) VALUES('Cruce con semáforo funcionando');
		INSERT INTO backoffice.relative_location(alias) VALUES('Cruce con semáforo apagado');
		INSERT INTO backoffice.relative_location(alias) VALUES('Cruce regulado por carabineros');
		INSERT INTO backoffice.relative_location(alias) VALUES('Cruce con señal "Pare"');
		INSERT INTO backoffice.relative_location(alias) VALUES('Cruce con señal "Ceda el Paso"');
		INSERT INTO backoffice.relative_location(alias) VALUES('Cruce sin señalización');
		INSERT INTO backoffice.relative_location(alias) VALUES('Tramo de vía recta');
		INSERT INTO backoffice.relative_location(alias) VALUES('Tramo de vía curva horizontal');
		INSERT INTO backoffice.relative_location(alias) VALUES('Tramo de vía curva vertical');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Acera o berma');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Puente');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Calle de servicio');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Ramal');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Lazo ramal');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Enlace a nivel');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Enlace a desnivel');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Acceso no habilitado');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Rotonda');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Plaza de peaje');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Otros no considerados');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Área atención emergencia');
		-- INSERT INTO backoffice.relative_location(alias) VALUES('Plaza de pesaje');
  END IF;
END $$;
