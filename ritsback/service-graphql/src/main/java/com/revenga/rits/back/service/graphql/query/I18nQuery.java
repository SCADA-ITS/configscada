package com.revenga.rits.back.service.graphql.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.I18nLabelVO;
import com.revenga.rits.back.front.core.model.vo.I18nLocaleVO;
import com.revenga.rits.back.service.graphql.service.I18nLabelService;
import com.revenga.rits.back.service.graphql.service.I18nLocaleService;

@Component
public class I18nQuery implements GraphQLQueryResolver {

	@Autowired
	private I18nLabelService i18nLabelService;

	@Autowired
	private I18nLocaleService i18nLocaleService;

	public List<I18nLocaleVO> getLocales() {

		return i18nLocaleService.getAll();
	}

	public Optional<I18nLocaleVO> getLocale(final String localeId) {

		return i18nLocaleService.get(localeId);
	}

	public List<I18nLabelVO> getTranslations(final String localeId, final Long moduleId) {

		return i18nLabelService.getTranslations(localeId, moduleId);
	}

	public Optional<I18nLabelVO> getTranslation(final String localeId, final Long moduleId, final String label,
			final List<String> fields) {

		return i18nLabelService.getTranslation(localeId, moduleId, label, fields);
	}
}