package com.test.www;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class RSATest {



		@Test
		public void orQuery() throws Throwable {
			

	            // TODO Auto-generated method stub  
	            HashMap<String, Object> map = RSAUtils.getKeys();  
	            //生成公钥和私钥  
	            RSAPublicKey publicKey = (RSAPublicKey) map.get("public");  
	            RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");  
	       
	            //模  
	            String modulus = publicKey.getModulus().toString();  
	            System.out.println("modulus"+modulus);
	            //公钥指数  
	            String public_exponent = publicKey.getPublicExponent().toString(); 
	            System.out.println("public_exponent"+public_exponent);
	            //私钥指数  
	            String private_exponent = privateKey.getPrivateExponent().toString();  
	            //明文  
	            String ming = "123456789";  
	            
	            //使用模和指数生成公钥和私钥  
	            RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);  
	            RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, private_exponent);  
	            //加密后的密文  
	            String mi = RSAUtils.encryptByPublicKey(ming, pubKey);  
	            System.err.println(mi);  
	            //解密后的明文  
	            ming = RSAUtils.decryptByPrivateKey(mi, priKey);  
	            System.err.println(ming);  
	      
	            
	             mi = RSAUtils.encryptByPrivateKey(ming, privateKey);
	            System.err.println(mi);  
	            //解密后的明文  
	            ming = RSAUtils.decryptByPublicKey(mi, publicKey);  
	            System.err.println(ming);  
		}
		
		
	
}
