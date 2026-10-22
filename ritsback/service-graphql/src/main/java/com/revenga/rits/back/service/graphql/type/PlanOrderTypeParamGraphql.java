package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.ElementTypeParamUnit;
import com.revenga.rits.back.data.core.model.PlanOrderTypeParam;
import com.revenga.rits.back.front.core.model.helper.PlanOrderTypeParamHelper;
import com.revenga.rits.back.front.core.model.vo.DataTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderTypeParamVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class PlanOrderTypeParamGraphql extends PlanOrderTypeParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3908796455858736265L;

	private PlanOrderTypeParam planOrderTypeParam;

	public PlanOrderTypeParamGraphql(final PlanOrderTypeParam planOrderTypeParam) {

		PlanOrderTypeParamHelper.assign(planOrderTypeParam, this);
		this.planOrderTypeParam = planOrderTypeParam;

	}

	@Override
	public DataTypeVO getDataType() {

		if (super.getDataType() == null && planOrderTypeParam != null && planOrderTypeParam.getDataTypeId() != null) {

			DataType dataType = EntitiesManager.getInstance().getDataTypeRepository()
					.get(planOrderTypeParam.getDataTypeId(), false);

			if (dataType != null) {

				super.setDataType(new DataTypeGraphql(dataType));
			}
		}

		return super.getDataType();
	}

	@Override
	public String getLabelUnit() {

		if (super.getLabelUnit() == null && planOrderTypeParam != null
				&& planOrderTypeParam.getPlanOrderTypeParamUnitId() != null) {

			ElementTypeParamUnit elementTypeParamUnit = EntitiesManager.getInstance()
					.getElementTypeParamUnitRepository().get(planOrderTypeParam.getPlanOrderTypeParamUnitId(), false);

			if (elementTypeParamUnit != null)
				super.setLabelUnit(elementTypeParamUnit.getLabelAlias());
		}

		return super.getLabelUnit();
	}
}
