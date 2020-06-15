package in.ibm.chirra.saf.utilities;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

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

    public static void main(String[] args) {
        getTempFolderPath();
        String originalString = "password";
        System.out.println("Original String to encrypt - " + originalString);
        String encryptedString = encryptText(originalString);
        System.out.println("Encrypted String - " + encryptedString);
        String decryptedString = decryptText(encryptedString);
        System.out.println("After decryption - " + decryptedString);
        System.out.println("User name - " +getUserName());
    }
}
