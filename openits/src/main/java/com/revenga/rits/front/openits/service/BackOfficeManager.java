package com.revenga.rits.front.openits.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.front.openits.app.AppInfra;
import com.revenga.rits.front.openits.backoffice.BackOfficeProcessor;
import com.revenga.rits.front.openits.backoffice.BackOfficeProcessor.ShowData;
import com.revenga.rits.front.openits.model.backoffice.Basic;
import com.revenga.rits.front.openits.model.backoffice.CronTask;
import com.revenga.rits.front.openits.model.backoffice.Table;
import com.revenga.rits.front.openits.scheduler.ScheduledBackOfficeTaskJob;
import com.revenga.rits.front.openits.util.ConversionUtil;
import com.revenga.rits.front.openits.util.GroovyManager;
import com.revenga.rits.front.openitsdao.model.BackOffice;
import com.revenga.rits.front.openitsdao.service.BackOfficeService;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BackOfficeManager extends FrontManager {

	private static final String BACKOFFICE_ENABLED = AppInfra.getProperty("backoffice.enabled");
	private static final String BACKOFFICE_SHOW_SQL = AppInfra.getProperty("backoffice.show_sql");
	private static final String BACKOFFICE_TRACE_TABLE = AppInfra.getProperty("backoffice.trace_table");
	private static final String FILE_BASEDIR = "file.basedir";
	
	private Map<Pair<Integer, String>, Map<String, String>> translations = new HashMap<>();
	private Map<Pair<Integer, String>, Table> tables = new HashMap<>();
	
	private Map<String, Pair<BackOffice, CronTask>> jobsMap = new HashMap<>();
	
	@Autowired
	BackOfficeService backOfficeService;
	
	@Autowired
	@Getter
	GroovyManager groovyManager;
	
	@Autowired
	private Environment env;
	
	private List<BackOffice> backOffices;
	
	@Getter
	private boolean showSql;
	
	@Getter
	private boolean traceTable;
	
	@Getter
	private String baseDir;
	
	@PostConstruct
	private void afterLoad() {
		
		this.enabled = StringUtils.equals(BACKOFFICE_ENABLED, Boolean.TRUE.toString()); 
		this.showSql = StringUtils.equals(BACKOFFICE_SHOW_SQL, Boolean.TRUE.toString());
		this.traceTable = StringUtils.equals(BACKOFFICE_TRACE_TABLE, Boolean.TRUE.toString());
		
		File directory = new File(env.getProperty(FILE_BASEDIR));
		
		if (directory.exists() && directory.isDirectory()) {
			
			this.baseDir = directory.getAbsolutePath();
		}
	}
	
	public Table getTable(BackOffice backOffice, String tableName) {
		
		return this.tables.get(new ImmutablePair<>(backOffice.getId(), tableName));
	}
	
	public boolean containsTable(BackOffice backOffice, String tableName) {
		
		return this.tables.containsKey(new ImmutablePair<>(backOffice.getId(), tableName));
	}
	
	public void putTable(BackOffice backOffice, Table table) {
		
		this.tables.put(new ImmutablePair<>(backOffice.getId(), table.getName()), table);
	}
	
	public void clearTables(BackOffice backOffice) {
		
		Set<Pair<Integer, String>> keysToRemove = this.tables.keySet().stream().filter(e -> e.getKey().equals(backOffice.getId())).collect(Collectors.toSet());
		
		if (!CollectionUtils.isEmpty(keysToRemove)) {
			
			this.tables.keySet().removeAll(keysToRemove);
		}
	}
	
	public void translateBasic(BackOffice backOffice, Connection conn, String localeCode, Basic basic) throws SQLException {
		
		basic.setLabelTranslated(translate(backOffice, conn, localeCode, basic.getLabel()));
		basic.setLabelDescriptionTranslated(translate(backOffice, conn, localeCode, basic.getLabelDescription()));
	}
	
	public String translate(BackOffice backOffice, Connection conn, String localeCode, String label) throws SQLException {
		
		String translation = label;
		
		if (!StringUtils.isEmpty(localeCode) && !StringUtils.isEmpty(label) && label.indexOf("LBL_") == 0) {
			
			Map<String, String> translationsByLocale = this.translations.get(new ImmutablePair<>(backOffice.getId(), localeCode));
			
			if (CollectionUtils.isEmpty(translationsByLocale)) {
				
				loadI18nLabels(localeCode, conn, backOffice);
				
				translationsByLocale = this.translations.get(new ImmutablePair<>(backOffice.getId(), localeCode));
			}
			
			if (!CollectionUtils.isEmpty(translationsByLocale)) {
				
				translation = translationsByLocale.get(label);
			}
		}
		
		return translation;
	}
	
	public BackOffice getBackOffice(String jobKeyName) {
		
		Pair<BackOffice, CronTask> pair = this.jobsMap.get(jobKeyName); 
		
		return pair != null ? pair.getKey() : null;
	}
	
	public CronTask getCronTask(String jobKeyName) {
		
		Pair<BackOffice, CronTask> pair = this.jobsMap.get(jobKeyName); 
		
		return pair != null ? pair.getValue() : null;
	}
	
	private void loadI18nLabels(String localeCode, Connection conn, BackOffice backOffice) throws SQLException {
		
		final String QRY_I18N_LABELS = String.format("SELECT label, translation FROM %s.sg_i18n_labels WHERE locale_code = ?", backOffice.getSchema());
		
		if (!StringUtils.isEmpty(localeCode)) {
			
			Map<String, String> translationsByLocale = this.translations.get(new ImmutablePair<>(backOffice.getId(), localeCode));
			
			if (translationsByLocale == null) {
				
				try (PreparedStatement stmt = conn.prepareStatement(QRY_I18N_LABELS)) {
					
					stmt.setString(1, localeCode);
					
					ResultSet rs = stmt.executeQuery();
					
					if (rs.next() ) {
						
						translationsByLocale = new HashMap<>();
						
						this.translations.put(new ImmutablePair<>(backOffice.getId(), localeCode), translationsByLocale);
					 
						do {
						
							translationsByLocale.put(rs.getString("label"), rs.getString("translation"));
							
					    } while (rs.next());
					}
				}
			}
		}
	}

	@Override
	protected void load() {
		
		this.backOffices = backOfficeService.getAll();
		
		if (!CollectionUtils.isEmpty(this.backOffices)) {
			
			for (BackOffice backOffice : this.backOffices) {
				
				BackOfficeProcessor backOfficeProcessor = new BackOfficeProcessor(groovyManager, backOfficeService, this, null, ShowData.NONE);
				
				Instant timeRef = Instant.now();
				
				List<Table> t = backOfficeProcessor.getAll(backOffice.getId());
				
				log.info("Cached backoffice " + backOffice.getId() + " [" + backOffice.getName() + "]: "
						+ (t != null ? t.size() : "0") + " ("
						+ Duration.between(timeRef, Instant.now()).toMillis() + " ms." + ")");
			}
			
			initQuartzScheduler();
			
			launchScheduled();
			
			// All of the jobs have been added to the scheduler, but none of the jobs
		    // will run until the scheduler has been started
			try {
				if (!scheduler.isStarted()) {
					
					scheduler.start();
				}
			} catch (SchedulerException e1) {
				log.error(e1.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e1));
			}
		}
	}
	
	private void initQuartzScheduler() {
		
		try {
			
			SchedulerFactory sf = new StdSchedulerFactory();
			scheduler = sf.getScheduler();
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	protected void launchScheduled() {
		
		try {
			
			if (!CollectionUtils.isEmpty(this.backOffices)) {
				
				scheduler.getContext().put(this.getClass().getSimpleName(), this);
				
				for (BackOffice backOffice : this.backOffices) {
					
					launchSheduledBackOffice(backOffice);
				}
			}
			
		} catch (SchedulerException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void launchSheduledBackOffice(BackOffice backOffice) {
		
		List<CronTask> cronTasks = getCronTasks(backOffice);
		
		if (!CollectionUtils.isEmpty(cronTasks)) {
			
			for (CronTask cronTask : cronTasks) {
				
				createJob(backOffice, cronTask);
			}
		}
	}
	
	private List<CronTask> getCronTasks(BackOffice backOffice) {
		
		List<CronTask> cronTasks = new ArrayList<>();
		
		final String QRY_TASKS = String.format("SELECT id, name, cron_expression, groovy, params FROM %s.sg_metadata_tasks", backOffice.getSchema());
		
		try (Connection conn = DriverManager.getConnection(backOffice.getJdbc(), backOffice.getUser(),
				AES.decrypt(backOffice.getPassword()))) {
			
			try (PreparedStatement stmt = conn.prepareStatement(QRY_TASKS)) {
				
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					
					CronTask cronTask = new CronTask();
					
					cronTask.setId(rs.getInt("id"));
					cronTask.setName(rs.getString("name"));
					cronTask.setCronExpression(rs.getString("cron_expression"));
					cronTask.setGroovy(rs.getString("groovy"));
					
					String params = rs.getString("params");
					
					if (!StringUtils.isEmpty(params)) {
						
						cronTask.setParams(ConversionUtil.stringToMap(params));
					}
					
					cronTasks.add(cronTask);
				}
			}
		}
		catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return cronTasks;
	}
	
	private void createJob(BackOffice backOffice, CronTask cronTask) {
			
		if (!StringUtils.isEmpty(cronTask.getCronExpression())) {
			
			try {
			
				Class<? extends Job> jobClass = ScheduledBackOfficeTaskJob.class;			
				// jobs can be scheduled before sched.start() has been called
				// job 1 will run every cron value
				
				JobDetail job = newJob(jobClass)
						.withIdentity("job" + jobClass.getSimpleName() + cronTask.hashCode(), "group" + jobClass.getSimpleName() + cronTask.hashCode())
						.withDescription(String.valueOf(cronTask.hashCode())).build();
				
				CronTrigger trigger = newTrigger().withIdentity("trigger" + jobClass.getSimpleName() + cronTask.hashCode(), "group" + jobClass.getSimpleName() + cronTask.hashCode())
						.withSchedule(cronSchedule(cronTask.getCronExpression())).build();
				
				Date dt = scheduler.scheduleJob(job, trigger);
				
				jobsMap.put(job.getKey().getName(), new ImmutablePair<>(backOffice, cronTask));
				
				log.debug(job.getKey() + " has been scheduled to run at: " + dt + " and repeat based on expression: "
						+ trigger.getCronExpression());
			
			} catch (SchedulerException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		else {
			
			log.debug(cronTask.getName() + " is not scheduled");
		}
	}

	@Override
	protected void afterStart() {
		// TODO Auto-generated method stub
		
	}
}
