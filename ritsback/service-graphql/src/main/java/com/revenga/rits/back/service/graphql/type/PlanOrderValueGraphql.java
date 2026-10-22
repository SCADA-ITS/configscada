package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.PlanOrderTypeParam;
import com.revenga.rits.back.data.core.model.PlanOrderValue;
import com.revenga.rits.back.front.core.model.helper.PlanOrderValueHelper;
import com.revenga.rits.back.front.core.model.vo.PlanOrderTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderValueVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class PlanOrderValueGraphql extends PlanOrderValueVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3908796455858736265L;
	private PlanOrderValue planOrderValue;

	public PlanOrderValueGraphql(final PlanOrderValue planOrderValue) {

		PlanOrderValueHelper.assign(planOrderValue, this);
		this.planOrderValue = planOrderValue;
	}

	@Override
	public PlanOrderTypeParamVO getPlanOrderTypeParam() {

		if (super.getPlanOrderTypeParam() == null) {

			if (planOrderValue != null) {

				PlanOrderTypeParam planOrderTypeParam = EntitiesManager.getInstance().getPlanOrderTypeParamRepository()
						.get(planOrderValue.getPlanOrderTypeId(), planOrderValue.getPlanOrderTypeParamId(), false);

				if (planOrderTypeParam != null) {

					super.setPlanOrderTypeParam(new PlanOrderTypeParamGraphql(planOrderTypeParam));
				}
			} else {

				log.error("planOrderValue has null pointer");
			}

		}

		return super.getPlanOrderTypeParam();
	}

}
