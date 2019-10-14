package Selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Parallel_Testing {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeMethod
    public void setUp() throws Exception {
        baseUrl = "http://book.theautomatedtester.co.uk/";
    }

    @Test
    @Parameters("browser")
    public void testFirst(String browser) throws Exception {
        if(browser.equalsIgnoreCase("internet explorer")) {
            System.setProperty("webdriver.ie.driver", "C:\\Users\\om\\Downloads\\Programs\\Selenium\\IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        }
        else if (browser.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\om\\Downloads\\Programs\\Selenium\\chromedriver.exe");
            driver = new ChromeDriver();
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl + "/chapter1");

        driver.findElement(By.id("radiobutton")).click();
        new Select(driver.findElement(By.id("selecttype"))).selectByVisibleText("Selenium Grid");
        try {
            assertEquals("Assert that this text is on the page", driver.findElement(By.id("divontheleft")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        try {
            assertTrue(isElementPresent(By.id("verifybutton")));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
