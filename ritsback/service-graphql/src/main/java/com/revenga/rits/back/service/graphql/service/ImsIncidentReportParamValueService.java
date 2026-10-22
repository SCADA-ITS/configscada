package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReportParamValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetIncidentReportsPublishCommand;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentReportParamValueHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportParamValueVO;
import com.revenga.rits.back.service.graphql.type.ImsIncidentReportParamValueGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ImsIncidentReportParamValueService implements QueryInterface<ImsIncidentReportParamValueVO>, MutationInterface<ImsIncidentReportParamValueVO> {

	@Override
	public List<ImsIncidentReportParamValueVO> getAll() {

		List<ImsIncidentReportParamValueVO> incidentParamValuesVO = new ArrayList<>();

		for (ImsIncidentReportParamValue imsIncidentParamValue : EntitiesManager.getInstance().getImsIncidentParamValueRepository().getAll(false)) {

			incidentParamValuesVO.add(new ImsIncidentReportParamValueGraphql(imsIncidentParamValue));
		}

		return incidentParamValuesVO;
	}

	@Override
	public Optional<ImsIncidentReportParamValueVO> get(String ImsIncidentParamValueId) {

		Optional<ImsIncidentReportParamValueVO> imsIncidentParamValueVO = null;

		try {
			Key key = new Key(ImsIncidentParamValueId);

			if (key.getClazz() == ImsIncidentReportParamValue.class) {

				ImsIncidentReportParamValue imsIncidentParamValue = EntitiesManager.getInstance()
						.getImsIncidentParamValueRepository()
						.get(Long.valueOf(key.getId(1)), Long.valueOf(key.getId(2)), false);

				if (imsIncidentParamValue != null) {

					imsIncidentParamValueVO = Optional.of(new ImsIncidentReportParamValueGraphql(imsIncidentParamValue));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ImsIncidentParamValue");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return imsIncidentParamValueVO;
	}
	
	public Boolean assignIncidentParamValue(List<ImsIncidentReportParamValueVO> incidentParamValues) {
	
		Set<Long> incidentReportsIds = new HashSet<>();
		
		if(incidentParamValues != null && !CollectionUtils.isEmpty(incidentParamValues)){
			
			for (ImsIncidentReportParamValueVO incidentParamValue : incidentParamValues) {
				
				ImsIncidentReportParamValue incidentParamValueAux = ImsIncidentReportParamValueHelper.get(incidentParamValue);
				ImsIncidentReportParamValue incidentParamValueResp = null;
				
				if (incidentParamValueAux != null) {
					
					incidentParamValueAux = EntitiesManager.getInstance().getImsIncidentParamValueRepository()
							.get(incidentParamValueAux.getIncidentReportId(), incidentParamValueAux.getIncidentParamId(), false);
					
					incidentParamValueResp = ImsIncidentReportParamValueHelper.get(incidentParamValue);
					
					if (incidentParamValueAux == null) {
						
						incidentParamValueResp = EntitiesManager.getInstance().getImsIncidentParamValueRepository().create(incidentParamValueResp);
					
					} else {
						
						incidentParamValueResp = EntitiesManager.getInstance().getImsIncidentParamValueRepository().update(incidentParamValueResp);
					}
				}
				
				if (incidentParamValueResp != null) {

					incidentReportsIds.add(incidentParamValueResp.getIncidentReportId());
				}		
			}			
		} else {
			
			return false;
			}
		
		if (!CollectionUtils.isEmpty(incidentReportsIds)) {
			
			SetIncidentReportsPublishCommand command = new SetIncidentReportsPublishCommand();
			
			incidentReportsIds.forEach(command::addIncident);
			
			command.setAction(Command.ACTION_UPDATE);
			
			EntitiesManager.getInstance().sendCommand(command);
		}
		
		return true;
	}

	
	@Override
	public Optional<ImsIncidentReportParamValueVO> update(ImsIncidentReportParamValueVO imsIncidentParamValueVO) {

		Optional<ImsIncidentReportParamValueVO> resp = null;

		if (imsIncidentParamValueVO != null) {

			ImsIncidentReportParamValue imsIncidentParamValue = ImsIncidentReportParamValueHelper.get(imsIncidentParamValueVO);

			ImsIncidentReportParamValue imsIncidentParamValueUpdated = EntitiesManager.getInstance().getImsIncidentParamValueRepository().update(imsIncidentParamValue);

			if (imsIncidentParamValueUpdated != null) {

				resp = Optional.of(new ImsIncidentReportParamValueGraphql(imsIncidentParamValueUpdated));
			}
		}

		return resp;

	}

	

	@Override
	public Optional<ImsIncidentReportParamValueVO> clone(String ImsIncidentParamValueVO) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public List<ImsIncidentReportParamValueVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ImsIncidentReportParamValueVO> delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ImsIncidentReportParamValueVO> create(ImsIncidentReportParamValueVO item) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
