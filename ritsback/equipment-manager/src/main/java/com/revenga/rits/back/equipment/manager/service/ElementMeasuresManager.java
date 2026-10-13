package com.revenga.rits.back.equipment.manager.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class ElementMeasuresManager {

	private static final String INSERT_TABLE_QUERY = ConfigProperty.getInstance().getProperty("application.properties",
			"db_sync_measures.executor.insert_table_query");
	private static final String UPDATE_TABLE_QUERY = ConfigProperty.getInstance().getProperty("application.properties",
			"db_sync_measures.executor.undate_table_query");

	Set<ElementValue> elemenValuestWithChangesSync;

	@Getter
	List<ElementValue> elemenValues;

	@Setter
	boolean running;
	
	public void putElementValueWithChanges(List<ElementValue> elementValues) {

		if (elemenValuestWithChangesSync != null) {

			elemenValuestWithChangesSync.addAll(elementValues);
		}
	}

	private static ElementMeasuresManager instance;

	public static ElementMeasuresManager getInstance() {

		if (instance == null) {

			instance = new ElementMeasuresManager();
		}

		return instance;
	}

	public void init() {

		if (elemenValuestWithChangesSync == null) {

			insertElementValues();
			elemenValuestWithChangesSync = Collections.synchronizedSet(new HashSet<>());
		}
	}

	public void load() {

		elemenValues = new ArrayList<>();

		if (!CollectionUtils.isEmpty(elemenValuestWithChangesSync)) {

			Instant instant1 = Instant.now();

			synchronized (elemenValuestWithChangesSync) {

				Iterator<ElementValue> it = elemenValuestWithChangesSync.iterator();

				while (it.hasNext()) {

					ElementValue elementValue = it.next();

					elemenValues.add(elementValue);
				}

				elemenValuestWithChangesSync.clear();
			}

			log.debug("Time to copy " + elemenValues.size() + " elemenValues = "
					+ Duration.between(instant1, Instant.now()).toMillis() + " ms.");
		}
	}

	private boolean insertElementValues() {

		boolean resp = false;
		Connection connection = null;
		Statement statement = null;
		try {

			connection = DataSourceConnection.getInstance().getConnection();

			statement = connection.createStatement();
			statement.executeUpdate(INSERT_TABLE_QUERY);
			resp = true;

		} catch (SQLException | IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		} finally {

			try {

				if (statement != null) {
					statement.close();
				}

				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return resp;
	}

	public boolean updateElementValues(final List<ElementValue> elementValues) {

		boolean resp = false;

		if (!CollectionUtils.isEmpty(elementValues)) {

			Instant instant1 = Instant.now();
			
			Connection connection = null;
			PreparedStatement pstmt = null;

			try {

				connection = DataSourceConnection.getInstance().getConnection();
				connection.setAutoCommit(false);
				pstmt = connection.prepareStatement(UPDATE_TABLE_QUERY);

				for (ElementValue elementValue : elementValues) {

					if (ObjectUtils.allNotNull(elementValue.getElementTypeId(), elementValue.getElementTypeParamId(),
							elementValue.getParamTypeId(), elementValue.getElementId())) {

						int index = 1;

						if (elementValue.getElementValueStateId() != null) {

							pstmt.setLong(index++, elementValue.getElementValueStateId());

						} else {

							pstmt.setNull(index++, Types.BIGINT);
						}

						if (elementValue.getValue() != null) {

							pstmt.setString(index++, elementValue.getValue());

						} else {

							pstmt.setNull(index++, Types.VARCHAR);
						}

						pstmt.setLong(index++, elementValue.getElementTypeId());
						pstmt.setLong(index++, elementValue.getElementTypeParamId());
						pstmt.setLong(index++, elementValue.getParamTypeId());
						pstmt.setLong(index++, elementValue.getElementId());
						pstmt.executeUpdate();

					} else {
						log.error("Null value in elementValue: ElementTypeId = " + elementValue.getElementTypeId()
								+ ", ElementTypeParamId=" + elementValue.getElementTypeParamId() 
								+ ", ParamTypeId=" + elementValue.getParamTypeId() 
								+ " ,ElementId=" + elementValue.getElementId());
					}

				}

				connection.commit();
				resp = true;

			} catch (SQLException | IOException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));

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

			log.debug("Time to update " + elemenValues.size() + " elemenValues = "
					+ Duration.between(instant1, Instant.now()).toMillis() + " ms.");
		}
		return resp;

	}

}
