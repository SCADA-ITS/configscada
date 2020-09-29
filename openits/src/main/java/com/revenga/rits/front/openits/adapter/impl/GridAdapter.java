package com.revenga.rits.front.openits.adapter.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.revenga.rits.front.openitsdao.model.Grid;
import com.revenga.rits.front.openits.model.GridUI;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class GridAdapter {
	
	private final static String GRID_ADAPTER_ELEMENT = "grid.adapter.element";
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Autowired
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	ElementAdapter elementAdapter;
	
	@Autowired
	Environment env;
	
	private Integer gridAdapterElement;
	
	@PostConstruct
	private void initialize() {
		
		this.gridAdapterElement = Integer.parseInt(env.getProperty(GRID_ADAPTER_ELEMENT));
	}
	
	
	public GridUI getData(Grid grid, String params) {
		
		GridUI gridUI = null;
		
		if (grid != null && grid.getAdapter() != null) {
			
			if (grid.getAdapter().getId().equals(this.gridAdapterElement)) {
				
				gridUI = adapterElement(grid, params);
			}
		}
		
		return gridUI;
	}
	
	private GridUI adapterElement(Grid grid, String params) {
		
		GridUI gridUI = new GridUI();
		
		try {
			
			gridUI.setGrid(grid);
			
			if (!StringUtils.isEmpty(params)) {
			
				String[] paramsArray = params.split(",");
				
				if (paramsArray.length > 0) {
					
					List<Object> paramsObj = Arrays.asList(paramsArray).stream().map(e -> (Object) e)
							.collect(Collectors.toList());
					
					List<Map<String, Object>> elements = voDataSourceAdapter
							.getList(grid.getAdapter().getDataSource().getId(), paramsObj.toArray(Object[]::new));
					
					if (!CollectionUtils.isEmpty(elements)) {
										
//						elementsUI = basicElementsUI.stream().map(beUI -> (ElementUI) beUI).collect(Collectors.toList());
						
						gridUI.setData(elementAdapter.toGridData(elements));
					}
				}
			}
			
		} catch (IllegalAccessException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
		
		return gridUI;
	}
}
