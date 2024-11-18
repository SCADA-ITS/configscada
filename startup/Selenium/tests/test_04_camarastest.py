import time
import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait

def test_camarastest( firefox_browser):

    driver = firefox_browser[0]

    # Esperar a que la página de equipos esté completamente cargada
    try:
        WebDriverWait(driver, 60).until(
            EC.url_contains("/home.html")
        )
        WebDriverWait(driver, 60).until(
            EC.presence_of_element_located((By.CLASS_NAME, 'graphic-icon-tooltip'))
        )
    except Exception as e:
        pytest.fail(f"No se pudo cargar la página de equipos: {e}")

    # Expandir el menú principal "Equipamiento"
    try:
        WebDriverWait(driver, 60).until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, ".webix_scroll_cont > .webix_el_label > .webix_el_box"))
        ).click()
    except Exception as e:
        pytest.fail(f"Error al expandir el menú 'Equipamiento': {e}")

    # Hacer clic en el submenú "Equipamiento"
    try:
        WebDriverWait(driver, 60).until(
            EC.element_to_be_clickable((By.LINK_TEXT, "Equipamiento"))
        ).click()
    except Exception as e:
        pytest.fail(f"Error al hacer clic en el submenú 'Equipamiento': {e}")

    # Hacer clic en el primer sub-elemento del menú expandido
    try:
        WebDriverWait(driver, 60).until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, ".webix_list_item:nth-child(1) .submenu-text"))
        ).click()
    except Exception as e:
        pytest.fail(f"Error al hacer clic en el primer submenú: {e}")

    # Esperar a que aparezcan los elementos con "ElementType"
    try:
        WebDriverWait(driver, 60).until(
            EC.presence_of_all_elements_located((By.CSS_SELECTOR, '[webix_l_id*="ElementType:"]'))
        )

        # Buscar y hacer clic en el elemento de búsqueda
        div_padre = driver.find_element(By.XPATH, '//*[contains(@view_id, "view-main-list-equipments")]')
        div_hijo = div_padre.find_element(By.XPATH, './div[2]/div[1]/div[1]/div[1]/div[1]/div[1]')
        div_search_box = div_hijo.find_element(By.CLASS_NAME, 'webix_el_box')
        input_text = div_search_box.find_element(By.XPATH, './input')
        input_text.send_keys('Cámara')

        # Esperar a que los resultados de búsqueda aparezcan
        WebDriverWait(driver, 60).until(
            EC.presence_of_all_elements_located((By.CSS_SELECTOR, ".webix_first > .webix_cell"))
        )

        # Re-localizar el elemento "ElementType:2" antes de hacer clic
        driver.find_element(By.CSS_SELECTOR, '[webix_l_id*="ElementType:2"]').click()
        WebDriverWait(driver, 60).until(
            EC.presence_of_all_elements_located((By.CSS_SELECTOR, ".webix_first > .webix_cell"))
        )

        # Lista para almacenar las cámaras sin video
        camaras_sin_video = []
        scroll_container = driver.find_element(By.CSS_SELECTOR, '.webix_ss_vscroll.webix_vscroll_y')
        i = 0

        # Iterar hasta que no haya más cámaras que procesar
        while True:
            # Verificar si se ha alcanzado el final de la página
            scroll_height = driver.execute_script("return arguments[0].scrollHeight", scroll_container)
            scroll_top = driver.execute_script("return arguments[0].scrollTop", scroll_container)
            client_height = driver.execute_script("return arguments[0].clientHeight", scroll_container)

            try:
                # Hacer clic en el elemento
                equipo_selector = f"[aria-rowindex='{i + 1}']"
                try:
                    WebDriverWait(driver, 3).until(
                        EC.element_to_be_clickable((By.CSS_SELECTOR, equipo_selector))
                    ).click()
                except Exception:
                    print(f"No se encontró el nombre de la cámara en el elemento {equipo_selector}, haciendo scroll...")

                    # Hacer scroll en el contenedor
                    driver.execute_script("arguments[0].scrollTop += arguments[0].offsetHeight;", scroll_container)

                    time.sleep(0.5)  # Esperar a que se complete el scroll

                    if scroll_top + client_height >= scroll_height:
                        print("Se ha alcanzado el final de la página.")
                        break  # Salir del bucle si se alcanza el final

                    WebDriverWait(driver, 3).until(
                        EC.element_to_be_clickable((By.CSS_SELECTOR, equipo_selector))
                    ).click()

                # Reintentar obtener el nombre de la cámara después del scroll
                nombre_camara = driver.find_element(
                    By.CSS_SELECTOR, f"{equipo_selector} .table-row-text"
                ).text

                # Doble clic para seleccionar el equipo
                time.sleep(0.5)  # Esperar un momento para asegurarse de que el elemento esté completamente visible
                element = driver.find_element(By.CSS_SELECTOR, equipo_selector)
                actions = ActionChains(driver)
                actions.double_click(element).perform()
                i += 1

                try:
                    video_image = WebDriverWait(driver, 5).until(
                        EC.presence_of_element_located((By.CSS_SELECTOR, 'canvas.camviewer-img, img.camviewer-img'))
                    )
                    src_value = video_image.get_attribute("src")
                    if src_value is None:
                        print("No se ha detectado la etiqueta src por lo que no hay video")
                        camaras_sin_video.append(nombre_camara)
                    else:
                        print(f"Se encontró una imagen de video para la cámara '{nombre_camara}': {src_value}")
                except Exception:
                    no_video_image = WebDriverWait(driver, 1).until(
                        EC.presence_of_element_located((By.CSS_SELECTOR, 'img[src="./img/no-video.jpg"]'))
                    )
                    src_value = no_video_image.get_attribute("src")
                    if "no-video.jpg" in src_value:
                        print(f"Detectado 'no-video.jpg' para la cámara '{nombre_camara}'")
                        camaras_sin_video.append(nombre_camara)

            except Exception as e:
                print(f"Error al hacer clic sobre cámara: {e}")

            # Cerrar la ventana actual antes de pasar a la siguiente
            try:
                close_button = WebDriverWait(driver, 10).until(
                    EC.element_to_be_clickable((By.CSS_SELECTOR, ".webix_icon.mdi.mdi-close"))
                )
                close_button.click()
            except Exception as e:
                print(f"Error al cerrar la ventana: {e}")

        # Guardar las cámaras sin video en un archivo de texto
        if camaras_sin_video:
            archivo = "camaras_sin_video.txt"
            with open(archivo, mode="w", encoding="utf-8") as f:
                for camara in camaras_sin_video:
                    f.write(camara + "\n")

            pytest.fail(f"Se ha hecho una comprobación de las cámaras.Se ha creado el archivo '{archivo}' con las cámaras sin video.")

    except Exception as e:
        pytest.fail(f"Fallo durante la prueba: {e}")
