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
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementIOControllerModule;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.StretchLocation;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LocationRepository extends EntityRepository implements EntityCacheable<Location>, EntityPersistent<Location> {

	// region Constructors
	
	private static final int DEFAULT_POSITION = 1;
	
	public LocationRepository() {

		super(Location.class);
	}
	
	public LocationRepository(EntityRepositoryOptions options) {

		super(Location.class, options);
	}


	public LocationRepository(Boolean cacheable) {

		super(Location.class, cacheable);
	}

	public LocationRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(Location.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public Location get(Long id, Boolean fillObject) {

		return (Location) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}

	@Override
	public List<Location> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (Location) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	@Override
	public Location put(Location entity) {
		return (Location) putFromCache(entity);
	}

	@Override
	public List<Location> put(List<Location> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (Location) be).collect(Collectors.toList());
	}

	@Override
	public Location del(Location entity) {

		return (Location) delFromCache(entity);
	}

	@Override
	public List<Location> del(List<Location> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (Location) be).collect(Collectors.toList());
	}
	
	@Override
	public Location create(Location location) {
	    try {
	    	
	        return create(location, null, null);
	        
	    } catch (EntityPersistenceException e) {
	    	
	        log.error(e.getMessage());
	        log.debug(ExceptionUtils.getStackTrace(e));
	        
	        return null;
	    }
	}

	@Override
	public Location create(Location location, Connection connection, List<Pair<PersistOperation, BasicElement>> persistOperations)
	        throws EntityPersistenceException {

	    Connection conn = null;
	    boolean closeConnection = false;
	    boolean commit = false;
	    conn = connection;
	    Location locationResult = null;

	    try {

	        if (conn == null) {
	        	
	            conn = DataSourceConnection.getInstance().getConnection();
	            conn.setAutoCommit(false);
	            closeConnection = true;
	            commit = true;
	            persistOperations = new ArrayList<>();
	        }

	        // Insert location in BBDD
	        Location locationInserted = null;
			locationInserted = (Location) getObjectDao().insert(conn, location);

			if (locationInserted == null) {
				throw new EntityDBException(EntityDBException.Operation.INSERT, location);
			}

			if (location.getStretchLocations() != null && !CollectionUtils.isEmpty(getRepositories())) {

				StretchLocationRepository stretchLocationRepository = (StretchLocationRepository) getRepository(StretchLocation.class);

				if (stretchLocationRepository != null) {

					for (StretchLocation stretchLocation : location.getStretchLocations()) {

						stretchLocation.setLocationId(locationInserted.getId());
						
						//Set 1 to position in null case.
						if (stretchLocation.getPosition() == null) {
							
							stretchLocation.setPosition(DEFAULT_POSITION);
						}
						
						stretchLocationRepository.create(stretchLocation, conn, persistOperations);
					}
				}
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, locationInserted));

			if (commit) {

				conn.commit();
				persistOperations(persistOperations);
				locationResult = get(locationInserted.getId(), true);
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
	        throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, location);

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

	    return locationResult;
	}

	@Override
	public Location clone(Location entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location clone(Location entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location update(Location entity) {
		try {

			return update(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Location update(Location entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Location entityResult = null;
		Location entityAux = null;

		try {

			if (entity == null || entity.getId() == null) {

				throw new NullPointerException("Location is null");
			}

			entityAux = get(entity.getId(), false);

			if (entityAux == null) {

				throw new NullPointerException("Location not found");
			}

			if (entity.getAlias() != null)
				entityAux.setAlias(entity.getAlias());
			if (entity.getDescription() != null)
				entityAux.setDescription(entity.getDescription());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}


			// StretchLocationRepository
			if (entity.getStretchLocations() != null && !CollectionUtils.isEmpty(getRepositories())) {
				
				StretchLocationRepository entityRepository = (StretchLocationRepository) getRepository(StretchLocation.class);

				if (entityRepository != null) {

					List<StretchLocation> stretchLocations = entityRepository.getByLocation(entityAux.getId(), false);

					if (!CollectionUtils.isEmpty(stretchLocations)) {

						for (StretchLocation stretchLocation : stretchLocations) {

							entityRepository.delete(stretchLocation, conn, persistOperations);
						}
					}
					
					for (StretchLocation stretchLocation : entity.getStretchLocations()) {

						stretchLocation.setLocationId(entity.getId());
						
						//Set 1 to position in null case.
						if (stretchLocation.getPosition() == null) {
							
							stretchLocation.setPosition(DEFAULT_POSITION);
						}
						
						entityRepository.create(stretchLocation, conn, persistOperations);
					}
					
				}
			}
			
			// Update element in BBDD
			Location entityUpdated = null;

			entityUpdated = (Location) getObjectDao().update(conn, entityAux);
			if (entityUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, entityAux);
			}

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
				
				putFromCache((BasicElement) CacheModelHelper.cloneBasicElementForPut(entityUpdated));
			}

			entityResult = entityUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, entity);

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

	@Override
	public Location delete(Location entity) {
		try {

			return delete(entity, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Location delete(Location entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Location entityResult = null;
		Location entityAux = null;

		try {

			if (entity == null || entity.getId() == null) {

				throw new NullPointerException("Location is null");
			}

			entityAux = get(entity.getId(), false);

			if (entityAux == null) {

				throw new NullPointerException("Location not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all StretchLocationRepository
			if (!CollectionUtils.isEmpty(getRepositories())) {
				
				StretchLocationRepository entityRepository = (StretchLocationRepository) getRepository(StretchLocation.class);

				if (entityRepository != null) {

					List<StretchLocation> stretchLocations = entityRepository.getByLocation(entityAux.getId(), false);

					if (!CollectionUtils.isEmpty(stretchLocations)) {

						for (StretchLocation stretchLocation : stretchLocations) {

							entityRepository.delete(stretchLocation, conn, persistOperations);
						}
					}
				}
			}
		
			// Delete element in BBDD
			Location locationDeleted = null;

			locationDeleted = (Location) getObjectDao().delete(conn, entityAux);

			if (locationDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, entityAux);
			}

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
				
				delFromCache((BasicElement) CacheModelHelper.cloneBasicElementForPut(locationDeleted));
			}

			entityResult = locationDeleted;

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
