package dataProviders;

import org.apache.poi.ss.usermodel.CellType;
import org.testng.annotations.DataProvider;
import utils.ExcelUtils;

public class DataProviders {

    // Provider filled manually

    @DataProvider(name = "DataForPost")
    public Object[][] dataForPost() {
        return new Object[][] {
                {"Albert", "Einstein", 2},
                {"Thomas", "Edison", 1}
        };
    }

    // Provider filled manually

    @DataProvider(name = "DataForDelete")
    public Object[][] dataForDelete() {
        return new Object[][] {
                {4}, {5}
        };
    }

    // Provider uses excel file
    // excelFile - name of the file (in src/test/java/resources/
    // sheetName - name of the sheet in excel file

    @DataProvider(name = "DataFromExcel")
    public Object[][] getDataForPost() {
        String excelFileName = "dataExcel.xlsx";
        String sheetName = "usersPost";

        return testData(excelFileName, sheetName);
    }

    // Method testData(String excelFile, String sheetName) returns Object[][]
    // extracted from Excel file
    // excelFile - name of the file (in src/test/java/resources/)
    // sheetName - name of the sheet in excel file

    public static Object[][] testData(String excelFile, String sheetName) {

        ExcelUtils excel = new ExcelUtils(excelFile, sheetName);

        int rowCount = excel.getRowCount();
        int columnCount = excel.getCellCount();

        Object[][] data = new Object[rowCount - 1][columnCount];

        for(int i = 1; i < rowCount; i++) {
            for(int j = 0; j < columnCount; j++) {
                CellType cellType = excel.getCellType(i, j);
                if (cellType.equals(CellType.STRING)) {
                    data[i-1][j] = excel.getCellDataString(i, j);
                } else {
                    data[i-1][j] = excel.getCellDataNumber(i, j);
                }
            }
        }
        return data;
    }
}
