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
import com.revenga.rits.back.data.core.model.RolePermission;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RolePermissionRepository extends EntityRepository
		implements EntityCacheable<RolePermission>, EntityPersistent<RolePermission> {

	// region Constructors
	public RolePermissionRepository() {

		super(RolePermission.class);
	}

	public RolePermissionRepository(Boolean cacheable) {

		super(RolePermission.class, cacheable);
	}

	public RolePermissionRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(RolePermission.class, cacheable, cacheRetentionTime);
	}

	// endregion Constructors

	// region getters

	public RolePermission get(Long roleId, Long systemPermissionId, Boolean fillObject) {

		return (RolePermission) getFromCache(
				new String[] { String.valueOf(roleId), String.valueOf(systemPermissionId) }, fillObject);
	}

	public List<RolePermission> getsByRole(Long roleId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(roleId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (RolePermission) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<RolePermission> getsBySystemPermision(Long systemPermissionId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(systemPermissionId) },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (RolePermission) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<RolePermission> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (RolePermission) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public RolePermission put(RolePermission entity) {

		return (RolePermission) putFromCache(entity);
	}

	@Override
	public List<RolePermission> put(List<RolePermission> entities) {

		List<BasicElement> basicElements = entities.stream().map(rolePermission -> (BasicElement) rolePermission)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (RolePermission) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels

	@Override
	public RolePermission del(RolePermission entity) {

		return (RolePermission) delFromCache(entity);
	}

	@Override
	public List<RolePermission> del(List<RolePermission> entities) {

		List<BasicElement> basicElements = entities.stream().map(rolePermission -> (BasicElement) rolePermission)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (RolePermission) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public RolePermission create(RolePermission rolePermission) {

		try {

			return create(rolePermission, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public RolePermission create(RolePermission rolePermission, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		RolePermission rolePermissionResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert element in BBDD
			RolePermission rolePermissionInserted = null;

			rolePermissionInserted = (RolePermission) getObjectDao().insert(conn, rolePermission);

			if (rolePermissionInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, rolePermission);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, rolePermissionInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
			}

			rolePermissionResult = rolePermissionInserted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, rolePermission);

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

		return rolePermissionResult;
	}

	@Override
	public RolePermission clone(RolePermission rolePermission) {

		return clone(rolePermission, null, null);
	}

	@Override
	public RolePermission clone(RolePermission entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {

		throw new NotImplementedException(
				"Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " without implementation");
	}

	@Override
	public RolePermission update(RolePermission rolePermission) {

		return update(rolePermission, null, null);
	}

	@Override
	public RolePermission update(RolePermission rolePermission, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {

		throw new NotImplementedException(
				"Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " without implementation");

	}

	@Override
	public RolePermission delete(RolePermission rolePermission) {

		try {

			return delete(rolePermission, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public RolePermission delete(RolePermission rolePermission, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		RolePermission rolePermissionResult = null;
		RolePermission rolePermissionAux = null;

		try {

			if (rolePermission == null || !ObjectUtils.allNotNull(rolePermission.getRoleId(), rolePermission.getSystemPermissionId())) {

				throw new NullPointerException("RolePermission is null");
			}

			rolePermissionAux = get(rolePermission.getRoleId(), rolePermission.getSystemPermissionId(), false);

			if (rolePermissionAux == null) {

				throw new NullPointerException("RolePermission not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete Role in BBDD
			RolePermission rolePermissionDeleted = null;

			rolePermissionDeleted = (RolePermission) getObjectDao().delete(conn, rolePermissionAux);

			if (rolePermissionDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, rolePermission);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, rolePermissionDeleted));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			rolePermissionResult = rolePermissionDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, rolePermission);

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

		return rolePermissionResult;
	}
}
