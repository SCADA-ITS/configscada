package com.revenga.rits.back.ep.pub.stomp.publisher;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.messaging.simp.stomp.StompSession;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher.Topics.Topic;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.data.core.model.GenericData.ManagementAreaValue;
import com.revenga.rits.back.data.core.model.command.SetGenericData;
import com.revenga.rits.back.ep.pub.stomp.service.ElementCacheManager;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.GenericDataVO;
import com.revenga.rits.back.front.core.model.vo.StompMessage;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class GenericDataPublisher extends GenericPublisher {

	public GenericDataPublisher(StompSession stompSession, Topic topic, ElementCacheManager elementCacheManager) {

		super(stompSession, topic, elementCacheManager);
	}

	@Override
	protected BasicElementVO prepare(Pair<Object, Message> queueElement) {

		BasicElementVO basicElementVO = null;
		try {

			if (queueElement != null && queueElement.getKey() instanceof SetGenericData) {

				SetGenericData setGenericData = (SetGenericData) queueElement.getKey();

				if (setGenericData != null && !CollectionUtils.isEmpty(setGenericData.getGenericDatas())) {

					for (GenericData genericData : setGenericData.getGenericDatas()) {

						if (genericData.getValue() != null) {

							GenericDataVO genericDataVO = new GenericDataVO();

							genericDataVO.setId((new Key(genericData)).getSimpleValue());
							genericDataVO.setValue(genericData.getValue());

							StompMessage stompMessage = new StompMessage();
							stompMessage.setTimestamp(Instant.now().getEpochSecond());
							stompMessage.getBasicElementsVO().add(genericDataVO);
							stompMessage.setManagementAreaIds(new ArrayList<>());
							preparedElements.put(genericDataVO.getId(), stompMessage);
						}

						if (CollectionUtils.isNotEmpty(genericData.getManagementAreaValues())) {

							for (ManagementAreaValue managementAreaValue : genericData.getManagementAreaValues()) {

								if (managementAreaValue.getValue() != null) {

									GenericDataVO genericDataVO = new GenericDataVO();

									genericDataVO.setId((new Key(genericData)).getSimpleValue());
									genericDataVO.setValue(managementAreaValue.getValue());

									StompMessage stompMessage = new StompMessage();

									stompMessage.setTimestamp(Instant.now().getEpochSecond());
									stompMessage.getBasicElementsVO().add(genericDataVO);
									stompMessage.setManagementAreaIds(
											Arrays.asList(managementAreaValue.getManagementAreaId()));
									preparedElements.put(
											genericDataVO.getId() + ": " + managementAreaValue.getManagementAreaId(),
											stompMessage);
								}
							}
						}

					}
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return basicElementVO;
	}
}