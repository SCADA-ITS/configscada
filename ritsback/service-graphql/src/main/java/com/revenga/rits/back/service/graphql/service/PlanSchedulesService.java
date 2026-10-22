package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanScheduleStateType;
import com.revenga.rits.back.data.core.model.PlanSchedules;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetPlanScheduleActionsCommand;
import com.revenga.rits.back.data.core.model.command.SetPlanScheduleActionsCommand.Action;
import com.revenga.rits.back.data.core.model.command.SetPlanSchedulesPublishCommand;
import com.revenga.rits.back.front.core.model.helper.PlanScheduleHelper;
import com.revenga.rits.back.front.core.model.vo.PlanScheduleStateTypeVO;
import com.revenga.rits.back.front.core.model.vo.PlanSchedulesVO;
import com.revenga.rits.back.service.graphql.type.PlanScheduleGraphql;
import com.revenga.rits.back.service.graphql.type.PlanScheduleStateTypeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PlanSchedulesService implements QueryInterface<PlanSchedulesVO>, MutationInterface<PlanSchedulesVO> {

	public List<PlanSchedulesVO> getAll(Long managementAreaId) {

		List<PlanSchedulesVO> planSchedules = new ArrayList<>();

		for (PlanSchedules planSchedule : EntitiesManager.getInstance().getPlanSchedulesRepository().getAll(false)) {

			if (checkManagementArea(planSchedule, managementAreaId)) {

				planSchedules.add(new PlanScheduleGraphql(planSchedule));
			}

		}

		return planSchedules;
	}

	@Override
	public Optional<PlanSchedulesVO> get(String planSchedulesKey) {

		Optional<PlanSchedulesVO> planSchedulesVO = null;
		try {
			Key key = new Key(planSchedulesKey);

			if (key.getClazz() == PlanSchedules.class) {

				PlanSchedules planSchedules = EntitiesManager.getInstance().getPlanSchedulesRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (planSchedules != null) {

					planSchedulesVO = Optional.of(new PlanScheduleGraphql(planSchedules));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: PlanSchedules");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return planSchedulesVO;
	}

	public List<PlanSchedulesVO> getByPlan(String planId) {

		List<PlanSchedulesVO> planSchedulesVO = null;

		try {
			Key key = new Key(planId);

			if (key.getClazz() == Plan.class) {

				List<PlanSchedules> planSchedules = EntitiesManager.getInstance().getPlanSchedulesRepository()
						.getsByPlan(Long.valueOf(key.getId(1)), false);

				if (!CollectionUtils.isEmpty(planSchedules)) {

					planSchedulesVO = new ArrayList<>();

					for (PlanSchedules planSchedule : planSchedules) {

						planSchedulesVO.add(new PlanScheduleGraphql(planSchedule));
					}
				}

			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: PlanSchedule");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return planSchedulesVO;
	}

	public List<PlanScheduleStateTypeVO> getPlanScheduleStateTypes() {

		List<PlanScheduleStateTypeVO> planScheduleStateTypeVO = new ArrayList<>();

		List<PlanScheduleStateType> planScheduleStateTypes = EntitiesManager.getInstance()
				.getPlanScheduleStateTypeRepository().getAll(false);

		if (!CollectionUtils.isEmpty(planScheduleStateTypes)) {

			for (PlanScheduleStateType planScheduleStateType : planScheduleStateTypes) {

				planScheduleStateTypeVO.add(new PlanScheduleStateTypeGraphql(planScheduleStateType));
			}
		}

		return planScheduleStateTypeVO;
	}

	@Override
	public Optional<PlanSchedulesVO> create(PlanSchedulesVO planSchedulesVO) {

		Optional<PlanSchedulesVO> resp = null;

		if (planSchedulesVO != null) {

			PlanSchedules planSchedule = PlanScheduleHelper.get(planSchedulesVO);

			PlanSchedules planScheduleCreated = EntitiesManager.getInstance().getPlanSchedulesRepository()
					.create(planSchedule);

			if (planScheduleCreated != null) {

				resp = Optional.of(new PlanScheduleGraphql(planScheduleCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<PlanSchedulesVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PlanSchedulesVO> update(PlanSchedulesVO planSchedulesVO) {

		Optional<PlanSchedulesVO> resp = null;

		if (planSchedulesVO != null) {

			PlanSchedules planSchedule = PlanScheduleHelper.get(planSchedulesVO);

			PlanSchedules planScheduleCreated = EntitiesManager.getInstance().getPlanSchedulesRepository()
					.update(planSchedule);

			if (planScheduleCreated != null) {

				resp = Optional.of(new PlanScheduleGraphql(planScheduleCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<PlanSchedulesVO> delete(String planSchedulesId) {

		Optional<PlanSchedulesVO> resp = null;

		if (planSchedulesId != null) {

			PlanSchedulesVO planSchedulesVO = new PlanSchedulesVO();
			planSchedulesVO.setId(planSchedulesId);

			PlanSchedules planSchedule = PlanScheduleHelper.get(planSchedulesVO);

			PlanSchedules planScheduleDeleted = EntitiesManager.getInstance().getPlanSchedulesRepository()
					.delete(planSchedule);

			if (planScheduleDeleted != null) {

				resp = Optional.of(new PlanScheduleGraphql(planScheduleDeleted));
			}
		}

		return resp;
	}

	public List<PlanSchedulesVO> sendPlanScheduleActions(final List<PlanSchedulesVO> planSchedules) {

		List<PlanSchedulesVO> planSchedulesVO = null;

		SetPlanScheduleActionsCommand setPlanScheduleCommand = null;
		SetPlanSchedulesPublishCommand setPlanSchedulesPublishCommand = null;

		if (!CollectionUtils.isEmpty(planSchedules)) {

			Long managementAreaIdOld = null;

			for (PlanSchedulesVO planScheduleVO : planSchedules) {

				PlanSchedules planSchedule = PlanScheduleHelper.get(planScheduleVO);
				Long managementAreaId = null;

				if (planSchedule != null) {

					switch (planScheduleVO.getAction()) {

					case STOP:

						if (ObjectUtils.allNotNull(planSchedule.getId())) {

							planSchedule = EntitiesManager.getInstance().getPlanSchedulesRepository()
									.delete(planSchedule);

							if (planSchedule != null) {

								managementAreaId = getManagementArea(planSchedule);
								
								if (planSchedulesVO == null) {

									planSchedulesVO = new ArrayList<>();
								}

								planSchedulesVO.add(new PlanScheduleGraphql(planSchedule));

								if (setPlanScheduleCommand == null) {

									setPlanScheduleCommand = new SetPlanScheduleActionsCommand();
								}

								if (setPlanSchedulesPublishCommand == null) {

									setPlanSchedulesPublishCommand = new SetPlanSchedulesPublishCommand();
									managementAreaIdOld = managementAreaId;
									
									if(managementAreaId != null) {
										
										setPlanSchedulesPublishCommand
										.setManagementAreaIds(managementAreaToArray(managementAreaId));
									}

								}

								setPlanScheduleCommand.addPlanScheduleAction(planSchedule,
										Action.PLAN_SCHEDULE_STOPPED);

								if ((setPlanSchedulesPublishCommand.getAction() == null
										|| setPlanSchedulesPublishCommand.getAction()
												.intValue() == Command.ACTION_DELETE)
										&& checkManagementArea(managementAreaId, managementAreaIdOld)) {

									setPlanSchedulesPublishCommand.setAction(Command.ACTION_DELETE);
									setPlanSchedulesPublishCommand.addPlanSchedule(planSchedule);

								} else {

									EntitiesManager.getInstance().sendCommand(setPlanSchedulesPublishCommand);

									setPlanSchedulesPublishCommand = new SetPlanSchedulesPublishCommand();
									managementAreaIdOld = managementAreaId;
									
									if(managementAreaId != null) {
										
										setPlanSchedulesPublishCommand
										.setManagementAreaIds(managementAreaToArray(managementAreaId));
									}

									setPlanSchedulesPublishCommand.setAction(Command.ACTION_DELETE);
									setPlanSchedulesPublishCommand.addPlanSchedule(planSchedule);
								}
							}
						}
						break;

					case START:

						if (ObjectUtils.allNotNull(planSchedule.getPlanId(), planSchedule.getCronExpression())) {

							planSchedule = EntitiesManager.getInstance().getPlanSchedulesRepository()
									.create(planSchedule);

							if (planSchedule != null) {
			
								managementAreaId = getManagementArea(planSchedule);
								
								if (planSchedulesVO == null) {

									planSchedulesVO = new ArrayList<>();
								}

								planSchedulesVO.add(new PlanScheduleGraphql(planSchedule));

								if (setPlanScheduleCommand == null) {

									setPlanScheduleCommand = new SetPlanScheduleActionsCommand();
								}

								if (setPlanSchedulesPublishCommand == null) {

									setPlanSchedulesPublishCommand = new SetPlanSchedulesPublishCommand();
									managementAreaIdOld = managementAreaId;

									if(managementAreaId != null) {
										setPlanSchedulesPublishCommand
										.setManagementAreaIds(managementAreaToArray(managementAreaId));
									}
								}

								setPlanScheduleCommand.addPlanScheduleAction(planSchedule, Action.PLAN_SCHEDULE_ON);

								if ((setPlanSchedulesPublishCommand.getAction() == null
										|| setPlanSchedulesPublishCommand.getAction()
												.intValue() == Command.ACTION_CREATE)
										&& checkManagementArea(managementAreaId, managementAreaIdOld)) {

									setPlanSchedulesPublishCommand.setAction(Command.ACTION_CREATE);
									setPlanSchedulesPublishCommand.addPlanSchedule(planSchedule);
									
								} else {

									EntitiesManager.getInstance().sendCommand(setPlanSchedulesPublishCommand);

									setPlanSchedulesPublishCommand = new SetPlanSchedulesPublishCommand();
									managementAreaIdOld = managementAreaId;

									if(managementAreaId != null) {
										setPlanSchedulesPublishCommand
										.setManagementAreaIds(managementAreaToArray(managementAreaId));
									}

									setPlanSchedulesPublishCommand.setAction(Command.ACTION_CREATE);
									setPlanSchedulesPublishCommand.addPlanSchedule(planSchedule);
								}
							}
						}
						break;

					case PAUSE:

						if (ObjectUtils.allNotNull(planSchedule.getId())) {

							if (planSchedulesVO == null) {

								planSchedulesVO = new ArrayList<>();
							}

							planSchedulesVO.add(new PlanScheduleGraphql(planSchedule));

							if (setPlanScheduleCommand == null) {

								setPlanScheduleCommand = new SetPlanScheduleActionsCommand();
							}

							setPlanScheduleCommand.addPlanScheduleAction(planSchedule, Action.PLAN_SCHEDULE_PAUSE);
						}
						break;

					case PLAY:

						if (ObjectUtils.allNotNull(planSchedule.getId())) {

							if (planSchedulesVO == null) {

								planSchedulesVO = new ArrayList<>();
							}

							planSchedulesVO.add(new PlanScheduleGraphql(planSchedule));

							if (setPlanScheduleCommand == null) {

								setPlanScheduleCommand = new SetPlanScheduleActionsCommand();
							}

							setPlanScheduleCommand.addPlanScheduleAction(planSchedule, Action.PLAN_SCHEDULE_ON);
						}
						break;

					case RESET:

						if (ObjectUtils.allNotNull(planSchedule.getPlanId(), planSchedule.getId(),
								planSchedule.getCronExpression())) {

							planSchedule = EntitiesManager.getInstance().getPlanSchedulesRepository()
									.update(planSchedule);

							if (planSchedule != null) {

								managementAreaId = getManagementArea(planSchedule);
								
								if (planSchedulesVO == null) {

									planSchedulesVO = new ArrayList<>();
								}

								planSchedulesVO.add(new PlanScheduleGraphql(planSchedule));

								if (setPlanScheduleCommand == null) {

									setPlanScheduleCommand = new SetPlanScheduleActionsCommand();
								}

								if (setPlanSchedulesPublishCommand == null) {

									setPlanSchedulesPublishCommand = new SetPlanSchedulesPublishCommand();
									managementAreaIdOld = managementAreaId;
									
									if(managementAreaId != null) {
										setPlanSchedulesPublishCommand
										.setManagementAreaIds(managementAreaToArray(managementAreaId));
									}
								}

								setPlanScheduleCommand.addPlanScheduleAction(planSchedule, Action.PLAN_SCHEDULE_RESET);

								if ((setPlanSchedulesPublishCommand.getAction() == null || setPlanSchedulesPublishCommand
										.getAction().intValue() == Command.ACTION_UPDATE)
									&& checkManagementArea(managementAreaId, managementAreaIdOld)) {

									setPlanSchedulesPublishCommand.setAction(Command.ACTION_UPDATE);
									setPlanSchedulesPublishCommand.addPlanSchedule(planSchedule);
									
								} else {

									EntitiesManager.getInstance().sendCommand(setPlanSchedulesPublishCommand);

									setPlanSchedulesPublishCommand = new SetPlanSchedulesPublishCommand();
									managementAreaIdOld = managementAreaId;

									if(managementAreaId != null) {
										setPlanSchedulesPublishCommand
										.setManagementAreaIds(managementAreaToArray(managementAreaId));
									};

									setPlanSchedulesPublishCommand.setAction(Command.ACTION_UPDATE);
									setPlanSchedulesPublishCommand.addPlanSchedule(planSchedule);
								}
							}
						}

						break;

					default:
						log.error("Unsupported action");
						break;
					}
				}
			}

			if (setPlanScheduleCommand != null) {

				EntitiesManager.getInstance().sendCommand(setPlanScheduleCommand);

			}

			if (setPlanSchedulesPublishCommand != null) {

				EntitiesManager.getInstance().sendCommand(setPlanSchedulesPublishCommand);

			}
		}

		return planSchedulesVO;
	}

	@Override
	public List<PlanSchedulesVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PlanSchedulesVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean checkManagementArea(final PlanSchedules planSchedule, final Long managementAreaId) {

		boolean resp = false;

		if (planSchedule != null && planSchedule.getPlanId() != null) {

			Plan plan = EntitiesManager.getInstance().getPlanRepository().get(planSchedule.getPlanId(), false);

			if (plan != null
					&& (plan.getManagementAreaId() == null || plan.getManagementAreaId().equals(managementAreaId))) {

				resp = true;

			}
		}
		return resp;
	}

	private List<Long> managementAreaToArray(final Long managementAreaId) {

		return managementAreaId != null ? Arrays.asList(managementAreaId) : null;

	}

	private Long getManagementArea(final PlanSchedules planSchedule) {
		
		Long managementAreaId = null;

		if (planSchedule != null) {
			Long planId = null;

			if (planSchedule.getPlanId() != null) {

				planId = planSchedule.getPlanId();

			} else {
				PlanSchedules schedule = EntitiesManager.getInstance().getPlanSchedulesRepository()
						.get(planSchedule.getId(), false);

				if (schedule != null) {

					planId = schedule.getPlanId();
				}
			}

			if (planId != null) {
				
				Plan plan = EntitiesManager.getInstance().getPlanRepository().get(planId, false);
				managementAreaId = plan != null ? plan.getManagementAreaId() : null;
			}
		}

		return managementAreaId;
	}
	
	private boolean checkManagementArea(final Long managementAreaId1, final Long managementAreaId2){
		
		return (managementAreaId1 == null && managementAreaId2 == null) || (managementAreaId1 != null && managementAreaId1.equals(managementAreaId2));
	}
}
