package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openitsdao.model.VmsLibrary;
import com.revenga.rits.front.openitsdao.service.VmsLibraryService;

@Service
public class VmsLibraryBO extends GenericBO {

	@Autowired
	VmsLibraryService vmsLibraryService;

	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> list = new ArrayList<>();

		List<VmsLibrary> vmsLibraries = vmsLibraryService.getAll();

		if (!CollectionUtils.isEmpty(vmsLibraries)) {

			for (VmsLibrary vmsLibrary : vmsLibraries) {

				Map<String, Object> vmsLibraryMap = ConversionUtil.objectToMap(vmsLibrary);

				list.add(vmsLibraryMap);
			}
		}

		return list;
	}

	public Map<String, Object> getItem(Integer id) {

		VmsLibrary vmsLibrary = vmsLibraryService.getById(id);

		return ConversionUtil.objectToMap(vmsLibrary);
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
