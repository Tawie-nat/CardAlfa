
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebIntTest {

        WebDriver driver;

        @BeforeAll
        static void setupClass() {
            WebDriverManager.chromedriver().setup();
        }

        @BeforeEach
        void setupTest() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
        }

        @AfterEach
        void teardown() {
            if (driver != null) {
                driver.quit();
            }
        }

        @Test
        void shouldSendFormWithValidData() {
            driver.get("http://localhost:9999");
            List<WebElement> elements = driver.findElements(By.className("input__control"));
            elements.get(0).sendKeys("Иванова Алиса");
            elements.get(1).sendKeys("+79050001234");
            driver.findElement(By.className("checkbox__box")).click();
            driver.findElement(By.className("button")).click();

            String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
            String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.".trim();
            assertEquals(expected, actual);
        }
    @Test
    void shouldSendFormWithInvalidNameInEnglish() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Ivanova Alisa");
        elements.get(1).sendKeys("+79050001234");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.className("input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.".trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldSendFormWithValidNameWithDash() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иванова-Смирнова Алиса");
        elements.get(1).sendKeys("+79050001234");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.".trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldSendFormWithInvalidPhoneNumber() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иванова Алиса");
        elements.get(1).sendKeys("+7905000123");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector(".input_invalid[data-test-id=phone] .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.".trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldSendFormWithInvalidPhoneNumberWithoutPlus() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иванова Алиса");
        elements.get(1).sendKeys("79050001234");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector(".input_invalid[data-test-id=phone] .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.".trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldSendFormWithoutMarker() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иванова Алиса");
        elements.get(1).sendKeys("79050001234");
        driver.findElement(By.className("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement")).getTagName();
        String expected = driver.findElement(By.cssSelector("[data-test-id=agreement")).getTagName();
        assertEquals(expected, actual);
    }
    }

