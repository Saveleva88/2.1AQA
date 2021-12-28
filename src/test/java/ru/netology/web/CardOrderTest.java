package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.cssSelector;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup(); // не надо качивать драйвер и вкладывать в отдельную папку
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendForm() {
        driver.get("http://localhost:9999/");
        System.out.println("");
    }

    @Test
    void shouldSendSuccessfulForm() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Фомина Ирина");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79258745098");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    void shouldSendSuccessfulFormWithHyphen() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Фомина София-Ирина");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79258745098");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, text);
    }

    @Test
    void shouldSendFormWithoutName() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79258745098");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    void shouldSendFormWithoutPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Фомина Ирина");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    void  shouldSendFormWithoutCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Фомина Ирина");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79258745098");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='agreement'] .checkbox__text")).getText().trim();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expected, text);
    }

    @Test
    void shouldSendFormWithoutAll() {
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, text);
    }

    @Test
    void shouldSendFormOnEnglishName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Fomina Irina");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79258745098");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, text);
    }

    @Test
    void shouldSendFormWithPartPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Фомина Ирина");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79258745098");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, text);
    }
}