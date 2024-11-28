package application;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import BouncyCastle.jce.provider.BouncyCastleProvider;

public class EncryptionHelper {

 private static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final byte[] keyBytes = new byte[]{
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f
    };
    private static final SecretKey key = new SecretKeySpec(keyBytes, "AES");

    public static String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);

        // Generate a secure random IV
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        // Initialize the cipher with encryption mode and the IV
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

        // Combine IV and encrypted text in Base64 format
        String ivBase64 = Base64.getEncoder().encodeToString(iv);
        String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);
        return ivBase64 + ":" + encryptedBase64;
    }

    public static String decrypt(String encryptedText) throws Exception {
        String[] parts = encryptedText.split(":");
        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] encryptedBytes = Base64.getDecoder().decode(parts[1]);

        // Initialize the cipher with decryption mode and the IV
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, "UTF-8");
    }

}
