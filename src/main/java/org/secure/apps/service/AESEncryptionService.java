package org.secure.apps.service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class AESEncryptionService implements SecureContentService {

    private static final int ITERATIONS = 10000;
    private static final int KEY_SIZE = 256;
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private byte[] iv;

    public byte[] encrypt(String data, String password) throws Exception {
        if (iv == null) {
            generateIV();
        }

        SecretKeySpec secretKey = generateKey(password);

        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

        return cipher.doFinal(data.getBytes());
    }

    public String decrypt(byte[] encryptedBytes, String password) throws Exception {
        if (encryptedBytes.length >= 16) {
            iv = new byte[16];
            System.arraycopy(encryptedBytes, 0, iv, 0, 16);

            byte[] encryptedData = new byte[encryptedBytes.length - 16];
            System.arraycopy(encryptedBytes, 16, encryptedData, 0, encryptedData.length);

            SecretKeySpec secretKey = generateKey(password);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] decryptedBytes = cipher.doFinal(encryptedData);
            return new String(decryptedBytes);
        } else {
            throw new RuntimeException("Invalid encrypted file format.");
        }
    }

    private void generateIV() {
        SecureRandom random = new SecureRandom();
        iv = new byte[16];
        random.nextBytes(iv);
    }

    private SecretKeySpec generateKey(String password) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), new byte[16], ITERATIONS, KEY_SIZE);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), ALGORITHM);
    }

    public byte[] getIv() {
        return this.iv;
    }
}
