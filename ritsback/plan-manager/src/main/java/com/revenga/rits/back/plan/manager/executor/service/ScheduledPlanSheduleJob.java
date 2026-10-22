package com.revenga.rits.back.plan.manager.executor.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.plan.manager.helper.PlanScheduleHelper;
import com.revenga.rits.back.plan.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScheduledPlanSheduleJob implements Job {

	private PlanService planService;

	public ScheduledPlanSheduleJob() {

		super();
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		log.debug("- EXECUTE SCHEDULED PLAY PLAN -");

		SchedulerContext schedulerContext;

		try {
			schedulerContext = context.getScheduler().getContext();
			planService = (PlanService) schedulerContext.get(PlanService.class.getSimpleName());

			JobDataMap data = context.getJobDetail().getJobDataMap();

			Long planId = data.getLong(PlanScheduleHelper.PLAN_ID);

			if (planId != null) {

				Plan plan = EntitiesManager.getInstance().getPlan(planId);
				
				if(plan != null){
					
					AuditHelper.Audit(LogType.LOG_TYPE_PLANS, LogSubtype.LOG_SUBTYPE_EXECUTE_PLAN, User.USER_SYSTEM,
							LogLevel.DEBUG, plan.getAlias());
					
					planService.playPlanAction(planId, null);
				}
				else{
					log.error("Plan not " + planId +" found");
				}

			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

	}
}
