package in.ibm.chirra.saf.driverSetup;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.SimpleLayout;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import in.ibm.chirra.saf.FWSetup.FW_Constants;
import in.ibm.chirra.saf.FWSetup.FW_DataLoad;
import in.ibm.chirra.saf.report.HTMLReport;
import in.ibm.chirra.saf.utilities.CustomCode;
import in.ibm.chirra.saf.utilities.DateTimeWork;
import in.ibm.chirra.saf.utilities.FileDirectoryWork;
import in.ibm.chirra.saf.utilities.LogFile;

public class DriverSetup {
	
	public static String htmlReport_resultsFolderPath = "";
	@BeforeSuite
	public void beforeSuiteTestAnnExecuted() {
		
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		htmlReport_resultsFolderPath = "src/main/resources/Results/";
		//Create Results folder if not exists
		if(!FileDirectoryWork.isFileOrDirectoryExists(htmlReport_resultsFolderPath)) {
			FileDirectoryWork.createDirectory(htmlReport_resultsFolderPath);
		}
		
		//Add current execution Results folder
		String newFolderName = ""+DateTimeWork.getCurrentDateTime();
		htmlReport_resultsFolderPath = htmlReport_resultsFolderPath+newFolderName+"/";
		
		FileDirectoryWork.createDirectory(htmlReport_resultsFolderPath);
		//FileDirectoryWork.createFile("CSR_SAF_RESULTS.html");
				
		HTMLReport.createExtentReport();
	}

	FW_Constants fwConsObj = null;
	public LinkedHashMap<String, String> classTestData_LHM = null;
	public HTMLReport htmlReportObj = null;
	public String logFilePath = null;
	public Logger log4jObj = null;
	@Parameters({ "CLASSNAME", "TC_ID", "BROWSER" })
	@BeforeClass
	public void beforeClassTestAnnLoad(String CLASSNAME, String TC_ID, String BROWSER) throws IOException {
		
		String driverType = FW_Constants.driverType;
		System.out.println("CLASSNAME : " + CLASSNAME);
		System.out.println("TC_ID : " + TC_ID);
		System.out.println("BROWSER : " + BROWSER);
		
		//Load class Test Data
		fwConsObj = new FW_Constants();
		classTestData_LHM = fwConsObj.loadTestDataForClass(CLASSNAME+"###"+TC_ID+"###"+BROWSER);
		htmlReportObj = new HTMLReport();
		
		logFilePath = htmlReport_resultsFolderPath+"/"+TC_ID+".log";
		LogFile logFileObj = new LogFile();
		logFileObj.createFile(logFilePath);
		
		log4jObj = Logger.getLogger(TC_ID);
		Layout layoutObj = new PatternLayout("%d %m %n");
		Appender appenderObj = new FileAppender(layoutObj, logFilePath);
		log4jObj.addAppender(appenderObj);
		
		loadDriver(driverType, BROWSER, TC_ID, htmlReportObj);
	}
	
	@AfterClass
	public void afterClassTestAnnLoad() {
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	public WebDriver driver = null;
	public FW_DataLoad fwDataLoadObj = new FW_DataLoad();
	public void loadDriver(String driverType, String browser, String TC_ID, HTMLReport htmlReportObj) {
		if (driverType.equalsIgnoreCase("Web Driver")) {
			if (browser.equalsIgnoreCase("Chrome")) {
				String appURL = classTestData_LHM.get("URL");
				System.out.println("appURL : "+appURL);
				htmlReportObj.addTestCaseToReport(TC_ID, TC_ID);
				launChromeBrowser(appURL);
				htmlReportObj.addTestStepToReportWithScreenShot(driver, "PASS", TC_ID, TC_ID);
			}
		}
	}

	private void launChromeBrowser(String appURL) {
		System.setProperty("webdriver.chrome.driver", CustomCode.getTempFolderPath()+"/Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(appURL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

}
