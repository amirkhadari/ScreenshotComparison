/**
 *In these program, we drive the IE and firefox browser through IE driver and firefox driver respectively.
 *For IE provide the path of driver exe file anf for firefox no need.
 */

package Selenium;

/**
 * Created by om on 12/6/13.
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.internal.WrapsDriver;

import javax.imageio.ImageIO;

public class Element_Screenshot {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        baseUrl = "http://www.innoraft.com/";
    }

    @Test
    public void testSecond() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/usr/lib/chromium-browser/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl);

        WebElement Element = driver.findElement(By.className("header-text"));
        WrapsDriver WrapsDriver = (WrapsDriver) Element;
        //Get the entire Screenshot from the driver of passed WebElement
        File Screen = ((TakesScreenshot) WrapsDriver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
        //Create an instance of Buffered Image from captured screenshot
        BufferedImage Img = ImageIO.read(Screen);
        // Get the Width and Height of the WebElement
        int Width = Element.getSize().getWidth();
        int Height = Element.getSize().getHeight();
        //Create a rectangle using Width and Height
        Rectangle Rect = new Rectangle(Width, Height);
        //Get the Location of WebElement in a Point. This will provide X & Y co-ordinates of the WebElement
        Point P = Element.getLocation();
        //Create image for element using its location and size. This will give image data specific to the WebElement
        BufferedImage Dest = Img.getSubimage(P.getX(), P.getY(), Rect.width, Rect.height);
        //Write back the image data for element in File object
        ImageIO.write(Dest, "png", Screen);
        FileUtils.copyFile(Screen, new File("/home/innoraft/Amir/Browserstack/Files/chrome.png"));
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