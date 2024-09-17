from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from datetime import datetime
import time
import csv



def test_alarmas(firefox_browser):

    
    # datetime object containing current date and time
    now = datetime.now()
    # dd/mm/YY H:M:S
    date = now.strftime("%d-%m-%Y_%H-%M-%S")

    # Espero a que el menú "Equipamiento" cuyo id es webix_l_id="2" sea visible y clico
    WebDriverWait(firefox_browser, 20).until(EC.visibility_of_element_located((By.CSS_SELECTOR, "[webix_l_id='2']"))).click()
    # Clico la opción "Monitor de alarmas" cuyo id es webix_l_id="2.202"
    firefox_browser.find_element(By.XPATH, "//*[contains(@webix_l_id, '2.202')]").click()   

    # Espero a que las vistas de las tablas de alarmas reconocidas y no reconocidas este presente
    WebDriverWait(firefox_browser, 10).until(EC.presence_of_element_located((By.XPATH, '//*[contains(@view_id, "viewDtNoRecognized")]')))
    WebDriverWait(firefox_browser, 10).until(EC.presence_of_element_located((By.XPATH, '//*[contains(@view_id, "viewDtRecognized")]')))

    alarmas_no_reconocidas(firefox_browser, date)
    alarmas_reconocidas(firefox_browser, date)

def alarmas_no_reconocidas(firefox_browser, date):
    alarmas_no_reconocidas_fecha =[]
    alarmas_no_reconocidas_equipos =[]
    alarmas_no_reconocidas_nombre = []
    alarmas_no_reconocidas_nivel = []

    # Identifico el contenedor de la tabla de alarmas reconocidas
    not_recognized_alarms_main = firefox_browser.find_element(By.XPATH, '//*[contains(@view_id, "viewDtNoRecognized")]')

    #Se guarda en una variable la datatable
    not_recognized_datatable = not_recognized_alarms_main.find_element(By.XPATH, './div[3]')
    total_elementos = int(not_recognized_datatable.get_dom_attribute("aria-rowcount"))
    print(f"\nExisten un total de {total_elementos} alarmas sin reconocer")

    scroll_container = not_recognized_datatable.find_element(By.CSS_SELECTOR, '.webix_ss_vscroll.webix_vscroll_y')
    while True:
        # Se busca a traves de la datatable las alarmas y los equipos
        columna_fecha = not_recognized_datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[3]/div/span')
        columna_alarmas = not_recognized_datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[4]/div/span')
        columna_equipos = not_recognized_datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[6]/div/span')
        columna_nivel = not_recognized_datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[7]/div/img')        

        # Imprimir las listas de alarmas y equipos
        for fecha, alarma, equipo, nivel in zip(columna_fecha, columna_alarmas, columna_equipos, columna_nivel):
            if(alarma.text == "" or equipo.text == ""):
                pass
            else:
                alarmas_no_reconocidas_fecha.append(fecha.text)
                alarmas_no_reconocidas_equipos.append(equipo.text)
                alarmas_no_reconocidas_nombre.append(alarma.text)
                alarmas_no_reconocidas_nivel.append(nivel.get_dom_attribute("src")[23:-4])
                

        scroll_height = firefox_browser.execute_script("return arguments[0].scrollHeight", scroll_container)
        scroll_top = firefox_browser.execute_script("return arguments[0].scrollTop", scroll_container)
        client_height = firefox_browser.execute_script("return arguments[0].clientHeight", scroll_container)


        if scroll_top + client_height >= scroll_height:
            break

        firefox_browser.execute_script("arguments[0].scrollTop = arguments[0].scrollTop + arguments[0].offsetHeight;", scroll_container)
        time.sleep(0.5)

    alarmas_no_reconocidas = list(set(list(zip(alarmas_no_reconocidas_fecha, alarmas_no_reconocidas_nombre, alarmas_no_reconocidas_equipos, alarmas_no_reconocidas_nivel))))
    
    columnas_csv = ["fecha", "alarma", "equipo", "nivel"]
    with open(f'alarmas_no_reconocidas-{date}.csv', mode='w', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)
        writer.writerow(columnas_csv)
        for alarma in alarmas_no_reconocidas:
            writer.writerow(alarma)
        file.close()

    assert len(alarmas_no_reconocidas) == total_elementos


def alarmas_reconocidas(firefox_browser, date):
    alarmas_reconocidas_fecha =[]
    alarmas_reconocidas_equipos =[]
    alarmas_reconocidas_nombre = []
    alarmas_reconocidas_nivel =[]

    # Identifico el contenedor de la tabla de alarmas reconocidas
    recognized_alarms_main = firefox_browser.find_element(By.XPATH, '//*[contains(@view_id, "viewDtRecognized")]')

    #Se guarda en una variable la datatable
    recognized_datatable = recognized_alarms_main.find_element(By.XPATH, './div[3]')
    total_elementos = int(recognized_datatable.get_dom_attribute("aria-rowcount"))
    print(f"Existen un total de {total_elementos} alarmas reconocidas")

    scroll_container = recognized_datatable.find_element(By.CSS_SELECTOR, '.webix_ss_vscroll.webix_vscroll_y')
    while True:
        # Se busca a traves de la datatable las alarmas y los equipos
        recognized_columna_fecha = recognized_datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[3]/div/span')
        recognized_columna_alarmas = recognized_datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[4]/div/span')
        recognized_columna_equipos = recognized_datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[6]/div/span')
        recognized_columna_nivel = recognized_datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[7]/div/img')


        # Imprimir las listas de alarmas y equipos
        for fecha, alarma, equipo, nivel in zip(recognized_columna_fecha, recognized_columna_alarmas, recognized_columna_equipos, recognized_columna_nivel):
            if(alarma.text == "" or equipo.text == ""):
                pass
            else:
                alarmas_reconocidas_fecha.append(fecha.text)
                alarmas_reconocidas_equipos.append(equipo.text)
                alarmas_reconocidas_nombre.append(alarma.text)
                alarmas_reconocidas_nivel.append(nivel.get_dom_attribute("src")[23:-4])
        
        scroll_height = firefox_browser.execute_script("return arguments[0].scrollHeight", scroll_container)
        scroll_top = firefox_browser.execute_script("return arguments[0].scrollTop", scroll_container)
        client_height = firefox_browser.execute_script("return arguments[0].clientHeight", scroll_container)


        if scroll_top + client_height >= scroll_height:
            break

        firefox_browser.execute_script("arguments[0].scrollTop = arguments[0].scrollTop + arguments[0].offsetHeight;", scroll_container)
        time.sleep(0.5)

    
    alarmas_reconocidas = list(set(list(zip(alarmas_reconocidas_fecha, alarmas_reconocidas_nombre, alarmas_reconocidas_equipos, alarmas_reconocidas_nivel))))
    
    columnas_csv = ["fecha", "alarma", "equipo", "nivel"]
    with open(f'alarmas_reconocidas-{date}.csv', mode='w', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)
        writer.writerow(columnas_csv)
        for alarma in alarmas_reconocidas:
            writer.writerow(alarma)
        file.close()

    assert len(alarmas_reconocidas) == total_elementos
