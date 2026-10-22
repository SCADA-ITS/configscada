package com.revenga.rits.back.service.graphql.type;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmFiltered;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.entities.lib.repository.AlarmRepository;
import com.revenga.rits.back.front.core.model.helper.AlarmFilteredHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.AlarmFilteredVO;
import com.revenga.rits.back.front.core.model.vo.AlarmVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.UserVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class AlarmFilteredGraphql extends AlarmFilteredVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4407504405154188276L;

	private AlarmFiltered alarmFiltered;

	private MapRequests mapRequests;

	public AlarmFilteredGraphql(final AlarmFiltered alarmFiltered) {

		AlarmFilteredHelper.assign(alarmFiltered, this);

		this.alarmFiltered = alarmFiltered;
		this.mapRequests = null;
	}

	public AlarmFilteredGraphql(final AlarmFiltered alarmFiltered, MapRequests mapRequests) {

		AlarmFilteredHelper.assign(alarmFiltered, this);

		this.alarmFiltered = alarmFiltered;
		this.mapRequests = mapRequests;
	}

	@Override
	public AlarmConfigVO getAlarmConfig() {

		if (super.getAlarmConfig() == null) {

			if (ObjectUtils.allNotNull(alarmFiltered)) {

				AlarmConfig alarmConfig = EntitiesManager.getInstance().getAlarmConfigRepository()
						.get(alarmFiltered.getAlarmConfigId(), false);

				if (alarmConfig != null) {

					super.setAlarmConfig(new AlarmConfigGraphql(alarmConfig));
				}
			}

		}

		return super.getAlarmConfig();
	}

	@Override
	public ElementVO getElement() {

		if (super.getElement() == null) {

			if (ObjectUtils.allNotNull(alarmFiltered)) {

				Element element = EntitiesManager.getInstance().getElementRepository()
						.get(alarmFiltered.getElementTypeId(), alarmFiltered.getElementId(), false);

				if (element != null) {

					super.setElement(new ElementGraphql(element));
				}
			}

		}

		return super.getElement();
	}

	@Override
	public AlarmVO getAlarm() {

		if (super.getAlarm() == null) {

			if (alarmFiltered != null) {

				Alarm alarm = null;

				if (mapRequests == null) {

					List<Alarm> alarms = EntitiesManager.getInstance().getAlarmRepository().getAll(false);

					if (!CollectionUtils.isEmpty(alarms)) {

						alarm = alarms.stream()
								.filter(be -> be.getAlarmConfigId() != null
										&& be.getAlarmConfigId().equals(alarmFiltered.getAlarmConfigId())
										&& be.getTargetElementTypeId() != null
										&& be.getTargetElementTypeId().equals(alarmFiltered.getElementTypeId())
										&& be.getTargetElementId() != null
										&& be.getTargetElementId().equals(alarmFiltered.getElementId()))
								.findFirst().orElse(null);
					}

				} else {

					List<BasicElement> basicElement = null;

					if (mapRequests.isGetAll()) {

						basicElement = mapRequests.get(AlarmRepository.class, "getAll", null);

					}

					if (!CollectionUtils.isEmpty(basicElement)) {

						alarm = (Alarm) basicElement.stream()
								.filter(be -> ((Alarm) be).getAlarmConfigId() != null
										&& ((Alarm) be).getAlarmConfigId().equals(alarmFiltered.getAlarmConfigId())
										&& ((Alarm) be).getTargetElementTypeId() != null
										&& ((Alarm) be).getTargetElementTypeId().equals(alarmFiltered.getElementTypeId())
										&& ((Alarm) be).getTargetElementId() != null
										&& ((Alarm) be).getTargetElementId().equals(alarmFiltered.getElementId()))
								.findFirst().orElse(null);
					}
				}

				if (alarm != null) {

					super.setAlarm(new AlarmGraphql(alarm));
				}

			} else {

				log.error("element has null pointer");
			}
		}

		return super.getAlarm();
	}
	
	@Override
	public UserVO getUser() {

		if (super.getUser() == null && alarmFiltered != null && alarmFiltered.getUserId() != null) {

			User user = EntitiesManager.getInstance().getUserRepository().get(alarmFiltered.getUserId(), false);

			if (user != null) {

				super.setUser(new UserGraphql(user));
			}
		}

		return super.getUser();
	}
}
