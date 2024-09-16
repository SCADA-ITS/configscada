from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import NoSuchElementException
import time
import pytest



def test_alarmas(firefox_browser):
    # Espero a que el menú "Equipamiento" cuyo id es webix_l_id="2" sea visible y clico
    WebDriverWait(firefox_browser, 20).until(EC.visibility_of_element_located((By.CSS_SELECTOR, "[webix_l_id='2']"))).click()
    # Clico la opción "Monitor de alarmas" cuyo id es webix_l_id="2.202"
    firefox_browser.find_element(By.XPATH, "//*[contains(@webix_l_id, '2.202')]").click()   

    # Espero a que las vistas de las tablas de alarmas reconocidas y no reconocidas este presente
    WebDriverWait(firefox_browser, 10).until(EC.presence_of_element_located((By.XPATH, '//*[contains(@view_id, "viewDtNoRecognized")]')))
    WebDriverWait(firefox_browser, 10).until(EC.presence_of_element_located((By.XPATH, '//*[contains(@view_id, "viewDtRecognized")]')))

    errores = []

    try:
        res1 = alarmas_no_reconocidas(firefox_browser)
        if not res1:
            errores.append("Se han encontrado alarmas graves no reconocidas")
    except Exception as e:
        print(f"Error capturado en la primera función: {e}")

    try:
        res2 = alarmas_reconocidas(firefox_browser)
        if not res2:
            errores.append("Se han encontrado alarmas graves reconocidas")
    except Exception as e:
        print(f"Error capturado en la segunda función: {e}")

    # Si hay errores, marcar el test como fallido con todos los mensajes de error
    if errores:
        pytest.fail("\n".join(errores))


def alarmas_no_reconocidas(firefox_browser):
    alarmas_no_reconocidas_equipos =[]
    alarmas_no_reconocidas_nombre = []
    # Identifico el contenedor de la tabla de alarmas reconocidas
    not_recognized_alarms_main = firefox_browser.find_element(By.XPATH, '//*[contains(@view_id, "viewDtNoRecognized")]')
    # Hago click en el botón de filtro
    not_recognized_alarms_main.find_element(By.XPATH, './div[2]/div/div[contains(@view_id, "tb_btn-showfilters")]').click()
    # Hago clic en el select de nivel de alrma
    not_recognized_alarms_main.find_element(By.XPATH, './div[3]/div[1]/div[2]/table/tbody/tr[3]/td[7]/div/select').click()
    # Hago clic en la opción "Nivel 4"
    filtro_nivel_4 = not_recognized_alarms_main.find_element(By.XPATH, './div[3]/div[1]/div[2]/table/tbody/tr[3]/td[7]/div/select/option[contains(@value, "Nivel 4")]')

    if not filtro_nivel_4:
        print("No existen alarmas no reconocidas de nivel 4")
        return
    else:
        filtro_nivel_4.click()

    # Se ordena por nombre
    not_recognized_alarms_main.find_element(By.XPATH, './div[3]/div[1]/div[2]/table/tbody/tr[2]/td[6]/div/div[1]').click()

    #Se guarda en una variable la datatable
    not_recognized_datatable = not_recognized_alarms_main.find_element(By.XPATH, './div[3]')
    total_elementos = int(not_recognized_datatable.get_dom_attribute("aria-rowcount"))
    print(f"Existen un total de {total_elementos} alarmas sin reconocer")

    scroll_container = not_recognized_datatable.find_element(By.CSS_SELECTOR, '.webix_ss_vscroll.webix_vscroll_y')
    while True:
        # Se busca a traves de la datatable las alarmas y los equipos
        columna_alarmas = not_recognized_datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[4]/div/span')
        columna_equipos = not_recognized_datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[6]/div/span')

        # Imprimir las listas de alarmas y equipos
        for alarma, equipo in zip(columna_alarmas, columna_equipos):
            if(alarma.text == "" or equipo.text == ""):
                pass
            else:
                alarmas_no_reconocidas_equipos.append(equipo.text)
                alarmas_no_reconocidas_nombre.append(alarma.text)

        scroll_height = firefox_browser.execute_script("return arguments[0].scrollHeight", scroll_container)
        scroll_top = firefox_browser.execute_script("return arguments[0].scrollTop", scroll_container)
        client_height = firefox_browser.execute_script("return arguments[0].clientHeight", scroll_container)


        if scroll_top + client_height >= scroll_height:
            break

        firefox_browser.execute_script("arguments[0].scrollTop = arguments[0].scrollTop + arguments[0].offsetHeight;", scroll_container)
        time.sleep(1)

    alarmas_no_reconocidas = list(set(list(zip(alarmas_no_reconocidas_equipos, alarmas_no_reconocidas_nombre))))
    with open("Alarmas_no_reconocidas.txt", "w") as file:
        for equipo, alarma in alarmas_no_reconocidas:
            file.write(f"Equipo: {equipo}, Alarma: {alarma} \n")
        file.close()

    assert len(alarmas_no_reconocidas) == total_elementos

    if len(alarmas_no_reconocidas) > 0:
        print("Se han encontrado alarmas graves reconocidas")
        return False
    else:
        return True


