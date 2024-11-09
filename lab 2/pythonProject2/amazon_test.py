from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
import time

def test_amazon(driver):
    driver.get("https://www.amazon.com")
    driver.maximize_window()

    try:
        accept_button = WebDriverWait(driver, 10).until(
            EC.element_to_be_clickable((By.ID, "sp-cc-accept"))
        )
        accept_button.click()
        print("Cookies accepted")
    except Exception as e:
        print("Cookie acceptance button not found or not applicable:", e)

    assert "Amazon" in driver.title, "The page title does not contain 'Amazon'"

    search_field = driver.find_element(By.ID, "twotabsearchtextbox")
    assert search_field.is_displayed(), "Search field is not visible"
    search_field.send_keys("laptop")
    search_field.send_keys(Keys.RETURN)
    time.sleep(2)

    assert "laptop" in driver.page_source, "Search results do not contain 'laptop'"

    first_result = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.XPATH, "(//span[@class='a-size-medium a-color-base a-text-normal'])[1]"))
    )
    assert first_result.is_displayed(), "First product result is not visible"
    first_result.click()
    time.sleep(2)

    assert "Laptop" in driver.title or "laptop" in driver.page_source, "The product page does not contain 'Laptop'"

    image = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.ID, "imgTagWrapperId"))
    )
    assert image.is_displayed(), "No image is displayed on the product page"

    navbar = driver.find_element(By.ID, "navbar")
    assert navbar.is_displayed(), "Navigation bar is not visible"

    footer = driver.find_element(By.ID, "navFooter")
    assert footer.is_displayed(), "Footer is not visible"

for browser in [webdriver.Chrome, webdriver.Firefox, webdriver.Edge]:
    driver = browser()
    try:
        test_amazon(driver)
        print(f"Test completed successfully in {driver.name}")
    finally:
        driver.quit()
