import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.data.core.util.ResourcesUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 
 * SignallingCommand_2_10: Send DAI alarm Camera  
 * 
 */
class SignallingCommand_2_10 {
	static final String XID1 = "_CT";
	static final String XID2 = "_visibilidad";


	GroovyShell shell;
	def signallingCommandUtils;

	org.apache.logging.log4j.Logger log;

	SignallingCommand_2_10(org.apache.logging.log4j.Logger log) {

		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
		this.log = log;
	}

	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
		Long daiValue = 1;
		
		String[] camera = ["S1TN-CAM-001",
		"S1TN-CAM-002",
		'S1TN-CAM-003',
		'S1TS-CAM-001',
		'S1TS-CAM-002',
		'S1TS-CAM-003',
		'S1TS-CAM-004',
		'S1TS-CAM-005',
		'S1TS-CAM-006',
		'S1TS-CAM-007',
		'S1TS-CAM-008',
		'S1TS-CAM-009',
		'S1TS-CAM-010',
		'S1TS-CAM-011',
		'S1TS-CAM-012',
		'S1TS-CAM-013',
		'S1TS-CAM-014',
		'S1TS-CAM-015',
		'S1TS-CAM-016',
		'S1TS-CAM-017',
		'S1TS-CAM-018',
		'S1TS-CAM-019',
		'S1TS-CAM-020',
		'S1TS-CAM-021',
		'S1TS-CAM-022',
		'S1TS-CAM-023',
		'S1TS-CAM-024',
		'S1TS-CAM-025',
		'S1TS-CAM-026',
		'S1TS-CAM-027',
		'S1TS-CAM-028',
		'S1TS-CAM-029',
		'SAEB-CAM-001',
		'SAEB-CAM-002',
		'SAEB-CAM-003',
		'SAEB-CAM-004',
		'SAEB-CAM-005',
		'SAEB-CAM-006',
		'SAEB-CAM-007',
		'SCNO-CAM-002',
		'SCNO-CAM-003',
		'SCNO-CAM-004',
		'SCNO-CAM-005',
		'SCNO-CAM-006',
		'SCNO-CAM-007',
		'SCNO-CAM-008',
		'SIGO-CAM-001',
		'SIGO-CAM-002',
		'SIGO-CAM-003',
		'SMPN-CAM-001',
		'SICO-CAM-001',
		'INKO-CAM-001',
		'SMPS-CAM-001',
		'SNKP-CAM-001',
		'SNKO-CAM-001',
		'INKO-CAM-002',
		'SNKP-CAM-002',
		'INKO-CAM-003',
		'SICO-CAM-002',
		'SMPN-CAM-002',
		'SMPS-CAM-002',
		'SNKP-CAM-003',
		'SNKO-CAM-002',
		'SAPR-CAM-001',
		'SMPN-CAM-003',
		'SMPS-CAM-003',
		'SICO-CAM-003',
		'ICNP-CAM-001',
		'SMPN-CAM-004',
		'SNKO-CAM-003',
		'SAPR-CAM-002',
		'SMPS-CAM-004',
		'SNKO-CAM-004',
		'INKP-CAM-001',
		'SICO-CAM-004',
		'ICNP-CAM-002',
		'SNKO-CAM-005',
		'SMPN-CAM-005',
		'SNKO-CAM-006',
		'SMPN-CAM-006',
		'SNKO-CAM-007',
		'INKP-CAM-002',
		'SNKO-CAM-008',
		'ICNP-CAM-003',
		'INKP-CAM-003',
		'SNKO-CAM-009',
		'ICNP-CAM-004',
		'INKP-CAM-004',
		'ICNP-CAM-005',
		'INKP-CAM-005',
		'INKP-CAM-006',
		'INKP-CAM-007',
		'INKP-CAM-008',
		'INKP-CAM-009',
		'SVIP-CAM-001',
		'IVIP-CAM-001',
		'IVIP-CAM-002',
		'S2TN-CAM-048',
		'S2TS-CAM-001',
		'S2TN-CAM-047',
		'S2TS-CAM-002',
		'S2TN-CAM-046',
		'S2TS-CAM-003',
		'S2TN-CAM-045',
		'S2TS-CAM-004',
		'S2TN-CAM-044',
		'S2TS-CAM-005',
		'S2TN-CAM-043',
		'S2TS-CAM-006',
		'S2TN-CAM-042',
		'S2TS-CAM-007',
		'IPRO-CAM-001',
		'S2TN-CAM-041',
		'S2TS-CAM-008',
		'IPRO-CAM-002',
		'S2TN-CAM-040',
		'S2TS-CAM-009',
		'S2TN-CAM-039',
		'S2TS-CAM-010',
		'S2TN-CAM-038',
		'S2TS-CAM-011',
		'S2TN-CAM-037',
		'S2TN-CAM-036',
		'S2TS-CAM-012',
		'S2TN-CAM-035',
		'S2TS-CAM-013',
		'S2TN-CAM-034',
		'S2TN-CAM-033',
		'S2TS-CAM-014',
		'S2TN-CAM-032',
		'S2TS-CAM-016',
		'S2TN-CAM-031',
		'S2TS-CAM-018',
		'S2TN-CAM-030',
		'ICCO-CAM-001',
		'S2TS-CAM-020',
		'S2TN-CAM-029',
		'ICCO-CAM-002',
		'S2TS-CAM-022',
		'S2TN-CAM-028',
		'S2TS-CAM-023',
		'S2TN-CAM-027',
		'S2TS-CAM-024',
		'S2TS-CAM-025',
		'S2TN-CAM-026',
		'S2TS-CAM-026',
		'S2TN-CAM-025',
		'S2TS-CAM-027',
		'S2TN-CAM-024',
		'S2TS-CAM-028',
		'S2TN-CAM-023',
		'S2TS-CAM-029',
		'S2TN-CAM-022',
		'S2TS-CAM-030',
		'IBTO-CAM-003',
		'S2TS-CAM-031',
		'S2TN-CAM-021',
		'ICCP-CAM-001',
		'ICCP-CAM-002',
		'S2TN-CAM-020',
		'S2TS-CAM-032',
		'ICCP-CAM-003',
		'S2TS-CAM-033',
		'S2TN-CAM-019',
		'S2TS-CAM-034',
		'S2TN-CAM-018',
		'S2TS-CAM-035',
		'S2TN-CAM-017',
		'S2TS-CAM-036',
		'S2TN-CAM-016',
		'S2TS-CAM-037',
		'S2TN-CAM-015',
		'S2TS-CAM-038',
		'S2TN-CAM-014',
		'S2TS-CAM-039',
		'S2TN-CAM-013',
		'S2TS-CAM-040',
		'S2TN-CAM-012',
		'S2TS-CAM-041',
		'S2TN-CAM-011',
		'S2TS-CAM-042',
		'IBTO-CAM-002',
		'IBTO-CAM-001',
		'S2TN-CAM-010',
		'S2TS-CAM-043',
		'S2TS-CAM-044',
		'S2TN-CAM-009',
		'S2TS-CAM-045',
		'S2TN-CAM-008',
		'S2TS-CAM-046',
		'S2TN-CAM-007',
		'S2TS-CAM-047',
		'S2TN-CAM-006',
		'S2TS-CAM-048',
		'S2TN-CAM-005',
		'S2TS-CAM-049',
		'S2TN-CAM-004',
		'S2TS-CAM-050',
		'SBTP-CAM-001',
		'S2TN-CAM-003',
		'S2TS-CAM-051',
		'SBTP-CAM-002',
		'S2TN-CAM-002',
		'S2TN-CAM-001']; 		
		
