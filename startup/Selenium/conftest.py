import pytest  
from selenium import webdriver  
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.common.exceptions import WebDriverException, NoSuchElementException, TimeoutException, ElementNotInteractableException
import configparser
from configparser import NoOptionError

def pytest_addoption(parser):
    parser.addoption(
        "--ip", action="store", default=None, help="Dirección IP para el login",
    )
    parser.addoption(
        "--user", action="store", default=None, help="Usuario el login",
    )
    parser.addoption(
        "--password", action="store", default=None, help="Contraseña el login",
    )
    parser.addoption(
        "--state", action="store", default=None, help="Estado a buscar")

@pytest.fixture(scope="function")
def ip(request):
    return request.config.getoption("--ip")
@pytest.fixture(scope="function")
def user(request):
    return request.config.getoption("--user")
@pytest.fixture(scope="function")
def password(request):
    return request.config.getoption("--password")
@pytest.fixture(scope="function")
def state(request):
    return request.config.getoption("--state")

@pytest.fixture(scope="function") 
def firefox_browser(request):  

    config = configparser.RawConfigParser()
    config.read('config.properties', encoding='utf-8')

    try:
        # Acceder a los valores
        ip = config.get('Config', 'ip')
        user = config.get('Config', 'user')
        password = config.get('Config', 'password')
        management_area = config.get('Management Areas', 'management_area')
        management_area_name = config.get('Management Areas', 'management_area_name')
        state = config.get('Equipment state', 'state')
    except NoOptionError as e:
        pytest.fail(f'Error al leer la configuración del archivo config.properties: {e}')
        
   
    try:

        driver = webdriver.Firefox()    
        driver.maximize_window()
        driver.get(f"http://{ip}:8090/openits/login.html")

        WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.NAME, "user"))
        )

        driver.find_element(By.NAME, "user").send_keys(user)
        driver.find_element(By.NAME, "password").send_keys(password)
        driver.find_element(By.CSS_SELECTOR, "button").click()

    
        login_result = driver.find_element(By.XPATH, '//*[contains(@view_id, "alertText")]/div')

        if login_result.text in ['Unauthorized', 'Usuario no autorizado']:
            pytest.fail("Usuario o contraseña no válidos")

        driver.implicitly_wait(1)

        if management_area == 'True':
            driver.find_element(By.XPATH, '//*[contains(@view_id, "mgr-area-select")]').click()
            areas_de_gestion = driver.find_elements(By.XPATH, '//*[contains(@class, "form-area-combo-list")]/div/div/div/div/div')
            
            for area in areas_de_gestion:
                if area.text == management_area_name:
                    area.click()
            
            driver.find_element(By.XPATH, '//*[contains(@class, "form-accept-button")]').click()
        else:
            pass

        
        # Yield the WebDriver instance  
        yield driver, management_area, state

    except WebDriverException:
        pytest.fail(f"No se ha podido conectar con un sistema SCADA en la dirección IP solicitada")
    except NoSuchElementException as e:
        pytest.fail(f"No se pudo encontrar un elemento en la página: {e}")
    except TimeoutException as e:
        pytest.fail(f"La operación ha superado el tiempo de espera: {e}")
    except ElementNotInteractableException as e:
        pytest.fail(f"No se pudo interactuar con el elemento: {e}")
    except Exception as e:
        pytest.fail(f"Ocurrió un error inesperado: {e}")
    finally:
        driver.quit()