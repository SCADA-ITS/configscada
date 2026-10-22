package com.revenga.rits.back.data.core.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.SmStateMachine;
import com.revenga.rits.back.data.loader.service.CollectionManagerService;
import com.revenga.rits.back.entities.lib.repository.SmStateMachineRepository;

public class SmProcessorTest {

	List<ElementValue> elementValues;
	ElementValue coConcentration;
	ElementValue noConcentration;
	ElementValue kConcentration;
	
	SmProcessor processor;
	SmStateMachine stateMachine;

	public SmProcessorTest() {

		CollectionManagerService cms = new CollectionManagerService();
		cms.load();

		elementValues = new ArrayList<>();
		coConcentration = new ElementValue();
		noConcentration = new ElementValue();
		kConcentration = new ElementValue();

		coConcentration.setElementTypeId(19L);
		coConcentration.setElementTypeParamId(1L);
		coConcentration.setParamTypeId(2L);
		coConcentration.setValue("0");
		elementValues.add(coConcentration);

		noConcentration.setElementTypeId(20L);
		noConcentration.setElementTypeParamId(1L);
		noConcentration.setParamTypeId(2L);
		noConcentration.setValue("0.0");
		elementValues.add(noConcentration);

		kConcentration.setElementTypeId(21L);
		kConcentration.setElementTypeParamId(1L);
		kConcentration.setParamTypeId(2L);
		kConcentration.setValue("0.0");
		elementValues.add(kConcentration);

		processor = new SmProcessor(1000L);

		stateMachine = (new SmStateMachineRepository()).get(1L, true);
	}

	@Test
	public void Test1() throws InterruptedException {

		assertNotNull(stateMachine);

		processor.Init(stateMachine);
		assertEquals(1L, processor.getCurrentState().getId());

		processor.update(elementValues);
		assertEquals(1L, processor.getCurrentState().getId()); // INIT

		coConcentration.setValue("51");
		processor.update(elementValues);
		assertEquals(2L, processor.getCurrentState().getId()); // SERVICE

		Thread.sleep(1000L);

		coConcentration.setValue("76");
		processor.update(elementValues);
		assertEquals(2L, processor.getCurrentState().getId()); // SERVICE

		Thread.sleep(5000L);

		processor.update(elementValues);
		assertEquals(3L, processor.getCurrentState().getId()); // ALERT_BY_THRESHOLD

		Thread.sleep(1000L);

		processor.update(elementValues);
		assertEquals(3L, processor.getCurrentState().getId()); // ALERT_BY_THRESHOLD

		Thread.sleep(1000L);

		coConcentration.setValue("121");
		processor.update(elementValues);
		assertEquals(3L, processor.getCurrentState().getId()); // ALERT_BY_THRESHOLD

		Thread.sleep(3100L);

		processor.update(elementValues);
		assertEquals(5L, processor.getCurrentState().getId()); // ALARM_BY_THRESHOLD

		Thread.sleep(5100L);

		kConcentration.setValue("0.0121");
		processor.update(elementValues);

		assertEquals(7L, processor.getCurrentState().getId()); // EMERGENCY
	}
	

	@Test
	public void Test2() throws InterruptedException {

		processor.Init(stateMachine);
		assertEquals(1L, processor.getCurrentState().getId()); // INIT

		coConcentration.setValue("121");
		kConcentration.setValue("0.010");
		noConcentration.setValue("3.60");
		processor.update(elementValues);
		assertEquals(2L, processor.getCurrentState().getId()); // SERVICE

		Thread.sleep(5100L);

		processor.update(elementValues);
		assertEquals(3L, processor.getCurrentState().getId()); // ALERT_BY_THRESHOLD

		Thread.sleep(5100L);

		processor.update(elementValues);
		assertEquals(5L, processor.getCurrentState().getId()); // ALARM_BY_THRESHOLD

		Thread.sleep(5100L);

		coConcentration.setValue("74");
		kConcentration.setValue("0.0065");
		noConcentration.setValue("0.9");

		processor.update(elementValues);
		assertEquals(3L, processor.getCurrentState().getId()); // ALERT_BY_THRESHOLD

		Thread.sleep(5100L);

		coConcentration.setValue("76");
		kConcentration.setValue("0.0");
		noConcentration.setValue("0.0");
		processor.update(elementValues);
		assertEquals(3L, processor.getCurrentState().getId()); // ALERT_BY_THRESHOLD

		Thread.sleep(2000L);

		processor.update(elementValues);
		assertEquals(3L, processor.getCurrentState().getId()); // ALERT_BY_THRESHOLD

		Thread.sleep(2000L);

		processor.update(elementValues);
		assertEquals(3L, processor.getCurrentState().getId()); // ALERT_BY_THRESHOLD

		Thread.sleep(1000L);

		processor.update(elementValues);
		assertEquals(3L, processor.getCurrentState().getId()); // ALERT_BY_THRESHOLD
		
		Thread.sleep(2000L);
		
		processor.update(elementValues);
		assertEquals(3L, processor.getCurrentState().getId()); // ALERT_BY_THRESHOLD
		
		Thread.sleep(2000L);
		
		processor.update(elementValues);
		assertEquals(3L, processor.getCurrentState().getId()); // ALERT_BY_THRESHOLD
		
		Thread.sleep(5000L);
		
		processor.update(elementValues);
		assertEquals(3L, processor.getCurrentState().getId()); // ALERT_BY_THRESHOLD
		
		Thread.sleep(2000L);
		
		processor.update(elementValues);
		assertEquals(6L, processor.getCurrentState().getId()); // ALARM_BY_TIMER
	}
}
