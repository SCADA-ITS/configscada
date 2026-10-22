package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.app.AppInfra;
import com.revenga.rits.front.openits.service.user.UserManager;

@Service
public class ManagementAreaBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired
	UserManager userManager;
	
	private static final Boolean USERMGR_ENABLED = Boolean.valueOf(AppInfra.getProperty("usermgr.enabled"));
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> list = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			list = userManager.getManagementAreas();
		}
		else {
		
			list = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_MANAGEMENT_AREAS, null);
		}

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_MANAGEMENT_AREAS, list);
	}
}
