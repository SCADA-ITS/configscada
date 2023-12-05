
// Uncomment following line for groovy file
package config.groovy.backoffice.bus_demo.command;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.command;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeCommand;
import com.revenga.rits.front.openits.backoffice.model.Row;

public class ImportCashCollectionDrivingStaffCommand extends AbstractBackOfficeCommand {

	public ImportCashCollectionDrivingStaffCommand(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onExecute() {
		
		Row oldRow = this.getOldRow();
		Row newRow = this.getNewRow();
		
		return true;
	}
}
