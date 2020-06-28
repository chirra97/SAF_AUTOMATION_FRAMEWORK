package com.techfocus.chirra.saf.test;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.tethering.model.Accepted;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;

import com.techfocus.chirra.saf.FWSetup.FWConstants;
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
	
	public static void optionalParmTest(String name, String...number) {
		System.out.println("name : "+name+", number : "+number.length);
		
		System.out.println(number[0]);
		
		System.out.println(number[1]);
	}
	
	public static void main(String[] args) {
		//optionalParmTest("CSR", "10", "yes");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\SureshChirra\\AppData\\Local\\Temp\\Drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://google.com");
		/*
		 * Actions actObj = new Actions(driver); actObj.sendKeys(Keys.CONTROL, "T");
		 */
		
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		
		//driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
		
		driver.navigate().to("http://google.com");
		
		
		
	}

}
