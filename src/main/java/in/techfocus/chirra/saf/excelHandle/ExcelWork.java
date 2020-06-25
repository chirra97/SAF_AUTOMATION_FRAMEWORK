package in.techfocus.chirra.saf.excelHandle;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import in.techfocus.chirra.saf.FWSetup.FWConstants;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ExcelWork {
	static String excelSheetName;
	static int excelSheetNumber;
	private static FileInputStream fis = null;
	private static Workbook fis_workbook;
	private static Sheet fis_worksheet;
	private static String excelFilePath;

	public static void configExcelFilePath(String fileName) {
		try {
			fis = new FileInputStream(new File(fileName));
			excelFilePath = fileName;
		} catch (FileNotFoundException e) {
			System.err.println("Unable to find the Excel file in path : " + fileName);
		}
	}

	public static void configWorkBook() {
		try {
			fis_workbook = new XSSFWorkbook(fis);
		} catch (Exception e) {
			System.err.println("Uanble to config Workbook : " + excelFilePath);
		}
	}

	public static void configWorkSheet(String sheetName) {
		try {
			fis_worksheet = fis_workbook.getSheet(sheetName);
			excelSheetName = sheetName;
		} catch (Exception e) {
			System.err.println("Uanble to find the Sheet name '" + sheetName + "'in Excel file : " + excelFilePath);
		}
	}

	public static void configWorkSheet(int sheetNumber) {
		try {
			fis_worksheet = fis_workbook.getSheetAt(sheetNumber);
			excelSheetNumber = sheetNumber;
		} catch (Exception e) {
			System.err.println("Uanble to find the Sheet Number '" + sheetNumber + "'in Excel file : " + excelFilePath);
		}
	}

	public static int getSheetCount() {
		int noOfSheet = 0;
		try {
			noOfSheet = fis_workbook.getNumberOfSheets();
		} catch (Exception e) {
		}
		return noOfSheet;
	}

	public static String getSheetName(int sheetIndex) {
		String sheetName = "";
		try {
			sheetName = fis_workbook.getSheetName(sheetIndex);
		} catch (Exception e) {
		}
		return sheetName;
	}

	public static int getRowCount() {
		int numberOfRows = -1;
		try {
			numberOfRows = fis_worksheet.getLastRowNum();
		} catch (Exception e) {
		}
		return numberOfRows;
	}

	public static int getColumnCount(int rowNumber) {
		int numberOfColumns = -1;
		try {
			numberOfColumns = fis_worksheet.getRow(rowNumber).getLastCellNum();
		} catch (Exception e) {
		}
		return numberOfColumns;
	}

	public static String getCellValue(int colNumber, int rowNumber) {
		Cell fis_cell = null;
		String fis_cellValue = "-1";
		try {
			fis_cell = fis_worksheet.getRow(rowNumber).getCell(colNumber);
			try {
				if (fis_cell.getCellType() == CellType.STRING) {
					fis_cellValue = fis_cell.getStringCellValue();
				} else if (fis_cell.getCellType() == CellType.FORMULA) {
					switch (fis_cell.getCellType()) {
					case NUMERIC:
						fis_cellValue = "" + fis_cell.getNumericCellValue();
						break;
					case STRING:
						fis_cellValue = "" + fis_cell.getRichStringCellValue();
						break;
					}
				} else if (fis_cell.getCellType() == CellType.NUMERIC) {
					fis_cellValue = "" + fis_cell.getNumericCellValue();
				}
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		return fis_cellValue;
	}

	public static int toNumber(String name) {
		int number = 0;
		for (int i = 0; i < name.length(); i++) {
			number = number * 26 + (name.charAt(i) - ('A' - 1));
		}
		return number;
	}

	public static String getCellValue(String colAlphabit, int rowNumber) {
		int colNumber = toNumber(colAlphabit.toUpperCase()) - 1;
		Cell fis_cell = null;
		String fis_cellValue = "-1";
		try {
			fis_cell = fis_worksheet.getRow(rowNumber).getCell(colNumber);
			try {
				if (fis_cell.getCellType() == CellType.NUMERIC) {
					fis_cellValue = fis_cell.getStringCellValue();
				} else if (fis_cell.getCellType() == CellType.FORMULA) {
					switch (fis_cell.getCachedFormulaResultType()) {
					case FORMULA:
						fis_cellValue = "" + fis_cell.getNumericCellValue();
						break;
					case STRING:
						fis_cellValue = "" + fis_cell.getRichStringCellValue();
						break;
					}
				} else if (fis_cell.getCellType() == CellType.NUMERIC) {
					fis_cellValue = "" + fis_cell.getNumericCellValue();
				}
			} catch (Exception e) {
				fis_cellValue = "-1";
			}
		} catch (Exception e) {
		}
		return fis_cellValue;
	}

	public static int getRowNumber(String searchText) {
		searchText = searchText.trim();
		int numberOfRows = getRowCount();
		int numberOfColumns = 0;
		for (int i = 0; i <= numberOfRows; i++) {
			int columnsCount = getColumnCount(i);
			if (columnsCount >= numberOfColumns)
				numberOfColumns = columnsCount;
		}
		String cellText = "";
		for (int i = 0; i <= numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns; j++) {
				cellText = getCellValue(j, i).trim();
				if (cellText.equalsIgnoreCase(searchText))
					return i;
			}
		}
		return -1;
	}

	public static int getColumnNumber(String searchText) {
		searchText = searchText.trim();
		int numberOfRows = getRowCount();
		int numberOfColumns = 0;
		for (int i = 0; i <= numberOfRows; i++) {
			int columnsCount = getColumnCount(i);
			if (columnsCount >= numberOfColumns)
				numberOfColumns = columnsCount;
		}
		String cellText = "";
		for (int i = 0; i <= numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns; j++) {
				cellText = getCellValue(j, i).trim();
				if (cellText.equalsIgnoreCase(searchText)) {
					return j;
				}
			}
		}
		return -1;
	}

	public static LinkedHashMap<String, LinkedHashMap<String, String>> getExcelDataIntoLHM(String excelFilePath,
			String sheetName) {
		FileInputStream fis;
		Workbook fis_workbook;
		Sheet fis_worksheet = null;
		try {
			fis = new FileInputStream(new File(excelFilePath));
			fis_workbook = new XSSFWorkbook(fis);
			fis_worksheet = fis_workbook.getSheet(sheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int numberOfRows = fis_worksheet.getLastRowNum();
		int numberOfColumns = fis_worksheet.getRow(0).getLastCellNum();

		LinkedHashMap<String, LinkedHashMap<String, String>> excelFileData_lhm = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		Cell fis_cell = null;
		for (int i = 0; i <= numberOfRows; i++) {
			LinkedHashMap<String, String> rowData_lhm = new LinkedHashMap<String, String>();
			for (int j = 0; j < numberOfColumns; j++) {
				String columnName = fis_worksheet.getRow(0).getCell(j).toString();
				String columnValue = fis_worksheet.getRow(i).getCell(j).toString();
				rowData_lhm.put(columnName, columnValue);
			}
			if (i == 0)
				continue;
			excelFileData_lhm.put("" + i, rowData_lhm);
		}
		return excelFileData_lhm;
	}

	public static String getAllSheetNames(String excelFilePath) {
		FileInputStream fis;
		Workbook fis_workbook = null;
		try {
			fis = new FileInputStream(new File(excelFilePath));
			fis_workbook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String sheetNames = "";
		for (int i = 0; i < fis_workbook.getNumberOfSheets(); i++) {
			sheetNames = sheetNames + "," + fis_workbook.getSheetName(i);
		}
		sheetNames = sheetNames.replaceFirst(",", "");
		return sheetNames;
	}

	public static LinkedHashMap<String, LinkedHashMap<String, String>> getTestDataSheetDataIntoLHM(String excelFilePath,
			String sheetName) {
		FileInputStream fis;
		Workbook fis_workbook;
		Sheet fis_worksheet = null;
		try {
			fis = new FileInputStream(new File(excelFilePath));
			fis_workbook = new XSSFWorkbook(fis);
			fis_worksheet = fis_workbook.getSheet(sheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int numberOfRows = fis_worksheet.getLastRowNum();
		int numberOfColumns = fis_worksheet.getRow(0).getLastCellNum();

		LinkedHashMap<String, LinkedHashMap<String, String>> excelFileData_lhm = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		Cell fis_cell = null;
		for (int i = 0; i <= numberOfRows; i++) {
			String exeStatus = "Y";
			LinkedHashMap<String, String> rowData_lhm = new LinkedHashMap<String, String>();
			for (int j = 0; j < numberOfColumns; j++) {
				String columnName = fis_worksheet.getRow(0).getCell(j).toString();
				String columnValue = fis_worksheet.getRow(i).getCell(j).toString();
				rowData_lhm.put(columnName, columnValue);

				if (columnName.equalsIgnoreCase("ExecutionStatus")) {
					exeStatus = columnValue;
					if (exeStatus.equalsIgnoreCase("N"))
						break;
				}
			}
			if (i == 0 || exeStatus.equalsIgnoreCase("N"))
				continue;
			excelFileData_lhm.put("" + i, rowData_lhm);
		}
		return excelFileData_lhm;
	}

	public static LinkedHashMap<String, String> getConfigFileData(String excelFilePath, String sheetName)
			throws IOException {
		FileInputStream fis = null;
		Workbook fis_workbook;
		Sheet fis_worksheet = null;
		try {
			fis = new FileInputStream(new File(excelFilePath));
			fis_workbook = new XSSFWorkbook(fis);
			fis_worksheet = fis_workbook.getSheet(sheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int numberOfRows = fis_worksheet.getLastRowNum();
		System.out.println("numberOfRows : " + numberOfRows);
		LinkedHashMap<String, String> excelFileData_lhm = new LinkedHashMap<String, String>();
		Cell fis_cell = null;
		for (int i = 0; i <= numberOfRows; i++) {
			String columnName = "";
			try {
				columnName = fis_worksheet.getRow(i).getCell(0).toString();
			} catch (Exception e) {
			}
			String columnValue = "";
			try {
				columnValue = fis_worksheet.getRow(i).getCell(1).toString();
			} catch (Exception e) {
			}
			if (i == 0 || columnName.trim().length() == 0)
				continue;
			excelFileData_lhm.put(columnName, columnValue);
		}
		fis.close();
		return excelFileData_lhm;
	}

	public static LinkedHashMap<String, LinkedHashMap<String, String>> generateUniqueKeyValueLHM(
			LinkedHashMap<String, LinkedHashMap<String, String>> excelFileData_lhm, ArrayList<String> uniqueKeyAL,
			String delimiter) {
		LinkedHashMap<String, LinkedHashMap<String, String>> excelFileDataKeyValLHM = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		for (String outerKey : excelFileData_lhm.keySet()) {
			LinkedHashMap<String, String> rowDataLHM = new LinkedHashMap<String, String>();
			rowDataLHM = excelFileData_lhm.get(outerKey);
			String uniqueKey = "";
			for (String innerKey : rowDataLHM.keySet()) {
				String value = rowDataLHM.get(innerKey);
				if (uniqueKeyAL.contains(innerKey))
					uniqueKey = uniqueKey + delimiter + value;
			}
			uniqueKey = uniqueKey.replaceFirst(delimiter, "");
			excelFileDataKeyValLHM.put(uniqueKey, rowDataLHM);
		}
		System.out.println("excelFileDataKeyValLHM : " + excelFileDataKeyValLHM);
		return excelFileDataKeyValLHM;
	}

	public static void generateDataSetsLHM(LinkedHashMap<String, LinkedHashMap<String, String>> excelFileData_lhm,
			ArrayList<String> uniqueKayAL, String delimiter) {
		LinkedHashMap<String, ArrayList<String>> excelFileDataSetsLHM = new LinkedHashMap<String, ArrayList<String>>();
		for (String outerKey : excelFileData_lhm.keySet()) {
			LinkedHashMap<String, String> rowDataLHM = new LinkedHashMap<String, String>();
			rowDataLHM = excelFileData_lhm.get(outerKey);
			ArrayList<String> setsRowNumAL = new ArrayList<String>();
			String uniqueKey = "";
			for (String innerKey : rowDataLHM.keySet()) {
				String value = rowDataLHM.get(innerKey);
				if (uniqueKayAL.contains(innerKey))
					uniqueKey = uniqueKey + delimiter + value;
			}
			uniqueKey = uniqueKey.replaceFirst(delimiter, "");

			if (excelFileDataSetsLHM.containsKey(uniqueKey)) {
				setsRowNumAL = excelFileDataSetsLHM.get(uniqueKey);
				setsRowNumAL.add("" + outerKey);
			} else {
				setsRowNumAL.add("" + outerKey);
			}
			excelFileDataSetsLHM.put(uniqueKey, setsRowNumAL);
		}
		System.out.println("excelFileDataSetsLHM : " + excelFileDataSetsLHM);
	}

	public static void setDataIntoExcel(String outputFileName, String sheetName, int columnNumber, int rowNumber,
			String writeText) throws IOException {

		FileInputStream inputStream = null;
		Workbook workbook = null;
		try {
			inputStream = new FileInputStream(new File(outputFileName));
			workbook = WorkbookFactory.create(inputStream);
		} catch (Exception e) {
			System.err.println("Unable to find the Excel file in path : " + outputFileName);
			System.exit(0);
		}
		Sheet sheet = null;
		try {
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			System.err.println("Unable to find the sheet '" + sheetName + "' in Excel file '" + outputFileName + "'");
			System.exit(0);
		}
		int totalRowCount = sheet.getLastRowNum();
		Cell cell = null;
		if (totalRowCount >= rowNumber)
			cell = sheet.getRow(rowNumber).createCell(columnNumber);
		else
			cell = sheet.createRow(rowNumber).createCell(columnNumber);
		cell.setCellValue(writeText); // Actual Value Set
		inputStream.close();
		FileOutputStream output_file = new FileOutputStream(new File(outputFileName));
		workbook.write(output_file);
		output_file.close();
		System.out.println("Done!");
	}

	public static void main(String[] args) {

		LinkedHashMap<String, LinkedHashMap<String, String>> excelFileData_lhm = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		excelFileData_lhm = getExcelDataIntoLHM("C:/Users/SureshChirra/Desktop/Demo.xlsx", "Sheet1");
		System.out.println("excelFileData_lhm : " + excelFileData_lhm);
		ArrayList<String> ukey = new ArrayList<String>();
		ukey.add("aa");
		ukey.add("cc");

		generateUniqueKeyValueLHM(excelFileData_lhm, ukey, "###");

		generateDataSetsLHM(excelFileData_lhm, ukey, "###");

	}

}
