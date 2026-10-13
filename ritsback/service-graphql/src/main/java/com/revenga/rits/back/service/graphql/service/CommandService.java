package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CommandElementError;
import com.revenga.rits.back.data.core.model.CommandElementPrecondition;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.ResponseCommand;
import com.revenga.rits.back.data.core.model.command.ResponseToServiceGraphqlCommand;
import com.revenga.rits.back.data.core.model.command.SetAddCommandsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeleteCommandsCommand;
import com.revenga.rits.back.data.core.model.command.SetPriorityComand;
import com.revenga.rits.back.data.core.model.command.SetSignallingsCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.data.core.util.GroovyManager;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.front.core.model.helper.CommandElementHelper;
import com.revenga.rits.back.front.core.model.helper.CommandElementPreconditionHelper;
import com.revenga.rits.back.front.core.model.helper.CommandElementTypeParamHelper;
import com.revenga.rits.back.front.core.model.helper.PmCommandElementHelper;
import com.revenga.rits.back.front.core.model.helper.command.SignallingCommandHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementErrorVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementPreconditionVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;
import com.revenga.rits.back.front.core.model.vo.PmCommandElementVO;
import com.revenga.rits.back.manager.lib.service.ResponseManager;
import com.revenga.rits.back.service.graphql.type.CommandElementErrorGraphql;

