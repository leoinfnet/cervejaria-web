package br.com.acme.cervejariaacme.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class SeleniumSimpleTest {
    private WebDriver driver;
    private WebDriverWait wait;
    @BeforeAll
    static void init(){
      log.info("Before All");
        WebDriverManager.chromedriver().setup();
        //WebDriverManager.safaridriver().setup();
       // WebDriverManager.firefoxdriver().setup();
    }
    @BeforeEach
    public void setup(){

        //driver = new FirefoxDriver();
        driver = new ChromeDriver();
       // driver = new SafariDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    @Test@DisplayName("Teste Simples")
    public void testaSimples() throws InterruptedException {
        driver.get("http://localhost:8091/");
        assertEquals("ACME co", driver.getTitle());
        assertEquals("http://localhost:8091/login", driver.getCurrentUrl());
        Thread.sleep(2000);
        WebElement spamLogout = driver.findElement(By.id("logoutTxt"));
        WebElement nomeUsuario = driver.findElement(By.id("userName"));
        WebElement currentPage = driver.findElement(By.id("currentPage"));
        log.info(nomeUsuario.getText());
        log.info(currentPage.getText());

        assertTrue(spamLogout.isDisplayed());
    }
    @AfterEach
    void tearDown(){
        driver.quit();
    }



}
