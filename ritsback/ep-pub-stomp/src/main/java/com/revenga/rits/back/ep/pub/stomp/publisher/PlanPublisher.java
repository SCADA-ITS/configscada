package com.revenga.rits.back.ep.pub.stomp.publisher;

import java.util.ArrayList;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.messaging.simp.stomp.StompSession;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1.Publishers.Publisher.Topics.Topic;
import com.revenga.rits.back.cache.core.manager.RedisConnection.Message;
import com.revenga.rits.back.data.core.model.PlanState;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetPlanStatePublishCommand;
import com.revenga.rits.back.ep.pub.stomp.service.ElementCacheManager;
import com.revenga.rits.back.front.core.model.helper.PlanHelper;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;
import com.revenga.rits.back.front.core.model.vo.PlanOrderVO;
import com.revenga.rits.back.front.core.model.vo.PlanVO;
import com.revenga.rits.back.front.core.model.vo.StompMessage;
import com.revenga.rits.back.pub.stomp.helper.StompMessageHelper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanPublisher extends GenericPublisher {

	public PlanPublisher(StompSession stompSession, Topic topic, ElementCacheManager elementCacheManager) {

		super(stompSession, topic, elementCacheManager);
	}

	@Override
	protected BasicElementVO prepare(Pair<Object, Message> queueElement) {

		BasicElementVO basicElementVO = null;

		try {

			if (queueElement != null && queueElement.getKey() instanceof SetPlanStatePublishCommand) {

				SetPlanStatePublishCommand command = (SetPlanStatePublishCommand) queueElement.getKey();

				if (!CollectionUtils.isEmpty(command.getPlanStates())) {

					for (PlanState planState : command.getPlanStates()) {

						if (planState.getPlanId() != null) {

							StompMessage stompMessage = null;

							PlanVO planVO = PlanHelper.get(planState);

							stompMessage = preparedElements.get(planVO.getId());

							if (stompMessage != null && !CollectionUtils.isEmpty(stompMessage.getBasicElementsVO())) {

								/*  Update plan */
								
								PlanVO oldPlanVO = (PlanVO) stompMessage.getBasicElementsVO().get(0);

								if (planVO.getPlanStateType() != null) {

									oldPlanVO.setPlanStateType(planVO.getPlanStateType());
								}

								if (planVO.getCurrentPlanOrder() != null) {

									oldPlanVO.setCurrentPlanOrder(planVO.getCurrentPlanOrder());
								}

								/*  Update plan orders*/
								if (!CollectionUtils.isEmpty(planVO.getPlanOrders())) {

									for (PlanOrderVO planOrderVO : planVO.getPlanOrders()) {

										PlanOrderVO oldPlanOrderVO = null;

										if (!CollectionUtils.isEmpty(oldPlanVO.getPlanOrders())) {

											oldPlanOrderVO = oldPlanVO.getPlanOrders().stream()
													.filter(be -> be.getId().equals(planOrderVO.getId())).findFirst()
													.orElse(null);
										}

										if (oldPlanOrderVO != null) {

											if (planOrderVO.getPlanOrderStateType() != null) {
												oldPlanOrderVO
														.setPlanOrderStateType(planOrderVO.getPlanOrderStateType());
											}

											if (planOrderVO.getNTimes() != null) {
												oldPlanOrderVO.setNTimes(planOrderVO.getNTimes());
											}
										} else {

											if (oldPlanVO.getPlanOrders() == null) {

												oldPlanVO.setPlanOrders(new ArrayList<>());
											}
											
											oldPlanVO.getPlanOrders().add(planOrderVO);
										}
									}
								}

								StompMessageHelper.updateMessage(stompMessage);

							} else {
								stompMessage = StompMessageHelper.get(Command.ACTION_UPDATE, null, planVO);
								stompMessage.setManagementAreaIds(command.getManagementAreaIds());
							}
							
							preparedElements.put(planVO.getId(), stompMessage);
						}
					}
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return basicElementVO;
	}
}