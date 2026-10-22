package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.SpathStretch;
import com.revenga.rits.back.front.core.model.vo.SpathStretchVO;

public class SpathStretchHelper {

	private SpathStretchHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SpathStretchVO get(final SpathStretch spathStretch) {

		SpathStretchVO spathStretchVO = null;

		if (spathStretch != null) {

			spathStretchVO = new SpathStretchVO();

			assign(spathStretch, spathStretchVO);
		}

		return spathStretchVO;
	}

	public static SpathStretch get(final SpathStretchVO spathStretchVO) {

		SpathStretch spathStretch = null;

		if (spathStretchVO != null) {

			spathStretch = new SpathStretch();
			
			if (spathStretchVO.getId() != null) {

				Key key = new Key(spathStretchVO.getId());

				if (key.getClazz() == SpathStretch.class) {

					spathStretch.setSpathId(Long.valueOf(key.getId(1)));
					spathStretch.setStretchId(Long.valueOf(key.getId(2)));
					spathStretch.setRelStretchId(Long.valueOf(key.getId(3)));
					
				}
			}

			spathStretch.setPosition(spathStretchVO.getPosition());
		}

		return spathStretch;
	}

	public static void assign(final SpathStretch spathStretch, SpathStretchVO spathStretchVO) {

		if (ObjectUtils.allNotNull(spathStretch, spathStretchVO)) {

			BasicElementHelper.fillBasicProperties(spathStretchVO, spathStretch);

			spathStretchVO.setPosition(spathStretch.getPosition());

			if (spathStretch.getSpath() != null) {

				spathStretchVO.setSpath(SpathHelper.get(spathStretch.getSpath()));
			}
		}
	}
}