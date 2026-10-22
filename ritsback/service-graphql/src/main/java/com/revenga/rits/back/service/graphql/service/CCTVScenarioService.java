package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CCTVScenario;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.type.CCTVScenarioGraphql;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class CCTVScenarioService implements QueryInterface<CCTVScenarioVO> {

	@Override
	public List<CCTVScenarioVO> getAll() {

		List<CCTVScenarioVO> cctvScenariosVO = new ArrayList<>();

		List<CCTVScenario> scenarios = EntitiesManager.getInstance().getCctvScenarioRepository().getAll(false);

		if (!CollectionUtils.isEmpty(scenarios)) {

			MapRequests mapRequests =  new MapRequests(MapRequests.Mode.GET_ALL);
			for (CCTVScenario scenario : scenarios) {

				cctvScenariosVO.add(new CCTVScenarioGraphql(scenario, mapRequests));
			}
		}

		return cctvScenariosVO;
	}

	@Override
	public Optional<CCTVScenarioVO> get(String scenarioId) {

		Optional<CCTVScenarioVO> cctvScenarioVO = Optional.empty();
		
		try {
			Key key = new Key(scenarioId);
			
			if (key.getClazz() == CCTVScenario.class) {

				CCTVScenario cctvScenario = EntitiesManager.getInstance().getCctvScenarioRepository().get(Long.valueOf(key.getId(1)), false);

				if (cctvScenario != null) {

					cctvScenarioVO = Optional.of(new CCTVScenarioGraphql(cctvScenario));
				}
			}			
			
		} catch (Exception e) {

			log.error("The id does not correspond to the required object: scenarioId");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return cctvScenarioVO;
	}

	@Override
	public List<CCTVScenarioVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
}
