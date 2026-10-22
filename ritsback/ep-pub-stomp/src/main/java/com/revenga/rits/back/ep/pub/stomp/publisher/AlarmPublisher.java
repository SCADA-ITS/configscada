package com.revenga.rits.back.ep.pub.stomp.publisher;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.messaging.simp.stomp.StompSession;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher.Topics.Topic;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ManagementArea;
import com.revenga.rits.back.data.core.model.command.SetAlarmsPublishCommand;
import com.revenga.rits.back.ep.pub.stomp.service.ElementCacheManager;
import com.revenga.rits.back.ep.pub.stomp.service.EntitiesManager;
import com.revenga.rits.back.front.core.model.helper.AlarmHelper;
import com.revenga.rits.back.front.core.model.helper.BasicElementHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.AlarmVO;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.StompMessage;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmPublisher extends GenericPublisher {

	private static final String TOPIC_ALARMS = "/topic/alarm";

	public AlarmPublisher(StompSession stompSession, Topic topic, ElementCacheManager elementCacheManager) {

		super(stompSession, topic, elementCacheManager);
	}

	@Override
	protected BasicElementVO prepare(Pair<Object, Message> queueElement) {

		try {

			if (queueElement != null && queueElement.getKey() instanceof SetAlarmsPublishCommand) {

				SetAlarmsPublishCommand setAlarmsPublishCommand = (SetAlarmsPublishCommand) queueElement.getKey();

				Set<Alarm> alarms = setAlarmsPublishCommand.getAlarms();

				if (!CollectionUtils.isEmpty(alarms)) {

					alarms.forEach(item -> prepareAlarm(item, setAlarmsPublishCommand.getManagementAreaIds()));
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

	private void prepareAlarm(@NonNull Alarm alarm, List<Long> managementAreaIds) {

		Element sourceElement = null;
		Element targetElement = null;

		if (alarm.getSourceElementTypeId() != null && alarm.getSourceElementId() != null) {

			sourceElement = EntitiesManager.getInstance().getElement(alarm.getSourceElementTypeId(),
					alarm.getSourceElementId());

			if (sourceElement.getVisible() != null && !sourceElement.getVisible().booleanValue()) {

				sourceElement = null;
			}
		}

		if (alarm.getTargetElementTypeId() != null && alarm.getTargetElementId() != null) {

			targetElement = EntitiesManager.getInstance().getElement(alarm.getTargetElementTypeId(),
					alarm.getTargetElementId());

			if (targetElement.getVisible() != null && !targetElement.getVisible().booleanValue()) {

				targetElement = null;
			}
		}

		AlarmVO alarmVO = AlarmHelper.get(alarm);

		if (sourceElement != null) {

			alarm.setSourceElement(sourceElement);

			ElementVO sourceElementVO = new ElementVO();
			sourceElementVO.setId(BasicElementHelper.getIdFromElement(sourceElement));
			alarmVO.setSourceElement(sourceElementVO);
		}

		if (targetElement != null) {

			alarm.setTargetElement(targetElement);

			ElementVO targetElementVO = new ElementVO();
			targetElementVO.setId(BasicElementHelper.getIdFromElement(targetElement));
			alarmVO.setTargetElement(targetElementVO);
		}

		alarmVO.setAlarmConfigId(null);
		alarmVO.setTargetElementId(null);
		alarmVO.setSourceElementId(null);

		Long alarmTypeId = 0L;
		Long alarmLevelId = 0L;

		AlarmConfig alarmConfig = EntitiesManager.getInstance().getAlarmConfig(alarm.getAlarmConfigId());

		if (alarmConfig != null && alarmConfig.getAlarmTypeId() != null && alarmConfig.getAlarmLevelId() != null) {

			alarm.setAlarmConfig(alarmConfig);

			AlarmConfigVO alarmConfigVO = new AlarmConfigVO();
			alarmConfigVO.setId(BasicElementHelper.getIdFromElement(alarm.getAlarmConfig()));
			alarmVO.setAlarmConfig(alarmConfigVO);

			alarmTypeId = alarmConfig.getAlarmTypeId();
			alarmLevelId = alarmConfig.getAlarmLevelId();
		}

		if (CollectionUtils.isNotEmpty(managementAreaIds)) {

			for (Long managementAreaId : managementAreaIds) {
				
				addAlarm(alarmTypeId, alarmLevelId, managementAreaId, alarmVO);
			}
			
		} else if (managementAreaIds == null) {
			addAlarm(alarmTypeId, alarmLevelId, null, alarmVO);
			
			 List<ManagementArea> managementAreas = EntitiesManager.getInstance().getManagementAreas();
			 
			 if (CollectionUtils.isNotEmpty(managementAreas)){
				 
				 for(ManagementArea managementArea : managementAreas) {
					 
					 addAlarm(alarmTypeId, alarmLevelId, managementArea.getId(), alarmVO);
				 }	 
			 }
		} else {
			addAlarm(alarmTypeId, alarmLevelId, null, alarmVO);
		}

	}

	private void addAlarm(final Long alarmTypeId, final Long alarmLevelId, final Long managementAreaId,
			final AlarmVO alarmVO) {

		StompMessage stompMessage = managementAreaId != null
				? preparedElements.get(alarmTypeId + "." + alarmLevelId + "." + managementAreaId)
				: preparedElements.get(alarmTypeId + "." + alarmLevelId);

		if (stompMessage == null) {

			stompMessage = new StompMessage();
			stompMessage.setTimestamp(Instant.now().getEpochSecond());
			stompMessage.setCustomDestination(TOPIC_ALARMS + "." + alarmTypeId + "." + alarmLevelId);

			if (managementAreaId != null) {

				stompMessage.setManagementAreaIds(Arrays.asList(managementAreaId));
			} else {
				stompMessage.setManagementAreaIds(new ArrayList<>());
			}
		}

		stompMessage.getBasicElementsVO().add(alarmVO);

		if (managementAreaId != null) {

			preparedElements.put(alarmTypeId + "." + alarmLevelId + "." + managementAreaId, stompMessage);

		} else {
			preparedElements.put(alarmTypeId + "." + alarmLevelId, stompMessage);
		}

	}
}