import pytest  
from selenium import webdriver  
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
  
  
@pytest.fixture(scope="function") 
def firefox_browser():  
    driver = webdriver.Firefox()    
    driver.implicitly_wait(0.5)  
    driver.get("http://192.168.88.201:8090/openits/login.html")
    driver.maximize_window()
    driver.implicitly_wait(0.5)
    try:
        WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.NAME, "user"))
        )
        driver.find_element(By.NAME, "user").send_keys("admin")
        driver.find_element(By.NAME, "password").send_keys("Revenga.19")
        driver.find_element(By.CSS_SELECTOR, "button").click()

    except Exception as e:
        print(f"Error al iniciar sesión: {e}")
        return
    
    # Yield the WebDriver instance  
    yield driver  

    # Close the WebDriver instance  
    driver.quit()