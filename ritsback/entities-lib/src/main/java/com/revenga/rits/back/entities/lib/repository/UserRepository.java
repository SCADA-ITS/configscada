package com.revenga.rits.back.entities.lib.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
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
import com.revenga.rits.back.data.core.model.ManagementAreaUser;
import com.revenga.rits.back.data.core.model.RolesUsers;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.UserPermission;
import com.revenga.rits.back.data.core.model.UserSession;
import com.revenga.rits.back.data.core.model.command.SetKillUserCommand;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserRepository extends EntityRepository implements EntityCacheable<User>, EntityPersistent<User> {

	// region Constructors
	public UserRepository() {

		super(User.class);
	}

	public UserRepository(Boolean cacheable) {

		super(User.class, cacheable);
	}

	public UserRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(User.class, cacheable, cacheRetentionTime);
	}

	public UserRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(User.class, cacheable, repositories);
	}

	public UserRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(User.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public User get(Long userId, Boolean fillObject) {

		return (User) getFromCache(new String[] { String.valueOf(userId) }, fillObject);
	}

	public List<User> getUserByRole(Long roleId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			List<User> user = basicElements.stream().map(be -> (User) be).collect(Collectors.toList());

			return user.stream().filter(e -> e.getRoleId().equals(roleId)).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<User> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (User) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public User put(User entity) {

		return (User) putFromCache(entity);
	}

	@Override
	public List<User> put(List<User> entities) {

		List<BasicElement> basicElements = entities.stream().map(user -> (BasicElement) user)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (User) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels

	@Override
	public User del(User entity) {

		return (User) delFromCache(entity);
	}

	@Override
	public List<User> del(List<User> entities) {

		List<BasicElement> basicElements = entities.stream().map(user -> (BasicElement) user)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (User) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public User create(User user) {

		try {

			return create(user, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public User create(User user, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		User userResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert element in BBDD
			User userInserted = null;

			userInserted = (User) getObjectDao().insert(conn, user);

			if (userInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, user);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, userInserted));

			// Insert all userPermission
			if (!CollectionUtils.isEmpty(user.getUserPermissions()) && !CollectionUtils.isEmpty(getRepositories())) {

				UserPermissionRepository entityRepository = (UserPermissionRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(UserPermission.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					for (UserPermission userPermission : user.getUserPermissions()) {

						userPermission.setUserId(userInserted.getId());
						entityRepository.create(userPermission, conn, persistOperations);
					}
				}
			}

			// Insert all managementAreas
			if (!CollectionUtils.isEmpty(user.getUserManagementAreas()) && !CollectionUtils.isEmpty(getRepositories())) {

				ManagementAreaUserRepository entityRepository = (ManagementAreaUserRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(ManagementAreaUser.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					for (ManagementAreaUser userManagementArea : user.getUserManagementAreas()){

						userManagementArea.setUserId(userInserted.getId());
						entityRepository.create(userManagementArea, conn, persistOperations);
					}
				}
			}

			// Insert all roleUsers
			if (!CollectionUtils.isEmpty(user.getRolesUsers()) && !CollectionUtils.isEmpty(getRepositories())) {

				RolesUsersRepository entityRepository = (RolesUsersRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(RolesUsers.class)).findFirst().orElse(null);

				if (entityRepository != null) {

					for (RolesUsers roleUsers : user.getRolesUsers()) {

						roleUsers.setUserId(userInserted.getId());
						entityRepository.create(roleUsers, conn, persistOperations);
					}
				}
			}

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
				
				userResult = get(userInserted.getId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, user);

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

		return userResult;
	}

	@Override
	public User clone(User user) {

		return clone(user, null, null);
	}

	@Override
	public User clone(User entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {

		throw new NotImplementedException(
				"Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " without implementation");
	}

	@Override
	public User update(User user) {

		try {

			return update(user, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public User update(User user, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		User userResult = null;
		User userAux = null;

		try {

			if (user == null || user.getId() == null) {

				throw new NullPointerException("User is null");
			}

			userAux = (User) get(user.getId(), false).clone();

			if (userAux == null) {

				throw new NullPointerException("User not found");
			}

			if (user.getName() != null)
				userAux.setName(user.getName());
			if (user.getPassword() != null)
				userAux.setPassword(user.getPassword());
			if (user.getPasswordExpiredAt() != null)
				userAux.setPasswordExpiredAt(user.getPasswordExpiredAt());
			if (user.getSessionExpireTimeS() != null)
				userAux.setSessionExpireTimeS(user.getSessionExpireTimeS());
			if (user.getLockedUntil() != null)
				userAux.setLockedUntil(user.getLockedUntil());
			if (user.getPreset() != null)
				userAux.setPreset(user.getPreset());
			if (user.getAllowMultipleSessions() != null)
				userAux.setAllowMultipleSessions(user.getAllowMultipleSessions());
			if (user.getFullName() != null)
				userAux.setFullName(user.getFullName());
			if (user.getEmail() != null)
				userAux.setEmail(user.getEmail());
			if (user.getRoleId() != null)
				userAux.setRoleId(user.getRoleId());
			if (user.getLocaleCode() != null)
				userAux.setLocaleCode(user.getLocaleCode());
			if (user.getLdap() != null)
				userAux.setLdap(user.getLdap());
			
			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update rolePermissions

			// Delete all RolePermission
			if (user.getUserPermissions() != null && !CollectionUtils.isEmpty(getRepositories())) {

				UserPermissionRepository entityRepository = (UserPermissionRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(UserPermission.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					List<UserPermission> userPermissions = entityRepository.getsByUser(user.getId(), false);

					if (!CollectionUtils.isEmpty(userPermissions)) {

						for (UserPermission userPermission : userPermissions) {

							entityRepository.delete(userPermission, conn, persistOperations);
						}
					}

					// Insert all rolePermission
					if (!CollectionUtils.isEmpty(user.getUserPermissions())) {

						for (UserPermission rolePermission : user.getUserPermissions()) {

							entityRepository.create(rolePermission, conn, persistOperations);
						}
					}
				}
			}

			// Update roleUsers

			// Delete all roleUsers
			if (user.getRolesUsers() != null && !CollectionUtils.isEmpty(getRepositories())) {

				RolesUsersRepository entityRepository = (RolesUsersRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(RolesUsers.class)).findFirst().orElse(null);

				if (entityRepository != null) {

					List<RolesUsers> rolesUsers = entityRepository.getsByUser(user.getId(), false);

					if (!CollectionUtils.isEmpty(rolesUsers)) {

						for (RolesUsers rolesUser : rolesUsers) {

							entityRepository.delete(rolesUser, conn, persistOperations);
						}
					}

					// Insert all usersRoles
					if (!CollectionUtils.isEmpty(user.getRolesUsers())) {

						for (RolesUsers usersRole : user.getRolesUsers()) {

							usersRole.setUserId(user.getId());
							entityRepository.create(usersRole, conn, persistOperations);
						}
					}
				}
			}
			
			// Update ManagementAreas

			// Delete all ManagementAreas
			if (user.getUserManagementAreas()!= null && !CollectionUtils.isEmpty(getRepositories())) {

				ManagementAreaUserRepository entityRepository = (ManagementAreaUserRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(ManagementAreaUser.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					List<ManagementAreaUser> userManagementAreas = entityRepository.gets(user.getId(), false);

					if (!CollectionUtils.isEmpty(userManagementAreas)) {

						for (ManagementAreaUser userManagementArea : userManagementAreas) {

							entityRepository.delete(userManagementArea, conn, persistOperations);
						}
					}

					// Insert all managementAreas
					if (!CollectionUtils.isEmpty(user.getUserManagementAreas())) {

						for (ManagementAreaUser userManagementArea : user.getUserManagementAreas()){

							entityRepository.create(userManagementArea, conn, persistOperations);
						}
					}
				}
			}

			// Insert element in BBDD
			User userUpdated = null;

			userUpdated = (User) getObjectDao().update(conn, userAux);

			if (userUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, userAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, userUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			userResult = userUpdated;

		} catch (EntityDBException | EntityPersistenceException | SQLException | NullPointerException
				| CloneNotSupportedException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, user);

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

		return userResult;
	}

	@Override
	public User delete(User user) {

		try {

			return delete(user, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public User delete(User user, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
			throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		User userResult = null;
		User userAux = null;
		List<UserSession> userSessions = null;
		List<UserPermission> userPermissions = null;
		List<RolesUsers> rolesUsers = null;
		List<ManagementAreaUser> userManagementAreas = null;

		try {

			if (user == null || user.getId() == null) {

				throw new NullPointerException("User is null");
			}

			userAux = get(user.getId(), false);

			if (userAux == null) {

				throw new NullPointerException("User not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all userPermission
			if (!CollectionUtils.isEmpty(getRepositories())) {

				UserPermissionRepository entityRepository = (UserPermissionRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(UserPermission.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					userPermissions = entityRepository.getsByUser(user.getId(), false);

					if (!CollectionUtils.isEmpty(userPermissions)) {

						for (UserPermission userPermission : userPermissions) {

							entityRepository.delete(userPermission, conn, persistOperations);
						}
					}
				}
			}

			// Delete all RolesUsers
			if (!CollectionUtils.isEmpty(getRepositories())) {

				RolesUsersRepository entityRepository = (RolesUsersRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(RolesUsers.class)).findFirst().orElse(null);

				if (entityRepository != null) {

					rolesUsers = entityRepository.getsByUser(user.getId(), false);

					if (!CollectionUtils.isEmpty(rolesUsers)) {

						for (RolesUsers rolesUser : rolesUsers) {

							entityRepository.delete(rolesUser, conn, persistOperations);
						}
					}
				}
			}

			// Delete All UserSession
			if (!CollectionUtils.isEmpty(getRepositories())) {

				UserSessionRepository entityRepository = (UserSessionRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(UserSession.class)).findFirst().orElse(null);

				if (entityRepository != null) {

					userSessions = entityRepository.getSessionsByUser(userAux.getId(), false);

					if (!CollectionUtils.isEmpty(userSessions)) {

						for (UserSession userSession : userSessions) {
							persistOperations.add(new MutablePair<>(PersistOperation.DELETE, userSession));
						}
					}
				}
			}
			
			// Delete all userManagementAreas
			if (!CollectionUtils.isEmpty(getRepositories())) {

				ManagementAreaUserRepository entityRepository = (ManagementAreaUserRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(ManagementAreaUser.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					userManagementAreas = entityRepository.gets(user.getId(), false);

					if (!CollectionUtils.isEmpty(userManagementAreas)) {

						for (ManagementAreaUser userManagementArea : userManagementAreas) {

							entityRepository.delete(userManagementArea, conn, persistOperations);
						}
					}
				}
			}

			// Delete User in BBDD
			User userDeleted = null;

			userDeleted = (User) getObjectDao().delete(conn, userAux);

			if (userDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, user);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, userDeleted));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			userResult = userDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, user);

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

		return userResult;
	}

	public Boolean sendKillUser(List<Long> userIds, Integer lockedTimeS) {

		if (!CollectionUtils.isEmpty(userIds)) {

			SetKillUserCommand cmd = new SetKillUserCommand();

			cmd.setCreatedAt(Instant.now().toEpochMilli());
			cmd.setUserIds(userIds);
			cmd.setLockedTimeS(lockedTimeS);

			return (publish(cmd) != null);
		}

		return false;
	}

}
