package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.StretchLocation;
import com.revenga.rits.back.front.core.model.vo.StretchLocationVO;

public class StretchLocationHelper {

	private StretchLocationHelper() {

		super();
	}

	public static StretchLocationVO get(final StretchLocation stretchLocation) {

		StretchLocationVO stretchLocationVO = null;

		if (stretchLocation != null) {

			stretchLocationVO = new StretchLocationVO();

			assign(stretchLocation, stretchLocationVO);
		}

		return stretchLocationVO;
	}
	
	public static StretchLocation get(final StretchLocationVO stretchLocationVO) {

		StretchLocation stretchLocation = null;

		if (stretchLocationVO != null) {

			stretchLocation = new StretchLocation();

			stretchLocation.setAlias(stretchLocationVO.getAlias());
			stretchLocation.setDescription(stretchLocationVO.getDescription());
			stretchLocation.setPosition(stretchLocationVO.getPosition());

			if (stretchLocationVO.getStretchId() != null) {

				Key key = new Key(stretchLocationVO.getStretchId());

				if (key.getClazz() == Stretch.class) {

					Stretch stretch = new Stretch();
					stretch.setId(Long.valueOf(key.getId(1)));

					stretchLocation.setStretchId(stretch.getId());

					stretchLocation.setStretch(stretch);
				}
			}
		}

		return stretchLocation;
	}
			

	public static void assign(final StretchLocation stretchLocation, StretchLocationVO stretchLocationVO) {

		if (stretchLocation != null && stretchLocationVO != null) {

			BasicElementHelper.fillBasicProperties(stretchLocationVO, stretchLocation);

			if (stretchLocation.getPosition() != null) {

				stretchLocationVO.setPosition(stretchLocation.getPosition());
			}
			
			if (stretchLocation.getStretchId() != null) {

				Stretch stretch = new Stretch();
				stretch.setId(stretchLocation.getStretchId() );
				stretchLocationVO.setStretchId(BasicElementHelper.getIdFromElement(stretch));
				
			}
		}
	}
}
