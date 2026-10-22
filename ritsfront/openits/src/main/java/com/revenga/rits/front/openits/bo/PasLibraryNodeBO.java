package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openitsdao.model.PasLibraryNode;
import com.revenga.rits.front.openitsdao.service.PasLibraryNodeService;
import com.revenga.rits.front.openitsdao.service.PasLibraryService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasLibraryNodeBO extends GenericBO {

	@Autowired
	PasLibraryService pasLibraryService;
	
	@Autowired
	PasLibraryNodeService pasLibraryNodeService;

	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> list = new ArrayList<>();

		List<PasLibraryNode> pasLibraryNodes = pasLibraryNodeService.getAll();

		if (!CollectionUtils.isEmpty(pasLibraryNodes)) {

			for (PasLibraryNode pasLibraryNode : pasLibraryNodes) {

				Map<String, Object> pasLibraryNodeMap = ConversionUtil.objectToMap(pasLibraryNode);

				list.add(pasLibraryNodeMap);
			}
		}

		return list;
	}

	public Map<String, Object> getItem(Integer id) {

		PasLibraryNode pasLibraryNode = pasLibraryNodeService.getById(id);

		return ConversionUtil.objectToMap(pasLibraryNode);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> save(Integer paslibraryId, Object paslibraryNode, String parentNodeIds) {

		PasLibraryNode pasLibraryNode = (PasLibraryNode) ConversionUtil.mapToObject(PasLibraryNode.class, (Map<String, Object>) paslibraryNode);
		
		pasLibraryNode.setPasLibrary(pasLibraryService.getById(paslibraryId));
		
		return ConversionUtil
				.objectToMap(pasLibraryNodeService.save(pasLibraryNode, Arrays.asList(parentNodeIds.split(",")).stream()
						.map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList())));
	}

	public Map<String, Object> delete(Integer id) {

		return ConversionUtil.objectToMap(pasLibraryNodeService.deleteById(id));
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
