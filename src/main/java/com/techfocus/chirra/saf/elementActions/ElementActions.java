package com.techfocus.chirra.saf.elementActions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.techfocus.chirra.saf.FWSetup.FWConstants;
import com.techfocus.chirra.saf.FWSetup.FWGlobalReferenceClass;
import com.techfocus.chirra.saf.enums.STATUS;
import com.techfocus.chirra.saf.report.HTMLReport;
import com.techfocus.chirra.saf.report.WordGenerator;

// TODO: Auto-generated Javadoc
/**
 * The Class ElementActions.
 */
public class ElementActions {

	/** The driver. */
	WebDriver driver = null;

	/** The class test data. */
	LinkedHashMap<String, String> classTestData = null;

	/** The html report obj. */
	HTMLReport htmlReportObj = null;

	/** The report TC object. */
	ExtentTest reportTCObject = null;

	/** The word doc generate obj. */
	WordGenerator wordDocGenerateObj = null;

	/** The log 4 j obj. */
	Logger log4jObj = null;

	/**
	 * Instantiates a new element actions.
	 *
	 * @param fwClassRefObj the fw class ref obj
	 */
	public ElementActions(FWGlobalReferenceClass fwClassRefObj) {
		this.driver = fwClassRefObj.driver;
		this.classTestData = fwClassRefObj.classTestData;
		this.htmlReportObj = fwClassRefObj.htmlReportObj;
		this.reportTCObject = fwClassRefObj.reportTCObject;
		this.wordDocGenerateObj = fwClassRefObj.wordDocGenerateObj;
		this.log4jObj = fwClassRefObj.log4jObj;
	}

