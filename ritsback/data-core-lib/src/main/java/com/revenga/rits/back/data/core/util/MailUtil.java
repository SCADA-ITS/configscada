package com.revenga.rits.back.data.core.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MailUtil {

	public static final String MAIL_SMTP_USER = "mail.smtp.user";
	public static final String MAIL_SMTP_PASSWORD = "mail.smtp.password";

	public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	public static final String MAIL_SMTP_HOST = "mail.smtp.host";
	public static final String MAIL_SMTP_PORT = "mail.smtp.port";
	public static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
	public static final String MAIL_SMTP_SSL_PROTOCOLS = "mail.smtp.ssl.protocols";

	private static final Map<Properties, Session> mapSession = Collections.synchronizedMap(new HashMap<>());

	private MailUtil() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static boolean send(Properties props, String addressTO, String addressCC, String addressBCC, String subject,
			String body) {

		boolean result = false;

		try {

			if (props != null && !StringUtils.isEmpty(addressTO)) {

				/*
				 * Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				 * 
				 * @Override protected PasswordAuthentication getPasswordAuthentication() {
				 * 
				 * return new PasswordAuthentication(props.getProperty(MAIL_SMTP_USER),
				 * props.getProperty(MAIL_SMTP_PASSWORD)); } });
				 */

				Session session = mapSession.computeIfAbsent(props,
						k -> Session.getInstance(props, new javax.mail.Authenticator() {
							@Override
							protected PasswordAuthentication getPasswordAuthentication() {

								return new PasswordAuthentication(props.getProperty(MAIL_SMTP_USER),
										props.getProperty(MAIL_SMTP_PASSWORD));
							}
						}));

				log.info("mapSession size:" + mapSession.size());

				MimeMessage message = new MimeMessage(session);

				message.setFrom(new InternetAddress(props.getProperty(MAIL_SMTP_USER)));
				message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(addressTO));

				if (!StringUtils.isEmpty(addressCC)) {

					message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(addressCC));
				}

				if (!StringUtils.isEmpty(addressBCC)) {

					message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(addressBCC));
				}

				message.setSubject(subject != null ? subject : "");
				message.setContent(body != null ? body : "", "text/html; charset=utf-8");

				Transport transport = session.getTransport("smtp");

				transport.connect(props.getProperty(MAIL_SMTP_HOST), props.getProperty(MAIL_SMTP_USER),
						props.getProperty(MAIL_SMTP_PASSWORD));
				transport.sendMessage(message, message.getAllRecipients());

				traceSend(Level.DEBUG, props, addressTO, addressCC, addressBCC, subject, body);

				transport.close();
			}
		} catch (MessagingException e) {

			traceSend(Level.ERROR, props, addressTO, addressCC, addressBCC, subject, body);

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return result;

	}

	private static void traceSend(Level level, Properties props, String addressTo, String addressCC, String addressBCC,
			String subject, String body) {

		log.log(level, "------------- SEND MAIL -------------");
		log.log(level, "");

		for (String key : props.stringPropertyNames()) {

			log.log(level, key + ": " + props.getProperty(key));
		}

		log.log(level, "");

		log.log(level, "<<< MESSAGE >>>");
		log.log(level, "    addressTo: " + addressTo);
		log.log(level, "    addressCC: " + addressCC);
		log.log(level, "    addressBCC: " + addressBCC);
		log.log(level, "    subject: " + subject);
		log.log(level, "    body: " + body);

		log.log(level, "");
		log.log(level, "-------------------------------------");
	}
}
