package com.revenga.rits.back.ep.pub.stomp.publisher;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.messaging.simp.stomp.StompSession;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher.Topics.Topic;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetPmQueuePublishCommand;
import com.revenga.rits.back.ep.pub.stomp.service.ElementCacheManager;
import com.revenga.rits.back.front.core.model.helper.BasicElementHelper;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.StompMessage;
import com.revenga.rits.back.pub.stomp.helper.StompMessageHelper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PmQueuePublisher extends GenericPublisher {

	public PmQueuePublisher(StompSession stompSession, Topic topic, ElementCacheManager elementCacheManager) {

		super(stompSession, topic, elementCacheManager);
	}

	@Override
	protected BasicElementVO prepare(Pair<Object, Message> queueElement) {

		BasicElementVO basicElementVO = null;

		try {

			if (queueElement != null && queueElement.getKey() instanceof SetPmQueuePublishCommand) {

				SetPmQueuePublishCommand command = (SetPmQueuePublishCommand) queueElement.getKey();

				if (!CollectionUtils.isEmpty(command.getElements())) {

					for (Element element : command.getElements()) {

						if (element.getId() != null && element.getElementTypeId() != null) {

							StompMessage stompMessage = null;

							ElementVO elementVO = new ElementVO();
							elementVO.setId(BasicElementHelper.getIdFromElement(element));

							stompMessage = preparedElements.get(elementVO.getId());

							if (stompMessage != null) {

								StompMessageHelper.updateMessage(stompMessage);

							} else {

								stompMessage = StompMessageHelper
										.get(((Command) queueElement.getKey()).getAction().intValue(), null, elementVO);
							}
							preparedElements.put(elementVO.getId(), stompMessage);
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