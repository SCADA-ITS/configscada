package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
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
	
	public List<GridUI> getGridUIs() {
		
		List<GridUI> gridUIs = null;
		
		List<Grid> grids= gridService.getAll();
		
		if (CollectionUtils.isEmpty(grids)) {
			
			for (Grid grid : grids) {
				
				if (gridUIs == null) {
					
					gridUIs = new ArrayList<>();
				}
			
				gridUIs.add(gridAdapter.getData(grid, null));
			}
		}
		
		return gridUIs;
	}
	
	public GridUI getGridUI(Integer id, String params) {
		
		GridUI gridUI = null;
		
		Grid grid = gridService.getById(id);
		
		if (grid != null) {
			
			gridUI = gridAdapter.getData(grid, params);
		}
		
		return gridUI;
	}
}
