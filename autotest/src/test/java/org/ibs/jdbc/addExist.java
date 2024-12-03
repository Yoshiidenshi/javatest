package org.ibs.jdbc;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class addExist extends General{
    @Test
    void addExistingProduct() throws SQLException, InterruptedException {
        WebElement SandboxMenu = driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
        SandboxMenu.click();

        WebElement sandboxProduct = driver.findElement(By.xpath("//a[@href='/food']"));
        sandboxProduct.click();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement addProduct = driver.findElement(By.xpath("//button[@data-toggle='modal']"));
        addProduct.click();

        WebElement selectInput = driver.findElement(By.xpath("//input[@id='name']"));
        selectInput.sendKeys("Апельсин");
        selectInput.click();

        WebElement selectType = driver.findElement(By.xpath("//option[@value='FRUIT']"));
        selectType.click();
        assertEquals("Фрукт", selectType.getText());

        WebElement exoticCheck = driver.findElement(By.xpath("//input[@id='exotic']"));
        if (!exoticCheck.isSelected()) {
            exoticCheck.click();
        }
        assertTrue(exoticCheck.isSelected());

        WebElement saveProduct = driver.findElement(By.xpath("//button[@id='save']"));
        saveProduct.click();

        Thread.sleep(300);

        resultSet = statement.executeQuery("SELECT COUNT(*) AS count_name FROM FOOD \n" +
                "WHERE FOOD_NAME = 'Апельсин' AND FOOD_TYPE = 'FRUIT' AND FOOD_EXOTIC ='1.00'");
        if (resultSet.next()) {
            if(resultSet.getInt("count_name") != 2){
                throw new IllegalStateException("Количество апельсинов не равно 2");
            }
        }

        PreparedStatement ps = connection.prepareStatement("DELETE FROM FOOD WHERE FOOD_ID = (SELECT MAX(FOOD_ID) FROM FOOD) AND FOOD_NAME = 'Апельсин'");
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Последний добавленный апельсин удален");
        } else {
            System.out.println("Что-то пошло не так");
        }

        resultSet = statement.executeQuery("SELECT COUNT(*) AS count_name FROM FOOD \n" +
                "WHERE FOOD_NAME = 'Апельсин' AND FOOD_TYPE = 'FRUIT' AND FOOD_EXOTIC ='1.00'");
        if (resultSet.next()) {
            if(resultSet.getInt("count_name") != 1){
                throw new IllegalStateException("Количество апельсинов не равно 1");
            }
        }

        resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM FOOD");
        if (resultSet.next()) {
            if(resultSet.getInt("row_count") != 4){
                throw new IllegalStateException("Таблица не в начальном состоянии");
            }
        }
    }
}

