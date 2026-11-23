-- Detalle de llamadas CCT de todos los meses.
-- La fecha local de Chile determina el mes del informe.

DO $do$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM pg_tablespace
        WHERE spcname = 'tbl_backoffice'
    ) THEN
        EXECUTE $view$
            CREATE OR REPLACE VIEW backoffice.cct_calls_monthly_detail AS
            WITH calls AS (
                SELECT
                    c.*,
                    c.call_datetime AT TIME ZONE 'America/Santiago'
                        AS local_datetime
                FROM backoffice.cct_calls c
                WHERE c.call_datetime IS NOT NULL
            )
            SELECT
                c.id,
                (
                    EXTRACT(YEAR FROM c.local_datetime)::integer * 100
                    + EXTRACT(MONTH FROM c.local_datetime)::integer
                ) AS period_id,
                c.call_number,
                TO_CHAR(
                    c.local_datetime,
                    'DD/MM/YYYY'
                ) AS call_date,
                TO_CHAR(
                    c.local_datetime,
                    'HH24:MI:SS'
                ) AS call_time,
                c.response_time,
                COALESCE(r.alias, '-') AS reason,
                c.call_description,
                c.operator,
                COALESCE(s.alias, '-') AS shift,
                EXTRACT(YEAR FROM c.local_datetime)::integer AS anio,
                EXTRACT(MONTH FROM c.local_datetime)::integer AS mes_numero
            FROM calls c
            LEFT JOIN backoffice.cct_call_reasons r
                ON r.id = c.reason_id
            LEFT JOIN backoffice.cct_call_shifts s
                ON s.id = c.shift_id
            ORDER BY c.call_datetime, c.id
        $view$;
    END IF;
END;
$do$;