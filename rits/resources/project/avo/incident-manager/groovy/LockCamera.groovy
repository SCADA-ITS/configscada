import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetFireAlarmCommand;
import com.revenga.rits.back.incident.manager.service.EntitiesManager;
import com.revenga.rits.back.incident.manager.service.IncidentEntitiesManager;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;

class LockCamera {

	static final Long ELEMENT_TYPE_CAMERA = 2L;
	static final Long ELEMENT_TYPE_SOS = 11L;
	static final Long LOCK_CAMERA_COMMAND = 1L;

    static final Long PRESET_COMMAND = 2L;
	static final int DATA_TYPE_ALPHANUMERIC = 2;
	static final Long PARAM_CONFIG_PTZ = 7L;
	static final Long PARAM_CONFIG = 1L;
	static final String PRESET_TYPE = "5";
	static final int PARAM_ID_1 = 1;
	static final int PARAM_ID_2 = 2;
	static final Long ELEMENT_SUBTYPE_DOMO = 3L;


    static final Long VOL_COMMAND = 2L;
    static final Long MIC_COMMAND = 3L;
	static final String VOL_VALUE = "8";
	static final String MIC_VALUE = "8";
	static final String VOL_VALUE_AUX = "7";
	static final String MIC_VALUE_AUX = "7";



	org.apache.logging.log4j.Logger log;

