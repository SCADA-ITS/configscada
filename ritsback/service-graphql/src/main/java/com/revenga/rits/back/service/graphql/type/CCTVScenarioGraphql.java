package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.CCTVScenario;
import com.revenga.rits.back.data.core.model.CCTVScenarioElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.front.core.model.helper.CCTVScenarioHelper;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@EqualsAndHashCode(callSuper = false)
@Log4j2
public class CCTVScenarioGraphql extends CCTVScenarioVO {



	/**
	 * 
	 */
	private static final long serialVersionUID = -1555576839706951897L;

	private CCTVScenario scenario;

	private MapRequests mapRequests;

	public CCTVScenarioGraphql(final CCTVScenario cctvScenario, MapRequests mapRequests) {

		CCTVScenarioHelper.assign(cctvScenario, this);

		this.scenario = cctvScenario;
		this.mapRequests = mapRequests;
	}

	public CCTVScenarioGraphql(final CCTVScenario cctvScenario) {

		CCTVScenarioHelper.assign(cctvScenario, this);

		this.scenario = cctvScenario;
		this.mapRequests = null;
	}


	@Override
	public List<ElementVO> getElements() {

		if (super.getElements() == null && this.scenario != null) {

				List<CCTVScenarioElement> scenarioElements = EntitiesManager.getInstance()
						.getCctvScenarioElementRepository().getsByScenario(this.scenario.getId(), false).stream()
						  .sorted(Comparator.comparing(CCTVScenarioElement::getNOrder))
						  .collect(Collectors.toList());
						
				if (!CollectionUtils.isEmpty(scenarioElements)) {
					
					List<ElementVO> elementsVO = new ArrayList<>();

					for (CCTVScenarioElement scenarioElement : scenarioElements) {

						Element element = EntitiesManager.getInstance().getElementRepository().get(
								scenarioElement.getElementTypeId(),
								scenarioElement.getElementId(), false);

						if (element != null) {

							elementsVO.add(new ElementGraphql(element));
						}
					}

					super.setElements(elementsVO);
				}
		} 
		else if(this.scenario == null) {
			log.error("scenario has null pointer");
		}
		
		return super.getElements();
	}
}
