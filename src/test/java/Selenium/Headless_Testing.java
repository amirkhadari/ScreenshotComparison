/**
 * In these program, Test will performed on a phantomJS browser through phantomJS driver.
 * Test will be headless means without GUI.
 */

package Selenium;

/**
 * Created by om on 10/11/2014.
 */

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class Headless_Testing {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        baseUrl = "http://localhost/";
    }

    @Test
    public void testHtmlUnit() throws Exception {
        driver = new HtmlUnitDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(baseUrl + "/Timehub/");
        driver.findElement(By.id("edit-name")).clear();
        driver.findElement(By.id("edit-name")).sendKeys("aman");
        driver.findElement(By.id("edit-pass")).clear();
        driver.findElement(By.id("edit-pass")).sendKeys("123456");
        driver.findElement(By.id("edit-submit")).click();
        driver.findElement(By.linkText("Create Invoice")).click();
        driver.findElement(By.linkText("Log out")).click();
    }

    @Test
    public void testPhantomJS() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "C:\\Program Files\\phantomjs\\phantomjs.exe");
        driver = new PhantomJSDriver(caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(baseUrl + "/Timehub/");
        driver.findElement(By.id("edit-name")).clear();
        driver.findElement(By.id("edit-name")).sendKeys("aman");
        driver.findElement(By.id("edit-pass")).clear();
        driver.findElement(By.id("edit-pass")).sendKeys("123456");
        driver.findElement(By.id("edit-submit")).click();
        driver.findElement(By.linkText("Create Invoice")).click();
        driver.findElement(By.linkText("Log out")).click();
    }

    @After
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