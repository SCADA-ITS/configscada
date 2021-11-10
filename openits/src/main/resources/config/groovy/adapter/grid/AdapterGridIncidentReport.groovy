package config.groovy.adapter.grid;

import config.groovy.adapter.grid.AdapterGrid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revenga.rits.front.openits.service.CacheManager;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

class AdapterGridIncidentReport extends AdapterGrid{

	static final String FIELD_ID = "id";
	static final String FIELD_ALIAS = "alias";
	static final String FIELD_DESCRIPTION = "description";
	static final String FIELD_LABEL_ALIAS = "labelAlias";
	static final String FIELD_LABEL_NAME = "name";

	static final String FIELD_OBJECT_INCIDENT_TYPE = "incidentType";
	static final String FIELD_OBJECT_LOCATION = "location";
	static final String FIELD_OBJECT_AFFECTION_STRETCH = "affectionStretch";
	static final String FIELD_OBJECT_INCIDENT_LEVEL = "incidentLevel";
	static final String FIELD_OBJECT_CURREN_USER = "currentUser";
	static final String FIELD_OBJECT_INCIDENT_STATE = "incidentState";

	static final String FIELD_INCIDENT_LEVEL_ID = "incidentLevelId";
	static final String FIELD_INCIDENT_ID = "incidentId";

	static final String FIELD_INCIDENT_TYPE_TYPE_ALIAS = "incidentTypeAlias";
	static final String FIELD_LOCATION_ALIAS = "locationAlias";
	static final String FIELD_AFFECTION_STRETCH_ALIAS = "affectionStretchAlias";

	static final String FIELD_INCIDENT_TYPE_TYPE_DESCRIPTION  = "incidentTypeDescription";
	static final String FIELD_LOCATION_DESCRIPTION  = "localtionDescription";
	static final String FIELD_AFFECTION_STRETCH_DESCRIPTION  = "affectionStretchDescription";

	static final String FIELD_INCIDENT_STATE_LABEL_ALIAS = "incidentStateLabelAlias";
	static final String FIELD_INCIDENT_LEVEL_LABEL_ALIAS = "incidentLevelLabelAlias";

	static final String FIELD_CURREN_USER_NAME = "currentUserName";

	public AdapterGridIncidentReport(CacheManager cacheManager, VO2UIAdapter vo2uiAdapter, org.apache.logging.log4j.Logger log) {
		
		super(cacheManager, vo2uiAdapter, log);
	}

	@Override
	public List<Map<String, Object>> toGridData(Integer datasourceId, List<Map<String, Object>> entities) {

		List<Map<String, Object>> data = new ArrayList<>();

		try {

			if (!CollectionUtils.isEmpty(entities)) {

				for (Map<String, Object> entity : entities) {

					Map<String, Object> dataRow = new HashMap<>();

					dataRow.putAll(entity);


					flattenIncidentId(dataRow);
					flattenIncidentType(dataRow);
					flattenLocation(dataRow);
					flattenAffectionStretch(dataRow);
					flattenIncidentLevel(dataRow);
					flattenIncidentState(dataRow);
					flattenCurrentUser(dataRow);


					data.add(vo2uiAdapter.convert(datasourceId, dataRow));
				}
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return data;
	}

	void flattenIncidentId(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_ID)) {

			String id = dataRow.get(FIELD_ID);
			String[] str;
			str = id.split(':');
			
			dataRow.put(FIELD_INCIDENT_ID, str[1]);
		}
	}

	void flattenIncidentType(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_OBJECT_INCIDENT_TYPE)) {

			Object obj = dataRow.get(FIELD_OBJECT_INCIDENT_TYPE);

			if (obj instanceof Map) {

				Map<String, Object> map = (Map<String, Object>) obj;

				dataRow.put(FIELD_INCIDENT_TYPE_TYPE_ALIAS, map.get(FIELD_ALIAS));
				dataRow.put(FIELD_INCIDENT_TYPE_TYPE_DESCRIPTION, map.get(FIELD_DESCRIPTION));
			}

			dataRow.remove(FIELD_OBJECT_INCIDENT_TYPE);
		}
	}

	void flattenLocation(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_OBJECT_LOCATION)) {

			Object obj = dataRow.get(FIELD_OBJECT_LOCATION);

			if (obj instanceof Map) {

				Map<String, Object> map = (Map<String, Object>) obj;

				dataRow.put(FIELD_LOCATION_ALIAS, map.get(FIELD_ALIAS));
				dataRow.put(FIELD_LOCATION_DESCRIPTION, map.get(FIELD_DESCRIPTION));
			}
			dataRow.remove(FIELD_OBJECT_LOCATION);
		}
	}

	void flattenAffectionStretch(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_OBJECT_AFFECTION_STRETCH)) {

			Object obj = dataRow.get(FIELD_OBJECT_AFFECTION_STRETCH);

			if (obj instanceof Map) {

				Map<String, Object> map = (Map<String, Object>) obj;

				dataRow.put(FIELD_AFFECTION_STRETCH_ALIAS, map.get(FIELD_ALIAS));
				dataRow.put(FIELD_AFFECTION_STRETCH_DESCRIPTION, map.get(FIELD_DESCRIPTION));
			}
			dataRow.remove(FIELD_OBJECT_AFFECTION_STRETCH);
		}
	}

	void flattenIncidentLevel(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_OBJECT_INCIDENT_LEVEL)) {

			Object obj = dataRow.get(FIELD_OBJECT_INCIDENT_LEVEL);

			if (obj instanceof Map) {

				Map<String, Object> map = (Map<String, Object>) obj;

				dataRow.put(FIELD_INCIDENT_LEVEL_ID, map.get(FIELD_ID));
				dataRow.put(FIELD_INCIDENT_LEVEL_LABEL_ALIAS, map.get(FIELD_LABEL_ALIAS));
			}
			dataRow.remove(FIELD_OBJECT_INCIDENT_LEVEL);
		}
	}

	void flattenIncidentState(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_OBJECT_INCIDENT_STATE)) {

			Object obj = dataRow.get(FIELD_OBJECT_INCIDENT_STATE);

			if (obj instanceof Map) {

				Map<String, Object> map = (Map<String, Object>) obj;

				dataRow.put(FIELD_INCIDENT_STATE_LABEL_ALIAS, map.get(FIELD_LABEL_ALIAS));
			}
			dataRow.remove(FIELD_OBJECT_INCIDENT_STATE);
		}
	}

	void flattenCurrentUser(Map<String, Object> dataRow) {

		if (dataRow.containsKey(FIELD_OBJECT_CURREN_USER)) {

			Object obj = dataRow.get(FIELD_OBJECT_CURREN_USER);

			if (obj instanceof Map) {

				Map<String, Object> map = (Map<String, Object>) obj;

				dataRow.put(FIELD_CURREN_USER_NAME, map.get(FIELD_LABEL_NAME));
			}
			dataRow.remove(FIELD_OBJECT_CURREN_USER);
		}
	}
}
