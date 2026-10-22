package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class I18nBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;

	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_LOCALES;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_LOCALE;
	}

	public List<Map<String, Object>> getTranslationsByLocale(String localeId) {

		List<Map<String, Object>> translations = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE, new Object[] { localeId });

		return vo2uiAdapter.convert(
				VODataSourceAdapter.DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE,
				translations);
	}

	public List<Map<String, Object>> getTranslationsByLocaleAndModule(String localeId, Integer moduleId) {

		List<Map<String, Object>> translations = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE_AND_MODULE,
				new Object[] { localeId, moduleId });

		return vo2uiAdapter.convert(
				VODataSourceAdapter.DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE_AND_MODULE,
				translations);
	}
	
	public Map<String, Object> getLabelTranslated(String localeId, Integer moduleId, String label) {

		Map<String, Object> translation = voDataSourceAdapter.get(
				VODataSourceAdapter.DATA_SOURCE_GET_LABEL_TRANSLATED,
				new Object[] { localeId, moduleId, label });

		return vo2uiAdapter.convert(
				VODataSourceAdapter.DATA_SOURCE_GET_LABEL_TRANSLATED,
						translation);
	}
	
	public Map<String, Object> getLabelTranslatedUsingFields(String localeId, Integer moduleId, String label, List<String> fields) {

		Map<String, Object> translation = voDataSourceAdapter.get(
				VODataSourceAdapter.DATA_SOURCE_GET_LABEL_TRANSLATED_USING_FIELDS,
				new Object[] { localeId, moduleId, label, fields });

		return vo2uiAdapter.convert(
				VODataSourceAdapter.DATA_SOURCE_GET_LABEL_TRANSLATED_USING_FIELDS,
						translation);
	}
}
