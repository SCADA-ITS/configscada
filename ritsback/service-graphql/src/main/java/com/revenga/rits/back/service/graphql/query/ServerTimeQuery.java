package com.revenga.rits.back.service.graphql.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.service.graphql.service.ServerTimeService;

@Component
public class ServerTimeQuery implements GraphQLQueryResolver {

	@Autowired
	private ServerTimeService serverTimeService;
	
	public Long getServerTime() {
		serverTimeService = new ServerTimeService();
		return serverTimeService.getServerTime();
		
	}
	

}
