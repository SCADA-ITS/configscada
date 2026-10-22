package com.revenga.rits.back.service.graphql.mutation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportParamValueVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportLogVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTaskPlanVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeStateTransitionVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeStretchVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskValueVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeVO;
import com.revenga.rits.back.service.graphql.service.ExtIncidentService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentAlarmConditionService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentReportParamValueService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentReportLogService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentReportService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentTaskPlanService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentTypeService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentTypeStateTransitionService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentTypeStretchService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentTypeTaskService;

@Component
public class IncidentMutation implements GraphQLMutationResolver {

	@Autowired
	private ImsIncidentReportService imsIncidentReportService;

	@Autowired
	private ExtIncidentService exIncidentService;

	@Autowired
	private ImsIncidentAlarmConditionService imsIncidentAlarmConditionService;

	@Autowired
	private ImsIncidentTypeService imsIncidentTypeService;

	@Autowired
	private ImsIncidentTypeStateTransitionService imsIncidentTypeStateTransitionService;

	@Autowired
	private ImsIncidentTypeStretchService imsIncidentTypeStretchService;

	@Autowired
	private ImsIncidentTypeTaskService imsIncidentTypeTaskService;
	
	@Autowired
	private ImsIncidentTaskPlanService imsIncidentTaskPlanService;
	
	@Autowired
	private ImsIncidentReportLogService imsIncidentReportLogService;
	
	@Autowired
	private ImsIncidentReportParamValueService imsIncidentparamValueService;
	

	public Boolean createIncidentReport(String incidentTypeId, String locationId, List<String> alarmIds,
			List<String> affectionStretchIds, String userId, String roadImpactId) {

		return imsIncidentReportService.sendCreate(incidentTypeId, locationId, alarmIds, affectionStretchIds, userId, roadImpactId);
	}
	
	public Boolean changeIncidentReport(String incidentReportId, String locationId, String affectionStretchId, String incidentTypeId, String roadImpactId) {
		           
		return imsIncidentReportService.sendChangeIncidentReport(incidentReportId, locationId, affectionStretchId, incidentTypeId, roadImpactId);
	}
	
	public Boolean changeRoadImpactIncidentReport(String incidentReportId,  String roadImpactId) {
        
		return imsIncidentReportService.sendChangeRoadImpactIncidentReport(incidentReportId, roadImpactId);
	}
	
	public Boolean confirmIncidentReport(String incidentReportId, String userId) {

		return imsIncidentReportService.sendConfirm(incidentReportId, userId);
	}
	
	public Boolean addLogIncidentReport(final List<ImsIncidentReportLogVO> incidentReportLogs) {

		return imsIncidentReportLogService.addLogIncidentReport(incidentReportLogs);
	}
	
	public Boolean deleteLogIncidentReport(String incidentReportLogId) {

		return imsIncidentReportLogService.deleteLogIncidentReport(incidentReportLogId);
	}

	public Boolean forwardIncidentReport(String incidentReportId) {

		return imsIncidentReportService.sendForward(incidentReportId);
	}

	public Boolean backwardIncidentReport(String incidentReportId) {

		return imsIncidentReportService.sendBackward(incidentReportId);
	}
	
	public Boolean cancelIncidentReport(String incidentReportId, String userId) {

		return imsIncidentReportService.sendCancel(incidentReportId, userId);
	}

	public Boolean assignUserIncidentReport(String incidentReportId, String userId) {

		return imsIncidentReportService.sendAssignUser(incidentReportId, userId);
	}
	
	public Boolean assignIncidentParamValues(List<ImsIncidentReportParamValueVO> incidentParamValues) {
		
		return imsIncidentparamValueService.assignIncidentParamValue(incidentParamValues);
	}
	
	/**
	 * 
	 * @param incidentReportId
	 * @param incidentTypeCauseId
	 * @return
	 */	
	public Boolean assignCauseIncidentReport(String incidentReportId, String incidentTypeCauseId) {

		return imsIncidentReportService.sendAssignCause(incidentReportId, incidentTypeCauseId);
	}	

	public Boolean finalizeTaskIncidentReport(String incidentReportId, String incidentTypeTaskId,
			List<ImsIncidentTypeTaskValueVO> incidentTypeTaskValues) {

		return imsIncidentReportService.sendFinalizeTask(incidentReportId, incidentTypeTaskId, incidentTypeTaskValues);
	}

	public Boolean executeTaskPlanIncidentReport(String incidentReportId, String incidentTaskPlanId) {

		return imsIncidentReportService.sendExecuteTaskPlan(incidentReportId, incidentTaskPlanId);
	}

	public List<ImsExtIncidentVO> sendCreateExIncident(final List<ImsExtIncidentVO> extIncidents) {

		return exIncidentService.sendCreateExIncident(extIncidents);
	}

