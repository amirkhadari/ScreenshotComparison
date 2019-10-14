/**
 *In these program, we drive the IE and firefox browser through IE driver and firefox driver respectively.
 *For IE provide the path of driver exe file anf for firefox no need.
 */

package Selenium;

/**
 * Created by om on 12/6/13.
 */

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Complete_Screenshot {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        baseUrl = "http://www.innoraft.com/";
    }

//    @Test
//    public void testFirefox() throws Exception {
//        driver = new FirefoxDriver();
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        driver.manage().window().maximize();
//        driver.get(baseUrl);
//
//        File screen = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(screen, new File("c:\\abc\\firefox.png"));
//    }

//    @Test
//    public void testIE() throws Exception {
//        System.setProperty("webdriver.ie.driver", "C:\\Users\\om\\Downloads\\Programs\\Selenium\\IEDriverServer.exe");
//        driver = new InternetExplorerDriver();
//        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        driver.manage().window().maximize();
//        driver.get(baseUrl);
//
//        File screen = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(screen, new File("c:\\abc\\explorer.png"));
//    }

    @Test
    public void testChrome() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/usr/lib/chromium-browser/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl);

        File screen = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screen, new File("/home/innoraft/Amir/Browserstack/Files/complete.png"));
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