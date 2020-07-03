package com.techfocus.chirra.saf.FWSetup;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.testng.TestNG;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.techfocus.chirra.saf.report.HTMLReport;
import com.techfocus.chirra.saf.utilities.CustomCode;
import com.techfocus.chirra.saf.utilities.DateTimeWork;
import com.techfocus.chirra.saf.utilities.FileDirectoryWork;

public class FWDataLoad {

	/**
	 * Version : 1.0
	 * 
	 * Adds the test and class.
	 *
	 * @param doc           the document
	 * @param parentElement the parent element
	 * @param className     the class name
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
		attr.setValue("com.techfocus.chirra.saf.test." + className);
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
		if (FWConstants.isParallelExecution.equalsIgnoreCase("yes")) {
			attr = doc.createAttribute("parallel");
			attr.setValue("tests");
			rootElement.setAttributeNode(attr);

			String threadCount = FWConstants.parallelExecutionCount.toString();
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

		if (FWConstants.wantToReRunFailedTestCasesOnce.equalsIgnoreCase("Yes")) {
			TestNG failRunner = new TestNG();
			List<String> suitesFail = new ArrayList<String>();
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
		if (!FileDirectoryWork.isFileOrDirectoryExists(FWConstants.htmlReport_resultsFolderPath)) {
			FileDirectoryWork.createDirectory(FWConstants.htmlReport_resultsFolderPath);
		}

		// Add current execution folder in inside Results
		String newFolderName = "" + DateTimeWork.getCurrentDateTime();
		FWConstants.htmlReport_resultsFolderPath = FWConstants.htmlReport_resultsFolderPath + newFolderName + "/";

		FileDirectoryWork.createDirectory(FWConstants.htmlReport_resultsFolderPath);

		HTMLReport.createExtentReport(FWConstants.htmlReport_resultsFolderPath);
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
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void fw_executeMethod() throws IOException {

		// Close all drivers from Task Manager
		killAllDrivers();

		// Creating Result folder if not exists and Generate subfolder with timestamp
		// for current execution results
		createResultsFolder();

		// Load config file data
		FWConstants.loadConfigSheetInfo();

		// Load ALL Test Data sheets data
		FWConstants.loadTestDataSheetsData();

		ArrayList<String> exeData = new ArrayList<String>();
		for (String key : FWConstants.testDataSheet_data_LHM_LHM.keySet())
			exeData.add(key);

		// Create CUSTOME testNG.xml
		generateCustomExeTestNGXMLFIle(exeData);

		// Run CustomTextNG.XML
		runTestNGXML();
	}
}
