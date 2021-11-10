package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public abstract class GenericBO {
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Autowired
	VO2UIAdapter vo2uiAdapter;
	
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
	
	public Boolean checkMapIsBoolean(Map<String, Object> map) {
	    
	    Boolean res = false;
	        
	    if (map.size() == 1) {
	      
	      for (Map.Entry<String, Object> entry : map.entrySet()) {
	        
	        if (entry.getValue() instanceof Boolean) {
	          
	          res = (Boolean) entry.getValue();
	        }
	      }
	    }
	    
	    return res;
	  }

}
