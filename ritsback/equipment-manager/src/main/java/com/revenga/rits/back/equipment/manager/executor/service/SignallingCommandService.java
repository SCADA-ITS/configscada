package com.revenga.rits.back.equipment.manager.executor.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.CommandElementError;
import com.revenga.rits.back.data.core.model.CommandElementPrecondition;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.PmDisabledQueuePriority;
import com.revenga.rits.back.data.core.model.PmElementTypeQueue;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.ResponseCommand;
import com.revenga.rits.back.data.core.model.command.SetAddCommandsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeleteCommandsCommand;
import com.revenga.rits.back.data.core.model.command.SetGenericData;
import com.revenga.rits.back.data.core.model.command.SetPmQueuePublishCommand;
import com.revenga.rits.back.data.core.model.command.SetPriorityComand;
import com.revenga.rits.back.data.core.model.command.SetSignallingsCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.helper.command.SignallingCommandHelper;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.data.core.util.GroovyManager;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.entities.lib.repository.GenericDataRepository;
import com.revenga.rits.back.equipment.manager.executor.scheduler.ScheduledMaintenacePmQueueJob;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import groovy.lang.GroovyObject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SignallingCommandService {

	private static final String DEFAULT_ON_CHANGE = ConfigProperty.getInstance().getProperty("application.properties",
			"service.signalling_command_service.default_on_change");

	private static final Long DEFAULT_END_TIME = Long.parseLong(ConfigProperty.getInstance()
			.getProperty("application.properties", "service.signalling_command_service.default_end_time"));

	private static final String PATH_GROOVY_EQUIPMENT_MANAGER_SIGNALLING = "equipment-manager/signalling/";

	GenericExecutor genericExecutor;

	@Getter
	@Setter
	private boolean running;

	private Scheduler scheduler;

	static class PmPmCommandElementComparator implements Comparator<PmCommandElement> {

		@Override
		public int compare(PmCommandElement pmCommandElement1, PmCommandElement pmCommandElement2) {

			PmQueuePriority pmQueuePriority1 = pmCommandElement1.getQueueId() != null
					&& pmCommandElement1.getPriorityId() != null
							? EntitiesManager.getInstance().getPmQueuePriority(pmCommandElement1.getQueueId(),
									pmCommandElement1.getPriorityId())
							: null;

			PmQueuePriority pmQueuePriority2 = pmCommandElement2.getQueueId() != null
					&& pmCommandElement2.getPriorityId() != null
							? EntitiesManager.getInstance().getPmQueuePriority(pmCommandElement2.getQueueId(),
									pmCommandElement2.getPriorityId())
							: null;

			int priorityCompare = pmQueuePriority1 != null && pmQueuePriority1.getPriority() != null
					&& pmQueuePriority2 != null && pmQueuePriority2.getPriority() != null
							? pmQueuePriority1.getPriority().compareTo(pmQueuePriority2.getPriority())
							: 0;

			return (priorityCompare == 0)
					? (pmCommandElement1.getStartTime().compareTo(pmCommandElement2.getStartTime()))
					: priorityCompare;
		}
	}

	public SignallingCommandService(GenericExecutor genericExecutor) {
		super();

		this.genericExecutor = genericExecutor;
	}

	public void publishPmQueue(List<Element> elements) {

		if (CollectionUtils.isNotEmpty(elements)) {

			SetPmQueuePublishCommand setPmQueuePublishCommand = new SetPmQueuePublishCommand();
			setPmQueuePublishCommand.setElements(elements);
			setPmQueuePublishCommand.setAction(Command.ACTION_UPDATE);

			log.info("SendCommand " + setPmQueuePublishCommand);
			EntitiesManager.getInstance().sendCommand(setPmQueuePublishCommand);
		}

	}

	public void setSignallingsCommand(SetSignallingsCommand setSignallingsCommand) {

		if (setSignallingsCommand != null) {

			ResponseCommand responseCommand = (setSignallingsCommand.getResponseCommand() instanceof ResponseCommand)
					? (ResponseCommand) setSignallingsCommand.getResponseCommand()
					: null;

			if (CollectionUtils.isNotEmpty(setSignallingsCommand.getCommands())) {

				List<SignallingCommand> signallingCommands = new ArrayList<>();

				for (SignallingCommand signallingCommand : setSignallingsCommand.getCommands()) {

					CommandElementError commandElementError = null;
					CommandElementError commandElementErrorPreconditions = null;

					Element element = EntitiesManager.getInstance().getElement(signallingCommand.getElementTypeId(),
							signallingCommand.getElementId());

					CommandElementType commandElementType = EntitiesManager.getInstance().getCommandElementType(
							signallingCommand.getSignallingCommandId(), signallingCommand.getElementTypeId());

					if (element == null) {

						commandElementError = new CommandElementError();
						commandElementError.setError(CommandElementError.ELEMENT_NOT_FOUND);
						commandElementError.setElementId(signallingCommand.getElementId());
						commandElementError.setElementTypeId(signallingCommand.getElementTypeId());

					} else if (commandElementType == null) {

						commandElementError = new CommandElementError();
						commandElementError.setError(CommandElementError.COMMAND_NOT_FOUND);
						commandElementError.setElementId(signallingCommand.getElementId());
						commandElementError.setElementTypeId(signallingCommand.getElementTypeId());

					} else {

						commandElementError = checkElementConnected(signallingCommand);

						commandElementErrorPreconditions = checkPreconditions(signallingCommand);

						if (commandElementErrorPreconditions != null) {

							try {
								AuditHelper.Audit(LogType.LOG_TYPE_COMMANDS,
										LogSubtype.LOG_SUBTYPE_COMMAND_NOT_EXECUTED, User.USER_SYSTEM, LogLevel.INFO,
										commandElementType.getLabelAlias(), element.getAlias(),
										new ObjectMapper().writeValueAsString(signallingCommand.getSignallingParams()),
										commandElementErrorPreconditions.getToStr());

							} catch (JsonProcessingException e) {

								log.error(e.getMessage());
								log.debug(ExceptionUtils.getStackTrace(e));
							}

							if (responseCommand != null) {
								responseCommand.addElement(commandElementErrorPreconditions);
							}

						}
					}

					if (commandElementError != null) {

						try {
							AuditHelper.Audit(LogType.LOG_TYPE_COMMANDS, LogSubtype.LOG_SUBTYPE_COMMAND_NOT_EXECUTED,
									User.USER_SYSTEM, LogLevel.INFO,
									commandElementType != null ? commandElementType.getLabelAlias() : null,
									element != null ? element.getAlias() : null,
									new ObjectMapper().writeValueAsString(signallingCommand.getSignallingParams()),
									commandElementError.getToStr());

						} catch (JsonProcessingException e) {

							log.error(e.getMessage());
							log.debug(ExceptionUtils.getStackTrace(e));
						}

						if (responseCommand != null) {
							responseCommand.addElement(commandElementError);
						}
					}

					if (commandElementError == null && commandElementErrorPreconditions == null) {

						signallingCommands.add(signallingCommand);
					}
				}

				if (responseCommand == null || CollectionUtils.isEmpty(responseCommand.getBasicElements())
						|| (setSignallingsCommand.getSendWithAnyError() != null
								&& setSignallingsCommand.getSendWithAnyError().booleanValue())) {

					signallingCommands.forEach(signallingsCommand -> {

						log.info("SendCommand " + signallingsCommand);
						EntitiesManager.getInstance().sendCommand(signallingsCommand);
					});
				}
			}

			if (responseCommand != null) {

				log.info("SendCommand " + responseCommand);
				EntitiesManager.getInstance().sendCommand(responseCommand);
			}

		}
	}

	public Set<Element> deleteExpiredCommand(Set<Pair<Long, Element>> allQueueElements) {

		Set<Element> changedElements = new HashSet<>();

		List<PmElementTypeQueue> pmElementTypeQueues = EntitiesManager.getInstance().getPmElementTypeQueues();

		if (CollectionUtils.isNotEmpty(pmElementTypeQueues) && allQueueElements != null) {

			Long now = Instant.now().toEpochMilli();

			for (PmElementTypeQueue pmElementTypeQueue : pmElementTypeQueues) {

				if (pmElementTypeQueue.getElementTypeId() != null) {

					List<Element> elements = EntitiesManager.getInstance()
							.getElements(pmElementTypeQueue.getElementTypeId());

					if (CollectionUtils.isNotEmpty(elements)) {

						for (Element element : elements) {

							Semaphore mutex = acquire(element.getElementTypeId(), element.getId());

							if (mutex == null) {

								log.error("Mutex not found: elementTypeId = " + element.getElementTypeId()
										+ "elementId = " + element.getId());

							} else {

								allQueueElements.add(new MutablePair<>(pmElementTypeQueue.getQueueId(), element));

								List<PmCommandElement> pmCommandElements = EntitiesManager.getInstance()
										.getsPmCommandElement(pmElementTypeQueue.getQueueId(),
												element.getElementTypeId(), element.getId());

								if (CollectionUtils.isNotEmpty(pmCommandElements)) {

									pmCommandElements = pmCommandElements.stream()
											.filter(be -> be.getEndTime() != null && now >= be.getEndTime())
											.collect(Collectors.toList());

									if (CollectionUtils.isNotEmpty(pmCommandElements)) {

										changedElements.add(element);

										pmCommandElements.forEach(be -> {

											List<PmCommandElementValue> pmCommandElementValues = EntitiesManager
													.getInstance().getsPmCommandElementValues(be);

											if (CollectionUtils.isNotEmpty(pmCommandElementValues)) {

												EntitiesManager.getInstance()
														.delPmCommandElementValue(pmCommandElementValues);
											}

											EntitiesManager.getInstance().delPmCommandElement(be);
										});
									}

								}
								mutex.release();
							}
						}
					}

				}
			}
		}

		return changedElements;
	}

	public List<Element> setAddCommandsCommand(SetAddCommandsCommand setAddCommandsCommand) {

		List<Element> changedElements = null;

		if (setAddCommandsCommand != null && CollectionUtils.isNotEmpty(setAddCommandsCommand.getCommands())) {

			List<PmCommandElement> commandsToProcess = new ArrayList<>();
			Set<Pair<Long, Element>> pmQueueElementToProcess = new HashSet<>();

			for (PmCommandElement pmCommandElement : setAddCommandsCommand.getCommands()) {

				if (pmCommandElement.getElementId() != null && pmCommandElement.getElementTypeId() != null
						&& EntitiesManager.getInstance().getElement(pmCommandElement.getElementTypeId(),
								pmCommandElement.getElementId()) != null
						&& pmCommandElement.getCommandElementTypeId() != null
						&& EntitiesManager.getInstance().getCommandElementType(
								pmCommandElement.getCommandElementTypeId(),
								pmCommandElement.getElementTypeId()) != null) {

					CommandElementType commandElementType = EntitiesManager.getInstance().getCommandElementType(
							pmCommandElement.getCommandElementTypeId(), pmCommandElement.getElementTypeId());

					if (commandElementType != null) {

						pmCommandElement.setCommandElementType(commandElementType);
						PmCommandElement newPmCommandElement = addCommand(pmCommandElement);

						if (newPmCommandElement != null) {

							pmQueueElementToProcess.add(new MutablePair<>(pmCommandElement.getQueueId(),
									new Element(pmCommandElement.getElementTypeId(), pmCommandElement.getElementId())));

						} else {
							commandsToProcess.add(pmCommandElement);
						}
					}

				}
			}

			if (CollectionUtils.isNotEmpty(pmQueueElementToProcess)) {

				List<PmCommandElement> newCommandsToProcess = proccessPmQueuesElements(pmQueueElementToProcess);

				if (CollectionUtils.isNotEmpty(newCommandsToProcess)) {

					commandsToProcess.addAll(newCommandsToProcess);
				}

				changedElements = pmQueueElementToProcess.stream().map(Pair::getValue).collect(Collectors.toList());
			}

			proccessCommands(commandsToProcess, setAddCommandsCommand, setAddCommandsCommand.getSendWithAnyError());
		}

		return changedElements;
	}

	public List<Element> setDeleteCommandsCommand(SetDeleteCommandsCommand setDeleteCommandsCommand) {

		List<Element> changedElements = null;

		if (setDeleteCommandsCommand != null && (setDeleteCommandsCommand.getSource() != null
				|| CollectionUtils.isNotEmpty(setDeleteCommandsCommand.getCommands()))) {

			Set<Pair<Long, Element>> pmQueueElementToProcess = new HashSet<>();
			List<PmCommandElement> commandsToProcess = null;

			List<PmCommandElement> commandToDelete = null;

			if (setDeleteCommandsCommand.getSource() != null) {

				commandToDelete = EntitiesManager.getInstance()
						.getsPmCommandElementBySource(setDeleteCommandsCommand.getSource());
			}

			if (CollectionUtils.isNotEmpty(setDeleteCommandsCommand.getCommands())) {

				if (commandToDelete == null) {

					commandToDelete = setDeleteCommandsCommand.getCommands();

				} else {

					commandToDelete.addAll(setDeleteCommandsCommand.getCommands());
				}
			}

			if (CollectionUtils.isNotEmpty(commandToDelete)) {

				for (PmCommandElement pmCommandElement : commandToDelete) {

					PmCommandElement newPmCommandElement = deleteCommand(pmCommandElement);

					if (newPmCommandElement != null) {

						pmQueueElementToProcess.add(new MutablePair<>(pmCommandElement.getQueueId(),
								new Element(pmCommandElement.getElementTypeId(), pmCommandElement.getElementId())));
					}
				}
			}

			if (CollectionUtils.isNotEmpty(pmQueueElementToProcess)) {

				commandsToProcess = proccessPmQueuesElements(pmQueueElementToProcess);
				changedElements = pmQueueElementToProcess.stream().map(Pair::getValue).collect(Collectors.toList());
			}

			proccessCommands(commandsToProcess, setDeleteCommandsCommand, true);
		}

		return changedElements;

	}

	public List<Element> setPriorityComand(SetPriorityComand setPriorityComand) {

		List<Element> changedElements = null;

		if (setPriorityComand != null && setPriorityComand.getElement() != null
				&& setPriorityComand.getPriority() != null && setPriorityComand.getEnable() != null) {

			List<PmCommandElement> commandsToProcess = null;

			PmDisabledQueuePriority result = null;
			PmDisabledQueuePriority pmDisabledQueuePriority = new PmDisabledQueuePriority();

			pmDisabledQueuePriority.setElementTypeId(setPriorityComand.getElement().getElementTypeId());
			pmDisabledQueuePriority.setElementId(setPriorityComand.getElement().getId());
			pmDisabledQueuePriority.setQueueId(setPriorityComand.getPriority().getQueueId());
			pmDisabledQueuePriority.setPriorityId(setPriorityComand.getPriority().getId());

			if (setPriorityComand.getEnable().booleanValue()) {

				result = EntitiesManager.getInstance().deletePmDisabledQueuePriority(pmDisabledQueuePriority);

			} else {

				result = EntitiesManager.getInstance().createPmDisabledQueuePriority(pmDisabledQueuePriority);
			}

			if (result != null) {

				Set<Pair<Long, Element>> pmQueueElementToProcess = new HashSet<>();

				pmQueueElementToProcess.add(new MutablePair<>(result.getQueueId(),
						new Element(result.getElementTypeId(), result.getElementId())));

				commandsToProcess = proccessPmQueuesElements(pmQueueElementToProcess);

				changedElements = pmQueueElementToProcess.stream().map(Pair::getValue).collect(Collectors.toList());
			}

			proccessCommands(commandsToProcess, setPriorityComand, true);
		}

		return changedElements;
	}

	public void proccessCommands(List<PmCommandElement> commandsToProcess, Command command, Boolean sendWithAnyError) {

		ResponseCommand responseCommand = command != null && command.getResponseCommand() instanceof ResponseCommand
				? (ResponseCommand) command.getResponseCommand()
				: null;

		if (CollectionUtils.isNotEmpty(commandsToProcess)) {

			List<SignallingCommand> signallingCommands = new ArrayList<>();

			for (PmCommandElement pmCommandElement : commandsToProcess) {

				CommandElementError commandElementError = null;

				Element element = EntitiesManager.getInstance().getElement(pmCommandElement.getElementTypeId(),
						pmCommandElement.getElementId());

				CommandElementType commandElementType = EntitiesManager.getInstance().getCommandElementType(
						pmCommandElement.getCommandElementTypeId(), pmCommandElement.getElementTypeId());

				if (element == null) {

					commandElementError = new CommandElementError();
					commandElementError.setError(CommandElementError.ELEMENT_NOT_FOUND);
					commandElementError.setElementId(pmCommandElement.getElementId());
					commandElementError.setElementTypeId(pmCommandElement.getElementTypeId());

				} else if (commandElementType == null) {

					commandElementError = new CommandElementError();
					commandElementError.setError(CommandElementError.COMMAND_NOT_FOUND);
					commandElementError.setElementId(pmCommandElement.getElementId());
					commandElementError.setElementTypeId(pmCommandElement.getElementTypeId());

				} else {

					List<?> commands = proccessCommand(pmCommandElement, commandElementType.getOnChange());

					if (!CollectionUtils.isEmpty(commands)) {

						commands.forEach(cmd -> {

							if (cmd instanceof SignallingCommand) {

								CommandElementError commandElementErrorElementConnected = null;
								CommandElementError commandElementErrorPreconditions = null;

								SignallingCommand signallingCommand = (SignallingCommand) cmd;

								commandElementErrorElementConnected = checkElementConnected(signallingCommand);

								commandElementErrorPreconditions = checkPreconditions(signallingCommand);

								if (commandElementErrorPreconditions != null) {

									try {
										AuditHelper.Audit(LogType.LOG_TYPE_COMMANDS,
												LogSubtype.LOG_SUBTYPE_COMMAND_NOT_EXECUTED, User.USER_SYSTEM,
												LogLevel.INFO, commandElementType.getLabelAlias(), element.getAlias(),
												new ObjectMapper()
														.writeValueAsString(signallingCommand.getSignallingParams()),
												commandElementErrorPreconditions.getToStr());

									} catch (JsonProcessingException e) {

										log.error(e.getMessage());
										log.debug(ExceptionUtils.getStackTrace(e));
									}

									if (responseCommand != null) {

										responseCommand.addElement(commandElementErrorPreconditions);
									}

								}

								if (commandElementErrorElementConnected != null) {

									try {
										AuditHelper.Audit(LogType.LOG_TYPE_COMMANDS,
												LogSubtype.LOG_SUBTYPE_COMMAND_NOT_EXECUTED, User.USER_SYSTEM,
												LogLevel.INFO,
												commandElementType != null ? commandElementType.getLabelAlias() : null,
												element != null ? element.getAlias() : null,
												new ObjectMapper()
														.writeValueAsString(signallingCommand.getSignallingParams()),
												commandElementErrorElementConnected.getToStr());

									} catch (JsonProcessingException e) {

										log.error(e.getMessage());
										log.debug(ExceptionUtils.getStackTrace(e));
									}

									if (responseCommand != null) {
										responseCommand.addElement(commandElementErrorElementConnected);
									}
								}

								if (commandElementErrorElementConnected == null
										&& commandElementErrorPreconditions == null) {

									signallingCommands.add(signallingCommand);
								}

							} else {
								log.info("SendCommand " + cmd);
								EntitiesManager.getInstance().sendCommand((Command) cmd);
							}
						});
					}
				}
			}

			if (responseCommand == null || CollectionUtils.isEmpty(responseCommand.getBasicElements())
					|| (Boolean.TRUE.equals(sendWithAnyError))) {

				signallingCommands.forEach(signallingsCommand -> {

					log.info("SendCommand " + signallingsCommand);
					EntitiesManager.getInstance().sendCommand(signallingsCommand);
				});
			}
		}

		if (responseCommand != null) {

			log.info("SendCommand " + responseCommand);
			EntitiesManager.getInstance().sendCommand(responseCommand);
		}
	}

	public Boolean init(String cronMaintenacePmQueue) {

		Boolean ok = true;

		launchJob(cronMaintenacePmQueue);

		return ok;
	}

	public Boolean deInit() {

		Boolean ok = true;

		try {
			if (scheduler != null) {

				log.debug("Shutdown scheduled jobs ...");
				scheduler.shutdown(true);
				log.debug("Shutdown Complete");
				log.debug("Executed " + scheduler.getMetaData().getNumberOfJobsExecuted() + " jobs.");
			}

		} catch (SchedulerException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			ok = false;
		}

		return ok;
	}

	public List<PmCommandElement> proccessPmQueuesElements(Set<Pair<Long, Element>> queueElementToProcess) {

		List<PmCommandElement> commandsToProcess = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(queueElementToProcess)) {

			Long now = Instant.now().toEpochMilli();
			
			//Long totalNConflicts = 0L;

			for (Pair<Long, Element> queueElement : queueElementToProcess) {
				
				if (queueElement.getKey() != null && queueElement.getRight() != null
						&& queueElement.getRight().getElementTypeId() != null
						&& queueElement.getRight().getId() != null) {

					Semaphore mutex = acquire(queueElement.getRight().getElementTypeId(),
							queueElement.getRight().getId());

					if (mutex == null) {

						log.error("Mutex not found: elementTypeId = " + queueElement.getRight().getElementTypeId()
								+ "elementId = " + queueElement.getRight().getId());

					} else {

						List<PmCommandElement> pmCommandElement = EntitiesManager.getInstance().getsPmCommandElement(
								queueElement.getKey(), queueElement.getRight().getElementTypeId(),
								queueElement.getRight().getId());

						if (CollectionUtils.isNotEmpty(pmCommandElement)) {

							for (PmCommandElement commandElement : pmCommandElement) {

								proccessConflicts(pmCommandElement, commandElement);
							}

							PmCommandElement pmCommandElementEnabled = pmCommandElement.stream()
									.filter(be -> be.getEnable() != null && be.getEnable().booleanValue()).findFirst()
									.orElse(null);

							pmCommandElement = pmCommandElement.stream()
									.filter(be -> be.getStartTime() != null && now >= be.getStartTime()
											&& be.getEndTime() != null && now <= be.getEndTime()
											&& EntitiesManager.getInstance().getPmDisabledQueuePriority(be) == null)
									.sorted(new PmPmCommandElementComparator()).collect(Collectors.toList());

							if (CollectionUtils.isEmpty(pmCommandElement)) {

								if (pmCommandElementEnabled != null) {

									EntitiesManager.getInstance().disablePmCommandElement(pmCommandElementEnabled);
								}
							} else {

								try {
									if (pmCommandElementEnabled != null) {

										if (!pmCommandElementEnabled.equals(pmCommandElement.get(0))) {

											EntitiesManager.getInstance()
													.disablePmCommandElement(pmCommandElementEnabled);
											EntitiesManager.getInstance()
													.enablePmCommandElement(pmCommandElement.get(0));

											PmCommandElement pmCommandElementToProcess;

											pmCommandElementToProcess = (PmCommandElement) pmCommandElement.get(0)
													.clone();

											commandsToProcess.add(pmCommandElementToProcess);
											pmCommandElementToProcess
													.setPmCommandElementValues(EntitiesManager.getInstance()
															.getsPmCommandElementValues(pmCommandElementToProcess));
										}

									} else {

										EntitiesManager.getInstance().enablePmCommandElement(pmCommandElement.get(0));

										PmCommandElement pmCommandElementToProcess = (PmCommandElement) pmCommandElement
												.get(0).clone();
										commandsToProcess.add(pmCommandElementToProcess);
										pmCommandElementToProcess.setPmCommandElementValues(EntitiesManager
												.getInstance().getsPmCommandElementValues(pmCommandElementToProcess));

									}
								} catch (CloneNotSupportedException e) {

									log.error(e.getMessage());
									log.debug(ExceptionUtils.getStackTrace(e));
								}
							}
						}
						mutex.release();
					}
				}
			}
			
			publishConflictsGenericData(getNConflicts());
		}

		return commandsToProcess;
	}

	private List<?> proccessCommand(PmCommandElement pmCommandElement, String onChange) {

		List<?> commands = null;

		if (pmCommandElement.getPmCommandElementValues() != null) {

			for (PmCommandElementValue pmCommandElementValue : pmCommandElement.getPmCommandElementValues()) {

				if (pmCommandElementValue.getCommandElementTypeParamId() != null
						&& pmCommandElementValue.getCommandElementTypeId() != null
						&& pmCommandElementValue.getElementTypeId() != null) {

					CommandElementTypeParam commandElementTypeParam = null;

					commandElementTypeParam = EntitiesManager.getInstance().getCommandElementTypeParam(
							pmCommandElementValue.getCommandElementTypeId(), pmCommandElementValue.getElementTypeId(),
							pmCommandElementValue.getCommandElementTypeParamId());

					if (commandElementTypeParam != null) {

						pmCommandElementValue.setCommandElementTypeParam(commandElementTypeParam);
					}

				}
			}
		}

		String groovyFile = PATH_GROOVY_EQUIPMENT_MANAGER_SIGNALLING + (onChange != null ? onChange : DEFAULT_ON_CHANGE)
				+ ".groovy";

		log.debug("--> " + groovyFile);

		GroovyObject groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);

		if (groovyObj != null) {

			commands = (List<?>) groovyObj.invokeMethod("process", new Object[] { pmCommandElement });

		} else {

			log.error(" Not found object with " + groovyFile);

		}
		return commands;
	}

	private CommandElementError checkElementConnected(SignallingCommand signallingCommand) {

		CommandElementError commandElementError = null;

		if (signallingCommand != null) {

			ElementType elementType = EntitiesManager.getInstance()
					.getElementType(signallingCommand.getElementTypeId());

			if (elementType != null && elementType.getCheckIfConnected() != null
					&& elementType.getCheckIfConnected().booleanValue()) {

				ElementState elementState = EntitiesManager.getInstance()
						.getElementState(signallingCommand.getElementTypeId(), signallingCommand.getElementId());

				if (elementState != null && ((elementType.getDisconnectedElementTypeStateId() != null
						&& elementType.getDisconnectedElementTypeStateId().equals(elementState.getStateId()))
						|| (elementType.getInitialElementTypeStateId() != null
								&& elementType.getInitialElementTypeStateId().equals(elementState.getStateId())))) {

					commandElementError = new CommandElementError();
					commandElementError.setError(CommandElementError.ELEMENT_NOT_CONNECTED);
					commandElementError.setElementId(signallingCommand.getElementId());
					commandElementError.setElementTypeId(signallingCommand.getElementTypeId());
				}
			}
		}

		return commandElementError;
	}

	private CommandElementError checkPreconditions(SignallingCommand signallingCommand) {

		CommandElementError commandElementError = null;

		if (signallingCommand != null) {

			List<CommandElementPrecondition> commandElementPrecondition = EntitiesManager.getInstance()
					.validateCommandElement(SignallingCommandHelper.get(signallingCommand));

			if (CollectionUtils.isNotEmpty(commandElementPrecondition)) {

				commandElementError = new CommandElementError();
				commandElementError.setError(CommandElementError.NOT_COMPLY_PRECONDITIONS);
				commandElementError.setElementId(signallingCommand.getElementId());
				commandElementError.setElementTypeId(signallingCommand.getElementTypeId());
				commandElementError.setCommandElementPreconditions(commandElementPrecondition);
			}
		}

		return commandElementError;

	}

	private void launchJob(String cronExpression) {

		if (!StringUtils.isEmpty(cronExpression)) {

			if (scheduler == null) {

				initQuartzScheduler();
			}
			createJob(ScheduledMaintenacePmQueueJob.class, cronExpression);

		} else {

			log.error("Not found cronMaintenacePmQueue");
		}
	}

	private boolean initQuartzScheduler() {

		Boolean ok = true;

		SchedulerFactory sf = new StdSchedulerFactory();
		try {

			scheduler = sf.getScheduler();
			scheduler.getContext().put(this.getClass().getSimpleName(), this);
			scheduler.start();

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			ok = false;
		}

		return ok;
	}

	private void createJob(Class<? extends Job> jobClass, String cronExpression) {

		try {
			JobDetail job = newJob(jobClass)
					.withIdentity("job_" + jobClass.getSimpleName(), "group_" + jobClass.getSimpleName()).build();

			if (!StringUtils.isEmpty(cronExpression)) {

				CronTrigger trigger = newTrigger()
						.withIdentity("trigger_" + jobClass.getSimpleName(), "group_" + jobClass.getSimpleName())
						.withSchedule(cronSchedule(cronExpression)).build();

				Date dt = scheduler.scheduleJob(job, trigger);

				log.info(job.getKey() + " has been scheduled to run at: " + dt + " and repeat based on expression: "
						+ trigger.getCronExpression());

			} else {

				log.debug(job.getKey() + " is not scheduled");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private PmCommandElement addCommand(PmCommandElement pmCommandElement) {

		PmCommandElement result = null;

		PmQueuePriority pmQueuePriority = null;

		if (pmCommandElement != null && pmCommandElement.getPriorityId() != null
				&& pmCommandElement.getQueueId() != null
				&& (pmQueuePriority = EntitiesManager.getInstance().getPmQueuePriority(pmCommandElement.getQueueId(),
						pmCommandElement.getPriorityId())) != null
				&& EntitiesManager.getInstance().getPmElementTypeQueue(pmCommandElement.getElementTypeId(),
						pmCommandElement.getQueueId()) != null
				&& pmCommandElement.getCommandElementType() != null
				&& Boolean.TRUE.equals(pmCommandElement.getCommandElementType().getPmQueueCompatibility())) {

			try {

				result = (PmCommandElement) pmCommandElement.clone();
				result.setPmCommandElementValues(null);
				result.setCommandElementType(null);

				result.generateId();

				if (result.getStartTime() == null) {

					result.setStartTime(Instant.now().toEpochMilli());
				}

				if (result.getEndTime() == null) {

					result.setEndTime(DEFAULT_END_TIME);
				}

				if (CollectionUtils.isNotEmpty(pmCommandElement.getPmCommandElementValues())) {

					for (PmCommandElementValue pmCommandElementValue : pmCommandElement.getPmCommandElementValues()) {

						if (!pmCommandElement.getElementId().equals(pmCommandElementValue.getElementId())
								|| !pmCommandElement.getElementTypeId().equals(pmCommandElementValue.getElementTypeId())
								|| !pmCommandElement.getCommandElementTypeId()
										.equals(pmCommandElementValue.getCommandElementTypeId())
								|| !pmCommandElement.getQueueId().equals(pmCommandElementValue.getQueueId())
								|| pmCommandElementValue.getCommandElementTypeParamId() == null
								|| EntitiesManager.getInstance().getCommandElementTypeParam(
										pmCommandElementValue.getCommandElementTypeId(),
										pmCommandElementValue.getElementTypeId(),
										pmCommandElementValue.getCommandElementTypeParamId()) == null) {
							result = null;
							break;

						} else {
							pmCommandElementValue.setPmCommandElementId(result.getId());
						}
					}
				}

				if (result != null) {

					Semaphore mutex = acquire(pmCommandElement.getElementTypeId(), pmCommandElement.getElementId());

					if (mutex == null) {

						log.error("Mutex not found: elementTypeId = " + pmCommandElement.getElementTypeId()
								+ "elementId = " + pmCommandElement.getElementId());
					} else {

						// Solo puede haber un comando en las colas de planes y incidencias
						if (Boolean.TRUE.equals(pmQueuePriority.getIncidentPriority())
								|| Boolean.TRUE.equals(pmQueuePriority.getPlanPriority())
								|| Boolean.TRUE.equals(pmQueuePriority.getTravelTimePriority())) {

							List<PmCommandElement> pmCommandElementsToDel = EntitiesManager.getInstance()
									.getsPmCommandElement(result.getQueueId(), result.getElementTypeId(),
											result.getElementId(), result.getPriorityId());

							if (CollectionUtils.isNotEmpty(pmCommandElementsToDel)) {

								for (PmCommandElement pmCommandElementToDel : pmCommandElementsToDel) {

									List<PmCommandElementValue> pmCommandElementValuesToDel = EntitiesManager
											.getInstance().getsPmCommandElementValues(pmCommandElementToDel);

									if (CollectionUtils.isNotEmpty(pmCommandElementValuesToDel)) {

										EntitiesManager.getInstance()
												.delPmCommandElementValue(pmCommandElementValuesToDel);
									}

									EntitiesManager.getInstance().delPmCommandElement(pmCommandElementToDel);
								}
							}
						}

						result = EntitiesManager.getInstance().putPmCommandElement(result);

						if (CollectionUtils.isNotEmpty(pmCommandElement.getPmCommandElementValues())) {

							EntitiesManager.getInstance()
									.putPmCommandElementValue(pmCommandElement.getPmCommandElementValues());
						}

						mutex.release();
					}

				}
			} catch (CloneNotSupportedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return result;
	}

	private PmCommandElement deleteCommand(PmCommandElement pmCommandElement) {

		PmCommandElement result = null;

		if (pmCommandElement != null && pmCommandElement.getQueueId() != null
				&& pmCommandElement.getCommandElementTypeId() != null && pmCommandElement.getId() != null
				&& pmCommandElement.getElementTypeId() != null && pmCommandElement.getElementId() != null) {

			Semaphore mutex = acquire(pmCommandElement.getElementTypeId(), pmCommandElement.getElementId());

			if (mutex == null) {

				log.error("Mutex not found: elementTypeId = " + pmCommandElement.getElementTypeId() + "elementId = "
						+ pmCommandElement.getElementId());
			} else {

				List<PmCommandElementValue> pmCommandElementValues = EntitiesManager.getInstance()
						.getsPmCommandElementValues(pmCommandElement);

				if (CollectionUtils.isNotEmpty(pmCommandElementValues)) {

					EntitiesManager.getInstance().delPmCommandElementValue(pmCommandElementValues);
				}

				result = EntitiesManager.getInstance().delPmCommandElement(pmCommandElement);

				mutex.release();
			}

		}

		return result;
	}

	private Semaphore acquire(Long elementTypeId, Long elementId) {

		boolean acquire = false;
		Semaphore mutex = null;

		if (elementId != null) {

			mutex = EntitiesManager.getInstance().getElementsMutex().get(new MutablePair<>(elementTypeId, elementId));

			try {
				if (mutex != null) {

					while (!genericExecutor.isStop() && !(acquire = mutex.tryAcquire(500, TimeUnit.MILLISECONDS)))
						;
				}

			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
			}
		}

		return acquire ? mutex : null;
	}

	private void proccessConflicts(List<PmCommandElement> commandElements, PmCommandElement command) {

		PmQueuePriority queuePriorityTarget = EntitiesManager.getInstance().getPmQueuePriority(command.getQueueId(),
				command.getPriorityId());

		if (queuePriorityTarget != null && Boolean.TRUE.equals(queuePriorityTarget.getCheckConflict())) {

			int conflictsCount = 0;

			for (PmCommandElement pmCommandElement : commandElements) {

				// Tenemos que comparar los ids para que se salte el objeto que es el mismo.
				if (((command.getPriorityId().equals(pmCommandElement.getPriorityId())
						&& command.getQueueId().equals(pmCommandElement.getQueueId()))
						&& (!pmCommandElement.getId().equals(command.getId()) || !pmCommandElement
								.getCommandElementTypeId().equals(command.getCommandElementTypeId())))
						&& ((command.getStartTime() >= pmCommandElement.getStartTime()
								&& command.getStartTime() < pmCommandElement.getEndTime())
								|| (pmCommandElement.getStartTime() >= command.getStartTime()
										&& pmCommandElement.getStartTime() < command.getEndTime()))) {

					if (!Boolean.TRUE.equals(command.getConflict())) {

						// se ha generado un nuevo conflicto dodne antes no lo habia, por lo que lo
						// seteamos y notificamos. //en cas de que esta condicion no se cumpliera,
						// significa que ya esta anteriormente a true,
						// por lo que no es necesario volverlo a setear ni notificarlo.
						command.setConflict(true);
						EntitiesManager.getInstance().putPmCommandElement(command);

					}

					conflictsCount++;
				}
			}

			if (conflictsCount == 0 && (!Boolean.FALSE.equals(command.getConflict()))) {
				
				command.setConflict(false);
				EntitiesManager.getInstance().putPmCommandElement(command);
			}
		}
	}

	private Long getNConflicts() {
		
		Long nConflictCommands = 0L;

		List<PmCommandElement> allPmCommandElements = EntitiesManager.getInstance().getsPmCommandElement();
		
		if (CollectionUtils.isNotEmpty(allPmCommandElements)) {
			
			nConflictCommands =allPmCommandElements.stream()
					.filter(e ->Boolean.TRUE.equals(e.getConflict())).count();
		}
	
		return nConflictCommands;
	}
	
	private void publishConflictsGenericData(Long nConflicts) {

		GenericDataRepository genericDataRepository = ((GenericDataRepository) EntitiesManager.getInstance()
				.getRepositoryData().getRepository(GenericData.class));
		
		if (genericDataRepository != null) {

			GenericData genericData = genericDataRepository.get(GenericData.GENERIC_DATA_CONFLICT_SIGNALINGS, false);
			
			if (genericData == null || (!genericData.getValue().equals(String.valueOf(nConflicts)))) {

				if (genericData == null) {

					genericData = new GenericData();
					genericData.setId(GenericData.GENERIC_DATA_CONFLICT_SIGNALINGS);
				}

				genericData.setValue(String.valueOf(nConflicts));

				genericDataRepository.put(genericData);

				SetGenericData setGenericData = new SetGenericData();
				setGenericData.setGenericDatas(Arrays.asList(genericData));

				EntitiesManager.getInstance().sendCommand(setGenericData);
			}
		}
	}
}