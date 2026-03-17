package com.example.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSignupTest {
    private static WebDriver driver;

    @BeforeAll
    static void setupDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        // Enable headless mode by default to keep CI environments stable.
        // Set -Dshow.browser=true to show the browser window.
        if (!Boolean.getBoolean("show.browser")) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
        }

        options.addArguments("--window-size=1200,800");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    void reset() {
        // Nothing to reset for pure static HTML pages.
    }

    @Test
    void login_success_showsSuccessMessage() {
        openPage("login.html");

        fillInput("username", "user1");
        fillInput("password", "secret123");

        clickSubmit();

        WebElement message = driver.findElement(By.id("message"));
        assertTrue(message.isDisplayed(), "Expected a message to be displayed");
        assertEquals("Login successful! Redirecting...", message.getText());
    }

    @Test
    void signup_success_showsSuccessMessage() {
        openPage("signup.html");

        fillInput("username", "newuser");
        fillInput("email", "newuser@example.com");
        fillInput("password", "strongpass");

        clickSubmit();

        WebElement message = driver.findElement(By.id("message"));
        assertTrue(message.isDisplayed(), "Expected a message to be displayed");
        assertEquals("Account created! Redirecting to login...", message.getText());
    }

    @Test
    void login_invalidPassword_showsErrorMessage() {
        openPage("login.html");

        fillInput("username", "user1");
        fillInput("password", "123");

        clickSubmit();

        WebElement message = driver.findElement(By.id("message"));
        assertTrue(message.isDisplayed(), "Expected a message to be displayed");
        assertEquals("Password must be at least 4 characters.", message.getText());
    }

    private void openPage(String fileName) {
        Path path = Paths.get("src", "main", "resources", "pages", fileName).toAbsolutePath();
        driver.get(path.toUri().toString());
    }

    private void fillInput(String name, String value) {
        WebElement input = driver.findElement(By.name(name));
        input.clear();
        input.sendKeys(value);
    }

    private void clickSubmit() {
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        button.click();

        // Wait for JS to update the page
        try {
            Thread.sleep(650);
        } catch (InterruptedException ignored) {
        }
    }
}
