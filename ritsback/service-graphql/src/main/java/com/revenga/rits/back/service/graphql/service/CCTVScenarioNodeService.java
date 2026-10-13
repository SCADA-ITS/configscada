package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CCTVScenario;
import com.revenga.rits.back.data.core.model.CCTVScenarioNode;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioNodeVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.type.CCTVScenarioNodeGraphql;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class CCTVScenarioNodeService implements QueryInterface<CCTVScenarioNodeVO> {

	@Override
	public List<CCTVScenarioNodeVO> getAll() {

		List<CCTVScenarioNodeVO> cctvScenarioNodesVO = new ArrayList<>();

		List<CCTVScenarioNode> scenarioNodes = EntitiesManager.getInstance().getCctvScenarioNodeRepository().getAll(false);

		if (!CollectionUtils.isEmpty(scenarioNodes)) {

			MapRequests mapRequests =  new MapRequests(MapRequests.Mode.GET_ALL);
			for (CCTVScenarioNode scenarioNode : scenarioNodes) {

				cctvScenarioNodesVO.add(new CCTVScenarioNodeGraphql(scenarioNode, mapRequests));
			}
		}

		return cctvScenarioNodesVO;
	}

	@Override
	public Optional<CCTVScenarioNodeVO> get(String scenarioId) {

		Optional<CCTVScenarioNodeVO> cctvScenarioNodeVO = null;
		
		try {
			Key key = new Key(scenarioId);
			
			if (key.getClazz() == CCTVScenario.class) {

				CCTVScenarioNode cctvScenarioNode = EntitiesManager.getInstance().getCctvScenarioNodeRepository().get(Long.valueOf(key.getId(1)), false);

				if (cctvScenarioNode != null) {

					cctvScenarioNodeVO = Optional.of(new CCTVScenarioNodeGraphql(cctvScenarioNode));
				}
			}			
			
		} catch (Exception e) {

			log.error("The id does not correspond to the required object: scenarioId");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return cctvScenarioNodeVO;
	}

	@Override
	public List<CCTVScenarioNodeVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
}
