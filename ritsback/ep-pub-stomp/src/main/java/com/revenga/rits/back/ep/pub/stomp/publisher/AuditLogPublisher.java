package com.revenga.rits.back.ep.pub.stomp.publisher;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.messaging.simp.stomp.StompSession;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher.Topics.Topic;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.AuditLogValue;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetAuditLogPublishCommand;
import com.revenga.rits.back.ep.pub.stomp.service.ElementCacheManager;
import com.revenga.rits.back.front.core.model.helper.AuditLogHelper;
import com.revenga.rits.back.front.core.model.helper.LogSubtypeHelper;
import com.revenga.rits.back.front.core.model.helper.LogTypeHelper;
import com.revenga.rits.back.front.core.model.helper.LogTypeParamHelper;
import com.revenga.rits.back.front.core.model.helper.UserHelper;
import com.revenga.rits.back.front.core.model.vo.AuditLogVO;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.StompMessage;
import com.revenga.rits.back.pub.stomp.helper.StompMessageHelper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuditLogPublisher extends GenericPublisher {

	private static final String TOPIC_AUDIT_LOG = "/topic/auditLog";

	public AuditLogPublisher(StompSession stompSession, Topic topic, ElementCacheManager elementCacheManager) {

		super(stompSession, topic, elementCacheManager);
	}

	@Override
	protected BasicElementVO prepare(Pair<Object, Message> queueElement) {

		BasicElementVO basicElementVO = null;

		try {

			if (queueElement.getKey() instanceof SetAuditLogPublishCommand) {

				SetAuditLogPublishCommand setAuditLogPublishCommand = (SetAuditLogPublishCommand) queueElement.getKey();

				if (!CollectionUtils.isEmpty(setAuditLogPublishCommand.getAuditLogs())) {

					List<AuditLogVO> auditLogVOs = new ArrayList<>();
					int action = ((Command) queueElement.getKey()).getAction().intValue();
					
					String lastLogTypeId = null;

					for (AuditLog auditLog : setAuditLogPublishCommand.getAuditLogs()) {

						if (auditLog != null) {

							AuditLogVO auditLogVO = AuditLogHelper.get(auditLog);

							if (auditLog.getLogTypeId() != null) {

								LogType logType = new LogType();
								logType.setId(auditLog.getLogTypeId());

								auditLogVO.setLogType(LogTypeHelper.get(logType));
							}

							if (auditLog.getLogSubtypeId() != null) {

								LogSubtype logSubtype = new LogSubtype();
								logSubtype.setId(auditLog.getLogSubtypeId());

								auditLogVO.setLogSubtype(LogSubtypeHelper.get(logSubtype));
							}

							if (auditLog.getUserId() != null) {

								User user = new User();
								user.setId(auditLog.getUserId());

								auditLogVO.setUser(UserHelper.get(user));
							}

							if (CollectionUtils.isNotEmpty(auditLog.getAuditLogValues())) {

								int i = 0;

								for (AuditLogValue value : auditLog.getAuditLogValues()) {

									LogTypeParam logTypeParam = new LogTypeParam();
									logTypeParam.setId(value.getLogParamId());
									logTypeParam.setLogTypeId(value.getLogTypeId());
									auditLogVO.getAuditLogValues().get(i)
											.setLogTypeParam(LogTypeParamHelper.get(logTypeParam));
									i++;
								}

							}

							if (auditLogVO != null && auditLogVO.getLogType() != null) {

								if (lastLogTypeId == null) {

									lastLogTypeId = auditLogVO.getLogType().getId();

								} else if (!lastLogTypeId.equals(auditLogVO.getLogType().getId())) {

									String key = lastLogTypeId + "_" + action;
									StompMessage stompMessage = preparedElements.get(key);
									
									if(stompMessage != null){
										
										StompMessageHelper.updateMessage(stompMessage,
												auditLogVOs.toArray(BasicElementVO[]::new));
									}
									else{
										
										stompMessage = StompMessageHelper.get(
												action,
												(TOPIC_AUDIT_LOG + "." + lastLogTypeId),
												auditLogVOs.toArray(BasicElementVO[]::new));

										preparedElements.put(key, stompMessage);
									}
									auditLogVOs = new ArrayList<>();

									lastLogTypeId = auditLogVO.getLogType().getId();
								}
								auditLogVOs.add(auditLogVO);
							}
						}
					}

					if (!CollectionUtils.isEmpty(auditLogVOs)) {

						String key = lastLogTypeId + "_" + action;
						StompMessage stompMessage = preparedElements.get(key);
						
						if(stompMessage != null){
							
							StompMessageHelper.updateMessage(stompMessage,
									auditLogVOs.toArray(BasicElementVO[]::new));
						}
						else{
							
							stompMessage = StompMessageHelper.get(
									action,
									(TOPIC_AUDIT_LOG + "." + lastLogTypeId),
									auditLogVOs.toArray(BasicElementVO[]::new));
							stompMessage.setManagementAreaIds(setAuditLogPublishCommand.getManagementAreaIds());
							preparedElements.put(key, stompMessage);
						}

					}
				}
			}
		}

		catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		return basicElementVO;
	}

}