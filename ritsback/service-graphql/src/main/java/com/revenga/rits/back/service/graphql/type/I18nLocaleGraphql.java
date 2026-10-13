package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.I18nLocale;
import com.revenga.rits.back.front.core.model.helper.I18nLocaleHelper;
import com.revenga.rits.back.front.core.model.vo.I18nLocaleVO;

public class I18nLocaleGraphql extends I18nLocaleVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3166106143527443100L;

	public I18nLocaleGraphql(final I18nLocale i18nLocale) {

		I18nLocaleHelper.assign(i18nLocale, this);
	}
}
