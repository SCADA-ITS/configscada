package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeParamUnit;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.front.core.model.helper.ElementTypeParamHelper;
import com.revenga.rits.back.front.core.model.vo.DataTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ParamTypeVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@EqualsAndHashCode(callSuper = false)
@Log4j2
public class ElementTypeParamGraphql extends ElementTypeParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5016331746136355946L;

	private ElementTypeParam elementTypeParam;

	public ElementTypeParamGraphql(final ElementTypeParam elementTypeParam) {

		ElementTypeParamHelper.assign(elementTypeParam, this);

		this.elementTypeParam = elementTypeParam;
	}
	
	@Override
	public DataTypeVO getDataType() {

		if (super.getDataType() == null && elementTypeParam != null
				&& elementTypeParam.getDataTypeId() != null) {

			DataType dataType = EntitiesManager.getInstance().getDataTypeRepository()
					.get(elementTypeParam.getDataTypeId(), false);

			if (dataType != null) {

				super.setDataType(new DataTypeGraphql(dataType));
			}
		}

		return super.getDataType();
	}
	
	@Override
	public ParamTypeVO getParamType() {

		if (super.getParamType() == null && elementTypeParam != null
				&& elementTypeParam.getParamTypeId() != null) {

			ParamType paramType = EntitiesManager.getInstance().getParamTypeRepository()
					.get(elementTypeParam.getParamTypeId(), false);

			if (paramType != null) {

				super.setParamType(new ParamTypeGraphql(paramType));
			}
		}

		return super.getParamType();
	}

	@Override
	public String getLabelUnit() {

		if (super.getLabelUnit() == null) {

			if (elementTypeParam != null) {

				ElementTypeParamUnit elementTypeParamUnit = EntitiesManager.getInstance()
						.getElementTypeParamUnitRepository().get(elementTypeParam.getElementTypeParamUnitId(), false);

				if (elementTypeParamUnit != null)
					super.setLabelUnit(elementTypeParamUnit.getLabelAlias());
			}

		} else {

			log.error("elementTypeParam has null pointer");
		}

		return super.getLabelUnit();
	}

}
