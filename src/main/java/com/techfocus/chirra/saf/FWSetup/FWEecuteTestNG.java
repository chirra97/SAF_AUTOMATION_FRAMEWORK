package com.techfocus.chirra.saf.FWSetup;

import java.io.IOException;

import org.testng.annotations.Test;

/**
 * The Class FW_Eecute_testNG.
 */
public class FWEecuteTestNG {
	
	/**
	 * Run frame work.
	 * @throws IOException 
	 */
	@Test
	public void runFrameWork() throws IOException {
		
		FWDataLoad obj = new FWDataLoad();
		obj.fw_executeMethod();
	}
}
