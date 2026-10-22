package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CCTVScenario;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeRoadImpact;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.data.core.model.Spath;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeStretchVO;

public class ImsIncidentTypeStretchHelper {

	private ImsIncidentTypeStretchHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentTypeStretchVO get(final ImsIncidentTypeStretch imsIncidentTypeStretch) {

		ImsIncidentTypeStretchVO imsIncidentTypeStretchVO = null;

		if (imsIncidentTypeStretch != null) {

			imsIncidentTypeStretchVO = new ImsIncidentTypeStretchVO();

			assign(imsIncidentTypeStretch, imsIncidentTypeStretchVO);
		}

		return imsIncidentTypeStretchVO;
	}

	public static ImsIncidentTypeStretch get(final ImsIncidentTypeStretchVO imsIncidentTypeStretchVO) {

		ImsIncidentTypeStretch imsIncidentTypeStretch = null;

		if (imsIncidentTypeStretchVO != null) {

			imsIncidentTypeStretch = new ImsIncidentTypeStretch();

			if (imsIncidentTypeStretchVO.getId() != null) {

				Key key = new Key(imsIncidentTypeStretchVO.getId());

				if (key.getClazz() == ImsIncidentTypeStretch.class) {

					imsIncidentTypeStretch.setIncidentTypeId(Long.valueOf(key.getId(1)));
					imsIncidentTypeStretch.setStretchId(Long.valueOf(key.getId(2)));
				}
			}

			imsIncidentTypeStretch.setOperationalPlanUrl(imsIncidentTypeStretchVO.getOperationalPlanUrl());
			imsIncidentTypeStretch.setAlias(imsIncidentTypeStretchVO.getAlias());
			imsIncidentTypeStretch.setDescription(imsIncidentTypeStretchVO.getDescription());
			imsIncidentTypeStretch.setMultiInstance(imsIncidentTypeStretchVO.getMultiInstance());
			imsIncidentTypeStretch.setCheckStretchStretch(imsIncidentTypeStretchVO.getCheckStretchStretch());

			if (imsIncidentTypeStretchVO.getIncidentTypeId() != null) {

				Key key = new Key(imsIncidentTypeStretchVO.getIncidentTypeId());

				if (key.getClazz() == ImsIncidentType.class) {

					imsIncidentTypeStretch.setIncidentTypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentTypeStretchVO.getStretchId() != null) {

				Key key = new Key(imsIncidentTypeStretchVO.getStretchId());

				if (key.getClazz() == Stretch.class) {

					imsIncidentTypeStretch.setStretchId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (imsIncidentTypeStretchVO.getSpathId() != null) {

				Key key = new Key(imsIncidentTypeStretchVO.getSpathId());

				if (key.getClazz() == Spath.class) {

					imsIncidentTypeStretch.setSpathId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentTypeStretchVO.getDefaultLocationId() != null) {

				Key key = new Key(imsIncidentTypeStretchVO.getDefaultLocationId());

				if (key.getClazz() == Location.class) {

					imsIncidentTypeStretch.setDefaultLocationId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (imsIncidentTypeStretchVO.getScenarioId() != null) {
				
				Key key = new Key(imsIncidentTypeStretchVO.getScenarioId());

				if (key.getClazz() == CCTVScenario.class) {

					imsIncidentTypeStretch.setCctvScenarioId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (imsIncidentTypeStretchVO.getRoadImpactIds() != null){

				if (!CollectionUtils.isEmpty(imsIncidentTypeStretchVO.getRoadImpactIds())) {
					
					for (String roadImpactId : imsIncidentTypeStretchVO.getRoadImpactIds()) {
						
						ImsIncidentTypeRoadImpact incidentTypeRoadImpact = new ImsIncidentTypeRoadImpact();
						
						incidentTypeRoadImpact.setStretchId(imsIncidentTypeStretch.getStretchId());
						incidentTypeRoadImpact.setIncidentTypeId(imsIncidentTypeStretch.getIncidentTypeId());
						
						Key key = new Key(roadImpactId);

						if (key.getClazz() == RoadImpact.class) {

							incidentTypeRoadImpact.setRoadImpactId(Long.valueOf(key.getId(1)));
						}
						
						if (imsIncidentTypeStretch.getIncidentTypeRoadImpacts() == null) {
							
							imsIncidentTypeStretch.setIncidentTypeRoadImpacts(new ArrayList<>());
						}
						
						imsIncidentTypeStretch.getIncidentTypeRoadImpacts().add(incidentTypeRoadImpact);
					}
				}else {
					
					imsIncidentTypeStretch.setIncidentTypeRoadImpacts(new ArrayList<>());
				}
							
			}
		}

		return imsIncidentTypeStretch;
	}

	public static void assign(final ImsIncidentTypeStretch imsIncidentTypeStretch,
			ImsIncidentTypeStretchVO imsIncidentTypeStretchVO) {

		if (ObjectUtils.allNotNull(imsIncidentTypeStretch, imsIncidentTypeStretchVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentTypeStretchVO, imsIncidentTypeStretch);

			imsIncidentTypeStretchVO.setOperationalPlanUrl(imsIncidentTypeStretch.getOperationalPlanUrl());
			imsIncidentTypeStretchVO.setMultiInstance(imsIncidentTypeStretch.getMultiInstance());
			imsIncidentTypeStretchVO.setCheckStretchStretch(imsIncidentTypeStretch.getCheckStretchStretch());
			
			if (imsIncidentTypeStretch.getSpathId() != null) {
				
				Spath spath = new Spath();
				spath.setId(imsIncidentTypeStretch.getSpathId());			
			
				imsIncidentTypeStretchVO.setSpathId(BasicElementHelper.getIdFromElement(spath));
			}

			if (imsIncidentTypeStretch.getIncidentTypeId() != null) {

				ImsIncidentType imsIncidentType = new ImsIncidentType();
				imsIncidentType.setId(imsIncidentTypeStretch.getIncidentTypeId());
				imsIncidentTypeStretchVO.setIncidentTypeId(BasicElementHelper.getIdFromElement(imsIncidentType));
			}

			if (imsIncidentTypeStretch.getStretchId() != null) {

				Stretch stretch = new Stretch();
				stretch.setId(imsIncidentTypeStretch.getStretchId());
				imsIncidentTypeStretchVO.setStretchId(BasicElementHelper.getIdFromElement(stretch));
			}

			if (imsIncidentTypeStretch.getDefaultLocationId() != null) {

				Location location = new Location();
				location.setId(imsIncidentTypeStretch.getDefaultLocationId());
				imsIncidentTypeStretchVO.setDefaultLocationId(BasicElementHelper.getIdFromElement(location));
			}

		}
	}

}
