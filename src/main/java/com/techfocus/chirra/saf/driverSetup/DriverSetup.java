package com.techfocus.chirra.saf.driverSetup;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentTest;

import com.techfocus.chirra.saf.FWSetup.FWConstants;
import com.techfocus.chirra.saf.FWSetup.FWGlobalReferenceClass;
import com.techfocus.chirra.saf.report.HTMLReport;
import com.techfocus.chirra.saf.report.WordGenerator;
import com.techfocus.chirra.saf.utilities.CustomCode;
import com.techfocus.chirra.saf.utilities.DateTimeWork;

import io.github.bonigarcia.wdm.WebDriverManager;

// TODO: Auto-generated Javadoc
/**
 * The Class DriverSetup.
 */
public class DriverSetup {

	/** The fw class ref obj. */
	public FWGlobalReferenceClass fwGlobalReferenceClassObj = null;

	/**
	 * Load class test data and html and log report initiation.
	 *
	 * @param CLASSNAME the classname
	 * @param TC_ID     the tc id
	 * @param BROWSER   the browser
	 */
	@Parameters({ "CLASSNAME", "TC_ID", "BROWSER" })
	@BeforeClass
	public void loadClassTestDataAndHtmlAndLogReportInitiation(String CLASSNAME, String TC_ID, String BROWSER) {

		// Load class Test Data
		FWConstants fwConsObj = new FWConstants();
		LinkedHashMap<String, String> classTestData_LHM = fwConsObj
				.loadTestDataForClass(CLASSNAME + "###" + TC_ID + "###" + BROWSER);

		// Results - Word document generate
		String documnetFilePath = FWConstants.htmlReport_resultsFolderPath + "/" + TC_ID + "_"
				+ DateTimeWork.getCurrentDateTime() + ".doc";
		WordGenerator wordDocGenerateObj = new WordGenerator(documnetFilePath);
		try {
			wordDocGenerateObj.createNewDocument(TC_ID);
		} catch (Exception e) {
		}

		// Results - Log file generate
		String logFilePath = FWConstants.htmlReport_resultsFolderPath + "/" + TC_ID + "_"
				+ DateTimeWork.getCurrentDateTime() + ".log";
		Logger log4jObj = Logger.getLogger(TC_ID + "-" + DateTimeWork.getCurrentDateTime());
		Layout layoutObj = new PatternLayout("%d %m %n");
		Appender appenderObj = null;
		try {
			appenderObj = new FileAppender(layoutObj, logFilePath);
		} catch (IOException e) {
		}
		log4jObj.addAppender(appenderObj);

		HTMLReport htmlReportObj = new HTMLReport();

		String driverType = FWConstants.driverType;
		System.out.println(">>>>>>>>>>> driverType : " + driverType);

		// Load driver
		try {
			driverSetup(driverType, BROWSER, TC_ID, classTestData_LHM, htmlReportObj, wordDocGenerateObj, log4jObj);
		} catch (IOException e) {
		}
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
	 * @param driverType         the driver type
	 * @param browser            the browser
	 * @param TC_ID              the tc id
	 * @param classTestData_LHM  the class test data LHM
	 * @param htmlReportObj      the html report obj
	 * @param wordDocGenerateObj the word doc generate obj
	 * @param log4jObj           the log 4 j obj
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void driverSetup(String driverType, String browser, String TC_ID,
			LinkedHashMap<String, String> classTestData_LHM, HTMLReport htmlReportObj, WordGenerator wordDocGenerateObj,
			Logger log4jObj) throws IOException {

		System.out.println(">>>>>>>>>> driver setup called>>> " + driverType + " >> " + browser);

		if (driverType.equalsIgnoreCase("Web Driver")) {

			// Load the driver object
			String appURL = classTestData_LHM.get("URL");
			System.out.println("appURL : " + appURL);

			switch (browser) {
			case "Chrome":
				driver = launchChromeBrowser(false, false, appURL);
				break;
			case "Chrome_DriverAutoConfig":
				driver = launchChromeBrowser(true, false, appURL);
				break;
			case "Chrome_Headless":
				driver = launchChromeBrowser(false, true, appURL);
				break;
			case "Chrome_DriverAutoConfig_Headless":
				driver = launchChromeBrowser(true, true, appURL);
				break;

			default:
				break;
			}

			ExtentTest reportTCObject = htmlReportObj.addTestCaseToReport(TC_ID, TC_ID);

			// Load all framework class Reference objects
			fwGlobalReferenceClassObj = new FWGlobalReferenceClass(driver, classTestData_LHM, htmlReportObj,
					reportTCObject, wordDocGenerateObj, log4jObj);

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS", TC_ID,
					TC_ID);
			log4jObj.info("******************************************************************************");
			log4jObj.info("===>     " + TC_ID + " - " + DateTimeWork.getCurrentDateTime());
			log4jObj.info("******************************************************************************");
		}
	}

