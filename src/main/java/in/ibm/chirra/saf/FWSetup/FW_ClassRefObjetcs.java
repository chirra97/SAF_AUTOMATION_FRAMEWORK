package in.ibm.chirra.saf.FWSetup;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import in.ibm.chirra.saf.report.HTMLReport;

public class FW_ClassRefObjetcs {
	
	
	public WebDriver driver = null;
	public LinkedHashMap<String, String> classTestData = null;
	public HTMLReport htmlReportObj = null;
	public Logger log4jObj = null;
	public ExtentTest reportTCObject = null;
	
	public FW_ClassRefObjetcs(WebDriver driver, LinkedHashMap<String, String> classTestData, 
			HTMLReport htmlReportObj, ExtentTest reportTCObject, Logger log4jObj) {
		
		this.driver = driver;
		this.classTestData = classTestData;
		this.htmlReportObj = htmlReportObj;
		this.reportTCObject = reportTCObject;
		this.log4jObj = log4jObj;
		 
	}
	
	
	
	
	

}
