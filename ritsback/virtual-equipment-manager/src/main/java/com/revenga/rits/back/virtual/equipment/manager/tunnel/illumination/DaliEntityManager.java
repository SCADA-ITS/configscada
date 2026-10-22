package com.revenga.rits.back.virtual.equipment.manager.tunnel.illumination;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.front.core.model.helper.CommandElementTypeParamHelper;
import com.revenga.rits.back.front.core.model.helper.command.SignallingCommandHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;
import com.revenga.rits.back.virtual.equipment.manager.helper.SetElementMeasuresChangeCommandHelper;
import com.revenga.rits.back.virtual.equipment.manager.service.EntityManager;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.illumination.model.Dali;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.Regime;
import com.revenga.rits.back.virtual.equipment.manager.tunnel.model.RegimeConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DaliEntityManager {

	protected static final String FILE_CONSTANTS = "constants.properties";

	public static final long ELEMENT_TYPE_PARAM_CONFIG_JSON = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.dali.param.config_json"));

	public static final long ELEMENT_TYPE_PARAM_DALI_REGIME = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.dali.param.dali_regime"));

	private Map<Long, Dali> dalis;

	public DaliEntityManager(final List<Element> elements) {

		load(elements);
	}

	public Command sendRegime(final Element elemenDali, final Long regime) {

		Command command = null;

		if (elemenDali != null && regime != null) {

			Dali dali = dalis.get(elemenDali.getId());

			if (dali != null) {

				command = SetElementMeasuresChangeCommandHelper.get(elemenDali.getId(), elemenDali.getElementTypeId(),
						ELEMENT_TYPE_PARAM_DALI_REGIME, regime.toString());

				if (command != null) {

					dali.setCurrentRegime(regime);
				}
			}
		}

		return command;
	}

	public Command sendRegimeToNull(final Element elemenDali) {

		Command command = null;

		if (elemenDali != null) {

			Dali dali = dalis.get(elemenDali.getId());

			if (dali != null) {

				command = SetElementMeasuresChangeCommandHelper.get(elemenDali.getId(), elemenDali.getElementTypeId(),
						ELEMENT_TYPE_PARAM_DALI_REGIME, null);

				if (command != null) {

					dali.setCurrentRegime(null);
				}
			}
		}

		return command;
	}

	public RegimeConfig getRegimeConfig(final Element elemenDali, final Long regimeValue) {

		RegimeConfig config = null;

		if (elemenDali != null && regimeValue != null) {

			Dali dali = dalis.get(elemenDali.getId());

			if (dali != null && dali.getRegimes() != null && CollectionUtils.isNotEmpty(dali.getRegimes())) {

				Regime regime = dali.getRegimes().stream().filter(item -> regimeValue.equals(item.getId())).findFirst()
						.orElse(null);

				if (regime != null && CollectionUtils.isNotEmpty(regime.getConfigs())) {

					config = regime.getConfigs().get(0);
					regime.setCurrentRegimeConfigId(config.getId());

				} else {

					log.error("Not found regime " + regime);
				}
			}
		}

		return config;
	}

	private void load(List<Element> elements) {

		if (dalis != null) {

			dalis.clear();

		} else {

			dalis = new HashedMap<>();
		}

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				ObjectMapper objectMapper = new ObjectMapper();

				if (element.getId() != null && CollectionUtils.isNotEmpty(element.getElementValues())) {
					try {

						ElementValue elementValue = element.getElementValues().stream()
								.filter(e -> e.getElementTypeParamId() != null && e.getValue() != null
										&& e.getParamTypeId() != null
										&& ELEMENT_TYPE_PARAM_CONFIG_JSON == e.getElementTypeParamId().intValue()
										&& ParamType.CONFIG == e.getParamTypeId().intValue())
								.findFirst().orElse(null);

						if (elementValue != null && elementValue.getValue() != null) {

							Dali dali = objectMapper.readValue(elementValue.getValue(), Dali.class);

							if (dali != null) {

								for (Regime regime : dali.getRegimes()) {

									if (CollectionUtils.isNotEmpty(regime.getConfigs())) {

										for (RegimeConfig regimeConfig : regime.getConfigs()) {

											loadCommandsRegimesConfig(regimeConfig);
										}
									}
								}

								elementValue = element.getElementValues().stream()
										.filter(e -> e.getElementTypeParamId() != null && e.getValue() != null
												&& e.getParamTypeId() != null
												&& ELEMENT_TYPE_PARAM_DALI_REGIME == e.getElementTypeParamId()
														.intValue()
												&& ParamType.MEASURE == e.getParamTypeId().intValue())
										.findFirst().orElse(null);

								if (elementValue != null && elementValue.getValue() != null) {
									
									dali.setCurrentRegime(Long.parseLong(elementValue.getValue()));
								}

								dalis.put(element.getId(), dali);
							}
						}

					} catch (JsonProcessingException | NumberFormatException e) {

						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
			}
		}
	}

	private void loadCommandsRegimesConfig(RegimeConfig config) {

		if (config != null && CollectionUtils.isNotEmpty(config.getCommands())) {

			for (CommandElementVO command : config.getCommands()) {

				if (command.getCommandElementTypeId() != null) {

					Key commandElementTypeIdKey = new Key(command.getCommandElementTypeId());

					if (commandElementTypeIdKey.getClazz() == CommandElementType.class) {

						CommandElementType commandElementType = new CommandElementType();
						commandElementType.setPks(commandElementTypeIdKey.getIds());

						commandElementType = EntityManager.getInstance().getCommandElementType(
								commandElementType.getId(), commandElementType.getElementTypeId());

						if (commandElementType != null) {

							if (command.getCommandElementValues() != null) {

								Key key = null;

								for (CommandElementValueVO commandElementValue : command.getCommandElementValues()) {

									if (commandElementValue.getCommandElementTypeParamId() != null) {

										key = new Key(commandElementValue.getCommandElementTypeParamId());

										if (key.getClazz() == CommandElementTypeParam.class) {

											CommandElementTypeParam commandElementTypeParam = null;

											commandElementTypeParam = EntityManager.getInstance()
													.getCommandElementTypeParam(Long.valueOf(key.getId(1)),
															Long.valueOf(key.getId(2)), Long.valueOf(key.getId(3)));

											if (commandElementTypeParam != null) {

												commandElementValue.setCommandElementTypeParam(
														CommandElementTypeParamHelper.get(commandElementTypeParam));

											}
										}
									}
								}
							}
							config.addSignallingCommand(SignallingCommandHelper.get(command));
						}
					}
				}

			}
		}
	}
}