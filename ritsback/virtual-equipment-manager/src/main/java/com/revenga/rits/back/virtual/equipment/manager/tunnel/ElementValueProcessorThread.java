package com.revenga.rits.back.virtual.equipment.manager.tunnel;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.front.core.model.helper.BasicElementHelper;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class ElementValueProcessorThread implements Runnable {

	private static final String APPLICATION_PROPERTIES = "application.properties";

	protected static final long DEFAULT_VALIDITY_PERIDOD = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(APPLICATION_PROPERTIES, "element_value_processor.default.measures_validity_period_s"));

	protected static final long DEFAULT_PERIOD_TIME = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(APPLICATION_PROPERTIES, "element_value_processor.default.period_time_s"));

	protected TubeEntityManager tubeEntityManager;
	protected Map<String, Map<String, List<Pair<ElementValue, Long>>>> mapElementValues;
	protected Map<String, Long> lastProcessingTime;

	private boolean stop;
	private boolean alive;

	protected ElementValueProcessorThread(TubeEntityManager tubeEntityManager) {

		if (mapElementValues != null) {

			mapElementValues.clear();

		} else {

			mapElementValues = new HashedMap<>();
		}

		if (lastProcessingTime != null) {

			lastProcessingTime.clear();

		} else {

			lastProcessingTime = new HashedMap<>();
		}

		List<Element> elements = EntityManager.getInstance().getElements(ElementType.ELEMENT_TYPE_TUBE);

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				lastProcessingTime.put(BasicElementHelper.getIdFromElement(element), 0L);
				mapElementValues.put(BasicElementHelper.getIdFromElement(element),
						Collections.synchronizedMap(new HashMap<>()));
			}
		}

		this.tubeEntityManager = tubeEntityManager;
	}

	public Boolean init() {

		Boolean ok = true;

		String threadNameService = "Thread" + this.getClass().getSimpleName();

		(new Thread(this, threadNameService)).start();

		return ok;
	}

	public Boolean deInit() {

		Boolean ok = true;

		try {
			this.stop();

			Instant start;
			Duration timeElapsed;
			boolean stopped;
			start = Instant.now();

			do {
				stopped = !this.isAlive();
				Thread.sleep(1L);

				timeElapsed = Duration.between(start, Instant.now());

			} while (timeElapsed.toMillis() < 5000L && !stopped);
		}

		catch (InterruptedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
			ok = false;
		}
		return ok;
	}

	public List<Command> processValues(Set<ElementValue> elementValues) {

		if (mapElementValues != null && CollectionUtils.isNotEmpty(elementValues)) {

			Instant now = Instant.now();

			for (ElementValue elementValue : elementValues) {

				processValue(now.toEpochMilli(), elementValue);
			}

			log.debug(this.getClass().getSimpleName() + " => Time to processValues element values: " + elementValues
					+ " => " + Duration.between(now, Instant.now()).toMillis() + " ms.");
		}
		return Collections.emptyList();
	}

	protected List<Command> processValue(long epochMilli, ElementValue elementValue) {

		if (elementValue != null) {

			List<Long> tubeIds = this.tubeEntityManager.getTubesByChild(elementValue.getElementTypeId(),
					elementValue.getElementId());

			if (CollectionUtils.isNotEmpty(tubeIds)) {

				for (Long elementTubeId : tubeIds) {

					if (elementTubeId != null) {

						String tubeId = BasicElementHelper
								.getIdFromElement(new Element(ElementType.ELEMENT_TYPE_TUBE, elementTubeId));

						Map<String, List<Pair<ElementValue, Long>>> mapTube = mapElementValues.get(tubeId);

						if (mapTube != null) {

							String elementValueId = BasicElementHelper.getIdFromElement(elementValue);
							List<Pair<ElementValue, Long>> elementValueList = mapTube.get(elementValueId);

							if (elementValueList == null) {

								elementValueList = Collections.synchronizedList(new ArrayList<>());
								mapTube.put(elementValueId, elementValueList);
							}

							if (elementValueList != null) {

								MutablePair<ElementValue, Long> pair = new MutablePair<>(elementValue, epochMilli);
								elementValueList.add(pair);
							}
						}
					}
				}
			}

		}
		return Collections.emptyList();
	}

	protected List<ElementValue> processMeasures(Map<String, List<Pair<ElementValue, Long>>> mapTube) {

		List<ElementValue> respElementValues = null;

		if (mapTube != null && !mapTube.isEmpty()) {

			for (Entry<String, List<Pair<ElementValue, Long>>> entryValue : mapTube.entrySet()) {

				if (CollectionUtils.isNotEmpty(entryValue.getValue())) {

					List<Pair<ElementValue, Long>> elementValues = entryValue.getValue();

					if (CollectionUtils.isNotEmpty(elementValues) && elementValues.get(0).getLeft() != null) {

						ElementTypeParam elementTypeParam = EntityManager.getInstance().getElementTypeParam(
								elementValues.get(0).getLeft().getElementTypeId(),
								elementValues.get(0).getLeft().getParamTypeId(),
								elementValues.get(0).getLeft().getElementTypeParamId());

						if (elementTypeParam != null && elementTypeParam.getDataTypeId() != null) {

							Object value = null;
							int numValues = 0;

							synchronized (elementValues) {

								for (Pair<ElementValue, Long> elementValue : elementValues) {

									if (elementValue.getLeft() != null && elementValue.getLeft().getValue() != null) {

										try {

											if (Long.valueOf(DataType.DATA_TYPE_INTEGER)
													.equals(elementTypeParam.getDataTypeId())) {

												if (value == null) {

													value = Integer.parseInt(elementValue.getLeft().getValue());

												} else {
													value = (Integer) value
															+ Integer.parseInt(elementValue.getLeft().getValue());
												}
												numValues++;

											} else if (Long.valueOf(DataType.DATA_TYPE_REAL)
													.equals(elementTypeParam.getDataTypeId())) {

												if (value == null) {

													value = Double.parseDouble(elementValue.getLeft().getValue());

												} else {
													value = (Double) value
															+ Double.parseDouble(elementValue.getLeft().getValue());
												}
												numValues++;
											}
										} catch (NumberFormatException e) {

											log.error(e.getMessage());
											log.debug(ExceptionUtils.getStackTrace(e));
										}
									}
								}
							}

							if (value != null
									&& Long.valueOf(DataType.DATA_TYPE_INTEGER).equals(elementTypeParam.getDataTypeId())
									&& numValues > 0) {

								value = (Integer) value / numValues;

							} else if (value != null
									&& Long.valueOf(DataType.DATA_TYPE_REAL).equals(elementTypeParam.getDataTypeId())
									&& numValues > 0) {

								value = (Double) value / Double.valueOf(numValues);
							}

							if (value != null) {

								ElementValue elementValue = new ElementValue();
								elementValue.setElementId(elementValues.get(0).getLeft().getElementId());
								elementValue.setElementTypeId(elementTypeParam.getElementTypeId());
								elementValue.setElementTypeParamId(elementTypeParam.getId());
								elementValue.setParamTypeId(elementTypeParam.getParamTypeId());
								elementValue.setValue(value.toString());

								if (respElementValues == null) {
									respElementValues = new ArrayList<>();
								}
								respElementValues.add(elementValue);
							}
						}

					}
				}
			}
		}

		return respElementValues;
	}

	protected void checkValidityMeasuresInTube(long measuresValidityPeriod, long toEpochMilli,
			Map<String, List<Pair<ElementValue, Long>>> mapElementValues) {

		if (mapElementValues != null) {

			for (Entry<String, List<Pair<ElementValue, Long>>> entryValue : mapElementValues.entrySet()) {

				if (CollectionUtils.isNotEmpty(entryValue.getValue())) {

					List<Pair<ElementValue, Long>> elementValues = entryValue.getValue();

					synchronized (elementValues) {

						List<Pair<ElementValue, Long>> elementValuesToRemove = new ArrayList<>();

						for (Pair<ElementValue, Long> elementValue : elementValues) {

							if (elementValue.getRight() != null && elementValue.getRight().longValue()
									+ (measuresValidityPeriod * 1000) < toEpochMilli) {

								elementValuesToRemove.add(elementValue);
							}
						}

						if (CollectionUtils.isNotEmpty(elementValuesToRemove)) {

							elementValues.removeAll(elementValuesToRemove);
						}
					}
				}

			}
		}
	}

	protected abstract void checkValidityMeasures();

	protected abstract List<Command> checkPeriodTime();

	@Override
	public void run() {

		try {
			log.info(Thread.currentThread().getName() + " is running");

			while (!stop) {

				List<Command> commands = null;

				checkValidityMeasures();

				commands = checkPeriodTime();

				EntityManager.getInstance().sendCommands(commands);

				Thread.sleep(100);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();

		} finally {

			this.setAlive(false);

			log.info(Thread.currentThread().getName() + " is stopped");
		}
	}

	private void stop() {

		this.stop = true;
	}

	private boolean isAlive() {

		return alive;
	}

	private void setAlive(boolean alive) {

		this.alive = alive;
	}

}
