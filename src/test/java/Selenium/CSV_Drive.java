package Selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.util.*;
import static org.junit.Assert.fail;

@RunWith(value = Parameterized.class)
public class CSV_Drive {
    private static WebDriver driver;
    private String baseUrl;
    private String home;
    private String principal;

    @Before
    public void setUp() throws Exception {
        baseUrl = "http://www.mortgagecalculator.org/";
    }

    public static Collection<String[]> getTestData(String fileName)
            throws IOException {
        List<String[]> records = new ArrayList<String[]>();
        String record;
        BufferedReader file = new BufferedReader(new
                FileReader(fileName));
        while ((record=file.readLine())!=null) {
            String fields[] = record.split(",");
            records.add(fields);
        }
        file.close();
        return records;
    }

    @Parameterized.Parameters
    public static Collection testData() throws IOException {
        return getTestData("C:\\Users\\om\\Downloads\\Programs\\Starting Selenium\\data.csv");
    }

    public CSV_Drive(String home, String principal)
    {
        this.home = home;
        this.principal = principal;
    }

    @Test
    public void testFirst() throws Exception {
        driver = new FirefoxDriver();
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
