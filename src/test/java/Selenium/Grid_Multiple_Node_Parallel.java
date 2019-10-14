/**
 * In these program, three nodes will registered on hub and two nodes will run parallel and each node will run two browser parallel.
 * This is done through TestNG, for that:-
 * For multiple node define port
 * 1.java -jar selenium-server-standalone-2.37.0.jar -role hub
 * 2.java -jar selenium-server-standalone-2.37.0.jar -role webdriver -hub http://localhost:4444/grid/register -port 5577 -Dwebdriver.chrome.driver=C:\Users\om\Downloads\Programs\Selenium\chromedriver.exe -Dwebdriver.ie.driver=C:\Users\om\Downloads\Programs\Selenium\IEDriverServer.exe
 * 3.java -jar selenium-server-standalone-2.37.0.jar -role webdriver -hub http://localhost:4444/grid/register -port 5544 -Dwebdriver.chrome.driver=C:\Users\om\Downloads\Programs\Selenium\chromedriver.exe -Dwebdriver.ie.driver=C:\Users\om\Downloads\Programs\Selenium\IEDriverServer.exe
 */

package Selenium;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import org.testng.annotations.Test;

public class Grid_Multiple_Node_Parallel {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Parameters({ "platform","browser","version", "url", "node" })
    @BeforeTest(alwaysRun=true)
    public void setUp(String platform, String browser, String version, String url, String node) throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        //Platforms
        if(platform.equalsIgnoreCase("Windows"))
            caps.setPlatform(Platform.WINDOWS);
        if(platform.equalsIgnoreCase("MAC"))
            caps.setPlatform(Platform.MAC);
        //Browsers
        if(browser.equalsIgnoreCase("Internet Explorer"))
            caps = DesiredCapabilities.internetExplorer();
        if(browser.equalsIgnoreCase("Chrome"))
            caps = DesiredCapabilities.chrome();
        //Version
        caps.setVersion(version);

        driver = new RemoteWebDriver(new URL("http://192.168.1.4:" + node + "/wd/hub"), caps);
        // Open the BMI Calculator Application
        driver.get(url);
    }

    @Test(description="Test Bmi Calculator")
    public void testBmiCalculator() throws InterruptedException {
        WebElement height = driver.findElement(By.name("heightCMS"));
        height.sendKeys("181");
        WebElement weight = driver.findElement(By.name("weightKg"));
        weight.sendKeys("80");
        WebElement calculateButton = driver.findElement(By.id("Calculate"));
        calculateButton.click();
        WebElement bmi = driver.findElement(By.name("bmi"));
        assertEquals(bmi.getAttribute("value"), "24.4");
        WebElement bmi_category = driver.findElement(By.name("bmi_category"));
        assertEquals(bmi_category.getAttribute("value"), "Normal");
    }

    @AfterTest
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