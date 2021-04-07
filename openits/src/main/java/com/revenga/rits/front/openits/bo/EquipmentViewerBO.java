package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.EquipViewer;
import com.revenga.rits.front.openitsdao.service.EquipViewerService;

@Service
public class EquipmentViewerBO extends GenericBO {
	
	@Autowired
	EquipViewerService equipViewerService;
	
	public List<EquipViewer> getEquipViewers() {
		
		return equipViewerService.getAll();
	}
	
	public EquipViewer getEquipViewer(String id) {
	
		return equipViewerService.getById(id);
	}

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
}
