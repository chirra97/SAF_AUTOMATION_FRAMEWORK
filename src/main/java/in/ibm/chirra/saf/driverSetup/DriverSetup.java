package in.ibm.chirra.saf.driverSetup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import in.ibm.chirra.saf.report.HTMLReport;
import in.ibm.chirra.saf.utilities.CustomCode;
import in.ibm.chirra.saf.utilities.DateTimeWork;
import in.ibm.chirra.saf.utilities.FW_Constants;
import in.ibm.chirra.saf.utilities.FileDirectoryWork;

public class DriverSetup {
	public static String htmlReport_resultsFolderPath = "";
	@BeforeSuite
	public static void beforeSuiteTestAnnExecuted() {
		
		System.out.println("BeforeSuite");
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		htmlReport_resultsFolderPath = "src/main/Results/";
		//Create Results folder if not exists
		if(!FileDirectoryWork.isFileOrDirectoryExists(htmlReport_resultsFolderPath)) {
			FileDirectoryWork.createDirectory(htmlReport_resultsFolderPath);
		}
		
		//Add current execution Results folder
		String newFolderName = ""+DateTimeWork.getCurrentDateTime();
		htmlReport_resultsFolderPath = htmlReport_resultsFolderPath+newFolderName+"/";
		
		FileDirectoryWork.createDirectory(htmlReport_resultsFolderPath);
		FileDirectoryWork.createFile("CSR_SAF_RESULTS.html");
	}

	@Parameters({ "CLASSNAME", "TC_ID", "BROWSER" })
	@BeforeClass
	public void beforeClassTestAnnLoad(String CLASSNAME, String TC_ID, String BROWSER) {
		System.out.println("beforeTestAnnLoad");

		String driverType = FW_Constants.runDriverType;
		
		System.out.println("CLASSNAME : " + CLASSNAME);
		System.out.println("TC_ID : " + TC_ID);
		System.out.println("BROWSER : " + BROWSER);
		
		//Load class Test Data
		FW_Constants.loadTestDataForClass(CLASSNAME+"###"+TC_ID+"###"+BROWSER);
		
		System.out.println("driverType : "+driverType);
		loadDriver(driverType, BROWSER, TC_ID);
	}
	
	@AfterClass
	public void afterClassTestAnnLoad() {
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	public static WebDriver driver = null;
	public void loadDriver(String driverType, String browser, String TC_ID) {
		if (driverType.equalsIgnoreCase("Web Driver")) {
			if (browser.equalsIgnoreCase("Chrome")) {
				String appURL = FW_Constants.getTestData("URL");
				System.out.println("appURL : "+appURL);
				HTMLReport.addTestCaseToReport(TC_ID, TC_ID);
				launChromeBrowser(appURL);
				HTMLReport.addTestStepToReportWithScreenShot(driver, "PASS",TC_ID, TC_ID);
			}
		}
	}

	private static void launChromeBrowser(String appURL) {
		System.setProperty("webdriver.chrome.driver", CustomCode.getTempFolderPath()+"/Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(appURL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

}
