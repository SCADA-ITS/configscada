package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.PlanOrderType;
import com.revenga.rits.back.data.core.model.PlanOrderTypeParam;
import com.revenga.rits.back.front.core.model.helper.PlanOrderTypeHelper;
import com.revenga.rits.back.front.core.model.vo.PlanOrderTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderTypeVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class PlanOrderTypeGraphql extends PlanOrderTypeVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3908796455858736265L;
	private PlanOrderType planOrderType;

	public PlanOrderTypeGraphql(final PlanOrderType planOrderType) {

		PlanOrderTypeHelper.assign(planOrderType, this);
		this.planOrderType = planOrderType;

	}

	@Override
	public List<PlanOrderTypeParamVO> getPlanOrderTypeParams() {

		if (super.getPlanOrderTypeParams() == null) {

			if (planOrderType != null) {

				List<PlanOrderTypeParam> params = EntitiesManager.getInstance().getPlanOrderTypeParamRepository()
						.getsByPlanOrderType(planOrderType.getId(), false);

				if (!CollectionUtils.isEmpty(params)) {

					List<PlanOrderTypeParamVO> planOrderTypeParamsVO = new ArrayList<>();

					for (PlanOrderTypeParam param : params) {

						planOrderTypeParamsVO.add(new PlanOrderTypeParamGraphql(param));
					}

					super.setPlanOrderTypeParams(planOrderTypeParamsVO);
				}
			} else {

				log.error("planOrderType has null pointer");
			}
		}
		return super.getPlanOrderTypeParams();
	}

}
