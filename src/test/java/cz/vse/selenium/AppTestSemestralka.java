package cz.vse.selenium;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AppTestSemestralka {
    protected ChromeDriver driver;
    protected static final String validUserName = "rukovoditel";
    protected static final String validPassword = "vse456ru";
    protected static final String inValidPassword = "1234";
    protected static final String projectName = "jarp01";

    public void prepareConfiguration(){
        ChromeOptions cho = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver(cho);
        driver.manage().window().maximize();
    }

    public void closeConfiguration(){
        if (driver != null) driver.close();
    }
}
