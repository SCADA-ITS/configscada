--copiar el archivo en la carpeta ddl y ejecutar make_param.sh.
--con esto creamos la tabla para recoger los datos de la simulacion de la etd_kineo.
--IMPORTANTE!! despues de usar el simulador borrar de ddl el archivo.
--IMPORTANTE!! No subir al repo el archivo en ddl.

CREATE TABLE conf.simulador_etd(
    CARRIL Int8 NULL,
    DATEREG timestamp NOT NULL,
    PERIOD Int8 NULL,
    VOLUME Int8 NULL,
    OCCUPANCY Int8 NULL,
    SPEED Int8 NULL,
    HEADWAY Int8 NULL,
    GAP Int8 NULL,
    WEIGHT Int8 NULL,
    CONGESTION Int8 NULL,
    WRONGWAY Int8 NULL,
    REVERSEDIRECTION Int8 NULL,
    LENGTH Int8 NULL,
    VOLUME0 Int8 NULL,
    VOLUME1 Int8 NULL,
    VOLUME2 Int8 NULL,
    VOLUME3 Int8 NULL,
    VOLUME4 Int8 NULL,
    VOLUME5 Int8 NULL,
    VOLUME6 Int8 NULL,
    SPEED0 Int8 NULL,
    SPEED1 Int8 NULL,
    SPEED2 Int8 NULL,
    ERRORDATOS Int8 NULL
);

INSERT INTO conf.simulador_etd(CARRIL, DATEREG, PERIOD, VOLUME, OCCUPANCY, SPEED, HEADWAY, GAP, WEIGHT, CONGESTION, WRONGWAY, REVERSEDIRECTION, LENGTH, VOLUME0, 
VOLUME1, VOLUME2, VOLUME3, VOLUME4, VOLUME5, VOLUME6, SPEED0, SPEED1, SPEED2, ERRORDATOS) VALUES
(1,CURRENT_TIMESTAMP, 1, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
