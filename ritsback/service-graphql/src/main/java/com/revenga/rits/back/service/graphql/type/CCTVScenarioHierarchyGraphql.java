package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.CCTVScenarioHierarchy;
import com.revenga.rits.back.data.core.model.CCTVScenarioNode;
import com.revenga.rits.back.front.core.model.helper.CCTVScenarioHierarchyHelper;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioHierarchyVO;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioNodeVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@EqualsAndHashCode(callSuper = false)
@Log4j2
public class CCTVScenarioHierarchyGraphql extends CCTVScenarioHierarchyVO {



	/**
	 * 
	 */
	private static final long serialVersionUID = 6741231576599917872L;

	private CCTVScenarioHierarchy scenarioHierarchy;

	private MapRequests mapRequests;

	public CCTVScenarioHierarchyGraphql(final CCTVScenarioHierarchy scenarioHierarchy, MapRequests mapRequests) {

		CCTVScenarioHierarchyHelper.assign(scenarioHierarchy, this);

		this.scenarioHierarchy = scenarioHierarchy;
		this.mapRequests = mapRequests;
	}

	public CCTVScenarioHierarchyGraphql(final CCTVScenarioHierarchy scenarioHierarchy) {

		CCTVScenarioHierarchyHelper.assign(scenarioHierarchy, this);

		this.scenarioHierarchy = scenarioHierarchy;
		this.mapRequests = null;
	}


	@Override
	public CCTVScenarioNodeVO getChild() {

		if (super.getChild() == null && this.scenarioHierarchy != null) {

				CCTVScenarioNode scenarioNodeChild = EntitiesManager.getInstance().getCctvScenarioNodeRepository().get(this.scenarioHierarchy.getNodeChildId(), false);
						
				if (scenarioNodeChild != null) {
					
					CCTVScenarioNodeVO scenarioNodeChildVO = new CCTVScenarioNodeGraphql(scenarioNodeChild);

					super.setChild(scenarioNodeChildVO);
				}
		} 
		else if(this.scenarioHierarchy == null) {
			log.error("scenarioHierarchy has null pointer");
		}
		
		return super.getChild();
	}
	
	@Override
	public CCTVScenarioNodeVO getParent() {

		if (super.getParent() == null && this.scenarioHierarchy != null) {

			CCTVScenarioNode scenarioNodeParent = EntitiesManager.getInstance().getCctvScenarioNodeRepository().get(this.scenarioHierarchy.getNodeParentId(), false);
					
			if (scenarioNodeParent != null) {
				
				CCTVScenarioNodeVO scenarioNodeParentVO = new CCTVScenarioNodeGraphql(scenarioNodeParent);

				super.setParent(scenarioNodeParentVO);
			}
		} 
		else if(this.scenarioHierarchy == null) {
			log.error("scenarioHierarchy has null pointer");
		}
	
		return super.getParent();
	}
	
}
