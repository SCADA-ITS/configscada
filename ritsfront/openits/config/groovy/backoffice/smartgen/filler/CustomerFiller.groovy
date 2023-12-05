// Uncomment following line for groovy file
package config.groovy.backoffice.smartgen.filler;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.filler;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeFiller;
import com.revenga.rits.front.openits.backoffice.model.Row;

public class CustomerFiller extends AbstractBackOfficeFiller {

	public CustomerFiller(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onChange() {
		
		Row oldRow = this.getOldRow();
		Row newRow = this.getNewRow();
		
		if (oldRow != null && newRow != null) {
			
			String fullName = getFieldValue(newRow, "fullname");
			
			if (!StringUtils.isEmpty(fullName) && fullName.contains(" ")) {
				
				String[] fullNameSplitted = fullName.split(" ");
				
				if (fullNameSplitted.length > 1) {
					
					setFieldValue(newRow, "name", fullNameSplitted[0].trim());
					
					StringBuilder sbSurname = new StringBuilder();
					
					for (int i = 1; i < fullNameSplitted.length; i++) {
						
						sbSurname.append(fullNameSplitted[i]);
						sbSurname.append(" ");
					}
					
					setFieldValue(newRow, "surname", sbSurname.toString().trim());
				}
			}
		}
		
		return true;
	}
}
