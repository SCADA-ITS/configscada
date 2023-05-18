package com.revenga.rits.front.openits.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.util.StringUtil;

import lombok.Data;

@Data
public class ElementHistoryDTO implements Serializable {

	private static final long serialVersionUID = -6674150215967521370L;
	
	private List<String> elementIds;
	private Long dateIni;
	private Long dateEnd;
	
	public enum ParamOperation { EQUALS, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL, IN }
	
	public static final String PARAM_COLUMN_NAME = "columnName";
	public static final String PARAM_OPERATION = "operation";
	public static final String PARAM_VALUE = "value";
	
	public enum ParamFieldName { ELEMENT_TYPE_ID, ELEMENT_ID, CREATED_AT }
	
	public static final Map<ParamFieldName, String> getStateFields() {
		
		Map<ParamFieldName, String> map = new EnumMap<>(ParamFieldName.class);
		
		map.put(ParamFieldName.ELEMENT_TYPE_ID, "\"element_type_id\"");
		map.put(ParamFieldName.ELEMENT_ID, "\"element_id\"");
		map.put(ParamFieldName.CREATED_AT, "\"created_at\"");
		
		return map;
	}
	
	public static final Map<ParamFieldName, String> getAlarmFields() { 
		
		Map<ParamFieldName, String> map = new EnumMap<>(ParamFieldName.class);
		
		map.put(ParamFieldName.ELEMENT_TYPE_ID, "\"target_element_type_id\"");
		map.put(ParamFieldName.ELEMENT_ID, "\"target_element_id\"");
		map.put(ParamFieldName.CREATED_AT, "\"created_at\"");
		
		return map;
	}
	
	public static List<Map<String, Object>> getObjectListParamHist(ElementHistoryDTO elementHistory, Map<ParamFieldName, String> fields) {
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final String ELEMENT_TEMPLATE = "Element:{type_id}:{id}";
		
		List<Map<String, Object>> objects = null;
		
		List<String> elementIds = elementHistory.getElementIds();
		Long dateIni = elementHistory.getDateIni();
		Long dateEnd = elementHistory.getDateEnd();
		
		if (!CollectionUtils.isEmpty(elementIds) && dateIni < dateEnd) {
			
			String elementSubtrType = elementIds.get(0).substring(0, elementIds.get(0).lastIndexOf(":") + 1);
			
			if (elementIds.stream().filter(elementId -> elementId.contains(elementSubtrType)).count() == elementIds.size()) {
				
				objects = new ArrayList<>();
				
				List<String> elementVariables = StringUtil.extractVariables(ELEMENT_TEMPLATE, elementIds.get(0));
				
				if (!CollectionUtils.isEmpty(elementVariables) && elementVariables.size() == 2) {
				
					String elementTypeIdValue = elementVariables.get(0);
					
					Map<String, Object> paramElementType = new HashMap<>();
					
					paramElementType.put(PARAM_COLUMN_NAME, fields.get(ParamFieldName.ELEMENT_TYPE_ID));
					paramElementType.put(PARAM_OPERATION, ParamOperation.EQUALS.toString());
					paramElementType.put(PARAM_VALUE, String.format("\"%s\"", elementTypeIdValue));
					
					objects.add(paramElementType);
					
					List<String> elementIdValues = new ArrayList<>();
					
					for (String elementId : elementIds) {
						
						elementVariables = StringUtil.extractVariables(ELEMENT_TEMPLATE, elementId);
						
						if (!CollectionUtils.isEmpty(elementVariables) && elementVariables.size() == 2) {
						
							elementIdValues.add(elementVariables.get(1));
						}
					}
					
					if (!CollectionUtils.isEmpty(elementIdValues)) {
						
						Map<String, Object> paramElement = new HashMap<>();
						
						paramElement.put(PARAM_COLUMN_NAME, fields.get(ParamFieldName.ELEMENT_ID));
						paramElement.put(PARAM_OPERATION, ParamOperation.IN.toString());
						paramElement.put(PARAM_VALUE, String.format("\"(%s)\"", String.join(", ", elementIdValues)));
						
						objects.add(paramElement);
					}
					
					Map<String, Object> paramDateIni = new HashMap<>();
					
					paramDateIni.put(PARAM_COLUMN_NAME, fields.get(ParamFieldName.CREATED_AT));
					paramDateIni.put(PARAM_OPERATION, ParamOperation.GREATER_OR_EQUAL.toString());
					paramDateIni.put(PARAM_VALUE, String.format("\"'%s'\"", sdf.format(new Timestamp(dateIni))));
					
					objects.add(paramDateIni);
					
					Map<String, Object> paramDateEnd = new HashMap<>();
					
					paramDateEnd.put(PARAM_COLUMN_NAME, fields.get(ParamFieldName.CREATED_AT));
					paramDateEnd.put(PARAM_OPERATION, ParamOperation.LESS_OR_EQUAL.toString());
					paramDateEnd.put(PARAM_VALUE, String.format("\"'%s'\"", sdf.format(new Timestamp(dateEnd))));
					
					objects.add(paramDateEnd);
				}
			}
		}
		
		return objects;
	}
}
