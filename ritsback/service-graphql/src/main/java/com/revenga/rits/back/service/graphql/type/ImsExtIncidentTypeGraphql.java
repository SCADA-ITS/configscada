package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.ImsExtIncidentGroup;
import com.revenga.rits.back.data.core.model.ImsExtIncidentType;
import com.revenga.rits.back.data.core.model.ImsExtIncidentTypeParam;
import com.revenga.rits.back.front.core.model.helper.ImsExtIncidentTypeHelper;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentGroupVO;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentTypeVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsExtIncidentTypeGraphql extends ImsExtIncidentTypeVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4393879248479589435L;
	
	private ImsExtIncidentType extIncidentType;

	public ImsExtIncidentTypeGraphql(final ImsExtIncidentType extIncidentType) {

		ImsExtIncidentTypeHelper.assign(extIncidentType, this);
		this.extIncidentType = extIncidentType;
	}
	
	@Override
	public ImsExtIncidentGroupVO getExtIncidentGroup() {

		if (super.getExtIncidentGroup() == null) {

			if (ObjectUtils.allNotNull(extIncidentType)) {

				ImsExtIncidentGroup extIncidentGroup = EntitiesManager.getInstance().getImsExtIncidentGroupRepository()
						.get(extIncidentType.getExtIncidentGroupId(), false);

				if (extIncidentGroup != null) {

					super.setExtIncidentGroup(new ImsExtIncidentGroupGraphql(extIncidentGroup));
				}
			}

		}

		return super.getExtIncidentGroup();
	}

	@Override
	public List<ImsExtIncidentTypeParamVO> getExtIncidentTypeParams() {

		if (super.getExtIncidentTypeParams() == null) {

			if (extIncidentType != null) {

				List<ImsExtIncidentTypeParam> imsExtIncidentTypeParams = EntitiesManager.getInstance()
						.getImsExtIncidentTypeParamRepository().getsByExtIncidentType(extIncidentType.getId(), false);

				if (!CollectionUtils.isEmpty(imsExtIncidentTypeParams)) {

					List<ImsExtIncidentTypeParamVO> imsExtIncidentTypeParamVO = new ArrayList<>();

					for (ImsExtIncidentTypeParam imsExtIncidentTypeParam : imsExtIncidentTypeParams) {

						imsExtIncidentTypeParamVO.add(new ImsExtIncidentTypeParamGraphql(imsExtIncidentTypeParam));
					}
					super.setExtIncidentTypeParams(imsExtIncidentTypeParamVO);
				}

			} else {

				log.error("extIncidentType has null pointer");
			}
		}

		return super.getExtIncidentTypeParams();
	}
	
	

}
