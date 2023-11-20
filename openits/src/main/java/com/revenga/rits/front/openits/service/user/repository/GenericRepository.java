package com.revenga.rits.front.openits.service.user.repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.CaseUtils;

import com.revenga.rits.front.openits.service.user.UserManager;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class GenericRepository {
		
	public static final String FIELD_ID = "id";
	public enum Operation { CREATE, UPDATE, DELETE }
	
	protected Connection conn;
	protected List<CacheRepository> cacheRepositories = new ArrayList<>();
	protected String qryAll;
	//protected String[] pks;
	protected String prefix;
	protected String tableName;
	
	protected Map<String, String> mapPks = new HashMap<>();
	protected Map<String, String> remapedFields;
	
	public class EntityPersistenceException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7094391427968485904L;
		
		

		public EntityPersistenceException(Operation operation, Class<? extends GenericRepository> clazz) {
			
			super("Exception in persistence operation " + operation.name() + ": " + clazz.getSimpleName());
		}
		
		public EntityPersistenceException(Operation operation, Class<? extends GenericRepository> clazz, String message) {
			
			super("Exception in persistence operation " + operation.name() + ": " + clazz.getSimpleName() + "; Message: " + message);
		}
	}
	
	@Data
	public class CacheRepository {
		
		Class<? extends GenericRepository> repositoryClass;
		List<Map<String, Object>> list;
		
		public CacheRepository(Class<? extends GenericRepository> repositoryClass, List<Map<String, Object>> list) {
			super();
			this.repositoryClass = repositoryClass;
			this.list = list;
		}
	}

	protected GenericRepository(Connection conn) {
		
		this.conn = conn;
	}
	
	protected GenericRepository(Connection conn, List<CacheRepository> cacheRepositories) {
		
		this.conn = conn;
		this.cacheRepositories = cacheRepositories;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> cache(Class<? extends GenericRepository> repositoryClass) {
		
		List<Map<String, Object>> repository = null;
		
		CacheRepository cacheRepository = this.cacheRepositories.stream()
				.filter(e -> e.getRepositoryClass().equals(repositoryClass)).findFirst().orElse(null);
		
		if (cacheRepository != null) {
			
			repository = cacheRepository.getList();
		}
		else {
			
			try {
				
				GenericRepository genericRepository = repositoryClass.getDeclaredConstructor(Connection.class).newInstance(this.conn);
				
				repository = genericRepository.gets();
				
				this.cacheRepositories.add(new CacheRepository(genericRepository.getClass(), repository));
				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
				
		return repository;
	}
	
	public String getObjAttribute(String dbColumn) {
		
		return mapPks.get(dbColumn);
	}
	
	public void commit() {
		
		try {
			
			if (this.conn != null && !this.conn.isClosed()) {
	
				this.conn.commit();
			}
			
		} catch (Exception e) {
				
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	protected boolean tryConnect() {
		
		try {
			
			if (this.conn == null || this.conn.isClosed()) {
					
				this.conn = DriverManager.getConnection( UserManager.USERMGR_SQL_JDBC, UserManager.USERMGR_SQL_USER,
						AES.decrypt(UserManager.USERMGR_SQL_PASSWORD));
				
				this.conn.setAutoCommit(false);
			}
			
			return !this.conn.isClosed();
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
	
	public String getIdStr(String...ids) {
		
		return this.prefix != null ? this.prefix + ":" + String.join(":", ids) : null;
	}
	
	public String[] getId(String idStr) {
		
		String[] id = null;
		
		if (!StringUtils.isEmpty(idStr) && idStr.contains(this.prefix + ":")) {
			
			String ids = idStr.replace(this.prefix + ":", "");
			
			id = ids.split(":"); 
		}
		
		return id;
	}
	
	public List<Map<String, Object>> gets(@SuppressWarnings("unchecked") Pair<String, Object>...filters) {
		
		List<Map<String, Object>> entities = new ArrayList<>();
		
		try {
			
			if (tryConnect() && !StringUtils.isEmpty(this.qryAll)) {

				StringBuilder sbSql = new StringBuilder();
				
				sbSql.append(this.qryAll); 
				
				if (!ArrayUtils.isEmpty(filters)) {
					
					sbSql.append(" WHERE ");
					
					List<String> conditions = new ArrayList<>();
					
					for (Pair<String, Object> entry : filters) {
					
						String field = entry.getKey();
						Object value = entry.getValue();
						
						if (value != null) {
							
							String condition = "";
							
							condition = field + " = ";
									
							if (value instanceof String) {
								
								condition += "'" + value +"'";		
							}
							else {
								
								condition += value;
							}
							
							if (!StringUtils.isEmpty(condition)) {
								
								conditions.add(condition);
							}
						}
					}
					
					if (!CollectionUtils.isEmpty(conditions)) {
						
						sbSql.append(String.join(" AND ", conditions));
					}
				}

				try (PreparedStatement stmt = this.conn.prepareStatement(sbSql.toString())) {
					
					ResultSet rs = stmt.executeQuery();
					
					while (rs.next()) {

						entities.add(getFromResultSet(rs));
					}
				}
			}
		
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return entities;
	}
	
	public Map<String, Object> get(@SuppressWarnings("unchecked") Pair<String, Object>...filters) {
		
		Map<String, Object> entity = null;
		List<Map<String, Object>> entities = gets(filters);
		
		if (!CollectionUtils.isEmpty(entities)) {
			
			entity = entities.get(0);
		}
		
		return entity;
	}
	
	protected Map<String, Object> getFromResultSet(ResultSet rs) throws SQLException {
		
		Map<String, Object> entity = null;
		ResultSetMetaData rsmd = rs.getMetaData();
		
		List<String> availablePKs = new ArrayList<>(this.mapPks.keySet());
		
		if (rsmd.getColumnCount() > 0) {
			
			entity = new HashMap<>();
			
			StringBuilder sbIdValue = new StringBuilder();
			
			sbIdValue.append(this.prefix);
			
			Object value = null;
			
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				
				String columnName = rsmd.getColumnName(i);
				
				String fieldName = null;
				
				if (!MapUtils.isEmpty(this.remapedFields)) {
					
					fieldName = this.remapedFields.get(columnName);
				}
				
				if (fieldName == null) {
					
					fieldName = CaseUtils.toCamelCase(columnName, false, '_');
				}
 
				String columnClassName = rsmd.getColumnClassName(i);
				
				if (String.class.getName().equals(columnClassName)) {
					
					value = rs.getString(i);
				}
				else if (Integer.class.getName().equals(columnClassName)) {
					
					value = rs.getInt(i);
					
					value = rs.wasNull() ? null : value; 
				}
				else if (Long.class.getName().equals(columnClassName)) {
					
					value = rs.getLong(i);
				}
				else if (Boolean.class.getName().equals(columnClassName)) {
					
					value = rs.getBoolean(i);
				}
				else if (Timestamp.class.getName().equals(columnClassName)) {
					
					value = rs.getTimestamp(i) != null ? rs.getTimestamp(i).getTime() : null;
				}
				
				if (value != null) {
					
					entity.put(fieldName, value);
					
					String pk = availablePKs.stream().filter(e -> e.equals(columnName)).findFirst().orElse(null);
					
					if (!StringUtils.isEmpty(pk)) {
						
						availablePKs.remove(pk);
						
						sbIdValue.append(":" + value);
					}
				}
			}
			
			if (!StringUtils.isEmpty(this.prefix) && !StringUtils.equals(sbIdValue.toString(), this.prefix)) {
				
				entity.put(GenericRepository.FIELD_ID, sbIdValue.toString());
			}
		}
		
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	protected void fillEntity(Map<String, Object> entity, Class<? extends GenericRepository> repositoryClass,
			String entityFieldIdName, String entityFieldName, String refEntityFieldIdName,
			String refTableColumnIdName) {
		
		try {
			
			Object refFieldValue = entity.get(entityFieldIdName);
			
			if (refFieldValue != null) {
			
				Map<String, Object> obj = null;
				
				CacheRepository cacheRepository = this.cacheRepositories.stream()
						.filter(e -> e.getRepositoryClass().equals(repositoryClass)).findFirst().orElse(null);
				
				
				
				if (cacheRepository != null) {
					
					List<Map<String, Object>> list = cacheRepository.getList();
					
					obj = list.stream()
							.filter(e -> refFieldValue.equals(e.get(refEntityFieldIdName))).findFirst()
							.orElse(null);
				}
				else {
					
					GenericRepository genericRepositoryInstance = repositoryClass.getDeclaredConstructor(Connection.class, List.class).newInstance(this.conn, this.cacheRepositories);
					
					obj = genericRepositoryInstance.get(new ImmutablePair<String, Object>(refTableColumnIdName, refFieldValue));
					
					genericRepositoryInstance.fill(obj);
				}
					
				if (obj != null) {
				
					entity.put(entityFieldName, obj);
					entity.remove(entityFieldIdName);
				}
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public Long getNextId(String fieldIdName) {
		
		Long nextId = 1L;
		
		final String sql = String.format("SELECT MAX(%s) FROM %s", fieldIdName, this.tableName);
	
		try {
			
			if (tryConnect() && !StringUtils.isEmpty(sql)) {
				
				try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
					
					ResultSet rs = stmt.executeQuery();
					
					if (rs.next()) {

						nextId = rs.getLong(1);
						nextId++;						
					}
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return nextId;
	}
	
	public void fill(Map<String, Object> entity) {
		
		this.fill(Arrays.asList(entity));
	}
	
	public abstract void fill(List<Map<String, Object>> entities);
	
	public abstract Map<String, Object> create(Map<String, Object> input) throws EntityPersistenceException;
	public abstract Map<String, Object> update(Map<String, Object> input) throws EntityPersistenceException;
	public abstract Map<String, Object> delete(String input) throws EntityPersistenceException;
}
