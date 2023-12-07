package com.mapfre.tron.api.cmn.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * The encryptor api.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Jun 2021 - 08:32:27
 *
 */
@Component
@Slf4j
public class Encryptor {

    private static final String ALGORITHM = "AES/GCM/NoPadding";

    private static final String DEFAULTSECRETKEY = "77455cb9f92a41e916d83a191e62c27e";

    private Key secretKeySpec;

    public static void main(String[] args) throws Exception {
        String plainText = "1-NIF-77777777G";

        Encryptor aes = new Encryptor();
        String encryptedText = aes.encrypt(plainText);
       log.info(aes.encrypt(plainText));
       log.info(aes.encrypt(plainText));
       log.info(aes.encrypt(plainText));
       log.info("Encripting " + plainText + ":" + encryptedText);
       log.info("Decripting " + encryptedText + ":" + aes.decrypt(encryptedText));
    }

    public Encryptor() {
        this(null);
    }

    public Encryptor(String secretKey) {
        try {
            this.secretKeySpec = generateKey(secretKey);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String encrypt(String plainText)  {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
            return encode(encrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }

    }

    public String decrypt(String encryptedString) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] original = cipher.doFinal(decode(encryptedString));
            return new String(original);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static Object resizeArray(Object oldArray, int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        @SuppressWarnings("rawtypes")
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
        int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0)
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);

        return newArray;
    }

    private Key generateKey(String secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (secretKey == null) {
            secretKey = DEFAULTSECRETKEY;
        }
        byte[] key = (secretKey).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        // key = Arrays.copyOf(key, 16); // use only the first 128 bit
        key = (byte[]) resizeArray(key, 16); // use only the first 128 bit
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128); // 192 and 256 bits may not be available

        return new SecretKeySpec(key, ALGORITHM);
    }

    private final String encode(byte buf[]) {
        StringBuilder strbuf = new StringBuilder(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();

    }

    private final byte[] decode(String hexString) {
        int arrLength = hexString.length() >> 1;
        byte buf[] = new byte[arrLength];
        for (int ii = 0; ii < arrLength; ii++) {
            int index = ii << 1;
            String l_digit = hexString.substring(index, index + 2);
            buf[ii] = (byte) Integer.parseInt(l_digit, 16);
        }
        return buf;
    }

}
