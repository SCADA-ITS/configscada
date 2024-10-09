import json
import pytest
import sys
import configparser
from configparser import NoOptionError
from pathlib import Path

def main():
    # Ruta del archivo de informe JSON
    report_file = Path("report.json")

    config = configparser.RawConfigParser()
    config.read('config.properties')

    try:
        # Acceder a los valores
        ip = config.get('Config', 'ip')
        user = config.get('Config', 'user')
        password = config.get('Config', 'password')
        state = config.get('Config', 'state')
    except NoOptionError as e:
        print(f'Error al leer la configuración del archivo config.properties: {e}')
        exit()
    
    try:
        # Ejecuta pytest y genera un informe en JSON
        result = pytest.main([
            f"--ip={ip}",
            f"--user={user}",
            f"--password={password}",
            f"--state={state}",
            "--disable-warnings",
            "--tb=line",
            "--json-report",
            "--json-report-file=" + str(report_file),
            "tests/"
        ])
    except UnboundLocalError as e:
        print(f'Error al setear una variable: {e}')
        exit()
    
    # Verifica el código de salida de pytest
    if result == pytest.ExitCode.OK:
        print("Todas las pruebas pasaron: Éxito")
        return "Éxito", []
    else:
        # Analiza el informe JSON para encontrar pruebas fallidas
        failed_tests = []
        if report_file.exists():
            with open(report_file, "r") as f:
                report_data = json.load(f)
            
            for test in report_data.get('tests', []):
                if test.get('outcome') == 'failed':
                    failed_tests.append(test['nodeid'])
        
        print("Algunas pruebas fallaron: Fallo")
        return "Fallo", failed_tests

if __name__ == "__main__":
    estado, failed_scripts = main()
    if estado == "Fallo":
        print(f"Los siguientes scripts fallaron: {failed_scripts}")
    sys.exit(0 if estado == "Éxito" else 1)
