package in.ibm.chirra.saf.actions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import in.ibm.chirra.saf.enums.REPORT;
import in.ibm.chirra.saf.enums.SELENIUM;
import in.ibm.chirra.saf.report.HTMLReport;
import in.ibm.chirra.saf.utilities.FW_Constants;

public class ElementActions {

	public static boolean dynamicWaitForElementVisible(WebDriver driver, By locater) {
		try {
			WebDriverWait waitObj = new WebDriverWait(driver, FW_Constants.maxWaitTimeForElement);
			waitObj.until(ExpectedConditions.presenceOfElementLocated(locater));
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static void enterText(WebDriver driver, By eleementLocater, String testData, String elementName,
			SELENIUM elementWaitTime, REPORT addStepToReport) {

		if (elementWaitTime.toString().equalsIgnoreCase("ELEMENT_WAIT_TIME_ENABLED"))
			dynamicWaitForElementVisible(driver, eleementLocater);

		try {
			driver.findElement(eleementLocater).clear();
			driver.findElement(eleementLocater).sendKeys(testData);

			if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_TRUE"))
				HTMLReport.addTestStepToReport("PASS", "Text entered", "Text entered");
			else if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_WITHSCREENSHOT_TRUE"))
				HTMLReport.addTestStepToReportWithScreenShot(driver, "PASS", "Text entered", "Text entered");

		} catch (Exception e) {
			if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_TRUE"))
				HTMLReport.addTestStepToReport("FAIL", "Text entered", "Text entered");
			else if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_WITHSCREENSHOT_TRUE"))
				HTMLReport.addTestStepToReportWithScreenShot(driver, "FAIL", "Text entered", "Text entered");
		}
	}

}
