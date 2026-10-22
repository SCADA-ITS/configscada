package com.revenga.rits.back.plan.manager.helper;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.revenga.rits.back.data.core.model.PlanScheduleStateType;
import com.revenga.rits.back.data.core.model.PlanSchedules;
import com.revenga.rits.back.plan.manager.executor.service.ScheduledPlanSheduleJob;
import com.revenga.rits.back.plan.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlanScheduleHelper {

	public static final String PLAN_ID = "plan_id";

	private PlanScheduleHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void initPlanSchedule(Scheduler scheduler) {

		List<PlanSchedules> planSchedules = EntitiesManager.getInstance().getAllPlanSchedule();

		if (!CollectionUtils.isEmpty(planSchedules)) {

			for (PlanSchedules planSchedule : planSchedules) {

				if (planSchedule.getId() != null) {

					PlanScheduleHelper.playPlanSchedule(planSchedule, scheduler);
					EntitiesManager.getInstance().putStateInPlanSchedule(planSchedule.getId(),
							PlanScheduleStateType.PLAN_SCHEDULE_STATE_TYPE_RUNNING);
				}
			}
		}
	}

	public static void deInitPlanSchedule(Scheduler scheduler) {

		List<PlanSchedules> planSchedules = EntitiesManager.getInstance().getAllPlanSchedule();

		if (!CollectionUtils.isEmpty(planSchedules)) {

			for (PlanSchedules planSchedule : planSchedules) {

				if (planSchedule.getId() != null) {

					PlanScheduleHelper.stopPlanSchedule(planSchedule, scheduler);
					EntitiesManager.getInstance().putStateInPlanSchedule(planSchedule.getId(),
							PlanScheduleStateType.PLAN_SCHEDULE_STATE_TYPE_PAUSE);

				}
			}
		}
	}

	public static boolean playPlanSchedule(PlanSchedules planSchedule, Scheduler scheduler) {

		boolean bOk = false;

		try {
			if (planSchedule != null
					&& ObjectUtils.allNotNull(planSchedule.getPlanId(), planSchedule.getId(), scheduler)) {

				JobDetail job = newJob(ScheduledPlanSheduleJob.class)
						.withIdentity("job" + ScheduledPlanSheduleJob.class.getSimpleName() + planSchedule.getId(),
								"group" + ScheduledPlanSheduleJob.class.getSimpleName())
						.build();

				job.getJobDataMap().put(PLAN_ID, planSchedule.getPlanId());

				if (!StringUtils.isEmpty(planSchedule.getCronExpression())) {

					CronTrigger trigger = newTrigger()
							.withIdentity(
									"trigger" + ScheduledPlanSheduleJob.class.getSimpleName() + planSchedule.getId(),
									"group" + ScheduledPlanSheduleJob.class.getSimpleName())
							.withSchedule(cronSchedule(planSchedule.getCronExpression())).build();

					Date dt = scheduler.scheduleJob(job, trigger);

					log.info(job.getKey() + " has been scheduled to run at: " + dt + " and repeat based on expression: "
							+ trigger.getCronExpression());
					bOk = true;
				} else {

					log.debug(job.getKey() + " is not scheduled");
				}
			}

		} catch (SchedulerException | RuntimeException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		return bOk;

	}

	public static boolean stopPlanSchedule(PlanSchedules planSchedule, Scheduler scheduler) {

		boolean bOk = false;

		if (planSchedule != null && ObjectUtils.allNotNull(scheduler, planSchedule.getId())) {

			JobKey jobKey = new JobKey("job" + ScheduledPlanSheduleJob.class.getSimpleName() + planSchedule.getId(),
					"group" + ScheduledPlanSheduleJob.class.getSimpleName());

			try {

				if (scheduler.deleteJob(jobKey)) {

					log.info(jobKey + ": deleted");
					bOk = true;

				} else {

					log.debug(jobKey + " is not deleted");
				}

			} catch (SchedulerException | RuntimeException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		return bOk;
	}
}
