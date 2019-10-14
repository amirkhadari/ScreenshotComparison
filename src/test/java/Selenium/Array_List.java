// Data will be drive on browser through Array list.

package Selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

@RunWith(value = Parameterized.class)
public class Array_List {
    private static WebDriver driver;
    private String baseUrl;
    private String home;
    private String principal;

    @Before
    public void setUp() throws Exception {
        baseUrl = "http://www.mortgagecalculator.org/";
    }

    /**
     * Define a Collection method that will return the collection of parameters to the parallelTestThroughJUnit class
     * by using the @Parameters annotation.
     */
    @Parameterized.Parameters
    public static Collection testData() {
        return Arrays.asList(
                new Object[][]{
                        {"160", "45"},
                        {"168", "70"},
                        {"181", "89"},
                        {"178", "10"}
                }
        );
    }

    // Constructor will be used by the test runner to pass the parameters to the Excel_drive class instance.
    public Array_List(String home, String principal)
    {
        this.home = home;
        this.principal = principal;
    }

    @Test
    public void testFirst() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/usr/lib/chromium-browser/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get(baseUrl + "/");
        driver.findElement(By.name("param[homevalue]")).clear();
        driver.findElement(By.name("param[homevalue]")).sendKeys(home);
        driver.findElement(By.name("param[principal]")).clear();
        driver.findElement(By.name("param[principal]")).sendKeys(principal);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
