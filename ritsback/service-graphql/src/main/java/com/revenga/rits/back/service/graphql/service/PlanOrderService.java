package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.PlanOrder;
import com.revenga.rits.back.data.core.model.PlanOrderStateType;
import com.revenga.rits.back.front.core.model.helper.PlanOrderHelper;
import com.revenga.rits.back.front.core.model.vo.PlanOrderStateTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderVO;
import com.revenga.rits.back.service.graphql.type.PlanOrderGraphql;
import com.revenga.rits.back.service.graphql.type.PlanOrderStateTypeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PlanOrderService implements QueryInterface<PlanOrderVO>, MutationInterface<PlanOrderVO> {
	
	@Override
	public List<PlanOrderVO> getAll() {
		
		List<PlanOrderVO> planOrders = new ArrayList<>();

		for (PlanOrder planOrder : EntitiesManager.getInstance().getPlanOrderRepository().getAll(false)) {

			planOrders.add(new PlanOrderGraphql(planOrder));
		}

		return planOrders;
	}

	@Override
	public Optional<PlanOrderVO> get(String planOrderId) {
		
		Optional<PlanOrderVO> planOrderVO = null;
		
		try {
			Key key = new Key(planOrderId);

			if (key.getClazz() == PlanOrder.class) {

				PlanOrder planOrder = EntitiesManager.getInstance().getPlanOrderRepository().get(Long.valueOf(key.getId(1)), Long.valueOf(key.getId(2)), false);

				if (planOrder != null) {

					planOrderVO = Optional.of(new PlanOrderGraphql(planOrder));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: PlanOrder");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return planOrderVO;
	}

	@Override
	public List<PlanOrderVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<PlanOrderStateTypeVO> getPlanOrderStateTypes() {

		List<PlanOrderStateTypeVO> planOrderStateTypeVO = new ArrayList<>();

		List<PlanOrderStateType> planOrderStateTypes = EntitiesManager.getInstance().getPlanOrderStateTypeRepository()
				.getAll(false);

		if (!CollectionUtils.isEmpty(planOrderStateTypes)) {

			for (PlanOrderStateType planOrderStateType : planOrderStateTypes) {

				planOrderStateTypeVO.add(new PlanOrderStateTypeGraphql(planOrderStateType));
			}
		}

		return planOrderStateTypeVO;
	}

	@Override
	public Optional<PlanOrderVO> create(PlanOrderVO planOrderVO) {
		Optional<PlanOrderVO> resp = null;

		if (planOrderVO != null) {

			PlanOrder planOrder = PlanOrderHelper.get(planOrderVO);

			PlanOrder planOrderCreated = EntitiesManager.getInstance().getPlanOrderRepository().create(planOrder);

			if (planOrderCreated != null) {

				resp = Optional.of(new PlanOrderGraphql(planOrderCreated));
			}
		}
		return resp;

	}

	@Override
	public Optional<PlanOrderVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PlanOrderVO> update(PlanOrderVO planOrderVO) {
		Optional<PlanOrderVO> resp = null;

		if (planOrderVO != null) {

			PlanOrder planOrder = PlanOrderHelper.get(planOrderVO);

			PlanOrder planOrderCreated = EntitiesManager.getInstance().getPlanOrderRepository().update(planOrder);

			if (planOrderCreated != null) {

				resp = Optional.of(new PlanOrderGraphql(planOrderCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<PlanOrderVO> delete(String planOrderId) {

		Optional<PlanOrderVO> resp = null;

		if (planOrderId != null) {

			PlanOrderVO planOrderVO = new PlanOrderVO();
			planOrderVO.setId(planOrderId);

			PlanOrder planOrder = PlanOrderHelper.get(planOrderVO);

			PlanOrder planOrderDeleted = EntitiesManager.getInstance().getPlanOrderRepository().delete(planOrder);

			if (planOrderDeleted != null) {

				resp = Optional.of(new PlanOrderGraphql(planOrderDeleted));
			}
		}

		return resp;
	}
}
