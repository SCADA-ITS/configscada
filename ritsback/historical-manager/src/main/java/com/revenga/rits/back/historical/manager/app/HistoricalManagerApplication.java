package com.revenga.rits.back.historical.manager.app;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.revenga.rits.back.data.core.util.ArgumentProcessor;
import com.revenga.rits.back.historical.manager.executor.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.app.AppInfra;

@SpringBootApplication
@ComponentScan(basePackages= {"com.revenga.rits.back.historical.manager", "com.revenga.rits.back.manager.lib"})
public class HistoricalManagerApplication implements ApplicationRunner {

	@Autowired
	AppInfra appInfra;
	
	public static void main(String[] args) {

		ArgumentProcessor.getInstance().setArgs(args);
		SpringApplication.run(HistoricalManagerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
        appInfra.start("historical-manager", new Callable<Integer>() {
			public Integer call() {
				
				EntitiesManager.getInstance();
				return 0;
			}
		});
	}
}
