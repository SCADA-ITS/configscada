package com.revenga.rits.back.io.controller.driver.mango;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MangoSubscriptorHttpHandler implements HttpHandler {

	MangoSubscriptor mangoSubscriptor;

	public MangoSubscriptorHttpHandler(MangoSubscriptor mangoSubscriptor) {
		super();

		this.mangoSubscriptor = mangoSubscriptor;
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

			if (mangoSubscriptor.getAllowedIpAddress().isEmpty()
					|| mangoSubscriptor.getAllowedIpAddress().contains(remoteAddress)) {

				mangoSubscriptor.receive(remoteAddress, headers, buf.toString());
			} else {

				log.warn("Mango publisher from " + remoteAddress + " is not allowed");
			}
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
