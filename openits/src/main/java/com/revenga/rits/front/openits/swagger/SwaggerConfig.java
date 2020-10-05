package com.revenga.rits.front.openits.swagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT = new Contact("Revenga Ingenieros, S.A.", "https://www.gruporevenga.com",
			"aborrajo@revenga.com");
	
	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
			Arrays.asList("application/json"));

	@Bean
	public Docket api() {
		
		final ApiInfo defaultApiInfo = new ApiInfo("Open-ITS REST v1 API", "", "1.0.0",
				"urn:tos", DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",
				new ArrayList<>());
		
		return new Docket(DocumentationType.SWAGGER_2)
					.apiInfo(defaultApiInfo)
					.produces(DEFAULT_PRODUCES_AND_CONSUMES)
					.consumes(DEFAULT_PRODUCES_AND_CONSUMES)
					.select()
					.paths(Predicates.not(PathSelectors.regex("/error")))
					.build();
	}
}
