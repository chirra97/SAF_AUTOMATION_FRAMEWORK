package in.ibm.chirra.saf.FWSetup;

import org.testng.annotations.Test;

public class FW_Eecute_testNG {
	
	
	
	@Test
	public void runFrameWork() {
		
		FW_DataLoad obj = new FW_DataLoad();
		obj.fw_executeMethod();
		
	}

}
