package com.revenga.rits.back.ep.pub.stomp.service;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PublisherHnd extends StompSessionHandlerAdapter {

	PublisherService publisherService;
	Publisher publisher;
	
	public PublisherHnd(PublisherService publisherService, Publisher publisher) {
		super();
		
		this.publisherService = publisherService;
		this.publisher = publisher;
	}

	@Override
	public Type getPayloadType(StompHeaders headers) {
		// TODO Auto-generated method stub
		return super.getPayloadType(headers);
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		// TODO Auto-generated method stub
		super.handleFrame(headers, payload);
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		
		log.info("New stomp session established : " + session.getSessionId());
		log.info("Stomp headers : " + connectedHeaders);
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		super.handleException(session, command, headers, payload, exception);
		
		if (!publisherService.attempingConnection && !session.isConnected()) {
			
			publisherService.disconnect(exception);
		}
	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		super.handleTransportError(session, exception);
		
		if (!publisherService.attempingConnection && !session.isConnected()) {

			
			publisherService.disconnect(exception);
		}
	}

}
