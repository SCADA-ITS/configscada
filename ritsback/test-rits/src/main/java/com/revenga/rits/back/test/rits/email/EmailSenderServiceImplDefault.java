package com.revenga.rits.back.test.rits.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class EmailSenderServiceImplDefault implements EmailSenderService {
	

	protected abstract void initConfig();
	protected abstract Properties getProperties();

	@Override
	public void sendEmail(String subject, String body, String addressTo) {
		
		try{
			initConfig();
		    
			Session session = Session.getInstance(getProperties(),
		        new javax.mail.Authenticator() {
		            protected PasswordAuthentication getPasswordAuthentication() {
		            	
		                return new PasswordAuthentication(getProperties().getProperty(EMAIL_PROPERTY_KEY_USER), getProperties().getProperty(EMAIL_PROPERTY_KEY_PASSWORD));
		            }
		        });
			
		    Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(getProperties().getProperty(EMAIL_PROPERTY_KEY_USER)));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addressTo));
	        message.setSubject(subject);
			message.setText(body);

	        Transport.send(message);

		}catch (MessagingException me){
	        
			me.printStackTrace();
		}
	}

}