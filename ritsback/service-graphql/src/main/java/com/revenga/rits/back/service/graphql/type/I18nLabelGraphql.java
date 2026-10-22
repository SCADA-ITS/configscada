package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.I18nLabel;
import com.revenga.rits.back.front.core.model.helper.I18nLabelHelper;
import com.revenga.rits.back.front.core.model.vo.I18nLabelVO;

public class I18nLabelGraphql extends I18nLabelVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1724722264393486119L;
	
	public I18nLabelGraphql(final I18nLabel i18nLabel) {
		
		I18nLabelHelper.assign(i18nLabel, this);
	}
}
