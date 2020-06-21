package in.ibm.chirra.saf.FWSetup;

import org.testng.TestNG;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import in.ibm.chirra.saf.report.HTMLReport;
import in.ibm.chirra.saf.utilities.DateTimeWork;
import in.ibm.chirra.saf.utilities.FileDirectoryWork;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FW_DataLoad {

	/**
	 * Adds the test and class.
	 *
	 * @param doc the document
	 * @param parentElement the parent element
	 * @param className the class name
	 * @param TCID
	 * @param browser
	 * @return the element
	 */
	private Element addTestAndClass(Document doc, Element parentElement, String className, String TCID,
			String browser) {

		// Test
		Element test1 = doc.createElement("test");
		parentElement.appendChild(test1);

		Attr attr = doc.createAttribute("name");
		attr.setValue(TCID);
		test1.setAttributeNode(attr);

		// Parameter 1
		Element parameter = doc.createElement("parameter");
		test1.appendChild(parameter);

		attr = doc.createAttribute("name");
		attr.setValue("TC_ID");
		parameter.setAttributeNode(attr);

		attr = doc.createAttribute("value");
		attr.setValue(TCID);
		parameter.setAttributeNode(attr);

		// Parameter 2
		parameter = doc.createElement("parameter");
		test1.appendChild(parameter);

		attr = doc.createAttribute("name");
		attr.setValue("BROWSER");
		parameter.setAttributeNode(attr);

		attr = doc.createAttribute("value");
		attr.setValue(browser);
		parameter.setAttributeNode(attr);

		// Parameter 3
		parameter = doc.createElement("parameter");
		test1.appendChild(parameter);

		attr = doc.createAttribute("name");
		attr.setValue("CLASSNAME");
		parameter.setAttributeNode(attr);

		attr = doc.createAttribute("value");
		attr.setValue(className);
		parameter.setAttributeNode(attr);

		// Classes
		Element classesObj = doc.createElement("classes");
		test1.appendChild(classesObj);
		// Class
		Element classObj = doc.createElement("class");
		classesObj.appendChild(classObj);

		attr = doc.createAttribute("name");
		attr.setValue("in.ibm.chirra.saf.test." + className);
		classObj.setAttributeNode(attr);

		return parentElement;
	}

	/**
	 * Generate custom exe test NGXMLF ile.
	 *
	 * @param exeData the exe data
	 */
	private void generateCustomExeTestNGXMLFIle(ArrayList<String> exeData) {

		Document doc = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		// Suite
		Element rootElement = doc.createElement("suite");
		doc.appendChild(rootElement);

		Attr attr = null;
		if (FW_Constants.isParallelExecution.equalsIgnoreCase("yes")) {
			attr = doc.createAttribute("parallel");
			attr.setValue("tests");
			rootElement.setAttributeNode(attr);

			String threadCount = FW_Constants.parallelExecutionCount.toString();
			attr = doc.createAttribute("thread-count");
			attr.setValue(threadCount);
			rootElement.setAttributeNode(attr);
		}
		attr = doc.createAttribute("name");
		attr.setValue("Suite");
		rootElement.setAttributeNode(attr);

		for (String row : exeData) {
			String[] xmlParameters = row.split("###");
			rootElement = addTestAndClass(doc, rootElement, xmlParameters[0].toString().trim(),
					xmlParameters[1].toString().trim(), xmlParameters[2].toString().trim());
		}

		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			String testNGXMLPath = "TestNG_" + System.currentTimeMillis() + ".xml";
			System.setProperty("exeTestNGXML", testNGXMLPath);

			StreamResult result = new StreamResult(new File(testNGXMLPath));
			transformer.transform(source, result);

		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Run test NGXML.
	 */
	private static void runTestNGXML() {
		TestNG runner = new TestNG();
		List<String> suitefiles = new ArrayList<String>();
		suitefiles.add(System.getProperty("exeTestNGXML"));
		runner.setTestSuites(suitefiles);
		runner.run();

		if (FW_Constants.configFileData_LHM.get("Want_To_Rerun_FailedCases_Once").toString().equalsIgnoreCase("Yes")) {
			TestNG failRunner = new TestNG();
			List<String> suitesFail = new ArrayList<String>();
			// suites.clear();
			String failedXMlFilePath = System.getProperty("user.dir") + "\\test-output\\testng-failed.xml";
			File failedXMlFilePathFileObj = new File(failedXMlFilePath);
			suitesFail.add(failedXMlFilePathFileObj.getPath());
			failRunner.setTestSuites(suitesFail);
			failRunner.run();
		}
	}

	/**
	 * Creates the results folder.
	 */
	public void createResultsFolder() {

		// Create Results folder if not exists
		if (!FileDirectoryWork.isFileOrDirectoryExists(FW_Constants.htmlReport_resultsFolderPath)) {
			FileDirectoryWork.createDirectory(FW_Constants.htmlReport_resultsFolderPath);
		}

		// Add current execution folder in inside Results
		String newFolderName = "" + DateTimeWork.getCurrentDateTime();
		FW_Constants.htmlReport_resultsFolderPath = FW_Constants.htmlReport_resultsFolderPath + newFolderName + "/";

		FileDirectoryWork.createDirectory(FW_Constants.htmlReport_resultsFolderPath);

		HTMLReport.createExtentReport(FW_Constants.htmlReport_resultsFolderPath);
	}

	/**
	 * Kill all drivers.
	 */
	private void killAllDrivers() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fw execute method.
	 */
	public void fw_executeMethod() {

		// Close all drivers from Task Manager
		killAllDrivers();

		// Creating Result folder if not exists and Generate subfolder with timestamp
		// for current execution results
		createResultsFolder();

		// Load config file data
		FW_Constants.loadConfigSheetInfo();

		// Load ALL Test Data sheets data
		FW_Constants.loadTestDataSheetsData();

		ArrayList<String> exeData = new ArrayList<String>();
		for (String key : FW_Constants.testDataSheet_data_LHM_LHM.keySet())
			exeData.add(key);

		// Create CUSTOME testNG.xml
		generateCustomExeTestNGXMLFIle(exeData);

		// Run CustomTextNG.XML
		runTestNGXML();
	}
}
