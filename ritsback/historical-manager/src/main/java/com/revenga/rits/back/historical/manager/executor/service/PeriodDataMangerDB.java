package com.revenga.rits.back.historical.manager.executor.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.adapter.lib.model.HistoricalPeriodParserV1;
import com.revenga.rits.back.adapter.lib.model.HistoricalPeriodParserV1.Fields.Field;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.historical.manager.executor.model.PeriodExecutorModel;
import com.revenga.rits.back.historical.manager.executor.util.ExecutorUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Deprecated
public class PeriodDataMangerDB {

	private HistoricalPeriodParserV1 historicalPeriodParserV1;

	private PeriodExecutorModel historicExecutorModel;

	private Map<String, ElementTypeParam> elementTypeParams;

	public boolean createTable() {

		Boolean createTable = true;

		if (historicalPeriodParserV1 != null && historicExecutorModel != null
				&& historicExecutorModel.getTableSpace() != null && historicExecutorModel.getTargetTable() != null
				&& historicExecutorModel.getFieldTargetElementId() != null
				&& historicExecutorModel.getFieldTargetTimestamp() != null
				&& !CollectionUtils.isEmpty(historicalPeriodParserV1.getFields().getField())
				&& elementTypeParams != null) {

			Connection connection = null;
			String createTableQuery = null;
			String tableName = null;
			String alterTableQuery = null;
			Statement stmt = null;
			String[] arryString = historicExecutorModel.getTargetTable().split("\\.");

			if (arryString.length == 2) {

				tableName = arryString[1];

			} else {

				tableName = historicExecutorModel.getTargetTable();
			}

			createTableQuery = "CREATE TABLE " + historicExecutorModel.getTargetTable() + " ( ";

			createTableQuery += historicExecutorModel.getFieldTargetElementId() + " int8 NOT NULL, ";
			createTableQuery += historicExecutorModel.getFieldTargetTimestamp() + " timestamptz NOT NULL, ";

			for (Field field : historicalPeriodParserV1.getFields().getField()) {

				String sElementTypeParam = field.getElementTypeParam().getParamTypeId() + ":"
						+ field.getElementTypeParam().getElementTypeParamId();
				if (elementTypeParams.containsKey(sElementTypeParam)) {

					createTableQuery += field.getName() + " "
							+ ExecutorUtil.getDataType(elementTypeParams.get(sElementTypeParam).getDataTypeId()) + " NULL, ";
				}
			}
			createTableQuery += " created_at timestamptz NOT NULL, ";

			createTableQuery += " CONSTRAINT pk_" + tableName + " PRIMARY KEY ("
					+ historicExecutorModel.getFieldTargetElementId() + ", ";
			createTableQuery += historicExecutorModel.getFieldTargetTimestamp() + " )";

			createTableQuery += " );";

			alterTableQuery = "ALTER TABLE " + historicExecutorModel.getTargetTable() + " SET TABLESPACE "
					+ historicExecutorModel.getTableSpace() + ";";

			try {
				connection = DataSourceConnection.getInstance().getConnection();

				connection.setAutoCommit(false);

				stmt = connection.createStatement();

				stmt.executeUpdate(createTableQuery);
				stmt.executeUpdate(alterTableQuery);

				connection.commit();

				log.info("Table: " + historicExecutorModel.getTargetTable() + " create succesfull");

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
		}

		return createTable;
	}

	public boolean insertHistoric(final Map<Long, Map<String, String>> elementValues, Long timestampAt) {

		Boolean saveHistorc = true;

		if (elementValues != null && historicalPeriodParserV1 != null && historicExecutorModel != null
				&& historicExecutorModel.getTargetTable() != null
				&& historicExecutorModel.getFieldTargetElementId() != null
				&& historicExecutorModel.getFieldTargetTimestamp() != null
				&& !CollectionUtils.isEmpty(historicalPeriodParserV1.getFields().getField())
				&& elementTypeParams != null) {

			Connection connection = null;
			String insertTableQuery = null;
			PreparedStatement pstmt = null;
			String string = "";

			insertTableQuery = "INSERT INTO " + historicExecutorModel.getTargetTable();
			insertTableQuery += " ( " + historicExecutorModel.getFieldTargetElementId() + ", "
					+ historicExecutorModel.getFieldTargetTimestamp();

			for (Field field : historicalPeriodParserV1.getFields().getField()) {

				String sElementTypeParam = field.getElementTypeParam().getParamTypeId() + ":"
						+ field.getElementTypeParam().getElementTypeParamId();

				if (elementTypeParams.containsKey(sElementTypeParam)) {

					insertTableQuery += ", " + field.getName();
					string += " ?, ";
				}
			}

			insertTableQuery += " , created_at)";

			insertTableQuery += " VALUES(?, ?, " + string;

			insertTableQuery += "CURRENT_TIMESTAMP);";

			try {
				connection = DataSourceConnection.getInstance().getConnection();

				connection.setAutoCommit(false);

				pstmt = connection.prepareStatement(insertTableQuery);

				for (Map.Entry<Long, Map<String, String>> entry : elementValues.entrySet()) {

					Long elementId = entry.getKey();

					int index = 1;

					try {

						// No insert empty column
						if (entry.getValue().size() > 0) {

							ExecutorUtil.setValue(pstmt, index++, elementId.toString());
							ExecutorUtil.setValue(pstmt, index++, timestampAt.toString());

							for (Field field : historicalPeriodParserV1.getFields().getField()) {

								String sElementTypeParam = field.getElementTypeParam().getParamTypeId() + ":"
										+ field.getElementTypeParam().getElementTypeParamId();

								if (elementTypeParams.containsKey(sElementTypeParam)) {

									if (entry.getValue().containsKey(sElementTypeParam)) {

										ExecutorUtil.setValue(pstmt, index++, entry.getValue().get(sElementTypeParam));

									} else {

										ExecutorUtil.setValue(pstmt, index++, null);
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
				log.info("Historic insert: " + timestampAt);

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

		} else

		{
			log.error("saveHistorc null value");
		}

		return saveHistorc;
	}

	public void setHistoricalPeriodParserV1(HistoricalPeriodParserV1 historyGenericParserV1) {

		this.historicalPeriodParserV1 = historyGenericParserV1;
	}

	public void setElementTypeParams(Map<String, ElementTypeParam> elementTypeParams) {

		this.elementTypeParams = elementTypeParams;
	}

	public void setHistoricExecutorModel(PeriodExecutorModel historicExecutorModel) {

		this.historicExecutorModel = historicExecutorModel;
	}

}
