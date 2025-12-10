ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_cause_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_cause_id FOREIGN KEY (cause_id) REFERENCES backoffice.causes(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_collision_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_collision_id FOREIGN KEY (collision_id) REFERENCES backoffice.collision(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_ligth_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_ligth_id FOREIGN KEY (ligth_id) REFERENCES backoffice.ligth(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_luminosity_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_luminosity_id FOREIGN KEY (luminosity_id) REFERENCES backoffice.luminosity(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_object_crash_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_object_crash_id FOREIGN KEY (object_crash_id) REFERENCES backoffice.object_crash(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_relative_location_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_relative_location_id FOREIGN KEY (relative_location_id) REFERENCES backoffice.relative_location(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_road_condition_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_road_condition_id FOREIGN KEY (road_condition_id) REFERENCES backoffice.road_conditions(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_road_state_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_road_state_id FOREIGN KEY (road_state_id) REFERENCES backoffice.road_states(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_road_type_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_road_type_id FOREIGN KEY (road_type_id) REFERENCES backoffice.road_types(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_type_event_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_type_event_id FOREIGN KEY (event_id) REFERENCES backoffice.event(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_type_event_type_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_type_event_type_id FOREIGN KEY (event_type_id) REFERENCES backoffice.event_type(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_type_side_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_type_side_id FOREIGN KEY (side_id) REFERENCES backoffice.side(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_type_stretch_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_type_stretch_id FOREIGN KEY (stretch_id) REFERENCES backoffice.stretch(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_vehicle_crash_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_vehicle_crash_id FOREIGN KEY (vehicle_crash_id) REFERENCES backoffice.vehicle_crash(id) ON DELETE RESTRICT;

ALTER TABLE backoffice.incidents DROP CONSTRAINT fk_weather_state_id;
ALTER TABLE backoffice.incidents ADD CONSTRAINT fk_weather_state_id FOREIGN KEY (weather_state_id) REFERENCES backoffice.weather_states(id) ON DELETE RESTRICT;