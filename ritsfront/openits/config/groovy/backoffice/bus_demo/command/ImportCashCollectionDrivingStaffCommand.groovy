// Uncomment following line for groovy file
package config.groovy.backoffice.bus_demo.command;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.command;

import java.io.File;
import java.io.FileInputStream;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeCommand;
import com.revenga.rits.front.openits.backoffice.model.Row;

public class ImportCashCollectionDrivingStaffCommand extends AbstractBackOfficeCommand {

	private static final String FILE_BASEDIR = "file.basedir";
	private static final String FIELD_FICHERO = "FICHERO";
	private static final String FIELD_FECHA_PROCESADO = "FECHA_PROCESADO";
	private static final String FIELD_CONTENIDO = "CONTENIDO";
	
	private String baseDir;
	
	public ImportCashCollectionDrivingStaffCommand(org.apache.logging.log4j.Logger log) {
		super(log);
	}

	@Override
	public boolean onExecute() {
		
		Row oldRow = this.getOldRow();
		Row newRow = this.getNewRow();
		
		if (!StringUtils.isEmpty(getFieldValue(newRow, FIELD_FECHA_PROCESADO))) {
			
			this.requestResultMessage.setWarn("El fichero ya ha sido procesado con anterioridad");
		
			return true;
		}
		
		
		if (CollectionUtils.isEmpty(oldRow.getAttachments())) {
			
			this.requestResultMessage.setWarn ("Debe de añadir un adjunto antes de ejecutar la importación");
		
			return true;
		}
		
		File directory = new File(env.getProperty(FILE_BASEDIR));
		String fileName = oldRow.getAttachments().get(0).getFileName();
		
		if (directory.exists() && directory.isDirectory()) {
			
			this.baseDir = directory.getAbsolutePath();
		}
		
		if (!StringUtils.isEmpty(this.baseDir)) {
			
			List<String> listPathArray = Arrays.asList(this.baseDir, "backoffice", backOffice.getId().toString(),
					table.getName().toLowerCase(), String.valueOf(this.getId()), "other", fileName);
			
			String path = String.join(File.separator, listPathArray.toArray(new String[listPathArray.size()]));
			
			File file = new File(path);
			
			if (file.isFile()) {
				
				try {
					
					String fileContent = readFromInputStream(new FileInputStream(file));

					setFieldValue(newRow, FIELD_FICHERO, fileName);
					setFieldValue(newRow, FIELD_FECHA_PROCESADO, String.valueOf(Instant.now().toEpochMilli()));
					setFieldValue(newRow, FIELD_CONTENIDO, fileContent);
					
					this.requestResultMessage.setInfo("El fichero se ha importado correctamente. Pulse sobre el boton aceptar para confirmar el proceso");
					
				} catch (Exception e) {
					
					this.requestResultMessage.setError(e.getMessage());
					this.requestResultMessage.setErrorDescription(ExceptionUtils.getStackTrace(e));
					
					return false;
				}
			}
			else {
				
				this.requestResultMessage.setWarn("La ruta " + path + " no existe");
			
				return true;
			}
		}
		else {
			
			this.requestResultMessage.setWarn("Base dir no existe");
		
			return true;
		}
			
		return true;
	}
}
