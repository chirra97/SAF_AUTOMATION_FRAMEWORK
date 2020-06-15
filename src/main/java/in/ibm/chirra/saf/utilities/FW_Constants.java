package in.ibm.chirra.saf.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FW_Constants {
	
	//Step #1
	private final static String CONFIGFILEPATH = "src\\main\\Config\\Config.xlsx";
	private final static String CONFIGSHEETNAME = "Initial_DataSetUp";
	
	public static String runDriverType = "";
	public static String isParallelExecution = "";
	public static String parallelExecutionCount = "";
	public static int maxWaitTimeForElement = 30;
	
	public static LinkedHashMap<String, String> configFileData_LHM = null;
	public static void lodConfigSheetInfo() {
		configFileData_LHM = new LinkedHashMap<String, String>();
		try {
			configFileData_LHM = ExcelWork.getConfigFileData(CONFIGFILEPATH, CONFIGSHEETNAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
		runDriverType = configFileData_LHM.get("Driver Type");
		isParallelExecution = configFileData_LHM.get("Is Parallel Execution");
		parallelExecutionCount= configFileData_LHM.get("Parallel Execution Count");
		maxWaitTimeForElement = Integer.parseInt(configFileData_LHM.get("Element Max Wait Time"));
		
		System.out.println("configFileData_LHM : "+configFileData_LHM);
	}
	
	//Step #2
	public static LinkedHashMap<String, LinkedHashMap<String, String>> testDataSheet_data_LHM_LHM= null;
	private final static String TESTDATAFILEPATH = "src\\main\\TestData\\TestData.xlsx";
	
	public static void loadTestDataSheetsData() {
		ArrayList<String> uniqueKeys_AL = new ArrayList<String>();
		uniqueKeys_AL.add("ClassName");uniqueKeys_AL.add("TestCaseID");uniqueKeys_AL.add("Browser");
		String[] testDataSheetNames = configFileData_LHM.get("Test Data Sheet Names").split(",");
		testDataSheet_data_LHM_LHM = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		
		for (int i = 0; i < testDataSheetNames.length; i++) {
			LinkedHashMap<String, LinkedHashMap<String, String>> sheetData_LHM = new 
					LinkedHashMap<String, LinkedHashMap<String, String>>();
			String testDataSheetName = testDataSheetNames[i];
			try {
				sheetData_LHM = ExcelWork.getTestDataSheetDataIntoLHM(TESTDATAFILEPATH, testDataSheetName);
				sheetData_LHM = ExcelWork.generateUniqueKeyValueLHM(sheetData_LHM, uniqueKeys_AL, "###");
			} catch (Exception e) {
			}
			testDataSheet_data_LHM_LHM.putAll(sheetData_LHM);
		}
		System.out.println("testDataSheet_data_LHM_LHM : "+testDataSheet_data_LHM_LHM);
	}
	
	//Step #3
	private static LinkedHashMap<String, String> classTestData_LHM = null;
	public static void loadTestDataForClass(String key) {
		classTestData_LHM = testDataSheet_data_LHM_LHM.get(key);
		System.out.println(key+" <> "+classTestData_LHM);
	}
	
	public static String getTestData(String columnName) {
		String value = "";
		try {
			value = classTestData_LHM.get(columnName);
		} catch (Exception e) {
		}
		return value;
	}
	
	
	
	public static void main(String[] args) {
		lodConfigSheetInfo();
		loadTestDataSheetsData();
	}
	
}
