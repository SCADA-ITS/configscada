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
import com.revenga.rits.back.data.core.model.ImsExtIncidentValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsExtIncidentValueRepository extends EntityRepository
		implements EntityCacheable<ImsExtIncidentValue>, EntityPersistent<ImsExtIncidentValue> {

	// region Constructors
	public ImsExtIncidentValueRepository() {

		super(ImsExtIncidentValue.class);
	}

	public ImsExtIncidentValueRepository(Boolean cacheable) {

		super(ImsExtIncidentValue.class, cacheable);
	}

	public ImsExtIncidentValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsExtIncidentValue.class, cacheable, cacheRetentionTime);
	}
	
	public ImsExtIncidentValueRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(ImsExtIncidentValue.class, cacheable, repositories);
	}

	public ImsExtIncidentValueRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(ImsExtIncidentValue.class, cacheable, cacheRetentionTime, repositories);
	}
	
	// endregion Constructors

	// region getters
	public ImsExtIncidentValue get(Long extIncidentTypeId, Long extIncidentTypeParamId, Long extIncidentId,
			Boolean fillObject) {

		return (ImsExtIncidentValue) getFromCache(new String[] { String.valueOf(extIncidentTypeId),
				String.valueOf(extIncidentTypeParamId), String.valueOf(extIncidentId) }, fillObject);
	}

	@Override
	public List<ImsExtIncidentValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsExtIncidentValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ImsExtIncidentValue> getsByImsExtIncident(Long extIncidentTypeId, Long extIncidentId,
			Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(extIncidentTypeId), null, String.valueOf(extIncidentId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsExtIncidentValue) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ImsExtIncidentValue put(ImsExtIncidentValue entity) {
		return (ImsExtIncidentValue) putFromCache(entity);
	}

	@Override
	public List<ImsExtIncidentValue> put(List<ImsExtIncidentValue> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsExtIncidentValue) be).collect(Collectors.toList());
	}

	@Override
	public ImsExtIncidentValue del(ImsExtIncidentValue entity) {

		return (ImsExtIncidentValue) delFromCache(entity);
	}

	@Override
	public List<ImsExtIncidentValue> del(List<ImsExtIncidentValue> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsExtIncidentValue) be).collect(Collectors.toList());
	}

	@Override
	public ImsExtIncidentValue create(ImsExtIncidentValue imsExtIncidentValue) {

		try {

			return create(imsExtIncidentValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsExtIncidentValue create(ImsExtIncidentValue imsExtIncidentValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsExtIncidentValue iimsExtIncidentValueResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsExtIncidentValue in BBDD
			ImsExtIncidentValue imsExtIncidentValueInserted = null;

			imsExtIncidentValueInserted = (ImsExtIncidentValue) getObjectDao().insert(conn, imsExtIncidentValue);

			if (imsExtIncidentValueInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsExtIncidentValue);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsExtIncidentValueInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				iimsExtIncidentValueResult = get(imsExtIncidentValueInserted.getExtIncidentTypeId(),
						imsExtIncidentValueInserted.getExtIncidentTypeParamId(),
						imsExtIncidentValueInserted.getExtIncidentId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, imsExtIncidentValue);

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

		return iimsExtIncidentValueResult;
	}

	@Override
	public ImsExtIncidentValue clone(ImsExtIncidentValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsExtIncidentValue clone(ImsExtIncidentValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsExtIncidentValue update(ImsExtIncidentValue imsExtIncidentValue) {

		try {

			return update(imsExtIncidentValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsExtIncidentValue update(ImsExtIncidentValue imsExtIncidentValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsExtIncidentValue imsExtIncidentValueResult = null;
		ImsExtIncidentValue imsExtIncidentValueAux = null;

		try {

			if (imsExtIncidentValue == null || !ObjectUtils.allNotNull(imsExtIncidentValue.getExtIncidentTypeId(),
					imsExtIncidentValue.getExtIncidentTypeParamId(), imsExtIncidentValue.getExtIncidentId())) {

				throw new NullPointerException("imsExtIncidentValue is null");
			}

			imsExtIncidentValueAux = get(imsExtIncidentValue.getExtIncidentTypeId(),
					imsExtIncidentValue.getExtIncidentTypeParamId(), imsExtIncidentValue.getExtIncidentId(), false);

			if (imsExtIncidentValueAux == null) {

				throw new NullPointerException("imsExtIncidentValue not found");
			}

			if (imsExtIncidentValue.getAlias() != null)
				imsExtIncidentValueAux.setAlias(imsExtIncidentValue.getAlias());
			if (imsExtIncidentValue.getDescription() != null)
				imsExtIncidentValueAux.setDescription(imsExtIncidentValue.getDescription());
			if (imsExtIncidentValue.getValue() != null)
				imsExtIncidentValueAux.setValue(imsExtIncidentValue.getValue());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsExtIncidentValue in BBDD
			ImsExtIncidentValue imsExtIncidentValueUpdated = null;

			imsExtIncidentValueUpdated = (ImsExtIncidentValue) getObjectDao().update(conn, imsExtIncidentValueAux);
			if (imsExtIncidentValueUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsExtIncidentValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsExtIncidentValueUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsExtIncidentValueResult = imsExtIncidentValueUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, imsExtIncidentValue);

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

		return imsExtIncidentValueResult;
	}

	@Override
	public ImsExtIncidentValue delete(ImsExtIncidentValue imsExtIncidentValue) {

		try {

			return delete(imsExtIncidentValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsExtIncidentValue delete(ImsExtIncidentValue imsExtIncidentValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsExtIncidentValue imsExtIncidentValueResult = null;
		ImsExtIncidentValue imsExtIncidentValueAux = null;

		try {

			if (imsExtIncidentValue == null || !ObjectUtils.allNotNull(imsExtIncidentValue.getExtIncidentTypeId(),
					imsExtIncidentValue.getExtIncidentTypeParamId(), imsExtIncidentValue.getExtIncidentId())) {

				throw new NullPointerException("imsExtIncidentValue is null");
			}

			imsExtIncidentValueAux = get(imsExtIncidentValue.getExtIncidentTypeId(),
					imsExtIncidentValue.getExtIncidentTypeParamId(), imsExtIncidentValue.getExtIncidentId(), false);

			if (imsExtIncidentValueAux == null) {

				throw new NullPointerException("imsExtIncidentValue not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete imsExtIncidentValue in BBDD
			ImsExtIncidentValue imsExtIncidentValueDeleted = null;

			imsExtIncidentValueDeleted = (ImsExtIncidentValue) getObjectDao().delete(conn, imsExtIncidentValueAux);

			if (imsExtIncidentValueDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsExtIncidentValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsExtIncidentValueAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsExtIncidentValueResult = imsExtIncidentValueDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, imsExtIncidentValue);

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

		return imsExtIncidentValueResult;
	}

}
