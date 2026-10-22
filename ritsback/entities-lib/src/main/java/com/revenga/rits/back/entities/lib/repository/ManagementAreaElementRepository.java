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
import com.revenga.rits.back.data.core.model.ManagementAreaElement;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ManagementAreaElementRepository extends EntityRepository
		implements EntityCacheable<ManagementAreaElement>,  EntityPersistent<ManagementAreaElement> {

	// region Constructors
	public ManagementAreaElementRepository() {

		super(ManagementAreaElement.class);
	}

	public ManagementAreaElementRepository(EntityRepositoryOptions options) {

		super(ManagementAreaElement.class, options);
	}

	// endregion Constructors

	// region getters
	public ManagementAreaElement get(Long id, Long elementTypeId, Long elementId, Boolean fillObject) {

		return (ManagementAreaElement) getFromCache(
				new String[] { String.valueOf(id), String.valueOf(elementTypeId), String.valueOf(elementId) },
				fillObject);
	}

	@Override
	public List<ManagementAreaElement> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ManagementAreaElement.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ManagementAreaElement> gets(Long elementTypeId, Long elementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { null, String.valueOf(elementTypeId), String.valueOf(elementId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ManagementAreaElement.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ManagementAreaElement put(ManagementAreaElement entity) {
		return (ManagementAreaElement) putFromCache(entity);
	}

	@Override
	public List<ManagementAreaElement> put(List<ManagementAreaElement> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ManagementAreaElement.class::cast).collect(Collectors.toList());
	}

	@Override
	public ManagementAreaElement del(ManagementAreaElement entity) {

		return (ManagementAreaElement) delFromCache(entity);
	}

	@Override
	public List<ManagementAreaElement> del(List<ManagementAreaElement> entities) {
		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ManagementAreaElement.class::cast).collect(Collectors.toList());
	}

	@Override
	public ManagementAreaElement create(ManagementAreaElement entity) {
		
		ManagementAreaElement managementAreaElement = null;
		
		try {

			managementAreaElement = create(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return managementAreaElement;
	}

	@Override
	public ManagementAreaElement create(ManagementAreaElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		
		Connection conn = null;
	    boolean closeConnection = false;
	    boolean commit = false;
	    conn = connection;
	    ManagementAreaElement managementAreaElementResult = null;

	    try {

	        if (conn == null) {

	            conn = DataSourceConnection.getInstance().getConnection();
	            conn.setAutoCommit(false);
	            closeConnection = true;
	            commit = true;
	            persistOperations = new ArrayList<>();
	        }

	        // Insert ManagementAreaElement in BBDD
	        ManagementAreaElement managementAreaElementInserted = null;

	        managementAreaElementInserted = (ManagementAreaElement) getObjectDao().insert(conn, entity);

	        if (managementAreaElementInserted == null) {

	            throw new EntityDBException(EntityDBException.Operation.INSERT, entity);
	        }

	        persistOperations.add(new MutablePair<>(PersistOperation.PUT, managementAreaElementInserted));

	        if (commit) {
	            conn.commit();

	            persistOperations(persistOperations);

				managementAreaElementResult = get(managementAreaElementInserted.getId(),
						managementAreaElementInserted.getElementTypeId(), managementAreaElementInserted.getElementId(),
						false);
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

	    return managementAreaElementResult;
	}

	@Override
	public ManagementAreaElement clone(ManagementAreaElement entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagementAreaElement clone(ManagementAreaElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagementAreaElement update(ManagementAreaElement entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagementAreaElement update(ManagementAreaElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManagementAreaElement delete(ManagementAreaElement entity) {
		
		ManagementAreaElement managementAreaElement = null;
		
		try {

			managementAreaElement = delete(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return managementAreaElement;
	}

	@Override
	public ManagementAreaElement delete(ManagementAreaElement entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ManagementAreaElement result = null;
		ManagementAreaElement aux = null;
		try {

			if (entity == null) {

				throw new NullPointerException("ManagementAreaElement is null");
			}

			aux = get(entity.getId(), entity.getElementTypeId(), entity.getElementId(), false);

			if (aux == null) {

				throw new NullPointerException("ManagementAreaElement not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete ManagementAreaElement in BBDD
			ManagementAreaElement deleted = null;

			deleted = (ManagementAreaElement) getObjectDao().delete(conn, aux);

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
