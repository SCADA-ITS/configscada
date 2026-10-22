package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.CCTVScenario;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmCondition;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeRoadImpact;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.data.core.model.SpathStretch;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentTypeStretchHelper;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeStretchVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeVO;
import com.revenga.rits.back.front.core.model.vo.LocationVO;
import com.revenga.rits.back.front.core.model.vo.RoadImpactVO;
import com.revenga.rits.back.front.core.model.vo.StretchVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class ImsIncidentTypeStretchGraphql extends ImsIncidentTypeStretchVO {
	/**
	* 
	*/
	private static final long serialVersionUID = -4872062377189658260L;

	private ImsIncidentTypeStretch imsIncidentTypeStretch;

	public ImsIncidentTypeStretchGraphql(final ImsIncidentTypeStretch imsIncidentTypeStretch) {

		ImsIncidentTypeStretchHelper.assign(imsIncidentTypeStretch, this);
		this.imsIncidentTypeStretch = imsIncidentTypeStretch;

	}

	@Override
	public ImsIncidentTypeVO getIncidentType() {

		if (super.getIncidentType() == null && imsIncidentTypeStretch != null
				&& imsIncidentTypeStretch.getIncidentTypeId() != null) {

			ImsIncidentType imsIncidentType = EntitiesManager.getInstance().getImsIncidentTypeRepository()
					.get(imsIncidentTypeStretch.getIncidentTypeId(), false);

			if (imsIncidentType != null) {

				super.setIncidentType(new ImsIncidentTypeGraphql(imsIncidentType));
			}
		}

		return super.getIncidentType();
	}

	@Override
	public StretchVO getStretch() {

		if (super.getStretch() == null && imsIncidentTypeStretch != null
				&& imsIncidentTypeStretch.getStretchId() != null) {

			Stretch stretch = EntitiesManager.getInstance().getStretchRepository()
					.get(imsIncidentTypeStretch.getStretchId(), false);

			if (stretch != null) {

				super.setStretch(new StretchGraphql(stretch));
			}
		}

		return super.getStretch();
	}

	@Override
	public LocationVO getDefaultLocation() {

		if (super.getDefaultLocation() == null) {

			if (imsIncidentTypeStretch != null && imsIncidentTypeStretch.getDefaultLocationId() != null) {

				Location location = EntitiesManager.getInstance().getLocationRepository()
						.get(imsIncidentTypeStretch.getDefaultLocationId(), false);

				if (location != null) {

					super.setDefaultLocation(new LocationGraphql(location));
				}
			}

		}

		return super.getDefaultLocation();
	}

	@Override
	public List<ImsIncidentAlarmConditionVO> getImsIncidentAlarmConditions() {

		if (super.getImsIncidentAlarmConditions() == null) {

			if (imsIncidentTypeStretch != null) {

				List<ImsIncidentAlarmCondition> imsIncidentAlarmConditions = EntitiesManager.getInstance()
						.getImsIncidentAlarmConditionRepository()
						.getsIncidentTypeAndStretch(imsIncidentTypeStretch.getStretchId(),
								imsIncidentTypeStretch.getIncidentTypeId(), false);

				if (!CollectionUtils.isEmpty(imsIncidentAlarmConditions)) {

					List<ImsIncidentAlarmConditionVO> imsIncidentAlarmConditionVO = new ArrayList<>();

					for (ImsIncidentAlarmCondition imsIncidentAlarmCondition : imsIncidentAlarmConditions) {

						imsIncidentAlarmConditionVO
								.add(new ImsIncidentAlarmConditionGraphql(imsIncidentAlarmCondition));
					}
					super.setImsIncidentAlarmConditions(imsIncidentAlarmConditionVO);
				}

			} else {

				log.error("imsIncidentTypeStretch has null pointer");
			}
		}

		return super.getImsIncidentAlarmConditions();
	}

	@Override
	public CCTVScenarioVO getScenario() {

		if (imsIncidentTypeStretch != null && imsIncidentTypeStretch.getCctvScenarioId() != null) {

			CCTVScenario cctvScenario = EntitiesManager.getInstance().getCctvScenarioRepository()
					.get(imsIncidentTypeStretch.getCctvScenarioId(), false);

			if (cctvScenario != null) {

				super.setScenario(new CCTVScenarioGraphql(cctvScenario));
			}
		}

		return super.getScenario();
	}

	@Override
	public List<StretchVO> getSpath() {

		if (super.getSpath() == null) {

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

								StretchGraphql stretchGraphql = new StretchGraphql(stretch);

								stretchGraphql.setPosition(spathStretch.getPosition());
								stretchVOs.add(stretchGraphql);
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
	public List<RoadImpactVO> getRoadImpacts() {

		if (super.getRoadImpacts() == null) {

			if (imsIncidentTypeStretch.getIncidentTypeId() != null && imsIncidentTypeStretch.getStretchId() != null) {

				List<RoadImpactVO> roadImpactsVO = null;

				if (EntitiesManager.getInstance().getImsIncidentTypeRoadImpactRepository() != null) {

					List<ImsIncidentTypeRoadImpact> incidentTypeRoadImpacts = EntitiesManager.getInstance()
							.getImsIncidentTypeRoadImpactRepository().getsByIncidentTypeStretch(
									imsIncidentTypeStretch.getIncidentTypeId(), imsIncidentTypeStretch.getStretchId(), false);

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

				log.error("imsIncidentTypeStretch has null pointer");
			}
		}

		return super.getRoadImpacts();
	}
}
