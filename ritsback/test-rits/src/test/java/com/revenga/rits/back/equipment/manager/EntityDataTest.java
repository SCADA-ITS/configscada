package com.revenga.rits.back.equipment.manager;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.Test;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

import com.github.javafaker.Faker;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.equipment.manager.data.ElementData;
import com.revenga.rits.back.equipment.manager.data.RootData.ThreadDataWriterInfo;
import com.revenga.rits.back.equipment.manager.data.RootData.ThreadDataWriterStat;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntityDataTest {
	
	private final static int THREAD_NUM = 3;
	private final static int VALUES_TO_GENERATE = 50000;
	private final static int VALUES_PER_SECOND = 500;
	private final static Long[] ELEMENT_TYPE_IDS = new Long[] { 2L, 3L, 4L, 9L, 16L, 53L, 71L };
//	private final static String[] ELEMENTS = new String[] { "Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4",
//			"Element:9:5", "Element:9:6", "Element:9:7", "Element:9:8", "Element:9:9", "Element:9:10" };
//	private final static Long[] ELEMENT_TYPE_IDS = new Long[] {};
	private final static String[] ELEMENTS = new String[] {};
	
	private List<Long> elementTypeIds = new ArrayList<>();
	
	private List<ThreadElementValueTest> threadElementValueTests = new ArrayList<>();
	
	private EntitiesManager em;

	class ThreadElementValueTest implements Runnable {

		private int valuesPending = 0;
		private  int valuesPerSecond = 0;
		
		private Random rnd = new Random();

		@Getter
		boolean alive = false;
		
		public ThreadElementValueTest(int valuesPending, int valuesPerSecond) {
		
			this.valuesPending = valuesPending;
			this.valuesPerSecond = valuesPerSecond;
					
		}
		
		@Override
		public void run() {
			
			this.alive = true;
			
			Instant instantInit = Instant.now();
			long valuesInitial = this.valuesPending; 
			
			Instant instantValuesPerSecondInit = Instant.now();
			long measuresGenerated = 0;
			
			while (this.valuesPending > 0) {
				
				try {
					
					Element element = getRandomElement();
					List<ElementValue> elementValuesUpdated = null;
					
					if (element != null) {
						
						elementValuesUpdated = getRandomMeasuredUpdated(element);
						
						if (!CollectionUtils.isEmpty(elementValuesUpdated)) {
							
							em.getRootData().getElementData(element.getElementTypeId()).updateElementValues(elementValuesUpdated, element);
						}
					}
					
					if (this.valuesPerSecond > 0) {
						
						Instant instantValuesPerSecondEnd = Instant.now();
						
						measuresGenerated += !CollectionUtils.isEmpty(elementValuesUpdated) ? elementValuesUpdated.size() : 0L;
						
						long diffTime = Duration.between(instantValuesPerSecondInit, instantValuesPerSecondEnd).toMillis();
						
						if (diffTime > 1000L) {
							
							if (measuresGenerated > this.valuesPerSecond) {
								
								Thread.sleep(1000 * ((measuresGenerated - this.valuesPerSecond) / this.valuesPerSecond));
							}
							
							instantValuesPerSecondInit = Instant.now();
							measuresGenerated = 0;
						}
						else {
							
							Thread.sleep(0, 1);
						}
					}
					else {
						
						Thread.sleep(0, 1);
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			long threadMill = Duration.between(instantInit, Instant.now()).toMillis();
			long valuesPerSecond = valuesInitial / (threadMill / 1000); 
			
			log.info(Thread.currentThread().getName() + " wrote " + valuesInitial + " values in " + threadMill + "ms. ("
					+ valuesPerSecond + " val/s "
					+ (this.valuesPerSecond > 0
							? "Efficency: " + Math.round(valuesPerSecond * 100 / this.valuesPerSecond) + "%"
							: "")
					+ ")");
			
			this.alive = false;
		}
		
		private Long getRandomElementTypeId() {
			
			Long elementTypeId = null;
			
			if (!ArrayUtils.isEmpty(ELEMENT_TYPE_IDS)) {
				
				elementTypeId = ELEMENT_TYPE_IDS[(this.rnd).nextInt(ELEMENT_TYPE_IDS.length)];
			}
			else {
			
				elementTypeId = elementTypeIds.get((this.rnd).nextInt(elementTypeIds.size()));
			}

			return elementTypeId;
		}
		
		private Element getRandomElement() {
			
			Element element = null;
			Long elementTypeId = getRandomElementTypeId();
			
			while (element == null) {
			
				Element elementSelected = null;
				
				if (elementTypeId != null) {
					
					ElementData elementData = em.getRootData().getElementData(elementTypeId);
					
					List<Element> elements = elementData.getAll();
					
					if (!CollectionUtils.isEmpty(elements)) {
						
						elementSelected = elements.get((this.rnd).nextInt(elements.size()));
							
						if (!ArrayUtils.isEmpty(ELEMENTS)) {
							
							if (ArrayUtils.contains(ELEMENTS, (new Key(elementSelected)).getSimpleValue())) {
							
								element = elementSelected;
							}
						}
						else {
							
							if (StringUtils.isAllEmpty(elementSelected.getOnChangeAlarm(),
									elementSelected.getOnChangeMeasure(), elementSelected.getOnChangeState())) {
								
								element = elementSelected;
							}
						}
					}
					else {
						
						elementTypeId = getRandomElementTypeId();
					}
				}
			}
			
			return element;
		}
		
		private List<ElementValue> getRandomMeasuredUpdated(Element element) {
			
			List<ElementValue> elementValuesUpdated = new ArrayList<>();
			List<ElementValue> elementValues = element.getElementValues();
			
			if (!CollectionUtils.isEmpty(elementValues)) {
				
				elementValues = elementValues.stream().filter(e -> e.getParamTypeId().equals(ParamType.MEASURE)).collect(Collectors.toList());
				
				if (!CollectionUtils.isEmpty(elementValues)) {
					
					int valuesCount = 0;
					
					do {
						
						valuesCount = (this.rnd).nextInt(elementValues.size()) + 1;
						
					} while (valuesCount > valuesPending);
					
					valuesPending -= valuesCount;
					
					Set<Integer> indexes = new HashSet<>();
					
					while (valuesCount-- > 0) {
						
						int idx;
						
						do {
							
							idx = (this.rnd).nextInt(elementValues.size());
							
						} while (indexes.contains(Integer.valueOf(idx)));
						
						indexes.add(Integer.valueOf(idx));
						
						ElementValue elementValue = elementValues.get(idx);
						ElementValue elementValueUpdated = null;
						
						try {
							elementValueUpdated = (ElementValue) elementValue.clone();
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						if (elementValue.getElementTypeParam().getDataTypeId() == DataType.DATA_TYPE_ALPHANUMERIC) {

							elementValueUpdated.setValue((new Faker()).name().firstName());
						}
						else if (elementValue.getElementTypeParam().getDataTypeId() == DataType.DATA_TYPE_INTEGER) {
							
							elementValueUpdated.setValue(String.valueOf((this.rnd).nextInt(10000)));
						}
						else if (elementValue.getElementTypeParam().getDataTypeId() == DataType.DATA_TYPE_REAL) {
							
							elementValueUpdated.setValue(String.valueOf((this.rnd).nextFloat() * 10000));
						}
						else if (elementValue.getElementTypeParam().getDataTypeId() == DataType.DATA_TYPE_BOOLEAN) {
							
							elementValueUpdated.setValue(String.valueOf((Instant.now().toEpochMilli() % 2) == 0));
						}
						else if (elementValue.getElementTypeParam().getDataTypeId() == DataType.DATA_TYPE_TIMESTAMP) {
							
							elementValueUpdated.setValue(String.valueOf(Instant.now().toEpochMilli()));
						}
						
						elementValuesUpdated.add(elementValueUpdated);
					}
				}
			}
			
			return elementValuesUpdated;
		}
	}
	
	
	@Test
	public void main() {
		
		log.debug("[INI] " + this.getClass().getSimpleName());	
		this.em = EntitiesManager.getInstance();
		
		String timeEstimated = "Unknown"; 
		if (VALUES_PER_SECOND > 0) {
			
			timeEstimated = DurationFormatUtils.formatDurationWords((VALUES_TO_GENERATE / VALUES_PER_SECOND) * 1000,
					true, false);			
		}
		
		log.info("");
		log.info("===================== T E S T =====================");
		log.info("");
		log.info("Measures        " + VALUES_TO_GENERATE);
		log.info("Values / sec:   " + VALUES_PER_SECOND);
		log.info("Threads:        " + THREAD_NUM);
		log.info("Element types:  " + Arrays.toString(ELEMENT_TYPE_IDS));
		log.info("Elements:       " + Arrays.toString(ELEMENTS));
		log.info("Time estimated: " + timeEstimated);
		log.info("");
		log.info("===================================================");
		log.info("");

		loadElementTypeIds();
		
		int i = 0;
		
		int modValues = VALUES_TO_GENERATE % THREAD_NUM;
		
		for (i = 0; i < THREAD_NUM; i++) {
			
			int valuesToProc = VALUES_TO_GENERATE / THREAD_NUM;
			
			valuesToProc = (i == THREAD_NUM - 1) ? valuesToProc + modValues : valuesToProc;
			
			this.threadElementValueTests.add(new ThreadElementValueTest(valuesToProc, VALUES_PER_SECOND / THREAD_NUM));	
		}
		
		i = 0;
		
		Instant instantInit = Instant.now();
		
		for (ThreadElementValueTest th : this.threadElementValueTests) {
			
			(new Thread(th, th.getClass().getSimpleName() + "_" + ++i)).start();
		}
		
		boolean isThreadsAlive = false;
		boolean threadsEnd = false;
		
		do {
			
			isThreadsAlive = false;
			
			try {
				
				for (ThreadElementValueTest th : this.threadElementValueTests) {
					
					isThreadsAlive |= th.isAlive();
					
					if (isThreadsAlive) {
						
						break;
					}
				}
				
				if (!isThreadsAlive && !threadsEnd) {
					
					log.info(VALUES_TO_GENERATE + " measures have been queued by " + THREAD_NUM
							+ " threads. Waiting to end persistence..");
					
					threadsEnd = true;
				}
				
				isThreadsAlive |= em.getRootData().getThreadDataWriterStat(ThreadDataWriterStat.WORKING);
				
				Thread.sleep(0, 1);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} while (isThreadsAlive);
		
		this.em.getRootData().stop();
		
		long queued = em.getRootData().getThreadDataWriterInfo(ThreadDataWriterInfo.QUEUED);
		long dequeued = em.getRootData().getThreadDataWriterInfo(ThreadDataWriterInfo.DEQUEUED);
		long persisted = em.getRootData().getThreadDataWriterInfo(ThreadDataWriterInfo.PERSISTED);
		long omitted = em.getRootData().getThreadDataWriterInfo(ThreadDataWriterInfo.OMITTED);
		long deleted = em.getRootData().getThreadDataWriterInfo(ThreadDataWriterInfo.DELETED);
		
		long timeElapsedTotal = Duration.between(instantInit, Instant.now()).toMillis();
		
		log.info("");
		log.info("================ S U M M A R Y ================");
		log.info("");
		log.info("Measures    " + VALUES_TO_GENERATE);
		log.info("Queued:     " + queued);
		log.info("Dequeued:   " + dequeued + "(" + Math.round(dequeued / (timeElapsedTotal / 1000)) + " val/s)");
		log.info("Persisted:  " + persisted + "(" + Math.round(persisted / (timeElapsedTotal / 1000)) + " val/s)");
		log.info("Omitted:    " + omitted);
		log.info("Deleted:    " + deleted);
		log.info("Total time: " + DurationFormatUtils.formatDurationWords(timeElapsedTotal, true, false));
		log.info("");
		log.info("===============================================");
		log.info("");
		
		log.info("[END] " + this.getClass().getSimpleName());
		
		assertEquals(VALUES_TO_GENERATE, queued);
		assertEquals(queued, dequeued);
		assertEquals(queued, persisted + omitted + deleted);
	}
	
	private void loadElementTypeIds() {
		
		this.elementTypeIds = this.em.getElementsType().stream().map(e -> e.getId()).collect(Collectors.toList());
	}
}
