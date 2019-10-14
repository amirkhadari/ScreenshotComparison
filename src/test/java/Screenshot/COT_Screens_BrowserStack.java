package Screenshot;

/**
 * Created by om on 12/6/13.
 */

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;

@RunWith(value = Parameterized.class)
public class COT_Screens_BrowserStack {
    private String baseUrl;
    private String browser;
    private String version;
    private String filename;
    private WebDriver driver;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Parameterized.Parameters
    public static Collection testData() {
        return Arrays.asList(
                new Object[][]{
                        {"IE", "9", "IE9"},
                        {"IE", "10", "IE10"},
                        {"IE", "11", "IE11"},
                        {"Chrome", "36", "Chrome36"},
                        {"Firefox", "30", "Firefox30"},
                        {"Safari", "5.1", "Safari5.1"},
                        {"Opera", "12.16", "Opera12.16"}
                }
        );
    }

    // Parametrization is done on a class level
    public COT_Screens_BrowserStack(String browser, String version, String filename) {
        this.browser = browser;
        this.version = version;
        this.filename = filename;
    }

    @Before
    public void setUp() throws Exception {
        baseUrl = "http://forums.collegeontrack.com/user";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", browser);
        caps.setCapability("browser_version", version);
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "7");
        caps.setCapability("browserstack.debug", "true");
        caps.setCapability("build", "version1");
        caps.setCapability("project", "Testing/HCL/COT");

        driver = new RemoteWebDriver(new URL("http://amangoel1:5mqhbQpxBq1qgYWshu9W@hub.browserstack.com/wd/hub"), caps);
        driver.manage().window().setSize(new Dimension(1200, 1200));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);

        // Reach at home page.
        driver.findElement(By.id("edit-name")).clear();
        driver.findElement(By.id("edit-name")).sendKeys("anna");
        driver.findElement(By.id("edit-pass")).clear();
        driver.findElement(By.id("edit-pass")).sendKeys("annalu");
        driver.findElement(By.id("edit-submit")).click();

    }

    @org.junit.Test
    public void testFirst() throws Exception {
        String filename = null;
        String url = null;

        String file_url[][] = new String[][]{
                {"forum"," http://forums.collegeontrack.com/"},
                {"forum_1","http://forums.collegeontrack.com/forum/1"},
                {"node_add_forum_1","http://forums.collegeontrack.com/node/add/forum/1"},
                {"node_56","http://forums.collegeontrack.com/node/56"},
                {"node_9_forum","http://forums.collegeontrack.com/node-9-forum"},
                {"search_node_natu","http://forums.collegeontrack.com/search/node/natu"},
                {"search_node_natudf","http://forums.collegeontrack.com/search/node/natudf"},
                {"node_add_1","https://forums.collegeontrack.com/node/add/forum/1"},
                {"node_25_forum","https://forums.collegeontrack.com/node-25-forum"}
        };

        String list[] = new String[2];
        for(int i=0; i<2; i++) {
            for(int j=0; j<2; j++) {
                list[j]  = file_url[i][j];
            }
            filename = list[0];
            url = list[1];
            output(filename, url);
        }

        driver.findElement(By.cssSelector("div.login--link > img")).click();
        driver.findElement(By.linkText("Logout")).click();
    }

    private void output(String filename, String url) throws IOException {
        driver.get(url);

        driver = new Augmenter().augment(driver);
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screen, new File("C:\\Users\\om\\Downloads\\COT_Screens\\" + filename + "_" + this.filename + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
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