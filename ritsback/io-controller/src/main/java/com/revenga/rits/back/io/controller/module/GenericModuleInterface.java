package com.revenga.rits.back.io.controller.module;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;

public interface GenericModuleInterface {

	void requestState(Element element);
	void requestAllStates();
	void requestMeasures(Element element);
	void requestAllMeasures();
	void requestAlarms(Element element);
	void requestAllAlarms();
	void requestKeepAlive();
	void sendCommand(SignallingCommand cmd);
	
	void receiveMeasures(Element element, List<ElementValue> elementValue);
	void receiveState(ElementState elementState);
	void receiveStates(List<ElementState> elementStates);
	void receiveAlarms(Element element, List<Pair<AlarmConfig, Boolean>> alarmConfigActivations);
	void receiveAlarm(Element element, Pair<AlarmConfig, Boolean> alarmConfigActivation);
}
