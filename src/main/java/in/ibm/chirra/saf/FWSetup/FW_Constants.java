package in.ibm.chirra.saf.FWSetup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import in.ibm.chirra.saf.utilities.ExcelWork;

public class FW_Constants {
	
	
	private final static String CONFIGFILEPATH = "src\\main\\resources\\Config\\Config.xlsx";
	private final static String CONFIGSHEETNAME = "Initial_DataSetUp";
	
	public static String driverType = "";
	public static String isParallelExecution = "";
	public static String parallelExecutionCount = "";
	public static int maxWaitTimeForElement = 30;
	public static String testDataSheetNames = "";
	
	/*
	 * Author : suresh chirra
	 * 
	 * Method will load all the config excel file data into Linked hash map
	 */
	
	//Step #1
	public static LinkedHashMap<String, String> configFileData_LHM = null;
	public static void lodConfigSheetInfo() {
		configFileData_LHM = new LinkedHashMap<String, String>();
		try {
			configFileData_LHM = ExcelWork.getConfigFileData(CONFIGFILEPATH, CONFIGSHEETNAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
		driverType = configFileData_LHM.get("Driver_Type");
		maxWaitTimeForElement = Integer.parseInt(configFileData_LHM.get("Element_Max_Wait_Time"));
		isParallelExecution = configFileData_LHM.get("Is_Parallel_Execution");
		parallelExecutionCount= configFileData_LHM.get("Parallel_Execution_Count");
		
		//testDataSheetNames= configFileData_LHM.get("Test_Data_Sheet_Names");
		testDataSheetNames = ExcelWork.getAllSheetNames(TESTDATAFILEPATH);
		
		System.out.println("testDataSheetNames : "+testDataSheetNames);
			
		System.out.println("configFileData_LHM : "+configFileData_LHM);
	}
	
	/*
	 * Author : suresh chirra
	 * 
	 * Method will load all the Test data excel file sheets data into Linked hash map
	 */
	
	//Step #2
	public static LinkedHashMap<String, LinkedHashMap<String, String>> testDataSheet_data_LHM_LHM= null;
	private static final String TESTDATAFILEPATH = "src\\main\\resources\\TestData\\TestData.xlsx";
	
	public static void loadTestDataSheetsData() {
		ArrayList<String> uniqueKeys_AL = new ArrayList<String>();
		uniqueKeys_AL.add("ClassName");uniqueKeys_AL.add("TestCaseID");uniqueKeys_AL.add("Browser");
		String[] testDataSheetNames_split = testDataSheetNames.split(",");
		testDataSheet_data_LHM_LHM = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		
		for (int i = 0; i < testDataSheetNames_split.length; i++) {
			LinkedHashMap<String, LinkedHashMap<String, String>> sheetData_LHM = new 
					LinkedHashMap<String, LinkedHashMap<String, String>>();
			String testDataSheetName = testDataSheetNames_split[i];
			try {
				sheetData_LHM = ExcelWork.getTestDataSheetDataIntoLHM(TESTDATAFILEPATH, testDataSheetName);
				sheetData_LHM = ExcelWork.generateUniqueKeyValueLHM(sheetData_LHM, uniqueKeys_AL, "###");
			} catch (Exception e) {
			}
			testDataSheet_data_LHM_LHM.putAll(sheetData_LHM);
		}
		System.out.println("testDataSheet_data_LHM_LHM : "+testDataSheet_data_LHM_LHM);
	}
	
	/*
	 * Author : suresh chirra
	 * 
	 * Method will load class Test data into Linked hash map
	 */
	//Step #3
	public LinkedHashMap<String, String> classTestData_LHM = null;
	public LinkedHashMap<String, String> loadTestDataForClass(String key) {
		classTestData_LHM = testDataSheet_data_LHM_LHM.get(key);
		System.out.println(key+" <> "+classTestData_LHM);
		return classTestData_LHM;
	}
	
	/*
	 * public String getTestData(String columnName) { String value = ""; try { value
	 * = classTestData_LHM.get(columnName); } catch (Exception e) { } return value;
	 * }
	 */
}
