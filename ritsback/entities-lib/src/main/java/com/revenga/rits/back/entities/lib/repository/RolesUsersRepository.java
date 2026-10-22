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
import com.revenga.rits.back.data.core.model.RolesUsers;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RolesUsersRepository extends EntityRepository
		implements EntityCacheable<RolesUsers>, EntityPersistent<RolesUsers> {

	// region Constructors
	public RolesUsersRepository() {

		super(RolesUsers.class);
	}

	public RolesUsersRepository(Boolean cacheable) {

		super(RolesUsers.class, cacheable);
	}

	public RolesUsersRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(RolesUsers.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public RolesUsers get(Long userId, Long roleId, Boolean fillObject) {

		return (RolesUsers) getFromCache(new String[] { String.valueOf(userId), String.valueOf(roleId) },
				fillObject);
	}

	@Override
	public List<RolesUsers> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (RolesUsers) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<RolesUsers> getsByUser(Long userId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(userId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (RolesUsers) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<RolesUsers> getsByRole(Long roleId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(roleId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (RolesUsers) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public RolesUsers put(RolesUsers entity) {
		return (RolesUsers) putFromCache(entity);
	}

	@Override
	public List<RolesUsers> put(List<RolesUsers> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (RolesUsers) be).collect(Collectors.toList());
	}

	@Override
	public RolesUsers del(RolesUsers entity) {

		return (RolesUsers) delFromCache(entity);
	}

	@Override
	public List<RolesUsers> del(List<RolesUsers> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (RolesUsers) be).collect(Collectors.toList());
	}

	@Override
	public RolesUsers create(RolesUsers roleUsers) {

		try {

			return create(roleUsers, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public RolesUsers create(RolesUsers roleUsers, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		RolesUsers roleUsersResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert roleUsers in BBDD
			RolesUsers roleUsersInserted = null;

			roleUsersInserted = (RolesUsers) getObjectDao().insert(conn, roleUsers);

			if (roleUsersInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, roleUsers);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, roleUsersInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
			}

			roleUsersResult = roleUsersInserted;

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

			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, roleUsers);

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

		return roleUsersResult;
	}

	@Override
	public RolesUsers clone(RolesUsers entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RolesUsers clone(RolesUsers entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RolesUsers update(RolesUsers entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RolesUsers update(RolesUsers entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RolesUsers delete(RolesUsers roleUsers) {
		
		try {

			return delete(roleUsers, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public RolesUsers delete(RolesUsers roleUsers, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		RolesUsers roleUsersResult = null;
		RolesUsers roleUsersAux = null;

		try {

			if (roleUsers == null || !ObjectUtils.allNotNull(roleUsers.getUserId(), roleUsers.getRoleId())) {

				throw new NullPointerException("roleUsers is null");
			}

			roleUsersAux = get(roleUsers.getUserId(), roleUsers.getRoleId(), false);

			if (roleUsersAux == null) {

				throw new NullPointerException("roleUsers not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete roleUsers in BBDD
			RolesUsers roleUsersDeleted = null;

			roleUsersDeleted = (RolesUsers) getObjectDao().delete(conn, roleUsersAux);

			if (roleUsersDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, roleUsers);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, roleUsersDeleted));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			roleUsersResult = roleUsersDeleted;

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

			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, roleUsers);

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

		return roleUsersResult;
	}

}
