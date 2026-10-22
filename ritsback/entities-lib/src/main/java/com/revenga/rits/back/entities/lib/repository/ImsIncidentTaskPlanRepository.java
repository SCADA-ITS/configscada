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
import com.revenga.rits.back.data.core.model.ImsIncidentTaskPlan;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsIncidentTaskPlanRepository extends EntityRepository
		implements EntityCacheable<ImsIncidentTaskPlan>, EntityPersistent<ImsIncidentTaskPlan> {

	// region Constructors
	public ImsIncidentTaskPlanRepository() {

		super(ImsIncidentTaskPlan.class);
	}

	public ImsIncidentTaskPlanRepository(Boolean cacheable) {

		super(ImsIncidentTaskPlan.class, cacheable);
	}

	public ImsIncidentTaskPlanRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsIncidentTaskPlan.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters
	public ImsIncidentTaskPlan get(Long id, Boolean fillObject) {

		return (ImsIncidentTaskPlan) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsIncidentTaskPlan> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ImsIncidentTaskPlan.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsIncidentTaskPlan> gets(Long stretchId, Long incidentTypeId, Long incidentTypeTaskId,
			Boolean fillObject) {

		if (ObjectUtils.allNotNull(stretchId, incidentTypeId, incidentTypeTaskId)) {

			List<BasicElement> basicElements = getsFromCache(fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				List<ImsIncidentTaskPlan> incidentTaskPlans = basicElements.stream().map(ImsIncidentTaskPlan.class::cast)
						.collect(Collectors.toList());

				return incidentTaskPlans.stream()
						.filter(e -> stretchId.equals(e.getStretchId()) && incidentTypeId.equals(e.getIncidentTypeId())
								&& incidentTypeTaskId.equals(e.getIncidentTypeTaskId()))
						.collect(Collectors.toList());
			}
		}

		return Collections.emptyList();
	}
	
	public List<ImsIncidentTaskPlan> getsByTypeTask(Long incidentTypeTaskId,
			Boolean fillObject) {

		if (ObjectUtils.allNotNull(incidentTypeTaskId)) {

			List<BasicElement> basicElements = getsFromCache(fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				List<ImsIncidentTaskPlan> incidentTaskPlans = basicElements.stream().map(ImsIncidentTaskPlan.class::cast)
						.collect(Collectors.toList());

				return incidentTaskPlans.stream()
						.filter(e -> incidentTypeTaskId.equals(e.getIncidentTypeTaskId()))
						.collect(Collectors.toList());
			}
		}

		return Collections.emptyList();
	}
	
	public List<ImsIncidentTaskPlan> getsByStretch(Long stretchId,
			Boolean fillObject) {

		if (ObjectUtils.allNotNull(stretchId)) {

			List<BasicElement> basicElements = getsFromCache(fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				List<ImsIncidentTaskPlan> incidentTaskPlans = basicElements.stream().map(ImsIncidentTaskPlan.class::cast)
						.collect(Collectors.toList());

				return incidentTaskPlans.stream()
						.filter(e -> stretchId.equals(e.getStretchId()))
						.collect(Collectors.toList());
			}
		}

		return Collections.emptyList();
	}
	
	public List<ImsIncidentTaskPlan> getsByIncidentTypeStretch(Long incidentTypeId, Long stretchId,
			Boolean fillObject) {

		if (ObjectUtils.allNotNull(stretchId, incidentTypeId)) {

			List<BasicElement> basicElements = getsFromCache(fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				List<ImsIncidentTaskPlan> incidentTaskPlans = basicElements.stream().map(ImsIncidentTaskPlan.class::cast)
						.collect(Collectors.toList());

				return incidentTaskPlans.stream()
						.filter(e -> stretchId.equals(e.getStretchId()) 
								&& incidentTypeId.equals(e.getIncidentTypeId()))
						.collect(Collectors.toList());
			}
		}

		return Collections.emptyList();
	}

	// endregion getters

	// region puts
	@Override
	public ImsIncidentTaskPlan put(ImsIncidentTaskPlan entity) {

		return (ImsIncidentTaskPlan) putFromCache(entity);
	}

	@Override
	public List<ImsIncidentTaskPlan> put(List<ImsIncidentTaskPlan> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentTaskPlan.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ImsIncidentTaskPlan del(ImsIncidentTaskPlan entity) {

		return (ImsIncidentTaskPlan) delFromCache(entity);

	}

	@Override
	public List<ImsIncidentTaskPlan> del(List<ImsIncidentTaskPlan> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ImsIncidentTaskPlan.class::cast).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ImsIncidentTaskPlan create(ImsIncidentTaskPlan imsIncidentTaskPlan) {

		try {

			return create(imsIncidentTaskPlan, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTaskPlan create(ImsIncidentTaskPlan imsIncidentTaskPlan, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTaskPlan imsIncidentTaskPlanResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsIncidentTaskPlan in BBDD
			ImsIncidentTaskPlan imsIncidentTaskPlanInserted = null;

			imsIncidentTaskPlanInserted = (ImsIncidentTaskPlan) getObjectDao().insert(conn, imsIncidentTaskPlan);

			if (imsIncidentTaskPlanInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsIncidentTaskPlan);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentTaskPlanInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				imsIncidentTaskPlanResult = get(imsIncidentTaskPlanInserted.getId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, imsIncidentTaskPlan);

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

		return imsIncidentTaskPlanResult;
	}

	@Override
	public ImsIncidentTaskPlan clone(ImsIncidentTaskPlan entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTaskPlan clone(ImsIncidentTaskPlan entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsIncidentTaskPlan update(ImsIncidentTaskPlan imsIncidentTaskPlan) {

		try {

			return update(imsIncidentTaskPlan, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTaskPlan update(ImsIncidentTaskPlan imsIncidentTaskPlan, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTaskPlan imsIncidentTaskPlanResult = null;
		ImsIncidentTaskPlan imsIncidentTaskPlanAux = null;

		try {

			if (imsIncidentTaskPlan == null || imsIncidentTaskPlan.getId() == null) {

				throw new NullPointerException("imsIncidentTaskPlan is null");
			}

			imsIncidentTaskPlanAux = get(imsIncidentTaskPlan.getId(), false);

			if (imsIncidentTaskPlanAux == null) {

				throw new NullPointerException("imsIncidentTaskPlan not found");
			} else {

				imsIncidentTaskPlanAux = (ImsIncidentTaskPlan) imsIncidentTaskPlanAux.clone();
			}

			if (imsIncidentTaskPlan.getAlias() != null)
				imsIncidentTaskPlanAux.setAlias(imsIncidentTaskPlan.getAlias());
			if (imsIncidentTaskPlan.getDescription() != null)
				imsIncidentTaskPlanAux.setDescription(imsIncidentTaskPlan.getDescription());
			if (imsIncidentTaskPlan.getIncidentTypeTaskId() != null)
				imsIncidentTaskPlanAux.setIncidentTypeTaskId(imsIncidentTaskPlan.getIncidentTypeTaskId());
			if (imsIncidentTaskPlan.getIncidentTypeId() != null)
				imsIncidentTaskPlanAux.setIncidentTypeId(imsIncidentTaskPlan.getIncidentTypeId());
			if (imsIncidentTaskPlan.getStretchId() != null)
				imsIncidentTaskPlanAux.setStretchId(imsIncidentTaskPlan.getStretchId());
			if (imsIncidentTaskPlan.getPlanId() != null)
				imsIncidentTaskPlanAux.setPlanId(imsIncidentTaskPlan.getPlanId());
			if (imsIncidentTaskPlan.getPosition() != null)
				imsIncidentTaskPlanAux.setPosition(imsIncidentTaskPlan.getPosition());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsIncidentTaskPlan in BBDD
			ImsIncidentTaskPlan imsIncidentTaskPlanUpdated = null;

			imsIncidentTaskPlanUpdated = (ImsIncidentTaskPlan) getObjectDao().update(conn, imsIncidentTaskPlanAux);

			if (imsIncidentTaskPlanUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsIncidentTaskPlanAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsIncidentTaskPlanUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsIncidentTaskPlanResult = imsIncidentTaskPlanUpdated;

		} catch (EntityDBException | SQLException | NullPointerException | IOException | CloneNotSupportedException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, imsIncidentTaskPlan);

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

		return imsIncidentTaskPlanResult;
	}

	@Override
	public ImsIncidentTaskPlan delete(ImsIncidentTaskPlan imsIncidentTaskPlan) {

		try {

			return delete(imsIncidentTaskPlan, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsIncidentTaskPlan delete(ImsIncidentTaskPlan imsIncidentTaskPlan, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsIncidentTaskPlan imsIncidentAlarmConditionAlarmResult = null;
		ImsIncidentTaskPlan imsIncidentAlarmConditionAlarmAux = null;

		try {

			if (imsIncidentTaskPlan == null || imsIncidentTaskPlan.getId() == null) {

				throw new NullPointerException("imsIncidentTaskPlan is null");
			}

			imsIncidentAlarmConditionAlarmAux = get(imsIncidentTaskPlan.getId(), false);

			if (imsIncidentAlarmConditionAlarmAux == null) {

				throw new NullPointerException("imsIncidentTaskPlan not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete imsIncidentTaskPlan in BBDD
			ImsIncidentTaskPlan imsIncidentTaskPlanDeleted = null;

			imsIncidentTaskPlanDeleted = (ImsIncidentTaskPlan) getObjectDao().delete(conn,
					imsIncidentAlarmConditionAlarmAux);

			if (imsIncidentTaskPlanDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsIncidentAlarmConditionAlarmAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsIncidentAlarmConditionAlarmAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsIncidentAlarmConditionAlarmResult = imsIncidentTaskPlanDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, imsIncidentTaskPlan);

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

		return imsIncidentAlarmConditionAlarmResult;
	}
}
