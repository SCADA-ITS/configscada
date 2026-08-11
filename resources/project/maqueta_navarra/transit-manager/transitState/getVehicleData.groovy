import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.back.data.core.model.Transit;
import com.revenga.rits.back.data.core.model.TransitValue;
import com.revenga.rits.back.data.core.model.TransitTypeStateTransition;
import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.util.MailUtil;
import com.revenga.rits.back.data.core.util.TelegramUtil;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.helper.CgiApiMultasClientHelper;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.helper.CgiApiMultasPropertiesHelper;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.helper.CgiApiMultasVehicleSpeedLimitHelper;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.CgiApiMultasClient;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.config.CgiApiMultasProperties;
import com.revenga.rits.back.transit.manager.cgi.api.multas.client.dto.CgiApiMultasPlateNumberResponseDto;
import com.revenga.rits.back.transit.manager.service.EntitiesManager;
import com.revenga.rits.back.transit.manager.transit.persistence.TransitPersistenceService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import groovy.json.JsonSlurper;

class getVehicleData {

	final Long TRANSIT_TYPE_ID = 1L;
	final long TRANSIT_PARAM_ROAD_SPEED_LIMIT = 1L;
	final Long TRANSIT_PARAM_API_VEHICLE_ITV_DATE = 2L;
	final long TRANSIT_PARAM_API_VEHICLE_INSURANCE_DATE = 5L;
	final long TRANSIT_PARAM_API_VEHICLE_MMA = 6L;
	final long TRANSIT_PARAM_CORRECTED_SPEED = 7L;
	final long TRANSIT_PARAM_GRAVITY = 8L;
	final long TRANSIT_PARAM_ARTICLE = 9L;
	final long TRANSIT_PARAM_CALCULATED_DBOID = 10L;
	final long TRANSIT_PARAM_LOCATION_TYPE = 11L;
	final long TRANSIT_PARAM_ROAD_TYPE = 12L;
	final long TRANSIT_PARAM_LIMITATION_TYPE = 13L;
	final long TRANSIT_PARAM_VEHICLE_LIMITATION_SPEED = 15L;
	final long TRANSIT_PARAM_OBSERVATIONS = 17L;
	final Long TRANSIT_STATE_IN_REVIEW = 3L;
	final Long TRANSIT_STATE_WHITE_LIST = 11L;
	final Long TRANSIT_STATE_BLACK_LIST = 12L;
	final String DGT_CONTACT_ERROR_MESSAGE = "Error al contactar con el servicio DGT";
	final String INFRACTION_TYPE_ARTICLE_48 = "Artículo 48";
	final String INFRACTION_TYPE_ARTICLE_50 = "Artículo 50";
	final String INFRACTION_TYPE_ARTICLE_52 = "Artículo 52";
	final String LOCATION_TYPE_INTERURBANA = "Interurbana";
	final String LOCATION_TYPE_TRAVESIA = "Travesía";
	final String LIMITATION_TYPE_GENERAL = "General";
	final String LIMITATION_TYPE_ESPECIFICA = "Específica";
	final String NOTIFICATION_TYPE_TELEGRAM = "Telegram";
	final String NOTIFICATION_TYPE_EMAIL = "Correo";
	final String[] GRAVITY_LABELS = [ "Grave - Sin puntos", "Grave - 2 puntos", "Grave - 4 puntos",
			"Grave - 6 puntos", "Muy grave - 6 puntos" ];
	private static final String WHITE_LIST_SQL = "SELECT 1 FROM transits_bo.white_list WHERE matricula = ?";
	private static final String BLACK_LIST_SQL = """
		SELECT bl.id AS black_list_id,
		       a.id AS administration_id,
		       a.name AS administration_name,
		       nt.name AS notification_type,
		       ar.recipient
		  FROM transits_bo.black_list bl
		  JOIN transits_bo.administrations a ON a.id = bl.administration_id
		  JOIN transits_bo.notification_types nt ON nt.id = a.notification_type_id
		  LEFT JOIN transits_bo.administration_recipients ar ON ar.administration_id = a.id
		 WHERE bl.matricula = ?
		   AND (bl.fecha_inicio IS NULL OR bl.fecha_inicio <= ?)
		   AND (bl.fecha_fin IS NULL OR bl.fecha_fin >= ?)
		 ORDER BY a.id, ar.id
	""";

