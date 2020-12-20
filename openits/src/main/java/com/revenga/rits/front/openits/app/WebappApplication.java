package com.revenga.rits.front.openits.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.revenga.rits.front.openits", "com.revenga.rits.front.openitsdao" })
@EntityScan("com.revenga.rits.front.openitsdao.model")
@EnableJpaRepositories("com.revenga.rits.front.openitsdao.repository")
@EnableJpaAuditing
public class WebappApplication implements ApplicationRunner {

	@Autowired
	AppInfra appInfra;

	public static void main(String[] args) {

		SpringApplication.run(WebappApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		appInfra.start();
	}

}
