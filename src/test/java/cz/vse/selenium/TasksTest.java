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

public class TasksTest extends AppTestSemestralka  {

    private static final String testTask = "testTask";

    public static void createTaskUnderProject(@org.jetbrains.annotations.NotNull ChromeDriver driver, String testName, int state){
        driver.findElement(By.className("btn-primary")).click();
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_168")));
        WebElement searchInput = driver.findElement(By.id("fields_168"));
        searchInput.sendKeys(testName);
        Select priorityInput = new Select(driver.findElement(By.id("fields_169")));
        priorityInput.selectByIndex(state);
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        driver.findElement(By.tagName("body")).sendKeys("Popis tasku");
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
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
    public void createOneTask() {
        //Given
        UserLoginTest.loginToTheRukovoditel(driver, validUserName, validPassword );
        CreatingProjectTest.createProject(driver, projectName);

        //When
        createTaskUnderProject(driver, testTask, 0);

        //Then
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(testTask)));
        driver.findElement(By.linkText(testTask)).click();
        Assert.assertTrue(driver.findElement(By.cssSelector(".form-group-167 div")).getText().contains("Task"));
        Assert.assertTrue(driver.findElement(By.cssSelector(".form-group-169 div")).getText().contains("New"));
        Assert.assertTrue(driver.findElement(By.cssSelector(".form-group-170 div")).getText().contains("Medium"));
        Assert.assertTrue(driver.findElement(By.cssSelector("div.content_box_content")).getText().contains("Popis tasku"));
        Assert.assertTrue(driver.findElement(By.cssSelector("div.caption")).getText().contains(testTask));

        //CleanUp
        driver.findElement(By.xpath("//button[contains(text(),'More Actions')]")).click();
        driver.findElement(By.linkText("Delete")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Delete')]")));
        driver.findElement(By.xpath("//button[contains(text(),'Delete')]")).click();
        CreatingProjectTest.deleteProjectByName(driver, projectName);
    }

    @Test
    public void createMultipleTasksFilterValidation() {
        //Given
        UserLoginTest.loginToTheRukovoditel(driver, validUserName, validPassword );
        CreatingProjectTest.createProject(driver, projectName);

        //When
        for (int i=0; i<7; i++) createTaskUnderProject(driver, testTask, i);

        //Then
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody tr")));
        Assert.assertTrue(driver.findElements(By.cssSelector("table tbody tr")).size()==3);
        driver.findElement(By.cssSelector("span.filters-preview-box-heading")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#values_chosen ul li a")));
        driver.findElement(By.cssSelector("div#values_chosen ul li:nth-of-type(2) a")).click();
        driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody tr")));
        Assert.assertTrue(driver.findElements(By.cssSelector("table tbody tr")).size()==2);
        for (int i=1; i<3; i++) Assert.assertTrue(driver.findElement(By.cssSelector("table tbody tr:nth-child("+i+")  td.field-169-td div")).getText().contains("New")  || driver.findElement(By.cssSelector("table tbody tr:nth-child("+i+") td.field-169-td div ")).getText().contains("Waiting"));

        driver.findElement(By.cssSelector("div.filters-preview-box a i.fa-trash-o")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody tr")));
        Assert.assertTrue(driver.findElements(By.cssSelector("table tbody tr")).size()==7);

        //CleanUp
        driver.findElement(By.cssSelector("input#select_all_items")).click();
        driver.findElement(By.xpath("//button[contains(text(),'With Selected')]")).click();
        driver.findElement(By.linkText("Delete")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#delete_selected_form")));
        driver.findElement(By.xpath("//button[contains(text(),'Delete')]")).click();

        CreatingProjectTest.deleteProjectByName(driver, projectName);
    }

}
