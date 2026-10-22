package com.revenga.rits.back.io.controller.driver.mango;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class MangoSubscriptor {

	MangoDriver mangoDriver;
	MangoSubscriptorHttpHandler mangoSubscriptorHttpHandler;
	int responseCount = 0;
	
	private AtomicInteger count = new AtomicInteger();
	
	@Getter
	List<String> allowedIpAddress = new ArrayList<>();
	
	public MangoSubscriptor(MangoDriver mangoDriver) {
		
		this.mangoDriver = mangoDriver;
		
		init();
	}
	
	private void init() {
	
		try {
			
			Executor threadPoolExecutor = Executors.newFixedThreadPool(this.mangoDriver.getHttpPoolThreadSize());
			
			HttpServer server = HttpServer.create(new InetSocketAddress(new InetSocketAddress(0).getAddress(), mangoDriver.getSubscriptorPort()), this.mangoDriver.getHttpPoolThreadSize());
			mangoSubscriptorHttpHandler = new MangoSubscriptorHttpHandler(this);
			server.createContext(mangoDriver.getSubscriptorPath(), mangoSubscriptorHttpHandler);
			
			if (!StringUtils.isEmpty(mangoDriver.getSubscriptorIpAddressesAllowed())) {
			
				for (String ip : mangoDriver.getSubscriptorIpAddressesAllowed().split(";")) {
				
					if (InetAddressValidator.getInstance().isValid(ip)) {
				
						allowedIpAddress.add(ip);
					}
					else {
						
						log.warn("Wrong ip configured in subscriptor. " + ip);
					}
				}
			}
			else {
				
				log.warn("No filters configured for mango subscriptor.");
			}
			
			server.setExecutor(threadPoolExecutor);
			server.start();
			log.info("HTTP Server started on " + server.getAddress() + " / Thread pool size = " + this.mangoDriver.getHttpPoolThreadSize());
			log.info("Mapping endpoint => " + RequestMethod.POST.name() + " " + mangoDriver.getSubscriptorPath());
			
		} catch (IOException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public void receive(String remoteAddress, Headers headers, String body) {
		
		if(count.incrementAndGet() >= this.mangoDriver.getHttpPoolThreadSize()) {
			
			log.warn("Max number of thread in use => " + this.mangoDriver.getHttpPoolThreadSize());
		}
		
		mangoDriver.receive(remoteAddress, headers, body);
		count.decrementAndGet();
	}
}