		int contador = 0;
		for (String a : camera){
			if (a.equals(dataSourceXid.substring(3))){
			 	break;
			}
			contador++; 
		}
		
		daiValue = daiValue << (contador%16);
		
		if( signallingCommand != null && signallingCommand.getElementId() != null &&
				signallingCommand.getSignallingParams() != null && !signallingCommand.getSignallingParams().isEmpty() &&
				signallingCommand.getSignallingParams().get(0) != null && signallingCommand.getSignallingParams().get(0).getValue() != null) {

			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
			XidPointValueTimeModel xidPointValueTimeModel;
			
			for(int i = 1; i <= 8; i++){
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
						"DP" + XID2 + ((int)(contador/16)+1) + XID1  + String.valueOf(i), daiValue);
							
				xidPointValueTimeModels.add(xidPointValueTimeModel);
	
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setSerializationInclusion(Include.NON_NULL);
				String message = objectMapper.writeValueAsString(xidPointValueTimeModels);
				
				if (driver != null) {

					driver.send(message);
				}
				
				xidPointValueTimeModels.clear();
			}
			
			sleep(180000);
			
			xidPointValueTimeModels.clear();
			for(int i = 1; i <= 8; i++){
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
						"DP" + XID2 + ((int)(contador/16)+1) + XID1  + String.valueOf(i), 0L);
							
				xidPointValueTimeModels.add(xidPointValueTimeModel);
	
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setSerializationInclusion(Include.NON_NULL);
				String message = objectMapper.writeValueAsString(xidPointValueTimeModels);
				
				if (driver != null) {

					driver.send(message);
				}
				
				xidPointValueTimeModels.clear();
			}
		}

		return true;
	}
}
