package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.StretchType;
import com.revenga.rits.back.front.core.model.vo.StretchVO;

public class StretchHelper {

	private StretchHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static StretchVO get(final Stretch stretch) {

		StretchVO stretchVO = null;

		if (stretch != null) {

			stretchVO = new StretchVO();

			assign(stretch, stretchVO);
		}

		return stretchVO;
	}

	public static Stretch get(final StretchVO stretchVO) {

		Stretch stretch = null;

		if (stretchVO != null) {

			stretch = new Stretch();
			if (stretchVO.getId() != null) {

				Key key = new Key(stretchVO.getId());

				if (key.getClazz() == Stretch.class) {

					stretch.setId(Long.valueOf(key.getId(1)));
				}
			}

			if (stretchVO.getStretchTypeId() != null) {

				Key key = new Key(stretchVO.getStretchTypeId());

				if (key.getClazz() == StretchType.class) {

					stretch.setStretchTypeId(Long.valueOf(key.getId(1)));
				}
			}

			stretch.setAlias(stretchVO.getAlias());
			stretch.setDescription((stretchVO.getDescription()));
			stretch.setPkIni(stretchVO.getPkIni());
			stretch.setPkEnd(stretchVO.getPkEnd());
			stretch.setLanes(stretchVO.getLanes());

		}

		return stretch;
	}

	public static void assign(final Stretch stretch, StretchVO stretchVO) {

		if (ObjectUtils.allNotNull(stretch, stretchVO)) {

			BasicElementHelper.fillBasicProperties(stretchVO, stretch);
			
			stretchVO.setPkIni(stretch.getPkIni());
			stretchVO.setPkEnd(stretch.getPkEnd());
			stretchVO.setLanes(stretch.getLanes());

			if (stretch.getStretchTypeId() != null) {

				StretchType stretchType = new StretchType();
				stretchType.setId(stretch.getStretchTypeId());
				stretchVO.setStretchTypeId(BasicElementHelper.getIdFromElement(stretchType));
			}
		}
	}

}
