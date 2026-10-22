package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openitsdao.model.SelectorTree;
import com.revenga.rits.front.openitsdao.service.SelectorTreeService;

@Service
public class SelectorTreeBO extends GenericBO {
	
	@Autowired
	SelectorTreeService selectorTreeService;

	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		List<SelectorTree> selectorTrees = selectorTreeService.getAll();
		
		if (!CollectionUtils.isEmpty(selectorTrees)) {
			
			for (SelectorTree selectorTree : selectorTrees) {
				
				Map<String, Object> selectorTreeMap = ConversionUtil.objectToMap(selectorTree);
				
				list.add(selectorTreeMap);
			}
		}
		
		return list;
	}

	
	public Map<String, Object> getItem(Integer id) {
		
		SelectorTree selectorTree = selectorTreeService.getById(id);
		
		Map<String, Object> selectorTreeMap = ConversionUtil.objectToMap(selectorTree);
	
		return selectorTreeMap;
	}


	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}


}
