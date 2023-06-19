package com.revenga.rits.front.openits.backoffice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeCommand;
import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeGroovy;
import com.revenga.rits.front.openits.model.backoffice.Request;
import com.revenga.rits.front.openits.model.backoffice.Request.RequestInput;
import com.revenga.rits.front.openits.model.backoffice.Request.RequestResult;
import com.revenga.rits.front.openits.model.backoffice.Row;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.model.backoffice.Table.Command;
import com.revenga.rits.front.openits.model.backoffice.Table.RequestType;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BackOfficeCommand extends BackOfficeAction {
	
	public BackOfficeCommand(BackOfficeProcessor backOfficeProcessor) {
		
		super(backOfficeProcessor);
	}
	
	public RequestResult onExecute(Integer backOfficeId, Request request) {
		
		RequestResult requestResult = new RequestResult();
		BackOffice backOffice = this.backOfficeProcessor.getBackOfficeService().getById(backOfficeId);

		if (backOffice != null) {
			
			try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
					AES.decrypt(backOffice.getPassword()))) {
				
				if (request.getRequestType() != RequestType.COMMAND) {
					
					throw new BackOfficeException("Wrong request type: " + request.getRequestType()
							+ ". RequestType expected is: " + RequestType.COMMAND);
				}
				
				Table tableRepository = this.backOfficeProcessor.getBackOfficeManager().getTable(backOffice, request.getTableName());
				
				if (tableRepository != null && !CollectionUtils.isEmpty(tableRepository.getCommands())) {
					
					Command cmd = tableRepository.getCommands().stream().filter(e -> e.getId().equals(request.getCommandId()))
							.findFirst().orElse(null);
					
					if (cmd != null) {
				
						Table table = (Table) tableRepository.clone();
						
						if (table != null) {
							
							table.getRows().clear();
							
							BackOfficeAssignment backOfficeAssignment = new BackOfficeAssignment(this.backOfficeProcessor);
							
							backOfficeAssignment.assignRows(conn, backOffice, table, null, "id = " + request.getId());
							
							if (!CollectionUtils.isEmpty(table.getRows())) {
								
								List<RequestInput> requestInputs = request.getRequestInput();
								
								if (!CollectionUtils.isEmpty(requestInputs)) {
								
									Row currentRow = table.getRows().get(0);
									Row userRow = (Row) currentRow.clone();
									
									requestInputs.forEach(persistValue -> BackOfficeUtil.setValue(userRow,
											persistValue.getColName(), persistValue.getNewValue()));
									
									requestResult.setOldRow(userRow);
									requestResult.setNewRow((Row) userRow.clone());
									
									executeCommand(conn, backOffice, table, cmd, request, requestResult);
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
	

	private void executeCommand(Connection conn, BackOffice backOffice, Table table, Command command, Request request,
			RequestResult requestResult) throws Exception {

		AbstractBackOfficeGroovy backOfficeGroovy = BackOfficeUtil.newGroovyInstance(
				this.backOfficeProcessor.getGroovyManager(), command, conn, backOffice.getSchema(), table,
				request.getId(), requestResult);
		
		if (backOfficeGroovy instanceof AbstractBackOfficeCommand) {
			
			AbstractBackOfficeCommand backOfficeCommandCloned = (AbstractBackOfficeCommand) backOfficeGroovy.clone(); 
			
			boolean commandResult = false;
			
			commandResult = backOfficeCommandCloned.onExecute();
								
			if (!commandResult) {
				
				throw new BackOfficeException(
						String.format("Filler %s on table: %s.%s with operation: %s fails.", command.getGroovy(),
								backOffice.getSchema(), table.getName(), request.getRequestType()));
			}
		}
	}
}
