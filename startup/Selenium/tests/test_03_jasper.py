'''
Este test tiene como objetivo acceder a la herramienta de informes Jasperserver 
para comprobar que la aplicación funciona correctamente.
'''
import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.remote.webelement import WebElement
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.firefox.webdriver import WebDriver


# Constante para tiempo de espera
TIMEOUT: int = 20
'''
Este ID se saca con la combinacion de ID en la tabla ui.menu_items, juntando <menu_group_id>.<id>
En este caso menu_group_id = 4 e id = 401
'''
WEBIX_ID_INFORMES: str = "4.401"

def test_jasper(firefox_browser):
    """
    Funcion principal, accede a la herramienta Jasperserver para comprobar 
    si está levantada la aplicación
    
    Args:
        firefox_browser (WebDriver): Driver firefox
    """

    driver: WebDriver = firefox_browser[0]

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
            EC.element_to_be_clickable((By.XPATH, f'//*[contains(@webix_l_id, "{WEBIX_ID_INFORMES}")]'))
        ).click()
    except TimeoutException:
        pytest.fail('No se ha encontrado el submenú "Informes"')

    try:
        # Esperar hasta que el iframe esté presente y cargado
        iframe: WebElement = WebDriverWait(driver, TIMEOUT).until(
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
