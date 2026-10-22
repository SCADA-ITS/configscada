package com.revenga.rits.back.data.core.dao.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.dao.ObjectDao;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ParamHist;
import com.revenga.rits.back.data.core.util.ModelHelper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ObjectDaoImpl extends ObjectDaoBuilder implements ObjectDao {

	private List<Object> objects = null;
	
	private ModelHelper.OrderType orderType;

	public ObjectDaoImpl(Class<?> clazz) {
		super(clazz);

		objects = Collections.synchronizedList(new ArrayList<>());
	}
	
	static final String WHERE = "WHERE ";
	static final String AND = " AND ";
	static final String GREATER = " > %s";
	static final String GREATER_OR_EQUAL = " >= %s";
	static final String LESS = " < %s";
	static final String LESS_OR_EQUAL = " <= %s";
	static final String EQUALS = " = %s";
	static final String IN = " IN %s";
	static final String CREATED_AT = "created_at";
	static final String UNION = " UNION ALL ";
	static final String ELEMENT_ID = "element_id";
	static final String MAX_CREATED_AT = "= (SELECT MAX(created_at) ";
	static final Pattern PATTERN = Pattern.compile("FROM\\s+([\\w.]+)");
	static final String FROM = "FROM ";
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public List<Object> getList() {
		return objects;
	}
	
	@Override
	public void setList(List<Object> objects) {
		
		this.objects = objects;
	}
	
	public ModelHelper.OrderType getOrderType() {
		return orderType;
	}
	
	public void sort(ModelHelper.OrderType orderType) {
		
		this.objects = ModelHelper.getSortedList(objects, orderType);
		this.orderType = orderType;
	}
	
	@Override
	public Object get(Long id) {
		
		Field idField = null;
		
		try {
			
			idField = this.getParentClazz().getSuperclass().getDeclaredField("id");
			
			if (idField == null) {
				
				idField = this.getParentClazz().getDeclaredField("id");
			}
			
		} catch (NoSuchFieldException e) {
			
		}

		if (idField != null) {
		
			return get(idField, id);
		}
	
		return null;
	}
	
	@Override
	public Object get(Field field, Object value) {
		
		try {
			
			if (field != null) {
				
				field.setAccessible(true);
				
				synchronized (objects) {
					
					Iterator<Object> it = objects.iterator();
					
					while (it.hasNext()) {

						Object obj = it.next();
						
						if (obj != null) {
							
							Object val = field.get(obj);
							
							if (val != null && value != null && val.equals(value)) {

								return obj;
							}
						}
					}
				}
			}
			
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return null;
	}

	@Override
	public boolean add(Object obj) {
				
		if (get(((BasicElement) obj).getId()) == null) {
			
			return objects.add(obj);
		}
		
		return false;
	}
	
	/***
	 * Update object to map
	 * 
	 * @param source Object to be updated
	 * @param target New Object to update
	 * 
	 * @return <tt>true</tt> (if success)
	 *         <tt>false</tt> (if error)
	 */
	@Override
	public boolean replace(Object source, Object target) {
		
		if (objects.contains(source)) {
			
			return ((BasicElement) source).update((BasicElement) target);
		}
		
		return false;
	}
	
	@Override
	public boolean replace(Long id, Object target) {
		
		BasicElement oldElement = null;
		
		oldElement = (BasicElement) get(id);
		
		if (oldElement != null) {
			
			return oldElement.update((BasicElement) target); 
		}
		
		return false;
	}
	
	@Override
	public boolean remove(Object obj) {
		
		if (objects.contains(obj)) {
			
			return objects.remove(obj);
		}
		
		return false;
	}
	
	@Override
	public boolean remove(Long id) {
		
		BasicElement oldElement = null;
		
		oldElement = (BasicElement) get(id);
		
		if (oldElement != null) {
			
			return objects.remove(oldElement);
		}
		
		return false;
	}

	public boolean load(Connection conn) {

		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = null;
		String prepareSql = null;

		try {
						
			if (conn == null || conn.isClosed()) {
				
				connection = DataSourceConnection.getInstance().getConnection();	
			}			
			else {			
				
				connection = conn;
			}
			
			if (connection != null) {
				
				sql = getSqlSelect();
				
				if (sql == null || sql.isEmpty()) {
					
					return false;
				}
				
				prepareSql = prepareSql(sql, getParamsNameSelect());
				pstmt = connection.prepareStatement(prepareSql);
						
				rset = pstmt.executeQuery();
			 	
				ResultSetMetaData rsmd = rset.getMetaData();
				
				getList().clear();

				while (rset.next()) {

					Object element = this.getParentClazz().getConstructor().newInstance();

					for (int col = 1; col <= rsmd.getColumnCount(); col++) {
						String colName = null;

						colName = rsmd.getColumnName(col);

						set(element, colName, rset.getObject(col));
					}

					getList().add(element);
				}

				rset.close();
				pstmt.close();

				result = true;
			}

		} catch (SQLException | InstantiationException | IllegalAccessException | IOException | IllegalArgumentException
				| SecurityException | InvocationTargetException | NoSuchMethodException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		} finally {

			try {

				if (connection != null && conn == null) {
					connection.close();
				}
				
			} catch (SQLException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return result;
	}

	public Object insert(Connection conn, Object newObject) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String prepareSql = null;
		Object insertedObject = null;
		
		try {

			if (newObject != null) {
				
				if (conn == null || conn.isClosed()) {
					
					connection = DataSourceConnection.getInstance().getConnection();	
				}			
				else {			
					
					connection = conn;
				}

				if (connection != null) {

					sql = getSqlInsert();
					
					if (sql == null || sql.isEmpty()) {
						
						return false;
					}
					
					prepareSql = prepareSql(sql, getParamsNameInsert());
					
					pstmt = connection.prepareStatement(prepareSql);
									
					if (assignStatement(pstmt, getParamsNameInsert(), newObject, conn)) {
					
						pstmt.executeUpdate();
						
						insertedObject = newObject;
					}
					
					pstmt.close();
				}
			}

		} catch (SQLException | IllegalArgumentException | IOException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
		} finally {
			
			try {
				
				if (connection != null && conn == null) {
					connection.close();
				}
				
			} catch (SQLException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return insertedObject;
	}

	public Object update(Connection conn, Object newObject) {

		Object updatedObject = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String prepareSql = null;
		
		try {

			if (newObject != null) {
				
				if (conn == null || conn.isClosed()) {
					
					connection = DataSourceConnection.getInstance().getConnection();	
				}			
				else {			
					
					connection = conn;
				}

				if (connection != null) {

					sql = getSqlUpdate();
					
					if (sql == null || sql.isEmpty()) {
						
						return false;
					}
					
					prepareSql = prepareSql(sql, getParamsNameUpdate());
					
					pstmt = connection.prepareStatement(prepareSql);
									
					if (assignStatement(pstmt, getParamsNameUpdate(), newObject, conn)) {
					
						pstmt.executeUpdate();
						
						updatedObject = newObject;
					}
					
					pstmt.close();
				}
			}

		} catch (SQLException | IllegalArgumentException | IOException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
		} finally {
			
			try {
				
				if (connection != null && conn == null) {
					connection.close();
				}
				
			} catch (SQLException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				
			}
		}

		return updatedObject;
	}

	public Object delete(Connection conn, Object oldObject) {

		Object deletedObject = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String prepareSql = null;
		
		try {

			if (oldObject != null) {
				
				if (conn == null || conn.isClosed()) {
					
					connection = DataSourceConnection.getInstance().getConnection();	
				}			
				else {			
					
					connection = conn;
				}

				if (connection != null) {

					sql = getSqlDelete();
					
					if (sql == null || sql.isEmpty()) {
						
						return false;
					}
					
					prepareSql = prepareSql(sql, getParamsNameDelete());
					
					pstmt = connection.prepareStatement(prepareSql);
									
					if (assignStatement(pstmt, getParamsNameDelete(), oldObject, conn)) {
					
						pstmt.executeUpdate();
						
						deletedObject = oldObject;
					}
					
					pstmt.close();
				}
			}

		} catch (SQLException | IllegalArgumentException | IOException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		
		} finally {
			
			try {
				
				if (connection != null && conn == null) {
					
					connection.close();
				}
				
			} catch (SQLException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return deletedObject;
	}
}

