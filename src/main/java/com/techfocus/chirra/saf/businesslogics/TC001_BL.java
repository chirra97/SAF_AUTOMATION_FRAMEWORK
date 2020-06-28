package com.techfocus.chirra.saf.businesslogics;

import com.techfocus.chirra.saf.FWSetup.FWGlobalReferenceClass;
import com.techfocus.chirra.saf.elementActions.ElementActions;
import com.techfocus.chirra.saf.pageObjects.LoginPage;

public class TC001_BL {

	ElementActions actObj = null;
	LoginPage loginpageObj = new LoginPage();
	

	public TC001_BL(FWGlobalReferenceClass fwClassRefObj) {
		actObj = new ElementActions(fwClassRefObj);
	}

	
	public void login() {
		actObj.enterText(loginpageObj.mobileNumber, "UID", "User ID", "10");
		actObj.enterText(loginpageObj.password, "PWD", "Password", "0");
		actObj.clickElement(loginpageObj.submit, "Submit", "0");
	}
	
	
}
