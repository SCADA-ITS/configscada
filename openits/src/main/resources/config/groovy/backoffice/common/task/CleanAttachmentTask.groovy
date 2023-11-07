// Uncomment following line for groovy file
package config.groovy.backoffice.common.task;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileSystemUtils;

import com.revenga.rits.front.openits.backoffice.BackOfficeException;
import com.revenga.rits.front.openits.backoffice.BackOfficeUtil.DBType;
import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTask;

public class CleanAttachmentTask extends AbstractBackOfficeTask {

	public CleanAttachmentTask(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onExecute() {
		
		boolean res = false;
		
		try {
			
			if (this.conn == null || this.conn.isClosed()) {
				
				throw new BackOfficeException("Connection is not initialized");
			}
			
			if (this.backOffice == null) {
				
				throw new BackOfficeException("Backoffice is not initialized");
			}
			
			if (this.baseDir == null) {
				
				throw new BackOfficeException("Base dir is not configured");
			}
			
			Set<String> tableNames = getTableNames();
			
			if (!CollectionUtils.isEmpty(tableNames)) {
				
				deleteAttachments(getMapImagesDB(tableNames), getMapImagesFS(tableNames));
			}
			
			res = true;
			
		} catch (Exception e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} 
		
		return res;
	}
	
	private Map<String, Set<Integer>> getMapImagesDB(Set<String> tables) throws SQLException {
		
		Map<String, Set<Integer>> map = new HashMap<>();
		
		for (String tableName : tables) {
					
			if (!StringUtils.isEmpty(tableName)) {
				
				map.put(tableName, new HashSet<>());
				
				String fieldName = getFieldNamePk(tableName);
				
				if (!StringUtils.isEmpty(fieldName)) {
					
					final String SQL = String.format("SELECT %s FROM %s.%s", fieldName, this.backOffice.getSchema(), tableName);
					
					PreparedStatement stmt = null;
					
					try {
						
						stmt = conn.prepareStatement(SQL);
						
						ResultSet rs = stmt.executeQuery();
						
						while (rs.next()) {
							
							Set<Integer> ids = map.get(tableName);
							
							if (ids != null) {
								
								ids.add(rs.getInt(fieldName));
							}
						}
					}
					catch (Exception e) {

						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
					finally {
						
						if (stmt != null) {
							
							stmt.close();
						}
					}
				}
			}
		}
		
		return map;
	}
	
	private Map<String, Set<Integer>> getMapImagesFS(Set<String> tables) {
		
		Map<String, Set<Integer>> map = new HashMap<>();
		
		for (String tableName : tables) {
					
			if (!StringUtils.isEmpty(tableName)) {
				
				map.put(tableName, new HashSet<>());
				
				List<String> listPathArray = Arrays.asList(this.getBaseDir(), "backoffice", backOffice.getId().toString(), tableName);
				
				String path = String.join(File.separator, listPathArray.toArray(new String[listPathArray.size()]));
				
				File baseDirectory = new File(path);
				
				if (baseDirectory.isDirectory()) {
					
					Set<Integer> ids = map.get(tableName);
					
					for (File dir : Arrays.asList(baseDirectory.listFiles())) {
						
						if (ids != null && dir.isDirectory() && NumberUtils.isCreatable(dir.getName())) {
							
							ids.add(Integer.parseInt(dir.getName()));
						}
					}
				}		
			}
		}
		
		return map;
	}
	
	private void deleteAttachments(Map<String, Set<Integer>> mapDB, Map<String, Set<Integer>> mapFS) throws IOException {
		
		if (!CollectionUtils.isEmpty(mapDB) && !CollectionUtils.isEmpty(mapFS)) {
			
			List<Path> pathsToDelete = new ArrayList<>();
		
			for (Map.Entry<String, Set<Integer>> entry : mapFS.entrySet()) {
				
				List<Integer> tableIdsToDelete = new ArrayList<>();
				
				String tableName = entry.getKey();
				Set<Integer> idsFS = entry.getValue();
				
				if (!CollectionUtils.isEmpty(idsFS)) {
					
					Set<Integer> idsDB = mapDB.get(tableName);
					
					for (Integer idFs : idsFS) {
						
						if (CollectionUtils.isEmpty(idsDB) || (!CollectionUtils.isEmpty(idsDB) && !idsDB.contains(idFs))) {
							
							tableIdsToDelete.add(idFs);
						}
					}

					if (!CollectionUtils.isEmpty(tableIdsToDelete)) {
						
						for (Integer id : tableIdsToDelete) {
							
							List<String> listPathArray = Arrays.asList(this.getBaseDir(), "backoffice", backOffice.getId().toString(), tableName, id.toString());
							String path = String.join(File.separator, listPathArray.toArray(new String[listPathArray.size()]));
							
							pathsToDelete.add(new File(path).toPath());
						}
					}
				}
			}
			
			if (!CollectionUtils.isEmpty(pathsToDelete)) {
				
				for (Path pathToDelete : pathsToDelete) {
					
					FileSystemUtils.deleteRecursively(pathToDelete);
					
					log.debug("Delete path: " + pathsToDelete.toString());
				}
			}
		}
	}
	
	private Set<String> getTableNames() throws SQLException {
		
		Set<String> tables = new HashSet<>();
		
		String qryTables = "SELECT name FROM %s.sg_metadata_tables WHERE support_images = %s OR support_attachments = %s"; 
		
		if (this.dbType.equals(DBType.POSTGRESQL)) {
			
			qryTables = String.format(qryTables, this.backOffice.getSchema(), "TRUE", "TRUE");
		}
		else if (this.dbType.equals(DBType.MSSQL)){
			
			qryTables = String.format(qryTables, this.backOffice.getSchema(), "1", "1");
		}
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = conn.prepareStatement(qryTables);
						
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				tables.add(rs.getString("name"));
			}
		}
		catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		finally {
			
			if (stmt != null) {
				
				stmt.close();
			}
		}
		
		return tables;
	}
	
