package com.revenga.rits.front.openits.service.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UserSubscriptorHttpHandler implements HttpHandler {

	UserSubscriptor userSubscriptor;

	public UserSubscriptorHttpHandler(UserSubscriptor userSubscriptor) {
		super();

		this.userSubscriptor = userSubscriptor;
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
	
		try (InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8")) {

			BufferedReader br = new BufferedReader(isr);

			int character;
			StringBuilder buf = new StringBuilder();

			while ((character = br.read()) != -1) {

				buf.append((char) character);
			}

			br.close();

			Headers headers = httpExchange.getRequestHeaders();

			// encode HTML content
//			String htmlResponse = "";

			httpExchange.sendResponseHeaders(HttpServletResponse.SC_OK, -1);

			String remoteAddress = getRemoteAddress(httpExchange);
			
			userSubscriptor.receive(remoteAddress, headers, buf.toString());
		}
	}

	private String getRemoteAddress(HttpExchange httpExchange) {

		String address = null;
		String fwdAddr = httpExchange.getRequestHeaders().getFirst("X-Forwarded-For");

		if (!StringUtils.isEmpty(fwdAddr)) {

			address = fwdAddr;
		}

		// not a proxy, return natural address.
		if (StringUtils.isEmpty(address)) {

			InetAddress addr = httpExchange.getRemoteAddress().getAddress();
			address = (addr == null ? httpExchange.getRemoteAddress().getHostName() : addr.getHostAddress());
		}

		return address;
	}
}
