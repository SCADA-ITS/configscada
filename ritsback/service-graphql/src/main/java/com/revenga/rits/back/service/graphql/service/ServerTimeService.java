package com.revenga.rits.back.service.graphql.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

@Service
public class ServerTimeService{

	public Long getServerTime() {
	 
		return Instant.now().toEpochMilli();
		
	}

}
