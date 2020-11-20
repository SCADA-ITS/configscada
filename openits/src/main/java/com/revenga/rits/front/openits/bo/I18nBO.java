package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class I18nBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;

	@Autowired
	VO2UIAdapter vo2uiAdapter;

	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> locales = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetLocales(),
				null);

		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetLocales()).getFieldId(), locales);
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> locale = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetLocale(),
				new Object[] { id });

		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetLocale()).getFieldId(), locale);
	}

	public List<Map<String, Object>> getTranslationsByLocale(String localeId) {

		List<Map<String, Object>> translations = voDataSourceAdapter
				.getList(voDataSourceAdapter.getDataSourceGetTranslationsByLocale(), new Object[] { localeId });

		return vo2uiAdapter.convert(
				voDataSourceAdapter.getDataSourceService()
						.getById(voDataSourceAdapter.getDataSourceGetTranslationsByLocale()).getFieldId(),
				translations);
	}

	public List<Map<String, Object>> getTranslationsByLocaleAndModule(String localeId, Integer moduleId) {

		List<Map<String, Object>> translations = voDataSourceAdapter.getList(
				voDataSourceAdapter.getDataSourceGetTranslationsByLocaleAndModule(),
				new Object[] { localeId, moduleId });

		return vo2uiAdapter.convert(
				voDataSourceAdapter.getDataSourceService()
						.getById(voDataSourceAdapter.getDataSourceGetTranslationsByLocaleAndModule()).getFieldId(),
				translations);
	}
	
	public Map<String, Object> getLabelTranslated(String localeId, Integer moduleId, String label) {

		Map<String, Object> translation = voDataSourceAdapter.get(
				voDataSourceAdapter.getDataSourceGetLabelTranslated(),
				new Object[] { localeId, moduleId, label });

		return vo2uiAdapter.convert(
				voDataSourceAdapter.getDataSourceService()
						.getById(voDataSourceAdapter.getDataSourceGetLabelTranslated()).getFieldId(),
						translation);
	}
	
	public Map<String, Object> getLabelTranslatedUsingFields(String localeId, Integer moduleId, String label, List<String> fields) {

		Map<String, Object> translation = voDataSourceAdapter.get(
				voDataSourceAdapter.getDataSourceGetLabelTranslatedUsingFields(),
				new Object[] { localeId, moduleId, label, fields });

		return vo2uiAdapter.convert(
				voDataSourceAdapter.getDataSourceService()
						.getById(voDataSourceAdapter.getDataSourceGetLabelTranslatedUsingFields()).getFieldId(),
						translation);
	}
}
