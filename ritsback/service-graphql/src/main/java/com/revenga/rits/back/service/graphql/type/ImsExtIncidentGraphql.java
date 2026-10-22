package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.ImsExtIncident;
import com.revenga.rits.back.data.core.model.ImsExtIncidentType;
import com.revenga.rits.back.data.core.model.ImsExtIncidentValue;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.front.core.model.helper.ImsExtIncidentHelper;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentTypeVO;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentVO;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentValueVO;
import com.revenga.rits.back.front.core.model.vo.LocationVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ImsExtIncidentGraphql extends ImsExtIncidentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4075055122947321558L;
	
	private ImsExtIncident imsExtIncident;

	public ImsExtIncidentGraphql(final ImsExtIncident imsExtIncident) {

		ImsExtIncidentHelper.assign(imsExtIncident, this);

		this.imsExtIncident = imsExtIncident;
	}
	
	@Override
	public LocationVO getLocation() {

		if (super.getLocation() == null) {

			if (ObjectUtils.allNotNull(imsExtIncident)) {

				Location location = EntitiesManager.getInstance().getLocationRepository().get(imsExtIncident.getLocationId(),
						false);

				if (location != null) {

					super.setLocation(new LocationGraphql(location));
				}

			}

		}

		return super.getLocation();
	}
	
	@Override
	public ImsExtIncidentTypeVO getExtIncidentType() {

		if (super.getExtIncidentType() == null) {

			if (ObjectUtils.allNotNull(imsExtIncident)) {

				ImsExtIncidentType extIncidentType = EntitiesManager.getInstance().getImsExtIncidentTypeRepository()
						.get(imsExtIncident.getExtIncidentTypeId(), false);

				if (extIncidentType != null) {

					super.setExtIncidentType(new ImsExtIncidentTypeGraphql(extIncidentType));
				}
			}
		}

		return super.getExtIncidentType();
	}
	
	@Override
	public List<ImsExtIncidentValueVO> getExtIncidentValues() {

		if (super.getExtIncidentValues() == null) {

			if (imsExtIncident != null) {

				List<ImsExtIncidentValue> imsExtIncidentValues = EntitiesManager.getInstance().getImsExtIncidentValueRepository()
						.getsByImsExtIncident(imsExtIncident.getExtIncidentTypeId(), imsExtIncident.getId(), false);

				if (!CollectionUtils.isEmpty(imsExtIncidentValues)) {

					List<ImsExtIncidentValueVO> imsExtIncidentValuesVO = new ArrayList<>();

					for (ImsExtIncidentValue imsExtIncidentValue : imsExtIncidentValues) {

						imsExtIncidentValuesVO.add(new ImsExtIncidentValueGraphql(imsExtIncidentValue));
					}
					super.setExtIncidentValues(imsExtIncidentValuesVO);
				}

			} else {

				log.error("incident has null pointer");
			}
		}

		return super.getExtIncidentValues();
	}

}
