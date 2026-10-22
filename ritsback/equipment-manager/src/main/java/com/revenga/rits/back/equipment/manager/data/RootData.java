package com.revenga.rits.back.equipment.manager.data;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.cache.core.helper.CacheModelHelper;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RootData extends EntityData<ElementType, Object> {
	
	private static final long MAX_TIME_STOP_THREAD = 5000;
	public enum ThreadDataWriterInfo {
		
		PERSISTED, DELETED, OMITTED, DEQUEUED, QUEUED
	}
	
	public enum ThreadDataWriterStat {
		
		WORKING, ISALIVE
	}

	private Map<Long, Pair<ElementType, ElementData>> elementTypesMap = new HashMap<>();
	
	private Map<Class<?>, ThreadDataGeneric> threads = new HashMap<>();

	public RootData(EntitiesManager em) {

		super(em);
	}
	
	public ThreadDataGeneric getThread(Class<? extends ThreadDataGeneric> threadClass) {
		
		if (!MapUtils.isEmpty(threads)) {
			
			return threads.get(threadClass);
		}
		
		return null;
	}
	
	@Override
	public void load() {

		ThreadDataWriter threadDataWriter = new ThreadDataWriter();
		ThreadGenericData threadGenericData = new ThreadGenericData(this.em);
		ThreadDBSyncAlarms threadDBSyncAlarms = new ThreadDBSyncAlarms(this);
		ThreadSyncAlarmsFiltered threadSyncAlarmsFiltered = new ThreadSyncAlarmsFiltered(this);
		
		this.threads.put(ThreadDataWriter.class, threadDataWriter);
		this.threads.put(ThreadGenericData.class, threadGenericData);
		this.threads.put(ThreadDBSyncAlarms.class, threadDBSyncAlarms);
		this.threads.put(ThreadSyncAlarmsFiltered.class, threadSyncAlarmsFiltered);
		
		// Load and fill entities
		(new RootDataLoader(this.em, this.elementTypesMap, threadDataWriter, threadGenericData)).load();
	}
	
	public void fill(BasicElement basicElement) {
		
		ThreadDataWriter threadDataWriter = (ThreadDataWriter) this.threads.get(ThreadDataWriter.class);
		ThreadGenericData threadGenericData = (ThreadGenericData) this.threads.get(ThreadGenericData.class);
		
		// re-fill entities
		(new RootDataLoader(this.em, this.elementTypesMap, threadDataWriter, threadGenericData)).fill(basicElement);
	}
	
	public void loadElement(Element element) {
		
		ThreadDataWriter threadDataWriter = (ThreadDataWriter) this.threads.get(ThreadDataWriter.class);
		ThreadGenericData threadGenericData = (ThreadGenericData) this.threads.get(ThreadGenericData.class);
		
		(new RootDataLoader(this.em, this.elementTypesMap, threadDataWriter, threadGenericData)).load(element);
	}
	
	public void updateElement(Element sourceElement) {
		
		ThreadDataWriter threadDataWriter = (ThreadDataWriter) this.threads.get(ThreadDataWriter.class);
		ThreadGenericData threadGenericData = (ThreadGenericData) this.threads.get(ThreadGenericData.class);
		
		if (sourceElement != null) {
			
			Element targetElement = getElement(sourceElement.getElementTypeId(), sourceElement.getId());
			
			if (targetElement != null) {
				
				CacheModelHelper.updateBasicElement(sourceElement, targetElement);
				
				(new RootDataLoader(this.em, this.elementTypesMap, threadDataWriter, threadGenericData)).load(targetElement);
			}
		}
	}
	
	public void deleteElement(Element element) {
		
		ThreadDataWriter threadDataWriter = (ThreadDataWriter) this.threads.get(ThreadDataWriter.class);
		ThreadGenericData threadGenericData = (ThreadGenericData) this.threads.get(ThreadGenericData.class);
		
		(new RootDataLoader(this.em, this.elementTypesMap, threadDataWriter, threadGenericData)).delete(element);
	}
	
	public void start() {
		
		ThreadDataWriter threadDataWriter = (ThreadDataWriter) this.threads.get(ThreadDataWriter.class);
		ThreadGenericData threadGenericData = (ThreadGenericData) this.threads.get(ThreadGenericData.class);
		ThreadDBSyncAlarms threadDBSyncAlarms = (ThreadDBSyncAlarms) this.threads.get(ThreadDBSyncAlarms.class);
		ThreadSyncAlarmsFiltered threadSyncAlarmsFiltered = (ThreadSyncAlarmsFiltered) this.threads.get(ThreadSyncAlarmsFiltered.class);
		
		// Start thread to write data to redis
		if (threadDataWriter != null) {
			
			(new Thread(threadDataWriter, ThreadDataWriter.class.getSimpleName())).start();
		}
		
		// Start thread to maintenance of GenericData
		if (threadGenericData != null) {
		
			threadGenericData.setElements(getElements());
			(new Thread(threadGenericData, ThreadGenericData.class.getSimpleName())).start();
		}

		// Start thread to persist alarms to RT db
		if (threadDBSyncAlarms != null) {
			
			(new Thread(threadDBSyncAlarms, ThreadDBSyncAlarms.class.getSimpleName())).start();
		}
		
		// Start thread to sync filtered alarms
		if (threadSyncAlarmsFiltered != null) {
		
			(new Thread(threadSyncAlarmsFiltered, ThreadSyncAlarmsFiltered.class.getSimpleName())).start();
		}
	}
	
	@Override
	public void load(Object entity) {
		// TODO Auto-generated method stub
		
	}
	
	public void stop() {
		
		Instant instantInit;
		
		try {

			for (Map.Entry<Class<?>, ThreadDataGeneric> entry : this.threads.entrySet()) {

				ThreadDataGeneric threadDataGeneric = entry.getValue();

				instantInit = Instant.now();

				if (threadDataGeneric != null && threadDataGeneric.isAlive()) {

					threadDataGeneric.stop();

					while (threadDataGeneric.isAlive()
							&& Duration.between(instantInit, Instant.now()).toMillis() < MAX_TIME_STOP_THREAD) {

						Thread.sleep(0, 1);
					}
				}
			}

		} catch (InterruptedException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public ElementType get(@NonNull Long elementTypeId) {

		Pair<ElementType, ElementData> pair = this.elementTypesMap.get(elementTypeId); 
		
		return pair != null ? pair.getLeft() : null;
	}
	
	public ElementData getElementData(@NonNull Long elementTypeId) {
		
		Pair<ElementType, ElementData> pair = this.elementTypesMap.get(elementTypeId);

		return pair != null ? pair.getRight() : null;
	}
	
	public Element getElement(@NonNull Long elementTypeId, @NonNull Long elementId) {
		
		Element element = null;
		Pair<ElementType, ElementData> pair = this.elementTypesMap.get(elementTypeId);
		ElementData elementData = pair != null ? pair.getRight() : null;
		
		if (elementData != null) {
			
			element = elementData.get(elementId);
		}

		return element;
	}
	
	public List<Element> getElements() {
		
		List<Element> elements = new ArrayList<>();
		
		if (!MapUtils.isEmpty(this.elementTypesMap)) {
			
			for (Map.Entry<Long, Pair<ElementType, ElementData>> entry : this.elementTypesMap.entrySet()) {
				
				Pair<ElementType, ElementData> pair = entry.getValue();
				
				if (pair != null && pair.getRight() != null) {
					
					elements.addAll(pair.getRight().getAll());
				}
			}
		}
		
		return elements;
	}
	
	public List<Alarm> getAlarms() {

		List<Alarm> alarms = new ArrayList<>();
		List<Element> elements = getElements();

		if (!CollectionUtils.isEmpty(elements)) {
			
			for (Element element : elements) {
				
				List<Alarm> elementAlarms = element.getAlarms();
				
				if (!CollectionUtils.isEmpty(elementAlarms)) {
					
					alarms.addAll(elementAlarms);
				}
			}
		}

		return alarms;
	}
	
	public List<ElementState> getElementStates() {

		List<ElementState> elementStates = null;
		List<Element> elements = getElements();

		if (!CollectionUtils.isEmpty(elements)) {
			
			elementStates = elements.stream().filter(e -> e.getElementState() != null).map(Element::getElementState).collect(Collectors.toList());
		}

		return elementStates;
	}
	
	public long getThreadDataWriterInfo(ThreadDataWriterInfo threadDataWriterInfo) {
		
		long value = 0L;
		
		ThreadDataWriter threadDataWriter = (ThreadDataWriter) this.threads.get(ThreadDataWriter.class);
		
		if (threadDataWriterInfo == ThreadDataWriterInfo.DEQUEUED) {
		
			value = threadDataWriter.getDequeued();
		}
		else if (threadDataWriterInfo == ThreadDataWriterInfo.PERSISTED) {
			
			value = threadDataWriter.getPersisted();
		}
		else if (threadDataWriterInfo == ThreadDataWriterInfo.DELETED) {
			
			value = threadDataWriter.getDeleted();
		}
		else if (threadDataWriterInfo == ThreadDataWriterInfo.OMITTED) {
			
			value = threadDataWriter.getOmitted();
		}
		else if (threadDataWriterInfo == ThreadDataWriterInfo.QUEUED) {
			
			value = threadDataWriter.getQueued();
		}
		
		return value;
	}
	
	public boolean getThreadDataWriterStat(ThreadDataWriterStat threadDataWriterStat) {
		
		boolean value = false;
		
		ThreadDataWriter threadDataWriter = (ThreadDataWriter) this.threads.get(ThreadDataWriter.class);
		
		if (threadDataWriterStat == ThreadDataWriterStat.ISALIVE) {
		
			value = threadDataWriter.isAlive();
		}
		else if (threadDataWriterStat == ThreadDataWriterStat.WORKING) {
			
			value = threadDataWriter.isWorking();
		}
		
		return value;
	}
	
	public void lock(@NonNull Long elementTypeId) {
		
		this.lock(elementTypeId, null);
	}
	
	public void lock(@NonNull Long elementTypeId, Long elementId) {
		
		Pair<ElementType, ElementData> pair = this.elementTypesMap.get(elementTypeId);
		ElementData elementData = pair != null ? pair.getRight() : null;
		
		if (elementData != null) {
			
			if (elementId != null) {
				
				elementData.lock(elementId);
			}
			else {
				
				elementData.getAll().forEach(element -> elementData.lock(element.getId()));
			}
		}
	}
	
	public void unlock(@NonNull Long elementTypeId) {
		
		this.unlock(elementTypeId, null);
	}
	
	public void unlock(@NonNull Long elementTypeId, Long elementId) {
		
		Pair<ElementType, ElementData> pair = this.elementTypesMap.get(elementTypeId);
		ElementData elementData = pair != null ? pair.getRight() : null;
		
		if (elementData != null) {
			
			if (elementId != null) {
				
				elementData.unlock(elementId);
			}
			else {
				
				elementData.getAll().forEach(element -> elementData.unlock(element.getId()));
			}	
		}
	}
}
