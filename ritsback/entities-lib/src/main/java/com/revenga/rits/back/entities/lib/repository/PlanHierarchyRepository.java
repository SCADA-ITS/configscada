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
import com.revenga.rits.back.data.core.model.PlanHierarchy;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanHierarchyRepository extends EntityRepository
		implements EntityCacheable<PlanHierarchy>, EntityPersistent<PlanHierarchy> {

	// region Constructors
	public PlanHierarchyRepository() {

		super(PlanHierarchy.class);
	}

	public PlanHierarchyRepository(Boolean cacheable) {

		super(PlanHierarchy.class, cacheable);
	}

	public PlanHierarchyRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanHierarchy.class, cacheable, cacheRetentionTime);
	}

	public PlanHierarchyRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(PlanHierarchy.class, cacheable, repositories);
	}

	public PlanHierarchyRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(PlanHierarchy.class, cacheable, cacheRetentionTime, repositories);
	}
	// endregion Constructors

	// region getters
	public PlanHierarchy get(Long nodeParentId, Long nodeChildId, Boolean fillObject) {

		return (PlanHierarchy) getFromCache(new String[] { String.valueOf(nodeParentId), String.valueOf(nodeChildId) },
				fillObject);
	}

	@Override
	public List<PlanHierarchy> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanHierarchy) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	public List<PlanHierarchy> getsByNodeParent(Long nodeParentId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(nodeParentId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanHierarchy) be).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	public List<PlanHierarchy> getsByNodeChild(Long nodeChildId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(nodeChildId) },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanHierarchy) be).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	public PlanHierarchy put(PlanHierarchy entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlanHierarchy> put(List<PlanHierarchy> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanHierarchy del(PlanHierarchy entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlanHierarchy> del(List<PlanHierarchy> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanHierarchy create(PlanHierarchy entity) {
		try {

			return create(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanHierarchy create(PlanHierarchy entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanHierarchy result = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert plan in BBDD
			PlanHierarchy inserted = null;

			inserted = (PlanHierarchy) getObjectDao().insert(conn, entity);

			if (inserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, entity);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, inserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				result = get(inserted.getNodeParentId(), inserted.getNodeChildId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, entity);

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

	@Override
	public PlanHierarchy clone(PlanHierarchy entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanHierarchy clone(PlanHierarchy entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanHierarchy update(PlanHierarchy entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanHierarchy update(PlanHierarchy entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanHierarchy delete(PlanHierarchy entity) {
		try {

			return delete(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanHierarchy delete(PlanHierarchy entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanHierarchy result = null;
		PlanHierarchy aux = null;
		try {

			if (entity == null || entity.getNodeParentId() == null || entity.getNodeParentId() == null) {

				throw new NullPointerException("PlanHierarchy is null");
			}
			aux = get(entity.getNodeParentId(), entity.getNodeChildId(), false);

			if (aux == null) {

				throw new NullPointerException("PlanHierarchy not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete plan in BBDD
			PlanHierarchy deleted = null;

			deleted = (PlanHierarchy) getObjectDao().delete(conn, aux);

			if (deleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, aux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, aux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			result = deleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, entity);

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
