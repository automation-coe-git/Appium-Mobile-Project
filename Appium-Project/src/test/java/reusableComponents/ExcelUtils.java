package reusableComponents;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private static FileInputStream fi;
	private static FileOutputStream fo;
	private static XSSFWorkbook wb;
	private static XSSFSheet ws;
	private static XSSFRow row;
	private static XSSFCell cell;
	private static String strFilepath = null;

	public ExcelUtils(String strFilepath, String strSheetName) {
		try {
			fi = new FileInputStream(strFilepath);
			wb = new XSSFWorkbook(fi);
			ws = wb.getSheet(strSheetName);
			ExcelUtils.strFilepath = strFilepath;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static int getRowCount() throws IOException {
		int rowcount = ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowcount;
	}

	public static int getCellCount(int rownum) throws IOException {
		row = ws.getRow(rownum);
		int cellcount = row.getLastCellNum();
		wb.close();
		fi.close();
		return cellcount;
	}

	public static String getCellData(int rownum, int colnum) throws IOException {
		row = ws.getRow(rownum);
		cell = row.getCell(colnum);
		String data;
		try {
			DataFormatter formatter = new DataFormatter();
			String cellData = formatter.formatCellValue(cell);
			return cellData;
		} catch (Exception e) {
			data = "";
		}
		wb.close();
		fi.close();
		return data;
	}

	public static void setCellData(int rownum, int colnum, String data) {
		row = ws.getRow(rownum);
		cell = row.createCell(colnum);
		cell.setCellValue(data);
		try {
			fo = new FileOutputStream(strFilepath);
			wb.write(fo);
			wb.close();
			fi.close();
			fo.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String getDataBasedOnTCandAttribute(String strAttributeName, String strTCname) throws IOException {
		DataFormatter formatter = new DataFormatter();
		int rowCount = ws.getLastRowNum() - ws.getFirstRowNum();
		int gRow = 0;
		int gCol = 0;
		for (int i = 0; i < rowCount - 1; i++) {
			Row row = ws.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				String s = formatter.formatCellValue(row.getCell(j));
				if (s.equalsIgnoreCase(strAttributeName)) {
					gRow = i;
				}
				if (s.equalsIgnoreCase(strTCname)) {
					gCol = j;
				}
			}
		}
		Row gRow1 = ws.getRow(gRow);
		String cellVal = formatter.formatCellValue(gRow1.getCell(gCol));
		wb.close();
		fi.close();
		return cellVal;
	}
}
