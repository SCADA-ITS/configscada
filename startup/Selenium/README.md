# Introducción
Este archivo tiene como función explicar como ejecutar los test disponibles, tanto globalmente como unitariamente

## Requisitos
Versión de python: 3.8.10

Necesitamos instalar los siguientes módulos con pip: 
* pytest
* selenium
* pytest-json-report

## Ejecución
### Linux
Para ejecutar todos los test disponibles necesitamos lanzar el siguiente comando:

```python3 execute_tests.py <Ip> <usuario> <contraseña>```

Ejemplo:

```python3 execute_tests.py 192.168.88.201 admin Revenga.19```

Para ejecutar un test en especifico necesitamos lanzar el siguiente comando:

```pytest tests/test_03_jasper.py --ip=<Ip> --user=<usuario> --password=<contraseña> -s```

Ejemplo:

```pytest tests/test_03_jasper.py --ip=192.168.88.201 --user=admin --password=Revenga.19 -s```

### Windows
Para ejecutar todos los test disponibles necesitamos lanzar el siguiente comando:

```python.exe execute_tests.py <Ip> <usuario> <contraseña>```

Ejemplo:

```python.exe execute_tests.py 192.168.88.201 admin Revenga.19```

Para ejecutar un test en especifico necesitamos lanzar el siguiente comando:

```pytest.exe tests/test_03_jasper.py --ip=<Ip> --user=<usuario> --password=<contraseña> -s```

Ejemplo:

```pytest.exe tests/test_03_jasper.py --ip=192.168.88.201 --user=admin --password=Revenga.19 -s```