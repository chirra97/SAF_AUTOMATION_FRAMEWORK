package in.ibm.chirra.saf.elementActions;

import java.util.LinkedHashMap;

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

import in.ibm.chirra.saf.FWSetup.FW_ClassRefObjetcs;
import in.ibm.chirra.saf.FWSetup.FW_Constants;
import in.ibm.chirra.saf.report.HTMLReport;

public class ElementActions {
	

	WebDriver driver = null;
	LinkedHashMap<String, String> classTestData = null;
	HTMLReport htmlReportObj = null;
	ExtentTest reportTCObject = null;
	Logger log4jObj = null;
	
	public ElementActions(FW_ClassRefObjetcs fwClassRefObj) {
		this.driver = fwClassRefObj.driver;
		this.classTestData = fwClassRefObj.classTestData;
		this.htmlReportObj = fwClassRefObj.htmlReportObj;
		this.reportTCObject = fwClassRefObj.reportTCObject;
		this.log4jObj = fwClassRefObj.log4jObj;
	}

	public String getElementTestData(String testDataColumnName) {
		if(classTestData.containsKey(testDataColumnName))
			return classTestData.get(testDataColumnName);
		else 
			return "";
	}
	public boolean dynamicWaitForElementVisible(By locater, int waitTimeInSeconds) {
		try {
			WebDriverWait waitObj = new WebDriverWait(driver, waitTimeInSeconds);
			waitObj.until(ExpectedConditions.presenceOfElementLocated(locater));
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public boolean dynamicWaitForElementClickable(By locater, int waitTimeInSeconds) {
		try {
			WebDriverWait waitObj = new WebDriverWait(driver, waitTimeInSeconds);
			waitObj.until(ExpectedConditions.elementToBeClickable(locater));
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	private boolean highlightElement(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
		} catch (Exception e) {
		}
		return true;
	}

	private boolean removeHighlightElement(WebElement element) {
		if (element == null) {
			System.out.println("Element is NULL");
			return false;
		}
		try {
			((JavascriptExecutor)driver).executeScript("arguments[0].style.border=''", element);
		} catch (Exception e) {
		}
		return true;
	}

	public boolean isElementPresent(By elementLocater) {
		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void verifyIsElementPresent(By elementLocater, String elementName) {

		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS",
					"Verify is Element " + elementName + " present", "Element " + elementName + " not present");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL",
					"Verify is Element " + elementName + " present", "Element " + elementName + " not present");
			e.printStackTrace();
		}
	}

	public void getTextFromElement(By elementLocater, String elementName) {

		String elementText = "";
		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);

			elementText = element.getText();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS", "Get element " + elementName + " text ",
					"Element " + elementName + " having text " + elementText);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL", "Get element " + elementName + " text ",
					"Unable to capture Element " + elementName + " text");
			e.printStackTrace();
		}
	}

	public void getTextFromInputElement(By elementLocater, String elementName) {

		String elementText = "";
		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			elementText = element.getAttribute("value");
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS", "Get element " + elementName + " text ",
					"Element " + elementName + " having text " + elementText);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL", "Get element " + elementName + " text ",
					"Unable to capture Element " + elementName + " text");
			e.printStackTrace();
		}
	}

	public void enterText(By elementLocater, String testDataColumnName, String elementName) {

		String testData = getElementTestData(testDataColumnName);
		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			element.clear();
			element.sendKeys(testData);
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS",
					"Enter text " + testData + " into element " + elementName,
					"Text " + testData + " entered into element " + elementName);
			removeHighlightElement(element);
			log4jObj.debug("Text " + testData + " entered into element " + elementName);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL",
					"Enter text " + testData + " into element " + elementName,
					"Unable to enter Text " + testData + " entered into element " + elementName);
			e.printStackTrace();
			log4jObj.debug("Unable to enter Text " + testData + " entered into element " + elementName);
			log4jObj.error(""+e.getMessage());
		}
	}

	public void enterTextUsingActions(By elementLocater, String testDataColumnName, String elementName) {
		String testData = getElementTestData(testDataColumnName);
		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement(element);
			element.clear();
			Actions actObj = new Actions(driver);
			actObj.moveToElement(element).click().sendKeys(testData).build().perform();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS",
					"Enter text " + testData + " into element " + elementName,
					"Text " + testData + " entered into element " + element);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL",
					"Unable to Enter text " + testData + " into element " + elementName,
					"Text " + testData + " entered into element " + elementName);
			e.printStackTrace();
		}
	}

	public void clickElement(By elementLocater, String elementName) {

		dynamicWaitForElementClickable(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement( element);
			element.click();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS", "Click on " + elementName+" element",
					"Unable to click on "+elementName+" element");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL", "Click on " + elementName+" element",
					"Unable to click on "+elementName+" element");
			e.printStackTrace();
			log4jObj.error(""+e.getMessage());
		}
	}

	public void clickElementUsingJavaScript(By elementLocater, String elementName) {

		dynamicWaitForElementClickable(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement( element);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS", "Click the element " + elementName,
					"Element " + elementName + " clicked");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL", "Click the element " + elementName,
					"Unable to click the element " + elementName);
			e.printStackTrace();
		}
	}

	public void clickElementUsingActions(By elementLocater, String elementName) {

		dynamicWaitForElementClickable(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement( element);
			Actions actObj = new Actions(driver);
			actObj.moveToElement(element).click().build().perform();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS", "Click the element " + elementName,
					"Element " + elementName + " clicked");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL", "Click the element " + elementName,
					"Unable to click the element " + elementName);
			e.printStackTrace();
		}
	}

	public void clickElementUsingActionsWithCoordinates(By elementLocater, int x_position,
			int y_position, String elementName) {

		dynamicWaitForElementClickable(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement( element);
			Actions actObj = new Actions(driver);
			actObj.moveToElement(element, x_position, y_position).click().build().perform();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS", "Click the element " + elementName,
					"Element " + elementName + " clicked");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL", "Click the element " + elementName,
					"Unable to click the element " + elementName);
			e.printStackTrace();
		}
	}

	public void selectListBoxItemByIndex(By elementLocater, String testDataColumnName, String elementName) {

		int indexNumber = 0;
		try {
			indexNumber = Integer.parseInt(getElementTestData(testDataColumnName));
		} catch (NumberFormatException e1) {
		}
		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement( element);

			Select selObj = new Select(element);
			selObj.selectByIndex(indexNumber);
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS",
					"Select the Item (" + indexNumber + ") from list box " + elementName,
					"Item (" + indexNumber + ") selected from list box " + elementName);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL",
					"Select the Item (" + indexNumber + ") from list box " + elementName,
					"Unable to select the Item (" + indexNumber + ") from list box " + elementName);
			e.printStackTrace();
		}
	}

	public void selectListBoxItemByValue(By elementLocater, String testDataColumnName, String elementName) {

		String value = getElementTestData(testDataColumnName);
		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement( element);

			Select selObj = new Select(element);
			selObj.selectByValue(value);

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS",
					"Select the value (" + value + ") from list box " + elementName,
					"value (" + value + ") selected from list box " + elementName);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL",
					"Select the value (" + value + ") from list box " + elementName,
					"Unable to select the value (" + value + ") from list box " + elementName);
			e.printStackTrace();
		}
	}

	public void selectListBoxItemByVisibleText(By elementLocater, String testDataColumnName,
			String elementName) {

		String visibleText = getElementTestData(testDataColumnName);
		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement( element);

			Select selObj = new Select(element);
			selObj.selectByValue(visibleText);

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS",
					"Select the option (" + visibleText + ") from list box " + elementName,
					"Item (" + visibleText + ") selected from list box " + elementName);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL",
					"Select the option (" + visibleText + ") from list box " + elementName,
					"Unable to select the option (" + visibleText + ") from list box " + elementName);
			e.printStackTrace();
		}
	}

	public void switchToFrameByFrameIndex(String testDataColumnName) {

		int frameIndexNumber = 0;
		try {
			frameIndexNumber = Integer.parseInt(getElementTestData(testDataColumnName));
		} catch (NumberFormatException e1) {
		}
		try {
			driver.switchTo().frame(frameIndexNumber);
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS",
					"Switch to frame by index number " + frameIndexNumber,
					"Swiched to frame  using index " + frameIndexNumber + " successfully");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL",
					"Switch to frame by index number " + frameIndexNumber,
					"Unable Swich to frame  using index " + frameIndexNumber);
			e.printStackTrace();
		}
	}

	public void switchToFrameByFrameName(String testDataColumnName) {
		String frameName = getElementTestData(testDataColumnName);
		try {
			driver.switchTo().frame(frameName);
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS",
					"Switch to frame by frame name " + frameName,
					"Swiched to frame using frame name " + frameName + " successfully");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL",
					"Switch to frame by frame name " + frameName,
					"Unable Swich to frame  using frame name " + frameName);
			e.printStackTrace();
		}
	}

	public void switchToFrameByFrameElement(By elementLocater) {

		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {
			WebElement element = driver.findElement(elementLocater);
			highlightElement( element);
			driver.switchTo().frame(element);
			element.click();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS", "Switch to frame using element",
					"Swiched to frame  using element successfully");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL", "Switch to frame using element",
					"Unable to Swich to frame using element");
			e.printStackTrace();
		}
	}

	public void acceptAlert(By elementLocater, String elementName) {

		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {

			Alert alertObj = driver.switchTo().alert();
			alertObj.accept();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS", "Accept alert", "Alert accpeted");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL", "Accept alert", "Unable to acept Alert");
			e.printStackTrace();
		}
	}

	public void dismissAlert(By elementLocater, String elementName) {

		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {

			Alert alertObj = driver.switchTo().alert();
			alertObj.dismiss();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS", "Dismiss alert", "Dismiss accpeted");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL", "Dismiss alert", "Unable to Dismiss Alert");
			e.printStackTrace();
		}
	}

	public String getAlertMessage(By elementLocater, String elementName) {

		String alertMessage = "";
		dynamicWaitForElementVisible(elementLocater, FW_Constants.maxWaitTimeForElement);
		try {

			Alert alertObj = driver.switchTo().alert();
			alertMessage = alertObj.getText();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS", "Capture alert message",
					"Alert having message " + alertMessage);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL", "Capture alert message",
					"Unable to capture Alert message");
			e.printStackTrace();
		}
		return alertMessage;
	}

	public void switchToWindowByFrameIndex(String testDataColumnName) {
		int windowIndexNumber = 0;
		try {
			windowIndexNumber = Integer.parseInt(getElementTestData(testDataColumnName));
		} catch (NumberFormatException e1) {
		}
		try {
			String windowId = driver.getWindowHandles().toArray()[windowIndexNumber].toString();
			driver.switchTo().window(windowId);
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS",
					"Switch to window by index number " + windowIndexNumber,
					"Swiched to window  using index " + windowIndexNumber + " successfully");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL",
					"Switch to window by index number " + windowIndexNumber,
					"Unable Swich to window  using index " + windowIndexNumber);
			e.printStackTrace();
		}
	}

	public void switchToLatestWindow() {

		try {
			int count = driver.getWindowHandles().toArray().length;
			String windowId = driver.getWindowHandles().toArray()[count - 1].toString();
			driver.switchTo().window(windowId);
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS", "Switch to latest window",
					"Swiched to latest window successfully");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL", "Switch to latest window",
					"Unable to Swiched to latest window");
			e.printStackTrace();
		}
	}

	public void switchToMainWindow() {

		try {
			driver.switchTo().defaultContent();
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS", "Switch to main window",
					"Swiched to main window successfully");
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL", "Switch to main window",
					"Unable to Swiched to main window");
			e.printStackTrace();
		}
	}

	public void moveCursorUsingActions(int x_position, int y_position) {

		try {
			Actions actObj = new Actions(driver);
			actObj.moveByOffset(x_position, y_position).build().perform();

			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "PASS",
					"Move cursor to X - position " + x_position + " Y - position " + y_position,
					"Cursor moved to X - position " + x_position + " Y - position " + y_position);
		} catch (Exception e) {
			htmlReportObj.addTestStepToReportWithScreenShot(driver, reportTCObject, "FAIL",
					"Move cursor to X - position " + x_position + " Y - position " + y_position,
					"Unable to move to Cursor to X - position " + x_position + " Y - position " + y_position);
			e.printStackTrace();
		}
	}

	public void scrollDown(int noOfTimes) {

		Actions actObj = new Actions(driver);
		for (int i = 0; i < noOfTimes; i++) {
			actObj.sendKeys(Keys.PAGE_DOWN).build().perform();
			waitSomeTime(2);
		}
	}

	public void scrollUp(int noOfTimes) {

		Actions actObj = new Actions(driver);
		for (int i = 0; i < noOfTimes; i++) {
			actObj.sendKeys(Keys.PAGE_UP).build().perform();
			waitSomeTime(2);
		}
	}

	public void pressEnter(int noOfTimes) {

		Actions actObj = new Actions(driver);
		for (int i = 0; i < noOfTimes; i++) {
			actObj.sendKeys(Keys.ENTER).build().perform();
			waitSomeTime(2);
		}
	}

	public void pressTab(int noOfTimes) {

		Actions actObj = new Actions(driver);
		for (int i = 0; i < noOfTimes; i++) {
			actObj.sendKeys(Keys.TAB).build().perform();
			waitSomeTime(2);
		}
	}

	public void waitSomeTime(int timeInSeconds) {
		Actions actObj = new Actions(driver);
		actObj.pause(timeInSeconds * 1000);
	}

	public void closeCurrentBrowser() {
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	public void closeAllBrowsers(WebDriver driver) {
		try {
			driver.quit();
		} catch (Exception e) {
		}
	}

}
