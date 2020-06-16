package in.ibm.chirra.saf.report;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import in.ibm.chirra.saf.driverSetup.DriverSetup;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class HTMLReport {

    String projectFolderPath = System.getProperty("user.dir");
    static ExtentReports extent = null;
    ExtentTest test = null;

    public String takeScreenshot(WebDriver driver) {
        try {
            String format = "png";
            String imageFilePath = DriverSetup.htmlReport_resultsFolderPath + "/Screenshots/Image_" + System.currentTimeMillis() + "." + format;
            TakesScreenshot scrShot =((TakesScreenshot)driver);
            File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile=new File(imageFilePath);
            FileUtils.copyFile(SrcFile, DestFile);
            return imageFilePath.replace(DriverSetup.htmlReport_resultsFolderPath+"/", "");
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return "";
    }
    
    public static void createExtentReport() {
    	if (extent == null) {
            extent = new ExtentReports(DriverSetup.htmlReport_resultsFolderPath+"/CSR_SAF_RESULTS.html", true);
        }
    }
    
    public void addTestCaseToReport(String testCaseName, String testCaseDescription) {
        test = extent.startTest(testCaseName, testCaseDescription);
    }
    public void addTestStepToReport(String status, String stepName, String description) {
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
            saveReport();
        } catch (Exception e) {
        }
    }
    public void addTestStepToReportWithScreenShot(WebDriver driver, String status, String stepName, String description) {
        try {
            if (status.equalsIgnoreCase("PASS")) {
                test.log(LogStatus.PASS, stepName, description + " : " + test.addScreenCapture(takeScreenshot(driver)));
            } else if (status.equalsIgnoreCase("FAIL")) {
                test.log(LogStatus.FAIL, stepName, description + " : " + test.addScreenCapture(takeScreenshot(driver)));
            } else if (status.equalsIgnoreCase("INFO")) {
                test.log(LogStatus.INFO, stepName, description + " : " + test.addScreenCapture(takeScreenshot(driver)));
            }
        } catch (Exception e) {
        }
        try {
            saveReport();
        } catch (Exception e) {
        }
    }
    public void saveReport() {
        try {
            extent.endTest(test);
            extent.flush();
        } catch (Exception e) {
        }
    }
}