import time
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

def test_jasper(firefox_browser):
    # 3 | Esperar hasta que el elemento "Herramientas" esté visible y clicarlo
    WebDriverWait(firefox_browser, 20).until(
        EC.visibility_of_element_located((By.LINK_TEXT, "Herramientas"))
    ).click()

    # 4 | Esperar y hacer clic en el submenú
    WebDriverWait(firefox_browser, 20).until(
        EC.element_to_be_clickable((By.CLASS_NAME, "submenu-text"))
    ).click()

    # 5 | Esperar hasta que el iframe esté presente y cargado
    WebDriverWait(firefox_browser, 20).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, "iframe[src*='/jasperserver']"))
    )

    # Opcional: agregar una espera adicional para asegurar que el contenido del iframe se cargue completamente.
    time.sleep(2)