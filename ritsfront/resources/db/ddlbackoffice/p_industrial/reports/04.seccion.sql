/*CREATE VIEW reports.minutos_sec AS
SELECT 
    -- Identificador de grupo de carriles
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Grupo_1y2'
        WHEN NroCarril IN (3, 4) THEN 'Grupo_3y4'
    END AS GrupoCarril,
    Fecha,
    Hora,
    -- Sumas o promedios de variables relevantes
    SUM(Volumen) AS VolumenTotal,
    SUM(Speed * Volumen) / NULLIF(SUM(Volumen), 0) AS VelocidadMedia,
    SUM(Categoria1) AS Categoria1,
    SUM(Categoria2) AS Categoria2,
    SUM(Categoria3) AS Categoria3,
    SUM(Categoria4) AS Categoria4,
    SUM(ErrorDatos) AS ErrorDatos,
    SUM(Occupancy) AS OccupancyTotal,
    SUM(Headway) AS HeadwayTotal,
    SUM(DistanciaMedia) AS DistanciaMediaTotal,
    SUM(Gap) AS GapTotal,
    SUM(Congestion) AS CongestionTotal,
    SUM(Slowdown) AS SlowdownTotal
FROM reports.minutos
WHERE NroCarril IN (1, 2, 3, 4)
GROUP BY 
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Grupo_1y2'
        WHEN NroCarril IN (3, 4) THEN 'Grupo_3y4'
    END,
    Fecha,
    Hora
ORDER BY Fecha DESC, Hora DESC;

CREATE VIEW reports.horas_sec AS
SELECT
    -- Definimos el grupo de carriles
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Grupo_1y2'
        WHEN NroCarril IN (3, 4) THEN 'Grupo_3y4'
    END AS GrupoCarril,
    MIN(FechaHora) AS FechaHora,
    Fecha,
    Hora,
    IDEquipo AS IDEquipo,  
    -- Agregaciones
    SUM(Volumen) AS Volumen,
    SUM(Speed * Volumen) / NULLIF(SUM(Volumen), 0) AS Speed,
    SUM(Categoria1) AS Categoria1,
    SUM(Categoria2) AS Categoria2,
    SUM(Categoria3) AS Categoria3,
    SUM(Categoria4) AS Categoria4
FROM reports.horas
WHERE NroCarril IN (1, 2, 3, 4)
GROUP BY
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Grupo_1y2'
        WHEN NroCarril IN (3, 4) THEN 'Grupo_3y4'
    END,
    Fecha,
    Hora
ORDER BY Fecha DESC, Hora DESC;

CREATE VIEW reports.dias_sec AS
SELECT
    -- Definimos los grupos de carriles
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Grupo_1y2'
        WHEN NroCarril IN (3, 4) THEN 'Grupo_3y4'
    END AS GrupoCarril,
    Fecha,
    IDEquipo AS IDEquipo, 
    -- Agregaciones
    SUM(Volumen) AS Volumen,
    SUM(Speed * Volumen) / NULLIF(SUM(Volumen), 0) AS Speed,
    SUM(Categoria1) AS Categoria1,
    SUM(Categoria2) AS Categoria2,
    SUM(Categoria3) AS Categoria3,
    SUM(Categoria4) AS Categoria4
FROM reports.dias
WHERE NroCarril IN (1, 2, 3, 4)
GROUP BY 
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Grupo_1y2'
        WHEN NroCarril IN (3, 4) THEN 'Grupo_3y4'
    END,
    Fecha
ORDER BY Fecha DESC;

   
CREATE VIEW reports.meses_sec AS
SELECT
    -- Definimos el grupo de carriles
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Grupo_1y2'
        WHEN NroCarril IN (3, 4) THEN 'Grupo_3y4'
    END AS GrupoCarril,
    Anio,
    Mes,
    IDEquipo AS IDEquipo,
    -- Agregaciones
    SUM(Volumen) AS Volumen,
    SUM(Speed * Volumen) / NULLIF(SUM(Volumen), 0) AS Speed,
    SUM(Categoria1) AS Categoria1,
    SUM(Categoria2) AS Categoria2,
    SUM(Categoria3) AS Categoria3,
    SUM(Categoria4) AS Categoria4

FROM reports.meses
WHERE NroCarril IN (1, 2, 3, 4)
GROUP BY 
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Grupo_1y2'
        WHEN NroCarril IN (3, 4) THEN 'Grupo_3y4'
    END,
    Anio,
    Mes
ORDER BY Anio DESC, Mes DESC;*/