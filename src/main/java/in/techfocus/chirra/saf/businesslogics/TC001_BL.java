package in.techfocus.chirra.saf.businesslogics;

import in.techfocus.chirra.saf.FWSetup.FWGlobalReferenceClass;
import in.techfocus.chirra.saf.elementActions.ElementActions;
import in.techfocus.chirra.saf.pageObjects.LoginPage;

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
