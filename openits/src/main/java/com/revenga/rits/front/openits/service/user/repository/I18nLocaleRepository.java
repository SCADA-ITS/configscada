package com.revenga.rits.front.openits.service.user.repository;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class I18nLocaleRepository extends GenericRepository {

	public static final String LOCALE_PREFIX = "I18nLocale";
	public static final String TABLE_NAME = "master.i18n_locales";
	
	public static final String PK_LOCALE_CODE = "locale_code";
	public static final String FIELD_LOCALE_CODE = "localeCode";
	
	private static final String QRY_LOCALES = String.format("SELECT locale_code, locale_code as code, name " +
											  				"  FROM %s", TABLE_NAME);

	public I18nLocaleRepository(Connection conn) {
		super(conn);
		
		this.qryAll = QRY_LOCALES;
		this.mapPks.put(PK_LOCALE_CODE, FIELD_LOCALE_CODE);
		this.prefix = LOCALE_PREFIX;
		this.tableName = TABLE_NAME;
	}
	
	public I18nLocaleRepository(Connection conn, List<CacheRepository> cacheRepositories) {
		super(conn, cacheRepositories);
		
		this.qryAll = QRY_LOCALES;
		this.mapPks.put(PK_LOCALE_CODE, FIELD_LOCALE_CODE);
		this.prefix = LOCALE_PREFIX;
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