	org.apache.logging.log4j.Logger log;
	private CgiApiMultasClient client;
	private CgiApiMultasProperties cgiApiMultasProperties;
	private TransitPersistenceService service;

	private static final DateTimeFormatter DGT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private static final DateTimeFormatter NOTIFICATION_DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern("dd/MM/yyyy HH:mm:ss");
	private static final ZoneId NOTIFICATION_ZONE = ZoneId.of("Europe/Madrid");

	private static class BlackListNotificationConfig {
		Long administrationId;
		String administrationName;
		String notificationType;
		Set<String> recipients = new LinkedHashSet<>();
	}

	getVehicleData(org.apache.logging.log4j.Logger log) {
		this.log = log;
		this.client = getCgiApiMultasClient();
		this.cgiApiMultasProperties = getCgiApiMultasProperties();
	}

	private static CgiApiMultasClient getCgiApiMultasClient() {

		try {
			return CgiApiMultasClientHelper.getClient();
		} catch (Exception e) {
			return null;
		}
	}

	private static CgiApiMultasProperties getCgiApiMultasProperties() {

		try {
			return CgiApiMultasPropertiesHelper.getProperties();
		} catch (Exception e) {
			return null;
		}
	}

	boolean onBeforeChangeTransitState(Transit transit, TransitTypeStateTransition transitTypeStateTransition) {

		return true;
	}

