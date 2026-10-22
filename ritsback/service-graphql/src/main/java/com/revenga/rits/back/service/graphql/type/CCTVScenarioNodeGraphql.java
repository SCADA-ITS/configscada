package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.CCTVScenario;
import com.revenga.rits.back.data.core.model.CCTVScenarioNode;
import com.revenga.rits.back.front.core.model.helper.CCTVScenarioNodeHelper;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioNodeVO;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@EqualsAndHashCode(callSuper = false)
@Log4j2
public class CCTVScenarioNodeGraphql extends CCTVScenarioNodeVO {



	/**
	 * 
	 */
	private static final long serialVersionUID = -1555576839706951897L;

	private CCTVScenarioNode scenarioNode;

	private MapRequests mapRequests;

	public CCTVScenarioNodeGraphql(final CCTVScenarioNode cctvScenarioNode, MapRequests mapRequests) {

		CCTVScenarioNodeHelper.assign(cctvScenarioNode, this);

		this.scenarioNode = cctvScenarioNode;
		this.mapRequests = mapRequests;
	}

	public CCTVScenarioNodeGraphql(final CCTVScenarioNode cctvScenarioNode) {

		CCTVScenarioNodeHelper.assign(cctvScenarioNode, this);

		this.scenarioNode = cctvScenarioNode;
		this.mapRequests = null;
	}


	@Override
	public CCTVScenarioVO getScenario() {

		if (super.getScenario() == null && this.scenarioNode != null) {

				CCTVScenario scenario = EntitiesManager.getInstance().getCctvScenarioRepository().get(this.scenarioNode.getScenarioId(), false);
						
				if (scenario != null) {
					
					CCTVScenarioVO scenarioVO = new CCTVScenarioGraphql(scenario);

					super.setScenario(scenarioVO);
				}
		} 
		else if(this.scenarioNode == null) {
			log.error("scenarioNode has null pointer");
		}
		
		return super.getScenario();
	}
}
