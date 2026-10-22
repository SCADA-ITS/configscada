package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsExtIncident;
import com.revenga.rits.back.data.core.model.ImsExtIncidentType;
import com.revenga.rits.back.data.core.model.ImsExtIncidentValue;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentVO;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentValueVO;

public class ImsExtIncidentHelper {

	private ImsExtIncidentHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsExtIncidentVO get(final ImsExtIncident imsExtIncident) {

		ImsExtIncidentVO imsExtIncidentVO = null;

		if (imsExtIncident != null) {

			imsExtIncidentVO = new ImsExtIncidentVO();

			assign(imsExtIncident, imsExtIncidentVO);
		}

		return imsExtIncidentVO;
	}

	public static ImsExtIncident get(final ImsExtIncidentVO imsExtIncidentVO) {

		ImsExtIncident imsExtIncident = null;

		if (imsExtIncidentVO != null) {

			imsExtIncident = new ImsExtIncident();

			if (imsExtIncidentVO.getId() != null) {

				Key key = new Key(imsExtIncidentVO.getId());

				if (key.getClazz() == ImsExtIncident.class) {

					imsExtIncident.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsExtIncident.setAlias(imsExtIncidentVO.getAlias());
			imsExtIncident.setDescription((imsExtIncidentVO.getDescription()));
			imsExtIncident.setIncidentInitDate(imsExtIncidentVO.getIncidentInitDate());

			if (imsExtIncidentVO.getExtIncidentTypeId() != null) {

				Key key = new Key(imsExtIncidentVO.getExtIncidentTypeId());

				if (key.getClazz() == ImsExtIncidentType.class) {

					imsExtIncident.setExtIncidentTypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsExtIncidentVO.getExtIncidentType() != null) {

				ImsExtIncidentType imsExtIncidentType = ImsExtIncidentTypeHelper
						.get(imsExtIncidentVO.getExtIncidentType());

				if (imsExtIncidentType != null) {

					if (imsExtIncidentVO.getExtIncidentTypeId() == null) {

						imsExtIncident.setExtIncidentTypeId(imsExtIncidentType.getId());
					}
					imsExtIncident.setExtIncidentType(imsExtIncidentType);
				}
			}

			if (imsExtIncidentVO.getLocationId() != null) {

				Key key = new Key(imsExtIncidentVO.getLocationId());

				if (key.getClazz() == Location.class) {

					imsExtIncident.setLocationId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsExtIncidentVO.getLocation() != null) {

				Location location = LocationHelper.get(imsExtIncidentVO.getLocation());

				if (location != null) {

					if (imsExtIncidentVO.getLocationId() == null) {

						imsExtIncident.setLocationId(location.getId());
					}
					imsExtIncident.setLocation(location);
				}
			}

			if (imsExtIncidentVO.getExtIncidentValues() != null) {

				List<ImsExtIncidentValue> imsExtIncidentValues = new ArrayList<>();

				for (ImsExtIncidentValueVO imsExtIncidentValueVO : imsExtIncidentVO.getExtIncidentValues()) {

					ImsExtIncidentValue imsExtIncidentValue = ImsExtIncidentValueHelper.get(imsExtIncidentValueVO);

					imsExtIncidentValues.add(imsExtIncidentValue);
				}

				imsExtIncident.setExtIncidentValues(imsExtIncidentValues);
			}

		}

		return imsExtIncident;
	}

	public static void assign(final ImsExtIncident imsExtIncident, ImsExtIncidentVO imsExtIncidentVO) {

		if (ObjectUtils.allNotNull(imsExtIncident, imsExtIncidentVO)) {

			BasicElementHelper.fillBasicProperties(imsExtIncidentVO, imsExtIncident);
			
			imsExtIncidentVO.setIncidentInitDate(imsExtIncident.getIncidentInitDate());

			if (imsExtIncident.getLocationId() != null) {

				Location location = new Location();
				location.setId(imsExtIncident.getLocationId());
				imsExtIncidentVO.setLocationId(BasicElementHelper.getIdFromElement(location));
			}

			imsExtIncidentVO.setLocation(LocationHelper.get(imsExtIncident.getLocation()));

			if (imsExtIncident.getExtIncidentTypeId() != null) {

				ImsExtIncidentType imsExtIncidentType = new ImsExtIncidentType();
				imsExtIncidentType.setId(imsExtIncident.getExtIncidentTypeId());
				imsExtIncidentVO.setExtIncidentTypeId(BasicElementHelper.getIdFromElement(imsExtIncidentType));
			}

			imsExtIncidentVO.setExtIncidentType(ImsExtIncidentTypeHelper.get(imsExtIncident.getExtIncidentType()));

			if (imsExtIncident.getExtIncidentValues() != null) {

				List<ImsExtIncidentValueVO> imsExtIncidentValuesVO = new ArrayList<>();

				for (ImsExtIncidentValue imsExtIncidentValue : imsExtIncident.getExtIncidentValues()) {

					if (imsExtIncidentValue.getExtIncidentId() != null) {

						imsExtIncidentValuesVO.add(ImsExtIncidentValueHelper.get(imsExtIncidentValue));
					}
				}

				imsExtIncidentVO.setExtIncidentValues(imsExtIncidentValuesVO);
			}

		}
	}

}
