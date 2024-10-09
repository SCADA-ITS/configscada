from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
import pytest

# Constante para tiempo de espera
TIMEOUT = 20

def test_jasper(firefox_browser):

    driver, management_area, state= firefox_browser

    try:
        # Esperar hasta que el elemento "Herramientas" esté visible y clicarlo
        WebDriverWait(driver, TIMEOUT).until(
            EC.visibility_of_element_located((By.LINK_TEXT, "Herramientas"))
        ).click()
    except TimeoutException:
        pytest.fail('No se ha encontrado el menú "Herramientas"')

    try:
        # Esperar y hacer clic en el submenú
        WebDriverWait(driver, TIMEOUT).until(
            EC.element_to_be_clickable((By.CLASS_NAME, "submenu-text"))
        ).click()
    except TimeoutException:
        pytest.fail('No se ha encontrado el submenú "Informes"')

    try:
        # Esperar hasta que el iframe esté presente y cargado
        iframe = WebDriverWait(driver, TIMEOUT).until(
            EC.presence_of_element_located((By.XPATH, '//*[contains(@src, "jasperserver")]'))
        )
    except TimeoutException:
        pytest.fail("No se ha encontrado la aplicación Jasperserver")


    # Cambiamos el contexto para ubicarnos en el frame
    driver.switch_to.frame(iframe)

    try:
        # Esperamos a que cargue la lista de resultados de jasperserver
        WebDriverWait(driver, TIMEOUT).until(
            EC.presence_of_element_located((By.ID, "resultsList"))
        )
    except TimeoutException:
        pytest.fail("No se ha encontrado una lista de resultados en Jasperserver")
