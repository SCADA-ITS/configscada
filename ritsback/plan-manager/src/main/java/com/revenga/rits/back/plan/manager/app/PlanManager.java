package com.revenga.rits.back.plan.manager.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.revenga.rits.back.manager.lib.app.AppInfra;
import com.revenga.rits.back.manager.lib.service.ResponseManager;
import com.revenga.rits.back.data.core.util.ArgumentProcessor;

@SpringBootApplication
@ComponentScan(basePackages= {"com.revenga.rits.back.plan.manager", "com.revenga.rits.back.manager.lib"})
public class PlanManager implements ApplicationRunner {

	@Autowired
	AppInfra appInfra;
	
	public static void main(String[] args) {
		
		ArgumentProcessor.getInstance().setArgs(args);
		SpringApplication.run(PlanManager.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		ResponseManager.getInstance();
		
        appInfra.start("plan-manager");
	}
}
