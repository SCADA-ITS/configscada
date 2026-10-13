package com.revenga.rits.back.ep.pub.stomp.publisher;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.messaging.simp.stomp.StompSession;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher.Topics.Topic;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetBlockIncidentReportsPublishCommand;
import com.revenga.rits.back.ep.pub.stomp.service.ElementCacheManager;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentReportHelper;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportVO;
import com.revenga.rits.back.front.core.model.vo.StompMessage;
import com.revenga.rits.back.pub.stomp.helper.StompMessageHelper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BlockIncidentPublisher extends GenericPublisher {

	public BlockIncidentPublisher(StompSession stompSession, Topic topic, ElementCacheManager elementCacheManager) {

		super(stompSession, topic, elementCacheManager);
	}

	@Override
	protected BasicElementVO prepare(Pair<Object, Message> queueElement) {

		BasicElementVO basicElementVO = null;

		try {

			if (queueElement != null && queueElement.getKey() instanceof SetBlockIncidentReportsPublishCommand) {

				SetBlockIncidentReportsPublishCommand command = (SetBlockIncidentReportsPublishCommand) queueElement.getKey();

				if (!CollectionUtils.isEmpty(command.getIncidentReportIds())) {

					for (Long imsIncidentReportId : command.getIncidentReportIds()) {

						if (imsIncidentReportId != null) {

							StompMessage stompMessage = null;

							ImsIncidentReport imsIncidentReport = new ImsIncidentReport();
							imsIncidentReport.setId(imsIncidentReportId);

							ImsIncidentReportVO imsIncidentReportVO = ImsIncidentReportHelper.get(imsIncidentReport);

							stompMessage = preparedElements.get(imsIncidentReportVO.getId());

							if (stompMessage != null) {

								StompMessageHelper.updateMessage(stompMessage);
							} else {
								stompMessage = StompMessageHelper.get(Command.ACTION_BLOCK, null, imsIncidentReportVO);
							}
							preparedElements.put(imsIncidentReportVO.getId(), stompMessage);
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