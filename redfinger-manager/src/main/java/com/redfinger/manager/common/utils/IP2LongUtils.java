package com.redfinger.manager.common.utils;

public class IP2LongUtils {
	public static Long ipToNumber(String ip) {      
        Long ips = 0L;   
        String[] numbers = ip.split("\\.");  
        for (int i = 0; i < 4; ++i) {  
            ips = ips << 8 | Integer.parseInt(numbers[i]);  
        }  
        return ips;     
    }      
        
	public static String numberToIp(Long number) {      
        String ip = "";  
        for (int i = 3; i >= 0; i--) {  
            ip  += String.valueOf((number & 0xff));  
            if(i != 0){  
                ip += ".";  
            }  
            number = number >> 8;  
        }  
        return ip;        
    }  
}
