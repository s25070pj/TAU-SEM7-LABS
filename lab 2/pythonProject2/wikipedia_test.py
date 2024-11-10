import unittest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


def test_wikipedia_content_navigation(self):
    for browser, driver in self.drivers.items():
        print(f"Running test on: {browser}")
        wait = WebDriverWait(driver, 10)

        driver.get("https://www.wikipedia.org/")
        self.assertIn("Wikipedia", driver.title, "Title did not contain 'Wikipedia'")

        search_box = wait.until(EC.visibility_of_element_located((By.ID, "searchInput")))
        self.assertIsNotNone(search_box, "Search box not found on Wikipedia homepage")
        search_box.send_keys("Python programming")
        search_box.submit()

        article_link = wait.until(EC.visibility_of_element_located((By.LINK_TEXT, "Python (programming language)")))
        self.assertIsNotNone(article_link, "Python (programming language) link not found in search results")
        article_link.click()

        first_heading = wait.until(EC.visibility_of_element_located((By.ID, "firstHeading")))
        self.assertEqual("Python (programming language)", first_heading.text, "Heading did not match expected text")

        history_section = wait.until(EC.presence_of_element_located((By.ID, "History")))
        self.assertIsNotNone(history_section, "History section not found in the article")

        external_links_section = driver.find_element(By.ID, "External_links")
        self.assertIsNotNone(external_links_section, "External links section not found in the article")

        external_links_list = external_links_section.find_element(By.XPATH, "following-sibling::ul")
        self.assertGreater(len(external_links_list.find_elements(By.TAG_NAME, "li")), 0,
                           "No external links found in the 'External links' section")

        references_section = driver.find_element(By.ID, "References")
        self.assertIsNotNone(references_section, "References section not found in the article")

for browser in [webdriver.Chrome, webdriver.Edge, webdriver.Firefox]:
    driver = browser()
    try:
        test_wikipedia_content_navigation(driver)
        print(f"{driver.name} - Test success")
    finally:
        driver.quit()
