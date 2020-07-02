package com.techfocus.chirra.saf.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.techfocus.chirra.saf.report.WordGenerator;

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

	public static void optionalParmTest(String name, String... number) {
		System.out.println("name : " + name + ", number : " + number.length);

		System.out.println(number[0]);

		System.out.println(number[1]);
	}

	public static void main(String[] args) {

		ArrayList<String> unsortedData = new ArrayList<>();
		unsortedData.add("BB");
		unsortedData.add("AA");
		unsortedData.add("DD");
		unsortedData.add("CC");
		System.out.println("unsortedData : " + unsortedData);

		ArrayList<String> orderedData = new ArrayList<String>();
		orderedData.addAll(unsortedData);
		
		System.out.println("orderedData : " + orderedData);
		
		Collections.sort(unsortedData);
		
		System.out.println("unsortedData : " + unsortedData);
		
		
		
		System.out.println(unsortedData.equals(orderedData));
		
		

		

	}

}
