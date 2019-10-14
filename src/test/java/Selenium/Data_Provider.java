package Selenium;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Data_Provider {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeMethod
    public void setUp() throws Exception {
        baseUrl = "http://www.innoraft.com/";
    }

    @DataProvider(name = "test1")
    public static Object[][] primeNumbers() {
        return new Object[][] { { "firefox" }, { "chrome" } };
    }

    @Test(dataProvider = "test1")
    public void testFirst(String browser) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);

        driver = new RemoteWebDriver(new URL("http://127.0.0.1:5555/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl + "/");

        try {
            assertEquals("Testimonial", driver.findElement(By.cssSelector("h2.block__title")).getText());
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

/*
For single node no need to define port
1.java -jar selenium-server-standalone-2.37.0.jar -role hub

2.java -jar selenium-server-standalone-2.37.0.jar -role webdriver -hub http://localhost:4444/grid/register -Dwebdriver.chrome.driver=C:\xampp\htdocs\selenium\chromedriver.exe
 */
