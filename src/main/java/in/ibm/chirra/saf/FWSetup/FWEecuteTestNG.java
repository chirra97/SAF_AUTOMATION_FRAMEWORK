package in.ibm.chirra.saf.FWSetup;

import org.testng.annotations.Test;

/**
 * The Class FW_Eecute_testNG.
 */
public class FWEecuteTestNG {
	
	/**
	 * Run frame work.
	 */
	@Test
	public void runFrameWork() {
		
		FWDataLoad obj = new FWDataLoad();
		obj.fw_executeMethod();
	}
}
