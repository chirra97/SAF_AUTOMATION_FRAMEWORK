package in.ibm.chirra.saf.test;

import org.testng.annotations.Test;

import in.ibm.chirra.saf.businesslogics.TC001_BL;
import in.ibm.chirra.saf.driverSetup.DriverSetup;


public class Test_2 extends DriverSetup{
	
	
	@Test
	public void sampleTest1() {
		System.out.println("Test 2 - sampleTest1");
		
		TC001_BL blObj = new TC001_BL(driver, classTestData_LHM, htmlReportObj, log4jObj);
		blObj.login();
	}

}
