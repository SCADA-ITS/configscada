package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentGroup;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentGroupVO;
import com.revenga.rits.back.service.graphql.type.ImsIncidentGroupGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ImsIncidentGroupService implements QueryInterface<ImsIncidentGroupVO> {
	
	@Override
	public List<ImsIncidentGroupVO> getAll() {

		List<ImsIncidentGroupVO> incidentGroups = new ArrayList<>();

		for (ImsIncidentGroup ImsIncidentGroup : EntitiesManager.getInstance().getImsIncidentGroupRepository()
				.getAll(false)) {

			incidentGroups.add(new ImsIncidentGroupGraphql(ImsIncidentGroup));
		}

		return incidentGroups;
	}

	@Override
	public Optional<ImsIncidentGroupVO> get(String ImsIncidentGroupId) {

		Optional<ImsIncidentGroupVO> ImsIncidentGroupVO = null;

		try {
			Key key = new Key(ImsIncidentGroupId);

			if (key.getClazz() == ImsIncidentGroup.class) {

				ImsIncidentGroup ImsIncidentGroup = EntitiesManager.getInstance().getImsIncidentGroupRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (ImsIncidentGroup != null) {

					ImsIncidentGroupVO = Optional.of(new ImsIncidentGroupGraphql(ImsIncidentGroup));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ImsIncidentGroup");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return ImsIncidentGroupVO;
	}

	@Override
	public List<ImsIncidentGroupVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