	public List<ImsExtIncidentVO> sendUpdateExIncident(final List<ImsExtIncidentVO> extIncidents) {

		return exIncidentService.sendUpdateExIncident(extIncidents);
	}

	public List<ImsExtIncidentVO> sendDeleteExIncident(final List<String> extIncidentIds) {

		return exIncidentService.sendDeleteExIncident(extIncidentIds);
	}

	public Optional<ImsIncidentAlarmConditionVO> createImsIncidentAlarmCondition(final ImsIncidentAlarmConditionVO imsIncidentAlarmCondition) {

		return imsIncidentAlarmConditionService.create(imsIncidentAlarmCondition);
	}

	public Optional<ImsIncidentAlarmConditionVO> updateImsIncidentAlarmCondition(final ImsIncidentAlarmConditionVO imsIncidentAlarmCondition) {

		return imsIncidentAlarmConditionService.update(imsIncidentAlarmCondition);
	}

	public Optional<ImsIncidentAlarmConditionVO> deleteImsIncidentAlarmCondition(final String imsIncidentAlarmConditionId) {

		return imsIncidentAlarmConditionService.delete(imsIncidentAlarmConditionId);
	}
	
	public Optional<ImsIncidentTaskPlanVO> createImsIncidentTaskPlan(final ImsIncidentTaskPlanVO imsIncidentTaskPlan) {

		return imsIncidentTaskPlanService.create(imsIncidentTaskPlan);
	}

	public Optional<ImsIncidentTaskPlanVO> updateImsIncidentTaskPlan(final ImsIncidentTaskPlanVO imsIncidentTaskPlan) {

		return imsIncidentTaskPlanService.update(imsIncidentTaskPlan);
	}

	public Optional<ImsIncidentTaskPlanVO> deleteImsIncidentTaskPlan(final String imsIncidentTaskPlanId) {

		return imsIncidentTaskPlanService.delete(imsIncidentTaskPlanId);
	}

	public Optional<ImsIncidentTypeVO> createImsIncidentType(final ImsIncidentTypeVO imsIncidentType) {

		return imsIncidentTypeService.create(imsIncidentType);
	}

	public Optional<ImsIncidentTypeVO> updateImsIncidentType(final ImsIncidentTypeVO imsIncidentType) {

		return imsIncidentTypeService.update(imsIncidentType);
	}

	public Optional<ImsIncidentTypeVO> deleteImsIncidentType(final String imsIncidentTypeId) {

		return imsIncidentTypeService.delete(imsIncidentTypeId);
	}

	public Optional<ImsIncidentTypeStateTransitionVO> createImsIncidentTypeStateTransition(final ImsIncidentTypeStateTransitionVO imsIncidentTypeStateTransition) {

		return imsIncidentTypeStateTransitionService.create(imsIncidentTypeStateTransition);
	}

	public Optional<ImsIncidentTypeStateTransitionVO> updateImsIncidentTypeStateTransition(final ImsIncidentTypeStateTransitionVO imsIncidentTypeStateTransition) {

		return imsIncidentTypeStateTransitionService.update(imsIncidentTypeStateTransition);
	}

	public Optional<ImsIncidentTypeStateTransitionVO> deleteImsIncidentTypeStateTransition(final String imsIncidentTypeStateTransitionId) {

		return imsIncidentTypeStateTransitionService.delete(imsIncidentTypeStateTransitionId);
	}

	public Optional<ImsIncidentTypeStretchVO> createImsIncidentTypeStretch(final ImsIncidentTypeStretchVO imsIncidentTypeStretch) {

		return imsIncidentTypeStretchService.create(imsIncidentTypeStretch);
	}

	public Optional<ImsIncidentTypeStretchVO> updateImsIncidentTypeStretch(final ImsIncidentTypeStretchVO imsIncidentTypeStretch) {

		return imsIncidentTypeStretchService.update(imsIncidentTypeStretch);
	}

	public Optional<ImsIncidentTypeStretchVO> deleteImsIncidentTypeStretch(final String imsIncidentTypeStretchId) {

		return imsIncidentTypeStretchService.delete(imsIncidentTypeStretchId);
	}

	public Optional<ImsIncidentTypeTaskVO> createImsIncidentTypeTask(final ImsIncidentTypeTaskVO imsIncidentTypeTask) {

		return imsIncidentTypeTaskService.create(imsIncidentTypeTask);
	}

	public Optional<ImsIncidentTypeTaskVO> updateImsIncidentTypeTask(final ImsIncidentTypeTaskVO imsIncidentTypeTask) {

		return imsIncidentTypeTaskService.update(imsIncidentTypeTask);
	}

	public Optional<ImsIncidentTypeTaskVO> deleteImsIncidentTypeTask(final String imsIncidentTypeTaskId) {

		return imsIncidentTypeTaskService.delete(imsIncidentTypeTaskId);
	}
	
}
