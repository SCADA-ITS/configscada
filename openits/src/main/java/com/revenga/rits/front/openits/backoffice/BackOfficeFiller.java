package com.revenga.rits.front.openits.backoffice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeFiller;
import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeGroovy;
import com.revenga.rits.front.openits.model.backoffice.Column;
import com.revenga.rits.front.openits.model.backoffice.Column.Filler;
import com.revenga.rits.front.openits.model.backoffice.Row;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist.PersistResult;
import com.revenga.rits.front.openits.model.backoffice.Table.Persist.PersistValue;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BackOfficeFiller {
	
	BackOfficeProcessor backOfficeProcessor;
	
	public BackOfficeFiller(BackOfficeProcessor backOfficeProcessor) {
		
		this.backOfficeProcessor = backOfficeProcessor;
	}
	
	public PersistResult onChange(Integer backOfficeId, String columnName, Persist persist) {
		
		PersistResult persistResult = new PersistResult();
		BackOffice backOffice = this.backOfficeProcessor.getBackOfficeService().getById(backOfficeId);
		
		if (backOffice != null) {
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
				
				conn.setAutoCommit(false);
				
				Table tableRepository = this.backOfficeProcessor.getBackOfficeManager().getTable(backOffice, persist.getTableName());
				
				if (tableRepository != null) {
					
					Column colFiller = tableRepository.getColumns().stream()
							.filter(e -> e.getName().equals(columnName) && !CollectionUtils.isEmpty(e.getFillers()))
							.findFirst().orElse(null);
					
					if (colFiller != null) {
				
						Table table = (Table) tableRepository.clone();
						
						if (table != null) {
							
							table.getRows().clear();
							
							BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this.backOfficeProcessor);
							
							backOfficeAssignment.assignRows(conn, backOffice, table, null, "id = " + persist.getId());
							
							if (!CollectionUtils.isEmpty(table.getRows())) {
								
								List<PersistValue> persistValues = persist.getPersistValues();
								
								if (!CollectionUtils.isEmpty(persistValues)) {
								
									Row currentRow = table.getRows().get(0);
									Row userRow = (Row) currentRow.clone();
									
									persistValues.forEach(persistValue -> BackOfficeUtil.setValue(userRow,
											persistValue.getColName(), persistValue.getNewValue()));
									
									persistResult.setOldRow(userRow);
									persistResult.setNewRow((Row) userRow.clone());
									
									executeFillers(conn, backOffice, table, colFiller, persist, persistResult);
								}
							}
						}
					}
				}
				
			} catch (Exception e) {
				
				String message = e.getMessage();
				String stackTrace = ExceptionUtils.getStackTrace(e);
				
				persistResult.setError(message);
				persistResult.setErrorDescription(ExceptionUtils.getStackTrace(e));
				
				log.error(message);
				log.debug(stackTrace);
			}
		}
		
		return persistResult;
	}
	

	private void executeFillers(Connection conn, BackOffice backOffice, Table table, Column column, Persist persist,
			PersistResult persistResult) throws Exception {

		List<Filler> fillers = column.getFillers();
		
		if (!CollectionUtils.isEmpty(fillers)) {
			
			for (Filler filler : fillers) {
				
				AbstractBackOfficeGroovy backOfficeGroovy = BackOfficeUtil.newGroovyInstance(
						this.backOfficeProcessor.getGroovyManager(), filler, conn, backOffice.getSchema(), table,
						persist.getId(), persistResult.getOldRow(), persistResult.getNewRow());
				
				if (backOfficeGroovy instanceof AbstractBackOfficeFiller) {
					
					AbstractBackOfficeFiller backOfficeFillerCloned = (AbstractBackOfficeFiller) backOfficeGroovy.clone(); 
					
					boolean fillerResult = false;
					
					fillerResult = backOfficeFillerCloned.onChange();
										
					if (!fillerResult) {
						
						throw new BackOfficeException(
								String.format("Filler %s on table: %s.%s with operation: %s fails.", filler.getGroovy(),
										backOffice.getSchema(), table.getName(), persist.getPersistenceType()));
					}
				}
			}
		}
	}
}
