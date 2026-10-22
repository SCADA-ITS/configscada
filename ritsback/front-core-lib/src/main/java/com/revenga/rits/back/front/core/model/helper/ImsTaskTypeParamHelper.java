package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsTaskTypeParam;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.front.core.model.vo.ImsTaskTypeParamVO;

public class ImsTaskTypeParamHelper {

	/**
	 * Default constructor
	 */
	private ImsTaskTypeParamHelper() {

		super();
	}

	/**
	 * Method that return an ImsTaskTypeParamVO
	 * @param imsTaskTypeParam: Param input
	 * @return: ImsTaskTypeParamVO object
	 */
	public static ImsTaskTypeParamVO get(ImsTaskTypeParam imsTaskTypeParam) {

		ImsTaskTypeParamVO imsTaskTypeParamVO = null;

		if (imsTaskTypeParam != null) {

			imsTaskTypeParamVO = new ImsTaskTypeParamVO();

			assign(imsTaskTypeParam, imsTaskTypeParamVO);
		}

		return imsTaskTypeParamVO;
	}

	/**
	 * Method that return an ImsTaskTypeParam
	 * @param imsTaskTypeParamVO: Param input
	 * @return: ImsTaskTypeParam object
	 */
	public static ImsTaskTypeParam get(ImsTaskTypeParamVO imsTaskTypeParamVO) {

		ImsTaskTypeParam imsTaskTypeParam = null;

		if (imsTaskTypeParamVO != null) {

			imsTaskTypeParam = new ImsTaskTypeParam();

			if (imsTaskTypeParamVO.getId() != null) {

				Key key = new Key(imsTaskTypeParamVO.getId());

				if (key.getClazz() == ImsTaskTypeParam.class) {

					imsTaskTypeParam.setTaskTypeId(Long.valueOf(key.getId(1)));
					imsTaskTypeParam.setParamTypeId(Long.valueOf(key.getId(2)));
					imsTaskTypeParam.setId(Long.valueOf(key.getId(3)));
				}
			}

			if (imsTaskTypeParamVO.getParamTypeId() != null) {

				Key key = new Key(imsTaskTypeParamVO.getParamTypeId());

				if (key.getClazz() == ParamType.class) {

					imsTaskTypeParam.setParamTypeId(Long.valueOf(key.getId(1)));
				}
			}

			imsTaskTypeParam.setAlias(imsTaskTypeParamVO.getAlias());
			imsTaskTypeParam.setDescription((imsTaskTypeParamVO.getDescription()));
		}

		return imsTaskTypeParam;
	}

	/**
	 * Method that make a copy of an ImsTaskTypeParam object to an ImsTaskTypeParamVO object 
	 * @param imsTaskTypeParam: Source object to copy
	 * @param imsTaskTypeParamVO: Destination object result of the copy process
	 */
	public static void assign(final ImsTaskTypeParam imsTaskTypeParam, ImsTaskTypeParamVO imsTaskTypeParamVO) {

		if (imsTaskTypeParam != null && imsTaskTypeParamVO != null) {

			BasicElementHelper.fillBasicProperties(imsTaskTypeParamVO, imsTaskTypeParam);

			if (ObjectUtils.allNotNull(imsTaskTypeParam.getTaskTypeParamUnit())
					&& ObjectUtils.allNotNull(imsTaskTypeParam.getTaskTypeParamUnit().getLabelAlias())) {

				imsTaskTypeParamVO.setLabelUnit(imsTaskTypeParam.getTaskTypeParamUnit().getLabelAlias());
			}

			if (imsTaskTypeParam.getParamTypeId() != null) {

				ParamType paramType = new ParamType();
				paramType.setId(imsTaskTypeParam.getParamTypeId());
				imsTaskTypeParamVO.setParamTypeId(BasicElementHelper.getIdFromElement(paramType));
			}
			
			imsTaskTypeParamVO.setThreshold1(imsTaskTypeParam.getThreshold1());
			imsTaskTypeParamVO.setThreshold2(imsTaskTypeParam.getThreshold2());
		}

		ModelVOHelper.translate(imsTaskTypeParamVO);
	}

}
