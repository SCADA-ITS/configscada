// Uncomment following line for groovy file
//Rpackage config.groovy.backoffice.navarra.command;
// or Uncomment following line for java file
package com.revenga.rits.front.openits.backoffice.groovy.command;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeCommand;
import com.revenga.rits.front.openits.backoffice.model.Row;

public class ConfirmCommand extends AbstractBackOfficeCommand {

	public ConfirmCommand(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onExecute() {
		
		Row oldRow = this.getOldRow();
		Row newRow = this.getNewRow();
		
		if (oldRow != null && newRow != null) {

			setFieldValue(newRow, "valoracion", "true");
			
		}

		return true;
	}
}