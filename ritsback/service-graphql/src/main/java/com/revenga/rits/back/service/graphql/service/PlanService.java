package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanStateType;
import com.revenga.rits.back.data.core.model.command.SetPlanActionsCommand;
import com.revenga.rits.back.front.core.model.helper.PlanHelper;
import com.revenga.rits.back.front.core.model.helper.command.SetPlanActionsCommandHelper;
import com.revenga.rits.back.front.core.model.vo.PlanActionVO;
import com.revenga.rits.back.front.core.model.vo.PlanActionVO.PlanAction;
import com.revenga.rits.back.front.core.model.vo.PlanStateTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.type.PlanGraphql;
import com.revenga.rits.back.service.graphql.type.PlanStateTypeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PlanService implements QueryInterface<PlanVO>, MutationInterface<PlanVO> {

	@Override
	public Optional<PlanVO> get(String planKey) {

		Optional<PlanVO> planVO = Optional.empty();
		try {
			Key key = new Key(planKey);

			if (key.getClazz() == Plan.class) {

				Plan plan = EntitiesManager.getInstance().getPlanRepository().get(Long.valueOf(key.getId(1)), false);

				if (plan != null) {

					MapRequests mapRequests = new MapRequests(MapRequests.Mode.GET);

					planVO = Optional.of(new PlanGraphql(plan, mapRequests));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Plan");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return planVO;
	}

	public List<PlanVO> getAll(Long managementAreaId) {

		List<PlanVO> plansVO = new ArrayList<>();

		List<Plan> plans = EntitiesManager.getInstance().getPlanRepository().getAll(false);

		if (!CollectionUtils.isEmpty(plans)) {

			MapRequests mapRequests = new MapRequests(MapRequests.Mode.GET_ALL);

			for (Plan plan : plans) {

				if (checkManagementArea(plan, managementAreaId)) {
					
					plansVO.add(new PlanGraphql(plan, mapRequests));
				}
			}
		}
		return plansVO;

	}

	public List<PlanStateTypeVO> getPlanStateTypes() {

		List<PlanStateTypeVO> planStateTypeVO = new ArrayList<>();

		List<PlanStateType> planStateTypes = EntitiesManager.getInstance().getPlanStateTypeRepository().getAll(false);

		if (!CollectionUtils.isEmpty(planStateTypes)) {

			for (PlanStateType planStateType : planStateTypes) {

				planStateTypeVO.add(new PlanStateTypeGraphql(planStateType));
			}
		}

		return planStateTypeVO;
	}


	public Optional<PlanVO> create(PlanVO planVO, Long managementAreaId) {

		Optional<PlanVO> resp = Optional.empty();

		if (planVO != null) {

			Plan plan = PlanHelper.get(planVO);
			plan.setManagementAreaId(managementAreaId);

			Plan planCreated = EntitiesManager.getInstance().getPlanRepository().create(plan);

			if (planCreated != null) {

				resp = Optional.of(new PlanGraphql(planCreated));
			}
		}
		return resp;

	}

	@Override
	public Optional<PlanVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PlanVO> update(PlanVO planVO) {
		Optional<PlanVO> resp = Optional.empty();

		if (planVO != null) {

			Plan plan = PlanHelper.get(planVO);

			Plan planCreated = EntitiesManager.getInstance().getPlanRepository().update(plan);

			if (planCreated != null) {

				resp = Optional.of(new PlanGraphql(planCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<PlanVO> delete(String planId) {

		Optional<PlanVO> resp = Optional.empty();

		if (planId != null) {

			PlanVO planVO = new PlanVO();
			planVO.setId(planId);

			Plan plan = PlanHelper.get(planVO);

			Plan planDeleted = EntitiesManager.getInstance().getPlanRepository().delete(plan);

			if (planDeleted != null) {

				resp = Optional.of(new PlanGraphql(planDeleted));
			}
		}

		return resp;
	}

	public Boolean sendPlayPlan(List<String> planIds) {

		if (CollectionUtils.isEmpty(planIds)) {

			return false;
		}

		SetPlanActionsCommand setPlanActionsCommand = SetPlanActionsCommandHelper.get(planIds, PlanAction.PLAY);

		if (setPlanActionsCommand != null) {

			EntitiesManager.getInstance().sendCommand(setPlanActionsCommand);

		} else {

			return false;
		}

		return true;
	}

	public Boolean sendPlanActions(List<PlanActionVO> planActions) {

		if (CollectionUtils.isEmpty(planActions)) {

			return false;
		}

		SetPlanActionsCommand setPlanActionsCommand = SetPlanActionsCommandHelper.get(planActions);

		if (setPlanActionsCommand != null) {

			EntitiesManager.getInstance().sendCommand(setPlanActionsCommand);

		} else {

			return false;
		}

		return true;
	}

	@Override
	public List<PlanVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlanVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PlanVO> create(PlanVO item) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean checkManagementArea(final Plan plan, final Long managementAreaId) {

		return plan.getManagementAreaId() == null || plan.getManagementAreaId().equals(managementAreaId);
	}
}
