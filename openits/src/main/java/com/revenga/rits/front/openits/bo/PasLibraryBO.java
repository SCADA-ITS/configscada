package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openitsdao.model.PasLibrary;
import com.revenga.rits.front.openitsdao.service.PasLibraryService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasLibraryBO extends GenericBO {

	@Autowired
	PasLibraryService pasLibraryService;

	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> list = new ArrayList<>();

		List<PasLibrary> pasLibraries = pasLibraryService.getAll();

		if (!CollectionUtils.isEmpty(pasLibraries)) {

			for (PasLibrary pasLibrary : pasLibraries) {

				Map<String, Object> pasLibraryMap = ConversionUtil.objectToMap(pasLibrary);

				list.add(pasLibraryMap);
			}
		}

		return list;
	}

	public Map<String, Object> getItem(Integer id) {

		PasLibrary pasLibrary = pasLibraryService.getById(id);

		return ConversionUtil.objectToMap(pasLibrary);
	}

	@Override
	public Map<String, Object> getItem(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
