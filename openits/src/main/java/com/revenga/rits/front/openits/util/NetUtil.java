package com.revenga.rits.front.openits.util;

import java.net.InetSocketAddress;
import java.net.Socket;

import com.microsoft.sqlserver.jdbc.StringUtils;

public class NetUtil {
	
	private static final int CONNECT_TIMEOUT = 2000;

	private NetUtil() {
	}

	public static boolean serverListening(String host, int port) {

		boolean listening = false;
		
		try (Socket socket = new Socket()) {
			
			socket.connect(new InetSocketAddress(host, port), CONNECT_TIMEOUT);
			listening = true;

		} catch (Exception e) {
			
			// No listen
		}

		return listening;
	}

	public static boolean ldapListening(String url) {

		boolean listening = false;

		if (!StringUtils.isEmpty(url) && url.indexOf("ldap://") == 0) {

			String[] ipPort = url.substring(7).split(":");

			if (ipPort.length == 2) {

				listening = serverListening(ipPort[0], Integer.valueOf(ipPort[1]));
			}
		}

		return listening;
	}

}
