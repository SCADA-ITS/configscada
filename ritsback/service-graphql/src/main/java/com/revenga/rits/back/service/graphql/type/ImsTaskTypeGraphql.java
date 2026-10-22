package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import com.revenga.rits.back.data.core.model.ImsTaskType;
import com.revenga.rits.back.data.core.model.ImsTaskTypeParam;
import com.revenga.rits.back.front.core.model.helper.ImsTaskTypeHelper;
import com.revenga.rits.back.front.core.model.vo.ImsTaskTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ImsTaskTypeVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class ImsTaskTypeGraphql extends ImsTaskTypeVO  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1232094218279832297L;

	/**
	 * 
	 */
	private ImsTaskType imsTaskType;

	/**
	 * 
	 * @param imsTaskType
	 */
	public ImsTaskTypeGraphql(final ImsTaskType imsTaskType) {

		ImsTaskTypeHelper.assign(imsTaskType, this);
		this.imsTaskType = imsTaskType;
	}
	
	/**
	 * 
	 */
	public List<ImsTaskTypeParamVO> getTaskTypeParams() {
		
		if (super.getTaskTypeParams() == null && imsTaskType != null && imsTaskType.getId() != null) {

			List<ImsTaskTypeParam> imsTaskTypeParams = EntitiesManager.getInstance().getImsTaskTypeParamRepository().gets(imsTaskType.getId(), false);

			if (!CollectionUtils.isEmpty(imsTaskTypeParams)) {

				List<ImsTaskTypeParamVO> imsTaskTypeParamsVO = new ArrayList<>();

				for (ImsTaskTypeParam imsTaskTypeParam : imsTaskTypeParams) {

				
					imsTaskTypeParamsVO.add(new ImsTaskTypeParamGraphql(imsTaskTypeParam));
				}
				
				super.setTaskTypeParams(imsTaskTypeParamsVO);
			}
		}
		
		return super.getTaskTypeParams();		
	}
}