	LockCamera(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {
		List<Command> commands = null;
		List<ImsIncidentReportAlarm> incidentReportAlarms = IncidentEntitiesManager.getInstance()
			.getIncidentReportAlarms(incidentReport.getId());
	
		if (incidentReportAlarms.isEmpty()){
			Set<Long> elements = EntitiesManager.getInstance()
					.getElementByStretch(ELEMENT_TYPE_SOS, incidentReport.getAffectionStretchId());
			
			List<Element> elementsSOS = EntitiesManager.getInstance().getElementByElementType(ELEMENT_TYPE_SOS);
			
			if(elements != null){
				for(Long id : elements){
					for(Element sos : elementsSOS){
						if (sos.getId() == id && sos.getLocationId() == incidentReport.getLocationId()){		
							List<ElementHierarchy> cameraChild = EntitiesManager.getInstance().getsByParent(sos.getElementTypeId(), sos.getId(), ELEMENT_TYPE_CAMERA);
							log.debug("Camara a enclavar: " + cameraChild.getAt(0));
							if(cameraChild != null){
								if (commands == null) {
									commands = new ArrayList<>();
								}	
								commands.add(rellenaSignallingCommand(cameraChild.getAt(0).getChildElementId()));
							}
						}
					}
				}
			}
		}else{
			Element sos = EntitiesManager.getInstance().getElement(incidentReportAlarms.get(0).getElementTypeId(), incidentReportAlarms.get(0).getElementId());
			commands = new ArrayList<>();
			List<ElementHierarchy> cameraChild = EntitiesManager.getInstance().getsByParent(sos.getElementTypeId(), sos.getId(), ELEMENT_TYPE_CAMERA);
			if(cameraChild != null && cameraChild.getAt(0) != null){
				log.debug("Camara a enclavar: " + cameraChild.getAt(0));		
				Element camara = EntitiesManager.getInstance().getElement(ELEMENT_TYPE_CAMERA, cameraChild.getAt(0).getChildElementId());	
	
				if(camara != null){	
					if(camara.getElementSubtypeId() == ELEMENT_SUBTYPE_DOMO){
						commands.add(rellenaSignallingCommand(cameraChild.getAt(0).getChildElementId()));
						commands.add(rellenaPreset(cameraChild.getAt(0).getChildElementId()));
					}else{
						commands.add(rellenaSignallingCommand(cameraChild.getAt(0).getChildElementId()));
					}
	
				}else{
					log.error("Este poste sos no tiene camara asociada");
				}
			}else{
				log.error("Este poste sos no tiene camara asociada");
			}
			commands.add(rellenaVol_aux(sos.getId()));
			commands.add(rellenaMic_aux(sos.getId()));
			commands.add(rellenaVol(sos.getId()));
			commands.add(rellenaMic(sos.getId()));
		}

		return commands;
	}


	
	SignallingCommand rellenaSignallingCommand(Long element){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(LOCK_CAMERA_COMMAND);
		signallingCommand.setElementTypeId(ELEMENT_TYPE_CAMERA);
		signallingCommand.setElementId(element);
		
		return signallingCommand;
	}

    SignallingCommand rellenaPreset(Long element){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(PRESET_COMMAND);
		signallingCommand.setElementTypeId(ELEMENT_TYPE_CAMERA);
		signallingCommand.setElementId(element);

		signallingCommand.setSignallingParams(paramPreset(PRESET_TYPE));
		
		return signallingCommand;
	}

	List<SignallingParam> paramPreset(String paramValue){
		List<SignallingParam> signallingParams = new ArrayList<>();
		
		SignallingParam signallingParam = new SignallingParam();

		signallingParam.setId(PARAM_ID_1);
		signallingParam.setType(DATA_TYPE_ALPHANUMERIC);
		signallingParam.setValue(paramValue);

		signallingParams.add(signallingParam);

		return signallingParams;
	}

	SignallingCommand rellenaVol(Long element){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(VOL_COMMAND);
		signallingCommand.setElementTypeId(ELEMENT_TYPE_SOS);
		signallingCommand.setElementId(element);

		signallingCommand.setSignallingParams(paramVol(VOL_VALUE));
		
		return signallingCommand;
	}

	List<SignallingParam> paramVol(String paramValue){
		List<SignallingParam> signallingParams = new ArrayList<>();
		
		SignallingParam signallingParam = new SignallingParam();

		signallingParam.setId(PARAM_ID_1);
		signallingParam.setType(DATA_TYPE_ALPHANUMERIC);
		signallingParam.setValue(paramValue);

		signallingParams.add(signallingParam);

		return signallingParams;
	}

		SignallingCommand rellenaVol_aux(Long element){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(VOL_COMMAND);
		signallingCommand.setElementTypeId(ELEMENT_TYPE_SOS);
		signallingCommand.setElementId(element);

		signallingCommand.setSignallingParams(paramVol(VOL_VALUE_AUX));
		
		return signallingCommand;
	}

	List<SignallingParam> paramVol_aux(String paramValue){
		List<SignallingParam> signallingParams = new ArrayList<>();
		
		SignallingParam signallingParam = new SignallingParam();

		signallingParam.setId(PARAM_ID_1);
		signallingParam.setType(DATA_TYPE_ALPHANUMERIC);
		signallingParam.setValue(paramValue);

		signallingParams.add(signallingParam);

		return signallingParams;
	}

	SignallingCommand rellenaMic(Long element){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(MIC_COMMAND);
		signallingCommand.setElementTypeId(ELEMENT_TYPE_SOS);
		signallingCommand.setElementId(element);

		signallingCommand.setSignallingParams(paramMic(MIC_VALUE));
		
		return signallingCommand;
	}

	List<SignallingParam> paramMic(String paramValue){
		List<SignallingParam> signallingParams = new ArrayList<>();
		
		SignallingParam signallingParam = new SignallingParam();

		signallingParam.setId(PARAM_ID_2);
		signallingParam.setType(DATA_TYPE_ALPHANUMERIC);
		signallingParam.setValue(paramValue);

		signallingParams.add(signallingParam);

		return signallingParams;
	}

		SignallingCommand rellenaMic_aux(Long element){
		SignallingCommand signallingCommand = new SignallingCommand();
		
		signallingCommand.setSignallingCommandId(MIC_COMMAND);
		signallingCommand.setElementTypeId(ELEMENT_TYPE_SOS);
		signallingCommand.setElementId(element);

		signallingCommand.setSignallingParams(paramMic(MIC_VALUE_AUX));
		
		return signallingCommand;
	}

	List<SignallingParam> paramMic_aux(String paramValue){
		List<SignallingParam> signallingParams = new ArrayList<>();
		
		SignallingParam signallingParam = new SignallingParam();

		signallingParam.setId(PARAM_ID_2);
		signallingParam.setType(DATA_TYPE_ALPHANUMERIC);
		signallingParam.setValue(paramValue);

		signallingParams.add(signallingParam);

		return signallingParams;
	}
}
