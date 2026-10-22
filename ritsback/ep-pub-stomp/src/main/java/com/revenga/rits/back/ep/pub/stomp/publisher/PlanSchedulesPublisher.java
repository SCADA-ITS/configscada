package com.revenga.rits.back.ep.pub.stomp.publisher;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.messaging.simp.stomp.StompSession;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher.Topics.Topic;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanScheduleState;
import com.revenga.rits.back.data.core.model.PlanScheduleStateType;
import com.revenga.rits.back.data.core.model.PlanSchedules;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetPlanSchedulesPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetPlanSchedulesStatePublishCommand;
import com.revenga.rits.back.ep.pub.stomp.service.ElementCacheManager;
import com.revenga.rits.back.front.core.model.helper.PlanScheduleHelper;
import com.revenga.rits.back.front.core.model.helper.PlanScheduleStateTypeHelper;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.PlanScheduleStateTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanSchedulesVO;
import com.revenga.rits.back.front.core.model.vo.StompMessage;
import com.revenga.rits.back.pub.stomp.helper.StompMessageHelper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanSchedulesPublisher extends GenericPublisher {

	public PlanSchedulesPublisher(StompSession stompSession, Topic topic, ElementCacheManager elementCacheManager) {

		super(stompSession, topic, elementCacheManager);
	}

	@Override
	protected BasicElementVO prepare(Pair<Object, Message> queueElement) {

		BasicElementVO basicElementVO = null;

		try {

			if (queueElement != null && isAllowedMessage(queueElement.getKey())) {

				PlanSchedulesVO planScheduleVO = null;
				List<PlanSchedulesVO> plansSchedulesVO = new ArrayList<>();

				if (queueElement.getKey() instanceof SetPlanSchedulesPublishCommand) {

					SetPlanSchedulesPublishCommand setPlanSchedulesPublishCommand = (SetPlanSchedulesPublishCommand) queueElement
							.getKey();

					if (setPlanSchedulesPublishCommand != null) {

						List<PlanSchedules> planSchedules = setPlanSchedulesPublishCommand.getPlanSchedules();

						if (!CollectionUtils.isEmpty(planSchedules)) {

							for (PlanSchedules planSchedule : planSchedules) {

								if (planSchedule.getPlanId() != null) {
									
									Plan plan = new Plan();
									plan.setId(planSchedule.getPlanId());
									planSchedule.setPlan(plan);
								}

								planScheduleVO = PlanScheduleHelper.get(planSchedule);
								plansSchedulesVO.add(planScheduleVO);
							}
						}
					}

				} else if (queueElement.getKey() instanceof SetPlanSchedulesStatePublishCommand) {

					SetPlanSchedulesStatePublishCommand setPlanSchedulesStatePublishCommand = (SetPlanSchedulesStatePublishCommand) queueElement
							.getKey();

					if (setPlanSchedulesStatePublishCommand != null) {

						List<PlanScheduleState> planScheduleStates = setPlanSchedulesStatePublishCommand
								.getPlanScheduleStates();

						if (!CollectionUtils.isEmpty(planScheduleStates)) {

							for (PlanScheduleState planScheduleState : planScheduleStates) {

								PlanScheduleStateType planScheduleStateType = new PlanScheduleStateType();

								planScheduleStateType.setId(planScheduleState.getPlanScheduleStateTypeId());

								PlanScheduleStateTypeVO planScheduleStateTypeVO = PlanScheduleStateTypeHelper
										.get(planScheduleStateType);

								if (planScheduleStateTypeVO != null) {

									PlanSchedules planSchedule = new PlanSchedules();
									planSchedule.setId(planScheduleState.getPlanScheduleId());
									planScheduleVO = PlanScheduleHelper.get(planSchedule);
									planScheduleVO.setPlanScheduleStateType(planScheduleStateTypeVO);
									plansSchedulesVO.add(planScheduleVO);
								}
							}
						}
					}
				}

				if (plansSchedulesVO != null) {

					for (PlanSchedulesVO planSchedulesVO : plansSchedulesVO) {

						StompMessage stompMessage = StompMessageHelper
								.get(((Command) queueElement.getKey()).getAction().intValue(), null, planSchedulesVO);

						stompMessage.setManagementAreaIds(((Command) queueElement.getKey()).getManagementAreaIds());
						preparedElements.put(planSchedulesVO.getId(), stompMessage);
					}
				}
			}

		} catch (

		Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return basicElementVO;
	}

	private boolean isAllowedMessage(Object object) {

		return (object instanceof SetPlanSchedulesPublishCommand
				|| object instanceof SetPlanSchedulesStatePublishCommand);
	}

}
