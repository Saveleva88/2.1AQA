package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.openqa.selenium.By.cssSelector;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestV1() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(cssSelector("form"));
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Фомина Ирина");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79258745098");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector("[data-test-id='order-success']")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", message.strip());
    }
    @Test
    void shouldTestV2() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(cssSelector("form"));
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79258745098");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", message.strip());
    }

    @Test
    void shouldTestV3() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(cssSelector("form"));
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Фомина Ирина");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", message.strip());
    }

    @Test
    void shouldTestV4() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(cssSelector("form"));
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Фомина Ирина");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79258745098");
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input_invalid")).getText();
        Assertions.assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", message.strip());
    }

    @Test
    void shouldTestV5() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(cssSelector("form"));
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input__sub")).getText();
        Assertions.assertEquals("Поле обязательно для заполнения", message.strip());
    }

    @Test
    void shouldTestV6() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(cssSelector("form"));
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Fomina Irina");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("+79258745098");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        Assertions.assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", message.strip());
    }

    @Test
    void shouldTestV7() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(cssSelector("form"));
        driver.findElement(cssSelector("[data-test-id='name'] input")).sendKeys("Фомина Ирина");
        driver.findElement(cssSelector("[data-test-id='phone'] input")).sendKeys("79258745098");
        driver.findElement(cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(cssSelector("button")).click();
        String message = driver.findElement(cssSelector(".input_invalid .input__sub")).getText();
        Assertions.assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", message.strip());
    }
}