package com.revenga.rits.back.equipment.manager.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class ElementStatesManager {

	private static final String INSERT_TABLE_QUERY = ConfigProperty.getInstance().getProperty("application.properties",
			"db_sync_states.executor.insert_table_query");
	private static final String DELETE_TABLE_QUERY = ConfigProperty.getInstance().getProperty("application.properties",
			"db_sync_states.executor.delete_table_query");

	Set<ElementState> elemenStatestWithChangesSync;

	@Getter
	List<ElementState> elementStates;

	@Setter
	boolean running;
	
	public void putElementStateWithChanges(ElementState elementState) {

		if (elemenStatestWithChangesSync != null) {

			elemenStatestWithChangesSync.add(elementState);
		}
	}

	private static ElementStatesManager instance;

	public static ElementStatesManager getInstance() {

		if (instance == null) {

			instance = new ElementStatesManager();
		}

		return instance;
	}

	public void init() {

		if (elemenStatestWithChangesSync == null) {

			elemenStatestWithChangesSync = Collections.synchronizedSet(new HashSet<>());
		}
	}

	public void load() {

		elementStates = new ArrayList<>();

		if (!CollectionUtils.isEmpty(elemenStatestWithChangesSync)) {

			Instant instant1 = Instant.now();

			synchronized (elemenStatestWithChangesSync) {

				Iterator<ElementState> it = elemenStatestWithChangesSync.iterator();

				while (it.hasNext()) {

					ElementState elementState = it.next();

					elementStates.add(elementState);
				}

				elemenStatestWithChangesSync.clear();
			}

			log.debug("Time to copy " + elementStates.size() + " elementStates = "
					+ Duration.between(instant1, Instant.now()).toMillis() + " ms.");
		}
	}
	
	private boolean insertElementStates(List<ElementState> elementStates) {
		
		boolean resp = false;

		if (!CollectionUtils.isEmpty(elementStates)) {

			Instant instant1 = Instant.now();
			
			Connection connection = null;
			PreparedStatement pstmt = null;

			try {

				connection = DataSourceConnection.getInstance().getConnection();
				connection.setAutoCommit(false);
				pstmt = connection.prepareStatement(INSERT_TABLE_QUERY);

				for (ElementState elementState : elementStates) {

					if (ObjectUtils.allNotNull(elementState.getElementTypeId(), elementState.getElementId(),
							elementState.getStateId(), elementState.getPreviousStateId())) {

						int index = 1;
						
						pstmt.setLong(index++, elementState.getElementTypeId());
						pstmt.setLong(index++, elementState.getElementId());
						pstmt.setLong(index++, elementState.getPreviousStateId());
						pstmt.setLong(index++, elementState.getStateId());
						
						if (elementState.getMaintenanceComment() == null) {
							
							pstmt.setNull(index++, Types.VARCHAR);
							
						} else{
							
							pstmt.setString(index++, elementState.getMaintenanceComment());
						}
						
						pstmt.setLong(index++, elementState.getElementTypeId());
						pstmt.setLong(index++, elementState.getElementId());
						
						pstmt.executeUpdate();

					} else {
						log.error("Not enought data to persist this ElementState");
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

			log.debug("Time to update " + elementStates.size() + " elementStates = "
					+ Duration.between(instant1, Instant.now()).toMillis() + " ms.");
		}
		return resp;

	}
	
private boolean deleteElementStates(List<ElementState> elementStates) {
		
		boolean resp = false;

		if (!CollectionUtils.isEmpty(elementStates)) {

			Instant instant1 = Instant.now();
			
			Connection connection = null;
			PreparedStatement pstmt = null;

			try {

				connection = DataSourceConnection.getInstance().getConnection();
				connection.setAutoCommit(false);
				pstmt = connection.prepareStatement(DELETE_TABLE_QUERY);

				for (ElementState elementState : elementStates) {

					if (ObjectUtils.allNotNull(elementState.getElementTypeId(), elementState.getElementId(),
							elementState.getStateId(), elementState.getPreviousStateId())) {

						int index = 1;
						
						pstmt.setLong(index++, elementState.getElementTypeId());
						pstmt.setLong(index++, elementState.getElementId());

						pstmt.executeUpdate();

					} else {
						log.error("Not enought data to persist this ElementState");
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

			log.debug("Time to update " + elementStates.size() + " elementStates = "
					+ Duration.between(instant1, Instant.now()).toMillis() + " ms.");
		}
		return resp;

	}
	
	

	public void updateElementStates(final List<ElementState> elementStates) {

		if (CollectionUtils.isNotEmpty(elementStates)) {
			
			List<ElementState> elementStatesToInsert = new ArrayList<>();
			List<ElementState> elementStatesToDelete = new ArrayList<>();
			
			for (ElementState elementState : elementStates) {
				
				ElementType elementType = elementState.getElement().getElementType();
				
				if (elementType != null) {
					
					if (elementState.getStateId().equals(elementType.getMaintenanceElementTypeStateId()) ){
						
						elementStatesToInsert.add(elementState);
						
					} else {
						
						elementStatesToDelete.add(elementState);
					}
				}

			}

			if (CollectionUtils.isNotEmpty(elementStatesToInsert)) {

				insertElementStates(elementStatesToInsert);
			}

			if (CollectionUtils.isNotEmpty(elementStatesToDelete)) {

				deleteElementStates(elementStatesToDelete);
			}
		}
	}
}
