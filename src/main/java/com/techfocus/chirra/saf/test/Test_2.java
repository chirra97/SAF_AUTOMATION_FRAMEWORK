package com.techfocus.chirra.saf.test;

import org.testng.annotations.Test;

import com.techfocus.chirra.saf.businesslogics.TC001_BL;
import com.techfocus.chirra.saf.driverSetup.DriverSetup;


public class Test_2 extends DriverSetup{
	
	
	@Test
	public void sampleTest1() {
		System.out.println("Test 2 - sampleTest1");
		
		TC001_BL blObj = new TC001_BL(fwGlobalReferenceClassObj);
		blObj.login();
		
		
	}

}
