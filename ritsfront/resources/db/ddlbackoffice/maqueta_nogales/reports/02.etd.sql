/*CREATE TABLE reports.sg_metadata_tables (
    id INT IDENTITY(1,1) NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL,
    label VARCHAR(255) NULL,
    label_singular VARCHAR(255) NULL,
    label_description VARCHAR(255) NULL,
    mdi_icon VARCHAR(255) NULL,
    support_images BIT NOT NULL,
    support_attachments BIT NOT NULL,
    sql_view VARCHAR(4000) NULL,
    grid_id INT NULL,
    metadata VARCHAR(MAX) NULL,
    CONSTRAINT pk_sg_metadata_tables PRIMARY KEY (id)
);

CREATE TABLE reports.sg_metadata_table_images (
	id INT IDENTITY(1,1) NOT NULL,
	sg_metadata_table_id int NOT NULL,
	id_value int NOT NULL,
	group_name VARCHAR(255) NOT NULL,
	file_name VARCHAR(255) NOT NULL,
	description VARCHAR(255) NULL,
	position int NOT NULL,
	CONSTRAINT pk_sg_metadata_table_images PRIMARY KEY (id)
);

CREATE INDEX idx_sg_metadata_table_images_sg_metadata_table_id ON reports.sg_metadata_table_images (sg_metadata_table_id);

ALTER TABLE reports.sg_metadata_table_images ADD CONSTRAINT fk_sg_metadata_table_images_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) REFERENCES reports.sg_metadata_tables(id);
	

CREATE TABLE reports.sg_metadata_table_attachments (
	id INT IDENTITY(1,1) NOT NULL,
	sg_metadata_table_id int NOT NULL,
	id_value int NOT NULL,
	group_name VARCHAR(255) NOT NULL,
	file_name VARCHAR(255) NOT NULL,
	description VARCHAR(255) NULL,
	position int NOT NULL,
	CONSTRAINT pk_sg_metadata_table_attachments PRIMARY KEY (id)
);

CREATE INDEX idx_sg_metadata_table_attachments_sg_metadata_table_id ON reports.sg_metadata_table_attachments (sg_metadata_table_id);

ALTER TABLE reports.sg_metadata_table_attachments ADD CONSTRAINT fk_sg_metadata_table_attachments_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) REFERENCES reports.sg_metadata_tables(id);


CREATE TABLE reports.sg_metadata_table_triggers (
	id INT IDENTITY(1,1) NOT NULL,
	sg_metadata_table_id int NOT NULL,
	name VARCHAR(255) NULL,
	groovy VARCHAR(255) NOT NULL,
	CONSTRAINT pk_sg_metadata_table_triggers PRIMARY KEY (id)
);

CREATE INDEX idx_sg_metadata_table_triggers_sg_metadata_table_id ON reports.sg_metadata_table_triggers (sg_metadata_table_id);

ALTER TABLE reports.sg_metadata_table_triggers ADD CONSTRAINT fk_sg_metadata_table_triggers_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) REFERENCES reports.sg_metadata_tables(id);


CREATE TABLE reports.sg_metadata_table_commands (
	id INT IDENTITY(1,1) NOT NULL,
	sg_metadata_table_id int NOT NULL,
	name VARCHAR(255) NOT NULL,
	label VARCHAR(255) NULL,
	label_description varchar NULL,
	mdi_icon varchar NULL,
	require_confirmation BIT NOT NULL,
	available_in_form BIT NOT NULL,
	available_in_table BIT NOT NULL,
	groovy VARCHAR(255) NOT NULL,
	custom_js VARCHAR(255) NULL,
	CONSTRAINT pk_sg_metadata_table_commands PRIMARY KEY (id)
);

CREATE INDEX idx_sg_metadata_table_commands_sg_metadata_table_id ON reports.sg_metadata_table_commands (sg_metadata_table_id);

ALTER TABLE reports.sg_metadata_table_commands ADD CONSTRAINT fk_sg_metadata_table_commands_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) REFERENCES reports.sg_metadata_tables(id);

ALTER TABLE reports.sg_metadata_table_commands ADD CONSTRAINT unique_sg_metadata_table_commands UNIQUE (sg_metadata_table_id, name);


CREATE TABLE reports.sg_metadata_columns (
	id INT IDENTITY(1,1) NOT NULL,
	sg_metadata_table_id int NOT NULL,
	column_name VARCHAR(255) NOT NULL,
	label VARCHAR(255) NULL,
	label_description VARCHAR(255) NULL,
	needs_translation BIT NOT NULL,
	metadata VARCHAR(MAX) null,
	ref_view_column_id int NULL,
	CONSTRAINT pk_sg_metadata_columns PRIMARY KEY (id)
);

CREATE INDEX idx_sg_metadata_columns_sg_metadata_table_id ON reports.sg_metadata_columns (sg_metadata_table_id);

ALTER TABLE reports.sg_metadata_columns ADD CONSTRAINT fk_sg_metadata_columns_sg_metadata_table_id FOREIGN KEY (sg_metadata_table_id) REFERENCES reports.sg_metadata_tables(id);

ALTER TABLE reports.sg_metadata_columns ADD CONSTRAINT fk_sg_metadata_columns_sg_metadata_column_id FOREIGN KEY (ref_view_column_id) REFERENCES reports.sg_metadata_columns(id);

ALTER TABLE reports.sg_metadata_columns ADD CONSTRAINT unique_sg_metadata_columns UNIQUE (sg_metadata_table_id, column_name);



CREATE TABLE reports.sg_metadata_column_fillers (
	id INT IDENTITY(1,1) NOT NULL,
	sg_metadata_column_id int NOT NULL,
	name VARCHAR(255) NULL,
	groovy VARCHAR(255) NOT NULL,
	CONSTRAINT pk_sg_metadata_column_fillers PRIMARY KEY (id)
);

CREATE INDEX idx_sg_metadata_column_fillers_sg_metadata_column_id ON reports.sg_metadata_column_fillers (sg_metadata_column_id);

ALTER TABLE reports.sg_metadata_column_fillers ADD CONSTRAINT fk_sg_metadata_column_fillers_sg_metadata_column_id FOREIGN KEY (sg_metadata_column_id) REFERENCES reports.sg_metadata_columns(id);


CREATE TABLE reports.sg_metadata_tasks (
	id INT IDENTITY(1,1) NOT NULL,
	name VARCHAR(255) NULL,
	cron_expression varchar NOT NULL,
	groovy VARCHAR(255) NOT NULL,
	params VARCHAR(255) NULL,
	CONSTRAINT pk_sg_metadata_tasks PRIMARY KEY (id)
);


CREATE TABLE reports.sg_i18n_labels (
	id INT IDENTITY(1,1) NOT NULL,
	locale_code VARCHAR(255) NOT NULL,
	label VARCHAR(255) NOT NULL,
	translation VARCHAR(255) NOT NULL,
	created_at datetimeoffset NOT NULL,
	updated_at datetimeoffset NOT NULL,
	CONSTRAINT pk_sg_i18n_labels PRIMARY KEY (id)
);
	
ALTER TABLE reports.sg_i18n_labels ADD CONSTRAINT unique_sg_i18n_labels UNIQUE (locale_code, label);*/



