package com.revenga.rits.back.incident.manager.app;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.revenga.rits.back.data.core.util.ArgumentProcessor;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentAlarmEntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.manager.lib.app.AppInfra;
import com.revenga.rits.back.manager.lib.service.ResponseManager;


@SpringBootApplication
@ComponentScan(basePackages = {"com.revenga.rits.back.incident.manager", "com.revenga.rits.back.manager.lib"})
public class IncidentManager implements ApplicationRunner {

	@Autowired
	AppInfra appInfra;
	
	public static void main(String[] args) {

		ArgumentProcessor.getInstance().setArgs(args);
		SpringApplication.run(IncidentManager.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
        
		appInfra.start("incident-manager", new Callable<Integer>() {
			public Integer call() {

				ResponseManager.getInstance();
				EntitiesManager.getInstance();
				IncidentAlarmEntitiesManager.getInstance();
				IncidentEntitiesManager.getInstance();
				return 0;
			}
		});
	}
}
