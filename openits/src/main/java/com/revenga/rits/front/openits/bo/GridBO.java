package com.revenga.rits.front.openits.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.Grid;
import com.revenga.rits.front.openitsdao.service.GridService;
import com.revenga.rits.front.openits.adapter.impl.GridAdapter;
import com.revenga.rits.front.openits.model.GridUI;

@Service
public class GridBO {
	
	@Autowired
	GridService gridService;
	
	@Autowired
	GridAdapter gridAdapter;
	
	public GridUI getGridUI(Integer id, String params) {
		
		GridUI gridUI = null;
		
		Grid grid = gridService.getById(id);
		
		if (grid != null) {
			
			gridUI = gridAdapter.getData(grid, params);
		}
		
		return gridUI;
	}
}
