package in.techfocus.chirra.saf.FWSetup;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import in.techfocus.chirra.saf.report.HTMLReport;
import in.techfocus.chirra.saf.report.WordGenerator;

public class FWGlobalReferenceClass {

	public WebDriver driver = null;
	public LinkedHashMap<String, String> classTestData = null;
	public HTMLReport htmlReportObj = null;
	public ExtentTest reportTCObject = null;
	public WordGenerator wordDocGenerateObj = null;
	public Logger log4jObj = null;

	public FWGlobalReferenceClass(WebDriver driver, LinkedHashMap<String, String> classTestData, HTMLReport htmlReportObj,
			ExtentTest reportTCObject, WordGenerator wordDocGenerateObj, Logger log4jObj) {

		this.driver = driver;
		this.classTestData = classTestData;
		this.htmlReportObj = htmlReportObj;
		this.reportTCObject = reportTCObject;
		this.wordDocGenerateObj = wordDocGenerateObj;
		this.log4jObj = log4jObj;
	}
}
