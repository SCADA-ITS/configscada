package com.revenga.rits.front.openits.backoffice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeFiller;
import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeGroovy;
import com.revenga.rits.front.openits.backoffice.model.Column;
import com.revenga.rits.front.openits.backoffice.model.Request;
import com.revenga.rits.front.openits.backoffice.model.Row;
import com.revenga.rits.front.openits.backoffice.model.Table;
import com.revenga.rits.front.openits.backoffice.model.Column.Filler;
import com.revenga.rits.front.openits.backoffice.model.Request.RequestInput;
import com.revenga.rits.front.openits.backoffice.model.Request.RequestResult;
import com.revenga.rits.front.openits.backoffice.model.Table.RequestType;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BackOfficeFiller extends BackOfficeAction {
	
	public BackOfficeFiller(BackOfficeProcessor backOfficeProcessor) {
		
		super(backOfficeProcessor);
	}
	
	public RequestResult onChange(Integer backOfficeId, Request request) {
		
		RequestResult requestResult = new RequestResult();
		BackOffice backOffice = this.backOfficeProcessor.getBackOfficeService().getById(backOfficeId);
		
		if (backOffice != null) {
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
				
				if (request.getRequestType() != RequestType.FILLER) {
					
					throw new BackOfficeException("Wrong request type: " + request.getRequestType()
							+ ". RequestType expected is: " + RequestType.FILLER);
				}
				
				Table tableRepository = this.backOfficeProcessor.getBackOfficeManager().getTable(backOffice, request.getTableName());
				
				if (tableRepository != null) {
					
					Column colFiller = tableRepository.getColumns().stream()
							.filter(e -> e.getName().equals(request.getColumnName()) && !CollectionUtils.isEmpty(e.getFillers()))
							.findFirst().orElse(null);
					
					if (colFiller != null) {
				
						Table table = (Table) tableRepository.clone();
						
						if (table != null) {
							
							table.getRows().clear();
							
							BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this.backOfficeProcessor);
							
							Column columnId = table.getColumns().stream().filter(e -> e.getPrimaryKey().booleanValue())
									.findFirst().orElse(null);
							
							if (columnId != null) {
								
								backOfficeAssignment.assignRows(conn, backOffice, table, null,
										String.format("%s = %d", columnId.getName(), request.getId()));
								
								if (!CollectionUtils.isEmpty(table.getRows())) {
									
									List<RequestInput> requestInputs = request.getRequestInput();
									
									if (!CollectionUtils.isEmpty(requestInputs)) {
									
										Row currentRow = table.getRows().get(0);
										Row userRow = (Row) currentRow.clone();
										
										requestInputs.forEach(persistValue -> BackOfficeUtil.setValue(userRow,
												persistValue.getColName(), persistValue.getNewValue()));
										
										requestResult.setOldRow(userRow);
										requestResult.setNewRow((Row) userRow.clone());
										
										executeFillers(conn, backOffice, table, colFiller, request, requestResult);
									}
								}
							}
						}
					}
				}
				
			} catch (Exception e) {
				
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
	

	private void executeFillers(Connection conn, BackOffice backOffice, Table table, Column column, Request request,
			RequestResult requestResult) throws Exception {

		List<Filler> fillers = column.getFillers();
		
		if (!CollectionUtils.isEmpty(fillers)) {
			
			for (Filler filler : fillers) {
				
				AbstractBackOfficeGroovy backOfficeGroovy = BackOfficeUtil.newGroovyInstance(
						this.backOfficeProcessor.getGroovyManager(), filler, conn, backOffice.getSchema(), table,
						request.getId(), requestResult);
				
				if (backOfficeGroovy instanceof AbstractBackOfficeFiller) {
					
					AbstractBackOfficeFiller backOfficeFillerCloned = (AbstractBackOfficeFiller) backOfficeGroovy.clone(); 
					
					boolean fillerResult = false;
					
					fillerResult = backOfficeFillerCloned.onChange();
										
					if (!fillerResult) {
						
						throw new BackOfficeException(
								String.format("Filler %s on table: %s.%s with operation: %s fails.", filler.getGroovy(),
										backOffice.getSchema(), table.getName(), request.getRequestType()));
					}
				}
			}
		}
	}
}
