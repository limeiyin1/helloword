package com.test.www; 

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
/**
 * Static functions to simplifiy common {@link java.security.MessageDigest} tasks.  This
 * class is thread safe.
 * 
 * @author 99bill
 *
 */
public class MD5Utils {

    private MD5Utils() {
    }

    /**
     * Returns a MessageDigest for the given <code>algorithm</code>.
     * 
     * @param algorithm
     *            The MessageDigest algorithm name.
     * @return An MD5 digest instance.
     * @throws RuntimeException
     *             when a {@link java.security.NoSuchAlgorithmException} is
     *             caught
     */

    static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element
     * <code>byte[]</code>.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest
     */
    public static byte[] md5(byte[] data) {
        return getDigest().digest(data);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element
     * <code>byte[]</code>.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest
     */
    public static byte[] md5(String data) {
        return md5(data.getBytes());
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex
     * string.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(byte[] data) {
        return HexUtils.toHexString(md5(data));
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex
     * string.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(String data) {
        return HexUtils.toHexString(md5(data));
    }
    
    public static void main(String[] args){
    	
    	/*System.out.println(MD5Utils.md5Hex("hongshouzhi01@gc.com.cn##hongshouzhi@hsz"));
    	System.out.println(MD5Utils.md5Hex("hongshouzhi02@gc.com.cn##hongshouzhi@hsz"));
    	System.out.println(MD5Utils.md5Hex("hongshouzhi03@gc.com.cn##hongshouzhi@hsz"));
    	System.out.println(MD5Utils.md5Hex("hongshouzhi04@gc.com.cn##hongshouzhi@hsz"));
    	
    	for(int i = 0; i < 200; i++){
    		String password = "";
    		password += "INSERT INTO pad_list(";
    		password += "         room_id, status, pad_code, video_id)";
    		password += "VALUES ( 1, '1', 'CSpad0031" + (41 + i) + "', 1);";
    		
    		password += "INSERT INTO finger_apply_code(";
	    	password += "apply_code, status)";
	    	password += "VALUES ('";
	    	password += RandomUtils.randomString(18);
	    	password += "', '1');";
	    	System.out.println(password);
    	}
    	
    	String test1 = "padCode0002";
    	System.out.println(test1.hashCode());

    	test1 = "padCode0001";
    	System.out.println(test1.hashCode());*/
    		
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	Calendar expireTime = Calendar.getInstance();
    	expireTime.setTimeInMillis(1444735223269l);
    	//expireTime.add(Calendar.DAY_OF_YEAR, -1);
    	
    	System.out.println(sdf.format(expireTime.getTime()));
    	
    	Calendar recoveryTime = Calendar.getInstance();
    	recoveryTime.add(Calendar.DAY_OF_YEAR, -3);
    	
    	System.out.println(sdf.format(recoveryTime.getTime()));
    	
    	int expireDays = Math.round((System.currentTimeMillis() - expireTime.getTimeInMillis())/(24*60*60*1000));
    	
    	System.out.println(expireDays);


    	int recoveryDays = Math.round((expireTime.getTimeInMillis() - recoveryTime.getTimeInMillis())/(24*60*60*1000));
    	System.out.println(recoveryDays);
    }
}
