package in.ibm.chirra.saf.elementActions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import in.ibm.chirra.saf.enums.REPORT;
import in.ibm.chirra.saf.enums.SELENIUM;
import in.ibm.chirra.saf.report.HTMLReport;
import in.ibm.chirra.saf.utilities.FW_Constants;

public class ElementActions {

	public boolean dynamicWaitForElementVisible(WebDriver driver, By locater, int waitTimeInSeconds) {
		try {
			WebDriverWait waitObj = new WebDriverWait(driver, FW_Constants.maxWaitTimeForElement);
			waitObj.until(ExpectedConditions.presenceOfElementLocated(locater));
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	private boolean highlightElement(WebDriver driver, WebElement element) {
		try {
			((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'",
					element);
		} catch (Exception e) {
		}
		return true;
	}

	public void enterText(WebDriver driver, By eleementLocater, String testData, String elementName,
			SELENIUM elementWaitTime, REPORT addStepToReport) {

		if (elementWaitTime.toString().equalsIgnoreCase("ELEMENT_WAIT_TIME_ENABLED"))
			dynamicWaitForElementVisible(driver, eleementLocater, FW_Constants.maxWaitTimeForElement);

		try {
			WebElement element = driver.findElement(eleementLocater);
			highlightElement(driver, element);
			element.clear();
			element.sendKeys(testData);

			if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_TRUE"))
				HTMLReport.addTestStepToReport("PASS", "Text entered", "Text entered");
			else if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_WITHSCREENSHOT_TRUE"))
				HTMLReport.addTestStepToReportWithScreenShot(driver, "PASS", "Text entered", "Text entered");

		} catch (Exception e) {
			HTMLReport.addTestStepToReportWithScreenShot(driver, "FAIL", "Text entered", "Text entered \n"+e.getMessage());
		}
	}
	
	public void click(WebDriver driver, By eleementLocater, String elementName,
			SELENIUM elementWaitTime, REPORT addStepToReport) {

		if (elementWaitTime.toString().equalsIgnoreCase("ELEMENT_WAIT_TIME_ENABLED"))
			dynamicWaitForElementVisible(driver, eleementLocater, FW_Constants.maxWaitTimeForElement);

		try {
			WebElement element = driver.findElement(eleementLocater);
			highlightElement(driver, element);
			element.click();
			
			if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_TRUE"))
				HTMLReport.addTestStepToReport("PASS", "Element clicked", "Element clicked");
			else if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_WITHSCREENSHOT_TRUE"))
				HTMLReport.addTestStepToReportWithScreenShot(driver, "PASS", "Element clicked", "Element clicked");

		} catch (Exception e) {
			HTMLReport.addTestStepToReportWithScreenShot(driver, "FAIL", "Element clicked", "Element clicked \n"+e.getMessage());
		}
	}
	
	public void selectListBoxItemByIndex(WebDriver driver, By eleementLocater, int indexNumber, String elementName,
			SELENIUM elementWaitTime, REPORT addStepToReport) {

		if (elementWaitTime.toString().equalsIgnoreCase("ELEMENT_WAIT_TIME_ENABLED"))
			dynamicWaitForElementVisible(driver, eleementLocater, FW_Constants.maxWaitTimeForElement);

		try {
			WebElement element = driver.findElement(eleementLocater);
			highlightElement(driver, element);
			
			Select selObj = new Select(element);
			selObj.selectByIndex(indexNumber);

			if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_TRUE"))
				HTMLReport.addTestStepToReport("PASS", "Item Selected", "Item Selected");
			else if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_WITHSCREENSHOT_TRUE"))
				HTMLReport.addTestStepToReportWithScreenShot(driver, "PASS", "Item Selected", "Item Selected");

		} catch (Exception e) {
			HTMLReport.addTestStepToReportWithScreenShot(driver, "FAIL", "Item Selected", "Item Selected \n"+e.getMessage());
		}
	}
	
	public void selectListBoxItemByValue(WebDriver driver, By eleementLocater, String value, String elementName,
			SELENIUM elementWaitTime, REPORT addStepToReport) {

		if (elementWaitTime.toString().equalsIgnoreCase("ELEMENT_WAIT_TIME_ENABLED"))
			dynamicWaitForElementVisible(driver, eleementLocater, FW_Constants.maxWaitTimeForElement);

		try {
			WebElement element = driver.findElement(eleementLocater);
			highlightElement(driver, element);
			
			Select selObj = new Select(element);
			selObj.selectByValue(value);

			if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_TRUE"))
				HTMLReport.addTestStepToReport("PASS", "Item Selected", "Item Selected");
			else if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_WITHSCREENSHOT_TRUE"))
				HTMLReport.addTestStepToReportWithScreenShot(driver, "PASS", "Item Selected", "Item Selected");

		} catch (Exception e) {
			HTMLReport.addTestStepToReportWithScreenShot(driver, "FAIL", "Item Selected", "Item Selected \n"+e.getMessage());
		}
	}
	
	public void selectListBoxItemByVisibleText(WebDriver driver, By eleementLocater, String visibleText, String elementName,
			SELENIUM elementWaitTime, REPORT addStepToReport) {

		if (elementWaitTime.toString().equalsIgnoreCase("ELEMENT_WAIT_TIME_ENABLED"))
			dynamicWaitForElementVisible(driver, eleementLocater, FW_Constants.maxWaitTimeForElement);

		try {
			WebElement element = driver.findElement(eleementLocater);
			highlightElement(driver, element);
			
			Select selObj = new Select(element);
			selObj.selectByValue(visibleText);

			if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_TRUE"))
				HTMLReport.addTestStepToReport("PASS", "Item Selected", "Item Selected");
			else if (addStepToReport.toString().equalsIgnoreCase("ADD_STEP_TO_REPORT_WITHSCREENSHOT_TRUE"))
				HTMLReport.addTestStepToReportWithScreenShot(driver, "PASS", "Item Selected", "Item Selected");

		} catch (Exception e) {
			HTMLReport.addTestStepToReportWithScreenShot(driver, "FAIL", "Item Selected", "Item Selected \n"+e.getMessage());
		}
	}
	
	

}
