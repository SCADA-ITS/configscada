-- Calcula los extremos del tramo que se muestra en el croquis.
-- Ejemplo: km = '32,5' -> km_start = '32', km_end = '33'.
-- No crea campos visibles en el formulario: solo aporta datos a la plantilla.

BEGIN;

ALTER TABLE backoffice.incidents
    ADD COLUMN IF NOT EXISTS km_start varchar NULL,
    ADD COLUMN IF NOT EXISTS km_end varchar NULL;

CREATE OR REPLACE FUNCTION backoffice.set_incident_km_range()
RETURNS trigger
LANGUAGE plpgsql
AS $function$
DECLARE
    km_value numeric;
BEGIN
    IF NEW.km IS NULL
       OR btrim(NEW.km) = ''
       OR btrim(NEW.km) !~ '^[0-9]+([.,][0-9]+)?$' THEN

        NEW.km_start := NULL;
        NEW.km_end := NULL;

    ELSE

        km_value := replace(btrim(NEW.km), ',', '.')::numeric;
        NEW.km_start := trunc(km_value)::int::varchar;
        NEW.km_end := (trunc(km_value)::int + 1)::varchar;

    END IF;

    RETURN NEW;
END;
$function$;

DROP TRIGGER IF EXISTS trg_set_incident_km_range
ON backoffice.incidents;

CREATE TRIGGER trg_set_incident_km_range
BEFORE INSERT OR UPDATE OF km
ON backoffice.incidents
FOR EACH ROW
EXECUTE PROCEDURE backoffice.set_incident_km_range();

-- Rellena los registros existentes sin modificar el valor original de km.
WITH normalized AS (
    SELECT
        id,
        CASE
            WHEN km IS NOT NULL
             AND btrim(km) <> ''
             AND btrim(km) ~ '^[0-9]+([.,][0-9]+)?$'
            THEN replace(btrim(km), ',', '.')::numeric
        END AS km_value
    FROM backoffice.incidents
)
UPDATE backoffice.incidents incident
SET km_start = trunc(normalized.km_value)::int::varchar,
    km_end = (trunc(normalized.km_value)::int + 1)::varchar
FROM normalized
WHERE normalized.id = incident.id;

-- Posición de la X según el sentido de la incidencia.
--
-- Opción 1, fila inferior, PK creciente:
--   Norte a Sur
--   Oriente a Poniente
--
-- Opción 2, fila superior, PK decreciente:
--   Sur a Norte
--   Poniente a Oriente

ALTER TABLE backoffice.incidents
    ADD COLUMN IF NOT EXISTS croquis_x_top varchar NULL,
    ADD COLUMN IF NOT EXISTS croquis_x_bottom varchar NULL;

-- Conservamos los nombres de la función y del trigger anteriores
-- para sustituir su comportamiento sin duplicarlos.
-- Ahora utilizan direction_id, no side_id.

CREATE OR REPLACE FUNCTION backoffice.set_incident_croquis_side()
RETURNS trigger
LANGUAGE plpgsql
AS $function$
DECLARE
    direction_alias text;
BEGIN
    SELECT lower(btrim(d.alias))
    INTO direction_alias
    FROM backoffice.direction d
    WHERE d.id = NEW.direction_id;

    IF direction_alias IN (
        'norte a sur',
        'oriente a poniente'
    ) THEN

        -- Opción 1: fila inferior.
        NEW.croquis_x_top := NULL;
        NEW.croquis_x_bottom := 'X';

    ELSIF direction_alias IN (
        'sur a norte',
        'poniente a oriente'
    ) THEN

        -- Opción 2: fila superior.
        NEW.croquis_x_top := 'X';
        NEW.croquis_x_bottom := NULL;

    ELSE

        -- Sin sentido válido, no se coloca ninguna X.
        NEW.croquis_x_top := NULL;
        NEW.croquis_x_bottom := NULL;

    END IF;

    RETURN NEW;
END;
$function$;

DROP TRIGGER IF EXISTS trg_set_incident_croquis_side
ON backoffice.incidents;

CREATE TRIGGER trg_set_incident_croquis_side
BEFORE INSERT OR UPDATE OF direction_id
ON backoffice.incidents
FOR EACH ROW
EXECUTE PROCEDURE backoffice.set_incident_croquis_side();

-- Recalcula las incidencias existentes, incluidas las que tenían
-- la X calculada anteriormente desde Lado carretera.
-- También elimina las X antiguas si no hay un sentido válido.

WITH positions AS (
    SELECT
        incident.id,
        CASE
            WHEN lower(btrim(direction.alias)) IN (
                'sur a norte',
                'poniente a oriente'
            )
            THEN 'X'
        END AS x_top,
        CASE
            WHEN lower(btrim(direction.alias)) IN (
                'norte a sur',
                'oriente a poniente'
            )
            THEN 'X'
        END AS x_bottom
    FROM backoffice.incidents incident
    LEFT JOIN backoffice.direction direction
        ON direction.id = incident.direction_id
)
UPDATE backoffice.incidents incident
SET croquis_x_top = positions.x_top,
    croquis_x_bottom = positions.x_bottom
FROM positions
WHERE positions.id = incident.id
  AND (
      incident.croquis_x_top IS DISTINCT FROM positions.x_top
      OR incident.croquis_x_bottom IS DISTINCT FROM positions.x_bottom
  );

COMMIT;