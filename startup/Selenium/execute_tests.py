import sys
import json
from pathlib import Path
import pytest

def main():
    # Ruta del archivo de informe JSON
    report_file: Path = Path("report.json")

    try:
        # Ejecuta pytest y genera un informe en JSON
        result: int = pytest.main([
            "--disable-warnings",
            "--tb=line",
            "--json-report",
            "--json-report-file=" + str(report_file),
            "tests/"
        ])
    except UnboundLocalError as e:
        sys.exit(f'Error al setear una variable: {e}')

    # Verifica el código de salida de pytest
    if result == pytest.ExitCode.OK:
        print("Todas las pruebas pasaron: Éxito")
        return "Éxito", []

    # Analiza el informe JSON para encontrar pruebas fallidas
    failed_tests: list = []
    if report_file.exists():
        with open(report_file, mode="r", encoding="utf-8") as f:
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
