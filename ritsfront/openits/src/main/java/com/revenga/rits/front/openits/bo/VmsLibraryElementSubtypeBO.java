package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openitsdao.model.VmsLibraryElementSubtype;
import com.revenga.rits.front.openitsdao.service.VmsLibraryElementSubtypeService;

@Service
public class VmsLibraryElementSubtypeBO extends GenericBO {

	@Autowired
	VmsLibraryElementSubtypeService vmsLibraryElementSubtypeService;

	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> list = new ArrayList<>();

		List<VmsLibraryElementSubtype> vmsLibraryElementSubtypes = vmsLibraryElementSubtypeService.getAll();

		if (!CollectionUtils.isEmpty(vmsLibraryElementSubtypes)) {

			for (VmsLibraryElementSubtype vmsLibraryElementSubtype : vmsLibraryElementSubtypes) {

				Map<String, Object> vmsLibraryElementSubtypeMap = ConversionUtil.objectToMap(vmsLibraryElementSubtype);

				list.add(vmsLibraryElementSubtypeMap);
			}
		}

		return list;
	}

	@Override
	public Map<String, Object> getItem(String elementSubtypeId) {

		VmsLibraryElementSubtype vmsLibraryElementSubtype = vmsLibraryElementSubtypeService.getByElementSubtypeId(elementSubtypeId);

		Map<String, Object> vmsLibraryElementSubtypeMap = ConversionUtil.objectToMap(vmsLibraryElementSubtype);

		return vmsLibraryElementSubtypeMap;
	}

}
