# La lista original
data = [
    ('en_GB', 1, 'LBL_CMD_SIGNALLING', 'Signalling', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
    ('en_GB', 1, 'LBL_DET_HUM', 'SMOKE DET', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
    ('en_GB', 1, 'LBL_ELEMENT_LOCATION', 'Location', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
    ('en_GB', 1, 'LBL_EQUIPMENT_SELECTED', 'Equipment selected', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
    ('en_GB', 1, 'LBL_LIST', 'List', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
    ('en_GB', 1, 'LBL_ALARM_MUTED_VIEW', 'Layers', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
    ('en_GB', 1, 'LBL_MUTED_UNTIL', 'Inhibited until...', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
    ('en_GB', 1, 'LBL_PULS', 'PUSH BUTTON', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
    ('en_GB', 1, 'LBL_VALV', 'VALVES', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
    ('en_GB', 1, 'Medidas', 'Measures', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP')
]

# Ordenar la lista por el cuarto campo
sorted_data = sorted(data, key=lambda x: x[3])

# Mostrar la lista ordenada
for item in sorted_data:
    print(",".join(map(str, item)))
