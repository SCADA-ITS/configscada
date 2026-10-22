package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanNode;
import com.revenga.rits.back.front.core.model.helper.PlanNodeHelper;
import com.revenga.rits.back.front.core.model.vo.PlanNodeVO;
import com.revenga.rits.back.front.core.model.vo.PlanVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@EqualsAndHashCode(callSuper = false)
@Log4j2
public class PlanNodeGraphql extends PlanNodeVO {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1555576839706951897L;

	private PlanNode planNode;


	public PlanNodeGraphql(final PlanNode planNode) {

		PlanNodeHelper.assign(planNode, this);
		this.planNode = planNode;
	}


	@Override
	public PlanVO getPlan() {

		if (super.getPlan() == null && this.planNode != null) {

				Plan plan = EntitiesManager.getInstance().getPlanRepository().get(this.planNode.getPlanId(), false);
						
				if (plan != null) {
					
					PlanVO planVO = new PlanGraphql(plan);

					super.setPlan(planVO);
				}
		} 
		else if(this.planNode == null) {
			log.error("planNode has null pointer");
		}
		
		return super.getPlan();
	}
}
