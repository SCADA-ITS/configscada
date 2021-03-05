import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

class AdapterTreeElementWithStates {

	org.apache.logging.log4j.Logger log;
	
	public AdapterTreeElementWithStates(org.apache.logging.log4j.Logger log) {
		
		this.log = log;
	}
	
	List<Map<String, Object>> toTreeData(VO2UIAdapter vo2uiAdapter, String fieldId, List<Map<String, Object>> entities) {
		
		List<Map<String, Object>> data = new ArrayList<>();
		
		try {
			
			if (!CollectionUtils.isEmpty(entities)) {
				
				for (Map<String, Object> entity : entities) {
					
					Map<String, Object> dataNode = new HashMap<>();

					
					data.add(dataNode);				
				}
			}
			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}
}
