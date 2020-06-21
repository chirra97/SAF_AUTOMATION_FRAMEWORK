package in.ibm.chirra.saf.FWSetup;

import org.testng.annotations.Test;
import in.ibm.chirra.saf.driverSetup.DriverSetup;

/**
 * The Class FW_Eecute_testNG.
 */
public class FW_Eecute_testNG {
	
	/**
	 * Run frame work.
	 */
	@Test
	public void runFrameWork() {
		
		FW_DataLoad obj = new FW_DataLoad();
		obj.fw_executeMethod();
	}
}
