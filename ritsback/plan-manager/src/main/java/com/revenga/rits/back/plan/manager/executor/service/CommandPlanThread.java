package com.revenga.rits.back.plan.manager.executor.service;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.plan.manager.service.EntitiesManager;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommandPlanThread implements Runnable {

	private PlanService planService;
	@Getter
	@Setter
	private List<Long> planIdList;

	private boolean stop;
	private boolean alive;

	@Getter
	private Long nextPlanState;

	public synchronized void setNextPlanState(Long nextPlanState) {

		this.nextPlanState = nextPlanState;
	}

	public CommandPlanThread(PlanService planService) {

		super();
		this.planService = planService;
	}

	@Override
	public void run() {

		try {

			log.info(Thread.currentThread().getName() + " is running");

			while (!stop) {

				if (!planIdList.isEmpty() && planIdList.get(0) != null) {

					Long planId = planIdList.get(0);

					Plan plan = EntitiesManager.getInstance().getPlanWithOrder(planId);

					if (plan != null) {

						setNextPlanState(null);
						planService.playPlan(plan, this, 0);

					} else {
						log.error("Plan not found");
					}
					planIdList.remove(planIdList.get(0));
				}

				Thread.sleep(1);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		} finally {

			this.setAlive(false);

			log.info(Thread.currentThread().getName() + " is stopped");
		}
	}

	public void stop() {

		this.stop = true;
	}

	public boolean isAlive() {

		return alive;
	}

	public void setAlive(boolean alive) {

		this.alive = alive;
	}

	public boolean getStop() {

		return this.stop;
	}
}
