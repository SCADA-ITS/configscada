package com.revenga.rits.back.virtual.equipment.manager.vms.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CommandElementError;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.data.core.model.command.ResponseCommand;
import com.revenga.rits.back.data.core.model.command.ResponseToVirtualEquipmentManagerCommand;
import com.revenga.rits.back.data.core.model.command.SetAddCommandsCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.data.core.model.helper.command.PmCommandElementHelper;
import com.revenga.rits.back.data.core.util.GroovyManager;
import com.revenga.rits.back.manager.lib.service.ResponseManager;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;
import com.revenga.rits.back.virtual.equipment.manager.vms.VmsEntityManager;
import com.revenga.rits.back.virtual.equipment.manager.vms.model.TravelTime;
import com.revenga.rits.back.virtual.equipment.manager.vms.model.VmsMessageZone;

import groovy.lang.GroovyObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class VmsTravelTimeProcessor {
	
	private static final String PATH_GROOVY_VMS_TRAVEL_TIMES = "virtual-equipment-manager/groovy/vms/travel_times/";
	private static final String GROOVY_FILE_PREFIX_VMS_TRAVEL_TIME = "TravelTime";
	private static final String GROOVY_METHOD_PROCESS = "process";
	
	private PmQueuePriority pmQueuePriority;
	
	public VmsTravelTimeProcessor() throws Exception {

		this.pmQueuePriority = EntityManager.getInstance()
				.getPmQueuePriority(VmsEntityManager.VMS_QUEUE_ID, VmsEntityManager.VMS_QUEUE_PRIORITY_ID);
		
		if (this.pmQueuePriority == null) {
			
			throw new Exception(String.format("VMS priority not defined: queue_id = %d; queue_priority_id = %d",
					VmsEntityManager.VMS_QUEUE_ID, VmsEntityManager.VMS_QUEUE_PRIORITY_ID));
		}
	}
	
	public void process() {
		
		List<Element> vmss = EntityManager.getInstance().getElements(ElementType.ELEMENT_TYPE_VMS);
		SetAddCommandsCommand setAddCommandsCommand = new SetAddCommandsCommand();
		
		if (!CollectionUtils.isEmpty(vmss)) {
			
			for (Element vms : vmss) {
				
				PmCommandElement cmd = processVms(vms);
				
				if (cmd != null) {
					
					setAddCommandsCommand.addCommand(cmd);
				}
			}
			
			if (!CollectionUtils.isEmpty(setAddCommandsCommand.getCommands())) {
				
				List<CommandElementError> commandElementErrors = send(setAddCommandsCommand);
				
				if (CollectionUtils.isNotEmpty(commandElementErrors)) {
					
					for (CommandElementError commandElementError : commandElementErrors) {
						
						log.error("VmsTravelTimeProcessor::send() -->" + commandElementError.toString());
					}
				}
			}
		}
	}
	
	private List<CommandElementError> send(SetAddCommandsCommand setAddCommandsCommand) {
		
		List<CommandElementError> commandElementErrors = new ArrayList<>();
		
		setAddCommandsCommand.setResponseCommand(new ResponseToVirtualEquipmentManagerCommand());
		setAddCommandsCommand.setSendWithAnyError(true);
		
		ResponseCommand response = ResponseManager.getInstance()
				.sendCommandSync(setAddCommandsCommand);

		if (response == null) {

			CommandElementError commandElementError = new CommandElementError();
			commandElementError.setError(CommandElementError.TIME_OUT_EXPIRED);
			commandElementErrors.add(commandElementError);

		} else if (CollectionUtils.isNotEmpty(response.getBasicElements())) {

			for (BasicElement be : response.getBasicElements()) {

				if (be instanceof CommandElementError) {

					commandElementErrors.add((CommandElementError) be);
				}
			}
		}
		
		return commandElementErrors;
	}
	
	private PmCommandElement processVms(Element vms) {
		
		PmCommandElement cmd = null;
	
		ElementValue evEnabledTravelTime = EntityManager.getInstance().getElementValue(ElementType.ELEMENT_TYPE_VMS, vms.getId(),
				VmsEntityManager.ELEMENT_TYPE_PARAM_VMS_ENABLED_TRAVEL_TIME, ParamType.MEASURE);
		
		if (evEnabledTravelTime != null && StringUtils.equals(evEnabledTravelTime.getValue(), "true")) {
			
			ElementValue evData = EntityManager.getInstance().getElementValue(ElementType.ELEMENT_TYPE_VMS, vms.getId(),
					VmsEntityManager.ELEMENT_TYPE_PARAM_VMS_DATA, ParamType.MEASURE);
			
			if (evData != null) {
				
				List<TravelTime> travelTimes = VmsEntityManager.getInstance().getTravelTimes(vms);
				List<VmsMessageZone> currentTravelTimes = null;
				List<VmsMessageZone> newTravelTimes = new ArrayList<>();
				
				if (!StringUtils.isEmpty(evData.getValue())) {
					
					String currentMessageZonesStr = evData.getValue();
					
					ObjectMapper mapper = new ObjectMapper();
					
					try {
						
						currentTravelTimes = mapper.readValue(currentMessageZonesStr,
								mapper.getTypeFactory().constructCollectionType(List.class, VmsMessageZone.class));
					
					} catch (JsonProcessingException e) {
						
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
				
				if (!CollectionUtils.isEmpty(travelTimes)) {
					
					travelTimes.sort((TravelTime a, TravelTime b) -> {
						
						Integer t1 = a.getTime() != null ? a.getTime() : -1;
						Integer t2 = b.getTime() != null ? b.getTime() : -1;
						
						return t1.compareTo(t2);
					});
				}
				
				if (processGroovy(vms, travelTimes, currentTravelTimes, newTravelTimes)) {

					cmd = getCommandElement(vms, newTravelTimes);
				}
			}
		}
		
		return cmd;
	}
	
	private boolean processGroovy(final Element vms, final List<TravelTime> travelTimes, final List<VmsMessageZone> currentMessageZones, List<VmsMessageZone> newMessageZones) {
		
		boolean res = false;
		
		try {
			
			String groovyFile = String.format("%s%s_%d_%d.groovy", PATH_GROOVY_VMS_TRAVEL_TIMES,
					GROOVY_FILE_PREFIX_VMS_TRAVEL_TIME, vms.getElementTypeId(), vms.getElementSubtypeId());
			
			if (!StringUtils.isEmpty(groovyFile)) {
				
				log.debug("Processing vms travel times with groovy "
						+ groovyFile + " for " + (new Key(vms)).getSimpleValue()
						+ " ...");
				
				GroovyObject groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);

				res = (boolean) groovyObj.invokeMethod(GROOVY_METHOD_PROCESS,
						new Object[] { vms, travelTimes, currentMessageZones, newMessageZones });
				
//				TravelTime_71_22 tt = new TravelTime_71_22(log);
//				
//				res = tt.process(vms, travelTimes, currentMessageZones, newMessageZones);
				
				if (res) {
					
					log.debug("Success processed vms travel times with groovy "
							+ groovyFile + " for " + (new Key(vms)).getSimpleValue());
				}
				else {
					
					log.debug("Error processing vms travel times with groovy "
							+ groovyFile + " for " + (new Key(vms)).getSimpleValue());
				}
			}
		}
		catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return res;
	}
	
	private PmCommandElement getCommandElement(Element vms, List<VmsMessageZone> messageZones) {
		
		PmCommandElement pmCommandElement = null;
		
		try {
			
			if (!CollectionUtils.isEmpty(messageZones)) {
				
				ObjectMapper objectMapper = new ObjectMapper();
				
				objectMapper.setSerializationInclusion(Include.NON_NULL);
				
				String messageZonesStr = objectMapper.writeValueAsString(messageZones);
				
				if (!StringUtils.isEmpty(messageZonesStr)) {
				
					SignallingCommand signallingCommand = new SignallingCommand();
					
					signallingCommand.setElementTypeId(vms.getElementTypeId());
					signallingCommand.setElementId(vms.getId());
					signallingCommand.setSignallingCommandId((long) VmsEntityManager.COMMAND_TYPE_VMS_SEND_MESSAGE);
					
					SignallingParam signallingParam = new SignallingParam();
					
					signallingParam.setId(VmsEntityManager.COMMAND_TYPE_VMS_SEND_MESSAGE_PARAM_JSON_VALUE);
					signallingParam.setType(VmsEntityManager.COMMAND_TYPE_VMS_SEND_MESSAGE);
					signallingParam.setValue(messageZonesStr);
					
					signallingCommand.setSignallingParams(new ArrayList<>());
					signallingCommand.getSignallingParams().add(signallingParam);
					
					pmCommandElement = PmCommandElementHelper.get(signallingCommand, this.pmQueuePriority, null);
					//pmCommandElement.setEndTime(Instant.now().toEpochMilli() + 60000);
				}
			}
			
		} catch (JsonProcessingException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return pmCommandElement;
	}
}
