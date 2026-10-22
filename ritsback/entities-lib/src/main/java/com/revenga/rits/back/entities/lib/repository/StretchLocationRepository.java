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

import com.revenga.rits.back.cache.core.helper.CacheModelHelper;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.StretchLocation;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class StretchLocationRepository extends EntityRepository implements EntityCacheable<StretchLocation>, EntityPersistent<StretchLocation> {

	// region Constructors
	public StretchLocationRepository() {

		super(StretchLocation.class);
	}

	public StretchLocationRepository(Boolean cacheable) {

		super(StretchLocation.class, cacheable);
	}

	public StretchLocationRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(StretchLocation.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public StretchLocation get(Long stretchId, Long locationId, Boolean fillObject) {

		return (StretchLocation) getFromCache(new String[] { String.valueOf(stretchId), String.valueOf(locationId) },
				fillObject);
	}

	@Override
	public List<StretchLocation> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (StretchLocation) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<StretchLocation> getByStretch(Long stretchId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(stretchId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (StretchLocation) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<StretchLocation> getByLocation(Long locationId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, String.valueOf(locationId) },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (StretchLocation) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public StretchLocation put(StretchLocation entity) {
		return (StretchLocation) putFromCache(entity);
	}

	@Override
	public List<StretchLocation> put(List<StretchLocation> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (StretchLocation) be).collect(Collectors.toList());
	}

	@Override
	public StretchLocation del(StretchLocation entity) {

		return (StretchLocation) delFromCache(entity);
	}

	@Override
	public List<StretchLocation> del(List<StretchLocation> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (StretchLocation) be).collect(Collectors.toList());
	}
	
	@Override
	public StretchLocation create(StretchLocation stretchLocation) {
	    try {
	    	
	        return create(stretchLocation, null, null);
	        
	    } catch (EntityPersistenceException e) {
	    	
	        log.error(e.getMessage());
	        log.debug(ExceptionUtils.getStackTrace(e));
	        
	        return null;
	    }
	}

	@Override
	public StretchLocation create(StretchLocation stretchLocation, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
	        throws EntityPersistenceException {

	    Connection conn = null;
	    boolean closeConnection = false;
	    boolean commit = false;
	    conn = connection;
	    StretchLocation stretchLocationResult = null;

	    try {

	        if (conn == null) {
	            conn = DataSourceConnection.getInstance().getConnection();
	            conn.setAutoCommit(false);
	            closeConnection = true;
	            commit = true;
	            persistOperations = new ArrayList<>();
	        }

	        // Insert stretchLocation in BBDD
	        StretchLocation stretchLocationInserted = null;
	        stretchLocationInserted = (StretchLocation) getObjectDao().insert(conn, stretchLocation);

	        if (stretchLocationInserted == null) {
	            throw new EntityDBException(EntityDBException.Operation.INSERT, stretchLocation);
	        }

	        persistOperations.add(new MutablePair<>(PersistOperation.PUT, stretchLocationInserted));

	        if (commit) {

	            conn.commit();
	            persistOperations(persistOperations);
	            stretchLocationResult = get(stretchLocationInserted.getStretchId(), stretchLocationInserted.getLocationId(),false);
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
	        throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, stretchLocation);

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

	    return stretchLocationResult;
	}

	@Override
	public StretchLocation clone(StretchLocation entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StretchLocation clone(StretchLocation entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StretchLocation update(StretchLocation entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StretchLocation update(StretchLocation entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StretchLocation delete(StretchLocation entity) {
		try {

			return delete(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public StretchLocation delete(StretchLocation entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		StretchLocation entityResult = null;
		StretchLocation entityAux = null;

		try {

			if (entity == null || entity.getStretchId() == null || entity.getLocationId() == null) {

				throw new NullPointerException("StretchLocation is null");
			}

			entityAux = get(entity.getStretchId(), entity.getLocationId(), false);

			if (entityAux == null) {

				throw new NullPointerException("StretchLocation not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete element in BBDD
			StretchLocation entityDeleted = null;

			entityDeleted = (StretchLocation) getObjectDao().delete(conn, entityAux);

			if (entityDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, entityAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, entityAux));
			
			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			entityResult = entityDeleted;

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
		return entityResult;

	}


}
