package com.revenga.rits.front.openits.backoffice;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.revenga.rits.front.openits.backoffice.BackOfficeUtil.DMLType;
import com.revenga.rits.front.openits.backoffice.model.Attachment;
import com.revenga.rits.front.openits.backoffice.model.Request;
import com.revenga.rits.front.openits.backoffice.model.Request.RequestResult;
import com.revenga.rits.front.openits.backoffice.model.Table;
import com.revenga.rits.front.openits.service.BackOfficeManager;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.front.openitsdao.service.BackOfficeService;
import com.revenga.rits.front.openitsdao.service.GridService;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class BackOfficeProcessor {
	
	private static final String FILE_BASEDIR = "file.basedir";
	
	public enum ShowData {
		
		ALL,
		NONE,
		ONLY_RELATIONS
	}

	GroovyManager groovyManager;
	BackOfficeService backOfficeService;
	BackOfficeManager backOfficeManager;
	GridService gridService;
	String localeCode;
	ShowData showData;
	String requestTableName;
	
	private Environment env;
	private String baseDir;
	
	private Map<String, Table> referencedTables = new HashMap<>();
	
	public BackOfficeProcessor(BackOfficeContext backOfficeCtx, String localeCode, ShowData showData) {
		
		init(backOfficeCtx, localeCode, showData, null);
	}
	
	public BackOfficeProcessor(BackOfficeContext backOfficeCtx, String localeCode, ShowData showData, String requestTableName) {
		
		init(backOfficeCtx, localeCode, showData, requestTableName);
	}
	
	private void init(BackOfficeContext backOfficeCtx, String localeCode, ShowData showData, String requestTableName) {
		
		this.groovyManager = backOfficeCtx.getGroovyManager();
		this.backOfficeService = backOfficeCtx.getBackOfficeService();
		this.backOfficeManager = backOfficeCtx.getBackOfficeManager();
		this.gridService = backOfficeCtx.getGridService();
		
		this.localeCode = localeCode;
		this.showData = showData;
		this.requestTableName = requestTableName;
		
		this.env = backOfficeCtx.getEnv();
		this.baseDir = backOfficeCtx.getEnv().getProperty(FILE_BASEDIR);
	}
	
	public List<Table> getAll(Integer backOfficeId) {
		
		List<Table> tables = null;
		BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
		
		if (backOffice != null) {
			
			this.backOfficeManager.clearTables(backOffice);
			
			this.referencedTables.clear();
		
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
				
				String sql = BackOfficeUtil.getDmlLib(BackOfficeUtil.getDBType(conn), DMLType.TABLES);
				
				try (PreparedStatement stmt = conn.prepareStatement(sql)) {
					
					stmt.setString(1, backOffice.getSchema());
					
					if (this.backOfficeManager.isShowSql()) {
						
						log.debug(stmt);					
					}
					
					ResultSet rs = stmt.executeQuery();
					
					while (rs.next()) {
						
						String tableName = rs.getString("table_name");
						
						if (!this.referencedTables.containsKey(tableName)) {
							
							getTable(conn, backOffice, tableName, null, null, null);
						}
					}
					
					getViews(backOffice, conn);
					
					if (!CollectionUtils.isEmpty(this.referencedTables)) {
						
						tables = this.referencedTables.values().stream().collect(Collectors.toList());
						
						Collections.sort(tables, Comparator.comparing(Table::getName));
					}
				}
			}
			catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return tables;
	}
	
	private List<Table> getViews(BackOffice backOffice, Connection conn) throws SQLException {
		
		List<Table> views = null;
		
		String sql = String.format("SELECT name, sql_view " +
					 			   "  FROM %s.sg_metadata_tables " +
					 			   " WHERE sql_view IS NOT NULL", backOffice.getSchema());
		
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				if (views == null) {
					
					views = new ArrayList<>();
				}
				
				String viewName = rs.getString("name");
				String sqlView = rs.getString("sql_view");
				
				if (!this.referencedTables.containsKey(viewName)) {
					
					Table view = getTable(conn, backOffice, viewName, null, null, sqlView);
					
					views.add(view);
				}
			}
		}
		
		return views;
	}

	public List<Table> get(Integer backOfficeId, String tableName, String conditions) {

		List<Table> tables = null;
		
		BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
		
		if (backOffice != null) {
		
			this.referencedTables.clear();
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
			
				getTable(conn, backOffice, tableName, null, conditions, null);
				
				if (!CollectionUtils.isEmpty(this.referencedTables)) {
				
					tables = this.referencedTables.values().stream().collect(Collectors.toList());
					
					Collections.sort(tables, Comparator.comparing(Table::getName));
				}
			}
			catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return tables;
	}
	
	public String translate(Integer backOfficeId, String label) {
		
		String labelTranslated = label;
		BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
		
		if (backOffice != null) {
		
			try {
				
				labelTranslated = this.backOfficeManager.translate(backOffice, null, this.localeCode, label);
				
			} catch (SQLException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return labelTranslated;
	}
	
	public RequestResult importTable(Integer backOfficeId, String tableName, MultipartFile multipartFile) {
		
		return (new BackOfficePersist(this)).importTable(backOfficeId, tableName, multipartFile);
	}
	
	public List<Attachment> getAttachments(Integer backOfficeId, Attachment.Type attachmentType, String tableName, Long idValue) {

		List<Attachment> attachments = null;
		
		BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
		
		if (backOffice != null) {
		
			Table table = this.referencedTables.get(tableName);
			
			if (table == null && this.backOfficeManager.containsTable(backOffice, tableName)) {
			
				table = this.backOfficeManager.getTable(backOffice, tableName);
			}
			
			if (table != null) {
				
				try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
						AES.decrypt(backOffice.getPassword()))) {
					
					BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this);
				
					attachments = backOfficeAssignment.getAttachments(conn, backOffice, attachmentType, table, idValue);
				}
				catch (Exception e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

		return attachments;
	}
	
	public Attachment editAttachment(String attachmentTypeStr, Integer backOfficeId, String tableName, Attachment attachment) {
		
		Attachment newAttachment = null;
		
		Attachment.Type attachmentType = StringUtils.equalsIgnoreCase(
				Attachment.Type.IMAGE.toString(), attachmentTypeStr) ? Attachment.Type.IMAGE : Attachment.Type.OTHER;
		
		BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
		Attachment attachmentFound = getAttachment(backOfficeId, attachmentType, tableName, attachment.getId());
		
		if (backOffice != null && attachmentFound != null) {
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
				
				Attachment editAttachment = (Attachment) attachmentFound.clone();
				editAttachment.change(attachment);
				
				BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this);
			
				newAttachment = backOfficeAssignment.editAttachment(conn, backOffice, editAttachment);
			}
			catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return newAttachment;
	}
	
	public Attachment upload(String attachmentTypeStr, Integer backOfficeId, String tableName, Long id, String groupName, Integer position, MultipartFile multipartFile) {
		
		Attachment newAttachment = null;
		BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
		
		Attachment.Type attachmentType = StringUtils.equalsIgnoreCase(
				Attachment.Type.IMAGE.toString(), attachmentTypeStr) ? Attachment.Type.IMAGE : Attachment.Type.OTHER;
		
		if (backOffice != null) {
			
			Table table = this.referencedTables.get(tableName);
			
			if (table == null && this.backOfficeManager.containsTable(backOffice, tableName)) {
			
				table = this.backOfficeManager.getTable(backOffice, tableName);
			}
			
			if (table != null) {
				
				Attachment attachmentInput = new Attachment();
		    	
		    	attachmentInput.setType(attachmentType);
		    	attachmentInput.setTable(table);
		    	attachmentInput.setIdValue(id);
		    	attachmentInput.setGroupName(groupName);
		    	attachmentInput.setFileName(multipartFile.getOriginalFilename());
		    	attachmentInput.setPosition(position);
		    	
		    	newAttachment = putAttachment(baseDir, backOfficeId, attachmentInput, multipartFile);
			}
		}
					
		return newAttachment;
	}
	
	public byte[] downloadImage(Integer backOfficeId, String tableName, Integer imageId) {
		
		byte[] fileBytes = null;
		
		Attachment attachmentFound = getAttachment(backOfficeId, Attachment.Type.IMAGE, tableName, imageId);
		
		if (attachmentFound != null) {
			
			Resource resource = loadFileAsResource(attachmentFound.getPath(baseDir, attachmentFound.getIdValue(), backOfficeId), attachmentFound.getFileName());
			
			try {
				
				if (resource != null) {
					
					fileBytes = Files.readAllBytes(Path.of(resource.getURI()));
				}
				
			} catch (IOException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return fileBytes;
	}
	
	public Resource downloadAttachment(Integer backOfficeId, String tableName, Integer imageId) {
		
		Resource resource = null;
		Attachment attachmentFound = getAttachment(backOfficeId, Attachment.Type.OTHER, tableName, imageId);
		
		if (attachmentFound != null) {
			
			resource = loadFileAsResource(attachmentFound.getPath(baseDir, attachmentFound.getIdValue(), backOfficeId), attachmentFound.getFileName());
		}

		return resource;
	}
	
	public Attachment deleteAttachment(String attachmentTypeStr, Integer backOfficeId, String tableName, Integer imageId) {
		
		Attachment deletedAttachment = null;
		
		Attachment.Type attachmentType = StringUtils.equalsIgnoreCase(
				Attachment.Type.IMAGE.toString(), attachmentTypeStr) ? Attachment.Type.IMAGE : Attachment.Type.OTHER;
		
		Attachment attachmentInput = getAttachment(backOfficeId, attachmentType, tableName, imageId);
		
		BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
		
		if (backOffice != null && attachmentInput != null) {
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
				
				BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this);
			
				deletedAttachment = backOfficeAssignment.deleteAttachment(conn, backOffice, attachmentInput);
				
				String targetDirStr = attachmentInput.getPath(baseDir, attachmentInput.getIdValue(), backOfficeId);
				String pathName = targetDirStr + File.separator + attachmentInput.getFileName();
				
				Path targetLocation = Path.of(pathName);
				
				Files.deleteIfExists(targetLocation);
			}
			catch (Exception e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return deletedAttachment;
	}
	
	private Attachment putAttachment(String baseDir, Integer backOfficeId, Attachment attachmentInput, MultipartFile multipartFile) {
		
		Attachment newAttachment = null;
		
		try {
			
			BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
			
			if (backOffice != null && multipartFile != null && multipartFile.getOriginalFilename() != null) {
			
				String tableName = attachmentInput.getTable().getName();
				String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
				
				// Check if the file's name contains invalid characters
	            if(!fileName.contains("..")) {
	            	
	            	// Check if target directory exists
	            	
					String targetDirStr = String
							.join(File.separator, baseDir, "backoffice", backOfficeId.toString(), tableName,
									attachmentInput.getIdValue().toString(), attachmentInput.getType().toString())
							.toLowerCase();
	            	
					String pathName = targetDirStr + File.separator + multipartFile.getOriginalFilename();
	            	
	            	File targetDir = new File(targetDirStr);
	            	
	            	if (!targetDir.exists()) {
	            		
	            		targetDir.mkdirs();
	            	}
	            	
	                Path targetLocation = Path.of(pathName);
	                Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	                
	                // Check if file is created
	                File file = new File(targetLocation.toString());
	                
	                if (file.isFile()) {
	                	
	                	try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
	    						AES.decrypt(backOffice.getPassword()))) {
	    					
	    					BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this);
	    				
	    					newAttachment = backOfficeAssignment.putAttachment(conn, backOffice, attachmentInput);
	    				}
	    				catch (Exception e) {

	    					log.error(e.getMessage());
	    					log.debug(ExceptionUtils.getStackTrace(e));
	    				}
	                }
	            }
			}
            
        } catch (IOException e) {
            
        	log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }
		
		return newAttachment;
	}
	
	private Attachment getAttachment(Integer backOfficeId, Attachment.Type attachmentType, String tableName, Integer id) {

		Attachment attachmentFound = null;
		
		BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
		
		if (backOffice != null) {
		
			Table table = this.referencedTables.get(tableName);
			
			if (table == null && this.backOfficeManager.containsTable(backOffice, tableName)) {
			
				table = this.backOfficeManager.getTable(backOffice, tableName);
			}
			
			if (table != null) {
				
				try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
						AES.decrypt(backOffice.getPassword()))) {
					
					BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this);
				
					attachmentFound = backOfficeAssignment.getAttachment(conn, backOffice, attachmentType,
							table, id);
				}
				catch (Exception e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

		return attachmentFound;
	}
	
	private Resource loadFileAsResource(String path, String fileName) {
        
		Resource resource = null;
		
		try {
        	
        	Path filePath = Path.of(path + File.separator + fileName); 

        	resource = new UrlResource(filePath.toUri());
        	
            if(resource.exists()) {
            	
                return resource;
                
            } else {
            	
            	throw new MalformedURLException("File not found: " + fileName); 
            }
        } catch (MalformedURLException e) {
            
        	log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }
		
		return null;
    }
	
	public RequestResult persist(Integer backOfficeId, Request request) {
		
		return (new BackOfficePersist(this)).persist(backOfficeId, request);
	}
	
	public RequestResult command(Integer backOfficeId, Request request) {
		
		return (new BackOfficeCommand(this)).onExecute(backOfficeId, request);
	}
	
	public RequestResult filler(Integer backOfficeId, Request request) {
		
		return (new BackOfficeFiller(this)).onChange(backOfficeId, request);
	}
		
	public Table getTable(Connection conn, BackOffice backOffice, String tableName, Table parentTable, String conditions, String sqlView) throws SQLException {
		
		Table table = this.referencedTables.get(tableName);
		
		if (table == null) {
			
			BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this);
			
			if (this.backOfficeManager.containsTable(backOffice, tableName)) {
				
				Table tableRepository = this.backOfficeManager.getTable(backOffice, tableName);
				
				if (tableRepository != null) {
					
					try {
						
						table = (Table) tableRepository.clone();
						
					} catch (CloneNotSupportedException e) {
						
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
				
				if (table != null) {
					
					table.getRows().clear();
						
					this.referencedTables.put(tableName, table);
					
					setTableAssignments(conn, backOfficeAssignment, backOffice, table, parentTable, conditions);
					
					backOfficeAssignment.translateMetadata(conn, backOffice, table);
				}
			}
			else {
				
				table = new Table(tableName);
				
				this.referencedTables.put(tableName, table);
				
				if (StringUtils.isEmpty(sqlView)) {
					
					backOfficeAssignment.assignColumns(conn, backOffice, table);
					
				}
				else {
					
					table.setSqlView(sqlView);
					
					backOfficeAssignment.assignColumnsView(conn, table);
				}
				
				setTableAssignments(conn, backOfficeAssignment, backOffice, table, parentTable, conditions);
				
				backOfficeAssignment.assignMetadata(conn, backOffice, table);			
				
				this.backOfficeManager.putTable(backOffice, table);
			}
			
			if (table != null) {
				
				traceTable(table);
			}
		}
		
		return table;
	}
	
	public Table getView(Connection conn, BackOffice backOffice, String viewName, String sql, String conditions) throws SQLException {
		
		Table view = this.referencedTables.get(viewName);
		
		if (view == null) {
			
			BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this);
			
			if (this.backOfficeManager.containsTable(backOffice, viewName)) {
				
				Table tableRepository = this.backOfficeManager.getTable(backOffice, viewName);
				
				if (tableRepository != null) {
					
					try {
						
						view = (Table) tableRepository.clone();
						
					} catch (CloneNotSupportedException e) {
						
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}
				
				if (view != null) {
					
					view.getRows().clear();
						
					this.referencedTables.put(viewName, view);
					
					setTableAssignments(conn, backOfficeAssignment, backOffice, view, null, conditions);
					
					backOfficeAssignment.translateMetadata(conn, backOffice, view);
				}
			}
			else {
				
				view = new Table(viewName);
				
				view.setSqlView(sql);				
				
				this.referencedTables.put(viewName, view);
				
				
				
				
				
				
				setTableAssignments(conn, backOfficeAssignment, backOffice, view, null, conditions);
				
				backOfficeAssignment.assignMetadata(conn, backOffice, view);			
				
				this.backOfficeManager.putTable(backOffice, view);
			}
			
			if (view != null) {
				
				traceTable(view);
			}
		}
		
		return view;
	}
	
	private void setTableAssignments(Connection conn, BackOfficeAssignment backOfficeAssignment, BackOffice backOffice,
			Table table, Table parentTable, String conditions) throws SQLException {
		
		backOfficeAssignment.assignReferTables(conn, backOffice, table);
		
		if (this.showData.equals(ShowData.ALL) || 
			(this.showData.equals(ShowData.ONLY_RELATIONS) && !StringUtils.equals(this.requestTableName, table.getName()))) {
		
			backOfficeAssignment.assignRows(conn, backOffice, table, parentTable, conditions);
		}
		
		if (StringUtils.equals(this.requestTableName, table.getName()) && StringUtils.isEmpty(table.getSqlView())) {
			
			backOfficeAssignment.assignReferencedByTables(conn, backOffice, table);
		}
	}
	
	private void traceTable(Table table) {
		
		if (this.backOfficeManager.isTraceTable()) {
			
			log.debug(String.format("Table: %s(%d)", table.getName(), table.getId()));
			
			if (!CollectionUtils.isEmpty(table.getColumns())) {

				log.debug(String.format("   >> Columns: %s",
						String.join(",", table.getColumns().stream()
								.map(e -> String.format("%s(%d) [%s%s]", e.getName(), e.getId(), e.getColType(),
										(e.getRefColumn() != null
												? String.format(" FK -> %s.%s", e.getRefColumn().getTable().getName(), e.getRefColumn().getName())
												: "")
										))
								.collect(Collectors.toList()))));
			}
			
			if (!CollectionUtils.isEmpty(table.getRows())) {
				
				log.debug(String.format("   >> Rows: %d", table.getRows().size()));
			}
		}
	}
}
