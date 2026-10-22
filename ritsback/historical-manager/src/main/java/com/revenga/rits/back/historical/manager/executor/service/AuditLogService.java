package com.revenga.rits.back.historical.manager.executor.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.HistoricalAuditLogParserV1;
import com.revenga.rits.back.adapter.lib.model.HistoricalAuditLogParserV1.Fields.Field;
import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.AuditLog;
import com.revenga.rits.back.data.core.model.AuditLogValue;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetAuditLogPublishCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.historical.manager.executor.util.ExecutorUtil;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuditLogService {

	public static final String NAME_SECUENCE = ConfigProperty.getInstance().getProperty("application.properties",
			"entities.manager.name.secuence");

	public static final String NAME_FIELD_AUDIT_LOG_ID = ConfigProperty.getInstance()
			.getProperty("application.properties", "entities.manager.name_field.audit_log_id");

	public static final String NAME_FIELD_USER_NAME = ConfigProperty.getInstance().getProperty("application.properties",
			"entities.manager.name_field.user_name");

	public static final String NAME_FIELD_TIME_STAMP = ConfigProperty.getInstance()
			.getProperty("application.properties", "entities.manager.name_field.time_stamp");

	public static final String NAME_FIELD_LEVEL = ConfigProperty.getInstance().getProperty("application.properties",
			"entities.manager.name_field.level");

	@Setter
	private HistoricalAuditLogParserV1 adapter;
	@Setter
	private List<LogTypeParam> logTypeParams;

	public AuditLogService() {

	}

	public boolean init() {
		return ExecutorUtil.existTable(adapter.getTargetTable()) || createTable();
	}

	public boolean recognizeAuditLogs(List<AuditLog> auditLogs) {


		if (EntitiesManager.getInstance().deleteAuditLogs(auditLogs) == null) {

			return false;
		}
		

		return !CollectionUtils.isEmpty(auditLogs) ? publishAuditLogs(auditLogs, Command.ACTION_DELETE)
				&& EntitiesManager.getInstance().refreshGenericData(null) : false;
	}

	public boolean processAuditLogs(List<AuditLog> auditLogs) {

		boolean ok = false;

		if (!CollectionUtils.isEmpty(auditLogs)) {

			List<AuditLog> auditLogOutputDb = null;
			List<AuditLog> auditLogOutputStomp = null;
			List<AuditLog> auditLogTreatment = null;

			ok = false;

			auditLogs.forEach(e -> e.setUser(EntitiesManager.getInstance().getUser(e.getUserId())));
			auditLogs = auditLogs.stream()
					.filter(e -> e.getUser() != null && e.getUser().getName() != null && e.getLevelId() != null)
					.collect(Collectors.toList());

			for (AuditLog auditLog : auditLogs) {

				Long levelOutputDb = null;
				Long levelOutputStomp = null;
				Long levelTreatment = null;

				if (auditLog.getLogSubtypeId() != null) {

					LogSubtype logSubtype = EntitiesManager.getInstance().getLogSubtype(auditLog.getLogSubtypeId());

					levelOutputDb = logSubtype != null ? logSubtype.getLevelOutputDb() : null;
					levelOutputStomp = logSubtype != null ? logSubtype.getLevelOutputStomp() : null;
					levelTreatment = logSubtype != null ? logSubtype.getLevelTreatment() : null;

				} else if (auditLog.getLogTypeId() != null) {

					LogType logType = EntitiesManager.getInstance().getLogType(auditLog.getLogTypeId());

					levelOutputDb = logType != null ? logType.getLevelOutputDb() : null;
					levelOutputStomp = logType != null ? logType.getLevelOutputStomp() : null;
					levelTreatment = logType != null ? logType.getLevelTreatment() : null;
				}

				if (levelOutputDb != null && levelOutputDb.longValue() <= auditLog.getLevelId().longValue()) {

					if (auditLogOutputDb == null) {
						auditLogOutputDb = new ArrayList<>();
					}
					auditLogOutputDb.add(auditLog);
				}

				if (levelOutputStomp != null && levelOutputStomp.longValue() <= auditLog.getLevelId().longValue()) {

					if (auditLogOutputStomp == null) {
						auditLogOutputStomp = new ArrayList<>();
					}
					auditLogOutputStomp.add(auditLog);
				}

				if (levelTreatment != null && levelTreatment.longValue() <= auditLog.getLevelId().longValue()) {

					if (auditLogTreatment == null) {

						auditLogTreatment = new ArrayList<>();
					}
					auditLogTreatment.add(auditLog);
				}
			}

			if (!CollectionUtils.isEmpty(auditLogOutputDb) && !insertAuditLogs(auditLogOutputDb)) {

				log.error("Insert auditLogs error");

			} else if (!CollectionUtils.isEmpty(auditLogOutputStomp)
					&& !publishAuditLogs(auditLogOutputStomp, Command.ACTION_CREATE)) {

				log.error("Publish auditLogs error");

			} else if (!CollectionUtils.isEmpty(auditLogTreatment) && !createAuditLogs(auditLogTreatment)) {

				log.error("Create auditLogs error");

			} else {

				ok = true;
			}

		}

		return ok;
	}

	private boolean createAuditLogs(final List<AuditLog> auditLogs) {

		for (AuditLog auditLog : auditLogs) {

			if (EntitiesManager.getInstance().createAuditLog(auditLog) == null) {
				EntitiesManager.getInstance().refreshGenericData(null);
				return false;
			}
		}
		EntitiesManager.getInstance().refreshGenericData(Long.valueOf(auditLogs.size()));
		return true;
	}

	private boolean publishAuditLogs(List<AuditLog> auditLogs, Integer action) {

		SetAuditLogPublishCommand command = new SetAuditLogPublishCommand();
		command.setAuditLogs(auditLogs);
		command.setAction(action);

		return (command != null && CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME) != null)
				? true
				: false;
	}

	private boolean createTable() {

		boolean createTable = true;

		if (adapter != null && adapter.getTableSpace() != null && adapter.getTargetTable() != null
				&& !CollectionUtils.isEmpty(adapter.getFields().getField())
				&& !CollectionUtils.isEmpty(logTypeParams)) {

			List<HistoricalAuditLogParserV1.Fields.Field> lIndexed = new ArrayList<>();

			Connection connection = null;
			StringBuilder createSecQuery = new StringBuilder();
			StringBuilder createTableQuery = new StringBuilder();
			StringBuilder createIndexQuery = new StringBuilder();
			String alterTableQuery = null;
			String tableName = null;
			Statement stmt = null;
			String[] arryString = adapter.getTargetTable().split("\\.");

			if (arryString.length == 2) {

				tableName = arryString[1];

			} else {

				tableName = adapter.getTargetTable();
			}

			// CREATE SEQUENCE
			createSecQuery.append("CREATE SEQUENCE " + adapter.getTargetTable() + "_" + NAME_SECUENCE + " START 1;");

			// CREATE TABLE
			createTableQuery.append("CREATE TABLE " + adapter.getTargetTable() + " ( ");

			createTableQuery.append(NAME_FIELD_AUDIT_LOG_ID + " int8 NOT NULL DEFAULT nextval('");
			createTableQuery.append(adapter.getTargetTable() + "_" + NAME_SECUENCE + "'), ");

			createTableQuery.append(NAME_FIELD_USER_NAME + " varchar NOT NULL, ");
			createTableQuery.append(NAME_FIELD_TIME_STAMP + " timestamptz NOT NULL, ");
			createTableQuery.append(NAME_FIELD_LEVEL + " int4 NOT NULL, ");

			if (adapter.getFieldLogSubtypeId() != null) {

				createTableQuery.append(adapter.getFieldLogSubtypeId() + " int8 NULL, ");
			}

			for (Field field : adapter.getFields().getField()) {

				if (field != null) {

					LogTypeParam logTypeParam = logTypeParams.stream()
							.filter(e -> Long.valueOf(field.getLogTypeParamId()).equals(e.getId())
									&& Long.valueOf(adapter.getLogTypeId()).equals(e.getLogTypeId()))
							.findFirst().orElse(null);

					if (logTypeParam != null) {
						createTableQuery.append(field.getName() + " varchar NULL, ");

						if (field.isIndexed() != null && field.isIndexed().booleanValue()) {

							lIndexed.add(field);
						}
					}
				}
			}

			createTableQuery.append(" created_at timestamptz NOT NULL, ");
			createTableQuery
					.append(" CONSTRAINT pk_" + tableName + " PRIMARY KEY (" + NAME_FIELD_AUDIT_LOG_ID +","+NAME_FIELD_TIME_STAMP+ ") );");

			// SET TABLESPACE
			alterTableQuery = "ALTER TABLE " + adapter.getTargetTable() + " SET TABLESPACE " + adapter.getTableSpace()
					+ ";";

			// INDEX
			createIndexQuery.append("CREATE INDEX idx_" + tableName + NAME_FIELD_USER_NAME + " ON "
					+ adapter.getTargetTable() + " USING btree (" + NAME_FIELD_USER_NAME + ");");

			createIndexQuery.append("CREATE INDEX idx_" + tableName + NAME_FIELD_TIME_STAMP + " ON "
					+ adapter.getTargetTable() + " USING btree (" + NAME_FIELD_TIME_STAMP + ");");

			createIndexQuery.append("CREATE INDEX idx_" + tableName + NAME_FIELD_USER_NAME + NAME_FIELD_TIME_STAMP
					+ " ON " + adapter.getTargetTable() + " USING btree (" + NAME_FIELD_USER_NAME + ","
					+ NAME_FIELD_TIME_STAMP + ");");
			/*
			 * createIndexQuery.append("CREATE INDEX idx_" + tableName + NAME_FIELD_LEVEL +
			 * " ON " + adapter.getTargetTable() + " USING btree (" + NAME_FIELD_LEVEL +
			 * ");");
			 * 
			 * if (adapter.getFieldLogSubtypeId() != null) {
			 * 
			 * createIndexQuery.append("CREATE INDEX idx_" + tableName +
			 * adapter.getFieldLogSubtypeId() + " ON " + adapter.getTargetTable() +
			 * " USING btree (" + adapter.getFieldLogSubtypeId() + ");"); }
			 */
			for (Field field : lIndexed) {

				createIndexQuery.append("CREATE INDEX idx_" + tableName + field.getName() + " ON "
						+ adapter.getTargetTable() + " USING btree (" + field.getName() + ");");
			}

			try {
				connection = DataSourceConnection.getInstance().getConnection();

				connection.setAutoCommit(false);

				stmt = connection.createStatement();

				stmt.executeUpdate(createSecQuery.toString());
				stmt.executeUpdate(createTableQuery.toString());
				stmt.executeUpdate(alterTableQuery);
				stmt.executeUpdate(createIndexQuery.toString());

				connection.commit();

				log.info("Table: " + adapter.getTargetTable() + " create succesfull");

			} catch (SQLException | IOException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				createTable = false;

				try {
					if (connection != null)
						connection.rollback();

				} catch (SQLException e1) {

					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}

			} finally {
				try {

					if (stmt != null) {
						stmt.close();
					}

					if (connection != null) {
						connection.close();
					}

				} catch (SQLException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}

		} else {
			log.error("createTable null value");
			createTable = false;
		}
		return createTable;
	}

	private boolean insertAuditLogs(final List<AuditLog> auditLogs) {

		Boolean saveHistorc = true;

		if (!CollectionUtils.isEmpty(auditLogs) && adapter != null && adapter.getTargetTable() != null
				&& !CollectionUtils.isEmpty(logTypeParams)) {

			Connection connection = null;
			StringBuilder insertTableQuery = new StringBuilder();
			PreparedStatement pstmt = null;
			String string = "";

			insertTableQuery.append("INSERT INTO " + adapter.getTargetTable());
			insertTableQuery.append(" ( " + NAME_FIELD_USER_NAME);
			insertTableQuery.append(", " + NAME_FIELD_TIME_STAMP);
			insertTableQuery.append(", " + NAME_FIELD_LEVEL);

			if (adapter.getFieldLogSubtypeId() != null) {

				insertTableQuery.append(", " + adapter.getFieldLogSubtypeId());
			}

			for (Field field : adapter.getFields().getField()) {

				if (field != null) {

					LogTypeParam logTypeParam = logTypeParams.stream()
							.filter(e -> Long.valueOf(field.getLogTypeParamId()).equals(e.getId())
									&& Long.valueOf(adapter.getLogTypeId()).equals(e.getLogTypeId()))
							.findFirst().orElse(null);

					if (logTypeParam != null) {

						insertTableQuery.append(", " + field.getName());
						string += " ?, ";
					}
				}
			}

			insertTableQuery.append(" , created_at)");
			insertTableQuery.append(" VALUES(?, ?, ?, ");

			if (adapter.getFieldLogSubtypeId() != null) {

				insertTableQuery.append(" ?,");
			}

			insertTableQuery.append(string);
			insertTableQuery.append("CURRENT_TIMESTAMP);");

			try {

				connection = DataSourceConnection.getInstance().getConnection();
				connection.setAutoCommit(false);
				pstmt = connection.prepareStatement(insertTableQuery.toString());

				for (AuditLog auditLog : auditLogs) {

					int index = 1;

					try {

						if (auditLog.getUser() != null && auditLog.getUser().getName() != null
								&& auditLog.getTime() != null && auditLog.getLevelId() != null
								&& !CollectionUtils.isEmpty(auditLog.getAuditLogValues())) {

							ExecutorUtil.setValue(pstmt, index++, auditLog.getUser().getName());
							ExecutorUtil.setValue(pstmt, index++, auditLog.getTime().toString());
							ExecutorUtil.setValue(pstmt, index++, auditLog.getLevelId().toString());

							if (adapter.getFieldLogSubtypeId() != null) {

								if (auditLog.getLogSubtypeId() != null) {
									ExecutorUtil.setValue(pstmt, index++, auditLog.getLogSubtypeId().toString());
								} else {
									ExecutorUtil.setValue(pstmt, index++, null);
								}
							}

							for (Field field : adapter.getFields().getField()) {

								if (field != null) {

									LogTypeParam logTypeParam = logTypeParams.stream()
											.filter(e -> Long.valueOf(field.getLogTypeParamId()).equals(e.getId())
													&& Long.valueOf(adapter.getLogTypeId()).equals(e.getLogTypeId()))
											.findFirst().orElse(null);

									if (logTypeParam != null) {

										AuditLogValue auditLogValue = auditLog.getAuditLogValues().stream().filter(
												e -> Long.valueOf(field.getLogTypeParamId()).equals(e.getLogParamId())
														&& Long.valueOf(adapter.getLogTypeId())
																.equals(e.getLogTypeId()))
												.findFirst().orElse(null);

										if (auditLogValue != null) {

											ExecutorUtil.setValue(pstmt, index++, auditLogValue.getValue());

										} else {

											ExecutorUtil.setValue(pstmt, index++, null);
										}
									}
								}
							}
							pstmt.executeUpdate();
						}
					} catch (Exception e) {
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
				connection.commit();

			} catch (SQLException | IOException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				saveHistorc = false;

			} finally {
				try {

					if (pstmt != null) {
						pstmt.close();
					}

					if (connection != null) {
						connection.close();
					}

				} catch (SQLException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}

		} else {
			log.error("saveHistorc null value");
		}

		return saveHistorc;
	}
}
