SELECT 
  DATE_TRUNC('hour', hist.camera.timestamp_at) AS "hora",
  COUNT(*) AS veces_velocidad_mayor_130
FROM hist.camera
WHERE f_element_id = 11
  AND cast (hist.camera.speed as float) > 130
GROUP BY "hora"
ORDER BY "hora";