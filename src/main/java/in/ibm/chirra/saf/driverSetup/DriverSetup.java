package in.ibm.chirra.saf.driverSetup;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import in.ibm.chirra.saf.FWSetup.FW_ClassRefObjetcs;
import in.ibm.chirra.saf.FWSetup.FW_Constants;
import in.ibm.chirra.saf.report.HTMLReport;
import in.ibm.chirra.saf.utilities.CustomCode;
import in.ibm.chirra.saf.utilities.DateTimeWork;
import in.ibm.chirra.saf.utilities.FileDirectoryWork;

public class DriverSetup {

	HTMLReport htmlReportObj = null;
	String htmlReport_resultsFolderPath = null;

	@BeforeSuite
	public void initiate() {

		htmlReport_resultsFolderPath = "src/main/resources/Results/";

		// Create Results folder if not exists
		if (!FileDirectoryWork.isFileOrDirectoryExists(htmlReport_resultsFolderPath)) {
			FileDirectoryWork.createDirectory(htmlReport_resultsFolderPath);
		}

		// Add current execution Results folder
		String newFolderName = "" + DateTimeWork.getCurrentDateTime();
		htmlReport_resultsFolderPath = htmlReport_resultsFolderPath + newFolderName + "/";

		FileDirectoryWork.createDirectory(htmlReport_resultsFolderPath);

		htmlReportObj = new HTMLReport();
		htmlReportObj.createExtentReport(htmlReport_resultsFolderPath);
	}

	public FW_ClassRefObjetcs fwClassRefObj = null;

	@Parameters({ "CLASSNAME", "TC_ID", "BROWSER" })
	@BeforeClass
	public void beforeClassTestAnnLoad(String CLASSNAME, String TC_ID, String BROWSER) throws IOException {

		String driverType = FW_Constants.driverType;
		
		// Load class Test Data
		FW_Constants fwConsObj = new FW_Constants();
		LinkedHashMap<String, String> classTestData_LHM = fwConsObj
				.loadTestDataForClass(CLASSNAME + "###" + TC_ID + "###" + BROWSER);

		String logFilePath = htmlReport_resultsFolderPath + "/" + TC_ID + ".log";
		Logger log4jObj = Logger.getLogger(TC_ID);
		Layout layoutObj = new PatternLayout("%d %m %n");
		Appender appenderObj = new FileAppender(layoutObj, logFilePath);
		log4jObj.addAppender(appenderObj);
		
		//htmlReportObj = new HTMLReport();
		
		// Load driver
		loadDriver(driverType, BROWSER, TC_ID, classTestData_LHM, htmlReportObj, log4jObj);
	}

	@AfterClass
	public void afterClassTestAnnLoad() {
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	private WebDriver driver = null;

	private void loadDriver(String driverType, String browser, String TC_ID,
			LinkedHashMap<String, String> classTestData_LHM, HTMLReport htmlReportObj, Logger log4jObj) {

		if (driverType.equalsIgnoreCase("Web Driver")) {
			if (browser.equalsIgnoreCase("Chrome")) {
				// Load the driver object
				String appURL = classTestData_LHM.get("URL");
				driver = launChromeBrowser(appURL);
				// Load all framework class Reference objects
				fwClassRefObj = new FW_ClassRefObjetcs(driver, classTestData_LHM, htmlReportObj, log4jObj);
				htmlReportObj.addTestCaseToReport(TC_ID + "_" + DateTimeWork.getCurrentDateTime(), TC_ID);
				htmlReportObj.addTestStepToReportWithScreenShot(driver, "PASS", TC_ID, TC_ID);
			}
		}
	}

	private WebDriver launChromeBrowser(String appURL) {
		System.setProperty("webdriver.chrome.driver", CustomCode.getTempFolderPath() + "/Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(appURL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		return driver;
	}

}
