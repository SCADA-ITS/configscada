package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentTypeStretchHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeStretchVO;
import com.revenga.rits.back.service.graphql.type.ImsIncidentTypeStretchGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ImsIncidentTypeStretchService
		implements QueryInterface<ImsIncidentTypeStretchVO>, MutationInterface<ImsIncidentTypeStretchVO> {

	@Override
	public List<ImsIncidentTypeStretchVO> getAll() {

		List<ImsIncidentTypeStretchVO> imsIncidentTypeStretchs = new ArrayList<>();

		for (ImsIncidentTypeStretch imsIncidentTypeStretch : EntitiesManager.getInstance().getImsIncidentTypeStretchRepository()
				.getAll(false)) {

			imsIncidentTypeStretchs.add(new ImsIncidentTypeStretchGraphql(imsIncidentTypeStretch));
		}

		return imsIncidentTypeStretchs;
	}
	
	public List<ImsIncidentTypeStretchVO> getsByIncidentType(String incidentTypeId) {

		List<ImsIncidentTypeStretchVO> imsIncidentTypeStretchsByIncidentType = new ArrayList<>();

		try {
			
			Key keyIncidentType = new Key(incidentTypeId);

			if (keyIncidentType.getClazz() == ImsIncidentType.class) {

				List<ImsIncidentTypeStretch> imsIncidentTypeStretchs = EntitiesManager.getInstance().getImsIncidentTypeStretchRepository()
						.getsByIncidentType(Long.valueOf(keyIncidentType.getId(1)), false);

				if (!CollectionUtils.isEmpty(imsIncidentTypeStretchs)) {

					imsIncidentTypeStretchs.forEach(
							e -> imsIncidentTypeStretchsByIncidentType.add(new ImsIncidentTypeStretchGraphql(e)));
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: imsIncidentTypeStretch");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return imsIncidentTypeStretchsByIncidentType;
	}

	@Override
	public Optional<ImsIncidentTypeStretchVO> get(String idItem) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Optional<ImsIncidentTypeStretchVO> get(String stretchId, String incidentTypeId) {

		Optional<ImsIncidentTypeStretchVO> imsIncidentTypeStretchVO = null;

		try {
			
			Key keyStretch = new Key(stretchId);
			Key keyIncidentType = new Key(incidentTypeId);

			if (keyStretch.getClazz() == Stretch.class && keyIncidentType.getClazz() == ImsIncidentType.class) {

				ImsIncidentTypeStretch imsIncidentTypeStretch = EntitiesManager.getInstance().getImsIncidentTypeStretchRepository()
						.get(Long.valueOf(keyIncidentType.getId(1)), Long.valueOf(keyStretch.getId(1)), false);

				if (imsIncidentTypeStretch != null) {

					imsIncidentTypeStretchVO = Optional.of(new ImsIncidentTypeStretchGraphql(imsIncidentTypeStretch));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: imsIncidentTypeStretch");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return imsIncidentTypeStretchVO;
	}

	@Override
	public List<ImsIncidentTypeStretchVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ImsIncidentTypeStretchVO> create(ImsIncidentTypeStretchVO imsIncidentTypeStretchVO) {

		Optional<ImsIncidentTypeStretchVO> resp = null;

		if (imsIncidentTypeStretchVO != null) {

			ImsIncidentTypeStretch imsIncidentTypeStretch = ImsIncidentTypeStretchHelper.get(imsIncidentTypeStretchVO);

			ImsIncidentTypeStretch imsIncidentTypeStretchCreated = EntitiesManager.getInstance()
					.getImsIncidentTypeStretchRepository().create(imsIncidentTypeStretch);

			if (imsIncidentTypeStretchCreated != null) {

				resp = Optional.of(new ImsIncidentTypeStretchGraphql(imsIncidentTypeStretchCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<ImsIncidentTypeStretchVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ImsIncidentTypeStretchVO> update(ImsIncidentTypeStretchVO imsIncidentTypeStretchVO) {

		Optional<ImsIncidentTypeStretchVO> resp = null;

		if (imsIncidentTypeStretchVO != null) {

			ImsIncidentTypeStretch imsIncidentTypeStretch = ImsIncidentTypeStretchHelper.get(imsIncidentTypeStretchVO);

			ImsIncidentTypeStretch imsIncidentTypeStretchUpdated = EntitiesManager.getInstance()
					.getImsIncidentTypeStretchRepository().update(imsIncidentTypeStretch);

			if (imsIncidentTypeStretchUpdated != null) {

				resp = Optional.of(new ImsIncidentTypeStretchGraphql(imsIncidentTypeStretchUpdated));
			}
		}
		return resp;
	}

	@Override
	public Optional<ImsIncidentTypeStretchVO> delete(String imsIncidentTypeStretchId) {

		Optional<ImsIncidentTypeStretchVO> resp = null;

		if (imsIncidentTypeStretchId != null) {

			ImsIncidentTypeStretchVO imsIncidentTypeStretchVO = new ImsIncidentTypeStretchVO();
			imsIncidentTypeStretchVO.setId(imsIncidentTypeStretchId);

			ImsIncidentTypeStretch imsIncidentTypeStretch = ImsIncidentTypeStretchHelper.get(imsIncidentTypeStretchVO);

			ImsIncidentTypeStretch imsIncidentTypeStretchDeleted = EntitiesManager.getInstance()
					.getImsIncidentTypeStretchRepository().delete(imsIncidentTypeStretch);

			if (imsIncidentTypeStretchDeleted != null) {

				resp = Optional.of(new ImsIncidentTypeStretchGraphql(imsIncidentTypeStretchDeleted));
			}
		}

		return resp;
	}

}