def alarmas_reconocidas(firefox_browser):
    alarmas_reconocidas_equipos =[]
    alarmas_reconocidas_nombre = []

    # Identifico el contenedor de la tabla de alarmas reconocidas
    recognized_alarms_main = firefox_browser.find_element(By.XPATH, '//*[contains(@view_id, "viewDtRecognized")]')
    # Hago click en el botón de filtro
    recognized_alarms_main.find_element(By.XPATH, './div[2]/div/div[contains(@view_id, "tb_btn-showfilters")]').click()
    # Hago clic en el select de nivel de alrma
    recognized_alarms_main.find_element(By.XPATH, './div[3]/div[1]/div[2]/table/tbody/tr[3]/td[7]/div/select').click()
    # Hago clic en la opción "Nivel 4"
    filtro_nivel_4 = recognized_alarms_main.find_element(By.XPATH, './div[3]/div[1]/div[2]/table/tbody/tr[3]/td[7]/div/select/option[contains(@value, "Nivel 4")]')
    if not filtro_nivel_4:
        print("No existen alarmas reconocidas de nivel 4")
        return
    else:
        filtro_nivel_4.click()


    #Se guarda en una variable la datatable
    recognized_datatable = recognized_alarms_main.find_element(By.XPATH, './div[3]')
    total_elementos = int(recognized_datatable.get_dom_attribute("aria-rowcount"))
    print("")
    print(f"Existen un total de {total_elementos} alarmas reconocidas")

    scroll_container = recognized_datatable.find_element(By.CSS_SELECTOR, '.webix_ss_vscroll.webix_vscroll_y')
    while True:
        # Se busca a traves de la datatable las alarmas y los equipos
        recognized_columna_alarmas = recognized_datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[4]/div/span')
        recognized_columna_equipos = recognized_datatable.find_elements(By.XPATH, './div[2]/div[2]/div[1]/div[6]/div/span')


        # Imprimir las listas de alarmas y equipos
        for alarma, equipo in zip(recognized_columna_alarmas, recognized_columna_equipos):
            if(alarma.text == "" or equipo.text == ""):
                pass
            else:
                alarmas_reconocidas_equipos.append(equipo.text)
                alarmas_reconocidas_nombre.append(alarma.text)
        
        scroll_height = firefox_browser.execute_script("return arguments[0].scrollHeight", scroll_container)
        scroll_top = firefox_browser.execute_script("return arguments[0].scrollTop", scroll_container)
        client_height = firefox_browser.execute_script("return arguments[0].clientHeight", scroll_container)


        if scroll_top + client_height >= scroll_height:
            break

        firefox_browser.execute_script("arguments[0].scrollTop = arguments[0].scrollTop + arguments[0].offsetHeight;", scroll_container)
        time.sleep(0.5)

    
    alarmas_reconocidas = list(set(list(zip(alarmas_reconocidas_equipos, alarmas_reconocidas_nombre))))

    with open("Alarmas_reconocidas.txt", "w") as file:
        for equipo, alarma in alarmas_reconocidas:
            file.write(f"Equipo: {equipo}, Alarma: {alarma} \n")
        file.close()

    assert len(alarmas_reconocidas) == total_elementos

    if len(alarmas_reconocidas) > 0:
        print("Se han encontrado alarmas graves reconocidas")
        return False
    else:
        return True
