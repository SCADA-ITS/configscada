package com.revenga.rits.back.entities.lib.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmConfigMeasure;
import com.revenga.rits.back.data.core.model.AlarmTargetElement;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementStateAlarm;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmConfigRepository extends EntityRepository
		implements EntityCacheable<AlarmConfig>, EntityPersistent<AlarmConfig> {

	// region Constructors
	public AlarmConfigRepository() {

		super(AlarmConfig.class);
	}
	
	public AlarmConfigRepository(EntityRepositoryOptions options) {

		super(AlarmConfig.class, options);
	}

	public AlarmConfigRepository(Boolean cacheable) {

		super(AlarmConfig.class, cacheable);
	}

	public AlarmConfigRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(AlarmConfig.class, cacheable, cacheRetentionTime);
	}

	public AlarmConfigRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(AlarmConfig.class, cacheable, repositories);
	}

	public AlarmConfigRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(AlarmConfig.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public AlarmConfig get(Long id, Boolean fillObject) {

		return (AlarmConfig) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	public List<AlarmConfig> getsByAlarmType(Long alarmType, Boolean fillObject) {

		  List<BasicElement> basicElements = getsFromCache(fillObject);

		    if (!CollectionUtils.isEmpty(basicElements)) {

		      List<AlarmConfig> alarmsConfig = basicElements.stream().map(be -> (AlarmConfig) be)
		          .collect(Collectors.toList());

		      return alarmsConfig.stream().filter(e -> e.getAlarmTypeId().equals(alarmType)).collect(Collectors.toList());
		    }

		    return Collections.emptyList();
	}

	public List<AlarmConfig> getsByAlarmLevel(Long alarmLevel, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

	    if (!CollectionUtils.isEmpty(basicElements)) {

	      List<AlarmConfig> alarmsConfig = basicElements.stream().map(be -> (AlarmConfig) be)
	          .collect(Collectors.toList());

	      return alarmsConfig.stream().filter(e -> e.getAlarmLevelId().equals(alarmLevel)).collect(Collectors.toList());
	    }

	    return Collections.emptyList();
	}

	public List<AlarmConfig> getsByElementType(Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

	    if (!CollectionUtils.isEmpty(basicElements)) {

	    	 List<AlarmConfig> alarmsConfig = basicElements.stream().map(be -> (AlarmConfig) be)
	   	          .collect(Collectors.toList());

	      return alarmsConfig.stream().filter(e -> e.getElementTypeId().equals(elementTypeId)).collect(Collectors.toList());
	    }

	    return Collections.emptyList();
	}

	@Override
	public List<AlarmConfig> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(AlarmConfig.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public AlarmConfig put(AlarmConfig entity) {

		return (AlarmConfig) putFromCache(entity);
	}

	@Override
	public List<AlarmConfig> put(List<AlarmConfig> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(AlarmConfig.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public AlarmConfig del(AlarmConfig entity) {

		return (AlarmConfig) delFromCache(entity);

	}

	@Override
	public List<AlarmConfig> del(List<AlarmConfig> entities) {

		List<BasicElement> basicElements = entities.stream().map(alarmConfig -> (BasicElement) alarmConfig)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(AlarmConfig.class::cast).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public AlarmConfig create(AlarmConfig alarmConfig) {

		try {

			return create(alarmConfig, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AlarmConfig create(AlarmConfig alarmConfig, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AlarmConfig alarmConfigResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert AlarmConfig in BBDD
			AlarmConfig alarmConfigInserted = null;

			alarmConfigInserted = (AlarmConfig) getObjectDao().insert(conn, alarmConfig);

			if (alarmConfigInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, alarmConfig);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, alarmConfigInserted));

			// Insert all AlarmConfigMeasure
			if (!CollectionUtils.isEmpty(alarmConfig.getAlarmConfigMeasures())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				AlarmConfigMeasureRepository entityRepository = (AlarmConfigMeasureRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(AlarmConfigMeasure.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					for (AlarmConfigMeasure alarmConfigMeasure : alarmConfig.getAlarmConfigMeasures()) {

						alarmConfigMeasure.setAlarmConfigId(alarmConfigInserted.getId());

						entityRepository.create(alarmConfigMeasure, conn, persistOperations);
					}

				}
			}
			
			// Insert all ElementStatesAlarm
			if (!CollectionUtils.isEmpty(alarmConfig.getAlarmConfigStates())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				ElementStateAlarmRepository entityRepository = (ElementStateAlarmRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ElementStateAlarm.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					for (ElementStateAlarm elementStateAlarm : alarmConfig.getAlarmConfigStates()) {

						elementStateAlarm.setAlarmConfigId(alarmConfigInserted.getId());

						entityRepository.create(elementStateAlarm, conn, persistOperations);
					}					
				}
			}

			// Insert all AlarmTargetElement
			if (!CollectionUtils.isEmpty(alarmConfig.getAlarmTargetElements())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				AlarmTargetElementRepository entityRepository = (AlarmTargetElementRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(AlarmTargetElement.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					for (AlarmTargetElement alarmTargetElement : alarmConfig.getAlarmTargetElements()) {

						alarmTargetElement.setAlarmConfigId(alarmConfigInserted.getId());
						entityRepository.create(alarmTargetElement, conn, persistOperations);
					}
				}

			}

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);

				alarmConfigResult = get(alarmConfigInserted.getId(), false);
			}

		} catch (EntityDBException | SQLException | IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

			if (closeConnection) {

				try {
					conn.rollback();

				} catch (SQLException e1) {

					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}
			}
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, alarmConfig);

		} finally {

			try {

				if (closeConnection) {

					conn.close();
				}

			} catch (SQLException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return alarmConfigResult;
	}

	@Override
	public AlarmConfig clone(AlarmConfig entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlarmConfig clone(AlarmConfig entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlarmConfig update(AlarmConfig alarmConfig) {
		try {

			return update(alarmConfig, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AlarmConfig update(AlarmConfig alarmConfig, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AlarmConfig alarmConfigResult = null;
		AlarmConfig alarmConfigAux = null;

		try {

			if (alarmConfig == null || alarmConfig.getId() == null) {

				throw new NullPointerException("alarmConfig is null");
			}

			alarmConfigAux = get(alarmConfig.getId(), false);

			if (alarmConfigAux == null) {

				throw new NullPointerException("alarmConfig not found");
				
			} else {

				alarmConfigAux = (AlarmConfig) alarmConfigAux.clone();
			}

			if (alarmConfig.getEnabled() != null)
				alarmConfigAux.setEnabled(alarmConfig.getEnabled());
			if (alarmConfig.getVisible() != null)
				alarmConfigAux.setVisible(alarmConfig.getVisible());
			if (alarmConfig.getAlias() != null)
				alarmConfigAux.setAlias(alarmConfig.getAlias());
			if (alarmConfig.getDescription() != null)
				alarmConfigAux.setDescription(alarmConfig.getDescription());
			if (alarmConfig.getLabelAlias() != null)
				alarmConfigAux.setLabelAlias(alarmConfig.getLabelAlias());
			if (alarmConfig.getLabelDescription() != null)
				alarmConfigAux.setLabelDescription(alarmConfig.getLabelDescription());
			if (alarmConfig.getAlarmTypeId() != null)
				alarmConfigAux.setAlarmTypeId(alarmConfig.getAlarmTypeId());
			if (alarmConfig.getAlarmLevelId() != null)
				alarmConfigAux.setAlarmLevelId(alarmConfig.getAlarmLevelId());
			if (alarmConfig.getElementTypeId() != null)
				alarmConfigAux.setElementTypeId(alarmConfig.getElementTypeId());

			alarmConfigAux.setElementTypeStateId(alarmConfig.getElementTypeStateId());
			alarmConfigAux.setElementSubtypeId(alarmConfig.getElementSubtypeId());

			if (alarmConfig.getElementId() != null)
				alarmConfigAux.setElementId(alarmConfig.getElementId());
			if (alarmConfig.getActiveCount() != null)
				alarmConfigAux.setActiveCount(alarmConfig.getActiveCount());
			if (alarmConfig.getMine() != null)
				alarmConfigAux.setMine(alarmConfig.getMine());
			if (alarmConfig.getTreatment() != null)
				alarmConfigAux.setTreatment(alarmConfig.getTreatment());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update AlarmConfigMeasures
			if (alarmConfig.getAlarmConfigMeasures() != null && !CollectionUtils.isEmpty(getRepositories())) {

				// Delete all AlarmConfigMeasure
				AlarmConfigMeasureRepository entityRepository = (AlarmConfigMeasureRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(AlarmConfigMeasure.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {
					List<AlarmConfigMeasure> alarmConfigMeasures = entityRepository
							.getsByAlarmConfig(alarmConfigAux.getId(), false);

					if (!CollectionUtils.isEmpty(alarmConfigMeasures)) {

						for (AlarmConfigMeasure alarmConfigMeasure : alarmConfigMeasures) {

							entityRepository.delete(alarmConfigMeasure, conn, persistOperations);
						}
					}

					// Insert all AlarmConfigMeasure
					if (!CollectionUtils.isEmpty(alarmConfig.getAlarmConfigMeasures())) {

						for (AlarmConfigMeasure alarmConfigMeasure : alarmConfig.getAlarmConfigMeasures()) {

							alarmConfigMeasure.setAlarmConfigId(alarmConfig.getId());
							entityRepository.create(alarmConfigMeasure, conn, persistOperations);
						}
					}
				}
			}
			
			// Update ElementStatesAlarm
			if (alarmConfig.getAlarmConfigStates() != null && !CollectionUtils.isEmpty(getRepositories())) {

				// Delete all ElementStatesAlarm
				ElementStateAlarmRepository entityRepository = (ElementStateAlarmRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ElementStateAlarm.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {
					List<ElementStateAlarm> elementStatesAlarm = entityRepository
							.getsByAlarmConfig(alarmConfigAux.getId(), false);

					if (!CollectionUtils.isEmpty(elementStatesAlarm)) {

						for (ElementStateAlarm elementStateAlarm : elementStatesAlarm) {

							entityRepository.delete(elementStateAlarm, conn, persistOperations);
						}
					}

					// Insert all ElementStatesAlarm
					if (!CollectionUtils.isEmpty(alarmConfig.getAlarmConfigStates())) {

						for (ElementStateAlarm elementStateAlarm : alarmConfig.getAlarmConfigStates()) {

							elementStateAlarm.setAlarmConfigId(alarmConfig.getId());
							entityRepository.create(elementStateAlarm, conn, persistOperations);
						}
					}
				}
			}

			// Update AlarmTargetElement
			if (alarmConfig.getAlarmTargetElements() != null && !CollectionUtils.isEmpty(getRepositories())) {

				// Delete all AlarmTargetElement

				AlarmTargetElementRepository entityRepository = (AlarmTargetElementRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(AlarmTargetElement.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					List<AlarmTargetElement> alarmTargetElements = entityRepository
							.getsByAlarmConfig(alarmConfigAux.getId(), false);

					if (!CollectionUtils.isEmpty(alarmTargetElements)) {

						for (AlarmTargetElement alarmTargetElement : alarmTargetElements) {

							entityRepository.delete(alarmTargetElement, conn, persistOperations);
						}
					}

					// Insert all AlarmTargetElement
					if (!CollectionUtils.isEmpty(alarmConfig.getAlarmTargetElements())) {

						for (AlarmTargetElement alarmTargetElement : alarmConfig.getAlarmTargetElements()) {

							alarmTargetElement.setAlarmConfigId(alarmConfig.getId());
							entityRepository.create(alarmTargetElement, conn, persistOperations);
						}
					}
				}
			}

			// Update AlarmConfig in BBDD
			AlarmConfig alarmConfigUpdated = null;

			alarmConfigUpdated = (AlarmConfig) getObjectDao().update(conn, alarmConfigAux);
			if (alarmConfigUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, alarmConfig);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, alarmConfigUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			alarmConfigResult = alarmConfigUpdated;

		} catch (CloneNotSupportedException | EntityDBException | SQLException | NullPointerException | IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

			if (closeConnection) {

				try {
					conn.rollback();

				} catch (SQLException e1) {

					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}
			}
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, alarmConfig);

		} finally {

			try {

				if (closeConnection) {

					conn.close();
				}

			} catch (SQLException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return alarmConfigResult;
	}

	@Override
	public AlarmConfig delete(AlarmConfig alarmConfig) {

		try {

			return delete(alarmConfig, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public AlarmConfig delete(AlarmConfig alarmConfig, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		AlarmConfig alarmConfigResult = null;
		AlarmConfig alarmConfigAux = null;
		List<AlarmConfigMeasure> alarmConfigMeasures = null;
		List<ElementStateAlarm> elementStatesAlarm = null;
		List<AlarmTargetElement> alarmTargetElements = null;

		try {

			if (alarmConfig == null || alarmConfig.getId() == null) {

				throw new NullPointerException("alarmConfig is null");
			}

			alarmConfigAux = get(alarmConfig.getId(), false);

			if (alarmConfigAux == null) {

				throw new NullPointerException("alarmConfig not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all AlarmConfigMeasure
			if (!CollectionUtils.isEmpty(getRepositories())) {

				AlarmConfigMeasureRepository entityRepository = (AlarmConfigMeasureRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(AlarmConfigMeasure.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					alarmConfigMeasures = entityRepository.getsByAlarmConfig(alarmConfigAux.getId(), false);

					if (!CollectionUtils.isEmpty(alarmConfigMeasures)) {

						for (AlarmConfigMeasure alarmConfigMeasure : alarmConfigMeasures) {

							entityRepository.delete(alarmConfigMeasure, conn, persistOperations);
						}
					}
				}
			}
			
			// Delete all AlarmConfigStates
			if (!CollectionUtils.isEmpty(getRepositories())) {

				ElementStateAlarmRepository entityRepository = (ElementStateAlarmRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ElementStateAlarm.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

				elementStatesAlarm = entityRepository.getsByAlarmConfig(alarmConfigAux.getId(), false);

				if (!CollectionUtils.isEmpty(elementStatesAlarm)) {

					for (ElementStateAlarm elementStateAlarm : elementStatesAlarm) {

							entityRepository.delete(elementStateAlarm, conn, persistOperations);
						}
					}
				}
			}

			// Delete all AlarmTargetElement
			if (!CollectionUtils.isEmpty(getRepositories())) {

				AlarmTargetElementRepository entityRepository = (AlarmTargetElementRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(AlarmTargetElement.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					alarmTargetElements = entityRepository.getsByAlarmConfig(alarmConfigAux.getId(), false);

					if (!CollectionUtils.isEmpty(alarmTargetElements)) {

						for (AlarmTargetElement alarmTargetElement : alarmTargetElements) {

							entityRepository.delete(alarmTargetElement, conn, persistOperations);
						}
					}
				}
			}

			// Delete AlarmConfig in BBDD
			AlarmConfig alarmConfigDeleted = null;

			alarmConfigDeleted = (AlarmConfig) getObjectDao().delete(conn, alarmConfigAux);

			if (alarmConfigDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, alarmConfigAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, alarmConfigAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			alarmConfigResult = alarmConfigDeleted;

		} catch (EntityDBException | EntityPersistenceException | SQLException | NullPointerException | IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

			if (closeConnection) {

				try {
					conn.rollback();

				} catch (SQLException e1) {

					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}
			}
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, alarmConfig);

		} finally {

			try {

				if (closeConnection) {

					conn.close();
				}

			} catch (SQLException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return alarmConfigResult;
	}

}
