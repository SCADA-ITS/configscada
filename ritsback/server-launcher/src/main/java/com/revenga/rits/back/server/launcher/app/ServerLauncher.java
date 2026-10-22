package com.revenga.rits.back.server.launcher.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.revenga.rits.back.data.core.util.ArgumentProcessor;

@SpringBootApplication
@ComponentScan(basePackages = { "com.revenga.rits.back.server.launcher", "com.revenga.rits.back.manager.lib" })
public class ServerLauncher implements ApplicationRunner {

	@Autowired
	AppInfraServerLauncher appInfra;

	public static void main(String[] args) {

		ArgumentProcessor.getInstance().setArgs(args);
		SpringApplication.run(ServerLauncher.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		appInfra.start("server-launcher");

	}
}
