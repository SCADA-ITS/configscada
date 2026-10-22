package com.revenga.rits.back.equipment.manager.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.equipment.manager.data.ElementData;
import com.revenga.rits.back.equipment.manager.executor.coordinator.AlarmDeactivationCoordinator;
import com.revenga.rits.back.equipment.manager.executor.coordinator.ElementStateCoordinator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThreadManagerAlarms implements Runnable {
	
	private static final long THREAD_SLEEP_MS = 1000;

	private Map<String, Alarm> alarmMinTimeMap;
	private Map<String, Alarm> alarmThresholdMap;
	private ExecutorService executors;
	private List<Method> procedures;
	
	private final Object lock = new Object();

	boolean stop;
	boolean alive;

	public ThreadManagerAlarms() {
		
		super();
		
		this.alarmMinTimeMap = Collections.synchronizedMap(new HashMap<>());
		this.alarmThresholdMap = Collections.synchronizedMap(new HashMap<>());
		this.procedures = getProcedures();
		this.executors = Executors.newFixedThreadPool(this.procedures.size());
	}

	public Alarm removeFromMinTimeMap(AlarmConfig alarmConfig, Element element) {

		Alarm updateAlarm = null;

		String key = getKeyForMap(alarmConfig, element);
		
		if (key != null) {
			
			updateAlarm = alarmMinTimeMap.remove(key);
		}

		return updateAlarm;
	}

	public boolean addInMinTimeMap(Alarm alarm) {

		boolean add = false;

		if (alarm != null && alarm.getAlarmConfig() != null && alarm.getAlarmConfig().getMinTimeAlarmed() != null
				&& alarm.getAlarmConfig().getMinTimeAlarmed() > 0 && alarm.getSourceElement() != null) {

			String key = getKeyForMap(alarm.getAlarmConfig(), alarm.getSourceElement());
			
			if (!alarmMinTimeMap.containsKey(key)) {
				
				alarmMinTimeMap.put(key, alarm);
				
				add = true;
			}
		}

		return add;
	}
	
	public Alarm removeFromThresholdMap(AlarmConfig alarmConfig, Element element) {

		Alarm updateAlarm = null;

		String key = getKeyForMap(alarmConfig, element);
		
		if (key != null) {
			
			updateAlarm = alarmThresholdMap.remove(key);
		}

		return updateAlarm;
	}

	public boolean addInThresholdMap(Alarm alarm) {

		boolean add = false;

		if (alarm != null && alarm.getAlarmConfig() != null && alarm.getAlarmConfig().getThresholdTime() != null
				&& alarm.getAlarmConfig().getThresholdTime() > 0 && alarm.getSourceElement() != null) {

			String key = getKeyForMap(alarm.getAlarmConfig(), alarm.getSourceElement());
			
			if (!alarmThresholdMap.containsKey(key)) {
				
				alarmThresholdMap.put(key, alarm);
				
				add = true;
			}
		}

		return add;
	}

	public void stop() {

		this.stop = true;
	}

	@Override
	public void run() {
		
		log.info(Thread.currentThread().getName() + " is running...");
		this.alive = true;

		long lastProcessedTime = Instant.now().toEpochMilli();
		
		synchronized (lock) {

			try {
				
				while (!stop) {

					long currentTime = Instant.now().toEpochMilli();

					if (currentTime - lastProcessedTime >= THREAD_SLEEP_MS) {
						
						execProcedures();
						
						lastProcessedTime = currentTime;

					} else {
						
						long waitTime = THREAD_SLEEP_MS - (currentTime - lastProcessedTime);
						
						if (waitTime > 0) {
						
							lock.wait(waitTime);
						}
					}
				}

			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();
			}
		}

		this.alive = false;

		log.info(Thread.currentThread().getName() + " is stopped");
	}
	
	private void execProcedures() {
		
		if (!CollectionUtils.isEmpty(this.procedures)) {
			
			List<Future<?>> futures = new ArrayList<>();
		
			for (Method procedure : procedures) {
				
				Runnable task = () -> {
					
					try {
						
						procedure.setAccessible(true);						
						procedure.invoke(this);
						
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						
						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				};
				
				futures.add(this.executors.submit(task));
			}

			// A) Await all runnables to be done (blocking)
			for (Future<?> future : futures) {
				
				try {
					
					future.get(); // get will block until the future is done
					
				} catch (InterruptedException | ExecutionException e) {
					
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	private List<Method> getProcedures() {
		
		List<Method> procs = new ArrayList<>();

		try {
			
			CollectionUtils.addAll(procs,
					ThreadManagerAlarms.class.getDeclaredMethod("procAlarmMinTimeMap"),
					ThreadManagerAlarms.class.getDeclaredMethod("procAlarmThresholdMap"));
			
		} catch (NoSuchMethodException | SecurityException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}	
		
		return procs;
	}
	
	@SuppressWarnings("unused")
	private void procAlarmMinTimeMap() {
		
		long currentTime = Instant.now().toEpochMilli();
		
		synchronized (alarmMinTimeMap) {

			List<String> alarmsToDel = new ArrayList<>();

			for (Entry<String, Alarm> entry : alarmMinTimeMap.entrySet()) {

				Alarm alarm = entry.getValue();

				if (alarm.getActivation() != null && alarm.getAlarmConfig() != null
						&& alarm.getAlarmConfig().getMinTimeAlarmed() != null && alarm.getActivation()
								+ (alarm.getAlarmConfig().getMinTimeAlarmed() * 1000) < currentTime) {

					alarmsToDel.add(entry.getKey());
				}
			}

			alarmsToDel.forEach(alarmToDel -> {

				Alarm alarm = alarmMinTimeMap.get(alarmToDel);

				AlarmDeactivationCoordinator alarmDeactivationCoordinator = new AlarmDeactivationCoordinator(
						alarm.getTargetElement());

				Map<Long, Object> mapProps = new HashMap<>();

				mapProps.put(AlarmDeactivationCoordinator.PROP_ALARM, alarm);

				alarmDeactivationCoordinator.update(mapProps);

				alarmMinTimeMap.remove(alarmToDel);
			});
		}
	}
	
	@SuppressWarnings("unused")
	private void procAlarmThresholdMap() {
		
		long currentTime = Instant.now().toEpochMilli();
		
		synchronized (alarmThresholdMap) {

			List<Alarm> alarmsToActivate = new ArrayList<>();

			for (Entry<String, Alarm> entry : alarmThresholdMap.entrySet()) {

				Alarm alarm = entry.getValue();

				if (alarm.getActivation() != null && alarm.getAlarmConfig() != null
						&& alarm.getAlarmConfig().getThresholdTime() != null
						&& alarm.getAlarmConfig().getThresholdTime() > 0 && (alarm.getActivation()
								+ (alarm.getAlarmConfig().getThresholdTime() * 1000)) < currentTime) {

					log.debug(alarm.getActivation() + (alarm.getAlarmConfig().getThresholdTime() * 1000));
					log.debug(currentTime);
					alarmsToActivate.add(alarm);
				}
			}
			
			if (!CollectionUtils.isEmpty(alarmsToActivate)) {
			
				alarmsToActivate.forEach(alarmToActivate -> {
					
					ElementData elementData = EntitiesManager.getInstance().getRootData().getElementData(alarmToActivate.getTargetElement().getElementTypeId());
					
					if (elementData.createAlarm(alarmToActivate) != null) {
						
						alarmThresholdMap.remove(getKeyForMap(alarmToActivate.getAlarmConfig(), alarmToActivate.getTargetElement()));
						
						(new ElementStateCoordinator(alarmToActivate.getTargetElement())).calculateState();
					}
				});
			}
		}
	}
	
	private String getKeyForMap(AlarmConfig alarmConfig, Element element) {

		String key = null;

		if (alarmConfig != null && element != null) {

			key = (new Key(alarmConfig)).getSimpleValue() + (new Key(element)).getSimpleValue();
		}

		return key;
	}

}
