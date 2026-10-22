package com.revenga.rits.back.test.rits.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/applicationContext-test.xml"})
public class EmailSenderTest {
	
	@Autowired
	EmailSenderService emailSender;

	@Test
	public void Test1() throws InterruptedException {

	    String addresseTo =  "francisco.molero@revenga.com";
	    String subject = "Pruebas Exchange";
	    String body = "Día 1095 cuaderno de bitacora...";
	    
	    emailSender.sendEmail(subject, body, addresseTo);
	}
}
