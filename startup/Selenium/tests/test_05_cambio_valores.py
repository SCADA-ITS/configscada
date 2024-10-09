from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import StaleElementReferenceException

import time, os

LUX = 12
LUM = 14
ANEM_INT = 18
CO = 19
NO = 20
OPAC = 21
FOTOCEL = 31

element_types_ambientales = [
    LUX,
    LUM,
    ANEM_INT,
    CO,
    NO,
    OPAC,
    FOTOCEL
]

def get_value(driver):

    values = []

    datatable = driver.find_element(By.XPATH, '//div[contains(@id, "datatable")]')
    values_datatable = datatable.find_elements(By.XPATH, './div[2]/div[2]/div/div[4]/div/div/span')
    values_equipment_name = datatable.find_elements(By.XPATH, './div[2]/div[1]/div/div[1]/div/span')

    for equipo, valor in zip(values_equipment_name, values_datatable):
        values.append((equipo.text, valor.text))

    return values

def test_cambio_valores(firefox_browser):

    driver, management_area, state= firefox_browser

    element_types_project = []

    WebDriverWait(driver, 20).until(EC.visibility_of_element_located((By.LINK_TEXT, "Equipamiento"))).click()
    driver.find_element(By.XPATH, "//span[text()='Equipamiento']").click()

    main = WebDriverWait(driver, 20).until(EC.visibility_of_element_located((By.XPATH, '//div[contains(@view_id, "view-main-list-equipments-")]')))
    
    time.sleep(5)

    equipment_list = main.find_elements(By.XPATH, './div[2]/div/div/div[2]/div/div')
    for x in equipment_list:
        element_types_project.append(int(x.get_dom_attribute('webix_l_id')[12:]))


    # Intersección de los dos arrays
    interseccion = [item for item in element_types_ambientales if item in element_types_project]
    filename = os.path.basename(__file__)[:-3]
    with open(f'{filename}.txt', "w") as file:
        for element_type_id in interseccion:
                
            WebDriverWait(driver, 20).until(EC.visibility_of_element_located((By.XPATH, f"//div[contains(@webix_l_id, 'ElementType:{element_type_id}')]"))).click()

            time.sleep(2)

            value_a = get_value(driver)

            # Pausa para que de tiempo a cambiar el valor
            time.sleep(10)

            value_b = get_value(driver)

            file.write(f'ElementTypeId:{element_type_id}\n')
            for item_a, item_b in zip(value_a, value_b):
                print(f'{item_a[0]}: {item_a[1]} - {item_b[1]}')
                try:
                    assert item_a != item_b
                except AssertionError:
                    file.write(f"El equipo {item_a[0]} no ha cambiado de valor en el periodo de tiempo establecido\n")
            file.write("\n")


