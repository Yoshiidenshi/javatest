package org.ibs.jdbc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import java.sql.*;
import java.util.concurrent.TimeUnit;


public class General {
    static WebDriver driver;
    static Connection connection;
    static ResultSet resultSet;
    static Statement statement;

    @BeforeEach
    void connectDB() throws SQLException {
        driver = new EdgeDriver();
        System.setProperty("webdriver.msedgedriver.driver","\\src\\test\\resources\\msedgedriver.exe");

        driver.manage().window().maximize();
        driver.get("http://localhost:8080");
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/mem:testdb","user","pass");
        statement = connection.createStatement();
    }

    @AfterEach
    void clear() throws SQLException {
        WebElement buttonSandboxAgain = driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
        buttonSandboxAgain.click();

        WebElement buttonClear = driver.findElement(By.xpath("//a[@id='reset']"));
        buttonClear.click();

        driver.quit();
        connection.close();
    }
}