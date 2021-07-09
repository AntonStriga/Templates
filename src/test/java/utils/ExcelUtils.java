package utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    static String projectPath;
    static XSSFWorkbook workbook;
    static XSSFSheet sheet;

    public ExcelUtils(String excelPath, String excelSheet){
        try {
            projectPath = System.getProperty("user.dir") + "/src/test/resources/";
            workbook = new XSSFWorkbook(projectPath + excelPath);
            sheet = workbook.getSheet(excelSheet);
        } catch(Exception exp) {
            exp.printStackTrace();
        }
    }

    // Returns number of data rows in excel file

    public int getRowCount() {
        int rowCount = 0;
        try {
            rowCount = sheet.getPhysicalNumberOfRows();
        } catch(Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return rowCount;
    }

    // Returns number of data columns in excel file

    public int getCellCount() {
        int cellCount = 0;
        try {
            cellCount = sheet.getRow(0).getPhysicalNumberOfCells();
        } catch(Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return cellCount;
    }

    // Returns type of data in certain cell in excel file

    public CellType getCellType(int rowNumber, int columnNumber) {
        CellType cellType = null;
        try {
            cellType = sheet.getRow(rowNumber).getCell(columnNumber).getCellType();
        } catch(Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return cellType;
    }

    // Returns string type data from excel cell

    public String getCellDataString(int rowNumber, int columnNumber) {
        String cellData = null;
        try {
            cellData = sheet.getRow(rowNumber).getCell(columnNumber).getStringCellValue();
        } catch(Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return cellData;
    }

    // Returns number type data from excel cell

    public double getCellDataNumber(int rowNumber, int columnNumber) {
        double cellData = 0;
        try {
            cellData = sheet.getRow(rowNumber).getCell(columnNumber).getNumericCellValue();
        } catch(Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return cellData;
    }
}
