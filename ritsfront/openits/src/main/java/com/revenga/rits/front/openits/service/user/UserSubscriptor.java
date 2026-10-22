package com.revenga.rits.front.openits.service.user;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserSubscriptor {

	UserServer userServer;
	UserSubscriptorHttpHandler userSubscriptorHttpHandler;
	int responseCount = 0;
	
	
	private AtomicInteger count = new AtomicInteger();
	
	public UserSubscriptor(UserServer userServer) {
		
		this.userServer = userServer;
		
		init();
	}
	
	private void init() {
	
		try {
			
			if (this.userServer == null || this.userServer.getOpenItsNode() == null) {
			
				throw new Exception("UserServer or OpenItsNode not configured");
			}
			
			OpenItsNode node = this.userServer.getOpenItsNode();
			
			String subscriptorPath = node.getSubscriptorPath();
			Integer subscriptorPort = node.getSubscriptorPort();
			
			Executor threadPoolExecutor = Executors.newFixedThreadPool(this.userServer.getHttpPoolThreadSize());
			
			HttpServer server = HttpServer.create(new InetSocketAddress(new InetSocketAddress(0).getAddress(), subscriptorPort), this.userServer.getHttpPoolThreadSize());
			userSubscriptorHttpHandler = new UserSubscriptorHttpHandler(this);

			server.createContext(subscriptorPath, userSubscriptorHttpHandler);
			server.setExecutor(threadPoolExecutor);
			server.start();
			
			log.info(node.getName() + " server started on " + server.getAddress() + " / Thread pool size = " + this.userServer.getHttpPoolThreadSize());
			log.info(node.getName() + " server mapping endpoint => " + RequestMethod.POST.name() + " " + subscriptorPath);
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void receive(String remoteAddress, Headers headers, String body) {
		
		if(count.incrementAndGet() >= this.userServer.getHttpPoolThreadSize()) {
			
			log.warn("Max number of thread in use => " + this.userServer.getHttpPoolThreadSize());
		}
		
		userServer.receive(remoteAddress, headers, body);
		count.decrementAndGet();
	}
}
