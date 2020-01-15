package cz.vse.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreatingProjectTest extends AppTestSemestralka  {



    public static void createProject(@org.jetbrains.annotations.NotNull ChromeDriver driver, String projectName){
        driver.findElement(By.linkText("Projects")).click();
        driver.findElement(By.className("btn-primary")).click();
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_158")));
        WebElement searchInput = driver.findElement(By.id("fields_158"));
        searchInput.sendKeys(projectName);
        Select priorityInput = new Select(driver.findElement(By.id("fields_156")));
        priorityInput.selectByIndex(1);
        driver.findElement(By.id("fields_159")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("td.active")));
        driver.findElement(By.cssSelector("td.active")).click();
        driver.findElement(By.className("btn-primary-modal-action")).click();
    }

    public static void deleteProjectByName(@org.jetbrains.annotations.NotNull ChromeDriver driver, String projectName){
        driver.findElement(By.linkText("Projects")).click();
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(projectName)));
        driver.findElement(By.linkText(projectName)).click();
        driver.findElement(By.linkText("Project Info")).click();
        driver.findElement(By.xpath("//button[contains(text(),'More Actions')]")).click();
        driver.findElement(By.linkText("Delete")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("single-checkbox")));
        driver.findElement(By.id("delete_confirm")).click();
        driver.findElement(By.xpath("//button[contains(text(),'Delete')]")).click();
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
    public void createProjectWithOutName() {
        //Given
        UserLoginTest.loginToTheRukovoditel(driver, validUserName, validPassword );
        //When
        createProject(driver, "");
        //Then
        Assert.assertFalse(driver.findElements(By.id("fields_158-error")).size() == 0);
    }

    @Test
    public void createProjectWithName() {
        //Given
        UserLoginTest.loginToTheRukovoditel(driver, validUserName, validPassword );
        //When
        createProject(driver, projectName);
        driver.findElement(By.linkText("Projects")).click();
        //Then
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(projectName)));
        //CleanUp
        deleteProjectByName(driver, projectName);

    }
}
