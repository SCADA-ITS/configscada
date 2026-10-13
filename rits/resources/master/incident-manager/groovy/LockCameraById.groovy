import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;

class LockCameraById {

	static final Long ELEMENT_TYPE_CAMERA = 2L;
	static final Long LOCK_CAMERA_COMMAND = 1L;
	static final Long TASK_TYPE_PARAM_CAMERA_ID = 1L;


	org.apache.logging.log4j.Logger log;

	LockCameraById(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}
	
	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {
		
		List<Command> commands = new ArrayList<>();
			
		String cameraId = null;

		if (!CollectionUtils.isEmpty(values)) {
		
			ImsIncidentTypeTaskValue incidentTypeTaskValue = null;
			
			for (ImsIncidentTypeTaskValue value : values) {
			
				if (TASK_TYPE_PARAM_CAMERA_ID.equals(value.getTaskTypeParamId())) {
			
					incidentTypeTaskValue = value;
						break;
				}
			}
			
			cameraId = incidentTypeTaskValue != null ? incidentTypeTaskValue.getValue() : null;
					
			if(cameraId != null) {
				
				try {
						SignallingCommand signallingCommand = new SignallingCommand();
						signallingCommand.setSignallingCommandId(LOCK_CAMERA_COMMAND);
						signallingCommand.setElementTypeId(ELEMENT_TYPE_CAMERA);
						signallingCommand.setElementId(Long.parseLong(cameraId));
						
						log.debug("Enviando comando de enclavamiento de cámara id = " + cameraId);
						commands.add(signallingCommand);
						
				} catch (NumberFormatException e) {
					
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
				}
			}	

		}
		
		return commands;
	}

	
}
