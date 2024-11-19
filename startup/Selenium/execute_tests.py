import sys
from pathlib import Path
import pytest

def main() -> None:
    # Ruta del archivo de informe JSON
    report_file: Path = Path("report.json")

    try:
        # Ejecuta pytest y genera un informe en JSON
        pytest.main([
            "--disable-warnings",
            "--tb=line",
            "--json-report",
            "--json-report-indent=2",
            "--json-report-file=" + str(report_file),
            "tests/"
        ])
    except UnboundLocalError as e:
        sys.exit(f'Error al setear una variable: {e}')

if __name__ == "__main__":
    main()
