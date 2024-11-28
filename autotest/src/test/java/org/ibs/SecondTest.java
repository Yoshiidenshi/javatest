package org.ibs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class SecondTest {
    private static WebDriver driver;
    @BeforeEach
    public void setUp () {
        driver = new EdgeDriver();
        System.setProperty("webdriver.edge.driver", "C:\\Users\\JackT\\IdeaProjects\\autotest\\src\\test\\resources\\msedgedriver.exe");
        driver.manage().window().maximize();
        driver.get("https://qualit.appline.ru/");
    }

    @Test
    public void test() throws InterruptedException {
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement sandboxMenu = driver.findElement(By.xpath("//li[@class='nav-item dropdown']"));
        sandboxMenu.click();

        WebElement sandboxProduct = driver.findElement(By.xpath("//a[@href='/food']"));
        sandboxProduct.click();

        WebElement addVeg = driver.findElement(By.xpath("//button[@data-target='#editModal']"));
        addVeg.click();

        WebElement selectInput = driver.findElement(By.xpath("//input[@placeholder='Наименование']"));
        selectInput.sendKeys("Римский кабачок");
        selectInput.click();

        WebElement selectType = driver.findElement(By.xpath("//select[@name='type']"));
        selectType.click();

        WebElement selectVegType = driver.findElement(By.xpath("//option[@value='VEGETABLE']"));
        selectVegType.click();

        WebElement exoticCheck = driver.findElement(By.xpath("//input[@id='exotic']"));
        if (!exoticCheck.isSelected()) {
            exoticCheck.click();
        }

        WebElement saveVeg = driver.findElement(By.xpath("//button[@id='save']"));
        saveVeg.click();

        Thread.sleep(500);

        addVeg = driver.findElement(By.xpath("//button[@data-target='#editModal']"));
        addVeg.click();

        selectInput = driver.findElement(By.xpath("//input[@placeholder='Наименование']"));
        selectInput.sendKeys("Картошка");
        selectInput.click();

        selectType = driver.findElement(By.xpath("//select[@name='type']"));
        selectType.click();

        selectVegType = driver.findElement(By.xpath("//option[@value='VEGETABLE']"));
        selectVegType.click();

        exoticCheck = driver.findElement(By.xpath("//input[@id='exotic']"));
        if (exoticCheck.isSelected()) {
            exoticCheck.click();
        }

        saveVeg = driver.findElement(By.xpath("//button[@id='save']"));
        saveVeg.click();

        WebElement exoticVegIsExists = driver.findElement(By.xpath("//td[contains(text(), 'Римский кабачок')]"));
        assertTrue(exoticVegIsExists.isDisplayed());

        WebElement nonexoticVegIsExists = driver.findElement(By.xpath("//td[contains(text(), 'Картошка')]"));
        assertTrue(nonexoticVegIsExists.isDisplayed());

        Thread.sleep(500);

    }

    @AfterEach
    public void clear() throws InterruptedException{
        WebElement sandboxMenu = driver.findElement(By.xpath("//li[@class='nav-item dropdown']"));
        sandboxMenu.click();

        WebElement sandboxMenuClear = driver.findElement(By.xpath("//a[@id='reset']"));
        sandboxMenuClear.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[contains(text(), 'Римский кабачок')]")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[contains(text(), 'Картошка')]")));
            System.out.println("Элементы не найдены");
            driver.quit();
        } catch (Exception e) {
            driver.quit();
            fail("Элементы найдены");
        }
    }
}
