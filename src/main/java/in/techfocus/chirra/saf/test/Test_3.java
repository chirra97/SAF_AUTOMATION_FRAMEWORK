package in.techfocus.chirra.saf.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

import in.techfocus.chirra.saf.report.WordGenerator;

public class Test_3 {

	public void generateDOCReport() throws Exception {

		String documnetFilePath = "C:\\Users\\SureshChirra\\Desktop\\abc.doc";
		String imagePath = "C:\\Users\\SureshChirra\\Desktop\\SBI Passbook.png";

		WordGenerator docObj = new WordGenerator(documnetFilePath);
		docObj.createNewDocument("TC001");

		docObj.addPassTepToDocument("Pass step");

		docObj.addPassStepToDocumentWithImage(imagePath, "Pass step with image");

		docObj.addFailStepToDocumentWithImage(imagePath, "failed step with image");

		docObj.saveDocumnet();
	}
	
	public static void optionalParmTest(String name, String...number) {
		System.out.println("name : "+name+", number : "+number.length);
		
		System.out.println(number[0]);
		
		System.out.println(number[1]);
	}
	
	public static void main(String[] args) {
		//optionalParmTest("CSR", "10", "yes");
		
		
	}

}
