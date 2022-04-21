package com.revenga.rits.front.openits.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;
import com.revenga.rits.front.openits.model.UserSessionUI;

@Service
public class ImsIncidentReportBO extends GenericBO {

	@Autowired
	GraphqlAdapter graphqlAdapter;

	@Autowired
	SessionBO sessionBO;

	@Autowired
	UserBO userBO;

	static final String FIELD_INCIDENT_REPORT_LOGS = "incidentReportLogs";
	static final String FIELD_INCIDENT_REPORT_ID = "incidentReportId";
	static final String FIELD_LOG_SUBTYPE_ID = "logSubtypeId";
	static final String FIELD_USER_ID = "userId";
	static final String FIELD_LEVEL = "level";
	static final String FIELD_INCIDENT_REPORT_LOG_VALUES = "incidentReportLogValues";
	static final String FIELD_LOG_TYPE_PARAM_ID = "logTypeParamId";
	static final String FIELD_VALUE = "value";
	static final String FIELD_ALIAS = "alias";
	static final String FIELD_DESCRIPTION = "description";
	static final String FIELD_ID = "id";

	static final String VALUE_DEFAULT_LEVEL = "INFO";
	static final String LOG_TYPE_PARAM_STATE = "LogTypeParam:1:1";
	static final String LOG_TYPE_PARAM_ARG_1 = "LogTypeParam:1:2";

	static final String LOG_SUBTYPE_INCIDENT_REPORT_CREATED = "LogSubtype:7";
	static final String LOG_SUBTYPE_INCIDENT_REPORT_CONFIRMED = "LogSubtype:8";
	static final String LOG_SUBTYPE_INCIDENT_REPORT_FORWARDED = "LogSubtype:9";
	static final String LOG_SUBTYPE_INCIDENT_REPORT_CANDELED = "LogSubtype:10";
	static final String LOG_SUBTYPE_ASSIGNED_USER = "LogSubtype:11";
	static final String LOG_SUBTYPE_ASSIGNED_CAUSE = "LogSubtype:12";
	static final String LOG_SUBTYPE_FINALIZED_TASK = "LogSubtype:13";
	static final String LOG_SUBTYPE_EXECUTED_TASK_PLAN = "LogSubtype:14";
	static final String LOG_SUBTYPE_ATTACHED_DOCUMENT = "LogSubtype:15";
	static final String LOG_SUBTYPE_DELETED_DOCUMENT = "LogSubtype:16";
	static final String LOG_SUBTYPE_INCIDENT_REPORT_CHANGED = "LogSubtype:18";

	static final String FIELD_INCIDENT_STATE = "incidentState";
	static final String FIELD_LABEL_ALIAS = "labelAlias";
	static final String FIELD_NAME = "name";

	static final String FIELD_INCIDENT_TYPE_TASK_ID = "incidentTypeTaskId";
	static final String FIELD_INCIDENT_TASK_PLAN_ID = "incidentTaskPlanId";
	static final String FIELD_INCIDENT_TYPE = "incidentType";
	static final String FIELD_INCIDENT_TYPE_ID = "incidentTypeId";

	static final String FIELD_SEQUENCE_OF_STATES = "sequenceOfStates";
	static final String FIELD_STATE = "state";
	static final String FIELD_TASK_STATES = "taskStates";
	static final String FIELD_INCIDENT_TYPE_TASK = "incidentTypeTask";
	static final String FIELD_TASK_PLAN_STATES = "taskPlanStates";
	static final String FIELD_INCIDENT_TASK_PLAN = "incidentTaskPlan";
	static final String FIELD_PLAN = "plan";

	static final String LBL_IMS_INCIDENT_STATE_CANCELED = "LBL_IMS_INCIDENT_STATE_CANCELED";

	static final String FIELD_IMS_INCIDENT_TYPE_CAUSE_ID = "incidentTypeCauseId";
	static final String FIELD_IMS_INCIDENT_TYPE_CAUSES = "incidentTypeCauses";

	static final String FIELD_LOCATION_ID = "locationId";
	static final String FIELD_AFFECTION_STRETCH_ID = "affectionStretchId";

