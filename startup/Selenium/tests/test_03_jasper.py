from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

def test_jasper(firefox_browser):
    # Esperar hasta que el elemento "Herramientas" esté visible y clicarlo
    WebDriverWait(firefox_browser, 20).until(
        EC.visibility_of_element_located((By.LINK_TEXT, "Herramientas"))
    ).click()

    # Esperar y hacer clic en el submenú
    WebDriverWait(firefox_browser, 20).until(
        EC.element_to_be_clickable((By.CLASS_NAME, "submenu-text"))
    ).click()

    # Esperar hasta que el iframe esté presente y cargado
    iframe = WebDriverWait(firefox_browser, 20).until(
        EC.presence_of_element_located((By.XPATH, '//*[contains(@src, "jasperserver")]'))
    )

    # Cambiamos el contexto para ubicarnos en el frame
    firefox_browser.switch_to.frame(iframe)

    # Esperamos a que cargue la lista de resultados de jasperserver
    WebDriverWait(firefox_browser, 20).until(
        EC.presence_of_element_located((By.ID, "resultsList"))
    )
