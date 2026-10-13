package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentGroup;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentTypeHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeVO;
import com.revenga.rits.back.service.graphql.type.ImsIncidentTypeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ImsIncidentTypeService implements QueryInterface<ImsIncidentTypeVO>, MutationInterface<ImsIncidentTypeVO> {

	/**
	 * 
	 * @param incidentGroupId
	 * @return
	 */
	public List<ImsIncidentTypeVO> getImsIncidentTypesByGroup(String incidentGroupId) {
		
		List<ImsIncidentTypeVO> imsIncidentTypeVO = null;

		Key keyIncidentGroupId = new Key(incidentGroupId);

		if (keyIncidentGroupId.getClazz() == ImsIncidentGroup.class) {

			imsIncidentTypeVO = new ArrayList<>();
			
			for (ImsIncidentType imsIncidentType : EntitiesManager.getInstance()
									.getImsIncidentTypeRepository().getByIncidentGroup(Long.valueOf(keyIncidentGroupId.getId(1)), false) ) {

				imsIncidentTypeVO.add(new ImsIncidentTypeGraphql(imsIncidentType));
			}
		}

		return imsIncidentTypeVO;		
	}
	
	/**
	 * 
	 */
	@Override
	public List<ImsIncidentTypeVO> getAll() {
		List<ImsIncidentTypeVO> imsIncidentTypesVO = null;

		imsIncidentTypesVO = new ArrayList<>();
			
		for (ImsIncidentType imsIncidentType : EntitiesManager.getInstance().getImsIncidentTypeRepository().getAll(false) ) {

			imsIncidentTypesVO.add(new ImsIncidentTypeGraphql(imsIncidentType));
		}

		return imsIncidentTypesVO;	
	}

	/**
	 * 
	 */
	@Override
	public Optional<ImsIncidentTypeVO> get(String incidentTypeId) {
		
		Optional<ImsIncidentTypeVO> imsIncidentTypeVO = null;
		try {
			Key key = new Key(incidentTypeId);
			
			if (key.getClazz() == ImsIncidentType.class) {

				ImsIncidentType imsIncidentType = EntitiesManager.getInstance().getImsIncidentTypeRepository().get(Long.valueOf(key.getId(1)), false);

				if (imsIncidentType != null) {

					imsIncidentTypeVO = Optional.of(new ImsIncidentTypeGraphql(imsIncidentType));
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ImsIncidentType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return imsIncidentTypeVO;
	}
	
	/**
	 * 
	 */
	@Override
	public List<ImsIncidentTypeVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public Optional<ImsIncidentTypeVO> create(ImsIncidentTypeVO imsIncidentTypeVO) {

		Optional<ImsIncidentTypeVO> resp = null;

		if (imsIncidentTypeVO != null) {

			ImsIncidentType imsIncidentType = ImsIncidentTypeHelper.get(imsIncidentTypeVO);

			ImsIncidentType imsIncidentTypeCreated = EntitiesManager.getInstance().getImsIncidentTypeRepository().create(imsIncidentType);

			if (imsIncidentTypeCreated != null) {

				resp = Optional.of(new ImsIncidentTypeGraphql(imsIncidentTypeCreated));
			}
		}
		return resp;
	}

	/**
	 * 
	 */
	@Override
	public Optional<ImsIncidentTypeVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public Optional<ImsIncidentTypeVO> update(ImsIncidentTypeVO imsIncidentTypeVO) {

		Optional<ImsIncidentTypeVO> resp = null;

		if (imsIncidentTypeVO != null) {

			ImsIncidentType imsIncidentType = ImsIncidentTypeHelper.get(imsIncidentTypeVO);

			ImsIncidentType imsIncidentTypeUpdated = EntitiesManager.getInstance().getImsIncidentTypeRepository().update(imsIncidentType);

			if (imsIncidentTypeUpdated != null) {

				resp = Optional.of(new ImsIncidentTypeGraphql(imsIncidentTypeUpdated));
			}
		}
		return resp;
	}

	/**
	 * 
	 */
	@Override
	public Optional<ImsIncidentTypeVO> delete(String imsIncidentTypeId) {

		Optional<ImsIncidentTypeVO> resp = null;

		if (imsIncidentTypeId != null) {

			ImsIncidentTypeVO imsIncidentTypeVO = new ImsIncidentTypeVO();
			imsIncidentTypeVO.setId(imsIncidentTypeId);

			ImsIncidentType imsIncidentType = ImsIncidentTypeHelper.get(imsIncidentTypeVO);

			ImsIncidentType imsIncidentTypeDeleted = EntitiesManager.getInstance().getImsIncidentTypeRepository().delete(imsIncidentType);

			if (imsIncidentTypeDeleted != null) {

				resp = Optional.of(new ImsIncidentTypeGraphql(imsIncidentTypeDeleted));
			}
		}

		return resp;
	}
}
