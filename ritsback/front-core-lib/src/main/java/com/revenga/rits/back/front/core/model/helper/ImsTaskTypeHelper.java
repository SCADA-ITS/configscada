package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsTaskType;
import com.revenga.rits.back.front.core.model.vo.ImsTaskTypeVO;

public class ImsTaskTypeHelper {

	private ImsTaskTypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsTaskTypeVO get(final ImsTaskType imsTaskType) {

		ImsTaskTypeVO imsTaskTypeVO = null;

		if (imsTaskType != null) {

			imsTaskTypeVO = new ImsTaskTypeVO();

			assign(imsTaskType, imsTaskTypeVO);
		}

		return imsTaskTypeVO;
	}

	public static ImsTaskType get(final ImsTaskTypeVO imsTaskTypeVO) {

		ImsTaskType imsTaskType = null;

		if (imsTaskTypeVO != null) {

			imsTaskType = new ImsTaskType();
			if (imsTaskTypeVO.getId() != null) {

				Key key = new Key(imsTaskTypeVO.getId());

				if (key.getClazz() == ImsTaskType.class) {

					imsTaskType.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsTaskType.setAlias(imsTaskTypeVO.getAlias());
			imsTaskType.setDescription((imsTaskTypeVO.getDescription()));

		}

		return imsTaskType;
	}

	public static void assign(final ImsTaskType imsTaskType, ImsTaskTypeVO imsTaskTypeVO) {

		if (ObjectUtils.allNotNull(imsTaskType, imsTaskTypeVO)) {

			BasicElementHelper.fillBasicProperties(imsTaskTypeVO, imsTaskType);

		}
	}

}
