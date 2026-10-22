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
import com.revenga.rits.back.data.core.model.UserPermission;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserPermissionRepository extends EntityRepository
		implements EntityCacheable<UserPermission>, EntityPersistent<UserPermission> {

	// region Constructors
	public UserPermissionRepository() {

		super(UserPermission.class);
	}

	public UserPermissionRepository(Boolean cacheable) {

		super(UserPermission.class, cacheable);
	}

	public UserPermissionRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(UserPermission.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters

	public UserPermission get(Long userId, Long systemPermissionId, Boolean fillObject) {

		return (UserPermission) getFromCache(
				new String[] { String.valueOf(userId), String.valueOf(systemPermissionId) }, fillObject);
	}

	public List<UserPermission> getsByUser(Long userId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(userId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (UserPermission) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<UserPermission> getsBySystemPermision(Long systemPermissionId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(systemPermissionId) },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (UserPermission) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<UserPermission> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (UserPermission) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public UserPermission put(UserPermission entity) {

		return (UserPermission) putFromCache(entity);
	}

	@Override
	public List<UserPermission> put(List<UserPermission> entities) {

		List<BasicElement> basicElements = entities.stream().map(userPermission -> (BasicElement) userPermission)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (UserPermission) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels

	@Override
	public UserPermission del(UserPermission entity) {

		return (UserPermission) delFromCache(entity);
	}

	@Override
	public List<UserPermission> del(List<UserPermission> entities) {

		List<BasicElement> basicElements = entities.stream().map(userPermission -> (BasicElement) userPermission)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (UserPermission) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public UserPermission create(UserPermission userPermission) {

		try {

			return create(userPermission, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public UserPermission create(UserPermission userPermission, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		UserPermission userPermissionResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert element in BBDD
			UserPermission userPermissionInserted = null;

			userPermissionInserted = (UserPermission) getObjectDao().insert(conn, userPermission);

			if (userPermissionInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, userPermission);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, userPermissionInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
			}

			userPermissionResult = userPermissionInserted;

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

			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, userPermission);

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

		return userPermissionResult;
	}

	@Override
	public UserPermission clone(UserPermission userPermission) {

		return clone(userPermission, null, null);
	}

	@Override
	public UserPermission clone(UserPermission entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {

		throw new NotImplementedException(
				"Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " without implementation");
	}

	@Override
	public UserPermission update(UserPermission userPermission) {

		return update(userPermission, null, null);
	}

	@Override
	public UserPermission update(UserPermission userPermission, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {

		throw new NotImplementedException(
				"Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " without implementation");

	}

	@Override
	public UserPermission delete(UserPermission userPermission) {

		try {

			return delete(userPermission, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public UserPermission delete(UserPermission userPermission, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		UserPermission userPermissionResult = null;
		UserPermission userPermissionAux = null;

		try {

			if (userPermission == null || !ObjectUtils.allNotNull(userPermission.getUserId(), userPermission.getSystemPermissionId())) {

				throw new NullPointerException("UserPermission is null");
			}

			userPermissionAux = get(userPermission.getUserId(), userPermission.getSystemPermissionId(), false);

			if (userPermissionAux == null) {

				throw new NullPointerException("UserPermission not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete User in BBDD
			UserPermission userPermissionDeleted = null;

			userPermissionDeleted = (UserPermission) getObjectDao().delete(conn, userPermissionAux);

			if (userPermissionDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, userPermission);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, userPermissionDeleted));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			userPermissionResult = userPermissionDeleted;

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

			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, userPermission);

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

		return userPermissionResult;
	}
}
