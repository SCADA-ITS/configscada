package com.revenga.rits.back.test.rits.email;

import java.util.Properties;

public class EmailSenderServiceImplRevenga extends EmailSenderServiceImplDefault {
	
	private Properties properties = new Properties();
	
	@Override
	protected void initConfig() {
		
		properties.put("mail.smtp.auth", true);
	    properties.put("mail.smtp.starttls.enable", true);
	    properties.put("mail.smtp.host", "mail.revenga.com");
	    properties.put("mail.smtp.port", "587");
	    properties.put("mail.smtp.ssl.trust", "*");
	    properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
	    properties.put(EMAIL_PROPERTY_KEY_USER, "user@revenga.com");
	    properties.put(EMAIL_PROPERTY_KEY_PASSWORD, "password");
	}
	
	@Override
	protected Properties getProperties() {
	
		return properties;
	}

}