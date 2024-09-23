import time
import re
import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

def test_pruebaequipos(firefox_browser, state):
    estados_a_buscar = [s.strip() for s in state.split(',')]
    print(f"Estados: {estados_a_buscar}")

    # Esperar a que la página de equipos esté completamente cargada
    print("Esperando que la página de equipos esté completamente cargada...")
    try:
        WebDriverWait(firefox_browser, 60).until(
            EC.url_contains("/home.html")
        )
        WebDriverWait(firefox_browser, 60).until(
            EC.presence_of_element_located((By.CLASS_NAME, 'graphic-icon-tooltip'))
        )
        print("Página de equipos cargada.")
    except Exception as e:
        pytest.fail(f"No se pudo cargar la página de equipos: {e}")

    # Expandir el menú principal y hacer clic en el submenú "Equipamiento"
    try:
        WebDriverWait(firefox_browser, 60).until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, ".webix_scroll_cont > .webix_el_label > .webix_el_box"))
        ).click()
        
        WebDriverWait(firefox_browser, 60).until(
            EC.element_to_be_clickable((By.LINK_TEXT, "Equipamiento"))
        ).click()

        WebDriverWait(firefox_browser, 60).until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, ".webix_list_item:nth-child(1) .submenu-text"))
        ).click()
    except Exception as e:
        pytest.fail(f"Error al interactuar con el menú 'Equipamiento': {e}")

    # Contar y mostrar cuántos elementos con webix_l_id="ElementType:" están presentes
    try:
        elements = WebDriverWait(firefox_browser, 60).until(
            EC.presence_of_all_elements_located((By.CSS_SELECTOR, '[webix_l_id*="ElementType:"]'))
        )
        elements_with_type = [
            (element, re.search(r'ElementType:(\d+)', element.get_attribute("webix_l_id")).group(1) if re.search(r'ElementType:(\d+)', element.get_attribute("webix_l_id")) else "Desconocido")
            for element in elements
        ]
        print(f"Se encontraron {len(elements_with_type)} elementos con 'ElementType:'.")
    except Exception as e:
        pytest.fail(f"No se pudieron contar los elementos con 'ElementType:': {e}")

    # Abrir el archivo de texto para guardar los resultados
    with open("equipos_estado.txt", "w") as file:
        encontrado = False
        for element, element_type_number in elements_with_type:
            print(f"Haciendo clic en el elemento ElementType: {element_type_number}")
            firefox_browser.execute_script("arguments[0].scrollIntoView(true);", element)
            time.sleep(0.5)  # Pequeño retraso para que se complete el desplazamiento
            element.click()

            # Esperar a que la clase webix_ss_body esté presente
            WebDriverWait(firefox_browser, 60).until(
                EC.presence_of_element_located((By.CSS_SELECTOR, '[class="webix_ss_body"]'))
            )

            # Hacer scroll en el contenedor de la tabla
            scroll_container = firefox_browser.find_element(By.CSS_SELECTOR, '.webix_ss_vscroll.webix_vscroll_y')
            while True:
                cell_elements = firefox_browser.find_elements(By.CSS_SELECTOR, ".webix_cell")
                
                comunicacion_estado = {
                    cell.get_attribute("aria-rowindex"): cell.text.strip()
                    for cell in cell_elements if cell.text.strip() in estados_a_buscar
                }

                nombres_columna_0 = {
                    first_cell.get_attribute("aria-rowindex"): first_cell.text.strip()
                    for first_cell in firefox_browser.find_elements(By.CSS_SELECTOR, '[column="0"] .webix_cell')
                }

                # Guardar los resultados en el archivo de texto
                for row_index, estado in comunicacion_estado.items():
                    equipo_name = nombres_columna_0.get(row_index, "Desconocido")
                    file.write(f"ElementType: {element_type_number} - Equipo '{equipo_name}' - Estado: {estado}\n")
                    encontrado = True

                # Verificar si se ha alcanzado el final de la página
                scroll_height = firefox_browser.execute_script("return arguments[0].scrollHeight", scroll_container)
                scroll_top = firefox_browser.execute_script("return arguments[0].scrollTop", scroll_container)
                client_height = firefox_browser.execute_script("return arguments[0].clientHeight", scroll_container)

                if scroll_top + client_height >= scroll_height:
                    break

                firefox_browser.execute_script("arguments[0].scrollTop += arguments[0].offsetHeight;", scroll_container)
                time.sleep(0.5)

            time.sleep(0.5)  # Pausa entre clics

        if not encontrado:
            print(f"No se ha encontrado ningún equipo con los estados {estados_a_buscar}.")
        else:
            pytest.fail(f'Existen equipos con estado {estados_a_buscar}. Se ha generado un archivo de texto con los equipos.')
