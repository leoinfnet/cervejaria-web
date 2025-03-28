package br.com.acme.cervejariaacme.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class LoginTests {
    private WebDriver driver;
    private WebDriverWait wait;
    @BeforeAll
    static void init(){
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    @Order(2)
    @Test@DisplayName("Deve se logar")
    public void testaLoginSucesso() throws InterruptedException {
        driver.get("http://localhost:8091/");
        WebElement email = driver.findElement(By.id("email"));
        WebElement senha = driver.findElement(By.id("password"));
        WebElement botaoLogar = driver.findElement(By.id("logarBtn"));

        email.sendKeys("administrador@acme.com");
        senha.sendKeys("123456");
        botaoLogar.click();
       // Thread.sleep(3000);

        assertEquals("ACME co", driver.getTitle());

        WebElement spamLogout = driver.findElement(By.id("logoutTxt"));
        WebElement nomeUsuario = driver.findElement(By.id("userName"));
        WebElement currentPage = driver.findElement(By.id("currentPage"));
        assertEquals("Patolino", nomeUsuario.getText());
        assertEquals("Dashboard", currentPage.getText());
        assertTrue(spamLogout.isDisplayed());

    }
    @Order(1)
    @Test@DisplayName("Nao Deve  deve se logar")
    public void testaLoginError() throws InterruptedException {
        driver.get("http://localhost:8091/");
        String currentUrl = driver.getCurrentUrl();
        WebElement email = driver.findElement(By.id("email"));
        WebElement senha = driver.findElement(By.id("password"));
        WebElement botaoLogar = driver.findElement(By.id("logarBtn"));

        email.sendKeys("administrador@acme.com");
        senha.sendKeys("1234568");
        botaoLogar.click();

        assertEquals(currentUrl, driver.getCurrentUrl());


    }
    @AfterEach
    void tearDown(){
        driver.quit();
    }
}
