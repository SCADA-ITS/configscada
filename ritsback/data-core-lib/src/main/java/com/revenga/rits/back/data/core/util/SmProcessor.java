package com.revenga.rits.back.data.core.util;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;

import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.SmState;
import com.revenga.rits.back.data.core.model.SmStateMachine;
import com.revenga.rits.back.data.core.model.SmStateTimer;
import com.revenga.rits.back.data.core.model.SmTransition;
import com.revenga.rits.back.data.core.model.SmTransitionCondition;
import com.revenga.rits.back.data.core.model.ThresholdOperation;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SmProcessor {

	public static final long MILLI_TO_MINUTE = 60000;

	private SmStateMachine stateMachine;
	private Map<Long, Long> timers;
	private Long lastUpdate;
	private Long lastChangeState;
	private Long timeFactor;

	@Getter
	private SmState currentState;

	public SmProcessor() {
		this.timeFactor = MILLI_TO_MINUTE;
	}

	public SmProcessor(Long timeFactor) {
		this.timeFactor = timeFactor;
	}

	public Set<ElementTypeParam> getElementTypeParam() {

		Set<ElementTypeParam> elementTypeParams = null;

		if (stateMachine != null && CollectionUtils.isNotEmpty(stateMachine.getStates())) {

			for (SmState state : stateMachine.getStates()) {

				if (CollectionUtils.isNotEmpty(state.getStateTimers())) {

					for (SmStateTimer stateTimer : state.getStateTimers()) {

						if (stateTimer.getTimer() != null && stateTimer.getTimer().getElementTypeParam() != null) {

							if (elementTypeParams == null) {

								elementTypeParams = new HashSet<>();
							}

							elementTypeParams.add(stateTimer.getTimer().getElementTypeParam());
						}
					}
				}

				if (CollectionUtils.isNotEmpty(state.getTransition())) {

					for (SmTransition transition : state.getTransition()) {

						if (CollectionUtils.isNotEmpty(transition.getTransitionConditions())) {

							for (SmTransitionCondition transitionCondition : transition.getTransitionConditions()) {

								if (transitionCondition.getCondition() != null
										&& transitionCondition.getCondition().getElementTypeParamId() != null
										&& transitionCondition.getCondition().getParamTypeId() != null
										&& transitionCondition.getCondition().getElementTypeId() != null) {

									ElementTypeParam elementTypeParam = new ElementTypeParam();
									elementTypeParam.setElementTypeId(transitionCondition.getCondition().getElementTypeId());
									elementTypeParam.setParamTypeId(transitionCondition.getCondition().getParamTypeId());
									elementTypeParam.setId(transitionCondition.getCondition().getElementTypeParamId());

									if (elementTypeParams == null) {

										elementTypeParams = new HashSet<>();
									}

									elementTypeParams.add(elementTypeParam);
								}
							}
						}

					}
				}
			}
		}

		return elementTypeParams;

	}

	public boolean Init(final SmStateMachine stateMachine) {

		boolean resp = false;

		if (stateMachine != null && !CollectionUtils.isEmpty(stateMachine.getStates())) {

			SmState state = stateMachine.getStates().stream()
					.filter(e -> e.getInitState() != null && e.getInitState().booleanValue()).findFirst().orElse(null);

			if (state != null) {

				this.currentState = state;

				log.info("Init State states machine: " + stateMachine.getId() + " init state: "
						+ (this.currentState != null ? this.currentState.getId() : null));

				this.stateMachine = stateMachine;

				initTimer();
				resp = true;

			} else {

				log.error("Not found init state in states machine: " + stateMachine.getId());
			}

		} else {

			log.error("Not found state in states Machine: " + stateMachine);
		}
		return resp;
	}

	public SmState update(final List<ElementValue> elementValues) {

		SmState state = null;

		if (CollectionUtils.isNotEmpty(elementValues)) {

			updateTimer(elementValues);

			state = updateState(elementValues);
		}

		if (state != null) {

			currentState = state;
		}
		return state;
	}

	private void initTimer() {

		if (currentState != null && CollectionUtils.isNotEmpty(currentState.getStateTimers())) {

			timers = new HashedMap<>();

			for (SmStateTimer stateTimers : currentState.getStateTimers()) {

				if (stateTimers.getTimer() != null) {

					timers.put(stateTimers.getTimer().getId(), 0L);
				}
			}
		}
		this.lastUpdate = Instant.now().toEpochMilli();
		this.lastChangeState = Instant.now().toEpochMilli();
	}

	private void updateTimer(final List<ElementValue> elementValues) {

		if (currentState != null && CollectionUtils.isNotEmpty(currentState.getStateTimers())
				&& CollectionUtils.isNotEmpty(elementValues)) {

			Long now = Instant.now().toEpochMilli();

			for (SmStateTimer stateTimers : currentState.getStateTimers()) {

				if (stateTimers.getTimer() != null && stateTimers.getTimer().getId() != null
						&& stateTimers.getTimer().getElementTypeId() != null
						&& stateTimers.getTimer().getElementTypeParam() != null
						&& stateTimers.getTimer().getElementTypeParamId() != null
						&& stateTimers.getTimer().getParamTypeId() != null
						&& stateTimers.getTimer().getThreshold() != null
						&& stateTimers.getTimer().getThreshold().getValue() != null) {

					List<ElementValue> valuesToCheck = elementValues.stream()
							.filter(e -> stateTimers.getTimer().getElementTypeId().equals(e.getElementTypeId())
									&& stateTimers.getTimer().getElementTypeParamId().equals(e.getElementTypeParamId())
									&& stateTimers.getTimer().getParamTypeId().equals(e.getParamTypeId()))
							.collect(Collectors.toList());

					if (CollectionUtils.isNotEmpty(valuesToCheck)) {

						int minElementsMatchThreshold = stateTimers.getTimer().getMinElementsMatchThreshold() != null
								? stateTimers.getTimer().getMinElementsMatchThreshold().intValue()
								: 1;

						for (ElementValue elementValue : valuesToCheck) {

							if (elementValue != null && elementValue.getValue() != null
									&& stateTimers.getTimer().getElementTypeParam() != null
									&& stateTimers.getTimer().getElementTypeParam().getDataTypeId() != null) {

								long resp = OperationProcessor.check(ThresholdOperation.OPERATION_GREATER,
										stateTimers.getTimer().getThreshold().getValue(), null, elementValue.getValue(),
										stateTimers.getTimer().getElementTypeParam().getDataTypeId());

								if (resp != 0) {

									minElementsMatchThreshold--;

									if (minElementsMatchThreshold == 0) {
										break;
									}
								}

							}
						}

						if (minElementsMatchThreshold == 0) {

							Long inc = now - this.lastUpdate;

							timers.put(stateTimers.getTimer().getId(),
									timers.get(stateTimers.getTimer().getId()) + inc);

							log.info("Timer: " + stateTimers.getTimer().getId() + "  --> "
									+ timers.get(stateTimers.getTimer().getId()) + " states machine: "
									+ stateMachine.getId());

						} else {

							timers.put(stateTimers.getTimer().getId(), 0L);

							log.info("Timer: " + stateTimers.getTimer().getId() + "  --> 0" + " states machine: "
									+ stateMachine.getId());
						}
					}

				}
			}

			this.lastUpdate = now;
		}

	}

	private SmState updateState(final List<ElementValue> elementValues) {

		Long stateId = null;

		if (currentState != null && CollectionUtils.isNotEmpty(currentState.getTransition())
				&& CollectionUtils.isNotEmpty(elementValues)) {

			if (currentState.getTMin() == null || Instant.now()
					.toEpochMilli() > (currentState.getTMin() * this.timeFactor) + this.lastChangeState) {

				Collections.sort(currentState.getTransition());

				for (SmTransition trasition : currentState.getTransition()) {

					if (CollectionUtils.isNotEmpty(trasition.getTransitionConditions())
							&& trasition.getNexusAnd() != null && trasition.getNextStateId() != null) {

						for (SmTransitionCondition transitionCondition : trasition.getTransitionConditions()) {

							if (transitionCondition.getCondition() != null
									&& transitionCondition.getCondition().getThresholdOperationId() != null
									&& transitionCondition.getCondition().getThreshold1() != null
									&& transitionCondition.getCondition().getThreshold1().getValue() != null
									&& transitionCondition.getCondition().getThreshold1().getDataTypeId() != null) {

								long resp = 0;

								if (transitionCondition.getCondition().getTimerId() != null
										&& timers.containsKey(transitionCondition.getCondition().getTimerId())) {

									ElementValue elementValue = new ElementValue();
									Long timerValue = timers.get(transitionCondition.getCondition().getTimerId());
									timerValue /= this.timeFactor;
									elementValue.setValue(timerValue.toString());

									resp = OperationProcessor.check(
											transitionCondition.getCondition().getThresholdOperationId(),
											transitionCondition.getCondition().getThreshold1().getValue(),
											transitionCondition.getCondition().getThreshold2() != null
													? transitionCondition.getCondition().getThreshold2().getValue()
													: null,
											elementValue.getValue(),
											transitionCondition.getCondition().getThreshold1().getDataTypeId());

								} else if (transitionCondition.getCondition().getElementTypeId() != null
										&& transitionCondition.getCondition().getElementTypeParamId() != null
										&& transitionCondition.getCondition().getParamTypeId() != null) {

									List<ElementValue> valuesToCheck = elementValues.stream()
											.filter(e -> transitionCondition.getCondition().getElementTypeId()
													.equals(e.getElementTypeId())
													&& transitionCondition.getCondition().getElementTypeParamId()
															.equals(e.getElementTypeParamId())
													&& transitionCondition.getCondition().getParamTypeId()
															.equals(e.getParamTypeId()))
											.collect(Collectors.toList());

									if (CollectionUtils.isNotEmpty(valuesToCheck)) {

										int minElementsMatchThreshold = transitionCondition.getCondition()
												.getMinElementsMatchThreshold() != null
														? transitionCondition.getCondition()
																.getMinElementsMatchThreshold().intValue()
														: 1;

										for (ElementValue elementValue : valuesToCheck) {

											if (elementValue != null && elementValue.getValue() != null) {

												resp = OperationProcessor.check(
														transitionCondition.getCondition().getThresholdOperationId(),
														transitionCondition.getCondition().getThreshold1().getValue(),
														transitionCondition.getCondition().getThreshold2() != null
																? transitionCondition.getCondition().getThreshold2()
																		.getValue()
																: null,
														elementValue.getValue(), transitionCondition.getCondition()
																.getThreshold1().getDataTypeId());

												if (resp != 0) {

													log.info("Condition " + transitionCondition.getCondition().getId()
															+ " satisfied, " + "element: " + elementValue.getElementId()
															+ " states machine: " + stateMachine.getId());

													minElementsMatchThreshold--;

													if (minElementsMatchThreshold == 0) {
														break;
													}
												}
											}
										}

										if (minElementsMatchThreshold == 0) {
											resp = 1;
										}
									}
								}

								if (resp != 0 && !trasition.getNexusAnd().booleanValue()) {

									stateId = trasition.getNextStateId();
									break;

								} else if (resp != 0 && trasition.getNexusAnd().booleanValue()) {

									stateId = trasition.getNextStateId();

								} else if (resp == 0 && trasition.getNexusAnd().booleanValue()) {

									stateId = null;
									break;

								}
							}

						}
						if (stateId != null) {
							break;
						}
					}
				}
			} else {

				log.info("The current state has t_min: " + currentState.getTMin() + " mimutes," + " states machine: "
						+ stateMachine.getId());
			}

		}
		return stateId != null ? updateState(stateId) : null;

	}

	private SmState updateState(final Long stateId) {

		SmState state = null;

		if (stateId != null && this.stateMachine != null && !CollectionUtils.isEmpty(this.stateMachine.getStates())) {

			state = stateMachine.getStates().stream().filter(e -> stateId.equals(e.getId())).findFirst().orElse(null);

			log.info("State " + (this.currentState != null ? this.currentState.getId() : null) + " -> "
					+ (state != null ? state.getId() : null) + " states machine: " + stateMachine.getId());

			this.currentState = state;

			initTimer();
		}
		return state;
	}
}
