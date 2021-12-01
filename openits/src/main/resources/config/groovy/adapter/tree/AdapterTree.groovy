package config.groovy.adapter.tree;

import java.util.List;
import java.util.Map;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.service.CacheManager;

import com.revenga.rits.front.openits.model.TreeDataUI;

public abstract class AdapterTree {
	
	public static final String TREE_FIELD_ID = "id";
	public static final String TREE_FIELD_VALUE = "value";
	public static final String TREE_FIELD_IMAGE = "image";
	public static final String TREE_FIELD_DATA = "data";
	
	public static final String TREE_PARAM_STATE_IMAGE = "state_image";

	protected CacheManager cacheManager;
	protected VO2UIAdapter vo2uiAdapter;
	protected org.apache.logging.log4j.Logger log;
	
	protected AdapterTree(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter, org.apache.logging.log4j.Logger log) {
		
		this.cacheManager = cacheManager;
		this.vo2uiAdapter = vo2uiAdapter;
		this.log = log;
	}
	
	public abstract List<TreeDataUI> toTreeData(Integer datasourceId, List<Map<String, Object>> entities);
}