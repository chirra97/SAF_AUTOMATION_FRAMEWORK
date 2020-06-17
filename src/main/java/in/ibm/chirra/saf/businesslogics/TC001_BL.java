package in.ibm.chirra.saf.businesslogics;

import java.util.LinkedHashMap;

import org.openqa.selenium.WebDriver;

import in.ibm.chirra.saf.elementActions.ElementActions;
import in.ibm.chirra.saf.pageObjects.LoginPage;
import in.ibm.chirra.saf.report.HTMLReport;

public class TC001_BL {

	WebDriver driver = null;
	LinkedHashMap<String, String> classTestData = null;
	HTMLReport htmlReportObj = null;
	
	ElementActions actObj = new ElementActions();
	LoginPage loginpageObj = new LoginPage();
	

	public TC001_BL(WebDriver driver, LinkedHashMap<String, String> classTestData, HTMLReport htmlReportObj) {
		this.driver = driver;
		this.classTestData = classTestData;
		this.htmlReportObj = htmlReportObj;
	}

	
	public void login() {
		actObj.enterText(driver, loginpageObj.mobileNumber, classTestData.get("UID"), "User ID", htmlReportObj);
		actObj.enterText(driver, loginpageObj.password, classTestData.get("UID"), "User ID", htmlReportObj);
		actObj.clickElement(driver, loginpageObj.submit, "Submit", htmlReportObj);
	}
	
	
}
