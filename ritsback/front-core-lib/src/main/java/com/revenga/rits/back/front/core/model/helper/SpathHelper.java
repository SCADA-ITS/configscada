package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Spath;
import com.revenga.rits.back.front.core.model.vo.SpathVO;

/**
 * 
 * @author M.A Monzón
 *
 */
public class SpathHelper {
	
	/**
	 * 
	 */
	private SpathHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @param imsIncidentGroup
	 * @return
	 */
	public static SpathVO get(final Spath spath) {

		SpathVO spathVO = null;

		if (spath != null) {

			spathVO = new SpathVO();

			assign(spath, spathVO);
		}

		return spathVO;
	}

	/**
	 * 
	 * @param spathVO
	 * @return
	 */
	public static Spath get(final SpathVO spathVO) {

		Spath spath = null;

		if (spathVO != null) {

			spath = new Spath();
			if (spathVO.getId() != null) {

				Key key = new Key(spathVO.getId());

				if (key.getClazz() == Spath.class) {

					spath.setId(Long.valueOf(key.getId(1)));
				}
			}

			spath.setAlias(spathVO.getAlias());
			spath.setDescription((spathVO.getDescription()));
			
		/*	if (spathVO.getSpathStretchs() != null) {

				List<SpathStretch> spathStretchs = new ArrayList<>();

				for (SpathStretchVO spathStretchVO : spathVO.getSpathStretchs()) {

					if (spathStretchVO != null) {
						
						spathStretchs.add(SpathStretchHelper.get(spathStretchVO));
					}
				}

				spath.setSpathStretchs(spathStretchs);
			}*/
		}

		return spath;
	}

	public static void assign(final Spath spath, SpathVO spathVO) {

		if (ObjectUtils.allNotNull(spath, spathVO)) {

			BasicElementHelper.fillBasicProperties(spathVO, spath);
		}
	}
	

}