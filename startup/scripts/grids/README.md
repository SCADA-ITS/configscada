Ejecutar: python3 db_create_grids.py [ip_bbdd] [puerto_bbdd] [option] [number_incidents(max=6) (solo si la opcion es = 1)]

Ejecutar: python3 db_create_equip_viewer.py [ip_bbdd] [puerto_bbdd] [option]
option = 1:
    Genera --> Config/grids.sql  (primeras 3 tablas)
           --> Properties/grids.xlsx Para configurar la tabla grid_option_values

Configurar a mano los campos del excel, donde existirá una hoja por cada tipo de elemento.
Para consultar que hace cada parametro, consultar en la primera hoja del excel, llamada INFO.
Si no se quiere configurar un parametro, dejar la celda en blanco, en caso de que se quiera configurar a NULL, escribir NULL en la celda correspondiente.

Una vez hecho esto, ejecutar la opcion dos.

option = 2:
    Completa Config/grids.sql con la info del excel.

