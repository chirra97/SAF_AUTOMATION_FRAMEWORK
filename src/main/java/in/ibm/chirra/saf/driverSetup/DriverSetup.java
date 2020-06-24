package in.ibm.chirra.saf.driverSetup;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentTest;

import in.ibm.chirra.saf.FWSetup.FWConstants;
import in.ibm.chirra.saf.FWSetup.FWGlobalReferenceClass;
import in.ibm.chirra.saf.report.HTMLReport;
import in.ibm.chirra.saf.report.WordGenerator;
import in.ibm.chirra.saf.utilities.CustomCode;
import in.ibm.chirra.saf.utilities.DateTimeWork;

// TODO: Auto-generated Javadoc
/**
 * The Class DriverSetup.
 */
public class DriverSetup {

	/** The fw class ref obj. */
	public FWGlobalReferenceClass fwClassRefObj = null;

	/**
	 * Load class test data and html and log report initiation.
	 *
	 * @param CLASSNAME the classname
	 * @param TC_ID     the tc id
	 * @param BROWSER   the browser
	 * @throws Exception
	 */
	@Parameters({ "CLASSNAME", "TC_ID", "BROWSER" })
	@BeforeClass
	public void loadClassTestDataAndHtmlAndLogReportInitiation(String CLASSNAME, String TC_ID, String BROWSER)
			throws Exception {

		// Load class Test Data
		FWConstants fwConsObj = new FWConstants();
		LinkedHashMap<String, String> classTestData_LHM = fwConsObj
				.loadTestDataForClass(CLASSNAME + "###" + TC_ID + "###" + BROWSER);

		// Results - Word document generate
		String documnetFilePath = FWConstants.htmlReport_resultsFolderPath + "/" + TC_ID + "_"
				+ DateTimeWork.getCurrentDateTime() + ".doc";
		WordGenerator wordDocGenerateObj = new WordGenerator(documnetFilePath);
		wordDocGenerateObj.createNewDocument(TC_ID);

		// Results - Log file generate
		String logFilePath = FWConstants.htmlReport_resultsFolderPath + "/" + TC_ID + "_"
				+ DateTimeWork.getCurrentDateTime() + ".log";
		Logger log4jObj = Logger.getLogger(TC_ID + "-" + DateTimeWork.getCurrentDateTime());
		Layout layoutObj = new PatternLayout("%d %m %n");
		Appender appenderObj = new FileAppender(layoutObj, logFilePath);
		log4jObj.addAppender(appenderObj);

		HTMLReport htmlReportObj = new HTMLReport();

		String driverType = FWConstants.driverType;
		System.out.println(">>>>>>>>>>> driverType : " + driverType);

		// Load driver
		driverSetup(driverType, BROWSER, TC_ID, classTestData_LHM, htmlReportObj, wordDocGenerateObj, log4jObj);
	}

	/**
	 * Close browser.
	 */
	@AfterClass
	public void closeBrowser() {
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	/** The driver. */
	private WebDriver driver = null;

	/**
	 * Driver setup.
	 *
	 * @param driverType        the driver type
	 * @param browser           the browser
	 * @param TC_ID             the tc id
	 * @param classTestData_LHM the class test data LHM
	 * @param htmlReportObj     the html report obj
	 * @param log4jObj          the log 4 j obj
	 * @throws IOException 
	 */
	private void driverSetup(String driverType, String browser, String TC_ID,
			LinkedHashMap<String, String> classTestData_LHM, HTMLReport htmlReportObj, WordGenerator wordDocGenerateObj,
			Logger log4jObj) throws IOException {

		System.out.println(">>>>>>>>>> driver setup called>>> " + driverType + " >> " + browser);

		if (driverType.equalsIgnoreCase("Web Driver")) {
			if (browser.equalsIgnoreCase("Chrome")) {
				// Load the driver object
				String appURL = classTestData_LHM.get("URL");

				System.out.println("appURL : " + appURL);

				driver = launchChromeBrowser(appURL);

				ExtentTest reportTCObject = htmlReportObj.addTestCaseToReport(TC_ID, TC_ID);

				// Load all framework class Reference objects
				fwClassRefObj = new FWGlobalReferenceClass(driver, classTestData_LHM, htmlReportObj, reportTCObject,
						wordDocGenerateObj, log4jObj);

				htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
						TC_ID, TC_ID);
				log4jObj.info("******************************************************************************");
				log4jObj.info("===>     " + TC_ID + " - " + DateTimeWork.getCurrentDateTime());
				log4jObj.info("******************************************************************************");
			}
		}
	}

	

	/**
	 * Launch Chrome browser.
	 *
	 * @param appURL
	 * @return driver Object
	 * @throws IOException 
	 */
	private WebDriver launchChromeBrowser(String appURL) throws IOException {
		
		System.setProperty("webdriver.chrome.driver", FWConstants.driverPath + "\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(appURL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

}