	/**
	 * Adds the test steps to report.
	 *
	 * @param stepStatus_PASSFAIL      the step status pass fail info
	 * @param requiredScreenshot_YESNO the required screenshot yes no
	 * @param stepName                 the step name
	 * @param stepDetails              the step details
	 * @param errorMessage             the error message
	 */
	public void addStepsToReport(STATUS stepStatus_PASSFAIL, STATUS requiredScreenshot_YESNO, String stepName,
			String stepDetails, String... errorMessage) {

		switch (requiredScreenshot_YESNO) {
		case YES:
			if ("PASS".contentEquals(stepStatus_PASSFAIL.toString())) {
				htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj,
						stepStatus_PASSFAIL.toString(), stepName, stepDetails);
				log4jObj.info(stepDetails);
			} else if ("FAIL".contentEquals(stepStatus_PASSFAIL.toString())) {
				htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj,
						stepStatus_PASSFAIL.toString(), stepName, stepDetails);
				log4jObj.error(stepDetails);
				if (errorMessage.length == 1)
					log4jObj.error(errorMessage[0]);
			} else if ("INFO".contentEquals(stepStatus_PASSFAIL.toString())) {
				htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj,
						stepStatus_PASSFAIL.toString(), stepName, stepDetails);
				log4jObj.info(stepDetails);
			}
			break;
		case NO:
			if ("PASS".contentEquals(stepStatus_PASSFAIL.toString())) {
				htmlReportObj.addTestStepToReport(reportTCObject, stepStatus_PASSFAIL.toString(), stepName,
						stepDetails);
				log4jObj.info(stepDetails);
			} else if ("FAIL".contentEquals(stepStatus_PASSFAIL.toString())) {
				htmlReportObj.addTestStepToReport(reportTCObject, stepStatus_PASSFAIL.toString(), stepName,
						stepDetails);
				log4jObj.error(stepDetails);
				if (errorMessage.length == 1)
					log4jObj.error(errorMessage[0]);
			} else if ("INFO".contentEquals(stepStatus_PASSFAIL.toString())) {
				htmlReportObj.addTestStepToReport(reportTCObject, stepStatus_PASSFAIL.toString(), stepName,
						stepDetails);
				log4jObj.info(stepDetails);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Gets the element test data.
	 *
	 * @param testDataColumnName the test data column name
	 * @return the element test data
	 */
	public String getTestDataForElement(String testDataColumnName) {
		if (classTestData.containsKey(testDataColumnName))
			return classTestData.get(testDataColumnName);
		else
			return "";
	}

	/**
	 * Sets the default element wait time.
	 *
	 * @return true, if successful
	 */
	public boolean setDefaultElementWaitTime() {
		driver.manage().timeouts().implicitlyWait(FWConstants.maxWaitTimeForElement, TimeUnit.SECONDS);
		return true;
	}

	/**
	 * Sets the custom element wait time.
	 *
	 * @param waitTimeInSeconds the wait time in seconds
	 * @return true, if successful
	 */
	public boolean setCustomWaitTimeForElement(int waitTimeInSeconds) {
		driver.manage().timeouts().implicitlyWait(waitTimeInSeconds, TimeUnit.SECONDS);
		return true;
	}

	/**
	 * Disable element wait time.
	 *
	 * @return true, if successful
	 */
	public boolean disableElementWaitTime() {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		return true;
	}

	/**
	 * Dynamic wait for element visible.
	 *
	 * @param locater           the locater
	 * @param waitTimeInSeconds the wait time in seconds
	 * @return true, if successful
	 */

	public boolean waitForElementVisible(By locater, int waitTimeInSeconds) {
		try {
			@SuppressWarnings("deprecation")
			WebDriverWait waitObj = new WebDriverWait(driver, waitTimeInSeconds);
			waitObj.until(ExpectedConditions.visibilityOfElementLocated(locater));
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Wait for element present.
	 *
	 * @param locater           the locater
	 * @param waitTimeInSeconds the wait time in seconds
	 * @return true, if successful
	 */

	public boolean waitForElementPresent(By locater, int waitTimeInSeconds) {
		try {
			@SuppressWarnings("deprecation")
			WebDriverWait waitObj = new WebDriverWait(driver, waitTimeInSeconds);
			waitObj.until(ExpectedConditions.presenceOfElementLocated(locater));
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Dynamic wait for element clickable.
	 *
	 * @param locater           the locater
	 * @param waitTimeInSeconds the wait time in seconds
	 * @return true, if successful
	 */
	public boolean waitForElementClickable(By locater, int waitTimeInSeconds) {
		try {
			@SuppressWarnings("deprecation")
			WebDriverWait waitObj = new WebDriverWait(driver, waitTimeInSeconds);
			waitObj.until(ExpectedConditions.elementToBeClickable(locater));
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Highlight element.
	 *
	 * @param element the element
	 * @return true, if successful
	 */
	private boolean highlightElement(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
		} catch (Exception e) {
		}
		return true;
	}

	/**
	 * Removes the highlight element.
	 *
	 * @param element the element
	 * @return true, if successful
	 */
	private boolean removeHighlightElement(WebElement element) {
		if (element == null) {
			System.out.println("Element is NULL");
			return false;
		}
		try {
			Thread.sleep(250);
			((JavascriptExecutor) driver).executeScript("arguments[0].style.border=''", element);
		} catch (Exception e) {
		}
		return true;
	}

	/** The wait time in seconds. */
	int waitTimeInSeconds = FWConstants.maxWaitTimeForElement;

	/** The want to add test step to report. */
	boolean wantToAddTestStepToReport = true;

	/** The action flag. */
	boolean actionFlag = false;

	/**
	 * Sets the wait time and add to report.
	 *
	 * @param waitTimeSec__addToReportYESNO the new wait time and add to report
	 */
	public void setWaitTimeAndAddToReport(String... waitTimeSec__addToReportYESNO) {

		if (waitTimeSec__addToReportYESNO.length == 2) {
			try {
				waitTimeInSeconds = Integer.parseInt(waitTimeSec__addToReportYESNO[0].trim());
			} catch (NumberFormatException e) {
			}
			try {
				wantToAddTestStepToReport = Boolean.parseBoolean(waitTimeSec__addToReportYESNO[1].trim());
			} catch (Exception e) {
			}
		} else if (waitTimeSec__addToReportYESNO.length == 1) {
			try {
				waitTimeInSeconds = Integer.parseInt(waitTimeSec__addToReportYESNO[0].trim());
			} catch (NumberFormatException e1) {
			}
		}
		// Set Wait time for element
		setCustomWaitTimeForElement(waitTimeInSeconds);
		actionFlag = false;
	}

	/**
	 * Checks if is element present.
	 *
	 * @param elementLocater                the element locater
	 * @param elementName                   the element name
	 * @param waitTimeSec__addToReportYESNO the wait time sec add to report YESNO
	 * @return true, if is element present
	 */
	public boolean verifyIsElementPresent(By elementLocater, String elementName,
			String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		String stepName = "Verify Is element " + elementName + " Present";
		String passStep = "Element " + elementName + " presented";
		String failStep = "Element " + elementName + " not presented";

		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			removeHighlightElement(element);
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
			actionFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
			actionFlag = false;
		}
		setDefaultElementWaitTime();
		return actionFlag;
	}

	/**
	 * Verify is element enabled.
	 *
	 * @param elementLocater                the element locater
	 * @param elementName                   the element name
	 * @param waitTimeSec__addToReportYESNO the wait time sec add to report YESNO
	 * @return true, if successful
	 */
	public boolean verifyIsElementEnabled(By elementLocater, String elementName,
			String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		String stepName = "Verify is Element " + elementName + " enabled";
		String passStep = "Element " + elementName + " enabled";
		String failStep = "Element " + elementName + " not enabled";
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			if (element.isEnabled() && wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
			actionFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
			actionFlag = false;
		}
		setDefaultElementWaitTime();
		return actionFlag;
	}

	/**
	 * Checks if is element displayed.
	 *
	 * @param elementLocater                the element locater
	 * @param elementName                   the element name
	 * @param waitTimeSec__addToReportYESNO the wait time sec add to report YESNO
	 * @return true, if is element displayed
	 */
	public boolean verifyIsElementDisplayed(By elementLocater, String elementName,
			String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		String stepName = "Verify is Element " + elementName + " displayed";
		String passStep = "Element " + elementName + " displayed";
		String failStep = "Element " + elementName + " not displayed";
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			if (element.isDisplayed() && wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
			actionFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
			actionFlag = false;
		}
		setDefaultElementWaitTime();
		return actionFlag;
	}

	/**
	 * Gets the text from element.
	 *
	 * @param elementLocater                the element locater
	 * @param elementName                   the element name
	 * @param waitTimeSec__addToReportYESNO the wait time sec add to report YESNO
	 * @return the text from element
	 */
	public String getTextFromElement(By elementLocater, String elementName, String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		String elementText = "";
		String stepName = "Get element " + elementName + " text";
		String failStep = "Unable to capture Element " + elementName + " text";
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			elementText = element.getText();
			String passStep = "Element " + elementName + " having text " + elementText;
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
		}
		setDefaultElementWaitTime();
		return elementText;
	}

	/**
	 * Gets the text from input element.
	 *
	 * @param elementLocater                the element locater
	 * @param elementName                   the element name
	 * @param waitTimeSec__addToReportYESNO the wait time sec add to report YESNO
	 * @return the text from input element
	 */
	public String getTextFromInputElement(By elementLocater, String elementName,
			String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		String elementText = "";
		String stepName = "Get element " + elementName + " text";
		String failStep = "Unable to capture Element " + elementName + " text";
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			elementText = element.getAttribute("value");
			String passStep = "Element " + elementName + " having text " + elementText;
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
		}
		setDefaultElementWaitTime();
		return elementText;
	}

	/**
	 * Enter text.
	 *
	 * @param elementLocater                the element locater
	 * @param testDataColumnName            the test data column name
	 * @param elementName                   the element name
	 * @param waitTimeSec__addToReportYESNO the wait time sec add to report YESNO
	 * @return true, if successful
	 */
	public boolean enterText(By elementLocater, String testDataColumnName, String elementName,
			String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		String testData = getTestDataForElement(testDataColumnName);
		String stepName = "Enter text " + testData + " into element " + elementName;
		String passStep = "Text " + testData + " entered into " + elementName;
		String failStep = "Unable to enter Text " + testData + " entered into " + elementName;
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			element.clear();
			element.sendKeys(testData);
			removeHighlightElement(element);

			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
			actionFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
			actionFlag = false;
		}
		setDefaultElementWaitTime();
		return actionFlag;
	}

	/**
	 * Enter text using java script.
	 *
	 * @param elementLocater                the element locater
	 * @param testDataColumnName            the test data column name
	 * @param elementName                   the element name
	 * @param waitTimeSec__addToReportYESNO the wait time sec add to report YESNO
	 * @return true, if successful
	 */
	public boolean enterTextUsingJavaScript(By elementLocater, String testDataColumnName, String elementName,
			String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		String testData = getTestDataForElement(testDataColumnName);
		String stepName = "Enter value " + testData + " into element " + elementName;
		String passStep = "Value " + testData + " entered into " + elementName;
		String failStep = "Unable to enter value " + testData + " entered into " + elementName;
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			element.clear();
			JavascriptExecutor myExecutor = (JavascriptExecutor) driver;
			myExecutor.executeScript("arguments[0].value='" + testData + "';", element);
			removeHighlightElement(element);

			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
			actionFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
			actionFlag = false;
		}
		setDefaultElementWaitTime();
		return actionFlag;
	}

	/**
	 * Enter text using actions.
	 *
	 * @param elementLocater                the element locater
	 * @param testDataColumnName            the test data column name
	 * @param elementName                   the element name
	 * @param waitTimeSec__addToReportYESNO the wait time sec add to report YESNO
	 * @return true, if successful
	 */
	public boolean enterTextUsingActions(By elementLocater, String testDataColumnName, String elementName,
			String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		String testData = getTestDataForElement(testDataColumnName);
		String stepName = "Enter text " + testData + " into element " + elementName;
		String passStep = "Text " + testData + " entered into " + elementName;
		String failStep = "Unable to enter Text " + testData + " entered into " + elementName;
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			element.clear();
			Actions actObj = new Actions(driver);
			actObj.moveToElement(element).click().sendKeys(testData).build().perform();
			removeHighlightElement(element);

			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
			actionFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
			actionFlag = false;
		}
		setDefaultElementWaitTime();
		return actionFlag;
	}

	/**
	 * Click element.
	 *
	 * @param elementLocater                the element locater
	 * @param elementName                   the element name
	 * @param waitTimeSec__addToReportYESNO the wait time sec add to report YESNO
	 * @return true, if successful
	 */
	public boolean clickElement(By elementLocater, String elementName, String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		String stepName = "Click on " + elementName + " element";
		String passStep = "Element " + elementName + " clicked";
		String failStep = "Unable to click on " + elementName + " element";
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			element.click();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
			actionFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
			actionFlag = false;
		}
		setDefaultElementWaitTime();
		return actionFlag;
	}

	/**
	 * Click element using java script.
	 *
	 * @param elementLocater                the element locater
	 * @param elementName                   the element name
	 * @param waitTimeSec__addToReportYESNO the wait time sec add to report YESNO
	 * @return true, if successful
	 */
	public boolean clickElementUsingJavaScript(By elementLocater, String elementName,
			String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		String stepName = "Click on " + elementName + " element";
		String passStep = "Element " + elementName + " clicked";
		String failStep = "Unable to click on " + elementName + " element";
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			removeHighlightElement(element);
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
			actionFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
			actionFlag = false;
		}
		setDefaultElementWaitTime();
		return actionFlag;
	}

	/**
	 * Click element using actions.
	 *
	 * @param elementLocater                the element locater
	 * @param elementName                   the element name
	 * @param waitTimeSec__addToReportYESNO the wait time sec add to report YESNO
	 * @return true, if successful
	 */
	public boolean clickElementUsingActions(By elementLocater, String elementName,
			String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		String stepName = "Click on " + elementName + " element";
		String passStep = "Element " + elementName + " clicked";
		String failStep = "Unable to click on " + elementName + " element";
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			Actions actObj = new Actions(driver);
			actObj.moveToElement(element).click().build().perform();
			removeHighlightElement(element);
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
			actionFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
			actionFlag = false;
		}
		setDefaultElementWaitTime();
		return actionFlag;
	}

	/**
	 * Click element using actions with coordinates.
	 *
	 * @param elementLocater                the element locater
	 * @param x_position                    the x position
	 * @param y_position                    the y position
	 * @param elementName                   the element name
	 * @param waitTimeSec__addToReportYESNO the wait time sec add to report YESNO
	 * @return true, if successful
	 */
	public boolean clickElementUsingActionsWithCoordinates(By elementLocater, int x_position, int y_position,
			String elementName, String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		String stepName = "Click on " + elementName + " element";
		String passStep = "Element " + elementName + " clicked";
		String failStep = "Unable to click on " + elementName + " element";
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			Actions actObj = new Actions(driver);
			actObj.moveToElement(element, x_position, y_position).click().build().perform();
			removeHighlightElement(element);
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
			actionFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
			actionFlag = false;
		}
		setDefaultElementWaitTime();
		return actionFlag;
	}

	/**
	 * Select list box option by index.
	 *
	 * @param elementLocater     the element locater
	 * @param testDataColumnName the test data column name
	 * @param elementName        the element name
	 */
	public boolean selectListBoxOptionByIndex(By elementLocater, String testDataColumnName, String elementName,
			String... waitTimeSec__addToReportYESNO) {

		// Set Wait Time and Step add to report status
		setWaitTimeAndAddToReport(waitTimeSec__addToReportYESNO);

		int indexNumber = 0;
		try {
			indexNumber = Integer.parseInt(getTestDataForElement(testDataColumnName));
		} catch (NumberFormatException e1) {
		}

		String stepName = "Select the Item (" + indexNumber + ") from list box " + elementName;
		String passStep = "Item (" + indexNumber + ") selected from list box " + elementName;
		String failStep = "Unable to select the Item (" + indexNumber + ") from list box " + elementName;
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			Select selObj = new Select(element);
			selObj.selectByIndex(indexNumber);
			removeHighlightElement(element);
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.PASS, STATUS.YES, stepName, passStep);
			actionFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (wantToAddTestStepToReport)
				addStepsToReport(STATUS.FAIL, STATUS.YES, stepName, failStep, e.getMessage());
			actionFlag = false;
		}
		setDefaultElementWaitTime();
		return actionFlag;
	}

	/**
	 * Select list box item by value.
	 *
	 * @param elementLocater     the element locater
	 * @param testDataColumnName the test data column name
	 * @param elementName        the element name
	 */
	public void selectListBoxItemByValue(By elementLocater, String testDataColumnName, String elementName) {

		String value = getTestDataForElement(testDataColumnName);
		waitForElementVisible(elementLocater, FWConstants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);

			Select selObj = new Select(element);
			selObj.selectByValue(value);

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					"Select the value (" + value + ") from list box " + elementName,
					"value (" + value + ") selected from list box " + elementName);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					"Select the value (" + value + ") from list box " + elementName,
					"Unable to select the value (" + value + ") from list box " + elementName);
			e.printStackTrace();
		}
	}

	/**
	 * Select list box item by visible text.
	 *
	 * @param elementLocater     the element locater
	 * @param testDataColumnName the test data column name
	 * @param elementName        the element name
	 */
	public void selectListBoxItemByVisibleText(By elementLocater, String testDataColumnName, String elementName) {

		String visibleText = getTestDataForElement(testDataColumnName);
		waitForElementVisible(elementLocater, FWConstants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);

			Select selObj = new Select(element);
			selObj.selectByValue(visibleText);

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					"Select the option (" + visibleText + ") from list box " + elementName,
					"Item (" + visibleText + ") selected from list box " + elementName);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					"Select the option (" + visibleText + ") from list box " + elementName,
					"Unable to select the option (" + visibleText + ") from list box " + elementName);
			e.printStackTrace();
		}
	}

	/**
	 * Gets the first selected item text from list box.
	 *
	 * @param elementLocater the element locater
	 * @param elementName    the element name
	 * @return the first selected item text from list box
	 */
	public String getFirstSelectedItemTextFromListBox(By elementLocater, String elementName) {

		String optionText = "";
		String stepName = "Get Listbox " + elementName + " selected Item text";
		waitForElementVisible(elementLocater, FWConstants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);

			Select selObj = new Select(element);
			optionText = selObj.getFirstSelectedOption().getText();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					stepName, optionText + " Item selected from list box " + elementName);
			return optionText;
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					stepName, "Unable to capture the selected Item text from list box " + elementName);
			e.printStackTrace();
			log4jObj.error("Unable to capture the selected Item text from list box " + elementName);
			log4jObj.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Gets the all selected item text from list box.
	 *
	 * @param elementLocater the element locater
	 * @param elementName    the element name
	 * @return the all selected item text from list box
	 */
	public ArrayList<String> getAllSelectedItemTextFromListBox(By elementLocater, String elementName) {

		List<WebElement> optionText = null;
		ArrayList<String> allOptionsText = new ArrayList<String>();
		String stepName = "Get all selected Item text from Listbox " + elementName;
		waitForElementVisible(elementLocater, FWConstants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);

			Select selObj = new Select(element);
			optionText = selObj.getAllSelectedOptions();
			for (WebElement option : optionText) {
				try {
					allOptionsText.add(option.getText());
				} catch (Exception e) {
				}
			}
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					stepName, "All selected Item text of list box " + elementName + " is " + optionText);
			return allOptionsText;
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					stepName, "Unable to capture selected Item text from list box " + elementName);
			e.printStackTrace();
			log4jObj.error("Unable to capture selected Item text from list box " + elementName);
			log4jObj.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Gets the all items text from list box.
	 *
	 * @param elementLocater the element locater
	 * @param elementName    the element name
	 * @return the all items text from list box
	 */
	public ArrayList<String> getAllItemsTextFromListBox(By elementLocater, String elementName) {

		List<WebElement> optionText = null;
		ArrayList<String> allOptionsText = new ArrayList<String>();
		String stepName = "Get all Items text from Listbox " + elementName;
		waitForElementVisible(elementLocater, FWConstants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);

			Select selObj = new Select(element);
			optionText = selObj.getOptions();
			for (WebElement option : optionText) {
				try {
					allOptionsText.add(option.getText());
				} catch (Exception e) {
				}
			}
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					stepName, "All Items text of list box " + elementName + " is " + optionText);
			return allOptionsText;
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					stepName, "Unable to capture Items text from list box " + elementName);
			e.printStackTrace();
			log4jObj.error("Unable to capture Items text from list box " + elementName);
			log4jObj.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Switch to frame by frame index.
	 *
	 * @param testDataColumnName the test data column name
	 */
	public void switchToFrameByFrameIndex(String testDataColumnName) {

		int frameIndexNumber = 0;
		try {
			frameIndexNumber = Integer.parseInt(getTestDataForElement(testDataColumnName));
		} catch (NumberFormatException e1) {
		}
		try {
			driver.switchTo().frame(frameIndexNumber);
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					"Switch to frame by index number " + frameIndexNumber,
					"Swiched to frame  using index " + frameIndexNumber + " successfully");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					"Switch to frame by index number " + frameIndexNumber,
					"Unable Swich to frame  using index " + frameIndexNumber);
			e.printStackTrace();
		}
	}

	/**
	 * Switch to frame by frame name.
	 *
	 * @param testDataColumnName the test data column name
	 */
	public void switchToFrameByFrameName(String testDataColumnName) {
		String frameName = getTestDataForElement(testDataColumnName);
		try {
			driver.switchTo().frame(frameName);
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					"Switch to frame by frame name " + frameName,
					"Swiched to frame using frame name " + frameName + " successfully");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					"Switch to frame by frame name " + frameName,
					"Unable Swich to frame  using frame name " + frameName);
			e.printStackTrace();
		}
	}

	/**
	 * Switch to frame by frame element.
	 *
	 * @param elementLocater the element locater
	 */
	public void switchToFrameByFrameElement(By elementLocater) {

		waitForElementVisible(elementLocater, FWConstants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			driver.switchTo().frame(element);
			element.click();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					"Switch to frame using element", "Swiched to frame  using element successfully");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					"Switch to frame using element", "Unable to Swich to frame using element");
			e.printStackTrace();
		}
	}

	/**
	 * Accept alert.
	 *
	 * @param elementLocater the element locater
	 * @param elementName    the element name
	 */
	public void acceptAlert(By elementLocater, String elementName) {

		waitForElementVisible(elementLocater, FWConstants.maxWaitTimeForElement);
		try {

			Alert alertObj = driver.switchTo().alert();
			alertObj.accept();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					"Accept alert", "Alert accpeted");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					"Accept alert", "Unable to acept Alert");
			e.printStackTrace();
		}
	}

	/**
	 * Dismiss alert.
	 *
	 * @param elementLocater the element locater
	 * @param elementName    the element name
	 */
	public void dismissAlert(By elementLocater, String elementName) {

		waitForElementVisible(elementLocater, FWConstants.maxWaitTimeForElement);
		try {

			Alert alertObj = driver.switchTo().alert();
			alertObj.dismiss();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					"Dismiss alert", "Dismiss accpeted");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					"Dismiss alert", "Unable to Dismiss Alert");
			e.printStackTrace();
		}
	}

	/**
	 * Gets the alert message.
	 *
	 * @param elementLocater the element locater
	 * @param elementName    the element name
	 * @return the alert message
	 */
	public String getAlertMessage(By elementLocater, String elementName) {

		String alertMessage = "";
		waitForElementVisible(elementLocater, FWConstants.maxWaitTimeForElement);
		try {

			Alert alertObj = driver.switchTo().alert();
			alertMessage = alertObj.getText();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					"Capture alert message", "Alert having message " + alertMessage);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					"Capture alert message", "Unable to capture Alert message");
			e.printStackTrace();
		}
		return alertMessage;
	}

	/**
	 * Switch to window by frame index.
	 *
	 * @param testDataColumnName the test data column name
	 */
	public void switchToWindowByFrameIndex(String testDataColumnName) {
		int windowIndexNumber = 0;
		try {
			windowIndexNumber = Integer.parseInt(getTestDataForElement(testDataColumnName));
		} catch (NumberFormatException e1) {
		}
		try {
			String windowId = driver.getWindowHandles().toArray()[windowIndexNumber].toString();
			driver.switchTo().window(windowId);
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					"Switch to window by index number " + windowIndexNumber,
					"Swiched to window  using index " + windowIndexNumber + " successfully");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					"Switch to window by index number " + windowIndexNumber,
					"Unable Swich to window  using index " + windowIndexNumber);
			e.printStackTrace();
		}
	}

	/**
	 * Switch to latest window.
	 */
	public void switchToLatestWindow() {

		try {
			int count = driver.getWindowHandles().toArray().length;
			String windowId = driver.getWindowHandles().toArray()[count - 1].toString();
			driver.switchTo().window(windowId);
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					"Switch to latest window", "Swiched to latest window successfully");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					"Switch to latest window", "Unable to Swiched to latest window");
			e.printStackTrace();
		}
	}

	/**
	 * Switch to main window.
	 */
	public void switchToMainWindow() {

		try {
			driver.switchTo().defaultContent();
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					"Switch to main window", "Swiched to main window successfully");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					"Switch to main window", "Unable to Swiched to main window");
			e.printStackTrace();
		}
	}

	/**
	 * Move cursor using actions.
	 *
	 * @param x_position the x position
	 * @param y_position the y position
	 */
	public void moveCursorUsingActions(int x_position, int y_position) {

		try {
			Actions actObj = new Actions(driver);
			actObj.moveByOffset(x_position, y_position).build().perform();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					"Move cursor to X - position " + x_position + " Y - position " + y_position,
					"Cursor moved to X - position " + x_position + " Y - position " + y_position);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					"Move cursor to X - position " + x_position + " Y - position " + y_position,
					"Unable to move to Cursor to X - position " + x_position + " Y - position " + y_position);
			e.printStackTrace();
		}
	}

	/**
	 * Scroll down.
	 *
	 * @param noOfTimes the no of times
	 */
	public void scrollDown(int noOfTimes) {

		Actions actObj = new Actions(driver);
		for (int i = 0; i < noOfTimes; i++) {
			actObj.sendKeys(Keys.PAGE_DOWN).build().perform();
			waitSomeTime(2);
		}
	}

	/**
	 * Scroll up.
	 *
	 * @param noOfTimes the no of times
	 */
	public void scrollUp(int noOfTimes) {

		Actions actObj = new Actions(driver);
		for (int i = 0; i < noOfTimes; i++) {
			actObj.sendKeys(Keys.PAGE_UP).build().perform();
			waitSomeTime(2);
		}
	}

	/**
	 * Press enter.
	 *
	 * @param noOfTimes the no of times
	 */
	public void pressEnter(int noOfTimes) {

		Actions actObj = new Actions(driver);
		for (int i = 0; i < noOfTimes; i++) {
			actObj.sendKeys(Keys.ENTER).build().perform();
			waitSomeTime(2);
		}
	}

	/**
	 * Press tab.
	 *
	 * @param noOfTimes the no of times
	 */
	public void pressTab(int noOfTimes) {

		Actions actObj = new Actions(driver);
		for (int i = 0; i < noOfTimes; i++) {
			actObj.sendKeys(Keys.TAB).build().perform();
			waitSomeTime(2);
		}
	}

	/**
	 * Wait some time.
	 *
	 * @param timeInSeconds the time in seconds
	 */
	public void waitSomeTime(int timeInSeconds) {
		Actions actObj = new Actions(driver);
		actObj.pause(timeInSeconds * 1000);
	}

	/**
	 * Removes the readonly attribute.
	 *
	 * @param elementLocater the element locater
	 * @param elementName    the element name
	 * @return true, if successful
	 */
	public boolean removeReadOnlyAttribute(By elementLocater, String elementName) {

		waitForElementVisible(elementLocater, FWConstants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly','readonly')", element);
			log4jObj.info("Readonly attribute removed for element " + elementName);
			return true;
		} catch (Exception e) {
			log4jObj.error("Unable to remove Readonly attribute for element " + elementName);
			e.printStackTrace();
			log4jObj.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Gets the attribute value.
	 *
	 * @param elementLocater the element locater
	 * @param elementName    the element name
	 * @param attributeName  the attribute name
	 * @return the attribute value
	 */
	public boolean getAttributeValue(By elementLocater, String elementName, String attributeName) {

		String attributeValue = "";
		String stepName = "Get attribute " + attributeName + " value of element" + elementName;
		waitForElementVisible(elementLocater, FWConstants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);

			attributeValue = element.getAttribute(attributeName);

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "PASS",
					stepName,
					"Element " + elementName + " attribute " + attributeName + " having value " + attributeValue);
			log4jObj.info("Element " + elementName + " attribute " + attributeName + " having value " + attributeValue);
			return true;
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, wordDocGenerateObj, "FAIL",
					stepName, "Unable to get attribute " + attributeName + " value of element" + elementName);
			e.printStackTrace();
			log4jObj.error("Unable to get attribute " + attributeName + " value of element" + elementName);
			log4jObj.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Gets the table data.
	 *
	 * @param elementLocater the element locater
	 * @param elementName    the element name
	 * @return the table data
	 */
	public LinkedHashMap<Integer, LinkedHashMap<Integer, String>> getTableData(By elementLocater, String elementName) {

		LinkedHashMap<Integer, LinkedHashMap<Integer, String>> tableData_LHM = new LinkedHashMap<Integer, LinkedHashMap<Integer, String>>();
		WebElement table = null;
		try {
			table = driver.findElement(elementLocater);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}

		List<WebElement> allRows = table.findElements(By.tagName("tr"));
		int tableRowNumber = 0;
		for (WebElement row : allRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			int tableColumnNumber = 0;
			LinkedHashMap<Integer, String> columnData_LHM = new LinkedHashMap<Integer, String>();
			for (WebElement cell : cells) {
				String value = "";
				try {
					value = cell.getText();
				} catch (Exception e) {
				}
				columnData_LHM.put(tableColumnNumber, value);
				tableColumnNumber += 1;
			}
			tableRowNumber += 1;
			tableData_LHM.put(tableRowNumber, columnData_LHM);
		}
		return tableData_LHM;
	}

	/**
	 * Open and switch to new tab.
	 *
	 * @param testDataColumnName the test data column name
	 */
	public void openAndSwitchToNewTab(String testDataColumnName) {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.navigate().to("http://google.com");
	}

	/**
	 * Browser forward.
	 */
	public void browserForward() {
		driver.navigate().forward();
	}

	/**
	 * Browser back.
	 */
	public void browserBack() {
		driver.navigate().back();
	}

	/**
	 * Browser refresh.
	 */
	public void browserRefresh() {
		driver.navigate().refresh();
	}

	/**
	 * Close current browser.
	 */
	public void closeCurrentBrowser() {
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	/**
	 * Close all browsers.
	 *
	 * @param driver the driver
	 */
	public void closeAllBrowsers(WebDriver driver) {
		try {
			driver.quit();
		} catch (Exception e) {
		}
	}

}
