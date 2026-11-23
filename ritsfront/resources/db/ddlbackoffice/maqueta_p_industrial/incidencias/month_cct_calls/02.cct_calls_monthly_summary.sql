-- Resumen mensual de llamadas CCT por motivo y turno.
-- Se carga despues de 01.cct_calls_monthly_details.sql.

DO $do$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM pg_tablespace
        WHERE spcname = 'tbl_backoffice'
    ) THEN
        EXECUTE $view$
            CREATE OR REPLACE VIEW backoffice.cct_calls_monthly_summary AS
            SELECT
                period_id AS id,
                anio,
                CASE mes_numero
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
                END::varchar AS mes,

                COUNT(*) AS total_llamadas,

                COUNT(*) FILTER (
                    WHERE LOWER(BTRIM(reason)) = 'asistencia en ruta'
                ) AS asistencia_ruta,

                COUNT(*) FILTER (
                    WHERE LOWER(BTRIM(reason)) = 'simulacro'
                ) AS simulacro,

                COUNT(*) FILTER (
                    WHERE LOWER(BTRIM(reason)) = 'otro'
                ) AS otro,

                COUNT(*) FILTER (
                    WHERE LOWER(BTRIM(reason)) = 'prueba'
                ) AS prueba,

                COUNT(*) FILTER (
                    WHERE LOWER(BTRIM(reason)) = 'emergencia'
                ) AS emergencia,

                COUNT(*) FILTER (
                    WHERE LOWER(BTRIM(reason)) = 'comercial'
                ) AS comercial,

                COUNT(*) FILTER (
                    WHERE LOWER(BTRIM(shift)) IN ('día', 'dia')
                ) AS turno_dia,

                COUNT(*) FILTER (
                    WHERE LOWER(BTRIM(shift)) = 'noche'
                ) AS turno_noche,

                COUNT(*) FILTER (
                    WHERE LOWER(BTRIM(reason)) NOT IN (
                        'asistencia en ruta',
                        'simulacro',
                        'otro',
                        'prueba',
                        'emergencia',
                        'comercial'
                    )
                ) AS motivo_sin_clasificar,

                COUNT(*) FILTER (
                    WHERE LOWER(BTRIM(shift)) NOT IN (
                        'día',
                        'dia',
                        'noche'
                    )
                ) AS turno_sin_clasificar

            FROM backoffice.cct_calls_monthly_detail
            GROUP BY period_id, anio, mes_numero
            ORDER BY anio DESC, mes_numero DESC
        $view$;
    END IF;
END;
$do$;