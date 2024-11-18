'''
Este test tiene como objetivo guardar en un archivo csv las alarmas reconocidas y no reconocidas del sistema.
'''
import os
import time
import csv
from datetime import datetime
from typing import List
import pytest
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.remote.webelement import WebElement
from selenium.webdriver.firefox.webdriver import WebDriver

TABLA_ALARMAS_NO_RECONOCIDAS: str = "viewDtNoRecognized"
TABLA_ALARMAS_RECONOCIDAS: str = "viewDtRecognized"
TIMEOUT: int = 40

def write_to_csv(alarm_type: str, alarm_data: list) -> None:
    """
    Inserta en un fichero csv las alarmas encontradas en el sistema

    Args:
        alarm_type (str): Tipo de alarmas.
        alarm_data (list): Lista de alarmas.
    """
    python_filename: str = os.path.basename(__file__)[:-3]
    columnas_csv: List[str] = ["fecha", "alarma", "equipo", "nivel"]
    with open(f'{python_filename}_{alarm_type}.csv', mode='w', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)
        writer.writerow(columnas_csv)
        writer.writerows(alarm_data)

def search_elements_fill_list(datatable: WebElement) -> list:
    """
    Busca en las lista de alarmas y devuelve una lista con las alarmas activas en el sistema

    Args:
        datatable (WebElement): Grid con las alarmas activas en el sistema.

    Returns:
        list: Lista con las alarmas guardadas.
    """
    columna_fecha: WebElement = datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[3]/div/span')
    columna_alarmas: WebElement = datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[4]/div/span')
    columna_equipos: WebElement = datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[6]/div/span')
    columna_nivel: WebElement = datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[7]/div/img')

    alarm_data: list = []
    for fecha, alarma, equipo, nivel in zip(columna_fecha, columna_alarmas, columna_equipos, columna_nivel):
        if alarma.text and equipo.text:
            alarm_data.append((fecha.text, alarma.text, equipo.text, nivel.get_dom_attribute("src")[23:-4]))

    return alarm_data

def process_alarms(driver: WebDriver, view_id: str, alarm_type: str, date: str) -> None:
    """
    Recorre la tabla de alarmas y asegura que el numero de las alarmas guardadas 
    en el fichero csv corresponden con las que el sistema tiene guardadas

    Args:
        driver (WebDriver): Driver firefox
        view_id (str): ID del identificador HTML "view_id"
        alarm_type (str): Tipo de alarma
        date (str): Fecha y hora en la que se ejecuta el script
    """

    alarms_data: list = []

    # Identifico el contenedor de la tabla de alarmas (reconocidas o no reconocidas)
    alarms_main: WebElement = driver.find_element(By.XPATH, f'//*[contains(@view_id, "{view_id}")]')
    datatable: WebElement = alarms_main.find_element(By.XPATH, './div[3]')
    total_elementos: int = int(datatable.get_dom_attribute("aria-rowcount"))
    print(f"Existen un total de {total_elementos} {alarm_type}")

    scroll_container: WebElement = datatable.find_element(By.CSS_SELECTOR, '.webix_ss_vscroll.webix_vscroll_y')

    while True:
        alarms_data.extend(search_elements_fill_list(datatable))

        scroll_height = driver.execute_script("return arguments[0].scrollHeight", scroll_container)
        scroll_top = driver.execute_script("return arguments[0].scrollTop", scroll_container)
        client_height = driver.execute_script("return arguments[0].clientHeight", scroll_container)

        if scroll_top + client_height >= scroll_height:
            break

        driver.execute_script("arguments[0].scrollTop += arguments[0].offsetHeight;", scroll_container)
        time.sleep(0.5)

    # Eliminar duplicados
    alarms_data: list = list(set(alarms_data))

    # Escribir en CSV
    write_to_csv(f"{alarm_type}-{date}", alarms_data)

    # Validar que se procesaron todas las alarmas
    assert len(alarms_data) == total_elementos

def test_alarmas(firefox_browser) -> None:
    """
    Funcion principal, accede al monitor de alarmas

    Args:
        firefox_browser (WebDriver): Driver firefox
    """
    driver: WebDriver = firefox_browser[0]

    now: datetime = datetime.now()
    date: str = now.strftime("%d-%m-%Y_%H-%M-%S")

    try:
        # Acceder al menú y monitor de alarmas
        WebDriverWait(driver, TIMEOUT).until(EC.visibility_of_element_located((By.LINK_TEXT, "Equipamiento"))).click()
        driver.find_element(By.XPATH, "//span[text()='Monitor de alarmas']").click()
    except NoSuchElementException:
        pytest.fail('No se ha encontrado el item "Monitor de alarmas"')

    # Esperar a que las tablas de alarmas sean visibles
    WebDriverWait(driver, TIMEOUT).until(EC.presence_of_element_located((By.XPATH, f'//*[contains(@view_id, {TABLA_ALARMAS_NO_RECONOCIDAS})]')))
    WebDriverWait(driver, TIMEOUT).until(EC.presence_of_element_located((By.XPATH, f'//*[contains(@view_id, {TABLA_ALARMAS_RECONOCIDAS})]')))

    driver.implicitly_wait(5)

    # Procesar alarmas no reconocidas y reconocidas
    process_alarms(driver, TABLA_ALARMAS_NO_RECONOCIDAS, "alarmas_no_reconocidas", date)
    process_alarms(driver, TABLA_ALARMAS_RECONOCIDAS, "alarmas_reconocidas", date)
