package Screenshot;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.openxml4j.opc.Package;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by om on 2/4/2015.
 */
public class ScreenshotFilenameExcel {
    private static String BaseUrlReplaceWith = "http://hcl.prod.acquia-sites.com";
    private static String BaseUrlToBeReplaced = "http://www.hcltech.com";
    private static String SheetName = "Sheet1";
    private static String ExcelFilePath = "C:\\Users\\om\\Downloads\\TestingFiles\\Screenshots\\Book1.xlsx";

    public static void main(String arg[]) throws IOException, InvalidFormatException {
        FileInputStream FileInput = new FileInputStream(ExcelFilePath);
        XSSFWorkbook ExcelWBook = new XSSFWorkbook(Package.open(FileInput));
        XSSFSheet ExcelWSheet = ExcelWBook.getSheet(SheetName);

        XSSFRow Row = null;
        XSSFCell Cell = null;
        String OldUrl = new String();
        String[] NewTitleUrl = new String[2];
        int RowNum = ExcelWSheet.getLastRowNum() + 1;
        for (int i = 1 ; i < RowNum ; i++){
            Row = ExcelWSheet.getRow(i);
            Cell = Row.getCell(1);
            OldUrl = cellToString(Cell);
            NewTitleUrl = GetNewTitleUrl(OldUrl);
            WriteData(NewTitleUrl, i);
        }
    }

    private static String cellToString(XSSFCell cell){
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

    private static String[] GetNewTitleUrl(String Url) throws IOException, InvalidFormatException {
        StringBuilder OmitBaseUrl = new StringBuilder(Url.split(BaseUrlToBeReplaced)[1]);
        ArrayList<Integer> ListOfIndex = new ArrayList<Integer>();
        String[] TitleUrl = new String[2];

        // Get all index of character '/' and '-'
        for(int i = 0; i < OmitBaseUrl.length(); i++){
            if(OmitBaseUrl.charAt(i) == '/' || OmitBaseUrl.charAt(i) == '-'){
                ListOfIndex.add(i);
            }
        }
        // Convert all character from lower case to upper case at index of '/' + 1 and at '-' + 1
        for (int j = 0 ; j < ListOfIndex.size() ; j++) {
            OmitBaseUrl.setCharAt(ListOfIndex.get(j) + 1, Character.toUpperCase(OmitBaseUrl.charAt(ListOfIndex.get(j) + 1)));
        }
        // Remove all '-' and first '/'
        String Title = String.valueOf(OmitBaseUrl).replace("-", "").substring(1);
        // Replace rest of '/' with '-'
        TitleUrl[0] = Title.replace("/", "-");
        // Replace Base Url
        TitleUrl[1] = Url.replace(BaseUrlToBeReplaced, BaseUrlReplaceWith);
        System.out.println(TitleUrl[0]);
        return TitleUrl;
    }

    private static void WriteData(String[] TitleUrl, int i) throws IOException, InvalidFormatException {
        FileInputStream FileInput = new FileInputStream(ExcelFilePath);
        XSSFWorkbook ExcelWBook = new XSSFWorkbook(Package.open(FileInput));
        XSSFSheet ExcelWSheet = ExcelWBook.getSheet(SheetName);

        XSSFRow Row = null;
        XSSFCell Cell = null;
        Row = ExcelWSheet.getRow(i);
        for (int j = 0 ; j < TitleUrl.length ; j++) {
            Cell = Row.getCell(j);
            Cell.setCellType(Cell.CELL_TYPE_STRING);
            Cell.setCellValue(TitleUrl[j]);
        }

        FileOutputStream FileOutput = new FileOutputStream(ExcelFilePath);
        ExcelWBook.write(FileOutput);
        FileOutput.close();
    }
}
