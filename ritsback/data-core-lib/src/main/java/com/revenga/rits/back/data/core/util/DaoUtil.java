package com.revenga.rits.back.data.core.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DaoUtil {

	private DaoUtil() {
		
	    throw new IllegalStateException(this.getClass().getSimpleName());
	}
	
	public static Long getNextId(Connection connection, Object newObject, String tableName, String colName, String [] colReferences) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		StringBuilder sql = new StringBuilder();
		Long nextId = 1L;
		
		try {
			
			if (connection != null && !connection.isClosed()) {
				
				sql.append("SELECT MAX(" + colName + ") FROM " + tableName);
				
				if (colReferences != null && colReferences.length > 0) {
				
					sql.append(" WHERE ");
					
					for (int i = 0; i < colReferences.length; i++) {
						
						String whereItem = colReferences[i];
						
						String [] whereSplitted = whereItem.trim().split("=");
						
						if (whereSplitted != null && whereSplitted.length == 2) {
						
							String fieldWhereSQL = whereSplitted[0];
							
							Field field = ClassesUtil.getField(newObject.getClass(), whereSplitted[1]);
							
							String fieldValue = null;
							
							field.setAccessible(true);
							
							Object objValue = field.get(newObject);
							
							if (objValue != null) {
							
								fieldValue = String.valueOf((long) field.get(newObject));
							}
							else {
								
								throw new NullPointerException("Field " + field.getName() + " value is null");
							}
								
							if (i != 0) {
								
								sql.append(" AND ");
							}	
								
							sql.append(fieldWhereSQL + " = '" + fieldValue + "'");
						}
					}
				}
				
				pstmt = connection.prepareStatement(sql.toString());
				
				rset = pstmt.executeQuery();
								
				while (rset.next()) {
					
					nextId = (long) rset.getInt(1);
					nextId++;
					
					break;
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
		} finally {

			try {
				
				if (pstmt != null) {
			
					pstmt.close();
				}
				
				if (rset != null) {
				
					rset.close();
				}
				
			} catch (SQLException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return nextId;
	}

	
	public static Long getNextValFromSec(Connection connection, String secName) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		StringBuilder sql = new StringBuilder();
		Long nextVal = 1L;
		
		try {
			
			if (connection != null && !connection.isClosed()) {
				
				sql.append("SELECT nextval ('" + secName + "')" );
				
				pstmt = connection.prepareStatement(sql.toString());
				
				rset = pstmt.executeQuery();
								
				if (rset.next()) {
					
					nextVal = (long) rset.getInt(1);
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
		} finally {

			try {
				
				if (pstmt != null) {
			
					pstmt.close();
				}
				
				if (rset != null) {
				
					rset.close();
				}
				
			} catch (SQLException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return nextVal;
	}
}
