package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.I18nLocale;
import com.revenga.rits.back.front.core.model.vo.I18nLocaleVO;
import com.revenga.rits.back.service.graphql.type.I18nLocaleGraphql;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class I18nLocaleService implements QueryInterface<I18nLocaleVO> {
	
	@Override
	public List<I18nLocaleVO> getAll() {
		
		List<I18nLocaleVO> i18nLocaleVO = new ArrayList<>();

		for (I18nLocale i18nLocale : EntitiesManager.getInstance().getI18nLocaleRepository().getAll(false)) {

			i18nLocaleVO.add(new I18nLocaleGraphql(i18nLocale));
		}

		return i18nLocaleVO;
	}

	@Override
	public Optional<I18nLocaleVO> get(final String localeId) {
		

		Optional<I18nLocaleVO> i18nLocaleVO = null;
		
		try {
			Key key = new Key(localeId);

			if (key.getClazz() == I18nLocale.class) {

				I18nLocale i18nLocale = EntitiesManager.getInstance().getI18nLocaleRepository()
						.get(key.getId(1), false);

				if (i18nLocale != null) {

					i18nLocaleVO = Optional.of(new I18nLocaleGraphql(i18nLocale));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: I18nLocale");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return i18nLocaleVO;
	}

	@Override
	public List<I18nLocaleVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
