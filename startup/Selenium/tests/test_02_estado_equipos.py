import time
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import re
import pytest

def test_pruebaequipos(firefox_browser):

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
    
    # Expandir el menú principal "Equipamiento"
    try:
        WebDriverWait(firefox_browser, 60).until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, ".webix_scroll_cont > .webix_el_label > .webix_el_box"))
        ).click()

    except Exception as e:
        pytest.fail(f"Error al expandir el menú 'Equipamiento': {e}")

    # Hacer clic en el submenú "Equipamiento"
    try:
        WebDriverWait(firefox_browser, 60).until(
            EC.element_to_be_clickable((By.LINK_TEXT, "Equipamiento"))
        ).click()

    except Exception as e:
        pytest.fail(f"Error al hacer clic en el submenú 'Equipamiento': {e}")
    
    # Hacer clic en el primer sub-elemento del menú expandido
    try:
        WebDriverWait(firefox_browser, 60).until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, ".webix_list_item:nth-child(1) .submenu-text"))
        ).click()

    except Exception as e:
        pytest.fail(f"Error al hacer clic en el primer submenú: {e}")

    # Contar y mostrar cuántos elementos con webix_l_id="ElementType:" están presentes
    try:
        elements = WebDriverWait(firefox_browser, 60).until(
            EC.presence_of_all_elements_located((By.CSS_SELECTOR, '[webix_l_id*="ElementType:"]'))
        )
        count = len(elements)
        print(f"Se encontraron {count} elementos con 'ElementType:'.")

        # Crear una lista de tuples con elementos y sus números de `ElementType`
        elements_with_type = []
        for element in elements:
            webix_l_id = element.get_attribute("webix_l_id")
            match = re.search(r'ElementType:(\d+)', webix_l_id)
            element_type_number = match.group(1) if match else "Desconocido"
            elements_with_type.append((element, element_type_number))

    except Exception as e:
        pytest.fail(f"No se pudieron contar los elementos con 'ElementType:': {e}")

    # Abrir el archivo de texto para guardar los resultados
    with open("equipos_estado.txt", "w") as file:
        # Realizar clics en cada elemento `ElementType`
        try:
            encontrado = False
            for element, element_type_number in elements_with_type:
                # Imprimir el mensaje con el número del ElementType
                print(f"Haciendo clic en el elemento ElementType: {element_type_number}")
                
                # Desplazar el elemento a la vista para asegurarse de que es clicable
                firefox_browser.execute_script("arguments[0].scrollIntoView(true);", element)
                time.sleep(0.5)  # Pequeño retraso para que se complete el desplazamiento
                
                element.click()
                
                # Esperar a que la clase `webix_ss_body` esté presente
                WebDriverWait(firefox_browser, 60).until(
                    EC.presence_of_element_located((By.CSS_SELECTOR, '[class="webix_ss_body"]'))
                )

                # Hacer scroll en el contenedor de la tabla hasta que se encuentren todos los elementos
                scroll_container = firefox_browser.find_element(By.CSS_SELECTOR, '.webix_ss_vscroll.webix_vscroll_y')

                while True:

                    # Crear un diccionario para mapear row_index a nombres de la columna 0
                    comunicacion_estado = {}
                    
                    cell_elements = firefox_browser.find_elements(By.CSS_SELECTOR, ".webix_cell")
                    for cell in cell_elements:
                        text = cell.text.strip()
                        row_index = cell.get_attribute("aria-rowindex")
                            
                        if text in ["No comunica", "Desconocido", "Inválido"]:
                            comunicacion_estado[row_index] = text
                            encontrado = True
                    
                    # Crear un diccionario para mapear row_index a nombres de la columna 0
                    nombres_columna_0 = {}
                    first_column_cells = firefox_browser.find_elements(By.CSS_SELECTOR, '[column="0"] .webix_cell')
                    for first_cell in first_column_cells:
                        row_index = first_cell.get_attribute("aria-rowindex")
                        equipo_name = first_cell.text.strip()
                        nombres_columna_0[row_index] = equipo_name
                    
                    # Guardar el estado de comunicación y el nombre del equipo en el archivo de texto
                    for row_index, estado in comunicacion_estado.items():
                        equipo_name = nombres_columna_0.get(row_index, "Desconocido")
                        resultado = f"ElementType: {element_type_number} - Equipo '{equipo_name}' - Estado: {estado}\n"
                        file.write(resultado)
                        # Hacer scroll hacia abajo
                    
                    # Verificar si se ha alcanzado el final de la página
                    scroll_height = firefox_browser.execute_script("return arguments[0].scrollHeight", scroll_container)
                    scroll_top = firefox_browser.execute_script("return arguments[0].scrollTop", scroll_container)
                    client_height = firefox_browser.execute_script("return arguments[0].clientHeight", scroll_container)

                    if scroll_top + client_height >= scroll_height:
                        break

                    firefox_browser.execute_script("arguments[0].scrollTop = arguments[0].scrollTop + arguments[0].offsetHeight;", scroll_container)
                    time.sleep(0.5)

                time.sleep(0.5)  # Añadir una pequeña pausa entre clics si es necesario
        
            if not encontrado:
                print("Todos los equipos estan comunicando")
            else:
                pytest.fail('Existen equipos con estado "No comunica", "Desconocido" ó "Inválido')
        except Exception as e:
            pytest.fail(f"Error al hacer clic en los elementos 'ElementType': {e}")
