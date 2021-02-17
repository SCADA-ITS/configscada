import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

class AdapterGridAuditLog {

	static final String FIELD_ID = "id";
	static final String FIELD_LABEL_ALIAS = "labelAlias";
	static final String FIELD_LABEL_NAME = "name";
	static final String FIELD_VALUE = "value";

	static final String FIELD_OBJECT_LOG_SUBTYPE = "logSubtype";
	static final String FIELD_OBJECT_USER = "user";
	static final String FIELD_OBJECT_LOG_VALUES = "auditLogValues";
	static final String FIELD_OBJECT_LOG_TYPE_PARAM = "logTypeParam";

	static final String FIELD_LOG_SUBTYPE_LABEL_ALIAS = "logSubtypeLabelAlias";
	static final String FIELD_USER_NAME = "userName";

	org.apache.logging.log4j.Logger log;

	public AdapterGridAuditLog(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Map<String, Object>> toGridData(VO2UIAdapter vo2uiAdapter, String fieldId, List<Map<String, Object>> entities) {

		List<Map<String, Object>> data = new ArrayList<>();

		try {

			if (!CollectionUtils.isEmpty(entities)) {

				for (Map<String, Object> entity : entities) {

					Map<String, Object> dataRow = new HashMap<>();

					dataRow.putAll(entity);

					flattenLogSubtype(dataRow);
					flattenUser(dataRow);
					flattenElementValues(dataRow);

					data.add(vo2uiAdapter.convert(fieldId, dataRow));
				}
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}

	void flattenLogSubtype(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_OBJECT_LOG_SUBTYPE)) {

			Object objElementTypeState = dataRow.get(FIELD_OBJECT_LOG_SUBTYPE);

			if (objElementTypeState instanceof Map) {

				Map<String, Object> elementTypeState = (Map<String, Object>) objElementTypeState;

				dataRow.put(FIELD_LOG_SUBTYPE_LABEL_ALIAS, elementTypeState.get(FIELD_LABEL_ALIAS));
			}
			dataRow.remove(FIELD_OBJECT_LOG_SUBTYPE);
		}
	}

	void flattenUser(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_OBJECT_USER)) {

			Object objElementTypeState = dataRow.get(FIELD_OBJECT_USER);

			if (objElementTypeState instanceof Map) {

				Map<String, Object> elementTypeState = (Map<String, Object>) objElementTypeState;

				dataRow.put(FIELD_USER_NAME, elementTypeState.get(FIELD_LABEL_NAME));
			}
			dataRow.remove(FIELD_OBJECT_USER);
		}
	}

	void flattenElementValues(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_OBJECT_LOG_VALUES)) {

			Object objAuditLogValues= dataRow.get(FIELD_OBJECT_LOG_VALUES);

			if (objAuditLogValues instanceof List) {

				List<?> auditLogValues = (List<?>) objAuditLogValues;

				if (!CollectionUtils.isEmpty(auditLogValues)) {

					for (Object objAuditLogValue : auditLogValues) {

						if (objAuditLogValue instanceof Map) {

							Map<String, Object> auditLogValue = (Map<String, Object>) objAuditLogValue;

							if (auditLogValue.get(FIELD_VALUE) != null) {

								if (auditLogValue.containsKey(FIELD_OBJECT_LOG_TYPE_PARAM)) {

									Object objLogTypeParam = auditLogValue.get(FIELD_OBJECT_LOG_TYPE_PARAM);

									if (objLogTypeParam instanceof Map) {

										Map<String, Object> logTypeParam = (Map<String, Object>) objLogTypeParam;

										String fieldParamId = (String) logTypeParam.get(FIELD_ID);

										if(fieldParamId != null){

											String valueStr = (String) auditLogValue.get(FIELD_VALUE);
											dataRow.put(fieldParamId, valueStr);
										}
									}
								}
							}
						}
					}
				}
			}

			dataRow.remove(FIELD_OBJECT_LOG_VALUES);
		}
	}
}
