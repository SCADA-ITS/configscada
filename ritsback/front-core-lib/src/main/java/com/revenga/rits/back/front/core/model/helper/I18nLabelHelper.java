package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.I18nLabel;
import com.revenga.rits.back.front.core.model.vo.I18nLabelVO;

public class I18nLabelHelper {

	private I18nLabelHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static I18nLabelVO get(final I18nLabel i18nLabel) {

		I18nLabelVO i18nLabelVO = null;

		if (i18nLabel != null) {

			i18nLabelVO = new I18nLabelVO();

			assign(i18nLabel, i18nLabelVO);
		}

		return i18nLabelVO;
	}

	public static I18nLabel get(final I18nLabelVO i18nLabelVO) {

		I18nLabel i18nLabel = null;

		if (i18nLabelVO != null) {

			i18nLabel = new I18nLabel();
			if (i18nLabelVO.getId() != null) {

				Key key = new Key(i18nLabelVO.getId());

				if (key.getClazz() == I18nLabel.class) {

					i18nLabel.setId(Long.valueOf(key.getId(1)));
				}
			}

			i18nLabel.setLabel(i18nLabelVO.getLabel());
			i18nLabel.setTranslation(i18nLabelVO.getTranslation());
		}

		return i18nLabel;
	}

	public static void assign(final I18nLabel i18nLabel, I18nLabelVO i18nLabelVO) {

		if (ObjectUtils.allNotNull(i18nLabel, i18nLabelVO)) {

			BasicElementHelper.fillBasicProperties(i18nLabelVO, i18nLabel);

			i18nLabelVO.setLabel(i18nLabel.getLabel());
			i18nLabelVO.setTranslation(i18nLabel.getTranslation());
		}
	}
}
