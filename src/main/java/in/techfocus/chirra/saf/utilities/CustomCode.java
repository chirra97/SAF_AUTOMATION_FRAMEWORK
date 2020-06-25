package in.techfocus.chirra.saf.utilities;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CustomCode {

	private static final String key = "SureshKumarChira";
	private static final String initVector = "SureshReddyChira";

	public static String getSystemOSName() {
		String osName = System.getProperty("os.name");
		return osName;
	}

	public static String getProjectPath() {
		String path = System.getProperty("user.dir");
		return path;
	}

	public static String getUserName() {
		String path = System.getProperty("user.name");
		return path;
	}

	public static String getTempFolderPath() {
		String path = System.getProperty("java.io.tmpdir");
		return path;
	}

	public static String encryptText(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String decryptText(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static boolean downloadFWJarToDriversFolder(String jarName) throws IOException {
		try {
			String path = "http://admin.akshara.edu.pushvastech.in/AKSHARA_EDUCATION_DOCUMENTS/" + jarName;
			URL url = new URL(path);
			InputStream inStream = url.openStream();
			BufferedInputStream bufIn = new BufferedInputStream(inStream);

			String currentDirectory = System.getProperty("user.dir");
			String libFolder = currentDirectory + "/Drivers";
			File fileObj = new File(libFolder);
			if (!fileObj.exists())
				fileObj.mkdir();
			else {
				ArrayList<String> jarFileNames = new ArrayList<String>();

				File folder = new File(libFolder);
				File[] files = folder.listFiles();
				for (File file : files) 
					jarFileNames.add(file.getName());
				
				System.out.println("jarFileNames : " + jarFileNames);

				if (jarFileNames.contains(jarName)) {
					System.out.println("++++++++++++++++++++++++++ Jar alrady downloaded to project LIB folder");
					return true;
				}
			}

			File fileWrite = new File(libFolder + "/" + jarName);
			OutputStream out = new FileOutputStream(fileWrite);
			BufferedOutputStream bufOut = new BufferedOutputStream(out);
			byte buffer[] = new byte[1024];
			while (true) {
				int nRead = bufIn.read(buffer, 0, buffer.length);
				if (nRead <= 0)
					break;
				bufOut.write(buffer, 0, nRead);
			}

			bufOut.flush();
			out.close();
			inStream.close();
			System.out.println("**************************************************************");
			System.out.println("                Successfully download the jar");
			System.out.println("**************************************************************");
			return true;
		} catch (Exception e) {
			System.err.println("**************************************************************");
			System.err.println("                Unable to download the jar");
			System.err.println("**************************************************************");
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) throws IOException {

		downloadFWJarToDriversFolder("133cde904fbd6c447a9ef60d7b6cfd5f_selenium.automation.framework-1.0-javadoc.jar");

		/*
		 * getTempFolderPath(); String originalString = "password";
		 * System.out.println("Original String to encrypt - " + originalString); String
		 * encryptedString = encryptText(originalString);
		 * System.out.println("Encrypted String - " + encryptedString); String
		 * decryptedString = decryptText(encryptedString);
		 * System.out.println("After decryption - " + decryptedString);
		 * System.out.println("User name - " +getUserName());
		 */
	}
}
