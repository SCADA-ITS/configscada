package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public abstract class GenericBO {
	
	public abstract List<Map<String, Object>> getList();
	
	public abstract Map<String, Object> getItem(String id);
	
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
