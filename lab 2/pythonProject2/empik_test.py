from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time

def empik_test(driver):
    driver.get('https://www.empik.com/')
    driver.maximize_window()
    assert driver.current_url == "https://www.empik.com/", "URL is not correct"
    time.sleep(1)

    assert "Empik" in driver.title, "Page title does not contain 'Empik'"

    header = driver.find_element(By.TAG_NAME, "header")
    assert header.is_displayed(), "Header is not visible"

    try:
        accept_button = WebDriverWait(driver, 1).until(
            EC.element_to_be_clickable((By.CLASS_NAME, "css-18n58r"))
        )
        assert accept_button.is_displayed()
        assert accept_button.is_enabled()
        accept_button.click()
        print("Cookies accepted")
    except Exception as e:
        print("Cookie acceptance button not found:", e)

    time.sleep(1)

    search_field = driver.find_element(By.CLASS_NAME, "css-1sobvo3")
    assert search_field.is_displayed(), "Search field is not visible"
    search_field.send_keys("Harry Potter")
    search_field.send_keys(Keys.RETURN)
    time.sleep(3)

    image = driver.find_element(By.TAG_NAME, "img")
    assert image.is_displayed(), "No image found on search results page"

    links = driver.find_elements(By.TAG_NAME, "a")
    assert len(links) > 0, "No links found on the page"

    business_button = driver.find_element(By.LINK_TEXT, "Biznes")
    assert business_button.is_displayed(), "'Biznes' button is not visible"
    business_button.click()

for browser in [webdriver.Chrome, webdriver.Edge, webdriver.Firefox]:
    driver = browser()
    try:
        empik_test(driver)
        print(f"{driver.name} - Test success")
    finally:
        driver.quit()
