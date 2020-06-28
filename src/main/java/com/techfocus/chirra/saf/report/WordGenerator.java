package com.techfocus.chirra.saf.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.techfocus.chirra.saf.utilities.DateTimeWork;

public class WordGenerator {

	private String documnetFilePath = "";
	
	public WordGenerator(String documnetFilePath) {
		this.documnetFilePath = documnetFilePath;
	}
	
	public String createNewDocument( String TC_ID) throws Exception {

		XWPFDocument document = new XWPFDocument();
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

		FileOutputStream out = new FileOutputStream(new File(documnetFilePath));

		// create Paragraph
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun paragraphOneRunOne = paragraph.createRun();
		paragraphOneRunOne.setBold(true);

		paragraphOneRunOne.setFontSize(20);

		paragraphOneRunOne.setText(TC_ID);
		paragraphOneRunOne.addBreak();
		paragraphOneRunOne.addBreak();
		paragraphOneRunOne.addBreak();

		XWPFRun paragraphOneRun2 = paragraph.createRun();
		paragraphOneRun2.setBold(true);
		paragraphOneRun2.setItalic(true);
		paragraphOneRun2.setFontSize(15);

		paragraphOneRun2.setText("Execution Started: " + timeStamp);
		paragraphOneRun2.addBreak();
		document.write(out);
		out.flush();
		out.close();
		return documnetFilePath;
	}

	public void saveDocumnet() throws IOException {

		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		FileInputStream fs = new FileInputStream(new File(documnetFilePath));

		XWPFDocument document = new XWPFDocument(fs);
		XWPFParagraph tmpParagraph = document.createParagraph();
		tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun tmpRun = tmpParagraph.createRun();
		// tmpRun.setText("Test Case Status: " + "ADD STATUS");

		tmpRun.setBold(true);
		tmpRun.setFontSize(20);
		tmpRun.setColor("000099");
		tmpRun.addBreak();

		XWPFRun paragraphOneRun2 = tmpParagraph.createRun();
		paragraphOneRun2.setBold(true);
		paragraphOneRun2.setItalic(true);
		// paragraphOneRunOne.setTextPosition(100);
		paragraphOneRun2.setFontSize(15);
		paragraphOneRun2.setText("Execution Completed: " + timeStamp);
		FileOutputStream fos = new FileOutputStream(documnetFilePath);
		document.write(fos);
		fos.close();
	}

	public void addPassTepToDocument( String message) throws IOException {

		FileInputStream fs = new FileInputStream(new File(documnetFilePath));
		XWPFDocument document = new XWPFDocument(fs);
		XWPFParagraph tmpParagraph = document.createParagraph();
		XWPFRun tmpRun = tmpParagraph.createRun();
		tmpRun.addBreak();
		tmpRun.setText(message);
		tmpRun.setText(DateTimeWork.getCurrentDateTime());
		tmpRun.setBold(true);
		tmpRun.setFontSize(12);
		tmpRun.setColor("000099");
		FileOutputStream fos = new FileOutputStream(documnetFilePath);
		document.write(fos);
		fos.close();
	}

	public void addPassStepToDocumentWithImage( String imagePath, String message)
			throws IOException, InvalidFormatException {

		FileInputStream fs = new FileInputStream(new File(documnetFilePath));
		XWPFDocument document = new XWPFDocument(fs);
		XWPFParagraph tmpParagraph = document.createParagraph();
		XWPFRun tmpRun = tmpParagraph.createRun();
		tmpRun.addBreak();
		tmpRun.setText(message);
		tmpRun.addBreak();
		tmpRun.setText(DateTimeWork.getCurrentDateTime());
		tmpRun.setBold(true);
		tmpRun.setFontSize(12);
		tmpRun.setColor("000099");

		FileOutputStream fos = new FileOutputStream(documnetFilePath);
		document.write(fos);
		fos.close();
		addImageToDocument(imagePath);
	}

	public void addFailStepToDocumentWithImage( String imagePath, String message)
			throws IOException, InvalidFormatException {

		FileInputStream fs = new FileInputStream(new File(documnetFilePath));
		XWPFDocument document = new XWPFDocument(fs);
		XWPFParagraph tmpParagraph = document.createParagraph();
		XWPFRun tmpRun = tmpParagraph.createRun();
		tmpRun.addBreak();
		tmpRun.setText(message);
		tmpRun.addBreak();
		tmpRun.setText(DateTimeWork.getCurrentDateTime());
		tmpRun.setBold(true);
		tmpRun.setFontSize(12);
		tmpRun.setColor("FF0000");
		FileOutputStream fos = new FileOutputStream(documnetFilePath);
		document.write(fos);
		fos.close();

		addImageToDocument(imagePath);
	}

	public void addImageToDocument(String imgePath)
			throws FileNotFoundException, IOException, InvalidFormatException {

		CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream(new File(documnetFilePath)));
		FileOutputStream fos = new FileOutputStream(new File(documnetFilePath));
		String blipId = "";
		try {
			blipId = document.addPictureData(new FileInputStream(new File(imgePath)), Document.PICTURE_TYPE_PNG);
			document.createPicture(blipId, document.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 600, 600);
		} catch (Exception e) {
		}
		document.write(fos);
		fos.flush();
		fos.close();
	}

}
