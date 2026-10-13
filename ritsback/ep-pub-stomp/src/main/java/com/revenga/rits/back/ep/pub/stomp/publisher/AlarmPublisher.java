package com.revenga.rits.back.ep.pub.stomp.publisher;

import java.time.Instant;
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

					for (Alarm alarm : alarms) {

						// Filter if elements of the alarm are Visible or not.
						Element sourceElement = EntitiesManager.getInstance()
								.getElement(alarm.getSourceElementTypeId(), alarm.getSourceElementId());

						Element targetElement = null;

						if (sourceElement != null && sourceElement.getElementTypeId().equals(alarm.getTargetElementTypeId()) 
								&& sourceElement.getId().equals(alarm.getTargetElementId()) ) {

							targetElement = sourceElement;

						} else {

							targetElement = EntitiesManager.getInstance().getElement(alarm.getTargetElementTypeId(),
									alarm.getTargetElementId());
						}

						if (alarm != null && sourceElement != null && targetElement != null
								&& (sourceElement.getVisible() == null || sourceElement.getVisible().booleanValue())
								&& (targetElement.getVisible() == null || targetElement.getVisible().booleanValue())) {
							
							AlarmVO alarmVO = AlarmHelper.get(alarm);

							AlarmConfig alarmConfig = EntitiesManager.getInstance()
									.getAlarmConfig(alarm.getAlarmConfigId());

							if (alarmConfig != null && alarmConfig.getAlarmTypeId() != null
									&& alarmConfig.getAlarmLevelId() != null) {

								alarm.setAlarmConfig(alarmConfig);

								AlarmConfigVO alarmConfigVO = new AlarmConfigVO();
								alarmConfigVO.setId(BasicElementHelper.getIdFromElement(alarm.getAlarmConfig()));
								alarmVO.setAlarmConfig(alarmConfigVO);

								alarm.setSourceElement(EntitiesManager.getInstance()
										.getElement(alarm.getSourceElementTypeId(), alarm.getSourceElementId()));

								if (alarm.getSourceElement() != null) {

									ElementVO sourceElementVO = new ElementVO();
									sourceElementVO.setId(BasicElementHelper.getIdFromElement(alarm.getSourceElement()));
									alarmVO.setSourceElement(sourceElementVO);
								}

								alarm.setTargetElement(EntitiesManager.getInstance()
										.getElement(alarm.getTargetElementTypeId(), alarm.getTargetElementId()));

								if (alarm.getTargetElement() != null) {

									ElementVO targetElementVO = new ElementVO();
									targetElementVO.setId(BasicElementHelper.getIdFromElement(alarm.getTargetElement()));
									alarmVO.setTargetElement(targetElementVO);
								}

								alarmVO.setAlarmConfigId(null);
								alarmVO.setTargetElementId(null);
								alarmVO.setSourceElementId(null);

								StompMessage stompMessage = preparedElements
										.get(alarmConfig.getAlarmTypeId() + "." + alarmConfig.getAlarmLevelId());

								if (stompMessage == null) {

									stompMessage = new StompMessage();
									stompMessage.setTimestamp(Instant.now().getEpochSecond());
									stompMessage.setCustomDestination(TOPIC_ALARMS + "." + alarmConfig.getAlarmTypeId()
											+ "." + alarmConfig.getAlarmLevelId());
								}

								stompMessage.getBasicElementsVO().add(alarmVO);

								preparedElements.put(alarmConfig.getAlarmTypeId() + "." + alarmConfig.getAlarmLevelId(),
										stompMessage);
							}
						}
					}
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}
}