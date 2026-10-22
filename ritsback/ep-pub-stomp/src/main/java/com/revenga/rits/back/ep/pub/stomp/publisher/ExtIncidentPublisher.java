package com.revenga.rits.back.ep.pub.stomp.publisher;

import java.time.Instant;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.messaging.simp.stomp.StompSession;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher.Topics.Topic;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsExtIncident;
import com.revenga.rits.back.data.core.model.ImsExtIncidentType;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetExIncidentPublishCommand;
import com.revenga.rits.back.ep.pub.stomp.service.ElementCacheManager;
import com.revenga.rits.back.front.core.model.helper.ImsExtIncidentHelper;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentVO;
import com.revenga.rits.back.front.core.model.vo.StompMessage;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ExtIncidentPublisher extends GenericPublisher {
	
	private static final String TOPIC_EXT_INCIDENTS = "/topic/extIncident";

	public ExtIncidentPublisher(StompSession stompSession, Topic topic, ElementCacheManager elementCacheManager) {

		super(stompSession, topic, elementCacheManager);
	}

	@Override
	protected BasicElementVO prepare(Pair<Object, Message> queueElement) {

		BasicElementVO basicElementVO = null;

		try {

			if (queueElement != null && isAllowedMessage(queueElement.getKey())) {

				ImsExtIncidentVO imsExtIncidentVO = null;

				if (queueElement.getKey() instanceof SetExIncidentPublishCommand) {

					SetExIncidentPublishCommand setExIncidentPublishCommand = (SetExIncidentPublishCommand) queueElement
							.getKey();

					if (setExIncidentPublishCommand != null) {

						List<ImsExtIncident> imsExtIncidents = setExIncidentPublishCommand.getImsExtIncidents();

						if (!CollectionUtils.isEmpty(imsExtIncidents)) {

							for (ImsExtIncident imsExtIncident : imsExtIncidents) {

								ImsExtIncidentType imsExtIncidentType = new ImsExtIncidentType();
								imsExtIncidentType.setId(imsExtIncident.getExtIncidentTypeId());
								imsExtIncident.setExtIncidentType(imsExtIncidentType);
								
								Location location = new Location();
								location.setId(imsExtIncident.getLocationId());
								imsExtIncident.setLocation(location);
								
								imsExtIncidentVO = ImsExtIncidentHelper.get(imsExtIncident);
								Key keyExtIncidenTypeId = new Key(imsExtIncidentVO.getExtIncidentTypeId());
								imsExtIncidentVO.setExtIncidentTypeId(null);
								imsExtIncidentVO.setLocationId(null);

								if (imsExtIncidentVO != null) {

									StompMessage stompMessage = new StompMessage();

									if (setExIncidentPublishCommand.getAction() != null) {

										switch (((Command) queueElement.getKey()).getAction().intValue()) {

										case Command.ACTION_UPDATE:

											stompMessage.setAction(StompMessage.Action.UPDATE);

											break;

										case Command.ACTION_DELETE:

											stompMessage.setAction(StompMessage.Action.DELETE);
											imsExtIncidentVO.setExtIncidentType(null);
											imsExtIncidentVO.setLocation(null);

											break;

										case Command.ACTION_CREATE:

											stompMessage.setAction(StompMessage.Action.CREATE);

											break;

										default:
											break;
										}
									}
									
									

									if (keyExtIncidenTypeId.getClazz() == ImsExtIncidentType.class) {

										stompMessage.setCustomDestination(TOPIC_EXT_INCIDENTS + "." + keyExtIncidenTypeId.getSimpleValue() + "." + imsExtIncidentVO.getId());

									}

									
									stompMessage.getBasicElementsVO().add(imsExtIncidentVO);
									stompMessage.setTimestamp(Instant.now().getEpochSecond());


									preparedElements.put(imsExtIncidentVO.getId(), stompMessage);

								}

							}
						}
					}

				}

			}

		} catch (

		Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return basicElementVO;

	}

	private boolean isAllowedMessage(Object object) {

		return (object instanceof SetExIncidentPublishCommand);
	}

}
