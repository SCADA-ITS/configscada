package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.app.AppInfra;
import com.revenga.rits.front.openits.service.user.UserManager;

@Service
public class I18nBO extends GenericBO {

	private static final Boolean USERMGR_ENABLED = Boolean.valueOf(AppInfra.getProperty("usermgr.enabled"));
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired
	UserManager userManager;

	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> list = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			list = userManager.getLocales();
		}
		else {
			
			list = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_LOCALES, null);
		}

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_LOCALES, list);
	}
	
	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> obj = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			obj = userManager.getLocale(id);
		}
		else {
			
			obj = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_LOCALE,
					new Object[] { id });
		}

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_LOCALE, obj);
	}

	public List<Map<String, Object>> getTranslationsByLocale(String localeId) {

		List<Map<String, Object>> translations = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			translations = userManager.getTranslationsByLocale(localeId);
		}
		else {
			
			translations = voDataSourceAdapter
					.getList(VODataSourceAdapter.DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE, new Object[] { localeId });
		}

		return vo2uiAdapter.convert(
				VODataSourceAdapter.DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE,
				translations);
	}

	public List<Map<String, Object>> getTranslationsByLocaleAndModule(String localeId, Integer moduleId) {

		List<Map<String, Object>> translations = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			translations = userManager.getTranslationsByLocaleAndModule(localeId, moduleId);
		}
		else {
			
			translations = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE_AND_MODULE,
					new Object[] { localeId, moduleId });
		}

		return vo2uiAdapter.convert(
				VODataSourceAdapter.DATA_SOURCE_GET_TRANSLATIONS_BY_LOCALE_AND_MODULE,
				translations);
	}
	
	public Map<String, Object> getLabelTranslated(String localeId, Integer moduleId, String label) {

		Map<String, Object> translation = null;
		
		if (USERMGR_ENABLED.booleanValue()) {
			
			translation = userManager.getLabelTranslated(localeId, moduleId, label);
		}
		else {
			
			translation = voDataSourceAdapter.get(
					VODataSourceAdapter.DATA_SOURCE_GET_LABEL_TRANSLATED,
					new Object[] { localeId, moduleId, label });
		}

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
