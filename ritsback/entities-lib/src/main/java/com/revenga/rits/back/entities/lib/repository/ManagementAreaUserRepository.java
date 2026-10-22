package com.revenga.rits.back.entities.lib.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ManagementAreaUser;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ManagementAreaUserRepository extends EntityRepository
		implements EntityCacheable<ManagementAreaUser>, EntityPersistent<ManagementAreaUser> {

	// region Constructors
	public ManagementAreaUserRepository() {

		super(ManagementAreaUser.class);
	}

	public ManagementAreaUserRepository(EntityRepositoryOptions options) {

		super(ManagementAreaUser.class, options);
	}

	// endregion Constructors

	// region getters
	public ManagementAreaUser get(Long id, Long userId, Boolean fillObject) {

		return (ManagementAreaUser) getFromCache(new String[] { String.valueOf(id), String.valueOf(userId) },
				fillObject);
	}

	@Override
	public List<ManagementAreaUser> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ManagementAreaUser.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ManagementAreaUser> gets(Long userId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(userId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ManagementAreaUser.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ManagementAreaUser put(ManagementAreaUser entity) {
		return (ManagementAreaUser) putFromCache(entity);
	}

	@Override
	public List<ManagementAreaUser> put(List<ManagementAreaUser> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ManagementAreaUser.class::cast).collect(Collectors.toList());
	}

	@Override
	public ManagementAreaUser del(ManagementAreaUser entity) {

		return (ManagementAreaUser) delFromCache(entity);
	}

	@Override
	public List<ManagementAreaUser> del(List<ManagementAreaUser> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ManagementAreaUser.class::cast).collect(Collectors.toList());
	}
	
	@Override
	public ManagementAreaUser create(ManagementAreaUser managementAreaUser) {

		try {

			return create(managementAreaUser, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ManagementAreaUser create(ManagementAreaUser managementAreaUser, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ManagementAreaUser managementAreaUserResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert element in BBDD
			ManagementAreaUser managementAreaUserInserted = null;

			managementAreaUserInserted = (ManagementAreaUser) getObjectDao().insert(conn, managementAreaUser);

			if (managementAreaUserInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, managementAreaUser);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, managementAreaUserInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
			}

			managementAreaUserResult = managementAreaUserInserted;

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

			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, managementAreaUser);

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

		return managementAreaUserResult;
	}

	@Override
	public ManagementAreaUser clone(ManagementAreaUser managementAreaUser) {

		return clone(managementAreaUser, null, null);
	}

	@Override
	public ManagementAreaUser clone(ManagementAreaUser managementAreaUser, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {

		throw new NotImplementedException(
				"Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " without implementation");
	}

	@Override
	public ManagementAreaUser update(ManagementAreaUser managementAreaUser) {

		return update(managementAreaUser, null, null);
	}

	@Override
	public ManagementAreaUser update(ManagementAreaUser managementAreaUser, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {

		throw new NotImplementedException(
				"Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " without implementation");

	}

	@Override
	public ManagementAreaUser delete(ManagementAreaUser managementAreaUser) {

		try {

			return delete(managementAreaUser, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ManagementAreaUser delete(ManagementAreaUser managementAreaUser, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ManagementAreaUser managementAreaUserResult = null;
		ManagementAreaUser managementAreaUserAux = null;

		try {

			if (managementAreaUser == null || !ObjectUtils.allNotNull(managementAreaUser.getUserId(), managementAreaUser.getId())) {

				throw new NullPointerException("ManagementAreaUser is null");
			}

			managementAreaUserAux = get(managementAreaUser.getId(), managementAreaUser.getUserId(),  false);

			if (managementAreaUserAux == null) {

				throw new NullPointerException("ManagementAreaUser not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete in BBDD
			ManagementAreaUser managementAreaUserDeleted = null;

			managementAreaUserDeleted = (ManagementAreaUser) getObjectDao().delete(conn, managementAreaUserAux);

			if (managementAreaUserDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, managementAreaUser);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, managementAreaUserDeleted));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			managementAreaUserResult = managementAreaUserDeleted;

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

			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, managementAreaUser);

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

		return managementAreaUserResult;
	}
}
