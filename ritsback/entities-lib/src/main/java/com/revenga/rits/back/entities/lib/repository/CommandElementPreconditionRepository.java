package com.revenga.rits.back.entities.lib.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementPrecondition;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.CommandElementValuePrecondition;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ThresholdOperation;
import com.revenga.rits.back.data.core.util.OperationProcessor;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommandElementPreconditionRepository extends EntityRepository
		implements EntityCacheable<CommandElementPrecondition> {

	// region Constructors
	public CommandElementPreconditionRepository() {

		super(CommandElementPrecondition.class);
	}

	public CommandElementPreconditionRepository(Boolean cacheable) {

		super(CommandElementPrecondition.class, cacheable);
	}

	public CommandElementPreconditionRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(CommandElementPrecondition.class, cacheable, cacheRetentionTime);
	}

	public CommandElementPreconditionRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(CommandElementPrecondition.class, cacheable, repositories);
	}

	public CommandElementPreconditionRepository(Boolean cacheable, Long cacheRetentionTime,
			List<EntityRepository> repositories) {

		super(CommandElementPrecondition.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public CommandElementPrecondition get(Long commandElementTypeId, Long elementTypeId,
			Long commandElementPreconditionId, Boolean fillObject) {

		return (CommandElementPrecondition) getFromCache(new String[] { String.valueOf(commandElementTypeId),
				String.valueOf(elementTypeId), String.valueOf(commandElementPreconditionId) }, fillObject);
	}

	public List<CommandElementPrecondition> gets(Long commandElementTypeId, Long elementTypeId, Long elementId,
			Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(commandElementTypeId),
				String.valueOf(elementTypeId), String.valueOf(elementId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(CommandElementPrecondition.class::cast).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	public List<CommandElementPrecondition> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(CommandElementPrecondition.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	// end region getters

	private boolean validateCommandElementValuePreconditions(
			List<CommandElementValuePrecondition> commandElementValuePreconditions,
			List<CommandElementValue> commandElementValues) {

		boolean valid = true;

		if (!CollectionUtils.isEmpty(commandElementValuePreconditions)) {

			for (CommandElementValuePrecondition commandElementValuePrecondition : commandElementValuePreconditions) {

				if (commandElementValuePrecondition.getThresholdVal1() != null
						&& commandElementValuePrecondition.getThresholdOperationId() != null) {

					CommandElementValue commandElementValue = null;

					if (!CollectionUtils.isEmpty(commandElementValues)) {

						commandElementValue = commandElementValues.stream()
								.filter(e -> commandElementValuePrecondition.getCommandElementTypeParamId() != null
										&& commandElementValuePrecondition.getCommandElementTypeParamId()
												.equals(e.getCommandElementTypeParamId())
										&& commandElementValuePrecondition.getElementTypeId() != null
										&& commandElementValuePrecondition.getElementTypeId()
												.equals(e.getElementTypeId())
										&& commandElementValuePrecondition.getCommandElementTypeId() != null
										&& commandElementValuePrecondition.getCommandElementTypeId()
												.equals(e.getCommandElementTypeId()))
								.findFirst().orElse(null);
					}

					if (commandElementValue != null) {

						valid = OperationProcessor.check(commandElementValuePrecondition.getThresholdOperationId(),
								commandElementValuePrecondition.getThresholdVal1(),
								commandElementValuePrecondition.getThresholdVal2(), commandElementValue.getValue(),
								commandElementValue.getCommandElementTypeParam().getDataTypeId()) != 0;

					} else {

						valid = false;
					}

					if (!valid) {

						break;
					}

				}

			}

		}
		return valid;
	}

	private boolean validateElementMeasuresPrecondition(CommandElementPrecondition commandElementPrecondition) {

		boolean valid = true;

		if (commandElementPrecondition.getThresholdOperationId() != null
				&& commandElementPrecondition.getThresholdVal1() != null) {

			ElementValueRepository elementValueRepository = (ElementValueRepository) getRepository(ElementValue.class);

			if (elementValueRepository != null) {

				ElementValue elementValue = elementValueRepository.get(
						commandElementPrecondition.getTargetElementTypeId(),
						commandElementPrecondition.getTargetElementId(),
						commandElementPrecondition.getTargetElementTypeParamId(),
						commandElementPrecondition.getTargetParamTypeId(), false);

				if (elementValue != null) {

					ElementTypeParamRepository elementTypeParamRepository = (ElementTypeParamRepository) getRepository(
							ElementTypeParam.class);

					if (elementTypeParamRepository != null) {

						ElementTypeParam elementTypeParam = elementTypeParamRepository.get(
								commandElementPrecondition.getTargetElementTypeId(),
								commandElementPrecondition.getTargetParamTypeId(),
								commandElementPrecondition.getTargetElementId(), false);
						elementValue.setElementTypeParam(elementTypeParam);

						if (elementTypeParam != null) {

							valid = OperationProcessor.check(commandElementPrecondition.getThresholdOperationId(),
									commandElementPrecondition.getThresholdVal1(),
									commandElementPrecondition.getThresholdVal2(), elementValue.getValue(),
									elementValue.getElementTypeParam().getDataTypeId()) != 0;

						}

					}

				}

				else {

					valid = false;
				}

			}

		}

		return valid;
	}

	private boolean validateElementTypeStatePrecondition(CommandElementPrecondition commandElementPrecondition) {

		boolean valid = true;

		if (commandElementPrecondition.getElementTypeStateId() != null) {

			ElementStateRepository elementStateRepository = (ElementStateRepository) getRepository(ElementState.class);
			
			if (elementStateRepository != null) {
				
				ElementState elementState = elementStateRepository.get(commandElementPrecondition.getElementTypeId(),
						commandElementPrecondition.getElementId(), false);

				if (elementState != null
						&& !elementState.getStateId().equals(commandElementPrecondition.getElementTypeStateId())) {

					valid = false;
				}
			}
		}

		return valid;
	}

	private boolean validateActiveAlarmConfigPrecondition(CommandElementPrecondition commandElementPrecondition) {

		boolean valid = true;

		if (commandElementPrecondition.getActiveAlarmConfigId() != null) {

			AlarmRepository alarmRepository = (AlarmRepository) getRepository(Alarm.class);

			if (alarmRepository != null) {

				List<Alarm> alarms = alarmRepository.getsByTargetElement(
						commandElementPrecondition.getTargetElementTypeId(),
						commandElementPrecondition.getTargetElementId(), false);

				if (!CollectionUtils.isEmpty(alarms)) {

					List<Alarm> activeAlarms = alarms.stream()
							.filter(e -> commandElementPrecondition.getActiveAlarmConfigId()
									.equals(e.getAlarmConfigId()) && e.getDeactivation() == null)
							.collect(Collectors.toList());

					if (CollectionUtils.isEmpty(activeAlarms)) {

						valid = false;
					}
				} else {
					
					valid = false;
				}

			} else {

				log.error("Alarm repository not found");
			}
		}

		return valid;
	}

	// Method to get the list of the NOT COMPLIED preconditions.
	public List<CommandElementPrecondition> validate(CommandElement commandElement) {

		List<CommandElementPrecondition> notComplyPreconditions = new ArrayList<>();

		if (commandElement != null) {

			List<CommandElementPrecondition> commandElementPreconditions = gets(
					commandElement.getCommandElementTypeId(), commandElement.getElementTypeId(),
					commandElement.getElementId(), false);

			if (!CollectionUtils.isEmpty(commandElementPreconditions)) {

				CommandElementValuePreconditionRepository commandElementValuePreconditionRepository = (CommandElementValuePreconditionRepository) getRepository(
						CommandElementValuePrecondition.class);

				List<CommandElementValuePrecondition> commandElementValuePreconditions = null;

				if (commandElementValuePreconditionRepository != null) {

					commandElementValuePreconditions = commandElementValuePreconditionRepository.gets(
							commandElement.getCommandElementTypeId(), commandElement.getElementTypeId(),
							commandElement.getElementId(), false);
				}

				for (CommandElementPrecondition commandElementPrecondition : commandElementPreconditions) {

					boolean valid = true;

					// Fill values in CommandElementPrecondition
					if (!CollectionUtils.isEmpty(commandElementValuePreconditions)) {

						List<CommandElementValuePrecondition> commandElementValuePreconditionsFiltered = commandElementValuePreconditions
								.stream()
								.filter(e -> commandElementPrecondition.getId() != null
										&& commandElementPrecondition.getId().equals(e.getPreconditionId()))
								.collect(Collectors.toList());

						commandElementPrecondition
								.setCommandElementValuePrecondition(commandElementValuePreconditionsFiltered);
					}

					// We will evaluate the precondition if the value preconditions
					// are complied or if the precondition has not values.
					if (validateCommandElementValuePreconditions(
							commandElementPrecondition.getCommandElementValuePrecondition(),
							commandElement.getCommandElementValues())) {

						// Evaluating the precondition
						valid = validateElementMeasuresPrecondition(commandElementPrecondition)
								&& validateElementTypeStatePrecondition(commandElementPrecondition)
								&& validateActiveAlarmConfigPrecondition(commandElementPrecondition);

						if (commandElementPrecondition.getNegative() != null
								&& commandElementPrecondition.getNegative().booleanValue()) {

							valid = !valid;
						}

						if (!valid) {

							if (notComplyPreconditions == null) {

								notComplyPreconditions = new ArrayList<>();
							}

							notComplyPreconditions.add(commandElementPrecondition);
						}

					}

				}
			}

		}

		setToStr(notComplyPreconditions);
		return notComplyPreconditions;

	}

	private void setToStr(List<CommandElementPrecondition> commandElementPreconditions) {

		ElementRepository elementRepository = (ElementRepository) getRepository(Element.class);

		if (elementRepository != null && !CollectionUtils.isEmpty(commandElementPreconditions)) {

			for (int i = 0; i < commandElementPreconditions.size(); i++) {

				if (commandElementPreconditions.get(i).getToStr() == null) {

					StringBuilder stringBuilder = new StringBuilder();

					String negative = "";

					if (commandElementPreconditions.get(i).getNegative() != null
							&& commandElementPreconditions.get(i).getNegative().booleanValue()) {

						negative = "LBL_NOT ";

					}

					Element element = elementRepository.get(commandElementPreconditions.get(i).getTargetElementTypeId(),
							commandElementPreconditions.get(i).getTargetElementId(), true);

					if (commandElementPreconditions.get(i).getActiveAlarmConfigId() != null && element != null) {

						AlarmConfigRepository alarmConfigRepository = (AlarmConfigRepository) getRepository(
								AlarmConfig.class);
						AlarmRepository alarmRepository = (AlarmRepository) getRepository(Alarm.class);

						if (ObjectUtils.allNotNull(alarmConfigRepository, alarmRepository)) {

							AlarmConfig alarmConfig = alarmConfigRepository
									.get(commandElementPreconditions.get(i).getActiveAlarmConfigId(), true);

							if (alarmConfig != null) {

								stringBuilder.append("(#" + (i + 1) + " LBL_ALARM) " + negative + element.getAlias() + " "
										+ alarmConfig.getLabelAlias() + ";");
							}
						}
					}

					if (commandElementPreconditions.get(i).getElementTypeStateId() != null && element != null) {

						ElementTypeStateRepository elementTypeStateRepository = (ElementTypeStateRepository) getRepository(
								ElementTypeState.class);

						if (ObjectUtils.allNotNull(elementTypeStateRepository)) {

							ElementTypeState elementTypeState = elementTypeStateRepository.get(
									commandElementPreconditions.get(i).getElementTypeId(),
									commandElementPreconditions.get(i).getElementTypeStateId(), true);

							if (elementTypeState != null) {

								stringBuilder.append("(#" + (i + 1) + " LBL_STATE) " + negative + element.getAlias() + " "
										+ elementTypeState.getLabelAlias() + ";");
							}
						}

					}

					if (commandElementPreconditions.get(i).getThresholdOperationId() != null
							&& commandElementPreconditions.get(i).getThresholdVal1() != null && element != null) {

						ElementValueRepository elementValueRepository = (ElementValueRepository) getRepository(
								ElementValue.class);
						ElementTypeParamRepository elementTypeParamRepository = (ElementTypeParamRepository) getRepository(
								ElementTypeParam.class);
						ThresholdOperationRepository thresholdOperationRepository = (ThresholdOperationRepository) getRepository(
								ThresholdOperation.class);

						if (ObjectUtils.allNotNull(elementValueRepository, elementTypeParamRepository,
								thresholdOperationRepository)) {

							ElementValue elementValue = elementValueRepository.get(
									commandElementPreconditions.get(i).getTargetElementTypeId(),
									commandElementPreconditions.get(i).getTargetElementId(),
									commandElementPreconditions.get(i).getTargetElementTypeParamId(),
									commandElementPreconditions.get(i).getTargetParamTypeId(), false);

							ElementTypeParam elementTypeParam = elementTypeParamRepository.get(
									commandElementPreconditions.get(i).getTargetElementTypeId(),
									commandElementPreconditions.get(i).getTargetParamTypeId(),
									commandElementPreconditions.get(i).getTargetElementTypeParamId(), false);
							elementValue.setElementTypeParam(elementTypeParam);

							ThresholdOperation thresholdOperation = thresholdOperationRepository
									.get(commandElementPreconditions.get(i).getThresholdOperationId(), false);

							if (ObjectUtils.allNotNull(elementValue, elementTypeParam, thresholdOperation,
									commandElementPreconditions.get(i).getThresholdVal1())) {

								stringBuilder.append("(#" + (i + 1) + " LBL_MEASURE) " + negative + element.getAlias()
										+ " - " + elementTypeParam.getLabelAlias() + " "
										+ thresholdOperation.getLabelAlias() + " "
										+ commandElementPreconditions.get(i).getThresholdVal1());

								if (commandElementPreconditions.get(i).getThresholdVal2() != null) {

									stringBuilder.append(" - " + commandElementPreconditions.get(i).getThresholdVal2());

								}

								stringBuilder.append(";");
							}

						}

					}
					commandElementPreconditions.get(i).setToStr(stringBuilder.toString());
				}

			}

		}
	}

	// region puts
	@Override
	public CommandElementPrecondition put(CommandElementPrecondition entity) {

		return (CommandElementPrecondition) putFromCache(entity);
	}

	@Override
	public List<CommandElementPrecondition> put(List<CommandElementPrecondition> entities) {

		List<BasicElement> basicElements = entities.stream()
				.map(commandElementType -> (BasicElement) commandElementType).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(CommandElementPrecondition.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public CommandElementPrecondition del(CommandElementPrecondition entity) {

		return (CommandElementPrecondition) delFromCache(entity);

	}

	@Override
	public List<CommandElementPrecondition> del(List<CommandElementPrecondition> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(CommandElementPrecondition.class::cast).collect(Collectors.toList());
	}
	// endregion dels

}
