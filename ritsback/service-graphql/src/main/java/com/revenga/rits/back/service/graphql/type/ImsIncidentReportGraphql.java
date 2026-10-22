package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.CCTVScenario;
import com.revenga.rits.back.data.core.model.ImsIncidentLevel;
import com.revenga.rits.back.data.core.model.ImsIncidentParam;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.data.core.model.ImsIncidentReportLog;
import com.revenga.rits.back.data.core.model.ImsIncidentReportLogValue;
import com.revenga.rits.back.data.core.model.ImsIncidentReportParamValue;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskElement;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskPlanState;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskState;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskValue;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeCause;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeRoadImpact;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStateTransition;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.data.core.model.SpathStretch;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentReportHelper;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentLevelVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportAlarmVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportLogVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportParamValueVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportTaskStateVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentSequenceStateVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentStateVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeCauseVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeVO;
import com.revenga.rits.back.front.core.model.vo.LocationVO;
import com.revenga.rits.back.front.core.model.vo.RoadImpactVO;
import com.revenga.rits.back.front.core.model.vo.StretchVO;
import com.revenga.rits.back.front.core.model.vo.UserVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class ImsIncidentReportGraphql extends ImsIncidentReportVO {
	/**
	* 
	*/
	private static final long serialVersionUID = -1977979367438456638L;

	private ImsIncidentReport incidentReport;
	private ImsIncidentType incidentType;
	private List<ImsIncidentReportTaskState> incidentReportTaskStates;
	private List<ImsIncidentReportTaskValue> incidentReportTaskValues;
	private List<ImsIncidentReportTaskElement> incidentReportTaskElements;
	private List<ImsIncidentReportTaskPlanState> incidentReportTaskPlanStates;
	private List<ImsIncidentReportParamValue> incidentParamValues;

	public ImsIncidentReportGraphql(final ImsIncidentReport imsIncidentReport) {

		ImsIncidentReportHelper.assign(imsIncidentReport, this);
		this.incidentReport = imsIncidentReport;
		this.incidentReportTaskStates = null;
		this.incidentReportTaskValues = null;
		this.incidentReportTaskElements = null;
		this.incidentReportTaskPlanStates = null;
		this.incidentParamValues = null;
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentTypeCauseVO getIncidentTypeCause() {

		if (super.getIncidentTypeCause() == null && incidentReport != null
				&& incidentReport.getIncidentTypeCauseId() != null) {

			ImsIncidentTypeCause incidentTypeCause = EntitiesManager.getInstance().getImsIncidentTypeCauseRepository()
					.get(incidentReport.getIncidentTypeId(), incidentReport.getIncidentTypeCauseId(), false);

			if (incidentTypeCause != null) {

				super.setIncidentTypeCause(new ImsIncidentTypeCauseGraphql(incidentTypeCause));
			}
		}

		return super.getIncidentTypeCause();
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentStateVO getIncidentState() {

		if (super.getIncidentState() == null && incidentReport != null && incidentReport.getIncidentStateId() != null) {

			ImsIncidentState imsIncidentState = EntitiesManager.getInstance().getImsIncidentStateRepository()
					.get(incidentReport.getIncidentStateId(), false);

			if (imsIncidentState != null) {

				super.setIncidentState(new ImsIncidentStateGraphql(imsIncidentState));
			}
		}

		return super.getIncidentState();
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentTypeVO getIncidentType() {

		if (super.getIncidentType() == null && incidentReport != null && incidentReport.getIncidentTypeId() != null) {

			if (incidentType == null) {

				incidentType = EntitiesManager.getInstance().getImsIncidentTypeRepository()
						.get(incidentReport.getIncidentTypeId(), false);
			}

			if (incidentType != null) {

				super.setIncidentType(new ImsIncidentTypeGraphql(incidentType));
			}
		}

		return super.getIncidentType();
	}

	/**
	 * 
	 */
	@Override
	public StretchVO getAffectionStretch() {

		if (super.getAffectionStretch() == null && incidentReport != null
				&& incidentReport.getAffectionStretchId() != null) {

			Stretch stretch = EntitiesManager.getInstance().getStretchRepository()
					.get(incidentReport.getAffectionStretchId(), false);

			if (stretch != null) {

				super.setAffectionStretch(new StretchGraphql(stretch));
			}
		}

		return super.getAffectionStretch();
	}

	/**
	 * 
	 */
	@Override
	public LocationVO getLocation() {

		if (super.getLocation() == null) {

			if (incidentReport != null && incidentReport.getLocationId() != null) {

				Location location = EntitiesManager.getInstance().getLocationRepository()
						.get(incidentReport.getLocationId(), false);

				if (location != null) {

					super.setLocation(new LocationGraphql(location));
				}
			}

		}

		return super.getLocation();
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentLevelVO getIncidentLevel() {

		if (super.getIncidentLevel() == null && incidentReport != null && incidentReport.getIncidentLevelId() != null) {

			ImsIncidentLevel imsIncidentLevel = EntitiesManager.getInstance().getImsIncidentLevelRepository()
					.get(incidentReport.getIncidentLevelId(), false);

			if (imsIncidentLevel != null) {

				super.setIncidentLevel(new ImsIncidentLevelGraphql(imsIncidentLevel));
			}
		}

		return super.getIncidentLevel();
	}

	/**
	 * 
	 */
	@Override
	public UserVO getCurrentUser() {

		if (super.getCurrentUser() == null && incidentReport != null && incidentReport.getCurrentUserId() != null) {

			User user = EntitiesManager.getInstance().getUserRepository().get(incidentReport.getCurrentUserId(), false);

			if (user != null) {

				super.setCurrentUser(new UserGraphql(user));
			}
		}

		return super.getCurrentUser();
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentReportLogVO> getIncidentLogs() {

		if (super.getIncidentLogs() == null) {

			if (incidentReport != null) {

				List<ImsIncidentReportLog> imsIncidentReportLogs = EntitiesManager.getInstance()
						.getImsIncidentReportLogRepository().getsByIncidentReport(incidentReport.getId(), false);

				if (!CollectionUtils.isEmpty(imsIncidentReportLogs)) {

					Collections.sort(imsIncidentReportLogs);

					List<ImsIncidentReportLogVO> imsIncidentReportLogsVO = new ArrayList<>();

					List<ImsIncidentReportLogValue> imsIncidentReportLogValues = EntitiesManager.getInstance()
							.getImsIncidentReportLogValueRepository().getAll(false);

					for (ImsIncidentReportLog imsIncidentReportLog : imsIncidentReportLogs) {

						imsIncidentReportLogsVO
								.add(new ImsIncidentReportLogGraphql(imsIncidentReportLog, imsIncidentReportLogValues));
					}
					super.setIncidentLogs(imsIncidentReportLogsVO);
				}

			} else {

				log.error("imsIncidentReport has null pointer");
			}
		}

		return super.getIncidentLogs();
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentSequenceStateVO> getSequenceOfStates() {

		if (super.getSequenceOfStates() == null) {

			if (incidentReport != null && incidentReport.getIncidentTypeId() != null) {

				if (incidentType == null) {

					incidentType = EntitiesManager.getInstance().getImsIncidentTypeRepository()
							.get(incidentReport.getIncidentTypeId(), false);
				}

				if (incidentType != null && incidentType.getIniIncidentStateId() != null) {

					ImsIncidentState incidentState = EntitiesManager.getInstance().getImsIncidentStateRepository()
							.get(incidentType.getIniIncidentStateId(), false);
					List<ImsIncidentSequenceStateVO> imsIncidentSequenceStatesVO = null;

					incidentReportTaskStates = incidentReportTaskStates == null
							? EntitiesManager.getInstance().getImsIncidentReportTaskStateRepository()
									.getByIncidentReport(incidentReport.getId(), false)
							: incidentReportTaskStates;

					incidentReportTaskValues = incidentReportTaskValues == null
							? EntitiesManager.getInstance().getImsIncidentReportTaskValueRepository()
									.getsByIncidentReport(incidentReport.getId(), false)
							: incidentReportTaskValues;

					incidentReportTaskElements = incidentReportTaskElements == null
							? EntitiesManager.getInstance().getImsIncidentReportTaskElementRepository()
									.getsByIncidentReport(incidentReport.getId(), false)
							: incidentReportTaskElements;

					incidentReportTaskPlanStates = incidentReportTaskPlanStates == null
							? EntitiesManager.getInstance().getImsIncidentReportTaskPlanStateRepository()
									.getByIncidentReport(incidentReport.getId(), false)
							: incidentReportTaskPlanStates;

					while (incidentState != null) {

						if (imsIncidentSequenceStatesVO == null) {

							imsIncidentSequenceStatesVO = new ArrayList<>();
						}
						imsIncidentSequenceStatesVO.add(new ImsIncidentSequenceStateGraphql(incidentReport,
								incidentState, incidentReportTaskStates, incidentReportTaskValues,
								incidentReportTaskElements, incidentReportTaskPlanStates));

						ImsIncidentTypeStateTransition imsIncidentTypeStateTransition = EntitiesManager.getInstance()
								.getImsIncidentTypeStateTransitionRepository()
								.get(incidentReport.getIncidentTypeId(), incidentState.getId(), false);

						incidentState = imsIncidentTypeStateTransition != null
								? EntitiesManager.getInstance().getImsIncidentStateRepository().get(
										imsIncidentTypeStateTransition.getEndIncidentStateId(), false)
								: null;

					}
					super.setSequenceOfStates(imsIncidentSequenceStatesVO);
				} else {
					log.error("imsIncidentTypeReport has null pointer");
				}

			} else {

				log.error("imsIncidentReport has null pointer");
			}
		}

		return super.getSequenceOfStates();
	}

	@Override
	public List<ImsIncidentReportTaskStateVO> getTaskStates() {

		if (super.getTaskStates() == null) {

			if (incidentReport != null) {

				incidentReportTaskStates = incidentReportTaskStates == null ? EntitiesManager.getInstance()
						.getImsIncidentReportTaskStateRepository().getByIncidentReport(incidentReport.getId(), false)
						: incidentReportTaskStates;

				incidentReportTaskValues = incidentReportTaskValues == null ? EntitiesManager.getInstance()
						.getImsIncidentReportTaskValueRepository().getsByIncidentReport(incidentReport.getId(), false)
						: incidentReportTaskValues;

				incidentReportTaskElements = incidentReportTaskElements == null
						? EntitiesManager.getInstance().getImsIncidentReportTaskElementRepository()
								.getsByIncidentReport(incidentReport.getId(), false)
						: incidentReportTaskElements;

				incidentReportTaskPlanStates = incidentReportTaskPlanStates == null
						? EntitiesManager.getInstance().getImsIncidentReportTaskPlanStateRepository()
								.getByIncidentReport(incidentReport.getId(), false)
						: incidentReportTaskPlanStates;

				if (!CollectionUtils.isEmpty(incidentReportTaskStates)) {

					List<ImsIncidentTypeTask> incidentTypeTasks = EntitiesManager.getInstance()
							.getImsIncidentTypeTaskRepository().gets(incidentReport.getIncidentTypeId(), null,
									incidentReport.getAffectionStretchId(), false);

					if (!CollectionUtils.isEmpty(incidentTypeTasks)) {

						List<ImsIncidentReportTaskStateVO> imsIncidentReportTaskStateVO = new ArrayList<>();

						incidentReportTaskStates.forEach(i -> {

							ImsIncidentTypeTask imsIncidentTypeTask = incidentTypeTasks.stream()
									.filter(j -> j.getId().equals(i.getIncidentTypeTaskId())).findFirst().orElse(null);

							if (imsIncidentTypeTask != null) {

								imsIncidentReportTaskStateVO.add(new ImsIncidentReportTaskStateGraphql(i,
										incidentReportTaskValues, incidentReportTaskElements,
										incidentReportTaskPlanStates, imsIncidentTypeTask));
							}

						});
						super.setTaskStates(imsIncidentReportTaskStateVO);
					}
				}

			} else {

				log.error("incidentReport or incidentState has null pointer");
			}
		}

		return super.getTaskStates();
	}

	/**
	 * 
	 */
	@Override
	public List<ImsIncidentReportAlarmVO> getAlarms() {

		if (super.getAlarms() == null) {

			if (incidentReport != null) {

				List<ImsIncidentReportAlarm> incidentReportAlarms = EntitiesManager.getInstance()
						.getImsIncidentReportAlarmRepository().getsByIncidentReport(incidentReport.getId(), false);

				if (!CollectionUtils.isEmpty(incidentReportAlarms)) {

					List<ImsIncidentReportAlarmVO> imsIncidentReportAlarmVO = new ArrayList<>();

					for (ImsIncidentReportAlarm incidentReportAlarm : incidentReportAlarms) {

						imsIncidentReportAlarmVO.add(new ImsIncidentReportAlarmGraphql(incidentReportAlarm));
					}
					super.setAlarms(imsIncidentReportAlarmVO);
				}

			} else {

				log.error("imsIncidentReport has null pointer");
			}
		}

		return super.getAlarms();
	}

	/**
	 * 
	 */
	@Override
	public String getOperationalPlanUrl() {

		if (super.getOperationalPlanUrl() == null) {

			if (incidentReport != null && incidentReport.getAffectionStretchId() != null
					&& incidentReport.getIncidentTypeId() != null) {

				ImsIncidentTypeStretch incidentTypeStretch = EntitiesManager.getInstance()
						.getImsIncidentTypeStretchRepository()
						.get(incidentReport.getIncidentTypeId(), incidentReport.getAffectionStretchId(), false);

				if (incidentTypeStretch != null && incidentTypeStretch.getOperationalPlanUrl() != null) {
					super.setOperationalPlanUrl(incidentTypeStretch.getOperationalPlanUrl());
				}

			} else {

				log.error("imsIncidentReport has null pointer");
			}
		}

		return super.getOperationalPlanUrl();

	}

	/**
	 * 
	 */
	@Override
	public CCTVScenarioVO getScenario() {

		if (super.getScenario() == null && incidentReport != null && incidentReport.getAffectionStretchId() != null
				&& incidentReport.getIncidentTypeId() != null) {

			ImsIncidentTypeStretch imsIncidentTypeStretch = EntitiesManager.getInstance()
					.getImsIncidentTypeStretchRepository()
					.get(incidentReport.getIncidentTypeId(), incidentReport.getAffectionStretchId(), false);

			if (imsIncidentTypeStretch != null && imsIncidentTypeStretch.getCctvScenarioId() != null) {

				CCTVScenario cctvScenario = EntitiesManager.getInstance().getCctvScenarioRepository()
						.get(imsIncidentTypeStretch.getCctvScenarioId(), false);

				if (cctvScenario != null) {

					super.setScenario(new CCTVScenarioGraphql(cctvScenario));
				}

			}
		}

		return super.getScenario();
	}

	@Override
	public List<StretchVO> getSpath() {

		if (super.getSpath() == null && incidentReport != null && incidentReport.getAffectionStretchId() != null) {

			ImsIncidentTypeStretch imsIncidentTypeStretch = EntitiesManager.getInstance()
					.getImsIncidentTypeStretchRepository()
					.get(incidentReport.getIncidentTypeId(), incidentReport.getAffectionStretchId(), false);

			if (imsIncidentTypeStretch != null && imsIncidentTypeStretch.getStretchId() != null
					&& imsIncidentTypeStretch.getSpathId() != null) {

				List<SpathStretch> spathStretchs = EntitiesManager.getInstance().getSpathStretchRepository()
						.gets(imsIncidentTypeStretch.getSpathId(), imsIncidentTypeStretch.getStretchId(), false);

				if (!CollectionUtils.isEmpty(spathStretchs)) {

					List<StretchVO> stretchVOs = new ArrayList<>();

					for (SpathStretch spathStretch : spathStretchs) {

						if (spathStretch.getRelStretchId() != null) {

							Stretch stretch = EntitiesManager.getInstance().getStretchRepository()
									.get(spathStretch.getRelStretchId(), false);

							if (stretch != null) {

								StretchVO stretchVO = new StretchGraphql(stretch);

								stretchVO.setPosition(spathStretch.getPosition());

								stretchVOs.add(stretchVO);
							}
						}
					}

					super.setSpath(stretchVOs);
				}
			}
		}

		return super.getSpath();
	}

	@Override
	public List<ImsIncidentReportParamValueVO> getIncidentParamValues() {

		List<ImsIncidentReportParamValue> incidentParamValuesAux = new ArrayList<>();

		if (super.getIncidentParamValues() == null) {

			if (incidentReport != null) {

				List<ImsIncidentParam> incidentParams = EntitiesManager.getInstance().getImsIncidentParamRepository()
						.getAll(true);

				this.incidentParamValues = EntitiesManager.getInstance().getImsIncidentParamValueRepository()
						.getsByIncidentReport(incidentReport.getId(), true);

				if (incidentParams != null && this.incidentParamValues != null) {

					for (ImsIncidentParam incidentParam : incidentParams) {

						ImsIncidentReportParamValue incidentParamValueAux = new ImsIncidentReportParamValue();
						incidentParamValueAux.setIncidentParamId(incidentParam.getId());
						incidentParamValueAux.setIncidentReportId(incidentReport.getId());

						incidentParamValuesAux.add(incidentParamValueAux);
					}

					for (ImsIncidentReportParamValue incidentParamValue : this.incidentParamValues) {

						for (ImsIncidentReportParamValue incidentParamValueAux : incidentParamValuesAux) {

							if (incidentParamValue.getIncidentReportId()
									.equals(incidentParamValueAux.getIncidentReportId())
									&& incidentParamValue.getIncidentParamId()
											.equals(incidentParamValueAux.getIncidentParamId())) {

								incidentParamValueAux.setValue(incidentParamValue.getValue());
							}
						}
					}
				}

				if (!CollectionUtils.isEmpty(incidentParamValuesAux)) {

					List<ImsIncidentReportParamValueVO> incidentParamValuesVO = new ArrayList<>();

					for (ImsIncidentReportParamValue incidentParamValue : incidentParamValuesAux) {

						incidentParamValuesVO.add(new ImsIncidentReportParamValueGraphql(incidentParamValue));
					}

					incidentParamValuesVO.sort((ImsIncidentReportParamValueVO a, ImsIncidentReportParamValueVO b) -> a
							.getIncidentParamId().compareTo(b.getIncidentParamId()));

					super.setIncidentParamValues(incidentParamValuesVO);

				} else {

					super.setIncidentParamValues(new ArrayList<>());
				}

			} else {

				log.error("imsIncidentReport has null pointer");
			}
		}

		return super.getIncidentParamValues();
	}
	
	@Override
	public RoadImpactVO getRoadImpact() {

		if (super.getRoadImpact() == null) {

			if (incidentReport.getRoadImpactId() != null){

				RoadImpact roadImpact = EntitiesManager.getInstance()
						.getRoadImpactRepository()
						.get(incidentReport.getRoadImpactId(), false);

				if (roadImpact != null) {

					super.setRoadImpact(new RoadImpactGraphql(roadImpact));
				}

			} 
		}

		return super.getRoadImpact();
	}
	
	@Override
	public List<RoadImpactVO> getRoadImpacts() {

		if (super.getRoadImpacts() == null) {

			if (incidentReport.getIncidentTypeId() != null && incidentReport.getAffectionStretchId()!= null) {

				List<RoadImpactVO> roadImpactsVO = null;

				if (EntitiesManager.getInstance().getImsIncidentTypeRoadImpactRepository() != null) {

					List<ImsIncidentTypeRoadImpact> incidentTypeRoadImpacts = EntitiesManager.getInstance()
							.getImsIncidentTypeRoadImpactRepository().getsByIncidentTypeStretch(
									incidentReport.getIncidentTypeId(), incidentReport.getAffectionStretchId(), false);

					if (!CollectionUtils.isEmpty(incidentTypeRoadImpacts)) {

						roadImpactsVO = new ArrayList<>();

						for (ImsIncidentTypeRoadImpact incidentTypeRoadImpact : incidentTypeRoadImpacts) {

							RoadImpact roadImpact = EntitiesManager.getInstance().getRoadImpactRepository()
									.get(incidentTypeRoadImpact.getRoadImpactId(), false);

							if (roadImpact != null) {

								roadImpactsVO.add(new RoadImpactGraphql(roadImpact));
							}
						}
					}

					if (roadImpactsVO != null) {

						super.setRoadImpacts(roadImpactsVO);
					}
				}

			} else {

				log.error("IncidentReport has null pointer");
			}
		}

		return super.getRoadImpacts();
	}
}
