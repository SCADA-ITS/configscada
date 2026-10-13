package com.revenga.rits.back.pubstomp;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.revenga.rits.back.cache.core.manager.CacheManager;
import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetAlarmsPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetElementStatesPublishCommand;
import com.revenga.rits.back.data.core.model.command.SetElementValuesPublishCommand;
import com.revenga.rits.back.front.core.model.helper.AlarmHelper;
import com.revenga.rits.back.front.core.model.helper.ElementTypeStateHelper;
import com.revenga.rits.back.front.core.model.helper.ElementValueHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueVO;

@SpringBootTest
public class ElementByElementTypePublisherTest {

	public ElementByElementTypePublisherTest() {

		super();
	}

	@Test
	public void sendCommandElementValuesByElementTypePublisher() {

		SetElementValuesPublishCommand setElementValuesPublishCommand = null;

		ElementValueVO elementValueVO = new ElementValueVO();
		List<ElementValueVO> elementValuesVO = new ArrayList<ElementValueVO>();

		elementValueVO.setId("ElementValue:1:1:1:2");
		elementValueVO.setValue("valor test elias");

		elementValuesVO.add(elementValueVO);

		for (ElementValueVO elementValuVO : elementValuesVO) {

			ElementValue elementValue = ElementValueHelper.get(elementValuVO);

			if (setElementValuesPublishCommand == null) {

				setElementValuesPublishCommand = new SetElementValuesPublishCommand();
			}

			setElementValuesPublishCommand.addElementValue(elementValue);

		}

		if (setElementValuesPublishCommand != null) {

			sendCommand(setElementValuesPublishCommand);

		}

	}

	@Test
	public void sendCommandElementStatesByElementTypePublisher() {

		SetElementStatesPublishCommand setElementStatesPublishCommand = null;

		ElementState elementState = new ElementState();
		List<ElementState> elementStates = new ArrayList<ElementState>();

		ElementTypeStateVO elementTypeStateVO = new ElementTypeStateVO();

		elementTypeStateVO.setId("ElementTypeState:1:1");

		ElementTypeState elementTypeState = ElementTypeStateHelper.get(elementTypeStateVO);

		elementState.setElementTypeId(elementTypeState.getElementTypeId());
		elementState.setStateId(elementTypeState.getStateId());
		elementState.setElementId(1l);
		
		elementStates.add(elementState);
		
		for (ElementState elementState2 : elementStates) {
			
			if (setElementStatesPublishCommand == null) {

				setElementStatesPublishCommand = new SetElementStatesPublishCommand();
			}

			setElementStatesPublishCommand.addElementState(elementState2);
			
		}


		if (setElementStatesPublishCommand != null) {

			sendCommand(setElementStatesPublishCommand);

		}

	}
	
	@Test
	public void sendCommandAlarmsByElementTypePublisher() {

		SetAlarmsPublishCommand setAlarmsPublishCommand = null;


		AlarmVO alarmVO = new AlarmVO();
		
		alarmVO.setAlarmConfigId("AlarmConfig:1");
		alarmVO.setSourceElementId("Element:1:1");
		alarmVO.setTargetElementId("Element:2:2");
		
		List<AlarmVO> alarmsVO = new ArrayList<AlarmVO>();
		
		alarmsVO.add(alarmVO);
		
		for (AlarmVO alarmaVO : alarmsVO) {
			
			Alarm alarm = AlarmHelper.get(alarmaVO);
			
			if (setAlarmsPublishCommand == null) {

				setAlarmsPublishCommand = new SetAlarmsPublishCommand();
			}

			setAlarmsPublishCommand.addAlarm(alarm);
			
		}


		if (setAlarmsPublishCommand != null) {

			sendCommand(setAlarmsPublishCommand);

		}

	}

	public Command sendCommand(Command command) {

		return CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
	}

}
