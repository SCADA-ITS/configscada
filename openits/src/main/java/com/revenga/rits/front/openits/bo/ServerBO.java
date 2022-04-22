package com.revenga.rits.front.openits.bo;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.model.ServerTimeUI;

@Service
public class ServerBO extends GenericBO {
	
	private static final String FIELD_GET_SERVER_TIME = "getServerTime";
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
		
	public List<Map<String, Object>> getProcessesInfo() {

		List<Map<String, Object>> processesInfo = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_PROCESSES_INFO, null);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_PROCESSES_INFO, processesInfo);
	}
	
	public ServerTimeUI getServerTime() {

		ServerTimeUI serverTimeUI = new ServerTimeUI();
		
		Instant timeRef = Instant.now();
		
		Map<String, Object> serverTime = voDataSourceAdapter
				.get(VODataSourceAdapter.DATA_SOURCE_GET_SERVER_TIME, null);
		
		Map<String, Object> serverTimeObj = vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_SERVER_TIME, serverTime);
		
		Object objServerEpochTime = serverTimeObj.get(FIELD_GET_SERVER_TIME);
				
		if (objServerEpochTime instanceof Long) {
			
			long serverEpochTime = (long) objServerEpochTime;
			
			long timeLatency = Duration.between(timeRef, Instant.now()).toMillis();
			
			serverTimeUI = new ServerTimeUI(serverEpochTime + timeLatency);
			
			serverTimeUI.setServerTime(true);
		}
		
		return serverTimeUI;
	}
}
