package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.StretchStretch;
import com.revenga.rits.back.front.core.model.vo.StretchStretchVO;

public class StretchStretchHelper {

	private StretchStretchHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static StretchStretchVO get(final StretchStretch stretchStretch) {

		StretchStretchVO stretchStretchVO = null;

		if (stretchStretch != null) {

			stretchStretchVO = new StretchStretchVO();

			assign(stretchStretch, stretchStretchVO);
		}

		return stretchStretchVO;
	}

	public static StretchStretch get(final StretchStretchVO stretchStretchVO) {

		StretchStretch stretchStretch = null;

		if (stretchStretchVO != null) {

			stretchStretch = new StretchStretch();
			if (stretchStretchVO.getId() != null) {

				Key key = new Key(stretchStretchVO.getId());

				if (key.getClazz() == StretchStretch.class) {

					stretchStretch.setId(Long.valueOf(key.getId(1)));
				}
			}
			
			stretchStretch.setAlias(stretchStretchVO.getAlias());
			stretchStretch.setDescription((stretchStretchVO.getDescription()));
		}

		return stretchStretch;
	}

	public static void assign(final StretchStretch stretchStretch, StretchStretchVO stretchStretchVO) {

		if (ObjectUtils.allNotNull(stretchStretch, stretchStretchVO)) {

			BasicElementHelper.fillBasicProperties(stretchStretchVO, stretchStretch);
		}
	}

}
