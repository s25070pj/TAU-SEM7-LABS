from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

def checkBiedronkaShopping(browser_name, driver):
    driver.get("https://www.amazon.com")
    driver.maximize_window()

    try:
        privacy_accept_button = WebDriverWait(driver, 10).until(
            EC.visibility_of_element_located((By.ID, "onetrust-accept-btn-handler"))
        )
        assert privacy_accept_button.is_displayed(), f"{browser_name}: Przyciski akceptacji polityki prywatności nie jest widoczny"
        privacy_accept_button.click()
    except:
        print(f"{browser_name}: Przyciski akceptacji polityki prywatności nie pojawił się")

    assert "Biedronka" in driver.title, f"{browser_name}: Tytuł strony nie zawiera słowa 'Biedronka'"

    flyer_section = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.LINK_TEXT, "Gazetka"))
    )
    assert flyer_section.is_displayed(), f"{browser_name}: Sekcja 'Gazetka' nie jest widoczna"
    flyer_section.click()

    assert WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.CLASS_NAME, "catalogs-title"))
    ), f"{browser_name}: Strona gazetki nie załadowała się poprawnie"

    first_page = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.XPATH, "//*[@alt='Strona 1']"))
    )
    assert first_page.is_displayed(), f"{browser_name}: 'Strona 1' gazetki nie jest widoczna"
    first_page.click()

    next_page_button = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.XPATH, "//*[@aria-label='Następna strona']"))
    )
    assert next_page_button.is_displayed(), f"{browser_name}: Przycisk następnej strony nie jest widoczny"
    next_page_button.click()

    assert WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.XPATH, "//*[@alt='Strona 2']"))
    ), f"{browser_name}: 'Strona 2' gazetki nie jest widoczna"

    previous_page_button = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.XPATH, "//*[@aria-label='Poprzednia strona']"))
    )
    assert previous_page_button.is_displayed(), f"{browser_name}: Przycisk poprzedniej strony nie jest widoczny"
    previous_page_button.click()

    assert WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.XPATH, "//*[@alt='Strona 1']"))
    ), f"{browser_name}: Powrót do 'Strony 1' nie działa poprawnie"

    driver.quit()

for browser in [webdriver.Chrome, webdriver.Edge, webdriver.Firefox]:
    driver = browser()
    try:
        amazon_test(driver)
        print(f"{driver.name} - Test success")
    finally:
        driver.quit()