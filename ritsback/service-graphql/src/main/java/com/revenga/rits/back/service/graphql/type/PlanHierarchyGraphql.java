package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.PlanHierarchy;
import com.revenga.rits.back.data.core.model.PlanNode;
import com.revenga.rits.back.front.core.model.helper.PlanHierarchyHelper;
import com.revenga.rits.back.front.core.model.vo.PlanHierarchyVO;
import com.revenga.rits.back.front.core.model.vo.PlanNodeVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@EqualsAndHashCode(callSuper = false)
@Log4j2
public class PlanHierarchyGraphql extends PlanHierarchyVO {



	/**
	 * 
	 */
	private static final long serialVersionUID = 6741231576599917872L;

	private PlanHierarchy planHierarchy;

	public PlanHierarchyGraphql(final PlanHierarchy planHierarchy) {

		PlanHierarchyHelper.assign(planHierarchy, this);

		this.planHierarchy = planHierarchy;
	}

	@Override
	public PlanNodeVO getChild() {

		if (super.getChild() == null && this.planHierarchy != null) {

				PlanNode planNodeChild = EntitiesManager.getInstance().getPlanNodeRepository().get(this.planHierarchy.getNodeChildId(), false);
						
				if (planNodeChild != null) {
					
					PlanNodeVO planNodeChildVO = new PlanNodeGraphql(planNodeChild);

					super.setChild(planNodeChildVO);
				}
		} 
		else if(this.planHierarchy == null) {
			log.error("planHierarchy has null pointer");
		}
		
		return super.getChild();
	}
	
	@Override
	public PlanNodeVO getParent() {

		if (super.getParent() == null && this.planHierarchy != null) {

			PlanNode planNodeParent = EntitiesManager.getInstance().getPlanNodeRepository().get(this.planHierarchy.getNodeParentId(), false);
					
			if (planNodeParent != null) {
				
				PlanNodeVO planNodeParentVO = new PlanNodeGraphql(planNodeParent);

				super.setParent(planNodeParentVO);
			}
		} 
		else if(this.planHierarchy == null) {
			log.error("planHierarchy has null pointer");
		}
	
		return super.getParent();
	}
	
}
