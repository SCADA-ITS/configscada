package com.revenga.rits.front.openits.adapter.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.revenga.rits.front.openits.model.TreeDataUI;
import com.revenga.rits.front.openits.model.TreeUI;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.AdapterDataSource;
import com.revenga.rits.front.openitsdao.model.DataSource;
import com.revenga.rits.front.openitsdao.model.Tree;

import groovy.lang.GroovyObject;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class TreeAdapter {
	
	private static final String METHOD_TO_TREE_DATA = "toTreeData";
	
	private static final int ADAPTER_DATA_SOURCE_TYPE_GROOVY = 1;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Autowired
	VO2UIAdapter vo2uiAdapter;
		
	@Autowired
	Environment env;
	
	
	@PostConstruct
	private void initialize() {
		
	}
	
	
	public TreeUI getData(Tree tree) {
		
		TreeUI treeUI = null;
		
		if (tree != null) {
			
			treeUI = adapter(tree);
		}
		
		return treeUI;
	}
	
	private TreeUI adapter(Tree tree) {
		
		TreeUI treeUI = new TreeUI();
		
		try {
			
			treeUI.setTree(tree);
			
			if (tree.getAdapter() != null) {
				
				AdapterDataSource adapterDataSource = tree.getAdapter().getAdapterDataSources().stream()
						.filter(e -> e.getAdapterDataSourceType().getId().equals(ADAPTER_DATA_SOURCE_TYPE_GROOVY))
						.findFirst().orElse(null);
				
				if (adapterDataSource != null) {
					
					DataSource dataSourceGraphQL = adapterDataSource.getDataSource();
					
					if (dataSourceGraphQL != null) {
						
						List<Map<String, Object>> items = voDataSourceAdapter.getList(dataSourceGraphQL.getId(), null);
						
						if (!CollectionUtils.isEmpty(items)) {
														
							String groovyFile = adapterDataSource.getValue();
							
							GroovyObject groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);
							
							if (groovyObj != null) {
								
								@SuppressWarnings("unchecked")
								List<TreeDataUI> treeData = (List<TreeDataUI>) groovyObj.invokeMethod(
										METHOD_TO_TREE_DATA,
										new Object[] { tree, vo2uiAdapter, dataSourceGraphQL.getFieldId(), items });
								
								if (treeData != null) {
									
									treeUI.setData(treeData);		
								}
							}
							
//							treeUI.setData((new AdapterTreeElementWithStates(log)).toTreeData(vo2uiAdapter, dataSourceGraphQL.getFieldId(), items));
						}
					}
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
		
		return treeUI;
	}
}
