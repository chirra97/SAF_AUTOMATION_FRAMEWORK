package in.ibm.chirra.saf.test;

import org.testng.annotations.Test;

import in.ibm.chirra.saf.businesslogics.TC001_BL;
import in.ibm.chirra.saf.driverSetup.DriverSetup;
import in.ibm.chirra.saf.report.WordGenerator;

public class Test_3{
	
	
	@Test
	public void sampleTest1() throws Exception {
		
		String documnetFilePath = "C:\\Users\\SureshChirra\\Desktop\\abc.doc";
		String imagePath = "C:\\Users\\SureshChirra\\Desktop\\SBI Passbook.png";
		
		WordGenerator docObj = new WordGenerator(documnetFilePath);
		docObj.createNewDocument("TC001");
		
		docObj.addPassTepToDocument("Pass step");
		
		docObj.addPassStepToDocumentWithImage(imagePath, "Pass step with image");
		
		docObj.addFailStepToDocumentWithImage(imagePath, "failed step with image");
		
		docObj.saveDocumnet();
		
	}

}
