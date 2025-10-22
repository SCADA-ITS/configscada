/*CREATE VIEW reports.minutos AS  
WITH Carriles AS (  
    SELECT 1 AS NroCarril UNION ALL  
    SELECT 2 UNION ALL  
    SELECT 3 UNION ALL  
    SELECT 4  
),  
FechasHorasValidas AS (  
    SELECT   
        DISTINCT CAST(Fecha AS DATE) AS Fecha  
    FROM PuenteIndustrial.dbo.Agrupados  
),  
Minutos AS (  
    SELECT Hora FROM MinutosDelDia  
),  
LimiteHora AS (  
    SELECT CAST(DATEADD(MINUTE, -2, DATEADD(MINUTE, DATEDIFF(MINUTE, 0, GETDATE()), 0)) AS TIME(0)) AS HoraLimite  
),  
Combinaciones AS (  
    SELECT   
        f.Fecha,  
        m.Hora,  
        c.NroCarril  
    FROM FechasHorasValidas f  
    CROSS JOIN Minutos m  
    CROSS JOIN Carriles c  
    CROSS JOIN LimiteHora l  
    WHERE m.Hora <= l.HoraLimite  
),  
AgrupadosExtendido AS (  
    SELECT   
        a.IDAgrupados,
        a.IDEquipo,
        a.NroCarril, 
        a.Fecha,  
        a.Hora,
        a.Periodo,  
        a.Volumen,  
        a.Occupancy,  
        a.Speed,  
        a.Headway,  
        a.DistanciaMedia,  
        a.Gap,  
        a.Congestion,  
        a.Slowdown,  
        a.ReverseDirection,  
        a.Length,  
        a.Consolidated,  
        a.Categoria1,  
        a.Categoria2,  
        a.Categoria3,  
        a.Categoria4,  
        a.Categoria5,  
        a.Categoria6,  
        a.Categoria7,  
        a.Categoria8,  
        a.Categoria9,  
        a.Categoria10,  
        a.Categoria11,  
        a.Categoria12,  
        a.Speed1,  
        a.Speed2,  
        a.Speed3,  
        a.Speed4,  
        a.ErrorDatos,  
        a.Dateadd  
    FROM PuenteIndustrial.dbo.Agrupados a  
)  
SELECT  
 	ROW_NUMBER() OVER (ORDER BY c.Fecha DESC , c.Hora DESC , c.NroCarril ASC) AS id,
    ISNULL(a.IDEquipo, 0) AS IDEquipo, 
    c.NroCarril,
    CONVERT(VARCHAR(50), c.Fecha, 120) AS Fecha,  
    CONVERT(VARCHAR(50), c.Hora, 108) AS Hora,  
    ISNULL(a.Periodo, 0) AS Periodo,  
    ISNULL(a.Volumen, 0) AS Volumen,  
    ISNULL(a.Occupancy, 0) AS Occupancy,  
    ISNULL(a.Speed, 0) AS Speed,  
    ISNULL(a.Headway, 0) AS Headway,  
    ISNULL(a.DistanciaMedia, 0) AS DistanciaMedia,  
    ISNULL(a.Gap, 0) AS Gap,  
    ISNULL(a.Congestion, 0) AS Congestion,  
    ISNULL(a.Slowdown, 0) AS Slowdown,  
    ISNULL(a.ReverseDirection, 0) AS ReverseDirection,  
    ISNULL(a.Length, 0) AS Length,  
    ISNULL(a.Consolidated, 0) AS Consolidated,  
    ISNULL(a.Categoria1, 0) + ISNULL(a.Categoria2, 0) AS Categoria1, 
    ISNULL(a.Categoria3, 0) + ISNULL(a.Categoria2, 0) AS Categoria2, 
    ISNULL(a.Categoria4, 0) + ISNULL(a.Categoria5, 0) + ISNULL(a.Categoria6, 0) + 
    ISNULL(a.Categoria7, 0) + ISNULL(a.Categoria8, 0) + ISNULL(a.Categoria9, 0) + 
    ISNULL(a.Categoria10, 0) + ISNULL(a.Categoria11, 0) AS Categoria3,   
    ISNULL(a.Categoria12, 0) AS Categoria4,   
    ISNULL(a.Speed1, 0) AS Speed1,  
    ISNULL(a.Speed2, 0) AS Speed2,  
    ISNULL(a.Speed3, 0) AS Speed3,  
    ISNULL(a.Speed4, 0) AS Speed4,  
    ISNULL(a.ErrorDatos, 0) AS ErrorDatos,  
    ISNULL(a.Dateadd, CAST(CONVERT(VARCHAR(10), c.Fecha, 120) + ' ' + CONVERT(VARCHAR(8), c.Hora, 108) AS DATETIME2(0))) AS Dateadd  
FROM Combinaciones c  
LEFT JOIN AgrupadosExtendido a  
    ON a.Fecha = c.Fecha AND a.Hora = c.Hora AND a.NroCarril = c.NroCarril;

CREATE VIEW reports.horas AS
WITH Datos AS (
    SELECT
        a.IDEquipo,
        a.NroCarril,
        CAST(a.Fecha AS DATE) AS Fecha,
        DATEPART(HOUR, a.Hora) AS HoraNumero,
        DATEADD(HOUR, DATEPART(HOUR, a.Hora), CAST(a.Fecha AS DATETIME)) AS HoraInicio,
        a.Volumen,
        a.Speed,
        a.Categoria1, a.Categoria2, a.Categoria3, a.Categoria4,
        a.Categoria5, a.Categoria6, a.Categoria7, a.Categoria8,
        a.Categoria9, a.Categoria10, a.Categoria11, a.Categoria12
    FROM PuenteIndustrial.dbo.Agrupados a
)
SELECT
	ROW_NUMBER() OVER (ORDER BY Fecha DESC, HoraNumero DESC , NroCarril ASC) AS id,
    MIN(DATEADD(MINUTE, 0, HoraInicio)) AS FechaHora,  -- Inicio de la hora
    Fecha,
    HoraNumero AS Hora,
    NroCarril,
    IDEquipo,
    SUM(Volumen) AS Volumen,
    AVG(Speed) AS Speed,
    SUM(Categoria1) + SUM(Categoria2) AS Categoria1,
    SUM(Categoria3) AS Categoria2,
    SUM(Categoria4) + SUM(Categoria5) + SUM(Categoria6) + SUM(Categoria7) + 
    SUM(Categoria8) + SUM(Categoria9) + SUM(Categoria10) + SUM(Categoria11) AS Categoria3, 
    SUM(Categoria12) AS Categoria4 
FROM Datos
GROUP BY
    Fecha,
    HoraNumero,
    HoraInicio,
    NroCarril,
    IDEquipo;
   
CREATE VIEW reports.dias AS
WITH Datos AS (
    SELECT
        a.IDEquipo,
        a.NroCarril,
        CAST(a.Fecha AS DATE) AS Fecha,
        a.Volumen,
        a.Speed,
        a.Categoria1, a.Categoria2, a.Categoria3, a.Categoria4,
        a.Categoria5, a.Categoria6, a.Categoria7, a.Categoria8,
        a.Categoria9, a.Categoria10, a.Categoria11, a.Categoria12
    FROM PuenteIndustrial.dbo.Agrupados a
)
SELECT
	ROW_NUMBER() OVER (ORDER BY Fecha DESC , NroCarril ASC) AS id,
    Fecha,
    NroCarril,
    IDEquipo,
    SUM(Volumen) AS Volumen,
    AVG(Speed) AS Speed,
    -- Categorías agrupadas
    SUM(Categoria1) + SUM(Categoria2) AS Categoria1,
    SUM(Categoria3) AS Categoria2,
    SUM(Categoria4) + SUM(Categoria5) + SUM(Categoria6) + SUM(Categoria7)
        + SUM(Categoria8) + SUM(Categoria9) + SUM(Categoria10) + SUM(Categoria11) AS Categoria3,
    SUM(Categoria12) AS Categoria4
FROM Datos
GROUP BY
    Fecha,
    NroCarril,
    IDEquipo;

   
CREATE VIEW reports.meses AS
WITH Datos AS (
    SELECT
    	a.IDEquipo,
        a.NroCarril,
        CAST(a.Fecha AS DATE) AS Fecha,
        YEAR(a.Fecha) AS Anio,
        MONTH(a.Fecha) AS Mes,
        a.Volumen,
        a.Speed,
        a.Categoria1, a.Categoria2, a.Categoria3, a.Categoria4,
        a.Categoria5, a.Categoria6, a.Categoria7, a.Categoria8,
        a.Categoria9, a.Categoria10, a.Categoria11, a.Categoria12
    FROM PuenteIndustrial.dbo.Agrupados a
)
SELECT
	ROW_NUMBER() OVER (ORDER BY Anio DESC ,  Mes DESC , NroCarril ASC) AS id,
    Anio,
    Mes,
    NroCarril,
    IDEquipo,
    SUM(Volumen) AS Volumen,
    AVG(Speed) AS Speed,
    -- Categorías agrupadas
    SUM(Categoria1) + SUM(Categoria2) AS Categoria1,
    SUM(Categoria3) AS Categoria2,
    SUM(Categoria4) + SUM(Categoria5) + SUM(Categoria6) + SUM(Categoria7)
        + SUM(Categoria8) + SUM(Categoria9) + SUM(Categoria10) + SUM(Categoria11) AS Categoria3,
    SUM(Categoria12) AS Categoria4
FROM Datos
GROUP BY
    Anio,
    Mes,
    NroCarril,
    IDEquipo;*/