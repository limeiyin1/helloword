package com.test.www;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSAUtils {
    /** 
     * 生成公钥和私钥 
     * @throws NoSuchAlgorithmException  
     * 
     */  
    public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException{  
        HashMap<String, Object> map = new HashMap<String, Object>();  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        keyPairGen.initialize(1024);  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        map.put("public", publicKey);  
        map.put("private", privateKey);  
        return map;  
    }  
    /** 
     * 使用模和指数生成RSA公钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    @SuppressWarnings("restriction")
	public static RSAPublicKey getPublicKey(String key) throws Exception {
          byte[] keyBytes;
          keyBytes = (new sun.misc.BASE64Decoder()).decodeBuffer(key);

          X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
          KeyFactory keyFactory = KeyFactory.getInstance("RSA");
          PublicKey publicKey = keyFactory.generatePublic(keySpec);
          return (RSAPublicKey)publicKey;
    	
    }
  
    /** 
     * 使用模和指数生成RSA私钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    @SuppressWarnings("restriction")
	public static RSAPrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new sun.misc.BASE64Decoder()).decodeBuffer(key);
        
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return (RSAPrivateKey)privateKey;
  }
  
    /** 
     * 公钥加密 
     *  
     * @param data 
     * @param publicKey 
     * @return 
     * @throws Exception 
     */  
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        // 模长  
        int key_len = publicKey.getModulus().bitLength() / 8;  
        // 加密数据长度 <= 模长-11  
        String[] datas = splitString(data, key_len - 11);  
        String mi = "";  
        //如果明文长度大于模长-11则要分组加密  
        for (String s : datas) {  
            mi += bcd2Str(cipher.doFinal(s.getBytes()));  
        }  
        return mi;  
    }  
  
    /** 
     * 私钥解密 
     *  
     * @param data 
     * @param privateKey 
     * @return 
     * @throws Exception 
     */  
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        //模长  
        int key_len = privateKey.getModulus().bitLength() / 8;  
        byte[] bytes = data.getBytes();  
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);    
        //如果密文长度大于模长则要分组解密  
        String ming = "";  
        byte[][] arrays = splitArray(bcd, key_len);  
        for(byte[] arr : arrays){  
            ming += new String(cipher.doFinal(arr));  
        }  
        return ming;  
    }  
    /** 
     * ASCII码转BCD码 
     *  
     */  
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {  
        byte[] bcd = new byte[asc_len / 2];  
        int j = 0;  
        for (int i = 0; i < (asc_len + 1) / 2; i++) {  
            bcd[i] = asc_to_bcd(ascii[j++]);  
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));  
        }  
        return bcd;  
    }  
    public static byte asc_to_bcd(byte asc) {  
        byte bcd;  
  
        if ((asc >= '0') && (asc <= '9'))  
            bcd = (byte) (asc - '0');  
        else if ((asc >= 'A') && (asc <= 'F'))  
            bcd = (byte) (asc - 'A' + 10);  
        else if ((asc >= 'a') && (asc <= 'f'))  
            bcd = (byte) (asc - 'a' + 10);  
        else  
            bcd = (byte) (asc - 48);  
        return bcd;  
    }  
    /** 
     * BCD转字符串 
     */  
    public static String bcd2Str(byte[] bytes) {  
        char temp[] = new char[bytes.length * 2], val;  
  
        for (int i = 0; i < bytes.length; i++) {  
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);  
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
  
            val = (char) (bytes[i] & 0x0f);  
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
        }  
        return new String(temp);  
    }  
    /** 
     * 拆分字符串 
     */  
    public static String[] splitString(String string, int len) {  
        int x = string.length() / len;  
        int y = string.length() % len;  
        int z = 0;  
        if (y != 0) {  
            z = 1;  
        }  
        String[] strings = new String[x + z];  
        String str = "";  
        for (int i=0; i<x+z; i++) {  
            if (i==x+z-1 && y!=0) {  
                str = string.substring(i*len, i*len+y);  
            }else{  
                str = string.substring(i*len, i*len+len);  
            }  
            strings[i] = str;  
        }  
        return strings;  
    }  
    /** 
     *拆分数组  
     */  
    public static byte[][] splitArray(byte[] data,int len){  
        int x = data.length / len;  
        int y = data.length % len;  
        int z = 0;  
        if(y!=0){  
            z = 1;  
        }  
        byte[][] arrays = new byte[x+z][];  
        byte[] arr;  
        for(int i=0; i<x+z; i++){  
            arr = new byte[len];  
            if(i==x+z-1 && y!=0){  
                System.arraycopy(data, i*len, arr, 0, y);  
            }else{  
                System.arraycopy(data, i*len, arr, 0, len);  
            }  
            arrays[i] = arr;  
        }  
        return arrays;  
    }  
    
    
    /** 
     * 私钥加密 
     *  
     * @param data 
     * @param publicKey 
     * @return 
     * @throws Exception 
     */  
    public static String encryptByPrivateKey(String data, RSAPrivateKey privateKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
        // 模长  
        int key_len = privateKey.getModulus().bitLength() / 8;  
        // 加密数据长度 <= 模长-11  
        String[] datas = splitString(data, key_len - 11);  
        String mi = "";  
        //如果明文长度大于模长-11则要分组加密  
        for (String s : datas) {  
            mi += bcd2Str(cipher.doFinal(s.getBytes()));  
        }  
        return mi;  
    } 
    
    /** 
     * 公钥解密 
     *  
     * @param data 
     * @param privateKey 
     * @return 
     * @throws Exception 
     */  
    public static String decryptByPublicKey(String data, RSAPublicKey publicKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, publicKey);  
        //模长  
        int key_len = publicKey.getModulus().bitLength() / 8;  
        byte[] bytes = data.getBytes();  
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);    
        //如果密文长度大于模长则要分组解密  
        String ming = "";  
        byte[][] arrays = splitArray(bcd, key_len);  
        for(byte[] arr : arrays){  
            ming += new String(cipher.doFinal(arr));  
        }  
        return ming;  
    }
    /** 
     * 取得私钥 
     *  
     * @param keyMap 
     * @return 
     * @throws Exception 
     */  
    public static String getPrivateKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get("private");  
  
        return encryptBASE64(key.getEncoded());  
    }  
  
    /** 
     * 取得公钥 
     *  
     * @param keyMap 
     * @return 
     * @throws Exception 
     */  
    public static String getPublicKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get("public");  
        return encryptBASE64(key.getEncoded());  
    }  
    /** 
     * BASE64解密 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    @SuppressWarnings("restriction")
    public static byte[] decryptBASE64(String key) throws Exception {  
        return new sun.misc.BASE64Decoder().decodeBuffer(key);  
    }  
      
    /** 
     * BASE64加密 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    @SuppressWarnings("restriction")
	public static String encryptBASE64(byte[] key) throws Exception { 
        return new sun.misc.BASE64Encoder().encodeBuffer(key);  
    } 
    
    public static void main(String[] args) throws Exception {  
        // TODO Auto-generated method stub  
//        HashMap<String, Object> map = RSAUtils.getKeys();  
        //生成公钥和私钥  
//        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");  
//        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");  
//        //模  
//        String modulus = publicKey.getModulus().toString();  
//        //公钥指数  
//        String public_exponent = publicKey.getPublicExponent().toString();  
//        //私钥指数  
//        String private_exponent = privateKey.getPrivateExponent().toString();  
//        //明文  
        String ming = "1";  
//        //使用模和指数生成公钥和私钥  
//        RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);
//        RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, private_exponent);  
        
        
//        System.out.println("公钥");
//        System.out.println(getPublicKey(map));
//        System.out.println("私钥");
//        System.out.println(getPrivateKey(map));
//        publicKey=RSAUtils.getPublicKey(getPublicKey(map));
//        privateKey=RSAUtils.getPrivateKey(getPrivateKey(map));
        String publicK="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPoJt0wEm1RPrXtzhJfYEZ4vRSKrQHWLF+LObh"+
"A50r6+uE5gffeeaY6nNP8SU5Se/VFIHLs6Y/nA2XYNOklKlhIh0h8ZcOC/5ImBSTmGyEd8vBYAzl"+
"npaXHziT5/IDUkM1mWPPzyCPrFQlg+hMyAIYBHL6rdlIMzmCdTum85DieQIDAQAB";
        String privateK="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI+gm3TASbVE+te3OEl9gRni9FIq"+
"tAdYsX4s5uEDnSvr64TmB9955pjqc0/xJTlJ79UUgcuzpj+cDZdg06SUqWEiHSHxlw4L/kiYFJOY"+
"bIR3y8FgDOWelpcfOJPn8gNSQzWZY8/PII+sVCWD6EzIAhgEcvqt2UgzOYJ1O6bzkOJ5AgMBAAEC"+
"gYAsObQkmyEXJAppag1286JRKkU5F9UfffwJciIVn1tCLv7yiTJbadnLtEWVlBd2MIIdBpeA9ex3"+
"IcI9np8Myons3L/wXUN6aPfOQq58Qc0Xd3ZAjnkA2iVNPwpIEr5XLKIrX4aNDnRngBIxmknrLL6v"+
"ZUE+rS4s6CsUjQ9yG4d2EQJBAMZkEjqAApjcFWfgBgT6LxxDwWIbEBim9vwcdP+BecMAjU3tQa16"+
"JEZBONQnss3RaEJ0W8HUn2OLalu36HkfTt8CQQC5VYokv5FBEpSvdcWKzZrEclUUTXwsza+cCYJa"+
"EvE9v+RNd+r6nCUDAGHLxMtrEgkrE20pqReN3bukwRpwLHGnAkAy/Ib+x+Vi+bT6pEWHw/CVmAg8"+
"OW5Sl56EPqAHBnSPnDW0oFQvzGNENwDu7WDzqmzcH2FxmD56a9sixUoWzugLAkBQrNulg35HRT9T"+
"4YBMG5PzT5GZdOFI34BB/CGx8+zvZEiNMFYpIS87Tz9C5DdoNEGpbptmNyT5rDuyTBymF7KhAkAC"+
"Iqa/Bsi7PzGq2A68TObxoPw6PYp8d06n2vtfH4l92K1kWdRU4upg4WQ4uc57TfsJ6+UVOEXmOtHD"+
"QFK7Zcd9";
        
        RSAPublicKey publicKey=RSAUtils.getPublicKey(publicK);
        RSAPrivateKey privateKey=RSAUtils.getPrivateKey(privateK);
        //加密后的密文  
        String mi = RSAUtils.encryptByPublicKey(ming, publicKey); 
        System.out.println(mi.length());
        System.out.println(mi);
        //解密后的明文  
        ming = RSAUtils.decryptByPrivateKey("00E0F70A0994950A4921E2CD827DA02C6402ADB4A9F6A20CA1564AE205A26307E39450B589FE0BD825DBEE7E468B51944216D182F4F663D8755DAF40EC325E2BBEC160D2BC60D9A44FE0E74A46EAA0CDE29B41CECB36DBF470C0BCB11748A1DE638BEF681EB2D2C80B52378D31BEEFC52E6D97C305A50FD618B44FABF3CDDD1A", privateKey);  
        System.out.println(ming);  
    }  
}
