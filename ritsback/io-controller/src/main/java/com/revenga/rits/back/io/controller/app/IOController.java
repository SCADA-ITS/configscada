package com.revenga.rits.back.io.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.revenga.rits.back.io.controller"})
public class IOController implements ApplicationRunner {

	@Autowired
	AppInfra appInfra;
	
	public static void main(String[] args) {

		ArgumentProcessor.getInstance().setArgs(args);
		SpringApplication.run(IOController.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
        appInfra.start("io-controller");
	}
}
