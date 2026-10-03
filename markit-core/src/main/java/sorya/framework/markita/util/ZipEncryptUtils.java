package sorya.framework.markita.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class ZipEncryptUtils {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256; // or 128, 192, 256

    // Compress (Zip) -> Encrypt -> Base64 String
    public static String zipAndEncrypt(String data, SecretKey secretKey) throws Exception {
        byte[] compressed = compress(data.getBytes("UTF-8"));
        byte[] encrypted = encrypt(compressed, secretKey);
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // Base64 String -> Decrypt -> Decompress (Unzip) -> String
    public static String decryptAndUnzip(String encryptedBase64, SecretKey secretKey) throws Exception {
        byte[] encrypted = Base64.getDecoder().decode(encryptedBase64);
        byte[] decrypted = decrypt(encrypted, secretKey);
        byte[] decompressed = decompress(decrypted);
        return new String(decompressed, "UTF-8");
    }

    // Compression using Deflater
    private static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DeflaterOutputStream dos = new DeflaterOutputStream(baos)) {
            dos.write(data);
        }
        return baos.toByteArray();
    }

    // Decompression using Inflater
    private static byte[] decompress(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (InflaterInputStream iis = new InflaterInputStream(bais)) {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = iis.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
        }
        return baos.toByteArray();
    }

    // AES Encryption
    private static byte[] encrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    // AES Decryption
    private static byte[] decrypt(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    // Helper to generate a new AES key
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEY_SIZE);
        return keyGen.generateKey();
    }

    public static String createSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEY_SIZE);
        SecretKey secretKey = keyGen.generateKey();

        byte[] rawData = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(rawData);
    }

    public static SecretKey getSecretKey(String stringKey) {
        // 1. Decode your Base64 string back into bytes
        byte[] decodedKey = Base64.getDecoder().decode(stringKey);

        // 2. Rebuild the SecretKey using SecretKeySpec (must match the original algorithm)
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

    public static void main(String[] args) {
        try {
            SecretKey key = ZipEncryptUtils.generateKey();
            String originalText = "Hello, this is a secret and long text message that needs compression and encryption!";

            // Compress and Encrypt
            String securedString = ZipEncryptUtils.zipAndEncrypt(originalText, key);
            System.out.println("Secured String: " + securedString);
            try {
                Base64.getDecoder().decode(securedString);
                String stringKey = createSecretKey();
                System.out.println("=============== " + stringKey + ": " + stringKey.length());

                System.out.println(getSecretKey(stringKey).getAlgorithm());

            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
            Base64.getDecoder().decode(securedString);

            // Decrypt and Decompress
            String restoredText = ZipEncryptUtils.decryptAndUnzip(securedString, key);
            System.out.println("Restored Text: " + restoredText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

