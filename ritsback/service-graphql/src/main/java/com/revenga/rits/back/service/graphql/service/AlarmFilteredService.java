package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ManagementAreaElement;
import com.revenga.rits.back.data.core.model.command.SetAlarmsFilteredCommand;
import com.revenga.rits.back.front.core.model.helper.command.SetAlarmsFilteredCommandHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmFilteredVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.type.AlarmFilteredGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AlarmFilteredService implements QueryInterface<AlarmFilteredVO> {

	public List<AlarmFilteredVO> getAll(Long managementAreaId) {

		List<AlarmFilteredVO> alarmsFiltered = new ArrayList<>();

		MapRequests mapRequests = new MapRequests(MapRequests.Mode.GET_ALL);

		for (AlarmFiltered alarmFiltered : EntitiesManager.getInstance().getAlarmFilteredRepository().getAll(false)) {

			if (checkManagementArea(alarmFiltered, managementAreaId)) {

				alarmsFiltered.add(new AlarmFilteredGraphql(alarmFiltered, mapRequests));
			}

		}

		return alarmsFiltered;
	}

	@Override
	public Optional<AlarmFilteredVO> get(String alarmFilteredId) {

		Optional<AlarmFilteredVO> alarmFilteredVO = null;

		MapRequests mapRequests = new MapRequests(MapRequests.Mode.GET_ALL);

		try {
			Key key = new Key(alarmFilteredId);

			if (key.getClazz() == AlarmFiltered.class) {

				AlarmFiltered alarmFiltered = EntitiesManager.getInstance().getAlarmFilteredRepository()
						.get(Long.valueOf(key.getId(1)), Long.valueOf(key.getId(2)), Long.valueOf(key.getId(3)), false);

				if (alarmFiltered != null) {

					alarmFilteredVO = Optional.of(new AlarmFilteredGraphql(alarmFiltered, mapRequests));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: AlarmFiltered");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmFilteredVO;
	}

	public List<AlarmFilteredVO> getAlarmFilteredByAlarmConfig(String alarmConfigId, Long managementAreaId) {

		List<AlarmFilteredVO> alarmsFilteredVO = new ArrayList<>();

		MapRequests mapRequests = new MapRequests(MapRequests.Mode.GET_ALL);

		try {
			Key key = new Key(alarmConfigId);

			if (key.getClazz() == AlarmConfig.class) {

				for (AlarmFiltered alarmFiltered : EntitiesManager.getInstance().getAlarmFilteredRepository()
						.getsByAlarmConfig(Long.valueOf(key.getId(1)), false)) {

					if (checkManagementArea(alarmFiltered, managementAreaId)) {

						alarmsFilteredVO.add(new AlarmFilteredGraphql(alarmFiltered, mapRequests));
					}
				}

			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: AlarmFiltered");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmsFilteredVO;
	}

	public List<AlarmFilteredVO> getAlarmFilteredsByElement(String elementId) {

		List<AlarmFilteredVO> alarmsFilteredVO = new ArrayList<>();

		MapRequests mapRequests = new MapRequests(MapRequests.Mode.GET_ALL);

		try {
			Key key = new Key(elementId);

			if (key.getClazz() == Element.class) {

				for (AlarmFiltered alarmFiltered : EntitiesManager.getInstance().getAlarmFilteredRepository()
						.getsByElement(Long.valueOf(key.getId(1)), Long.valueOf(key.getId(2)), false)) {

					alarmsFilteredVO.add(new AlarmFilteredGraphql(alarmFiltered, mapRequests));
				}

			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: AlarmFiltered");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmsFilteredVO;
	}

	public Boolean sendAlarmFilteredActions(final List<AlarmFilteredVO> alarmsFilteredVO) {

		if (CollectionUtils.isEmpty(alarmsFilteredVO)) {

			return false;
		}

		SetAlarmsFilteredCommand setAlarmsFilteredCommand = SetAlarmsFilteredCommandHelper.get(alarmsFilteredVO);

		if (setAlarmsFilteredCommand != null) {

			EntitiesManager.getInstance().sendCommand(setAlarmsFilteredCommand);

		} else {

			return false;
		}

		return true;
	}

	@Override
	public List<AlarmFilteredVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AlarmFilteredVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean checkManagementArea(final AlarmFiltered alarmFiltered, final Long managementAreaId) {

		List<ManagementAreaElement> managementAreaElements = EntitiesManager.getInstance()
				.getManagementAreaElementRepository()
				.gets(alarmFiltered.getElementTypeId(), alarmFiltered.getElementId(), false);

		return CollectionUtils.isEmpty(managementAreaElements) || (managementAreaId != null
				&& managementAreaElements.stream().filter(e -> e.getId().equals(managementAreaId)).count() > 0);
	}

}
