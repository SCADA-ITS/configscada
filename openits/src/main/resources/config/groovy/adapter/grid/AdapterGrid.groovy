package config.groovy.adapter.grid;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.service.CacheManager;

public abstract class AdapterGrid {
	
	protected CacheManager cacheManager;
	protected VO2UIAdapter vo2uiAdapter;
	protected org.apache.logging.log4j.Logger log;
	
	public AdapterGrid(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter, org.apache.logging.log4j.Logger log) {
		
		this.cacheManager = cacheManager;
		this.vo2uiAdapter = vo2uiAdapter;
		this.log = log;
	}
	
	public abstract List<Map<String, Object>> toGridData(String fieldId, List<Map<String, Object>> entities);
}
