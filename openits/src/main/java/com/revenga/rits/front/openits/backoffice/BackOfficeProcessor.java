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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.revenga.rits.front.openits.backoffice.BackOfficeUtil.DMLType;
import com.revenga.rits.front.openits.model.backoffice.Attachment;
import com.revenga.rits.front.openits.model.backoffice.Request;
import com.revenga.rits.front.openits.model.backoffice.Request.RequestResult;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.service.BackOfficeManager;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.front.openitsdao.service.BackOfficeService;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class BackOfficeProcessor {
	
	public enum ShowData {
		
		ALL,
		NONE,
		ONLY_RELATIONS
	}

	GroovyManager groovyManager;
	BackOfficeService backOfficeService;
	BackOfficeManager backOfficeManager;
	String localeCode;
	ShowData showData;
	String requestTableName;
	
	private Map<String, Table> referencedTables = new HashMap<>();
	
	public BackOfficeProcessor(GroovyManager groovyManager, BackOfficeService backOfficeService, BackOfficeManager backOfficeManager, String localeCode, ShowData showData) {
		
		this.groovyManager = groovyManager;
		this.backOfficeService = backOfficeService;
		this.backOfficeManager = backOfficeManager;
		this.localeCode = localeCode;
		this.showData = showData;
	}
	
	public BackOfficeProcessor(GroovyManager groovyManager, BackOfficeService backOfficeService, BackOfficeManager backOfficeManager, String localeCode, ShowData showData, String requestTableName) {
		
		this.groovyManager = groovyManager;
		this.backOfficeService = backOfficeService;
		this.backOfficeManager = backOfficeManager;
		this.localeCode = localeCode;
		this.showData = showData;
		this.requestTableName = requestTableName;
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
							
							getTable(conn, backOffice, tableName);
						}
					}
					
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

	public List<Table> get(Integer backOfficeId, String tableName, String conditions) {

		List<Table> tables = null;
		
		BackOffice backOffice = this.backOfficeService.getById(backOfficeId);
		
		if (backOffice != null) {
		
			this.referencedTables.clear();
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
			
				getTable(conn, backOffice, tableName, conditions);
				
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
	
	public List<Attachment> getAttachments(Integer backOfficeId, Attachment.Type attachmentType, String tableName, Integer idValue) {

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
	
	public Attachment upload(String attachmentTypeStr, String baseDir, Integer backOfficeId, String tableName, Integer id, String groupName, Integer position, MultipartFile multipartFile) {
		
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
	
	public byte[] downloadImage(String baseDir, Integer backOfficeId, String tableName, Integer imageId) {
		
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
	
	public Resource downloadAttachment(String baseDir, Integer backOfficeId, String tableName, Integer imageId) {
		
		Resource resource = null;
		Attachment attachmentFound = getAttachment(backOfficeId, Attachment.Type.OTHER, tableName, imageId);
		
		if (attachmentFound != null) {
			
			resource = loadFileAsResource(attachmentFound.getPath(baseDir, attachmentFound.getIdValue(), backOfficeId), attachmentFound.getFileName());
		}

		return resource;
	}
	
	public Attachment deleteAttachment(String attachmentTypeStr, String baseDir, Integer backOfficeId, String tableName, Integer imageId) {
		
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
	
	public Table getTable(Connection conn, BackOffice backOffice, String tableName) throws SQLException {
		
		return getTable(conn, backOffice, tableName, null , null);
	}
	
	public Table getTable(Connection conn, BackOffice backOffice, String tableName, Table parentTable) throws SQLException {
		
		return getTable(conn, backOffice, tableName, parentTable, null);
	}
	
	private Table getTable(Connection conn, BackOffice backOffice, String tableName, String conditions) throws SQLException {
		
		return getTable(conn, backOffice, tableName, null, conditions);
	}
	
	private Table getTable(Connection conn, BackOffice backOffice, String tableName, Table parentTable, String conditions) throws SQLException {
		
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
					
					backOfficeAssignment.assignReferTables(conn, backOffice, table);
					
					if (this.showData.equals(ShowData.ALL) || 
						(this.showData.equals(ShowData.ONLY_RELATIONS) && !StringUtils.equals(this.requestTableName, tableName))) {
					
						backOfficeAssignment.assignRows(conn, backOffice, table, parentTable, conditions);
					}
					
					backOfficeAssignment.assignReferencedByTables(conn, backOffice, table);
					
					backOfficeAssignment.translateMetadata(conn, backOffice, table);
				}
			}
			else {
				
				table = new Table(tableName);
				
				this.referencedTables.put(tableName, table);
				
				backOfficeAssignment.assignColumns(conn, backOffice, table);
				
				backOfficeAssignment.assignReferTables(conn, backOffice, table);
				
				if (this.showData.equals(ShowData.ALL) || 
					(this.showData.equals(ShowData.ONLY_RELATIONS) && !StringUtils.equals(this.requestTableName, tableName))) {
					
					backOfficeAssignment.assignRows(conn, backOffice, table, parentTable, conditions);
				}
				
				backOfficeAssignment.assignReferencedByTables(conn, backOffice, table);
				
				backOfficeAssignment.assignMetadata(conn, backOffice, table);			
				
				this.backOfficeManager.putTable(backOffice, table);
			}
			
			if (table != null) {
				
				traceTable(table);
			}
		}
		
		return table;
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
