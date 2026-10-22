package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.I18nLocale;
import com.revenga.rits.back.front.core.model.vo.I18nLocaleVO;

public class I18nLocaleHelper {

	private I18nLocaleHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static I18nLocaleVO get(final I18nLocale i18nLocale) {

		I18nLocaleVO i18nLocaleVO = null;

		if (i18nLocale != null) {

			i18nLocaleVO = new I18nLocaleVO();

			assign(i18nLocale, i18nLocaleVO);
		}

		return i18nLocaleVO;
	}

	public static I18nLocale get(final I18nLocaleVO i18nLocaleVO) {

		I18nLocale i18nLocale = null;

		if (i18nLocaleVO != null) {

			i18nLocale = new I18nLocale();
			if (i18nLocaleVO.getId() != null) {

				Key key = new Key(i18nLocaleVO.getId());

				if (key.getClazz() == I18nLocale.class) {

					i18nLocale.setId(Long.valueOf(key.getId(1)));
				}
			}

			i18nLocale.setCode(i18nLocaleVO.getCode());
			i18nLocale.setName(i18nLocaleVO.getName());
		}

		return i18nLocale;
	}

	public static void assign(final I18nLocale i18nLocale, I18nLocaleVO i18nLocaleVO) {

		if (ObjectUtils.allNotNull(i18nLocale, i18nLocaleVO)) {

			BasicElementHelper.fillBasicProperties(i18nLocaleVO, i18nLocale);

			i18nLocaleVO.setCode(i18nLocale.getCode());
			i18nLocaleVO.setName(i18nLocale.getName());
		}
	}
}
