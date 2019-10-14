package Screenshot;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.*;
import org.apache.poi.openxml4j.opc.Package;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by om on 1/7/2015.
 */
public class TakeScreenshot {
    ScreenshotFunctions func;
    String Browser = "Chrome";
    String ScreenshotThrough = "Stack";
    String ReadTitleAndUrlFrom = "Array";
    String Path = "/TestingFiles/ScreenShots/HCL/";

    String SheetName = "Sheet1";
    String ExcelFilePath = "C:\\Users\\om\\Downloads\\TestingFiles\\Screenshots\\";

    @Test
    @Parameters({"BaseUrl","SiteLevel","ExcelFile"})
    public void testFirst(String BaseUrl, String SiteLevel, String ExcelFile) throws Exception {
        func = new ScreenshotFunctions();
        // Create the directory for SiteLevel.
        File dir = new File(func.BasePath + Path + SiteLevel + ScreenshotThrough);
        if (!dir.exists()) {
            dir.mkdir();
        }

        // Get Selections
        String[][] Selections = GetSelections();
        // Get Title and Url
        String[][] FilenameUrl = new String[2000][2];
        if (ReadTitleAndUrlFrom == "Excel") {
            FilenameUrl = ReadExcel(ExcelFile);
        } else if (ReadTitleAndUrlFrom == "Array") {
            FilenameUrl = ReadArray(BaseUrl);
        }

        if (ScreenshotThrough == "Driver") {
            func.TestOnFirefox();
            func.OnBrowsersByWebDriver(FilenameUrl, SiteLevel, Path + SiteLevel + ScreenshotThrough, Browser);
        } else if (ScreenshotThrough == "Stack"){
            func.LoginBrowserStack(Selections);
            func.OnBrowsersByBrowserStack(FilenameUrl, SiteLevel, Path + SiteLevel + ScreenshotThrough, Selections.length);
            func.LogoutBrowserStack();
        }
    }

    private String[][] GetSelections() {
        return new String[][]{
//                {"Windows", "7", "ie", "8"},
//                {"Windows", "7", "ie", "9"},
//                {"Windows", "7", "ie", "10"},
//                {"Windows", "7", "firefox", "30"},
//                {"Windows", "7", "firefox", "15"},
                {"Windows", "10", "chrome", "70"},
                {"Windows", "10", "chrome", "65"},
        };
    }

    private String[][] ReadArray(String BaseUrl) {
        return new String[][]{{"About Us","" + BaseUrl + "/about-us"},
                {"About Us_About HCL Technologies","" + BaseUrl + "/about-us/about-hcl-technologies"},
                {"About Us_About HCL Enterprise","" + BaseUrl + "/about-us/about-hcl-enterprise"},
//                {"About Us_Vision & Mission","" + BaseUrl + "/about-us/value-statements"},
//                {"About Us_Leadership","" + BaseUrl + "/about-us/leadership/board-of-directors"},
//                {"About Us_Leadership_Executive Profiles","" + BaseUrl + "/about-us/leadership/executive-profiles"},
//                {"About Us_Leadership_Board of Directors","" + BaseUrl + "/about-us/leadership/board-of-directors"},
//                {"About Us_Governance","" + BaseUrl + "/about-us/corporate-governance"},
//                {"About Us_Governance_Governance Policies","" + BaseUrl + "/about-us/corporate-governance/governance-policies"},
//                {"About Us_Alliances","" + BaseUrl + "/about-us/alliances"},
//                {"About Us_Alliances_Microsoft Alliance","" + BaseUrl + "/about-us/microsoft-alliance"},
//                {"About Us_Alliances_Microsoft Alliance_GamEdge","" + BaseUrl + "/about-us/gamedge"},
//                {"About Us_Alliances_Microsoft Alliance_EmpoweRetail CRM for Retailers","" + BaseUrl + "/about-us/empoweretail-crm-retailers"},
//                {"About Us_Alliances_Microsoft Alliance_FinEdge","" + BaseUrl + "/about-us/finedge"},
//                {"About Us_Recognitions","" + BaseUrl + "/about-us/awards-recognitions"},
//                {"About Us_Socially Responsible Business","" + BaseUrl + "/socially-responsible-business"}
        };
    }

    private String[][] ReadExcel(String ExcelFile) throws IOException, InvalidFormatException {
        FileInputStream FileInput = new FileInputStream(ExcelFilePath + ExcelFile);
        XSSFWorkbook ExcelWBook = new XSSFWorkbook(Package.open(FileInput));
        XSSFSheet ExcelWSheet = ExcelWBook.getSheet(SheetName);

        XSSFRow Row = null;
        XSSFCell Cell = null;
        int RowNum = ExcelWSheet.getLastRowNum() + 1;
        int ColNum = ExcelWSheet.getRow(0).getLastCellNum();
        String[][] TitleUrl = new String[RowNum][ColNum];
        for (int i = 1 ; i < RowNum ; i++){
            Row = ExcelWSheet.getRow(i);
            for (int j = 0 ; j < ColNum ; j++) {
                Cell = Row.getCell(j);
                TitleUrl[i][j] = cellToString(Cell);
            }
        }
        return TitleUrl;
    }

    private String cellToString(XSSFCell cell){
        int type;
        Object result;
        type = cell.getCellType();

        switch (type) {
            case XSSFCell.CELL_TYPE_STRING:
                result = cell.getRichStringCellValue().getString();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                result = cell.getNumericCellValue();
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                result = cell.getCellFormula();
                break;
            default:
                throw new RuntimeException("No support for this type of cell");
        }
        return result.toString();
    }
}

