package com.revenga.rits.back.historical.manager.executor.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.common.base.CaseFormat;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.DataType;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ExecutorUtil {

	private ExecutorUtil() {
	}

	public static Object loadParser(String parserFile, Class<?> clazz) throws IOException {

		try {

			if (StringUtils.isEmpty(parserFile)) {

				throw new NullPointerException("parser_file is null");
			}

			SessionAdapter sessionAdapter = new SessionAdapter();

			return sessionAdapter.getAdapter("adapters/manager/" + parserFile, clazz);

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			throw new IOException("loadParser Exception");

		}
	}

	public static boolean existTable(String table) {

		Connection connection = null;
		DatabaseMetaData dbm = null;
		ResultSet rs = null;
		Boolean existTable = false;

		if (table != null) {

			try {
				String tableName = null;
				String tableSchema = null;

				String[] arryStrig = table.split("\\.");

				connection = DataSourceConnection.getInstance().getConnection();

				dbm = connection.getMetaData();

				if (arryStrig.length == 2) {

					tableSchema = arryStrig[0];
					tableName = arryStrig[1];

				} else {

					tableName = table;
				}

				rs = dbm.getTables(null, tableSchema, tableName, null);

				existTable = rs.next();

				connection.close();

			} catch (SQLException | IOException e) {

				log.debug(ExceptionUtils.getStackTrace(e));
				log.error(e.getMessage());
			}

		} else {
			log.warn("Not found target table in model");
		}

		return existTable;
	}

	public static String getDataType(Long dataTypeId) {

		String dataType = null;

		if (dataTypeId == DataType.DATA_TYPE_ALPHANUMERIC) {

			dataType = "varchar";

		} else if (dataTypeId == DataType.DATA_TYPE_BOOLEAN) {

			dataType = "bool";

		} else if (dataTypeId == DataType.DATA_TYPE_INTEGER) {

			dataType = "int4";

		} else if (dataTypeId == DataType.DATA_TYPE_REAL) {

			dataType = "float8";

		} else if (dataTypeId == DataType.DATA_TYPE_TIMESTAMP) {

			dataType = "timestamptz";
		}

		return dataType;
	}

	public static void setValue(PreparedStatement pstmt, int index, String value) throws IOException {

		int fieldTypeDB;

		try {
			fieldTypeDB = pstmt.getParameterMetaData().getParameterType(index);

			if (value == null || (fieldTypeDB != Types.VARCHAR && StringUtils.isEmpty(value))) {

				pstmt.setNull(index, fieldTypeDB);

			} else if (fieldTypeDB == Types.INTEGER) {

				pstmt.setInt(index, Integer.parseInt(value));

			} else if (fieldTypeDB == Types.BIGINT) {

				pstmt.setLong(index, Long.parseLong(value));

			} else if (fieldTypeDB == Types.VARCHAR) {

				pstmt.setString(index, value);

			} else if (fieldTypeDB == Types.BOOLEAN) {

				pstmt.setBoolean(index, Boolean.parseBoolean(value));

			} else if (fieldTypeDB == Types.BIT) {

				pstmt.setBoolean(index, Boolean.parseBoolean(value));

			} else if (fieldTypeDB == Types.TIMESTAMP) {

				pstmt.setTimestamp(index, new Timestamp(Long.parseLong(value)));

			} else if (fieldTypeDB == Types.TIMESTAMP_WITH_TIMEZONE) {

				pstmt.setTimestamp(index, new Timestamp(Long.parseLong(value)));

			} else if (fieldTypeDB == Types.REAL) {

				pstmt.setFloat(index, Float.parseFloat(value));

			} else if (fieldTypeDB == Types.DOUBLE) {

				pstmt.setDouble(index, Double.parseDouble(value));

			} else {
				throw new IllegalArgumentException("unsupported type");
			}

		} catch (IllegalArgumentException | SQLException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

			throw new IOException("setValue Exception");
		}

	}

	public static String camelCaseToSnakeCase(String camelCase) {
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCase);
	}

	public static String snakeCaseToCamelCase(String snakeCase) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, snakeCase);
	}
}
