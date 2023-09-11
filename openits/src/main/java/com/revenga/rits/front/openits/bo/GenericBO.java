package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.app.AppInfra;

@Service
public abstract class GenericBO {
	
	protected static int getPropertyInt(final String prop) {
		
		String value = AppInfra.getProperty(prop);
		
		if (!StringUtils.isEmpty(value)) {
			
			return Integer.parseInt(value);
		}
			
		return -1;
	}
	
	@Autowired
	protected VODataSourceAdapter voDataSourceAdapter;
	
	@Autowired
	protected VO2UIAdapter vo2uiAdapter;
	
	protected Integer dataSourceList;
	protected Integer dataSourceItem;
	
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> list = voDataSourceAdapter.getList(dataSourceList, null);

		return vo2uiAdapter.convert(dataSourceList, list);
	}
	
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> obj = voDataSourceAdapter.get(dataSourceItem,
				new Object[] { id });

		return vo2uiAdapter.convert(dataSourceItem, obj);
	}
	
	protected Boolean checkMapIsBoolean(Map<String, Object> map) {
	    
		Boolean res = false;

		if (!MapUtils.isEmpty(map) && map.size() == 1) {

			for (Map.Entry<String, Object> entry : map.entrySet()) {

				if (entry.getValue() instanceof Boolean) {

					res = (Boolean) entry.getValue();
				}
			}
		}

		return res;
	}
}
