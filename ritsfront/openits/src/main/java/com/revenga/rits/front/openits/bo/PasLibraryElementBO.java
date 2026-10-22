package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openitsdao.model.PasLibraryElement;
import com.revenga.rits.front.openitsdao.service.PasLibraryElementService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasLibraryElementBO extends GenericBO {

	@Autowired
	PasLibraryElementService pasLibraryElementService;

	@Override
	public List<Map<String, Object>> getList() {

		List<Map<String, Object>> list = new ArrayList<>();

		List<PasLibraryElement> pasLibraryElements = pasLibraryElementService.getAll();

		if (!CollectionUtils.isEmpty(pasLibraryElements)) {

			for (PasLibraryElement pasLibraryElement : pasLibraryElements) {

				Map<String, Object> pasLibraryElementMap = ConversionUtil.objectToMap(pasLibraryElement);

				list.add(pasLibraryElementMap);
			}
		}

		return list;
	}

	@Override
	public Map<String, Object> getItem(String elementId) {

		PasLibraryElement pasLibraryElement = pasLibraryElementService.getByElementId(elementId);

		Map<String, Object> pasLibraryElementMap = ConversionUtil.objectToMap(pasLibraryElement);

		return pasLibraryElementMap;
	}

}