	boolean onAfterChangeTransitState(Transit transit, TransitTypeStateTransition transitTypeStateTransition) {

		log.debug("transitId = " + transit.getId() +
				" currentState = " + transitTypeStateTransition.getParentTransitStateId() +
				" nextState = " + transitTypeStateTransition.getChildTransitStateId());

		Connection connection = null;
		PreparedStatement whiteListStatement = null;
		ResultSet whiteListResult = null;
		service = new TransitPersistenceService(null);
		String plateNumber = StringUtils.trimToNull(transit.getVehiclePlateNumber());
		Collection<BlackListNotificationConfig> pendingBlackListNotifications = new ArrayList<>();

		log.debug("transit: " + transit);
		log.debug("Solicitada informacion de vehiculo con la matricula: " + plateNumber);
		if (StringUtils.isNotBlank(plateNumber)) {

			try {

				// Cargamos primero los valores actuales para no perderlos al persistir el transito.
				List<TransitValue> persistedTransitValues = EntitiesManager.getInstance().getTransitValues(transit);
				if (persistedTransitValues != null) {
					transit.setTransitValues(new ArrayList<>(persistedTransitValues));
				}

				// Primero obtenemos la informacion del vehiculo a traves de la peticion a la DGT.
				pedirDatosDGT(transit);

				// Consulto las BBDD de listas blancas y negras a ver si se encuentra en alguna.
				connection = DataSourceConnection.getInstance().getConnection();
				boolean ewl = false;
				Map<Long, BlackListNotificationConfig> blackListNotifications = new LinkedHashMap<>();
				try {
					whiteListStatement = connection.prepareStatement(WHITE_LIST_SQL);
					whiteListStatement.setString(1, plateNumber);
					whiteListResult = whiteListStatement.executeQuery();
					ewl = whiteListResult.next();

					blackListNotifications = findActiveBlackListNotifications(
							connection, plateNumber, transit.getDateTransit());
				} catch (SQLException e) {
					if (isMissingRelationError(e)) {
						log.error(
								"No se han podido consultar las listas blanca/negra para transitId={} y matricula={} porque no existen las tablas requeridas: {}",
								transit != null ? transit.getId() : null, plateNumber, e.getMessage());
					} else {
						throw e;
					}
				}

				boolean ebl = !blackListNotifications.isEmpty();
				log.debug("Resultado de peticion de listas. Lista blanca: " + ewl + ". Lista negra vigente: " + ebl);
				if (ewl) {
					service.changeStateTransit(transit.getId(), TRANSIT_STATE_WHITE_LIST, null);
				} else if (ebl) {
					service.changeStateTransit(transit.getId(), TRANSIT_STATE_BLACK_LIST, null);
					pendingBlackListNotifications = new ArrayList<>(blackListNotifications.values());
				} else {
					service.changeStateTransit(transit.getId(), TRANSIT_STATE_IN_REVIEW, null);
				}

			} catch (Exception e) {
				log.error(e.getMessage(), e);
				service.changeStateTransit(transit.getId(), TRANSIT_STATE_IN_REVIEW, null);
			} finally {

				try {
					if (whiteListResult != null && !whiteListResult.isClosed()) {
						whiteListResult.close();
					}
					if (whiteListStatement != null && !whiteListStatement.isClosed()) {
						whiteListStatement.close();
					}
					if (connection != null && !connection.isClosed()) {
						connection.close();
					}
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
		} else {
			log.debug("No se realiza la consulta de datos de vehiculo por no tener ninguna matricula asociada");
			service.changeStateTransit(transit.getId(), TRANSIT_STATE_IN_REVIEW, null);
		}

		// La notificacion se realiza con la conexion de consulta ya liberada.
		sendBlackListNotifications(transit, plateNumber, pendingBlackListNotifications);

		return true;
	}

	private Map<Long, BlackListNotificationConfig> findActiveBlackListNotifications(
			Connection connection, String plateNumber, Long transitDate) throws SQLException {

		Map<Long, BlackListNotificationConfig> notifications = new LinkedHashMap<>();
		if (connection == null || StringUtils.isBlank(plateNumber) || transitDate == null) {
			if (transitDate == null) {
				log.warn("No se puede comprobar la vigencia de la lista negra porque el transito no tiene fecha");
			}
			return notifications;
		}

		Timestamp transitTimestamp = new Timestamp(transitDate);
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(BLACK_LIST_SQL);
			statement.setString(1, plateNumber);
			statement.setTimestamp(2, transitTimestamp);
			statement.setTimestamp(3, transitTimestamp);

			result = statement.executeQuery();
			while (result.next()) {
				Long administrationId = result.getLong("administration_id");
				BlackListNotificationConfig config = notifications.get(administrationId);
				if (config == null) {
					config = new BlackListNotificationConfig();
					config.administrationId = administrationId;
					config.administrationName = StringUtils.trimToNull(result.getString("administration_name"));
					config.notificationType = StringUtils.trimToNull(result.getString("notification_type"));
					notifications.put(administrationId, config);
				}

				String recipient = StringUtils.trimToNull(result.getString("recipient"));
				if (recipient != null) {
					config.recipients.add(recipient);
				}
			}
		} finally {
			if (result != null && !result.isClosed()) {
				result.close();
			}
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}

		return notifications;
	}

	private void sendBlackListNotifications(Transit transit, String plateNumber,
			Collection<BlackListNotificationConfig> notifications) {

		if (notifications == null || notifications.isEmpty()) {
			return;
		}

		String telegramConfig = getTelegramConfig();
		Properties smtpProperties = null;
		for (BlackListNotificationConfig notification : notifications) {
			if (notification.recipients.isEmpty()) {
				log.warn("La administracion {} no tiene destinatarios configurados para el aviso de lista negra",
						notification.administrationName);
				continue;
			}

			String message = buildBlackListNotificationMessage(transit, plateNumber,
					notification.administrationName);
			try {
				if (NOTIFICATION_TYPE_TELEGRAM.equalsIgnoreCase(notification.notificationType)) {
					if (telegramConfig == null) {
						log.error("No se puede enviar el aviso de lista negra por Telegram: no hay configuracion del sistema");
						continue;
					}
					for (String destination : notification.recipients) {
						TelegramUtil.sendConfigured(telegramConfig, destination, message);
					}
				} else if (NOTIFICATION_TYPE_EMAIL.equalsIgnoreCase(notification.notificationType)) {
					if (smtpProperties == null) {
						smtpProperties = getSmtpProperties();
					}
					if (smtpProperties == null) {
						log.error("No se puede enviar el aviso de lista negra por correo: no hay configuracion SMTP");
						continue;
					}
					String recipients = String.join(",", notification.recipients);
					String subject = "[Lista negra] Vehiculo detectado - " + plateNumber;
					MailUtil.send(smtpProperties, recipients, null, null, subject,
							message.replace("\n", "<br/>"));
				} else {
					log.error("Tipo de aviso no soportado para la administracion {}: {}",
							notification.administrationName, notification.notificationType);
				}
			} catch (Exception e) {
				log.error("Error enviando el aviso de lista negra a la administracion {}",
						notification.administrationName, e);
			}
		}
	}

	private String buildBlackListNotificationMessage(Transit transit, String plateNumber, String administrationName) {

		return "Vehiculo incluido en lista negra detectado" +
				"\nAdministracion: " + StringUtils.defaultString(administrationName) +
				"\nMatricula: " + StringUtils.defaultString(plateNumber) +
				"\nTransito: " + (transit != null ? transit.getId() : "") +
				"\nFecha: " + formatTransitDate(transit != null ? transit.getDateTransit() : null);
	}

	private String formatTransitDate(Long transitDate) {

		if (transitDate == null) {
			return "";
		}
		return ZonedDateTime.ofInstant(Instant.ofEpochMilli(transitDate), NOTIFICATION_ZONE)
				.format(NOTIFICATION_DATE_TIME_FORMATTER);
	}

	private String getTelegramConfig() {

		try {
			SystemParamValue telegramConfig = new SystemParamValueRepository(false)
					.get(Long.valueOf(SystemParam.SYSTEM_PARAM_TELEGRAM_CONFIG), false);
			return telegramConfig != null ? StringUtils.trimToNull(telegramConfig.getValue()) : null;
		} catch (Exception e) {
			log.error("No se ha podido cargar la configuracion de Telegram para el aviso de lista negra", e);
			return null;
		}
	}

	private Properties getSmtpProperties() {

		try {
			SystemParamValue smtpConfig = new SystemParamValueRepository(false)
					.get(Long.valueOf(SystemParam.SYSTEM_PARAM_SMTP_CONFIG), false);
			String value = smtpConfig != null ? StringUtils.trimToNull(smtpConfig.getValue()) : null;
			if (value == null) {
				return null;
			}

			Object parsed = new JsonSlurper().parseText(value);
			if (!(parsed instanceof Map) || parsed.isEmpty()) {
				return null;
			}

			Properties properties = new Properties();
			parsed.each { key, propertyValue ->
				if (key != null && propertyValue != null) {
					properties.setProperty(String.valueOf(key), String.valueOf(propertyValue));
				}
			};
			return properties;
		} catch (Exception e) {
			log.error("No se ha podido cargar la configuracion SMTP para el aviso de lista negra", e);
			return null;
		}
	}

	private CgiApiMultasPlateNumberResponseDto pedirDatosDGT(Transit transit) {

		if (transit == null) {
			return null;
		}

		String plateNumber = StringUtils.trimToNull(transit.getVehiclePlateNumber());

		if (plateNumber == null || client == null) {
			return null;
		}

		try {
			CgiApiMultasPlateNumberResponseDto response = client.getByPlateNumber(plateNumber);
			log.debug("Resultado de peticion de datos para la matricula {}: {}", plateNumber, response);

			if (response == null) {
				return null;
			}

			fillTransitVehicleData(transit, response);
			clearTransitValue(transit, TRANSIT_PARAM_OBSERVATIONS);
			addOrReplaceTransitValue(transit, TRANSIT_PARAM_API_VEHICLE_ITV_DATE, getFormattedItvExpiryDate(response));
			addOrReplaceTransitValue(transit, TRANSIT_PARAM_API_VEHICLE_INSURANCE_DATE, getInsuranceExpiryDate(response));
			addOrReplaceTransitValue(transit, TRANSIT_PARAM_API_VEHICLE_MMA, getVehicleMma(response));
			String roadType = EntitiesManager.getInstance().getTransitValue(transit, TRANSIT_PARAM_ROAD_TYPE);
			Short vehicleSpeedLimit = CgiApiMultasVehicleSpeedLimitHelper.getVehicleSpeedLimit(response, roadType);
			addOrReplaceTransitValue(transit, TRANSIT_PARAM_VEHICLE_LIMITATION_SPEED, vehicleSpeedLimit);
			recalculateSanctionData(transit);

			EntitiesManager.getInstance().updateTransit(transit);
			return response;

		} catch (Exception e) {
			String errorDetail = getErrorDetail(e);
			if (StringUtils.isBlank(errorDetail) && isDgtContactError(e)) {
				errorDetail = DGT_CONTACT_ERROR_MESSAGE;
			}
			if (StringUtils.isNotBlank(errorDetail)) {
				addOrReplaceTransitValue(transit, TRANSIT_PARAM_OBSERVATIONS, errorDetail);
				EntitiesManager.getInstance().updateTransit(transit);
			}
			log.error("Se ha producido un error en la peticion de datos de vehiculo con la matricula: {}", plateNumber, e);
			return null;
		}
	}

	private void fillTransitVehicleData(Transit transit, CgiApiMultasPlateNumberResponseDto response) {

		CgiApiMultasPlateNumberResponseDto.Identificacion identification = response.getIdentificacion();
		CgiApiMultasPlateNumberResponseDto.DescripcionVehiculo vehicleDescription = identification != null
				? identification.getDescripcionVehiculo()
				: null;

		if (vehicleDescription == null) {
			return;
		}

		transit.setVehicleBrandName(vehicleDescription.getMarca() != null
				? StringUtils.trimToNull(vehicleDescription.getMarca().getDescripcion())
				: null);
		transit.setVehicleModelName(StringUtils.trimToNull(vehicleDescription.getModelo()));
		transit.setVehicleColor(vehicleDescription.getColor() != null
				? StringUtils.trimToNull(vehicleDescription.getColor().getDescripcion())
				: null);
		transit.setVehicleTypeName(vehicleDescription.getTipoVehiculo() != null
				? StringUtils.trimToNull(vehicleDescription.getTipoVehiculo().getDescripcion())
				: null);
	}

	private void recalculateSanctionData(Transit transit) {

		if (transit == null) {
			return;
		}

		String locationType = EntitiesManager.getInstance().getTransitValue(transit, TRANSIT_PARAM_LOCATION_TYPE);
		String limitationType = EntitiesManager.getInstance().getTransitValue(transit, TRANSIT_PARAM_LIMITATION_TYPE);
		Integer correctedSpeed = parseInteger(
				EntitiesManager.getInstance().getTransitValue(transit, TRANSIT_PARAM_CORRECTED_SPEED));
		Integer roadSpeedLimit = parseInteger(
				EntitiesManager.getInstance().getTransitValue(transit, TRANSIT_PARAM_ROAD_SPEED_LIMIT));
		Integer vehicleSpeedLimit = parseInteger(
				EntitiesManager.getInstance().getTransitValue(transit, TRANSIT_PARAM_VEHICLE_LIMITATION_SPEED));
		Integer effectiveSpeedLimit = getMostRestrictiveSpeedLimit(roadSpeedLimit, vehicleSpeedLimit);
		String article = StringUtils.defaultString(getInfractionArticle(locationType, limitationType));
		String gravity = "";
		String calculatedDboid = "";

		if (correctedSpeed != null && effectiveSpeedLimit != null) {
			int speedDifference = correctedSpeed - effectiveSpeedLimit;

			if (speedDifference > 0) {
				int dboidIndex = getDboidIndex(effectiveSpeedLimit, speedDifference);
				gravity = StringUtils.defaultString(getGravityLabel(dboidIndex));
				calculatedDboid = StringUtils.defaultString(
						getInfractionTypeDboid(article, effectiveSpeedLimit, correctedSpeed));
			}
		}

		addOrReplaceTransitValue(transit, TRANSIT_PARAM_ARTICLE, article);
		addOrReplaceTransitValue(transit, TRANSIT_PARAM_GRAVITY, gravity);
		addOrReplaceTransitValue(transit, TRANSIT_PARAM_CALCULATED_DBOID, calculatedDboid);
	}

	private String getFormattedItvExpiryDate(CgiApiMultasPlateNumberResponseDto response) {

		CgiApiMultasPlateNumberResponseDto.Itvs itvs = response.getItvs();
		if (itvs == null || CollectionUtils.isEmpty(itvs.getItv())) {
			return null;
		}

		String originalDate = StringUtils.trimToNull(itvs.getItv().get(0).getFechaCaducidad());
		if (originalDate == null) {
			return null;
		}

		try {
			return OffsetDateTime.parse(originalDate).format(DGT_DATE_TIME_FORMATTER);
		} catch (Exception e) {
			log.debug("No se ha podido formatear la fecha de ITV [{}]: {}", originalDate, e.getMessage());
			return originalDate;
		}
	}

	private String getInsuranceExpiryDate(CgiApiMultasPlateNumberResponseDto response) {

		CgiApiMultasPlateNumberResponseDto.Seguros seguros = response.getSeguros();
		if (seguros == null || CollectionUtils.isEmpty(seguros.getSeguro())) {
			return null;
		}

		String originalDate = StringUtils.trimToNull(seguros.getSeguro().get(0).getFechaInicio());
		if (originalDate == null) {
			return null;
		}

		try {
			return OffsetDateTime.parse(originalDate).format(DGT_DATE_TIME_FORMATTER);
		} catch (Exception e) {
			log.debug("No se ha podido formatear la fecha del seguro [{}]: {}", originalDate, e.getMessage());
			return originalDate;
		}
	}

	private Integer getVehicleMma(CgiApiMultasPlateNumberResponseDto response) {

		CgiApiMultasPlateNumberResponseDto.Identificacion identification = response.getIdentificacion();
		if (identification == null || identification.getDatosTecnicos() == null) {
			return null;
		}

		Integer mma = identification.getDatosTecnicos().getPesoMax();
		return mma != null ? mma : identification.getDatosTecnicos().getMasaMaxTecnica();
	}

	private void addOrReplaceTransitValue(Transit transit, long transitTypeParamId, Object value) {

		if (value == null) {
			return;
		}

		List<TransitValue> transitValues = transit.getTransitValues();
		if (transitValues == null) {
			transitValues = new ArrayList<>();
			transit.setTransitValues(transitValues);
		}

		String stringValue = String.valueOf(value);

		for (TransitValue transitValue : transitValues) {
			if (Long.valueOf(transitTypeParamId).equals(transitValue.getTransitTypeParamId())) {
				transitValue.setValue(stringValue);
				return;
			}
		}

		addTransitValue(transit, transitTypeParamId, stringValue);
	}

	private void addTransitValue(Transit transit, long transitTypeParamId, Object value) {

		if (value == null) {
			return;
		}

		List<TransitValue> transitValues = transit.getTransitValues();
		if (transitValues == null) {
			transitValues = new ArrayList<>();
			transit.setTransitValues(transitValues);
		}

		TransitValue transitValue = new TransitValue();
		transitValue.setTransitTypeId(TRANSIT_TYPE_ID);
		transitValue.setTransitTypeParamId(transitTypeParamId);
		transitValue.setValue(String.valueOf(value));
		transitValues.add(transitValue);
	}

	private String getInfractionArticle(String locationType, String limitationType) {

		if (isEspecificaLimitationType(limitationType)) {
			return INFRACTION_TYPE_ARTICLE_52;
		}

		if (isGeneralLimitationType(limitationType)) {
			if (isTravesiaLocationType(locationType)) {
				return INFRACTION_TYPE_ARTICLE_50;
			}
			if (isInterurbanaLocationType(locationType)) {
				return INFRACTION_TYPE_ARTICLE_48;
			}
		}

		return "";
	}

	private boolean isInterurbanaLocationType(String locationType) {

		return "I".equalsIgnoreCase(locationType) || LOCATION_TYPE_INTERURBANA.equalsIgnoreCase(locationType);
	}

	private boolean isTravesiaLocationType(String locationType) {

		return "T".equalsIgnoreCase(locationType) || LOCATION_TYPE_TRAVESIA.equalsIgnoreCase(locationType);
	}

	private boolean isGeneralLimitationType(String limitationType) {

		return "G".equalsIgnoreCase(limitationType) || LIMITATION_TYPE_GENERAL.equalsIgnoreCase(limitationType);
	}

	private boolean isEspecificaLimitationType(String limitationType) {

		return "E".equalsIgnoreCase(limitationType) || LIMITATION_TYPE_ESPECIFICA.equalsIgnoreCase(limitationType);
	}

	private String getInfractionTypeDboid(String infractionArticle, Integer roadSpeedLimit, Integer correctedVehicleSpeed) {

		if (correctedVehicleSpeed == null || roadSpeedLimit == null) {
			return "";
		}

		int speedDifference = correctedVehicleSpeed - roadSpeedLimit;
		if (speedDifference <= 0) {
			return "";
		}

		int dboidIndex = getDboidIndex(roadSpeedLimit, speedDifference);
		return dboidIndex >= 0 ? getDboidByArticle(infractionArticle, dboidIndex) : "";
	}

	private int getDboidIndex(int roadSpeedLimit, int speedDifference) {

		if (roadSpeedLimit <= 50) {
			if (speedDifference <= 20) {
				return 0;
			}
			if (speedDifference <= 30) {
				return 1;
			}
			if (speedDifference <= 40) {
				return 2;
			}
			if (speedDifference <= 50) {
				return 3;
			}
			return 4;
		}

		if (speedDifference <= 30) {
			return 0;
		}
		if (speedDifference <= 50) {
			return 1;
		}
		if (speedDifference <= 60) {
			return 2;
		}
		if (speedDifference <= 70) {
			return 3;
		}
		return 4;
	}

	private String getGravityLabel(int gravityIndex) {

		return gravityIndex >= 0 && gravityIndex < GRAVITY_LABELS.length ? GRAVITY_LABELS[gravityIndex] : null;
	}

	private String getDboidByArticle(String infractionArticle, int dboidIndex) {

		String[] dboids = getDboidsByArticle(infractionArticle);
		return dboidIndex >= 0 && dboidIndex < dboids.length ? dboids[dboidIndex] : "";
	}

	private String[] getDboidsByArticle(String infractionArticle) {

		if (INFRACTION_TYPE_ARTICLE_48.equals(infractionArticle)) {
			return getDboids("48");
		}
		if (INFRACTION_TYPE_ARTICLE_50.equals(infractionArticle)) {
			return getDboids("50");
		}
		if (INFRACTION_TYPE_ARTICLE_52.equals(infractionArticle)) {
			return getDboids("52");
		}
		return new String[0];
	}

	private String[] getDboids(String articleCode) {

		if (cgiApiMultasProperties == null || cgiApiMultasProperties.getInfractionDboid() == null) {
			return new String[5];
		}

		Map<String, CgiApiMultasProperties.ArticleTiers> articles = cgiApiMultasProperties.getInfractionDboid().getArticle();
		CgiApiMultasProperties.ArticleTiers articleTiers = articles != null ? articles.get(articleCode) : null;
		return articleTiers != null ? articleTiers.asArray() : new String[5];
	}

	private Integer getMostRestrictiveSpeedLimit(Integer roadSpeedLimit, Integer vehicleSpeedLimit) {

		if (roadSpeedLimit == null) {
			return vehicleSpeedLimit;
		}
		if (vehicleSpeedLimit == null) {
			return roadSpeedLimit;
		}
		return Math.min(roadSpeedLimit, vehicleSpeedLimit);
	}

	private Integer parseInteger(String value) {

		if (StringUtils.isBlank(value)) {
			return null;
		}

		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private String getErrorDetail(Throwable throwable) {

		Throwable current = throwable;
		while (current != null) {
			String detail = extractDetailFromThrowable(current);
			if (StringUtils.isNotBlank(detail)) {
				return detail;
			}
			current = current.getCause();
		}

		return null;
	}

	private String extractDetailFromThrowable(Throwable throwable) {

		if (throwable instanceof RestClientResponseException) {
			String detail = extractDetailFromJson(((RestClientResponseException) throwable).getResponseBodyAsString());
			if (StringUtils.isNotBlank(detail)) {
				return detail;
			}
		}

		return extractDetailFromMessage(throwable != null ? throwable.getMessage() : null);
	}

	private boolean isDgtContactError(Throwable throwable) {

		Throwable current = throwable;
		while (current != null) {
			if (current instanceof ResourceAccessException) {
				return true;
			}

			String message = current.getMessage();
			if (StringUtils.containsIgnoreCase(message, "Read timed out")
					|| StringUtils.containsIgnoreCase(message, "Connection timed out")
					|| StringUtils.containsIgnoreCase(message, "I/O error on")) {
				return true;
			}

			current = current.getCause();
		}

		return false;
	}

	private String extractDetailFromMessage(String message) {

		if (StringUtils.isBlank(message)) {
			return null;
		}

		int bodyIndex = message.indexOf("Body:");
		if (bodyIndex < 0) {
			return null;
		}

		String body = StringUtils.trimToNull(message.substring(bodyIndex + "Body:".length()));
		return extractDetailFromJson(body);
	}

	private String extractDetailFromJson(String jsonText) {

		if (StringUtils.isBlank(jsonText)) {
			return null;
		}

		try {
			Object parsed = new JsonSlurper().parseText(jsonText);
			if (parsed instanceof Map) {
				return StringUtils.trimToNull(parsed.detail != null ? String.valueOf(parsed.detail) : null);
			}
		} catch (Exception e) {
			log.debug("No se ha podido extraer el campo detail del body de error [{}]: {}", jsonText, e.getMessage());
		}

		return null;
	}

	private boolean isMissingRelationError(SQLException exception) {

		SQLException current = exception;
		while (current != null) {
			String sqlState = current.getSQLState();
			String message = current.getMessage();
			if ("42P01".equals(sqlState) || (message != null && message.toLowerCase().contains("does not exist"))) {
				return true;
			}
			current = current.getNextException();
		}

		return false;
	}

	private void clearTransitValue(Transit transit, long transitTypeParamId) {

		List<TransitValue> transitValues = transit.getTransitValues();
		if (transitValues == null) {
			return;
		}

		transitValues.removeAll { transitValue ->
    			Long.valueOf(transitTypeParamId) == transitValue.transitTypeParamId
		}
	}
}
