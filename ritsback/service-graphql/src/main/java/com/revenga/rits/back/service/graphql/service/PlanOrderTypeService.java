package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.PlanOrderType;
import com.revenga.rits.back.front.core.model.vo.PlanOrderTypeVO;
import com.revenga.rits.back.service.graphql.type.PlanOrderTypeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PlanOrderTypeService implements QueryInterface<PlanOrderTypeVO> {
	
	@Override
	public List<PlanOrderTypeVO> getAll() {
		List<PlanOrderTypeVO> planOrderTypes = new ArrayList<>();

		for (PlanOrderType planOrderType : EntitiesManager.getInstance().getPlanOrderTypeRepository().getAll(false)) {

			planOrderTypes.add(new PlanOrderTypeGraphql(planOrderType));
		}

		return planOrderTypes;

	}

	@Override
	public Optional<PlanOrderTypeVO> get(String planOrderTypeId) {
		Optional<PlanOrderTypeVO> planOrderTypeVO = null;

		try {
			Key key = new Key(planOrderTypeId);

			if (key.getClazz() == PlanOrderType.class) {

				PlanOrderType planOrderType = EntitiesManager.getInstance().getPlanOrderTypeRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (planOrderType != null) {

					planOrderTypeVO = Optional.of(new PlanOrderTypeGraphql(planOrderType));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: PlanOrderType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return planOrderTypeVO;
	}

	@Override
	public List<PlanOrderTypeVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
