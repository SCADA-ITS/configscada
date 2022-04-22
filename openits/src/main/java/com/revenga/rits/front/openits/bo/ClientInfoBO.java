package com.revenga.rits.front.openits.bo;

import java.lang.management.ManagementFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.app.AppInfra;
import com.revenga.rits.front.openits.model.ClientInfoUI;

@Service
public class ClientInfoBO {
	
	 @Autowired
	 AppInfra appInfra;

	public ClientInfoUI getInfo() {
		
		ClientInfoUI clientInfo = new ClientInfoUI();
		
		clientInfo.setVersion(appInfra.getVersion());
		clientInfo.setUpTime(ManagementFactory.getRuntimeMXBean().getUptime());
		
		return clientInfo;
	}
}
