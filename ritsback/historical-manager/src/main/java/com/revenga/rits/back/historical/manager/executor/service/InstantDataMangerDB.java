package com.revenga.rits.back.historical.manager.executor.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.HistoricalInstantParserV1;
import com.revenga.rits.back.adapter.lib.model.HistoricalInstantParserV1.Fields.Field;
import com.revenga.rits.back.adapter.lib.model.HistoricalInstantParserV1.Fields.Field.References.Reference;
import com.revenga.rits.back.adapter.lib.model.HistoricalInstantParserV2;
import com.revenga.rits.back.data.core.annotation.PrimaryKeys;
import com.revenga.rits.back.data.core.annotation.TimeStampField;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.historical.manager.executor.model.InstantExecutorModel;
import com.revenga.rits.back.historical.manager.executor.util.ExecutorUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class InstantDataMangerDB {

	@Deprecated
	private HistoricalInstantParserV1 historicalInstantParserV1;

	private InstantExecutorModel instantExecutorModel;

	@Deprecated
	public boolean createTable() {

		Boolean createTable = true;

		if (historicalInstantParserV1 != null && instantExecutorModel != null
				&& instantExecutorModel.getTableSpace() != null) {

			Connection connection = null;
			StringBuilder createTableQuery = new StringBuilder();
			StringBuilder bPk = new StringBuilder();
			String tableName = null;
			String alterTableQuery = null;
			Statement stmt = null;
			String[] arryString = instantExecutorModel.getTargetTable().split("\\.");
			Class<?> clazz;

			if (arryString.length == 2) {

				tableName = arryString[1];

			} else {

				tableName = instantExecutorModel.getTargetTable();
			}

			createTableQuery.append("CREATE TABLE " + instantExecutorModel.getTargetTable() + " (");
			createTableQuery.append(" created_at timestamptz NOT NULL");

			try {

				clazz = ClassesUtil.getModelClassBySimpleClassName(historicalInstantParserV1.getModel());
				PrimaryKeys pks = clazz.getAnnotation(PrimaryKeys.class);

				if (pks != null) {

					for (String field : pks.fields()) {

						createTableQuery.append(", " + ExecutorUtil.camelCaseToSnakeCase(field) + " int8 NOT NULL ");
						bPk.append(", " + ExecutorUtil.camelCaseToSnakeCase(field));
					}
					createTableQuery.append(", ");
				}

				for (Field field : historicalInstantParserV1.getFields().getField()) {

					String dataType = null;

					if (field.getSourceEntity() != null) {

						Class<?> sourceClazz = ClassesUtil.getModelClassBySimpleClassName(field.getSourceEntity());
						dataType = getDataType(sourceClazz, field.getSoureFieldValue());
					} else {
						dataType = getDataType(clazz, field.getSoureFieldValue());
					}

					if (dataType != null) {

						createTableQuery.append(field.getName() + " " + dataType + " NULL, ");
					}

				}

				createTableQuery.append(" CONSTRAINT pk_" + tableName + " PRIMARY KEY (created_at");
				createTableQuery.append(bPk.toString());

				createTableQuery.append(" ));");

				alterTableQuery = "ALTER TABLE " + instantExecutorModel.getTargetTable() + " SET TABLESPACE "
						+ instantExecutorModel.getTableSpace() + ";";

				connection = DataSourceConnection.getInstance().getConnection();

				connection.setAutoCommit(false);

				stmt = connection.createStatement();

				stmt.executeUpdate(createTableQuery.toString());
				stmt.executeUpdate(alterTableQuery);

				connection.commit();

				log.info("Table: " + instantExecutorModel.getTargetTable() + " create succesfull");

			} catch (SQLException | IOException | ClassNotFoundException e) {

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
		}

		return createTable;
	}

	@Deprecated
	public boolean insertHistoric(BasicElement basicElement) {

		Boolean saveHistorc = true;

		if (basicElement != null && historicalInstantParserV1 != null && instantExecutorModel != null

				&& instantExecutorModel.getTableSpace() != null) {

			Connection connection = null;
			PreparedStatement pstmt = null;
			StringBuilder stringBuilder = new StringBuilder();
			StringBuilder insertTableQuery = new StringBuilder();

			insertTableQuery.append("INSERT INTO " + instantExecutorModel.getTargetTable());

			insertTableQuery.append(" ( created_at ");
			stringBuilder.append("CURRENT_TIMESTAMP");

			try {
				Class<?> clazz = ClassesUtil.getModelClassBySimpleClassName(historicalInstantParserV1.getModel());
				PrimaryKeys pks = clazz.getAnnotation(PrimaryKeys.class);

				if (pks != null) {

					for (String field : pks.fields()) {

						insertTableQuery.append(", " + ExecutorUtil.camelCaseToSnakeCase(field));
						stringBuilder.append(", ?");
					}
				}

				for (Field field : historicalInstantParserV1.getFields().getField()) {

					insertTableQuery.append("," + field.getName());
					stringBuilder.append(", ?");
				}
				insertTableQuery.append(")");

				insertTableQuery.append(" VALUES(");
				insertTableQuery.append(stringBuilder.toString());
				insertTableQuery.append(");");

				int index = 1;

				connection = DataSourceConnection.getInstance().getConnection();
				pstmt = connection.prepareStatement(insertTableQuery.toString());

				// PK
				if (pks != null) {

					for (String field : pks.fields()) {

						java.lang.reflect.Field pk = ClassesUtil.getField(clazz, field);
						pk.setAccessible(true);
						setValue(pstmt, index++, pk.get(basicElement));
					}
				}

				// VALUES
				for (Field field : historicalInstantParserV1.getFields().getField()) {

					if (field.getSourceEntity() != null) {

						Class<?> sourceClazz = ClassesUtil.getModelClassBySimpleClassName(field.getSourceEntity());

						Object object = sourceClazz.getDeclaredConstructor().newInstance();

						for (Reference references : field.getReferences().getReference()) {

							java.lang.reflect.Field sourceField = ClassesUtil.getField(sourceClazz,
									references.getSourceField());
							sourceField.setAccessible(true);

							java.lang.reflect.Field targetField = ClassesUtil.getField(clazz,
									references.getTargetField());
							targetField.setAccessible(true);

							sourceField.set(object, targetField.get(basicElement));
						}

						BasicElement sourceElement = null;

						sourceElement = EntitiesManager.getInstance().getFromCache(field.getSourceEntity(),
								(BasicElement) object);

						if (sourceElement == null) {
							throw new NullPointerException(
									"sourceElement Not found, check the configuration of the xml file");
						}

						java.lang.reflect.Field sourceFieldValue = ClassesUtil.getField(sourceClazz,
								field.getSoureFieldValue());

						sourceFieldValue.setAccessible(true);

						setValue(pstmt, index++, sourceFieldValue.get(sourceElement));

					} else {

						java.lang.reflect.Field sourceFieldValue = ClassesUtil.getField(clazz,
								field.getSoureFieldValue());

						sourceFieldValue.setAccessible(true);
						setValue(pstmt, index++, sourceFieldValue.get(basicElement));
					}

				}
				pstmt.executeUpdate();

			} catch (SQLException | IOException | IllegalAccessException | ClassNotFoundException
					| InstantiationException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {

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

	private void setValue(PreparedStatement pstmt, int index, Object object) throws IOException {

		int fieldTypeDB;

		try {
			fieldTypeDB = pstmt.getParameterMetaData().getParameterType(index);

			if (object == null) {

				pstmt.setNull(index, fieldTypeDB);

			} else if (fieldTypeDB == Types.INTEGER && object instanceof Integer) {

				pstmt.setInt(index, (Integer) object);

			} else if (fieldTypeDB == Types.BIGINT && object instanceof Long) {

				pstmt.setLong(index, (Long) object);

			} else if (fieldTypeDB == Types.INTEGER && object instanceof Long) {

				pstmt.setInt(index, (Integer) object);

			} else if (fieldTypeDB == Types.VARCHAR && object instanceof String) {

				pstmt.setString(index, (String) object);

			} else if (fieldTypeDB == Types.BOOLEAN && object instanceof Boolean) {

				pstmt.setBoolean(index, (Boolean) object);

			} else if (fieldTypeDB == Types.BIT && object instanceof Boolean) {

				pstmt.setBoolean(index, (Boolean) object);

			} else if (fieldTypeDB == Types.TIMESTAMP && object instanceof Long) {

				pstmt.setTimestamp(index, new Timestamp((Long) object));

			} else if (fieldTypeDB == Types.TIMESTAMP_WITH_TIMEZONE && object instanceof Long) {

				pstmt.setTimestamp(index, new Timestamp((Long) object));

			} else if (fieldTypeDB == Types.REAL && object instanceof Float) {

				pstmt.setFloat(index, (Float) object);

			} else if (fieldTypeDB == Types.DOUBLE && object instanceof Double) {

				pstmt.setDouble(index, (Double) object);

			} else {
				throw new IllegalArgumentException("unsupported type");
			}

		} catch (IllegalArgumentException | SQLException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

			throw new IOException("setValue Exception");
		}

	}

	private String getDataType(Class<?> clazz, String nameField) {

		String dataType = null;

		java.lang.reflect.Field field = ClassesUtil.getField(clazz, nameField);

		if (field == null) {

			throw new NullPointerException(nameField + " not found");

		} else if (field.isAnnotationPresent(TimeStampField.class)) {

			dataType = "timestamptz";

		} else if (field.getType() == String.class) {

			dataType = "varchar";

		} else if (field.getType() == Boolean.class) {

			dataType = "bool";

		} else if (field.getType() == Integer.class) {

			dataType = "int4";

		} else if (field.getType() == Long.class) {

			dataType = "int8";

		} else if (field.getType() == Double.class) {

			dataType = "float8";

		} else if (field.getType() == Float.class) {

			dataType = "float4";

		}

		return dataType;
	}

	public void setHistoricalInstantParserV1(HistoricalInstantParserV1 historicalInstantParserV1) {

		this.historicalInstantParserV1 = historicalInstantParserV1;

	}

	public void setInstantExecutorModel(InstantExecutorModel instantExecutorModel) {

		this.instantExecutorModel = instantExecutorModel;

	}

	public boolean createTable(final HistoricalInstantParserV2 adapter) {

		Boolean createTable = true;

		if (adapter != null && adapter.getFields() != null && adapter.getTableSpace() != null) {

			Connection connection = null;
			StringBuilder createTableQuery = new StringBuilder();
			StringBuilder bPk = new StringBuilder();
			String tableName = null;
			String alterTableQuery = null;
			Statement stmt = null;
			String[] arryString = adapter.getTargetTable().split("\\.");
			Class<?> clazz;

			if (arryString.length == 2) {

				tableName = arryString[1];

			} else {

				tableName = adapter.getTargetTable();
			}

			createTableQuery.append("CREATE TABLE " + adapter.getTargetTable() + " (");
			createTableQuery.append(" created_at timestamptz NOT NULL");

			try {

				clazz = ClassesUtil.getModelClassBySimpleClassName(adapter.getModel());
				PrimaryKeys pks = clazz.getAnnotation(PrimaryKeys.class);

				if (pks != null) {

					for (String field : pks.fields()) {

						createTableQuery.append(", " + ExecutorUtil.camelCaseToSnakeCase(field) + " int8 NOT NULL ");
						bPk.append(", " + ExecutorUtil.camelCaseToSnakeCase(field));
					}
					createTableQuery.append(", ");
				}

				for (com.revenga.rits.back.adapter.lib.model.HistoricalInstantParserV2.Fields.Field field : adapter
						.getFields().getField()) {

					String dataType = null;

					if (field.getSourceEntity() != null) {

						Class<?> sourceClazz = ClassesUtil.getModelClassBySimpleClassName(field.getSourceEntity());
						dataType = getDataType(sourceClazz, field.getSoureFieldValue());
					} else {
						dataType = getDataType(clazz, field.getSoureFieldValue());
					}

					if (dataType != null) {

						createTableQuery.append(field.getName() + " " + dataType + " NULL, ");
					}

				}

				createTableQuery.append(" CONSTRAINT pk_" + tableName + " PRIMARY KEY (created_at");
				createTableQuery.append(bPk.toString());

				createTableQuery.append(" ));");

				alterTableQuery = "ALTER TABLE " + adapter.getTargetTable() + " SET TABLESPACE "
						+ adapter.getTableSpace() + ";";

				connection = DataSourceConnection.getInstance().getConnection();

				connection.setAutoCommit(false);

				stmt = connection.createStatement();

				stmt.executeUpdate(createTableQuery.toString());
				stmt.executeUpdate(alterTableQuery);

				connection.commit();

				log.info("Table: " + adapter.getTargetTable() + " create succesfull");

			} catch (SQLException | IOException | ClassNotFoundException e) {

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
		}

		return createTable;
	}

	public boolean insertHistoric(final HistoricalInstantParserV2 adapter, final List<BasicElement> basicElements) {

		Boolean saveHistorc = true;

		if (!CollectionUtils.isEmpty(basicElements) && adapter != null

				&& adapter.getFields() != null && adapter.getTableSpace() != null && adapter.getTargetTable() != null) {

			Connection connection = null;
			PreparedStatement pstmt = null;
			StringBuilder stringBuilder = new StringBuilder();
			StringBuilder insertTableQuery = new StringBuilder();

			insertTableQuery.append("INSERT INTO " + adapter.getTargetTable());

			insertTableQuery.append(" ( created_at ");
			stringBuilder.append("?");

			try {
				Class<?> clazz;
				clazz = ClassesUtil.getModelClassBySimpleClassName(adapter.getModel());

				PrimaryKeys pks = clazz.getAnnotation(PrimaryKeys.class);

				if (pks != null) {

					for (String field : pks.fields()) {

						insertTableQuery.append(", " + ExecutorUtil.camelCaseToSnakeCase(field));
						stringBuilder.append(", ?");
					}
				}

				for (com.revenga.rits.back.adapter.lib.model.HistoricalInstantParserV2.Fields.Field field : adapter
						.getFields().getField()) {

					insertTableQuery.append("," + field.getName());
					stringBuilder.append(", ?");
				}
				insertTableQuery.append(")");

				insertTableQuery.append(" VALUES(");
				insertTableQuery.append(stringBuilder.toString());
				insertTableQuery.append(");");

				connection = DataSourceConnection.getInstance().getConnection();
				connection.setAutoCommit(false);
				pstmt = connection.prepareStatement(insertTableQuery.toString());

				for (BasicElement basicElement : basicElements) {

					int index = 1;

					try {

						// created_at
						if (basicElement.getCreatedAt() != null) {

							setValue(pstmt, index++, basicElement.getCreatedAt());

						} else {

							setValue(pstmt, index++, Instant.now().toEpochMilli());
						}

						// PK
						if (pks != null) {

							for (String field : pks.fields()) {

								java.lang.reflect.Field pk = ClassesUtil.getField(clazz, field);
								pk.setAccessible(true);
								setValue(pstmt, index++, pk.get(basicElement));
							}
						}

						// VALUES
						for (com.revenga.rits.back.adapter.lib.model.HistoricalInstantParserV2.Fields.Field field : adapter
								.getFields().getField()) {

							if (field.getSourceEntity() != null) {

								Class<?> sourceClazz = ClassesUtil
										.getModelClassBySimpleClassName(field.getSourceEntity());

								Object object = sourceClazz.getDeclaredConstructor().newInstance();

								for (com.revenga.rits.back.adapter.lib.model.HistoricalInstantParserV2.Fields.Field.References.Reference references : field
										.getReferences().getReference()) {

									java.lang.reflect.Field sourceField = ClassesUtil.getField(sourceClazz,
											references.getSourceField());
									sourceField.setAccessible(true);

									java.lang.reflect.Field targetField = ClassesUtil.getField(clazz,
											references.getTargetField());
									targetField.setAccessible(true);

									sourceField.set(object, targetField.get(basicElement));
								}

								BasicElement sourceElement = null;

								sourceElement = EntitiesManager.getInstance().getFromCache(field.getSourceEntity(),
										(BasicElement) object);

								if (sourceElement != null) {
									
									java.lang.reflect.Field sourceFieldValue = ClassesUtil.getField(sourceClazz,
											field.getSoureFieldValue());

									sourceFieldValue.setAccessible(true);
									setValue(pstmt, index++, sourceFieldValue.get(sourceElement));
									
								} else {
									
									setValue(pstmt, index++, null);
								}

							} else {

								java.lang.reflect.Field sourceFieldValue = ClassesUtil.getField(clazz,
										field.getSoureFieldValue());

								sourceFieldValue.setAccessible(true);
								setValue(pstmt, index++, sourceFieldValue.get(basicElement));
							}
						}

						pstmt.executeUpdate();

					} catch (SQLException | IOException | InstantiationException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | IllegalAccessException
							| ClassNotFoundException | SecurityException e) {

						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
				connection.commit();

			} catch (SQLException | ClassNotFoundException | IOException e) {

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
