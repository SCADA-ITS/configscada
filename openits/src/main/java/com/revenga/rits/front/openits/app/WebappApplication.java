package com.revenga.rits.front.openits.app;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@ComponentScan(basePackages= {"com.revenga.rits.front.openits", "com.revenga.rits.front.openitsdao"})
@EntityScan("com.revenga.rits.front.openitsdao.model")
@EnableJpaRepositories("com.revenga.rits.front.openitsdao.repository")
@EnableJpaAuditing
public class WebappApplication implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>, ApplicationRunner{

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
		
		
	}
	
	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/errors/404.html"));
        factory.addErrorPages(new ErrorPage(HttpStatus.SERVICE_UNAVAILABLE, "/errors/503.html"));
        factory.addErrorPages(new ErrorPage("/errors/maintenance.html"));
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
	}

}
