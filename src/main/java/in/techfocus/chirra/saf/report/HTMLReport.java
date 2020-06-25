package in.techfocus.chirra.saf.report;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import in.techfocus.chirra.saf.FWSetup.FWConstants;
import in.techfocus.chirra.saf.utilities.DateTimeWork;

public class HTMLReport {

	static ExtentReports extent = null;
	public String takeScreenshot(WebDriver driver) {
		try {
			String format = "png";
			String imageFilePath = FWConstants.htmlReport_resultsFolderPath + "/Screenshots/Image_" + System.currentTimeMillis()
					+ "." + format;
			TakesScreenshot scrShot = ((TakesScreenshot) driver);
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile = new File(imageFilePath);
			FileUtils.copyFile(SrcFile, DestFile);
			return imageFilePath.replace(FWConstants.htmlReport_resultsFolderPath + "/", "");
		} catch (Exception ex) {
			System.err.println(ex);
		}
		return "";
	}

	public static void createExtentReport(String htmlReportResultsFolderPath) {
		
		if (extent == null) {
			extent = new ExtentReports(htmlReportResultsFolderPath + "/CSR_SAF_RESULTS" + "_"
					+ DateTimeWork.getCurrentDateTime() + ".html", true);
		}
	}

	public ExtentTest addTestCaseToReport(String testCaseName, String testCaseDescription) {
		ExtentTest test = extent.startTest(testCaseName, testCaseDescription);
		return test;
	}

	public void addTestStepToReport(ExtentTest test, String status, String stepName, String description) {
		try {
			if (status.equalsIgnoreCase("PASS")) {
				test.log(LogStatus.PASS, stepName, description);
			} else if (status.equalsIgnoreCase("FAIL")) {
				test.log(LogStatus.FAIL, stepName, description);
			} else if (status.equalsIgnoreCase("INFO")) {
				test.log(LogStatus.INFO, stepName, description);
			}
		} catch (Exception e) {
		}
		try {
			saveReport(test);
		} catch (Exception e) {
		}
	}

	public void addTestStepToReportWithScreenShot(WebDriver driver, ExtentTest test, WordGenerator wordDocGenerateObj, String status, String stepName,
			String description) {
		try {
			String imagePath = takeScreenshot(driver);
			if (status.equalsIgnoreCase("PASS")) {
				test.log(LogStatus.PASS, stepName, description + " : " + test.addScreenCapture(imagePath));
				wordDocGenerateObj.addPassStepToDocumentWithImage(FWConstants.htmlReport_resultsFolderPath+"/"+imagePath, description);
			} else if (status.equalsIgnoreCase("FAIL")) {
				test.log(LogStatus.FAIL, stepName, description + " : " + test.addScreenCapture(imagePath));
				wordDocGenerateObj.addFailStepToDocumentWithImage(FWConstants.htmlReport_resultsFolderPath+"/"+imagePath, description);
			} else if (status.equalsIgnoreCase("INFO")) {
				test.log(LogStatus.INFO, stepName, description + " : " + test.addScreenCapture(imagePath));
				wordDocGenerateObj.addPassStepToDocumentWithImage(FWConstants.htmlReport_resultsFolderPath+"/"+imagePath, description);
			}
		} catch (Exception e) {
		}
		try {
			saveReport(test);
		} catch (Exception e) {
		}
	}

	public void saveReport(ExtentTest test) {
		try {
			extent.endTest(test);
			extent.flush();
		} catch (Exception e) {
		}
	}
}