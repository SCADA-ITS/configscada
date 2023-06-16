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
import com.revenga.rits.front.openits.model.backoffice.Row;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist.PersistResult;
import com.revenga.rits.front.openits.model.backoffice.Table.Trigger;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BackOfficePersist {
	
	BackOfficeProcessor backOfficeProcessor;
	
	public BackOfficePersist(BackOfficeProcessor backOfficeProcessor) {
		
		this.backOfficeProcessor = backOfficeProcessor;
	}
	
	public PersistResult persist(Integer backOfficeId, Persist persist) {
		
		PersistResult persistResult = new PersistResult();
		BackOffice backOffice = this.backOfficeProcessor.getBackOfficeService().getById(backOfficeId);
		String sql = null;
		
		if (backOffice != null) {
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
				
				conn.setAutoCommit(false);
				
				sql = BackOfficeUtil.getDml(conn, backOffice, persist);
				
				if (!StringUtils.isEmpty(sql)) {
					
					Table tableRepository = this.backOfficeProcessor.getBackOfficeManager().getTable(backOffice, persist.getTableName());
					
					if (tableRepository != null) {
						
						Table table = (Table) tableRepository.clone();
						
						if (table != null) {
							
							table.getRows().clear();
							
							BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this.backOfficeProcessor);
							
							List<Row> rowsOld = backOfficeAssignment.assignRows(conn, backOffice, table, null, "id = " + persist.getId());
							
							if (!CollectionUtils.isEmpty(rowsOld) && rowsOld.size() == 1) {
								
								persistResult.setOldRow(rowsOld.get(0));
							}
								
							try (Statement stmt = conn.createStatement()) {
								
								//log.debug(String.format("Executing DML [%s]: %s", persist.getPersistenceType(), sql));
								
								if (this.backOfficeProcessor.getBackOfficeManager().isShowSql()) {
									
									log.debug(stmt);					
								}
								
								executeTriggers(Trigger.Type.BEFORE, conn, backOffice, table, persist, persistResult);
								
								int rowsAffected = stmt.executeUpdate(sql);
								
								//log.debug(String.format("Executed DML [%s] %d rows affected ", persist.getPersistenceType(), rowsAffected));

								if (rowsAffected == 1) {
									
									Table tableNew = new Table(persist.getTableName());
									
									tableNew.setColumns(table.getColumns());
									
									List<Row> rowsNew = backOfficeAssignment.assignRows(conn, backOffice, tableNew, null, "id = " + persist.getId());
									
									if (!CollectionUtils.isEmpty(rowsNew) && rowsNew.size() == 1) {
										
										persistResult.setNewRow(rowsNew.get(0));
									}
									
									executeTriggers(Trigger.Type.AFTER, conn, backOffice, table, persist, persistResult);
								}
								else {
									
									// TODO: Cascade rows affected???
								}
							}
							
							conn.commit();
						}
					}
				}
				
			} catch (Exception e) {
				
				String message = e.getMessage() + (!StringUtils.isEmpty(sql) ? " SQL: " + sql : "");
				String stackTrace = ExceptionUtils.getStackTrace(e);
				
				persistResult.setError(message);
				persistResult.setErrorDescription(ExceptionUtils.getStackTrace(e));
				
				log.error(message);
				log.debug(stackTrace);
			}
		}
		
		return persistResult;
	}
	
	private void executeTriggers(Trigger.Type triggerType, Connection conn, BackOffice backOffice, Table table,
			Persist persist, PersistResult persistResult) throws Exception {

		List<Trigger> triggers = table.getTriggers();
		
		if (!CollectionUtils.isEmpty(triggers)) {
			
			for (Trigger trigger : triggers) {
				
				AbstractBackOfficeGroovy backOfficeGroovy = BackOfficeUtil.newGroovyInstance(
						this.backOfficeProcessor.getGroovyManager(), trigger, conn, backOffice.getSchema(), table,
						persist.getId(), persistResult.getOldRow(), persistResult.getNewRow());
				
				if (backOfficeGroovy instanceof AbstractBackOfficeTrigger) {
					
					backOfficeGroovy.trace(persist.getPersistenceType());
					
					boolean triggerResult = false;
					
					triggerResult = (boolean) Trigger.call(triggerType, persist.getPersistenceType(), backOfficeGroovy);
										
					if (!triggerResult) {
						
						throw new BackOfficeException(
								String.format("Trigger %s on table: %s.%s with operation: %s fails.", trigger.getGroovy(),
										backOffice.getSchema(), table.getName(), persist.getPersistenceType()));
					}
				}
			}
		}
	}
}
