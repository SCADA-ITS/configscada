package com.revenga.rits.front.openits.adapter.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.revenga.rits.front.openits.model.GridUI;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.AdapterDataSource;
import com.revenga.rits.front.openitsdao.model.DataSource;
import com.revenga.rits.front.openitsdao.model.Grid;

import groovy.lang.GroovyObject;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class GridAdapter {
	
	private static final String METHOD_TO_GRID_DATA = "toGridData";
	
	private static final int ADAPTER_DATA_SOURCE_TYPE_GROOVY = 1;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Autowired
	VO2UIAdapter vo2uiAdapter;
		
	@Autowired
	Environment env;
	
	@Autowired
	GroovyManager groovyManager;
	
	public GridUI getData(Grid grid, String params) {
		
		GridUI gridUI = null;
		
		if (grid != null) {
			
			gridUI = adapter(grid, params);
		}
		
		return gridUI;
	}
	
	private GridUI adapter(Grid grid, String params) {
		
		GridUI gridUI = new GridUI();
		List<Object> paramsObj = null;
		
		try {
			
			gridUI.setGrid(grid);
			
			if (!StringUtils.isEmpty(params)) {
			
				String[] paramsArray = params.split(",");
				
				if (paramsArray.length > 0) {
					
					paramsObj = Arrays.asList(paramsArray).stream().map(e -> (Object) e)
							.collect(Collectors.toList());
				}
			}
			
			if (grid.getAdapter() != null) {
				
				AdapterDataSource adapterDataSource = grid.getAdapter().getAdapterDataSources().stream()
						.filter(e -> e.getAdapterDataSourceType().getId().equals(ADAPTER_DATA_SOURCE_TYPE_GROOVY))
						.findFirst().orElse(null);
				
				if (adapterDataSource != null) {
					
					DataSource dataSourceGraphQL = adapterDataSource.getDataSource();
					
					if (dataSourceGraphQL != null) {
						
						List<Map<String, Object>> items = voDataSourceAdapter.getList(dataSourceGraphQL.getId(),
								paramsObj != null ? paramsObj.toArray(Object[]::new) : null);
						
						if (!CollectionUtils.isEmpty(items)) {
														
							// Load from groovy file here
							String groovyFile = adapterDataSource.getValue();
							
							GroovyObject groovyObj = groovyManager.getGroovyObject(groovyFile, GroovyManager.GroovyType.ADAPTER);
							
							if (groovyObj != null) {
								
								@SuppressWarnings("unchecked")
								List<Map<String, Object>> gridData = (List<Map<String, Object>>) groovyObj.invokeMethod(
										METHOD_TO_GRID_DATA,
										new Object[] { dataSourceGraphQL.getId(), items });
								
								if (gridData != null) {
									
									gridUI.setData(gridData);		
								}
							}
							
							// Test groovy from class here
//							AdapterGridGeneric adapterGridGeneric = new AdapterGridGeneric(log);
//							
//							List<Map<String, Object>> gridData = adapterGridGeneric.toGridData(vo2uiAdapter,
//									dataSourceGraphQL.getFieldId(), items);
//							
//							if (gridData != null) {
//								
//								gridUI.setData(gridData);		
//							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
		
		return gridUI;
	}
}
