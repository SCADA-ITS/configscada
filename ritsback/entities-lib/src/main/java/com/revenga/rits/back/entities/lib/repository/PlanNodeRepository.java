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
import com.revenga.rits.back.data.core.model.PlanNode;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanNodeRepository extends EntityRepository
		implements EntityCacheable<PlanNode>, EntityPersistent<PlanNode> {

	// region Constructors
	public PlanNodeRepository() {

		super(PlanNode.class);
	}

	public PlanNodeRepository(Boolean cacheable) {

		super(PlanNode.class, cacheable);
	}

	public PlanNodeRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PlanNode.class, cacheable, cacheRetentionTime);
	}

	public PlanNodeRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(PlanNode.class, cacheable, repositories);
	}

	public PlanNodeRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(PlanNode.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public PlanNode get(Long id, Boolean fillObject) {

		return (PlanNode) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<PlanNode> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (PlanNode) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	@Override
	public PlanNode put(PlanNode entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlanNode> put(List<PlanNode> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanNode del(PlanNode entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlanNode> del(List<PlanNode> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanNode create(PlanNode entity) {
		try {

			return create(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanNode create(PlanNode entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanNode result = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert plan in BBDD
			PlanNode inserted = null;

			inserted = (PlanNode) getObjectDao().insert(conn, entity);

			if (inserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, entity);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, inserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				result = get(inserted.getId(), false);
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
	public PlanNode clone(PlanNode entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanNode clone(PlanNode entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanNode update(PlanNode entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanNode update(PlanNode entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanNode delete(PlanNode entity) {
		try {

			return delete(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PlanNode delete(PlanNode entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PlanNode result = null;
		PlanNode aux = null;
		try {

			if (entity == null || entity.getId() == null) {

				throw new NullPointerException("PlanNode is null");
			}

			aux = get(entity.getId(), false);

			if (aux == null) {

				throw new NullPointerException("PlanNode not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all PlanHierarchy
			if (!CollectionUtils.isEmpty(getRepositories())) {

				PlanHierarchyRepository entityRepository = (PlanHierarchyRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(PlanHierarchy.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					// Node Child
					List<PlanHierarchy> planHierarchies = entityRepository.getsByNodeChild(entity.getId(), false);

					if (!CollectionUtils.isEmpty(planHierarchies)) {

						for (PlanHierarchy planHierarchy : planHierarchies) {

							entityRepository.delete(planHierarchy, conn, persistOperations);
						}
					}

					// Node Parent
					planHierarchies = entityRepository.getsByNodeParent(entity.getId(), false);

					if (!CollectionUtils.isEmpty(planHierarchies)) {

						for (PlanHierarchy planHierarchy : planHierarchies) {

							entityRepository.delete(planHierarchy, conn, persistOperations);
						}
					}
				}
			}

			// Delete plan in BBDD
			PlanNode deleted = null;

			deleted = (PlanNode) getObjectDao().delete(conn, aux);

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
