package com.revenga.rits.back.ep.pub.stomp.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.revenga.rits.back.data.core.util.ArgumentProcessor;

@SpringBootApplication
@ComponentScan(basePackages= {"com.revenga.rits.back.ep.pub.stomp"})
public class PubStompApplication implements ApplicationRunner {

	@Autowired
	AppInfra appInfra;
	
	public static void main(String[] args) {
		
		ArgumentProcessor.getInstance().setArgs(args);
		SpringApplication.run(PubStompApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		        
        appInfra.start();
	}
}
