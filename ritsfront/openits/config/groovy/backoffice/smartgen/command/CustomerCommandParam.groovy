// Uncomment following line for groovy file
package config.groovy.backoffice.smartgen.command;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.command;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeCommand;
import com.revenga.rits.front.openits.backoffice.model.Row;
import java.io.Serializable;

public class CustomerCommandParam extends AbstractBackOfficeCommand {

	public CustomerCommandParam(org.apache.logging.log4j.Logger log) {
		super(log);
	}
	
	static class ObjParam implements Serializable {
		
		private static final long serialVersionUID = -655252423901320907L;
		
		private String fieldParam1;
		private Integer fieldParam2;
		private Boolean fieldParam3;
		
		public void setFieldParam1(String fieldParam1) {
			
			this.fieldParam1 = fieldParam1;
		}
		
		public void setFieldParam2(Integer fieldParam2) {
			
			this.fieldParam2 = fieldParam2;
		}
		
		public void setFieldParam3(Boolean fieldParam3) {
			
			this.fieldParam3 = fieldParam3;
		}
		
		public String getFieldParam1() {
			
			return this.fieldParam1;
		}
		
		public Integer getFieldParam2() {
			
			return this.fieldParam2;
		}
		
		public Boolean getFieldParam3() {
			
			return this.fieldParam3;
		}
	}

	@Override
	public boolean onExecute() {
		
		Row oldRow = this.getOldRow();
		Row newRow = this.getNewRow();
		
		if (oldRow != null && newRow != null) {
			
			ObjParam objParam = (ObjParam) this.getParamsObject(ObjParam.class);
			
			String newFullName = objParam.getFieldParam1() + ";" + objParam.getFieldParam2() + ";" + objParam.getFieldParam3();
			
			setFieldValue(newRow, "fullname", newFullName);
			
			this.requestResultMessage.setInfo("Nombre cambiado");
			this.requestResultMessage.setInfoDescription("El nombre completo se ha cambido por: " + newFullName);
		}
		
		return true;
	}
}
