package in.ibm.chirra.saf.FWSetup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import in.ibm.chirra.saf.excelHandle.ExcelWork;

public class FWConstants {

	public static String htmlReport_resultsFolderPath = "src/main/resources/Results/";
	private final static String CONFIGFILEPATH = "src/main/resources/Config/Config.xlsx";
	private final static String CONFIGSHEETNAME = "Initial_DataSetUp";

	public static String driverType = "";
	public static String isParallelExecution = "";
	public static String parallelExecutionCount = "";
	public static int maxWaitTimeForElement = 0;
	public static String testDataSheetNames = "";
	public static String wantToReRunFailedTestCasesOnce = "";

	/**
	 * Load config sheet info.
	 */
	// Step #1
	public static LinkedHashMap<String, String> configFileData_LHM = null;

	public static void loadConfigSheetInfo() {
		configFileData_LHM = new LinkedHashMap<String, String>();
		try {
			configFileData_LHM = ExcelWork.getConfigFileData(CONFIGFILEPATH, CONFIGSHEETNAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
		driverType = configFileData_LHM.get("Driver_Type");
		maxWaitTimeForElement = Integer.parseInt(configFileData_LHM.get("Element_Max_Wait_Time"));
		isParallelExecution = configFileData_LHM.get("Is_Parallel_Execution");
		parallelExecutionCount = configFileData_LHM.get("Parallel_Execution_Count");
		wantToReRunFailedTestCasesOnce = configFileData_LHM.get("Want_To_Rerun_FailedCases_Once");

		System.out.println("configFileData_LHM : " + configFileData_LHM);
	}
	

	/** The test data sheet data LH M LHM. */
	// Step #2
	public static LinkedHashMap<String, LinkedHashMap<String, String>> testDataSheet_data_LHM_LHM = null;
	private static final String TESTDATAFILEPATH = "src\\main\\resources\\TestData\\TestData.xlsx";

	public static void loadTestDataSheetsData() {
		ArrayList<String> uniqueKeys_AL = new ArrayList<String>();
		uniqueKeys_AL.add("ClassName");
		uniqueKeys_AL.add("TestCaseID");
		uniqueKeys_AL.add("Browser");
		
		testDataSheetNames = ExcelWork.getAllSheetNames(TESTDATAFILEPATH);
		String[] testDataSheetNames_split = testDataSheetNames.split(",");
		testDataSheet_data_LHM_LHM = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		for (int i = 0; i < testDataSheetNames_split.length; i++) {
			LinkedHashMap<String, LinkedHashMap<String, String>> sheetData_LHM = new LinkedHashMap<String, LinkedHashMap<String, String>>();
			String testDataSheetName = testDataSheetNames_split[i];
			try {
				sheetData_LHM = ExcelWork.getTestDataSheetDataIntoLHM(TESTDATAFILEPATH, testDataSheetName);
				sheetData_LHM = ExcelWork.generateUniqueKeyValueLHM(sheetData_LHM, uniqueKeys_AL, "###");
			} catch (Exception e) {
			}
			testDataSheet_data_LHM_LHM.putAll(sheetData_LHM);
		}
		System.out.println("testDataSheet_data_LHM_LHM : " + testDataSheet_data_LHM_LHM);
	}

	/**
	 * Load test data for class.
	 *
	 * @param class key
	 * @return the linked hash map
	 */
	// Step #3
	public LinkedHashMap<String, String> classTestData_LHM = null;

	/**
	 * Load test data for class.
	 *
	 * @param key the key
	 * @return the linked hash map
	 */
	public LinkedHashMap<String, String> loadTestDataForClass(String key) {
		classTestData_LHM = testDataSheet_data_LHM_LHM.get(key);
		System.out.println(key + " <> " + classTestData_LHM);
		return classTestData_LHM;
	}

}
