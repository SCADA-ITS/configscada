/*delete from rt.transit_attachments;
delete from rt.transit_images;
delete from rt.transits;

INSERT INTO rt.transits (transit_id, transit_code, transit_type_id, dossier_code, remittance_code, installation_code, detection_point_code, date_transit, transit_state_id, transit_state_option_id, location_id, lane_number, element_type_id, element_id, certificate_valid_date, certificate_path, infraction_type_id, infraction_code, speed, distance, assigned_user_id, towards, address, locality, road, pk, direction, vehicle_type_id, vehicle_id, vehicle_brand_id, vehicle_model_id, vehicle_class_id, vehicle_country_id, vehicle_plate_number, vehicle_direction, vehicle_date_registration, vehicle_color, driver_type_id, driver_id, driver_license_type_id, driver_license_value, driver_license_issue_date, driver_license_expire_date, driver_name, driver_surname, driver_gender, driver_date_of_birth, driver_country_id, driver_state_id, driver_region_id, driver_locality_id, driver_address, enabled, visible, created_at, updated_at)	VALUES
(1739893658868, '001-20250515_105840_458-515110029', 1, NULL, NULL, 'LPR', 'LPR', '2025-09-06 12:14:09.000', 1, NULL, NULL, NULL, 99, 1, NULL, NULL, NULL, NULL, 90.0, NULL, 1000, 'C', 'CR. TEST, 8', 'Talluntxe', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 'B1111BB', true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, true, '2025-02-18 17:48:01.793', '2025-02-18 17:48:01.793'),
(1739893658864, '001-20250515_105912_884-515110064', 1, NULL, NULL, 'LPR', 'LPR', '2025-09-07 12:14:13.000', 1, NULL, NULL, NULL, 99, 1, NULL, NULL, NULL, NULL, 120.0, NULL, 1000, 'C', 'CR. TEST, 8', 'Talluntxe', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '4231BBB', true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, true, '2025-02-18 17:48:01.793', '2025-02-18 17:48:01.793'),
(1739893658872, '001-20250515_105923_238-515110118', 1, NULL, NULL, 'LPR', 'LPR', '2025-09-05 12:14:02.000', 1, NULL, NULL, NULL, 99, 1, NULL, NULL, NULL, NULL, 60.0, NULL, 1000, 'C', 'CR. TEST, 8', 'Talluntxe', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '1234AAA', true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, true, '2025-02-18 17:48:01.793', '2025-04-07 16:05:42.582');
        
INSERT INTO rt.transit_attachments (transit_attachment_id, transit_id, "path", visible, created_at, updated_at) VALUES
(1739893658867, 1739893658864, 'CR. TEST, 8 C-20250217_121413_01854_001.txt', true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658871, 1739893658868, 'CR. TEST, 8 C-20250216_121409_01853_001.txt', true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658875, 1739893658872, 'CR. TEST, 8 C-20250215_121402_01852_001.txt', true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793');

INSERT INTO rt.transit_images (transit_image_id, transit_id, "path", read_only, visible, created_at, updated_at) VALUES
(1739893658873, 1739893658868, '001-20250515_105840_458-515110029_001.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658874, 1739893658868, '001-20250515_105840_458-515110029_002.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658875, 1739893658868, '001-20250515_105840_458-515110029_003.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658876, 1739893658868, '001-20250515_105840_458-515110029_004.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658869, 1739893658864, '001-20250515_105912_884-515110064_001.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658870, 1739893658864, '001-20250515_105912_884-515110064_002.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658871, 1739893658864, '001-20250515_105912_884-515110064_003.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658872, 1739893658864, '001-20250515_105912_884-515110064_004.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658865, 1739893658872, '001-20250515_105923_238-515110118_001.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658866, 1739893658872, '001-20250515_105923_238-515110118_002.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658867, 1739893658872, '001-20250515_105923_238-515110118_003.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793'),
(1739893658868, 1739893658872, '001-20250515_105923_238-515110118_004.jpg', true, true, '2025-02-18 16:48:01.793', '2025-02-18 16:48:01.793');*/
