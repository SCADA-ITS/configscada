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
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ImsExtIncident;
import com.revenga.rits.back.data.core.model.ImsExtIncidentValue;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsExtIncidentRepository extends EntityRepository
		implements EntityCacheable<ImsExtIncident>, EntityPersistent<ImsExtIncident> {

	// region Constructors
	public ImsExtIncidentRepository() {

		super(ImsExtIncident.class);
	}

	public ImsExtIncidentRepository(Boolean cacheable) {

		super(ImsExtIncident.class, cacheable);
	}

	public ImsExtIncidentRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ImsExtIncident.class, cacheable, cacheRetentionTime);
	}

	public ImsExtIncidentRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(ImsExtIncident.class, cacheable, repositories);
	}

	public ImsExtIncidentRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(ImsExtIncident.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public ImsExtIncident get(Long id, Boolean fillObject) {

		return (ImsExtIncident) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<ImsExtIncident> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ImsExtIncident) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ImsExtIncident put(ImsExtIncident entity) {
		return (ImsExtIncident) putFromCache(entity);
	}

	@Override
	public List<ImsExtIncident> put(List<ImsExtIncident> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsExtIncident) be).collect(Collectors.toList());
	}

	@Override
	public ImsExtIncident del(ImsExtIncident entity) {

		return (ImsExtIncident) delFromCache(entity);
	}

	@Override
	public List<ImsExtIncident> del(List<ImsExtIncident> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ImsExtIncident) be).collect(Collectors.toList());
	}

	@Override
	public ImsExtIncident create(ImsExtIncident imsExtIncident) {

		try {

			return create(imsExtIncident, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsExtIncident create(ImsExtIncident imsExtIncident, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsExtIncident imsExtIncidentResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert imsExtIncident in BBDD
			ImsExtIncident imsExtIncidentInserted = null;

			imsExtIncidentInserted = (ImsExtIncident) getObjectDao().insert(conn, imsExtIncident);

			if (imsExtIncidentInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, imsExtIncident);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsExtIncidentInserted));

			// Insert all imsExtIncidentValues
			if (!CollectionUtils.isEmpty(imsExtIncident.getExtIncidentValues())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				ImsExtIncidentValueRepository entityRepository = (ImsExtIncidentValueRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ImsExtIncidentValue.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					for (ImsExtIncidentValue imsExtIncidentValue : imsExtIncident.getExtIncidentValues()) {

						imsExtIncidentValue.setExtIncidentId(imsExtIncidentInserted.getId());
						imsExtIncidentValue.setExtIncidentTypeId(imsExtIncidentInserted.getExtIncidentTypeId());
						entityRepository.create(imsExtIncidentValue, conn, persistOperations);
					}
				}
			}

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				imsExtIncidentResult = get(imsExtIncidentInserted.getId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, imsExtIncident);

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

		return imsExtIncidentResult;
	}

	@Override
	public ImsExtIncident clone(ImsExtIncident entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsExtIncident clone(ImsExtIncident entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImsExtIncident update(ImsExtIncident imsExtIncident) {

		try {

			return update(imsExtIncident, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsExtIncident update(ImsExtIncident imsExtIncident, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsExtIncident imsExtIncidentResult = null;
		ImsExtIncident imsExtIncidentAux = null;

		try {

			if (imsExtIncident == null || imsExtIncident.getId() == null) {

				throw new NullPointerException("imsExtIncident is null");
			}

			imsExtIncidentAux = get(imsExtIncident.getId(), false);

			if (imsExtIncidentAux == null) {

				throw new NullPointerException("imsExtIncident not found");
			}

			if (imsExtIncident.getAlias() != null)
				imsExtIncidentAux.setAlias(imsExtIncident.getAlias());
			if (imsExtIncident.getDescription() != null)
				imsExtIncidentAux.setDescription(imsExtIncident.getDescription());
			if (imsExtIncident.getIncidentInitDate() != null)
				imsExtIncidentAux.setIncidentInitDate(imsExtIncident.getIncidentInitDate());
			if (imsExtIncident.getLocationId() != null)
				imsExtIncidentAux.setLocationId(imsExtIncident.getLocationId());
			if (imsExtIncident.getExtIncidentTypeId() != null)
				imsExtIncidentAux.setExtIncidentTypeId(imsExtIncident.getExtIncidentTypeId());

			

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update imsExtIncidentValues
			if (!CollectionUtils.isEmpty(imsExtIncident.getExtIncidentValues())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				ImsExtIncidentValueRepository entityRepository = (ImsExtIncidentValueRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ImsExtIncidentValue.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					if (!CollectionUtils.isEmpty(imsExtIncident.getExtIncidentValues())) {

						for (ImsExtIncidentValue extIncidentValue : imsExtIncident.getExtIncidentValues()) {

							entityRepository.update(extIncidentValue, conn, persistOperations);
						}
					}
				}
			}

			// Update imsExtIncident in BBDD
			ImsExtIncident imsExtIncidentUpdated = null;

			imsExtIncidentUpdated = (ImsExtIncident) getObjectDao().update(conn, imsExtIncidentAux);
			if (imsExtIncidentUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, imsExtIncidentAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, imsExtIncidentUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			imsExtIncidentResult = imsExtIncidentUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, imsExtIncident);

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

		return imsExtIncidentResult;
	}

	@Override
	public ImsExtIncident delete(ImsExtIncident imsExtIncident) {

		try {

			return delete(imsExtIncident, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ImsExtIncident delete(ImsExtIncident imsExtIncident, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ImsExtIncident imsExtIncidentResult = null;
		ImsExtIncident imsExtIncidentAux = null;
		List<ImsExtIncidentValue> imsExtIncidentValues = null;

		try {

			if (imsExtIncident == null || imsExtIncident.getId() == null) {

				throw new NullPointerException("imsExtIncident is null");
			}

			imsExtIncidentAux = get(imsExtIncident.getId(), false);

			if (imsExtIncidentAux == null) {

				throw new NullPointerException("imsExtIncident not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all imsExtIncidentValues
			if (!CollectionUtils.isEmpty(getRepositories())) {

				ImsExtIncidentValueRepository entityRepository = (ImsExtIncidentValueRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ImsExtIncidentValue.class))
						.findFirst().orElse(null);

				if (entityRepository != null) {

					imsExtIncidentValues = entityRepository.getsByImsExtIncident(
							imsExtIncidentAux.getExtIncidentTypeId(), imsExtIncidentAux.getId(), false);

					if (!CollectionUtils.isEmpty(imsExtIncidentValues)) {

						for (ImsExtIncidentValue imsExtIncidentValue : imsExtIncidentValues) {

							entityRepository.delete(imsExtIncidentValue, conn, persistOperations);
						}
					}
				}
			}

			// Delete imsExtIncident in BBDD
			ImsExtIncident imsExtIncidentDeleted = null;

			imsExtIncidentDeleted = (ImsExtIncident) getObjectDao().delete(conn, imsExtIncidentAux);

			if (imsExtIncidentDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, imsExtIncidentAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, imsExtIncidentAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			imsExtIncidentResult = imsExtIncidentDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, imsExtIncident);

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

		return imsExtIncidentResult;
	}
}
