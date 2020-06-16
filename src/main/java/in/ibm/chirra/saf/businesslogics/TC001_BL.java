package in.ibm.chirra.saf.businesslogics;

import java.util.LinkedHashMap;

import org.openqa.selenium.WebDriver;

import in.ibm.chirra.saf.elementActions.ElementActions;
import in.ibm.chirra.saf.enums.REPORT;
import in.ibm.chirra.saf.enums.SELENIUM;
import in.ibm.chirra.saf.pageObjects.HomePage;
import in.ibm.chirra.saf.report.HTMLReport;

public class TC001_BL {

	WebDriver driver = null;
	LinkedHashMap<String, String> classTestData = null;
	HTMLReport htmlReportObj = null;
	
	ElementActions actObj = new ElementActions();
	HomePage pageObj = new HomePage();
	

	public TC001_BL(WebDriver driver, LinkedHashMap<String, String> classTestData, HTMLReport htmlReportObj) {
		this.driver = driver;
		this.classTestData = classTestData;
		this.htmlReportObj = htmlReportObj;
	}

	public void basicSteps() {

		actObj.enterText(driver, pageObj.inputFields_edit, classTestData.get("UID"), "SEARCH TEXT",
				SELENIUM.ELEMENT_WAIT_TIME_ENABLED, REPORT.ADD_STEP_TO_REPORT_WITHSCREENSHOT_TRUE, htmlReportObj);

	}

}
