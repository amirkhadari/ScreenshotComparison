/**
 * In these program, fetch the values from excel file in 2-D array than with these values perform the testing
 * on websites and after that result will be stored in excel file. Result will be stored opposite to those values
 * for which testing performed.
 */

package Selenium;

import org.apache.poi.ss.usermodel.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Class uses a parametrized runner.
@RunWith(value = Parameterized.class)
public class Excel_Drive {
    private static WebDriver driver;
    private static int count = 0;
    private String baseUrl;
    private String home;
    private String principal;

    @Before
    public void setUp() throws Exception {
        baseUrl = "http://www.mortgagecalculator.org/";
    }

    // Define a Collection method that will return the collection of parameters to the Excel_drive class
    // by using the @Parameters annotation.
    @Parameterized.Parameters
    public static Collection ReadData() throws Exception {
        // Access Excel file through FileInputStream class.
        FileInputStream fis = new FileInputStream("C:\\Users\\om\\Downloads\\Programs\\Selenium\\Book1.xlsx");
        // Access Workbook through WorkbookFactory class that auto-detect the type of file either
        // .xls(Created by HSSFWorkbook) or .xlsx(Created by XSSFWorkbook).
        Workbook wb = WorkbookFactory.create(fis);
        // Access Worksheet through Sheet class.
        Sheet ws = wb.getSheet("Sheet1");

        // To know the total number of rows in Excel file that has data, get the last row number through
        // getLastRowNum method and add 1 because Excel file first row number is 0.
        int rowNum = ws.getLastRowNum() + 1;
        // To know the total number of columns in Excel file that has data, get the first row and then get
        // the last cell number through getLastCellNum method. In Excel file first column number is 1.
        int colNum = ws.getRow(0).getLastCellNum();
        // Create the two dimensional array according to accessed row and column number.
        String[][] data = new String[rowNum][colNum];

        // Now read data from Excel file through Sheet class object and write into the two dimensional array.
        for (int i = 0 ; i < rowNum ; i++){
            Row row = ws.getRow(i);
            for (int j = 0 ; j < colNum ; j++){
                Cell cell = row.getCell(j);
                String value = cellToString(cell);
                data[i][j] = value;
            }
        }
        // Create dynamic array means size of array can grow as needed.
        List<String[]> wordList = Arrays.asList(data);
        return wordList;
    }

    public static String cellToString(Cell cell){
        int type;
        Object result;
        type = cell.getCellType();

        switch (type) {
            case Cell.CELL_TYPE_STRING:
                result = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    result = cell.getDateCellValue();
                } else {
                    result = cell.getNumericCellValue();
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                result = cell.getCellFormula();
                break;
            default:
                throw new RuntimeException("No support for this type of cell");
        }

        return result.toString();
    }

    // Constructor will be used by the test runner to pass the parameters to the Excel_drive class variable.
    public Excel_Drive(String home, String principal)
    {
        this.home = home;
        this.principal = principal;
    }

    @Test
    public void testFirst() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\om\\Downloads\\Programs\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get(baseUrl + "/");
        driver.findElement(By.name("param[homevalue]")).clear();
        driver.findElement(By.name("param[homevalue]")).sendKeys(home);
        driver.findElement(By.name("param[principal]")).clear();
        driver.findElement(By.name("param[principal]")).sendKeys(principal);
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        String result = driver.findElement(By.cssSelector("h3")).getText();
        System.out.println(result);
        WriteData(result);
    }

    public static void WriteData(String result) throws Exception {
        // Access the Workbook of Excel file.
        FileInputStream inp = new FileInputStream("C:\\Users\\om\\Downloads\\Programs\\Selenium\\Book1.xlsx");
        Workbook wb = WorkbookFactory.create(inp);
        Sheet sheet = wb.getSheetAt(0);

        // Create the cell and then set the cell value(result) in workbook.
        Row row = sheet.getRow(count);
        Cell cell = row.createCell(2);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(result);
        count++;

        // Write the Workbook in Excel file.
        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\om\\Downloads\\Programs\\Selenium\\Book1.xlsx");
        wb.write(fileOut);
        fileOut.close();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
