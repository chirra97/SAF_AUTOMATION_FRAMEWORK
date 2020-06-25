package in.techfocus.chirra.saf.FWSetup;

import java.io.IOException;

public class FWEecuteJava {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		FWDataLoad obj = new FWDataLoad();
		obj.fw_executeMethod();
	}

}
