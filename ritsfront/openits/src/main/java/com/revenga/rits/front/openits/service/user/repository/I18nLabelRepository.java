package com.revenga.rits.front.openits.service.user.repository;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class I18nLabelRepository extends GenericRepository {

	public static final String LABEL_PREFIX = "I18nLabel";
	public static final String TABLE_NAME = "master.i18n_labels";
	
	public static final String PK_LOCALE_CODE = "locale_code";
	public static final String PK_MODULE_ID = "module_id";
	public static final String PK_LABEL = "label";
	
	public static final String FIELD_LOCALE_CODE = "localeCode";
	public static final String FIELD_MODULE_ID = "moduleId";
	public static final String FIELD_LABEL = "label";
	
	private static final String QRY_LABELS = String.format("SELECT locale_code, module_id, label, translation " +
											  			   "  FROM %s", TABLE_NAME);

	public I18nLabelRepository(Connection conn) {
		super(conn);
		
		this.qryAll = QRY_LABELS;
		this.mapPks.put(PK_LOCALE_CODE, FIELD_LOCALE_CODE);
		this.mapPks.put(PK_MODULE_ID, FIELD_MODULE_ID);
		this.mapPks.put(PK_LABEL, FIELD_LABEL);
		this.prefix = LABEL_PREFIX;
		this.tableName = TABLE_NAME;
	}
	
	public I18nLabelRepository(Connection conn, List<CacheRepository> cacheRepositories) {
		super(conn, cacheRepositories);
		
		this.qryAll = QRY_LABELS;
		this.mapPks.put(PK_LOCALE_CODE, FIELD_LOCALE_CODE);
		this.mapPks.put(PK_MODULE_ID, FIELD_MODULE_ID);
		this.mapPks.put(PK_LABEL, FIELD_LABEL);
		this.prefix = LABEL_PREFIX;
		this.tableName = TABLE_NAME;
	}

	@Override
	public void fill(List<Map<String, Object>> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> create(Map<String, Object> input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> update(Map<String, Object> input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> delete(String input) {
		// TODO Auto-generated method stub
		return null;
	}
}
