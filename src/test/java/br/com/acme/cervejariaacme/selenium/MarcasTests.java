package br.com.acme.cervejariaacme.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class MarcasTests {
    private WebDriver driver;
    private WebDriverWait wait;
    @BeforeAll
    static void init(){
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    @Test
    public void testaCadastrar() throws InterruptedException {
        navegarLogin();
        Thread.sleep(200);
        WebElement marcasBtn = driver.findElement(By.xpath("//span[text()='Marcas']/ancestor::a"));
        marcasBtn.click();

        WebElement tiposCervejaTable = driver.findElement(By.id("tiposCervejaTable"));
        List<WebElement> linhas = tiposCervejaTable.findElements(By.tagName("tr"));
        int qtdLinhas = linhas.size() - 1;
        WebElement adicionarBtn = driver.findElement(By.xpath("//a[text()='Adicionar Marca de Cerveja']"));
        adicionarBtn.click();
        assertEquals(driver.getCurrentUrl(),"http://localhost:8091/marcas/adicionarForm");
        WebElement nome = driver.findElement(By.id("nomeMarca"));
        WebElement pais = driver.findElement(By.id("paisMarca"));
        WebElement email = driver.findElement(By.id("emailMarca"));
        WebElement dataFundacao = driver.findElement(By.id("dtfundacaoMarca"));

        nome.sendKeys("Marca Teste");
        pais.sendKeys("Brasil");
        email.sendKeys("marca@acme.com");
        dataFundacao.sendKeys("2020-05-05");
        Actions actions = new Actions(driver);
        actions.moveByOffset(100,100).click().perform();
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Adicionar']"));
        submitButton.click();


        Thread.sleep(2000);
        tiposCervejaTable = driver.findElement(By.id("tiposCervejaTable"));
        linhas = tiposCervejaTable.findElements(By.tagName("tr"));
        int noaQtd = linhas.size() - 1;

        assertEquals(qtdLinhas,noaQtd  );
        log.info(String.valueOf(qtdLinhas));
        log.info(String.valueOf(linhas.size()));


    }
    private void navegarLogin(){
        driver.get("http://localhost:8091/");
        WebElement email = driver.findElement(By.id("email"));
        WebElement senha = driver.findElement(By.id("password"));
        WebElement botaoLogar = driver.findElement(By.id("logarBtn"));

        email.sendKeys("administrador@acme.com");
        senha.sendKeys("123456");
        botaoLogar.click();



    }
    @AfterEach
    void tearDown(){
        driver.quit();
    }

}
