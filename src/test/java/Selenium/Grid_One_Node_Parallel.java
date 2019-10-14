/**
 * In these program, one node will registered on hub and node will run two browser parallel.
 * This is done by parallel execution through TestNG, for that:-
 * For single node no need to define port
 * 1.java -jar selenium-server-standalone-2.37.0.jar -role hub
 * 2.java -jar selenium-server-standalone-2.37.0.jar -role webdriver -hub http://localhost:4444/grid/register -port 5566 -Dwebdriver.chrome.driver=C:\Users\om\Downloads\Programs\Selenium\chromedriver.exe -Dwebdriver.ie.driver=C:\Users\om\Downloads\Programs\Selenium\IEDriverServer.exe
 */

package Selenium;

/**
 * Created by om on 11/2/2014.
 */
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.Assert.fail;
import static org.testng.Assert.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import java.net.MalformedURLException;
import java.net.URL;

public class Grid_One_Node_Parallel {
    private WebDriver driver = null;
    private StringBuffer verificationErrors = new StringBuffer();

    @Parameters({ "platform","browser","version", "url" })
    @BeforeTest(alwaysRun=true)
    public void setup(String platform, String browser, String version, String url) throws MalformedURLException {
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

        driver = new RemoteWebDriver(new URL("http://192.168.1.4:5566/wd/hub"), caps);
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
    public void afterTest() {
        //Close the browser
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}