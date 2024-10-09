from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from datetime import datetime
import time
import csv
import os

def write_to_csv(filename, alarm_data):
    python_filename = os.path.basename(__file__)[:-3]
    columnas_csv = ["fecha", "alarma", "equipo", "nivel"]
    with open(f'{python_filename}_{filename}.csv', mode='w', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)
        writer.writerow(columnas_csv)
        writer.writerows(alarm_data)

def search_elements_fill_list(datatable):
    # Se busca a través de la datatable las alarmas y los equipos
    columna_fecha = datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[3]/div/span')
    columna_alarmas = datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[4]/div/span')
    columna_equipos = datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[6]/div/span')
    columna_nivel = datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[7]/div/img')        

    alarm_data = []
    for fecha, alarma, equipo, nivel in zip(columna_fecha, columna_alarmas, columna_equipos, columna_nivel):
        if alarma.text and equipo.text:
            alarm_data.append((fecha.text, alarma.text, equipo.text, nivel.get_dom_attribute("src")[23:-4]))
    
    return alarm_data

def process_alarms(driver, view_id, alarm_type, date):
    alarms_data = []
    
    # Identifico el contenedor de la tabla de alarmas (reconocidas o no reconocidas)
    alarms_main = driver.find_element(By.XPATH, f'//*[contains(@view_id, "{view_id}")]')
    datatable = alarms_main.find_element(By.XPATH, './div[3]')
    total_elementos = int(datatable.get_dom_attribute("aria-rowcount"))
    print(f"Existen un total de {total_elementos} {alarm_type}")

    scroll_container = datatable.find_element(By.CSS_SELECTOR, '.webix_ss_vscroll.webix_vscroll_y')
    
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
    alarms_data = list(set(alarms_data))
    
    # Escribir en CSV
    write_to_csv(f"{alarm_type}-{date}", alarms_data)

    # Validar que se procesaron todas las alarmas
    assert len(alarms_data) == total_elementos

def test_alarmas(firefox_browser):

    driver, management_area, state= firefox_browser

    now = datetime.now()
    date = now.strftime("%d-%m-%Y_%H-%M-%S")

    # Acceder al menú y monitor de alarmas
    WebDriverWait(driver, 20).until(EC.visibility_of_element_located((By.LINK_TEXT, "Equipamiento"))).click()
    driver.find_element(By.XPATH, "//span[text()='Monitor de alarmas']").click()   

    # Esperar a que las tablas de alarmas sean visibles
    WebDriverWait(driver, 20).until(EC.presence_of_element_located((By.XPATH, '//*[contains(@view_id, "viewDtNoRecognized")]')))
    WebDriverWait(driver, 20).until(EC.presence_of_element_located((By.XPATH, '//*[contains(@view_id, "viewDtRecognized")]')))

    # Procesar alarmas no reconocidas y reconocidas
    process_alarms(driver, "viewDtNoRecognized", "alarmas_no_reconocidas", date)
    process_alarms(driver, "viewDtRecognized", "alarmas_reconocidas", date)
