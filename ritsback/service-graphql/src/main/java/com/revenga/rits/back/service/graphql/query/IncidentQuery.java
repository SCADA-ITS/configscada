package com.revenga.rits.back.service.graphql.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentTypeVO;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionAlarmTypeVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentGroupVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentLevelVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentStateVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTaskPlanVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeStretchVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeVO;
import com.revenga.rits.back.front.core.model.vo.ImsTaskTypeVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;
import com.revenga.rits.back.service.graphql.service.ExtIncidentService;
import com.revenga.rits.back.service.graphql.service.ExtIncidentTypeService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentAlarmConditionAlarmTypeService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentGroupService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentLevelService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentReportService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentStatesService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentTaskPlanService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentTypeService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentTypeStretchService;
import com.revenga.rits.back.service.graphql.service.ImsIncidentTypeTaskService;
import com.revenga.rits.back.service.graphql.service.ImsTaskTypeService;

import graphql.schema.DataFetchingEnvironment;

@Component
public class IncidentQuery implements GraphQLQueryResolver {

	@Autowired
	private ExtIncidentService extIncidentService;

	@Autowired
	private ExtIncidentTypeService extIncidentTypeService;
	
	@Autowired
	private ImsIncidentStatesService imsIncidentStatesService;
	
	@Autowired
	ImsIncidentGroupService imsIncidentGroupService;
	
	@Autowired
	private ImsIncidentLevelService imsIncidentLevelService;
	
	@Autowired
	private ImsTaskTypeService imsTaskTypeService;
	
	@Autowired
	private ImsIncidentTypeTaskService imsIncidentTypeTaskService;
	
	@Autowired
	private ImsIncidentTaskPlanService imsIncidentTaskPlanService;
	
	@Autowired
	private ImsIncidentReportService imsIncidentReportService;
	
	@Autowired
	private ImsIncidentTypeStretchService imsIncidentTypeStretchService;
	
	@Autowired
	private ImsIncidentAlarmConditionAlarmTypeService imsIncidentAlarmConditionAlarmTypeService;
	
	@Autowired
	private ImsIncidentTypeService imsIncidentTypeService;
	
	
	/**
	 * 
	 * @return
	 */
	public List<ImsExtIncidentVO> getExtIncidents() {

		return extIncidentService.getAll();
	}

	/**
	 * 
	 * @param extIncidentId
	 * @return
	 */
	public Optional<ImsExtIncidentVO> getExtIncident(final String extIncidentId) {

		return extIncidentService.get(extIncidentId);
	}

	/**
	 * 
	 * @return
	 */
	public List<ImsExtIncidentTypeVO> getExtIncidentTypes() {

		return extIncidentTypeService.getAll();
	}

