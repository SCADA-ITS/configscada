package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.LevelOfService;
import com.revenga.rits.back.front.core.model.vo.LevelOfServiceVO;

public class LevelOfServiceHelper {

	private LevelOfServiceHelper() {

		super();
	}

	public static LevelOfServiceVO get(LevelOfService levelOfService) {

		LevelOfServiceVO levelOfServiceVO = null;

		if (levelOfService != null) {

			levelOfServiceVO = new LevelOfServiceVO();

			assign(levelOfService, levelOfServiceVO);
		}

		return levelOfServiceVO;
	}

	public static LevelOfService get(LevelOfServiceVO levelOfServiceVO) {

		LevelOfService levelOfService = null;

		if (levelOfServiceVO != null) {

			levelOfService = new LevelOfService();

			if (levelOfServiceVO.getId() != null) {

				Key key = new Key(levelOfServiceVO.getId());

				if (key.getClazz() == LevelOfService.class) {

					levelOfService.setId(Long.valueOf(key.getId(1)));
				}
			}

			levelOfService.setAlias(levelOfServiceVO.getAlias());
			levelOfService.setDescription((levelOfServiceVO.getDescription()));
			levelOfService.setColour(levelOfServiceVO.getColour());
			levelOfService.setLos(levelOfServiceVO.getLos());

		}

		return levelOfService;
	}

	public static void assign(final LevelOfService levelOfService, LevelOfServiceVO levelOfServiceVO) {

		if (levelOfService != null && levelOfServiceVO != null) {

			BasicElementHelper.fillBasicProperties(levelOfServiceVO, levelOfService);

			levelOfServiceVO.setColour(levelOfService.getColour());
			levelOfServiceVO.setLos(levelOfService.getLos());

		}
	}

}
