package com.revenga.rits.back.io.external.manager.executor;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.adapter.lib.model.ManagerAdapterV1.Executors.Executor;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.SetAlarmsPublishCommand;
import com.revenga.rits.back.io.external.manager.executor.model.AlarmTCMAN;
import com.revenga.rits.back.io.external.manager.service.EntitiesManager;
import com.revenga.rits.back.manager.lib.executor.GenericExecutor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TCMANExecutor extends GenericExecutor {

	private static final Long OFFSET_TIME = 500L;

	private static final String URL = "url";
	private String url;

	private static final String KEY = "KEY";
	private String key;

	private static final String CLIENT_ALIAS = "client_alias";
	private String clientAlias;

	private static final String USER_NAME = "user_name";
	private String userName;

	private static final String SOURCE_ALIAS = "source_alias";
	private String sourceAlias;

	private static final String GIM_ID = "GIM_id";
	private Integer gimId;

	private static final String GIM_NUMBER = "GIM_number";
	private Integer gimNumber;

	private static final String DATE_FORMAT = "date_format";
	private static final String TIME_ZONE = "time_zone";
	private SimpleDateFormat simpleDateFormat;

	private static final String ELEMENT_TYPES = "element_types";
	private Set<Long> elementTypes;

	private static final String ALARM_LEVELS = "alarm_levels";
	private Set<Long> alarmLevels;

	private static final String ALARM_TYPES = "alarm_types";
	private Set<Long> alarmTypes;

	public static final Integer SECURE_TOKEN_LENGTH = 10;
	private static final String FORMAT_APPLICATION_JSON_CHARSET_UTF8 = "application/json;charset=UTF-8";
	private static final String REQUEST_PROP_CONTENT_TYPE = "Content-Type";

	public TCMANExecutor(Executor executor, short thread) {

		super(executor, thread);

		this.url = getParamValue(URL);
		this.key = getParamValue(KEY);
		this.clientAlias = getParamValue(CLIENT_ALIAS);
		this.userName = getParamValue(USER_NAME);
		this.sourceAlias = getParamValue(SOURCE_ALIAS);

		try {

			this.gimId = Integer.parseInt(getParamValue(GIM_ID));
			this.gimNumber = Integer.parseInt(getParamValue(GIM_NUMBER));

		} catch (NumberFormatException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		this.simpleDateFormat = new SimpleDateFormat(getParamValue(DATE_FORMAT));
		this.simpleDateFormat
				.setTimeZone(TimeZone.getTimeZone(getParamValue(TIME_ZONE) != null ? getParamValue(TIME_ZONE) : "UTC"));

		String valueElementTypes = getParamValue(ELEMENT_TYPES);

		if (valueElementTypes != null) {

			List<String> values = Arrays.asList(valueElementTypes.split(","));

			if (!CollectionUtils.isEmpty(values)) {

				elementTypes = new HashSet<>();

				try {
					values.forEach(item -> elementTypes.add(Long.valueOf(item)));
				} catch (NumberFormatException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

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

							if (element != null && alarmConfig != null) {

								alarm.setAlarmConfig(alarmConfig);

								alarm.setSourceElement(element);

								if ((alarmTypes == null || alarmTypes.contains(alarm.getAlarmConfig().getAlarmTypeId()))
										&& (alarmLevels == null || alarmLevels
												.contains(alarm.getAlarmConfig().getAlarmLevelId())
												&& (elementTypes == null
														|| elementTypes.contains(alarm.getSourceElementTypeId())))) {

									send(alarm);
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

	private void send(Alarm alarm) {

		AlarmTCMAN alarmTCMAN = new AlarmTCMAN();

		alarmTCMAN.setClientAlias(clientAlias);

		if (sourceAlias != null) {

			alarmTCMAN.setSourceAlias(sourceAlias);

		} else {

			alarmTCMAN.setSourceAlias(alarm.getSourceElement().getAlias());
		}

		alarmTCMAN.setAlarmAlias(alarm.getAlarmConfig().getAlias());
		alarmTCMAN.setAlarmDescription(alarm.getAlarmConfig().getDescription());
		Date date = new Date(alarm.getActivation());

		if (ObjectUtils.allNotNull(date, simpleDateFormat)) {

			alarmTCMAN.setCreatedAt(simpleDateFormat.format(date));
		}

		alarmTCMAN.setUserName(userName);
		alarmTCMAN.setGimId(gimId);
		alarmTCMAN.setGimNumber(gimNumber);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);

			String body = objectMapper.writeValueAsString(alarmTCMAN);

			send(body);

		} catch (NumberFormatException | JsonProcessingException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private void send(String body) {

		int responseCode = -1;

		if (ObjectUtils.allNotNull(body, this.url)) {

			try {

				HttpURLConnection conn = (HttpURLConnection) (new URL(this.url)).openConnection();

				conn.setConnectTimeout(5000);
				conn.setRequestMethod(RequestMethod.PUT.name());

				conn.setRequestProperty(KEY, key);
				conn.setRequestProperty(REQUEST_PROP_CONTENT_TYPE, FORMAT_APPLICATION_JSON_CHARSET_UTF8);

				conn.setDoOutput(true);

				try (OutputStream os = conn.getOutputStream()) {

					byte[] input = body.getBytes("utf-8");
					os.write(input, 0, input.length);
				}

				responseCode = conn.getResponseCode();

				if (responseCode != HttpURLConnection.HTTP_OK) {

					log.error("Failed : HTTP error code : " + responseCode);
				}

				conn.disconnect();

			} catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

	}
}
