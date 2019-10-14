package Selenium;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class RemoteControl_Testing {
    private Selenium selenium;

    @Before
    public void setUp() throws Exception {
        selenium = new DefaultSelenium("localhost", 4444, "*firefox C:\\Program Files\\Mozilla Firefox\\firefox.exe", "http://book.theautomatedtester.co.uk/");
        selenium.start();
    }

    @Test
    public void testUntitled() throws Exception {
        selenium.open("/");
        selenium.click("link=Chapter1");
        selenium.waitForPageToLoad("30000");
        selenium.select("id=selecttype", "label=Selenium Grid");
        selenium.click("id=secondajaxbutton");
    }

    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }
}
