package com.revenga.rits.front.openits.service.user;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.front.openits.app.AppInfra;
import com.revenga.rits.front.openits.model.UserSessionUI;
import com.sun.net.httpserver.Headers;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class UserServer {
	
	public static final String HEADER_OPERATION = "operation";
	public static final String HEADER_NODE_PORT = "node_port";
	
	private static final String NODE_REPLICA_OF = AppInfra.getProperty("node.replicaof");
	
	public enum SessionOperation {
		
		CREATE_SESSION,
		UPDATE_SESSION,
		REMOVE_SESSION,
		REQUEST_SESSIONS
	}
	
	private OpenItsNode openItsNode;
	private Integer httpPoolThreadSize = 20;
	
	private SessionService sessionService;
	private UserSubscriptor userSubscriptor;
	
	
	private Set<OpenItsNode> replicas = Collections.synchronizedSet(new HashSet<>());
	
	public UserServer(SessionService sessionService) {
		
		super();
		
		this.sessionService = sessionService;
		this.openItsNode = OpenItsNode.newIntance();
		
		if (this.openItsNode != null) {
			
			loadReplicas();
			
			if (!CollectionUtils.isEmpty(this.replicas)) {
				
				log.info("Cluster mode is detected.");
				log.info("Node name is: " + this.openItsNode.getName());
				
				this.userSubscriptor = new UserSubscriptor(this);
				
				recoverSessions();
			}
		}
	}
	
	private void loadReplicas() {
		
		if (!StringUtils.isEmpty(NODE_REPLICA_OF)) {
			
			String[] replicasArr = NODE_REPLICA_OF.split(";");
			
			if (!ArrayUtils.isEmpty(replicasArr)) {
				
				for (String replicaStr : List.of(replicasArr)) {
			
					String[] replicaArr = replicaStr.split(":");
					
					if (!ArrayUtils.isEmpty(replicaArr) && replicaArr.length == 2) {
						
						OpenItsNode replica = new OpenItsNode(replicaArr[0], Integer.valueOf(replicaArr[1]));
						
						this.replicas.add(replica);
					}
				}
			}
		}
	}
	
	private void recoverSessions() {
		
		boolean response = false;
		
		Iterator<OpenItsNode> it = this.replicas.iterator();

		while (it.hasNext() && !response) {
			
			OpenItsNode replica = it.next();
			
			response = replica.sendSessionOperation(this.getOpenItsNode(), SessionOperation.REQUEST_SESSIONS, null);
		}
	}

	public void receive(String remoteAddress, Headers headers, String body) {
		
		if (headers != null) {
			
			String operation = headers.getFirst(HEADER_OPERATION);
			String nodePort = headers.getFirst(HEADER_NODE_PORT);
			
			if (!StringUtils.isEmpty(operation) && !StringUtils.isEmpty(nodePort) && NumberUtils.isCreatable(nodePort)) {
				
				processOperation(remoteAddress, operation, Integer.valueOf(nodePort), body);
			}
		}
	}
		
	private void processOperation(String remoteAddress, String operation, Integer nodePort, String body) {
		
		if (!StringUtils.isEmpty(operation) && !CollectionUtils.isEmpty(this.replicas)) {
			
			if (!StringUtils.isEmpty(remoteAddress) && nodePort != null) {
				
				OpenItsNode replica = this.replicas.stream().filter(
						e -> e.getRemoteAddress().equals(remoteAddress) && 
						e.getSubscriptorPort().equals(nodePort))
						.findFirst().orElse(null);
				
				if (replica != null) {
					
					UserSessionUI userSessionUI = getUserSessionUI(body);
				
					log.info(String.format("[%s << %s] - %s %s", 
							this.openItsNode.getName(),
							replica.getName(),
							operation,
							userSessionUI != null ? " => " + userSessionUI.getSessionId() : ""));
					
					if (userSessionUI != null) {
						
						if (SessionOperation.CREATE_SESSION.toString().equals(operation) ||
							SessionOperation.UPDATE_SESSION.toString().equals(operation)) {
							
							this.sessionService.putSession(userSessionUI);
						}
						else if (SessionOperation.REMOVE_SESSION.toString().equals(operation)) {
							
							this.sessionService.removeSession(userSessionUI.getSessionId());
						}
					}
					else {
						
						if (SessionOperation.REQUEST_SESSIONS.toString().equals(operation)) {
							
							requestSessions(replica);
						}
					}
				}
			}
		}
	}
	
	private UserSessionUI getUserSessionUI(String body) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		UserSessionUI userSessionUI = null;
		
		try {
			
			if (!StringUtils.isEmpty(body)) {
				
				userSessionUI = objectMapper.readValue(body, UserSessionUI.class);
			}
			
		} catch (JsonProcessingException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return userSessionUI;
	}
	
	private void requestSessions(OpenItsNode replica) {
		
		if (replica != null) {

			for (UserSessionUI userSessionUI : this.sessionService.getSessions()) {

				syncSession(SessionOperation.UPDATE_SESSION, userSessionUI, replica);
			}
		}
	}
	
	public void syncSession(SessionOperation operation, UserSessionUI userSessionUI) {
		
		List<OpenItsNode> replicasToSync = this.replicas.stream().collect(Collectors.toList());
		
		if (!CollectionUtils.isEmpty(replicasToSync)) {
			
			for (OpenItsNode replicaToSync : replicasToSync) {
				
				replicaToSync.sendSessionOperation(this.getOpenItsNode(), operation, userSessionUI);
			}
		}
	}
	
	public void syncSession(SessionOperation operation, UserSessionUI userSessionUI, OpenItsNode replicaToSync) {
		
		replicaToSync.sendSessionOperation(this.getOpenItsNode(), operation, userSessionUI);
	}
}
