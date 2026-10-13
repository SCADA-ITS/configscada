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
import com.revenga.rits.back.data.core.model.PmDisabledQueuePriority;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PmDisabledQueuePriorityRepository extends EntityRepository
		implements EntityCacheable<PmDisabledQueuePriority>, EntityPersistent<PmDisabledQueuePriority> {

	// region Constructors
	public PmDisabledQueuePriorityRepository() {

		super(PmDisabledQueuePriority.class);
	}

	public PmDisabledQueuePriorityRepository(Boolean cacheable) {

		super(PmDisabledQueuePriority.class, cacheable);
	}

	public PmDisabledQueuePriorityRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PmDisabledQueuePriority.class, cacheable, cacheRetentionTime);
	}

	public PmDisabledQueuePriorityRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(PmDisabledQueuePriority.class, cacheable, repositories);
	}

	public PmDisabledQueuePriorityRepository(Boolean cacheable, Long cacheRetentionTime,
			List<EntityRepository> repositories) {

		super(PmDisabledQueuePriority.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public PmDisabledQueuePriority get(Long queueId, Long priorityId, Long elementTypeId, Long elementId,
			Boolean fillObject) {

		return (PmDisabledQueuePriority) getFromCache(new String[] { String.valueOf(queueId),
				String.valueOf(priorityId), String.valueOf(elementTypeId), String.valueOf(elementId) }, fillObject);
	}

	public List<PmDisabledQueuePriority> gets(Long queueId, Long elementTypeId, Long elementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(queueId), null,
				String.valueOf(elementTypeId), String.valueOf(elementId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmDisabledQueuePriority.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<PmDisabledQueuePriority> getsByElementType( Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, null,
				String.valueOf(elementTypeId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmDisabledQueuePriority.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	@Override
	public List<PmDisabledQueuePriority> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmDisabledQueuePriority.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PmDisabledQueuePriority put(PmDisabledQueuePriority entity) {

		return (PmDisabledQueuePriority) putFromCache(entity);
	}

	@Override
	public List<PmDisabledQueuePriority> put(List<PmDisabledQueuePriority> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(PmDisabledQueuePriority.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PmDisabledQueuePriority del(PmDisabledQueuePriority entity) {

		return (PmDisabledQueuePriority) delFromCache(entity);

	}

	@Override
	public List<PmDisabledQueuePriority> del(List<PmDisabledQueuePriority> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(PmDisabledQueuePriority.class::cast).collect(Collectors.toList());
	}

	@Override
	public PmDisabledQueuePriority create(PmDisabledQueuePriority entity) {
		try {

			return create(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PmDisabledQueuePriority create(PmDisabledQueuePriority entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PmDisabledQueuePriority result = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert plan in BBDD
			PmDisabledQueuePriority inserted = null;

			inserted = (PmDisabledQueuePriority) getObjectDao().insert(conn, entity);

			if (inserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, entity);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, inserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				result = get(inserted.getQueueId(), inserted.getPriorityId(), inserted.getElementTypeId(),
						inserted.getElementId(), false);
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
	public PmDisabledQueuePriority clone(PmDisabledQueuePriority entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PmDisabledQueuePriority clone(PmDisabledQueuePriority entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PmDisabledQueuePriority update(PmDisabledQueuePriority entity) {
		try {

			return update(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PmDisabledQueuePriority update(PmDisabledQueuePriority entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PmDisabledQueuePriority delete(PmDisabledQueuePriority entity) {
		try {

			return delete(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public PmDisabledQueuePriority delete(PmDisabledQueuePriority entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		PmDisabledQueuePriority result = null;
		PmDisabledQueuePriority aux = null;
		try {

			if (entity == null) {

				throw new NullPointerException("PmDisabledQueuePriority is null");
			}
			if (entity.getQueueId() != null && entity.getPriorityId() != null && entity.getElementTypeId() != null
					&& entity.getElementId() != null) {
				
				aux = get(entity.getQueueId(), entity.getPriorityId(), entity.getElementTypeId(), entity.getElementId(),
						false);
			}

			if (aux == null) {

				throw new NullPointerException("PmDisabledQueuePriority not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete plan in BBDD
			PmDisabledQueuePriority deleted = null;

			deleted = (PmDisabledQueuePriority) getObjectDao().delete(conn, aux);

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
