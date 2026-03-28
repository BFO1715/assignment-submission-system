package com.ass.assignmentsubmissionsystem.model;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class SeleniumLoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    // URL
    private static final String BASE_URL = "https://ass-app-bcfe6aca8257.herokuapp.com";

    // Valid student credentials
    private static final String STUDENT_EMAIL    = "darth.vader@university.ac.uk";
    private static final String STUDENT_PASSWORD = "Test123!";

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait   = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Go to login page
        driver.get(BASE_URL + "/login");

        // Wait for login page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));

        // Enter credentials
        WebElement emailField    = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement signInButton  = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys(STUDENT_EMAIL);
        passwordField.sendKeys(STUDENT_PASSWORD);
        signInButton.click();

        // Wait for redirect to student dashboard
        wait.until(ExpectedConditions.urlContains("/student/dashboard"));

        // Student dashboard
        assertTrue(driver.getCurrentUrl().contains("/student/dashboard"),
            "Should be redirected to student dashboard after login");

        System.out.println("Login test passed - redirected to: " + driver.getCurrentUrl());
    }

    @Test
    public void testLoginAndViewSubmissions() {
        // Navigate to login page
        driver.get(BASE_URL + "/login");

        // Wait for login page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));

        // Enter credentials and login
        driver.findElement(By.id("username")).sendKeys(STUDENT_EMAIL);
        driver.findElement(By.id("password")).sendKeys(STUDENT_PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Wait for student dashboard
        wait.until(ExpectedConditions.urlContains("/student/dashboard"));

        // Submissions section is present
        WebElement submissionsHeading = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h1[contains(text(),'My submissions')]")
            )
        );

        assertTrue(submissionsHeading.isDisplayed(),
            "My submissions section should be visible on the student dashboard");

        System.out.println("View submissions test passed - Submissions section found");
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Go to login page
        driver.get(BASE_URL + "/login");

        // Wait for login page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));

        // Enter invalid credentials
        driver.findElement(By.id("username")).sendKeys("invalid@university.ac.uk");
        driver.findElement(By.id("password")).sendKeys("wrongpassword");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Wait for error message
        wait.until(ExpectedConditions.urlContains("error"));

        // Check still on the login page with error
        assertTrue(driver.getCurrentUrl().contains("error"),
            "Should show error for invalid credentials");

        System.out.println("Invalid login test passed - error shown for invalid credentials");
    }
}
