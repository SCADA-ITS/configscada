'''
Este test tiene como objetivo comprobar que el stomp funciona correctamente
comprobando que los valores de los ambientales cambian cada 10 segundos
'''
import os
from datetime import datetime
import time
from typing import List
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.remote.webelement import WebElement
from selenium.webdriver.firefox.webdriver import WebDriver

TIMEOUT: int = 40

element_types_ambientales: dict = {
    "LUX": 12,
    "LUM": 14,
    "ANEM_INT": 18,
    "CO": 19,
    "NO": 20,
    "OPAC": 21,
    "FOTOCEL": 31
}

def get_value(driver: WebDriver) -> list:
    """
    Comprueba los valores de las medidas y las devuelve

    Args:
        driver (WebDriver): Driver firefox.
    
    Returns:
        list: Lista con los valores de los ambientales para su posterior escritura en fichero.
    """
    values: list = []

    datatable: WebElement = driver.find_element(By.XPATH, '//div[contains(@id, "datatable")]')
    values_datatable: WebElement = datatable.find_elements(By.XPATH, './div[2]/div[2]/div/div[4]/div/div/span')
    values_equipment_name: WebElement = datatable.find_elements(By.XPATH, './div[2]/div[1]/div/div[1]/div/span')

    for equipo, valor in zip(values_equipment_name, values_datatable):
        values.append((equipo.text, valor.text))

    return values

def write_to_txt(driver: WebDriver, date: str, element_type_id_list: List[int]) -> None:
    """
    Crea un archivo txt donde se almacenaran los datos de las medidas de los equipos

    Args:
        driver (WebDriver): Driver firefox.
        date (str): Fecha y hora para la creación del fichero.
        element_type_id_list (list): Lista con los tipos de equipos disponibles 
        en el sistema que coincidan con la variable global element_types_ambientales.
    """

    filename: str = os.path.basename(__file__)[:-3]
    with open(f'{filename}-{date}.txt', mode="w", encoding='utf-8') as file:
        for element_type_id in element_type_id_list:

            WebDriverWait(driver, TIMEOUT).until(
                EC.visibility_of_element_located((By.XPATH, f"//div[contains(@webix_l_id, 'ElementType:{element_type_id}')]"))
            ).click()

            time.sleep(2)

            value_a: list = get_value(driver)

            # Pausa para que de tiempo a cambiar el valor
            time.sleep(10)

            value_b: list = get_value(driver)

            equipo: str = next((equipo for equipo, id in element_types_ambientales.items() if id == element_type_id), None)

            file.write(f'Tipo de equipo: {equipo}\n')
            for item_a, item_b in zip(value_a, value_b):
                print(f'{item_a[0]}: {item_a[1]} - {item_b[1]}')
                try:
                    assert item_a != item_b
                except AssertionError:
                    file.write(f"El equipo {item_a[0]} no ha cambiado de valor en el periodo de tiempo establecido\n")
            file.write("\n")



def test_cambio_valores(firefox_browser) -> None:
    """
    Funcion principal, accede al menú "Equipamiento" y guarda todos los 
    tipos de equipo que hay disponibles

    Args:
        firefox_browser (WebDriver): Driver firefox
    """
    driver: WebDriver = firefox_browser[0]
    custom_trees: WebDriver = firefox_browser[3]

    element_types_project: List[int] = []

    WebDriverWait(driver, TIMEOUT).until(EC.visibility_of_element_located((By.LINK_TEXT, "Equipamiento"))).click()
    driver.find_element(By.XPATH, "//span[text()='Equipamiento']").click()

    time.sleep(2)

    main: WebElement = WebDriverWait(driver, TIMEOUT).until(EC.visibility_of_element_located((By.XPATH, '//div[contains(@view_id, "view-main-list-equipments-")]')))

    time.sleep(5)
    if custom_trees == 'False':
        equipment_list: List[WebElement] = main.find_elements(By.XPATH, './div[2]/div/div/div[2]/div/div')
    else:
        equipment_list: List[WebElement] = main.find_elements(By.XPATH, './div[2]/div/div[1]/div[1]/div/div[2]/div/div')

    for x in equipment_list:
        element_types_project.append(int(x.get_dom_attribute('webix_l_id')[12:]))


    # Intersección de los dos arrays
    interseccion: List[int] = [item for item in element_types_ambientales.values() if item in element_types_project]

    now: datetime = datetime.now()
    date: str = now.strftime("%d-%m-%Y_%H-%M-%S")

    write_to_txt(driver, date, interseccion)
