package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.data.core.model.SystemParamValue;
import com.revenga.rits.back.front.core.model.helper.SystemParamHelper;
import com.revenga.rits.back.front.core.model.vo.SystemParamVO;
import com.revenga.rits.back.service.graphql.type.SystemParamGraphql;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class SystemParamService implements QueryInterface<SystemParamVO> {

	@Override
	public List<SystemParamVO> getAll() {

		List<SystemParamVO> systemParams = new ArrayList<>();

		for (SystemParam systemParam : EntitiesManager.getInstance().getSystemParamRepository()
				.getAll(false)) {
			
			SystemParamValue systemParamValue = EntitiesManager.getInstance().getSystemParamValueRepository().get(systemParam.getId(), false);
			
			SystemParamVO systemParamVO = new SystemParamGraphql(systemParam);
			
			if(systemParamValue != null) {
				systemParamVO.setValue(systemParamValue.getValue());
			}
			
			systemParams.add(systemParamVO);			

			
		}

		return systemParams;
	}

	@Override
	public Optional<SystemParamVO> get(String systemParamId) {

		Optional<SystemParamVO> systemParamVO = null;

		Key key = new Key(systemParamId);
		
		if (key.getClazz() == SystemParam.class) {

			SystemParam systemParam = EntitiesManager.getInstance().getSystemParamRepository().get(Long.valueOf(key.getId(1)), false);

			if (systemParam != null) {

				systemParamVO = Optional.of(new SystemParamGraphql(systemParam));
				SystemParamValue systemParamValue = EntitiesManager.getInstance().getSystemParamValueRepository().get(systemParam.getId(), false);
				
				if(systemParamValue != null) {
					
					systemParamVO.ifPresent(param -> {
					    param.setValue(systemParamValue.getValue());    
					});
				}				

			}
		}
		return systemParamVO;
	}

	@Override
	public List<SystemParamVO> gets(List<String> paramsIds) {
		
		List<SystemParamVO> systemParamsVO = new ArrayList<SystemParamVO>();
		
		if (CollectionUtils.isEmpty(paramsIds)) {
			
			systemParamsVO = getAll();
			
		} else {

			for (String paramId : paramsIds) {

				try {

					Key key = new Key(paramId);

					if (key.getClazz() == SystemParam.class) {

						SystemParam systemParam = EntitiesManager.getInstance().getSystemParamRepository().get(Long.valueOf(key.getId(1)), false);

						if (systemParam != null) {
							
							systemParamsVO.add(SystemParamHelper.get(systemParam, 
																	 EntitiesManager.getInstance().getSystemParamValueRepository().get(systemParam.getId(), false)));
						}
					}

				} catch (Exception e) {

					log.error("The id does not correspond to the required object: SystemParam");
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

		return systemParamsVO;
	}

}