	private String getFieldNamePk(String table) throws SQLException {
		
		String fieldNamePk = null;
		String sql = null;
		
		if (this.dbType.equals(DBType.POSTGRESQL)) {
		
			sql = String.format( "select column_name " +
								  "  from " +
								  "  ( " +
								  "  SELECT a.attname AS column_name, " +  
								  "                CASE " +  
								  "                    WHEN c.conrelid IS NOT NULL THEN TRUE " +  
								  "                    ELSE FALSE " +  
								  "                END AS primary_key   " +
								  "       FROM pg_attribute AS a " +  
								  "            LEFT OUTER JOIN pg_constraint c ON (c.conrelid = a.attrelid " +  
								  "                                              AND c.contype = 'p' " +  
								  "                                              AND a.attnum = any(c.conkey)) " +  
								  "      WHERE a.attrelid = ('%s' || '.' || '%s')::regclass " +  
								  "        AND a.attnum > 0 " +  
								  "        AND NOT a.attisdropped " +  
								  "  ) a " +
								  "  where primary_key = true ", this.backOffice.getSchema(), table); 
		}
		else if (this.dbType.equals(DBType.MSSQL)) {
			
			sql = String.format("SELECT column_name " +
								"FROM ( " +
								"   SELECT c.column_name AS column_name,   " +
								"          c.ordinal_position AS ordinal_position,   " +
								"          c.data_type AS data_type,   " +
								"          CAST(CASE WHEN c.is_nullable = 'YES' THEN 1 ELSE 0 END AS BIT) AS is_nullable,   " +
								"          CAST(ISNULL(OBJECTPROPERTY(OBJECT_ID(K.CONSTRAINT_SCHEMA + '.' +  K.CONSTRAINT_NAME), 'IsPrimaryKey'), 0) AS BIT) AS primary_key,   " +
								"          CAST(ISNULL(COLUMNPROPERTY(OBJECT_ID(k.TABLE_NAME), c.column_name, 'IsIdentity'), 0) AS BIT) AS is_identity,   " +
								"          c.column_default as default_value    " +
								"     FROM information_schema.columns C LEFT OUTER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE K   " +
								"          ON (c.TABLE_SCHEMA = K.TABLE_SCHEMA   " +
								"               AND c.TABLE_NAME = k.TABLE_NAME    " +
								"               AND C.COLUMN_NAME = K.COLUMN_NAME)     " +
								"    WHERE c.table_schema = '%s'   " +
								"      AND c.table_name = '%s' " +
								") A " +
								"WHERE A.primary_key = 1 ", this.backOffice.getSchema(), table);
		}
		
		if (!StringUtils.isEmpty(sql)) {
		
			PreparedStatement stmt = null;
			
			try {
				
				stmt = conn.prepareStatement(sql);
							
				ResultSet rs = stmt.executeQuery();
				
				if (rs.next()) {
					
					fieldNamePk = rs.getString("column_name");
				}
			}
			catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
			finally {
				
				if (stmt != null) {
					
					stmt.close();
				}
			}
		}
		
		
		return fieldNamePk;
	}
}
