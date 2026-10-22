package com.revenga.rits.back.virtual.traffic.manager;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetElementMeasuresChangeCommand;
import com.revenga.rits.back.data.core.model.command.SetElementStatesChangeCommand;

public class TravelTimeTest {
	
	private static final int PERIODS = 5;
	private Map<Long, List<Integer>> detectorData = new HashMap<>();
	private final List<Long> detectors = Arrays.asList(new Long[] {50L, 51L, 52L, 53L, 54L, 55L, 56L});
	private static final boolean RANDOM_SPEED = true;;
	
	private static final int REFERENCE_SPEED = 60;
	private static final int THRESHOLD_SPEED = 20;
	
	@Test
	public void process() {
	
		if (RANDOM_SPEED) {
			
			loadRandomData();
		}
		else {
			
			loadData();
		}
		
		printData();
		sendData();
	}
	
	
	
	private void loadData() {
		
		detectorData.clear();
		
		int idx = 0;
		
		detectorData.put(detectors.get(idx++), Arrays.asList(new Integer[] {87, 90, 95, 85, 80}));
		detectorData.put(detectors.get(idx++), Arrays.asList(new Integer[] {70, 81, 91, 82, 83}));
		detectorData.put(detectors.get(idx++), Arrays.asList(new Integer[] {97, 96, 95, 94, 100}));
		detectorData.put(detectors.get(idx++), Arrays.asList(new Integer[] {101, 102, 103, 98, 99}));
		detectorData.put(detectors.get(idx++), Arrays.asList(new Integer[] {86, 79, 78, 70, 71}));
		detectorData.put(detectors.get(idx++), Arrays.asList(new Integer[] {61, 60, 62, 63, 65}));
		detectorData.put(detectors.get(idx++), Arrays.asList(new Integer[] {64, 67, 68, 72, 73}));
	}
	
	private void loadRandomData() {
		
		detectorData.clear();
		
		Collections.sort(detectors);
		
		for (int i = 0; i < 7; i++) {
			
			List<Integer> listValues = new ArrayList<>();
			
			for (int j = 0; j < 5; j++) {
				
				listValues.add(ThreadLocalRandom.current().nextInt(REFERENCE_SPEED - THRESHOLD_SPEED, REFERENCE_SPEED + THRESHOLD_SPEED + 1));
			}
		
			detectorData.put(detectors.get(i), listValues);
		}
	}

	private void printData() {
		
		System.out.println("Copy the following lines to excel: travel_time_test.xmlx");
		
		if (detectorData.size() == detectors.size()) {
		
			for (Long id : detectors) {
				
				List<Integer> dataDetector = detectorData.get(id);
				
				int[] dataDetectorArray = dataDetector.stream().mapToInt(Integer::intValue).toArray();
				
				System.out.println(IntStream.of(dataDetectorArray).mapToObj(String::valueOf).collect(Collectors.joining("\t")));
			}
		}
	}
	
