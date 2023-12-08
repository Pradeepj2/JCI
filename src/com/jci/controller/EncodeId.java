//package com.jci.controller;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//
//public class EncodeId {
//
//    public static SecretKey generateAESKey(int keyLength) throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(keyLength); // 128, 192, or 256
//        return keyGenerator.generateKey();
//    }
//
//    public static String encrypt(String data, SecretKey secretKey) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
//        return Base64.getEncoder().encodeToString(encryptedBytes);
//    }
//
//    public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.DECRYPT_MODE, secretKey);
//        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
//        return new String(decryptedBytes);
//    }
//
//	
//	
//	
//}

/*
 * package com.jci.controller;
 * 
 * import java.util.Base64;
 * 
 * public class EncodeId {
 * 
 * public static String encodeBase64(String originalString) { byte[]
 * encodedBytes = Base64.getEncoder().encode(originalString.getBytes()); return
 * new String(encodedBytes); }
 * 
 * // Method to decode a Base64 string public static String decodeBase64(String
 * base64String) { byte[] decodedBytes =
 * Base64.getDecoder().decode(base64String); return new String(decodedBytes); }
 * 
 * }
 */