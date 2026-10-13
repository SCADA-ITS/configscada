package com.revenga.rits.back.entities.lib.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentReportLog;
import com.revenga.rits.back.data.core.model.ImsIncidentReportParamValue;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskElement;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskPlanState;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskState;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentReportRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentReport>, EntityPersistent<ImsIncidentReport> {

	// region Constructors
	/**
	 * 
	 */
	public ImsIncidentReportRepository() {

		super(ImsIncidentReport.class);
	}

	/**
	 * 
	 * @param cacheable
	 */
	public ImsIncidentReportRepository(Boolean cacheable) {

		super(ImsIncidentReport.class, cacheable);
	}

	/**
	 * 
	 * @param cacheable
	 * @param cacheRetentionTime
	 */
	public ImsIncidentReportRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentReport.class, cacheable, cacheRetentionTime);
	}

	/**
	 * 
	 * @param cacheable
	 * @param repositories
	 */
	public ImsIncidentReportRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(ImsIncidentReport.class, cacheable, repositories);
	}

	/**
	 * 
	 * @param cacheable
	 * @param cacheRetentionTime
	 * @param repositories
	 */
	public ImsIncidentReportRepository(Boolean cacheable, Long cacheRetentionTime,
			List<EntityRepository> repositories) {

		super(ImsIncidentReport.class, cacheable, cacheRetentionTime, repositories);
	}
	// endregion Constructors

	// region getters
	/**
	 * 
	 * @param id
	 * @param fillObject
	 * @return
	 */
	public ImsIncidentReport get(Long id, Boolean fillObject) {

		return (ImsIncidentReport) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentReport> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentReport.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	/**
	 * 
	 * @param affectionStretchId
	 * @param fillObject
	 * @return
	 */
	public List<ImsIncidentReport> getByAffectionStretch(Long affectionStretchId, Boolean fillObject) {

		if (affectionStretchId != null) {
			List<BasicElement> basicElements = getsFromCache(fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				List<ImsIncidentReport> incidentReports = basicElements.stream().map(ImsIncidentReport.class::cast)
						.collect(Collectors.toList());
				return incidentReports.stream().filter(be -> affectionStretchId.equals(be.getAffectionStretchId()))
						.collect(Collectors.toList());
			}
		}
		return Collections.emptyList();
	}

	/**
	 * 
	 * @param incidentTypeId
	 * @param fillObject
	 * @return
	 */
	public List<ImsIncidentReport> getByIncidentType(Long incidentTypeId, Boolean fillObject) {

		if (incidentTypeId != null) {
			List<BasicElement> basicElements = getsFromCache(fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				List<ImsIncidentReport> incidentReports = basicElements.stream().map(ImsIncidentReport.class::cast)
						.collect(Collectors.toList());
				return incidentReports.stream().filter(be -> incidentTypeId.equals(be.getIncidentTypeId()))
						.collect(Collectors.toList());
			}
		}
		return Collections.emptyList();
	}

	/**
	 * 
	 * @param incidentTypeCauseId
	 * @param fillObject
	 * @return
	 */
	public List<ImsIncidentReport> getIncidentReportsByCause(Long incidentTypeId, Long incidentTypeCauseId,
			Boolean fillObject) {

		if (incidentTypeId != null && incidentTypeCauseId != null) {
			List<BasicElement> basicElements = getsFromCache(fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				List<ImsIncidentReport> incidentReports = basicElements.stream().map(ImsIncidentReport.class::cast)
						.collect(Collectors.toList());
				if (incidentReports != null) {
					return incidentReports.stream()
							.filter(be -> (incidentTypeId.equals(be.getIncidentTypeId())
									&& incidentTypeCauseId.equals(be.getIncidentTypeCauseId())))
							.collect(Collectors.toList());
				}
			}
		}
		return Collections.emptyList();
	}

	/**
	 * 
	 * @param incidentTypeId
	 * @param stretchId
	 * @param fillObject
	 * @return
	 */
	public List<ImsIncidentReport> getByIncidentTypeStretch(Long incidentTypeId, Long stretchId, Boolean fillObject) {

		if (ObjectUtils.allNotNull(incidentTypeId, stretchId)) {

			List<BasicElement> basicElements = getsFromCache(fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				List<ImsIncidentReport> incidentReports = basicElements.stream().map(ImsIncidentReport.class::cast)
						.collect(Collectors.toList());

				return incidentReports.stream().filter(be -> incidentTypeId.equals(be.getIncidentTypeId())
						&& stretchId.equals(be.getAffectionStretchId())).collect(Collectors.toList());
			}
		}
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	/**
	 * 
	 */
	@Override
	public ImsIncidentReport put(ImsIncidentReport entity) {

		return (ImsIncidentReport) putFromCache(entity);
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentReport> put(List<ImsIncidentReport> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentReport.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	/**
	 * 
	 */
	@Override
	public ImsIncidentReport del(ImsIncidentReport entity) {

		return (ImsIncidentReport) delFromCache(entity);

	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentReport> del(List<ImsIncidentReport> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentReport.class::cast).collect(Collectors.toList());
	}
	// endregion dels

	/**
	 * 
	 */
	@Override
	public ImsIncidentReport create(ImsIncidentReport imsIncidentReport) {

		try {

			return create(imsIncidentReport, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentReport create(ImsIncidentReport imsIncidentReport, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReport imsIncidentReportResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentReport in BBDD
			ImsIncidentReport imsIncidentReportInserted = (ImsIncidentReport) imsIncidentReport.clone();

			imsIncidentReportInserted = (ImsIncidentReport) getObjectDao().insert(conn, imsIncidentReportInserted);

			if (imsIncidentReportInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentReport);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentReportInserted));

			if (!CollectionUtils.isEmpty(getRepositories())) {

				// ImsIncidentReportTaskState
				if (!CollectionUtils.isEmpty(imsIncidentReport.getIncidentReportTaskStates())) {

					ImsIncidentReportTaskStateRepository entityRepository = (ImsIncidentReportTaskStateRepository) getRepository(
							ImsIncidentReportTaskState.class);

					if (entityRepository != null) {

						for (ImsIncidentReportTaskState incidentReportTaskState : imsIncidentReport
								.getIncidentReportTaskStates()) {

							incidentReportTaskState.setIncidentReportId(imsIncidentReportInserted.getId());
							entityRepository.create(incidentReportTaskState, conn, persistOperations);
						}
					}
				}

				// ImsIncidentReportTaskElements
				if (!CollectionUtils.isEmpty(imsIncidentReport.getIncidentReportTaskElements())) {

					ImsIncidentReportTaskElementRepository entityRepository = (ImsIncidentReportTaskElementRepository) getRepository(
							ImsIncidentReportTaskElement.class);

					if (entityRepository != null) {

						for (ImsIncidentReportTaskElement incidentReportTaskElement : imsIncidentReport
								.getIncidentReportTaskElements()) {

							incidentReportTaskElement.setIncidentReportId(imsIncidentReportInserted.getId());
							entityRepository.create(incidentReportTaskElement, conn, persistOperations);
						}
					}
				}

				// ImsIncidentReportTaskPlanStates
				if (!CollectionUtils.isEmpty(imsIncidentReport.getIncidentReportTaskPlanStates())) {

					ImsIncidentReportTaskPlanStateRepository entityRepository = (ImsIncidentReportTaskPlanStateRepository) getRepository(
							ImsIncidentReportTaskPlanState.class);

					if (entityRepository != null) {

						for (ImsIncidentReportTaskPlanState incidentReportTaskPlanState : imsIncidentReport
								.getIncidentReportTaskPlanStates()) {

							incidentReportTaskPlanState.setIncidentReportId(imsIncidentReportInserted.getId());
							entityRepository.create(incidentReportTaskPlanState, conn, persistOperations);
						}
					}
				}

				// ImsIncidentReportAlarm
				if (!CollectionUtils.isEmpty(imsIncidentReport.getIncidentReportAlarms())) {

					ImsIncidentReportAlarmRepository entityRepository = (ImsIncidentReportAlarmRepository) getRepository(
							ImsIncidentReportAlarm.class);

					if (entityRepository != null) {

						for (ImsIncidentReportAlarm incidentReportAlarm : imsIncidentReport.getIncidentReportAlarms()) {

							incidentReportAlarm.setIncidentReportId(imsIncidentReportInserted.getId());
							entityRepository.create(incidentReportAlarm, conn, persistOperations);
						}
					}
				}

			}

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);

				imsIncidentReportResult = get(imsIncidentReportInserted.getId(), false);
			}

		} catch (EntityDBException | SQLException | IOException | CloneNotSupportedException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, imsIncidentReport);

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

		return imsIncidentReportResult;
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentReport clone(ImsIncidentReport entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentReport clone(ImsIncidentReport entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentReport update(ImsIncidentReport imsIncidentReport) {

		try {

			return update(imsIncidentReport, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentReport update(ImsIncidentReport incidentReport, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReport result = null;
		ImsIncidentReport incidentReportAux = null;

		try {

			if (incidentReport == null || incidentReport.getId() == null) {

				throw new NullPointerException("imsIncidentReport is null");
			}

			incidentReportAux = get(incidentReport.getId(), false);

			if (incidentReportAux == null) {

				throw new NullPointerException("imsIncidentReport not found");
			}

			if (incidentReport.getIncidentTypeId() == null)
				incidentReport.setIncidentTypeId(incidentReportAux.getIncidentTypeId());
			if (incidentReport.getAffectionStretchId() == null)
				incidentReport.setAffectionStretchId(incidentReportAux.getAffectionStretchId());
			if (incidentReport.getIncidentTypeCauseId() == null)
				incidentReport.setIncidentTypeCauseId(incidentReportAux.getIncidentTypeCauseId());
			if (incidentReport.getLocationId() == null)
				incidentReport.setLocationId(incidentReportAux.getLocationId());
			if (incidentReport.getIncidentStateId() == null)
				incidentReport.setIncidentStateId(incidentReportAux.getIncidentStateId());
			if (incidentReport.getIncidentLevelId() == null)
				incidentReport.setIncidentLevelId(incidentReportAux.getIncidentLevelId());
			if (incidentReport.getCurrentUserId() == null)
				incidentReport.setCurrentUserId(incidentReportAux.getCurrentUserId());
			if (incidentReport.getValidForMin() == null)
				incidentReport.setValidForMin(incidentReportAux.getValidForMin());
			if (incidentReport.getConfirm() == null)
				incidentReport.setConfirm(incidentReportAux.getConfirm());

			incidentReport.setGeneratedAt(incidentReportAux.getGeneratedAt());

			if (incidentReport.getLastUpdate() == null)
				incidentReport.setLastUpdate(incidentReportAux.getLastUpdate());

			if (incidentReport.getLastUpdateState() == null)
				incidentReport.setLastUpdateState(incidentReportAux.getLastUpdateState());

			if (incidentReport.getBackward() == null)
				incidentReport.setBackward(incidentReportAux.getBackward());

			if (incidentReport.getRoadImpact() == null) {

				incidentReport.setRoadImpactId(incidentReportAux.getRoadImpactId());

			} else {
				incidentReport.setRoadImpactId(incidentReport.getRoadImpact().getId());
				incidentReport.setRoadImpact(null);
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsIncidentReport in BBDD
			ImsIncidentReport updated = null;

			updated = (ImsIncidentReport) getObjectDao().update(conn, incidentReport);

			if (updated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, updated);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, updated));

			if (!CollectionUtils.isEmpty(getRepositories())) {

				// Delete incidentReportTaskElements
				if (incidentReport.getIncidentReportTaskElements() != null) {

					ImsIncidentReportTaskElementRepository entityRepository = (ImsIncidentReportTaskElementRepository) getRepository(
							ImsIncidentReportTaskElement.class);

					if (entityRepository != null) {

						List<ImsIncidentReportTaskElement> incidentReportTaskElements = entityRepository
								.getsByIncidentReport(incidentReport.getId(), false);

						if (!CollectionUtils.isEmpty(incidentReportTaskElements)) {

							for (ImsIncidentReportTaskElement incidentReportTaskElement : incidentReportTaskElements) {

								entityRepository.delete(incidentReportTaskElement, conn, persistOperations);
							}
						}
					}
				}

				// Update incidentReportTaskState
				if (incidentReport.getIncidentReportTaskStates() != null) {

					// Delete all IncidentReportTaskValues
					{
						ImsIncidentReportTaskValueRepository entityRepository = (ImsIncidentReportTaskValueRepository) getRepository(
								ImsIncidentReportTaskValue.class);

						if (entityRepository != null) {

							List<ImsIncidentReportTaskValue> incidentReportTaskValues = entityRepository
									.getsByIncidentReport(incidentReport.getId(), false);

							if (!CollectionUtils.isEmpty(incidentReportTaskValues)) {

								for (ImsIncidentReportTaskValue incidentReportTaskValue : incidentReportTaskValues) {

									entityRepository.delete(incidentReportTaskValue, conn, persistOperations);
								}
							}
						}
					}
					{
						ImsIncidentReportTaskStateRepository entityRepository = (ImsIncidentReportTaskStateRepository) getRepository(
								ImsIncidentReportTaskState.class);

						if (entityRepository != null) {

							List<ImsIncidentReportTaskState> incidentReportTaskStates = entityRepository
									.getByIncidentReport(incidentReport.getId(), false);

							if (!CollectionUtils.isEmpty(incidentReportTaskStates)) {

								// Delete all incidentReportTaskState
								for (ImsIncidentReportTaskState ImsIncidentReportTaskState : incidentReportTaskStates) {

									entityRepository.delete(ImsIncidentReportTaskState, conn, persistOperations);
								}
							}

							// Create all incidentReportTaskState
							for (ImsIncidentReportTaskState incidentReportTaskState : incidentReport
									.getIncidentReportTaskStates()) {

								incidentReportTaskState.setIncidentReportId(incidentReport.getId());
								entityRepository.create(incidentReportTaskState, conn, persistOperations);
							}
						}
					}
				}

				// Create all incidentReportTaskPlanStates
				if (!CollectionUtils.isEmpty(incidentReport.getIncidentReportTaskElements())) {

					ImsIncidentReportTaskElementRepository entityRepository = (ImsIncidentReportTaskElementRepository) getRepository(
							ImsIncidentReportTaskElement.class);

					if (entityRepository != null) {

						for (ImsIncidentReportTaskElement incidentReportTaskElement : incidentReport
								.getIncidentReportTaskElements()) {

							incidentReportTaskElement.setIncidentReportId(incidentReport.getId());
							entityRepository.create(incidentReportTaskElement, conn, persistOperations);
						}
					}
				}

				// Update incidentReportTaskPlanStates
				if (incidentReport.getIncidentReportTaskPlanStates() != null) {

					ImsIncidentReportTaskPlanStateRepository entityRepository = (ImsIncidentReportTaskPlanStateRepository) getRepository(
							ImsIncidentReportTaskPlanState.class);

					if (entityRepository != null) {

						List<ImsIncidentReportTaskPlanState> incidentReportTaskPlanStates = entityRepository
								.getByIncidentReport(incidentReport.getId(), false);

						// Delete all incidentReportTaskPlanStates
						if (!CollectionUtils.isEmpty(incidentReportTaskPlanStates)) {

							for (ImsIncidentReportTaskPlanState ImsIncidentReportTaskPlanState : incidentReportTaskPlanStates) {

								entityRepository.delete(ImsIncidentReportTaskPlanState, conn, persistOperations);
							}
						}

						// Create all incidentReportTaskPlanStates
						for (ImsIncidentReportTaskPlanState incidentReportTaskPlanState : incidentReport
								.getIncidentReportTaskPlanStates()) {

							incidentReportTaskPlanState.setIncidentReportId(incidentReport.getId());
							entityRepository.create(incidentReportTaskPlanState, conn, persistOperations);
						}
					}
				}
			}

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			result = updated;

		} catch (EntityDBException | SQLException | NullPointerException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, incidentReport);

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

		return result;
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentReport delete(ImsIncidentReport imsIncidentReport) {

		try {

			return delete(imsIncidentReport, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentReport delete(ImsIncidentReport imsIncidentReport, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentReport imsIncidentReportResult = null;
		ImsIncidentReport imsIncidentReportAux = null;

		try {

			if (imsIncidentReport == null || imsIncidentReport.getId() == null) {

				throw new NullPointerException("ImsIncidentReport is null");
			}

			imsIncidentReportAux = get(imsIncidentReport.getId(), false);

			if (imsIncidentReportAux == null) {

				throw new NullPointerException("ImsIncidentReport not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			if (!CollectionUtils.isEmpty(getRepositories())) {

				// Delete all IncidentReportTaskPlanStates
				{
					ImsIncidentReportTaskPlanStateRepository entityRepository = (ImsIncidentReportTaskPlanStateRepository) getRepository(
							ImsIncidentReportTaskPlanState.class);

					if (entityRepository != null) {

						List<ImsIncidentReportTaskPlanState> incidentReportTaskPlanStates = entityRepository
								.getByIncidentReport(imsIncidentReport.getId(), false);

						if (!CollectionUtils.isEmpty(incidentReportTaskPlanStates)) {

							for (ImsIncidentReportTaskPlanState ImsIncidentReportTaskPlanState : incidentReportTaskPlanStates) {

								entityRepository.delete(ImsIncidentReportTaskPlanState, conn, persistOperations);
							}
						}
					}
				}

				// Delete all IncidentReportTaskValues
				{
					ImsIncidentReportTaskValueRepository entityRepository = (ImsIncidentReportTaskValueRepository) getRepository(
							ImsIncidentReportTaskValue.class);

					if (entityRepository != null) {

						List<ImsIncidentReportTaskValue> incidentReportTaskValues = entityRepository
								.getsByIncidentReport(imsIncidentReport.getId(), false);

						if (!CollectionUtils.isEmpty(incidentReportTaskValues)) {

							for (ImsIncidentReportTaskValue incidentReportTaskValue : incidentReportTaskValues) {

								entityRepository.delete(incidentReportTaskValue, conn, persistOperations);
							}
						}
					}
				}

				// Delete all incidentReportTaskElements
				{
					ImsIncidentReportTaskElementRepository entityRepository = (ImsIncidentReportTaskElementRepository) getRepository(
							ImsIncidentReportTaskElement.class);

					if (entityRepository != null) {

						List<ImsIncidentReportTaskElement> incidentReportTaskElements = entityRepository
								.getsByIncidentReport(imsIncidentReport.getId(), false);

						if (!CollectionUtils.isEmpty(incidentReportTaskElements)) {

							for (ImsIncidentReportTaskElement incidentReportTaskElement : incidentReportTaskElements) {

								entityRepository.delete(incidentReportTaskElement, conn, persistOperations);
							}
						}
					}
				}

				// Delete all incidentReportTaskStateRepository
				{
					ImsIncidentReportTaskStateRepository entityRepository = (ImsIncidentReportTaskStateRepository) getRepository(
							ImsIncidentReportTaskState.class);

					if (entityRepository != null) {

						List<ImsIncidentReportTaskState> incidentReportTaskStates = entityRepository
								.getByIncidentReport(imsIncidentReport.getId(), false);

						if (!CollectionUtils.isEmpty(incidentReportTaskStates)) {

							for (ImsIncidentReportTaskState ImsIncidentReportTaskState : incidentReportTaskStates) {

								entityRepository.delete(ImsIncidentReportTaskState, conn, persistOperations);
							}
						}
					}
				}

				// Delete all incidentReportAlarms
				{
					ImsIncidentReportAlarmRepository entityRepository = (ImsIncidentReportAlarmRepository) getRepository(
							ImsIncidentReportAlarm.class);

					if (entityRepository != null) {

						List<ImsIncidentReportAlarm> incidentReportAlarms = entityRepository
								.getsByIncidentReport(imsIncidentReport.getId(), false);

						if (!CollectionUtils.isEmpty(incidentReportAlarms)) {

							for (ImsIncidentReportAlarm incidentReportAlarm : incidentReportAlarms) {

								entityRepository.delete(incidentReportAlarm, conn, persistOperations);
							}
						}
					}
				}

				// Delete all incidentReportLog
				{
					ImsIncidentReportLogRepository entityRepository = (ImsIncidentReportLogRepository) getRepository(
							ImsIncidentReportLog.class);

					if (entityRepository != null) {

						List<ImsIncidentReportLog> incidentReportLogs = entityRepository
								.getsByIncidentReport(imsIncidentReport.getId(), false);

						if (!CollectionUtils.isEmpty(incidentReportLogs)) {

							for (ImsIncidentReportLog incidentReportLog : incidentReportLogs) {

								entityRepository.delete(incidentReportLog, conn, persistOperations);
							}
						}
					}
				}

				// delete all incident param values
				{
					ImsIncidentReportParamValueRepository entityRepository = (ImsIncidentReportParamValueRepository) getRepository(
							ImsIncidentReportParamValue.class);

					if (entityRepository != null) {

						List<ImsIncidentReportParamValue> incidentParamValues = entityRepository
								.getsByIncidentReport(imsIncidentReport.getId(), false);

						if (!CollectionUtils.isEmpty(incidentParamValues)) {

							for (ImsIncidentReportParamValue incidentParamValue : incidentParamValues) {

								entityRepository.delete(incidentParamValue, conn, persistOperations);
							}
						}
					}
				}
			}

			// Delete ImsIncidentReport in BBDD
			ImsIncidentReport imsIncidentReportDeleted = null;

			imsIncidentReportDeleted = (ImsIncidentReport) getObjectDao().delete(conn, imsIncidentReportAux);

			if (imsIncidentReportDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentReportAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentReportAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentReportResult = imsIncidentReportDeleted;

		} catch (EntityDBException | SQLException | NullPointerException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, imsIncidentReport);

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

		return imsIncidentReportResult;
	}

	public ImsIncidentReport addIncidentReportTaskState(ImsIncidentReport incidentReport) {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		ImsIncidentReport result = null;

		try {

			conn = DataSourceConnection.getInstance().getConnection();
			conn.setAutoCommit(false);
			closeConnection = true;
			commit = true;
			
			List<Pair<PersistOperation, BasicElement>> persistOperations = new ArrayList<>();

			if (incidentReport != null && !CollectionUtils.isEmpty(getRepositories())) {

				// ImsIncidentReportTaskState
				if (!CollectionUtils.isEmpty(incidentReport.getIncidentReportTaskStates())) {

					ImsIncidentReportTaskStateRepository entityRepository = (ImsIncidentReportTaskStateRepository) getRepository(
							ImsIncidentReportTaskState.class);

					if (entityRepository != null) {

						for (ImsIncidentReportTaskState incidentReportTaskState : incidentReport
								.getIncidentReportTaskStates()) {

							incidentReportTaskState.setIncidentReportId(incidentReport.getId());
							entityRepository.create(incidentReportTaskState, conn, persistOperations);
						}
					}
				}

				// ImsIncidentReportTaskElements
				if (!CollectionUtils.isEmpty(incidentReport.getIncidentReportTaskElements())) {

					ImsIncidentReportTaskElementRepository entityRepository = (ImsIncidentReportTaskElementRepository) getRepository(
							ImsIncidentReportTaskElement.class);

					if (entityRepository != null) {

						for (ImsIncidentReportTaskElement incidentReportTaskElement : incidentReport
								.getIncidentReportTaskElements()) {

							incidentReportTaskElement.setIncidentReportId(incidentReport.getId());
							entityRepository.create(incidentReportTaskElement, conn, persistOperations);
						}
					}
				}

				// ImsIncidentReportTaskPlanStates
				if (!CollectionUtils.isEmpty(incidentReport.getIncidentReportTaskPlanStates())) {

					ImsIncidentReportTaskPlanStateRepository entityRepository = (ImsIncidentReportTaskPlanStateRepository) getRepository(
							ImsIncidentReportTaskPlanState.class);

					if (entityRepository != null) {

						for (ImsIncidentReportTaskPlanState incidentReportTaskPlanState : incidentReport
								.getIncidentReportTaskPlanStates()) {

							incidentReportTaskPlanState.setIncidentReportId(incidentReport.getId());
							entityRepository.create(incidentReportTaskPlanState, conn, persistOperations);
						}
					}
				}

			}

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);

				result = get(incidentReport.getId(), false);
			}

		} catch (EntityPersistenceException | SQLException | IOException e) {

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

		return result;
	}
}