	private void sendData() {
		
		try {
			
			SetElementStatesChangeCommand setElementStatesChangeCommand = new SetElementStatesChangeCommand();
			
			if (detectorData.size() == detectors.size()) {
				
				for (Long id : detectors) {
					
					ElementState elementStateDetector = getElementStateDetector(id, 1L);
					
					if (elementStateDetector != null) {
					
						setElementStatesChangeCommand.addElementState(elementStateDetector);
					}
				}
				
				sendCommand(setElementStatesChangeCommand);
				
				System.out.println("Sent states for detectors");
				
				while (true) {
					
					int currentPeriod = 0;
					
					System.out.println("Starting 5 minutes period");
					
					if (RANDOM_SPEED) {
						
						loadRandomData();
						printData();
					}
					
					while (currentPeriod < PERIODS) {
						
						long date = 0L;
						
						while (Instant.now().getEpochSecond() % 60 != 0) {
							
							Thread.sleep(1000);
						}
						
						date = Instant.now().getEpochSecond() * 1000;
						
						System.out.println("Sending data detectors for period " + (currentPeriod + 1) + "...");
						
						SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = new SetElementMeasuresChangeCommand();
						
						for (Long id : detectors) {
							
							List<Integer> dataDetector = detectorData.get(id);
							
							if (!CollectionUtils.isEmpty(dataDetector)) {
								
								Integer speedDetectorPeriod = dataDetector.get(currentPeriod);
								
								Element detector = getElementDetector(id, date, speedDetectorPeriod);
								
								if (detector != null) {
								
									setElementMeasuresChangeCommand.addElement(detector);
									
									System.out.println(String.format("Detector %d: %d Km/h", id, speedDetectorPeriod));
								}
							}
						}
						
						sendCommand(setElementMeasuresChangeCommand);
						
						System.out.println("Sent data detectors for period " + (currentPeriod + 1) + "...");
						
						currentPeriod++;
						
						if (currentPeriod < PERIODS) {
							
							System.out.println("Waiting to send data in next period ...");
						}
						
						Thread.sleep(1000);
					}
					
					System.out.println("Ending 5 minutes period");
					
					Thread.sleep(1000);
				}
						
				
			}
			
			System.out.println("No send more data");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	private Element getElementDetector(Long detectorId, Long date, Integer speed) {
		
		Element detector = new Element(3L, detectorId);
		
		List<ElementValue> elementValues = new ArrayList<>();
		
		detector.setElementValues(elementValues);
		
		ElementValue elementValueDate = new ElementValue();

		elementValueDate.setElementTypeId(detector.getElementTypeId());
		elementValueDate.setElementId(detector.getId());
		elementValueDate.setElementTypeParamId(1L);
		elementValueDate.setParamTypeId(2L);
		elementValueDate.setValue(String.valueOf(date));
		
		elementValues.add(elementValueDate);
		
		ElementValue elementValueSpeed = new ElementValue();

		elementValueSpeed.setElementTypeId(detector.getElementTypeId());
		elementValueSpeed.setElementId(detector.getId());
		elementValueSpeed.setElementTypeParamId(9L);
		elementValueSpeed.setParamTypeId(2L);
		elementValueSpeed.setValue(String.valueOf(speed));
		
		elementValues.add(elementValueSpeed);
		
		ElementValue elementValuePeriod = new ElementValue();

		elementValuePeriod.setElementTypeId(detector.getElementTypeId());
		elementValuePeriod.setElementId(detector.getId());
		elementValuePeriod.setElementTypeParamId(2L);
		elementValuePeriod.setParamTypeId(2L);
		elementValuePeriod.setValue("1");
		
		elementValues.add(elementValuePeriod);
		
		ElementValue elementValueNVehicles = new ElementValue();

		elementValueNVehicles.setElementTypeId(detector.getElementTypeId());
		elementValueNVehicles.setElementId(detector.getId());
		elementValueNVehicles.setElementTypeParamId(4L);
		elementValueNVehicles.setParamTypeId(2L);
		elementValueNVehicles.setValue("50");
		
		elementValues.add(elementValueNVehicles);
			
		ElementValue elementValueFaildata = new ElementValue();

		elementValueFaildata.setElementTypeId(detector.getElementTypeId());
		elementValueFaildata.setElementId(detector.getId());
		elementValueFaildata.setElementTypeParamId(13L);
		elementValueFaildata.setParamTypeId(2L);
		elementValueFaildata.setValue("false");
		
		elementValues.add(elementValueFaildata);

		return detector;
	}
	
	private ElementState getElementStateDetector(Long detectorId, Long stateId) {
		
		ElementState elementStateDetector = new ElementState();
		
		elementStateDetector.setElementTypeId(3L);
		elementStateDetector.setElementId(detectorId);
		
		elementStateDetector.setStateId(stateId);
		
		return elementStateDetector; 
	}
	
	private Command sendCommand(Command command) {

		return CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
	}
}
