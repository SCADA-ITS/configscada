package com.revenga.rits.back.historical.manager.executor.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

import com.revenga.rits.back.adapter.lib.model.HistoricalElementParserV1;
import com.revenga.rits.back.adapter.lib.model.HistoricalElementParserV1.Fields.Field;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.historical.manager.executor.util.ExecutorUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProcessInfoService {

	public static final String ADAPTER = "adapter";

	public ProcessInfoService() {

	}
	
	private void launchProcessInfoJob(HistoricalElementParserV1 adapter, String cronExpression) {
		
		try {
			
			if (adapter != null) {
				
				if (!StringUtils.isEmpty(cronExpression)) {

					if (EntitiesManager.getInstance().getScheduler() == null) {

						start();
					}
					
					EntitiesManager.getInstance().getScheduler().getContext().put(this.getClass().getSimpleName(), this);
					createJob(ScheduledProcessInfoJob.class, adapter, cronExpression);

				} else {

					log.error("Not found cronProcessInfo");
				}
			}
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	private void createJob(Class<? extends Job> jobClass, HistoricalElementParserV1 adapter, String cronExpression) {

		try {
			
			JobDetail job = newJob(jobClass)
					.withIdentity("job" + jobClass.getSimpleName() + adapter.getElementTypeId(), "group" + jobClass.getSimpleName())
					.build();

			job.getJobDataMap().put(ADAPTER, adapter);

			if (!StringUtils.isEmpty(cronExpression)) {

				CronTrigger trigger = newTrigger().withIdentity("trigger" + jobClass.getSimpleName() + adapter.getElementTypeId(),
						"group" + jobClass.getSimpleName()).withSchedule(cronSchedule(cronExpression)).build();

				Date dt = EntitiesManager.getInstance().getScheduler().scheduleJob(job, trigger);

				log.info(job.getKey() + " has been scheduled to run at: " + dt + " and repeat based on expression: "
						+ trigger.getCronExpression());
			} else {

				log.debug(job.getKey() + " is not scheduled");
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	public boolean start() {

		Boolean ok = true;

		try {

			//EntitiesManager.getInstance().initQuartzScheduler();

			if (EntitiesManager.getInstance().getScheduler() != null
					&& !EntitiesManager.getInstance().getScheduler().isStarted()) {

				//EntitiesManager.getInstance().getScheduler().start();
			}

		} catch (SchedulerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			ok = false;
		}

		return ok;
	}

	public boolean insertHistoric(final HistoricalElementParserV1 adapter,
			final List<ElementTypeParam> elementTypeParams, final List<Element> elements,
			Map<Element, List<ElementValue>> processesData) {

		Boolean saveHistorc = true;

		if (elements != null && adapter != null && adapter.getTargetTable() != null
				&& adapter.getFieldElementId() != null && !CollectionUtils.isEmpty(elements)
				&& !CollectionUtils.isEmpty(elementTypeParams)) {

			Connection connection = null;
			StringBuilder insertTableQuery = new StringBuilder();
			PreparedStatement pstmt = null;
			String string = "";

			insertTableQuery.append("INSERT INTO " + adapter.getTargetTable());
			insertTableQuery.append(" ( " + adapter.getFieldElementId());

			if (adapter.getFieldTimestamp() != null) {

				insertTableQuery.append(", " + adapter.getFieldTimestamp());
			}
			
			boolean isFirstParam = true;

			for (Field field : adapter.getFields().getField()) {

				if (field != null && field.getElementTypeParam() != null) {

					ElementTypeParam elementTypeParam = elementTypeParams.stream().filter(
							e -> Long.valueOf(field.getElementTypeParam().getElementTypeParamId()).equals(e.getId())
									&& Long.valueOf(field.getElementTypeParam().getParamTypeId())
											.equals(e.getParamTypeId())
									&& Long.valueOf(adapter.getElementTypeId()).equals(e.getElementTypeId()))
							.findFirst().orElse(null);

					if (elementTypeParam != null) {

						insertTableQuery.append(", " + field.getName());
						if (isFirstParam) {
							
							string += "?";
							isFirstParam=false;
							
						} else {
							
							string += " ,?";
						}
						
					}
				}
			}

			insertTableQuery.append(") VALUES(?, CURRENT_TIMESTAMP, " + string+");");

			try {
				try {

					connection = DataSourceConnection.getInstance().getConnection();
					connection.setAutoCommit(false);
					pstmt = connection.prepareStatement(insertTableQuery.toString());

					for (Element element : elements) {

						int index = 1;

						if (element.getId() != null && CollectionUtils.isNotEmpty(processesData.get(element))) {
							
							List<ElementValue> elementElementValues = new ArrayList<>();

							// Save Values
							ExecutorUtil.setValue(pstmt, index++, element.getId().toString());
							//index++;

							/*
							if (now != null) {
								ExecutorUtil.setValue(pstmt, index++, now.toString());
							}
*/
							for (Field field : adapter.getFields().getField()) {

								if (field != null && field.getElementTypeParam() != null) {

									ElementTypeParam elementTypeParam = elementTypeParams.stream().filter(e -> Long
											.valueOf(field.getElementTypeParam().getElementTypeParamId())
											.equals(e.getId())
											&& Long.valueOf(field.getElementTypeParam().getParamTypeId())
													.equals(e.getParamTypeId())
											&& Long.valueOf(adapter.getElementTypeId()).equals(e.getElementTypeId()))
											.findFirst().orElse(null);

									if (elementTypeParam != null) {

										ElementValue elementValue = processesData.get(element).stream()
												.filter(e -> Long.valueOf(field.getElementTypeParam()
														.getElementTypeParamId())
												.equals(e.getElementTypeParamId())
												&& Long.valueOf(adapter.getElementTypeId()).equals(e.getElementTypeId())
												&& element.getId().equals(e.getElementId())).findFirst().orElse(null);

										if (elementValue != null) {

											elementElementValues.add(elementValue);
											ExecutorUtil.setValue(pstmt, index++, elementValue.getValue());

										} else {

											ExecutorUtil.setValue(pstmt, index++, null);
										}
									}
								}
							}

							boolean allValuesAreNullOrEmpty = (elementElementValues.stream()
									.filter(e -> !StringUtils.isEmpty(e.getValue())).findFirst().orElse(null) == null);

							if (adapter.isNoSaveEmptyRows() == null || !adapter.isNoSaveEmptyRows().booleanValue()
									|| !allValuesAreNullOrEmpty) {

								pstmt.executeUpdate();
							}
						}
						pstmt.clearParameters();
					}
					connection.commit();

				} catch (SQLException | IOException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					saveHistorc = false;
					connection.rollback();

				}
			} catch (SQLException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));

			} finally {

				try {

					if (pstmt != null) {
						pstmt.close();
					}

					if (connection != null) {
						connection.close();
					}

				} catch (SQLException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}

		} else {
			log.error("saveHistorc null value");
		}

		return saveHistorc;
	}

	public boolean init(final HistoricalElementParserV1 adapter, final List<ElementTypeParam> elementTypeParams, String cronExpression) {

		boolean ok = false;

		if (ExecutorUtil.existTable(adapter.getTargetTable())) {

			if (cronExpression != null) {

				launchProcessInfoJob(adapter, cronExpression);

				ok = true;

			} else {

				ok = true;
			}
		}
		return ok;
	}

	public boolean stop() {

		Boolean ok = true;

		if (EntitiesManager.getInstance().getScheduler() != null) {

			try {

				log.debug("Shutdown scheduled jobs ...");

				EntitiesManager.getInstance().getScheduler().shutdown(true);

				log.debug("Shutdown Complete");
				log.debug("Executed " + EntitiesManager.getInstance().getScheduler().getMetaData().getNumberOfJobsExecuted() + " jobs.");

			} catch (SchedulerException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		return ok;
	}
}
