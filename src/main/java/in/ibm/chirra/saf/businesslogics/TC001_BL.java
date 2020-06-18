package in.ibm.chirra.saf.businesslogics;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import in.ibm.chirra.saf.elementActions.ElementActions;
import in.ibm.chirra.saf.pageObjects.LoginPage;
import in.ibm.chirra.saf.report.HTMLReport;

public class TC001_BL {

	WebDriver driver = null;
	LinkedHashMap<String, String> classTestData = null;
	HTMLReport htmlReportObj = null;
	String logFilePath = null;
	Logger log4jObj = null;
	
	ElementActions actObj = null;
	LoginPage loginpageObj = new LoginPage();
	

	public TC001_BL(WebDriver driver, LinkedHashMap<String, String> classTestData, HTMLReport htmlReportObj, Logger log4jObj) {
		this.driver = driver;
		this.classTestData = classTestData;
		this.htmlReportObj = htmlReportObj;
		this.log4jObj = log4jObj;
		actObj = new ElementActions(driver, classTestData, htmlReportObj, log4jObj);
	}

	
	public void login() {
		actObj.enterText(loginpageObj.mobileNumber, classTestData.get("UID"), "User ID");
		actObj.enterText(loginpageObj.password, classTestData.get("PWD"), "Password");
		actObj.clickElement(loginpageObj.submit, "Submit");
	}
	
	
}
