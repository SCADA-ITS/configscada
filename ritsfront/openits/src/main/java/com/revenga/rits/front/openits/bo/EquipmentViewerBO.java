package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.EquipViewer;
import com.revenga.rits.front.openitsdao.service.EquipViewerService;

@Service
public class EquipmentViewerBO extends GenericBO {
	
	@Autowired
	EquipViewerService equipViewerService;
	
	@Autowired
	ElementBO elementBO;
	
	static final String ELEMENT_FIELD_SUBTYPE = "elementSubtype";
	static final String ELEMENT_FIELD_SUBTYPE_ID = "id";
	static final String ELEMENT_FIELD_TYPE = "elementType";
	static final String ELEMENT_FIELD_TYPE_ID = "id";
	
	public List<EquipViewer> getEquipViewers() {
		
		return equipViewerService.getAll();
	}
	
	@SuppressWarnings("unchecked")
	public EquipViewer getEquipViewer(String elementId) {

		EquipViewer equipViewer = null;
		Map<String, Object> element;
		
		element = elementBO.getItem(elementId);
		
		if (!MapUtils.isEmpty(element)) {
		
			equipViewer = equipViewerService.getById(elementId);
			
			if (equipViewer == null) {
				
				Object objSubtype = element.get(ELEMENT_FIELD_SUBTYPE);
				
				if (objSubtype instanceof Map) {
					
					Map<String, Object> subtype = (Map<String, Object>) objSubtype;
					
					equipViewer = equipViewerService.getById((String) subtype.get(ELEMENT_FIELD_SUBTYPE_ID));
				}
			}
			
			if (equipViewer == null) {
				
				Object objType = element.get(ELEMENT_FIELD_TYPE);
				
				if (objType instanceof Map) {
					
					Map<String, Object> subtype = (Map<String, Object>) objType;
					
					equipViewer = equipViewerService.getById((String) subtype.get(ELEMENT_FIELD_TYPE_ID));
				}
			}
		}
		
		return equipViewer;
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
