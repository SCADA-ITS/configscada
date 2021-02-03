package com.revenga.rits.front.openits.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class ImsIncidentReportBO extends GenericBO  {

	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;
	
	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> incidentReports = voDataSourceAdapter.getList(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORTS, null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORTS).getFieldId(), incidentReports);
	}

	@Override
	public Map<String, Object> getItem(String id) {

		Map<String, Object> incidentReport = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORT, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORT).getFieldId(), incidentReport);
	}
	
	public Map<String, Object> getItemSumary(String id) {

		Map<String, Object> incidentReport = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_INCIDENT_REPORT_SUMMARY, new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_INCIDENT_REPORT_SUMMARY).getFieldId(), incidentReport);
	}

	public Object create(Object report) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object confirm(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("incidentReportId", id);
		
		Map<String, Object> incidentReport = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_CONFIRM_INCIDENT_REPORT, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_SEND_CONFIRM_INCIDENT_REPORT).getFieldId(), incidentReport);
	}

	public Object forward(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("incidentReportId", id);
		
		Map<String, Object> incidentReport = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_FORWARD_INCIDENT_REPORT, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_SEND_FORWARD_INCIDENT_REPORT).getFieldId(), incidentReport);
	}
	
	public Object cancel(String id) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("incidentReportId", id);
		
		Map<String, Object> incidentReport = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_CANCEL_INCIDENT_REPORT, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_SEND_CANCEL_INCIDENT_REPORT).getFieldId(), incidentReport);
	}

	public Object assignUser(String id, String userId) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("incidentReportId", id);
		input.put("userId", userId);
		
		Map<String, Object> incidentReport = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_ASSING_USER_INCIDENT_REPORT, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_SEND_ASSING_USER_INCIDENT_REPORT).getFieldId(), incidentReport);
	}

	public Object finalizeTask(String id, String incidentTypeTaskId) {
		
		Map<String, Object> input = new HashMap<>();
		
		input.put("incidentReportId", id);
		input.put("incidentTypeTaskId", incidentTypeTaskId);
		
		Map<String, Object> incidentReport = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_FINALIZE_TASK_INCIDENT_REPORT, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_SEND_FINALIZE_TASK_INCIDENT_REPORT).getFieldId(), incidentReport);
	}

	public Object executeTaskPlan(String id, String incidentTaskPlanId) {
		

		Map<String, Object> input = new HashMap<>();
		
		input.put("incidentReportId", id);
		input.put("incidentTaskPlanId", incidentTaskPlanId);
		
		Map<String, Object> incidentReport = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_EXECUTE_TASK_PLAN_INCIDENT_REPORT, input);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(VODataSourceAdapter.DATA_SOURCE_SEND_EXECUTE_TASK_PLAN_INCIDENT_REPORT).getFieldId(), incidentReport);
	}
}
