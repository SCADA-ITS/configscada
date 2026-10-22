package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.ElementTypeParamUnit;
import com.revenga.rits.back.data.core.model.ImsTaskTypeParam;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.front.core.model.helper.ImsTaskTypeParamHelper;
import com.revenga.rits.back.front.core.model.vo.DataTypeVO;
import com.revenga.rits.back.front.core.model.vo.ImsTaskTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ParamTypeVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsTaskTypeParamGraphql extends ImsTaskTypeParamVO {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3047785479935053869L;
	
	
	/**
	 * 
	 */
	private ImsTaskTypeParam imsTaskTypeParam;

	public ImsTaskTypeParamGraphql(final ImsTaskTypeParam imsTaskTypeParam) {

		ImsTaskTypeParamHelper.assign(imsTaskTypeParam, this);

		this.imsTaskTypeParam = imsTaskTypeParam;
	}
	
	/**
	 * 
	 */
	@Override
	public ParamTypeVO getParamType() {


		if (super.getParamType() == null && imsTaskTypeParam != null
				&& imsTaskTypeParam.getParamTypeId() != null) {

			ParamType paramType = EntitiesManager.getInstance().getParamTypeRepository()
					.get(imsTaskTypeParam.getParamTypeId(), false);

			if (paramType != null) {

				super.setParamType(new ParamTypeGraphql(paramType));
			}
		}

		return super.getParamType();
	}

	@Override
	public DataTypeVO getDataType() {

		if (super.getDataType() == null && imsTaskTypeParam != null
				&& imsTaskTypeParam.getDataTypeId() != null) {

			DataType dataType = EntitiesManager.getInstance().getDataTypeRepository()
					.get(imsTaskTypeParam.getDataTypeId(), false);

			if (dataType != null) {

				super.setDataType(new DataTypeGraphql(dataType));
			}
		}

		return super.getDataType();
	}
	
	@Override
	public String getLabelUnit() {

		if (super.getLabelUnit() == null) {

			if (imsTaskTypeParam != null) {

				ElementTypeParamUnit elementTypeParamUnit = EntitiesManager.getInstance()
						.getElementTypeParamUnitRepository().get(imsTaskTypeParam.getTaskTypeParamUnitId(), false);

				if (elementTypeParamUnit != null)
					super.setLabelUnit(elementTypeParamUnit.getLabelAlias());
			}

		} else {

			log.error("elementTypeParam has null pointer");
		}

		return super.getLabelUnit();
	}
}