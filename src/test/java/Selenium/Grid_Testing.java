/**
 * In these program, three nodes will registered on hub and one by one, one node will run one browser, for that:-
 * For multiple node define port
 * 1.java -jar selenium-server-standalone-2.37.0.jar -role hub
 * 2.java -jar selenium-server-standalone-2.37.0.jar -role webdriver -hub http://localhost:4444/grid/register -port 5544
 * 3.java -jar selenium-server-standalone-2.37.0.jar -role webdriver -hub http://localhost:4444/grid/register -port 5566 -Dwebdriver.chrome.driver=C:\Users\om\Downloads\Programs\Selenium\chromedriver.exe
 * 4.java -jar selenium-server-standalone-2.37.0.jar -role webdriver -hub http://localhost:4444/grid/register -port 5577 -Dwebdriver.ie.driver=C:\Users\om\Downloads\Programs\Selenium\IEDriverServer.exe
 */

package Selenium;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Grid_Testing {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        baseUrl = "http://book.theautomatedtester.co.uk/";
    }

    @Test
    public void testFirst() throws Exception {
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        driver = new RemoteWebDriver(new URL("http://localhost:5544/wd/hub"),capability);
        testOutput();
    }

    @Test
    public void testSecond() throws Exception {
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        driver = new RemoteWebDriver(new URL("http://localhost:5566/wd/hub"),capability);
        testOutput();
    }

    @Test
    public void testThird() throws Exception {
        DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
        driver = new RemoteWebDriver(new URL("http://localhost:5577/wd/hub"),capability);
        testOutput();
    }

    private void testOutput() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl + "/chapter1");

        driver.findElement(By.id("secondajaxbutton")).click();
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if ("I have been added with a timeout".equals(driver.findElement(By.cssSelector("#html5div > div")).getText())) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }
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

