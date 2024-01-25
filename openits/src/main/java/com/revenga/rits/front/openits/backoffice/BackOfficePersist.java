package com.revenga.rits.front.openits.backoffice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeGroovy;
import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTrigger;
import com.revenga.rits.front.openits.backoffice.model.Column;
import com.revenga.rits.front.openits.backoffice.model.Request;
import com.revenga.rits.front.openits.backoffice.model.Request.RequestResult;
import com.revenga.rits.front.openits.backoffice.model.Row;
import com.revenga.rits.front.openits.backoffice.model.Table;
import com.revenga.rits.front.openits.backoffice.model.Table.RequestType;
import com.revenga.rits.front.openits.backoffice.model.Table.Trigger;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BackOfficePersist extends BackOfficeAction {
	
	public BackOfficePersist(BackOfficeProcessor backOfficeProcessor) {
		
		super(backOfficeProcessor);
	}
	
	public RequestResult persist(Integer backOfficeId, Request request) {
		
		RequestResult requestResult = new RequestResult();
		BackOffice backOffice = this.backOfficeProcessor.getBackOfficeService().getById(backOfficeId);
		String sql = null;
		
		if (backOffice != null) {
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
				
				if (request.getRequestType() != RequestType.INSERT &&
					request.getRequestType() != RequestType.UPDATE &&
					request.getRequestType() != RequestType.DELETE) {
					
					throw new BackOfficeException("Wrong request type: " + request.getRequestType()
							+ ". RequestType expected is: " + String.join(", ", RequestType.INSERT.toString(),
									RequestType.UPDATE.toString(), RequestType.DELETE.toString()));
				}
				
				conn.setAutoCommit(false);
				
				Table tableRepository = this.backOfficeProcessor.getBackOfficeManager().getTable(backOffice,
						request.getTableName());
				
				if (tableRepository != null) {
				
					sql = BackOfficeUtil.getDml(conn, backOffice, tableRepository, request);
				
					if (!StringUtils.isEmpty(sql)) {
					
						Table table = (Table) tableRepository.clone();
						
						if (table != null) {
							
							Column columnId = table.getColumns().stream()
									.filter(e -> e.getPrimaryKey().booleanValue()).findFirst().orElse(null);
							
							if (columnId != null) {
								
								table.getRows().clear();
								
								BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this.backOfficeProcessor);
								
								List<Row> rowsOld = backOfficeAssignment.assignRows(conn, backOffice, table, null,
										String.format("%s = %d", columnId.getName(), request.getId()));
								
								if (!CollectionUtils.isEmpty(rowsOld) && rowsOld.size() == 1) {
									
									requestResult.setOldRow(rowsOld.get(0));
								}
									
								try (Statement stmt = conn.createStatement()) {
									
									if (this.backOfficeProcessor.getBackOfficeManager().isShowSql()) {
										
										log.debug(stmt);					
									}
									
									executeTriggers(Trigger.Type.BEFORE, conn, backOffice, table, request, requestResult);
									
									int rowsAffected = stmt.executeUpdate(sql);
									
									if (rowsAffected == 1) {
										
										Table tableNew = new Table(request.getTableName());
										
										tableNew.setColumns(table.getColumns());
										
										List<Row> rowsNew = backOfficeAssignment.assignRows(conn, backOffice, tableNew,
												null, columnId.getName() + " = " + request.getId());
										
										if (!CollectionUtils.isEmpty(rowsNew) && rowsNew.size() == 1) {
											
											requestResult.setNewRow(rowsNew.get(0));
										}

										executeTriggers(Trigger.Type.AFTER, conn, backOffice, table, request, requestResult);
									}
								}
								
								conn.commit();
							}
						}
					}
				}
				
			} catch (Exception e) {
				
				String message = e.getMessage() + (!StringUtils.isEmpty(sql) ? " SQL: " + sql : "");
				String stackTrace = ExceptionUtils.getStackTrace(e);
				
				requestResult.getRequestResultMessage().setError(message);
				requestResult.getRequestResultMessage().setErrorDescription(ExceptionUtils.getStackTrace(e));
				
				log.error(message);
				log.debug(stackTrace);
			}
		}
		
		return requestResult;
	}
	
	public RequestResult importTable(Integer backOfficeId, String tableName, MultipartFile multipartFile) {
	
		RequestResult requestResult = new RequestResult();
		BackOffice backOffice = this.backOfficeProcessor.getBackOfficeService().getById(backOfficeId);
		
		if (backOffice != null) {
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
				
				conn.setAutoCommit(false);
				
				Table tableRepository = this.backOfficeProcessor.getBackOfficeManager().getTable(backOffice, tableName);
				
				if (tableRepository != null) {
					
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
						
						List<String> lines = new ArrayList<>();
					
						while(reader.ready()) {
							
						     lines.add(reader.readLine());
						}
						
						if (!CollectionUtils.isEmpty(lines) && lines.size() > 1) {
							
							importLines(conn, requestResult, lines, tableName);
						}
						else {
							
							// TODO Error
						}
					}
				}
			} 
			catch (Exception e) {
				
				String message = e.getMessage();
				String stackTrace = ExceptionUtils.getStackTrace(e);
				
				requestResult.getRequestResultMessage().setError(message);
				requestResult.getRequestResultMessage().setErrorDescription(ExceptionUtils.getStackTrace(e));
				
				log.error(message);
				log.debug(stackTrace);
			}
		}
		
		return requestResult;
	}
	
	private void importLines(Connection conn, RequestResult requestResult, List<String> lines, String tableName) throws SQLException {
		
		String separator = String.valueOf(this.backOfficeProcessor.getBackOfficeManager().getCsvCharSeparator()); 
		
		String fieldsLine = lines.remove(0);
		
		fieldsLine = fieldsLine.replace(separator, ",");
		
		int colsCount = (fieldsLine.split(",")).length;
		
		String paramsLine = "?,".repeat(colsCount);
		
		paramsLine = String.join(", ", paramsLine.split(","));
		
		String sql = String.format("INSERT INTO %s(%s) VALUES(%s)", tableName, fieldsLine, paramsLine);
		
		int lineOffset = 1;
		String line = "";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			boolean lineCheck = true;
			
			for (lineOffset = 1; lineCheck && lineOffset <= lines.size(); lineOffset++) {
				
				line = lines.get(lineOffset - 1);
				
				String[] colValues = line.split(separator);
				
				lineCheck &= (colValues.length == colsCount);
				
				if (lineCheck) {
				
					for (int colPos = 1; colPos <= colsCount; colPos++) {
						
						pstmt.setObject(colPos, colValues[colPos - 1]);
					}
					
					pstmt.executeUpdate();
				}
			}
			
			if (lineCheck) {
				
				conn.commit();
			}
			else {
				
				throw new Exception("Line format error.");
			}
			
		}
		catch (Exception e) {
			
			conn.rollback();
			
			String message = e.getMessage() + (!StringUtils.isEmpty(sql) ? " SQL: " + sql : "")
					+ "; Line #" + lineOffset + ": " + line;
			String stackTrace = ExceptionUtils.getStackTrace(e);
			
			requestResult.getRequestResultMessage().setError(message);
			requestResult.getRequestResultMessage().setErrorDescription(ExceptionUtils.getStackTrace(e));
			
			log.error(message);
			log.debug(stackTrace);
		}
	}
	
	private void executeTriggers(Trigger.Type triggerType, Connection conn, BackOffice backOffice, Table table,
			Request request, RequestResult requestResult) throws Exception {

		List<Trigger> triggers = table.getTriggers();
		
		if (!CollectionUtils.isEmpty(triggers)) {
			
			for (Trigger trigger : triggers) {
				
				AbstractBackOfficeGroovy backOfficeGroovy = BackOfficeUtil.newGroovyInstance(this.backOfficeProcessor.getEnv(),
						this.backOfficeProcessor.getGroovyManager(), trigger, conn, backOffice, table,
						request.getId(), requestResult);
				
				if (backOfficeGroovy instanceof AbstractBackOfficeTrigger) {
					
					backOfficeGroovy.trace(request.getRequestType());
					
					boolean triggerResult = false;
					
					triggerResult = (boolean) Trigger.call(triggerType, request.getRequestType(), backOfficeGroovy);
										
					if (!triggerResult) {
						
						throw new BackOfficeException(
								String.format("Trigger %s on table: %s.%s with operation: %s fails.", trigger.getGroovy(),
										backOffice.getSchema(), table.getName(), request.getRequestType()));
					}
				}
			}
		}
	}
}
