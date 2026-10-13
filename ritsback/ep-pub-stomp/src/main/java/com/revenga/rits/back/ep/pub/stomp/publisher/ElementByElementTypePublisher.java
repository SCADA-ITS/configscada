package com.revenga.rits.back.ep.pub.stomp.publisher;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.messaging.simp.stomp.StompSession;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher.Topics.Topic;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.command.SetAlarmsPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetElementStatesPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;
import com.revenga.rits.back.ep.pub.stomp.service.ElementCacheManager;
import com.revenga.rits.back.ep.pub.stomp.service.EntitiesManager;
import com.revenga.rits.back.front.core.model.helper.AlarmHelper;
import com.revenga.rits.back.front.core.model.helper.BasicElementHelper;
import com.revenga.rits.back.front.core.model.helper.ElementTypeStateHelper;
import com.revenga.rits.back.front.core.model.helper.ElementValueHelper;
import com.revenga.rits.back.front.core.model.helper.ModelVOHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.AlarmVO;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueVO;
import com.revenga.rits.back.front.core.model.vo.StompMessage;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementByElementTypePublisher extends GenericPublisher {
	
	private static final String TOPIC_ELEMENTS = "/topic/element";

	public ElementByElementTypePublisher(StompSession stompSession, Topic topic, ElementCacheManager elementCacheManager) {

		super(stompSession, topic, elementCacheManager);
	}

	@Override
	protected BasicElementVO prepare(Pair<Object, Message> queueElement) {

		try {
			
			if (queueElement != null) {

				if (queueElement.getKey() instanceof ElementValue) {

					prepareElementValue((ElementValue) queueElement.getKey());
				} 
				else if (queueElement.getKey() instanceof SetElementValuesPublishCommand) {
					
					prepareElementValue((SetElementValuesPublishCommand) queueElement.getKey());
				}
				else if (queueElement.getKey() instanceof ElementState) {

					prepareElementState((ElementState) queueElement.getKey());
				}
				else if (queueElement.getKey() instanceof SetElementStatesPublishCommand) {

					prepareElementState((SetElementStatesPublishCommand) queueElement.getKey());
				}
				else if (queueElement.getKey() instanceof SetAlarmsPublishCommand) {
					
					prepareAlarm((SetAlarmsPublishCommand) queueElement.getKey());
				}
			}
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}
	
	private BasicElementVO prepareElementValue(SetElementValuesPublishCommand setElementValuesPublishCommand) {
		
		BasicElementVO basicElementVO = null;
		
		if (!CollectionUtils.isEmpty(setElementValuesPublishCommand.getElementValues())) {
			
			for (ElementValue elementValue : setElementValuesPublishCommand.getElementValues()) {
				
				prepareElementValue(elementValue);
			}
		}
		 
		return basicElementVO;
	}
	
	private void prepareElementValue(ElementValue elementValue) {
		
		Element element = EntitiesManager.getInstance().getElement(elementValue.getElementTypeId(), elementValue.getElementId());
		
		if (element != null && (element.getVisible() == null || element.getVisible().booleanValue())) {
			
			if (elementValue.getParamTypeId().equals(Long.valueOf(ParamType.CONFIG))) {

				log.warn("Incompatible type ElementValue: " + (new Key(elementValue).getValue() + " => is of type ParamType.CONFIG"));
				
				return;
			}

			if (elementValue.getElementTypeParam() == null) {
			
				elementValue.setElementTypeParam(EntitiesManager.getInstance().getElementTypeParam(elementValue));
			}

			ElementVO elementVO = ElementValueHelper.getElementVO(elementValue);
			
			ElementValueVO elementValueVO = ElementValueHelper.get(elementValue);

			ElementType elementType = new ElementType();
			
			elementType.setId(elementValue.getElementTypeId());
			
			ElementTypeVO elementTypeVO = new ElementTypeVO();
			
			elementTypeVO.setId(BasicElementHelper.getIdFromElement(elementType));
			
			Key keyElementTypeId = new Key(elementTypeVO.getId());
			
			elementValueVO.setElementTypeParam(null);
			
			StompMessage stompMessage = preparedElements.get(elementVO.getId());

			if (stompMessage != null) {
				
				ElementVO elementInMap = (ElementVO) stompMessage.getFirst();
				
				List<ElementValueVO> elementValuesVO = elementInMap.getElementValues();

				if (elementValuesVO == null) {
					
					elementValuesVO = new ArrayList<>();
				}
				
				if (!CollectionUtils.isEmpty(elementValuesVO)) {
					
					elementValuesVO.removeIf(elementValVO -> elementValVO.getId().equals(elementValueVO.getId()));						
				}

				elementValuesVO.add(elementValueVO);
				
				elementInMap.setElementValues(elementValuesVO);
			} 
			else {

				stompMessage = new StompMessage();
				
				elementVO.setElementValues(new ArrayList<ElementValueVO>());
				elementVO.getElementValues().add(elementValueVO);
				
				stompMessage.getBasicElementsVO().add(elementVO);
				stompMessage.setTimestamp(Instant.now().getEpochSecond());
				
				if (keyElementTypeId.getClazz() == ElementType.class) {

					stompMessage.setCustomDestination(TOPIC_ELEMENTS + "." + keyElementTypeId.getSimpleValue() + "." + elementVO.getId());

				}

				preparedElements.put(elementVO.getId(), stompMessage);
			}
		}
	}
	
	private void prepareElementState(ElementState elementState) {
		
		Element element = EntitiesManager.getInstance().getElement(elementState.getElementTypeId(), elementState.getElementId());

		if (element != null && (element.getVisible() == null || element.getVisible().booleanValue())) {
			
			String key = (new Key(element)).getSimpleValue();
			
			log.debug(key + " state = " + elementState.getStateId() + "; DiffTime = "
					+ Duration.between(Instant.ofEpochMilli(elementState.getUpdatedAt()), Instant.now()).toMillis());
			
			StompMessage stompMessage = preparedElements.get(key);
			
			ElementTypeState elementTypeState = new ElementTypeState();
			elementTypeState.setElementTypeId(elementState.getElementTypeId());
			elementTypeState.setStateId(elementState.getStateId());
			
			ElementTypeStateVO elementTypeStateVO = ElementTypeStateHelper.get(elementTypeState);
			
			Key keyElementTypeId = new Key(elementTypeStateVO.getElementTypeId());

			if (stompMessage != null) {

				ElementVO elementInMap = (ElementVO) stompMessage.getFirst();
				
				elementInMap.setState(elementState.getStateId());
				
				elementInMap.setElementTypeState(elementTypeStateVO);
				
				ModelVOHelper.translate(elementInMap);
			} 
			else {

				stompMessage = new StompMessage();
				
				ElementVO elementVO = new ElementVO();

				elementVO.setId(BasicElementHelper.getIdFromElement(element));
				elementVO.setState(elementState.getStateId());
				
				elementVO.setElementTypeState(elementTypeStateVO);

				elementVO = (ElementVO) ModelVOHelper.translate(elementVO);
				
				stompMessage.getBasicElementsVO().add(elementVO);
				stompMessage.setTimestamp(Instant.now().getEpochSecond());
				
				if (keyElementTypeId.getClazz() == ElementType.class) {

					stompMessage.setCustomDestination(TOPIC_ELEMENTS + "." + keyElementTypeId.getSimpleValue() + "." + elementVO.getId());

				}

				preparedElements.put(elementVO.getId(), stompMessage);
			}
		}
	}

	private void prepareElementState(SetElementStatesPublishCommand setElementStatesPublishCommand) {

		if (setElementStatesPublishCommand != null
				&& !CollectionUtils.isEmpty(setElementStatesPublishCommand.getElementStates())) {

			for (ElementState elementState : setElementStatesPublishCommand.getElementStates()) {

				prepareElementState(elementState);
			}
		}
	}

	private void prepareAlarm(SetAlarmsPublishCommand setAlarmsPublishCommand) {

		Set<Alarm> alarms = setAlarmsPublishCommand.getAlarms();

		if (!CollectionUtils.isEmpty(alarms)) {

			for (Alarm alarm : alarms) {

				// Filter if elements of the alarm are Visible or not.
				Element sourceElement = EntitiesManager.getInstance().getElement(alarm.getSourceElementTypeId(),
						alarm.getSourceElementId());

				Element targetElement = null;

				if (sourceElement != null && sourceElement.getElementTypeId().equals(alarm.getTargetElementTypeId())
						&& sourceElement.getId().equals(alarm.getTargetElementId())) {

					targetElement = sourceElement;

				} else {

					targetElement = EntitiesManager.getInstance().getElement(alarm.getTargetElementTypeId(),
							alarm.getTargetElementId());
				}

				if (alarm != null && sourceElement != null && targetElement != null
						&& (sourceElement.getVisible() == null || sourceElement.getVisible().booleanValue())
						&& (targetElement.getVisible() == null || targetElement.getVisible().booleanValue())) {

					String key = (new Key(targetElement)).getSimpleValue();

					StompMessage stompMessage = preparedElements.get(key);

					AlarmVO alarmVO = AlarmHelper.get(alarm);

					AlarmConfig alarmConfig = EntitiesManager.getInstance().getAlarmConfig(alarm.getAlarmConfigId());

					ElementType elementType = new ElementType();

					elementType.setId(targetElement.getElementTypeId());

					ElementTypeVO elementTypeVO = new ElementTypeVO();

					elementTypeVO.setId(BasicElementHelper.getIdFromElement(elementType));

					if (alarmConfig != null) {

						alarm.setAlarmConfig(alarmConfig);

						AlarmConfigVO alarmConfigVO = new AlarmConfigVO();
						alarmConfigVO.setId(BasicElementHelper.getIdFromElement(alarm.getAlarmConfig()));
						alarmVO.setAlarmConfig(alarmConfigVO);

						Key keyElementTypeId = new Key(elementTypeVO.getId());

						alarm.setSourceElement(EntitiesManager.getInstance().getElement(alarm.getSourceElementTypeId(),
								alarm.getSourceElementId()));

						if (alarm.getSourceElement() != null) {

							ElementVO sourceElementVO = new ElementVO();
							sourceElementVO.setId(BasicElementHelper.getIdFromElement(alarm.getSourceElement()));
							alarmVO.setSourceElement(sourceElementVO);
						}

						alarm.setTargetElement(EntitiesManager.getInstance().getElement(alarm.getTargetElementTypeId(),
								alarm.getTargetElementId()));

						if (alarm.getTargetElement() != null) {

							ElementVO targetElementVO = new ElementVO();
							targetElementVO.setId(BasicElementHelper.getIdFromElement(alarm.getTargetElement()));
							alarmVO.setTargetElement(targetElementVO);
						}

						alarmVO.setAlarmConfigId(null);
						alarmVO.setTargetElementId(null);
						alarmVO.setSourceElementId(null);

						if (stompMessage != null) {

							ElementVO elementInMap = (ElementVO) stompMessage.getFirst();

							if (elementInMap.getAlarms() == null) {

								elementInMap.setAlarms(new ArrayList<>());
							}

							elementInMap.getAlarms().add(alarmVO);

							ModelVOHelper.translate(elementInMap);
						} else {

							stompMessage = new StompMessage();

							ElementVO elementVO = new ElementVO();

							elementVO.setId(BasicElementHelper.getIdFromElement(targetElement));

							elementVO.setAlarms(new ArrayList<>());

							elementVO.getAlarms().add(alarmVO);

							elementVO = (ElementVO) ModelVOHelper.translate(elementVO);

							stompMessage.getBasicElementsVO().add(elementVO);
							stompMessage.setTimestamp(Instant.now().getEpochSecond());

							if (keyElementTypeId.getClazz() == ElementType.class) {

								stompMessage.setCustomDestination(TOPIC_ELEMENTS + "."
										+ keyElementTypeId.getSimpleValue() + "." + elementVO.getId());

							}

							preparedElements.put(elementVO.getId(), stompMessage);
						}
					}
				}
			}
		}
	}
}