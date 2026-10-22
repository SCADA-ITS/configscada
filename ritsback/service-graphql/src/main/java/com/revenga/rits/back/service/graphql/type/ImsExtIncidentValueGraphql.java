package com.revenga.rits.back.service.graphql.type;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.ImsExtIncidentTypeParam;
import com.revenga.rits.back.data.core.model.ImsExtIncidentValue;
import com.revenga.rits.back.front.core.model.helper.ImsExtIncidentValueHelper;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentValueVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class ImsExtIncidentValueGraphql extends ImsExtIncidentValueVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8738811235123119217L;
	private ImsExtIncidentValue imsExtIncidentValue;

	public ImsExtIncidentValueGraphql(final ImsExtIncidentValue imsExtIncidentValue) {

		ImsExtIncidentValueHelper.assign(imsExtIncidentValue, this);

		this.imsExtIncidentValue = imsExtIncidentValue;
	}

	@Override
	public ImsExtIncidentTypeParamVO getExtIncidentTypeParam() {

		if (super.getExtIncidentTypeParam() == null) {

			if (ObjectUtils.allNotNull(imsExtIncidentValue)) {

				ImsExtIncidentTypeParam extIncidentTypeParam = EntitiesManager.getInstance()
						.getImsExtIncidentTypeParamRepository()
						.get(imsExtIncidentValue.getExtIncidentTypeId(), imsExtIncidentValue.getExtIncidentTypeParamId(), false);

				if (extIncidentTypeParam != null) {

					super.setExtIncidentTypeParam(new ImsExtIncidentTypeParamGraphql(extIncidentTypeParam));
				}
			}

		}

		return super.getExtIncidentTypeParam();
	}

}
