package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ParamHist;
import com.revenga.rits.back.front.core.model.vo.ParamHistVO;

public class ParamHistHelper {

	private ParamHistHelper() {

		super();
	}

	public static ParamHistVO get(ParamHist paramHist) {

		ParamHistVO paramHistVO = null;

		if (paramHist != null) {

			paramHistVO = new ParamHistVO();

			assign(paramHist, paramHistVO);
		}

		return paramHistVO;
	}

	public static ParamHist get(ParamHistVO paramHistVO) {

		ParamHist paramHist = null;

		if (paramHistVO != null) {

			paramHist = new ParamHist();

			if (paramHistVO.getId() != null) {

				Key key = new Key(paramHistVO.getId());

				if (key.getClazz() == ParamHist.class) {

					paramHist.setId(Long.valueOf(key.getId(1)));
				}
			}
			
			paramHist.setColumnName(paramHistVO.getColumnName());
			paramHist.setOperation(paramHistVO.getOperation());
			paramHist.setValue(paramHistVO.getValue());
			

		}

		return paramHist;
	}

	public static void assign(final ParamHist paramHist, ParamHistVO paramHistVO) {

		if (paramHist != null && paramHistVO != null) {

			BasicElementHelper.fillBasicProperties(paramHistVO, paramHist);
			paramHistVO.setColumnName(paramHist.getColumnName());
			paramHistVO.setOperation(paramHist.getOperation());
			paramHistVO.setValue(paramHist.getValue());

		}
	}
}