DO $$ 
BEGIN
  IF EXISTS (SELECT 1 FROM pg_namespace WHERE nspname = 'dgt_gip') THEN
INSERT INTO dgt_gip.transitos (conductor, marca_vehiculo, modelo_vehiculo, categoria_vehiculo, matricula, nacionalidad_vehiculo, itv, seguro, fecha)
VALUES 
('Juan Pérez Martínez', 'Toyota', 'Corolla', 'Turismo', '1234ABC', 'Española', '15/01/2025', 'AXA 20/04/2026', '01/01/2024'),
('Alberto González Fernández', 'Yamaha', 'MT-07', 'Moto', '2345HIJ', 'Española', '30/10/2025', 'AXA 01/12/2026', '30/10/2024'),
('Marina Ruiz García', 'Renault', 'Trafic', 'Furgoneta', '4567QRS', 'Española', '25/01/2025', 'Mapfre 30/03/2026', '25/01/2024'),
('María García López', 'Ford', 'Focus', 'Turismo', '5678DEF', 'Francesa', '20/02/2026', 'Allianz 25/04/2027', '15/02/2024'),
('Ana Martínez Rodríguez', 'Suzuki', 'GSX-R750', 'Moto', '6789KLM', 'Española', '05/11/2026', 'Generali 10/12/2027', '05/11/2024'),
('Carlos López Gómez', 'Volvo', 'FH16', 'Camión', '0123NOP', 'Sueca', '18/12/2027', 'Allianz 20/01/2029', '18/12/2024'),
('David Martínez Sánchez', 'Volkswagen', 'Golf', 'Turismo', '9012GHI', 'Española', '10/03/2027', 'Mapfre 30/04/2028', '10/03/2024'),
('Sara Gómez Pérez', 'BMW', 'Serie 3', 'Turismo', '9012VWX', 'Española', '30/08/2026', 'Generali 18/09/2027', '30/08/2024'),
('Mario López Martínez', 'Iveco', 'Stralis', 'Camión', '8901TUV', 'Española', '01/02/2026', 'AXA 05/03/2027', '01/02/2024'),
('Elena Martín López', 'Citroën', 'C4', 'Turismo', '7890BCD', 'Española', '05/11/2028', 'Allianz 07/12/2029', '05/11/2024'),
('Carlos Sánchez Fernández', 'Renault', 'Clio', 'Turismo', '7890MNO', 'Española', '20/05/2029', 'AXA 10/06/2030', '20/05/2024'),
('Ana López Rodríguez', 'Seat', 'Ibiza', 'Turismo', '3456JKL', 'Española', '02/04/2028', 'Generali 05/05/2029', '02/04/2024'),
('Javier Ruiz García', 'Audi', 'A3', 'Turismo', '3456YZA', 'Española', '12/10/2027', 'AXA 22/11/2028', '12/10/2024'),
('Marina Ruiz García', 'Renault', 'Trafic', 'Furgoneta', '5678KLM', 'Española', '05/11/2026', 'Generali 10/12/2027', '05/11/2024'),
('Pablo Fernández Martínez', 'Mercedes-Benz', 'Clase A', 'Turismo', '5678STU', 'Española', '25/07/2025', 'Mapfre 20/08/2026', '25/07/2024'),
('Rosa Torres Martínez', 'Volvo', 'S60', 'Turismo', '7890QRS', 'Española', '25/04/2025', 'Mapfre 30/05/2026', '25/04/2024'),
('Manuel López Martínez', 'Citroën', 'C3', 'Turismo', '3456CDE', 'Española', '10/08/2025', 'Mapfre 12/09/2026', '10/08/2024'),
('Diego Castro García', 'Mazda', '3', 'Turismo', '1234TUV', 'Española', '30/05/2026', 'Generali 05/06/2027', '30/05/2024'),
('Lorena Martínez Sánchez', 'Renault', 'Megane', 'Turismo', '9012ZAB', 'Española', '25/07/2028', 'Allianz 30/08/2029', '25/07/2024'),
('Jorge Gutiérrez Sánchez', 'Skoda', 'Octavia', 'Turismo', '3456NOP', 'Española', '18/03/2028', 'Allianz 20/04/2029', '18/03/2024'),
('Laura Rodríguez Gómez', 'Peugeot', '208', 'Turismo', '1234PQR', 'Española', '18/06/2030', 'Allianz 15/07/2031', '18/06/2024'),
('Luisa Serrano Martínez', 'Opel', 'Corsa', 'Turismo', '1234EFG', 'Española', '15/12/2025', 'Mapfre 18/01/2027', '15/12/2024'),
('Carmen Ruiz García', 'Kia', 'Rio', 'Turismo', '9012KLM', 'Española', '05/02/2027', 'AXA 10/03/2028', '05/02/2024'),
('Natalia García López', 'Fiat', '500', 'Turismo', '5678WXY', 'Española', '12/06/2027', 'AXA 15/07/2028', '12/06/2024'),
('Sandra Sánchez Rodríguez', 'Volkswagen', 'Polo', 'Turismo', '7890EFG', 'Española', '20/09/2026', 'Generali 22/10/2027', '20/09/2024'),
('Ana Sánchez Rodríguez', 'Honda', 'CBR600RR', 'Moto', '0123CDE', 'Española', '10/04/2028', 'AXA 12/05/2029', '10/04/2024'),
('Pedro Díaz Rodríguez', 'Hyundai', 'i30', 'Turismo', '5678HIJ', 'Coreana', '22/01/2026', 'Generali 25/02/2027', '22/01/2024'),
('Santiago Gómez Martínez', 'Kawasaki', 'Ninja ZX-10R', 'Moto', '8901FGH', 'Española', '15/11/2027', 'Mapfre 20/12/2028', '15/11/2024'),
('Laura Martín Sánchez', 'Peugeot', 'Boxer', 'Furgoneta', '2345IJK', 'Española', '20/12/2028', 'AXA 25/01/2030', '20/12/2024');

  END IF;
END $$;