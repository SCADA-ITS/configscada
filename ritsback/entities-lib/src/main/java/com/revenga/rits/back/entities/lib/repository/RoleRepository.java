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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Role;
import com.revenga.rits.back.data.core.model.RolePermission;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RoleRepository extends EntityRepository implements EntityCacheable<Role>, EntityPersistent<Role> {

	// region Constructors
	public RoleRepository() {

		super(Role.class);
	}

	public RoleRepository(Boolean cacheable) {

		super(Role.class, cacheable);
	}

	public RoleRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(Role.class, cacheable, cacheRetentionTime);
	}

	public RoleRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(Role.class, cacheable, repositories);
	}

	public RoleRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(Role.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public Role get(Long roleId, Boolean fillObject) {

		return (Role) getFromCache(new String[] { String.valueOf(roleId) }, fillObject);
	}

	@Override
	public List<Role> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (Role) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public Role put(Role entity) {

		return (Role) putFromCache(entity);
	}

	@Override
	public List<Role> put(List<Role> entities) {

		List<BasicElement> basicElements = entities.stream().map(role -> (BasicElement) role)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (Role) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public Role del(Role entity) {

		return (Role) delFromCache(entity);

	}

	@Override
	public List<Role> del(List<Role> entities) {

		List<BasicElement> basicElements = entities.stream().map(role -> (BasicElement) role)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (Role) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public Role create(Role role) {
		try {

			return create(role, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Role create(Role role, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Role roleResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert role in BBDD
			Role roleInserted = null;

			roleInserted = (Role) getObjectDao().insert(conn, role);

			if (roleInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, role);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, roleInserted));

			// Insert all rolePermission
			if (!CollectionUtils.isEmpty(role.getRolePermissions()) && !CollectionUtils.isEmpty(getRepositories())) {

				RolePermissionRepository entityRepository = (RolePermissionRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(RolePermission.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					for (RolePermission rolePermission : role.getRolePermissions()) {

						rolePermission.setRoleId(roleInserted.getId());
						entityRepository.create(rolePermission, conn, persistOperations);
					}
				}
			}

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				roleResult = get(roleInserted.getId(), false);
			}

			
		} catch (EntityDBException | EntityPersistenceException | SQLException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, role);

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

		return roleResult;
	}

	@Override
	public Role clone(Role role) {
		return clone(role, null, null);
	}

	@Override
	public Role clone(Role entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {

		throw new NotImplementedException(
				"Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " without implementation");

	}

	@Override
	public Role update(Role role) {

		try {

			return update(role, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Role update(Role role, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Role roleResult = null;
		Role roleAux = null;

		try {

			if (role == null || role.getId() == null) {

				throw new NullPointerException("Role is null");
			}

			roleAux = get(role.getId(), false);

			if (roleAux == null) {

				throw new NullPointerException("Role not found");
			}

			if (role.getAlias() != null)
				roleAux.setAlias(role.getAlias());
			if (role.getDescription() != null)
				roleAux.setDescription(role.getDescription());
			if (role.getPreset() != null)
				roleAux.setPreset(role.getPreset());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update rolePermissions
			if (role.getRolePermissions() != null && !CollectionUtils.isEmpty(getRepositories())) {

				// Delete all RolePermission
				RolePermissionRepository entityRepository = (RolePermissionRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(RolePermission.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					List<RolePermission> rolePermissions = entityRepository.getsByRole(role.getId(), false);

					if (!CollectionUtils.isEmpty(rolePermissions)) {

						for (RolePermission rolePermission : rolePermissions) {

							entityRepository.delete(rolePermission, conn, persistOperations);
						}
					}

					// Insert all rolePermission
					
					if(!CollectionUtils.isEmpty(role.getRolePermissions())){
						
						for (RolePermission rolePermission : role.getRolePermissions()) {

							entityRepository.create(rolePermission, conn, persistOperations);
						}
					}
				}
			}

			// Update element in BBDD
			Role roleUpdated = null;

			roleUpdated = (Role) getObjectDao().update(conn, roleAux);

			if (roleUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, roleAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, roleUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			roleResult = roleUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, role);

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

		return roleResult;
	}

	@Override
	public Role delete(Role role) {

		try {

			return delete(role, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Role delete(Role role, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Role roleResult = null;
		Role roleAux = null;
		List<User> users = null;
		List<RolePermission> rolePermissions = null;

		try {

			if (role == null || role.getId() == null) {

				throw new NullPointerException("Role is null");
			}

			roleAux = get(role.getId(), false);

			if (roleAux == null) {

				throw new NullPointerException("Role not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all RolePermission
			if (!CollectionUtils.isEmpty(getRepositories())) {

				RolePermissionRepository entityRepository = (RolePermissionRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(RolePermission.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					rolePermissions = entityRepository.getsByRole(role.getId(), false);

					if (!CollectionUtils.isEmpty(rolePermissions)) {

						for (RolePermission rolePermission : rolePermissions) {

							entityRepository.delete(rolePermission, conn, persistOperations);
						}
					}
				}
			}

			// Delete all user with this role
			if (!CollectionUtils.isEmpty(getRepositories())) {

				UserRepository entityRepository = (UserRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(User.class)).findFirst().orElse(null);

				if (entityRepository != null) {

					users = entityRepository.getUserByRole(role.getId(), false);

					if (!CollectionUtils.isEmpty(users)) {

						for (User user : users) {

							entityRepository.delete(user, conn, persistOperations);
						}
					}
				}
			}

			// Delete role in BBDD
			Role roleDeleted = null;

			roleDeleted = (Role) getObjectDao().delete(conn, roleAux);

			if (roleDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, roleAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, roleAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			roleResult = roleDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, role);

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

		return roleResult;
	}

}
