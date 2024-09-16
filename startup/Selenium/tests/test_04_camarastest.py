import pytest
import time
import re
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait

class TestCamarastest():
    def test_camarastest(self,firefox_browser):
        # Esperar a que la página de equipos esté completamente cargada
        try:
            WebDriverWait(firefox_browser, 60).until(
                EC.url_contains("/home.html")
            )
            WebDriverWait(firefox_browser, 60).until(
                EC.presence_of_element_located((By.CLASS_NAME, 'graphic-icon-tooltip'))
            )
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

        # Esperar a que aparezcan los elementos con "ElementType"
        try:
            WebDriverWait(firefox_browser, 60).until(
                EC.presence_of_all_elements_located((By.CSS_SELECTOR, '[webix_l_id*="ElementType:"]'))
            )
            div_padre = firefox_browser.find_element(By.XPATH, '//*[contains(@view_id, "view-main-list-equipments")]')
            div_hijo = div_padre.find_element(By.XPATH, './div[2]').find_element(By.XPATH, './div[1]').find_element(By.XPATH, './div[1]').find_element(By.XPATH, './div[1]').find_element(By.XPATH, './div[1]').find_element(By.XPATH, './div[1]')
            div_search_box = div_hijo.find_element(By.CLASS_NAME, 'webix_el_box')
            input_text = div_search_box.find_element(By.XPATH, './input')
            input_text.send_keys('Cámara')
            elements = WebDriverWait(firefox_browser, 60).until(
                EC.presence_of_all_elements_located((By.CSS_SELECTOR, ".webix_first > .webix_cell:nth-child(1)"))
            )
            # Re-localizar el elemento "ElementType:2" antes de hacer clic
            firefox_browser.find_element(By.CSS_SELECTOR, '[webix_l_id*="ElementType:2"]').click()
            equipos = WebDriverWait(firefox_browser, 60).until(
                EC.presence_of_all_elements_located((By.CSS_SELECTOR, ".webix_first > .webix_cell:nth-child(1)"))
            )

            # Lista para almacenar las cámaras sin video
            camaras_sin_video = []

            # Iterar sobre los primeros 5 elementos para verificar si la cámara tiene video
            for i in range(1, 6):
                try:
                    # Hacer clic en el elemento
                    equipo_selector = f".webix_first > .webix_cell:nth-child({i})"
                    WebDriverWait(firefox_browser, 60).until(
                        EC.element_to_be_clickable((By.CSS_SELECTOR, equipo_selector))
                    ).click()
                    
                    # Obtener el nombre de la cámara
                    nombre_camara = firefox_browser.find_element(By.CSS_SELECTOR, f"{equipo_selector} .table-row-text").text
                    
                    # Doble clic para seleccionar el equipo
                    time.sleep(0.5)  # Esperar un momento para asegurarse de que el elemento esté completamente visible
                    element = firefox_browser.find_element(By.CSS_SELECTOR, equipo_selector)
                    actions = ActionChains(firefox_browser)
                    actions.double_click(element).perform()

                    try:
                        video_image = WebDriverWait(firefox_browser, 5).until(
                                EC.presence_of_element_located((By.CSS_SELECTOR, 'img.camviewer-img'))
                            )
                        src_value = video_image.get_attribute("src")
                        print(f"Se encontró una imagen de video para la cámara '{nombre_camara}': {src_value}")  # Trazas de depuración                        
                    except Exception:
                        no_video_image = WebDriverWait(firefox_browser, 5).until(
                        EC.presence_of_element_located((By.CSS_SELECTOR, 'img[src="./img/no-video.jpg"]'))
                        )
                        src_value = no_video_image.get_attribute("src")
                        if "no-video.jpg" in src_value:
                            print(f"Detectado 'no-video.jpg' para la cámara '{nombre_camara}'")  # Trazas de depuración
                            camaras_sin_video.append(nombre_camara)
                except Exception as e:
                        print(f"error al hacer clic sobre cámara: {e}")
                
                # Cerrar la ventana actual antes de pasar a la siguiente
                try:
                    close_button = WebDriverWait(firefox_browser, 10).until(
                        EC.element_to_be_clickable((By.CSS_SELECTOR, ".webix_icon.mdi.mdi-close"))
                    )
                    close_button.click()
                except Exception as e:
                    pytest.fail(f"Error al cerrar la ventana: {e}")

            # Reportar las cámaras sin video al final
            if camaras_sin_video:
                pytest.fail(f"Se ha hecho una comprobacion de las primeras 5 cámaras.Las siguientes cámaras no tienen video: {', '.join(camaras_sin_video)}")

        except Exception as e:
            pytest.fail(f"Fallo durante la prueba: {e}")
