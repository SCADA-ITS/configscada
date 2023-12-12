# La lista original
data = [
('es_CL', 1, 'LBL_MESSAGE', 'Mensaje', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_DESCENDING_DIRECTION', 'Sentido decreciente', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_TRAVEL_TIME_SECTIONS', 'Secciones', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_ADD_NEW_TRAVEL_TIME', 'Añadir tiempo de recorrido', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_ADD_TRAVEL_TIME_DESCRIPTION', 'Añadir nuevo tiempo de recorrido y configura sus secciones asociadas', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_EDIT_TRAVEL_TIME', 'Editar tiempo de recorrido', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_EDIT_TRAVEL_TIME_DESCRIPTION', 'Edita el tiempo de recorrido y configura sus secciones asociadas', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_SELECT_SECTION', 'Selección de sección', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_TRAVEL_TIME_SAVE', '¿Desea guardar los cambios?', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_QUESTION_DELETE_TRAVEL_TIMES_DESCRIPTION', '¿Desea eliminar los tiempos de recorrido seleccionados?', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_AFFECTED_STRETCH', 'Tramo afectado', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_TIME_MIN', 'Tiempo (min)', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_ASSIGNED_PANELS', 'Paneles asociados', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_CMD_ASSIGN_PANEL', 'Asociar panel', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_CMD_UNASSIGN_ALL_PANELS', 'Desasociar todos los paneles', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_SELECT_PANEL', 'Selección de panel', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_QUESTION_DELETE_ASSOCIATED_PANELS', '¿Desasociar todos los paneles al tiempo de recorrido seleccionado?', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_CMD_VIEW_PANEL', 'Ver panel', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_NO_VMS_ASSOCIATED_TO_VIEW', 'No hay paneles asociados', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_PANEL', 'Panel', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
('es_CL', 1, 'LBL_ERROR_OF_PERSISTENCE', 'Error de persistencia', 'CURRENT_TIMESTAMP', 'CURRENT_TIMESTAMP'),
]

# Ordenar la lista por el cuarto campo
sorted_data = sorted(data, key=lambda x: x[2])

# Mostrar la lista ordenada
for item in sorted_data:
   print(str(item).replace("'CURRENT_TIMESTAMP'", "CURRENT_TIMESTAMP") + ",")
   # print(", ".join(map(str, item)))
