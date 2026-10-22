package com.revenga.rits.back.virtual.equipment.manager.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.revenga.rits.back.data.core.util.ArgumentProcessor;
import com.revenga.rits.back.manager.lib.app.AppInfra;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.TubeEntityManager;

@SpringBootApplication
@ComponentScan(basePackages = {"com.revenga.rits.back.virtual.equipment.manager", "com.revenga.rits.back.manager.lib"})
public class VitualEquipmentManager implements ApplicationRunner {

	@Autowired
	AppInfra appInfra;
	
	public static void main(String[] args) {

		ArgumentProcessor.getInstance().setArgs(args);
		SpringApplication.run(VitualEquipmentManager.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		TubeEntityManager.getInstance();
		
		appInfra.start("virtual-equipment-manager");
	}
}