	/**
	 * Launch IE browser with auto driver config.
	 *
	 * @param driverAutoConfig the driver auto config
	 * @param isHeadLess       the is head less
	 * @param appURL           the app URL
	 * @return the web driver
	 */
	public WebDriver launchIEBrowserWithAutoDriverConfig(boolean driverAutoConfig, boolean isHeadLess, String appURL) {

		if (driverAutoConfig)
			WebDriverManager.iedriver().setup();
		else
			System.setProperty("webdriver.ie.driver", FWConstants.driverPath + "\\IEDriverServer.exe");

		DesiredCapabilities ieCapabilities = new DesiredCapabilities();
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		ieCapabilities.setCapability("ignoreZoomSetting", true);
		ieCapabilities.setCapability("ignoreProtectedModeSettings", true);

		@SuppressWarnings("deprecation")
		WebDriver driver = new InternetExplorerDriver(ieCapabilities);
		driver.manage().timeouts().implicitlyWait(FWConstants.maxWaitTimeForElement, TimeUnit.SECONDS);

		// Navigate to a URL
		driver.get(appURL);
		if (!isHeadLess)
			driver.manage().window().maximize();

		return driver;
	}

	/**
	 * Launch chrome browser.
	 *
	 * @param driverAutoConfig the driver auto config
	 * @param isHeadLess       the is head less
	 * @param appURL           the app URL
	 * @return the web driver
	 */
	public WebDriver launchChromeBrowser(boolean driverAutoConfig, boolean isHeadLess, String appURL) {

		if (driverAutoConfig)
			WebDriverManager.chromedriver().setup();
		else
			System.setProperty("webdriver.chrome.driver", FWConstants.driverPath + "\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
		options.setAcceptInsecureCerts(true);
		options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

		if (isHeadLess) {
			options.addArguments("--headless");
			options.setHeadless(true);
		}

		// Download the files for selected Folder
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", CustomCode.getDownloadFolderPath());
		options.setExperimentalOption("prefs", prefs);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(capabilities);

		@SuppressWarnings("deprecation")
		WebDriver driver = new ChromeDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(FWConstants.maxWaitTimeForElement, TimeUnit.SECONDS);
		driver.get(appURL);

		if (!isHeadLess)
			driver.manage().window().maximize();

		return driver;
	}

	/**
	 * Launch firefox browser with auto driver config.
	 *
	 * @param driverAutoConfig the driver auto config
	 * @param isHeadLess       the is head less
	 * @param appURL           the app URL
	 * @return the web driver
	 */
	public WebDriver launchFirefoxBrowserWithAutoDriverConfig(boolean driverAutoConfig, boolean isHeadLess,
			String appURL) {

		if (driverAutoConfig)
			WebDriverManager.firefoxdriver().setup();
		else
			System.setProperty("webdriver.gecko.driver", FWConstants.driverPath + "\\geckodriver.exe");

		FirefoxBinary firefoxBinary = new FirefoxBinary();
		if (isHeadLess)
			firefoxBinary.addCommandLineOptions("--headless");

		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setBinary(firefoxBinary);

		WebDriver driver = new FirefoxDriver(firefoxOptions);
		driver.manage().timeouts().implicitlyWait(FWConstants.maxWaitTimeForElement, TimeUnit.SECONDS);
		driver.get(appURL);

		if (!isHeadLess)
			driver.manage().window().maximize();

		return driver;
	}

}
