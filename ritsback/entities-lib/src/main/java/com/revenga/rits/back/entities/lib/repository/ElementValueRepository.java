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
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementValueRepository extends EntityRepository
		implements EntityCacheable<ElementValue>, EntityPersistent<ElementValue> {

	// region Constructors
	public ElementValueRepository() {

		super(ElementValue.class);
	}

	public ElementValueRepository(Boolean cacheable) {

		super(ElementValue.class, cacheable);
	}

	public ElementValueRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ElementValue.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementValue get(Long elementTypeId, Long elementId, Long elementTypeParamId, Long paramTypeId,
			Boolean fillObject) {

		return (ElementValue) getFromCache(new String[] { String.valueOf(elementTypeId), String.valueOf(elementId),
				String.valueOf(elementTypeParamId), String.valueOf(paramTypeId) }, fillObject);
	}

	public List<ElementValue> gets(Long elementTypeId, Long elementId, Long paramTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(elementTypeId),
				String.valueOf(elementId), null, String.valueOf(paramTypeId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ElementValue> gets(Long elementTypeId, Long elementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(elementTypeId), String.valueOf(elementId), null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ElementValue> getsByElementType(Long elementTypeId, Long paramTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(elementTypeId), null, null, String.valueOf(paramTypeId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ElementValue> getsByElementType(Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(
				new String[] { String.valueOf(elementTypeId), null, null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ElementValue> getByElementTypeParam(Long elementTypeId, Long elementTypeParamId, Long paramTypeId,
			Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(elementTypeId), null,
				String.valueOf(elementTypeParamId), String.valueOf(paramTypeId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<ElementValue> getByParamType(Long paramTypeId,
			Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, null,
				null, String.valueOf(paramTypeId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	@Override
	public List<ElementValue> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ElementValue.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementValue put(ElementValue entity) {

		return (ElementValue) putFromCache(entity);
	}

	@Override
	public List<ElementValue> put(List<ElementValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(ElementValue.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public ElementValue del(ElementValue entity) {

		return (ElementValue) delFromCache(entity);

	}

	@Override
	public List<ElementValue> del(List<ElementValue> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(ElementValue.class::cast).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public ElementValue create(ElementValue elementValue) {
		try {
			return create(elementValue, null, null);
		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ElementValue create(ElementValue elementValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ElementValue elementValueResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert ElementValue in BBDD
			ElementValue elementValueInserted = null;

			if (elementValue.getParamTypeId().equals(Long.valueOf(ParamType.CONFIG))) {

				elementValueInserted = (ElementValue) getObjectDao().insert(conn, elementValue);

				if (elementValueInserted == null) {

					throw new EntityDBException(EntityDBException.Operation.INSERT, elementValue);
				}
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, elementValue));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				if (elementValueInserted != null) {

					elementValueResult = get(elementValueInserted.getElementTypeId(), elementValueInserted.getElementId(),
							elementValueInserted.getElementTypeParamId(), elementValueInserted.getParamTypeId(), false);
				} else {

					elementValueResult = get(elementValue.getElementTypeId(), elementValue.getElementId(),
							elementValue.getElementTypeParamId(), elementValue.getParamTypeId(), false);
				}
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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, elementValue);

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

		return elementValueResult;

	}

	@Override
	public ElementValue clone(ElementValue entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementValue clone(ElementValue entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementValue update(ElementValue elementValue) {

		try {

			return update(elementValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ElementValue update(ElementValue elementValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ElementValue elementValueResult = null;
		ElementValue elementValueAux = null;

		try {

			if (elementValue == null || !ObjectUtils.allNotNull(elementValue.getElementTypeId(),
					elementValue.getElementId(), elementValue.getElementTypeParamId(), elementValue.getParamTypeId())) {

				throw new NullPointerException("elementValue is null");
			}

			elementValueAux = get(elementValue.getElementTypeId(), elementValue.getElementId(),
					elementValue.getElementTypeParamId(), elementValue.getParamTypeId(), false);

			if (elementValueAux == null) {

				throw new NullPointerException("elementValue not found");
			}

			if (!elementValueAux.getParamTypeId().equals(Long.valueOf(ParamType.CONFIG))) {

				log.warn("Only param config can be updated");
				throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, elementValue);
			}

			if (elementValue.getValue() != null)
				elementValueAux.setValue(elementValue.getValue());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Update elementValue in BBDD
			ElementValue elementValueUpdated = null;

			elementValueUpdated = (ElementValue) getObjectDao().update(conn, elementValueAux);
			if (elementValueUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, elementValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, elementValueUpdated));

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
			}

			elementValueResult = elementValueUpdated;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, elementValue);

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

		return elementValueResult;
	}

	@Override
	public ElementValue delete(ElementValue elementValue) {

		try {

			return delete(elementValue, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public ElementValue delete(ElementValue elementValue, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		ElementValue elementValueResult = null;
		ElementValue elementValueAux = null;

		try {

			if (elementValue == null || !ObjectUtils.allNotNull(elementValue.getElementTypeId(),
					elementValue.getElementId(), elementValue.getElementTypeParamId(), elementValue.getParamTypeId())) {

				throw new NullPointerException("elementValue is null");
			}

			elementValueAux = get(elementValue.getElementTypeId(), elementValue.getElementId(),
					elementValue.getElementTypeParamId(), elementValue.getParamTypeId(), false);
			if (elementValueAux == null) {

				throw new NullPointerException("elementValue not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete elementValue in BBDD
			ElementValue elementValueDeleted = null;

			elementValueDeleted = (ElementValue) getObjectDao().delete(conn, elementValueAux);

			if (elementValueDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, elementValueAux);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.DELETE, elementValueAux));

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
			}

			elementValueResult = elementValueDeleted;

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, elementValue);

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

		return elementValueResult;
	}

}
