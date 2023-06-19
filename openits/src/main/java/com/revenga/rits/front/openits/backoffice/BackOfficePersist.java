package com.revenga.rits.front.openits.backoffice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeGroovy;
import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTrigger;
import com.revenga.rits.front.openits.model.backoffice.Request;
import com.revenga.rits.front.openits.model.backoffice.Request.RequestResult;
import com.revenga.rits.front.openits.model.backoffice.Row;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Table.RequestType;
import com.revenga.rits.front.openits.model.backoffice.Table.Trigger;
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
				
				sql = BackOfficeUtil.getDml(conn, backOffice, request);
				
				if (!StringUtils.isEmpty(sql)) {
					
					Table tableRepository = this.backOfficeProcessor.getBackOfficeManager().getTable(backOffice, request.getTableName());
					
					if (tableRepository != null) {
						
						Table table = (Table) tableRepository.clone();
						
						if (table != null) {
							
							table.getRows().clear();
							
							BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this.backOfficeProcessor);
							
							List<Row> rowsOld = backOfficeAssignment.assignRows(conn, backOffice, table, null, "id = " + request.getId());
							
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
									
									List<Row> rowsNew = backOfficeAssignment.assignRows(conn, backOffice, tableNew, null, "id = " + request.getId());
									
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
	
	private void executeTriggers(Trigger.Type triggerType, Connection conn, BackOffice backOffice, Table table,
			Request request, RequestResult requestResult) throws Exception {

		List<Trigger> triggers = table.getTriggers();
		
		if (!CollectionUtils.isEmpty(triggers)) {
			
			for (Trigger trigger : triggers) {
				
				AbstractBackOfficeGroovy backOfficeGroovy = BackOfficeUtil.newGroovyInstance(
						this.backOfficeProcessor.getGroovyManager(), trigger, conn, backOffice.getSchema(), table,
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
