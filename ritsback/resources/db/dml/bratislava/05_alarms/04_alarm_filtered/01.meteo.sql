INSERT INTO conf.alarms_filtered (
    alarm_config_id,
    element_type_id,
    element_id,
    filtered_until,
    alarm_disabled,
    user_id,
    comment,
    created_at,
    updated_at
)
SELECT
    ac.alarm_config_id,
    1 AS element_type_id,
    e.element_id,
    '2100-01-01 00:00:00.000 +0100' AS filtered_until,
    false AS alarm_disable,
    '1' AS user_id,
    'Deshabilitadas por proyecto' AS comment,
    CURRENT_TIMESTAMP AS created_at,
    CURRENT_TIMESTAMP AS updated_at
FROM
    conf.alarm_configs ac
JOIN
    conf.elements e ON ac.element_type_id = e.element_type_id
WHERE
    ac.element_type_id = 1 and alarm_config_id > 3000000;
