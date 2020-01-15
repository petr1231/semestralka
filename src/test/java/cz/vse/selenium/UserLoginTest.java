package cz.vse.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserLoginTest extends AppTestSemestralka {

    public static void loginToTheRukovoditel(@org.jetbrains.annotations.NotNull ChromeDriver driver, String username, String password) {
        driver.get("https://digitalnizena.cz/rukovoditel/");
        WebElement searchInputUsername = driver.findElement(By.name("username"));
        searchInputUsername.sendKeys(username);
        WebElement searchInputPassword = driver.findElement(By.name("password"));
        searchInputPassword.sendKeys(password);
        searchInputPassword.sendKeys(Keys.ENTER);
    }

    public static void loginOffRukovoditel(@org.jetbrains.annotations.NotNull ChromeDriver driver){
        driver.findElement(By.cssSelector(".username")).click();
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logoff")));
        driver.findElement(By.linkText("Logoff")).click();
    }

    @Before
    public void init() {
        super.prepareConfiguration();
    }

    @After
    public void tearDown() {
        super.closeConfiguration();
    }

    @Test
    public void loginValid() {
        //Given
            //Computer has Chrome
        //When
        loginToTheRukovoditel(driver, validUserName, validPassword );
        //Then
        Assert.assertTrue(driver.getTitle().contains("Rukovoditel | Dashboard"));
    }

    @Test
    public void loginInValidPassword() {
        //Given
            //Computer has Chrome
        //When
        loginToTheRukovoditel(driver, validUserName, inValidPassword );
        //Then
        Assert.assertFalse(driver.getTitle().contains("Rukovoditel | Dashboard"));
    }

    @Test
    public void loginOff() {
        //Given
        loginToTheRukovoditel(driver, validUserName, validPassword );
        //When
        loginOffRukovoditel(driver);
        //Then
        Assert.assertFalse(driver.getTitle().contains("Dashboard") && driver.findElement(By.name("username")) != null);
    }

}
