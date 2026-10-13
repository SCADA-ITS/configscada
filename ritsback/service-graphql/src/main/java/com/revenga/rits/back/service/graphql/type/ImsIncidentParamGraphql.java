package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.ImsIncidentEnumParam;
import com.revenga.rits.back.data.core.model.ImsIncidentParam;
import com.revenga.rits.back.data.core.model.ImsIncidentParamGroup;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentParamHelper;
import com.revenga.rits.back.front.core.model.vo.DataTypeVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentEnumParamVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentParamGroupVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentParamVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ImsIncidentParamGraphql extends ImsIncidentParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4236372544860713429L;
	
	private ImsIncidentParam incidentParam;

	public ImsIncidentParamGraphql(final ImsIncidentParam incidentParam) {

		ImsIncidentParamHelper.assign(incidentParam, this);

		this.incidentParam = incidentParam;
	}
	
	@Override
	public ImsIncidentParamGroupVO getIncidentParamGroup() {

		if (super.getIncidentParamGroup() == null && incidentParam != null
				&& incidentParam.getIncidentParamGroupId() != null) {

			ImsIncidentParamGroup incidentParamGroup = EntitiesManager.getInstance()
					.getImsIncidentParamGroupRepository().get(incidentParam.getIncidentParamGroupId(), true);

			if (incidentParamGroup != null) {

				super.setIncidentParamGroup(new ImsIncidentParamGroupGraphql(incidentParamGroup));

			}
		}

		return super.getIncidentParamGroup();
	}
	
	@Override
	public DataTypeVO getDataType() {

		if (super.getDataType() == null && incidentParam != null
				&& incidentParam.getDataTypeId() != null) {

			DataType dataType = EntitiesManager.getInstance().getDataTypeRepository()
					.get(incidentParam.getDataTypeId(), false);

			if (dataType != null) {

				super.setDataType(new DataTypeGraphql(dataType));
			}
		}

		return super.getDataType();
	}
	
	@Override
	public List<ImsIncidentEnumParamVO> getIncidentEnumsParam(){
		
		if (super.getIncidentEnumsParam() == null && incidentParam != null
				&& incidentParam.getId() != null) {

			List<ImsIncidentEnumParam> incidentEnumParams = EntitiesManager.getInstance().getImsIncidentEnumParamRepository().gets(incidentParam.getId(), false);
			
			List<ImsIncidentEnumParamVO> incidentEnumParamsVO = new ArrayList<>();
			
			if (incidentEnumParams != null && !CollectionUtils.isEmpty(incidentEnumParams)) {

				for (ImsIncidentEnumParam incidentEnumParam : incidentEnumParams) {
					
					if (incidentEnumParam != null && incidentEnumParam.getId()!= null && incidentEnumParam.getIncidentParamId() != null) {
						
						incidentEnumParamsVO.add(new ImsIncidentEnumParamGraphql(incidentEnumParam));
					}
				}
			}
			
			super.setIncidentEnumsParam(incidentEnumParamsVO);
		}

		return super.getIncidentEnumsParam();
	}
}