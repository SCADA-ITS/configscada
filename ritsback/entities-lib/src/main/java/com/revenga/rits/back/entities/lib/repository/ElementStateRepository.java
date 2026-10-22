package com.revenga.rits.back.entities.lib.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementStateRepository extends EntityRepository implements EntityCacheable<ElementState> {

	private static final String BASE_QUERY = "SELECT created_at, element_type_id, element_id, element, state_id, "
			+ "alias FROM hist.element_states WHERE element_type_id = %elementTypeId AND element_id IN(%elementIds) ";

	private static final String AND = " AND ";
	private static final String GREATER_OR_EQUAL = " >= ";
	private static final String LESS_OR_EQUAL = " <= ";
	private static final String EQUALS = " = ";
	static final String MAX_CREATED_AT = "(SELECT MAX(created_at) FROM hist.element_states  "
			+ "WHERE element_type_id = %elementTypeId AND element_id IN( %elementIds ) AND created_at < %createdAt )";
	
	private static final String CREATED_AT = "created_at";
	private static final String ELEMENT_TYPE_ID = "element_type_id";
	private static final String ELEMENT_ID = "element_id";
	private static final String STATE_ID = "state_id";
	
	
	private static final String UNION = " UNION ";
	
	// region Constructors
	public ElementStateRepository() {

		super(ElementState.class);
	}

	public ElementStateRepository(Boolean cacheable) {

		super(ElementState.class, cacheable);
	}
	
	public ElementStateRepository(EntityRepositoryOptions options) {

		super(ElementState.class, options);
	}

	public ElementStateRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(ElementState.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public ElementState get(Long elementTypeId, Long elementId, Boolean fillObject) {

		return (ElementState) getFromCache(new String[] { String.valueOf(elementTypeId), String.valueOf(elementId) },
				fillObject);
	}

	public List<ElementState> getsByElementType(Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(elementTypeId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<ElementState> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (ElementState) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public ElementState put(ElementState entity) {

		return (ElementState) putFromCache(entity);
	}

	@Override
	public List<ElementState> put(List<ElementState> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementState) be).collect(Collectors.toList());
	}
	
	// endregion puts

	// region dels
	@Override
	public ElementState del(ElementState entity) {

		return (ElementState) delFromCache(entity);

	}

	@Override
	public List<ElementState> del(List<ElementState> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (ElementState) be).collect(Collectors.toList());
	}

	
	public List<ElementState> getHistoricalData(List<String> elementIds, Long initDate, Long finalDate) {

		List<ElementState> elementStates = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Connection conn = null;
		Statement st = null;

		try {
			
			conn = DataSourceConnection.getInstance().getConnection();
			
			st = conn.createStatement();
			
			Long elementTypeId = null;

			if (CollectionUtils.isNotEmpty(elementIds)) {

				StringBuilder dbQuery = new StringBuilder();
				StringBuilder targetElementIds = new StringBuilder();
				dbQuery.append(BASE_QUERY);

				for (int i = 0; i < elementIds.size(); i++) {

					if (elementIds.get(i) != null) {

						Key key = new Key(elementIds.get(i));

						if (key.getClazz() == Element.class) {

							Element element = new Element();
							
							element.setPks(key.getIds());
							
							if (i == 0) {
								
								elementTypeId = element.getElementTypeId();
								targetElementIds.append(element.getId());
							}
							
							if (i != 0 && elementIds.size() > 1) {
								
								targetElementIds.append(",");
								targetElementIds.append(element.getId());
							}
						}
					}
				}
				
				String firstQueryPart = dbQuery.toString();
				
				firstQueryPart = firstQueryPart.replace("%elementTypeId", String.valueOf(elementTypeId));
				
				firstQueryPart = firstQueryPart.replace("%elementIds", targetElementIds);
				
				StringBuilder finalFirstQueryPart = new StringBuilder();
				
				StringBuilder finalSecondQueryPart = new StringBuilder();
				
				finalFirstQueryPart.append(firstQueryPart);
				
				finalSecondQueryPart.append(firstQueryPart);
				
				if (initDate != null) {
					
					Timestamp initTimeStamp = new Timestamp(initDate);
					finalFirstQueryPart.append(AND);
					finalFirstQueryPart.append(CREATED_AT);
					finalFirstQueryPart.append(GREATER_OR_EQUAL);
					finalFirstQueryPart.append("'"+dateFormat.format(initTimeStamp)+"'");
					
					//second part
					finalSecondQueryPart.append(AND);
					finalSecondQueryPart.append(CREATED_AT);
					finalSecondQueryPart.append(EQUALS);
					
					String finalQueryPart = MAX_CREATED_AT.replace("%elementTypeId", String.valueOf(elementTypeId));
					finalQueryPart = finalQueryPart.replace("%elementIds", targetElementIds);
					finalQueryPart = finalQueryPart.replace("%createdAt", "'"+dateFormat.format(initTimeStamp)+"'");
					
					finalSecondQueryPart.append(finalQueryPart);
				}
				
				if (finalDate != null) {

					Timestamp finalTimeStamp = new Timestamp(finalDate);
					finalFirstQueryPart.append(AND);
					finalFirstQueryPart.append(CREATED_AT);
					finalFirstQueryPart.append(LESS_OR_EQUAL);
					finalFirstQueryPart.append("'"+dateFormat.format(finalTimeStamp)+"'");
				}
				
				finalFirstQueryPart.append(UNION);
				finalFirstQueryPart.append(finalSecondQueryPart);
				
				ResultSet rs = st.executeQuery(finalFirstQueryPart.toString());

				while (rs.next()) {
					
					ElementState elementState = new ElementState();
					elementState.setElementTypeId(rs.getLong(ELEMENT_TYPE_ID));
					elementState.setElementId(rs.getLong(ELEMENT_ID));	
					elementState.setStateId(rs.getLong(STATE_ID));	
					elementState.setCreatedAt(rs.getTimestamp(CREATED_AT) != null ? rs.getTimestamp(CREATED_AT).getTime() : null);	
					
					elementStates.add(elementState);
				}
			}
			
	

		} catch (SQLException | IOException | IllegalArgumentException | SecurityException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
			return new ArrayList<>();
			
		} finally {
			
			try {
				
				if (conn != null && st != null) {
					
					conn.close();
					st.close();
				}
				
			} catch (SQLException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return elementStates;
	}
	
}
