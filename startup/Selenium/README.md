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

```python3 execute_tests.py```

Para ejecutar un test en especifico necesitamos lanzar el siguiente comando:

```pytest <Path del archivo> -s --tb=line```

Ejemplo:

```pytest tests/test_03_jasper.py -s --tb=line```

### Windows
Para ejecutar todos los test disponibles necesitamos lanzar el siguiente comando:

```python.exe .\execute_tests.py```


Para ejecutar un test en especifico necesitamos lanzar el siguiente comando:

```pytest.exe <Path del archivo> -s --tb=line```

Ejemplo:

```pytest.exe .\tests\test_03_jasper.py -s --tb=line```

