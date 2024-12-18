package com.antonopoulos;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PropertySearchTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        // Set up ChromeDriver
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testPropertySearch() {
        // Navigate to the website
        driver.get("https://www.xe.gr/property/");

        // Locate the search box and input "Παγκράτι"
        WebElement searchBox = driver.findElement(By.id("search-box-id"));
        searchBox.sendKeys("Παγκράτι");

        // Wait for autocomplete and select all areas
        List<WebElement> autoCompleteOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".autocomplete-option-selector"))); // Update with correct selector
        for (WebElement option : autoCompleteOptions) {
            option.click();
        }

        // Set price range: €200 to €700
        WebElement minPrice = driver.findElement(By.id("min-price-id"));
        WebElement maxPrice = driver.findElement(By.id("max-price-id"));
        minPrice.sendKeys("200");
        maxPrice.sendKeys("700");

        // Set square footage: 75m2 to 150m2
        WebElement minArea = driver.findElement(By.id("min-area-id"));
        WebElement maxArea = driver.findElement(By.id("max-area-id"));
        minArea.sendKeys("75");
        maxArea.sendKeys("150");

        // Submit search
        WebElement searchButton = driver.findElement(By.id("search-button-id"));
        searchButton.click();

        // Verify search results
        List<WebElement> results = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".search-result-item-selector"))); // Update with correct selector
        for (WebElement result : results) {
            // Verify price range
            int price = Integer.parseInt(result.findElement(By.cssSelector(".price-selector")).getText().replace("€", "").trim());
            Assert.assertTrue(price >= 200 && price <= 700, "Price out of range: " + price);

            // Verify square footage
            int area = Integer.parseInt(result.findElement(By.cssSelector(".area-selector")).getText().replace("m2", "").trim());
            Assert.assertTrue(area >= 75 && area <= 150, "Area out of range: " + area);

            // Verify number of images
            List<WebElement> images = result.findElements(By.cssSelector(".image-carousel img"));
            Assert.assertTrue(images.size() <= 30, "Too many images: " + images.size());
        }

        // Sort by descending price
        WebElement sortDropdown = driver.findElement(By.id("sort-dropdown-id"));
        sortDropdown.click();
        WebElement descendingPriceOption = driver.findElement(By.id("desc-price-option-id"));
        descendingPriceOption.click();

        // Verify sorting
        List<WebElement> sortedResults = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".search-result-item-selector")));
        int previousPrice = Integer.MAX_VALUE;
        for (WebElement result : sortedResults) {
            int price = Integer.parseInt(result.findElement(By.cssSelector(".price-selector")).getText().replace("€", "").trim());
            Assert.assertTrue(price <= previousPrice, "Results not sorted by descending price");
            previousPrice = price;
        }

        // Bonus: Verify contact phone visibility
        for (WebElement result : results) {
            WebElement phoneButton = result.findElement(By.cssSelector(".phone-button-selector"));
            Assert.assertTrue(phoneButton.isDisplayed(), "Phone button not visible");
            phoneButton.click();
            WebElement phonePopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".phone-popup-selector")));
            Assert.assertTrue(phonePopup.isDisplayed(), "Phone popup not displayed after click");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
