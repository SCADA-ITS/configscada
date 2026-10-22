package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.Tree;
import com.revenga.rits.front.openitsdao.service.TreeService;
import com.revenga.rits.front.openits.adapter.impl.TreeAdapter;
import com.revenga.rits.front.openits.model.TreeUI;

@Service
public class TreeBO extends GenericBO {
	
	@Autowired
	TreeService treeService;
	
	@Autowired
	TreeAdapter treeAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<TreeUI> getTreeUIs() {
		
		List<TreeUI> treeUIs = null;
		
		List<Tree> trees= treeService.getAll();
		
		if (!CollectionUtils.isEmpty(trees)) {
			
			for (Tree tree : trees) {
				
				if (treeUIs == null) {
					
					treeUIs = new ArrayList<>();
				}
			
				treeUIs.add(treeAdapter.getData(tree));
			}
		}
		
		return treeUIs;
	}
	
	public TreeUI getTreeUI(Integer id) {
		
		TreeUI treeUI = null;
		
		Tree tree = treeService.getById(id);
		
		if (tree != null) {
			
			treeUI = treeAdapter.getData(tree);
		}
		
		return treeUI;
	}

}
