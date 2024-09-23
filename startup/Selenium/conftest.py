import pytest  
from selenium import webdriver  
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
  

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
def firefox_browser(request, ip, user, password):  
    driver = webdriver.Firefox()    
    driver.implicitly_wait(0.5)  
    driver.get(f"http://{ip}:8090/openits/login.html")
    driver.maximize_window()
    driver.implicitly_wait(0.5)
    try:
        WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.NAME, "user"))
        )
        driver.find_element(By.NAME, "user").send_keys({user})
        driver.find_element(By.NAME, "password").send_keys({password})
        driver.find_element(By.CSS_SELECTOR, "button").click()

    except Exception as e:
        print(f"Error al iniciar sesión: {e}")
        return
    
    # Yield the WebDriver instance  
    yield driver  

    # Close the WebDriver instance  
    driver.quit()