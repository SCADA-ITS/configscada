package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openitsdao.model.VmsLibraryNode;
import com.revenga.rits.front.openitsdao.service.VmsLibraryNodeService;
import com.revenga.rits.front.openitsdao.service.VmsLibraryService;

@Service
public class VmsLibraryNodeBO extends GenericBO {

	@Autowired
	VmsLibraryService vmsLibraryService;
	
	@Autowired
	VmsLibraryNodeService vmsLibraryNodeService;

	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> list = new ArrayList<>();

		List<VmsLibraryNode> vmsLibraryNodes = vmsLibraryNodeService.getAll();

		if (!CollectionUtils.isEmpty(vmsLibraryNodes)) {

			for (VmsLibraryNode vmsLibraryNode : vmsLibraryNodes) {

				Map<String, Object> vmsLibraryNodeMap = ConversionUtil.objectToMap(vmsLibraryNode);

				list.add(vmsLibraryNodeMap);
			}
		}

		return list;
	}

	public Map<String, Object> getItem(Integer id) {

		VmsLibraryNode vmsLibraryNode = vmsLibraryNodeService.getById(id);

		return ConversionUtil.objectToMap(vmsLibraryNode);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> save(Integer vmslibraryId, Object vmslibraryNode, String parentNodeIds) {

		VmsLibraryNode vmsLibraryNode = (VmsLibraryNode) ConversionUtil.mapToObject(VmsLibraryNode.class, (Map<String, Object>) vmslibraryNode);
		
		vmsLibraryNode.setVmsLibrary(vmsLibraryService.getById(vmslibraryId));
		
		return ConversionUtil
				.objectToMap(vmsLibraryNodeService.save(vmsLibraryNode, Arrays.asList(parentNodeIds.split(",")).stream()
						.map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList())));
	}

	public Map<String, Object> delete(Integer id) {

		return ConversionUtil.objectToMap(vmsLibraryNodeService.deleteById(id));
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
