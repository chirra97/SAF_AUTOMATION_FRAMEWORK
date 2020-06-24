package in.ibm.chirra.saf.businesslogics;

import in.ibm.chirra.saf.FWSetup.FWGlobalReferenceClass;
import in.ibm.chirra.saf.elementActions.ElementActions;
import in.ibm.chirra.saf.pageObjects.LoginPage;

public class TC001_BL {

	ElementActions actObj = null;
	LoginPage loginpageObj = new LoginPage();
	

	public TC001_BL(FWGlobalReferenceClass fwClassRefObj) {
		actObj = new ElementActions(fwClassRefObj);
	}

	
	public void login() {
		actObj.enterText(loginpageObj.mobileNumber, "UID", "User ID");
		actObj.enterText(loginpageObj.password, "PWD", "Password");
		actObj.clickElement(loginpageObj.submit, "Submit");
	}
	
	
}
