package com.revenga.rits.back.service.graphql;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.revenga.rits.back.data.core.util.ArgumentProcessor;
import com.revenga.rits.back.manager.lib.app.AppInfra;
import com.revenga.rits.back.manager.lib.service.ResponseManager;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

@SpringBootApplication
@ComponentScan(basePackages = {"com.revenga.rits.back.service.graphql", "com.revenga.rits.back.manager.lib"})
public class ServiceGraphqlApplication implements ApplicationRunner{

	@Autowired
	AppInfra appInfra;
	
	@Value("${graphql.url:graphql}")
	private String graphqlurl;
	
	public static void main(String[] args) {
		ArgumentProcessor.getInstance().setArgs(args);
		SpringApplication.run(ServiceGraphqlApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/" + graphqlurl + "/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(0);
		
		return bean;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		appInfra.start("service-graphql", new Callable<Integer>() {
			public Integer call() {

				ResponseManager.getInstance();
				EntitiesManager.getInstance();
				
				return 0;
			}
		});
	}
}