	static final String FIELD_INCIDENT_TYPE_TASK_VALUES = "incidentTypeTaskValues";

	@PostConstruct
	private void initialize() {

		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORTS;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORT;
	}

	@SuppressWarnings("unchecked")
	private Object getIncidentStateLabelAlias(final Map<String, Object> incidentReport) {

		if (incidentReport != null && incidentReport.containsKey(FIELD_INCIDENT_STATE)) {

			Object objIncidentState = incidentReport.get(FIELD_INCIDENT_STATE);

			if (objIncidentState instanceof Map) {

				Map<String, Object> incidentState = (Map<String, Object>) objIncidentState;
				Object objIncidentStateLabelAlias = incidentState.get(FIELD_LABEL_ALIAS);

				if (objIncidentStateLabelAlias != null) {

					return objIncidentStateLabelAlias;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Object getIncidentTypeTaskAlias(final Map<String, Object> incidentReport, final String incidentTypeTaskId) {

		if (incidentReport != null && incidentTypeTaskId != null) {

			if (incidentReport.containsKey(FIELD_TASK_STATES)) {

				Object objectTaskStates = incidentReport.get(FIELD_TASK_STATES);

				if (objectTaskStates instanceof List) {

					List<?> taskStates = (List<?>) objectTaskStates;

					for (Object objTaskState : taskStates) {

						if (objTaskState instanceof Map) {

							Map<String, Object> taskState = (Map<String, Object>) objTaskState;

							if (taskState.containsKey(FIELD_INCIDENT_TYPE_TASK)) {

								Object objIncidentTypeTask = taskState.get(FIELD_INCIDENT_TYPE_TASK);

								if (objIncidentTypeTask instanceof Map) {

									Map<String, Object> incidentTypeTask = (Map<String, Object>) objIncidentTypeTask;

									Object objIncidentTypeTaskId = incidentTypeTask.get(FIELD_ID);

									if (objIncidentTypeTaskId != null
											&& incidentTypeTaskId.equals(objIncidentTypeTaskId)) {

										StringBuilder stringBuilder = new StringBuilder();
										stringBuilder.append(incidentTypeTask.get(FIELD_ALIAS));

										if (incidentTypeTask.containsKey(FIELD_DESCRIPTION)
												&& incidentTypeTask.get(FIELD_DESCRIPTION) != null) {

											stringBuilder.append(" - " + incidentTypeTask.get(FIELD_DESCRIPTION));
										}

										return stringBuilder.toString();
									}
								}
							}
						}
					}
				}
			}

			if (incidentReport.containsKey(FIELD_SEQUENCE_OF_STATES)) {

				Object objSequenceOfStates = incidentReport.get(FIELD_SEQUENCE_OF_STATES);

				if (objSequenceOfStates instanceof List) {

					List<?> sequenceOfStates = (List<?>) objSequenceOfStates;

					for (Object objSequenceOfState : sequenceOfStates) {

						if (objSequenceOfState instanceof Map) {

							Map<String, Object> sequenceOfState = (Map<String, Object>) objSequenceOfState;

							if (sequenceOfState.containsKey(FIELD_TASK_STATES)) {

								Object objectTaskStates = sequenceOfState.get(FIELD_TASK_STATES);

								if (objectTaskStates instanceof List) {

									List<?> taskStates = (List<?>) objectTaskStates;

									for (Object objTaskState : taskStates) {

										if (objTaskState instanceof Map) {

											Map<String, Object> taskState = (Map<String, Object>) objTaskState;

											if (taskState.containsKey(FIELD_INCIDENT_TYPE_TASK)) {

												Object objIncidentTypeTask = taskState.get(FIELD_INCIDENT_TYPE_TASK);

												if (objIncidentTypeTask instanceof Map) {

													Map<String, Object> incidentTypeTask = (Map<String, Object>) objIncidentTypeTask;

													Object objIncidentTypeTaskId = incidentTypeTask.get(FIELD_ID);

													if (objIncidentTypeTaskId != null
															&& incidentTypeTaskId.equals(objIncidentTypeTaskId)) {

														StringBuilder stringBuilder = new StringBuilder();
														stringBuilder.append(incidentTypeTask.get(FIELD_ALIAS));

														if (incidentTypeTask.containsKey(FIELD_DESCRIPTION)
																&& incidentTypeTask.get(FIELD_DESCRIPTION) != null) {

															stringBuilder.append(
																	" - " + incidentTypeTask.get(FIELD_DESCRIPTION));
														}

														return stringBuilder.toString();
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	private Object getPlanAlias(final Map<String, Object> incidentReport, final String incidentTaskPlanId) {

		if (incidentReport != null && incidentTaskPlanId != null
				&& incidentReport.containsKey(FIELD_SEQUENCE_OF_STATES)) {

			Object objIncidentState = incidentReport.get(FIELD_SEQUENCE_OF_STATES);

			if (objIncidentState instanceof List) {

				List<?> sequenceOfStates = (List<?>) objIncidentState;

				for (Object objSequenceOfState : sequenceOfStates) {

					if (objSequenceOfState instanceof Map) {

						Map<String, Object> sequenceOfState = (Map<String, Object>) objSequenceOfState;

						if (sequenceOfState.containsKey(FIELD_TASK_STATES)) {

							Object objectTaskStates = sequenceOfState.get(FIELD_TASK_STATES);

							if (objectTaskStates instanceof List) {

								List<?> taskStates = (List<?>) objectTaskStates;

								for (Object objTaskState : taskStates) {

									if (objTaskState instanceof Map) {

										Map<String, Object> taskState = (Map<String, Object>) objTaskState;

										if (taskState.containsKey(FIELD_TASK_PLAN_STATES)) {

											Object objTaskPlanStates = taskState.get(FIELD_TASK_PLAN_STATES);

											if (objTaskPlanStates instanceof List) {

												List<?> taskPlanStates = (List<?>) objTaskPlanStates;

												for (Object objTaskPlanState : taskPlanStates) {

													if (objTaskPlanState instanceof Map) {

														Map<String, Object> taskPlanState = (Map<String, Object>) objTaskPlanState;

														if (taskPlanState.containsKey(FIELD_INCIDENT_TASK_PLAN)) {

															Object objIncidentTaskPlan = taskPlanState
																	.get(FIELD_INCIDENT_TASK_PLAN);

															if (objIncidentTaskPlan instanceof Map) {

																Map<String, Object> incidentTaskPlan = (Map<String, Object>) objIncidentTaskPlan;

																Object objIncidentTaskPlanId = incidentTaskPlan
																		.get(FIELD_ID);

																if (objIncidentTaskPlanId != null && incidentTaskPlanId
																		.equals(objIncidentTaskPlanId)) {

																	return incidentTaskPlan.containsKey(FIELD_PLAN)
																			&& incidentTaskPlan
																					.get(FIELD_PLAN) instanceof Map
																							? ((Map<String, Object>) incidentTaskPlan
																									.get(FIELD_PLAN))
																											.get(FIELD_ALIAS)
																							: null;
																}
															}
														}

													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Object getNextStateLabelAlias(final Map<String, Object> incidentReport) {

		if (incidentReport != null && incidentReport.containsKey(FIELD_SEQUENCE_OF_STATES)
				&& incidentReport.containsKey(FIELD_INCIDENT_STATE)) {

			Object objIncidentState = incidentReport.get(FIELD_INCIDENT_STATE);

			if (objIncidentState instanceof Map) {

				Map<String, Object> incidentState = (Map<String, Object>) objIncidentState;

				Object objIncidentStateId = incidentState.get(FIELD_ID);

				if (objIncidentStateId != null) {

					Object objSequenceOfStates = incidentReport.get(FIELD_SEQUENCE_OF_STATES);

					if (objSequenceOfStates instanceof List) {

						List<?> sequenceOfStates = (List<?>) objSequenceOfStates;

						if (!sequenceOfStates.isEmpty()) {

							// Current state not is the last

							Object objLastSequenceOfState = sequenceOfStates.get(sequenceOfStates.size() - 1);

							if (objLastSequenceOfState instanceof Map) {

								Map<String, Object> lastSequenceOfState = (Map<String, Object>) objLastSequenceOfState;

								if (lastSequenceOfState.containsKey(FIELD_STATE)) {

									Object objLastState = lastSequenceOfState.get(FIELD_STATE);

									if (objLastState instanceof Map) {

										Map<String, Object> lastState = (Map<String, Object>) objLastState;

										Object objLastStateId = lastState.get(FIELD_ID);

										if (objLastStateId != null && !objLastStateId.equals(objIncidentStateId)) {

											boolean stateFound = false;

											for (Object objSequenceOfState : sequenceOfStates) {

												if (objSequenceOfState instanceof Map) {

													Map<String, Object> sequenceOfState = (Map<String, Object>) objSequenceOfState;

													if (sequenceOfState.containsKey(FIELD_STATE)) {

														Object objState = sequenceOfState.get(FIELD_STATE);

														if (objState instanceof Map) {

															Map<String, Object> state = (Map<String, Object>) objState;

															Object objStateId = state.get(FIELD_ID);

															if (stateFound) {

																return state.get(FIELD_LABEL_ALIAS);

															} else if (objStateId != null
																	&& objStateId.equals(objIncidentStateId)) {

																stateFound = true;
															}

														}
													}
												}
											}
										}

									}
								}

							}
						}

					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Object getCauseAlias(final Map<String, Object> incidentReport, final String incidentTypeCauseId) {

		if (incidentReport != null && incidentTypeCauseId != null && incidentReport.containsKey(FIELD_INCIDENT_TYPE)) {

			Object objIncidentType = incidentReport.get(FIELD_INCIDENT_TYPE);

			if (objIncidentType instanceof Map) {

				Map<String, Object> incidentType = (Map<String, Object>) objIncidentType;

				Object objIncidentTypeId = incidentType.get(FIELD_ID);

				if (objIncidentTypeId != null) {

					Map<String, Object> incidentTypeReport = voDataSourceAdapter.get(
							VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_TYPE,
							new Object[] { (String) objIncidentTypeId });

					Object objIncidentTypeCause = incidentTypeReport.get(FIELD_IMS_INCIDENT_TYPE_CAUSES);

					if (objIncidentTypeCause instanceof List) {

						List<?> sequenceOfCauses = (List<?>) objIncidentTypeCause;

						for (Object objSequenceOfCauses : sequenceOfCauses) {

							if (objSequenceOfCauses instanceof Map) {

								Map<String, Object> sequenceOfCause = (Map<String, Object>) objSequenceOfCauses;
								Object objIncidentTypeCauseId = sequenceOfCause.get(FIELD_ID);

								if (objIncidentTypeCauseId != null
										&& incidentTypeCauseId.equals(objIncidentTypeCauseId)) {

									return sequenceOfCause.get(FIELD_ALIAS);

								}
							}

						}
					}

				}
			}
		}

		return null;
	}

	private Boolean addLog(final Map<String, Object> incidentReport, final String incidentReportId,
			final String logSubtypeId, final Object stateLabel, final Object arg1) {

		Boolean res = false;

		if (incidentReport != null) {

			String userId = null;

			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

			HttpSession httpSession = attr.getRequest().getSession(true);

			UserSessionUI userSession = sessionBO.getUserSession(httpSession.getId());

			if (userSession != null
					&& !StringUtils.isEmpty((String) userSession.getUser().get(UserSessionUI.USER_FIELD_ID))
					&& userSession.getAccess().equals(UserSessionUI.AUTHORIZED)) {

				userId = (String) userSession.getUser().get(UserSessionUI.USER_FIELD_ID);
			}

			if (userId != null) {

				Object objIncidentStateLabelAlias = (stateLabel != null) ? stateLabel
						: getIncidentStateLabelAlias(incidentReport);

				if (objIncidentStateLabelAlias != null) {
					Map<String, Object> input = new HashMap<>();

					List<Map<String, Object>> incidentReportLogs = new ArrayList<>();
					Map<String, Object> incidentReportLog = new HashMap<>();

					incidentReportLog.put(FIELD_INCIDENT_REPORT_ID, incidentReportId);
					incidentReportLog.put(FIELD_LOG_SUBTYPE_ID, logSubtypeId);
					incidentReportLog.put(FIELD_USER_ID, userId);
					incidentReportLog.put(FIELD_LEVEL, VALUE_DEFAULT_LEVEL);

					List<Map<String, Object>> incidentReportLogValues = new ArrayList<>();

					Map<String, Object> incidentReportLogValueState = new HashMap<>();
					incidentReportLogValueState.put(FIELD_VALUE, objIncidentStateLabelAlias);
					incidentReportLogValueState.put(FIELD_LOG_TYPE_PARAM_ID, LOG_TYPE_PARAM_STATE);
					incidentReportLogValues.add(incidentReportLogValueState);

					if (arg1 != null) {

						Map<String, Object> incidentReportLogValueArg1 = new HashMap<>();
						incidentReportLogValueArg1.put(FIELD_VALUE, arg1);
						incidentReportLogValueArg1.put(FIELD_LOG_TYPE_PARAM_ID, LOG_TYPE_PARAM_ARG_1);
						incidentReportLogValues.add(incidentReportLogValueArg1);
					}

					incidentReportLog.put(FIELD_INCIDENT_REPORT_LOG_VALUES, incidentReportLogValues);

					incidentReportLogs.add(incidentReportLog);
					input.put(FIELD_INCIDENT_REPORT_LOGS, incidentReportLogs);

					return addLog(input);
				}
			}
		}

		return res;
	}

	private Object getLocationAlias(String locationId) {

		if (locationId != null) {

			Map<String, Object> location = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_LOCATION,
					new Object[] { locationId });

			if (location != null && location.containsKey(FIELD_ALIAS)) {

				return location.get(FIELD_ALIAS);

			}
		}

		return null;
	}

	private Object getStretchAlias(String stretchId) {

		if (stretchId != null) {

			Map<String, Object> location = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_STRETCH,
					new Object[] { stretchId });

			if (location != null && location.containsKey(FIELD_ALIAS)) {

				return location.get(FIELD_ALIAS);

			}
		}
		return null;
	}

	public List<Map<String, Object>> getSummaryList() {

		List<Map<String, Object>> incidentReports = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORTS_SUMMARY, null);

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORTS_SUMMARY, incidentReports);
	}

	public Map<String, Object> getItemSumary(String id) {

		Map<String, Object> incidentReport = voDataSourceAdapter
				.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_INCIDENT_REPORT_SUMMARY, new Object[] { id });

		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_INCIDENT_REPORT_SUMMARY,
				incidentReport);
	}

	@SuppressWarnings("unchecked")
	public Object create(Object input) {

		Boolean res = false;

		Map<String, Object> createIncidentReport = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_CREATE_INCIDENT_REPORT, (Map<String, Object>) input);

		res = checkMapIsBoolean(createIncidentReport);

		return res;
	}

	public Boolean confirm(String id) {

		return confirm(id, null);
	}
	
	
	public Boolean confirm(String id, String userId) {

		Boolean res = false;

		Map<String, Object> input = new HashMap<>();

		input.put(FIELD_INCIDENT_REPORT_ID, id);
		
		if (!StringUtils.isEmpty(userId)) {
		
			input.put(FIELD_USER_ID, userId);
		}

		Map<String, Object> incidentReport = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_SEND_CONFIRM_INCIDENT_REPORT, input);

		res = checkMapIsBoolean(incidentReport);

		if (Boolean.TRUE.equals(res)) {

			incidentReport = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORT,
					new Object[] { id });

			res = addLog(incidentReport, id, LOG_SUBTYPE_INCIDENT_REPORT_CONFIRMED, null, null);
		}
		return res;
	}

	public Boolean forward(String id) {

		Boolean res = false;

		Map<String, Object> incidentReport = voDataSourceAdapter
				.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORT, new Object[] { id });

		if (incidentReport != null) {
			Map<String, Object> input = new HashMap<>();

			input.put(FIELD_INCIDENT_REPORT_ID, id);

			Map<String, Object> incidentReportRes = voDataSourceAdapter
					.put(VODataSourceAdapter.DATA_SOURCE_SEND_FORWARD_INCIDENT_REPORT, input);

			res = checkMapIsBoolean(incidentReportRes);

			if (Boolean.TRUE.equals(res)) {

				res = addLog(incidentReport, id, LOG_SUBTYPE_INCIDENT_REPORT_FORWARDED,
						getNextStateLabelAlias(incidentReport), null);
			}
		}

		return res;
	}
	
	public Boolean cancel(String id) {
		
		return cancel(id, null);
	}

	public Boolean cancel(String id, String userId) {
		Boolean res = false;

		Map<String, Object> incidentReport = voDataSourceAdapter
				.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORT, new Object[] { id });

		if (incidentReport != null) {

			Map<String, Object> input = new HashMap<>();

			input.put(FIELD_INCIDENT_REPORT_ID, id);
			
			if (!StringUtils.isEmpty(userId)) {
			
				input.put(FIELD_USER_ID, userId);
			}

			Map<String, Object> incidentReportRes = voDataSourceAdapter
					.put(VODataSourceAdapter.DATA_SOURCE_SEND_CANCEL_INCIDENT_REPORT, input);

			res = checkMapIsBoolean(incidentReportRes);

			if (Boolean.TRUE.equals(res)) {

				addLog(incidentReport, id, LOG_SUBTYPE_INCIDENT_REPORT_CANDELED, LBL_IMS_INCIDENT_STATE_CANCELED, null);
			}
		}

		return res;
	}

	public Boolean assignUser(String id, String userId) {

		Boolean res = false;

		Map<String, Object> user = userBO.getItem(userId);

		if (user != null && user.containsKey(FIELD_NAME)) {
			Map<String, Object> input = new HashMap<>();

			input.put(FIELD_INCIDENT_REPORT_ID, id);
			input.put(FIELD_USER_ID, userId);

			Map<String, Object> incidentReport = voDataSourceAdapter
					.put(VODataSourceAdapter.DATA_SOURCE_SEND_ASSING_USER_INCIDENT_REPORT, input);

			res = checkMapIsBoolean(incidentReport);

			if (Boolean.TRUE.equals(res)) {

				incidentReport = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORT,
						new Object[] { id });

				addLog(incidentReport, id, LOG_SUBTYPE_ASSIGNED_USER, null, user.get(FIELD_NAME));
			}
		}

		return res;
	}

	public Boolean finalizeTask(String id, String incidentTypeTaskId, Object incidentTypeTaskValues) {

		Boolean res = false;
		StringBuilder taskValues = new StringBuilder();

		Map<String, Object> input = new HashMap<>();

		input.put(FIELD_INCIDENT_REPORT_ID, id);
		input.put(FIELD_INCIDENT_TYPE_TASK_ID, incidentTypeTaskId);

		if (incidentTypeTaskValues instanceof Map) {

			@SuppressWarnings("unchecked")
			Map<String, Object> mapIncidentTypeTaskValues = (Map<String, Object>) incidentTypeTaskValues;

			if (mapIncidentTypeTaskValues.containsKey(FIELD_INCIDENT_TYPE_TASK_VALUES)) {

				input.put(FIELD_INCIDENT_TYPE_TASK_VALUES,
						mapIncidentTypeTaskValues.get(FIELD_INCIDENT_TYPE_TASK_VALUES));

				Object objIncidentTypeTaskValues = mapIncidentTypeTaskValues.get(FIELD_INCIDENT_TYPE_TASK_VALUES);

				if (objIncidentTypeTaskValues instanceof List) {

					List<?> incidentTypeTaskValuesList = (List<?>) objIncidentTypeTaskValues;

					for (Object objIncidentTypeTaskValue : incidentTypeTaskValuesList) {

						if (objIncidentTypeTaskValue instanceof Map) {

							Map<String, Object> incidentTypeTaskValue = (Map<String, Object>) objIncidentTypeTaskValue;

							if (incidentTypeTaskValue.containsKey(FIELD_VALUE)) {

								if (taskValues.length() != 0) {

									taskValues.append(", ");

								} else {
									taskValues.append(": ");
								}
								taskValues.append(incidentTypeTaskValue.get(FIELD_VALUE));
							}
						}

					}
				}
			}
		}

		Map<String, Object> incidentReport = voDataSourceAdapter
				.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORT, new Object[] { id });

		addLog(incidentReport, id, LOG_SUBTYPE_FINALIZED_TASK, null,
				getIncidentTypeTaskAlias(incidentReport, incidentTypeTaskId) + taskValues.toString());

		incidentReport = voDataSourceAdapter.put(VODataSourceAdapter.DATA_SOURCE_SEND_FINALIZE_TASK_INCIDENT_REPORT,
				input);

		res = checkMapIsBoolean(incidentReport);

		return res;
	}

	public Boolean executeTaskPlan(String id, String incidentTaskPlanId) {

		Boolean res = false;

		Map<String, Object> input = new HashMap<>();

		input.put(FIELD_INCIDENT_REPORT_ID, id);
		input.put(FIELD_INCIDENT_TASK_PLAN_ID, incidentTaskPlanId);

		Map<String, Object> incidentReport = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_SEND_EXECUTE_TASK_PLAN_INCIDENT_REPORT, input);

		res = checkMapIsBoolean(incidentReport);

		if (Boolean.TRUE.equals(res)) {

			incidentReport = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORT,
					new Object[] { id });

			addLog(incidentReport, id, LOG_SUBTYPE_EXECUTED_TASK_PLAN, null,
					getPlanAlias(incidentReport, incidentTaskPlanId));
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public Boolean addLog(Object input) {

		Boolean res = false;

		Map<String, Object> addLog = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_ADD_LOG_INCIDENT_INCIDENT_REPORT, (Map<String, Object>) input);

		res = checkMapIsBoolean(addLog);

		return res;
	}

	public Boolean deleteLog(String id) {

		Map<String, Object> input = new HashMap<>();

		input.put("incidentReportLogId", id);

		Boolean res = false;

		Map<String, Object> deleteLog = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_DELETE_LOG_INCIDENT_INCIDENT_REPORT, input);

		res = checkMapIsBoolean(deleteLog);

		return res;
	}

	public Boolean assignCause(String id, String causeId) {

		Boolean res = false;

		Map<String, Object> input = new HashMap<>();

		input.put(FIELD_INCIDENT_REPORT_ID, id);
		input.put(FIELD_IMS_INCIDENT_TYPE_CAUSE_ID, causeId);

		Map<String, Object> incidentReport = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_SEND_ASSING_CAUSE_INCIDENT_REPORT, input);

		res = checkMapIsBoolean(incidentReport);

		if (Boolean.TRUE.equals(res)) {

			incidentReport = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORT,
					new Object[] { id });

			addLog(incidentReport, id, LOG_SUBTYPE_ASSIGNED_CAUSE, null, getCauseAlias(incidentReport, causeId));
		}
		return res;
	}

	public Object changeIncidentReport(String id, String locationId, String stretchId, String incidentTypeId) {

		Boolean res = false;

		Map<String, Object> input = new HashMap<>();

		input.put(FIELD_INCIDENT_REPORT_ID, id);
		input.put(FIELD_LOCATION_ID, locationId);
		input.put(FIELD_AFFECTION_STRETCH_ID, stretchId);
		input.put(FIELD_INCIDENT_TYPE_ID, incidentTypeId);

		Map<String, Object> incidentReport = voDataSourceAdapter
				.put(VODataSourceAdapter.DATA_SOURCE_SEND_CHANGE_INCIDENT_REPORT, input);

		res = checkMapIsBoolean(incidentReport);

		if (Boolean.TRUE.equals(res)) {

			incidentReport = voDataSourceAdapter.get(VODataSourceAdapter.DATA_SOURCE_GET_INCIDENT_REPORT,
					new Object[] { id });

			addLog(incidentReport, id, LOG_SUBTYPE_INCIDENT_REPORT_CHANGED, null,
					getStretchAlias(stretchId) + "->" + getLocationAlias(locationId));
		}

		return res;
	}

}
