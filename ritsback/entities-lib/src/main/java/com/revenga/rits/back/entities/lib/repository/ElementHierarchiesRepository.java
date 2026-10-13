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
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementHierarchiesRepository extends EntityRepository
		implements EntityCacheable<ElementHierarchy>, EntityPersistent<ElementHierarchy> {

	// region Constructors
	public ElementHierarchiesRepository() {

		super(ElementHierarchy.class);
	}

	public ElementHierarchiesRepository(Boolean cacheable) {

		super(ElementHierarchy.class, cacheable);
	}

	public ElementHierarchiesRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ElementHierarchy.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementHierarchy get(Long parentElementTypeId, Long parentElementId, Long childElemenTypetId,
			Long childElementId, Boolean fillObject) {

		return (ElementHierarchy) getFromCache(new String[] { String.valueOf(parentElementTypeId),
				String.valueOf(parentElementId), String.valueOf(childElemenTypetId), String.valueOf(childElementId) },
				fillObject);
	}

	public List<ElementHierarchy> getsByParent(Long parentElementTypeId, Long parentElementId, Long childElementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(parentElementTypeId), String.valueOf(parentElementId), String.valueOf(childElementTypeId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<ElementHierarchy> getsByParent(Long parentElementTypeId, Long parentElementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(parentElementTypeId), String.valueOf(parentElementId), null, null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ElementHierarchy> getsByParent(Long parentElementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(parentElementTypeId), null, null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ElementHierarchy> getsByChild(Long parentElementTypeId, Long childElementTypeId, Long childElementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(parentElementTypeId), null, String.valueOf(childElementTypeId), String.valueOf(childElementId) },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	public List<ElementHierarchy> getsByChild(Long childElementTypeId, Long childElementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { null, null, String.valueOf(childElementTypeId), String.valueOf(childElementId) },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ElementHierarchy> getsByChild(Long childElementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { null, null, String.valueOf(childElementTypeId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ElementHierarchy> getsByElementType(Long parentElementTypeId, Long childElementTypeId,
			Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(parentElementTypeId), null, String.valueOf(childElementTypeId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<ElementHierarchy> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public ElementHierarchy put(ElementHierarchy entity) {

		return (ElementHierarchy) putFromCache(entity);
	}

	@Override
	public List<ElementHierarchy> put(List<ElementHierarchy> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
	}

	@Override
	public ElementHierarchy del(ElementHierarchy entity) {

		return (ElementHierarchy) delFromCache(entity);
	}

	@Override
	public List<ElementHierarchy> del(List<ElementHierarchy> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ElementHierarchy.class::cast).collect(Collectors.toList());
	}

	@Override
	public ElementHierarchy create(ElementHierarchy elementHierarchy) {

		try {

			return create(elementHierarchy, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ElementHierarchy create(ElementHierarchy elementHierarchy, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ElementHierarchy elementHierarchyResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert Element in BBDD
			ElementHierarchy elementHierarchyInserted = null;

			elementHierarchyInserted = (ElementHierarchy) getObjectDao().insert(conn, elementHierarchy);

			if (elementHierarchyInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, elementHierarchy);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, elementHierarchyInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				elementHierarchyResult = get(elementHierarchyInserted.getParentElementTypeId(),
						elementHierarchyInserted.getParentElementId(), elementHierarchyInserted.getChildElementTypeId(),
						elementHierarchyInserted.getChildElementId(), false);
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, elementHierarchy);

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

		return elementHierarchyResult;
	}

	@Override
	public ElementHierarchy clone(ElementHierarchy entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementHierarchy clone(ElementHierarchy entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementHierarchy update(ElementHierarchy entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementHierarchy update(ElementHierarchy entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementHierarchy delete(ElementHierarchy elementHierarchy) {

		try {

			return delete(elementHierarchy, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ElementHierarchy delete(ElementHierarchy elementHierarchy, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ElementHierarchy elementHierarchyResult = null;
		ElementHierarchy elementHierarchyAux = null;

		try {

			if (elementHierarchy == null || !ObjectUtils.allNotNull(elementHierarchy.getParentElementId(),
					elementHierarchy.getChildElementId())) {

				throw new NullPointerException("ElementHierarchy is null");
			}

			elementHierarchyAux = get(elementHierarchy.getParentElementTypeId(), elementHierarchy.getParentElementId(),
					elementHierarchy.getChildElementTypeId(), elementHierarchy.getChildElementId(), false);

			if (elementHierarchyAux == null) {

				throw new NullPointerException("ElementHierarchy not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete ElementHierarchy in BBDD
			ElementHierarchy elementHierarchyDeleted = null;

			elementHierarchyDeleted = (ElementHierarchy) getObjectDao().delete(conn, elementHierarchyAux);

			if (elementHierarchyDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, elementHierarchyAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, elementHierarchyAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			elementHierarchyResult = elementHierarchyDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, elementHierarchy);

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

		return elementHierarchyResult;
	}

}
