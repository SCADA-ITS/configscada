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
import com.revenga.rits.back.data.core.model.ElementIOControllerModule;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementIOControllerModuleRepository extends EntityRepository
		implements EntityCacheable<ElementIOControllerModule>, EntityPersistent<ElementIOControllerModule> {

	// region Constructors
	public ElementIOControllerModuleRepository() {

		super(ElementIOControllerModule.class);
	}
		
	public ElementIOControllerModuleRepository(EntityRepositoryOptions options) {

		super(ElementIOControllerModule.class, options);
	}
	// endregion Constructors
	
	// region getters
	public ElementIOControllerModule get(Long elementTypeId, Long elementId, Long modduleTypeId,
			Long moduleId, Boolean fillObject) {

		return (ElementIOControllerModule) getFromCache(new String[] { String.valueOf(elementTypeId),
				String.valueOf(elementId), String.valueOf(modduleTypeId), String.valueOf(moduleId) },
				fillObject);
	}

	public List<ElementIOControllerModule> gets(Long elementTypeId, Long elementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(elementTypeId),
				String.valueOf(elementId), null, null }, fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (ElementIOControllerModule) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	
	@Override
	public List<ElementIOControllerModule> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (ElementIOControllerModule) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters
	
	// region puts
	@Override
	public ElementIOControllerModule put(ElementIOControllerModule entity) {
		
		return (ElementIOControllerModule) putFromCache(entity);
	}

	@Override
	public List<ElementIOControllerModule> put(List<ElementIOControllerModule> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementIOControllerModule) be).collect(Collectors.toList());
	}
	// endregion puts
	
	// region dels
	@Override
	public ElementIOControllerModule del(ElementIOControllerModule entity) {

		return (ElementIOControllerModule) delFromCache(entity);

	}

	@Override
	public List<ElementIOControllerModule> del(List<ElementIOControllerModule> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementIOControllerModule) be).collect(Collectors.toList());
	}
	// endregion dels
	
	@Override
	public ElementIOControllerModule create(ElementIOControllerModule entity) {
		
		ElementIOControllerModule elementIOControllerModule = null;
		
		try {

			elementIOControllerModule = create(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return elementIOControllerModule;
	}

	@Override
	public ElementIOControllerModule create(ElementIOControllerModule elementIOControllerModule, Connection connection,
	        List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

	    Connection conn = null;
	    boolean closeConnection = false;
	    boolean commit = false;
	    conn = connection;
	    ElementIOControllerModule elementIOControllerModuleResult = null;

	    try {

	        if (conn == null) {

	            conn = DataSourceConnection.getInstance().getConnection();
	            conn.setAutoCommit(false);
	            closeConnection = true;
	            commit = true;
	            persistOperations = new ArrayList<>();
	        }

	        // Insert ElementIOControllerModuleInserted in BBDD
	        ElementIOControllerModule elementIOControllerModuleInserted = null;

	        elementIOControllerModuleInserted = (ElementIOControllerModule) getObjectDao().insert(conn, elementIOControllerModule);

	        if (elementIOControllerModuleInserted == null) {

	            throw new EntityDBException(EntityDBException.Operation.INSERT, elementIOControllerModule);
	        }

	        persistOperations.add(new MutablePair<>(PersistOperation.PUT, elementIOControllerModuleInserted));

	        if (commit) {
	            conn.commit();

	            persistOperations(persistOperations);

	            elementIOControllerModuleResult = get(elementIOControllerModuleInserted.getElementTypeId(),
	                    elementIOControllerModuleInserted.getElementId(), elementIOControllerModuleInserted.getModuleTypeId(),
	                    elementIOControllerModuleInserted.getModuleId(), false);
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
	        throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, elementIOControllerModule);

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

	    return elementIOControllerModuleResult;
	}

	@Override
	public ElementIOControllerModule clone(ElementIOControllerModule entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementIOControllerModule clone(ElementIOControllerModule entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementIOControllerModule update(ElementIOControllerModule entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementIOControllerModule update(ElementIOControllerModule entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementIOControllerModule delete(ElementIOControllerModule entity) {
		
		ElementIOControllerModule elementIOControllerModule = null;
		
		try {

			elementIOControllerModule = delete(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return elementIOControllerModule;
	}

	@Override
	public ElementIOControllerModule delete(ElementIOControllerModule entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ElementIOControllerModule result = null;
		ElementIOControllerModule aux = null;
		try {

			if (entity == null) {

				throw new NullPointerException("ElementIOControllerModule is null");
			}

			aux = get(entity.getElementTypeId(), entity.getElementId(), entity.getModuleTypeId(), entity.getModuleId(),
					false);

			if (aux == null) {

				throw new NullPointerException("ElementIOControllerModule not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete ElementIOControllerModule in BBDD
			ElementIOControllerModule deleted = null;

			deleted = (ElementIOControllerModule) getObjectDao().delete(conn, aux);

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
