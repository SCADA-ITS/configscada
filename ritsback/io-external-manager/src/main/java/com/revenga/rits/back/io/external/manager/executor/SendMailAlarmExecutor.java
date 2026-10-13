package com.revenga.rits.back.io.external.manager.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.model.command.SetAlarmsPublishCommand;
import com.revenga.rits.back.data.core.util.MailUtil;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.entities.lib.helper.EntitiesManagerHelper;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.io.external.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SendMailAlarmExecutor extends GenericExecutor {
	private static final Long OFFSET_TIME = 500L;
	private static final Integer NUM_ALARM_LEVELS = 6;

	private static final String ALARM_LEVELS = "alarm_levels";
	private Set<Long> alarmLevels;

	private static final String ALARM_TYPES = "alarm_types";
	private Set<Long> alarmTypes;

	private static final String ADDRESS_TO = "address_to";
	private String[] addressTO;

	private static final String ADDRESS_CC = "address_cc";
	private String[] addressCC;

	private static final String ADDRESS_BCC = "address_bcc";
	private String[] addressBCC;

	private static final String SUBJECT = "subject";
	private String subject;

	private static final String DATE_FORMAT = "date_format";
	private String dateFormat;
	
	private static final String BODY = "body_file_path";
	private String body;

	private static final String ROAD_FILE_PATH = "road_file_path";
	private String roadFilePath;

	private static final String TRANSLATION_FILE_PATH = "translation_file_path";
	private String translationFilePath;

	private static final String ELEMENT_FILE_PATH = "element_file_path";
	private String elementFilePath;
	
	private static final String RECIPIENTS_FILE_PATH = "recipients_file_path";
	private String recipientsFilePath;

	private PropertiesReader propertiesReader;
	private SystemParamValue systemParamValueSmtpConfig; 
	
	public SendMailAlarmExecutor(Executor executor, short thread) {

		super(executor, thread);

		this.recipientsFilePath = getParamValue(RECIPIENTS_FILE_PATH);
		this.addressTO = setAddress(ADDRESS_TO);
		this.addressCC = setAddress(ADDRESS_CC);
		this.addressBCC = setAddress(ADDRESS_BCC);
		this.subject = getParamValue(SUBJECT);
		this.body = setBody(getParamValue(BODY));
		this.dateFormat = getParamValue(DATE_FORMAT);
		this.roadFilePath = getParamValue(ROAD_FILE_PATH);
		this.translationFilePath = getParamValue(TRANSLATION_FILE_PATH);
		this.elementFilePath = getParamValue(ELEMENT_FILE_PATH);

		this.propertiesReader = new PropertiesReader(translationFilePath, elementFilePath, roadFilePath);

		this.systemParamValueSmtpConfig = (new SystemParamValueRepository(
				false)).get(Long.valueOf(SystemParam.SYSTEM_PARAM_SMTP_CONFIG), false);
		
		String valueAlarmLevels = getParamValue(ALARM_LEVELS);
		if (valueAlarmLevels != null) {

			List<String> values = Arrays.asList(valueAlarmLevels.split(","));

			if (!CollectionUtils.isEmpty(values)) {

				alarmLevels = new HashSet<>();

				try {
					values.forEach(item -> alarmLevels.add(Long.valueOf(item)));
				} catch (NumberFormatException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

		String valueAlarmTypes = getParamValue(ALARM_TYPES);

		if (valueAlarmTypes != null) {

			List<String> values = Arrays.asList(valueAlarmTypes.split(","));

			if (!CollectionUtils.isEmpty(values)) {

				alarmTypes = new HashSet<>();

				try {
					values.forEach(item -> alarmTypes.add(Long.valueOf(item)));
				} catch (NumberFormatException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}
	}

	private String setBody(String bodyFilePath) {

		String result = "";

		File file = new File(ResourcesUtil.getPath(bodyFilePath));

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			result = br.lines().collect(Collectors.joining());

		} catch (IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return result;
	}
	
	private String[] setAddress(String property) {
		
		String[] result = new String[NUM_ALARM_LEVELS];
		
		Properties p = new Properties();
		
		try {
			p.load(new FileReader(ResourcesUtil.getPath(recipientsFilePath)));
			
			for(int i = 0; i < NUM_ALARM_LEVELS; i++) {
				
				result[i] = p.getProperty(property + ".level" + i);
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	protected Object prepare(Object queueItem) {

		try {

			if (queueItem instanceof SetAlarmsPublishCommand) {
				SetAlarmsPublishCommand setAlarmsPublishCommand = (SetAlarmsPublishCommand) queueItem;

				if (!CollectionUtils.isEmpty(setAlarmsPublishCommand.getAlarms())) {

					setAlarmsPublishCommand.getAlarms().forEach(alarm -> {

						if (alarm.getActivation() != null && alarm.getDeactivation() == null
								&& (alarm.getRecognized() == null
										|| alarm.getRecognized() - alarm.getActivation() < OFFSET_TIME)
								&& alarm.getSourceElementId() != null && alarm.getSourceElementTypeId() != null
								&& alarm.getAlarmConfigId() != null) {

							Element element = EntitiesManager.getInstance().getElement(alarm.getSourceElementTypeId(),
									alarm.getSourceElementId());

							AlarmConfig alarmConfig = EntitiesManager.getInstance()
									.getAlarmConfig(alarm.getAlarmConfigId());

							EntitiesManagerHelper.cacheRepository(this);

							if (element != null && alarmConfig != null) {

								alarm.setAlarmConfig(alarmConfig);
								alarm.setSourceElement(element);

								String auxAddressTo = addressTO[alarm.getAlarmConfig().getAlarmLevelId().intValue() - 1];
								String auxAddressCc = addressCC[alarm.getAlarmConfig().getAlarmLevelId().intValue() - 1];
								String auxAddressBcc = addressBCC[alarm.getAlarmConfig().getAlarmLevelId().intValue() - 1];

								String auxSubject = subject.replace("%level%",
										String.valueOf(alarm.getAlarmConfig().getAlarmLevelId() - 1));
								auxSubject = auxSubject.replace("%element%", alarm.getSourceElement().getAlias());

								SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

								String auxBody = body.replace("%element%", alarm.getSourceElement().getAlias());
								auxBody = auxBody.replace("%elementType%",
										propertiesReader.getName(propertiesReader.getTypes(),
												String.valueOf(alarm.getSourceElement().getElementTypeId())));
								auxBody = auxBody.replace("%description%", propertiesReader.getName(
										propertiesReader.getAlarms(), String.valueOf(alarm.getAlarmConfig().getId())));
								auxBody = auxBody.replace("%date%", formatter.format(new Date(alarm.getActivation())));
								auxBody = auxBody.replace("%road%",
										propertiesReader.getName(propertiesReader.getRoads(),
												alarm.getSourceElement().getAlias().split("-")[0]));
								auxBody = replaceHTML(auxBody);

								if ((alarmTypes == null
										|| alarmLevels.contains(alarm.getAlarmConfig().getAlarmLevelId()))
										&& (alarmLevels == null
												|| alarmLevels.contains(alarm.getAlarmConfig().getAlarmLevelId()))) {


									if (this.systemParamValueSmtpConfig != null
											&& this.systemParamValueSmtpConfig.getValue() != null) {

										Map<String, Object> mapProps = null;

										try {
											mapProps = (new ObjectMapper()).readValue(
													this.systemParamValueSmtpConfig.getValue(),
													new TypeReference<HashMap<String, Object>>() {
													});
										} catch (JsonProcessingException e) {

											log.error(e.getMessage());
											log.debug(ExceptionUtils.getStackTrace(e));
										}

										if (!MapUtils.isEmpty(mapProps)) {
											Properties props = new Properties();

											for (Map.Entry<String, Object> entry : mapProps.entrySet()) {

												props.put(entry.getKey(), entry.getValue());
											}
											
											log.error("Jony body: " + auxBody);
											MailUtil.send(props, auxAddressTo, auxAddressCc, auxAddressBcc,
													auxSubject, auxBody);
										}
									}
								}
							}
						}
					});
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return null;
	}

	@Override
	protected Object publish() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void evaluate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	public String replaceHTML(String s) {

		StringBuilder builder = new StringBuilder();

		for (char c : s.toCharArray()) {

			switch (c) {

			case 'á':
				builder.append("&aacute;");
				break;
				
			case 'é':
				builder.append("&eacute;");
				break;
				
			case 'í':
				builder.append("&iacute;");
				break;
				
			case 'ó':
				builder.append("&oacute;");
				break;
				
			case 'ú':
				builder.append("&uacute;");
				break;
				
			default:
				builder.append(c);
				
			}
		}
		
		return builder.toString();
	}

	class PropertiesReader {
		private List<Pair<String, String>> alarms;
		private List<Pair<String, String>> types;
		private List<Pair<String, String>> roads;

		private String translationFilePath;
		private String element_file_path;
		private String road_file_path;

		public PropertiesReader(String translationFilePath, String element_file_path, String road_file_path) {
			this.translationFilePath = translationFilePath;
			this.element_file_path = element_file_path;
			this.road_file_path = road_file_path;

			setFile();
		}

		public List<Pair<String, String>> getAlarms() {

			return alarms;
		}

		public List<Pair<String, String>> getTypes() {

			return types;
		}

		public List<Pair<String, String>> getRoads() {

			return roads;
		}

		private void setFile() {
			File file;

			file = new File(ResourcesUtil.getPath(translationFilePath));

			if (alarms == null) {

				alarms = new ArrayList<>();
				try (BufferedReader br = new BufferedReader(new FileReader(file))) {

					String line;

					while ((line = br.readLine()) != null) {

						String[] parts = line.split("=");
						alarms.add(new ImmutablePair<>(parts[0], parts[1]));
					}

				} catch (IOException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}

			} else {

				log.error("Ya existia la lista de alarmas.");
			}

			file = new File(ResourcesUtil.getPath(element_file_path));

			if (types == null) {

				types = new ArrayList<>();

				try (BufferedReader br = new BufferedReader(new FileReader(file))) {

					String line;

					while ((line = br.readLine()) != null) {

						String[] parts = line.split("=");
						types.add(new ImmutablePair<>(parts[0], parts[1]));
					}
				} catch (IOException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}

			} else {

				log.error("Ya existia la lista de tipos.");
			}

			file = new File(ResourcesUtil.getPath(road_file_path));

			if (roads == null) {

				roads = new ArrayList<>();

				try (BufferedReader br = new BufferedReader(new FileReader(file))) {

					String line;

					while ((line = br.readLine()) != null) {

						String[] parts = line.split("=");
						roads.add(new ImmutablePair<>(parts[0], parts[1]));
					}

				} catch (IOException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}

			} else {

				log.error("Ya existia la lista de carreteras.");
			}
		}

		private String getName(List<Pair<String, String>> list, String id) {

			String name = new String();

			if (id != null && list != null) {

				for (Pair<String, String> al : list) {

					if (al.getKey().equals(id)) {

						name = al.getValue();
						break;
					}
				}
			}

			if (name.isEmpty()) {

				name = id;
			}
			return name;
		}
	}
}
