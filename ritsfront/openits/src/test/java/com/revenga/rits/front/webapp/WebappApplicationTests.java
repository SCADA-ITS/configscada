package com.revenga.rits.front.webapp;

import org.junit.Test;

import com.revenga.rits.lib.encrypt.util.AES;

public class WebappApplicationTests {
	
	@Test
	public void test() {
		
		try {
			String originalString = "hola que tal";
			String encryptedString = AES.encrypt(originalString) ;
			String decryptedString = AES.decrypt(encryptedString) ;

			System.out.println(originalString);
			System.out.println(encryptedString);
			System.out.println(decryptedString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