	/**
	 * 
	 * @param extIncidentTypeId
	 * @return
	 */
	public Optional<ImsExtIncidentTypeVO> getExtIncidentType(final String extIncidentTypeId) {

		return extIncidentTypeService.get(extIncidentTypeId);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ImsIncidentStateVO> getImsIncidentStates() {

		return imsIncidentStatesService.getAll();
	}

	/**
	 * 
	 * @param imsIncidentStateId
	 * @return
	 */
	public Optional<ImsIncidentStateVO> getImsIncidentState(final String imsIncidentStateId) {

		return imsIncidentStatesService.get(imsIncidentStateId);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ImsIncidentLevelVO> getImsIncidentLevels() {

		return imsIncidentLevelService.getAll();
	}

	/**
	 * 
	 * @param imsIncidentLevelId
	 * @return
	 */
	public Optional<ImsIncidentLevelVO> getImsIncidentLevel(final String imsIncidentLevelId) {

		return imsIncidentLevelService.get(imsIncidentLevelId);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ImsTaskTypeVO> getImsTaskTypes() {

		return imsTaskTypeService.getAll();
	}

	/**
	 * 
	 * @param imsTaskTypeId
	 * @return
	 */
	public Optional<ImsTaskTypeVO> getImsTaskType(final String imsTaskTypeId) {

		return imsTaskTypeService.get(imsTaskTypeId);
	}
	
	/**
	 * 
	 * @param stretchId
	 * @param incidentTypeId
	 * @param incidentTypeTaskId
	 * @return
	 */
	public List<ImsIncidentTaskPlanVO> getImsTaskPlans(final String stretchId, final String incidentTypeId, final String incidentTypeTaskId) {

		return imsIncidentTaskPlanService.getImsTaskPlans(stretchId, incidentTypeId, incidentTypeTaskId);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ImsIncidentTaskPlanVO> getAllImsTaskPlans() {

		return imsIncidentTaskPlanService.getAll();
	}
	
	/**
	 * 
	 * @param incidentTypeId
	 * @param incidentStateId
	 * @return
	 */
	public List<ImsIncidentTypeTaskVO> getImsIncidentTypeTasks(final String incidentTypeId, final String incidentStateId) {

		return imsIncidentTypeTaskService.getImsIncidentTypeTasks(incidentTypeId, incidentStateId);
	}
	
	public List<ImsIncidentGroupVO> getImsIncidentGroups(){
		
		return imsIncidentGroupService.getAll();	
	}
	
	public Optional<ImsIncidentGroupVO> getImsIncidentGroup(final String incidentGroupId){
		
		return imsIncidentGroupService.get(incidentGroupId);	
	}
	
	/**
	 * 
	 * @param incidentGroupId
	 * @return
	 */
	public List<ImsIncidentTypeVO> getImsIncidentTypesByGroup(final String incidentGroupId){
		
		return imsIncidentTypeService.getImsIncidentTypesByGroup(incidentGroupId);	
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ImsIncidentTypeVO> getImsIncidentTypes(){
		
		return imsIncidentTypeService.getAll();	
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Optional<ImsIncidentTypeVO> getImsIncidentType(final String incidentTypeId) {
		
		return imsIncidentTypeService.get(incidentTypeId);		
	}

	/**
	 * 
	 * @return
	 */
	public List<ImsIncidentTypeTaskVO> getAllImsIncidentTypeTasks() {

		return imsIncidentTypeTaskService.getAll();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ImsIncidentReportVO> getImsIncidentReports(DataFetchingEnvironment env) {

		return imsIncidentReportService.getAll(EntitiesManager.getManagementAreaId(env));
	}

	/**
	 * 
	 * @param imsIncidentReportId
	 * @return
	 */
	public Optional<ImsIncidentReportVO> getImsIncidentReport(final String imsIncidentReportId) {

		return imsIncidentReportService.get(imsIncidentReportId);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ImsIncidentTypeStretchVO> getImsIncidentTypeStretchs(DataFetchingEnvironment env) {

		return imsIncidentTypeStretchService.getAll(EntitiesManager.getManagementAreaId(env));
	}

	/**
	 * 
	 * @param stretchId
	 * @param incidentTypeId
	 * @return
	 */
	public Optional<ImsIncidentTypeStretchVO> getImsIncidentTypeStretch(final String stretchId, final String incidentTypeId) {

		return imsIncidentTypeStretchService.get(stretchId, incidentTypeId);
	}
	
	/**
	 * 
	 * @param incidentTypeId
	 * @return
	 */
	public List<ImsIncidentTypeStretchVO> getImsIncidentTypeStretchsByIncidentType(final String incidentTypeId, DataFetchingEnvironment env) {

		return imsIncidentTypeStretchService.getsByIncidentType(incidentTypeId, EntitiesManager.getManagementAreaId(env));
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ImsIncidentAlarmConditionAlarmTypeVO> getImsIncidentAlarmConditionAlarmTypes() {

		return imsIncidentAlarmConditionAlarmTypeService.getAll();
	}

	/**
	 * 
	 * @param imsIncidentAlarmConditionAlarmTypeId
	 * @return
	 */
	public Optional<ImsIncidentAlarmConditionAlarmTypeVO> getImsIncidentAlarmConditionAlarmType(final String imsIncidentAlarmConditionAlarmTypeId) {

		return imsIncidentAlarmConditionAlarmTypeService.get(imsIncidentAlarmConditionAlarmTypeId);
	}

}