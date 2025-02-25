'''
Este archivo tiene como objetivo crear una sesion en Selenium que se comparte con todos los test
'''
import configparser
from configparser import RawConfigParser
from configparser import NoSectionError
from configparser import NoOptionError
from typing import List
from typing import Generator
from typing import Tuple
import pytest
from selenium import webdriver
from selenium.webdriver.firefox.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webelement import WebElement
from selenium.webdriver.firefox.webdriver import WebDriver
from selenium.common.exceptions import WebDriverException
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import TimeoutException
from selenium.common.exceptions import ElementNotInteractableException

def open_scada(ip: str) -> WebDriver:
    '''
    Esta función crea una sesion en Firefox y abre la web del SCADA

    Args:
        ip (str): IP donde se ubica el SCADA

    Returns:
        WebDriver: Devuelve el WebDriver de Firefox
    '''
    options = Options()
    options.add_argument("--headless")  # Asegurar que Firefox corra en modo headless
    options.add_argument("--width=1920")  # Establecer tamaño de ventana
    options.add_argument("--height=1080")  # Evita problemas de renderizado
    options.binary_location = "/usr/bin/firefox"  # Asegúrate de que esta ruta sea correcta en tu entorno

    driver: WebDriver = webdriver.Firefox(options=options)
    driver.get(f"http://{ip}:8090/openits/login.html")

    WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.NAME, "user"))
    )

    return driver

def get_properties() -> List[str]:
    '''
    Esta función lee los parámetros necesarios del archivo config.properties 
    para su posterior uso

    Returns:
        List[str]: Devuelve una lista de strings con los valores 
        del archivo config.properties
    '''
    try:
        config: RawConfigParser = configparser.RawConfigParser()
        config.read('config.properties', encoding='utf-8')
        
        # Acceder a los valores
        ip: str = config.get('Config', 'ip')
        user: str = config.get('Config', 'user')
        password: str = config.get('Config', 'password')
        management_area: str = config.get('Management Areas', 'management_area')
        management_area_name: str = config.get('Management Areas', 'management_area_name')
        state: str = config.get('Equipment state', 'state')
        custom_trees: str = config.get('Equipment', 'custom_trees')

        return [ip, user, password, management_area, management_area_name, state, custom_trees]

    except NoSectionError as e:
        pytest.fail(f'Error al leer la configuración del archivo config.properties,\
                    sección no encontrada: {e}')
    except NoOptionError as e:
        pytest.fail(f'Error al leer la configuración del archivo config.properties,\
                    opción no encontrada: {e}')

def login(driver: WebDriver, user: str, password: str) -> None:
    '''
    Esta función logea al usuario en el sistema SCADA

    Args:
        driver (WebDriver): Driver Firefox
        user (str): Nombre del usuario
        password (str): Contraseña del usuario
    '''
    try:
        driver.find_element(By.NAME, "user").send_keys(user)
        driver.find_element(By.NAME, "password").send_keys(password)
        driver.find_element(By.CSS_SELECTOR, "button").click()

        login_result: WebElement = driver.find_element(By.XPATH, '//*[contains(@view_id, "alertText")]/div')

        if login_result.text in ['Unauthorized', 'Usuario no autorizado']:
            pytest.fail("Usuario o contraseña no válidos")
    except NoSuchElementException as e:
        pytest.fail(f"No se pudo encontrar un elemento en la página: {e}")

def select_management_area(driver: WebDriver, management_area: str, management_area_name: str) -> None:
    '''
    Esta función lee el parametro "management_area" del archivo config.properties 
    para determinar si existen o no areas de gestion, si existen comprobara la propiedad 
    "management_area_name" y entrará, si no existen no ejecuta lógica

    Args:
        driver (WebDriver): Driver Firefox
        management_area (str): Indica si existen areas de gestion
        management_area_name (str): Nombre del area de gestion
    '''
    try:
        if management_area == 'True':
            driver.find_element(By.XPATH, '//*[contains(@view_id, "mgr-area-select")]').click()
            areas_de_gestion: List[WebElement] = driver.find_elements(By.XPATH, '//*[contains(@class, "form-area-combo-list")]/div/div/div/div/div')

            for area in areas_de_gestion:
                if area.text == management_area_name:
                    area.click()

            driver.find_element(By.XPATH, '//*[contains(@class, "form-accept-button")]').click()
        else:
            pass

    except NoSuchElementException as e:
        pytest.fail(f"No se pudo encontrar un elemento en la página: {e}")

@pytest.fixture(scope="function")
def firefox_browser() -> Generator[Tuple[WebDriver, str, str], None, None]:
    '''
    Funcion principal, incluye un fixture que se pasará a todos los test
    con toda la lógica incluida en la función

    Returns:
        Generator[Tuple[WebDriver, str, str], None, None]: Generador con el driver de Firefox, 
        el estado de los equipos y si esta activo el arbol custom de los equipos
    '''
    try:
        ip, user, password, management_area, management_area_name, state, custom_trees = get_properties()

        driver: WebDriver = open_scada(ip)

        login(driver, user, password)

        driver.implicitly_wait(1)

        select_management_area(driver, management_area, management_area_name)

        # Yield the WebDriver instance
        yield driver, state, custom_trees

    except TimeoutException as e:
        pytest.fail(f"La operación ha superado el tiempo de espera: {e}")
    except ElementNotInteractableException as e:
        pytest.fail(f"No se pudo interactuar con el elemento: {e}")
    except WebDriverException:
        pytest.fail("No se ha podido conectar con un sistema SCADA en la dirección IP solicitada")
    except Exception as e:
        pytest.fail(f"Ocurrió un error inesperado: {e}")
    finally:
        driver.quit()