/*-- dbo.VistaAgrupados source

--SELECT OBJECT_DEFINITION(OBJECT_ID('dbo.VistaAgrupados')) AS ScriptVista;

--EXEC sp_helptext 'dbo.VistaAgrupados';


CREATE VIEW reports.minutos AS  
WITH Carriles AS (  
    SELECT 1 AS NroCarril UNION ALL  
    SELECT 2 UNION ALL  
    SELECT 3 UNION ALL  
    SELECT 4  
),  
FechasHorasValidas AS (  
    SELECT   
        DISTINCT CAST(Fecha AS DATE) AS Fecha  
    FROM Nogales.dbo.Agrupados  
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
        a.DistanciaMedia,  
        a.Congestion,  
        a.Slowdown,  
        a.ReverseDirection,  
        a.Length,  
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
    FROM Nogales.dbo.Agrupados a  
)  
SELECT  
 	ROW_NUMBER() OVER (ORDER BY c.Fecha DESC , c.Hora DESC , c.NroCarril ASC) AS id,
    ISNULL(a.IDEquipo, 0) AS IDEquipo, 
    c.NroCarril,
    CASE 
        WHEN c.NroCarril = 1 THEN 'Hacia San Pedro de la Paz Pista Lenta'
        WHEN c.NroCarril = 2 THEN 'Hacia San Pedro de la Paz Pista Rápida'
        WHEN c.NroCarril = 3 THEN 'Hacia Hualpén Pista Rápida'
        WHEN c.NroCarril = 4 THEN 'Hacia Hualpén Pista Lenta'
        ELSE 'Desconocido'
    END AS NombreCarril,
    CONVERT(VARCHAR(50), c.Fecha, 120) AS Fecha,  
    CONVERT(VARCHAR(50), c.Hora, 108) AS Hora,  
    ISNULL(a.Periodo, 0) AS Periodo,  
    ISNULL(a.Volumen, 0) AS Volumen,  
    ISNULL(a.Occupancy / 60000.0 * 100, 0) AS Occupancy,  
    ISNULL(a.Speed, 0) AS Speed,  
    ISNULL(a.DistanciaMedia, 0) AS DistanciaMedia,  
    ISNULL(a.Congestion, 0) AS Congestion,  
    ISNULL(a.Slowdown, 0) AS Slowdown,  
    ISNULL(a.ReverseDirection, 0) AS ReverseDirection,  
    ISNULL(a.Length, 0) AS Length,  
    ISNULL(a.Categoria1, 0) + ISNULL(a.Categoria2, 0) AS Categoria1, 
    ISNULL(a.Categoria3, 0) AS Categoria2, 
    ISNULL(a.Categoria4, 0) AS Categoria3, 
    ISNULL(a.Categoria5, 0) + ISNULL(a.Categoria6, 0) + 
    ISNULL(a.Categoria7, 0) + ISNULL(a.Categoria8, 0) AS Categoria4,  
    ISNULL(a.Categoria9, 0) AS Categoria5,  
    ISNULL(a.Categoria10, 0) + ISNULL(a.Categoria11, 0) AS Categoria6, 
    ISNULL(a.Categoria12, 0) AS Categoria7,  
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
	    CASE 
		   WHEN a.NroCarril = 1 THEN 'Hacia San Pedro de la Paz Pista Lenta'
		   WHEN a.NroCarril = 2 THEN 'Hacia San Pedro de la Paz Pista Rápida'
		   WHEN a.NroCarril = 3 THEN 'Hacia Hualpén Pista Rápida'
		   WHEN a.NroCarril = 4 THEN 'Hacia Hualpén Pista Lenta'
		   ELSE 'Desconocido'
		END AS NombreCarril,
        CONVERT(VARCHAR(50), a.Fecha, 120) AS Fecha,
        DATEPART(HOUR, a.Hora) AS HoraNumero,
        DATEADD(HOUR, DATEPART(HOUR, a.Hora), CAST(a.Fecha AS DATETIME)) AS HoraInicio,
        a.Volumen,
        a.Speed,
        a.Categoria1, a.Categoria2, a.Categoria3, a.Categoria4,
        a.Categoria5, a.Categoria6, a.Categoria7, a.Categoria8,
        a.Categoria9, a.Categoria10, a.Categoria11, a.Categoria12
    FROM Nogales.dbo.Agrupados a
)
SELECT
	ROW_NUMBER() OVER (ORDER BY Fecha DESC, HoraNumero DESC , NroCarril ASC) AS id,
    MIN(DATEADD(MINUTE, 0, HoraInicio)) AS FechaHora,  -- Inicio de la hora
    Fecha,
    HoraNumero AS Hora,
    NroCarril,
    NombreCarril,
    IDEquipo,
    SUM(Volumen) AS Volumen,
    AVG(Speed) AS Speed,
    SUM(Categoria1) + SUM(Categoria2) AS Categoria1, 
    SUM(Categoria3) AS Categoria2, 
    SUM(Categoria4) AS Categoria3, 
    SUM(Categoria5) + SUM(Categoria6) + 
    SUM(Categoria7) + SUM(Categoria8) AS Categoria4,  
    SUM(Categoria9) AS Categoria5,  
    SUM(Categoria10) + SUM(Categoria11) AS Categoria6, 
    SUM(Categoria12) AS Categoria7
FROM Datos
GROUP BY
    Fecha,
    HoraNumero,
    HoraInicio,
    NroCarril,
    NombreCarril,
    IDEquipo;
   
CREATE VIEW reports.dias AS
WITH Datos AS (
    SELECT
        a.IDEquipo,
        a.NroCarril,
		CASE 
		   WHEN NroCarril = 1 THEN 'Hacia San Pedro de la Paz Pista Lenta'
		   WHEN NroCarril = 2 THEN 'Hacia San Pedro de la Paz Pista Rápida'
		   WHEN NroCarril = 3 THEN 'Hacia Hualpén Pista Rápida'
		   WHEN NroCarril = 4 THEN 'Hacia Hualpén Pista Lenta'
		   ELSE 'Desconocido'
		END AS NombreCarril,
        CONVERT(VARCHAR(50), a.Fecha, 120) AS Fecha,
        a.Volumen,
        a.Speed,
        a.Categoria1, a.Categoria2, a.Categoria3, a.Categoria4,
        a.Categoria5, a.Categoria6, a.Categoria7, a.Categoria8,
        a.Categoria9, a.Categoria10, a.Categoria11, a.Categoria12
    FROM Nogales.dbo.Agrupados a
)
SELECT
	ROW_NUMBER() OVER (ORDER BY Fecha DESC , NroCarril ASC) AS id,
    Fecha,
    NroCarril,
    NombreCarril,
    IDEquipo,
    SUM(Volumen) AS Volumen,
    AVG(Speed) AS Speed,
    -- Categorías agrupadas
    SUM(Categoria1) + SUM(Categoria2) AS Categoria1, 
    SUM(Categoria3) AS Categoria2, 
    SUM(Categoria4) AS Categoria3, 
    SUM(Categoria5) + SUM(Categoria6) + 
    SUM(Categoria7) + SUM(Categoria8) AS Categoria4,  
    SUM(Categoria9) AS Categoria5,  
    SUM(Categoria10) + SUM(Categoria11) AS Categoria6, 
    SUM(Categoria12) AS Categoria7
FROM Datos
GROUP BY
    Fecha,
    NroCarril,
    NombreCarril,
    IDEquipo;

   
CREATE VIEW reports.meses AS
WITH Datos AS (
    SELECT
    	a.IDEquipo,
        a.NroCarril,
		CASE 
		   WHEN NroCarril = 1 THEN 'Hacia San Pedro de la Paz Pista Lenta'
		   WHEN NroCarril = 2 THEN 'Hacia San Pedro de la Paz Pista Rápida'
		   WHEN NroCarril = 3 THEN 'Hacia Hualpén Pista Rápida'
		   WHEN NroCarril = 4 THEN 'Hacia Hualpén Pista Lenta'
		   ELSE 'Desconocido'
		END AS NombreCarril,
        CONVERT(VARCHAR(50), a.Fecha, 120) AS Fecha,
        YEAR(a.Fecha) AS Anio,
        CASE MONTH(a.Fecha)
            WHEN 1 THEN 'Enero'
            WHEN 2 THEN 'Febrero'
            WHEN 3 THEN 'Marzo'
            WHEN 4 THEN 'Abril'
            WHEN 5 THEN 'Mayo'
            WHEN 6 THEN 'Junio'
            WHEN 7 THEN 'Julio'
            WHEN 8 THEN 'Agosto'
            WHEN 9 THEN 'Septiembre'
            WHEN 10 THEN 'Octubre'
            WHEN 11 THEN 'Noviembre'
            WHEN 12 THEN 'Diciembre'
        END AS Mes,
        a.Volumen,
        a.Speed,
        a.Categoria1, a.Categoria2, a.Categoria3, a.Categoria4,
        a.Categoria5, a.Categoria6, a.Categoria7, a.Categoria8,
        a.Categoria9, a.Categoria10, a.Categoria11, a.Categoria12
    FROM Nogales.dbo.Agrupados a
)
SELECT
	ROW_NUMBER() OVER (ORDER BY Anio DESC ,  Mes DESC , NroCarril ASC) AS id,
    Anio,
    Mes,
    NroCarril,
    NombreCarril,
    IDEquipo,
    SUM(Volumen) AS Volumen,
    AVG(Speed) AS Speed,
    -- Categorías agrupadas
    SUM(Categoria1) + SUM(Categoria2) AS Categoria1, 
    SUM(Categoria3) AS Categoria2, 
    SUM(Categoria4) AS Categoria3, 
    SUM(Categoria5) + SUM(Categoria6) + 
    SUM(Categoria7) + SUM(Categoria8) AS Categoria4,  
    SUM(Categoria9) AS Categoria5,  
    SUM(Categoria10) + SUM(Categoria11) AS Categoria6, 
    SUM(Categoria12) AS Categoria7
FROM Datos
GROUP BY
    Anio,
    Mes,
    NroCarril,
	NombreCarril,
    IDEquipo;


















CREATE VIEW reports.minutos_sec AS
SELECT 
    ROW_NUMBER() OVER (ORDER BY Fecha DESC , Hora DESC) AS id,
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Sentido Nogales'
        WHEN NroCarril IN (3, 4) THEN 'Sentido Puchuncaví'
    END AS GrupoCarril,
    Fecha,
    Hora,
    -- Sumas o promedios de variables relevantes
	SUM(CAST(Volumen AS BIGINT)) AS Volumen,
    CASE
	    WHEN SUM(Volumen) = 0 THEN 0
    	ELSE SUM(Speed * Volumen) / NULLIF(SUM(Volumen), 0)
	END AS VelocidadMedia,
    CASE
	    WHEN SUM(Volumen) = 0 THEN 0
    	ELSE SUM(Length * Volumen) / NULLIF(SUM(Volumen), 0)
	END AS LongituddMedia,
    SUM(CAST(Categoria1 AS BIGINT)) AS Categoria1,
    SUM(CAST(Categoria2 AS BIGINT)) AS Categoria2,
    SUM(CAST(Categoria3 AS BIGINT)) AS Categoria3,
    SUM(CAST(Categoria4 AS BIGINT)) AS Categoria4,
    SUM(CAST(Categoria5 AS BIGINT)) AS Categoria5,
    SUM(CAST(Categoria6 AS BIGINT)) AS Categoria6,
    SUM(CAST(Categoria7 AS BIGINT)) AS Categoria7,
    MAX(CASE WHEN ErrorDatos = 1 THEN 1 ELSE 0 END) AS ErrorDatos,
    CASE
	    WHEN SUM(Volumen) = 0 THEN 0
    	ELSE SUM(Occupancy * Volumen) / NULLIF(SUM(Volumen), 0)
	END AS Occupancy,
    CASE
	    WHEN SUM(Volumen) = 0 THEN 0
    	ELSE SUM(DistanciaMedia * Volumen) / NULLIF(SUM(Volumen), 0)
	END AS DistanciaMedia,
    MAX(CASE WHEN Congestion = 1 THEN 1 ELSE 0 END) AS Congestion,
    MAX(CASE WHEN Slowdown = 1 THEN 1 ELSE 0 END) AS Slowdown
FROM reports.minutos
WHERE NroCarril IN (1, 2, 3, 4)
GROUP BY 
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Sentido Nogales'
        WHEN NroCarril IN (3, 4) THEN 'Sentido Puchuncaví'
    END,
    Fecha,
    Hora;



CREATE VIEW reports.horas_sec AS
SELECT
    ROW_NUMBER() OVER (ORDER BY Fecha DESC , Hora DESC) AS id,
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Sentido Nogales'
        WHEN NroCarril IN (3, 4) THEN 'Sentido Puchuncaví'
    END AS GrupoCarril,
    Fecha,
    Hora,
    -- Agregaciones
    SUM(Volumen) AS Volumen,
    CASE
	    WHEN SUM(Volumen) = 0 THEN 0
    	ELSE SUM(Speed * Volumen) / NULLIF(SUM(Volumen), 0)
	END AS Speed,
    SUM(Categoria1) AS Categoria1,
    SUM(Categoria2) AS Categoria2,
    SUM(Categoria3) AS Categoria3,
    SUM(Categoria4) AS Categoria4,
    SUM(Categoria5) AS Categoria5,
    SUM(Categoria6) AS Categoria6,
    SUM(Categoria7) AS Categoria7
FROM reports.horas
WHERE NroCarril IN (1, 2, 3, 4)
GROUP BY
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Sentido Nogales'
        WHEN NroCarril IN (3, 4) THEN 'Sentido Puchuncaví'
    END,
    Fecha,
    Hora;

CREATE VIEW reports.dias_sec AS
SELECT
    ROW_NUMBER() OVER (ORDER BY Fecha DESC) AS id,
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Sentido Nogales'
        WHEN NroCarril IN (3, 4) THEN 'Sentido Puchuncaví'
    END AS GrupoCarril,
    Fecha,
    -- Agregaciones
    SUM(Volumen) AS Volumen,
    CASE
	    WHEN SUM(Volumen) = 0 THEN 0
    	ELSE SUM(Speed * Volumen) / NULLIF(SUM(Volumen), 0)
	END AS Speed,
    SUM(Categoria1) AS Categoria1,
    SUM(Categoria2) AS Categoria2,
    SUM(Categoria3) AS Categoria3,
    SUM(Categoria4) AS Categoria4,
    SUM(Categoria5) AS Categoria5,
    SUM(Categoria6) AS Categoria6,
    SUM(Categoria7) AS Categoria7
FROM reports.dias
WHERE NroCarril IN (1, 2, 3, 4)
GROUP BY 
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Sentido Nogales'
        WHEN NroCarril IN (3, 4) THEN 'Sentido Puchuncaví'
    END,
    Fecha;

   
CREATE VIEW reports.meses_sec AS
SELECT
    ROW_NUMBER() OVER (ORDER BY Anio DESC , Mes DESC) AS id,
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Sentido Nogales'
        WHEN NroCarril IN (3, 4) THEN 'Sentido Puchuncaví'
    END AS GrupoCarril,
    Anio,
    Mes,
    -- Agregaciones
    SUM(Volumen) AS Volumen,
    CASE
	    WHEN SUM(Volumen) = 0 THEN 0
    	ELSE SUM(Speed * Volumen) / NULLIF(SUM(Volumen), 0)
	END AS Speed,
    SUM(Categoria1) AS Categoria1,
    SUM(Categoria2) AS Categoria2,
    SUM(Categoria3) AS Categoria3,
    SUM(Categoria4) AS Categoria4,
    SUM(Categoria5) AS Categoria5,
    SUM(Categoria6) AS Categoria6,
    SUM(Categoria7) AS Categoria7
FROM reports.meses
WHERE NroCarril IN (1, 2, 3, 4)
GROUP BY 
    CASE 
        WHEN NroCarril IN (1, 2) THEN 'Sentido Nogales'
        WHEN NroCarril IN (3, 4) THEN 'Sentido Puchuncaví'
    END,
    Anio,
    Mes;*/
