package com.revenga.rits.back.data.core.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.data.core.util.DaoUtil;
import com.revenga.rits.back.data.core.util.ResourcesUtil;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public abstract class ObjectDaoBuilder {

	public static final String DAO_FUNC_GET_NEXT_ID = "getNextId";
	public static final String DAO_FUNC_GET_NEXT_VAL_FROM_SEC= "getNextValFromSec";
	
	private String sqlSelect;
	private String sqlInsert;
	private String sqlUpdate;
	private String sqlDelete;
	
	private Map<String, Object> paramsSelect = new HashMap<>();
	private Map<String, Object> paramsInsert = new HashMap<>();
	private Map<String, Object> paramsUpdate = new HashMap<>();
	private Map<String, Object> paramsDelete = new HashMap<>();
	
	private Map<Integer, String> paramsNameSelect = new HashMap<>();
	private Map<Integer, String> paramsNameInsert = new HashMap<>();
	private Map<Integer, String> paramsNameUpdate = new HashMap<>();
	private Map<Integer, String> paramsNameDelete = new HashMap<>();

	private Class<?> parentClazz = null;

	ObjectDaoBuilder(Class<?> parentClazz) {

		this.parentClazz = parentClazz;

		initialize();
	}
	
	private void initialize() {
		
		XMLInputFactory f = null;
		XMLStreamReader sr = null;
		XmlMapper mapper = null;
		
		try {
			
			for (InputStream dmlFile : ResourcesUtil.getResources("/dml")) {
				
				f = XMLInputFactory.newInstance();
				sr = f.createXMLStreamReader(dmlFile);
				mapper = new XmlMapper();
				
				boolean srBreak = false;
				
				while (sr.hasNext() && !srBreak) {
					
					int eventType = sr.next();
					
					MapperDML mapperDML;

					if (eventType == XMLStreamConstants.START_ELEMENT) {
				
						mapperDML = mapper.readValue(sr, MapperDML.class);
						
						Class<?> clazzModel = ClassesUtil.getModelClassBySimpleClassName(mapperDML.getModel());
						
						if (clazzModel != null) {
							
							if (mapperDML.getEnabled().equals(MapperDML.ENABLED)
									&& (clazzModel.getName().equals(this.parentClazz.getName()))) {

								sqlSelect = mapperDML.getSelect();
								sqlInsert = mapperDML.getInsert();
								sqlUpdate = mapperDML.getUpdate();
								sqlDelete = mapperDML.getDelete();
								
								initParameters(sqlSelect, paramsSelect);
								initParameters(sqlInsert, paramsInsert);
								initParameters(sqlUpdate, paramsUpdate);
								initParameters(sqlDelete, paramsDelete);
								
								srBreak = true;
							}
						}
						else {
							
							log.warn("Not found model class: " + mapperDML.getModel());
						}
					}
					else if (eventType == XMLStreamConstants.ATTRIBUTE) {

					}
				}
				
				sr.close();
				dmlFile.close();
			}
			
		} catch (XMLStreamException | IOException | ClassNotFoundException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private void initParameters(String dml, Map<String, Object> params) {
		if (dml != null && !dml.isEmpty() && params != null) {

			params.clear();

			StringTokenizer strTok = new StringTokenizer(dml, "[ (),]");
			ArrayList<String> arrStr = new ArrayList<>();

			
			while (strTok.hasMoreTokens()) {
				String strT = strTok.nextToken();
				arrStr.add(strT);
			}

			for (int i = 0; i < arrStr.size(); i++) {
				if (arrStr.get(i).contains(":")) {
					String key = arrStr.get(i).replace(":", "");
					key = key.trim();

					params.put(key, null);
				}
			}
		}
	}

	protected boolean set(Object object, String fieldName, Object fieldValue) {

		Class<?> clazz = object.getClass();

		if (clazz != null) {

			Field field = null;
					
			try {
				
				field = ClassesUtil.getField(clazz, fieldName);
								
				if (field != null) {

					field.setAccessible(true);

					if (fieldValue != null) {
					
						if (field.getType() == Boolean.class && fieldValue instanceof Integer) {

							fieldValue = ((int) fieldValue != 0);
						}
						else if (field.getType() == Long.class && fieldValue instanceof Integer) {
							
							fieldValue = Long.valueOf(String.valueOf(fieldValue));
						}
						else if (field.getType() == Long.class && fieldValue instanceof Timestamp) {
							
							Timestamp ts = (Timestamp) fieldValue;
							fieldValue = Long.valueOf(ts.getTime());
						}
						
						field.set(object, fieldValue);

						return true;
					}
				}
					
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			} 
		}

		return false;
	}
	
	protected String prepareSql(String sqlStatement, Map<Integer, String> paramNames) 
	{
	    String returnStatement = null;
	    
	    if (sqlStatement != null && !sqlStatement.isEmpty())
	    {
	    	returnStatement = sqlStatement;
	    	
	    	int parameterPosition = 0;
	    	
	    	sqlStatement = sqlStatement.replace("\t", "");
	    	sqlStatement = sqlStatement.replace("\n", "");
	    	String[] sqlSplittedStatement = sqlStatement.split("[ (),]"); 
		    	    
		    for (final String sqlStatementSplitItem : sqlSplittedStatement) 
		    {
		        if (sqlStatementSplitItem.startsWith(":")) 
		        {
		        	if (paramNames == null)
		        	{
		        		paramNames = new HashMap<Integer, String>();	
		        	}
		        	
		            String value = sqlStatementSplitItem.substring(1).trim();
		        	
		            paramNames.put(++parameterPosition, value);
		            
		            returnStatement = StringUtils.replaceOnce(returnStatement, sqlStatementSplitItem, "?");
		        }
		    }
	    }

	    return returnStatement;
	}
	
	protected boolean assignStatement(PreparedStatement pstmt, Map<Integer, String> params, Object newObject, Connection conn) {
		
		try {
			
			for (int i = 1; i <= params.size(); i++) {
				
				String paramName = params.get(i);
				Object valueCalculated = null;
				int fieldTypeDB = pstmt.getParameterMetaData().getParameterType(i);
				
				if (paramName != null && !paramName.isEmpty()) {
					
					valueCalculated = setCalculatedField(paramName, conn, newObject);
					
					if (valueCalculated != null) {
						
						paramName = paramName.substring(0, paramName.indexOf('='));
					}
				}
				
				Field field = ClassesUtil.getField(newObject.getClass(), paramName);
				
				if (field != null)
				{
					
					field.setAccessible(true);
					
					Object objValue;
					
					if (valueCalculated != null) {
						
						field.set(newObject, valueCalculated);
					}
					
					objValue = field.get(newObject);
					
					if (objValue != null) {
						
						if (objValue instanceof Integer && fieldTypeDB == Types.INTEGER) {
							
							pstmt.setInt(i, (Integer) objValue);
						}
						else if (objValue instanceof Long) {
							
							if (fieldTypeDB == Types.BIGINT) {
								
								pstmt.setLong(i, (Long) objValue);
							}
							else if (fieldTypeDB == Types.INTEGER) {
							
								pstmt.setInt(i, ((Long) objValue).intValue());
							}
							else if (fieldTypeDB == Types.TIMESTAMP) {
								
								pstmt.setTimestamp(i, new Timestamp((Long) objValue));
							}
						}
						else if (objValue instanceof String) {
							
							pstmt.setString(i, (String) objValue);
						}
						else if (objValue instanceof Float && fieldTypeDB == Types.REAL) {
							
							pstmt.setFloat(i, (Float) objValue);
						}
						else if (objValue instanceof Double && fieldTypeDB == Types.DOUBLE) {
							
							pstmt.setDouble(i, (Double) objValue);
						}
						else if (objValue instanceof Date) {
							
							pstmt.setDate(i, new java.sql.Date(((Date) objValue).getTime()));
						}
						else if (objValue instanceof Boolean) {
							
							if (fieldTypeDB == Types.INTEGER) {
							
								pstmt.setInt(i, ((Boolean) objValue).booleanValue() ? 1 : 0);
							}
							else if (fieldTypeDB == Types.BOOLEAN) {
							
								pstmt.setBoolean(i, (Boolean) objValue);
							}
							else if (fieldTypeDB == Types.BIT) {
								
								pstmt.setBoolean(i, (Boolean) objValue);
							}
						}
					}
					else {
						
						pstmt.setNull(i, fieldTypeDB);
					}
				}
				else {
					
					log.error("Field with name " + paramName + " not exists. " + 
							((newObject != null) ? " Please review dml config for " + newObject.getClass().getSimpleName() : " Unknown source"));
				}
			}
			
			return true;
			
		} catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}
	
	protected Object setCalculatedField(String paramToEvaluate, Connection conn, Object newObject) {
		
		Connection connection = null;
		Object objReturn = null;
		String field = null;
		Boolean conStarted = false;
		// Determined if paramName is a function
		
		if (paramToEvaluate != null && !paramToEvaluate.isEmpty() && paramToEvaluate.contains("{")) {
			
			field = paramToEvaluate.substring(0, paramToEvaluate.indexOf('='));
			
			try {
				
				connection = conn;
				
				if (connection == null || connection.isClosed()) {
					
					connection = DataSourceConnection.getInstance().getConnection();
					conStarted = true;
				}
				
				String expToEval = paramToEvaluate.substring(paramToEvaluate.indexOf('{') + 1, paramToEvaluate.indexOf('}'));
				
				if (expToEval != null && !expToEval.isEmpty() && field != null && !field.isEmpty()) {
							
					if(paramToEvaluate.contains(DAO_FUNC_GET_NEXT_ID)){
						
						expToEval = expToEval.replace(DAO_FUNC_GET_NEXT_ID, "");
						expToEval = expToEval.replaceAll("\\{", "");
						expToEval = expToEval.replaceAll("\\}", "");
						expToEval = expToEval.replace("'", "");
						         	
						String[] paramValues = expToEval.split("\\$");
						
						if (paramValues.length >= 2 && paramValues.length < 4) {
						
							String tableName = null;
							String colName = null;
							String [] colReferences = null;
							
							tableName = paramValues[0].trim();
							colName = paramValues[1].trim();
							
							if (paramValues.length > 2) {
								
								colReferences = paramValues[2].trim().split(";");
							}
							
							Long nextId = DaoUtil.getNextId(connection, newObject, tableName, colName, colReferences);
							
							objReturn = nextId;
						}	
					}
					else if(paramToEvaluate.contains(DAO_FUNC_GET_NEXT_VAL_FROM_SEC)) {
						
						expToEval = expToEval.replace(DAO_FUNC_GET_NEXT_VAL_FROM_SEC, "");
						expToEval = expToEval.replaceAll("\\{", "");
						expToEval = expToEval.replaceAll("\\}", "");
						expToEval = expToEval.replace("'", "");


						Long nextId = DaoUtil.getNextValFromSec(connection, expToEval);
						
						objReturn = nextId; 
					}

				}
				
			} catch (SQLException | IOException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));

			} finally {
				
				try {
					if (connection != null && !connection.isClosed() && conStarted.booleanValue()) {
					
						connection.close();
					}
					
				} catch (SQLException e) {
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}
		
		return objReturn;
		
	}
}
