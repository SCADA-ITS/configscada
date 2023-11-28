// Uncomment following line for groovy file
package config.groovy.backoffice.smartgen.command;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.command;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeCommand;
import com.revenga.rits.front.openits.backoffice.model.Row;

public class CustomerCommand extends AbstractBackOfficeCommand {

	public CustomerCommand(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onExecute() {
		
		Row oldRow = this.getOldRow();
		Row newRow = this.getNewRow();
		
		if (oldRow != null && newRow != null) {
			
			String name = getFieldValue(newRow, "name");
			String surname = getFieldValue(newRow, "surname");
			
			setFieldValue(newRow, "fullname", name + " " + surname);
			
			this.requestResultMessage.setInfo("Nombre cambiado");
			this.requestResultMessage.setInfoDescription("El nombre completo se ha cambido");
		}
		
		return true;
	}
}
