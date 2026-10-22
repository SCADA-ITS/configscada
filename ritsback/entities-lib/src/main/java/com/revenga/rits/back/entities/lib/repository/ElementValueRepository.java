package com.revenga.rits.back.entities.lib.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementValueRepository extends EntityRepository
		implements EntityCacheable<ElementValue>, EntityPersistent<ElementValue> {
	
	private static final String HIST_FIELD_TIMESTAMP = "timestamp_at";
	private static final String HIST_FIELD_DATE = "date";
	private static final String FROM = " FROM ";
	private static final String WHERE_ELEMENTS = " WHERE f_element_id IN (";
	private static final String ELEMENT = "f_element_id";

	// region Constructors
	public ElementValueRepository() {

		super(ElementValue.class);
	}

	public ElementValueRepository(Boolean cacheable) {

		super(ElementValue.class, cacheable);
	}
	
	public ElementValueRepository(EntityRepositoryOptions options) {

		super(ElementValue.class, options);
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
	
	private String getColumnNameForTimestampData(Connection conn, String table) throws SQLException {
		
		boolean foundTimestampField = false;
		boolean foundDateField = false;
		
		try (Statement st = conn.createStatement()) {
			
			String sql = String.format(
					"SELECT column_name FROM information_schema.columns WHERE table_schema = '%s' AND table_name = '%s' AND column_name IN ('%s', '%s')",
					table.split("\\.")[0], table.split("\\.")[1], HIST_FIELD_TIMESTAMP, HIST_FIELD_DATE);
		
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				
				if (rs.getString("column_name").equals(HIST_FIELD_TIMESTAMP)) {
					
					foundTimestampField = true;
				}
				else if (rs.getString("column_name").equals(HIST_FIELD_DATE)) {
					
					foundDateField = true;
				}
			}
		}

		return foundTimestampField ? HIST_FIELD_TIMESTAMP : (foundDateField ? HIST_FIELD_DATE : null);
	}

	public List<ElementValue> getHistoricalData(Map<Long, Pair<String, Map<ElementTypeParam, String>>> mapAdapterInfo,
			Map<Long, Pair<Set<Long>, Set<ElementTypeParam>>> mapElementTypeParam,
			Map<Long, Map<Long, Set<ElementValue>>> mapElemementValues, Long initDate, Long finalDate) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<ElementValue> elementValues = new ArrayList<>();

		Connection conn = null;

		Statement st = null;

		try {

			conn = DataSourceConnection.getInstance().getConnection();

			st = conn.createStatement();

			for (Map.Entry<Long, Pair<Set<Long>, Set<ElementTypeParam>>> mapElementTypeParamElementType : mapElementTypeParam
					.entrySet()) {

				Pair<Set<Long>, Set<ElementTypeParam>> pair = mapElementTypeParamElementType.getValue();

				List<ElementTypeParam> elementTypeParams = new ArrayList<>(pair.getRight());

				Map<String, ElementTypeParam> aliasElementTypeParamMap = new HashMap<>();
				
				Pair<String, Map<ElementTypeParam, String>> pairParam = mapAdapterInfo.get(mapElementTypeParamElementType.getKey());
				
				String columnNameForTimestampData = getColumnNameForTimestampData(conn, pairParam.getKey());
				
				if (!StringUtils.isEmpty(columnNameForTimestampData) && pairParam != null) {
					
					StringBuilder dbQuery = new StringBuilder();
					StringBuilder dbQueryUnion = new StringBuilder();
					
					dbQuery.append(String.format("SELECT f_element_id, %s ", columnNameForTimestampData));
					dbQueryUnion.append(String.format(" UNION ALL SELECT f_element_id, %s", columnNameForTimestampData));
					
					for (ElementTypeParam elementTypeParam : elementTypeParams) {
						
						Map<ElementTypeParam, String> pairParamRight = pairParam.getRight();
						
						if (!MapUtils.isEmpty(pairParamRight)) {
						
							String param = pairParamRight.get(elementTypeParam);
							
							if (!StringUtils.isEmpty(param)) {
								
								param = param.toLowerCase();
								dbQuery.append(", " + param);
								dbQueryUnion.append(", " + param);
								aliasElementTypeParamMap.put(param, elementTypeParam);
							}
						}
					}
					
					dbQuery.append(FROM);
					dbQuery.append(pairParam.getKey());
					dbQueryUnion.append(FROM);
					dbQueryUnion.append(pairParam.getKey());
					dbQuery.append(WHERE_ELEMENTS);
					dbQueryUnion.append(WHERE_ELEMENTS);
					
					List<Long> elementIds = new ArrayList<>(pair.getLeft());

					for (int i = 1; i <= elementIds.size(); i++) {

						dbQuery.append(elementIds.get(i - 1));
						dbQueryUnion.append(elementIds.get(i - 1));

						if (elementIds.size() > 1 && i < elementIds.size()) {

							dbQuery.append(", ");
							dbQueryUnion.append(", ");
						}
					}

					dbQuery.append(")");
					dbQueryUnion.append(")");

					if (initDate != null) {

						Timestamp initTimeStamp = new Timestamp(initDate);

						dbQuery.append(String.format(" AND %s >= '%s'", columnNameForTimestampData, dateFormat.format(initTimeStamp)));
					}

					if (finalDate != null) {

						Timestamp finalTimeStamp = new Timestamp(finalDate);

						dbQuery.append(String.format(" AND %s <= '%s'", columnNameForTimestampData, dateFormat.format(finalTimeStamp)));

					}
					
					if (initDate != null) {
						
						dbQueryUnion.append(String.format(" AND %s = (SELECT MAX(%s)", columnNameForTimestampData, columnNameForTimestampData));
						dbQueryUnion.append(FROM);
						dbQueryUnion.append(pairParam.getKey());
						
						dbQueryUnion.append(WHERE_ELEMENTS);

						for (int i = 1; i <= elementIds.size(); i++) {

							dbQueryUnion.append(elementIds.get(i - 1));

							if (elementIds.size() > 1 && i < elementIds.size()) {

								dbQueryUnion.append(", ");
							}
						}
						
						dbQueryUnion.append(")");
						
						dbQueryUnion.append(String.format(" AND %s < '%s')", columnNameForTimestampData, dateFormat.format(new Timestamp(initDate))));
						
						dbQuery.append(dbQueryUnion);
						
					}
					
					ResultSet rs = st.executeQuery(dbQuery.toString());

					ResultSetMetaData metaData = rs.getMetaData();

					while (rs.next()) {

						for (int i = 3; i <= metaData.getColumnCount(); i++) {

							if (rs.getString(i) != null && !StringUtils.isEmpty(metaData.getColumnName(i))) {

								ElementTypeParam elementTypeParam = aliasElementTypeParamMap
										.get(metaData.getColumnName(i).toLowerCase());
								
								if (elementTypeParam != null) {
									
									Map<Long, Set<ElementValue>> m = mapElemementValues
											.get(mapElementTypeParamElementType.getKey());
								
									if (m != null) {
										
										Set<ElementValue> elementValuesPerElement = m.get(rs.getLong(ELEMENT));

										ElementValue elementValue = new ElementValue();

										elementValue.setElementId(rs.getLong(ELEMENT));
										elementValue.setElementTypeId(elementTypeParam.getElementTypeId());
										elementValue.setElementTypeParamId(elementTypeParam.getId());
										elementValue.setParamTypeId(elementTypeParam.getParamTypeId());

										if (elementValuesPerElement.contains(elementValue)) {

											elementValue.setCreatedAt(rs.getTimestamp(columnNameForTimestampData).getTime());

											elementValue.setValue(rs.getString(i));
											elementValues.add(elementValue);
										}
									}
								}
							}
						}
					}
				}
			}

		} catch (SQLException | IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
			return elementValues;

		} finally {

			if (conn != null) {

				try {

					conn.close();
				} catch (SQLException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));

				}
			}

			if (st != null) {

				try {

					st.close();
				} catch (SQLException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));

				}
			}
		}
		
		return elementValues;
	}
}
