package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeParamUnit;
import com.revenga.rits.back.front.core.model.helper.CommandElementTypeParamHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.DataTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class CommandElementTypeParamGraphql extends CommandElementTypeParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3908796455858736265L;

	private CommandElementTypeParam commandElementTypeParam;

	public CommandElementTypeParamGraphql(final CommandElementTypeParam commandElementTypeParam) {

		CommandElementTypeParamHelper.assign(commandElementTypeParam, this);
		this.commandElementTypeParam = commandElementTypeParam;

	}

	@Override
	public DataTypeVO getDataType() {

		if (super.getDataType() == null && commandElementTypeParam != null
				&& commandElementTypeParam.getDataTypeId() != null) {

			DataType dataType = EntitiesManager.getInstance().getDataTypeRepository()
					.get(commandElementTypeParam.getDataTypeId(), false);

			if (dataType != null) {

				super.setDataType(new DataTypeGraphql(dataType));
			}
		}

		return super.getDataType();
	}
	
	@Override
	public ElementTypeParamVO getElementTypeParam() {

		if (super.getElementTypeParam() == null && commandElementTypeParam != null
				&& commandElementTypeParam.getElementTypeParamId() != null) {

			ElementTypeParam elementTypeParam = EntitiesManager.getInstance().getElementTypeParamRepository().get(
					commandElementTypeParam.getElementTypeId(), commandElementTypeParam.getParamTypeId(),
					commandElementTypeParam.getElementTypeParamId(), false);

			if (elementTypeParam != null) {

				super.setElementTypeParam(new ElementTypeParamGraphql(elementTypeParam));
			}
		}

		return super.getElementTypeParam();
	}

	@Override
	public String getLabelUnit() {

		if (super.getLabelUnit() == null && commandElementTypeParam != null
				&& commandElementTypeParam.getCommandElementTypeParamUnitId() != null) {

			ElementTypeParamUnit elementTypeParamUnit = EntitiesManager.getInstance()
					.getElementTypeParamUnitRepository()
					.get(commandElementTypeParam.getCommandElementTypeParamUnitId(), false);

			if (elementTypeParamUnit != null)
				super.setLabelUnit(elementTypeParamUnit.getLabelAlias());
		}

		return super.getLabelUnit();
	}
}
