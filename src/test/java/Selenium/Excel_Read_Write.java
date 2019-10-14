package Selenium;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Excel_Read_Write {

    @Test
    public void testFirst() throws Exception {
        write();
        read();
        read_write();
    }

    public void write() throws Exception {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        CreationHelper createHelper = wb.getCreationHelper();

        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);
        Cell cell2 = row.createCell(1);
        cell1.setCellValue(1);
        cell2.setCellValue(createHelper.createRichTextString("This is a string"));

        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\om\\Downloads\\Programs\\Selenium\\book2.xlsx");
        wb.write(fileOut);
        fileOut.close();
    }

    public void read() throws Exception {
        InputStream inp = new FileInputStream("C:\\Users\\om\\Downloads\\Programs\\Selenium\\book2.xlsx");
        Workbook wb = WorkbookFactory.create(inp);
        Sheet sheet1 = wb.getSheet("sheet1");
        for (Row row : sheet1) {
            for (Cell cell : row) {

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.println(cell.getRichStringCellValue().getString());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            System.out.println(cell.getDateCellValue());
                        } else {
                            System.out.println(cell.getNumericCellValue());
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.println(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        System.out.println(cell.getCellFormula());
                        break;
                    default:
                        System.out.println();
                }
            }
        }
    }

    public void read_write() throws Exception {
        InputStream inp = new FileInputStream("C:\\Users\\om\\Downloads\\Programs\\Selenium\\book2.xlsx");
        Workbook wb = WorkbookFactory.create(inp);
        Sheet sheet = wb.getSheetAt(0);

        Row row = sheet.getRow(0);
        Cell cell = row.createCell(2);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("a test");

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\om\\Downloads\\Programs\\Selenium\\book2.xlsx");
        wb.write(fileOut);
        fileOut.close();
    }
}
