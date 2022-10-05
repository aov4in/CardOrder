package ru.netology.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.cssSelector;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll(){
        WebDriverManager.chromedriver().setup();
    }



    @BeforeEach
    void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown(){
        driver.quit();
        driver = null;
    }

    @Test
    void shouldCardOrderTest(){
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Андреев Андрей");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79123456789");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(cssSelector("[data-test-id=order-success]")).getText();
        String expectedMessage = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldValidateNameIsEngland() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Andreev Andrei");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79123456789");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(cssSelector("[data-test-id=name] .input__sub")).getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldValidateNoName() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79123456789");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(cssSelector("[data-test-id=name] .input__sub")).getText();
        String expectedMessage = "Поле обязательно для заполнения";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldValidateNoPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Андреев Андрей");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(cssSelector("[data-test-id=phone] .input__sub")).getText();
        String expectedMessage = "Поле обязательно для заполнения";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldValidateNoPlusPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Андреев Андрей");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("12345678911");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(cssSelector("[data-test-id=phone] .input__sub")).getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldValidateNameIsNumber() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("123456");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79123456789");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(cssSelector("[data-test-id=name] .input__sub")).getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldValidateNumberPhoneElevenSymbol() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Андреев Андрей");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79879");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(cssSelector("[data-test-id=phone] .input__sub")).getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldValidateNumberIsText() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Андреев Андрей");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("Андреев Андрей");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(cssSelector("[data-test-id=phone] .input__sub")).getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldValidateNoCheckBox() {
        driver.get("http://localhost:9999/");
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Андреев Андрей");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79123456789");
//        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(className("checkbox__text")).getText();
        String expectedMessage = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expectedMessage, actualMessage);
    }

}