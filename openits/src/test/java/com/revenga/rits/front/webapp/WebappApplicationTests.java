package com.revenga.rits.front.webapp;

import org.junit.Test;

import com.revenga.rits.front.openits.util.AES;

public class WebappApplicationTests {
	
	@Test
	public void test() {
		
		final String secretKey = "ssshhhhhhhhhhh!!!!";

		String originalString = "howtodoinjava.com";
		String encryptedString = AES.encrypt(originalString, secretKey) ;
		String decryptedString = AES.decrypt(encryptedString, secretKey) ;

		System.out.println(originalString);
		System.out.println(encryptedString);
		System.out.println(decryptedString);
	}
}