import groovy.lang.GroovyObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CommandService {

	private static final String DEFAULT_ON_CHANGE = ConfigProperty.getInstance().getProperty("application.properties",
			"service.command_service.default_on_change");

	@Deprecated
	public List<CommandElementPreconditionVO> sendCommandElement(final List<CommandElementVO> commands) {

		List<CommandElementPreconditionVO> commandElementPreconditions = new ArrayList<>();

		if (CollectionUtils.isEmpty(commands)) {

			return commandElementPreconditions;
		}

		for (CommandElementVO command : commands) {

			if (command.getCommandElementTypeId() != null) {

				Key commandElementTypeIdKey = new Key(command.getCommandElementTypeId());

				if (commandElementTypeIdKey.getClazz() == CommandElementType.class) {

					CommandElementType commandElementType = new CommandElementType();
					commandElementType.setPks(commandElementTypeIdKey.getIds());

					commandElementType = EntitiesManager.getInstance().getCommandElementTypeRepository()
							.get(commandElementType.getId(), commandElementType.getElementTypeId(), false);

					if (commandElementType != null) {

						if (command.getCommandElementValues() != null) {

							Key key = null;

							for (CommandElementValueVO commandElementValue : command.getCommandElementValues()) {

								if (commandElementValue.getCommandElementTypeParamId() != null) {

									key = new Key(commandElementValue.getCommandElementTypeParamId());

									if (key.getClazz() == CommandElementTypeParam.class) {

										CommandElementTypeParam commandElementTypeParam = null;

										commandElementTypeParam = EntitiesManager.getInstance()
												.getCommandElementTypeParamRepository().get(Long.valueOf(key.getId(1)),
														Long.valueOf(key.getId(2)), Long.valueOf(key.getId(3)), false);

										if (commandElementTypeParam != null) {

											commandElementValue.setCommandElementTypeParam(
													CommandElementTypeParamHelper.get(commandElementTypeParam));

										}
									}
								}
							}
						}

						List<CommandElementPrecondition> commandElementPreconditionsCore = EntitiesManager.getInstance()
								.getCommandElementPreconditionRepository().validate(CommandElementHelper.get(command));

						for (CommandElementPrecondition cmdep : commandElementPreconditionsCore) {

							commandElementPreconditions.add(CommandElementPreconditionHelper.get(cmdep));

						}

						if (CollectionUtils.isEmpty(commandElementPreconditions)) {

							String groovyFile = "service-graphql/groovy/"
									+ (commandElementType.getOnChange() != null ? commandElementType.getOnChange()
											: DEFAULT_ON_CHANGE)
									+ ".groovy";

							log.debug("--> " + groovyFile);

							GroovyObject groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);

							if (groovyObj != null) {

								List<?> cmds = (List<?>) groovyObj.invokeMethod("process", new Object[] { command });

								if (!CollectionUtils.isEmpty(cmds)) {

									cmds.forEach(cmd -> {

										if (cmd instanceof SignallingCommand) {

											List<CommandElementPrecondition> cmdElementPreconditions = EntitiesManager
													.getInstance().getCommandElementPreconditionRepository()
													.validate(CommandElementHelper
															.get(SignallingCommandHelper.get((SignallingCommand) cmd)));

											if (!CollectionUtils.isEmpty(cmdElementPreconditions)) {

												for (CommandElementPrecondition commandElementPrecondition : cmdElementPreconditions) {

													commandElementPreconditions.add(CommandElementPreconditionHelper
															.get(commandElementPrecondition));
												}
											}
										}
									});

									if (!CollectionUtils.isEmpty(commandElementPreconditions)) {

										String audit = "[LBL_NOT_COMPLY_PRECONDITIONS] => ";

										for (CommandElementPreconditionVO cep : commandElementPreconditions) {

											audit = audit + cep.getToStr();
										}

										/*
										 * AuditHelper.Audit(LogType.LOG_TYPE_COMMANDS,
										 * LogSubtype.LOG_SUBTYPE_COMMAND_EXECUTED, User.USER_SYSTEM, LogLevel.INFO,
										 * commandElementType.getLabelAlias(), element.getAlias(), new
										 * ObjectMapper().writeValueAsString(cmd.getSignallingParams()));
										 */
										AuditHelper.Audit(LogType.LOG_TYPE_COMMANDS,
												LogSubtype.LOG_SUBTYPE_COMMAND_NOT_EXECUTED, User.USER_SYSTEM,
												LogLevel.DEBUG, audit);
										log.info(audit);

									} else {

										cmds.forEach(cmd -> {
											log.info("SendCommand " + cmd);
											EntitiesManager.getInstance().sendCommand((Command) cmd);
										});
									}
								}
							} else {

								log.error(" Not found object with " + groovyFile);
							}
						}
					} else {

						log.error(" Not found  commandElementType " + command.getCommandElementTypeId());
					}
				}
			}
		}

		return commandElementPreconditions;
	}

	@Deprecated
	public List<CommandElementErrorVO> sendCommandElementSync(final List<CommandElementVO> commands) {

		List<CommandElementErrorVO> commandElementErrors = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(commands)) {

			SetSignallingsCommand setSignallingsCommand = new SetSignallingsCommand();
			setSignallingsCommand.setResponseCommand(new ResponseToServiceGraphqlCommand());

			for (CommandElementVO command : commands) {

				Key commandElementTypeIdKey = new Key(command.getCommandElementTypeId());

				if (commandElementTypeIdKey.getClazz() == CommandElementType.class) {

					CommandElementType commandElementType = new CommandElementType();
					commandElementType.setPks(commandElementTypeIdKey.getIds());

					commandElementType = EntitiesManager.getInstance().getCommandElementTypeRepository()
							.get(commandElementType.getId(), commandElementType.getElementTypeId(), false);

					if (commandElementType != null) {

						if (command.getCommandElementValues() != null) {

							Key key = null;

							for (CommandElementValueVO commandElementValue : command.getCommandElementValues()) {

								if (commandElementValue.getCommandElementTypeParamId() != null) {

									key = new Key(commandElementValue.getCommandElementTypeParamId());

									if (key.getClazz() == CommandElementTypeParam.class) {

										CommandElementTypeParam commandElementTypeParam = null;

										commandElementTypeParam = EntitiesManager.getInstance()
												.getCommandElementTypeParamRepository().get(Long.valueOf(key.getId(1)),
														Long.valueOf(key.getId(2)), Long.valueOf(key.getId(3)), false);

										if (commandElementTypeParam != null) {

											commandElementValue.setCommandElementTypeParam(
													CommandElementTypeParamHelper.get(commandElementTypeParam));
										}
									}
								}
							}
						}

						String groovyFile = "service-graphql/groovy/"
								+ (commandElementType.getOnChange() != null ? commandElementType.getOnChange()
										: DEFAULT_ON_CHANGE)
								+ ".groovy";

						log.debug("--> " + groovyFile);

						GroovyObject groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);

						if (groovyObj != null) {

							List<?> cmds = (List<?>) groovyObj.invokeMethod("process", new Object[] { command });

							if (!CollectionUtils.isEmpty(cmds)) {

								cmds.forEach(cmd -> {

									if (cmd instanceof SignallingCommand) {

										setSignallingsCommand.addCommand((SignallingCommand) cmd);

									} else {

										EntitiesManager.getInstance().sendCommand((Command) cmd);
									}
								});
							}
						} else {

							log.error(" Not found object with " + groovyFile);
							CommandElementError commandElementError = new CommandElementError();
							commandElementError.setError(CommandElementError.GROOVY_NOT_FOUND);
							commandElementErrors.add(new CommandElementErrorGraphql(commandElementError));
						}

					} else {

						log.error(" Not found commandElementType " + command.getCommandElementTypeId());
						CommandElementError commandElementError = new CommandElementError();
						commandElementError.setError(CommandElementError.COMMAND_NOT_FOUND);
						commandElementErrors.add(new CommandElementErrorGraphql(commandElementError));
					}
				}
			}

			if (CollectionUtils.isEmpty(commandElementErrors)
					&& CollectionUtils.isNotEmpty(setSignallingsCommand.getCommands())) {

				ResponseCommand respounse = ResponseManager.getInstance().sendCommandSync(setSignallingsCommand);

				if (respounse != null) {

					if (CollectionUtils.isNotEmpty(respounse.getBasicElements())) {

						for (BasicElement be : respounse.getBasicElements()) {

							if (be instanceof CommandElementError) {

								CommandElementError commandElementError = (CommandElementError) be;

								if (commandElementError != null) {

									commandElementErrors.add(new CommandElementErrorGraphql(commandElementError));
								}
							}
						}

					}

				} else {

					CommandElementError commandElementError = new CommandElementError();
					commandElementError.setError(CommandElementError.TIME_OUT_EXPIRED);
					commandElementErrors.add(new CommandElementErrorGraphql(commandElementError));
				}
			}
		}
		return commandElementErrors;
	}

	public List<CommandElementErrorVO> addCommandElement(List<PmCommandElementVO> commands) {

		List<CommandElementErrorVO> commandElementErrors = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(commands)) {

			SetAddCommandsCommand setAddCommandsCommand = new SetAddCommandsCommand();
			setAddCommandsCommand.setResponseCommand(new ResponseToServiceGraphqlCommand());

			for (PmCommandElementVO pmCommandElementVO : commands) {

				PmCommandElement pmCommandElement = PmCommandElementHelper.get(pmCommandElementVO);

				if (pmCommandElement != null) {

					setAddCommandsCommand.addCommand(pmCommandElement);
				}
			}

			if (CollectionUtils.isNotEmpty(setAddCommandsCommand.getCommands())) {

				ResponseCommand respounse = ResponseManager.getInstance().sendCommandSync(setAddCommandsCommand);

				if (respounse != null) {

					if (CollectionUtils.isNotEmpty(respounse.getBasicElements())) {

						for (BasicElement be : respounse.getBasicElements()) {

							if (be instanceof CommandElementError) {

								CommandElementError commandElementError = (CommandElementError) be;

								commandElementErrors.add(new CommandElementErrorGraphql(commandElementError));
							}
						}

					}

				} else {

					CommandElementError commandElementError = new CommandElementError();
					commandElementError.setError(CommandElementError.TIME_OUT_EXPIRED);
					commandElementErrors.add(new CommandElementErrorGraphql(commandElementError));
				}
			}
		}

		return commandElementErrors;
	}

	public List<CommandElementErrorVO> deleteCommandElement(List<String> commandElementsId) {

		List<CommandElementErrorVO> commandElementErrors = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(commandElementsId)) {

			SetDeleteCommandsCommand setDeleteCommandsCommand = new SetDeleteCommandsCommand();
			setDeleteCommandsCommand.setResponseCommand(new ResponseToServiceGraphqlCommand());

			for (String commandElementId : commandElementsId) {

				Key key = new Key(commandElementId);

				if (key.getClazz() == PmCommandElement.class) {

					PmCommandElement pmCommandElement = new PmCommandElement();
					pmCommandElement.setPks(key.getIds());
					setDeleteCommandsCommand.addCommand(pmCommandElement);
				}
			}

			if (CollectionUtils.isNotEmpty(setDeleteCommandsCommand.getCommands())) {

				ResponseCommand respounse = ResponseManager.getInstance().sendCommandSync(setDeleteCommandsCommand);

				if (respounse != null) {

					if (CollectionUtils.isNotEmpty(respounse.getBasicElements())) {

						for (BasicElement be : respounse.getBasicElements()) {

							if (be instanceof CommandElementError) {

								CommandElementError commandElementError = (CommandElementError) be;

								commandElementErrors.add(new CommandElementErrorGraphql(commandElementError));
							}
						}

					}

				} else {

					CommandElementError commandElementError = new CommandElementError();
					commandElementError.setError(CommandElementError.TIME_OUT_EXPIRED);
					commandElementErrors.add(new CommandElementErrorGraphql(commandElementError));
				}
			}
		}

		return commandElementErrors;
	}

	public List<CommandElementErrorVO> deleteCommandElementBySource(String source) {

		List<CommandElementErrorVO> commandElementErrors = new ArrayList<>();

		if (source != null) {

			SetDeleteCommandsCommand setDeleteCommandsCommand = new SetDeleteCommandsCommand();
			setDeleteCommandsCommand.setResponseCommand(new ResponseToServiceGraphqlCommand());
			setDeleteCommandsCommand.setSource(source);

			ResponseCommand respounse = ResponseManager.getInstance().sendCommandSync(setDeleteCommandsCommand);

			if (respounse != null) {

				if (CollectionUtils.isNotEmpty(respounse.getBasicElements())) {

					for (BasicElement be : respounse.getBasicElements()) {

						if (be instanceof CommandElementError) {

							CommandElementError commandElementError = (CommandElementError) be;

							commandElementErrors.add(new CommandElementErrorGraphql(commandElementError));
						}
					}
				}

			} else {

				CommandElementError commandElementError = new CommandElementError();
				commandElementError.setError(CommandElementError.TIME_OUT_EXPIRED);
				commandElementErrors.add(new CommandElementErrorGraphql(commandElementError));
			}

		}

		return commandElementErrors;
	}

	private List<CommandElementErrorVO> priorityAction(String elementId, String priorityId, boolean enable) {

		List<CommandElementErrorVO> commandElementErrors = new ArrayList<>();

		if (elementId != null && priorityId != null) {

			Element element = null;
			PmQueuePriority pmQueuePriority = null;

			Key keyElement = new Key(elementId);

			if (keyElement.getClazz() == Element.class) {

				element = new Element();
				element.setPks(keyElement.getIds());
			}

			Key keyPriority = new Key(priorityId);
			if (keyPriority.getClazz() == PmQueuePriority.class) {

				pmQueuePriority = new PmQueuePriority();
				pmQueuePriority.setPks(keyPriority.getIds());
			}

			if (element != null && pmQueuePriority != null) {

				SetPriorityComand setPriorityComand = new SetPriorityComand();
				setPriorityComand.setPriority(pmQueuePriority);
				setPriorityComand.setElement(element);
				setPriorityComand.setEnable(enable);

				setPriorityComand.setResponseCommand(new ResponseToServiceGraphqlCommand());

				ResponseCommand respounse = ResponseManager.getInstance().sendCommandSync(setPriorityComand);

				if (respounse != null) {

					if (CollectionUtils.isNotEmpty(respounse.getBasicElements())) {

						for (BasicElement be : respounse.getBasicElements()) {

							if (be instanceof CommandElementError) {

								CommandElementError commandElementError = (CommandElementError) be;

								commandElementErrors.add(new CommandElementErrorGraphql(commandElementError));
							}
						}

					}

				} else {

					CommandElementError commandElementError = new CommandElementError();
					commandElementError.setError(CommandElementError.TIME_OUT_EXPIRED);
					commandElementErrors.add(new CommandElementErrorGraphql(commandElementError));
				}
			}

		}
		return commandElementErrors;

	}

	public List<CommandElementErrorVO> enablePriority(String elementId, String priorityId) {

		return priorityAction(elementId, priorityId, true);
	}

	public List<CommandElementErrorVO> disablePriority(String elementId, String priorityId) {

		return priorityAction(elementId, priorityId, false);
	}
}
