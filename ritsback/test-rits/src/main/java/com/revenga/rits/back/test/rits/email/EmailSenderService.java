package com.revenga.rits.back.test.rits.email;

public interface EmailSenderService {
	
	public String EMAIL_PROPERTY_KEY_USER = "mail.smtp.user";
	public String EMAIL_PROPERTY_KEY_PASSWORD = "mail.smtp.password";
	
	public void sendEmail(String address, String subject, String message);	

}