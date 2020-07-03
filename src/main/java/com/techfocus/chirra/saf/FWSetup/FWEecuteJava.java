package com.techfocus.chirra.saf.FWSetup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class FWEecuteJava {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		/*
		 * TestNG runner = new TestNG(); List<String> suitefiles = new
		 * ArrayList<String>(); suitefiles.add("TestNGExecuter.xml");
		 * runner.setTestSuites(suitefiles); runner.run();
		 */
		
		
		FWDataLoad obj = new FWDataLoad();
		obj.fw_executeMethod();
	}

}
